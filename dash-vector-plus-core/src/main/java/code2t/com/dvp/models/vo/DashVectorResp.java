package code2t.com.dvp.models.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashVectorResp<T> {

    private boolean success;
    private T data;

}
