package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;

public class MappingRule implements Serializable {
    private String claim;
    private String matchType;
    private String roleARN;
    private String value;

    public String getClaim() {
        return this.claim;
    }

    public void setClaim(String claim2) {
        this.claim = claim2;
    }

    public MappingRule withClaim(String claim2) {
        this.claim = claim2;
        return this;
    }

    public String getMatchType() {
        return this.matchType;
    }

    public void setMatchType(String matchType2) {
        this.matchType = matchType2;
    }

    public MappingRule withMatchType(String matchType2) {
        this.matchType = matchType2;
        return this;
    }

    public void setMatchType(MappingRuleMatchType matchType2) {
        this.matchType = matchType2.toString();
    }

    public MappingRule withMatchType(MappingRuleMatchType matchType2) {
        this.matchType = matchType2.toString();
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public MappingRule withValue(String value2) {
        this.value = value2;
        return this;
    }

    public String getRoleARN() {
        return this.roleARN;
    }

    public void setRoleARN(String roleARN2) {
        this.roleARN = roleARN2;
    }

    public MappingRule withRoleARN(String roleARN2) {
        this.roleARN = roleARN2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getClaim() != null) {
            sb.append("Claim: " + getClaim() + ",");
        }
        if (getMatchType() != null) {
            sb.append("MatchType: " + getMatchType() + ",");
        }
        if (getValue() != null) {
            sb.append("Value: " + getValue() + ",");
        }
        if (getRoleARN() != null) {
            sb.append("RoleARN: " + getRoleARN());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((1 * 31) + (getClaim() == null ? 0 : getClaim().hashCode())) * 31) + (getMatchType() == null ? 0 : getMatchType().hashCode())) * 31) + (getValue() == null ? 0 : getValue().hashCode())) * 31;
        if (getRoleARN() != null) {
            i = getRoleARN().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof MappingRule)) {
            return false;
        }
        MappingRule other = (MappingRule) obj;
        if ((other.getClaim() == null) ^ (getClaim() == null)) {
            return false;
        }
        if (other.getClaim() != null && !other.getClaim().equals(getClaim())) {
            return false;
        }
        if ((other.getMatchType() == null) ^ (getMatchType() == null)) {
            return false;
        }
        if (other.getMatchType() != null && !other.getMatchType().equals(getMatchType())) {
            return false;
        }
        if ((other.getValue() == null) ^ (getValue() == null)) {
            return false;
        }
        if (other.getValue() != null && !other.getValue().equals(getValue())) {
            return false;
        }
        if ((other.getRoleARN() == null) ^ (getRoleARN() == null)) {
            return false;
        }
        if (other.getRoleARN() == null || other.getRoleARN().equals(getRoleARN())) {
            return true;
        }
        return false;
    }
}
