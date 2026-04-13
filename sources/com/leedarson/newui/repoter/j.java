package com.leedarson.newui.repoter;

import android.content.Context;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.H5ActionName;
import com.leedarson.log.mgr.u;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.smartcamera.bean.IPCLiveStepBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import java.util.Locale;

/* compiled from: NewIPCSDCardReporter */
public class j {
    private static j a;
    public static ChangeQuickRedirect changeQuickRedirect;
    long b = 0;
    long c = 0;
    long d = 0;

    static /* synthetic */ void a(j x0, HashMap x1, String x2) {
        Class[] clsArr = {j.class, HashMap.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 4648, clsArr, Void.TYPE).isSupported) {
            x0.h(x1, x2);
        }
    }

    private j() {
    }

    public static j b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 4637, new Class[0], j.class);
        if (proxy.isSupported) {
            return (j) proxy.result;
        }
        if (a == null) {
            synchronized (j.class) {
                if (a == null) {
                    a = new j();
                }
            }
        }
        return a;
    }

    /* compiled from: NewIPCSDCardReporter */
    public class a extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;
        final /* synthetic */ boolean j;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(Context context, String str, boolean z) {
            super(context);
            this.i = str;
            this.j = z;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4649, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            j.a(j.this, fields, this.i);
            if (this.j) {
                fields.put("module", "SDCardKVS");
            } else {
                fields.put("module", "SDCardTUTK");
            }
            return fields;
        }
    }

    public void j(Context context, String str, long startTimeStamp, long endTimeStamp, int type, boolean isKVS) {
        Object[] objArr = {context, str, new Long(startTimeStamp), new Long(endTimeStamp), new Integer(type), new Byte(isKVS ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4638, new Class[]{Context.class, String.class, cls, cls, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            String deviceId = str;
            this.b = System.currentTimeMillis();
            com.leedarson.smartcamera.logreport.b tracker = new a(context, deviceId, isKVS);
            Locale locale = Locale.US;
            tracker.j(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_HAS_VIDEO_TRACK_ID", deviceId}), "LdsPlayer", "HasVideo");
            u.d().b(tracker);
            IPCLiveStepBean bean = new IPCLiveStepBean("hasVideoSeq", 200);
            bean.setDesc("开始请求是否有回放");
            bean.putRequestParams("startTime", Long.valueOf(startTimeStamp));
            bean.putRequestParams("endTime", Long.valueOf(endTimeStamp));
            bean.putRequestParams("eventType", Integer.valueOf(type));
            u.d().a(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_HAS_VIDEO_TRACK_ID", deviceId}), bean);
        }
    }

    public void d(String deviceId, String resp) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, resp}, this, changeQuickRedirect, false, 4639, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("hasVideoResp", 200);
            bean.setDesc("请求是否有回放成功");
            bean.setResponse(resp);
            bean.setTotalDuration(System.currentTimeMillis() - this.b);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_HAS_VIDEO_TRACK_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_HAS_VIDEO_TRACK_ID", deviceId}));
        }
    }

    /* compiled from: NewIPCSDCardReporter */
    public class b extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;
        final /* synthetic */ boolean j;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(Context context, String str, boolean z) {
            super(context);
            this.i = str;
            this.j = z;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4650, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            j.a(j.this, fields, this.i);
            if (this.j) {
                fields.put("module", "SDCardKVS");
            } else {
                fields.put("module", "SDCardTUTK");
            }
            return fields;
        }
    }

    public void i(Context context, String str, long startTimeStamp, long endTimeStamp, int type, boolean isKVS) {
        Object[] objArr = {context, str, new Long(startTimeStamp), new Long(endTimeStamp), new Integer(type), new Byte(isKVS ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4640, new Class[]{Context.class, String.class, cls, cls, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            String deviceId = str;
            this.c = System.currentTimeMillis();
            com.leedarson.smartcamera.logreport.b tracker = new b(context, deviceId, isKVS);
            Locale locale = Locale.US;
            tracker.j(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_QUERY_VIDEO_TRACK_ID", deviceId}), "LdsPlayer", "GetVideoList");
            u.d().b(tracker);
            IPCLiveStepBean bean = new IPCLiveStepBean("getVideoListSeq", 200);
            bean.setDesc("开始请求回放列表");
            bean.putRequestParams("startTime", Long.valueOf(startTimeStamp));
            bean.putRequestParams("endTime", Long.valueOf(endTimeStamp));
            bean.putRequestParams("eventType", Integer.valueOf(type));
            u.d().a(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_QUERY_VIDEO_TRACK_ID", deviceId}), bean);
        }
    }

    public void c(String deviceId, String resp) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{deviceId, resp}, this, changeQuickRedirect, false, 4641, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("getVideoListResp", 200);
            bean.setDesc("请求回放列表成功");
            bean.setResponse(resp);
            bean.setTotalDuration(System.currentTimeMillis() - this.c);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_QUERY_VIDEO_TRACK_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_QUERY_VIDEO_TRACK_ID", deviceId}));
        }
    }

    /* compiled from: NewIPCSDCardReporter */
    public class c extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;
        final /* synthetic */ boolean j;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(Context context, String str, boolean z) {
            super(context);
            this.i = str;
            this.j = z;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4651, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            j.a(j.this, fields, this.i);
            if (this.j) {
                fields.put("module", "SDCardKVS");
            } else {
                fields.put("module", "SDCardTUTK");
            }
            return fields;
        }
    }

    public void k(Context context, String str, long j, int type, boolean z) {
        Object[] objArr = {context, str, new Long(j), new Integer(type), new Byte(z ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4642, new Class[]{Context.class, String.class, Long.TYPE, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            String deviceId = str;
            boolean isKVS = z;
            Context context2 = context;
            long startTimeStamp = j;
            this.d = System.currentTimeMillis();
            com.leedarson.smartcamera.logreport.b tracker = new c(context2, deviceId, isKVS);
            Locale locale = Locale.US;
            tracker.j(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_PLAY_TRACK_ID", deviceId}), "LdsPlayer", "FirstFrameRender");
            u.d().b(tracker);
            IPCLiveStepBean bean = new IPCLiveStepBean("playVideoSeq", 200);
            bean.setDesc("请求播放");
            bean.putRequestParams("startTime", Long.valueOf(startTimeStamp));
            bean.putRequestParams("eventType", Integer.valueOf(type));
            u.d().a(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_PLAY_TRACK_ID", deviceId}), bean);
            d dVar = new d(context2, deviceId, isKVS, startTimeStamp);
            String str2 = "%s:%s";
            tracker.j(String.format(locale, str2, new Object[]{"IPC_SDCARD_START_PLAY_TRACK_ID", deviceId}), "LdsPlayer", H5ActionName.ACTION_START_PLAY);
            u.d().b(dVar);
            u.d().e(String.format(locale, str2, new Object[]{"IPC_SDCARD_START_PLAY_TRACK_ID", deviceId}));
        }
    }

    /* compiled from: NewIPCSDCardReporter */
    public class d extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;
        final /* synthetic */ boolean j;
        final /* synthetic */ long k;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(Context context, String str, boolean z, long j2) {
            super(context);
            this.i = str;
            this.j = z;
            this.k = j2;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4652, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            j.a(j.this, fields, this.i);
            if (this.j) {
                fields.put("module", "SDCardKVS");
            } else {
                fields.put("module", "SDCardTUTK");
            }
            fields.put("startTime", Long.valueOf(this.k));
            fields.put("message", "点击SD卡回放");
            return fields;
        }
    }

    public void f(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4643, new Class[]{String.class}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("receiveIFrame", 200);
            bean.setDesc("收到首个I帧");
            u.d().a(String.format(Locale.US, "%s:%s", new Object[]{"IPC_SD_CARD_PLAY_TRACK_ID", deviceId}), bean);
        }
    }

    public void e(String deviceId) {
        if (!PatchProxy.proxy(new Object[]{deviceId}, this, changeQuickRedirect, false, 4644, new Class[]{String.class}, Void.TYPE).isSupported) {
            IPCLiveStepBean bean = new IPCLiveStepBean("playVideoResp", 200);
            bean.setDesc("播放成功");
            bean.setTotalDuration(System.currentTimeMillis() - this.d);
            u d2 = u.d();
            Locale locale = Locale.US;
            d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_PLAY_TRACK_ID", deviceId}), bean);
            u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_PLAY_TRACK_ID", deviceId}));
        }
    }

    /* compiled from: NewIPCSDCardReporter */
    public class e extends com.leedarson.smartcamera.logreport.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String i;
        final /* synthetic */ boolean j;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(Context context, String str, boolean z) {
            super(context);
            this.i = str;
            this.j = z;
        }

        public HashMap<String, Object> f() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4653, new Class[0], HashMap.class);
            if (proxy.isSupported) {
                return (HashMap) proxy.result;
            }
            HashMap<String, Object> fields = new HashMap<>();
            j.a(j.this, fields, this.i);
            if (this.j) {
                fields.put("module", "SDCardKVS");
            } else {
                fields.put("module", "SDCardTUTK");
            }
            return fields;
        }
    }

    private void l(Context context, String deviceId, boolean isKVS) {
        if (!PatchProxy.proxy(new Object[]{context, deviceId, new Byte(isKVS ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4645, new Class[]{Context.class, String.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            com.leedarson.smartcamera.logreport.b tracker = new e(context, deviceId, isKVS);
            tracker.j(String.format(Locale.US, "%s:%s", new Object[]{"IPC_SD_CARD_OTHER_TRACK_ID", deviceId}), "LdsPlayer", "sdcardStatus");
            u.d().b(tracker);
        }
    }

    public void g(String str, String str2, boolean isKVS) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, new Byte(isKVS ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 4646, new Class[]{cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            String message = str2;
            String deviceId = str;
            try {
                l(BaseApplication.b(), deviceId, isKVS);
                IPCLiveStepBean bean = new IPCLiveStepBean("useRecord", 200);
                bean.setDesc(message);
                u d2 = u.d();
                Locale locale = Locale.US;
                d2.a(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_OTHER_TRACK_ID", deviceId}), bean);
                u.d().e(String.format(locale, "%s:%s", new Object[]{"IPC_SD_CARD_OTHER_TRACK_ID", deviceId}));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void h(HashMap<String, Object> fields, String deviceId) {
        Class[] clsArr = {HashMap.class, String.class};
        if (!PatchProxy.proxy(new Object[]{fields, deviceId}, this, changeQuickRedirect, false, 4647, clsArr, Void.TYPE).isSupported) {
            if (fields != null) {
                fields.put("deviceId", deviceId);
                try {
                    BaseApplication b2 = BaseApplication.b();
                    String firVer = SharePreferenceUtils.getPrefString(b2, "firmware_" + deviceId, "");
                    BaseApplication b3 = BaseApplication.b();
                    String hardVer = SharePreferenceUtils.getPrefString(b3, "hardware_" + deviceId, "");
                    BaseApplication b4 = BaseApplication.b();
                    String modelId = SharePreferenceUtils.getPrefString(b4, "modelId_" + deviceId, "");
                    fields.put("firmwareVersion", firVer);
                    fields.put("hardwareVersion", hardVer);
                    fields.put("modelId", modelId);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
