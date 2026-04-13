package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListIdentityPoolsResult implements Serializable {
    private List<IdentityPoolShortDescription> identityPools;
    private String nextToken;

    public List<IdentityPoolShortDescription> getIdentityPools() {
        return this.identityPools;
    }

    public void setIdentityPools(Collection<IdentityPoolShortDescription> identityPools2) {
        if (identityPools2 == null) {
            this.identityPools = null;
        } else {
            this.identityPools = new ArrayList(identityPools2);
        }
    }

    public ListIdentityPoolsResult withIdentityPools(IdentityPoolShortDescription... identityPools2) {
        if (getIdentityPools() == null) {
            this.identityPools = new ArrayList(identityPools2.length);
        }
        for (IdentityPoolShortDescription value : identityPools2) {
            this.identityPools.add(value);
        }
        return this;
    }

    public ListIdentityPoolsResult withIdentityPools(Collection<IdentityPoolShortDescription> identityPools2) {
        setIdentityPools(identityPools2);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListIdentityPoolsResult withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityPools() != null) {
            sb.append("IdentityPools: " + getIdentityPools() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getIdentityPools() == null ? 0 : getIdentityPools().hashCode())) * 31;
        if (getNextToken() != null) {
            i = getNextToken().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListIdentityPoolsResult)) {
            return false;
        }
        ListIdentityPoolsResult other = (ListIdentityPoolsResult) obj;
        if ((other.getIdentityPools() == null) ^ (getIdentityPools() == null)) {
            return false;
        }
        if (other.getIdentityPools() != null && !other.getIdentityPools().equals(getIdentityPools())) {
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
