package com.chad.library.adapter.base.binder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.l;
import kotlin.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseItemBinder.kt */
public abstract class a<T, VH extends BaseViewHolder> {
    private final g a;
    private final g b;
    @Nullable
    private Context c;

    private final ArrayList<Integer> e() {
        return (ArrayList) this.a.getValue();
    }

    private final ArrayList<Integer> f() {
        return (ArrayList) this.b.getValue();
    }

    public abstract void a(@NotNull VH vh, T t);

    @NotNull
    public abstract VH j(@NotNull ViewGroup viewGroup, int i);

    public a() {
        k kVar = k.NONE;
        this.a = i.a(kVar, C0057a.INSTANCE);
        this.b = i.a(kVar, b.INSTANCE);
    }

    /* renamed from: com.chad.library.adapter.base.binder.a$a  reason: collision with other inner class name */
    /* compiled from: BaseItemBinder.kt */
    public static final class C0057a extends l implements kotlin.jvm.functions.a<ArrayList<Integer>> {
        public static final C0057a INSTANCE = new C0057a();

        C0057a() {
            super(0);
        }

        @NotNull
        public final ArrayList<Integer> invoke() {
            return new ArrayList<>();
        }
    }

    /* compiled from: BaseItemBinder.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<ArrayList<Integer>> {
        public static final b INSTANCE = new b();

        b() {
            super(0);
        }

        @NotNull
        public final ArrayList<Integer> invoke() {
            return new ArrayList<>();
        }
    }

    public final void o(@Nullable Context context) {
        this.c = context;
    }

    public void b(@NotNull VH holder, T data, @NotNull List<? extends Object> payloads) {
        kotlin.jvm.internal.k.e(holder, "holder");
        kotlin.jvm.internal.k.e(payloads, "payloads");
    }

    public boolean k(@NotNull VH holder) {
        kotlin.jvm.internal.k.e(holder, "holder");
        return false;
    }

    public void m(@NotNull VH holder) {
        kotlin.jvm.internal.k.e(holder, "holder");
    }

    public void n(@NotNull VH holder) {
        kotlin.jvm.internal.k.e(holder, "holder");
    }

    public void i(@NotNull VH holder, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.e(holder, "holder");
        kotlin.jvm.internal.k.e(view, "view");
    }

    public boolean l(@NotNull VH holder, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.e(holder, "holder");
        kotlin.jvm.internal.k.e(view, "view");
        return false;
    }

    public void g(@NotNull VH holder, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.e(holder, "holder");
        kotlin.jvm.internal.k.e(view, "view");
    }

    public boolean h(@NotNull VH holder, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.e(holder, "holder");
        kotlin.jvm.internal.k.e(view, "view");
        return false;
    }

    @NotNull
    public final ArrayList<Integer> c() {
        return e();
    }

    @NotNull
    public final ArrayList<Integer> d() {
        return f();
    }
}
