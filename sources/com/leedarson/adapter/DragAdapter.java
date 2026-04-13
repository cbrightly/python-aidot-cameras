package com.leedarson.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.d;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.bean.DeviceBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class DragAdapter extends BaseQuickAdapter<DeviceBean, BaseViewHolder> implements d {
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ void d(@NotNull BaseViewHolder baseViewHolder, @NotNull Object obj) {
        Class[] clsArr = {BaseViewHolder.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, obj}, this, changeQuickRedirect, false, 10, clsArr, Void.TYPE).isSupported) {
            x(baseViewHolder, (DeviceBean) obj);
        }
    }

    public DragAdapter(List<DeviceBean> data) {
        super(R$layout.item_draggable_view, data);
    }

    public void x(@NotNull BaseViewHolder helper, @NotNull DeviceBean item) {
        Class[] clsArr = {BaseViewHolder.class, DeviceBean.class};
        if (!PatchProxy.proxy(new Object[]{helper, item}, this, changeQuickRedirect, false, 9, clsArr, Void.TYPE).isSupported) {
            helper.setText(R$id.f39tv, (CharSequence) item.getName());
        }
    }
}
