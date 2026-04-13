package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class ListIdentityPoolsRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer maxResults;
    private String nextToken;

    public Integer getMaxResults() {
        return this.maxResults;
    }

    public void setMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
    }

    public ListIdentityPoolsRequest withMaxResults(Integer maxResults2) {
        this.maxResults = maxResults2;
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListIdentityPoolsRequest withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
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
        int i = 0;
        int hashCode = ((1 * 31) + (getMaxResults() == null ? 0 : getMaxResults().hashCode())) * 31;
        if (getNextToken() != null) {
            i = getNextToken().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListIdentityPoolsRequest)) {
            return false;
        }
        ListIdentityPoolsRequest other = (ListIdentityPoolsRequest) obj;
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
