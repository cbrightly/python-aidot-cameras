package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GetFederationTokenRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer durationSeconds;
    private String name;
    private String policy;
    private List<PolicyDescriptorType> policyArns;
    private List<Tag> tags;

    public GetFederationTokenRequest() {
    }

    public GetFederationTokenRequest(String name2) {
        setName(name2);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public GetFederationTokenRequest withName(String name2) {
        this.name = name2;
        return this;
    }

    public String getPolicy() {
        return this.policy;
    }

    public void setPolicy(String policy2) {
        this.policy = policy2;
    }

    public GetFederationTokenRequest withPolicy(String policy2) {
        this.policy = policy2;
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

    public GetFederationTokenRequest withPolicyArns(PolicyDescriptorType... policyArns2) {
        if (getPolicyArns() == null) {
            this.policyArns = new ArrayList(policyArns2.length);
        }
        for (PolicyDescriptorType value : policyArns2) {
            this.policyArns.add(value);
        }
        return this;
    }

    public GetFederationTokenRequest withPolicyArns(Collection<PolicyDescriptorType> policyArns2) {
        setPolicyArns(policyArns2);
        return this;
    }

    public Integer getDurationSeconds() {
        return this.durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
    }

    public GetFederationTokenRequest withDurationSeconds(Integer durationSeconds2) {
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

    public GetFederationTokenRequest withTags(Tag... tags2) {
        if (getTags() == null) {
            this.tags = new ArrayList(tags2.length);
        }
        for (Tag value : tags2) {
            this.tags.add(value);
        }
        return this;
    }

    public GetFederationTokenRequest withTags(Collection<Tag> tags2) {
        setTags(tags2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getName() != null) {
            sb.append("Name: " + getName() + ",");
        }
        if (getPolicy() != null) {
            sb.append("Policy: " + getPolicy() + ",");
        }
        if (getPolicyArns() != null) {
            sb.append("PolicyArns: " + getPolicyArns() + ",");
        }
        if (getDurationSeconds() != null) {
            sb.append("DurationSeconds: " + getDurationSeconds() + ",");
        }
        if (getTags() != null) {
            sb.append("Tags: " + getTags());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((1 * 31) + (getName() == null ? 0 : getName().hashCode())) * 31) + (getPolicy() == null ? 0 : getPolicy().hashCode())) * 31) + (getPolicyArns() == null ? 0 : getPolicyArns().hashCode())) * 31) + (getDurationSeconds() == null ? 0 : getDurationSeconds().hashCode())) * 31;
        if (getTags() != null) {
            i = getTags().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetFederationTokenRequest)) {
            return false;
        }
        GetFederationTokenRequest other = (GetFederationTokenRequest) obj;
        if ((other.getName() == null) ^ (getName() == null)) {
            return false;
        }
        if (other.getName() != null && !other.getName().equals(getName())) {
            return false;
        }
        if ((other.getPolicy() == null) ^ (getPolicy() == null)) {
            return false;
        }
        if (other.getPolicy() != null && !other.getPolicy().equals(getPolicy())) {
            return false;
        }
        if ((other.getPolicyArns() == null) ^ (getPolicyArns() == null)) {
            return false;
        }
        if (other.getPolicyArns() != null && !other.getPolicyArns().equals(getPolicyArns())) {
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
        if (other.getTags() == null || other.getTags().equals(getTags())) {
            return true;
        }
        return false;
    }
}
