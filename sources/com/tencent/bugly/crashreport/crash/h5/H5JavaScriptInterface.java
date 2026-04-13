package com.tencent.bugly.crashreport.crash.h5;

import android.webkit.JavascriptInterface;
import com.google.maps.android.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.inner.InnerApi;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: BUGLY */
public class H5JavaScriptInterface {
    private static HashSet<Integer> a = new HashSet<>();
    private String b = null;
    private Thread c = null;
    private String d = null;
    private Map<String, String> e = null;

    private H5JavaScriptInterface() {
    }

    public static H5JavaScriptInterface getInstance(CrashReport.WebViewInterface webViewInterface) {
        String str = null;
        if (webViewInterface == null || a.contains(Integer.valueOf(webViewInterface.hashCode()))) {
            return null;
        }
        H5JavaScriptInterface h5JavaScriptInterface = new H5JavaScriptInterface();
        a.add(Integer.valueOf(webViewInterface.hashCode()));
        Thread currentThread = Thread.currentThread();
        h5JavaScriptInterface.c = currentThread;
        if (currentThread != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (int i = 2; i < currentThread.getStackTrace().length; i++) {
                StackTraceElement stackTraceElement = currentThread.getStackTrace()[i];
                if (!stackTraceElement.toString().contains("crashreport")) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
            }
            str = sb.toString();
        }
        h5JavaScriptInterface.d = str;
        HashMap hashMap = new HashMap();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(webViewInterface.getContentDescription());
        hashMap.put("[WebView] ContentDescription", sb2.toString());
        h5JavaScriptInterface.e = hashMap;
        return h5JavaScriptInterface;
    }

    private static a a(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            a aVar = new a();
            String string = jSONObject.getString("projectRoot");
            aVar.a = string;
            if (string == null) {
                return null;
            }
            String string2 = jSONObject.getString("context");
            aVar.b = string2;
            if (string2 == null) {
                return null;
            }
            String string3 = jSONObject.getString("url");
            aVar.c = string3;
            if (string3 == null) {
                return null;
            }
            String string4 = jSONObject.getString("userAgent");
            aVar.d = string4;
            if (string4 == null) {
                return null;
            }
            String string5 = jSONObject.getString(IjkMediaMeta.IJKM_KEY_LANGUAGE);
            aVar.e = string5;
            if (string5 == null) {
                return null;
            }
            String string6 = jSONObject.getString("name");
            aVar.f = string6;
            if (string6 != null) {
                if (!string6.equals(BuildConfig.TRAVIS)) {
                    String string7 = jSONObject.getString("stacktrace");
                    if (string7 == null) {
                        return null;
                    }
                    int indexOf = string7.indexOf("\n");
                    if (indexOf < 0) {
                        x.d("H5 crash stack's format is wrong!", new Object[0]);
                        return null;
                    }
                    aVar.h = string7.substring(indexOf + 1);
                    String substring = string7.substring(0, indexOf);
                    aVar.g = substring;
                    int indexOf2 = substring.indexOf(":");
                    if (indexOf2 > 0) {
                        aVar.g = aVar.g.substring(indexOf2 + 1);
                    }
                    aVar.i = jSONObject.getString("file");
                    if (aVar.f == null) {
                        return null;
                    }
                    long j = jSONObject.getLong("lineNumber");
                    aVar.j = j;
                    if (j < 0) {
                        return null;
                    }
                    long j2 = jSONObject.getLong("columnNumber");
                    aVar.k = j2;
                    if (j2 < 0) {
                        return null;
                    }
                    x.a("H5 crash information is following: ", new Object[0]);
                    x.a("[projectRoot]: " + aVar.a, new Object[0]);
                    x.a("[context]: " + aVar.b, new Object[0]);
                    x.a("[url]: " + aVar.c, new Object[0]);
                    x.a("[userAgent]: " + aVar.d, new Object[0]);
                    x.a("[language]: " + aVar.e, new Object[0]);
                    x.a("[name]: " + aVar.f, new Object[0]);
                    x.a("[message]: " + aVar.g, new Object[0]);
                    x.a("[stacktrace]: \n" + aVar.h, new Object[0]);
                    x.a("[file]: " + aVar.i, new Object[0]);
                    x.a("[lineNumber]: " + aVar.j, new Object[0]);
                    x.a("[columnNumber]: " + aVar.k, new Object[0]);
                    return aVar;
                }
            }
            return null;
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
            return null;
        }
    }

    @JavascriptInterface
    public void printLog(String str) {
        x.d("Log from js: %s", str);
    }

    @JavascriptInterface
    public void reportJSException(String str) {
        if (str == null) {
            x.d("Payload from JS is null.", new Object[0]);
            return;
        }
        String b2 = z.b(str.getBytes());
        String str2 = this.b;
        if (str2 == null || !str2.equals(b2)) {
            this.b = b2;
            x.d("Handling JS exception ...", new Object[0]);
            a a2 = a(str);
            if (a2 == null) {
                x.d("Failed to parse payload.", new Object[0]);
                return;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            String str3 = a2.a;
            if (str3 != null) {
                linkedHashMap2.put("[JS] projectRoot", str3);
            }
            String str4 = a2.b;
            if (str4 != null) {
                linkedHashMap2.put("[JS] context", str4);
            }
            String str5 = a2.c;
            if (str5 != null) {
                linkedHashMap2.put("[JS] url", str5);
            }
            String str6 = a2.d;
            if (str6 != null) {
                linkedHashMap2.put("[JS] userAgent", str6);
            }
            String str7 = a2.i;
            if (str7 != null) {
                linkedHashMap2.put("[JS] file", str7);
            }
            long j = a2.j;
            if (j != 0) {
                linkedHashMap2.put("[JS] lineNumber", Long.toString(j));
            }
            linkedHashMap.putAll(linkedHashMap2);
            linkedHashMap.putAll(this.e);
            linkedHashMap.put("Java Stack", this.d);
            InnerApi.postH5CrashAsync(this.c, a2.f, a2.g, a2.h, linkedHashMap);
            return;
        }
        x.d("Same payload from js. Please check whether you've injected bugly.js more than one times.", new Object[0]);
    }
}
