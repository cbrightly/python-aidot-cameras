package com.leedarson.utils;

import android.content.Context;
import android.graphics.Color;
import com.leedarson.bean.CommandItem;
import com.leedarson.bean.SchemeBean;
import com.leedarson.serviceimpl.m;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.functions.e;
import io.reactivex.l;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: AssetsScript */
public class c {
    /* access modifiers changed from: private */
    public static String a = "AssetsScript";
    public static ChangeQuickRedirect changeQuickRedirect;

    /* renamed from: com.leedarson.utils.c$c  reason: collision with other inner class name */
    /* compiled from: AssetsScript */
    public interface C0195c {
        void onFinish();
    }

    static /* synthetic */ String b(String x0, Context x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 11462, new Class[]{String.class, Context.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : c(x0, x1);
    }

    public static void d(Context context, C0195c scriptListener) {
        if (!PatchProxy.proxy(new Object[]{context, scriptListener}, (Object) null, changeQuickRedirect, true, 11457, new Class[]{Context.class, C0195c.class}, Void.TYPE).isSupported) {
            timber.log.a.g(a).a("AssetsScript#parseJsonData", new Object[0]);
            String[] files = {"Classic", "Disco", "Pop"};
            l.y(files).b0(com.leedarson.base.http.observer.l.a).J(com.leedarson.base.http.observer.l.a).Y(new a(context, new AtomicInteger(0), files, scriptListener), new b());
        }
    }

    /* compiled from: AssetsScript */
    public class a implements e<String> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ Context c;
        final /* synthetic */ AtomicInteger d;
        final /* synthetic */ String[] f;
        final /* synthetic */ C0195c q;

        a(Context context, AtomicInteger atomicInteger, String[] strArr, C0195c cVar) {
            this.c = context;
            this.d = atomicInteger;
            this.f = strArr;
            this.q = cVar;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11464, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((String) obj);
            }
        }

        public void a(String key) {
            C0195c cVar;
            if (!PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 11463, new Class[]{String.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(c.a);
                g.a("start parse .." + key, new Object[0]);
                m.o().o.put(key, c.f(c.b(key + ".txt", this.c)));
                a.b g2 = timber.log.a.g(c.a);
                g2.a("finish parse .." + key, new Object[0]);
                if (this.d.incrementAndGet() == this.f.length && (cVar = this.q) != null) {
                    cVar.onFinish();
                }
            }
        }
    }

    /* compiled from: AssetsScript */
    public class b implements e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 11466, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 11465, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(c.a);
                g.c("parseJsonData error:" + throwable.toString(), new Object[0]);
            }
        }
    }

    public static SchemeBean f(String jsonData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonData}, (Object) null, changeQuickRedirect, true, 11458, new Class[]{String.class}, SchemeBean.class);
        if (proxy.isSupported) {
            return (SchemeBean) proxy.result;
        }
        SchemeBean schemeBean = new SchemeBean();
        try {
            JSONArray array = new JSONArray(jsonData);
            for (int i = 0; i < array.length(); i++) {
                a.b g = timber.log.a.g(a);
                g.a("解析" + i, new Object[0]);
                schemeBean.frequencyStageList.add(g(array.getJSONObject(i), i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return schemeBean;
    }

    private static SchemeBean.FrequencyStage g(JSONObject jsonObject, int freqIndex) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonObject, new Integer(freqIndex)}, (Object) null, changeQuickRedirect, true, 11459, new Class[]{JSONObject.class, Integer.TYPE}, SchemeBean.FrequencyStage.class);
        if (proxy.isSupported) {
            return (SchemeBean.FrequencyStage) proxy.result;
        }
        SchemeBean.FrequencyStage frequencyStage = new SchemeBean.FrequencyStage();
        int frequencyUpperLimit = jsonObject.getInt("frequencyUpperLimit");
        frequencyStage.frequencyLowerLimit = jsonObject.getInt("frequencyLowerLimit");
        frequencyStage.frequencyUpperLimit = frequencyUpperLimit;
        JSONArray modesArr = jsonObject.getJSONArray("modes");
        for (int i = 0; i < modesArr.length(); i++) {
            frequencyStage.modes.add(e(modesArr.getJSONObject(i), freqIndex, i));
        }
        return frequencyStage;
    }

    private static SchemeBean.AmpMode e(JSONObject modeJson, int freqIndex, int modeIndex) {
        CommandItem commandItem;
        Object[] objArr = {modeJson, new Integer(freqIndex), new Integer(modeIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11460, new Class[]{JSONObject.class, cls, cls}, SchemeBean.AmpMode.class);
        if (proxy.isSupported) {
            return (SchemeBean.AmpMode) proxy.result;
        }
        SchemeBean.AmpMode mode = new SchemeBean.AmpMode();
        mode.amplitudeUpperLimit = (float) modeJson.getDouble("amplitudeUpperLimit");
        mode.amplitudeLowerLimit = (float) modeJson.getDouble("amplitudeLowerLimit");
        mode.speed = modeJson.getInt("speed");
        mode.setCalcMaxBrightness(modeJson.getString("calcMaxBrightness"));
        mode.setModeKey(String.valueOf(freqIndex) + String.valueOf(modeIndex));
        JSONArray effectArr = modeJson.getJSONArray("lightingEffect");
        for (int i = 0; i < effectArr.length(); i++) {
            JSONObject json = effectArr.getJSONObject(i);
            String RGBW = json.getString("RGBW");
            int fading = json.getInt("Fading");
            try {
                commandItem = new CommandItem(fading, Color.parseColor(RGBW), json.getInt("Dimming"));
            } catch (Exception e) {
                CommandItem commandItem2 = new CommandItem(fading, Color.parseColor(RGBW), -1);
                commandItem2.setCalcParams(mode.getK(), mode.getB());
                commandItem = commandItem2;
            }
            mode.lightingEffect.add(commandItem);
        }
        return mode;
    }

    private static String c(String fileName, Context context) {
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{fileName, context}, (Object) null, changeQuickRedirect2, true, 11461, new Class[]{String.class, Context.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            while (true) {
                String readLine = bf.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
