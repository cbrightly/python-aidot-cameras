package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class UpdateDataRetentionRequest extends AmazonWebServiceRequest implements Serializable {
    private String currentVersion;
    private Integer dataRetentionChangeInHours;
    private String operation;
    private String streamARN;
    private String streamName;

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName2) {
        this.streamName = streamName2;
    }

    public UpdateDataRetentionRequest withStreamName(String streamName2) {
        this.streamName = streamName2;
        return this;
    }

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public UpdateDataRetentionRequest withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String getCurrentVersion() {
        return this.currentVersion;
    }

    public void setCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
    }

    public UpdateDataRetentionRequest withCurrentVersion(String currentVersion2) {
        this.currentVersion = currentVersion2;
        return this;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation2) {
        this.operation = operation2;
    }

    public UpdateDataRetentionRequest withOperation(String operation2) {
        this.operation = operation2;
        return this;
    }

    public void setOperation(UpdateDataRetentionOperation operation2) {
        this.operation = operation2.toString();
    }

    public UpdateDataRetentionRequest withOperation(UpdateDataRetentionOperation operation2) {
        this.operation = operation2.toString();
        return this;
    }

    public Integer getDataRetentionChangeInHours() {
        return this.dataRetentionChangeInHours;
    }

    public void setDataRetentionChangeInHours(Integer dataRetentionChangeInHours2) {
        this.dataRetentionChangeInHours = dataRetentionChangeInHours2;
    }

    public UpdateDataRetentionRequest withDataRetentionChangeInHours(Integer dataRetentionChangeInHours2) {
        this.dataRetentionChangeInHours = dataRetentionChangeInHours2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStreamName() != null) {
            sb.append("StreamName: " + getStreamName() + ",");
        }
        if (getStreamARN() != null) {
            sb.append("StreamARN: " + getStreamARN() + ",");
        }
        if (getCurrentVersion() != null) {
            sb.append("CurrentVersion: " + getCurrentVersion() + ",");
        }
        if (getOperation() != null) {
            sb.append("Operation: " + getOperation() + ",");
        }
        if (getDataRetentionChangeInHours() != null) {
            sb.append("DataRetentionChangeInHours: " + getDataRetentionChangeInHours());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((((1 * 31) + (getStreamName() == null ? 0 : getStreamName().hashCode())) * 31) + (getStreamARN() == null ? 0 : getStreamARN().hashCode())) * 31) + (getCurrentVersion() == null ? 0 : getCurrentVersion().hashCode())) * 31) + (getOperation() == null ? 0 : getOperation().hashCode())) * 31;
        if (getDataRetentionChangeInHours() != null) {
            i = getDataRetentionChangeInHours().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UpdateDataRetentionRequest)) {
            return false;
        }
        UpdateDataRetentionRequest other = (UpdateDataRetentionRequest) obj;
        if ((other.getStreamName() == null) ^ (getStreamName() == null)) {
            return false;
        }
        if (other.getStreamName() != null && !other.getStreamName().equals(getStreamName())) {
            return false;
        }
        if ((other.getStreamARN() == null) ^ (getStreamARN() == null)) {
            return false;
        }
        if (other.getStreamARN() != null && !other.getStreamARN().equals(getStreamARN())) {
            return false;
        }
        if ((other.getCurrentVersion() == null) ^ (getCurrentVersion() == null)) {
            return false;
        }
        if (other.getCurrentVersion() != null && !other.getCurrentVersion().equals(getCurrentVersion())) {
            return false;
        }
        if ((other.getOperation() == null) ^ (getOperation() == null)) {
            return false;
        }
        if (other.getOperation() != null && !other.getOperation().equals(getOperation())) {
            return false;
        }
        if ((other.getDataRetentionChangeInHours() == null) ^ (getDataRetentionChangeInHours() == null)) {
            return false;
        }
        if (other.getDataRetentionChangeInHours() == null || other.getDataRetentionChangeInHours().equals(getDataRetentionChangeInHours())) {
            return true;
        }
        return false;
    }
}
