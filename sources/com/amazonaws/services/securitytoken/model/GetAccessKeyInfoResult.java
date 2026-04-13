package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class GetAccessKeyInfoResult implements Serializable {
    private String account;

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account2) {
        this.account = account2;
    }

    public GetAccessKeyInfoResult withAccount(String account2) {
        this.account = account2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAccount() != null) {
            sb.append("Account: " + getAccount());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getAccount() == null ? 0 : getAccount().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetAccessKeyInfoResult)) {
            return false;
        }
        GetAccessKeyInfoResult other = (GetAccessKeyInfoResult) obj;
        if ((other.getAccount() == null) ^ (getAccount() == null)) {
            return false;
        }
        if (other.getAccount() == null || other.getAccount().equals(getAccount())) {
            return true;
        }
        return false;
    }
}
