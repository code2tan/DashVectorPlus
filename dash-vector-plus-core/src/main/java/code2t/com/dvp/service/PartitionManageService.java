package code2t.com.dvp.service;

import com.aliyun.dashvector.DashVectorClient;
import com.aliyun.dashvector.DashVectorCollection;
import com.aliyun.dashvector.models.PartitionStats;
import com.aliyun.dashvector.models.responses.Response;
import com.aliyun.dashvector.proto.Status;

import java.util.List;

/**
 * Partition操作
 */
public interface PartitionManageService extends ClusterManageService {

    /**
     * 为Collection创建一个新的Partition
     *
     * @param collectionName 集合名称
     * @param partitionName  待创建的分区名称
     * @param timeout        timeout == null：接口开启同步，待Partition创建成功后返回，
     *                       timeout == -1：接口开启异步，
     *                       timeout >= 0：接口开启同步并等待，若规定时间Partition未创建成功，则返回超时
     * @return Bool，是否创建成功
     */
    default Boolean createPartition(String collectionName, String partitionName, Integer timeout) {
        DashVectorClient client = getClient();
        DashVectorCollection collection = client.get(collectionName);
        Response<Void> partition = collection.createPartition(partitionName, timeout);
        return partition.isSuccess();
    }

    /**
     * 获取Collection中一个已存在的Partition的状态
     *
     * @param collectionName 集合名称
     * @param partitionName  分区名称
     * @return com.aliyun.dashvector.proto.Status
     */
    default Status describePartition(String collectionName, String partitionName) {
        DashVectorClient client = getClient();
        DashVectorCollection collection = client.get(collectionName);
        Response<Status> status = collection.describePartition(partitionName);
        return status.getOutput();
    }


    /**
     * 获取Collection中所有Partition名称的列表
     *
     * @param collectionName Partition名称
     * @return List<String> Collection中所有Partition的列表
     */
    default List<String> listPartitions(String collectionName) {
        DashVectorClient client = getClient();
        DashVectorCollection collection = client.get(collectionName);
        Response<List<String>> partitions = collection.listPartitions();
        return partitions.getOutput();
    }

    /**
     * 获取Collection中一个已存在的Partition的统计信息，如Doc数等
     *
     * @param collectionName Partition名称
     * @param partitionName  Partition名称
     * @return PartitionStats
     */
    default PartitionStats statsPartition(String collectionName, String partitionName) {
        DashVectorClient client = getClient();
        DashVectorCollection collection = client.get(collectionName);
        Response<PartitionStats> response = collection.statsPartition(partitionName);
        return response.getOutput();
    }

    /**
     * 删除Collection中一个已存在的Partition
     *
     * @param collectionName Partition名称
     * @param partitionName  Partition名称
     * @return Boolean, 是否删除成功
     */
    default Boolean deletePartition(String collectionName, String partitionName) {
        DashVectorClient client = getClient();
        DashVectorCollection collection = client.get(collectionName);
        Response<Void> response = collection.deletePartition(partitionName);
        return response.isSuccess();
    }
}
