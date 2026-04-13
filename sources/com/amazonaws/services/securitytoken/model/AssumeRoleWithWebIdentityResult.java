package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class AssumeRoleWithWebIdentityResult implements Serializable {
    private AssumedRoleUser assumedRoleUser;
    private String audience;
    private Credentials credentials;
    private Integer packedPolicySize;
    private String provider;
    private String subjectFromWebIdentityToken;

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Credentials credentials2) {
        this.credentials = credentials2;
    }

    public AssumeRoleWithWebIdentityResult withCredentials(Credentials credentials2) {
        this.credentials = credentials2;
        return this;
    }

    public String getSubjectFromWebIdentityToken() {
        return this.subjectFromWebIdentityToken;
    }

    public void setSubjectFromWebIdentityToken(String subjectFromWebIdentityToken2) {
        this.subjectFromWebIdentityToken = subjectFromWebIdentityToken2;
    }

    public AssumeRoleWithWebIdentityResult withSubjectFromWebIdentityToken(String subjectFromWebIdentityToken2) {
        this.subjectFromWebIdentityToken = subjectFromWebIdentityToken2;
        return this;
    }

    public AssumedRoleUser getAssumedRoleUser() {
        return this.assumedRoleUser;
    }

    public void setAssumedRoleUser(AssumedRoleUser assumedRoleUser2) {
        this.assumedRoleUser = assumedRoleUser2;
    }

    public AssumeRoleWithWebIdentityResult withAssumedRoleUser(AssumedRoleUser assumedRoleUser2) {
        this.assumedRoleUser = assumedRoleUser2;
        return this;
    }

    public Integer getPackedPolicySize() {
        return this.packedPolicySize;
    }

    public void setPackedPolicySize(Integer packedPolicySize2) {
        this.packedPolicySize = packedPolicySize2;
    }

    public AssumeRoleWithWebIdentityResult withPackedPolicySize(Integer packedPolicySize2) {
        this.packedPolicySize = packedPolicySize2;
        return this;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider2) {
        this.provider = provider2;
    }

    public AssumeRoleWithWebIdentityResult withProvider(String provider2) {
        this.provider = provider2;
        return this;
    }

    public String getAudience() {
        return this.audience;
    }

    public void setAudience(String audience2) {
        this.audience = audience2;
    }

    public AssumeRoleWithWebIdentityResult withAudience(String audience2) {
        this.audience = audience2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getCredentials() != null) {
            sb.append("Credentials: " + getCredentials() + ",");
        }
        if (getSubjectFromWebIdentityToken() != null) {
            sb.append("SubjectFromWebIdentityToken: " + getSubjectFromWebIdentityToken() + ",");
        }
        if (getAssumedRoleUser() != null) {
            sb.append("AssumedRoleUser: " + getAssumedRoleUser() + ",");
        }
        if (getPackedPolicySize() != null) {
            sb.append("PackedPolicySize: " + getPackedPolicySize() + ",");
        }
        if (getProvider() != null) {
            sb.append("Provider: " + getProvider() + ",");
        }
        if (getAudience() != null) {
            sb.append("Audience: " + getAudience());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((1 * 31) + (getCredentials() == null ? 0 : getCredentials().hashCode())) * 31;
        if (getSubjectFromWebIdentityToken() == null) {
            i = 0;
        } else {
            i = getSubjectFromWebIdentityToken().hashCode();
        }
        int hashCode2 = (((((((hashCode + i) * 31) + (getAssumedRoleUser() == null ? 0 : getAssumedRoleUser().hashCode())) * 31) + (getPackedPolicySize() == null ? 0 : getPackedPolicySize().hashCode())) * 31) + (getProvider() == null ? 0 : getProvider().hashCode())) * 31;
        if (getAudience() != null) {
            i2 = getAudience().hashCode();
        }
        return hashCode2 + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AssumeRoleWithWebIdentityResult)) {
            return false;
        }
        AssumeRoleWithWebIdentityResult other = (AssumeRoleWithWebIdentityResult) obj;
        if ((other.getCredentials() == null) ^ (getCredentials() == null)) {
            return false;
        }
        if (other.getCredentials() != null && !other.getCredentials().equals(getCredentials())) {
            return false;
        }
        if ((other.getSubjectFromWebIdentityToken() == null) ^ (getSubjectFromWebIdentityToken() == null)) {
            return false;
        }
        if (other.getSubjectFromWebIdentityToken() != null && !other.getSubjectFromWebIdentityToken().equals(getSubjectFromWebIdentityToken())) {
            return false;
        }
        if ((other.getAssumedRoleUser() == null) ^ (getAssumedRoleUser() == null)) {
            return false;
        }
        if (other.getAssumedRoleUser() != null && !other.getAssumedRoleUser().equals(getAssumedRoleUser())) {
            return false;
        }
        if ((other.getPackedPolicySize() == null) ^ (getPackedPolicySize() == null)) {
            return false;
        }
        if (other.getPackedPolicySize() != null && !other.getPackedPolicySize().equals(getPackedPolicySize())) {
            return false;
        }
        if ((other.getProvider() == null) ^ (getProvider() == null)) {
            return false;
        }
        if (other.getProvider() != null && !other.getProvider().equals(getProvider())) {
            return false;
        }
        if ((other.getAudience() == null) ^ (getAudience() == null)) {
            return false;
        }
        if (other.getAudience() == null || other.getAudience().equals(getAudience())) {
            return true;
        }
        return false;
    }
}
