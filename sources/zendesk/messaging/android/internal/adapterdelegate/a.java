package zendesk.messaging.android.internal.adapterdelegate;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* compiled from: AdapterDelegate.kt */
public abstract class a<T> {
    public abstract boolean a(T t, int i);

    public abstract void b(T t, int i, @NotNull RecyclerView.ViewHolder viewHolder, @NotNull List<? extends Object> list);

    @NotNull
    public abstract RecyclerView.ViewHolder c(@NotNull ViewGroup viewGroup);
}
