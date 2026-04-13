package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class AssumeRoleWithSAMLResult implements Serializable {
    private AssumedRoleUser assumedRoleUser;
    private String audience;
    private Credentials credentials;
    private String issuer;
    private String nameQualifier;
    private Integer packedPolicySize;
    private String subject;
    private String subjectType;

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void setCredentials(Credentials credentials2) {
        this.credentials = credentials2;
    }

    public AssumeRoleWithSAMLResult withCredentials(Credentials credentials2) {
        this.credentials = credentials2;
        return this;
    }

    public AssumedRoleUser getAssumedRoleUser() {
        return this.assumedRoleUser;
    }

    public void setAssumedRoleUser(AssumedRoleUser assumedRoleUser2) {
        this.assumedRoleUser = assumedRoleUser2;
    }

    public AssumeRoleWithSAMLResult withAssumedRoleUser(AssumedRoleUser assumedRoleUser2) {
        this.assumedRoleUser = assumedRoleUser2;
        return this;
    }

    public Integer getPackedPolicySize() {
        return this.packedPolicySize;
    }

    public void setPackedPolicySize(Integer packedPolicySize2) {
        this.packedPolicySize = packedPolicySize2;
    }

    public AssumeRoleWithSAMLResult withPackedPolicySize(Integer packedPolicySize2) {
        this.packedPolicySize = packedPolicySize2;
        return this;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject2) {
        this.subject = subject2;
    }

    public AssumeRoleWithSAMLResult withSubject(String subject2) {
        this.subject = subject2;
        return this;
    }

    public String getSubjectType() {
        return this.subjectType;
    }

    public void setSubjectType(String subjectType2) {
        this.subjectType = subjectType2;
    }

    public AssumeRoleWithSAMLResult withSubjectType(String subjectType2) {
        this.subjectType = subjectType2;
        return this;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public void setIssuer(String issuer2) {
        this.issuer = issuer2;
    }

    public AssumeRoleWithSAMLResult withIssuer(String issuer2) {
        this.issuer = issuer2;
        return this;
    }

    public String getAudience() {
        return this.audience;
    }

    public void setAudience(String audience2) {
        this.audience = audience2;
    }

    public AssumeRoleWithSAMLResult withAudience(String audience2) {
        this.audience = audience2;
        return this;
    }

    public String getNameQualifier() {
        return this.nameQualifier;
    }

    public void setNameQualifier(String nameQualifier2) {
        this.nameQualifier = nameQualifier2;
    }

    public AssumeRoleWithSAMLResult withNameQualifier(String nameQualifier2) {
        this.nameQualifier = nameQualifier2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getCredentials() != null) {
            sb.append("Credentials: " + getCredentials() + ",");
        }
        if (getAssumedRoleUser() != null) {
            sb.append("AssumedRoleUser: " + getAssumedRoleUser() + ",");
        }
        if (getPackedPolicySize() != null) {
            sb.append("PackedPolicySize: " + getPackedPolicySize() + ",");
        }
        if (getSubject() != null) {
            sb.append("Subject: " + getSubject() + ",");
        }
        if (getSubjectType() != null) {
            sb.append("SubjectType: " + getSubjectType() + ",");
        }
        if (getIssuer() != null) {
            sb.append("Issuer: " + getIssuer() + ",");
        }
        if (getAudience() != null) {
            sb.append("Audience: " + getAudience() + ",");
        }
        if (getNameQualifier() != null) {
            sb.append("NameQualifier: " + getNameQualifier());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((1 * 31) + (getCredentials() == null ? 0 : getCredentials().hashCode())) * 31) + (getAssumedRoleUser() == null ? 0 : getAssumedRoleUser().hashCode())) * 31) + (getPackedPolicySize() == null ? 0 : getPackedPolicySize().hashCode())) * 31) + (getSubject() == null ? 0 : getSubject().hashCode())) * 31) + (getSubjectType() == null ? 0 : getSubjectType().hashCode())) * 31) + (getIssuer() == null ? 0 : getIssuer().hashCode())) * 31) + (getAudience() == null ? 0 : getAudience().hashCode())) * 31;
        if (getNameQualifier() != null) {
            i = getNameQualifier().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AssumeRoleWithSAMLResult)) {
            return false;
        }
        AssumeRoleWithSAMLResult other = (AssumeRoleWithSAMLResult) obj;
        if ((other.getCredentials() == null) ^ (getCredentials() == null)) {
            return false;
        }
        if (other.getCredentials() != null && !other.getCredentials().equals(getCredentials())) {
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
        if ((other.getSubject() == null) ^ (getSubject() == null)) {
            return false;
        }
        if (other.getSubject() != null && !other.getSubject().equals(getSubject())) {
            return false;
        }
        if ((other.getSubjectType() == null) ^ (getSubjectType() == null)) {
            return false;
        }
        if (other.getSubjectType() != null && !other.getSubjectType().equals(getSubjectType())) {
            return false;
        }
        if ((other.getIssuer() == null) ^ (getIssuer() == null)) {
            return false;
        }
        if (other.getIssuer() != null && !other.getIssuer().equals(getIssuer())) {
            return false;
        }
        if ((other.getAudience() == null) ^ (getAudience() == null)) {
            return false;
        }
        if (other.getAudience() != null && !other.getAudience().equals(getAudience())) {
            return false;
        }
        if ((other.getNameQualifier() == null) ^ (getNameQualifier() == null)) {
            return false;
        }
        if (other.getNameQualifier() == null || other.getNameQualifier().equals(getNameQualifier())) {
            return true;
        }
        return false;
    }
}
