package code2t.com.dvp.service;

import com.aliyun.dashvector.DashVectorClient;

public interface ClientBuilder {
    /**
     * 初始化
     */
    void initialize();


    /**
     * 获取客户端对象
     *
     * @return DashVectorClient
     */
    DashVectorClient getClient();
}
