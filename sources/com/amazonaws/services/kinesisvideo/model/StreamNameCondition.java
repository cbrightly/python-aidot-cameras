package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;

public class StreamNameCondition implements Serializable {
    private String comparisonOperator;
    private String comparisonValue;

    public String getComparisonOperator() {
        return this.comparisonOperator;
    }

    public void setComparisonOperator(String comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2;
    }

    public StreamNameCondition withComparisonOperator(String comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2;
        return this;
    }

    public void setComparisonOperator(ComparisonOperator comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2.toString();
    }

    public StreamNameCondition withComparisonOperator(ComparisonOperator comparisonOperator2) {
        this.comparisonOperator = comparisonOperator2.toString();
        return this;
    }

    public String getComparisonValue() {
        return this.comparisonValue;
    }

    public void setComparisonValue(String comparisonValue2) {
        this.comparisonValue = comparisonValue2;
    }

    public StreamNameCondition withComparisonValue(String comparisonValue2) {
        this.comparisonValue = comparisonValue2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getComparisonOperator() != null) {
            sb.append("ComparisonOperator: " + getComparisonOperator() + ",");
        }
        if (getComparisonValue() != null) {
            sb.append("ComparisonValue: " + getComparisonValue());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getComparisonOperator() == null ? 0 : getComparisonOperator().hashCode())) * 31;
        if (getComparisonValue() != null) {
            i = getComparisonValue().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof StreamNameCondition)) {
            return false;
        }
        StreamNameCondition other = (StreamNameCondition) obj;
        if ((other.getComparisonOperator() == null) ^ (getComparisonOperator() == null)) {
            return false;
        }
        if (other.getComparisonOperator() != null && !other.getComparisonOperator().equals(getComparisonOperator())) {
            return false;
        }
        if ((other.getComparisonValue() == null) ^ (getComparisonValue() == null)) {
            return false;
        }
        if (other.getComparisonValue() == null || other.getComparisonValue().equals(getComparisonValue())) {
            return true;
        }
        return false;
    }
}
