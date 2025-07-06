package code2t.com.dvp.converter;

import code2t.com.dvp.models.req.CreatePartitionRequest;

public interface PartitionAnnoHandler extends AnnoHandler{
    /**
     * 处理注解业务逻辑
     *
     * @param builder     CreateCollectionRequest builder
     * @param entityClass 要处理的类
     */
    void handle(
            CreatePartitionRequest.CreatePartitionRequestBuilder builder,
            Class<?> entityClass);
}