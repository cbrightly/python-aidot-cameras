package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TagStreamRequest extends AmazonWebServiceRequest implements Serializable {
    private String streamARN;
    private String streamName;
    private Map<String, String> tags = new HashMap();

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public TagStreamRequest withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName2) {
        this.streamName = streamName2;
    }

    public TagStreamRequest withStreamName(String streamName2) {
        this.streamName = streamName2;
        return this;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public void setTags(Map<String, String> tags2) {
        this.tags = tags2;
    }

    public TagStreamRequest withTags(Map<String, String> tags2) {
        this.tags = tags2;
        return this;
    }

    public TagStreamRequest addTagsEntry(String key, String value) {
        if (this.tags == null) {
            this.tags = new HashMap();
        }
        if (!this.tags.containsKey(key)) {
            this.tags.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public TagStreamRequest clearTagsEntries() {
        this.tags = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getStreamARN() != null) {
            sb.append("StreamARN: " + getStreamARN() + ",");
        }
        if (getStreamName() != null) {
            sb.append("StreamName: " + getStreamName() + ",");
        }
        if (getTags() != null) {
            sb.append("Tags: " + getTags());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getStreamARN() == null ? 0 : getStreamARN().hashCode())) * 31) + (getStreamName() == null ? 0 : getStreamName().hashCode())) * 31;
        if (getTags() != null) {
            i = getTags().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TagStreamRequest)) {
            return false;
        }
        TagStreamRequest other = (TagStreamRequest) obj;
        if ((other.getStreamARN() == null) ^ (getStreamARN() == null)) {
            return false;
        }
        if (other.getStreamARN() != null && !other.getStreamARN().equals(getStreamARN())) {
            return false;
        }
        if ((other.getStreamName() == null) ^ (getStreamName() == null)) {
            return false;
        }
        if (other.getStreamName() != null && !other.getStreamName().equals(getStreamName())) {
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
