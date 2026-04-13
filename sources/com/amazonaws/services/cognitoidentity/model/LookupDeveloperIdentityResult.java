package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LookupDeveloperIdentityResult implements Serializable {
    private List<String> developerUserIdentifierList;
    private String identityId;
    private String nextToken;

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public LookupDeveloperIdentityResult withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public List<String> getDeveloperUserIdentifierList() {
        return this.developerUserIdentifierList;
    }

    public void setDeveloperUserIdentifierList(Collection<String> developerUserIdentifierList2) {
        if (developerUserIdentifierList2 == null) {
            this.developerUserIdentifierList = null;
        } else {
            this.developerUserIdentifierList = new ArrayList(developerUserIdentifierList2);
        }
    }

    public LookupDeveloperIdentityResult withDeveloperUserIdentifierList(String... developerUserIdentifierList2) {
        if (getDeveloperUserIdentifierList() == null) {
            this.developerUserIdentifierList = new ArrayList(developerUserIdentifierList2.length);
        }
        for (String value : developerUserIdentifierList2) {
            this.developerUserIdentifierList.add(value);
        }
        return this;
    }

    public LookupDeveloperIdentityResult withDeveloperUserIdentifierList(Collection<String> developerUserIdentifierList2) {
        setDeveloperUserIdentifierList(developerUserIdentifierList2);
        return this;
    }

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public LookupDeveloperIdentityResult withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityId() != null) {
            sb.append("IdentityId: " + getIdentityId() + ",");
        }
        if (getDeveloperUserIdentifierList() != null) {
            sb.append("DeveloperUserIdentifierList: " + getDeveloperUserIdentifierList() + ",");
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
        int hashCode = ((1 * 31) + (getIdentityId() == null ? 0 : getIdentityId().hashCode())) * 31;
        if (getDeveloperUserIdentifierList() == null) {
            i = 0;
        } else {
            i = getDeveloperUserIdentifierList().hashCode();
        }
        int hashCode2 = (hashCode + i) * 31;
        if (getNextToken() != null) {
            i2 = getNextToken().hashCode();
        }
        return hashCode2 + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof LookupDeveloperIdentityResult)) {
            return false;
        }
        LookupDeveloperIdentityResult other = (LookupDeveloperIdentityResult) obj;
        if ((other.getIdentityId() == null) ^ (getIdentityId() == null)) {
            return false;
        }
        if (other.getIdentityId() != null && !other.getIdentityId().equals(getIdentityId())) {
            return false;
        }
        if ((other.getDeveloperUserIdentifierList() == null) ^ (getDeveloperUserIdentifierList() == null)) {
            return false;
        }
        if (other.getDeveloperUserIdentifierList() != null && !other.getDeveloperUserIdentifierList().equals(getDeveloperUserIdentifierList())) {
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
