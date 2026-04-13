package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;

public class UnprocessedIdentityId implements Serializable {
    private String errorCode;
    private String identityId;

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public UnprocessedIdentityId withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode2) {
        this.errorCode = errorCode2;
    }

    public UnprocessedIdentityId withErrorCode(String errorCode2) {
        this.errorCode = errorCode2;
        return this;
    }

    public void setErrorCode(ErrorCode errorCode2) {
        this.errorCode = errorCode2.toString();
    }

    public UnprocessedIdentityId withErrorCode(ErrorCode errorCode2) {
        this.errorCode = errorCode2.toString();
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityId() != null) {
            sb.append("IdentityId: " + getIdentityId() + ",");
        }
        if (getErrorCode() != null) {
            sb.append("ErrorCode: " + getErrorCode());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getIdentityId() == null ? 0 : getIdentityId().hashCode())) * 31;
        if (getErrorCode() != null) {
            i = getErrorCode().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UnprocessedIdentityId)) {
            return false;
        }
        UnprocessedIdentityId other = (UnprocessedIdentityId) obj;
        if ((other.getIdentityId() == null) ^ (getIdentityId() == null)) {
            return false;
        }
        if (other.getIdentityId() != null && !other.getIdentityId().equals(getIdentityId())) {
            return false;
        }
        if ((other.getErrorCode() == null) ^ (getErrorCode() == null)) {
            return false;
        }
        if (other.getErrorCode() == null || other.getErrorCode().equals(getErrorCode())) {
            return true;
        }
        return false;
    }
}
