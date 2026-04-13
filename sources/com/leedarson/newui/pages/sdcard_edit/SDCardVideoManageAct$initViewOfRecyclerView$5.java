package com.leedarson.newui.pages.sdcard_edit;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: SDCardVideoManageAct.kt */
public final class SDCardVideoManageAct$initViewOfRecyclerView$5 extends RecyclerView.OnScrollListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    final /* synthetic */ SDCardVideoManageAct a;

    SDCardVideoManageAct$initViewOfRecyclerView$5(SDCardVideoManageAct $receiver) {
        this.a = $receiver;
    }

    public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
        if (!PatchProxy.proxy(new Object[]{recyclerView, new Integer(newState)}, this, changeQuickRedirect, false, 4407, new Class[]{RecyclerView.class, Integer.TYPE}, Void.TYPE).isSupported) {
            k.e(recyclerView, "recyclerView");
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == 0 && (recyclerView.getLayoutManager() instanceof GridLayoutManager)) {
                SDCardVideoManageAct.k0(this.a);
            }
        }
    }
}
