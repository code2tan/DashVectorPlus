package code2t.com.dvp.converter.chain;

import code2t.com.dvp.converter.CollectionAnnoHandler;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;

import java.util.ArrayList;
import java.util.List;

public class AnnotationProcessorChain {
    private final List<CollectionAnnoHandler> handlers = new ArrayList<>();

    /**
     * Add processor to the chain
     *
     * @param handler Annotation handler
     * @return AnnotationProcessorChain
     */
    public AnnotationProcessorChain addHandler(CollectionAnnoHandler handler) {
        handlers.add(handler);
        return this;
    }

    /**
     * process all target annotation
     *
     * @param entityClass entityClass
     * @return CreateCollectionRequest
     */
    public CreateCollectionRequest process(Class<?> entityClass) {
        CreateCollectionRequest.CreateCollectionRequestBuilder builder = CreateCollectionRequest.builder();
        handlers.stream()
                .filter(handler -> handler.supports(entityClass))
                .forEach(handler -> {
                    handler.handle(builder, entityClass);
                });
        return builder.build();
    }
}
