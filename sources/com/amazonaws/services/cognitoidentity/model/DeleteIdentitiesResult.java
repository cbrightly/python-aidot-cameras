package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeleteIdentitiesResult implements Serializable {
    private List<UnprocessedIdentityId> unprocessedIdentityIds;

    public List<UnprocessedIdentityId> getUnprocessedIdentityIds() {
        return this.unprocessedIdentityIds;
    }

    public void setUnprocessedIdentityIds(Collection<UnprocessedIdentityId> unprocessedIdentityIds2) {
        if (unprocessedIdentityIds2 == null) {
            this.unprocessedIdentityIds = null;
        } else {
            this.unprocessedIdentityIds = new ArrayList(unprocessedIdentityIds2);
        }
    }

    public DeleteIdentitiesResult withUnprocessedIdentityIds(UnprocessedIdentityId... unprocessedIdentityIds2) {
        if (getUnprocessedIdentityIds() == null) {
            this.unprocessedIdentityIds = new ArrayList(unprocessedIdentityIds2.length);
        }
        for (UnprocessedIdentityId value : unprocessedIdentityIds2) {
            this.unprocessedIdentityIds.add(value);
        }
        return this;
    }

    public DeleteIdentitiesResult withUnprocessedIdentityIds(Collection<UnprocessedIdentityId> unprocessedIdentityIds2) {
        setUnprocessedIdentityIds(unprocessedIdentityIds2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getUnprocessedIdentityIds() != null) {
            sb.append("UnprocessedIdentityIds: " + getUnprocessedIdentityIds());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i;
        int i2 = 1 * 31;
        if (getUnprocessedIdentityIds() == null) {
            i = 0;
        } else {
            i = getUnprocessedIdentityIds().hashCode();
        }
        return i2 + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteIdentitiesResult)) {
            return false;
        }
        DeleteIdentitiesResult other = (DeleteIdentitiesResult) obj;
        if ((other.getUnprocessedIdentityIds() == null) ^ (getUnprocessedIdentityIds() == null)) {
            return false;
        }
        if (other.getUnprocessedIdentityIds() == null || other.getUnprocessedIdentityIds().equals(getUnprocessedIdentityIds())) {
            return true;
        }
        return false;
    }
}
