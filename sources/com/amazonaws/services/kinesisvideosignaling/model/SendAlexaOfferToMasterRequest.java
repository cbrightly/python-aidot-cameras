package com.amazonaws.services.kinesisvideosignaling.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;

public class SendAlexaOfferToMasterRequest extends AmazonWebServiceRequest implements Serializable {
    private String channelARN;
    private String messagePayload;
    private String senderClientId;

    public String getChannelARN() {
        return this.channelARN;
    }

    public void setChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
    }

    public SendAlexaOfferToMasterRequest withChannelARN(String channelARN2) {
        this.channelARN = channelARN2;
        return this;
    }

    public String getSenderClientId() {
        return this.senderClientId;
    }

    public void setSenderClientId(String senderClientId2) {
        this.senderClientId = senderClientId2;
    }

    public SendAlexaOfferToMasterRequest withSenderClientId(String senderClientId2) {
        this.senderClientId = senderClientId2;
        return this;
    }

    public String getMessagePayload() {
        return this.messagePayload;
    }

    public void setMessagePayload(String messagePayload2) {
        this.messagePayload = messagePayload2;
    }

    public SendAlexaOfferToMasterRequest withMessagePayload(String messagePayload2) {
        this.messagePayload = messagePayload2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getChannelARN() != null) {
            sb.append("ChannelARN: " + getChannelARN() + ",");
        }
        if (getSenderClientId() != null) {
            sb.append("SenderClientId: " + getSenderClientId() + ",");
        }
        if (getMessagePayload() != null) {
            sb.append("MessagePayload: " + getMessagePayload());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((1 * 31) + (getChannelARN() == null ? 0 : getChannelARN().hashCode())) * 31) + (getSenderClientId() == null ? 0 : getSenderClientId().hashCode())) * 31;
        if (getMessagePayload() != null) {
            i = getMessagePayload().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SendAlexaOfferToMasterRequest)) {
            return false;
        }
        SendAlexaOfferToMasterRequest other = (SendAlexaOfferToMasterRequest) obj;
        if ((other.getChannelARN() == null) ^ (getChannelARN() == null)) {
            return false;
        }
        if (other.getChannelARN() != null && !other.getChannelARN().equals(getChannelARN())) {
            return false;
        }
        if ((other.getSenderClientId() == null) ^ (getSenderClientId() == null)) {
            return false;
        }
        if (other.getSenderClientId() != null && !other.getSenderClientId().equals(getSenderClientId())) {
            return false;
        }
        if ((other.getMessagePayload() == null) ^ (getMessagePayload() == null)) {
            return false;
        }
        if (other.getMessagePayload() == null || other.getMessagePayload().equals(getMessagePayload())) {
            return true;
        }
        return false;
    }
}
