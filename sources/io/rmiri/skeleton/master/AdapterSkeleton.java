package io.rmiri.skeleton.master;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import io.rmiri.skeleton.utils.a;
import java.util.Collections;
import java.util.List;

public abstract class AdapterSkeleton<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> a = Collections.emptyList();
    protected a b = new a();

    public int getItemCount() {
        if (!this.b.c()) {
            a.a("getItemCount ==> items.size(): " + this.a.size());
            return this.a.size();
        } else if (this.b.a() == 0.0f) {
            a.a("getItemCount ==> getItemHeight() is zero : 1");
            return 1;
        } else {
            a.a("getItemCount ==> getNumberItemShow: " + this.b.b());
            return this.b.b();
        }
    }
}
