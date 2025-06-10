package code2t.com.dvp.anno;


import com.aliyun.dashvector.proto.CollectionInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 集合注解 注解字段
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DashVectorCollection {
    /**
     * 集合的名称
     */
    String name();

    /**
     * 向量维度
     */
    int dimension();


    /**
     * 向量数据类型，支持
     * <p>
     * DataType.INT
     * <p>
     * DataType.FLOAT
     */
    CollectionInfo.DataType dataType() default CollectionInfo.DataType.FLOAT;

    /**
     * 距离度量支持
     * <p>
     * Metric.cosine
     * <p>
     * Metric.euclidean
     * <p>
     * Metric.dotproduct
     * <p>
     * <b>metric为cosine时，datatype必须为FLOAT</b>
     */
    CollectionInfo.Metric metric() default CollectionInfo.Metric.cosine;

    /**
     * timeout == null：接口开启同步，待Collection 创建成功后返回
     * <p>
     * timeout == -1：接口开启异步
     * <p>
     * timeout >= 0：接口开启同步并等待，若规定时间Collection未创建成功，则返回超时
     */
    int timeout() default -1;

    /**
     * embedding模型
     */
    String embeddingModel() default "";

    /**
     * 禁用动态字段
     */
    boolean enableDynamicField() default true;

}