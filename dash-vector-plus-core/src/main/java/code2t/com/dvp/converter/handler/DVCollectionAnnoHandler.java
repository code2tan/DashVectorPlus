package code2t.com.dvp.converter.handler;

import code2t.com.dvp.anno.DVCollection;
import code2t.com.dvp.converter.CollectionAnnoHandler;
import code2t.com.dvp.toolkits.DVCollectionToolkits;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class DVCollectionAnnoHandler implements CollectionAnnoHandler {
    private DVCollection dvCollection;

    /**
     * Determine whether to support processing the annotation
     *
     * @param entityClass class by annotation
     * @return is supports
     */
    @Override
    public boolean supports(Class<?> entityClass) {
        dvCollection = entityClass.getDeclaredAnnotation(DVCollection.class);
        return Objects.nonNull(dvCollection);
    }

    /**
     * 处理注解业务逻辑
     *
     * @param builder     CreateCollectionRequest builder
     * @param entityClass 要处理的类
     */
    @Override
    public void handle(CreateCollectionRequest.CreateCollectionRequestBuilder builder, Class<?> entityClass) {
        try {
            builder.name(DVCollectionToolkits.parseCollectionName(entityClass))
                    .dimension(dvCollection.dimension())
                    .dataType(dvCollection.dataType())
                    .metric(dvCollection.metric())
                    .timeout(dvCollection.timeout());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
