package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class MergeDeveloperIdentitiesRequest extends AmazonWebServiceRequest implements Serializable {
    private String destinationUserIdentifier;
    private String developerProviderName;
    private String identityPoolId;
    private String sourceUserIdentifier;

    public String getSourceUserIdentifier() {
        return this.sourceUserIdentifier;
    }

    public void setSourceUserIdentifier(String sourceUserIdentifier2) {
        this.sourceUserIdentifier = sourceUserIdentifier2;
    }

    public MergeDeveloperIdentitiesRequest withSourceUserIdentifier(String sourceUserIdentifier2) {
        this.sourceUserIdentifier = sourceUserIdentifier2;
        return this;
    }

    public String getDestinationUserIdentifier() {
        return this.destinationUserIdentifier;
    }

    public void setDestinationUserIdentifier(String destinationUserIdentifier2) {
        this.destinationUserIdentifier = destinationUserIdentifier2;
    }

    public MergeDeveloperIdentitiesRequest withDestinationUserIdentifier(String destinationUserIdentifier2) {
        this.destinationUserIdentifier = destinationUserIdentifier2;
        return this;
    }

    public String getDeveloperProviderName() {
        return this.developerProviderName;
    }

    public void setDeveloperProviderName(String developerProviderName2) {
        this.developerProviderName = developerProviderName2;
    }

    public MergeDeveloperIdentitiesRequest withDeveloperProviderName(String developerProviderName2) {
        this.developerProviderName = developerProviderName2;
        return this;
    }

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public MergeDeveloperIdentitiesRequest withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getSourceUserIdentifier() != null) {
            sb.append("SourceUserIdentifier: " + getSourceUserIdentifier() + ",");
        }
        if (getDestinationUserIdentifier() != null) {
            sb.append("DestinationUserIdentifier: " + getDestinationUserIdentifier() + ",");
        }
        if (getDeveloperProviderName() != null) {
            sb.append("DeveloperProviderName: " + getDeveloperProviderName() + ",");
        }
        if (getIdentityPoolId() != null) {
            sb.append("IdentityPoolId: " + getIdentityPoolId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((1 * 31) + (getSourceUserIdentifier() == null ? 0 : getSourceUserIdentifier().hashCode())) * 31;
        if (getDestinationUserIdentifier() == null) {
            i = 0;
        } else {
            i = getDestinationUserIdentifier().hashCode();
        }
        int hashCode2 = (((hashCode + i) * 31) + (getDeveloperProviderName() == null ? 0 : getDeveloperProviderName().hashCode())) * 31;
        if (getIdentityPoolId() != null) {
            i2 = getIdentityPoolId().hashCode();
        }
        return hashCode2 + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MergeDeveloperIdentitiesRequest)) {
            return false;
        }
        MergeDeveloperIdentitiesRequest other = (MergeDeveloperIdentitiesRequest) obj;
        if ((other.getSourceUserIdentifier() == null) ^ (getSourceUserIdentifier() == null)) {
            return false;
        }
        if (other.getSourceUserIdentifier() != null && !other.getSourceUserIdentifier().equals(getSourceUserIdentifier())) {
            return false;
        }
        if ((other.getDestinationUserIdentifier() == null) ^ (getDestinationUserIdentifier() == null)) {
            return false;
        }
        if (other.getDestinationUserIdentifier() != null && !other.getDestinationUserIdentifier().equals(getDestinationUserIdentifier())) {
            return false;
        }
        if ((other.getDeveloperProviderName() == null) ^ (getDeveloperProviderName() == null)) {
            return false;
        }
        if (other.getDeveloperProviderName() != null && !other.getDeveloperProviderName().equals(getDeveloperProviderName())) {
            return false;
        }
        if ((other.getIdentityPoolId() == null) ^ (getIdentityPoolId() == null)) {
            return false;
        }
        if (other.getIdentityPoolId() == null || other.getIdentityPoolId().equals(getIdentityPoolId())) {
            return true;
        }
        return false;
    }
}
