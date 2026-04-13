package com.leedarson.newui.pages.sdcard_edit;

import androidx.recyclerview.widget.GridLayoutManager;
import com.leedarson.bean.SDRecord;
import com.leedarson.newui.pages.adapters.SDCardEventEditAdapter;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;

/* compiled from: SDCardVideoManageAct.kt */
public final class SDCardVideoManageAct$initViewOfRecyclerView$1 extends GridLayoutManager.SpanSizeLookup {
    public static ChangeQuickRedirect changeQuickRedirect;
    final /* synthetic */ SDCardVideoManageAct a;

    SDCardVideoManageAct$initViewOfRecyclerView$1(SDCardVideoManageAct $receiver) {
        this.a = $receiver;
    }

    public int getSpanSize(int position) {
        ArrayList<SDRecord> a2;
        SDRecord sDRecord;
        boolean z = false;
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4400, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        SDCardEventEditAdapter m0 = this.a.m0();
        if (!(m0 == null || (a2 = m0.a()) == null || (sDRecord = a2.get(position)) == null || sDRecord.itemType != 1)) {
            z = true;
        }
        if (z) {
            return 3;
        }
        return 1;
    }
}
