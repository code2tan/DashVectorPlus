package code2t.com.dvp.anno;

import com.aliyun.dashvector.proto.CollectionInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DashVectorPartition {
    /**
     * 分区的名称
     */
    String[] name();

    /**
     * 向量维度
     */
    int dimension();

    /**
     * 向量数据类型，支持
     * <li>DataType.INT</li>
     * <li>DataType.FLOAT</li>
     */
    CollectionInfo.DataType dataType();

    /**
     * 距离度量
     * <li>Metric.cosine</li>
     * <li>Metric.euclidean</li>
     * <li>Metric.dotproduct</li>
     */
    CollectionInfo.Metric metric();

    /**
     * <li>timeout == -1：接口开启异步</li>
     * <li>timeout >= 0：接口开启同步并等待，若规定时间Collection未创建成功，则返回超时</li>
     */
    int timeout() default 0;
}