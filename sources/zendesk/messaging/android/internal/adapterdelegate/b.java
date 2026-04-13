package zendesk.messaging.android.internal.adapterdelegate;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ListItemAdapterDelegate.kt */
public abstract class b<I extends T, T, VH extends RecyclerView.ViewHolder> extends a<List<? extends T>> {
    /* access modifiers changed from: protected */
    public abstract boolean d(T t, @NotNull List<? extends T> list, int i);

    /* access modifiers changed from: protected */
    public abstract void f(I i, @NotNull VH vh, @NotNull List<? extends Object> list);

    /* renamed from: e */
    public boolean a(@NotNull List<? extends T> item, int position) {
        k.e(item, "item");
        return d(item.get(position), item, position);
    }

    /* renamed from: g */
    public void b(@NotNull List<? extends T> item, int position, @NotNull RecyclerView.ViewHolder holder, @NotNull List<? extends Object> payloads) {
        k.e(item, "item");
        k.e(holder, "holder");
        k.e(payloads, "payloads");
        f(item.get(position), holder, payloads);
    }
}
