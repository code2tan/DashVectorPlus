package code2t.com.dvp.models;

import lombok.Data;

import java.util.List;


/**
 * DashVector 配置信息
 */
@Data
public class DashVectorProperties {
    /**
     * api key
     */
    private String apiKey;
    /**
     * dash vector server address
     */
    private String endpoint;

    /**
     * timeout（s）
     */
    private Float timeout = 10.0f;
    /**
     * anno scan path
     */
    private List<String> packagesScan;

    /**
     * auto init not exist collection
     */
    private Boolean autoInitCollection = true;
}
