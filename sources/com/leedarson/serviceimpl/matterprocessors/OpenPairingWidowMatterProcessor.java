package com.leedarson.serviceimpl.matterprocessors;

import android.app.Activity;
import chip.devicecontroller.OpenCommissioningCallback;
import com.didichuxing.doraemonkit.kit.weaknetwork.WeakNetworkManager;
import com.google.gson.Gson;
import com.leedarson.base.utils.p;
import com.leedarson.serviceimpl.bean.OpenPairingWindowBean;
import com.leedarson.serviceimpl.i;
import com.leedarson.serviceimpl.k;
import com.leedarson.serviceinterface.MatterService;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import java.util.Random;
import meshsdk.BaseResp;
import org.json.JSONObject;

public class OpenPairingWidowMatterProcessor extends a {
    /* access modifiers changed from: private */
    public static HashMap<String, MatterOPenPairCacheBean> a = new HashMap<>();
    public static ChangeQuickRedirect changeQuickRedirect;
    Gson b = new Gson();

    static /* synthetic */ String e(OpenPairingWidowMatterProcessor x0, int x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 8449, new Class[]{OpenPairingWidowMatterProcessor.class, Integer.TYPE}, String.class);
        return proxy.isSupported ? (String) proxy.result : x0.i(x1);
    }

    public boolean c(String action) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{action}, this, changeQuickRedirect, false, 8445, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return MatterService.ON_OPEN_PAIRINGWINDOW.equals(action);
    }

    public void a(Activity activity, String str, String str2, String str3) {
        String callbackKey;
        OpenPairingWidowMatterProcessor openPairingWidowMatterProcessor;
        i iVar;
        int i;
        a aVar;
        a aVar2;
        String callbackKey2;
        long j;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{activity, str, str2, str3}, this, changeQuickRedirect, false, 8446, new Class[]{Activity.class, cls, cls, cls}, Void.TYPE).isSupported) {
            String callbackKey3 = str;
            String data = str3;
            Activity activity2 = activity;
            String action = str2;
            try {
                OpenPairingWindowBean dataBean = (OpenPairingWindowBean) this.b.fromJson(data, OpenPairingWindowBean.class);
                HashMap<String, MatterOPenPairCacheBean> hashMap = a;
                MatterOPenPairCacheBean _tempPairBean = hashMap.get(dataBean.matterAddr + "_" + dataBean.useDefaultCode);
                if (_tempPairBean != null) {
                    try {
                        if (_tempPairBean.useDefaultCode == dataBean.useDefaultCode && _tempPairBean.expiredTimeSpan > System.currentTimeMillis()) {
                            if (dataBean.useDefaultCode) {
                                b(callbackKey3, p.c().toString(), action);
                                return;
                            }
                            JSONObject respObj = new JSONObject();
                            try {
                                respObj.put("manualCode", (Object) _tempPairBean.manualPairingCode);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            b(callbackKey3, p.d(respObj).toString(), action);
                            return;
                        }
                    } catch (Exception e) {
                        openPairingWidowMatterProcessor = this;
                        callbackKey = callbackKey3;
                        String str4 = data;
                        openPairingWidowMatterProcessor.b(callbackKey, p.a(BaseResp.ERR_MSG_SEND_FAIL, "打开Admin配网模式开启").toString(), action);
                        k.a.g("打开Matter Admin 配网模式失败", "");
                    }
                }
                MatterOPenPairCacheBean cachPairBean = new MatterOPenPairCacheBean();
                if (dataBean.useDefaultCode) {
                    try {
                        iVar = i.a;
                        long j2 = dataBean.matterAddr;
                        i = dataBean.duration;
                        String str5 = data;
                        aVar2 = aVar;
                        callbackKey2 = callbackKey3;
                        j = j2;
                    } catch (Exception e2) {
                        String str6 = data;
                        openPairingWidowMatterProcessor = this;
                        callbackKey = callbackKey3;
                        openPairingWidowMatterProcessor.b(callbackKey, p.a(BaseResp.ERR_MSG_SEND_FAIL, "打开Admin配网模式开启").toString(), action);
                        k.a.g("打开Matter Admin 配网模式失败", "");
                    }
                    try {
                        aVar = new a(callbackKey3, action, cachPairBean, dataBean);
                        iVar.w(j, i, aVar2);
                        String str7 = callbackKey2;
                    } catch (Exception e3) {
                        openPairingWidowMatterProcessor = this;
                        callbackKey = callbackKey2;
                        openPairingWidowMatterProcessor.b(callbackKey, p.a(BaseResp.ERR_MSG_SEND_FAIL, "打开Admin配网模式开启").toString(), action);
                        k.a.g("打开Matter Admin 配网模式失败", "");
                    }
                } else {
                    String str8 = data;
                    openPairingWidowMatterProcessor = this;
                    callbackKey = callbackKey3;
                    try {
                        openPairingWidowMatterProcessor.d(callbackKey, action, dataBean, cachPairBean);
                    } catch (Exception e4) {
                    }
                }
            } catch (Exception e5) {
                openPairingWidowMatterProcessor = this;
                callbackKey = callbackKey3;
                String str9 = data;
                openPairingWidowMatterProcessor.b(callbackKey, p.a(BaseResp.ERR_MSG_SEND_FAIL, "打开Admin配网模式开启").toString(), action);
                k.a.g("打开Matter Admin 配网模式失败", "");
            }
        }
    }

    public class a implements OpenCommissioningCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ MatterOPenPairCacheBean c;
        final /* synthetic */ OpenPairingWindowBean d;

        a(String str, String str2, MatterOPenPairCacheBean matterOPenPairCacheBean, OpenPairingWindowBean openPairingWindowBean) {
            this.a = str;
            this.b = str2;
            this.c = matterOPenPairCacheBean;
            this.d = openPairingWindowBean;
        }

        public void onError(int status, long deviceId) {
            Object[] objArr = {new Integer(status), new Long(deviceId)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8450, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                OpenPairingWidowMatterProcessor openPairingWidowMatterProcessor = OpenPairingWidowMatterProcessor.this;
                String str = this.a;
                openPairingWidowMatterProcessor.b(str, p.a(status, OpenPairingWidowMatterProcessor.e(OpenPairingWidowMatterProcessor.this, status) + " deviceId=" + deviceId).toString(), this.b);
                k kVar = k.a;
                kVar.c("打开matter Admin 模式失败 status=" + status + "  deviceId=" + deviceId, "");
            }
        }

        public void onSuccess(long deviceId, String manualPairingCode, String qrCode) {
            Class<String> cls = String.class;
            Object[] objArr = {new Long(deviceId), manualPairingCode, qrCode};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8451, new Class[]{Long.TYPE, cls, cls}, Void.TYPE).isSupported) {
                OpenPairingWidowMatterProcessor.this.b(this.a, p.c().toString(), this.b);
                MatterOPenPairCacheBean matterOPenPairCacheBean = this.c;
                long currentTimeMillis = System.currentTimeMillis();
                OpenPairingWindowBean openPairingWindowBean = this.d;
                matterOPenPairCacheBean.expiredTimeSpan = currentTimeMillis + ((long) (openPairingWindowBean.duration * 1000));
                MatterOPenPairCacheBean matterOPenPairCacheBean2 = this.c;
                matterOPenPairCacheBean2.useDefaultCode = openPairingWindowBean.useDefaultCode;
                matterOPenPairCacheBean2.matterAddr = openPairingWindowBean.matterAddr;
                matterOPenPairCacheBean2.manualPairingCode = manualPairingCode;
                HashMap f = OpenPairingWidowMatterProcessor.a;
                f.put(this.d.matterAddr + "_" + this.d.useDefaultCode, this.c);
                k kVar = k.a;
                kVar.g("打开matter Admin 成功了 manualPairingCode=" + manualPairingCode + "  deviceId=" + deviceId + "  qrCode=" + qrCode, "");
            }
        }
    }

    private void d(String callbackKey, String action, OpenPairingWindowBean openPairingWindowBean, MatterOPenPairCacheBean cachPairBean) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{callbackKey, action, openPairingWindowBean, cachPairBean}, this, changeQuickRedirect, false, 8447, new Class[]{cls, cls, OpenPairingWindowBean.class, MatterOPenPairCacheBean.class}, Void.TYPE).isSupported) {
            OpenPairingWindowBean dataBean = openPairingWindowBean;
            int discriminator = new Random().nextInt(WeakNetworkManager.DEFAULT_TIMEOUT_MILLIS);
            int tempSetuppinCode = g();
            k kVar = k.a;
            kVar.l("LDS.matter  discriminator=" + discriminator + "   tempSetuppinCode=" + tempSetuppinCode, "");
            int i = discriminator;
            i.a.x(dataBean.matterAddr, dataBean.duration, 1200, i, (long) tempSetuppinCode, new b(callbackKey, action, cachPairBean, dataBean));
        }
    }

    public class b implements OpenCommissioningCallback {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ String b;
        final /* synthetic */ MatterOPenPairCacheBean c;
        final /* synthetic */ OpenPairingWindowBean d;

        b(String str, String str2, MatterOPenPairCacheBean matterOPenPairCacheBean, OpenPairingWindowBean openPairingWindowBean) {
            this.a = str;
            this.b = str2;
            this.c = matterOPenPairCacheBean;
            this.d = openPairingWindowBean;
        }

        public void onError(int status, long deviceId) {
            Object[] objArr = {new Integer(status), new Long(deviceId)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8452, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                k kVar = k.a;
                kVar.c("使用manueCode配网失败  status=" + status + "  deviceId=" + deviceId, "");
                OpenPairingWidowMatterProcessor openPairingWidowMatterProcessor = OpenPairingWidowMatterProcessor.this;
                String str = this.a;
                openPairingWidowMatterProcessor.b(str, p.a(status, OpenPairingWidowMatterProcessor.e(OpenPairingWidowMatterProcessor.this, status) + "deviceId=" + deviceId).toString(), this.b);
            }
        }

        public void onSuccess(long j, String manualPairingCode, String qrCode) {
            Class<String> cls = String.class;
            Object[] objArr = {new Long(j), manualPairingCode, qrCode};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8453, new Class[]{Long.TYPE, cls, cls}, Void.TYPE).isSupported) {
                JSONObject respObj = new JSONObject();
                try {
                    respObj.put("manualCode", (Object) manualPairingCode);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                OpenPairingWidowMatterProcessor.this.b(this.a, p.d(respObj).toString(), this.b);
                k kVar = k.a;
                kVar.g("使用manueCode配置成功:" + manualPairingCode + "  qrCode=" + qrCode, "");
                MatterOPenPairCacheBean matterOPenPairCacheBean = this.c;
                long currentTimeMillis = System.currentTimeMillis();
                OpenPairingWindowBean openPairingWindowBean = this.d;
                matterOPenPairCacheBean.expiredTimeSpan = currentTimeMillis + ((long) (openPairingWindowBean.duration * 1000));
                MatterOPenPairCacheBean matterOPenPairCacheBean2 = this.c;
                matterOPenPairCacheBean2.useDefaultCode = openPairingWindowBean.useDefaultCode;
                matterOPenPairCacheBean2.matterAddr = openPairingWindowBean.matterAddr;
                matterOPenPairCacheBean2.manualPairingCode = manualPairingCode;
                HashMap f = OpenPairingWidowMatterProcessor.a;
                f.put(this.d.matterAddr + "_" + this.d.useDefaultCode, this.c);
            }
        }
    }

    private int g() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8448, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        Random random = new Random();
        int tempPinCode = random.nextInt(99999998);
        while (!h(tempPinCode)) {
            tempPinCode = random.nextInt(99999998);
        }
        return tempPinCode;
    }

    private String i(int status) {
        if (status == 50) {
            return "设备还未连接到网络";
        }
        if (status == 1538) {
            return "设备已经处于配网模式，不需要重新打开";
        }
        return "打开配网模式失败";
    }

    private boolean h(int setupPIN) {
        if (setupPIN == 0 || setupPIN > 99999998 || setupPIN == 11111111 || setupPIN == 22222222 || setupPIN == 33333333 || setupPIN == 44444444 || setupPIN == 55555555 || setupPIN == 66666666 || setupPIN == 77777777 || setupPIN == 88888888 || setupPIN == 12345678 || setupPIN == 87654321) {
            return false;
        }
        return true;
    }

    public class MatterOPenPairCacheBean {
        public long expiredTimeSpan = System.currentTimeMillis();
        public String manualPairingCode = "";
        public long matterAddr = 0;
        public boolean useDefaultCode = false;

        public MatterOPenPairCacheBean() {
        }
    }

    public class MatterOpenPairResponseBean {
        public String deviceId = "";
        public String manualCode = "";
        public String qrCode = "";

        public MatterOpenPairResponseBean() {
        }
    }
}
