package code2t.com.dvp.core.conditions;

import code2t.com.dvp.cache.DashVectorCache;
import code2t.com.dvp.exception.RetryExhaustedException;
import code2t.com.dvp.models.vo.DashVectorResp;
import com.aliyun.dashvector.DashVectorClient;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

@Slf4j
public abstract class AbstractChainWrapper<T>
        extends ConditionBuilder<T>
        implements Wrapper<LambdaInsertWrapper<T>, T> {

    protected DashVectorCache.ConversionCache conversionCache;
    protected Class<T> entityType;
    protected String collectionName;
    protected DashVectorClient client;

    /**
     * 初始化 Wrapper
     *
     * @param client          DashVector客户端
     * @param conversionCache 实体缓存
     * @param entityType      实体类型
     * @return Wrapper
     */
    @Override
    public Wrapper<LambdaInsertWrapper<T>, T> init(DashVectorClient client,
                                                   DashVectorCache.ConversionCache conversionCache,
                                                   Class<T> entityType) {
        this.client = client;
        this.conversionCache = conversionCache;
        this.entityType = entityType;
        this.collectionName = conversionCache.collectionName();
        return this;
    }

    private static final Duration MAX_RETRY_DURATION = Duration.ofSeconds(2);
    private static final Duration INITIAL_DELAY = Duration.ofMillis(100);
    private static final int MAX_BACKOFF_SHIFT = 6;

    protected <R> DashVectorResp<R> executeWithBackoffRetry(Supplier<DashVectorResp<R>> action,
                                                            String retryableErrorMessage) {
        Objects.requireNonNull(action, "Action supplier cannot be null");
        Objects.requireNonNull(retryableErrorMessage, "Retryable error message cannot be null");

        final var startTime = System.currentTimeMillis();
        var attempt = 0;
        Exception lastException = null;

        while (true) {
            try {
                return action.get();
            } catch (final Exception e) {
                lastException = e;
                if (!isRetryable(e, retryableErrorMessage, startTime)) {
                    break;
                }
                attempt++;
                final var delay = calculateBackoffDelay(attempt, startTime);
                if (delay.isZero() || delay.isNegative()) {
                    log.warn("Max retry duration reached after {} attempts", attempt);
                    break;
                }

                log.warn("Attempt {} failed with error: '{}' Will retry after {}ms",
                        attempt, e.getMessage(), delay.toMillis());

                sleep(delay);
            }
        }

        final var duration = Duration.ofMillis(System.currentTimeMillis() - startTime);
        throw new RetryExhaustedException(attempt, duration, lastException);
    }

    private Duration calculateBackoffDelay(int attempt, long startTime) {
        final var shift = Math.min(attempt - 1, MAX_BACKOFF_SHIFT);
        final var baseDelay = INITIAL_DELAY.multipliedBy(1L << shift);

        final var elapsed = Duration.ofMillis(System.currentTimeMillis() - startTime);
        final var remaining = MAX_RETRY_DURATION.minus(elapsed);

        return baseDelay.compareTo(remaining) > 0 ? remaining : baseDelay;
    }

    private boolean isRetryable(Exception e, String retryableMsg, long startTime) {
        return e.getMessage() != null
                && e.getMessage().contains(retryableMsg)
                && Duration.ofMillis(System.currentTimeMillis() - startTime)
                .compareTo(MAX_RETRY_DURATION) < 0;
    }

    private void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Sleep interrupted after {}", duration, e);
        }
    }
}
