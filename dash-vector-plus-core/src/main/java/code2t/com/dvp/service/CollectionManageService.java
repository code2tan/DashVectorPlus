package code2t.com.dvp.service;

import com.aliyun.dashvector.DashVectorClient;
import com.aliyun.dashvector.DashVectorCollection;
import com.aliyun.dashvector.models.CollectionMeta;
import com.aliyun.dashvector.models.CollectionStats;
import com.aliyun.dashvector.models.requests.CreateCollectionRequest;
import com.aliyun.dashvector.models.responses.Response;

import java.util.List;

public interface CollectionManageService extends ClusterManageService {

    default Boolean create(CreateCollectionRequest request) {
        DashVectorClient client = getClient();
        Response<Void> response = client.create(request);
        return response.isSuccess();
    }

    default Boolean create(String collectionName, int dimension) {
        DashVectorClient client = getClient();
        Response<Void> response = client.create(collectionName, dimension);
        return response.isSuccess();
    }

    default Boolean delete(String collectionName) {
        DashVectorClient client = getClient();
        Response<Void> response = client.delete(collectionName);
        return response.isSuccess();
    }

    default CollectionMeta describe(String collectionName) {
        DashVectorClient client = getClient();
        Response<CollectionMeta> response = client.describe(collectionName);
        return response.getOutput();
    }

    default DashVectorCollection get(String collectionName) {
        return getClient().get(collectionName);
    }

    default List<String> list() {
        DashVectorClient client = getClient();
        Response<List<String>> response = client.list();
        return response.getOutput();
    }

    default CollectionStats stats(String collectionName) {
        DashVectorClient client = getClient();
        DashVectorCollection collection = client.get(collectionName);
        Response<CollectionStats> response = collection.stats();
        return response.getOutput();
    }
}
