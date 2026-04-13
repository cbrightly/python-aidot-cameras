package com.airbnb.lottie.model;

import androidx.annotation.RestrictTo;
import com.airbnb.lottie.model.content.p;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: FontCharacter */
public class d {
    private final List<p> a;
    private final char b;
    private final double c;
    private final double d;
    private final String e;
    private final String f;

    public static int c(char character, String fontFamily, String style) {
        return (((character * 31) + fontFamily.hashCode()) * 31) + style.hashCode();
    }

    public d(List<p> shapes, char character, double size, double width, String style, String fontFamily) {
        this.a = shapes;
        this.b = character;
        this.c = size;
        this.d = width;
        this.e = style;
        this.f = fontFamily;
    }

    public List<p> a() {
        return this.a;
    }

    public double b() {
        return this.d;
    }

    public int hashCode() {
        return c(this.b, this.f, this.e);
    }
}
