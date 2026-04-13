package com.sensorsdata.analytics.android.sdk.visual;

import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;
import com.sensorsdata.analytics.android.sdk.listener.SAEventListener;
import com.sensorsdata.analytics.android.sdk.util.ThreadUtils;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONObject;

public class VisualDebugHelper {
    private static final String TAG = "SA.VP.VisualDebugHelper";
    private TrackEventAdapter mEventListener = null;
    private JSONArray mJsonArray;
    private final Object object = new Object();

    /* access modifiers changed from: package-private */
    public void startMonitor() {
        try {
            if (this.mEventListener == null) {
                final ExecutorService executorService = ThreadUtils.getSinglePool();
                this.mEventListener = new TrackEventAdapter() {
                    public void trackEvent(final JSONObject jsonObject) {
                        executorService.execute(new Runnable() {
                            public void run() {
                                VisualDebugHelper.this.handlerEvent(jsonObject);
                            }
                        });
                    }
                };
            }
            SensorsDataAPI.sharedInstance().addEventListener(this.mEventListener);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: package-private */
    public void stopMonitor() {
        try {
            if (this.mEventListener != null) {
                SensorsDataAPI.sharedInstance().removeEventListener(this.mEventListener);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
        	at java.util.ArrayList.rangeCheck(ArrayList.java:659)
        	at java.util.ArrayList.get(ArrayList.java:435)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public synchronized void handlerEvent(org.json.JSONObject r13) {
        /*
            r12 = this;
            monitor-enter(r12)
            if (r13 != 0) goto L_0x0005
            monitor-exit(r12)
            return
        L_0x0005:
            com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService r0 = com.sensorsdata.analytics.android.sdk.visual.VisualizedAutoTrackService.getInstance()     // Catch:{ Exception -> 0x0175 }
            boolean r0 = r0.isServiceRunning()     // Catch:{ Exception -> 0x0175 }
            if (r0 != 0) goto L_0x0011
            monitor-exit(r12)
            return
        L_0x0011:
            java.lang.String r0 = "event"
            java.lang.String r0 = r13.optString(r0)     // Catch:{ Exception -> 0x0175 }
            java.lang.String r1 = "$AppClick"
            boolean r1 = android.text.TextUtils.equals(r1, r0)     // Catch:{ Exception -> 0x0175 }
            if (r1 != 0) goto L_0x0044
            java.lang.String r1 = "$WebClick"
            boolean r1 = android.text.TextUtils.equals(r1, r0)     // Catch:{ Exception -> 0x0175 }
            if (r1 != 0) goto L_0x0044
            java.lang.String r1 = "SA.VP.VisualDebugHelper"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0175 }
            r2.<init>()     // Catch:{ Exception -> 0x0175 }
            java.lang.String r3 = "eventName is "
            r2.append(r3)     // Catch:{ Exception -> 0x0175 }
            r2.append(r0)     // Catch:{ Exception -> 0x0175 }
            java.lang.String r3 = " filter"
            r2.append(r3)     // Catch:{ Exception -> 0x0175 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0175 }
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r1, (java.lang.String) r2)     // Catch:{ Exception -> 0x0175 }
            monitor-exit(r12)
            return
        L_0x0044:
            java.lang.String r1 = "properties"
            org.json.JSONObject r1 = r13.optJSONObject(r1)     // Catch:{ Exception -> 0x0175 }
            if (r1 != 0) goto L_0x004f
            monitor-exit(r12)
            return
        L_0x004f:
            com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager r2 = com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager.getInstance()     // Catch:{ Exception -> 0x0175 }
            boolean r2 = r2.checkAppIdAndProject()     // Catch:{ Exception -> 0x0175 }
            if (r2 != 0) goto L_0x005b
            monitor-exit(r12)
            return
        L_0x005b:
            com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager r2 = com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager.getInstance()     // Catch:{ Exception -> 0x0175 }
            com.sensorsdata.analytics.android.sdk.visual.model.VisualConfig r2 = r2.getVisualConfig()     // Catch:{ Exception -> 0x0175 }
            if (r2 != 0) goto L_0x0067
            monitor-exit(r12)
            return
        L_0x0067:
            java.util.List<com.sensorsdata.analytics.android.sdk.visual.model.VisualConfig$VisualPropertiesConfig> r3 = r2.events     // Catch:{ Exception -> 0x0175 }
            if (r3 == 0) goto L_0x0169
            int r4 = r3.size()     // Catch:{ Exception -> 0x0175 }
            if (r4 != 0) goto L_0x0073
            goto L_0x0169
        L_0x0073:
            java.lang.String r4 = "$AppClick"
            boolean r4 = android.text.TextUtils.equals(r4, r0)     // Catch:{ Exception -> 0x0175 }
            if (r4 == 0) goto L_0x00f4
            java.lang.String r4 = "$screen_name"
            java.lang.String r7 = r1.optString(r4)     // Catch:{ Exception -> 0x0175 }
            boolean r4 = android.text.TextUtils.isEmpty(r7)     // Catch:{ Exception -> 0x0175 }
            if (r4 == 0) goto L_0x0091
            java.lang.String r4 = "SA.VP.VisualDebugHelper"
            java.lang.String r5 = "screenName is empty "
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r4, (java.lang.String) r5)     // Catch:{ Exception -> 0x0175 }
            monitor-exit(r12)
            return
        L_0x0091:
            com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager r4 = com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager.getInstance()     // Catch:{ Exception -> 0x0175 }
            com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager$VisualEventType r6 = com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager.VisualEventType.getVisualEventType(r0)     // Catch:{ Exception -> 0x0175 }
            java.lang.String r5 = "$element_path"
            java.lang.String r8 = r1.optString(r5)     // Catch:{ Exception -> 0x0175 }
            java.lang.String r5 = "$element_position"
            java.lang.String r9 = r1.optString(r5)     // Catch:{ Exception -> 0x0175 }
            java.lang.String r5 = "$element_content"
            java.lang.String r10 = r1.optString(r5)     // Catch:{ Exception -> 0x0175 }
            r5 = r3
            java.util.List r4 = r4.getMatchEventConfigList(r5, r6, r7, r8, r9, r10)     // Catch:{ Exception -> 0x0175 }
            int r5 = r4.size()     // Catch:{ Exception -> 0x0175 }
            if (r5 <= 0) goto L_0x0167
            java.lang.Object r5 = r12.object     // Catch:{ Exception -> 0x0175 }
            monitor-enter(r5)     // Catch:{ Exception -> 0x0175 }
            java.util.Iterator r6 = r4.iterator()     // Catch:{ all -> 0x00f1 }
        L_0x00bd:
            boolean r8 = r6.hasNext()     // Catch:{ all -> 0x00f1 }
            if (r8 == 0) goto L_0x00ee
            java.lang.Object r8 = r6.next()     // Catch:{ all -> 0x00f1 }
            com.sensorsdata.analytics.android.sdk.visual.model.VisualConfig$VisualPropertiesConfig r8 = (com.sensorsdata.analytics.android.sdk.visual.model.VisualConfig.VisualPropertiesConfig) r8     // Catch:{ all -> 0x00f1 }
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ Exception -> 0x00e9 }
            r9.<init>()     // Catch:{ Exception -> 0x00e9 }
            com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils.mergeJSONObject(r13, r9)     // Catch:{ Exception -> 0x00e9 }
            java.lang.String r10 = "event_name"
            java.lang.String r11 = r8.eventName     // Catch:{ Exception -> 0x00e9 }
            r9.put((java.lang.String) r10, (java.lang.Object) r11)     // Catch:{ Exception -> 0x00e9 }
            org.json.JSONArray r10 = r12.mJsonArray     // Catch:{ Exception -> 0x00e9 }
            if (r10 != 0) goto L_0x00e3
            org.json.JSONArray r10 = new org.json.JSONArray     // Catch:{ Exception -> 0x00e9 }
            r10.<init>()     // Catch:{ Exception -> 0x00e9 }
            r12.mJsonArray = r10     // Catch:{ Exception -> 0x00e9 }
        L_0x00e3:
            org.json.JSONArray r10 = r12.mJsonArray     // Catch:{ Exception -> 0x00e9 }
            r10.put((java.lang.Object) r9)     // Catch:{ Exception -> 0x00e9 }
            goto L_0x00ed
        L_0x00e9:
            r9 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r9)     // Catch:{ all -> 0x00f1 }
        L_0x00ed:
            goto L_0x00bd
        L_0x00ee:
            monitor-exit(r5)     // Catch:{ all -> 0x00f1 }
            goto L_0x0167
        L_0x00f1:
            r6 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x00f1 }
            throw r6     // Catch:{ Exception -> 0x0175 }
        L_0x00f4:
            java.lang.String r4 = "$WebClick"
            boolean r4 = android.text.TextUtils.equals(r4, r0)     // Catch:{ Exception -> 0x0175 }
            if (r4 == 0) goto L_0x0167
            java.lang.String r4 = "sensorsdata_web_visual_eventName"
            org.json.JSONArray r4 = r1.optJSONArray(r4)     // Catch:{ Exception -> 0x0162 }
            if (r4 != 0) goto L_0x0121
            int r5 = r13.hashCode()     // Catch:{ Exception -> 0x0162 }
            com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager r6 = com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager.getInstance()     // Catch:{ Exception -> 0x0162 }
            com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesH5Helper r6 = r6.getVisualPropertiesH5Helper()     // Catch:{ Exception -> 0x0162 }
            org.json.JSONArray r6 = r6.getEventName(r5)     // Catch:{ Exception -> 0x0162 }
            r4 = r6
            com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager r6 = com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesManager.getInstance()     // Catch:{ Exception -> 0x0162 }
            com.sensorsdata.analytics.android.sdk.visual.property.VisualPropertiesH5Helper r6 = r6.getVisualPropertiesH5Helper()     // Catch:{ Exception -> 0x0162 }
            r6.clearCache(r5)     // Catch:{ Exception -> 0x0162 }
        L_0x0121:
            if (r4 == 0) goto L_0x0161
            int r5 = r4.length()     // Catch:{ Exception -> 0x0162 }
            if (r5 <= 0) goto L_0x0161
            java.lang.Object r5 = r12.object     // Catch:{ Exception -> 0x0162 }
            monitor-enter(r5)     // Catch:{ Exception -> 0x0162 }
            r6 = 0
        L_0x012d:
            int r7 = r4.length()     // Catch:{ all -> 0x015e }
            if (r6 >= r7) goto L_0x015c
            org.json.JSONObject r7 = new org.json.JSONObject     // Catch:{ Exception -> 0x0155 }
            r7.<init>()     // Catch:{ Exception -> 0x0155 }
            com.sensorsdata.analytics.android.sdk.util.SensorsDataUtils.mergeJSONObject(r13, r7)     // Catch:{ Exception -> 0x0155 }
            java.lang.String r8 = "event_name"
            java.lang.String r9 = r4.optString(r6)     // Catch:{ Exception -> 0x0155 }
            r7.put((java.lang.String) r8, (java.lang.Object) r9)     // Catch:{ Exception -> 0x0155 }
            org.json.JSONArray r8 = r12.mJsonArray     // Catch:{ Exception -> 0x0155 }
            if (r8 != 0) goto L_0x014f
            org.json.JSONArray r8 = new org.json.JSONArray     // Catch:{ Exception -> 0x0155 }
            r8.<init>()     // Catch:{ Exception -> 0x0155 }
            r12.mJsonArray = r8     // Catch:{ Exception -> 0x0155 }
        L_0x014f:
            org.json.JSONArray r8 = r12.mJsonArray     // Catch:{ Exception -> 0x0155 }
            r8.put((java.lang.Object) r7)     // Catch:{ Exception -> 0x0155 }
            goto L_0x0159
        L_0x0155:
            r7 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r7)     // Catch:{ all -> 0x015e }
        L_0x0159:
            int r6 = r6 + 1
            goto L_0x012d
        L_0x015c:
            monitor-exit(r5)     // Catch:{ all -> 0x015e }
            goto L_0x0161
        L_0x015e:
            r6 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x015e }
            throw r6     // Catch:{ Exception -> 0x0162 }
        L_0x0161:
            goto L_0x0168
        L_0x0162:
            r4 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r4)     // Catch:{ Exception -> 0x0175 }
            goto L_0x0168
        L_0x0167:
        L_0x0168:
            goto L_0x0179
        L_0x0169:
            java.lang.String r4 = "SA.VP.VisualDebugHelper"
            java.lang.String r5 = "propertiesConfigs is empty "
            com.sensorsdata.analytics.android.sdk.SALog.i((java.lang.String) r4, (java.lang.String) r5)     // Catch:{ Exception -> 0x0175 }
            monitor-exit(r12)
            return
        L_0x0173:
            r13 = move-exception
            goto L_0x017b
        L_0x0175:
            r0 = move-exception
            com.sensorsdata.analytics.android.sdk.SALog.printStackTrace(r0)     // Catch:{ all -> 0x0173 }
        L_0x0179:
            monitor-exit(r12)
            return
        L_0x017b:
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sensorsdata.analytics.android.sdk.visual.VisualDebugHelper.handlerEvent(org.json.JSONObject):void");
    }

    /* access modifiers changed from: package-private */
    public String getDebugInfo() {
        synchronized (this.object) {
            JSONArray jSONArray = this.mJsonArray;
            if (jSONArray == null) {
                return null;
            }
            String result = jSONArray.toString();
            this.mJsonArray = null;
            return result;
        }
    }

    public static abstract class TrackEventAdapter implements SAEventListener {
        private TrackEventAdapter() {
        }

        public void login() {
        }

        public void logout() {
        }

        public void identify() {
        }

        public void resetAnonymousId() {
        }
    }
}
