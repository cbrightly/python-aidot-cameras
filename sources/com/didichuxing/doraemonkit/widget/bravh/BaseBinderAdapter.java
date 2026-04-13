package com.didichuxing.doraemonkit.widget.bravh;

import android.annotation.SuppressLint;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.exifinterface.media.ExifInterface;
import androidx.recyclerview.widget.DiffUtil;
import com.didichuxing.doraemonkit.widget.bravh.binder.BaseItemBinder;
import com.didichuxing.doraemonkit.widget.bravh.viewholder.BaseViewHolder;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BaseBinderAdapter.kt */
public class BaseBinderAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {
    /* access modifiers changed from: private */
    public final HashMap<Class<?>, DiffUtil.ItemCallback<Object>> classDiffMap;
    private final SparseArray<BaseItemBinder<Object, ?>> mBinderArray;
    private final HashMap<Class<?>, Integer> mTypeMap;

    public BaseBinderAdapter() {
        this((List) null, 1, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final <T> BaseBinderAdapter addItemBinder(@NotNull Class<? extends T> cls, @NotNull BaseItemBinder<T, ?> baseItemBinder) {
        return addItemBinder$default(this, cls, baseItemBinder, (DiffUtil.ItemCallback) null, 4, (Object) null);
    }

    public BaseBinderAdapter(@Nullable List<Object> list) {
        super(0, list);
        this.classDiffMap = new HashMap<>();
        this.mTypeMap = new HashMap<>();
        this.mBinderArray = new SparseArray<>();
        setDiffCallback(new ItemCallback());
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ BaseBinderAdapter(List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : list);
    }

    public static /* synthetic */ BaseBinderAdapter addItemBinder$default(BaseBinderAdapter baseBinderAdapter, Class cls, BaseItemBinder baseItemBinder, DiffUtil.ItemCallback itemCallback, int i, Object obj) {
        if (obj == null) {
            if ((i & 4) != 0) {
                itemCallback = null;
            }
            return baseBinderAdapter.addItemBinder(cls, baseItemBinder, itemCallback);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addItemBinder");
    }

    @NotNull
    public final <T> BaseBinderAdapter addItemBinder(@NotNull Class<? extends T> clazz, @NotNull BaseItemBinder<T, ?> baseItemBinder, @Nullable DiffUtil.ItemCallback<T> callback) {
        k.f(clazz, "clazz");
        k.f(baseItemBinder, "baseItemBinder");
        int itemType = this.mTypeMap.size() + 1;
        this.mTypeMap.put(clazz, Integer.valueOf(itemType));
        this.mBinderArray.append(itemType, baseItemBinder);
        baseItemBinder.set_adapter$doraemonkit_release(this);
        if (callback != null) {
            this.classDiffMap.put(clazz, callback);
        }
        return this;
    }

    public static /* synthetic */ BaseBinderAdapter addItemBinder$default(BaseBinderAdapter baseBinderAdapter, BaseItemBinder baseItemBinder, DiffUtil.ItemCallback callback, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                callback = null;
            }
            k.f(baseItemBinder, "baseItemBinder");
            k.i(4, ExifInterface.GPS_DIRECTION_TRUE);
            baseBinderAdapter.addItemBinder(Object.class, baseItemBinder, callback);
            return baseBinderAdapter;
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addItemBinder");
    }

    @NotNull
    public final /* synthetic */ <T> BaseBinderAdapter addItemBinder(@NotNull BaseItemBinder<T, ?> baseItemBinder, @Nullable DiffUtil.ItemCallback<T> callback) {
        k.f(baseItemBinder, "baseItemBinder");
        k.i(4, ExifInterface.GPS_DIRECTION_TRUE);
        addItemBinder(Object.class, baseItemBinder, callback);
        return this;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public BaseViewHolder onCreateDefViewHolder(@NotNull ViewGroup parent, int viewType) {
        k.f(parent, "parent");
        BaseItemBinder it = getItemBinder(viewType);
        it.set_context$doraemonkit_release(getContext());
        return it.onCreateViewHolder(parent, viewType);
    }

    /* access modifiers changed from: protected */
    public void convert(@NotNull BaseViewHolder holder, @NotNull Object item) {
        k.f(holder, "holder");
        k.f(item, "item");
        getItemBinder(holder.getItemViewType()).convert(holder, item);
    }

    /* access modifiers changed from: protected */
    public void convert(@NotNull BaseViewHolder holder, @NotNull Object item, @NotNull List<? extends Object> payloads) {
        k.f(holder, "holder");
        k.f(item, "item");
        k.f(payloads, "payloads");
        getItemBinder(holder.getItemViewType()).convert(holder, item, payloads);
    }

    @NotNull
    public BaseItemBinder<Object, BaseViewHolder> getItemBinder(int viewType) {
        BaseItemBinder binder = this.mBinderArray.get(viewType);
        if (binder != null) {
            return binder;
        }
        throw new IllegalStateException(("getItemBinder: viewType '" + viewType + "' no such Binder found，please use addItemBinder() first!").toString());
    }

    @Nullable
    public BaseItemBinder<Object, BaseViewHolder> getItemBinderOrNull(int viewType) {
        BaseItemBinder binder = this.mBinderArray.get(viewType);
        if (!(binder instanceof BaseItemBinder)) {
            return null;
        }
        return binder;
    }

    /* access modifiers changed from: protected */
    public int getDefItemViewType(int position) {
        return findViewType(getData().get(position).getClass());
    }

    /* access modifiers changed from: protected */
    public void bindViewClickListener(@NotNull BaseViewHolder viewHolder, int viewType) {
        k.f(viewHolder, "viewHolder");
        super.bindViewClickListener(viewHolder, viewType);
        bindClick(viewHolder);
        bindChildClick(viewHolder, viewType);
    }

    public void onViewAttachedToWindow(@NotNull BaseViewHolder holder) {
        k.f(holder, "holder");
        super.onViewAttachedToWindow(holder);
        BaseItemBinder<Object, BaseViewHolder> itemBinderOrNull = getItemBinderOrNull(holder.getItemViewType());
        if (itemBinderOrNull != null) {
            itemBinderOrNull.onViewAttachedToWindow(holder);
        }
    }

    public void onViewDetachedFromWindow(@NotNull BaseViewHolder holder) {
        k.f(holder, "holder");
        super.onViewDetachedFromWindow(holder);
        BaseItemBinder<Object, BaseViewHolder> itemBinderOrNull = getItemBinderOrNull(holder.getItemViewType());
        if (itemBinderOrNull != null) {
            itemBinderOrNull.onViewDetachedFromWindow(holder);
        }
    }

    public boolean onFailedToRecycleView(@NotNull BaseViewHolder holder) {
        k.f(holder, "holder");
        BaseItemBinder<Object, BaseViewHolder> itemBinderOrNull = getItemBinderOrNull(holder.getItemViewType());
        if (itemBinderOrNull != null) {
            return itemBinderOrNull.onFailedToRecycleView(holder);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final int findViewType(@NotNull Class<?> clazz) {
        k.f(clazz, "clazz");
        Integer type = this.mTypeMap.get(clazz);
        if (type != null) {
            return type.intValue();
        }
        throw new IllegalStateException(("findViewType: ViewType: " + clazz + " Not Find!").toString());
    }

    /* access modifiers changed from: protected */
    public void bindClick(@NotNull BaseViewHolder viewHolder) {
        k.f(viewHolder, "viewHolder");
        if (getOnItemClickListener() == null) {
            viewHolder.itemView.setOnClickListener(new BaseBinderAdapter$bindClick$1(this, viewHolder));
        }
        if (getOnItemLongClickListener() == null) {
            viewHolder.itemView.setOnLongClickListener(new BaseBinderAdapter$bindClick$2(this, viewHolder));
        }
    }

    /* access modifiers changed from: protected */
    public void bindChildClick(@NotNull BaseViewHolder viewHolder, int viewType) {
        k.f(viewHolder, "viewHolder");
        if (getOnItemChildClickListener() == null) {
            BaseItemBinder provider = getItemBinder(viewType);
            for (Number intValue : provider.getChildClickViewIds()) {
                View it = viewHolder.itemView.findViewById(intValue.intValue());
                if (it != null) {
                    if (!it.isClickable()) {
                        it.setClickable(true);
                    }
                    it.setOnClickListener(new BaseBinderAdapter$bindChildClick$$inlined$forEach$lambda$1(this, viewHolder, provider));
                }
            }
        }
        if (getOnItemChildLongClickListener() == null) {
            BaseItemBinder provider2 = getItemBinder(viewType);
            for (Number intValue2 : provider2.getChildLongClickViewIds()) {
                View it2 = viewHolder.itemView.findViewById(intValue2.intValue());
                if (it2 != null) {
                    if (!it2.isLongClickable()) {
                        it2.setLongClickable(true);
                    }
                    it2.setOnLongClickListener(new BaseBinderAdapter$bindChildClick$$inlined$forEach$lambda$2(this, viewHolder, provider2));
                }
            }
        }
    }

    /* compiled from: BaseBinderAdapter.kt */
    public final class ItemCallback extends DiffUtil.ItemCallback<Object> {
        public ItemCallback() {
        }

        public boolean areItemsTheSame(@NotNull Object oldItem, @NotNull Object newItem) {
            DiffUtil.ItemCallback it;
            k.f(oldItem, "oldItem");
            k.f(newItem, "newItem");
            if (!k.a(oldItem.getClass(), newItem.getClass()) || (it = (DiffUtil.ItemCallback) BaseBinderAdapter.this.classDiffMap.get(oldItem.getClass())) == null) {
                return k.a(oldItem, newItem);
            }
            return it.areItemsTheSame(oldItem, newItem);
        }

        @SuppressLint({"DiffUtilEquals"})
        public boolean areContentsTheSame(@NotNull Object oldItem, @NotNull Object newItem) {
            DiffUtil.ItemCallback it;
            k.f(oldItem, "oldItem");
            k.f(newItem, "newItem");
            if (!k.a(oldItem.getClass(), newItem.getClass()) || (it = (DiffUtil.ItemCallback) BaseBinderAdapter.this.classDiffMap.get(oldItem.getClass())) == null) {
                return true;
            }
            return it.areContentsTheSame(oldItem, newItem);
        }

        @Nullable
        public Object getChangePayload(@NotNull Object oldItem, @NotNull Object newItem) {
            DiffUtil.ItemCallback itemCallback;
            k.f(oldItem, "oldItem");
            k.f(newItem, "newItem");
            if (!k.a(oldItem.getClass(), newItem.getClass()) || (itemCallback = (DiffUtil.ItemCallback) BaseBinderAdapter.this.classDiffMap.get(oldItem.getClass())) == null) {
                return null;
            }
            return itemCallback.getChangePayload(oldItem, newItem);
        }
    }
}
