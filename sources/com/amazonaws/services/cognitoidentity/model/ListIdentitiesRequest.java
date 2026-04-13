package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class ListIdentitiesRequest extends AmazonWebServiceRequest implements Serializable {
    private Boolean hideDisabled;
    private String identityPoolId;
    private Integer maxResults;
    private String nextToken;

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public ListIdentitiesRequest withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public Integer getMaxResults() {
        return this.maxResults;
    }

    public void setMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
    }

    public ListIdentitiesRequest withMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListIdentitiesRequest withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public Boolean isHideDisabled() {
        return this.hideDisabled;
    }

    public Boolean getHideDisabled() {
        return this.hideDisabled;
    }

    public void setHideDisabled(Boolean hideDisabled2) {
        this.hideDisabled = hideDisabled2;
    }

    public ListIdentitiesRequest withHideDisabled(Boolean hideDisabled2) {
        this.hideDisabled = hideDisabled2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityPoolId() != null) {
            sb.append("IdentityPoolId: " + getIdentityPoolId() + ",");
        }
        if (getMaxResults() != null) {
            sb.append("MaxResults: " + getMaxResults() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken() + ",");
        }
        if (getHideDisabled() != null) {
            sb.append("HideDisabled: " + getHideDisabled());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((1 * 31) + (getIdentityPoolId() == null ? 0 : getIdentityPoolId().hashCode())) * 31) + (getMaxResults() == null ? 0 : getMaxResults().hashCode())) * 31) + (getNextToken() == null ? 0 : getNextToken().hashCode())) * 31;
        if (getHideDisabled() != null) {
            i = getHideDisabled().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListIdentitiesRequest)) {
            return false;
        }
        ListIdentitiesRequest other = (ListIdentitiesRequest) obj;
        if ((other.getIdentityPoolId() == null) ^ (getIdentityPoolId() == null)) {
            return false;
        }
        if (other.getIdentityPoolId() != null && !other.getIdentityPoolId().equals(getIdentityPoolId())) {
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
        if (other.getNextToken() != null && !other.getNextToken().equals(getNextToken())) {
            return false;
        }
        if ((other.getHideDisabled() == null) ^ (getHideDisabled() == null)) {
            return false;
        }
        if (other.getHideDisabled() == null || other.getHideDisabled().equals(getHideDisabled())) {
            return true;
        }
        return false;
    }
}
