package com.leedarson.serviceimpl.business.netease;

import android.util.Log;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.http.observer.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import timber.log.a;

public class MTR {
    private static final String MATCH_PING_IP = "(?<=from ).*(?=: icmp_seq=1 ttl=)";
    private static final String MATCH_PING_TIME = "(?<=time=).*?ms";
    private static final String MATCH_TRACE_IP = "(?<=From )(?:[0-9]{1,3}\\.){3}[0-9]{1,3}";
    public static ChangeQuickRedirect changeQuickRedirect;
    private static MTR instance;
    /* access modifiers changed from: private */
    public final String TAG = MTR.class.getSimpleName();
    /* access modifiers changed from: private */
    public AtomicBoolean busy = new AtomicBoolean(false);
    boolean finish = false;
    LDNetTraceRouteListener listener;
    Pattern patternIp = Pattern.compile(MATCH_PING_IP);
    Pattern patternTime = Pattern.compile(MATCH_PING_TIME);
    Pattern patternTrace = Pattern.compile(MATCH_TRACE_IP);
    private ExecutorService service;

    public interface LDNetPingListener {
        void OnNetPingResult(TraceResp traceResp);
    }

    public interface LDNetTraceRouteListener {
        void OnNetTraceFinished();

        void OnNetTraceUpdated(TraceResp traceResp);
    }

    static /* synthetic */ void access$000(MTR x0, String x1, int x2, TraceResp x3) {
        Object[] objArr = {x0, x1, new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 7237, new Class[]{MTR.class, String.class, Integer.TYPE, TraceResp.class}, Void.TYPE).isSupported) {
            x0.keepPing(x1, x2, x3);
        }
    }

    static /* synthetic */ void access$300(MTR x0, TraceTask x1) {
        Class[] clsArr = {MTR.class, TraceTask.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 7238, clsArr, Void.TYPE).isSupported) {
            x0.execTrace(x1);
        }
    }

    public static MTR getInstance() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 7229, new Class[0], MTR.class);
        if (proxy.isSupported) {
            return (MTR) proxy.result;
        }
        if (instance == null) {
            instance = new MTR();
        }
        return instance;
    }

    public void startTraceRoute(String host, LDNetTraceRouteListener listener2) {
        if (!PatchProxy.proxy(new Object[]{host, listener2}, this, changeQuickRedirect, false, 7230, new Class[]{String.class, LDNetTraceRouteListener.class}, Void.TYPE).isSupported) {
            if (this.busy.get()) {
                a.g(this.TAG).a("startTraceRoute is busy now", new Object[0]);
                return;
            }
            this.listener = listener2;
            if (this.service == null) {
                this.service = l.i(1, "traceRoute", 1);
            }
            this.service.submit(new TraceRunnable(host));
        }
    }

    public void startPing(final String host, final LDNetPingListener pingListener) {
        if (!PatchProxy.proxy(new Object[]{host, pingListener}, this, changeQuickRedirect, false, 7231, new Class[]{String.class, LDNetPingListener.class}, Void.TYPE).isSupported) {
            if (this.service == null) {
                this.service = l.i(1, "startPing", 1);
            }
            this.service.submit(new Runnable() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7239, new Class[0], Void.TYPE).isSupported) {
                        TraceResp traceResp = new TraceResp(host, 0);
                        MTR.access$000(MTR.this, host, 4, traceResp);
                        LDNetPingListener lDNetPingListener = pingListener;
                        if (lDNetPingListener != null) {
                            lDNetPingListener.OnNetPingResult(traceResp);
                        }
                    }
                }
            });
        }
    }

    public void interrupt() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7232, new Class[0], Void.TYPE).isSupported) {
            a.g(this.TAG).a("#interrupt", new Object[0]);
            this.busy.set(false);
            this.finish = true;
            ExecutorService executorService = this.service;
            if (executorService != null && !executorService.isShutdown()) {
                this.service.shutdownNow();
                this.service = null;
            }
        }
    }

    public class TraceRunnable implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        private String host;

        public TraceRunnable(String host2) {
            this.host = host2;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7246, new Class[0], Void.TYPE).isSupported) {
                a.g(MTR.this.TAG).a("TraceRunnable", new Object[0]);
                MTR.this.busy.set(true);
                MTR.access$300(MTR.this, new TraceTask(this.host, 1));
            }
        }
    }

    private String execPing(PingTask ping) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ping}, this, changeQuickRedirect, false, 7233, new Class[]{PingTask.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        Process process = null;
        String str = "";
        BufferedReader reader = null;
        try {
            Process process2 = Runtime.getRuntime().exec("ping -c 1 " + ping.getHost());
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
            while (true) {
                String readLine = reader2.readLine();
                String line = readLine;
                if (readLine == null) {
                    break;
                }
                str = str + line;
            }
            reader2.close();
            process2.waitFor();
            try {
                reader2.close();
                process2.destroy();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (reader != null) {
                reader.close();
            }
            process.destroy();
        } catch (InterruptedException e3) {
            e3.printStackTrace();
            if (reader != null) {
                reader.close();
            }
            process.destroy();
        } catch (Throwable th) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e4) {
                    throw th;
                }
            }
            process.destroy();
            throw th;
        }
        return str;
    }

    private void execTrace(TraceTask traceTask) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{traceTask}, this, changeQuickRedirect, false, 7234, new Class[]{TraceTask.class}, Void.TYPE).isSupported) {
            TraceTask trace = traceTask;
            Process process = null;
            BufferedReader reader = null;
            while (!this.finish && trace.getHop() < 64) {
                try {
                    String str = "";
                    String command = "ping -c 1 -t " + trace.getHop() + " " + trace.getHost();
                    Log.e("CZB", "trace task:" + command);
                    process = Runtime.getRuntime().exec(command);
                    reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    while (true) {
                        String readLine = reader.readLine();
                        String line = readLine;
                        if (readLine == null) {
                            break;
                        }
                        str = str + line;
                    }
                    reader.close();
                    process.waitFor();
                    a.g(this.TAG).a(str, new Object[i]);
                    Matcher m = this.patternTrace.matcher(str);
                    if (m.find()) {
                        String pingIp = m.group();
                        TraceResp traceResp = new TraceResp(pingIp, trace.getHop());
                        keepPing(pingIp, 4, traceResp);
                        this.listener.OnNetTraceUpdated(traceResp);
                        trace.setHop(trace.getHop() + 1);
                    } else {
                        Matcher matchPingIp = this.patternIp.matcher(str);
                        if (matchPingIp.find()) {
                            String pingIp2 = matchPingIp.group();
                            Matcher matcherTime = this.patternTime.matcher(str);
                            if (matcherTime.find()) {
                                String time = matcherTime.group();
                                TraceResp traceResp2 = new TraceResp(pingIp2, trace.getHop());
                                PingResp pingResp = new PingResp(pingIp2);
                                pingResp.result = time;
                                traceResp2.addPingResp(pingResp);
                                this.listener.OnNetTraceUpdated(traceResp2);
                            }
                            this.finish = true;
                        } else {
                            this.listener.OnNetTraceUpdated(new TraceResp("********", trace.getHop()));
                            trace.setHop(trace.getHop() + 1);
                        }
                    }
                    i = 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                    process.destroy();
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                    if (reader != null) {
                        reader.close();
                    }
                    process.destroy();
                } catch (Throwable th) {
                    Throwable th2 = th;
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (Exception e3) {
                            throw th2;
                        }
                    }
                    process.destroy();
                    throw th2;
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e4) {
                }
            }
            process.destroy();
            this.listener.OnNetTraceFinished();
        }
    }

    private void keepPing(String pingIp, int pingCount, TraceResp traceResp) {
        Object[] objArr = {pingIp, new Integer(pingCount), traceResp};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7235, new Class[]{String.class, Integer.TYPE, TraceResp.class}, Void.TYPE).isSupported) {
            for (int i = 0; i < pingCount; i++) {
                PingResp pingResp = execPingResponse(pingIp);
                if (pingResp != null) {
                    traceResp.addPingResp(pingResp);
                }
            }
        }
    }

    private PingResp execPingResponse(String pingIp) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{pingIp}, this, changeQuickRedirect, false, 7236, new Class[]{String.class}, PingResp.class);
        if (proxy.isSupported) {
            return (PingResp) proxy.result;
        }
        String status = execPing(new PingTask(pingIp));
        if (status.length() == 0) {
            return null;
        }
        PingResp resp = new PingResp(pingIp);
        a.g(this.TAG).a(status, new Object[0]);
        Matcher matcherTime = this.patternTime.matcher(status);
        if (matcherTime.find()) {
            resp.result = matcherTime.group();
        } else {
            resp.result = "timeout";
        }
        return resp;
    }

    public class PingTask {
        private static final String MATCH_PING_HOST_IP = "(?<=\\().*?(?=\\))";
        public static ChangeQuickRedirect changeQuickRedirect;
        private String host;

        public String getHost() {
            return this.host;
        }

        public PingTask(String host2) {
            this.host = host2;
            Matcher m = Pattern.compile(MATCH_PING_HOST_IP).matcher(host2);
            if (m.find()) {
                this.host = m.group();
            }
        }
    }

    public class TraceTask {
        public static ChangeQuickRedirect changeQuickRedirect;
        private int hop;
        private final String host;

        public TraceTask(String host2, int hop2) {
            this.host = host2;
            this.hop = hop2;
        }

        public String getHost() {
            return this.host;
        }

        public int getHop() {
            return this.hop;
        }

        public void setHop(int hop2) {
            this.hop = hop2;
        }
    }

    public static class TraceResp {
        public static ChangeQuickRedirect changeQuickRedirect;
        private float avg;
        private float best;
        private int index;
        private float lossRate;
        private String nodeIp;
        private List<PingResp> pingRespList;
        private float worst;

        public float getLossRate() {
            return this.lossRate;
        }

        public TraceResp(String nodeIp2, int index2) {
            this.nodeIp = nodeIp2;
            this.index = index2;
        }

        public void addPingResp(PingResp pingResp) {
            if (!PatchProxy.proxy(new Object[]{pingResp}, this, changeQuickRedirect, false, 7241, new Class[]{PingResp.class}, Void.TYPE).isSupported) {
                if (this.pingRespList == null) {
                    this.pingRespList = new CopyOnWriteArrayList();
                }
                this.pingRespList.add(pingResp);
            }
        }

        public String getNodeIp() {
            return this.nodeIp;
        }

        public float getBest() {
            return this.best;
        }

        public float getWorst() {
            return this.worst;
        }

        public String toFormatString() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7242, new Class[0], String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            analyse();
            StringBuffer sb = new StringBuffer();
            sb.append(this.index);
            sb.append("\t\t");
            sb.append(this.nodeIp);
            sb.append("\t\t");
            sb.append(this.best);
            sb.append("ms");
            sb.append("\t\t");
            sb.append(this.worst);
            sb.append("ms");
            sb.append("\t\t");
            sb.append(String.format(Locale.US, "%.1f", new Object[]{Float.valueOf(this.avg)}));
            sb.append("ms");
            sb.append("\t\t");
            sb.append(this.lossRate * 100.0f);
            sb.append("%");
            return sb.toString();
        }

        public JSONObject toJson() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7243, new Class[0], JSONObject.class);
            if (proxy.isSupported) {
                return (JSONObject) proxy.result;
            }
            analyse();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(FirebaseAnalytics.Param.INDEX, this.index);
            jsonObject.put(SerializableCookie.HOST, (Object) this.nodeIp);
            jsonObject.put("best", (double) shortFloat(this.best));
            jsonObject.put("worst", (double) shortFloat(this.worst));
            jsonObject.put("average", (double) shortFloat(this.avg));
            jsonObject.put("lossRate", (double) this.lossRate);
            return jsonObject;
        }

        private float shortFloat(float f) {
            Object[] objArr = {new Float(f)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Float.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7244, new Class[]{cls}, cls);
            if (proxy.isSupported) {
                return ((Float) proxy.result).floatValue();
            }
            return Float.valueOf(String.format(Locale.US, "%.1f", new Object[]{Float.valueOf(f)})).floatValue();
        }

        /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
            r1 = r8;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void analyse() {
            /*
                r8 = this;
                r0 = 0
                java.lang.Object[] r1 = new java.lang.Object[r0]
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 7245(0x1c4d, float:1.0152E-41)
                r2 = r8
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r1 = r1.isSupported
                if (r1 == 0) goto L_0x0016
                return
            L_0x0016:
                r1 = r8
                java.util.List<com.leedarson.serviceimpl.business.netease.MTR$PingResp> r2 = r1.pingRespList
                if (r2 == 0) goto L_0x00a6
                int r2 = r2.size()
                if (r2 <= 0) goto L_0x00a6
                r2 = 0
                java.util.List<com.leedarson.serviceimpl.business.netease.MTR$PingResp> r3 = r1.pingRespList
                java.lang.Object r3 = r3.get(r0)
                com.leedarson.serviceimpl.business.netease.MTR$PingResp r3 = (com.leedarson.serviceimpl.business.netease.MTR.PingResp) r3
                float r3 = r3.parseTime()
                r1.best = r3
                java.util.List<com.leedarson.serviceimpl.business.netease.MTR$PingResp> r3 = r1.pingRespList
                java.lang.Object r0 = r3.get(r0)
                com.leedarson.serviceimpl.business.netease.MTR$PingResp r0 = (com.leedarson.serviceimpl.business.netease.MTR.PingResp) r0
                float r0 = r0.parseTime()
                r1.worst = r0
                r0 = 0
                java.util.List<com.leedarson.serviceimpl.business.netease.MTR$PingResp> r3 = r1.pingRespList
                java.util.Iterator r3 = r3.iterator()
            L_0x0045:
                boolean r4 = r3.hasNext()
                if (r4 == 0) goto L_0x008d
                java.lang.Object r4 = r3.next()
                com.leedarson.serviceimpl.business.netease.MTR$PingResp r4 = (com.leedarson.serviceimpl.business.netease.MTR.PingResp) r4
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "#analyse.pingResp:"
                r5.append(r6)
                java.lang.String r6 = r4.result
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                java.lang.String r6 = "MTR"
                android.util.Log.e(r6, r5)
                float r5 = r4.parseTime()
                r6 = 0
                int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
                if (r6 >= 0) goto L_0x0075
                int r2 = r2 + 1
                goto L_0x008c
            L_0x0075:
                float r6 = r1.best
                r7 = -1082130432(0xffffffffbf800000, float:-1.0)
                int r7 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
                if (r7 == 0) goto L_0x0083
                int r6 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
                if (r6 <= 0) goto L_0x0083
                r1.best = r5
            L_0x0083:
                float r6 = r1.worst
                int r6 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
                if (r6 >= 0) goto L_0x008b
                r1.worst = r5
            L_0x008b:
                float r0 = r0 + r5
            L_0x008c:
                goto L_0x0045
            L_0x008d:
                float r3 = (float) r2
                r4 = 1065353216(0x3f800000, float:1.0)
                float r3 = r3 * r4
                java.util.List<com.leedarson.serviceimpl.business.netease.MTR$PingResp> r4 = r1.pingRespList
                int r4 = r4.size()
                float r4 = (float) r4
                float r3 = r3 / r4
                r1.lossRate = r3
                java.util.List<com.leedarson.serviceimpl.business.netease.MTR$PingResp> r3 = r1.pingRespList
                int r3 = r3.size()
                float r3 = (float) r3
                float r3 = r0 / r3
                r1.avg = r3
            L_0x00a6:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.netease.MTR.TraceResp.analyse():void");
        }
    }

    public static class PingResp {
        public static ChangeQuickRedirect changeQuickRedirect;
        public String pingIp;
        public String result = "-1ms";

        public PingResp(String pingIp2) {
            this.pingIp = pingIp2;
        }

        public float parseTime() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7240, new Class[0], Float.TYPE);
            if (proxy.isSupported) {
                return ((Float) proxy.result).floatValue();
            }
            try {
                return Float.parseFloat(this.result.substring(0, this.result.indexOf("m")).trim());
            } catch (Exception e) {
                return -1.0f;
            }
        }
    }
}
