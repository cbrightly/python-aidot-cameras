package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AssumeRoleRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer durationSeconds;
    private String externalId;
    private String policy;
    private List<PolicyDescriptorType> policyArns;
    private String roleArn;
    private String roleSessionName;
    private String serialNumber;
    private List<Tag> tags;
    private String tokenCode;
    private List<String> transitiveTagKeys;

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String roleArn2) {
        this.roleArn = roleArn2;
    }

    public AssumeRoleRequest withRoleArn(String roleArn2) {
        this.roleArn = roleArn2;
        return this;
    }

    public String getRoleSessionName() {
        return this.roleSessionName;
    }

    public void setRoleSessionName(String roleSessionName2) {
        this.roleSessionName = roleSessionName2;
    }

    public AssumeRoleRequest withRoleSessionName(String roleSessionName2) {
        this.roleSessionName = roleSessionName2;
        return this;
    }

    public List<PolicyDescriptorType> getPolicyArns() {
        return this.policyArns;
    }

    public void setPolicyArns(Collection<PolicyDescriptorType> policyArns2) {
        if (policyArns2 == null) {
            this.policyArns = null;
        } else {
            this.policyArns = new ArrayList(policyArns2);
        }
    }

    public AssumeRoleRequest withPolicyArns(PolicyDescriptorType... policyArns2) {
        if (getPolicyArns() == null) {
            this.policyArns = new ArrayList(policyArns2.length);
        }
        for (PolicyDescriptorType value : policyArns2) {
            this.policyArns.add(value);
        }
        return this;
    }

    public AssumeRoleRequest withPolicyArns(Collection<PolicyDescriptorType> policyArns2) {
        setPolicyArns(policyArns2);
        return this;
    }

    public String getPolicy() {
        return this.policy;
    }

    public void setPolicy(String policy2) {
        this.policy = policy2;
    }

    public AssumeRoleRequest withPolicy(String policy2) {
        this.policy = policy2;
        return this;
    }

    public Integer getDurationSeconds() {
        return this.durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
    }

    public AssumeRoleRequest withDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
        return this;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Collection<Tag> tags2) {
        if (tags2 == null) {
            this.tags = null;
        } else {
            this.tags = new ArrayList(tags2);
        }
    }

    public AssumeRoleRequest withTags(Tag... tags2) {
        if (getTags() == null) {
            this.tags = new ArrayList(tags2.length);
        }
        for (Tag value : tags2) {
            this.tags.add(value);
        }
        return this;
    }

    public AssumeRoleRequest withTags(Collection<Tag> tags2) {
        setTags(tags2);
        return this;
    }

    public List<String> getTransitiveTagKeys() {
        return this.transitiveTagKeys;
    }

    public void setTransitiveTagKeys(Collection<String> transitiveTagKeys2) {
        if (transitiveTagKeys2 == null) {
            this.transitiveTagKeys = null;
        } else {
            this.transitiveTagKeys = new ArrayList(transitiveTagKeys2);
        }
    }

    public AssumeRoleRequest withTransitiveTagKeys(String... transitiveTagKeys2) {
        if (getTransitiveTagKeys() == null) {
            this.transitiveTagKeys = new ArrayList(transitiveTagKeys2.length);
        }
        for (String value : transitiveTagKeys2) {
            this.transitiveTagKeys.add(value);
        }
        return this;
    }

    public AssumeRoleRequest withTransitiveTagKeys(Collection<String> transitiveTagKeys2) {
        setTransitiveTagKeys(transitiveTagKeys2);
        return this;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String externalId2) {
        this.externalId = externalId2;
    }

    public AssumeRoleRequest withExternalId(String externalId2) {
        this.externalId = externalId2;
        return this;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String serialNumber2) {
        this.serialNumber = serialNumber2;
    }

    public AssumeRoleRequest withSerialNumber(String serialNumber2) {
        this.serialNumber = serialNumber2;
        return this;
    }

    public String getTokenCode() {
        return this.tokenCode;
    }

    public void setTokenCode(String tokenCode2) {
        this.tokenCode = tokenCode2;
    }

    public AssumeRoleRequest withTokenCode(String tokenCode2) {
        this.tokenCode = tokenCode2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn() + ",");
        }
        if (getRoleSessionName() != null) {
            sb.append("RoleSessionName: " + getRoleSessionName() + ",");
        }
        if (getPolicyArns() != null) {
            sb.append("PolicyArns: " + getPolicyArns() + ",");
        }
        if (getPolicy() != null) {
            sb.append("Policy: " + getPolicy() + ",");
        }
        if (getDurationSeconds() != null) {
            sb.append("DurationSeconds: " + getDurationSeconds() + ",");
        }
        if (getTags() != null) {
            sb.append("Tags: " + getTags() + ",");
        }
        if (getTransitiveTagKeys() != null) {
            sb.append("TransitiveTagKeys: " + getTransitiveTagKeys() + ",");
        }
        if (getExternalId() != null) {
            sb.append("ExternalId: " + getExternalId() + ",");
        }
        if (getSerialNumber() != null) {
            sb.append("SerialNumber: " + getSerialNumber() + ",");
        }
        if (getTokenCode() != null) {
            sb.append("TokenCode: " + getTokenCode());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((((((((((1 * 31) + (getRoleArn() == null ? 0 : getRoleArn().hashCode())) * 31) + (getRoleSessionName() == null ? 0 : getRoleSessionName().hashCode())) * 31) + (getPolicyArns() == null ? 0 : getPolicyArns().hashCode())) * 31) + (getPolicy() == null ? 0 : getPolicy().hashCode())) * 31) + (getDurationSeconds() == null ? 0 : getDurationSeconds().hashCode())) * 31) + (getTags() == null ? 0 : getTags().hashCode())) * 31) + (getTransitiveTagKeys() == null ? 0 : getTransitiveTagKeys().hashCode())) * 31) + (getExternalId() == null ? 0 : getExternalId().hashCode())) * 31) + (getSerialNumber() == null ? 0 : getSerialNumber().hashCode())) * 31;
        if (getTokenCode() != null) {
            i = getTokenCode().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AssumeRoleRequest)) {
            return false;
        }
        AssumeRoleRequest other = (AssumeRoleRequest) obj;
        if ((other.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        if (other.getRoleArn() != null && !other.getRoleArn().equals(getRoleArn())) {
            return false;
        }
        if ((other.getRoleSessionName() == null) ^ (getRoleSessionName() == null)) {
            return false;
        }
        if (other.getRoleSessionName() != null && !other.getRoleSessionName().equals(getRoleSessionName())) {
            return false;
        }
        if ((other.getPolicyArns() == null) ^ (getPolicyArns() == null)) {
            return false;
        }
        if (other.getPolicyArns() != null && !other.getPolicyArns().equals(getPolicyArns())) {
            return false;
        }
        if ((other.getPolicy() == null) ^ (getPolicy() == null)) {
            return false;
        }
        if (other.getPolicy() != null && !other.getPolicy().equals(getPolicy())) {
            return false;
        }
        if ((other.getDurationSeconds() == null) ^ (getDurationSeconds() == null)) {
            return false;
        }
        if (other.getDurationSeconds() != null && !other.getDurationSeconds().equals(getDurationSeconds())) {
            return false;
        }
        if ((other.getTags() == null) ^ (getTags() == null)) {
            return false;
        }
        if (other.getTags() != null && !other.getTags().equals(getTags())) {
            return false;
        }
        if ((other.getTransitiveTagKeys() == null) ^ (getTransitiveTagKeys() == null)) {
            return false;
        }
        if (other.getTransitiveTagKeys() != null && !other.getTransitiveTagKeys().equals(getTransitiveTagKeys())) {
            return false;
        }
        if ((other.getExternalId() == null) ^ (getExternalId() == null)) {
            return false;
        }
        if (other.getExternalId() != null && !other.getExternalId().equals(getExternalId())) {
            return false;
        }
        if ((other.getSerialNumber() == null) ^ (getSerialNumber() == null)) {
            return false;
        }
        if (other.getSerialNumber() != null && !other.getSerialNumber().equals(getSerialNumber())) {
            return false;
        }
        if ((other.getTokenCode() == null) ^ (getTokenCode() == null)) {
            return false;
        }
        if (other.getTokenCode() == null || other.getTokenCode().equals(getTokenCode())) {
            return true;
        }
        return false;
    }
}
