package com.leedarson.serviceimpl.reporters;

import android.content.Context;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.log.tracker.BaseStepBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.Encipher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import meshsdk.SIGMesh;
import meshsdk.model.MeshAppKey;
import meshsdk.model.MeshNetKey;
import meshsdk.model.json.AddDevicesBean;
import org.spongycastle.pqc.math.linearalgebra.ByteUtils;

/* compiled from: AddDeviceEventTracker */
public class a extends com.leedarson.log.tracker.a<AddDeviceStepBean> {
    public static ChangeQuickRedirect changeQuickRedirect;
    public AddDevicesBean i;
    public int j;
    public long k;
    public long l;
    public long m;
    public int n;
    public int o;
    public int p;
    public List<String> q = new ArrayList();

    public /* bridge */ /* synthetic */ void e(BaseStepBean baseStepBean) {
        if (!PatchProxy.proxy(new Object[]{baseStepBean}, this, changeQuickRedirect, false, 8517, new Class[]{BaseStepBean.class}, Void.TYPE).isSupported) {
            q((AddDeviceStepBean) baseStepBean);
        }
    }

    public /* bridge */ /* synthetic */ void o(HashMap hashMap, BaseStepBean baseStepBean) {
        Class[] clsArr = {HashMap.class, BaseStepBean.class};
        if (!PatchProxy.proxy(new Object[]{hashMap, baseStepBean}, this, changeQuickRedirect, false, 8516, clsArr, Void.TYPE).isSupported) {
            r(hashMap, (AddDeviceStepBean) baseStepBean);
        }
    }

    public a(Context context, AddDevicesBean addDeviceParam) {
        super(context);
        this.i = addDeviceParam;
    }

    public HashMap<String, Object> f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8513, new Class[0], HashMap.class);
        if (proxy.isSupported) {
            return (HashMap) proxy.result;
        }
        HashMap<String, Object> fields = new HashMap<>();
        fields.put("modelId", this.i.getModelId());
        fields.put("mac", this.i.getMac());
        return fields;
    }

    public void q(AddDeviceStepBean addDeviceStepBean) {
        if (!PatchProxy.proxy(new Object[]{addDeviceStepBean}, this, changeQuickRedirect, false, 8514, new Class[]{AddDeviceStepBean.class}, Void.TYPE).isSupported) {
            if (addDeviceStepBean.isStepRequestOob()) {
                this.k = addDeviceStepBean.duration;
            }
            if (addDeviceStepBean.isStepPostDevice()) {
                this.l = addDeviceStepBean.duration;
            }
            if (addDeviceStepBean.isStepSetFilterRetry()) {
                this.n++;
            }
            if (addDeviceStepBean.isStepGetCompositionDataRetry()) {
                this.o++;
            }
            if (addDeviceStepBean.isStepAddAppKeyRetry()) {
                this.p++;
            }
            if (g() != null && g().size() > 0) {
                addDeviceStepBean.setDuration(addDeviceStepBean._beginTraceTimeSpan - ((AddDeviceStepBean) g().get(g().size() - 1))._beginTraceTimeSpan);
            }
            if (addDeviceStepBean.isStepStartBleConnect()) {
                this.j++;
            }
            if (addDeviceStepBean.isStepBleConnected()) {
                Iterator it = g().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    AddDeviceStepBean stepBean = (AddDeviceStepBean) it.next();
                    if (stepBean.isStepStartBleConnect()) {
                        this.m = addDeviceStepBean._beginTraceTimeSpan - stepBean._beginTraceTimeSpan;
                        break;
                    }
                }
            }
            int code = addDeviceStepBean.getCode();
            e eVar = e.CODE_SUCCESS;
            if (code != eVar.getCode()) {
                StringBuilder builder = new StringBuilder();
                builder.append(addDeviceStepBean.getCode());
                if (addDeviceStepBean.getSdkErrorCode() != eVar.getCode()) {
                    builder.append("_");
                    builder.append(addDeviceStepBean.getSdkErrorCode());
                }
                this.q.add(builder.toString());
            }
            super.e(addDeviceStepBean);
        }
    }

    public void r(HashMap<String, Object> firstFields, AddDeviceStepBean bean) {
        if (!PatchProxy.proxy(new Object[]{firstFields, bean}, this, changeQuickRedirect, false, 8515, new Class[]{HashMap.class, AddDeviceStepBean.class}, Void.TYPE).isSupported) {
            firstFields.put("code", Integer.valueOf(bean.getCode()));
            firstFields.put("desc", !TextUtils.isEmpty(bean.getDesc()) ? bean.getDesc() : bean.getStep());
            if (bean.getRssi() != 0) {
                firstFields.put("rssi", Integer.valueOf(bean.getRssi()));
            }
            if (g() != null && g().size() > 0) {
                firstFields.put(TypedValues.TransitionType.S_DURATION, Long.valueOf(((AddDeviceStepBean) g().get(g().size() - 1))._beginTraceTimeSpan - ((AddDeviceStepBean) g().get(0))._beginTraceTimeSpan));
            }
            firstFields.put("bleConnectedDuration", Long.valueOf(this.m));
            firstFields.put("bleConnectCount", Integer.valueOf(this.j));
            firstFields.put("deviceStatus", this.q);
            firstFields.put("bindSetFilterRetryCount", Integer.valueOf(this.n));
            firstFields.put("bindGetCompositionDataRetryCount", Integer.valueOf(this.o));
            firstFields.put("bindAddAppKeyRetryCount", Integer.valueOf(this.p));
            List<MeshNetKey> netKeys = SIGMesh.getInstance().getMeshInfo().meshNetKeyList;
            if (netKeys != null && netKeys.size() > 0) {
                firstFields.put("networkKey", ByteUtils.e(netKeys.get(0).getKey()));
                firstFields.put("deviceNetworkKey", ByteUtils.e(Encipher.o(netKeys.get(0).getKey())));
            }
            List<MeshAppKey> appKeys = SIGMesh.getInstance().getMeshInfo().appKeyList;
            if (appKeys != null && appKeys.size() > 0) {
                firstFields.put("appkey", ByteUtils.e(appKeys.get(0).getKey()));
            }
            long j2 = this.k;
            if (j2 > 0) {
                firstFields.put("getOOBDuration", Long.valueOf(j2));
            }
            long j3 = this.l;
            if (j3 > 0) {
                firstFields.put("postDeviceDuration", Long.valueOf(j3));
            }
        }
    }
}
