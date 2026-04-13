package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GetIdentityPoolRolesResult implements Serializable {
    private String identityPoolId;
    private Map<String, RoleMapping> roleMappings;
    private Map<String, String> roles;

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public GetIdentityPoolRolesResult withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public Map<String, String> getRoles() {
        return this.roles;
    }

    public void setRoles(Map<String, String> roles2) {
        this.roles = roles2;
    }

    public GetIdentityPoolRolesResult withRoles(Map<String, String> roles2) {
        this.roles = roles2;
        return this;
    }

    public GetIdentityPoolRolesResult addRolesEntry(String key, String value) {
        if (this.roles == null) {
            this.roles = new HashMap();
        }
        if (!this.roles.containsKey(key)) {
            this.roles.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public GetIdentityPoolRolesResult clearRolesEntries() {
        this.roles = null;
        return this;
    }

    public Map<String, RoleMapping> getRoleMappings() {
        return this.roleMappings;
    }

    public void setRoleMappings(Map<String, RoleMapping> roleMappings2) {
        this.roleMappings = roleMappings2;
    }

    public GetIdentityPoolRolesResult withRoleMappings(Map<String, RoleMapping> roleMappings2) {
        this.roleMappings = roleMappings2;
        return this;
    }

    public GetIdentityPoolRolesResult addRoleMappingsEntry(String key, RoleMapping value) {
        if (this.roleMappings == null) {
            this.roleMappings = new HashMap();
        }
        if (!this.roleMappings.containsKey(key)) {
            this.roleMappings.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public GetIdentityPoolRolesResult clearRoleMappingsEntries() {
        this.roleMappings = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityPoolId() != null) {
            sb.append("IdentityPoolId: " + getIdentityPoolId() + ",");
        }
        if (getRoles() != null) {
            sb.append("Roles: " + getRoles() + ",");
        }
        if (getRoleMappings() != null) {
            sb.append("RoleMappings: " + getRoleMappings());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getIdentityPoolId() == null ? 0 : getIdentityPoolId().hashCode())) * 31) + (getRoles() == null ? 0 : getRoles().hashCode())) * 31;
        if (getRoleMappings() != null) {
            i = getRoleMappings().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetIdentityPoolRolesResult)) {
            return false;
        }
        GetIdentityPoolRolesResult other = (GetIdentityPoolRolesResult) obj;
        if ((other.getIdentityPoolId() == null) ^ (getIdentityPoolId() == null)) {
            return false;
        }
        if (other.getIdentityPoolId() != null && !other.getIdentityPoolId().equals(getIdentityPoolId())) {
            return false;
        }
        if ((other.getRoles() == null) ^ (getRoles() == null)) {
            return false;
        }
        if (other.getRoles() != null && !other.getRoles().equals(getRoles())) {
            return false;
        }
        if ((other.getRoleMappings() == null) ^ (getRoleMappings() == null)) {
            return false;
        }
        if (other.getRoleMappings() == null || other.getRoleMappings().equals(getRoleMappings())) {
            return true;
        }
        return false;
    }
}
