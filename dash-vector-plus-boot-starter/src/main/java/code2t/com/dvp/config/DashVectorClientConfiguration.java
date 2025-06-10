package code2t.com.dvp.config;

import code2t.com.dvp.service.DashVectorInit;
import com.aliyun.dashvector.DashVectorClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DashVectorClientConfiguration {
    private final DashVectorInit dashVectorInit;

    public DashVectorClientConfiguration(DashVectorInit dashVectorInit) {
        this.dashVectorInit = dashVectorInit;
    }

    @Bean
    public DashVectorClient dashVectorClient() {
        return dashVectorInit.getClient();
    }
}
