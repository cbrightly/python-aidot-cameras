package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;

public class CognitoIdentityProvider implements Serializable {
    private String clientId;
    private String providerName;
    private Boolean serverSideTokenCheck;

    public String getProviderName() {
        return this.providerName;
    }

    public void setProviderName(String providerName2) {
        this.providerName = providerName2;
    }

    public CognitoIdentityProvider withProviderName(String providerName2) {
        this.providerName = providerName2;
        return this;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId2) {
        this.clientId = clientId2;
    }

    public CognitoIdentityProvider withClientId(String clientId2) {
        this.clientId = clientId2;
        return this;
    }

    public Boolean isServerSideTokenCheck() {
        return this.serverSideTokenCheck;
    }

    public Boolean getServerSideTokenCheck() {
        return this.serverSideTokenCheck;
    }

    public void setServerSideTokenCheck(Boolean serverSideTokenCheck2) {
        this.serverSideTokenCheck = serverSideTokenCheck2;
    }

    public CognitoIdentityProvider withServerSideTokenCheck(Boolean serverSideTokenCheck2) {
        this.serverSideTokenCheck = serverSideTokenCheck2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getProviderName() != null) {
            sb.append("ProviderName: " + getProviderName() + ",");
        }
        if (getClientId() != null) {
            sb.append("ClientId: " + getClientId() + ",");
        }
        if (getServerSideTokenCheck() != null) {
            sb.append("ServerSideTokenCheck: " + getServerSideTokenCheck());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getProviderName() == null ? 0 : getProviderName().hashCode())) * 31) + (getClientId() == null ? 0 : getClientId().hashCode())) * 31;
        if (getServerSideTokenCheck() != null) {
            i = getServerSideTokenCheck().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CognitoIdentityProvider)) {
            return false;
        }
        CognitoIdentityProvider other = (CognitoIdentityProvider) obj;
        if ((other.getProviderName() == null) ^ (getProviderName() == null)) {
            return false;
        }
        if (other.getProviderName() != null && !other.getProviderName().equals(getProviderName())) {
            return false;
        }
        if ((other.getClientId() == null) ^ (getClientId() == null)) {
            return false;
        }
        if (other.getClientId() != null && !other.getClientId().equals(getClientId())) {
            return false;
        }
        if ((other.getServerSideTokenCheck() == null) ^ (getServerSideTokenCheck() == null)) {
            return false;
        }
        if (other.getServerSideTokenCheck() == null || other.getServerSideTokenCheck().equals(getServerSideTokenCheck())) {
            return true;
        }
        return false;
    }
}
