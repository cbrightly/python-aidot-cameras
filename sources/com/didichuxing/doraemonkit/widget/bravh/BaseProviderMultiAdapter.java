package com.didichuxing.doraemonkit.widget.bravh;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.didichuxing.doraemonkit.widget.bravh.provider.BaseItemProvider;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.List;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseProviderMultiAdapter.kt */
public abstract class BaseProviderMultiAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private final g mItemProviders$delegate;

    public BaseProviderMultiAdapter() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: private */
    public final SparseArray<BaseItemProvider<T>> getMItemProviders() {
        return (SparseArray) this.mItemProviders$delegate.getValue();
    }

    /* access modifiers changed from: protected */
    public abstract int getItemType(@NotNull List<? extends T> list, int i);

    public BaseProviderMultiAdapter(@Nullable List<T> data) {
        super(0, data);
        this.mItemProviders$delegate = i.a(k.NONE, BaseProviderMultiAdapter$mItemProviders$2.INSTANCE);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseProviderMultiAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public void addItemProvider(@NotNull BaseItemProvider<T> provider) {
        kotlin.jvm.internal.k.f(provider, "provider");
        provider.setAdapter$doraemonkit_release(this);
        getMItemProviders().put(provider.getItemViewType(), provider);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public BaseViewHolder onCreateDefViewHolder(@NotNull ViewGroup parent, int viewType) {
        kotlin.jvm.internal.k.f(parent, "parent");
        BaseItemProvider provider = getItemProvider(viewType);
        if (provider != null) {
            Context context = parent.getContext();
            kotlin.jvm.internal.k.b(context, "parent.context");
            provider.setContext(context);
            BaseViewHolder $this$apply = provider.onCreateViewHolder(parent, viewType);
            provider.onViewHolderCreated($this$apply, viewType);
            return $this$apply;
        }
        throw new IllegalStateException(("ViewType: " + viewType + " no such provider found，please use addItemProvider() first!").toString());
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        return getItemType(getData(), position);
    }

    /* access modifiers changed from: protected */
    public void convert(@NotNull BaseViewHolder holder, T item) {
        kotlin.jvm.internal.k.f(holder, "holder");
        BaseItemProvider itemProvider = getItemProvider(holder.getItemViewType());
        if (itemProvider == null) {
            kotlin.jvm.internal.k.n();
        }
        itemProvider.convert(holder, item);
    }

    /* access modifiers changed from: protected */
    public void convert(@NotNull BaseViewHolder holder, T item, @NotNull List<? extends Object> payloads) {
        kotlin.jvm.internal.k.f(holder, "holder");
        kotlin.jvm.internal.k.f(payloads, "payloads");
        BaseItemProvider itemProvider = getItemProvider(holder.getItemViewType());
        if (itemProvider == null) {
            kotlin.jvm.internal.k.n();
        }
        itemProvider.convert(holder, item, payloads);
    }

    /* access modifiers changed from: protected */
    public void bindViewClickListener(@NotNull BaseViewHolder viewHolder, int viewType) {
        kotlin.jvm.internal.k.f(viewHolder, "viewHolder");
        super.bindViewClickListener(viewHolder, viewType);
        bindClick(viewHolder);
        bindChildClick(viewHolder, viewType);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public BaseItemProvider<T> getItemProvider(int viewType) {
        return (BaseItemProvider) getMItemProviders().get(viewType);
    }

    /* access modifiers changed from: protected */
    public void bindClick(@NotNull BaseViewHolder viewHolder) {
        kotlin.jvm.internal.k.f(viewHolder, "viewHolder");
        if (getOnItemClickListener() == null) {
            viewHolder.itemView.setOnClickListener(new BaseProviderMultiAdapter$bindClick$1(this, viewHolder));
        }
        if (getOnItemLongClickListener() == null) {
            viewHolder.itemView.setOnLongClickListener(new BaseProviderMultiAdapter$bindClick$2(this, viewHolder));
        }
    }

    /* access modifiers changed from: protected */
    public void bindChildClick(@NotNull BaseViewHolder viewHolder, int viewType) {
        BaseItemProvider provider;
        kotlin.jvm.internal.k.f(viewHolder, "viewHolder");
        if (getOnItemChildClickListener() == null) {
            BaseItemProvider provider2 = getItemProvider(viewType);
            if (provider2 != null) {
                for (Number intValue : provider2.getChildClickViewIds()) {
                    View it = viewHolder.itemView.findViewById(intValue.intValue());
                    if (it != null) {
                        if (!it.isClickable()) {
                            it.setClickable(true);
                        }
                        it.setOnClickListener(new BaseProviderMultiAdapter$bindChildClick$$inlined$forEach$lambda$1(this, viewHolder, provider2));
                    }
                }
            } else {
                return;
            }
        }
        if (getOnItemChildLongClickListener() == null && (provider = getItemProvider(viewType)) != null) {
            for (Number intValue2 : provider.getChildLongClickViewIds()) {
                View it2 = viewHolder.itemView.findViewById(intValue2.intValue());
                if (it2 != null) {
                    if (!it2.isLongClickable()) {
                        it2.setLongClickable(true);
                    }
                    it2.setOnLongClickListener(new BaseProviderMultiAdapter$bindChildClick$$inlined$forEach$lambda$2(this, viewHolder, provider));
                }
            }
        }
    }
}
