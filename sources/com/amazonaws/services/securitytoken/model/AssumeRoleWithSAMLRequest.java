package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AssumeRoleWithSAMLRequest extends AmazonWebServiceRequest implements Serializable {
    private Integer durationSeconds;
    private String policy;
    private List<PolicyDescriptorType> policyArns;
    private String principalArn;
    private String roleArn;
    private String sAMLAssertion;

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String roleArn2) {
        this.roleArn = roleArn2;
    }

    public AssumeRoleWithSAMLRequest withRoleArn(String roleArn2) {
        this.roleArn = roleArn2;
        return this;
    }

    public String getPrincipalArn() {
        return this.principalArn;
    }

    public void setPrincipalArn(String principalArn2) {
        this.principalArn = principalArn2;
    }

    public AssumeRoleWithSAMLRequest withPrincipalArn(String principalArn2) {
        this.principalArn = principalArn2;
        return this;
    }

    public String getSAMLAssertion() {
        return this.sAMLAssertion;
    }

    public void setSAMLAssertion(String sAMLAssertion2) {
        this.sAMLAssertion = sAMLAssertion2;
    }

    public AssumeRoleWithSAMLRequest withSAMLAssertion(String sAMLAssertion2) {
        this.sAMLAssertion = sAMLAssertion2;
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

    public AssumeRoleWithSAMLRequest withPolicyArns(PolicyDescriptorType... policyArns2) {
        if (getPolicyArns() == null) {
            this.policyArns = new ArrayList(policyArns2.length);
        }
        for (PolicyDescriptorType value : policyArns2) {
            this.policyArns.add(value);
        }
        return this;
    }

    public AssumeRoleWithSAMLRequest withPolicyArns(Collection<PolicyDescriptorType> policyArns2) {
        setPolicyArns(policyArns2);
        return this;
    }

    public String getPolicy() {
        return this.policy;
    }

    public void setPolicy(String policy2) {
        this.policy = policy2;
    }

    public AssumeRoleWithSAMLRequest withPolicy(String policy2) {
        this.policy = policy2;
        return this;
    }

    public Integer getDurationSeconds() {
        return this.durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
    }

    public AssumeRoleWithSAMLRequest withDurationSeconds(Integer durationSeconds2) {
        this.durationSeconds = durationSeconds2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn() + ",");
        }
        if (getPrincipalArn() != null) {
            sb.append("PrincipalArn: " + getPrincipalArn() + ",");
        }
        if (getSAMLAssertion() != null) {
            sb.append("SAMLAssertion: " + getSAMLAssertion() + ",");
        }
        if (getPolicyArns() != null) {
            sb.append("PolicyArns: " + getPolicyArns() + ",");
        }
        if (getPolicy() != null) {
            sb.append("Policy: " + getPolicy() + ",");
        }
        if (getDurationSeconds() != null) {
            sb.append("DurationSeconds: " + getDurationSeconds());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((((1 * 31) + (getRoleArn() == null ? 0 : getRoleArn().hashCode())) * 31) + (getPrincipalArn() == null ? 0 : getPrincipalArn().hashCode())) * 31) + (getSAMLAssertion() == null ? 0 : getSAMLAssertion().hashCode())) * 31) + (getPolicyArns() == null ? 0 : getPolicyArns().hashCode())) * 31) + (getPolicy() == null ? 0 : getPolicy().hashCode())) * 31;
        if (getDurationSeconds() != null) {
            i = getDurationSeconds().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AssumeRoleWithSAMLRequest)) {
            return false;
        }
        AssumeRoleWithSAMLRequest other = (AssumeRoleWithSAMLRequest) obj;
        if ((other.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        if (other.getRoleArn() != null && !other.getRoleArn().equals(getRoleArn())) {
            return false;
        }
        if ((other.getPrincipalArn() == null) ^ (getPrincipalArn() == null)) {
            return false;
        }
        if (other.getPrincipalArn() != null && !other.getPrincipalArn().equals(getPrincipalArn())) {
            return false;
        }
        if ((other.getSAMLAssertion() == null) ^ (getSAMLAssertion() == null)) {
            return false;
        }
        if (other.getSAMLAssertion() != null && !other.getSAMLAssertion().equals(getSAMLAssertion())) {
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
        if (other.getDurationSeconds() == null || other.getDurationSeconds().equals(getDurationSeconds())) {
            return true;
        }
        return false;
    }
}
