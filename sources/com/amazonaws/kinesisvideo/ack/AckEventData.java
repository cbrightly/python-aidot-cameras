package com.amazonaws.kinesisvideo.ack;

public class AckEventData {
    private int errorCode;
    private long fragmentTimecode;
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode2) {
        this.errorCode = errorCode2;
    }

    public long getFragmentTimecode() {
        return this.fragmentTimecode;
    }

    public void setFragmentTimecode(long fragmentTimecode2) {
        this.fragmentTimecode = fragmentTimecode2;
    }
}
