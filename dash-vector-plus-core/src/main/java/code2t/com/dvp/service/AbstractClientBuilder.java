package code2t.com.dvp.service;

import code2t.com.dvp.cache.DashVectorCache;
import code2t.com.dvp.cache.DashVectorCollectionCache;
import code2t.com.dvp.converter.DashVectorConverter;
import code2t.com.dvp.models.DashVectorProperties;
import code2t.com.dvp.toolkits.ClassToolkits;
import com.aliyun.dashvector.DashVectorClient;
import com.aliyun.dashvector.DashVectorClientConfig;
import com.aliyun.dashvector.DashVectorCollection;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;
import com.aliyun.dashvector.models.responses.Response;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static code2t.com.dvp.toolkits.ValidationToolkits.ensureNotBlank;
import static code2t.com.dvp.toolkits.ValidationToolkits.ensureNotEmpty;

@Slf4j
public class AbstractClientBuilder implements ClusterManageService, ClientBuilder {
    /**
     * 客户端配置信息
     */
    @Setter
    DashVectorProperties properties;

    /**
     * 客户端
     */
    DashVectorClient client;

    @Override
    public DashVectorClient getClient() {
        return client;
    }

    /**
     * 初始化
     */
    @Override
    public void initialize() {
        DashVectorClientConfig config = DashVectorClientConfig.builder()
                .apiKey(ensureNotBlank(properties.getApiKey(), "apiKey"))
                .endpoint(ensureNotBlank(properties.getEndpoint(), "endpoint"))
                .timeout(properties.getTimeout())
                .build();
        client = new DashVectorClient(config);
        postProcess();
    }


    public void postProcess() {
        initCache();
        List<Class<?>> classes = ClassToolkits.getClass(
                ensureNotEmpty(properties.getPackagesScan(), "packageScan")
        );
        if (classes.isEmpty()) {
            log.warn("no any collections have been initialized, see if the [packagesScan] parameter is configured correctly. :( !");
            return;
        }

        if (properties.getAutoInitCollection()) {
            initCollection(classes);
        }
    }

    /**
     * init collection cache
     */
    private void initCache() {
        Response<List<String>> response = client.list();
        if (!response.isSuccess()) {
            log.warn("failed to init dash vector cache [{}]", response.getMessage());
            return;
        }

        if (response.isSuccess()) {
            List<CompletableFuture<DashVectorCollection>> futures = response.getOutput().stream()
                    .map(collectionName -> CompletableFuture.supplyAsync(() -> client.get(collectionName)))
                    .toList();

            List<DashVectorCollection> collectionList = futures.stream()
                    .map(CompletableFuture::join)
                    .toList();

            DashVectorCollectionCache.init(collectionList);
        }

    }

    /**
     * init not exist collection
     *
     * @param annotatedClasses the collection object
     */
    public void initCollection(List<Class<?>> annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            if (DashVectorCollectionCache.exists(annotatedClass.getName())) {
                continue;
            }
            DashVectorCache.ConversionCache conversionCache = DashVectorCache.get(annotatedClass.getName());

            if (Objects.nonNull(conversionCache)) {
                log.info("Create collection [{}] using conversion cache", annotatedClass.getName());
                return;
            }

            CreateCollectionRequest collectionRequest = DashVectorConverter.convert(annotatedClass);
            if (collectionRequest == null) {
                continue;
            }
            Response<Void> response = client.create(collectionRequest);
            log.info("create dash vector collection {} {}", collectionRequest.getName(), response.getMessage());
        }
    }
}
