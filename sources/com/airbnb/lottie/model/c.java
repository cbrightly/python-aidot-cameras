package com.airbnb.lottie.model;

import android.graphics.Typeface;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: Font */
public class c {
    private final String a;
    private final String b;
    private final String c;
    private final float d;
    @Nullable
    private Typeface e;

    public c(String family, String name, String style, float ascent) {
        this.a = family;
        this.b = name;
        this.c = style;
        this.d = ascent;
    }

    public String a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    @Nullable
    public Typeface d() {
        return this.e;
    }

    public void e(@Nullable Typeface typeface) {
        this.e = typeface;
    }
}
