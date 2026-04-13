package smarthome.bean;

public class SmartHomeAppConfig {
    private String androidVersion;
    private String appId;
    private String appName;
    private String osVersion;
    private String tenantId;
    private String webViewVersion;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId2) {
        this.appId = appId2;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName2) {
        this.appName = appName2;
    }

    public String getAndroidVersion() {
        return this.androidVersion;
    }

    public void setAndroidVersion(String androidVersion2) {
        this.androidVersion = androidVersion2;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public void setOsVersion(String osVersion2) {
        this.osVersion = osVersion2;
    }

    public String getWebViewVersion() {
        return this.webViewVersion;
    }

    public void setWebViewVersion(String webViewVersion2) {
        this.webViewVersion = webViewVersion2;
    }

    public String getTenantId() {
        return this.tenantId;
    }

    public void setTenantId(String tenantId2) {
        this.tenantId = tenantId2;
    }
}
