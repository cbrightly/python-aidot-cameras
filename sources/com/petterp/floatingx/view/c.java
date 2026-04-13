package com.petterp.floatingx.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.app.NotificationCompat;
import com.petterp.floatingx.assist.helper.b;
import com.petterp.floatingx.util.a;
import kotlin.jvm.internal.k;
import kotlin.n;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FxViewConfigHelper.kt */
public final class c {
    private float a;
    private float b;
    private float c;
    private float d;
    private b e;
    private int f;
    private float g;
    private float h;
    private float i;
    private float j;
    private int k = -1;

    public final float d() {
        return this.g;
    }

    public final float c() {
        return this.h;
    }

    public final float e() {
        return this.i;
    }

    public final void p(int i2) {
        this.k = i2;
    }

    public final void g(@NotNull Context context, @NotNull b helper) {
        k.e(context, "context");
        k.e(helper, "helper");
        this.e = helper;
        this.f = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public final float l(float x) {
        return com.petterp.floatingx.util.b.a(x, this.i, this.j);
    }

    public final float n(float y) {
        return com.petterp.floatingx.util.b.a(y, this.g, this.h);
    }

    public final float m(float x, @NotNull MotionEvent ev) {
        k.e(ev, "ev");
        return com.petterp.floatingx.util.b.a((ev.getX() + x) - this.a, this.i, this.j);
    }

    public final float o(float y, @NotNull MotionEvent ev) {
        k.e(ev, "ev");
        return com.petterp.floatingx.util.b.a((ev.getY() + y) - this.b, this.g, this.h);
    }

    public final boolean f() {
        return this.k != -1;
    }

    public final boolean i(@NotNull MotionEvent ev) {
        k.e(ev, "ev");
        if (this.k != -1 && com.petterp.floatingx.util.b.b(ev) == this.k) {
            return true;
        }
        return false;
    }

    public final void h(@NotNull MotionEvent ev) {
        k.e(ev, "ev");
        this.k = com.petterp.floatingx.util.b.b(ev);
        this.a = ev.getX();
        this.b = ev.getY();
        b bVar = this.e;
        if (bVar != null) {
            com.petterp.floatingx.util.c cVar = bVar.y;
            if (cVar != null) {
                cVar.b(k.l("fxView---newTouchDown:", Integer.valueOf(this.k)));
                return;
            }
            return;
        }
        k.t("helper");
        throw null;
    }

    public final boolean s(@NotNull ViewGroup view) {
        k.e(view, "view");
        ViewParent parent = view.getParent();
        ViewGroup parentGroup = parent instanceof ViewGroup ? (ViewGroup) parent : null;
        if (parentGroup == null) {
            return false;
        }
        return r(parentGroup.getWidth(), parentGroup.getHeight(), view);
    }

    public final boolean r(int parentW, int parentH, @NotNull ViewGroup view) {
        k.e(view, "view");
        float parentWidth = (float) (parentW - view.getWidth());
        float parentHeight = (float) (parentH - view.getHeight());
        if (this.d == parentHeight) {
            if (this.c == parentWidth) {
                return false;
            }
        }
        b bVar = this.e;
        if (bVar != null) {
            com.petterp.floatingx.util.c cVar = bVar.y;
            if (cVar != null) {
                cVar.b("fxView->updateContainerSize: oldW-(" + this.c + "),oldH-(" + this.d + "),newW-(" + parentWidth + "),newH-(" + parentHeight + ')');
            }
            this.c = parentWidth;
            this.d = parentHeight;
            q(false);
            return true;
        }
        k.t("helper");
        throw null;
    }

    public final boolean j(float x) {
        return x < this.c / ((float) 2);
    }

    public final boolean k(float y) {
        return y < this.d / ((float) 2);
    }

    public final boolean a(@NotNull MotionEvent event) {
        k.e(event, NotificationCompat.CATEGORY_EVENT);
        if (!i(event)) {
            return false;
        }
        if (Math.abs(event.getX() - this.a) >= ((float) this.f) || Math.abs(event.getY() - this.b) >= ((float) this.f)) {
            return true;
        }
        return false;
    }

    @Nullable
    public final n<Float, Float> b(float x, float y) {
        b bVar = this.e;
        if (bVar == null) {
            k.t("helper");
            throw null;
        } else if (bVar.n) {
            if (bVar == null) {
                k.t("helper");
                throw null;
            } else if (bVar.l == a.LEFT_OR_RIGHT) {
                return t.a(Float.valueOf(j(x) ? this.i : this.j), Float.valueOf(n(y)));
            } else {
                return t.a(Float.valueOf(l(x)), Float.valueOf(k(y) ? this.g : this.h));
            }
        } else if (bVar == null) {
            k.t("helper");
            throw null;
        } else if (bVar.o) {
            return t.a(Float.valueOf(l(x)), Float.valueOf(n(y)));
        } else {
            return null;
        }
    }

    public final void q(boolean isDownTouchInit) {
        float edgeOffset;
        float marginTop;
        float marginBto;
        float marginLef;
        b bVar = this.e;
        if (bVar == null) {
            k.t("helper");
            throw null;
        } else if (bVar.o) {
            float marginRig = 0.0f;
            if (isDownTouchInit) {
                edgeOffset = 0.0f;
            } else if (bVar != null) {
                edgeOffset = bVar.i;
            } else {
                k.t("helper");
                throw null;
            }
            if (isDownTouchInit) {
                marginTop = 0.0f;
            } else if (bVar != null) {
                marginTop = bVar.j.d() + edgeOffset;
            } else {
                k.t("helper");
                throw null;
            }
            if (isDownTouchInit) {
                marginBto = 0.0f;
            } else {
                b bVar2 = this.e;
                if (bVar2 != null) {
                    marginBto = bVar2.j.a() + edgeOffset;
                } else {
                    k.t("helper");
                    throw null;
                }
            }
            if (isDownTouchInit) {
                marginLef = 0.0f;
            } else {
                b bVar3 = this.e;
                if (bVar3 != null) {
                    marginLef = bVar3.j.b() + edgeOffset;
                } else {
                    k.t("helper");
                    throw null;
                }
            }
            if (!isDownTouchInit) {
                b bVar4 = this.e;
                if (bVar4 != null) {
                    marginRig = bVar4.j.c() + edgeOffset;
                } else {
                    k.t("helper");
                    throw null;
                }
            }
            this.i = marginLef;
            this.j = this.c - marginRig;
            b bVar5 = this.e;
            if (bVar5 != null) {
                this.g = ((float) bVar5.B) + marginTop;
                float f2 = this.d;
                if (bVar5 != null) {
                    this.h = (f2 - ((float) bVar5.A)) - marginBto;
                } else {
                    k.t("helper");
                    throw null;
                }
            } else {
                k.t("helper");
                throw null;
            }
        } else if (bVar != null) {
            this.i = bVar.j.b();
            float f3 = this.c;
            b bVar6 = this.e;
            if (bVar6 != null) {
                this.j = f3 - bVar6.j.c();
                b bVar7 = this.e;
                if (bVar7 != null) {
                    float f4 = (float) bVar7.B;
                    if (bVar7 != null) {
                        this.g = f4 + bVar7.j.d();
                        float f5 = this.d;
                        b bVar8 = this.e;
                        if (bVar8 != null) {
                            float f6 = f5 - ((float) bVar8.A);
                            if (bVar8 != null) {
                                this.h = f6 - bVar8.j.a();
                            } else {
                                k.t("helper");
                                throw null;
                            }
                        } else {
                            k.t("helper");
                            throw null;
                        }
                    } else {
                        k.t("helper");
                        throw null;
                    }
                } else {
                    k.t("helper");
                    throw null;
                }
            } else {
                k.t("helper");
                throw null;
            }
        } else {
            k.t("helper");
            throw null;
        }
    }
}
