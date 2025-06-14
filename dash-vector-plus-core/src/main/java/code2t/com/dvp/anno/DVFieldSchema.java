package code2t.com.dvp.anno;

import com.aliyun.dashvector.proto.FieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DVFieldSchema {

    /**
     * 字段名字
     */
    String name() default "";

    /**
     * Field类型
     */
    FieldType fieldType() default FieldType.UNRECOGNIZED;

    boolean require() default true;
}
