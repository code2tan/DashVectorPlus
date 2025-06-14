package code2t.com.dvp.converter.handler;

import code2t.com.dvp.anno.DVCollection;
import code2t.com.dvp.converter.DashVectorHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class DVCollectionAnnoHandler implements AnnotationHandler {
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
     * Annotation business handle
     *
     * @param builder    CreateCollectionRequest builder
     */
    @Override
    public void handle(
            DashVectorHandlerContext builder,
            Class<?> entityClass) {

        try {
            builder.getCollectionRequestBuilder()
                    .name(dvCollection.name())
                    .dimension(dvCollection.dimension())
                    .dataType(dvCollection.dataType())
                    .metric(dvCollection.metric())
                    .timeout(dvCollection.timeout());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}
