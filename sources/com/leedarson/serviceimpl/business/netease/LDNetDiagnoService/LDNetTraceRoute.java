package com.leedarson.serviceimpl.business.netease.LDNetDiagnoService;

import android.util.Log;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LDNetTraceRoute {
    private static final String MATCH_PING_IP = "(?<=from ).*(?=: icmp_seq=1 ttl=)";
    private static final String MATCH_PING_TIME = "(?<=time=).*?ms";
    private static final String MATCH_TRACE_IP = "(?<=From )(?:[0-9]{1,3}\\.){3}[0-9]{1,3}";
    public static ChangeQuickRedirect changeQuickRedirect;
    private static LDNetTraceRoute instance;
    static boolean loaded = true;
    private final String LOG_TAG = "LDNetTraceRoute";
    public boolean isCTrace = true;
    LDNetTraceRouteListener listener;

    public interface LDNetTraceRouteListener {
        void OnNetTraceFinished();

        void OnNetTraceUpdated(String str);
    }

    public native void startJNICTraceRoute(String str);

    private LDNetTraceRoute() {
    }

    public static LDNetTraceRoute getInstance() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 7207, new Class[0], LDNetTraceRoute.class);
        if (proxy.isSupported) {
            return (LDNetTraceRoute) proxy.result;
        }
        if (instance == null) {
            instance = new LDNetTraceRoute();
        }
        return instance;
    }

    public void initListenter(LDNetTraceRouteListener listener2) {
        this.listener = listener2;
    }

    public void startTraceRoute(String host) {
        if (!PatchProxy.proxy(new Object[]{host}, this, changeQuickRedirect, false, 7208, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (!this.isCTrace || !loaded) {
                execTrace(new TraceTask(host, 1));
                return;
            }
            try {
                startJNICTraceRoute(host);
            } catch (UnsatisfiedLinkError e) {
                e.printStackTrace();
                Log.i("LDNetTraceRoute", "调用java模拟traceRoute");
                execTrace(new TraceTask(host, 1));
            }
        }
    }

    public void resetInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    static {
        try {
            System.loadLibrary("tracepath");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void printTraceInfo(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 7209, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.listener.OnNetTraceUpdated(log);
        }
    }

    private String execPing(PingTask ping) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{ping}, this, changeQuickRedirect, false, 7210, new Class[]{PingTask.class}, String.class);
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

    /* JADX WARNING: Removed duplicated region for block: B:105:0x0247 A[SYNTHETIC, Splitter:B:105:0x0247] */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x0252 A[SYNTHETIC, Splitter:B:111:0x0252] */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0234 A[SYNTHETIC, Splitter:B:96:0x0234] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:102:0x0242=Splitter:B:102:0x0242, B:93:0x022f=Splitter:B:93:0x022f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void execTrace(com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute.TraceTask r21) {
        /*
            r20 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r21
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute$TraceTask> r4 = com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute.TraceTask.class
            r6[r2] = r4
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 7211(0x1c2b, float:1.0105E-41)
            r2 = r20
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001e
            return
        L_0x001e:
            r1 = r20
            r2 = r21
            java.lang.String r3 = "(?<=From )(?:[0-9]{1,3}\\.){3}[0-9]{1,3}"
            java.util.regex.Pattern r3 = java.util.regex.Pattern.compile(r3)
            java.lang.String r4 = "(?<=from ).*(?=: icmp_seq=1 ttl=)"
            java.util.regex.Pattern r4 = java.util.regex.Pattern.compile(r4)
            java.lang.String r5 = "(?<=time=).*?ms"
            java.util.regex.Pattern r5 = java.util.regex.Pattern.compile(r5)
            r6 = 0
            r7 = 0
            r8 = 0
        L_0x0037:
            if (r8 != 0) goto L_0x025b
            int r9 = r2.getHop()     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            r10 = 10
            if (r9 >= r10) goto L_0x025b
            java.lang.String r9 = ""
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            r10.<init>()     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            java.lang.String r11 = "ping -c 1 -t "
            r10.append(r11)     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            int r11 = r2.getHop()     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            r10.append(r11)     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            java.lang.String r11 = " "
            r10.append(r11)     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            java.lang.String r11 = r2.getHost()     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            r10.append(r11)     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            java.lang.String r10 = r10.toString()     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            java.lang.Runtime r11 = java.lang.Runtime.getRuntime()     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            java.lang.Process r11 = r11.exec(r10)     // Catch:{ IOException -> 0x023d, InterruptedException -> 0x022a, all -> 0x0223 }
            r6 = r11
            java.io.BufferedReader r11 = new java.io.BufferedReader     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            java.io.InputStreamReader r12 = new java.io.InputStreamReader     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            java.io.InputStream r13 = r6.getInputStream()     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            r12.<init>(r13)     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            r11.<init>(r12)     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            r7 = r11
            r11 = 0
        L_0x007d:
            java.lang.String r12 = r7.readLine()     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            r11 = r12
            if (r12 == 0) goto L_0x00ab
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00a4, InterruptedException -> 0x009d, all -> 0x0095 }
            r12.<init>()     // Catch:{ IOException -> 0x00a4, InterruptedException -> 0x009d, all -> 0x0095 }
            r12.append(r9)     // Catch:{ IOException -> 0x00a4, InterruptedException -> 0x009d, all -> 0x0095 }
            r12.append(r11)     // Catch:{ IOException -> 0x00a4, InterruptedException -> 0x009d, all -> 0x0095 }
            java.lang.String r12 = r12.toString()     // Catch:{ IOException -> 0x00a4, InterruptedException -> 0x009d, all -> 0x0095 }
            r9 = r12
            goto L_0x007d
        L_0x0095:
            r0 = move-exception
            r21 = r3
            r17 = r4
            r3 = r0
            goto L_0x0250
        L_0x009d:
            r0 = move-exception
            r21 = r3
            r17 = r4
            goto L_0x022f
        L_0x00a4:
            r0 = move-exception
            r21 = r3
            r17 = r4
            goto L_0x0242
        L_0x00ab:
            r7.close()     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            r6.waitFor()     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            java.util.regex.Matcher r12 = r3.matcher(r9)     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            r14 = 256(0x100, float:3.59E-43)
            r13.<init>(r14)     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            boolean r14 = r12.find()     // Catch:{ IOException -> 0x021b, InterruptedException -> 0x0213, all -> 0x020a }
            java.lang.String r15 = "\t"
            java.lang.String r0 = "\t\t timeout \t"
            r21 = r3
            java.lang.String r3 = "\t\t"
            if (r14 == 0) goto L_0x0169
            java.lang.String r14 = r12.group()     // Catch:{ IOException -> 0x0162, InterruptedException -> 0x015b, all -> 0x0153 }
            r16 = r6
            com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute$PingTask r6 = new com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute$PingTask     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r6.<init>(r14)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            java.lang.String r17 = r1.execPing(r6)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r18 = r17
            int r17 = r18.length()     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            if (r17 != 0) goto L_0x00e8
            java.lang.String r0 = "unknown host or network error\n"
            r13.append(r0)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r8 = 1
            goto L_0x0138
        L_0x00e8:
            r17 = r6
            r6 = r18
            java.util.regex.Matcher r18 = r5.matcher(r6)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            boolean r19 = r18.find()     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            if (r19 == 0) goto L_0x0114
            java.lang.String r0 = r18.group()     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r19 = r6
            int r6 = r2.getHop()     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r6)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r3)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r14)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r3)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r0)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r15)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            goto L_0x0126
        L_0x0114:
            r19 = r6
            int r6 = r2.getHop()     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r6)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r3)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r14)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r13.append(r0)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
        L_0x0126:
            com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute$LDNetTraceRouteListener r0 = r1.listener     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            java.lang.String r3 = r13.toString()     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r0.OnNetTraceUpdated(r3)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            int r0 = r2.getHop()     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
            r3 = 1
            int r0 = r0 + r3
            r2.setHop(r0)     // Catch:{ IOException -> 0x014c, InterruptedException -> 0x0145, all -> 0x013d }
        L_0x0138:
            r17 = r4
            r3 = 1
            goto L_0x01df
        L_0x013d:
            r0 = move-exception
            r3 = r0
            r17 = r4
            r6 = r16
            goto L_0x0250
        L_0x0145:
            r0 = move-exception
            r17 = r4
            r6 = r16
            goto L_0x022f
        L_0x014c:
            r0 = move-exception
            r17 = r4
            r6 = r16
            goto L_0x0242
        L_0x0153:
            r0 = move-exception
            r16 = r6
            r3 = r0
            r17 = r4
            goto L_0x0250
        L_0x015b:
            r0 = move-exception
            r16 = r6
            r17 = r4
            goto L_0x022f
        L_0x0162:
            r0 = move-exception
            r16 = r6
            r17 = r4
            goto L_0x0242
        L_0x0169:
            r16 = r6
            java.util.regex.Matcher r6 = r4.matcher(r9)     // Catch:{ IOException -> 0x0204, InterruptedException -> 0x01fe, all -> 0x01f6 }
            boolean r14 = r6.find()     // Catch:{ IOException -> 0x0204, InterruptedException -> 0x01fe, all -> 0x01f6 }
            if (r14 == 0) goto L_0x01b3
            java.lang.String r0 = r6.group()     // Catch:{ IOException -> 0x0204, InterruptedException -> 0x01fe, all -> 0x01f6 }
            java.util.regex.Matcher r14 = r5.matcher(r9)     // Catch:{ IOException -> 0x0204, InterruptedException -> 0x01fe, all -> 0x01f6 }
            boolean r17 = r14.find()     // Catch:{ IOException -> 0x0204, InterruptedException -> 0x01fe, all -> 0x01f6 }
            if (r17 == 0) goto L_0x01ad
            java.lang.String r17 = r14.group()     // Catch:{ IOException -> 0x0204, InterruptedException -> 0x01fe, all -> 0x01f6 }
            r18 = r17
            r17 = r4
            int r4 = r2.getHop()     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r13.append(r4)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r13.append(r3)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r13.append(r0)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r13.append(r3)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r3 = r18
            r13.append(r3)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r13.append(r15)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute$LDNetTraceRouteListener r4 = r1.listener     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            java.lang.String r15 = r13.toString()     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r4.OnNetTraceUpdated(r15)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            goto L_0x01af
        L_0x01ad:
            r17 = r4
        L_0x01af:
            r0 = 1
            r8 = r0
            r3 = 1
            goto L_0x01df
        L_0x01b3:
            r17 = r4
            int r3 = r9.length()     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            if (r3 != 0) goto L_0x01c3
            java.lang.String r0 = "unknown host or network error\t"
            r13.append(r0)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r8 = 1
            r3 = 1
            goto L_0x01d6
        L_0x01c3:
            int r3 = r2.getHop()     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r13.append(r3)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r13.append(r0)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            int r0 = r2.getHop()     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r3 = 1
            int r0 = r0 + r3
            r2.setHop(r0)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
        L_0x01d6:
            com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute$LDNetTraceRouteListener r0 = r1.listener     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            java.lang.String r4 = r13.toString()     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
            r0.OnNetTraceUpdated(r4)     // Catch:{ IOException -> 0x01f2, InterruptedException -> 0x01ee, all -> 0x01e8 }
        L_0x01df:
            r0 = r3
            r6 = r16
            r4 = r17
            r3 = r21
            goto L_0x0037
        L_0x01e8:
            r0 = move-exception
            r3 = r0
            r6 = r16
            goto L_0x0250
        L_0x01ee:
            r0 = move-exception
            r6 = r16
            goto L_0x022f
        L_0x01f2:
            r0 = move-exception
            r6 = r16
            goto L_0x0242
        L_0x01f6:
            r0 = move-exception
            r17 = r4
            r3 = r0
            r6 = r16
            goto L_0x0250
        L_0x01fe:
            r0 = move-exception
            r17 = r4
            r6 = r16
            goto L_0x022f
        L_0x0204:
            r0 = move-exception
            r17 = r4
            r6 = r16
            goto L_0x0242
        L_0x020a:
            r0 = move-exception
            r21 = r3
            r17 = r4
            r16 = r6
            r3 = r0
            goto L_0x0250
        L_0x0213:
            r0 = move-exception
            r21 = r3
            r17 = r4
            r16 = r6
            goto L_0x022f
        L_0x021b:
            r0 = move-exception
            r21 = r3
            r17 = r4
            r16 = r6
            goto L_0x0242
        L_0x0223:
            r0 = move-exception
            r21 = r3
            r17 = r4
            r3 = r0
            goto L_0x0250
        L_0x022a:
            r0 = move-exception
            r21 = r3
            r17 = r4
        L_0x022f:
            r0.printStackTrace()     // Catch:{ all -> 0x024e }
            if (r7 == 0) goto L_0x0237
            r7.close()     // Catch:{ Exception -> 0x023b }
        L_0x0237:
            r6.destroy()     // Catch:{ Exception -> 0x023b }
        L_0x023a:
            goto L_0x0268
        L_0x023b:
            r0 = move-exception
            goto L_0x0268
        L_0x023d:
            r0 = move-exception
            r21 = r3
            r17 = r4
        L_0x0242:
            r0.printStackTrace()     // Catch:{ all -> 0x024e }
            if (r7 == 0) goto L_0x024a
            r7.close()     // Catch:{ Exception -> 0x023b }
        L_0x024a:
            r6.destroy()     // Catch:{ Exception -> 0x023b }
            goto L_0x023a
        L_0x024e:
            r0 = move-exception
            r3 = r0
        L_0x0250:
            if (r7 == 0) goto L_0x0255
            r7.close()     // Catch:{ Exception -> 0x0259 }
        L_0x0255:
            r6.destroy()     // Catch:{ Exception -> 0x0259 }
            goto L_0x025a
        L_0x0259:
            r0 = move-exception
        L_0x025a:
            throw r3
        L_0x025b:
            r21 = r3
            r17 = r4
            if (r7 == 0) goto L_0x0264
            r7.close()     // Catch:{ Exception -> 0x023b }
        L_0x0264:
            r6.destroy()     // Catch:{ Exception -> 0x023b }
            goto L_0x023a
        L_0x0268:
            com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute$LDNetTraceRouteListener r0 = r1.listener
            r0.OnNetTraceFinished()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute.execTrace(com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetTraceRoute$TraceTask):void");
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
}
