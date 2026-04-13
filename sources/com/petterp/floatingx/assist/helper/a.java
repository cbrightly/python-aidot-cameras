package com.petterp.floatingx.assist.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.petterp.floatingx.assist.helper.b;
import com.petterp.floatingx.util.FxScopeEnum;
import com.petterp.floatingx.util.FxScreenExtKt;
import com.petterp.floatingx.util.c;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AppHelper.kt */
public final class a extends b {
    @NotNull
    public static final b C = new b((DefaultConstructorMarker) null);
    private /* synthetic */ String D;
    private final /* synthetic */ List<Class<?>> E;
    private final /* synthetic */ List<Class<?>> F;
    private final /* synthetic */ boolean G;
    private final /* synthetic */ com.petterp.floatingx.listener.b H;

    @NotNull
    public static final C0198a c() {
        return C.a();
    }

    @NotNull
    public final String e() {
        return this.D;
    }

    @Nullable
    public final com.petterp.floatingx.listener.b d() {
        return this.H;
    }

    public a(@NotNull String tag, @NotNull List<Class<?>> blackFilterList, @NotNull List<Class<?>> whiteInsertList, boolean isAllInstall, @Nullable com.petterp.floatingx.listener.b fxLifecycleExpand) {
        k.e(tag, Progress.TAG);
        k.e(blackFilterList, "blackFilterList");
        k.e(whiteInsertList, "whiteInsertList");
        this.D = tag;
        this.E = blackFilterList;
        this.F = whiteInsertList;
        this.G = isAllInstall;
        this.H = fxLifecycleExpand;
    }

    public final /* synthetic */ void h(Activity activity) {
        Integer valueOf = activity == null ? null : Integer.valueOf(FxScreenExtKt.a(activity));
        int intValue = valueOf == null ? this.A : valueOf.intValue();
        this.A = intValue;
        c cVar = this.y;
        if (cVar != null) {
            cVar.d(k.l("system-> navigationBar-", Integer.valueOf(intValue)));
        }
    }

    public final /* synthetic */ void i(Activity activity) {
        Integer valueOf = activity == null ? null : Integer.valueOf(FxScreenExtKt.g(activity));
        int intValue = valueOf == null ? this.B : valueOf.intValue();
        this.B = intValue;
        c cVar = this.y;
        if (cVar != null) {
            cVar.d(k.l("system-> statusBarHeight-", Integer.valueOf(intValue)));
        }
    }

    public final /* synthetic */ boolean f(Activity act) {
        k.e(act, "act");
        return g(act.getClass());
    }

    public final /* synthetic */ boolean g(Class cls) {
        k.e(cls, "cls");
        return (this.G && !this.E.contains(cls)) || (!this.G && this.F.contains(cls));
    }

    /* renamed from: com.petterp.floatingx.assist.helper.a$a  reason: collision with other inner class name */
    /* compiled from: AppHelper.kt */
    public static final class C0198a extends b.a<C0198a, a> {
        @NotNull
        private List<Class<?>> A = new ArrayList();
        @NotNull
        private List<Class<?>> B = new ArrayList();
        @Nullable
        private com.petterp.floatingx.listener.b C;
        private boolean D = true;
        @NotNull
        private String E = "FX_DEFAULT_TAG";
        private boolean F;

        @NotNull
        public final C0198a s() {
            this.F = true;
            return this;
        }

        @NotNull
        public final C0198a t(@NotNull Context context) {
            k.e(context, "context");
            if (context instanceof Application) {
                com.petterp.floatingx.a.a.k((Application) context);
            } else {
                com.petterp.floatingx.a aVar = com.petterp.floatingx.a.a;
                Context applicationContext = context.getApplicationContext();
                if (applicationContext != null) {
                    aVar.k((Application) applicationContext);
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type android.app.Application");
                }
            }
            return this;
        }

        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: r */
        public a c() {
            return new a(this.E, this.B, this.A, this.D, this.C);
        }

        @NotNull
        public a q() {
            b b = super.b();
            a $this$build_u24lambda_u2d0 = (a) b;
            $this$build_u24lambda_u2d0.m = this.F;
            if ($this$build_u24lambda_u2d0.r) {
                if ($this$build_u24lambda_u2d0.z.length() == 0) {
                    $this$build_u24lambda_u2d0.z = $this$build_u24lambda_u2d0.e();
                }
            }
            $this$build_u24lambda_u2d0.b(FxScopeEnum.APP_SCOPE.getTag());
            return (a) b;
        }
    }

    /* compiled from: AppHelper.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }

        @NotNull
        public final C0198a a() {
            return new C0198a();
        }
    }
}
