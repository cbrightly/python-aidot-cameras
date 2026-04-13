package androidx.camera.view;

import android.content.Context;
import android.view.OrientationEventListener;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public abstract class SensorRotationListener extends OrientationEventListener {
    public static final int INVALID_SURFACE_ROTATION = -1;
    private int mRotation = -1;

    public abstract void onRotationChanged(int i);

    public SensorRotationListener(@NonNull Context context) {
        super(context);
    }

    public void onOrientationChanged(int orientation) {
        int newRotation;
        if (orientation != -1) {
            if (orientation >= 315 || orientation < 45) {
                newRotation = 0;
            } else if (orientation >= 225) {
                newRotation = 1;
            } else if (orientation >= 135) {
                newRotation = 2;
            } else {
                newRotation = 3;
            }
            if (this.mRotation != newRotation) {
                this.mRotation = newRotation;
                onRotationChanged(newRotation);
            }
        }
    }
}
