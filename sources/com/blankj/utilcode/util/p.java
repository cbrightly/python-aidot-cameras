package com.blankj.utilcode.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import androidx.annotation.NonNull;
import java.lang.reflect.Field;
import java.util.Locale;

/* compiled from: LanguageUtils */
public class p {
    static void a(@NonNull Activity activity) {
        if (activity != null) {
            String spLocale = h0.u().c("KEY_LOCALE");
            if (!TextUtils.isEmpty(spLocale)) {
                if ("VALUE_FOLLOW_SYSTEM".equals(spLocale)) {
                    Locale sysLocale = Resources.getSystem().getConfiguration().locale;
                    d(f0.a(), sysLocale);
                    d(activity, sysLocale);
                    return;
                }
                Locale settingLocale = c(spLocale);
                if (settingLocale != null) {
                    d(f0.a(), settingLocale);
                    d(activity, settingLocale);
                    return;
                }
                return;
            }
            return;
        }
        throw new NullPointerException("Argument 'activity' of type Activity (#0 out of 1, zero-based) is marked by @androidx.annotation.NonNull but got null for it");
    }

    private static Locale c(String str) {
        String[] language_country = str.split("\\$");
        if (language_country.length == 2) {
            return new Locale(language_country[0], language_country[1]);
        }
        Log.e("LanguageUtils", "The string of " + str + " is not in the correct format.");
        return null;
    }

    private static void d(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        if (!b(config.locale, locale)) {
            DisplayMetrics dm = resources.getDisplayMetrics();
            if (Build.VERSION.SDK_INT >= 17) {
                config.setLocale(locale);
                if (context instanceof Application) {
                    Context newContext = context.createConfigurationContext(config);
                    try {
                        Field mBaseField = ContextWrapper.class.getDeclaredField("mBase");
                        mBaseField.setAccessible(true);
                        mBaseField.set(context, newContext);
                    } catch (Exception e) {
                    }
                }
            } else {
                config.locale = locale;
            }
            resources.updateConfiguration(config, dm);
        }
    }

    private static boolean b(Locale l0, Locale l1) {
        return h0.g(l1.getLanguage(), l0.getLanguage()) && h0.g(l1.getCountry(), l0.getCountry());
    }
}
