package com.amazonaws.auth.policy;

import org.slf4j.e;

public class Principal {
    public static final Principal All = new Principal(e.ANY_MARKER, e.ANY_MARKER);
    public static final Principal AllServices = new Principal("Service", e.ANY_MARKER);
    public static final Principal AllUsers = new Principal("AWS", e.ANY_MARKER);
    public static final Principal AllWebProviders = new Principal("Federated", e.ANY_MARKER);
    private final String id;
    private final String provider;

    public Principal(Services service) {
        if (service != null) {
            this.id = service.getServiceId();
            this.provider = "Service";
            return;
        }
        throw new IllegalArgumentException("Null AWS service name specified");
    }

    public Principal(String provider2, String id2) {
        this.provider = provider2;
        this.id = "AWS".equals(provider2) ? id2.replaceAll("-", "") : id2;
    }

    public Principal(String accountId) {
        if (accountId != null) {
            this.id = accountId.replaceAll("-", "");
            this.provider = "AWS";
            return;
        }
        throw new IllegalArgumentException("Null AWS account ID specified");
    }

    public Principal(WebIdentityProviders webIdentityProvider) {
        if (webIdentityProvider != null) {
            this.id = webIdentityProvider.getWebIdentityProvider();
            this.provider = "Federated";
            return;
        }
        throw new IllegalArgumentException("Null web identity provider specified");
    }

    public String getProvider() {
        return this.provider;
    }

    public String getId() {
        return this.id;
    }

    public enum Services {
        AWSDataPipeline("datapipeline.amazonaws.com"),
        AmazonElasticTranscoder("elastictranscoder.amazonaws.com"),
        AmazonEC2("ec2.amazonaws.com"),
        AWSOpsWorks("opsworks.amazonaws.com"),
        AWSCloudHSM("cloudhsm.amazonaws.com"),
        AllServices(e.ANY_MARKER);
        
        private String serviceId;

        private Services(String serviceId2) {
            this.serviceId = serviceId2;
        }

        public String getServiceId() {
            return this.serviceId;
        }

        public static Services fromString(String serviceId2) {
            if (serviceId2 == null) {
                return null;
            }
            for (Services s : values()) {
                if (s.getServiceId().equalsIgnoreCase(serviceId2)) {
                    return s;
                }
            }
            return null;
        }
    }

    public enum WebIdentityProviders {
        Facebook("graph.facebook.com"),
        Google("accounts.google.com"),
        Amazon("www.amazon.com"),
        AllProviders(e.ANY_MARKER);
        
        private String webIdentityProvider;

        private WebIdentityProviders(String webIdentityProvider2) {
            this.webIdentityProvider = webIdentityProvider2;
        }

        public String getWebIdentityProvider() {
            return this.webIdentityProvider;
        }

        public static WebIdentityProviders fromString(String webIdentityProvider2) {
            if (webIdentityProvider2 == null) {
                return null;
            }
            for (WebIdentityProviders provider : values()) {
                if (provider.getWebIdentityProvider().equalsIgnoreCase(webIdentityProvider2)) {
                    return provider;
                }
            }
            return null;
        }
    }

    public int hashCode() {
        return (((1 * 31) + this.provider.hashCode()) * 31) + this.id.hashCode();
    }

    public boolean equals(Object principal) {
        if (this == principal) {
            return true;
        }
        if (principal == null || !(principal instanceof Principal)) {
            return false;
        }
        Principal other = (Principal) principal;
        if (!getProvider().equals(other.getProvider()) || !getId().equals(other.getId())) {
            return false;
        }
        return true;
    }
}
