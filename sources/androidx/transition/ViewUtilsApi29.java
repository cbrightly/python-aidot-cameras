package androidx.transition;

import android.graphics.Matrix;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

@RequiresApi(29)
public class ViewUtilsApi29 extends ViewUtilsApi23 {
    ViewUtilsApi29() {
    }

    public void setTransitionAlpha(@NonNull View view, float alpha) {
        view.setTransitionAlpha(alpha);
    }

    public float getTransitionAlpha(@NonNull View view) {
        return view.getTransitionAlpha();
    }

    public void setTransitionVisibility(@NonNull View view, int visibility) {
        view.setTransitionVisibility(visibility);
    }

    public void setLeftTopRightBottom(@NonNull View v, int left, int top, int right, int bottom) {
        v.setLeftTopRightBottom(left, top, right, bottom);
    }

    public void transformMatrixToGlobal(@NonNull View view, @NonNull Matrix matrix) {
        view.transformMatrixToGlobal(matrix);
    }

    public void transformMatrixToLocal(@NonNull View view, @NonNull Matrix matrix) {
        view.transformMatrixToLocal(matrix);
    }

    public void setAnimationMatrix(@NonNull View view, @Nullable Matrix matrix) {
        view.setAnimationMatrix(matrix);
    }
}
