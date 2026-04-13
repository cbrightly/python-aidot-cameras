package com.chad.library.adapter.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.l;
import kotlin.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseProviderMultiAdapter.kt */
public abstract class BaseProviderMultiAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private final g N4;

    public BaseProviderMultiAdapter() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    /* access modifiers changed from: private */
    public final SparseArray<com.chad.library.adapter.base.provider.a<T>> getMItemProviders() {
        return (SparseArray) this.N4.getValue();
    }

    /* access modifiers changed from: protected */
    public abstract int getItemType(@NotNull List<? extends T> list, int i);

    public BaseProviderMultiAdapter(@Nullable List<T> data) {
        super(0, data);
        this.N4 = i.a(k.NONE, e.INSTANCE);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseProviderMultiAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    /* compiled from: BaseProviderMultiAdapter.kt */
    public static final class e extends l implements kotlin.jvm.functions.a<SparseArray<com.chad.library.adapter.base.provider.a<T>>> {
        public static final e INSTANCE = new e();

        e() {
            super(0);
        }

        @NotNull
        public final SparseArray<com.chad.library.adapter.base.provider.a<T>> invoke() {
            return new SparseArray<>();
        }
    }

    /* access modifiers changed from: protected */
    @NotNull
    public BaseViewHolder s(@NotNull ViewGroup parent, int viewType) {
        kotlin.jvm.internal.k.e(parent, "parent");
        com.chad.library.adapter.base.provider.a provider = A(viewType);
        if (provider != null) {
            Context context = parent.getContext();
            kotlin.jvm.internal.k.d(context, "parent.context");
            provider.p(context);
            BaseViewHolder $this$apply = provider.k(parent, viewType);
            provider.o($this$apply, viewType);
            return $this$apply;
        }
        throw new IllegalStateException(("ViewType: " + viewType + " no such provider found，please use addItemProvider() first!").toString());
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        return getItemType(getData(), position);
    }

    /* access modifiers changed from: protected */
    public void d(@NotNull BaseViewHolder holder, T item) {
        kotlin.jvm.internal.k.e(holder, "holder");
        com.chad.library.adapter.base.provider.a A = A(holder.getItemViewType());
        kotlin.jvm.internal.k.c(A);
        A.a(holder, item);
    }

    /* access modifiers changed from: protected */
    public void e(@NotNull BaseViewHolder holder, T item, @NotNull List<? extends Object> payloads) {
        kotlin.jvm.internal.k.e(holder, "holder");
        kotlin.jvm.internal.k.e(payloads, "payloads");
        com.chad.library.adapter.base.provider.a A = A(holder.getItemViewType());
        kotlin.jvm.internal.k.c(A);
        A.b(holder, item, payloads);
    }

    /* access modifiers changed from: protected */
    public void c(@NotNull BaseViewHolder viewHolder, int viewType) {
        kotlin.jvm.internal.k.e(viewHolder, "viewHolder");
        super.c(viewHolder, viewType);
        z(viewHolder);
        y(viewHolder, viewType);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public com.chad.library.adapter.base.provider.a<T> A(int viewType) {
        return (com.chad.library.adapter.base.provider.a) getMItemProviders().get(viewType);
    }

    /* renamed from: v */
    public void onViewAttachedToWindow(@NotNull BaseViewHolder holder) {
        kotlin.jvm.internal.k.e(holder, "holder");
        super.onViewAttachedToWindow(holder);
        com.chad.library.adapter.base.provider.a A = A(holder.getItemViewType());
        if (A != null) {
            A.m(holder);
        }
    }

    /* renamed from: B */
    public void onViewDetachedFromWindow(@NotNull BaseViewHolder holder) {
        kotlin.jvm.internal.k.e(holder, "holder");
        super.onViewDetachedFromWindow(holder);
        com.chad.library.adapter.base.provider.a A = A(holder.getItemViewType());
        if (A != null) {
            A.n(holder);
        }
    }

    /* access modifiers changed from: protected */
    public void z(@NotNull BaseViewHolder viewHolder) {
        kotlin.jvm.internal.k.e(viewHolder, "viewHolder");
        if (n() == null) {
            viewHolder.itemView.setOnClickListener(new c(this, viewHolder));
        }
        if (o() == null) {
            viewHolder.itemView.setOnLongClickListener(new d(this, viewHolder));
        }
    }

    /* compiled from: BaseProviderMultiAdapter.kt */
    public static final class c implements View.OnClickListener {
        final /* synthetic */ BaseProviderMultiAdapter c;
        final /* synthetic */ BaseViewHolder d;

        c(BaseProviderMultiAdapter baseProviderMultiAdapter, BaseViewHolder baseViewHolder) {
            this.c = baseProviderMultiAdapter;
            this.d = baseViewHolder;
        }

        @SensorsDataInstrumented
        public final void onClick(View view) {
            View it = view;
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            int itemViewType = this.d.getItemViewType();
            BaseViewHolder baseViewHolder = this.d;
            kotlin.jvm.internal.k.d(it, "it");
            ((com.chad.library.adapter.base.provider.a) this.c.getMItemProviders().get(itemViewType)).j(baseViewHolder, it, this.c.getData().get(position2), position2);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* compiled from: BaseProviderMultiAdapter.kt */
    public static final class d implements View.OnLongClickListener {
        final /* synthetic */ BaseProviderMultiAdapter c;
        final /* synthetic */ BaseViewHolder d;

        d(BaseProviderMultiAdapter baseProviderMultiAdapter, BaseViewHolder baseViewHolder) {
            this.c = baseProviderMultiAdapter;
            this.d = baseViewHolder;
        }

        public final boolean onLongClick(View it) {
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                return false;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            int itemViewType = this.d.getItemViewType();
            BaseViewHolder baseViewHolder = this.d;
            kotlin.jvm.internal.k.d(it, "it");
            return ((com.chad.library.adapter.base.provider.a) this.c.getMItemProviders().get(itemViewType)).l(baseViewHolder, it, this.c.getData().get(position2), position2);
        }
    }

    /* access modifiers changed from: protected */
    public void y(@NotNull BaseViewHolder viewHolder, int viewType) {
        com.chad.library.adapter.base.provider.a provider;
        kotlin.jvm.internal.k.e(viewHolder, "viewHolder");
        if (l() == null) {
            com.chad.library.adapter.base.provider.a provider2 = A(viewType);
            if (provider2 != null) {
                for (Number intValue : provider2.c()) {
                    View it = viewHolder.itemView.findViewById(intValue.intValue());
                    if (it != null) {
                        if (!it.isClickable()) {
                            it.setClickable(true);
                        }
                        it.setOnClickListener(new a(this, viewHolder, provider2));
                    }
                }
            } else {
                return;
            }
        }
        if (m() == null && (provider = A(viewType)) != null) {
            for (Number intValue2 : provider.d()) {
                View it2 = viewHolder.itemView.findViewById(intValue2.intValue());
                if (it2 != null) {
                    if (!it2.isLongClickable()) {
                        it2.setLongClickable(true);
                    }
                    it2.setOnLongClickListener(new b(this, viewHolder, provider));
                }
            }
        }
    }

    /* compiled from: BaseProviderMultiAdapter.kt */
    public static final class a implements View.OnClickListener {
        final /* synthetic */ BaseProviderMultiAdapter c;
        final /* synthetic */ BaseViewHolder d;
        final /* synthetic */ com.chad.library.adapter.base.provider.a f;

        a(BaseProviderMultiAdapter baseProviderMultiAdapter, BaseViewHolder baseViewHolder, com.chad.library.adapter.base.provider.a aVar) {
            this.c = baseProviderMultiAdapter;
            this.d = baseViewHolder;
            this.f = aVar;
        }

        @SensorsDataInstrumented
        public final void onClick(View view) {
            View v = view;
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            com.chad.library.adapter.base.provider.a aVar = this.f;
            BaseViewHolder baseViewHolder = this.d;
            kotlin.jvm.internal.k.d(v, "v");
            aVar.h(baseViewHolder, v, this.c.getData().get(position2), position2);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* compiled from: BaseProviderMultiAdapter.kt */
    public static final class b implements View.OnLongClickListener {
        final /* synthetic */ BaseProviderMultiAdapter c;
        final /* synthetic */ BaseViewHolder d;
        final /* synthetic */ com.chad.library.adapter.base.provider.a f;

        b(BaseProviderMultiAdapter baseProviderMultiAdapter, BaseViewHolder baseViewHolder, com.chad.library.adapter.base.provider.a aVar) {
            this.c = baseProviderMultiAdapter;
            this.d = baseViewHolder;
            this.f = aVar;
        }

        public final boolean onLongClick(View v) {
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                return false;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            com.chad.library.adapter.base.provider.a aVar = this.f;
            BaseViewHolder baseViewHolder = this.d;
            kotlin.jvm.internal.k.d(v, "v");
            return aVar.i(baseViewHolder, v, this.c.getData().get(position2), position2);
        }
    }
}
