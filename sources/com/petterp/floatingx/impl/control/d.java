package com.petterp.floatingx.impl.control;

import android.view.ViewGroup;
import androidx.core.view.ViewCompat;
import com.petterp.floatingx.assist.helper.b;
import com.petterp.floatingx.view.FxManagerView;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FxScopeControl.kt */
public final class d<T> extends c implements com.petterp.floatingx.listener.control.d<T> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public d(@NotNull b helper) {
        super(helper);
        k.e(helper, "helper");
    }

    public void show() {
        FxManagerView managerView;
        ViewGroup o;
        if (!f() && (managerView = q()) != null) {
            y(true);
            if (!ViewCompat.isAttachedToWindow(managerView) && (o = o()) != null) {
                o.addView(managerView);
            }
            x(managerView);
        }
    }
}
