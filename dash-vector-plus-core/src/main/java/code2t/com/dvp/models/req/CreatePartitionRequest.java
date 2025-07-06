package code2t.com.dvp.models.req;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreatePartitionRequest {
    private List<String> partitionName;
}
