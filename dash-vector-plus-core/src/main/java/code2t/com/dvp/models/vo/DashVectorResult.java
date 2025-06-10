package code2t.com.dvp.models.vo;

import lombok.Data;

@Data
public class DashVectorResult<T> {
    private T entity;
    private Float distance;
    private Object id;
    private Long total;
}
