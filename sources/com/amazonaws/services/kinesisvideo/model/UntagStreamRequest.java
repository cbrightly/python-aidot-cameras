package com.amazonaws.services.kinesisvideo.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UntagStreamRequest extends AmazonWebServiceRequest implements Serializable {
    private String streamARN;
    private String streamName;
    private List<String> tagKeyList = new ArrayList();

    public String getStreamARN() {
        return this.streamARN;
    }

    public void setStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
    }

    public UntagStreamRequest withStreamARN(String streamARN2) {
        this.streamARN = streamARN2;
        return this;
    }

    public String getStreamName() {
        return this.streamName;
    }

    public void setStreamName(String streamName2) {
        this.streamName = streamName2;
    }

    public UntagStreamRequest withStreamName(String streamName2) {
        this.streamName = streamName2;
        return this;
    }

    public List<String> getTagKeyList() {
        return this.tagKeyList;
    }

    public void setTagKeyList(Collection<String> tagKeyList2) {
        if (tagKeyList2 == null) {
            this.tagKeyList = null;
        } else {
            this.tagKeyList = new ArrayList(tagKeyList2);
        }
    }

    public UntagStreamRequest withTagKeyList(String... tagKeyList2) {
        if (getTagKeyList() == null) {
            this.tagKeyList = new ArrayList(tagKeyList2.length);
        }
        for (String value : tagKeyList2) {
            this.tagKeyList.add(value);
        }
        return this;
    }

    public UntagStreamRequest withTagKeyList(Collection<String> tagKeyList2) {
        setTagKeyList(tagKeyList2);
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
        if (getTagKeyList() != null) {
            sb.append("TagKeyList: " + getTagKeyList());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getStreamARN() == null ? 0 : getStreamARN().hashCode())) * 31) + (getStreamName() == null ? 0 : getStreamName().hashCode())) * 31;
        if (getTagKeyList() != null) {
            i = getTagKeyList().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof UntagStreamRequest)) {
            return false;
        }
        UntagStreamRequest other = (UntagStreamRequest) obj;
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
        if ((other.getTagKeyList() == null) ^ (getTagKeyList() == null)) {
            return false;
        }
        if (other.getTagKeyList() == null || other.getTagKeyList().equals(getTagKeyList())) {
            return true;
        }
        return false;
    }
}
