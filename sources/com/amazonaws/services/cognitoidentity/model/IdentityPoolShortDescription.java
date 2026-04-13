package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;

public class IdentityPoolShortDescription implements Serializable {
    private String identityPoolId;
    private String identityPoolName;

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public IdentityPoolShortDescription withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public String getIdentityPoolName() {
        return this.identityPoolName;
    }

    public void setIdentityPoolName(String identityPoolName2) {
        this.identityPoolName = identityPoolName2;
    }

    public IdentityPoolShortDescription withIdentityPoolName(String identityPoolName2) {
        this.identityPoolName = identityPoolName2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityPoolId() != null) {
            sb.append("IdentityPoolId: " + getIdentityPoolId() + ",");
        }
        if (getIdentityPoolName() != null) {
            sb.append("IdentityPoolName: " + getIdentityPoolName());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getIdentityPoolId() == null ? 0 : getIdentityPoolId().hashCode())) * 31;
        if (getIdentityPoolName() != null) {
            i = getIdentityPoolName().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IdentityPoolShortDescription)) {
            return false;
        }
        IdentityPoolShortDescription other = (IdentityPoolShortDescription) obj;
        if ((other.getIdentityPoolId() == null) ^ (getIdentityPoolId() == null)) {
            return false;
        }
        if (other.getIdentityPoolId() != null && !other.getIdentityPoolId().equals(getIdentityPoolId())) {
            return false;
        }
        if ((other.getIdentityPoolName() == null) ^ (getIdentityPoolName() == null)) {
            return false;
        }
        if (other.getIdentityPoolName() == null || other.getIdentityPoolName().equals(getIdentityPoolName())) {
            return true;
        }
        return false;
    }
}
