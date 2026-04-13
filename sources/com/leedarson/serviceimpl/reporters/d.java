package com.leedarson.serviceimpl.reporters;

import android.content.Context;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.log.tracker.BaseStepBean;
import com.leedarson.log.tracker.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import meshsdk.SIGMesh;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: AutoConnectEventTracker */
public class d extends a<AutoConnectDeviceStepBean> {
    public static ChangeQuickRedirect changeQuickRedirect;
    public List<String> i = new ArrayList();
    public int j;
    public long k;
    public long l;

    public /* bridge */ /* synthetic */ void e(BaseStepBean baseStepBean) {
        if (!PatchProxy.proxy(new Object[]{baseStepBean}, this, changeQuickRedirect, false, 8542, new Class[]{BaseStepBean.class}, Void.TYPE).isSupported) {
            q((AutoConnectDeviceStepBean) baseStepBean);
        }
    }

    public /* bridge */ /* synthetic */ void o(HashMap hashMap, BaseStepBean baseStepBean) {
        Class[] clsArr = {HashMap.class, BaseStepBean.class};
        if (!PatchProxy.proxy(new Object[]{hashMap, baseStepBean}, this, changeQuickRedirect, false, 8541, clsArr, Void.TYPE).isSupported) {
            r(hashMap, (AutoConnectDeviceStepBean) baseStepBean);
        }
    }

    public d(Context context) {
        super(context);
    }

    public boolean k(JSONObject buildBody) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{buildBody}, this, changeQuickRedirect, false, 8537, new Class[]{JSONObject.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            JSONArray message = buildBody.optJSONArray("message");
            return (message == null || message.length() == 0) ? false : true;
        } catch (Exception e) {
            a.b g = timber.log.a.g("MeshAutoConnectTracker");
            g.m("auto_connect_mesh isInitCreateTraceLog exception:" + e.getMessage(), new Object[0]);
        }
    }

    public HashMap<String, Object> f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8538, new Class[0], HashMap.class);
        if (proxy.isSupported) {
            return (HashMap) proxy.result;
        }
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("allDeviceCount", Integer.valueOf(SIGMesh.getInstance().getMeshInfo().nodes.size()));
        fields.put("allDevice", SIGMesh.getInstance().getMeshInfo().getNodeMacs());
        fields.put("deletedDevice", SIGMesh.getInstance().getMeshInfo().getDeletedMacs());
        return fields;
    }

    public void q(AutoConnectDeviceStepBean autoConnectDeviceStepBean) {
        if (!PatchProxy.proxy(new Object[]{autoConnectDeviceStepBean}, this, changeQuickRedirect, false, 8539, new Class[]{AutoConnectDeviceStepBean.class}, Void.TYPE).isSupported) {
            if (autoConnectDeviceStepBean.isStepStartBleConnect()) {
                this.j++;
                if (this.l == 0) {
                    this.l = autoConnectDeviceStepBean._beginTraceTimeSpan;
                }
                this.i.add(autoConnectDeviceStepBean.getMac());
            }
            if (autoConnectDeviceStepBean.isStepBleConnected()) {
                Iterator it = g().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    AutoConnectDeviceStepBean stepBean = (AutoConnectDeviceStepBean) it.next();
                    if (stepBean.isStepStartBleConnect()) {
                        this.k = autoConnectDeviceStepBean._beginTraceTimeSpan - stepBean._beginTraceTimeSpan;
                        break;
                    }
                }
            }
            super.e(autoConnectDeviceStepBean);
        }
    }

    public void r(HashMap<String, Object> firstFields, AutoConnectDeviceStepBean autoConnectDeviceStepBean) {
        if (!PatchProxy.proxy(new Object[]{firstFields, autoConnectDeviceStepBean}, this, changeQuickRedirect, false, 8540, new Class[]{HashMap.class, AutoConnectDeviceStepBean.class}, Void.TYPE).isSupported) {
            super.o(firstFields, autoConnectDeviceStepBean);
            if (this.i.size() > 0) {
                List<String> list = this.i;
                firstFields.put("mac", list.get(list.size() - 1));
            }
            firstFields.put("macs", this.i.toString());
            Iterator it = g().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                AutoConnectDeviceStepBean step = (AutoConnectDeviceStepBean) it.next();
                if (step.isStepStartBleConnect()) {
                    firstFields.put(TypedValues.TransitionType.S_DURATION, Long.valueOf(((AutoConnectDeviceStepBean) g().get(g().size() - 1))._beginTraceTimeSpan - step._beginTraceTimeSpan));
                    break;
                }
            }
            if (!firstFields.containsKey(TypedValues.TransitionType.S_DURATION)) {
                firstFields.put(TypedValues.TransitionType.S_DURATION, 0);
            }
            firstFields.put("code", Integer.valueOf(autoConnectDeviceStepBean.getCode()));
            firstFields.put("desc", autoConnectDeviceStepBean.getStep());
            firstFields.put("bleConnectCount", Integer.valueOf(this.j));
            firstFields.put("bleConnectedDuration", Long.valueOf(this.k));
            if (autoConnectDeviceStepBean.getRssi() != 0) {
                firstFields.put("rssi", Integer.valueOf(autoConnectDeviceStepBean.getRssi()));
            }
        }
    }
}
