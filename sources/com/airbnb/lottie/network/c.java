package com.airbnb.lottie.network;

import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: FileExtension */
public enum c {
    JSON(".json"),
    ZIP(".zip");
    
    public final String extension;

    private c(String extension2) {
        this.extension = extension2;
    }

    public String tempExtension() {
        return ".temp" + this.extension;
    }

    public String toString() {
        return this.extension;
    }
}
