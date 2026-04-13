package com.leedarson.newui.repoter;

import android.content.Context;
import com.leedarson.log.tracker.BaseStepBean;
import com.leedarson.log.tracker.a;
import com.leedarson.smartcamera.bean.IPCLiveStepBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.HashMap;

/* compiled from: IPCEventKVSTracker */
public class c extends a<IPCLiveStepBean> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ void o(HashMap hashMap, BaseStepBean baseStepBean) {
        Class[] clsArr = {HashMap.class, BaseStepBean.class};
        if (!PatchProxy.proxy(new Object[]{hashMap, baseStepBean}, this, changeQuickRedirect, false, 4489, clsArr, Void.TYPE).isSupported) {
            q(hashMap, (IPCLiveStepBean) baseStepBean);
        }
    }

    public c(Context context) {
        super(context);
    }

    public void q(HashMap<String, Object> firstFields, IPCLiveStepBean bean) {
        Class[] clsArr = {HashMap.class, IPCLiveStepBean.class};
        if (!PatchProxy.proxy(new Object[]{firstFields, bean}, this, changeQuickRedirect, false, 4488, clsArr, Void.TYPE).isSupported) {
            super.o(firstFields, bean);
            firstFields.put("code", Integer.valueOf(bean.getCode()));
        }
    }
}
