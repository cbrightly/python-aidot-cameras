package com.amazonaws.services.securitytoken.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class DecodeAuthorizationMessageRequest extends AmazonWebServiceRequest implements Serializable {
    private String encodedMessage;

    public String getEncodedMessage() {
        return this.encodedMessage;
    }

    public void setEncodedMessage(String encodedMessage2) {
        this.encodedMessage = encodedMessage2;
    }

    public DecodeAuthorizationMessageRequest withEncodedMessage(String encodedMessage2) {
        this.encodedMessage = encodedMessage2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getEncodedMessage() != null) {
            sb.append("EncodedMessage: " + getEncodedMessage());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getEncodedMessage() == null ? 0 : getEncodedMessage().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof DecodeAuthorizationMessageRequest)) {
            return false;
        }
        DecodeAuthorizationMessageRequest other = (DecodeAuthorizationMessageRequest) obj;
        if ((other.getEncodedMessage() == null) ^ (getEncodedMessage() == null)) {
            return false;
        }
        if (other.getEncodedMessage() == null || other.getEncodedMessage().equals(getEncodedMessage())) {
            return true;
        }
        return false;
    }
}
