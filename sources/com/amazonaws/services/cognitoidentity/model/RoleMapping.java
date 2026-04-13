package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;

public class RoleMapping implements Serializable {
    private String ambiguousRoleResolution;
    private RulesConfigurationType rulesConfiguration;
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public RoleMapping withType(String type2) {
        this.type = type2;
        return this;
    }

    public void setType(RoleMappingType type2) {
        this.type = type2.toString();
    }

    public RoleMapping withType(RoleMappingType type2) {
        this.type = type2.toString();
        return this;
    }

    public String getAmbiguousRoleResolution() {
        return this.ambiguousRoleResolution;
    }

    public void setAmbiguousRoleResolution(String ambiguousRoleResolution2) {
        this.ambiguousRoleResolution = ambiguousRoleResolution2;
    }

    public RoleMapping withAmbiguousRoleResolution(String ambiguousRoleResolution2) {
        this.ambiguousRoleResolution = ambiguousRoleResolution2;
        return this;
    }

    public void setAmbiguousRoleResolution(AmbiguousRoleResolutionType ambiguousRoleResolution2) {
        this.ambiguousRoleResolution = ambiguousRoleResolution2.toString();
    }

    public RoleMapping withAmbiguousRoleResolution(AmbiguousRoleResolutionType ambiguousRoleResolution2) {
        this.ambiguousRoleResolution = ambiguousRoleResolution2.toString();
        return this;
    }

    public RulesConfigurationType getRulesConfiguration() {
        return this.rulesConfiguration;
    }

    public void setRulesConfiguration(RulesConfigurationType rulesConfiguration2) {
        this.rulesConfiguration = rulesConfiguration2;
    }

    public RoleMapping withRulesConfiguration(RulesConfigurationType rulesConfiguration2) {
        this.rulesConfiguration = rulesConfiguration2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getType() != null) {
            sb.append("Type: " + getType() + ",");
        }
        if (getAmbiguousRoleResolution() != null) {
            sb.append("AmbiguousRoleResolution: " + getAmbiguousRoleResolution() + ",");
        }
        if (getRulesConfiguration() != null) {
            sb.append("RulesConfiguration: " + getRulesConfiguration());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        int hashCode = ((1 * 31) + (getType() == null ? 0 : getType().hashCode())) * 31;
        if (getAmbiguousRoleResolution() == null) {
            i = 0;
        } else {
            i = getAmbiguousRoleResolution().hashCode();
        }
        int hashCode2 = (hashCode + i) * 31;
        if (getRulesConfiguration() != null) {
            i2 = getRulesConfiguration().hashCode();
        }
        return hashCode2 + i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RoleMapping)) {
            return false;
        }
        RoleMapping other = (RoleMapping) obj;
        if ((other.getType() == null) ^ (getType() == null)) {
            return false;
        }
        if (other.getType() != null && !other.getType().equals(getType())) {
            return false;
        }
        if ((other.getAmbiguousRoleResolution() == null) ^ (getAmbiguousRoleResolution() == null)) {
            return false;
        }
        if (other.getAmbiguousRoleResolution() != null && !other.getAmbiguousRoleResolution().equals(getAmbiguousRoleResolution())) {
            return false;
        }
        if ((other.getRulesConfiguration() == null) ^ (getRulesConfiguration() == null)) {
            return false;
        }
        if (other.getRulesConfiguration() == null || other.getRulesConfiguration().equals(getRulesConfiguration())) {
            return true;
        }
        return false;
    }
}
