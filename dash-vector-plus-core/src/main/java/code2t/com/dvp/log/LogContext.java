package code2t.com.dvp.log;


import code2t.com.dvp.log.spi.LogFrameworkAdapter;

public class LogContext {
    private static final LogFrameworkAdapter ADAPTER = LogAdapterFactory.getAdapter();

    public static void setLogLevel(String packageName, String level) {
        ADAPTER.setLogLevel(packageName, level);
    }

    public static void setLoggingEnabled(String packageName, boolean enabled, String level) {
        setLogLevel(packageName, enabled ? level : "OFF");
    }
}
