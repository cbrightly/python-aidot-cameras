package com.airbnb.lottie.manager;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.Nullable;
import com.airbnb.lottie.model.c;
import com.airbnb.lottie.model.i;
import com.airbnb.lottie.utils.d;
import com.airbnb.lottie.z;
import java.util.HashMap;
import java.util.Map;

/* compiled from: FontAssetManager */
public class a {
    private final i<String> a = new i<>();
    private final Map<i<String>, Typeface> b = new HashMap();
    private final Map<String, Typeface> c = new HashMap();
    private final AssetManager d;
    @Nullable
    private z e;
    private String f = ".ttf";

    public a(Drawable.Callback callback, @Nullable z delegate) {
        if (!(callback instanceof View)) {
            d.c("LottieDrawable must be inside of a view for images to work.");
            this.d = null;
            return;
        }
        this.d = ((View) callback).getContext().getAssets();
    }

    public void d(@Nullable z assetDelegate) {
    }

    public void c(String defaultFontFileExtension) {
        this.f = defaultFontFileExtension;
    }

    public Typeface b(c font) {
        this.a.b(font.a(), font.c());
        Typeface typeface = this.b.get(this.a);
        if (typeface != null) {
            return typeface;
        }
        Typeface typeface2 = e(a(font), font.c());
        this.b.put(this.a, typeface2);
        return typeface2;
    }

    private Typeface a(c font) {
        String fontFamily = font.a();
        Typeface defaultTypeface = this.c.get(fontFamily);
        if (defaultTypeface != null) {
            return defaultTypeface;
        }
        Typeface typeface = null;
        String fontStyle = font.c();
        String fontName = font.b();
        z zVar = this.e;
        if (zVar != null) {
            zVar.a(fontFamily, fontStyle, fontName);
            throw null;
        } else if (zVar != null && 0 == 0) {
            zVar.b(fontFamily, fontStyle, fontName);
            throw null;
        } else if (font.d() != null) {
            return font.d();
        } else {
            if (0 == 0) {
                typeface = Typeface.createFromAsset(this.d, "fonts/" + fontFamily + this.f);
            }
            this.c.put(fontFamily, typeface);
            return typeface;
        }
    }

    private Typeface e(Typeface typeface, String style) {
        int styleInt = 0;
        boolean containsItalic = style.contains("Italic");
        boolean containsBold = style.contains("Bold");
        if (containsItalic && containsBold) {
            styleInt = 3;
        } else if (containsItalic) {
            styleInt = 2;
        } else if (containsBold) {
            styleInt = 1;
        }
        if (typeface.getStyle() == styleInt) {
            return typeface;
        }
        return Typeface.create(typeface, styleInt);
    }
}
