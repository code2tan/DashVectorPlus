package code2t.com.dvp.converter;

import code2t.com.dvp.converter.handler.DVCollectionAnnoHandler;
import code2t.com.dvp.converter.handler.DVFieldSchemaAnnoHandler;
import code2t.com.dvp.converter.handler.DVPartitionAnnoHandler;

import java.util.Map;

public class DashVectorConverter {

    public static Map<ActionType, Object> convert(Class<?> entityClass) {
        return new AnnotationProcessorChain()
                .addHandler(new DVCollectionAnnoHandler())
                .addHandler(new DVFieldSchemaAnnoHandler())
                .addHandler(new DVPartitionAnnoHandler())
                .process(entityClass);
    }
}
