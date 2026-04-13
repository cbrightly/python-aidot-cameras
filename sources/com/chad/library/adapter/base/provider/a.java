package com.chad.library.adapter.base.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.l;
import kotlin.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: BaseItemProvider.kt */
public abstract class a<T> {
    @NotNull
    public Context a;
    private final g b;
    private final g c;

    private final ArrayList<Integer> e() {
        return (ArrayList) this.b.getValue();
    }

    private final ArrayList<Integer> g() {
        return (ArrayList) this.c.getValue();
    }

    public abstract void a(@NotNull BaseViewHolder baseViewHolder, T t);

    @LayoutRes
    public abstract int f();

    public a() {
        k kVar = k.NONE;
        this.b = i.a(kVar, C0062a.INSTANCE);
        this.c = i.a(kVar, b.INSTANCE);
    }

    public final void p(@NotNull Context context) {
        kotlin.jvm.internal.k.e(context, "<set-?>");
        this.a = context;
    }

    /* renamed from: com.chad.library.adapter.base.provider.a$a  reason: collision with other inner class name */
    /* compiled from: BaseItemProvider.kt */
    public static final class C0062a extends l implements kotlin.jvm.functions.a<ArrayList<Integer>> {
        public static final C0062a INSTANCE = new C0062a();

        C0062a() {
            super(0);
        }

        @NotNull
        public final ArrayList<Integer> invoke() {
            return new ArrayList<>();
        }
    }

    /* compiled from: BaseItemProvider.kt */
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

    public void b(@NotNull BaseViewHolder helper, T item, @NotNull List<? extends Object> payloads) {
        kotlin.jvm.internal.k.e(helper, "helper");
        kotlin.jvm.internal.k.e(payloads, "payloads");
    }

    @NotNull
    public BaseViewHolder k(@NotNull ViewGroup parent, int viewType) {
        kotlin.jvm.internal.k.e(parent, "parent");
        return new BaseViewHolder(com.chad.library.adapter.base.util.a.a(parent, f()));
    }

    public void o(@NotNull BaseViewHolder viewHolder, int viewType) {
        kotlin.jvm.internal.k.e(viewHolder, "viewHolder");
    }

    public void m(@NotNull BaseViewHolder holder) {
        kotlin.jvm.internal.k.e(holder, "holder");
    }

    public void n(@NotNull BaseViewHolder holder) {
        kotlin.jvm.internal.k.e(holder, "holder");
    }

    public void j(@NotNull BaseViewHolder helper, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.e(helper, "helper");
        kotlin.jvm.internal.k.e(view, "view");
    }

    public boolean l(@NotNull BaseViewHolder helper, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.e(helper, "helper");
        kotlin.jvm.internal.k.e(view, "view");
        return false;
    }

    public void h(@NotNull BaseViewHolder helper, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.e(helper, "helper");
        kotlin.jvm.internal.k.e(view, "view");
    }

    public boolean i(@NotNull BaseViewHolder helper, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.e(helper, "helper");
        kotlin.jvm.internal.k.e(view, "view");
        return false;
    }

    @NotNull
    public final ArrayList<Integer> c() {
        return e();
    }

    @NotNull
    public final ArrayList<Integer> d() {
        return g();
    }
}
