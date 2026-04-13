package androidx.transition;

import android.annotation.SuppressLint;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

@RequiresApi(19)
public class ViewUtilsApi19 extends ViewUtilsBase {
    private static boolean sTryHiddenTransitionAlpha = true;

    ViewUtilsApi19() {
    }

    @SuppressLint({"NewApi"})
    public void setTransitionAlpha(@NonNull View view, float alpha) {
        if (sTryHiddenTransitionAlpha) {
            try {
                view.setTransitionAlpha(alpha);
                return;
            } catch (NoSuchMethodError e) {
                sTryHiddenTransitionAlpha = false;
            }
        }
        view.setAlpha(alpha);
    }

    @SuppressLint({"NewApi"})
    public float getTransitionAlpha(@NonNull View view) {
        if (sTryHiddenTransitionAlpha) {
            try {
                return view.getTransitionAlpha();
            } catch (NoSuchMethodError e) {
                sTryHiddenTransitionAlpha = false;
            }
        }
        return view.getAlpha();
    }

    public void saveNonTransitionAlpha(@NonNull View view) {
    }

    public void clearNonTransitionAlpha(@NonNull View view) {
    }
}
