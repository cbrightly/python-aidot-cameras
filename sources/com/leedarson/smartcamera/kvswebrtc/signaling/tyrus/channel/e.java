package com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel;

import com.leedarson.base.application.BaseApplication;
import com.leedarson.serviceinterface.TcpService;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.prefs.SpExtendHelper;
import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.a;
import com.leedarson.smartcamera.kvswebrtc.signaling.tyrus.channel.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TcpSignalSwitchChannel */
public class e implements a {
    public static ChangeQuickRedirect changeQuickRedirect;
    TcpService a = ((TcpService) com.alibaba.android.arouter.launcher.a.c().g(TcpService.class));

    static /* synthetic */ void d(e x0, String x1) {
        Class[] clsArr = {e.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10084, clsArr, Void.TYPE).isSupported) {
            x0.c(x1);
        }
    }

    static /* synthetic */ void e(e x0, String x1) {
        Class[] clsArr = {e.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 10085, clsArr, Void.TYPE).isSupported) {
            x0.b(x1);
        }
    }

    public void a(String str, String str2, b bVar, LdsSignalSendConfigBean ldsSignalSendConfigBean, a.C0173a aVar) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, bVar, ldsSignalSendConfigBean, aVar}, this, changeQuickRedirect, false, 10081, new Class[]{cls, cls, b.class, LdsSignalSendConfigBean.class, a.C0173a.class}, Void.TYPE).isSupported) {
            String messageBody = str2;
            LdsSignalSendConfigBean configBean = ldsSignalSendConfigBean;
            String deviceId = str;
            b _handler = bVar;
            a.C0173a msgType = aVar;
            try {
                b("tcp sendMessageTo deviceId " + deviceId);
                JSONObject _preSendMsg = null;
                try {
                    _preSendMsg = new JSONObject(messageBody);
                    if (!_preSendMsg.has("srcAddr")) {
                        String userId = SharePreferenceUtils.getPrefString(BaseApplication.b(), "userId", "");
                        _preSendMsg.put("srcAddr", (Object) "0." + userId);
                    }
                    if (!_preSendMsg.has("seq")) {
                        _preSendMsg.put("seq", (Object) SpExtendHelper.generateNextSeq());
                    }
                    if (!_preSendMsg.has("tst")) {
                        _preSendMsg.put("tst", System.currentTimeMillis());
                    }
                    if (!_preSendMsg.has("tid")) {
                        _preSendMsg.put("tid", (Object) SpExtendHelper.generateTid());
                    }
                } catch (Exception e) {
                }
                String sendMsg = _preSendMsg.toString();
                this.a.nativeSendMessageToTarget(deviceId, sendMsg, configBean.timeOutLimitOfMs, msgType == a.C0173a.OFFER ? 1 : 2, new a(sendMsg, _handler, deviceId));
            } catch (Exception e2) {
            }
        }
    }

    /* compiled from: TcpSignalSwitchChannel */
    public class a implements TcpService.INativeTcpSendMsgHandler {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ b b;
        final /* synthetic */ String c;

        a(String str, b bVar, String str2) {
            this.a = str;
            this.b = bVar;
            this.c = str2;
        }

        public void onSendMessageFail(String desc, int code) {
            if (!PatchProxy.proxy(new Object[]{desc, new Integer(code)}, this, changeQuickRedirect, false, 10086, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.d(eVar, "tcp sendMessageTo onSendMessageFail code : " + code + " desc" + desc + " sendMsg:" + this.a.toString());
                this.b.b(code, this.c, desc, b.a.TCP);
            }
        }

        public void onSendMessageSuccess(String msg, int code) {
            if (!PatchProxy.proxy(new Object[]{msg, new Integer(code)}, this, changeQuickRedirect, false, 10087, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
                e eVar = e.this;
                e.e(eVar, "tcp sendMessageTo onSendMessageSuccess code : " + code + " msg" + msg);
                try {
                    this.b.a(this.c, new JSONObject(msg), b.a.TCP);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10082, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("TcpSignalSwitchChannel").h(msg, new Object[0]);
        }
    }

    private void c(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 10083, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("TcpSignalSwitchChannel").c(msg, new Object[0]);
        }
    }
}
