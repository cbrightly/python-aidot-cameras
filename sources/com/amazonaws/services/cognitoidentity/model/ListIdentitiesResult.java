package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListIdentitiesResult implements Serializable {
    private List<IdentityDescription> identities;
    private String identityPoolId;
    private String nextToken;

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public ListIdentitiesResult withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public List<IdentityDescription> getIdentities() {
        return this.identities;
    }

    public void setIdentities(Collection<IdentityDescription> identities2) {
        if (identities2 == null) {
            this.identities = null;
        } else {
            this.identities = new ArrayList(identities2);
        }
    }

    public ListIdentitiesResult withIdentities(IdentityDescription... identities2) {
        if (getIdentities() == null) {
            this.identities = new ArrayList(identities2.length);
        }
        for (IdentityDescription value : identities2) {
            this.identities.add(value);
        }
        return this;
    }

    public ListIdentitiesResult withIdentities(Collection<IdentityDescription> identities2) {
        setIdentities(identities2);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListIdentitiesResult withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityPoolId() != null) {
            sb.append("IdentityPoolId: " + getIdentityPoolId() + ",");
        }
        if (getIdentities() != null) {
            sb.append("Identities: " + getIdentities() + ",");
        }
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getIdentityPoolId() == null ? 0 : getIdentityPoolId().hashCode())) * 31) + (getIdentities() == null ? 0 : getIdentities().hashCode())) * 31;
        if (getNextToken() != null) {
            i = getNextToken().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListIdentitiesResult)) {
            return false;
        }
        ListIdentitiesResult other = (ListIdentitiesResult) obj;
        if ((other.getIdentityPoolId() == null) ^ (getIdentityPoolId() == null)) {
            return false;
        }
        if (other.getIdentityPoolId() != null && !other.getIdentityPoolId().equals(getIdentityPoolId())) {
            return false;
        }
        if ((other.getIdentities() == null) ^ (getIdentities() == null)) {
            return false;
        }
        if (other.getIdentities() != null && !other.getIdentities().equals(getIdentities())) {
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
