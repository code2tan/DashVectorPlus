package code2t.com.dvp.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DVPartition {
    /**
     * 分区的名称
     */
    String[] name();

    /**
     * timeout == null：接口开启同步，待Collection 创建成功后返回
     * <p>
     * timeout == -1：接口开启异步
     * <p>
     * timeout >= 0：接口开启同步并等待，若规定时间Collection未创建成功，则返回超时
     */
    int timeout() default -1;
}