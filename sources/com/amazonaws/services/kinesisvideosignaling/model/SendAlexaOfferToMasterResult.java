package com.amazonaws.services.kinesisvideosignaling.model;

import java.io.Serializable;

public class SendAlexaOfferToMasterResult implements Serializable {
    private String answer;

    public String getAnswer() {
        return this.answer;
    }

    public void setAnswer(String answer2) {
        this.answer = answer2;
    }

    public SendAlexaOfferToMasterResult withAnswer(String answer2) {
        this.answer = answer2;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAnswer() != null) {
            sb.append("Answer: " + getAnswer());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (1 * 31) + (getAnswer() == null ? 0 : getAnswer().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SendAlexaOfferToMasterResult)) {
            return false;
        }
        SendAlexaOfferToMasterResult other = (SendAlexaOfferToMasterResult) obj;
        if ((other.getAnswer() == null) ^ (getAnswer() == null)) {
            return false;
        }
        if (other.getAnswer() == null || other.getAnswer().equals(getAnswer())) {
            return true;
        }
        return false;
    }
}
