package com.petterp.floatingx.listener.control;

import android.view.View;
import com.petterp.floatingx.view.FxManagerView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: IFxControl.kt */
public interface c {
    @NotNull
    b a();

    @Nullable
    FxManagerView c();

    void e(float f, float f2);

    boolean f();

    void hide();

    void setClickListener(@NotNull View.OnClickListener onClickListener);
}
