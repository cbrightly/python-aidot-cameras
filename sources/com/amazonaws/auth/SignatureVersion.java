package com.amazonaws.auth;

import androidx.exifinterface.media.ExifInterface;

public enum SignatureVersion {
    V1("1"),
    V2(ExifInterface.GPS_MEASUREMENT_2D);
    
    private String value;

    private SignatureVersion(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }
}
