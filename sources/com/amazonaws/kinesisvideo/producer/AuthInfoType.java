package com.amazonaws.kinesisvideo.producer;

public enum AuthInfoType {
    UNDEFINED(0),
    CERT(1),
    SECURITY_TOKEN(2),
    NONE(3);
    
    private final int mType;

    private AuthInfoType(int type) {
        this.mType = type;
    }

    public int getIntType() {
        return this.mType;
    }
}
