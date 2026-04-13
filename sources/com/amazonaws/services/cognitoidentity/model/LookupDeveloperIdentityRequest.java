package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class LookupDeveloperIdentityRequest extends AmazonWebServiceRequest implements Serializable {
    private String developerUserIdentifier;
    private String identityId;
    private String identityPoolId;
    private Integer maxResults;
    private String nextToken;

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public LookupDeveloperIdentityRequest withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public LookupDeveloperIdentityRequest withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public String getDeveloperUserIdentifier() {
        return this.developerUserIdentifier;
    }

    public void setDeveloperUserIdentifier(String developerUserIdentifier2) {
        this.developerUserIdentifier = developerUserIdentifier2;
    }

    public LookupDeveloperIdentityRequest withDeveloperUserIdentifier(String developerUserIdentifier2) {
        this.developerUserIdentifier = developerUserIdentifier2;
        return this;
    }

    public Integer getMaxResults() {
        return this.maxResults;
    }

    public void setMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
    }

    public LookupDeveloperIdentityRequest withMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public LookupDeveloperIdentityRequest withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
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
        if (getDeveloperUserIdentifier() != null) {
            sb.append("DeveloperUserIdentifier: " + getDeveloperUserIdentifier() + ",");
        }
        if (getMaxResults() != null) {
            sb.append("MaxResults: " + getMaxResults() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((((1 * 31) + (getIdentityPoolId() == null ? 0 : getIdentityPoolId().hashCode())) * 31) + (getIdentityId() == null ? 0 : getIdentityId().hashCode())) * 31;
        if (getDeveloperUserIdentifier() == null) {
            i = 0;
        } else {
            i = getDeveloperUserIdentifier().hashCode();
        }
        int hashCode2 = (((hashCode + i) * 31) + (getMaxResults() == null ? 0 : getMaxResults().hashCode())) * 31;
        if (getNextToken() != null) {
            i2 = getNextToken().hashCode();
        }
        return hashCode2 + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof LookupDeveloperIdentityRequest)) {
            return false;
        }
        LookupDeveloperIdentityRequest other = (LookupDeveloperIdentityRequest) obj;
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
        if ((other.getDeveloperUserIdentifier() == null) ^ (getDeveloperUserIdentifier() == null)) {
            return false;
        }
        if (other.getDeveloperUserIdentifier() != null && !other.getDeveloperUserIdentifier().equals(getDeveloperUserIdentifier())) {
            return false;
        }
        if ((other.getMaxResults() == null) ^ (getMaxResults() == null)) {
            return false;
        }
        if (other.getMaxResults() != null && !other.getMaxResults().equals(getMaxResults())) {
            return false;
        }
        if ((other.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        if (other.getNextToken() == null || other.getNextToken().equals(getNextToken())) {
            return true;
        }
        return false;
    }
}
