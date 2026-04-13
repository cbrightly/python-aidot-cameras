package com.petterp.floatingx.assist.helper;

import android.widget.FrameLayout;
import com.petterp.floatingx.assist.helper.b;
import com.petterp.floatingx.listener.control.d;
import com.petterp.floatingx.util.FxScopeEnum;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ScopeHelper.kt */
public final class c extends b {
    @NotNull
    public static final b C = new b((DefaultConstructorMarker) null);

    @NotNull
    public static final a c() {
        return C.a();
    }

    @NotNull
    public final d<FrameLayout> d(@NotNull FrameLayout group) {
        k.e(group, "group");
        b(FxScopeEnum.VIEW_GROUP_SCOPE.getTag());
        com.petterp.floatingx.impl.control.d control = new com.petterp.floatingx.impl.control.d(this);
        control.w(group);
        return control;
    }

    /* compiled from: ScopeHelper.kt */
    public static final class b {
        public /* synthetic */ b(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private b() {
        }

        @NotNull
        public final a a() {
            return new a();
        }
    }

    /* compiled from: ScopeHelper.kt */
    public static final class a extends b.a<a, c> {
        /* access modifiers changed from: protected */
        @NotNull
        /* renamed from: q */
        public c c() {
            return new c();
        }
    }
}
