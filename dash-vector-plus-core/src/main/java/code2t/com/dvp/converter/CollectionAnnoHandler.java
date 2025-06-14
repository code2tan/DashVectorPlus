package code2t.com.dvp.converter;

import com.aliyun.dashvector.models.requests.CreateCollectionRequest;

public interface CollectionAnnoHandler extends AnnoHandler{

    /**
     * 处理注解业务逻辑
     *
     * @param builder     CreateCollectionRequest builder
     * @param entityClass 要处理的类
     */
    void handle(
            CreateCollectionRequest.CreateCollectionRequestBuilder builder,
            Class<?> entityClass);
}