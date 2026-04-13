package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GetOpenIdTokenForDeveloperIdentityRequest extends AmazonWebServiceRequest implements Serializable {
    private String identityId;
    private String identityPoolId;
    private Map<String, String> logins;
    private Long tokenDuration;

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public GetOpenIdTokenForDeveloperIdentityRequest withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public GetOpenIdTokenForDeveloperIdentityRequest withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public Map<String, String> getLogins() {
        return this.logins;
    }

    public void setLogins(Map<String, String> logins2) {
        this.logins = logins2;
    }

    public GetOpenIdTokenForDeveloperIdentityRequest withLogins(Map<String, String> logins2) {
        this.logins = logins2;
        return this;
    }

    public GetOpenIdTokenForDeveloperIdentityRequest addLoginsEntry(String key, String value) {
        if (this.logins == null) {
            this.logins = new HashMap();
        }
        if (!this.logins.containsKey(key)) {
            this.logins.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public GetOpenIdTokenForDeveloperIdentityRequest clearLoginsEntries() {
        this.logins = null;
        return this;
    }

    public Long getTokenDuration() {
        return this.tokenDuration;
    }

    public void setTokenDuration(Long tokenDuration2) {
        this.tokenDuration = tokenDuration2;
    }

    public GetOpenIdTokenForDeveloperIdentityRequest withTokenDuration(Long tokenDuration2) {
        this.tokenDuration = tokenDuration2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityPoolId() != null) {
            sb.append("IdentityPoolId: " + getIdentityPoolId() + ",");
        }
        if (getIdentityId() != null) {
            sb.append("IdentityId: " + getIdentityId() + ",");
        }
        if (getLogins() != null) {
            sb.append("Logins: " + getLogins() + ",");
        }
        if (getTokenDuration() != null) {
            sb.append("TokenDuration: " + getTokenDuration());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((1 * 31) + (getIdentityPoolId() == null ? 0 : getIdentityPoolId().hashCode())) * 31) + (getIdentityId() == null ? 0 : getIdentityId().hashCode())) * 31) + (getLogins() == null ? 0 : getLogins().hashCode())) * 31;
        if (getTokenDuration() != null) {
            i = getTokenDuration().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetOpenIdTokenForDeveloperIdentityRequest)) {
            return false;
        }
        GetOpenIdTokenForDeveloperIdentityRequest other = (GetOpenIdTokenForDeveloperIdentityRequest) obj;
        if ((other.getIdentityPoolId() == null) ^ (getIdentityPoolId() == null)) {
            return false;
        }
        if (other.getIdentityPoolId() != null && !other.getIdentityPoolId().equals(getIdentityPoolId())) {
            return false;
        }
        if ((other.getIdentityId() == null) ^ (getIdentityId() == null)) {
            return false;
        }
        if (other.getIdentityId() != null && !other.getIdentityId().equals(getIdentityId())) {
            return false;
        }
        if ((other.getLogins() == null) ^ (getLogins() == null)) {
            return false;
        }
        if (other.getLogins() != null && !other.getLogins().equals(getLogins())) {
            return false;
        }
        if ((other.getTokenDuration() == null) ^ (getTokenDuration() == null)) {
            return false;
        }
        if (other.getTokenDuration() == null || other.getTokenDuration().equals(getTokenDuration())) {
            return true;
        }
        return false;
    }
}
