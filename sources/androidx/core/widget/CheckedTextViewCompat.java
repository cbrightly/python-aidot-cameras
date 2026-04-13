package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.widget.CheckedTextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Field;

public final class CheckedTextViewCompat {
    private static final String TAG = "CheckedTextViewCompat";

    private CheckedTextViewCompat() {
    }

    public static void setCheckMarkTintList(@NonNull CheckedTextView textView, @Nullable ColorStateList tint) {
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.setCheckMarkTintList(textView, tint);
        } else if (textView instanceof TintableCheckedTextView) {
            ((TintableCheckedTextView) textView).setSupportCheckMarkTintList(tint);
        }
    }

    @Nullable
    public static ColorStateList getCheckMarkTintList(@NonNull CheckedTextView textView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getCheckMarkTintList(textView);
        }
        if (textView instanceof TintableCheckedTextView) {
            return ((TintableCheckedTextView) textView).getSupportCheckMarkTintList();
        }
        return null;
    }

    public static void setCheckMarkTintMode(@NonNull CheckedTextView textView, @Nullable PorterDuff.Mode tintMode) {
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.setCheckMarkTintMode(textView, tintMode);
        } else if (textView instanceof TintableCheckedTextView) {
            ((TintableCheckedTextView) textView).setSupportCheckMarkTintMode(tintMode);
        }
    }

    @Nullable
    public static PorterDuff.Mode getCheckMarkTintMode(@NonNull CheckedTextView textView) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getCheckMarkTintMode(textView);
        }
        if (textView instanceof TintableCheckedTextView) {
            return ((TintableCheckedTextView) textView).getSupportCheckMarkTintMode();
        }
        return null;
    }

    @Nullable
    public static Drawable getCheckMarkDrawable(@NonNull CheckedTextView textView) {
        if (Build.VERSION.SDK_INT >= 16) {
            return Api16Impl.getCheckMarkDrawable(textView);
        }
        return Api14Impl.getCheckMarkDrawable(textView);
    }

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        static void setCheckMarkTintList(@NonNull CheckedTextView textView, @Nullable ColorStateList tint) {
            textView.setCheckMarkTintList(tint);
        }

        @Nullable
        static ColorStateList getCheckMarkTintList(@NonNull CheckedTextView textView) {
            return textView.getCheckMarkTintList();
        }

        static void setCheckMarkTintMode(@NonNull CheckedTextView textView, @Nullable PorterDuff.Mode tintMode) {
            textView.setCheckMarkTintMode(tintMode);
        }

        @Nullable
        static PorterDuff.Mode getCheckMarkTintMode(@NonNull CheckedTextView textView) {
            return textView.getCheckMarkTintMode();
        }
    }

    @RequiresApi(16)
    public static class Api16Impl {
        private Api16Impl() {
        }

        @Nullable
        static Drawable getCheckMarkDrawable(@NonNull CheckedTextView textView) {
            return textView.getCheckMarkDrawable();
        }
    }

    public static class Api14Impl {
        private static Field sCheckMarkDrawableField;
        private static boolean sResolved;

        private Api14Impl() {
        }

        @Nullable
        static Drawable getCheckMarkDrawable(@NonNull CheckedTextView textView) {
            if (!sResolved) {
                try {
                    Field declaredField = CheckedTextView.class.getDeclaredField("mCheckMarkDrawable");
                    sCheckMarkDrawableField = declaredField;
                    declaredField.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    Log.i(CheckedTextViewCompat.TAG, "Failed to retrieve mCheckMarkDrawable field", e);
                }
                sResolved = true;
            }
            Field field = sCheckMarkDrawableField;
            if (field != null) {
                try {
                    return (Drawable) field.get(textView);
                } catch (IllegalAccessException e2) {
                    Log.i(CheckedTextViewCompat.TAG, "Failed to get check mark drawable via reflection", e2);
                    sCheckMarkDrawableField = null;
                }
            }
            return null;
        }
    }
}
