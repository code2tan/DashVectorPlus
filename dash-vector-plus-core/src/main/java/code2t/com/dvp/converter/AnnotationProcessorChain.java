package code2t.com.dvp.converter;

import code2t.com.dvp.converter.handler.AnnotationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnnotationProcessorChain {
    private final List<AnnotationHandler> handlers = new ArrayList<>();

    /**
     * Add processor to the chain
     *
     * @param handler Annotation handler
     * @return AnnotationProcessorChain
     */
    public AnnotationProcessorChain addHandler(AnnotationHandler handler) {
        handlers.add(handler);
        return this;
    }

    /**
     * process all target annotation
     *
     * @param entityClass entityClass
     * @return CreateCollectionRequest
     */
    public Map<ActionType, Object> process(Class<?> entityClass) {

        DashVectorHandlerContext dashVectorHandlerContext = new DashVectorHandlerContext();
        handlers.stream()
                .filter(handler -> handler.supports(entityClass))
                .forEach(handler -> {
                    handler.handle(dashVectorHandlerContext, entityClass);
                });
        return dashVectorHandlerContext.finish();
    }
}
