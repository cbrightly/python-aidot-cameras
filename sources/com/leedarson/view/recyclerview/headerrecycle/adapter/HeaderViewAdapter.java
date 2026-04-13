package com.leedarson.view.recyclerview.headerrecycle.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.List;

public class HeaderViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private RecyclerView.Adapter a;
    private final List<a> b = new ArrayList();
    private final List<a> c = new ArrayList();
    private RecyclerView.AdapterDataObserver d;

    public HeaderViewAdapter(RecyclerView.Adapter adapter) {
        AnonymousClass1 r0 = new RecyclerView.AdapterDataObserver() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public void onChanged() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12052, new Class[0], Void.TYPE).isSupported) {
                    HeaderViewAdapter.this.notifyDataSetChanged();
                }
            }

            public void onItemRangeChanged(int positionStart, int itemCount) {
                Object[] objArr = {new Integer(positionStart), new Integer(itemCount)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                Class cls = Integer.TYPE;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12053, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                    super.onItemRangeChanged(positionStart, itemCount);
                }
            }

            public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                Object[] objArr = {new Integer(positionStart), new Integer(itemCount), payload};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                Class cls = Integer.TYPE;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12054, new Class[]{cls, cls, Object.class}, Void.TYPE).isSupported) {
                    HeaderViewAdapter headerViewAdapter = HeaderViewAdapter.this;
                    headerViewAdapter.notifyItemRangeChanged(headerViewAdapter.g() + positionStart, itemCount, payload);
                }
            }

            public void onItemRangeInserted(int positionStart, int itemCount) {
                Object[] objArr = {new Integer(positionStart), new Integer(itemCount)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                Class cls = Integer.TYPE;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12055, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                    HeaderViewAdapter headerViewAdapter = HeaderViewAdapter.this;
                    headerViewAdapter.notifyItemRangeInserted(headerViewAdapter.g() + positionStart, itemCount);
                }
            }

            public void onItemRangeRemoved(int positionStart, int itemCount) {
                Object[] objArr = {new Integer(positionStart), new Integer(itemCount)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                Class cls = Integer.TYPE;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12056, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                    HeaderViewAdapter headerViewAdapter = HeaderViewAdapter.this;
                    headerViewAdapter.notifyItemRangeRemoved(headerViewAdapter.g() + positionStart, itemCount);
                }
            }

            public void onItemRangeMoved(int fromPosition, int toPosition, int i) {
                Object[] objArr = {new Integer(fromPosition), new Integer(toPosition), new Integer(i)};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                Class cls = Integer.TYPE;
                Class[] clsArr = {cls, cls, cls};
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12057, clsArr, Void.TYPE).isSupported) {
                    HeaderViewAdapter headerViewAdapter = HeaderViewAdapter.this;
                    headerViewAdapter.notifyItemMoved(headerViewAdapter.g() + fromPosition, HeaderViewAdapter.this.g() + toPosition);
                }
            }
        };
        this.d = r0;
        this.a = adapter;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(r0);
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parent, new Integer(viewType)}, this, changeQuickRedirect, false, 12027, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        if (proxy.isSupported) {
            return (RecyclerView.ViewHolder) proxy.result;
        }
        Log.d("TAG", "onCreateViewHolder: " + viewType);
        View view = c(viewType);
        if (view != null) {
            return new ViewHolder(view);
        }
        return this.a.onCreateViewHolder(parent, viewType);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 12028, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (!j(position) && !i(position)) {
                this.a.onBindViewHolder(holder, position - g());
            }
        }
    }

    public int getItemCount() {
        int i = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12029, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int size = this.b.size() + this.c.size();
        RecyclerView.Adapter adapter = this.a;
        if (adapter != null) {
            i = adapter.getItemCount();
        }
        return size + i;
    }

    public int getItemViewType(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12030, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (j(position)) {
            return this.b.get(position).b;
        }
        if (i(position)) {
            return this.c.get((position - this.b.size()) - this.a.getItemCount()).b;
        }
        return this.a.getItemViewType(position - g());
    }

    public void l(RecyclerView.Adapter adapter) {
        if (!PatchProxy.proxy(new Object[]{adapter}, this, changeQuickRedirect, false, 12031, new Class[]{RecyclerView.Adapter.class}, Void.TYPE).isSupported) {
            if (!(adapter instanceof HeaderViewAdapter)) {
                this.a = adapter;
                if (adapter != null) {
                    adapter.registerAdapterDataObserver(this.d);
                }
                notifyDataSetChanged();
                return;
            }
            throw new IllegalArgumentException("Cannot wrap a HeaderViewAdapter");
        }
    }

    public RecyclerView.Adapter e() {
        return this.a;
    }

    public boolean j(int position) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 12032, new Class[]{Integer.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return position < g();
    }

    public boolean i(int position) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 12033, new Class[]{Integer.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return getItemCount() - position <= f();
    }

    public int g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12034, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.b.size();
    }

    public int f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12035, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.c.size();
    }

    public void a(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 12036, new Class[]{View.class}, Void.TYPE).isSupported) {
            b(view, d());
        }
    }

    private void b(View view, int viewType) {
        if (!PatchProxy.proxy(new Object[]{view, new Integer(viewType)}, this, changeQuickRedirect, false, 12037, new Class[]{View.class, Integer.TYPE}, Void.TYPE).isSupported) {
            a info = new a();
            info.a = view;
            info.b = viewType;
            this.b.add(info);
            notifyDataSetChanged();
        }
    }

    private int d() {
        int viewType;
        boolean isExist;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12042, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int count = getItemCount();
        do {
            viewType = ((int) (Math.random() * 2.147483647E9d)) + 1;
            isExist = false;
            int i = 0;
            while (true) {
                if (i >= count) {
                    break;
                } else if (viewType == getItemViewType(i)) {
                    isExist = true;
                    continue;
                    break;
                } else {
                    i++;
                }
            }
        } while (isExist);
        return viewType;
    }

    private View c(int viewType) {
        Object[] objArr = {new Integer(viewType)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 12043, new Class[]{Integer.TYPE}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        for (a info : this.b) {
            if (info.b == viewType) {
                return info.a;
            }
        }
        for (a info2 : this.c) {
            if (info2.b == viewType) {
                return info2.a;
            }
        }
        return null;
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 12044, new Class[]{RecyclerView.ViewHolder.class}, Void.TYPE).isSupported) {
            if (holder instanceof ViewHolder) {
                super.onViewAttachedToWindow(holder);
            } else {
                this.a.onViewAttachedToWindow(holder);
            }
            if (k(holder)) {
                h(holder, holder.getLayoutPosition());
            }
        }
    }

    private boolean k(RecyclerView.ViewHolder holder) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 12045, new Class[]{RecyclerView.ViewHolder.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        return layoutParams != null && (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams);
    }

    private void h(RecyclerView.ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 12046, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (j(position) || i(position)) {
                ((StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams()).setFullSpan(true);
            }
        }
    }

    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 12047, new Class[]{RecyclerView.ViewHolder.class}, Void.TYPE).isSupported) {
            if (holder instanceof ViewHolder) {
                super.onViewDetachedFromWindow(holder);
            } else {
                this.a.onViewDetachedFromWindow(holder);
            }
        }
    }

    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 12048, new Class[]{RecyclerView.ViewHolder.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (holder instanceof ViewHolder) {
            return super.onFailedToRecycleView(holder);
        }
        return this.a.onFailedToRecycleView(holder);
    }

    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter;
        if (!PatchProxy.proxy(new Object[]{recyclerView}, this, changeQuickRedirect, false, 12049, new Class[]{RecyclerView.class}, Void.TYPE).isSupported && (adapter = this.a) != null) {
            adapter.onAttachedToRecyclerView(recyclerView);
        }
    }

    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        RecyclerView.Adapter adapter;
        if (!PatchProxy.proxy(new Object[]{recyclerView}, this, changeQuickRedirect, false, 12050, new Class[]{RecyclerView.class}, Void.TYPE).isSupported && (adapter = this.a) != null) {
            adapter.onDetachedFromRecyclerView(recyclerView);
        }
    }

    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (!PatchProxy.proxy(new Object[]{holder}, this, changeQuickRedirect, false, 12051, new Class[]{RecyclerView.ViewHolder.class}, Void.TYPE).isSupported) {
            if (holder instanceof ViewHolder) {
                super.onViewRecycled(holder);
            } else {
                this.a.onViewRecycled(holder);
            }
        }
    }

    public class a {
        View a;
        int b;

        private a() {
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
