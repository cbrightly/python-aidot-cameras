package zendesk.messaging.android.internal.adapterdelegate;

import android.view.View;
import android.view.ViewGroup;
import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: AdapterDelegatesManager.kt */
public final class AdapterDelegatesManager<T> {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private static final List<Object> b = q.g();
    @NotNull
    private SparseArrayCompat<a<T>> c = new SparseArrayCompat<>();

    public AdapterDelegatesManager(@NotNull a<T>... delegates) {
        k.e(delegates, "delegates");
        int length = delegates.length;
        int i = 0;
        while (i < length) {
            a element = delegates[i];
            i++;
            a(element);
        }
    }

    private final AdapterDelegatesManager<T> a(a<T> delegate) {
        int viewType = this.c.size();
        while (this.c.get(viewType) != null) {
            viewType++;
        }
        this.c.put(viewType, delegate);
        return this;
    }

    public final int c(T items, int position) {
        String itemString;
        if (items == null) {
            zendesk.logger.a.d("AdapterDelegatesManager", "Items data source is null!", new Object[0]);
        }
        int delegatesCount = this.c.size();
        if (delegatesCount > 0) {
            int i = 0;
            do {
                int i2 = i;
                boolean z = true;
                i++;
                a delegate = this.c.valueAt(i2);
                if (delegate == null || !delegate.a(items, position)) {
                    z = false;
                }
                if (z) {
                    return this.c.keyAt(i2);
                }
            } while (i < delegatesCount);
        }
        if (items instanceof List) {
            itemString = "No AdapterDelegate added that matches item=" + String.valueOf(((List) items).get(position)) + " at position=" + position + " in data source";
        } else {
            itemString = "No AdapterDelegate added for item at position=" + position + ". items=" + items;
        }
        zendesk.logger.a.d("AdapterDelegatesManager", itemString, new Object[0]);
        return 0;
    }

    @NotNull
    public final RecyclerView.ViewHolder e(@NotNull ViewGroup parent, int viewType) {
        k.e(parent, "parent");
        a delegate = b(viewType);
        RecyclerView.ViewHolder c2 = delegate == null ? null : delegate.c(parent);
        return c2 == null ? new DefaultViewHolder(parent) : c2;
    }

    public final void d(T items, int position, @NotNull RecyclerView.ViewHolder holder, @Nullable List<? extends Object> payloads) {
        x xVar;
        k.e(holder, "holder");
        a delegate = b(holder.getItemViewType());
        if (delegate == null) {
            xVar = null;
        } else {
            delegate.b(items, position, holder, payloads == null ? b : payloads);
            xVar = x.a;
        }
        if (xVar == null) {
            zendesk.logger.a.d("AdapterDelegatesManager", "No delegate found for item at position = " + position + " for viewType = " + holder.getItemViewType(), new Object[0]);
        }
    }

    private final a<T> b(int viewType) {
        return this.c.get(viewType);
    }

    /* compiled from: AdapterDelegatesManager.kt */
    public static final class DefaultViewHolder extends RecyclerView.ViewHolder {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public DefaultViewHolder(@NotNull View view) {
            super(view);
            k.e(view, "view");
        }
    }

    /* compiled from: AdapterDelegatesManager.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
