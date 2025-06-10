package code2t.com.dvp.service;

import code2t.com.dvp.config.DashVectorPropertiesConfiguration;
import code2t.com.dvp.log.LogLevelController;
import code2t.com.dvp.models.DashVectorProperties;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class DashVectorInit extends AbstractClientBuilder implements InitializingBean, DisposableBean {

    @Resource
    private DashVectorPropertiesConfiguration propertiesConfiguration;


    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialize();
    }

    public void initialize() {
        maybePrintBanner();
        LogLevelController.setLoggingEnabledForPackage("dvp.plus",
                propertiesConfiguration.getLogOpen(),
                propertiesConfiguration.getLogLevel());

        DashVectorProperties dashVectorProperties = new DashVectorProperties();
        BeanUtils.copyProperties(propertiesConfiguration, dashVectorProperties);

        super.setProperties(dashVectorProperties);
        super.initialize();
    }

    private void maybePrintBanner() {
        if (propertiesConfiguration.getBanner()) {
            printBanner();
        }
    }

    private static void printBanner() {
        String banner = """
                 _____            _ __      __       _             _____  _          \s
                |  __ \\          | |\\ \\    / /      | |           |  __ \\| |         \s
                | |  | | __ _ ___| |_\\ \\  / /__  ___| |_ ___  _ __| |__) | |_   _ ___\s
                | |  | |/ _` / __| '_ \\ \\/ / _ \\/ __| __/ _ \\| '__|  ___/| | | | / __|
                | |__| | (_| \\__ \\ | | \\  /  __/ (__| || (_) | |  | |    | | |_| \\__ \\
                |_____/ \\__,_|___/_| |_|\\/ \\___|\\___|\\__\\___/|_|  |_|    |_|\\__,_|___/ \n
                """;

        System.out.println(banner);
        System.out.println("\033[36m Powered by Code2T. Version: 0.0.1 \033[0m");
    }
}
