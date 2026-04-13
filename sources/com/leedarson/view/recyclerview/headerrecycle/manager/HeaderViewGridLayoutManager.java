package com.leedarson.view.recyclerview.headerrecycle.manager;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import com.leedarson.view.recyclerview.headerrecycle.adapter.HeaderViewAdapter;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class HeaderViewGridLayoutManager extends GridLayoutManager {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public HeaderViewAdapter a;

    public HeaderViewGridLayoutManager(Context context, int spanCount, HeaderViewAdapter adapter) {
        super(context, spanCount);
        this.a = adapter;
        c();
    }

    private void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12058, new Class[0], Void.TYPE).isSupported) {
            super.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public int getSpanSize(int position) {
                    Object[] objArr = {new Integer(position)};
                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                    Class cls = Integer.TYPE;
                    PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12059, new Class[]{cls}, cls);
                    if (proxy.isSupported) {
                        return ((Integer) proxy.result).intValue();
                    }
                    if (HeaderViewGridLayoutManager.this.a == null) {
                        return 1;
                    }
                    if (HeaderViewGridLayoutManager.this.a.j(position) || HeaderViewGridLayoutManager.this.a.i(position)) {
                        return HeaderViewGridLayoutManager.this.getSpanCount();
                    }
                    return HeaderViewGridLayoutManager.this.b(position - HeaderViewGridLayoutManager.this.a.g());
                }
            });
        }
    }

    public int b(int position) {
        return 1;
    }

    public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
    }
}
