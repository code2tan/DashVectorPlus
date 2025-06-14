package code2t.com.dvp.domain;


import code2t.com.dvp.anno.DVCollection;
import code2t.com.dvp.anno.DVFieldSchema;
import com.aliyun.dashvector.proto.FieldType;
import lombok.Data;

import java.util.Map;

@Data
@DVCollection(name = "test_collection", dimension = 1024)
public class TestCollection {

    @DVFieldSchema(name = "test_id")
    private Long testId0;

    @DVFieldSchema
    private Boolean testField1;
    @DVFieldSchema
    private String testField2;
    @DVFieldSchema
    private Integer testField3;
    @DVFieldSchema
    private Float testField4;
    @DVFieldSchema
    private Long testField5;


    @DVFieldSchema(name = "test_field11")
    private Boolean testField11;
    @DVFieldSchema(name = "test_field22")
    private String testField22;
    @DVFieldSchema(name = "test_field33")
    private Integer testField33;
    @DVFieldSchema(name = "test_field44")
    private Float testField44;
    @DVFieldSchema(name = "test_field55")
    private Long testField55;

    @DVFieldSchema(name = "test_field111", fieldType = FieldType.BOOL)
    private Boolean testField111;
    @DVFieldSchema(name = "test_field222", fieldType = FieldType.STRING)
    private String testField222;
    @DVFieldSchema(name = "test_field333", fieldType = FieldType.INT)
    private Integer testField333;
    @DVFieldSchema(name = "test_field444", fieldType = FieldType.FLOAT)
    private Float testField444;
    @DVFieldSchema(name = "test_field555", fieldType = FieldType.LONG)
    private Long testField555;

    private Object other;
    private Map<String, Object> objectMap;
}
