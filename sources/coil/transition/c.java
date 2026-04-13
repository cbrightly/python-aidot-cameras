package coil.transition;

import android.graphics.drawable.Drawable;
import android.view.View;
import coil.target.b;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TransitionTarget.kt */
public interface c extends b {
    @Nullable
    Drawable e();

    @NotNull
    View getView();
}
