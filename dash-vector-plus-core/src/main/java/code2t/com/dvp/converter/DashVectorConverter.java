package code2t.com.dvp.converter;

import code2t.com.dvp.anno.DVCollection;
import code2t.com.dvp.anno.DVFieldSchema;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;
import com.aliyun.dashvector.proto.FieldType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DashVectorConverter {

    public static CreateCollectionRequest convert(Class<?> entityClass) {
        DVCollection dvCollection = entityClass.getAnnotation(DVCollection.class);
        if (Objects.isNull(dvCollection)) {
            return null;
        }

        Map<String, FieldType> filedSchemaMap = new HashMap<>();
        for (Field classField : entityClass.getDeclaredFields()) {
            Optional.ofNullable(classField.getAnnotation(DVFieldSchema.class))
                    .ifPresent(fieldAnno -> {
                        if (fieldAnno.require()) {
                            filedSchemaMap.put(
                                    fieldAnno.name().isEmpty() ? classField.getName() : fieldAnno.name(),
                                    parseFieldType(classField, fieldAnno)
                            );
                        }
                    });

        }

        return CreateCollectionRequest.builder()
                .name(dvCollection.name())
                .dimension(dvCollection.dimension())
                .dataType(dvCollection.dataType())
                .metric(dvCollection.metric())
                .timeout(dvCollection.timeout())
                .filedsSchema(filedSchemaMap)
                .build();
    }

    private static FieldType parseFieldType(Field classField, DVFieldSchema annotation) {
        FieldType fieldType = annotation.fieldType();

        if (annotation.fieldType() == FieldType.UNRECOGNIZED) {
            if (classField.getType().equals(Boolean.class)) {
                fieldType = FieldType.BOOL;
            } else if (classField.getType().equals(String.class)) {
                fieldType = FieldType.STRING;
            } else if (classField.getType().equals(Integer.class)) {
                fieldType = FieldType.INT;
            } else if (classField.getType().equals(Long.class)) {
                fieldType = FieldType.LONG;
            } else if (classField.getType().equals(Float.class)) {
                fieldType = FieldType.FLOAT;
            }
        }
        return fieldType;
    }
}
