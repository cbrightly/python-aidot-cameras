package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnlinkIdentityRequest extends AmazonWebServiceRequest implements Serializable {
    private String identityId;
    private Map<String, String> logins;
    private List<String> loginsToRemove;

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public UnlinkIdentityRequest withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public Map<String, String> getLogins() {
        return this.logins;
    }

    public void setLogins(Map<String, String> logins2) {
        this.logins = logins2;
    }

    public UnlinkIdentityRequest withLogins(Map<String, String> logins2) {
        this.logins = logins2;
        return this;
    }

    public UnlinkIdentityRequest addLoginsEntry(String key, String value) {
        if (this.logins == null) {
            this.logins = new HashMap();
        }
        if (!this.logins.containsKey(key)) {
            this.logins.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public UnlinkIdentityRequest clearLoginsEntries() {
        this.logins = null;
        return this;
    }

    public List<String> getLoginsToRemove() {
        return this.loginsToRemove;
    }

    public void setLoginsToRemove(Collection<String> loginsToRemove2) {
        if (loginsToRemove2 == null) {
            this.loginsToRemove = null;
        } else {
            this.loginsToRemove = new ArrayList(loginsToRemove2);
        }
    }

    public UnlinkIdentityRequest withLoginsToRemove(String... loginsToRemove2) {
        if (getLoginsToRemove() == null) {
            this.loginsToRemove = new ArrayList(loginsToRemove2.length);
        }
        for (String value : loginsToRemove2) {
            this.loginsToRemove.add(value);
        }
        return this;
    }

    public UnlinkIdentityRequest withLoginsToRemove(Collection<String> loginsToRemove2) {
        setLoginsToRemove(loginsToRemove2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityId() != null) {
            sb.append("IdentityId: " + getIdentityId() + ",");
        }
        if (getLogins() != null) {
            sb.append("Logins: " + getLogins() + ",");
        }
        if (getLoginsToRemove() != null) {
            sb.append("LoginsToRemove: " + getLoginsToRemove());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getIdentityId() == null ? 0 : getIdentityId().hashCode())) * 31) + (getLogins() == null ? 0 : getLogins().hashCode())) * 31;
        if (getLoginsToRemove() != null) {
            i = getLoginsToRemove().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UnlinkIdentityRequest)) {
            return false;
        }
        UnlinkIdentityRequest other = (UnlinkIdentityRequest) obj;
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
        if ((other.getLoginsToRemove() == null) ^ (getLoginsToRemove() == null)) {
            return false;
        }
        if (other.getLoginsToRemove() == null || other.getLoginsToRemove().equals(getLoginsToRemove())) {
            return true;
        }
        return false;
    }
}
