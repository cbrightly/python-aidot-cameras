package org.glassfish.grizzly.http.server;

public class BackendConfiguration {
    private String remoteUserMapping;
    private String scheme;
    private String schemeMapping;

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme2) {
        this.scheme = scheme2;
        this.schemeMapping = null;
    }

    public String getSchemeMapping() {
        return this.schemeMapping;
    }

    public void setSchemeMapping(String schemeMapping2) {
        this.schemeMapping = schemeMapping2;
        this.scheme = null;
    }

    public String getRemoteUserMapping() {
        return this.remoteUserMapping;
    }

    public void setRemoteUserMapping(String remoteUserMapping2) {
        this.remoteUserMapping = remoteUserMapping2;
    }
}
