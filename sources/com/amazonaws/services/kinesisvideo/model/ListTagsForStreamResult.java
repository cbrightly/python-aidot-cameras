package com.amazonaws.services.kinesisvideo.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ListTagsForStreamResult implements Serializable {
    private String nextToken;
    private Map<String, String> tags = new HashMap();

    public String getNextToken() {
        return this.nextToken;
    }

    public void setNextToken(String nextToken2) {
        this.nextToken = nextToken2;
    }

    public ListTagsForStreamResult withNextToken(String nextToken2) {
        this.nextToken = nextToken2;
        return this;
    }

    public Map<String, String> getTags() {
        return this.tags;
    }

    public void setTags(Map<String, String> tags2) {
        this.tags = tags2;
    }

    public ListTagsForStreamResult withTags(Map<String, String> tags2) {
        this.tags = tags2;
        return this;
    }

    public ListTagsForStreamResult addTagsEntry(String key, String value) {
        if (this.tags == null) {
            this.tags = new HashMap();
        }
        if (!this.tags.containsKey(key)) {
            this.tags.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public ListTagsForStreamResult clearTagsEntries() {
        this.tags = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getNextToken() != null) {
            sb.append("NextToken: " + getNextToken() + ",");
        }
        if (getTags() != null) {
            sb.append("Tags: " + getTags());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((1 * 31) + (getNextToken() == null ? 0 : getNextToken().hashCode())) * 31;
        if (getTags() != null) {
            i = getTags().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListTagsForStreamResult)) {
            return false;
        }
        ListTagsForStreamResult other = (ListTagsForStreamResult) obj;
        if ((other.getNextToken() == null) ^ (getNextToken() == null)) {
            return false;
        }
        if (other.getNextToken() != null && !other.getNextToken().equals(getNextToken())) {
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
