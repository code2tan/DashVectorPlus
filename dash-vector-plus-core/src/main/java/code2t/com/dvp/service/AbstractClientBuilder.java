package code2t.com.dvp.service;

import code2t.com.dvp.cache.DashVectorCollectionCache;
import code2t.com.dvp.converter.DashVectorConverter;
import code2t.com.dvp.models.DashVectorProperties;
import code2t.com.dvp.toolkits.ClassToolkits;
import code2t.com.dvp.toolkits.DVCollectionToolkits;
import com.aliyun.dashvector.DashVectorClient;
import com.aliyun.dashvector.DashVectorClientConfig;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static code2t.com.dvp.toolkits.ValidationToolkits.ensureNotBlank;
import static code2t.com.dvp.toolkits.ValidationToolkits.ensureNotEmpty;

@Slf4j
public class AbstractClientBuilder implements CollectionManageService {
    /**
     * 客户端配置信息
     */
    @Setter
    DashVectorProperties properties;

    /**
     * 客户端
     */
    protected DashVectorClient client;

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
        DashVectorCollectionCache.init(client);
        postProcess();
    }


    public void postProcess() {
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
        if (properties.getAutoInitpartition()) {
            // initPartition(classes);
        }
    }


    /**
     * init not exist collection
     *
     * @param annotatedClasses the collection object
     */
    public void initCollection(List<Class<?>> annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            String collectionName = DVCollectionToolkits.parseCollectionName(annotatedClass);
            if (DashVectorCollectionCache.exist(collectionName)) {
                log.info("Create collection [{}] using local cache", DVCollectionToolkits.parseCollectionName(annotatedClass));
                continue;
            }
            CreateCollectionRequest collectionRequest = DashVectorConverter.convertCollection(annotatedClass);

            Boolean response = create(collectionRequest);
            // update cache
            if (response) {
                DashVectorCollectionCache.put(collectionRequest.getName(), get(collectionRequest.getName()));
            }
            log.info("create dash vector collection -> {} response {}", collectionRequest.getName(), response);
        }
    }
}
