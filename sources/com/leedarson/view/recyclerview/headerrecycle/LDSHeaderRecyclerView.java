package com.leedarson.view.recyclerview.headerrecycle;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.view.recyclerview.LDSSlidingConflictRecyclerView;
import com.leedarson.view.recyclerview.headerrecycle.adapter.HeaderViewAdapter;
import com.leedarson.view.recyclerview.headerrecycle.manager.HeaderViewGridLayoutManager;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class LDSHeaderRecyclerView extends LDSSlidingConflictRecyclerView {
    public static ChangeQuickRedirect changeQuickRedirect;
    private HeaderViewAdapter y;

    public LDSHeaderRecyclerView(Context context) {
        super(context);
        d();
    }

    public LDSHeaderRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        d();
    }

    public LDSHeaderRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        d();
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        if (!PatchProxy.proxy(new Object[]{layout}, this, changeQuickRedirect, false, 12017, new Class[]{RecyclerView.LayoutManager.class}, Void.TYPE).isSupported) {
            if (!(layout instanceof GridLayoutManager) || (layout instanceof HeaderViewGridLayoutManager)) {
                super.setLayoutManager(layout);
            } else {
                super.setLayoutManager(new HeaderViewGridLayoutManager(getContext(), ((GridLayoutManager) layout).getSpanCount(), this.y));
            }
        }
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12018, new Class[0], Void.TYPE).isSupported) {
            HeaderViewAdapter headerViewAdapter = new HeaderViewAdapter(super.getAdapter());
            this.y = headerViewAdapter;
            super.setAdapter(headerViewAdapter);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (!PatchProxy.proxy(new Object[]{adapter}, this, changeQuickRedirect, false, 12019, new Class[]{RecyclerView.Adapter.class}, Void.TYPE).isSupported) {
            this.y.l(adapter);
        }
    }

    public RecyclerView.Adapter getAdapter() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12020, new Class[0], RecyclerView.Adapter.class);
        return proxy.isSupported ? (RecyclerView.Adapter) proxy.result : this.y.e();
    }

    public int getHeadersCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12021, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.y.g();
    }

    public int getFootersCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12022, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.y.f();
    }

    public void c(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 12023, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.y.a(view);
        }
    }
}
