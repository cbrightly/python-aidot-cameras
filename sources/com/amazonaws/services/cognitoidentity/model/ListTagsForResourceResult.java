package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ListTagsForResourceResult implements Serializable {
    private Map<String, String> tags;

    public Map<String, String> getTags() {
        return this.tags;
    }

    public void setTags(Map<String, String> tags2) {
        this.tags = tags2;
    }

    public ListTagsForResourceResult withTags(Map<String, String> tags2) {
        this.tags = tags2;
        return this;
    }

    public ListTagsForResourceResult addTagsEntry(String key, String value) {
        if (this.tags == null) {
            this.tags = new HashMap();
        }
        if (!this.tags.containsKey(key)) {
            this.tags.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public ListTagsForResourceResult clearTagsEntries() {
        this.tags = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getTags() != null) {
            sb.append("Tags: " + getTags());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getTags() == null ? 0 : getTags().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ListTagsForResourceResult)) {
            return false;
        }
        ListTagsForResourceResult other = (ListTagsForResourceResult) obj;
        if ((other.getTags() == null) ^ (getTags() == null)) {
            return false;
        }
        if (other.getTags() == null || other.getTags().equals(getTags())) {
            return true;
        }
        return false;
    }
}
