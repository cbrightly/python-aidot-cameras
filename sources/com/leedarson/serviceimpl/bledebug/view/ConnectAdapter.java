package com.leedarson.serviceimpl.bledebug.view;

import android.graphics.Color;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.leedarson.serviceimpl.bledebug.bean.ConnectBean;
import com.leedarson.serviceimpl.shake.R$id;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.List;
import meshsdk.util.MeshConstants;

public class ConnectAdapter extends BaseQuickAdapter<ConnectBean, BaseViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String N4 = "#0000FF";
    private final String O4 = "#00FF00";
    private final String P4 = "#FF0000";

    public /* bridge */ /* synthetic */ void d(@NonNull BaseViewHolder baseViewHolder, Object obj) {
        Class[] clsArr = {BaseViewHolder.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, obj}, this, changeQuickRedirect, false, 6869, clsArr, Void.TYPE).isSupported) {
            x(baseViewHolder, (ConnectBean) obj);
        }
    }

    public ConnectAdapter(int layoutResId, @Nullable List<ConnectBean> data) {
        super(layoutResId, data);
    }

    public void x(@NonNull BaseViewHolder baseViewHolder, ConnectBean connectBean) {
        Class[] clsArr = {BaseViewHolder.class, ConnectBean.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, connectBean}, this, changeQuickRedirect, false, 6867, clsArr, Void.TYPE).isSupported) {
            baseViewHolder.setText(R$id.tv_mac, (CharSequence) connectBean.mac);
            TextView tv_state = (TextView) baseViewHolder.findView(R$id.tv_state);
            TextView tv_state_desc = (TextView) baseViewHolder.findView(R$id.tv_state_desc);
            if (connectBean.bluetoothDevice != null) {
                baseViewHolder.setText(R$id.ble_mac, (CharSequence) connectBean.advertisingDevice.c.getAddress());
                int i = R$id.tv_rssi;
                baseViewHolder.setText(i, (CharSequence) connectBean.advertisingDevice.d + "");
            }
            switch (connectBean.state) {
                case -1:
                    tv_state_desc.setText(MeshConstants.AC_STATE_IDLE);
                    return;
                case 0:
                    tv_state_desc.setText("found");
                    tv_state.setBackgroundColor(Color.parseColor("#0000FF"));
                    return;
                case 1:
                    tv_state_desc.setText(MeshConstants.AC_STATE_DEV_CONNECTING);
                    return;
                case 2:
                    tv_state_desc.setText(MeshConstants.AC_STATE_DEV_CONNECTED);
                    tv_state.setBackgroundColor(Color.parseColor("#00FF00"));
                    return;
                case 3:
                    tv_state_desc.setText("connect fail");
                    tv_state.setBackgroundColor(Color.parseColor("#FF0000"));
                    return;
                default:
                    return;
            }
        }
    }
}
