package org.glassfish.grizzly.http.server;

import java.nio.charset.Charset;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.utils.JdkVersion;

public class ServerFilterConfiguration {
    public static final int MAX_REQUEST_PARAMETERS = 10000;
    public static final String USE_SEND_FILE = "org.glassfish.grizzly.http.USE_SEND_FILE";
    private BackendConfiguration backendConfiguration;
    private ErrorPageGenerator defaultErrorPageGenerator;
    private Charset defaultQueryEncoding;
    private String httpServerName;
    private String httpServerVersion;
    private boolean isGracefulShutdownSupported;
    private int maxBufferedPostSize;
    private int maxFormPostSize;
    private long maxPostSize;
    private int maxRequestParameters;
    private boolean passTraceRequest;
    private boolean sendFileEnabled;
    private SessionManager sessionManager;
    private int sessionTimeoutSeconds;
    private boolean traceEnabled;

    public ServerFilterConfiguration() {
        this("Grizzly", Grizzly.getDotedVersion());
    }

    public ServerFilterConfiguration(String serverName, String serverVersion) {
        this.maxRequestParameters = 10000;
        this.maxPostSize = -1;
        this.maxFormPostSize = 2097152;
        this.maxBufferedPostSize = 2097152;
        this.sessionTimeoutSeconds = -1;
        this.isGracefulShutdownSupported = true;
        this.httpServerName = serverName;
        this.httpServerVersion = serverVersion;
        configureSendFileSupport();
        this.defaultErrorPageGenerator = new DefaultErrorPageGenerator();
    }

    public ServerFilterConfiguration(ServerFilterConfiguration configuration) {
        this.maxRequestParameters = 10000;
        this.maxPostSize = -1;
        this.maxFormPostSize = 2097152;
        this.maxBufferedPostSize = 2097152;
        this.sessionTimeoutSeconds = -1;
        this.isGracefulShutdownSupported = true;
        this.httpServerName = configuration.httpServerName;
        this.httpServerVersion = configuration.httpServerVersion;
        this.sendFileEnabled = configuration.sendFileEnabled;
        this.backendConfiguration = configuration.backendConfiguration;
        this.traceEnabled = configuration.traceEnabled;
        this.passTraceRequest = configuration.passTraceRequest;
        this.maxRequestParameters = configuration.maxRequestParameters;
        this.maxFormPostSize = configuration.maxFormPostSize;
        this.maxBufferedPostSize = configuration.maxBufferedPostSize;
        this.defaultQueryEncoding = configuration.defaultQueryEncoding;
        this.defaultErrorPageGenerator = configuration.defaultErrorPageGenerator;
        this.isGracefulShutdownSupported = configuration.isGracefulShutdownSupported;
        this.maxPostSize = configuration.maxPostSize;
        this.sessionTimeoutSeconds = configuration.sessionTimeoutSeconds;
        this.sessionManager = configuration.sessionManager;
    }

    public String getHttpServerName() {
        return this.httpServerName;
    }

    public void setHttpServerName(String httpServerName2) {
        this.httpServerName = httpServerName2;
    }

    public String getHttpServerVersion() {
        return this.httpServerVersion;
    }

    public void setHttpServerVersion(String httpServerVersion2) {
        this.httpServerVersion = httpServerVersion2;
    }

    public boolean isSendFileEnabled() {
        return this.sendFileEnabled;
    }

    public void setSendFileEnabled(boolean sendFileEnabled2) {
        this.sendFileEnabled = sendFileEnabled2;
    }

    public String getScheme() {
        BackendConfiguration config = this.backendConfiguration;
        if (config != null) {
            return config.getScheme();
        }
        return null;
    }

    public void setScheme(String scheme) {
        BackendConfiguration config = this.backendConfiguration;
        if (config == null) {
            config = new BackendConfiguration();
        }
        config.setScheme(scheme);
        this.backendConfiguration = config;
    }

    public BackendConfiguration getBackendConfiguration() {
        return this.backendConfiguration;
    }

    public void setBackendConfiguration(BackendConfiguration backendConfiguration2) {
        this.backendConfiguration = backendConfiguration2;
    }

    public boolean isPassTraceRequest() {
        return this.passTraceRequest;
    }

    public void setPassTraceRequest(boolean passTraceRequest2) {
        this.passTraceRequest = passTraceRequest2;
    }

    public boolean isTraceEnabled() {
        return this.traceEnabled;
    }

    public void setTraceEnabled(boolean enabled) {
        this.traceEnabled = enabled;
    }

    public int getMaxRequestParameters() {
        return this.maxRequestParameters;
    }

    public void setMaxRequestParameters(int maxRequestParameters2) {
        if (maxRequestParameters2 < 0) {
            this.maxRequestParameters = -1;
        } else {
            this.maxRequestParameters = maxRequestParameters2;
        }
    }

    @Deprecated
    public boolean isReuseSessionID() {
        return false;
    }

    @Deprecated
    public void setReuseSessionID(boolean isReuseSessionID) {
    }

    public long getMaxPostSize() {
        return this.maxPostSize;
    }

    public void setMaxPostSize(long maxPostSize2) {
        this.maxPostSize = maxPostSize2 < 0 ? -1 : maxPostSize2;
    }

    public int getMaxFormPostSize() {
        return this.maxFormPostSize;
    }

    public void setMaxFormPostSize(int maxFormPostSize2) {
        this.maxFormPostSize = maxFormPostSize2 < 0 ? -1 : maxFormPostSize2;
    }

    public int getMaxBufferedPostSize() {
        return this.maxBufferedPostSize;
    }

    public void setMaxBufferedPostSize(int maxBufferedPostSize2) {
        this.maxBufferedPostSize = maxBufferedPostSize2 < 0 ? -1 : maxBufferedPostSize2;
    }

    public Charset getDefaultQueryEncoding() {
        return this.defaultQueryEncoding;
    }

    public void setDefaultQueryEncoding(Charset defaultQueryEncoding2) {
        this.defaultQueryEncoding = defaultQueryEncoding2;
    }

    public ErrorPageGenerator getDefaultErrorPageGenerator() {
        return this.defaultErrorPageGenerator;
    }

    public void setDefaultErrorPageGenerator(ErrorPageGenerator defaultErrorPageGenerator2) {
        this.defaultErrorPageGenerator = defaultErrorPageGenerator2;
    }

    public boolean isGracefulShutdownSupported() {
        return this.isGracefulShutdownSupported;
    }

    public void setGracefulShutdownSupported(boolean isGracefulShutdownSupported2) {
        this.isGracefulShutdownSupported = isGracefulShutdownSupported2;
    }

    public int getSessionTimeoutSeconds() {
        return this.sessionTimeoutSeconds;
    }

    public void setSessionTimeoutSeconds(int sessionTimeoutSeconds2) {
        this.sessionTimeoutSeconds = sessionTimeoutSeconds2;
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager2) {
        this.sessionManager = sessionManager2;
    }

    private void configureSendFileSupport() {
        if ((System.getProperty("os.name").equalsIgnoreCase("linux") && !linuxSendFileSupported()) || System.getProperty("os.name").equalsIgnoreCase("HP-UX")) {
            this.sendFileEnabled = false;
        }
        if (System.getProperty(USE_SEND_FILE) != null) {
            this.sendFileEnabled = Boolean.valueOf(System.getProperty(USE_SEND_FILE)).booleanValue();
        }
    }

    private static boolean linuxSendFileSupported() {
        return JdkVersion.parseVersion("1.6.0_18").compareTo(JdkVersion.getJdkVersion()) <= 0;
    }
}
