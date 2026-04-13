package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DescribeIdentityResult implements Serializable {
    private Date creationDate;
    private String identityId;
    private Date lastModifiedDate;
    private List<String> logins;

    public String getIdentityId() {
        return this.identityId;
    }

    public void setIdentityId(String identityId2) {
        this.identityId = identityId2;
    }

    public DescribeIdentityResult withIdentityId(String identityId2) {
        this.identityId = identityId2;
        return this;
    }

    public List<String> getLogins() {
        return this.logins;
    }

    public void setLogins(Collection<String> logins2) {
        if (logins2 == null) {
            this.logins = null;
        } else {
            this.logins = new ArrayList(logins2);
        }
    }

    public DescribeIdentityResult withLogins(String... logins2) {
        if (getLogins() == null) {
            this.logins = new ArrayList(logins2.length);
        }
        for (String value : logins2) {
            this.logins.add(value);
        }
        return this;
    }

    public DescribeIdentityResult withLogins(Collection<String> logins2) {
        setLogins(logins2);
        return this;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate2) {
        this.creationDate = creationDate2;
    }

    public DescribeIdentityResult withCreationDate(Date creationDate2) {
        this.creationDate = creationDate2;
        return this;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate2) {
        this.lastModifiedDate = lastModifiedDate2;
    }

    public DescribeIdentityResult withLastModifiedDate(Date lastModifiedDate2) {
        this.lastModifiedDate = lastModifiedDate2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityId() != null) {
            sb.append("IdentityId: " + getIdentityId() + ",");
        }
        if (getLogins() != null) {
            sb.append("Logins: " + getLogins() + ",");
        }
        if (getCreationDate() != null) {
            sb.append("CreationDate: " + getCreationDate() + ",");
        }
        if (getLastModifiedDate() != null) {
            sb.append("LastModifiedDate: " + getLastModifiedDate());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((1 * 31) + (getIdentityId() == null ? 0 : getIdentityId().hashCode())) * 31) + (getLogins() == null ? 0 : getLogins().hashCode())) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31;
        if (getLastModifiedDate() != null) {
            i = getLastModifiedDate().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DescribeIdentityResult)) {
            return false;
        }
        DescribeIdentityResult other = (DescribeIdentityResult) obj;
        if ((other.getIdentityId() == null) ^ (getIdentityId() == null)) {
            return false;
        }
        if (other.getIdentityId() != null && !other.getIdentityId().equals(getIdentityId())) {
            return false;
        }
        if ((other.getLogins() == null) ^ (getLogins() == null)) {
            return false;
        }
        if (other.getLogins() != null && !other.getLogins().equals(getLogins())) {
            return false;
        }
        if ((other.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (other.getCreationDate() != null && !other.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((other.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (other.getLastModifiedDate() == null || other.getLastModifiedDate().equals(getLastModifiedDate())) {
            return true;
        }
        return false;
    }
}
