package code2t.com.dvp.converter;

import code2t.com.dvp.converter.chain.AnnotationProcessorChain;
import code2t.com.dvp.converter.handler.DVCollectionAnnoHandler;
import code2t.com.dvp.converter.handler.DVFieldSchemaAnnoHandler;
import code2t.com.dvp.converter.handler.DVPartitionAnnoHandler;
import code2t.com.dvp.models.req.CreatePartitionRequest;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;

public class DashVectorConverter {

    public static CreateCollectionRequest convertCollection(Class<?> entityClass) {
        return new AnnotationProcessorChain()
                .addHandler(new DVCollectionAnnoHandler())
                .addHandler(new DVFieldSchemaAnnoHandler())
                .process(entityClass);
    }

    public static CreatePartitionRequest convertPartition(Class<?> entityClass) {
        DVPartitionAnnoHandler dvPartitionAnnoHandler = new DVPartitionAnnoHandler();

        CreatePartitionRequest.CreatePartitionRequestBuilder builder = CreatePartitionRequest.builder();
        if (dvPartitionAnnoHandler.supports(entityClass)) {
            dvPartitionAnnoHandler.handle(builder, entityClass);
        }
        return builder.build();
    }
}
