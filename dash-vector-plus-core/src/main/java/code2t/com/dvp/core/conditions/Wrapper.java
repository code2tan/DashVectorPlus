package code2t.com.dvp.core.conditions;

import code2t.com.dvp.cache.DashVectorCache;
import com.aliyun.dashvector.DashVectorClient;

/**
 * 构建器接口
 *
 * @param <W> 构建器类型
 * @param <T> 实体类型
 */
public interface Wrapper<W, T> {
    /**
     * 初始化 Wrapper
     *
     * @param client          DashVector客户端
     * @param conversionCache 实体缓存
     * @param entityType      实体类型
     * @return Wrapper
     */
    Wrapper<W, T> init(
            DashVectorClient client,
            DashVectorCache.ConversionCache conversionCache,
            Class<T> entityType);

    W wrapper();
}