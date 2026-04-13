package com.didichuxing.doraemonkit.widget.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.widget.recyclerview.AbsViewBinder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbsRecyclerAdapter<T extends AbsViewBinder, V> extends RecyclerView.Adapter<T> {
    private static final String TAG = "AbsRecyclerAdapter";
    /* access modifiers changed from: protected */
    public Context mContext;
    private LayoutInflater mInflater;
    /* access modifiers changed from: protected */
    public List<V> mList;

    /* access modifiers changed from: protected */
    public abstract View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int i);

    /* access modifiers changed from: protected */
    public abstract T createViewHolder(View view, int i);

    public AbsRecyclerAdapter(Context context) {
        if (context == null) {
            LogHelper.e(TAG, "Context should not be null");
            return;
        }
        this.mContext = context;
        this.mList = new ArrayList();
        this.mInflater = LayoutInflater.from(context);
    }

    public final T onCreateViewHolder(ViewGroup parent, int viewType) {
        return createViewHolder(createView(this.mInflater, parent, viewType), viewType);
    }

    public final void onBindViewHolder(T holder, int position) {
        V data = this.mList.get(position);
        holder.setData(data);
        holder.bind(data, position);
    }

    public int getItemCount() {
        return this.mList.size();
    }

    public void append(V item) {
        if (item != null) {
            this.mList.add(item);
            notifyDataSetChanged();
        }
    }

    public void append(V item, int position) {
        if (item != null) {
            if (position < 0) {
                position = 0;
            } else if (position > this.mList.size()) {
                position = this.mList.size();
            }
            this.mList.add(position, item);
            notifyDataSetChanged();
        }
    }

    public final void append(Collection<V> items) {
        if (items != null && items.size() != 0) {
            this.mList.addAll(items);
            notifyDataSetChanged();
        }
    }

    public final void clear() {
        if (!this.mList.isEmpty()) {
            this.mList.clear();
            notifyDataSetChanged();
        }
    }

    public final void remove(V item) {
        if (item != null && this.mList.contains(item)) {
            this.mList.remove(item);
            notifyDataSetChanged();
        }
    }

    public final void remove(int index) {
        if (index < this.mList.size()) {
            this.mList.remove(index);
            notifyDataSetChanged();
        }
    }

    public final void remove(Collection<V> items) {
        if (items != null && items.size() != 0 && this.mList.removeAll(items)) {
            notifyDataSetChanged();
        }
    }

    public void setData(Collection<V> items) {
        if (items != null && items.size() != 0) {
            if (this.mList.size() > 0) {
                this.mList.clear();
            }
            this.mList.addAll(items);
            notifyDataSetChanged();
        }
    }

    public List<V> getData() {
        return new ArrayList(this.mList);
    }
}
