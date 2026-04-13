package com.leedarson.base.utils;

import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.didichuxing.doraemonkit.kit.loginfo.helper.LogcatHelper;
import com.leedarson.serviceinterface.Constans;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TimeRecorder */
public class v {
    private static ConcurrentHashMap<String, Long> a;
    private static CopyOnWriteArrayList<a> b;
    private static ConcurrentHashMap<String, a> c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private static SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void d(String tag, String message) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{tag, message}, (Object) null, changeQuickRedirect, true, 532, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (a == null) {
                synchronized (v.class) {
                    if (a == null) {
                        a = new ConcurrentHashMap<>();
                        b = new CopyOnWriteArrayList<>();
                        c = new ConcurrentHashMap<>();
                    }
                }
            }
            if (a.containsKey(tag)) {
                long timestamp = a.get(tag).longValue();
                a _item = c.get(tag);
                if (_item != null) {
                    long endTimeMillis = System.currentTimeMillis();
                    _item.e = d.format(new Date(endTimeMillis));
                    _item.f = message + "";
                    _item.i = endTimeMillis;
                }
                int duration = (int) (System.currentTimeMillis() - timestamp);
                if (LogcatHelper.BUFFER_MAIN.equals(Thread.currentThread().getName())) {
                    Log.e("TimeRecorder", tag + " duration is " + duration + " ms,current thread is " + Thread.currentThread().getName());
                } else {
                    Log.d("TimeRecorder", tag + " duration is " + duration + " ms,current thread is " + Thread.currentThread().getName());
                }
                if ("CoreActivity#onCreate2loadH5".equals(tag)) {
                    Constans.onCreate2loadH5 = (long) duration;
                }
                if ("CoreActivity#launchHttpServer".equals(tag)) {
                    Constans.launchHttpServer = (long) duration;
                }
                if ("APP#loadH52DidRender".equals(tag)) {
                    Constans.loadH52DidRender = (long) duration;
                }
                if ("Application#attach#Splash#onCreate".equals(tag) && duration > 1000) {
                    duration = 1000;
                }
                e(tag, duration);
                a.remove(tag);
                return;
            }
            long startTimeMillis = System.currentTimeMillis();
            a.put(tag, Long.valueOf(startTimeMillis));
            a newItem = new a(tag, 0, Thread.currentThread().getName());
            newItem.h = startTimeMillis;
            newItem.g = tag;
            newItem.f = tag;
            newItem.d = d.format(new Date());
            newItem.f = message + "";
            c.put(tag, newItem);
        }
    }

    private static void e(String tag, int duration) {
        if (!PatchProxy.proxy(new Object[]{tag, new Integer(duration)}, (Object) null, changeQuickRedirect, true, 533, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (b == null) {
                b = new CopyOnWriteArrayList<>();
            }
            a item = new a(tag, duration, Thread.currentThread().getName());
            if (c.get(tag) != null) {
                item.d = c.get(tag).d;
                item.g = c.get(tag).a;
                item.e = c.get(tag).e;
                item.f = c.get(tag).f;
                item.h = c.get(tag).h;
                item.i = c.get(tag).i;
            }
            b.add(item);
        }
    }

    public static JSONArray c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 534, new Class[0], JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        JSONArray jsonArray = new JSONArray();
        CopyOnWriteArrayList<a> copyOnWriteArrayList = b;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            try {
                Iterator<a> it = b.iterator();
                while (it.hasNext()) {
                    a item = it.next();
                    JSONObject json1 = new JSONObject();
                    json1.put(TypedValues.TransitionType.S_DURATION, item.b).put("thread", (Object) item.c).put("start", (Object) item.d).put("end", (Object) item.e).put("step", (Object) item.g).put("message", (Object) item.f);
                    jsonArray.put((Object) json1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    public static int b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 535, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        long duration = 0;
        CopyOnWriteArrayList<a> copyOnWriteArrayList = b;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            long startTime = 0;
            Iterator<a> it = b.iterator();
            while (it.hasNext()) {
                a item = it.next();
                if (startTime == 0) {
                    startTime = item.h;
                    duration = item.i - item.h;
                } else {
                    duration = item.i - startTime;
                }
            }
        }
        return (int) duration;
    }

    public static void a() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 536, new Class[0], Void.TYPE).isSupported) {
            ConcurrentHashMap<String, Long> concurrentHashMap = a;
            if (concurrentHashMap != null) {
                concurrentHashMap.clear();
            }
            CopyOnWriteArrayList<a> copyOnWriteArrayList = b;
            if (copyOnWriteArrayList != null) {
                copyOnWriteArrayList.clear();
            }
            ConcurrentHashMap<String, a> concurrentHashMap2 = c;
            if (concurrentHashMap2 != null) {
                concurrentHashMap2.clear();
            }
        }
    }

    /* compiled from: TimeRecorder */
    public static class a {
        public String a;
        public int b;
        public String c;
        public String d = "";
        public String e = "";
        public String f = "";
        public String g = "";
        public long h;
        public long i;

        public a(String tag, int duration, String threadName) {
            this.a = tag;
            this.b = duration;
            this.c = threadName;
        }
    }
}
