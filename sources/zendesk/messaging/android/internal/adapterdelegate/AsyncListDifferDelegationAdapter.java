package zendesk.messaging.android.internal.adapterdelegate;

import android.view.ViewGroup;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: AsyncListDifferDelegationAdapter.kt */
public class AsyncListDifferDelegationAdapter<T> extends ListAdapter<T, RecyclerView.ViewHolder> {
    @NotNull
    private final AdapterDelegatesManager<List<T>> a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AsyncListDifferDelegationAdapter(@NotNull DiffUtil.ItemCallback<T> diffCallback, @NotNull AdapterDelegatesManager<List<T>> delegatesManager) {
        super(diffCallback);
        k.e(diffCallback, "diffCallback");
        k.e(delegatesManager, "delegatesManager");
        this.a = delegatesManager;
    }

    @NotNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        k.e(parent, "parent");
        return this.a.e(parent, viewType);
    }

    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
        k.e(holder, "holder");
        AdapterDelegatesManager<List<T>> adapterDelegatesManager = this.a;
        List currentList = getCurrentList();
        k.d(currentList, "currentList");
        adapterDelegatesManager.d(currentList, position, holder, (List<? extends Object>) null);
    }

    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position, @NotNull List<? extends Object> payloads) {
        k.e(holder, "holder");
        k.e(payloads, "payloads");
        AdapterDelegatesManager<List<T>> adapterDelegatesManager = this.a;
        List currentList = getCurrentList();
        k.d(currentList, "currentList");
        adapterDelegatesManager.d(currentList, position, holder, payloads);
    }

    public int getItemViewType(int position) {
        AdapterDelegatesManager<List<T>> adapterDelegatesManager = this.a;
        List currentList = getCurrentList();
        k.d(currentList, "currentList");
        return adapterDelegatesManager.c(currentList, position);
    }
}
