package androidx.transition;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.util.Property;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

public class ViewUtils {
    static final Property<View, Rect> CLIP_BOUNDS = new Property<View, Rect>(Rect.class, "clipBounds") {
        public Rect get(View view) {
            return ViewCompat.getClipBounds(view);
        }

        public void set(View view, Rect clipBounds) {
            ViewCompat.setClipBounds(view, clipBounds);
        }
    };
    private static final ViewUtilsBase IMPL;
    private static final String TAG = "ViewUtils";
    static final Property<View, Float> TRANSITION_ALPHA = new Property<View, Float>(Float.class, "translationAlpha") {
        public Float get(View view) {
            return Float.valueOf(ViewUtils.getTransitionAlpha(view));
        }

        public void set(View view, Float alpha) {
            ViewUtils.setTransitionAlpha(view, alpha.floatValue());
        }
    };

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 29) {
            IMPL = new ViewUtilsApi29();
        } else if (i >= 23) {
            IMPL = new ViewUtilsApi23();
        } else if (i >= 22) {
            IMPL = new ViewUtilsApi22();
        } else if (i >= 21) {
            IMPL = new ViewUtilsApi21();
        } else if (i >= 19) {
            IMPL = new ViewUtilsApi19();
        } else {
            IMPL = new ViewUtilsBase();
        }
    }

    static ViewOverlayImpl getOverlay(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new ViewOverlayApi18(view);
        }
        return ViewOverlayApi14.createFrom(view);
    }

    static WindowIdImpl getWindowId(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= 18) {
            return new WindowIdApi18(view);
        }
        return new WindowIdApi14(view.getWindowToken());
    }

    static void setTransitionAlpha(@NonNull View view, float alpha) {
        IMPL.setTransitionAlpha(view, alpha);
    }

    static float getTransitionAlpha(@NonNull View view) {
        return IMPL.getTransitionAlpha(view);
    }

    static void saveNonTransitionAlpha(@NonNull View view) {
        IMPL.saveNonTransitionAlpha(view);
    }

    static void clearNonTransitionAlpha(@NonNull View view) {
        IMPL.clearNonTransitionAlpha(view);
    }

    static void setTransitionVisibility(@NonNull View view, int visibility) {
        IMPL.setTransitionVisibility(view, visibility);
    }

    static void transformMatrixToGlobal(@NonNull View view, @NonNull Matrix matrix) {
        IMPL.transformMatrixToGlobal(view, matrix);
    }

    static void transformMatrixToLocal(@NonNull View view, @NonNull Matrix matrix) {
        IMPL.transformMatrixToLocal(view, matrix);
    }

    static void setAnimationMatrix(@NonNull View v, @Nullable Matrix m) {
        IMPL.setAnimationMatrix(v, m);
    }

    static void setLeftTopRightBottom(@NonNull View v, int left, int top, int right, int bottom) {
        IMPL.setLeftTopRightBottom(v, left, top, right, bottom);
    }

    private ViewUtils() {
    }
}
