package com.amazonaws.services.securitytoken.model;

import java.io.Serializable;

public class DecodeAuthorizationMessageResult implements Serializable {
    private String decodedMessage;

    public String getDecodedMessage() {
        return this.decodedMessage;
    }

    public void setDecodedMessage(String decodedMessage2) {
        this.decodedMessage = decodedMessage2;
    }

    public DecodeAuthorizationMessageResult withDecodedMessage(String decodedMessage2) {
        this.decodedMessage = decodedMessage2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getDecodedMessage() != null) {
            sb.append("DecodedMessage: " + getDecodedMessage());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getDecodedMessage() == null ? 0 : getDecodedMessage().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DecodeAuthorizationMessageResult)) {
            return false;
        }
        DecodeAuthorizationMessageResult other = (DecodeAuthorizationMessageResult) obj;
        if ((other.getDecodedMessage() == null) ^ (getDecodedMessage() == null)) {
            return false;
        }
        if (other.getDecodedMessage() == null || other.getDecodedMessage().equals(getDecodedMessage())) {
            return true;
        }
        return false;
    }
}
