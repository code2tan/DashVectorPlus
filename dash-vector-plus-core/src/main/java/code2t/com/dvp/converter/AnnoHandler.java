package code2t.com.dvp.converter;

import code2t.com.dvp.models.req.CreatePartitionRequest;

public interface AnnoHandler {
    /**
     * 判断是否支持处理该类的注解
     *
     * @param entityClass 要处理的类
     * @return 是否支持
     */
    boolean supports(Class<?> entityClass);
}