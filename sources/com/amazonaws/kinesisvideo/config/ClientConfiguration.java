package com.amazonaws.kinesisvideo.config;

import java.net.URI;

public final class ClientConfiguration {
    private String apiName;
    private Integer connectionTimeoutInMillis;
    private String materialSet;
    private Integer readTimeoutInMillis;
    private String region;
    private String serviceName;
    private String streamName;
    private URI streamUri;

    public String toString() {
        return "ClientConfiguration [region=" + this.region + ", serviceName=" + this.serviceName + ", apiName=" + this.apiName + ", materialSet=" + this.materialSet + ", streamName=" + this.streamName + ", streamUri=" + this.streamUri + ", connectionTimeoutInMillis=" + this.connectionTimeoutInMillis + ", readTimeoutInMillis=" + this.readTimeoutInMillis + "super=" + super.toString() + "]";
    }

    ClientConfiguration(ClientConfigurationBuilder builder) {
        this.region = builder.region;
        this.serviceName = builder.serviceName;
        this.apiName = builder.apiName;
        this.materialSet = builder.materialSet;
        this.streamName = builder.streamName;
        this.streamUri = builder.streamUri;
        this.connectionTimeoutInMillis = builder.connectionTimeoutInMillis;
        this.readTimeoutInMillis = builder.readTimeoutInMillis;
    }

    public static ClientConfigurationBuilder builder() {
        return new ClientConfigurationBuilder();
    }

    public static class ClientConfigurationBuilder {
        /* access modifiers changed from: private */
        public String apiName;
        /* access modifiers changed from: private */
        public Integer connectionTimeoutInMillis;
        /* access modifiers changed from: private */
        public String materialSet;
        /* access modifiers changed from: private */
        public Integer readTimeoutInMillis;
        /* access modifiers changed from: private */
        public String region;
        /* access modifiers changed from: private */
        public String serviceName;
        /* access modifiers changed from: private */
        public String streamName;
        /* access modifiers changed from: private */
        public URI streamUri;

        ClientConfigurationBuilder() {
        }

        public ClientConfigurationBuilder region(String region2) {
            this.region = region2;
            return this;
        }

        public ClientConfigurationBuilder serviceName(String serviceName2) {
            this.serviceName = serviceName2;
            return this;
        }

        public ClientConfigurationBuilder apiName(String apiName2) {
            this.apiName = apiName2;
            return this;
        }

        public ClientConfigurationBuilder materialSet(String materialSet2) {
            this.materialSet = materialSet2;
            return this;
        }

        public ClientConfigurationBuilder streamName(String streamName2) {
            this.streamName = streamName2;
            return this;
        }

        public ClientConfigurationBuilder streamUri(URI streamUri2) {
            this.streamUri = streamUri2;
            return this;
        }

        public ClientConfigurationBuilder connectionTimeoutInMillis(Integer connectionTimeoutInMillis2) {
            this.connectionTimeoutInMillis = connectionTimeoutInMillis2;
            return this;
        }

        public ClientConfigurationBuilder readTimeoutInMillis(Integer readTimeoutInMillis2) {
            this.readTimeoutInMillis = readTimeoutInMillis2;
            return this;
        }

        public ClientConfiguration build() {
            return new ClientConfiguration(this);
        }
    }

    public String getRegion() {
        return this.region;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public String getApiName() {
        return this.apiName;
    }

    public String getMaterialSet() {
        return this.materialSet;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public URI getStreamUri() {
        return this.streamUri;
    }

    public Integer getConnectionTimeoutInMillis() {
        return this.connectionTimeoutInMillis;
    }

    public Integer getReadTimeoutInMillis() {
        return this.readTimeoutInMillis;
    }
}
