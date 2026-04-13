package com.petterp.floatingx.view;

import android.view.View;
import androidx.annotation.Keep;
import com.petterp.floatingx.assist.helper.b;
import com.petterp.floatingx.util.c;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FxClickHelper.kt */
public final class FxClickHelper {
    private float a;
    private float b;
    private boolean c;
    private boolean d = true;
    private long e;
    private b f;

    public final void b(@NotNull b helper) {
        k.e(helper, "helper");
        g();
        this.f = helper;
    }

    public final void c(float x, float y) {
        b bVar = this.f;
        if (bVar == null) {
            k.t("helper");
            throw null;
        } else if (!bVar.s) {
        } else {
            if (bVar == null) {
                k.t("helper");
                throw null;
            } else if (bVar.x != null) {
                this.a = x;
                this.b = y;
                this.c = true;
                this.e = System.currentTimeMillis();
            }
        }
    }

    public final void a(float x, float y) {
        if (this.c) {
            this.c = Math.abs(x - this.a) < 2.0f && Math.abs(y - this.b) < 2.0f;
        }
    }

    @Keep
    public final void performClick(@NotNull FxManagerView view) {
        k.e(view, "view");
        if (d()) {
            b bVar = this.f;
            if (bVar != null) {
                View.OnClickListener onClickListener = bVar.x;
                if (onClickListener != null) {
                    onClickListener.onClick(view);
                }
                b bVar2 = this.f;
                if (bVar2 != null) {
                    long j = bVar2.d;
                    if (j > 0) {
                        this.d = false;
                        a aVar = new a(this);
                        if (bVar2 != null) {
                            view.postDelayed(aVar, j);
                        } else {
                            k.t("helper");
                            throw null;
                        }
                    }
                    b bVar3 = this.f;
                    if (bVar3 != null) {
                        c cVar = bVar3.y;
                        if (cVar != null) {
                            cVar.b("fxView -> click");
                        }
                        g();
                        return;
                    }
                    k.t("helper");
                    throw null;
                }
                k.t("helper");
                throw null;
            }
            k.t("helper");
            throw null;
        }
    }

    /* access modifiers changed from: private */
    public static final void f(FxClickHelper this$0) {
        k.e(this$0, "this$0");
        this$0.d = true;
    }

    private final void g() {
        this.a = 0.0f;
        this.b = 0.0f;
        this.c = false;
        this.e = 0;
    }

    private final boolean d() {
        if (this.c && this.d) {
            b bVar = this.f;
            if (bVar == null) {
                k.t("helper");
                throw null;
            } else if (bVar.s) {
                if (bVar != null) {
                    return bVar.x != null && System.currentTimeMillis() - this.e < 150;
                }
                k.t("helper");
                throw null;
            }
        }
    }
}
