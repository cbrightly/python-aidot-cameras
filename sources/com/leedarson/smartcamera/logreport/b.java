package com.leedarson.smartcamera.logreport;

import android.content.Context;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.log.tracker.BaseStepBean;
import com.leedarson.log.tracker.a;
import com.leedarson.smartcamera.bean.IPCLiveStepBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.HashMap;

/* compiled from: IPCEventTracker */
public class b extends a<IPCLiveStepBean> {
    public static ChangeQuickRedirect changeQuickRedirect;

    public /* bridge */ /* synthetic */ void o(HashMap hashMap, BaseStepBean baseStepBean) {
        Class[] clsArr = {HashMap.class, BaseStepBean.class};
        if (!PatchProxy.proxy(new Object[]{hashMap, baseStepBean}, this, changeQuickRedirect, false, 10153, clsArr, Void.TYPE).isSupported) {
            q(hashMap, (IPCLiveStepBean) baseStepBean);
        }
    }

    public b(Context context) {
        super(context);
    }

    public void q(HashMap<String, Object> firstFields, IPCLiveStepBean bean) {
        Class[] clsArr = {HashMap.class, IPCLiveStepBean.class};
        if (!PatchProxy.proxy(new Object[]{firstFields, bean}, this, changeQuickRedirect, false, 10152, clsArr, Void.TYPE).isSupported) {
            firstFields.put("code", Integer.valueOf(bean.getCode()));
            firstFields.put(TypedValues.TransitionType.S_DURATION, Long.valueOf(bean.getTotalDuration()));
            if (bean.getDeviceOnlieStatue() >= 0) {
                firstFields.put("deviceOnlieStatue", Integer.valueOf(bean.getDeviceOnlieStatue()));
            }
        }
    }
}
