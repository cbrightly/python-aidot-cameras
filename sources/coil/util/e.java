package coil.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Xml;
import androidx.annotation.DrawableRes;
import androidx.annotation.XmlRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: Contexts.kt */
public final class e {
    @NotNull
    public static final Drawable a(@NotNull Context $this$getDrawableCompat, @DrawableRes int resId) {
        k.e($this$getDrawableCompat, "<this>");
        Drawable drawable = AppCompatResources.getDrawable($this$getDrawableCompat, resId);
        if (drawable != null) {
            return drawable;
        }
        throw new IllegalStateException(k.l("Invalid resource ID: ", Integer.valueOf(resId)).toString());
    }

    @NotNull
    public static final Drawable b(@NotNull Resources $this$getDrawableCompat, @DrawableRes int resId, @Nullable Resources.Theme theme) {
        k.e($this$getDrawableCompat, "<this>");
        Drawable drawable = ResourcesCompat.getDrawable($this$getDrawableCompat, resId, theme);
        if (drawable != null) {
            return drawable;
        }
        throw new IllegalStateException(k.l("Invalid resource ID: ", Integer.valueOf(resId)).toString());
    }

    @NotNull
    @SuppressLint({"ResourceType"})
    public static final Drawable d(@NotNull Context $this$getXmlDrawableCompat, @NotNull Resources resources, @XmlRes int resId) {
        k.e($this$getXmlDrawableCompat, "<this>");
        k.e(resources, "resources");
        XmlResourceParser parser = resources.getXml(resId);
        k.d(parser, "resources.getXml(resId)");
        int type = parser.next();
        while (type != 2 && type != 1) {
            type = parser.next();
        }
        if (type == 2) {
            if (Build.VERSION.SDK_INT < 24) {
                String name = parser.getName();
                if (k.a(name, "vector")) {
                    VectorDrawableCompat createFromXmlInner = VectorDrawableCompat.createFromXmlInner(resources, parser, Xml.asAttributeSet(parser), $this$getXmlDrawableCompat.getTheme());
                    k.d(createFromXmlInner, "createFromXmlInner(resources, parser, attrs, theme)");
                    return createFromXmlInner;
                } else if (k.a(name, "animated-vector")) {
                    AnimatedVectorDrawableCompat createFromXmlInner2 = AnimatedVectorDrawableCompat.createFromXmlInner($this$getXmlDrawableCompat, resources, parser, Xml.asAttributeSet(parser), $this$getXmlDrawableCompat.getTheme());
                    k.d(createFromXmlInner2, "createFromXmlInner(this, resources, parser, attrs, theme)");
                    return createFromXmlInner2;
                }
            }
            return b(resources, resId, $this$getXmlDrawableCompat.getTheme());
        }
        throw new XmlPullParserException("No start tag found.");
    }

    @Nullable
    public static final Lifecycle c(@Nullable Context $this$getLifecycle) {
        Context context = $this$getLifecycle;
        while (!(context instanceof LifecycleOwner)) {
            if (!(context instanceof ContextWrapper)) {
                return null;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return ((LifecycleOwner) context).getLifecycle();
    }
}
