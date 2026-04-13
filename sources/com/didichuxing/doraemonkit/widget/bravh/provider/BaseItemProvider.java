package com.didichuxing.doraemonkit.widget.bravh.provider;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import com.didichuxing.doraemonkit.widget.bravh.BaseProviderMultiAdapter;
import com.didichuxing.doraemonkit.widget.bravh.util.AdapterUtilsKt;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.g;
import kotlin.i;
import kotlin.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseItemProvider.kt */
public abstract class BaseItemProvider<T> {
    private final g clickViewIds$delegate;
    @NotNull
    public Context context;
    private final g longClickViewIds$delegate;
    private WeakReference<BaseProviderMultiAdapter<T>> weakAdapter;

    private final ArrayList<Integer> getClickViewIds() {
        return (ArrayList) this.clickViewIds$delegate.getValue();
    }

    private final ArrayList<Integer> getLongClickViewIds() {
        return (ArrayList) this.longClickViewIds$delegate.getValue();
    }

    public abstract void convert(@NotNull BaseViewHolder baseViewHolder, T t);

    public abstract int getItemViewType();

    @LayoutRes
    public abstract int getLayoutId();

    public BaseItemProvider() {
        k kVar = k.NONE;
        this.clickViewIds$delegate = i.a(kVar, BaseItemProvider$clickViewIds$2.INSTANCE);
        this.longClickViewIds$delegate = i.a(kVar, BaseItemProvider$longClickViewIds$2.INSTANCE);
    }

    @NotNull
    public final Context getContext() {
        Context context2 = this.context;
        if (context2 == null) {
            kotlin.jvm.internal.k.t("context");
        }
        return context2;
    }

    public final void setContext(@NotNull Context context2) {
        kotlin.jvm.internal.k.f(context2, "<set-?>");
        this.context = context2;
    }

    public final void setAdapter$doraemonkit_release(@NotNull BaseProviderMultiAdapter<T> adapter) {
        kotlin.jvm.internal.k.f(adapter, "adapter");
        this.weakAdapter = new WeakReference<>(adapter);
    }

    @Nullable
    public BaseProviderMultiAdapter<T> getAdapter() {
        WeakReference<BaseProviderMultiAdapter<T>> weakReference = this.weakAdapter;
        if (weakReference != null) {
            return (BaseProviderMultiAdapter) weakReference.get();
        }
        return null;
    }

    public void convert(@NotNull BaseViewHolder helper, T item, @NotNull List<? extends Object> payloads) {
        kotlin.jvm.internal.k.f(helper, "helper");
        kotlin.jvm.internal.k.f(payloads, "payloads");
    }

    @NotNull
    public BaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        kotlin.jvm.internal.k.f(parent, "parent");
        return new BaseViewHolder(AdapterUtilsKt.getItemView(parent, getLayoutId()));
    }

    public void onViewHolderCreated(@NotNull BaseViewHolder viewHolder, int viewType) {
        kotlin.jvm.internal.k.f(viewHolder, "viewHolder");
    }

    public void onClick(@NotNull BaseViewHolder helper, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.f(helper, "helper");
        kotlin.jvm.internal.k.f(view, "view");
    }

    public boolean onLongClick(@NotNull BaseViewHolder helper, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.f(helper, "helper");
        kotlin.jvm.internal.k.f(view, "view");
        return false;
    }

    public void onChildClick(@NotNull BaseViewHolder helper, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.f(helper, "helper");
        kotlin.jvm.internal.k.f(view, "view");
    }

    public boolean onChildLongClick(@NotNull BaseViewHolder helper, @NotNull View view, T data, int position) {
        kotlin.jvm.internal.k.f(helper, "helper");
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
