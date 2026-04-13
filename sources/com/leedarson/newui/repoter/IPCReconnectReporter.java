package com.leedarson.newui.repoter;

import android.content.Context;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.IpcDeviceBean;
import com.leedarson.log.mgr.u;
import com.leedarson.serviceimpl.ipcservice.IpcServiceImpl;
import com.leedarson.smartcamera.bean.IPCLiveStepBean;
import com.leedarson.smartcamera.logreport.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import java.util.Locale;
import meshsdk.BaseResp;

public class IPCReconnectReporter {
    public static ChangeQuickRedirect changeQuickRedirect;

    public void b(String str, int code, String str2, ReconnectEventInfoBean config) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, new Integer(code), str2, config}, this, changeQuickRedirect, false, 4569, new Class[]{cls, Integer.TYPE, cls, ReconnectEventInfoBean.class}, Void.TYPE).isSupported) {
            String deviceId = str;
            String message = str2;
            IpcDeviceBean deviceBean = a(deviceId);
            a aVar = new a(BaseApplication.b(), deviceId, message, deviceBean, config);
            Locale locale = Locale.US;
            aVar.j(String.format(locale, "%s:%s", new Object[]{"IPCReconnectReporter", deviceId}), "Live", "LiveReconnectEvent");
            IPCLiveStepBean stepBean = new IPCLiveStepBean("showOffline", code);
            stepBean.setDesc(message);
            u.d().b(aVar);
            u.d().a(String.format(locale, "%s:%s", new Object[]{"IPCReconnectReporter", deviceId}), stepBean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPCReconnectReporter", deviceId}));
        }
    }

    public class a extends b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;
        final /* synthetic */ String j;
        final /* synthetic */ IpcDeviceBean k;
        final /* synthetic */ ReconnectEventInfoBean l;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Context context, String str, String str2, IpcDeviceBean ipcDeviceBean, ReconnectEventInfoBean reconnectEventInfoBean) {
            super(context);
            this.i = str;
            this.j = str2;
            this.k = ipcDeviceBean;
            this.l = reconnectEventInfoBean;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4571, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            fields.put("deviceId", this.i);
            fields.put("owner", "AndroidApp");
            fields.put("description", this.j);
            if (this.k != null) {
                fields.put("hardwareVersion", this.k.hardwareVersion + "");
                fields.put("firmwareVersion", this.k.firmwareVersion + "");
                fields.put("deviceOnlineStatue", this.k.online);
            }
            fields.put("IPCSDKVersion", "");
            fields.put("peerClientId", this.l._peerId);
            long keepNormalTime = 0;
            if (this.l.firstFrameReceiveTime != 0) {
                keepNormalTime = System.currentTimeMillis() - this.l.firstFrameReceiveTime;
            }
            fields.put("keepNormalDuration", Long.valueOf(keepNormalTime));
            fields.put("desc", this.l.leaveReason);
            if (keepNormalTime == 0) {
                fields.put("code", Integer.valueOf(BaseResp.ERR_MSG_SEND_FAIL));
            } else if (TextUtils.isEmpty(this.l.leaveReason)) {
                fields.put("code", Integer.valueOf(BaseResp.ERR_MSG_SEND_FAIL));
            } else {
                fields.put("code", 200);
            }
            fields.put(TypedValues.TransitionType.S_DURATION, Long.valueOf(System.currentTimeMillis() - this.l.startConnectTime));
            return fields;
        }
    }

    public static class ReconnectEventInfoBean {
        public static ChangeQuickRedirect changeQuickRedirect;
        public String _peerId = "";
        public long firstFrameReceiveTime = 0;
        public String leaveReason = "";
        public long startConnectTime = 0;

        public void init() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4572, new Class[0], Void.TYPE).isSupported) {
                this.startConnectTime = System.currentTimeMillis();
                this.firstFrameReceiveTime = 0;
                this._peerId = "";
                this.leaveReason = "";
            }
        }
    }

    private IpcDeviceBean a(String _deviceId) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{_deviceId}, this, changeQuickRedirect, false, 4570, new Class[]{String.class}, IpcDeviceBean.class);
        if (proxy.isSupported) {
            return (IpcDeviceBean) proxy.result;
        }
        for (int i = 0; i < IpcServiceImpl.a.size(); i++) {
            if (_deviceId != null && _deviceId.equals(IpcServiceImpl.a.get(i).id)) {
                return IpcServiceImpl.a.get(i);
            }
        }
        return null;
    }
}
