package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.Date;

public class Credentials implements Serializable {
    private String accessKeyId;
    private Date expiration;
    private String secretKey;
    private String sessionToken;

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId2) {
        this.accessKeyId = accessKeyId2;
    }

    public Credentials withAccessKeyId(String accessKeyId2) {
        this.accessKeyId = accessKeyId2;
        return this;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey2) {
        this.secretKey = secretKey2;
    }

    public Credentials withSecretKey(String secretKey2) {
        this.secretKey = secretKey2;
        return this;
    }

    public String getSessionToken() {
        return this.sessionToken;
    }

    public void setSessionToken(String sessionToken2) {
        this.sessionToken = sessionToken2;
    }

    public Credentials withSessionToken(String sessionToken2) {
        this.sessionToken = sessionToken2;
        return this;
    }

    public Date getExpiration() {
        return this.expiration;
    }

    public void setExpiration(Date expiration2) {
        this.expiration = expiration2;
    }

    public Credentials withExpiration(Date expiration2) {
        this.expiration = expiration2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAccessKeyId() != null) {
            sb.append("AccessKeyId: " + getAccessKeyId() + ",");
        }
        if (getSecretKey() != null) {
            sb.append("SecretKey: " + getSecretKey() + ",");
        }
        if (getSessionToken() != null) {
            sb.append("SessionToken: " + getSessionToken() + ",");
        }
        if (getExpiration() != null) {
            sb.append("Expiration: " + getExpiration());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((1 * 31) + (getAccessKeyId() == null ? 0 : getAccessKeyId().hashCode())) * 31) + (getSecretKey() == null ? 0 : getSecretKey().hashCode())) * 31) + (getSessionToken() == null ? 0 : getSessionToken().hashCode())) * 31;
        if (getExpiration() != null) {
            i = getExpiration().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Credentials)) {
            return false;
        }
        Credentials other = (Credentials) obj;
        if ((other.getAccessKeyId() == null) ^ (getAccessKeyId() == null)) {
            return false;
        }
        if (other.getAccessKeyId() != null && !other.getAccessKeyId().equals(getAccessKeyId())) {
            return false;
        }
        if ((other.getSecretKey() == null) ^ (getSecretKey() == null)) {
            return false;
        }
        if (other.getSecretKey() != null && !other.getSecretKey().equals(getSecretKey())) {
            return false;
        }
        if ((other.getSessionToken() == null) ^ (getSessionToken() == null)) {
            return false;
        }
        if (other.getSessionToken() != null && !other.getSessionToken().equals(getSessionToken())) {
            return false;
        }
        if ((other.getExpiration() == null) ^ (getExpiration() == null)) {
            return false;
        }
        if (other.getExpiration() == null || other.getExpiration().equals(getExpiration())) {
            return true;
        }
        return false;
    }
}
