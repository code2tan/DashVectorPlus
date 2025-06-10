package code2t.com.dvp.mapper;

import code2t.com.dvp.core.mapper.BaseMapper;
import com.aliyun.dashvector.DashVectorClient;

public class DashVectorMapper<T> extends BaseMapper<T> {

    private final DashVectorClient dashVectorClient;

    public DashVectorMapper(DashVectorClient dashVectorClient) {
        this.dashVectorClient = dashVectorClient;
    }

}