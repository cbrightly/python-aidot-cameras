package com.amazonaws.services.cognitoidentity.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeleteIdentitiesRequest extends AmazonWebServiceRequest implements Serializable {
    private List<String> identityIdsToDelete;

    public List<String> getIdentityIdsToDelete() {
        return this.identityIdsToDelete;
    }

    public void setIdentityIdsToDelete(Collection<String> identityIdsToDelete2) {
        if (identityIdsToDelete2 == null) {
            this.identityIdsToDelete = null;
        } else {
            this.identityIdsToDelete = new ArrayList(identityIdsToDelete2);
        }
    }

    public DeleteIdentitiesRequest withIdentityIdsToDelete(String... identityIdsToDelete2) {
        if (getIdentityIdsToDelete() == null) {
            this.identityIdsToDelete = new ArrayList(identityIdsToDelete2.length);
        }
        for (String value : identityIdsToDelete2) {
            this.identityIdsToDelete.add(value);
        }
        return this;
    }

    public DeleteIdentitiesRequest withIdentityIdsToDelete(Collection<String> identityIdsToDelete2) {
        setIdentityIdsToDelete(identityIdsToDelete2);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityIdsToDelete() != null) {
            sb.append("IdentityIdsToDelete: " + getIdentityIdsToDelete());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getIdentityIdsToDelete() == null ? 0 : getIdentityIdsToDelete().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DeleteIdentitiesRequest)) {
            return false;
        }
        DeleteIdentitiesRequest other = (DeleteIdentitiesRequest) obj;
        if ((other.getIdentityIdsToDelete() == null) ^ (getIdentityIdsToDelete() == null)) {
            return false;
        }
        if (other.getIdentityIdsToDelete() == null || other.getIdentityIdsToDelete().equals(getIdentityIdsToDelete())) {
            return true;
        }
        return false;
    }
}
