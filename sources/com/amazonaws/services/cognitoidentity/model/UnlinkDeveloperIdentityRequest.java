package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class UnlinkDeveloperIdentityRequest extends AmazonWebServiceRequest implements Serializable {
    private String developerProviderName;
    private String developerUserIdentifier;
    private String identityId;
    private String identityPoolId;

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public UnlinkDeveloperIdentityRequest withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public UnlinkDeveloperIdentityRequest withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public String getDeveloperProviderName() {
        return this.developerProviderName;
    }

    public void setDeveloperProviderName(String developerProviderName2) {
        this.developerProviderName = developerProviderName2;
    }

    public UnlinkDeveloperIdentityRequest withDeveloperProviderName(String developerProviderName2) {
        this.developerProviderName = developerProviderName2;
        return this;
    }

    public String getDeveloperUserIdentifier() {
        return this.developerUserIdentifier;
    }

    public void setDeveloperUserIdentifier(String developerUserIdentifier2) {
        this.developerUserIdentifier = developerUserIdentifier2;
    }

    public UnlinkDeveloperIdentityRequest withDeveloperUserIdentifier(String developerUserIdentifier2) {
        this.developerUserIdentifier = developerUserIdentifier2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityId() != null) {
            sb.append("IdentityId: " + getIdentityId() + ",");
        }
        if (getIdentityPoolId() != null) {
            sb.append("IdentityPoolId: " + getIdentityPoolId() + ",");
        }
        if (getDeveloperProviderName() != null) {
            sb.append("DeveloperProviderName: " + getDeveloperProviderName() + ",");
        }
        if (getDeveloperUserIdentifier() != null) {
            sb.append("DeveloperUserIdentifier: " + getDeveloperUserIdentifier());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((1 * 31) + (getIdentityId() == null ? 0 : getIdentityId().hashCode())) * 31) + (getIdentityPoolId() == null ? 0 : getIdentityPoolId().hashCode())) * 31) + (getDeveloperProviderName() == null ? 0 : getDeveloperProviderName().hashCode())) * 31;
        if (getDeveloperUserIdentifier() != null) {
            i = getDeveloperUserIdentifier().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UnlinkDeveloperIdentityRequest)) {
            return false;
        }
        UnlinkDeveloperIdentityRequest other = (UnlinkDeveloperIdentityRequest) obj;
        if ((other.getIdentityId() == null) ^ (getIdentityId() == null)) {
            return false;
        }
        if (other.getIdentityId() != null && !other.getIdentityId().equals(getIdentityId())) {
            return false;
        }
        if ((other.getIdentityPoolId() == null) ^ (getIdentityPoolId() == null)) {
            return false;
        }
        if (other.getIdentityPoolId() != null && !other.getIdentityPoolId().equals(getIdentityPoolId())) {
            return false;
        }
        if ((other.getDeveloperProviderName() == null) ^ (getDeveloperProviderName() == null)) {
            return false;
        }
        if (other.getDeveloperProviderName() != null && !other.getDeveloperProviderName().equals(getDeveloperProviderName())) {
            return false;
        }
        if ((other.getDeveloperUserIdentifier() == null) ^ (getDeveloperUserIdentifier() == null)) {
            return false;
        }
        if (other.getDeveloperUserIdentifier() == null || other.getDeveloperUserIdentifier().equals(getDeveloperUserIdentifier())) {
            return true;
        }
        return false;
    }
}
