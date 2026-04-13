package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class ViewUtils {
    private ViewUtils() {
    }

    @KeepForSdk
    @Nullable
    public static String getXmlAttributeString(@NonNull String namespace, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs, boolean allowResources, boolean required, @NonNull String logTag) {
        String str;
        if (attrs == null) {
            str = null;
        } else {
            str = attrs.getAttributeValue(namespace, name);
        }
        if (str != null && str.startsWith("@string/") && allowResources) {
            String substring = str.substring(8);
            String packageName = context.getPackageName();
            TypedValue typedValue = new TypedValue();
            try {
                Resources resources = context.getResources();
                resources.getValue(packageName + ":string/" + substring, typedValue, true);
            } catch (Resources.NotFoundException e) {
                Log.w(logTag, "Could not find resource for " + name + ": " + str);
            }
            CharSequence charSequence = typedValue.string;
            if (charSequence != null) {
                str = charSequence.toString();
            } else {
                String obj = typedValue.toString();
                Log.w(logTag, "Resource " + name + " was not a string: " + obj);
            }
        }
        if (required && str == null) {
            Log.w(logTag, "Required XML attribute \"" + name + "\" missing");
        }
        return str;
    }
}
