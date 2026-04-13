package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class Tag implements Serializable {
    private String key;
    private String value;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key2) {
        this.key = key2;
    }

    public Tag withKey(String key2) {
        this.key = key2;
        return this;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value2) {
        this.value = value2;
    }

    public Tag withValue(String value2) {
        this.value = value2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getKey() != null) {
            sb.append("Key: " + getKey() + ",");
        }
        if (getValue() != null) {
            sb.append("Value: " + getValue());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getKey() == null ? 0 : getKey().hashCode())) * 31;
        if (getValue() != null) {
            i = getValue().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) obj;
        if ((other.getKey() == null) ^ (getKey() == null)) {
            return false;
        }
        if (other.getKey() != null && !other.getKey().equals(getKey())) {
            return false;
        }
        if ((other.getValue() == null) ^ (getValue() == null)) {
            return false;
        }
        if (other.getValue() == null || other.getValue().equals(getValue())) {
            return true;
        }
        return false;
    }
}
