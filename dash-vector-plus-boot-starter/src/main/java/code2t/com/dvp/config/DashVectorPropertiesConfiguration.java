package code2t.com.dvp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "dvp")
public class DashVectorPropertiesConfiguration {
    /**
     * banner
     */
    private Boolean banner = true;
    /**
     * log switch
     */
    private Boolean logOpen = true;
    /**
     * log level
     */
    private String logLevel = "INFO";
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

}
