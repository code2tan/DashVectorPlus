package code2t.com.dvp.log;


import code2t.com.dvp.log.spi.LogFrameworkAdapter;

import java.util.Comparator;
import java.util.Objects;
import java.util.ServiceLoader;

public class LogAdapterFactory {
    private static volatile LogFrameworkAdapter cachedAdapter;

    public static LogFrameworkAdapter getAdapter() {
        if (Objects.nonNull(cachedAdapter)) {
            return cachedAdapter;
        }
        synchronized (LogAdapterFactory.class) {
            if (Objects.nonNull(cachedAdapter)) {
                return cachedAdapter;
            }
            ServiceLoader<LogFrameworkAdapter> loader = ServiceLoader.load(LogFrameworkAdapter.class);

            cachedAdapter = loader.stream()
                    .map(ServiceLoader.Provider::get)
                    .filter(LogFrameworkAdapter::isSupported)
                    .min(Comparator.comparing(LogFrameworkAdapter::getPriority))
                    .orElseThrow(() -> new IllegalStateException("""
                                    No supported logging framework found! Please add one of:
                                    - Logback: ch.qos.logback:logback-classic
                                    - Log4j2: org.apache.logging.log4j:log4j-core + log4j-slf4j2-impl
                                    - JUL: JDK built-in (no extra dependencies needed)
                                    """
                            )
                    );
        }
        return cachedAdapter;
    }
}
