package code2t.com.dvp.converter;

import code2t.com.dvp.models.req.CreatePartitionRequest;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class DashVectorHandlerContext {
    private CreateCollectionRequest.CreateCollectionRequestBuilder collectionRequestBuilder;
    private CreatePartitionRequest.CreatePartitionRequestBuilder partitionRequestBuilder;

    Map<ActionType, Object> contexts = new HashMap<>();

    public DashVectorHandlerContext() {
        collectionRequestBuilder = CreateCollectionRequest.builder();
        partitionRequestBuilder = CreatePartitionRequest.builder();
    }

    public Map<ActionType, Object> finish() {
        contexts.put(ActionType.COLLECTION, collectionRequestBuilder.build());
        contexts.put(ActionType.PARTITION, partitionRequestBuilder.build());
        return contexts;
    }
}
