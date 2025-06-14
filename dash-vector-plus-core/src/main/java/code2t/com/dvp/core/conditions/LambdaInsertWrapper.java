package code2t.com.dvp.core.conditions;

import code2t.com.dvp.models.vo.DashVectorResp;
import com.aliyun.dashvector.models.DocOpResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LambdaInsertWrapper<T> extends AbstractChainWrapper<T> {


    @Override
    public LambdaInsertWrapper<T> wrapper() {
        return this;
    }

    private DashVectorResp<List<DocOpResult>> insert(List<Object> param) {
        // return executeWithBackoffRetry(
        //         () -> {
        //             DVCollection collection = client.get(collectionName);
        //             // todo Response<List<DocOpResult>> response = collection.insert();
        //             Response<List<DocOpResult>> response = collection.insert(null);
        //
        //             return DashVectorResp.builder().success(response.isSuccess())
        //                     .data(response.getOutput())
        //                     .build();
        //         },
        //         ""
        // );
        return null;
    }
}