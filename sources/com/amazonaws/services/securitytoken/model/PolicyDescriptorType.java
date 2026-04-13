package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class PolicyDescriptorType implements Serializable {
    private String arn;

    public String getArn() {
        return this.arn;
    }

    public void setArn(String arn2) {
        this.arn = arn2;
    }

    public PolicyDescriptorType withArn(String arn2) {
        this.arn = arn2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getArn() != null) {
            sb.append("arn: " + getArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getArn() == null ? 0 : getArn().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PolicyDescriptorType)) {
            return false;
        }
        PolicyDescriptorType other = (PolicyDescriptorType) obj;
        if ((other.getArn() == null) ^ (getArn() == null)) {
            return false;
        }
        if (other.getArn() == null || other.getArn().equals(getArn())) {
            return true;
        }
        return false;
    }
}
