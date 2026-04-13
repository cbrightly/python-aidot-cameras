package com.amazonaws.services.kinesisvideosignaling.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class IceServer implements Serializable {
    private String password;
    private Integer ttl;
    private List<String> uris = new ArrayList();
    private String username;

    public List<String> getUris() {
        return this.uris;
    }

    public void setUris(Collection<String> uris2) {
        if (uris2 == null) {
            this.uris = null;
        } else {
            this.uris = new ArrayList(uris2);
        }
    }

    public IceServer withUris(String... uris2) {
        if (getUris() == null) {
            this.uris = new ArrayList(uris2.length);
        }
        for (String value : uris2) {
            this.uris.add(value);
        }
        return this;
    }

    public IceServer withUris(Collection<String> uris2) {
        setUris(uris2);
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username2) {
        this.username = username2;
    }

    public IceServer withUsername(String username2) {
        this.username = username2;
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public IceServer withPassword(String password2) {
        this.password = password2;
        return this;
    }

    public Integer getTtl() {
        return this.ttl;
    }

    public void setTtl(Integer ttl2) {
        this.ttl = ttl2;
    }

    public IceServer withTtl(Integer ttl2) {
        this.ttl = ttl2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getUris() != null) {
            sb.append("Uris: " + getUris() + ",");
        }
        if (getUsername() != null) {
            sb.append("Username: " + getUsername() + ",");
        }
        if (getPassword() != null) {
            sb.append("Password: " + getPassword() + ",");
        }
        if (getTtl() != null) {
            sb.append("Ttl: " + getTtl());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((1 * 31) + (getUris() == null ? 0 : getUris().hashCode())) * 31) + (getUsername() == null ? 0 : getUsername().hashCode())) * 31) + (getPassword() == null ? 0 : getPassword().hashCode())) * 31;
        if (getTtl() != null) {
            i = getTtl().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IceServer)) {
            return false;
        }
        IceServer other = (IceServer) obj;
        if ((other.getUris() == null) ^ (getUris() == null)) {
            return false;
        }
        if (other.getUris() != null && !other.getUris().equals(getUris())) {
            return false;
        }
        if ((other.getUsername() == null) ^ (getUsername() == null)) {
            return false;
        }
        if (other.getUsername() != null && !other.getUsername().equals(getUsername())) {
            return false;
        }
        if ((other.getPassword() == null) ^ (getPassword() == null)) {
            return false;
        }
        if (other.getPassword() != null && !other.getPassword().equals(getPassword())) {
            return false;
        }
        if ((other.getTtl() == null) ^ (getTtl() == null)) {
            return false;
        }
        if (other.getTtl() == null || other.getTtl().equals(getTtl())) {
            return true;
        }
        return false;
    }
}
