package com.petterp.floatingx.assist.helper;

import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.LayoutRes;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.petterp.floatingx.assist.FxGravity;
import com.petterp.floatingx.assist.c;
import com.petterp.floatingx.listener.d;
import com.petterp.floatingx.util.c;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BasisHelper.kt */
public class b {
    public int A;
    public int B;
    public int a;
    @Nullable
    public View b;
    @NotNull
    public FxGravity c = FxGravity.DEFAULT;
    public long d = 300;
    @Nullable
    public FrameLayout.LayoutParams e;
    @Nullable
    public com.petterp.floatingx.assist.a f;
    public float g;
    public float h;
    public float i;
    @NotNull
    public com.petterp.floatingx.assist.b j = new com.petterp.floatingx.assist.b(0.0f, 0.0f, 0.0f, 0.0f, 15, (DefaultConstructorMarker) null);
    @NotNull
    public c k = c.Normal;
    @NotNull
    public com.petterp.floatingx.util.a l = com.petterp.floatingx.util.a.LEFT_OR_RIGHT;
    public boolean m;
    public boolean n = true;
    public boolean o = true;
    public boolean p;
    public boolean q;
    public boolean r;
    public boolean s = true;
    public boolean t;
    @Nullable
    public com.petterp.floatingx.listener.c u;
    @Nullable
    public d v;
    @Nullable
    public com.petterp.floatingx.listener.a w;
    @Nullable
    public View.OnClickListener x;
    @Nullable
    public com.petterp.floatingx.util.c y;
    @NotNull
    public String z = "";

    public final /* synthetic */ void b(String scope) {
        k.e(scope, "scope");
        if (this.r) {
            c.a aVar = com.petterp.floatingx.util.c.a;
            this.y = aVar.a(scope + '-' + this.z);
        }
    }

    public final /* synthetic */ void a() {
        this.b = null;
        this.m = false;
        com.petterp.floatingx.assist.a aVar = this.f;
        if (aVar != null) {
            aVar.a();
        }
    }

    /* compiled from: BasisHelper.kt */
    public static abstract class a<T, B extends b> {
        @LayoutRes
        private int a;
        @Nullable
        private View b;
        private long c = 300;
        @Nullable
        private com.petterp.floatingx.assist.a d;
        @NotNull
        private FxGravity e = FxGravity.DEFAULT;
        @Nullable
        private FrameLayout.LayoutParams f;
        @NotNull
        private com.petterp.floatingx.assist.c g = com.petterp.floatingx.assist.c.Normal;
        @NotNull
        private com.petterp.floatingx.util.a h = com.petterp.floatingx.util.a.LEFT_OR_RIGHT;
        private float i;
        private float j;
        private float k;
        private boolean l;
        @NotNull
        private com.petterp.floatingx.assist.b m = new com.petterp.floatingx.assist.b(0.0f, 0.0f, 0.0f, 0.0f, 15, (DefaultConstructorMarker) null);
        @NotNull
        private com.petterp.floatingx.assist.b n = new com.petterp.floatingx.assist.b(0.0f, 0.0f, 0.0f, 0.0f, 15, (DefaultConstructorMarker) null);
        @NotNull
        private String o = "";
        private boolean p;
        private boolean q;
        private boolean r = true;
        private boolean s;
        private boolean t;
        private boolean u = true;
        private boolean v;
        @Nullable
        private com.petterp.floatingx.listener.a w;
        @Nullable
        private d x;
        @Nullable
        private com.petterp.floatingx.listener.c y;
        @Nullable
        private View.OnClickListener z;

        /* renamed from: com.petterp.floatingx.assist.helper.b$a$a  reason: collision with other inner class name */
        /* compiled from: BasisHelper.kt */
        public final /* synthetic */ class C0199a {
            public static final /* synthetic */ int[] a;

            static {
                int[] iArr = new int[FxGravity.values().length];
                iArr[FxGravity.DEFAULT.ordinal()] = 1;
                iArr[FxGravity.LEFT_OR_TOP.ordinal()] = 2;
                iArr[FxGravity.LEFT_OR_BOTTOM.ordinal()] = 3;
                iArr[FxGravity.RIGHT_OR_BOTTOM.ordinal()] = 4;
                iArr[FxGravity.RIGHT_OR_TOP.ordinal()] = 5;
                iArr[FxGravity.RIGHT_OR_CENTER.ordinal()] = 6;
                iArr[FxGravity.LEFT_OR_CENTER.ordinal()] = 7;
                iArr[FxGravity.TOP_OR_CENTER.ordinal()] = 8;
                iArr[FxGravity.BOTTOM_OR_CENTER.ordinal()] = 9;
                a = iArr;
            }
        }

        /* access modifiers changed from: protected */
        @NotNull
        public abstract B c();

        public final T n(@NotNull View.OnClickListener onClickListener) {
            k.e(onClickListener, "clickListener");
            return o(this, 0, onClickListener, 1, (Object) null);
        }

        @NotNull
        public B b() {
            b c2 = c();
            b $this$build_u24lambda_u2d0 = c2;
            a();
            $this$build_u24lambda_u2d0.m = this.l;
            $this$build_u24lambda_u2d0.a = this.a;
            $this$build_u24lambda_u2d0.b = this.b;
            $this$build_u24lambda_u2d0.c = this.e;
            $this$build_u24lambda_u2d0.d = this.c;
            $this$build_u24lambda_u2d0.e = this.f;
            $this$build_u24lambda_u2d0.f = this.d;
            $this$build_u24lambda_u2d0.k = this.g;
            $this$build_u24lambda_u2d0.g = this.i;
            $this$build_u24lambda_u2d0.h = this.j;
            $this$build_u24lambda_u2d0.i = this.k;
            $this$build_u24lambda_u2d0.j = this.m;
            $this$build_u24lambda_u2d0.l = this.h;
            $this$build_u24lambda_u2d0.p = this.q;
            $this$build_u24lambda_u2d0.n = this.u;
            $this$build_u24lambda_u2d0.o = this.r;
            $this$build_u24lambda_u2d0.q = this.s;
            $this$build_u24lambda_u2d0.s = this.t;
            $this$build_u24lambda_u2d0.t = this.v;
            $this$build_u24lambda_u2d0.r = this.p;
            $this$build_u24lambda_u2d0.z = this.o;
            $this$build_u24lambda_u2d0.u = this.y;
            $this$build_u24lambda_u2d0.v = this.x;
            $this$build_u24lambda_u2d0.w = this.w;
            $this$build_u24lambda_u2d0.x = this.z;
            return c2;
        }

        public final T l(@NotNull View view) {
            k.e(view, "view");
            this.a = 0;
            this.b = view;
            return this;
        }

        public final T f(@NotNull com.petterp.floatingx.assist.c mode) {
            k.e(mode, "mode");
            this.g = mode;
            return this;
        }

        public final T i(boolean isEnable) {
            this.u = isEnable;
            return this;
        }

        public static /* synthetic */ Object o(a aVar, long j2, View.OnClickListener onClickListener, int i2, Object obj) {
            if (obj == null) {
                if ((i2 & 1) != 0) {
                    j2 = 500;
                }
                return aVar.m(j2, onClickListener);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setOnClickListener");
        }

        public final T m(long time, @NotNull View.OnClickListener clickListener) {
            k.e(clickListener, "clickListener");
            this.t = true;
            this.z = clickListener;
            this.c = time;
            return this;
        }

        public final T e(float t2, float l2, float b2, float r2) {
            com.petterp.floatingx.assist.b $this$setBorderMargin_u24lambda_u2d1 = this.m;
            $this$setBorderMargin_u24lambda_u2d1.h(t2);
            $this$setBorderMargin_u24lambda_u2d1.f(l2);
            $this$setBorderMargin_u24lambda_u2d1.e(b2);
            $this$setBorderMargin_u24lambda_u2d1.g(r2);
            return this;
        }

        public final T j(boolean isLog, @NotNull String tag) {
            k.e(tag, Progress.TAG);
            this.p = isLog;
            this.o = tag.length() > 0 ? k.l("-", tag) : "";
            return this;
        }

        public final T h(float t2, float b2, float l2, float r2) {
            this.v = true;
            this.n.h(t2);
            this.n.e(b2);
            this.n.f(l2);
            this.n.g(r2);
            return this;
        }

        public final T g(boolean isEnable) {
            this.q = isEnable;
            return this;
        }

        public final T k(@NotNull FxGravity gravity) {
            k.e(gravity, "gravity");
            this.e = gravity;
            return this;
        }

        public final T d(@NotNull com.petterp.floatingx.assist.a fxAnimation) {
            k.e(fxAnimation, "fxAnimation");
            this.d = fxAnimation;
            this.q = true;
            return this;
        }

        public final T p(@NotNull com.petterp.floatingx.listener.c iFxScrollListener) {
            k.e(iFxScrollListener, "iFxScrollListener");
            this.y = iFxScrollListener;
            return this;
        }

        private final void a() {
            if (this.v || this.e.isDefault()) {
                float edgeOffset = this.r ? this.k : 0.0f;
                float b2 = this.n.a() + this.m.a() + edgeOffset;
                float t2 = this.n.d() + this.m.d() + edgeOffset;
                float r2 = this.n.c() + this.m.c() + edgeOffset;
                float l2 = this.n.b() + this.m.b() + edgeOffset;
                this.j = 0.0f;
                this.i = 0.0f;
                switch (C0199a.a[this.e.ordinal()]) {
                    case 1:
                    case 2:
                        this.j = l2;
                        this.i = t2;
                        return;
                    case 3:
                        this.i = -b2;
                        this.j = l2;
                        return;
                    case 4:
                        this.i = -b2;
                        this.j = -r2;
                        return;
                    case 5:
                        this.j = -r2;
                        this.i = t2;
                        return;
                    case 6:
                        this.j = -r2;
                        return;
                    case 7:
                        this.j = l2;
                        return;
                    case 8:
                        this.i = t2;
                        return;
                    case 9:
                        this.i = -b2;
                        return;
                    default:
                        return;
                }
            }
        }
    }
}
