package com.bumptech.glide.load.resource.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

/* compiled from: DrawableDecoderCompat */
public final class a {
    private static volatile boolean a = true;

    public static Drawable b(Context ourContext, Context targetContext, @DrawableRes int id) {
        return c(ourContext, targetContext, id, (Resources.Theme) null);
    }

    public static Drawable a(Context ourContext, @DrawableRes int id, @Nullable Resources.Theme theme) {
        return c(ourContext, ourContext, id, theme);
    }

    private static Drawable c(Context ourContext, Context targetContext, @DrawableRes int id, @Nullable Resources.Theme theme) {
        try {
            if (a) {
                return e(targetContext, id, theme);
            }
        } catch (NoClassDefFoundError e) {
            a = false;
        } catch (IllegalStateException e2) {
            if (!ourContext.getPackageName().equals(targetContext.getPackageName())) {
                return ContextCompat.getDrawable(targetContext, id);
            }
            throw e2;
        } catch (Resources.NotFoundException e3) {
        }
        return d(targetContext, id, theme != null ? theme : targetContext.getTheme());
    }

    private static Drawable e(Context context, @DrawableRes int id, @Nullable Resources.Theme theme) {
        return AppCompatResources.getDrawable(theme != null ? new ContextThemeWrapper(context, theme) : context, id);
    }

    private static Drawable d(Context context, @DrawableRes int id, @Nullable Resources.Theme theme) {
        return ResourcesCompat.getDrawable(context.getResources(), id, theme);
    }
}
