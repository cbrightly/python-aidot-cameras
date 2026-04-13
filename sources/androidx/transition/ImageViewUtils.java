package androidx.transition;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import java.lang.reflect.Field;

public class ImageViewUtils {
    private static Field sDrawMatrixField;
    private static boolean sDrawMatrixFieldFetched;
    private static boolean sTryHiddenAnimateTransform = true;

    static void animateTransform(@NonNull ImageView view, @Nullable Matrix matrix) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 29) {
            view.animateTransform(matrix);
        } else if (matrix == null) {
            Drawable drawable = view.getDrawable();
            if (drawable != null) {
                drawable.setBounds(0, 0, (view.getWidth() - view.getPaddingLeft()) - view.getPaddingRight(), (view.getHeight() - view.getPaddingTop()) - view.getPaddingBottom());
                view.invalidate();
            }
        } else if (i >= 21) {
            hiddenAnimateTransform(view, matrix);
        } else {
            Drawable drawable2 = view.getDrawable();
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                Matrix drawMatrix = null;
                fetchDrawMatrixField();
                Field field = sDrawMatrixField;
                if (field != null) {
                    try {
                        drawMatrix = (Matrix) field.get(view);
                        if (drawMatrix == null) {
                            drawMatrix = new Matrix();
                            sDrawMatrixField.set(view, drawMatrix);
                        }
                    } catch (IllegalAccessException e) {
                    }
                }
                if (drawMatrix != null) {
                    drawMatrix.set(matrix);
                }
                view.invalidate();
            }
        }
    }

    @RequiresApi(21)
    @SuppressLint({"NewApi"})
    private static void hiddenAnimateTransform(@NonNull ImageView view, @Nullable Matrix matrix) {
        if (sTryHiddenAnimateTransform) {
            try {
                view.animateTransform(matrix);
            } catch (NoSuchMethodError e) {
                sTryHiddenAnimateTransform = false;
            }
        }
    }

    private static void fetchDrawMatrixField() {
        if (!sDrawMatrixFieldFetched) {
            try {
                Field declaredField = ImageView.class.getDeclaredField("mDrawMatrix");
                sDrawMatrixField = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException e) {
            }
            sDrawMatrixFieldFetched = true;
        }
    }

    private ImageViewUtils() {
    }
}
