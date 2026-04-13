package com.chad.library.adapter.base;

import android.annotation.SuppressLint;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseBinderAdapter.kt */
public class BaseBinderAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    /* access modifiers changed from: private */
    public final HashMap<Class<?>, DiffUtil.ItemCallback<Object>> N4;
    private final HashMap<Class<?>, Integer> O4;
    private final SparseArray<com.chad.library.adapter.base.binder.a<Object, ?>> P4;

    public BaseBinderAdapter() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    public BaseBinderAdapter(@Nullable List<Object> list) {
        super(0, list);
        this.N4 = new HashMap<>();
        this.O4 = new HashMap<>();
        this.P4 = new SparseArray<>();
        setDiffCallback(new ItemCallback());
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseBinderAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public BaseViewHolder s(@NotNull ViewGroup parent, int viewType) {
        k.e(parent, "parent");
        com.chad.library.adapter.base.binder.a it = A(viewType);
        it.o(getContext());
        return it.j(parent, viewType);
    }

    /* access modifiers changed from: protected */
    public void d(@NotNull BaseViewHolder holder, @NotNull Object item) {
        k.e(holder, "holder");
        k.e(item, "item");
        A(holder.getItemViewType()).a(holder, item);
    }

    /* access modifiers changed from: protected */
    public void e(@NotNull BaseViewHolder holder, @NotNull Object item, @NotNull List<? extends Object> payloads) {
        k.e(holder, "holder");
        k.e(item, "item");
        k.e(payloads, "payloads");
        A(holder.getItemViewType()).b(holder, item, payloads);
    }

    @NotNull
    public com.chad.library.adapter.base.binder.a<Object, BaseViewHolder> A(int viewType) {
        com.chad.library.adapter.base.binder.a binder = this.P4.get(viewType);
        if (binder != null) {
            return binder;
        }
        throw new IllegalStateException(("getItemBinder: viewType '" + viewType + "' no such Binder found，please use addItemBinder() first!").toString());
    }

    @Nullable
    public com.chad.library.adapter.base.binder.a<Object, BaseViewHolder> B(int viewType) {
        com.chad.library.adapter.base.binder.a binder = this.P4.get(viewType);
        if (!(binder instanceof com.chad.library.adapter.base.binder.a)) {
            return null;
        }
        return binder;
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        return findViewType(getData().get(position).getClass());
    }

    /* access modifiers changed from: protected */
    public void c(@NotNull BaseViewHolder viewHolder, int viewType) {
        k.e(viewHolder, "viewHolder");
        super.c(viewHolder, viewType);
        z(viewHolder);
        y(viewHolder, viewType);
    }

    /* renamed from: v */
    public void onViewAttachedToWindow(@NotNull BaseViewHolder holder) {
        k.e(holder, "holder");
        super.onViewAttachedToWindow(holder);
        com.chad.library.adapter.base.binder.a<Object, BaseViewHolder> B = B(holder.getItemViewType());
        if (B != null) {
            B.m(holder);
        }
    }

    /* renamed from: D */
    public void onViewDetachedFromWindow(@NotNull BaseViewHolder holder) {
        k.e(holder, "holder");
        super.onViewDetachedFromWindow(holder);
        com.chad.library.adapter.base.binder.a<Object, BaseViewHolder> B = B(holder.getItemViewType());
        if (B != null) {
            B.n(holder);
        }
    }

    /* renamed from: C */
    public boolean onFailedToRecycleView(@NotNull BaseViewHolder holder) {
        k.e(holder, "holder");
        com.chad.library.adapter.base.binder.a<Object, BaseViewHolder> B = B(holder.getItemViewType());
        if (B != null) {
            return B.k(holder);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final int findViewType(@NotNull Class<?> clazz) {
        k.e(clazz, "clazz");
        Integer type = this.O4.get(clazz);
        if (type != null) {
            return type.intValue();
        }
        throw new IllegalStateException(("findViewType: ViewType: " + clazz + " Not Find!").toString());
    }

    /* access modifiers changed from: protected */
    public void z(@NotNull BaseViewHolder viewHolder) {
        k.e(viewHolder, "viewHolder");
        if (n() == null) {
            viewHolder.itemView.setOnClickListener(new c(this, viewHolder));
        }
        if (o() == null) {
            viewHolder.itemView.setOnLongClickListener(new d(this, viewHolder));
        }
    }

    /* compiled from: BaseBinderAdapter.kt */
    public static final class c implements View.OnClickListener {
        final /* synthetic */ BaseBinderAdapter c;
        final /* synthetic */ BaseViewHolder d;

        c(BaseBinderAdapter baseBinderAdapter, BaseViewHolder baseViewHolder) {
            this.c = baseBinderAdapter;
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
            com.chad.library.adapter.base.binder.a binder = this.c.A(this.d.getItemViewType());
            BaseViewHolder baseViewHolder = this.d;
            k.d(it, "it");
            binder.i(baseViewHolder, it, this.c.getData().get(position2), position2);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* compiled from: BaseBinderAdapter.kt */
    public static final class d implements View.OnLongClickListener {
        final /* synthetic */ BaseBinderAdapter c;
        final /* synthetic */ BaseViewHolder d;

        d(BaseBinderAdapter baseBinderAdapter, BaseViewHolder baseViewHolder) {
            this.c = baseBinderAdapter;
            this.d = baseViewHolder;
        }

        public final boolean onLongClick(View it) {
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                return false;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            com.chad.library.adapter.base.binder.a binder = this.c.A(this.d.getItemViewType());
            BaseViewHolder baseViewHolder = this.d;
            k.d(it, "it");
            return binder.l(baseViewHolder, it, this.c.getData().get(position2), position2);
        }
    }

    /* access modifiers changed from: protected */
    public void y(@NotNull BaseViewHolder viewHolder, int viewType) {
        k.e(viewHolder, "viewHolder");
        if (l() == null) {
            com.chad.library.adapter.base.binder.a provider = A(viewType);
            for (Number intValue : provider.c()) {
                View it = viewHolder.itemView.findViewById(intValue.intValue());
                if (it != null) {
                    if (!it.isClickable()) {
                        it.setClickable(true);
                    }
                    it.setOnClickListener(new a(this, viewHolder, provider));
                }
            }
        }
        if (m() == null) {
            com.chad.library.adapter.base.binder.a provider2 = A(viewType);
            for (Number intValue2 : provider2.d()) {
                View it2 = viewHolder.itemView.findViewById(intValue2.intValue());
                if (it2 != null) {
                    if (!it2.isLongClickable()) {
                        it2.setLongClickable(true);
                    }
                    it2.setOnLongClickListener(new b(this, viewHolder, provider2));
                }
            }
        }
    }

    /* compiled from: BaseBinderAdapter.kt */
    public static final class a implements View.OnClickListener {
        final /* synthetic */ BaseBinderAdapter c;
        final /* synthetic */ BaseViewHolder d;
        final /* synthetic */ com.chad.library.adapter.base.binder.a f;

        a(BaseBinderAdapter baseBinderAdapter, BaseViewHolder baseViewHolder, com.chad.library.adapter.base.binder.a aVar) {
            this.c = baseBinderAdapter;
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
            com.chad.library.adapter.base.binder.a aVar = this.f;
            BaseViewHolder baseViewHolder = this.d;
            k.d(v, "v");
            aVar.g(baseViewHolder, v, this.c.getData().get(position2), position2);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    /* compiled from: BaseBinderAdapter.kt */
    public static final class b implements View.OnLongClickListener {
        final /* synthetic */ BaseBinderAdapter c;
        final /* synthetic */ BaseViewHolder d;
        final /* synthetic */ com.chad.library.adapter.base.binder.a f;

        b(BaseBinderAdapter baseBinderAdapter, BaseViewHolder baseViewHolder, com.chad.library.adapter.base.binder.a aVar) {
            this.c = baseBinderAdapter;
            this.d = baseViewHolder;
            this.f = aVar;
        }

        public final boolean onLongClick(View v) {
            int position = this.d.getAdapterPosition();
            if (position == -1) {
                return false;
            }
            int position2 = position - this.c.getHeaderLayoutCount();
            com.chad.library.adapter.base.binder.a aVar = this.f;
            BaseViewHolder baseViewHolder = this.d;
            k.d(v, "v");
            return aVar.h(baseViewHolder, v, this.c.getData().get(position2), position2);
        }
    }

    /* compiled from: BaseBinderAdapter.kt */
    public final class ItemCallback extends DiffUtil.ItemCallback<Object> {
        public ItemCallback() {
        }

        public boolean areItemsTheSame(@NotNull Object oldItem, @NotNull Object newItem) {
            DiffUtil.ItemCallback it;
            k.e(oldItem, "oldItem");
            k.e(newItem, "newItem");
            if (!k.a(oldItem.getClass(), newItem.getClass()) || (it = (DiffUtil.ItemCallback) BaseBinderAdapter.this.N4.get(oldItem.getClass())) == null) {
                return k.a(oldItem, newItem);
            }
            return it.areItemsTheSame(oldItem, newItem);
        }

        @SuppressLint({"DiffUtilEquals"})
        public boolean areContentsTheSame(@NotNull Object oldItem, @NotNull Object newItem) {
            DiffUtil.ItemCallback it;
            k.e(oldItem, "oldItem");
            k.e(newItem, "newItem");
            if (!k.a(oldItem.getClass(), newItem.getClass()) || (it = (DiffUtil.ItemCallback) BaseBinderAdapter.this.N4.get(oldItem.getClass())) == null) {
                return true;
            }
            return it.areContentsTheSame(oldItem, newItem);
        }

        @Nullable
        public Object getChangePayload(@NotNull Object oldItem, @NotNull Object newItem) {
            DiffUtil.ItemCallback itemCallback;
            k.e(oldItem, "oldItem");
            k.e(newItem, "newItem");
            if (!k.a(oldItem.getClass(), newItem.getClass()) || (itemCallback = (DiffUtil.ItemCallback) BaseBinderAdapter.this.N4.get(oldItem.getClass())) == null) {
                return null;
            }
            return itemCallback.getChangePayload(oldItem, newItem);
        }
    }
}
