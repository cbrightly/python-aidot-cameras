package com.leedarson.log;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.http.exception.ApiException;
import com.leedarson.base.utils.u;
import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.http.manager.b0;
import com.leedarson.serviceinterface.HttpService;
import com.leedarson.serviceinterface.ShakeService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.AVIOCTRLDEFs;
import io.reactivex.l;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import meshsdk.BaseResp;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: ReportTree */
public final class h extends a.b {
    private static int b = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    SimpleDateFormat A;
    private int c;
    /* access modifiers changed from: private */
    public StringBuffer d;
    /* access modifiers changed from: private */
    public SimpleDateFormat e;
    private Writer f;
    private PrintWriter g;
    private String h;
    /* access modifiers changed from: private */
    public String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private u o;
    private HttpService p;
    /* access modifiers changed from: private */
    public Context q;
    private final String r;
    private boolean s;
    private int t;
    /* access modifiers changed from: private */
    public String u;
    /* access modifiers changed from: private */
    public HashSet<String> v;
    /* access modifiers changed from: private */
    public String w;
    private long x;
    File y;
    FileOutputStream z;

    /* compiled from: ReportTree */
    public interface j {
        void onError();

        void onSuccess();
    }

    static /* synthetic */ void C(h x0, String x1, j x2) {
        Class[] clsArr = {h.class, String.class, j.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 1199, clsArr, Void.TYPE).isSupported) {
            x0.M(x1, x2);
        }
    }

    static /* synthetic */ void r(h x0, String x1, String x2, Throwable x3, long x4) {
        Class<String> cls = String.class;
        Object[] objArr = {x0, x1, x2, x3, new Long(x4)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 1197, new Class[]{h.class, cls, cls, Throwable.class, Long.TYPE}, Void.TYPE).isSupported) {
            x0.Q(x1, x2, x3, x4);
        }
    }

    static /* synthetic */ void w(h x0, String x1, String x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 1198, new Class[]{h.class, cls, cls, cls}, Void.TYPE).isSupported) {
            x0.U(x1, x2, x3);
        }
    }

    public h(Context context) {
        this(context, false);
    }

    public h(Context context, boolean isDebug) {
        this.c = 2097152;
        this.h = "";
        this.l = "";
        this.m = "";
        this.s = false;
        this.t = 0;
        this.u = "";
        this.A = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        this.s = isDebug;
        this.q = context;
        if (!"prod".equals(this.w)) {
            this.c = 104857600;
        }
        Locale locale = Locale.US;
        String format = String.format(locale, "%s/web/log", new Object[]{this.q.getApplicationContext().getFilesDir().getPath()});
        this.i = format;
        this.j = String.format(locale, "%s/%s1.txt", new Object[]{format, "reportLog"});
        this.k = String.format(locale, "%s/%s2.txt", new Object[]{this.i, "reportLog"});
        this.n = String.format(locale, "%s/%s1.txt", new Object[]{this.i, "reportLog"});
        this.r = String.format(locale, "%s/crashLog/crashLog.log", new Object[]{this.q.getApplicationContext().getFilesDir().getPath()});
        this.v = new HashSet<>();
    }

    public void T(int reportlogLevel) {
        b = reportlogLevel;
    }

    public void S(int logLevel) {
        if (!PatchProxy.proxy(new Object[]{new Integer(logLevel)}, this, changeQuickRedirect, false, 1178, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("logger");
            g2.a("setLogLevel: " + logLevel, new Object[0]);
            this.t = logLevel;
        }
    }

    public boolean j(@Nullable String tag, int priority) {
        int tempLevel = this.t;
        int i2 = b;
        if (tempLevel < i2) {
            tempLevel = i2;
        }
        boolean logFlag = true;
        switch (tempLevel) {
            case 0:
                return false;
            case 1:
                if (priority != 6) {
                    logFlag = false;
                }
                return logFlag;
            case 2:
                if (priority != 5) {
                    logFlag = false;
                }
                return logFlag;
            case 3:
                if (!(priority == 6 || priority == 5)) {
                    logFlag = false;
                }
                return logFlag;
            case 4:
                if (priority != 3) {
                    logFlag = false;
                }
                return logFlag;
            case 5:
                if (!(priority == 6 || priority == 3)) {
                    logFlag = false;
                }
                return logFlag;
            case 6:
                if (!(priority == 5 || priority == 3)) {
                    logFlag = false;
                }
                return logFlag;
            case 7:
                if (!(priority == 6 || priority == 5 || priority == 3)) {
                    logFlag = false;
                }
                return logFlag;
            case 8:
                if (priority != 4) {
                    logFlag = false;
                }
                return logFlag;
            case 9:
                if (!(priority == 6 || priority == 4)) {
                    logFlag = false;
                }
                return logFlag;
            case 10:
                if (!(priority == 5 || priority == 4)) {
                    logFlag = false;
                }
                return logFlag;
            case 11:
                if (!(priority == 6 || priority == 5 || priority == 4)) {
                    logFlag = false;
                }
                return logFlag;
            case 12:
                if (!(priority == 3 || priority == 4)) {
                    logFlag = false;
                }
                return logFlag;
            case 13:
                if (!(priority == 6 || priority == 3 || priority == 4)) {
                    logFlag = false;
                }
                return logFlag;
            case 14:
                if (!(priority == 5 || priority == 3 || priority == 4)) {
                    logFlag = false;
                }
                return logFlag;
            case 15:
                if (!(priority == 6 || priority == 5 || priority == 3 || priority == 4)) {
                    logFlag = false;
                }
                return logFlag;
            case 16:
                if (priority != 2) {
                    logFlag = false;
                }
                return logFlag;
            case 17:
                if (!(priority == 6 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 18:
                if (!(priority == 5 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 19:
                if (!(priority == 6 || priority == 5 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 20:
                if (!(priority == 3 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 21:
                if (!(priority == 6 || priority == 3 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 22:
                if (!(priority == 5 || priority == 3 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 23:
                if (!(priority == 6 || priority == 5 || priority == 3 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 24:
                if (!(priority == 4 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 25:
                if (!(priority == 6 || priority == 4 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 26:
                if (!(priority == 5 || priority == 4 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 27:
                if (!(priority == 6 || priority == 5 || priority == 4 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 28:
                if (!(priority == 3 || priority == 4 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 29:
                if (!(priority == 6 || priority == 3 || priority == 4 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 30:
                if (!(priority == 5 || priority == 3 || priority == 4 || priority == 2)) {
                    logFlag = false;
                }
                return logFlag;
            case 31:
            case 255:
                if (priority < 2) {
                    logFlag = false;
                }
                return logFlag;
            default:
                return false;
        }
    }

    public void k(int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable t2) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(priority), tag, message, t2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1179, new Class[]{Integer.TYPE, cls, cls, Throwable.class}, Void.TYPE).isSupported) {
            if (!"prod".equals(this.w) || this.s || this.t > 0) {
                L(priority, tag, message, t2);
            }
            int tid = Process.myTid();
            if (this.x == 0) {
                this.x = this.q.getApplicationContext().getMainLooper().getThread().getId();
            }
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                this.x = (long) tid;
            }
            switch (priority) {
                case 2:
                    this.h = ExifInterface.GPS_MEASUREMENT_INTERRUPTED;
                    break;
                case 3:
                    this.h = "D";
                    break;
                case 4:
                    this.h = "I";
                    break;
                case 5:
                    this.h = ExifInterface.LONGITUDE_WEST;
                    break;
                case 6:
                    this.h = ExifInterface.LONGITUDE_EAST;
                    break;
                case 7:
                    this.h = ExifInterface.GPS_MEASUREMENT_IN_PROGRESS;
                    break;
                default:
                    this.h = ExifInterface.LONGITUDE_EAST;
                    break;
            }
            try {
                P(this.h, tag, message, t2, tid);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @SuppressLint({"LogNotTimber"})
    private void L(@Nullable int priority, @Nullable String tag, @NotNull String message, @Nullable Throwable th) {
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(priority), tag, message, th};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1180, new Class[]{Integer.TYPE, cls, cls, Throwable.class}, Void.TYPE).isSupported) {
            String str = "TAG";
            switch (priority) {
                case 2:
                    if (!TextUtils.isEmpty(tag)) {
                        str = tag;
                    }
                    Log.v(str, message);
                    return;
                case 3:
                    if (!TextUtils.isEmpty(tag)) {
                        str = tag;
                    }
                    Log.d(str, message);
                    return;
                case 4:
                    if (!TextUtils.isEmpty(tag)) {
                        str = tag;
                    }
                    Log.i(str, message);
                    return;
                case 5:
                    if (!TextUtils.isEmpty(tag)) {
                        str = tag;
                    }
                    Log.w(str, message);
                    return;
                case 6:
                    if (!TextUtils.isEmpty(tag)) {
                        str = tag;
                    }
                    Log.e(str, message);
                    return;
                default:
                    return;
            }
        }
    }

    @SuppressLint({"LogNotTimber"})
    private void P(String str, @Nullable String str2, @NotNull String str3, @Nullable Throwable th, int i2) {
        Class<String> cls = String.class;
        Object[] objArr = {str, str2, str3, th, new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 1181, new Class[]{cls, cls, cls, Throwable.class, Integer.TYPE}, Void.TYPE).isSupported) {
            String tag = str2;
            Throwable t2 = th;
            String message = str3;
            int threadId = i2;
            if (this.o == null) {
                this.o = new u(u.b.FixedThread, 1, "reporttree-save-logcat");
            }
            if (!this.o.b()) {
                this.o.a(new a(tag, message, t2, threadId));
            }
        }
    }

    /* compiled from: ReportTree */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;
        final /* synthetic */ Throwable f;
        final /* synthetic */ int q;

        a(String str, String str2, Throwable th, int i) {
            this.c = str;
            this.d = str2;
            this.f = th;
            this.q = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1200, new Class[0], Void.TYPE).isSupported) {
                if ((TextUtils.isEmpty(this.c) || !"H5LOG".equals(this.c)) && !"prod".equals(h.this.w)) {
                    h.r(h.this, this.c, this.d, this.f, (long) this.q);
                }
            }
        }
    }

    private void Q(String str, String str2, Throwable th, long j2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, th, new Long(j2)}, this, changeQuickRedirect, false, 1182, new Class[]{cls, cls, Throwable.class, Long.TYPE}, Void.TYPE).isSupported) {
            String message = str2;
            long threadId = j2;
            String tag = str;
            Throwable t2 = th;
            if (TextUtils.isEmpty(message) || !message.contains("SIGMeshHandlerThread --mac")) {
                if (this.d == null) {
                    this.d = new StringBuffer();
                }
                if (this.e == null) {
                    this.e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.getDefault());
                }
                if (this.d.length() > 0) {
                    this.d.setLength(0);
                }
                StringBuffer stringBuffer = this.d;
                stringBuffer.append(this.e.format(new Date()));
                stringBuffer.append(" ");
                stringBuffer.append(this.h);
                stringBuffer.append("/TAG:");
                StringBuffer stringBuffer2 = this.d;
                stringBuffer2.append(" ");
                stringBuffer2.append(this.x);
                stringBuffer2.append("/");
                stringBuffer2.append(threadId);
                stringBuffer2.append(" ");
                StringBuffer stringBuffer3 = this.d;
                stringBuffer3.append(!TextUtils.isEmpty(tag) ? tag : "");
                stringBuffer3.append(" Msg:");
                stringBuffer3.append(message);
                stringBuffer3.append("\n");
                if (t2 != null) {
                    if (this.f == null) {
                        this.f = new StringWriter();
                    }
                    if (this.g == null) {
                        this.g = new PrintWriter(this.f);
                    }
                    t2.printStackTrace(this.g);
                    for (Throwable cause = t2.getCause(); cause != null; cause = cause.getCause()) {
                        cause.printStackTrace(this.g);
                    }
                    this.g.close();
                    String result = this.f.toString();
                    StringBuffer stringBuffer4 = this.d;
                    stringBuffer4.append("错误信息:");
                    stringBuffer4.append("\n");
                    stringBuffer4.append(result);
                }
                if (this.y == null) {
                    this.y = new File(this.i);
                }
                if (!this.y.exists()) {
                    this.y.mkdirs();
                }
                String fileName = p(this.n);
                try {
                    if (fileName.equals(this.n)) {
                        this.z = new FileOutputStream(new File(fileName), true);
                    } else {
                        o(new File(this.n));
                        this.z = new FileOutputStream(new File(fileName), false);
                    }
                    this.n = fileName;
                    this.z.write(this.d.toString().getBytes());
                    this.z.flush();
                    this.z.close();
                } catch (Exception e2) {
                }
            }
        }
    }

    public void O(@Nullable String str, @NotNull String message, boolean append) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, message, new Byte(append ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 1183, new Class[]{cls, cls, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (this.o == null) {
                this.o = new u(u.b.FixedThread, 1, "report-tree-saveH5log");
            }
            if (!this.o.b()) {
                this.o.a(new b(message, append));
            }
        }
    }

    /* compiled from: ReportTree */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ boolean d;

        b(String str, boolean z) {
            this.c = str;
            this.d = z;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1201, new Class[0], Void.TYPE).isSupported) {
                if (h.this.d == null) {
                    StringBuffer unused = h.this.d = new StringBuffer();
                }
                if (h.this.e == null) {
                    SimpleDateFormat unused2 = h.this.e = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
                }
                if (h.this.d.length() > 0) {
                    h.this.d.setLength(0);
                }
                StringBuffer s = h.this.d;
                s.append(this.c);
                s.append("\n");
                a.b g = timber.log.a.g("logger");
                g.a("saveH5Log:" + h.this.d.toString(), new Object[0]);
                h hVar = h.this;
                h.w(hVar, "saveH5Log:" + h.this.d.toString(), "debug", "saveH5Log");
                h hVar2 = h.this;
                if (hVar2.y == null) {
                    hVar2.y = new File(h.this.i);
                }
                if (!h.this.y.exists()) {
                    h.this.y.mkdirs();
                }
                h hVar3 = h.this;
                String unused3 = hVar3.u = SharePreferenceUtils.getPrefString(hVar3.q, "h5LogFilePath", "");
                if (TextUtils.isEmpty(h.this.u)) {
                    h hVar4 = h.this;
                    String unused4 = hVar4.u = String.format(Locale.US, "%s/%s.log", new Object[]{hVar4.i, h.this.e.format(new Date())});
                    try {
                        new File(h.this.u).createNewFile();
                        SharePreferenceUtils.setPrefString(h.this.q, "h5LogFilePath", h.this.u);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    h.this.z = new FileOutputStream(new File(h.this.u), this.d);
                    h hVar5 = h.this;
                    hVar5.z.write(hVar5.d.toString().getBytes());
                    timber.log.a.g("report").a("write success!", new Object[0]);
                    h.this.z.flush();
                    h.this.z.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void V(String content, String callbackKey) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{content, callbackKey}, this, changeQuickRedirect, false, 1184, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("logger");
            g2.a("updateH5Log->" + content, new Object[0]);
            String filePath = SharePreferenceUtils.getPrefString(this.q, "h5LogFilePath", "");
            l.F(filePath).J(com.leedarson.base.http.observer.l.a).b0(com.leedarson.base.http.observer.l.a).G(new f(callbackKey)).G(new e(content)).Y(new c(filePath, callbackKey), new d(callbackKey));
        }
    }

    /* compiled from: ReportTree */
    public class f implements io.reactivex.functions.f<String, String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        f(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CUSTOM_COMMAND_REQ, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String) obj);
        }

        public String a(String filePath) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IIOTYPE_USER_IPCAM_DELLISTEVENT_RESP, new Class[]{String.class}, String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            File f = new File(filePath);
            if (f.exists()) {
                return e.c(f);
            }
            JSONObject json = new JSONObject().put("code", (int) BaseResp.ERR_MSG_SEND_FAIL);
            json.put(NotificationCompat.CATEGORY_MESSAGE, (Object) "h5 log file not exist");
            org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, json.toString()));
            return "";
        }
    }

    /* compiled from: ReportTree */
    public class e implements io.reactivex.functions.f<String, String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        e(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, AVIOCTRLDEFs.OTYPE_USER_IPCAM_DELLISTEVENT_REQ, new Class[]{Object.class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String) obj);
        }

        public String a(String fileSrcContent) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{fileSrcContent}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HASLISTEVENT_RESP, new Class[]{String.class}, String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            if (TextUtils.isEmpty(fileSrcContent)) {
                return fileSrcContent;
            }
            JSONObject srcJson = new JSONObject(fileSrcContent);
            JSONObject contentJSon = new JSONObject(this.c);
            if (contentJSon.has("replace")) {
                JSONObject replaceJson = contentJSon.getJSONObject("replace");
                Iterator<String> keys = replaceJson.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    srcJson.put(key, (Object) replaceJson.getString(key));
                }
            }
            if (contentJSon.has("concat")) {
                JSONObject concatJson = contentJSon.getJSONObject("concat");
                Iterator<String> keys2 = concatJson.keys();
                while (keys2.hasNext()) {
                    String key2 = keys2.next();
                    Object value = concatJson.get(key2);
                    if (srcJson.has(key2) && (srcJson.get(key2) instanceof JSONArray)) {
                        JSONArray srcArr = (JSONArray) srcJson.get(key2);
                        if (value instanceof JSONObject) {
                            srcArr.put(value);
                        } else if (value instanceof JSONArray) {
                            JSONArray valArr = (JSONArray) value;
                            for (int i = 0; i < valArr.length(); i++) {
                                srcArr.put(valArr.get(i));
                            }
                        }
                    }
                }
            }
            return srcJson.toString();
        }
    }

    /* compiled from: ReportTree */
    public class c implements io.reactivex.functions.e<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ String d;

        c(String str, String str2) {
            this.c = str;
            this.d = str2;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HAS_SDCARD_REQ, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((String) obj);
            }
        }

        public void a(String destContent) {
            if (!PatchProxy.proxy(new Object[]{destContent}, this, changeQuickRedirect, false, 1202, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (!TextUtils.isEmpty(destContent)) {
                    e.e(this.c, destContent, false);
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.d, new JSONObject().put("code", 200).toString()));
                }
            }
        }
    }

    /* compiled from: ReportTree */
    public class d implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        d(String str) {
            this.c = str;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HASLISTEVENT_REQ, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_HAS_SDCARD_RESP, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                try {
                    JSONObject json = new JSONObject().put("code", (int) BaseResp.ERR_MSG_SEND_FAIL);
                    json.put(NotificationCompat.CATEGORY_MESSAGE, (Object) throwable.toString());
                    org.greenrobot.eventbus.c.c().l(new JsBridgeCallbackEvent(this.c, json.toString()));
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        }
    }

    private void o(File logFile) {
        if (!PatchProxy.proxy(new Object[]{logFile}, this, changeQuickRedirect, false, 1185, new Class[]{File.class}, Void.TYPE).isSupported) {
            if (this.p == null) {
                this.p = (HttpService) com.alibaba.android.arouter.launcher.a.c().g(HttpService.class);
            }
            if (this.p != null && !TextUtils.isEmpty(this.l)) {
                this.p.handleData(this.m, this.l, logFile);
            }
        }
    }

    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        */
    public void W() {
        /*
            r18 = this;
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r7 = new java.lang.Class[r1]
            java.lang.Class r8 = java.lang.Void.TYPE
            r5 = 0
            r6 = 1186(0x4a2, float:1.662E-42)
            r3 = r18
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r2, r3, r4, r5, r6, r7, r8)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0017
            return
        L_0x0017:
            r8 = r18
            android.content.Context r0 = r8.q
            java.lang.String r2 = "logUploadUrl"
            java.lang.String r3 = ""
            java.lang.String r15 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r0, r2, r3)
            android.content.Context r0 = r8.q
            java.lang.String r2 = "h5LogFilePath"
            java.lang.String r14 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r0, r2, r3)
            boolean r0 = android.text.TextUtils.isEmpty(r15)
            if (r0 != 0) goto L_0x0131
            boolean r0 = android.text.TextUtils.isEmpty(r14)
            if (r0 == 0) goto L_0x003b
            r6 = r14
            r7 = r15
            goto L_0x0133
        L_0x003b:
            java.lang.String r2 = "logger"
            timber.log.a$b r0 = timber.log.a.g(r2)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "upload h5 logFile:"
            r4.append(r5)
            r4.append(r14)
            java.lang.String r4 = r4.toString()
            java.lang.Object[] r6 = new java.lang.Object[r1]
            r0.a(r4, r6)
            java.util.HashSet<java.lang.String> r0 = r8.v
            boolean r0 = r0.contains(r14)
            if (r0 == 0) goto L_0x007b
            timber.log.a$b r0 = timber.log.a.g(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "current file is uploading ,return : "
            r2.append(r3)
            r2.append(r14)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r0.a(r2, r1)
            return
        L_0x007b:
            java.util.HashSet<java.lang.String> r0 = r8.v
            r0.add(r14)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r13 = r0
            java.io.File r0 = new java.io.File
            r0.<init>(r14)
            r12 = r0
            r13.add(r12)
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r11 = r0
            android.content.Context r0 = r8.q
            java.lang.String r4 = "accessToken"
            java.lang.String r10 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r0, r4, r3)
            java.lang.String r0 = "appId"
            android.content.Context r4 = r8.q     // Catch:{ JSONException -> 0x00bf }
            java.lang.String r6 = "APP_ID"
            java.lang.String r3 = com.leedarson.serviceinterface.prefs.SharePreferenceUtils.getPrefString(r4, r6, r3)     // Catch:{ JSONException -> 0x00bf }
            r11.put((java.lang.String) r0, (java.lang.Object) r3)     // Catch:{ JSONException -> 0x00bf }
            boolean r0 = android.text.TextUtils.isEmpty(r10)     // Catch:{ JSONException -> 0x00bf }
            if (r0 != 0) goto L_0x00b6
            java.lang.String r0 = "token"
            r11.put((java.lang.String) r0, (java.lang.Object) r10)     // Catch:{ JSONException -> 0x00bf }
        L_0x00b6:
            java.lang.String r0 = "terminal"
            java.lang.String r3 = "app"
            r11.put((java.lang.String) r0, (java.lang.Object) r3)     // Catch:{ JSONException -> 0x00bf }
            goto L_0x00c3
        L_0x00bf:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00c3:
            timber.log.a$b r0 = timber.log.a.g(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "start upload ...url:"
            r2.append(r3)
            r2.append(r15)
            java.lang.String r3 = ",,,header:"
            r2.append(r3)
            java.lang.String r3 = r11.toString()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r0.a(r2, r1)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r5)
            r0.append(r14)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "debug"
            java.lang.String r2 = "uploadFile"
            r8.U(r0, r1, r2)
            com.leedarson.serviceimpl.http.manager.b0 r9 = com.leedarson.serviceimpl.http.manager.b0.b()
            android.content.Context r0 = r8.q
            android.content.Context r0 = r0.getApplicationContext()
            r1 = 0
            java.lang.String r16 = r11.toString()
            com.leedarson.log.h$g r17 = new com.leedarson.log.h$g
            r2 = r17
            r3 = r8
            r4 = r14
            r5 = r11
            r6 = r15
            r7 = r13
            r2.<init>(r4, r5, r6, r7)
            java.lang.String r2 = ""
            r3 = r10
            r10 = r0
            r4 = r11
            r11 = r1
            r1 = r12
            r12 = r15
            r5 = r13
            r13 = r16
            r6 = r14
            r14 = r5
            r7 = r15
            r15 = r2
            r16 = r17
            r9.h(r10, r11, r12, r13, r14, r15, r16)
            return
        L_0x0131:
            r6 = r14
            r7 = r15
        L_0x0133:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.log.h.W():void");
    }

    /* compiled from: ReportTree */
    public class g extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;
        final /* synthetic */ JSONObject d;
        final /* synthetic */ String f;
        final /* synthetic */ ArrayList q;

        g(String str, JSONObject jSONObject, String str2, ArrayList arrayList) {
            this.c = str;
            this.d = jSONObject;
            this.f = str2;
            this.q = arrayList;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1212, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException e) {
            if (!PatchProxy.proxy(new Object[]{e}, this, changeQuickRedirect, false, AVIOCTRLDEFs.IOTYPE_USER_IPCAM_CUSTOM_COMMAND_RESP, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("logger");
                g.c("upload error!!!" + e.toString(), new Object[0]);
                h hVar = h.this;
                h.w(hVar, "h5 logFile upload error!!!" + e.toString() + ",code:" + e.getCode(), "info", "uploadFile");
                h.this.v.remove(this.c);
                if (e.getCode() == 21026) {
                    timber.log.a.g("logger").n("refreshToken", new Object[0]);
                    h.C(h.this, this.d.toString(), new a());
                    return;
                }
                SharePreferenceUtils.setPrefBoolean(h.this.q, "needRetryUpload", true);
            }
        }

        /* compiled from: ReportTree */
        public class a implements j {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void onSuccess() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1213, new Class[0], Void.TYPE).isSupported) {
                    try {
                        JSONObject headerObj = new JSONObject(g.this.d.toString());
                        headerObj.remove("token");
                        headerObj.put("token", (Object) SharePreferenceUtils.getPrefString(h.this.q, "accessToken", ""));
                        h.this.v.add(g.this.c);
                        b0.b().h(h.this.q.getApplicationContext(), (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, g.this.f, headerObj.toString(), g.this.q, "", new C0015a());
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            /* renamed from: com.leedarson.log.h$g$a$a  reason: collision with other inner class name */
            /* compiled from: ReportTree */
            public class C0015a extends com.leedarson.base.http.observer.i<String> {
                public static ChangeQuickRedirect changeQuickRedirect;

                C0015a() {
                }

                public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
                    if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1216, new Class[]{Object.class}, Void.TYPE).isSupported) {
                        onSuccess((String) obj);
                    }
                }

                public void onStart(io.reactivex.disposables.b d) {
                }

                public void onError(ApiException apiException) {
                    if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 1214, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                        h.this.v.remove(g.this.c);
                        SharePreferenceUtils.setPrefBoolean(h.this.q, "needRetryUpload", true);
                    }
                }

                public void onSuccess(String str) {
                    if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 1215, new Class[]{String.class}, Void.TYPE).isSupported) {
                        h.this.E();
                    }
                }
            }

            public void onError() {
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1211, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("logger");
                g.a("upload onSuccess...:" + response, new Object[0]);
                h hVar = h.this;
                h.w(hVar, "h5 logFile upload onSuccess...:" + response, "debug", "uploadFile");
                h.this.E();
            }
        }
    }

    private String p(String fileName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{fileName}, this, changeQuickRedirect, false, 1187, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        File file = new File(this.i);
        if (!file.exists()) {
            return this.j;
        }
        String[] fileArray = file.list();
        if (fileArray == null || fileArray.length <= 0) {
            return this.j;
        }
        if (new File(fileName).length() <= ((long) this.c)) {
            return fileName;
        }
        if (fileName.equals(this.j)) {
            com.leedarson.base.http.observer.l.l.execute(new a(this));
            return this.k;
        }
        com.leedarson.base.http.observer.l.l.execute(new c(this));
        return this.j;
    }

    /* access modifiers changed from: private */
    /* renamed from: F */
    public /* synthetic */ void G() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1196, new Class[0], Void.TYPE).isSupported) {
            Locale locale = Locale.US;
            String firstZipBackFilePath = String.format(locale, "%s/%s1_%s_back.zip", new Object[]{this.i, "reportLog", this.A.format(new Date(System.currentTimeMillis())) + ""});
            File firstLogReportFile = new File(this.j);
            e.b(firstLogReportFile, new File(firstZipBackFilePath));
            firstLogReportFile.delete();
            D();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: H */
    public /* synthetic */ void I() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1195, new Class[0], Void.TYPE).isSupported) {
            Locale locale = Locale.US;
            String secondZipBackFilePath = String.format(locale, "%s/%s2_%s_back.zip", new Object[]{this.i, "reportLog", this.A.format(new Date(System.currentTimeMillis())) + ""});
            File secondFile = new File(this.k);
            e.b(secondFile, new File(secondZipBackFilePath));
            secondFile.delete();
            D();
        }
    }

    private void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1188, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.base.http.observer.l.l.execute(new b(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: J */
    public /* synthetic */ void K() {
        File[] logFiles;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1194, new Class[0], Void.TYPE).isSupported) {
            try {
                File logFolder = new File(this.i);
                if (logFolder.exists() && (logFiles = logFolder.listFiles()) != null) {
                    for (int i2 = 0; i2 < logFiles.length; i2++) {
                        if (System.currentTimeMillis() - logFiles[i2].lastModified() > 604800000) {
                            logFiles[i2].delete();
                        }
                    }
                }
            } catch (Exception e2) {
                Log.e("deleteOldData", "delete old data Exception:=" + e2.toString());
            }
        }
    }

    public void R(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 1189, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.w = SharePreferenceUtils.getPrefString(this.q, "PACK_SERVER_ENV", "prod");
            if (data != null && !TextUtils.isEmpty(data)) {
                try {
                    JSONObject dataObj = new JSONObject(data);
                    if (dataObj.has("debug")) {
                        this.t = dataObj.getInt("debug");
                    }
                    if (dataObj.has("report")) {
                        b = dataObj.getInt("report");
                    }
                    if (dataObj.has("runtimeDebug")) {
                        if (dataObj.getJSONObject("runtimeDebug").has(FirebaseAnalytics.Param.LEVEL)) {
                            this.t = dataObj.getJSONObject("runtimeDebug").getInt(FirebaseAnalytics.Param.LEVEL);
                        }
                    }
                    if (dataObj.has("runtimeReport") && dataObj.getJSONObject("runtimeReport").has(FirebaseAnalytics.Param.LEVEL)) {
                        b = dataObj.getJSONObject("runtimeReport").getInt(FirebaseAnalytics.Param.LEVEL);
                    }
                    if (dataObj.has("collectHttpServer")) {
                        SharePreferenceUtils.setPrefString(this.q, "logUploadUrl", dataObj.getString("collectHttpServer"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    this.t = 0;
                    b = 0;
                }
                ShakeService shakeService = (ShakeService) com.alibaba.android.arouter.launcher.a.c().g(ShakeService.class);
                if (shakeService != null) {
                    if (this.t > 1) {
                        shakeService.setJumpShake(true);
                        timber.log.a.g("logger").a("打开摇一摇", new Object[0]);
                    } else {
                        shakeService.setJumpShake(false);
                        timber.log.a.g("logger").a("关闭摇一摇", new Object[0]);
                    }
                }
                File file = new File(this.r);
                if (file.exists()) {
                    o(file);
                }
            }
        }
    }

    public void N() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1190, new Class[0], Void.TYPE).isSupported) {
            u uVar = this.o;
            if (uVar != null) {
                uVar.c();
                this.o = null;
            }
            HttpService httpService = this.p;
            if (httpService != null) {
                httpService.cancelRequest();
                this.p = null;
            }
        }
    }

    public void E() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1191, new Class[0], Void.TYPE).isSupported) {
            SharePreferenceUtils.setPrefBoolean(this.q, "needRetryUpload", false);
            if (this.o == null) {
                this.o = new u(u.b.FixedThread, 1, "deletefile");
            }
            if (!this.o.b()) {
                this.o.a(new C0016h());
            }
        }
    }

    /* renamed from: com.leedarson.log.h$h  reason: collision with other inner class name */
    /* compiled from: ReportTree */
    public class C0016h implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0016h() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1217, new Class[0], Void.TYPE).isSupported) {
                h hVar = h.this;
                String unused = hVar.u = SharePreferenceUtils.getPrefString(hVar.q, "h5LogFilePath", "");
                h.this.v.remove(h.this.u);
                if (!TextUtils.isEmpty(h.this.u)) {
                    File file = new File(h.this.u);
                    try {
                        a.b g = timber.log.a.g("logger");
                        g.a("delete h5 logFile:" + h.this.u, new Object[0]);
                        if (file.exists()) {
                            file.delete();
                        }
                        SharePreferenceUtils.setPrefString(h.this.q, "h5LogFilePath", "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void M(String str, j listener) {
        Class[] clsArr = {String.class, j.class};
        if (!PatchProxy.proxy(new Object[]{str, listener}, this, changeQuickRedirect, false, 1192, clsArr, Void.TYPE).isSupported) {
            String header = str;
            String base_url = SharePreferenceUtils.getPrefString(this.q, "httpServer", "");
            JSONObject msgObj = new JSONObject();
            try {
                msgObj.put("refreshToken", (Object) SharePreferenceUtils.getPrefString(this.q, "refreshToken", ""));
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            b0 b2 = b0.b();
            Context applicationContext = this.q.getApplicationContext();
            b2.K(applicationContext, (com.trello.rxlifecycle3.b<com.trello.rxlifecycle3.android.a>) null, base_url + "/user/refreshUserToken", header, msgObj.toString(), new i(listener));
        }
    }

    /* compiled from: ReportTree */
    public class i extends com.leedarson.base.http.observer.i<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ j c;

        i(j jVar) {
            this.c = jVar;
        }

        public /* bridge */ /* synthetic */ void onSuccess(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 1220, new Class[]{Object.class}, Void.TYPE).isSupported) {
                onSuccess((String) obj);
            }
        }

        public void onStart(io.reactivex.disposables.b d2) {
        }

        public void onError(ApiException apiException) {
            if (!PatchProxy.proxy(new Object[]{apiException}, this, changeQuickRedirect, false, 1218, new Class[]{ApiException.class}, Void.TYPE).isSupported) {
                timber.log.a.g("TAG").h("refreshToke onError: ", new Object[0]);
                j jVar = this.c;
                if (jVar != null) {
                    jVar.onError();
                }
            }
        }

        public void onSuccess(String response) {
            if (!PatchProxy.proxy(new Object[]{response}, this, changeQuickRedirect, false, 1219, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("TAG");
                g.h("refreshToke onSuccess: " + response, new Object[0]);
                try {
                    JSONObject dataObj = new JSONObject(response).getJSONObject("data");
                    if (dataObj.has("accessToken")) {
                        SharePreferenceUtils.setPrefString(h.this.q, "accessToken", dataObj.getString("accessToken"));
                    }
                    if (dataObj.has("refreshToken")) {
                        SharePreferenceUtils.setPrefString(h.this.q, "refreshToken", dataObj.getString("refreshToken"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                j jVar = this.c;
                if (jVar != null) {
                    jVar.onSuccess();
                }
            }
        }
    }

    private void U(String message, String level, String method) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{message, level, method}, this, changeQuickRedirect, false, 1193, clsArr, Void.TYPE).isSupported) {
            com.leedarson.log.elk.a.y(this).c(h.class.getSimpleName()).x(Constants.SERVICE_LOGGER).t("LdsLogger").o(level).p(message).s(method).a().b();
        }
    }
}
