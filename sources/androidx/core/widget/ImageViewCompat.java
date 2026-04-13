package androidx.core.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageViewCompat {
    @Nullable
    public static ColorStateList getImageTintList(@NonNull ImageView view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getImageTintList();
        }
        if (view instanceof TintableImageSourceView) {
            return ((TintableImageSourceView) view).getSupportImageTintList();
        }
        return null;
    }

    public static void setImageTintList(@NonNull ImageView view, @Nullable ColorStateList tintList) {
        Drawable imageViewDrawable;
        int i = Build.VERSION.SDK_INT;
        if (i >= 21) {
            view.setImageTintList(tintList);
            if (i == 21 && (imageViewDrawable = view.getDrawable()) != null && view.getImageTintList() != null) {
                if (imageViewDrawable.isStateful()) {
                    imageViewDrawable.setState(view.getDrawableState());
                }
                view.setImageDrawable(imageViewDrawable);
            }
        } else if (view instanceof TintableImageSourceView) {
            ((TintableImageSourceView) view).setSupportImageTintList(tintList);
        }
    }

    @Nullable
    public static PorterDuff.Mode getImageTintMode(@NonNull ImageView view) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getImageTintMode();
        }
        if (view instanceof TintableImageSourceView) {
            return ((TintableImageSourceView) view).getSupportImageTintMode();
        }
        return null;
    }

    public static void setImageTintMode(@NonNull ImageView view, @Nullable PorterDuff.Mode mode) {
        Drawable imageViewDrawable;
        int i = Build.VERSION.SDK_INT;
        if (i >= 21) {
            view.setImageTintMode(mode);
            if (i == 21 && (imageViewDrawable = view.getDrawable()) != null && view.getImageTintList() != null) {
                if (imageViewDrawable.isStateful()) {
                    imageViewDrawable.setState(view.getDrawableState());
                }
                view.setImageDrawable(imageViewDrawable);
            }
        } else if (view instanceof TintableImageSourceView) {
            ((TintableImageSourceView) view).setSupportImageTintMode(mode);
        }
    }

    private ImageViewCompat() {
    }
}
