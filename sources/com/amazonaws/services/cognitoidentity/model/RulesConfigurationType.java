package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RulesConfigurationType implements Serializable {
    private List<MappingRule> rules;

    public List<MappingRule> getRules() {
        return this.rules;
    }

    public void setRules(Collection<MappingRule> rules2) {
        if (rules2 == null) {
            this.rules = null;
        } else {
            this.rules = new ArrayList(rules2);
        }
    }

    public RulesConfigurationType withRules(MappingRule... rules2) {
        if (getRules() == null) {
            this.rules = new ArrayList(rules2.length);
        }
        for (MappingRule value : rules2) {
            this.rules.add(value);
        }
        return this;
    }

    public RulesConfigurationType withRules(Collection<MappingRule> rules2) {
        setRules(rules2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getRules() != null) {
            sb.append("Rules: " + getRules());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getRules() == null ? 0 : getRules().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RulesConfigurationType)) {
            return false;
        }
        RulesConfigurationType other = (RulesConfigurationType) obj;
        if ((other.getRules() == null) ^ (getRules() == null)) {
            return false;
        }
        if (other.getRules() == null || other.getRules().equals(getRules())) {
            return true;
        }
        return false;
    }
}
