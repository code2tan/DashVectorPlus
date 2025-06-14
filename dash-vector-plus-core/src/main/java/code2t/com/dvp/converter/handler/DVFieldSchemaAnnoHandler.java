package code2t.com.dvp.converter.handler;

import code2t.com.dvp.anno.DVFieldSchema;
import code2t.com.dvp.converter.DashVectorHandlerContext;
import com.aliyun.dashvector.proto.FieldType;

import java.lang.reflect.Field;
import java.util.*;

public class DVFieldSchemaAnnoHandler implements AnnotationHandler {
    List<DVFieldSchema> fieldSchemas = new ArrayList<>();
    List<Field> fields = new ArrayList<>();

    /**
     * 判断是否支持处理该类的注解
     *
     * @param entityClass 要处理的类
     * @return 是否支持
     */
    @Override
    public boolean supports(Class<?> entityClass) {
        for (Field field : entityClass.getFields()) {
            DVFieldSchema fieldAnno = field.getDeclaredAnnotation(DVFieldSchema.class);
            if (Objects.nonNull(fieldAnno)) {
                fieldSchemas.add(fieldAnno);
                fields.add(field);
            }
        }
        return fieldSchemas.isEmpty();
    }


    /**
     * 处理注解业务逻辑
     *
     * @param context     处理上下文（包含构建器等）
     * @param entityClass 要处理的类
     */
    @Override
    public void handle(DashVectorHandlerContext context,
                       Class<?> entityClass) {
        Map<String, FieldType> filedSchemaMap = new HashMap<>();

        for (Field classField : fields) {
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
        context.getCollectionRequestBuilder()
                .filedsSchema(filedSchemaMap);
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
