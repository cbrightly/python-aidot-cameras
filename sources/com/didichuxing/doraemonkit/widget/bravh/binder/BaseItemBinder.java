package com.didichuxing.doraemonkit.widget.bravh.binder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import com.didichuxing.doraemonkit.widget.bravh.BaseBinderAdapter;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.ArrayList;
import java.util.List;
import kotlin.g;
import kotlin.i;
import kotlin.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseItemBinder.kt */
public abstract class BaseItemBinder<T, VH extends BaseViewHolder> {
    @Nullable
    private BaseBinderAdapter _adapter;
    @Nullable
    private Context _context;
    private final g clickViewIds$delegate;
    private final g longClickViewIds$delegate;

    private final ArrayList<Integer> getClickViewIds() {
        return (ArrayList) this.clickViewIds$delegate.getValue();
    }

    private final ArrayList<Integer> getLongClickViewIds() {
        return (ArrayList) this.longClickViewIds$delegate.getValue();
    }

    public abstract void convert(@NotNull VH vh, T t);

    @NotNull
    public abstract VH onCreateViewHolder(@NotNull ViewGroup viewGroup, int i);

    public BaseItemBinder() {
        k kVar = k.NONE;
        this.clickViewIds$delegate = i.a(kVar, BaseItemBinder$clickViewIds$2.INSTANCE);
        this.longClickViewIds$delegate = i.a(kVar, BaseItemBinder$longClickViewIds$2.INSTANCE);
    }

    @Nullable
    public final BaseBinderAdapter get_adapter$doraemonkit_release() {
        return this._adapter;
    }

    public final void set_adapter$doraemonkit_release(@Nullable BaseBinderAdapter baseBinderAdapter) {
        this._adapter = baseBinderAdapter;
    }

    @Nullable
    public final Context get_context$doraemonkit_release() {
        return this._context;
    }

    public final void set_context$doraemonkit_release(@Nullable Context context) {
        this._context = context;
    }

    @NotNull
    public final BaseBinderAdapter getAdapter() {
        BaseBinderAdapter baseBinderAdapter = this._adapter;
        if (baseBinderAdapter != null) {
            if (baseBinderAdapter == null) {
                kotlin.jvm.internal.k.n();
            }
            return baseBinderAdapter;
        }
        throw new IllegalStateException(("This " + this + " has not been attached to BaseBinderAdapter yet.\n                    You should not call the method before addItemBinder().").toString());
    }

    @NotNull
    public final Context getContext() {
        Context context = this._context;
        if (context != null) {
            if (context == null) {
                kotlin.jvm.internal.k.n();
            }
            return context;
        }
        throw new IllegalStateException(("This " + this + " has not been attached to BaseBinderAdapter yet.\n                    You should not call the method before onCreateViewHolder().").toString());
    }

    @NotNull
    public final List<Object> getData() {
        return getAdapter().getData();
    }

    public void convert(@NotNull VH holder, T data, @NotNull List<? extends Object> payloads) {
        kotlin.jvm.internal.k.f(holder, "holder");
        kotlin.jvm.internal.k.f(payloads, "payloads");
    }

    public boolean onFailedToRecycleView(@NotNull VH holder) {
        kotlin.jvm.internal.k.f(holder, "holder");
        return false;
    }

    public void onViewAttachedToWindow(@NotNull VH holder) {
        kotlin.jvm.internal.k.f(holder, "holder");
    }

    public void onViewDetachedFromWindow(@NotNull VH holder) {
        kotlin.jvm.internal.k.f(holder, "holder");
    }

    public void onClick(@NotNull VH holder, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.f(holder, "holder");
        kotlin.jvm.internal.k.f(view, "view");
    }

    public boolean onLongClick(@NotNull VH holder, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.f(holder, "holder");
        kotlin.jvm.internal.k.f(view, "view");
        return false;
    }

    public void onChildClick(@NotNull VH holder, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.f(holder, "holder");
        kotlin.jvm.internal.k.f(view, "view");
    }

    public boolean onChildLongClick(@NotNull VH holder, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.f(holder, "holder");
        kotlin.jvm.internal.k.f(view, "view");
        return false;
    }

    public final void addChildClickViewIds(@NotNull @IdRes int... ids) {
        kotlin.jvm.internal.k.f(ids, "ids");
        for (int element$iv : ids) {
            getClickViewIds().add(Integer.valueOf(element$iv));
        }
    }

    @NotNull
    public final ArrayList<Integer> getChildClickViewIds() {
        return getClickViewIds();
    }

    public final void addChildLongClickViewIds(@NotNull @IdRes int... ids) {
        kotlin.jvm.internal.k.f(ids, "ids");
        for (int element$iv : ids) {
            getLongClickViewIds().add(Integer.valueOf(element$iv));
        }
    }

    @NotNull
    public final ArrayList<Integer> getChildLongClickViewIds() {
        return getLongClickViewIds();
    }
}
