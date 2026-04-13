package com.airbnb.lottie.model;

/* compiled from: Marker */
public class h {
    private final String a;
    public final float b;
    public final float c;

    public h(String name, float startFrame, float durationFrames) {
        this.a = name;
        this.c = durationFrames;
        this.b = startFrame;
    }

    public boolean a(String name) {
        if (this.a.equalsIgnoreCase(name)) {
            return true;
        }
        if (this.a.endsWith("\r")) {
            String str = this.a;
            if (str.substring(0, str.length() - 1).equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
