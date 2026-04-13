package com.leedarson.serviceimpl;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioRecord;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.bean.AmpDotBean;
import com.leedarson.bean.FftResult;
import com.leedarson.bean.IRhyDevice;
import com.leedarson.bean.RhyBLEDevice;
import com.leedarson.bean.RhyBLEMeshDevice;
import com.leedarson.bean.RhyBLEQhmDevice;
import com.leedarson.bean.RhyTcpDevice;
import com.leedarson.bean.RhyUDPDevice;
import com.leedarson.bean.SchemeBean;
import com.leedarson.scheme.c;
import com.leedarson.utils.d;
import com.leedarson.utils.p;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.MeshLog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: RhythmManager */
public class m {
    private static m a;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public AudioRecord b;
    /* access modifiers changed from: private */
    public boolean c;
    private Context d;
    private a e;
    private int f = 1000;
    private int g;
    /* access modifiers changed from: private */
    public int h;
    /* access modifiers changed from: private */
    public double i;
    private JSONArray j = null;
    private JSONArray k = null;
    /* access modifiers changed from: private */
    public AtomicBoolean l = new AtomicBoolean(true);
    private int m;
    private int[] n = d.s;
    public HashMap<String, SchemeBean> o = new HashMap<>();
    private CopyOnWriteArrayList<IRhyDevice> p;
    private volatile c q = new com.leedarson.scheme.d();
    private String r;
    /* access modifiers changed from: private */
    public b s;

    /* compiled from: RhythmManager */
    public interface b {
        void a(FftResult fftResult);
    }

    static /* synthetic */ int h(m x0) {
        int i2 = x0.h;
        x0.h = i2 + 1;
        return i2;
    }

    static /* synthetic */ void i(m x0, double x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Double(x1)}, (Object) null, changeQuickRedirect, true, 6092, new Class[]{m.class, Double.TYPE}, Void.TYPE).isSupported) {
            x0.w(x1);
        }
    }

    public String p() {
        return this.r;
    }

    public static m o() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 6073, new Class[0], m.class);
        if (proxy.isSupported) {
            return (m) proxy.result;
        }
        if (a == null) {
            synchronized (m.class) {
                if (a == null) {
                    a = new m();
                }
            }
        }
        return a;
    }

    private void r() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6074, new Class[0], Void.TYPE).isSupported) {
            timber.log.a.g("RhythmManager").a("#initAudioRecord", new Object[0]);
            this.b = new AudioRecord(1, 44100, 1, 2, 1024);
        }
    }

    public boolean t() {
        return this.c;
    }

    public JSONArray m() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6075, new Class[0], JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        JSONArray allDevices = new JSONArray();
        CopyOnWriteArrayList<IRhyDevice> copyOnWriteArrayList = this.p;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<IRhyDevice> it = this.p.iterator();
            while (it.hasNext()) {
                IRhyDevice device = it.next();
                JSONObject deviceObj = new JSONObject();
                String protocolType = device.getProtocolType();
                JSONObject protocolData = device.getProtocolData();
                String rhythmType = device.getRhythmType();
                int rhythmTag = device.getRhythmTap();
                int rhythmMicSensitivity = device.getRhythmMicSensitivity();
                int rhythmMAXDimming = device.getRhythmMAXDimming();
                int[] rhythmColors = device.getRhythmColors();
                try {
                    deviceObj.put("protocolType", (Object) protocolType);
                    deviceObj.put("protocolData", (Object) protocolData);
                    deviceObj.put("rhythmTag", rhythmTag);
                    deviceObj.put("rhythmMicSensitivity", rhythmMicSensitivity);
                    deviceObj.put("rhythmMAXDimming", rhythmMAXDimming);
                    deviceObj.put("rhythmType", (Object) rhythmType);
                    deviceObj.put("rhythmColors", (Object) rhythmColors);
                    allDevices.put((Object) deviceObj);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return allDevices;
    }

    public JSONArray n() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6076, new Class[0], JSONArray.class);
        if (proxy.isSupported) {
            return (JSONArray) proxy.result;
        }
        JSONArray bleMeshDevices = new JSONArray();
        CopyOnWriteArrayList<IRhyDevice> copyOnWriteArrayList = this.p;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<IRhyDevice> it = this.p.iterator();
            while (it.hasNext()) {
                IRhyDevice device = it.next();
                String protocolType = device.getProtocolType();
                if (IRhyDevice.TYPE_BLE_MESH.equals(protocolType)) {
                    JSONObject deviceObj = new JSONObject();
                    String rhythmType = device.getRhythmType();
                    JSONObject protocolData = device.getProtocolData();
                    try {
                        deviceObj.put("rhythmType", (Object) rhythmType);
                        deviceObj.put("protocolType", (Object) protocolType);
                        deviceObj.put("protocolData", (Object) protocolData);
                        bleMeshDevices.put((Object) deviceObj);
                    } catch (JSONException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return bleMeshDevices;
    }

    public void v(JSONObject jSONObject) {
        String str;
        String rhythmType;
        String str2;
        int rhythmTap;
        String str3;
        int rhythmTap2;
        int i2;
        String str4;
        int[] rhythmColors;
        List macList;
        List list;
        int rhythmMicSensitivity;
        int rhythmMAXDimming;
        int rhythmMINDimming;
        String str5;
        int rhythmFading;
        String str6;
        int rhythmChangeColorSpeed;
        String str7;
        String str8;
        String str9;
        String str10;
        int[] rhythmColors2;
        String rhythmVersion;
        IRhyDevice iRhyDevice;
        int rhythmTap3;
        String str11;
        JSONArray colors;
        String str12;
        int rhythmTap4;
        int i3;
        List rhythmLightIndexes;
        List macList2;
        String str13 = "rhythmColors";
        String str14 = "rhythmChangeColorSpeed";
        String str15 = "rhythmFading";
        String str16 = "mac";
        String str17 = "rhythmMINDimming";
        String str18 = FirebaseAnalytics.Param.INDEX;
        String str19 = "rhythmMAXDimming";
        String str20 = "rhythmDimming";
        String str21 = "rhythmLightIndexes";
        String str22 = "rhythmTap";
        String str23 = "rhythmType";
        String str24 = "rhythmVersion";
        if (!PatchProxy.proxy(new Object[]{jSONObject}, this, changeQuickRedirect, false, 6077, new Class[]{JSONObject.class}, Void.TYPE).isSupported) {
            JSONObject paramObj = jSONObject;
            if (paramObj.has("devices")) {
                if (this.p == null) {
                    this.p = new CopyOnWriteArrayList<>();
                }
                try {
                    this.j = paramObj.getJSONArray("devices");
                    int i4 = 0;
                    while (i4 < this.j.length()) {
                        int rhythmMAXDimming2 = 100;
                        JSONObject paramObj2 = paramObj;
                        String rhythmType2 = IRhyDevice.RHYTHM_TYPE_SYNC;
                        try {
                            int[] rhythmColors3 = new int[0];
                            List rhythmLightIndexes2 = new ArrayList();
                            List macList3 = new ArrayList();
                            JSONObject deviceObj = (JSONObject) this.j.get(i4);
                            if (deviceObj.has(str23)) {
                                str = str23;
                                rhythmType = (String) deviceObj.get(str23);
                            } else {
                                str = str23;
                                rhythmType = rhythmType2;
                            }
                            if (deviceObj.has(str22)) {
                                str2 = str22;
                                rhythmTap = ((Integer) deviceObj.get(str22)).intValue();
                            } else {
                                str2 = str22;
                                rhythmTap = 1;
                            }
                            if (deviceObj.has(str21) != 0) {
                                JSONArray rhythmLightIndexArray = deviceObj.getJSONArray(str21);
                                str4 = str21;
                                int r2 = 0;
                                while (true) {
                                    rhythmColors = rhythmColors3;
                                    if (r2 >= rhythmLightIndexArray.length()) {
                                        break;
                                    }
                                    JSONArray rhythmLightIndexArray2 = rhythmLightIndexArray;
                                    rhythmLightIndexArray = rhythmLightIndexArray2;
                                    JSONObject jsonObject = (JSONObject) rhythmLightIndexArray2.get(r2);
                                    if (jsonObject.has(str18)) {
                                        JSONArray jsonArray = jsonObject.getJSONArray(str18);
                                        str12 = str18;
                                        int a2 = 0;
                                        while (true) {
                                            i3 = i4;
                                            if (a2 >= jsonArray.length()) {
                                                break;
                                            }
                                            JSONArray jsonArray2 = jsonArray;
                                            int rhythmTap5 = rhythmTap;
                                            Object obj = jsonArray2.get(a2);
                                            JSONArray jsonArray3 = jsonArray2;
                                            List rhythmLightIndexes3 = rhythmLightIndexes2;
                                            rhythmLightIndexes3.add(obj);
                                            a2++;
                                            rhythmLightIndexes2 = rhythmLightIndexes3;
                                            i4 = i3;
                                            rhythmTap = rhythmTap5;
                                            jsonArray = jsonArray3;
                                        }
                                        rhythmLightIndexes = rhythmLightIndexes2;
                                        JSONArray jSONArray = jsonArray;
                                        rhythmTap4 = rhythmTap;
                                    } else {
                                        i3 = i4;
                                        str12 = str18;
                                        rhythmTap4 = rhythmTap;
                                        rhythmLightIndexes = rhythmLightIndexes2;
                                    }
                                    if (jsonObject.has(str16)) {
                                        macList2 = macList3;
                                        macList2.add(jsonObject.getString(str16));
                                    } else {
                                        macList2 = macList3;
                                    }
                                    r2++;
                                    rhythmLightIndexes2 = rhythmLightIndexes;
                                    macList3 = macList2;
                                    rhythmColors3 = rhythmColors;
                                    i4 = i3;
                                    rhythmTap = rhythmTap4;
                                    str18 = str12;
                                }
                                i2 = i4;
                                str3 = str18;
                                rhythmTap2 = rhythmTap;
                                list = rhythmLightIndexes2;
                                macList = macList3;
                            } else {
                                i2 = i4;
                                str3 = str18;
                                str4 = str21;
                                rhythmTap2 = rhythmTap;
                                rhythmColors = rhythmColors3;
                                list = rhythmLightIndexes2;
                                macList = macList3;
                            }
                            if (deviceObj.has("rhythmMicSensitivity")) {
                                rhythmMicSensitivity = ((Integer) deviceObj.get("rhythmMicSensitivity")).intValue();
                            } else {
                                rhythmMicSensitivity = 100;
                            }
                            if (deviceObj.has(str20)) {
                                rhythmMAXDimming2 = ((Integer) deviceObj.get(str20)).intValue();
                            }
                            if (deviceObj.has(str19)) {
                                rhythmMAXDimming = ((Integer) deviceObj.get(str19)).intValue();
                            } else {
                                rhythmMAXDimming = rhythmMAXDimming2;
                            }
                            if (deviceObj.has(str17)) {
                                rhythmMINDimming = ((Integer) deviceObj.get(str17)).intValue();
                            } else {
                                rhythmMINDimming = 30;
                            }
                            if (deviceObj.has(str15)) {
                                str5 = str15;
                                rhythmFading = ((Integer) deviceObj.get(str15)).intValue();
                            } else {
                                str5 = str15;
                                rhythmFading = 50;
                            }
                            if (deviceObj.has(str14)) {
                                str6 = str14;
                                rhythmChangeColorSpeed = ((Integer) deviceObj.get(str14)).intValue();
                            } else {
                                str6 = str14;
                                rhythmChangeColorSpeed = 0;
                            }
                            if (deviceObj.has(str13)) {
                                JSONArray colors2 = (JSONArray) deviceObj.get(str13);
                                str10 = str13;
                                rhythmColors2 = new int[colors2.length()];
                                str9 = str16;
                                int r3 = 0;
                                while (true) {
                                    str8 = str17;
                                    if (r3 >= colors2.length()) {
                                        break;
                                    }
                                    JSONArray colors3 = colors2;
                                    String color = colors3.getString(r3);
                                    if (!TextUtils.isEmpty(color)) {
                                        colors = colors3;
                                        str11 = str19;
                                        String color2 = color;
                                        if (color2.contains("#")) {
                                            rhythmColors2[r3] = Color.parseColor(color2);
                                        } else {
                                            rhythmColors2[r3] = Integer.parseInt(color2);
                                        }
                                    } else {
                                        colors = colors3;
                                        str11 = str19;
                                        String str25 = color;
                                    }
                                    r3++;
                                    colors2 = colors;
                                    str17 = str8;
                                    str19 = str11;
                                }
                                str7 = str19;
                                JSONArray jSONArray2 = colors2;
                            } else {
                                str10 = str13;
                                str9 = str16;
                                str8 = str17;
                                str7 = str19;
                                rhythmColors2 = rhythmColors;
                            }
                            String str26 = str24;
                            if (deviceObj.has(str26)) {
                                rhythmVersion = (String) deviceObj.get(str26);
                            } else {
                                rhythmVersion = null;
                            }
                            String protocolType = deviceObj.get("protocolType").toString();
                            str24 = str26;
                            JSONObject protocolData = (JSONObject) deviceObj.get("protocolData");
                            String str27 = str20;
                            if ("TCP".equals(protocolType)) {
                                IRhyDevice iRhyDevice2 = new RhyTcpDevice(rhythmType, protocolType, protocolData);
                                this.r = protocolData.get("sessionId").toString();
                                iRhyDevice = iRhyDevice2;
                            } else if (IRhyDevice.TYPE_BLE_MESH.equals(protocolType)) {
                                iRhyDevice = new RhyBLEMeshDevice(rhythmType, protocolType, protocolData);
                            } else if (IRhyDevice.TYPE_BLE.equals(protocolType)) {
                                iRhyDevice = new RhyBLEDevice(rhythmType, protocolType, protocolData);
                            } else if (IRhyDevice.TYPE_BLE_QHM.equals(protocolType)) {
                                iRhyDevice = new RhyBLEQhmDevice(rhythmType, protocolType, protocolData);
                            } else if ("UDP".equals(protocolType)) {
                                iRhyDevice = new RhyUDPDevice(rhythmType, protocolType, protocolData);
                            } else {
                                iRhyDevice = null;
                            }
                            if (iRhyDevice == null || s(iRhyDevice)) {
                                List rhythmLightIndexes4 = list;
                                int[] iArr = rhythmColors2;
                                rhythmTap3 = rhythmTap2;
                            } else {
                                iRhyDevice.setMacList(macList);
                                iRhyDevice.setRhythmLightIndexes(list);
                                List list2 = list;
                                rhythmTap3 = rhythmTap2;
                                iRhyDevice.setRhythmTap(rhythmTap3);
                                iRhyDevice.setRhythmType(rhythmType);
                                iRhyDevice.setRhythmColors(rhythmColors2);
                                iRhyDevice.setRhythmMAXDimming(rhythmMAXDimming);
                                iRhyDevice.setRhythmMINDimming(rhythmMINDimming);
                                iRhyDevice.setRhythmFading(rhythmFading);
                                iRhyDevice.setRhythmChangeColorSpeed(rhythmChangeColorSpeed);
                                iRhyDevice.setRhythmVersion(rhythmVersion);
                                iRhyDevice.setRhythmMicSensitivity(rhythmMicSensitivity);
                                int[] iArr2 = rhythmColors2;
                                this.p.add(iRhyDevice);
                                iRhyDevice.start();
                            }
                            if (rhythmTap3 == 6) {
                                f.c().g();
                            }
                            i4 = i2 + 1;
                            paramObj = paramObj2;
                            str22 = str2;
                            str15 = str5;
                            str14 = str6;
                            str20 = str27;
                            str13 = str10;
                            str16 = str9;
                            str17 = str8;
                            str19 = str7;
                            str23 = str;
                            str21 = str4;
                            str18 = str3;
                        } catch (Exception e2) {
                            e = e2;
                            e.printStackTrace();
                            MeshLog.logMusicRhythmWarn("resetDeviceList exception:" + e.getMessage());
                        }
                    }
                    int i5 = i4;
                    JSONObject jSONObject2 = paramObj;
                } catch (Exception e3) {
                    e = e3;
                    JSONObject jSONObject3 = paramObj;
                    e.printStackTrace();
                    MeshLog.logMusicRhythmWarn("resetDeviceList exception:" + e.getMessage());
                }
            }
        }
    }

    public boolean u() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6078, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.l.get();
    }

    public void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6079, new Class[0], Void.TYPE).isSupported) {
            this.l.set(false);
            r();
            this.c = true;
            MeshLog.logMusicRhythm("开启声音采集 isGetVoiceRun=true");
            this.g = 0;
            this.h = 0;
            a aVar = new a();
            this.e = aVar;
            aVar.execute(new String[0]);
        }
    }

    public void B(String ref) {
        if (!PatchProxy.proxy(new Object[]{ref}, this, changeQuickRedirect, false, 6080, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.l.set(true);
            this.h = 0;
            this.c = false;
            MeshLog.logMusicRhythm("停止声音采集律动 ref:" + ref);
            AudioRecord audioRecord = this.b;
            if (audioRecord != null) {
                audioRecord.stop();
                this.b.release();
                this.b = null;
            }
            if (this.q != null) {
                this.q.c();
            }
            CopyOnWriteArrayList<IRhyDevice> copyOnWriteArrayList = this.p;
            if (copyOnWriteArrayList != null) {
                copyOnWriteArrayList.clear();
            }
            a aVar = this.e;
            if (aVar != null) {
                aVar.cancel(false);
            }
        }
    }

    public boolean s(IRhyDevice rhyDevice) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{rhyDevice}, this, changeQuickRedirect, false, 6081, new Class[]{IRhyDevice.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        CopyOnWriteArrayList<IRhyDevice> copyOnWriteArrayList = this.p;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            Iterator<IRhyDevice> it = this.p.iterator();
            while (it.hasNext()) {
                IRhyDevice device = it.next();
                String rhyId = device.getRhyId();
                String rhyType = device.getProtocolType();
                if (rhyId != null && rhyType != null && rhyDevice != null && rhyId.equals(rhyDevice.getRhyId()) && rhyType.equals(rhyDevice.getProtocolType())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void C(String rhyDeviceId, String protocolType, String ref) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{rhyDeviceId, protocolType, ref}, this, changeQuickRedirect, false, 6083, clsArr, Void.TYPE).isSupported) {
            try {
                MeshLog.logMusicRhythm("stopByDeviceId ref:" + ref + ",rhyDeviceId:" + rhyDeviceId + ",protocolType:" + protocolType);
                CopyOnWriteArrayList<IRhyDevice> copyOnWriteArrayList = this.p;
                if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
                    Iterator<IRhyDevice> it = this.p.iterator();
                    while (it.hasNext()) {
                        IRhyDevice device = it.next();
                        if (device != null && !TextUtils.isEmpty(device.getRhyId())) {
                            String rhyId = device.getRhyId().replace(":", "").toUpperCase();
                            if (IRhyDevice.TYPE_BLE_MESH.equals(protocolType) && !TextUtils.isEmpty(rhyDeviceId) && device.getMacList().contains(rhyDeviceId)) {
                                device.getMacList().remove(rhyDeviceId);
                                if (device.getMacList().size() == 0) {
                                    MeshLog.logMusicRhythm("当前律动任务下，macs已都停止律动，移除该律动任务");
                                    this.p.remove(device);
                                    device.stop();
                                }
                            }
                            if (TextUtils.isEmpty(rhyDeviceId) && TextUtils.isEmpty(protocolType)) {
                                this.p.remove(device);
                                device.stop();
                            } else if (!TextUtils.isEmpty(rhyDeviceId)) {
                                if (rhyId.equalsIgnoreCase(rhyDeviceId.toUpperCase()) || device.getRhyId().contains(rhyDeviceId)) {
                                    this.p.remove(device);
                                    device.stop();
                                }
                            } else if (!TextUtils.isEmpty(protocolType) && protocolType.equals(device.getProtocolType())) {
                                this.p.remove(device);
                                device.stop();
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.getMessage();
                MeshLog.logMusicRhythmWarn("stopByDeviceId exception:" + e2.getMessage());
            }
        }
    }

    public int k() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6084, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        CopyOnWriteArrayList<IRhyDevice> copyOnWriteArrayList = this.p;
        if (copyOnWriteArrayList == null) {
            return 0;
        }
        return copyOnWriteArrayList.size();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005f, code lost:
        if (r10.equals("Pop") != false) goto L_0x0063;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void D(java.lang.String r10) {
        /*
            r9 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r10
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r8] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 6085(0x17c5, float:8.527E-42)
            r2 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x001d
            return
        L_0x001d:
            r1 = r9
            com.leedarson.scheme.c r2 = r1.q
            if (r2 == 0) goto L_0x003c
            com.leedarson.scheme.c r2 = r1.q
            java.lang.String r2 = r2.a()
            boolean r2 = r2.equals(r10)
            if (r2 == 0) goto L_0x003c
            java.lang.String r0 = "RhythmManager"
            timber.log.a$b r0 = timber.log.a.g(r0)
            java.lang.Object[] r2 = new java.lang.Object[r8]
            java.lang.String r3 = "switchMode=> 当前重复模式"
            r0.a(r3, r2)
            return
        L_0x003c:
            r2 = -1
            int r3 = r10.hashCode()
            switch(r3) {
                case 80433: goto L_0x0059;
                case 66041178: goto L_0x004f;
                case 1994885149: goto L_0x0045;
                default: goto L_0x0044;
            }
        L_0x0044:
            goto L_0x0062
        L_0x0045:
            java.lang.String r0 = "Classical"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0044
            r0 = 2
            goto L_0x0063
        L_0x004f:
            java.lang.String r0 = "Disco"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x0044
            r0 = r8
            goto L_0x0063
        L_0x0059:
            java.lang.String r3 = "Pop"
            boolean r3 = r10.equals(r3)
            if (r3 == 0) goto L_0x0044
            goto L_0x0063
        L_0x0062:
            r0 = r2
        L_0x0063:
            switch(r0) {
                case 0: goto L_0x0077;
                case 1: goto L_0x006f;
                case 2: goto L_0x0067;
                default: goto L_0x0066;
            }
        L_0x0066:
            goto L_0x007f
        L_0x0067:
            com.leedarson.scheme.a r0 = new com.leedarson.scheme.a
            r0.<init>()
            r1.q = r0
            goto L_0x007f
        L_0x006f:
            com.leedarson.scheme.d r0 = new com.leedarson.scheme.d
            r0.<init>()
            r1.q = r0
            goto L_0x007f
        L_0x0077:
            com.leedarson.scheme.b r0 = new com.leedarson.scheme.b
            r0.<init>()
            r1.q = r0
        L_0x007f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.m.D(java.lang.String):void");
    }

    public void y(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6086, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                JSONObject paramObj = new JSONObject(data);
                a.b g2 = timber.log.a.g("Rhythm");
                g2.a("setConfig paramObj=" + paramObj, new Object[0]);
                CopyOnWriteArrayList<IRhyDevice> copyOnWriteArrayList = this.p;
                if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
                    Iterator<IRhyDevice> it = this.p.iterator();
                    while (it.hasNext()) {
                        IRhyDevice device = it.next();
                        if (paramObj.has("groupId")) {
                            if (device.getRhyId().contains(paramObj.getString("groupId"))) {
                                E(paramObj, device);
                            }
                        } else if (paramObj.has("mac")) {
                            if (device.getRhyId().contains(paramObj.getString("mac"))) {
                                E(paramObj, device);
                            }
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:72:0x0164 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0165  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void E(org.json.JSONObject r19, com.leedarson.bean.IRhyDevice r20) {
        /*
            r18 = this;
            java.lang.String r0 = "rhythmLightIndexes"
            java.lang.String r1 = "rhythmMAXDimming"
            java.lang.String r2 = "rhythmDimming"
            java.lang.String r3 = "rhythmMicSensitivity"
            java.lang.String r4 = "rhythmType"
            java.lang.String r5 = "rhythmChangeColorSpeed"
            java.lang.String r6 = "rhythmFading"
            java.lang.String r7 = "rhythmMINDimming"
            java.lang.String r8 = "rhythmColors"
            java.lang.String r9 = "rhythmTap"
            r10 = 2
            java.lang.Object[] r11 = new java.lang.Object[r10]
            r12 = 0
            r11[r12] = r19
            r15 = 1
            r11[r15] = r20
            com.meituan.robust.ChangeQuickRedirect r13 = changeQuickRedirect
            java.lang.Class[] r10 = new java.lang.Class[r10]
            java.lang.Class<org.json.JSONObject> r14 = org.json.JSONObject.class
            r10[r12] = r14
            java.lang.Class<com.leedarson.bean.IRhyDevice> r12 = com.leedarson.bean.IRhyDevice.class
            r10[r15] = r12
            java.lang.Class r17 = java.lang.Void.TYPE
            r14 = 0
            r16 = 6087(0x17c7, float:8.53E-42)
            r12 = r18
            r15 = r16
            r16 = r10
            com.meituan.robust.PatchProxyResult r10 = com.meituan.robust.PatchProxy.proxy(r11, r12, r13, r14, r15, r16, r17)
            boolean r10 = r10.isSupported
            if (r10 == 0) goto L_0x003d
            return
        L_0x003d:
            r10 = r18
            r11 = r20
            r12 = r19
            boolean r13 = r12.has(r4)     // Catch:{ Exception -> 0x013f }
            if (r13 == 0) goto L_0x0050
            java.lang.String r4 = r12.getString(r4)     // Catch:{ Exception -> 0x013f }
            r11.setRhythmType(r4)     // Catch:{ Exception -> 0x013f }
        L_0x0050:
            boolean r4 = r12.has(r9)     // Catch:{ Exception -> 0x013f }
            if (r4 == 0) goto L_0x005d
            int r4 = r12.getInt(r9)     // Catch:{ Exception -> 0x013f }
            r11.setRhythmTap(r4)     // Catch:{ Exception -> 0x013f }
        L_0x005d:
            boolean r4 = r12.has(r3)     // Catch:{ Exception -> 0x013f }
            if (r4 == 0) goto L_0x006a
            int r3 = r12.getInt(r3)     // Catch:{ Exception -> 0x013f }
            r11.setRhythmMicSensitivity(r3)     // Catch:{ Exception -> 0x013f }
        L_0x006a:
            boolean r3 = r12.has(r2)     // Catch:{ Exception -> 0x013f }
            if (r3 == 0) goto L_0x0077
            int r2 = r12.getInt(r2)     // Catch:{ Exception -> 0x013f }
            r11.setRhythmMAXDimming(r2)     // Catch:{ Exception -> 0x013f }
        L_0x0077:
            boolean r2 = r12.has(r1)     // Catch:{ Exception -> 0x013f }
            if (r2 == 0) goto L_0x0084
            int r1 = r12.getInt(r1)     // Catch:{ Exception -> 0x013f }
            r11.setRhythmMAXDimming(r1)     // Catch:{ Exception -> 0x013f }
        L_0x0084:
            boolean r1 = r12.has(r7)     // Catch:{ Exception -> 0x013f }
            if (r1 == 0) goto L_0x0091
            int r1 = r12.getInt(r7)     // Catch:{ Exception -> 0x013f }
            r11.setRhythmMINDimming(r1)     // Catch:{ Exception -> 0x013f }
        L_0x0091:
            boolean r1 = r12.has(r6)     // Catch:{ Exception -> 0x013f }
            if (r1 == 0) goto L_0x009e
            int r1 = r12.getInt(r6)     // Catch:{ Exception -> 0x013f }
            r11.setRhythmFading(r1)     // Catch:{ Exception -> 0x013f }
        L_0x009e:
            boolean r1 = r12.has(r5)     // Catch:{ Exception -> 0x013f }
            if (r1 == 0) goto L_0x00ab
            int r1 = r12.getInt(r5)     // Catch:{ Exception -> 0x013f }
            r11.setRhythmChangeColorSpeed(r1)     // Catch:{ Exception -> 0x013f }
        L_0x00ab:
            boolean r1 = r12.has(r8)     // Catch:{ Exception -> 0x013f }
            if (r1 == 0) goto L_0x00d4
            java.lang.Object r1 = r12.get(r8)     // Catch:{ Exception -> 0x013f }
            org.json.JSONArray r1 = (org.json.JSONArray) r1     // Catch:{ Exception -> 0x013f }
            int r2 = r1.length()     // Catch:{ Exception -> 0x013f }
            int[] r2 = new int[r2]     // Catch:{ Exception -> 0x013f }
            r3 = 0
        L_0x00be:
            int r4 = r1.length()     // Catch:{ Exception -> 0x013f }
            if (r3 >= r4) goto L_0x00d1
            java.lang.String r4 = r1.getString(r3)     // Catch:{ Exception -> 0x013f }
            int r4 = android.graphics.Color.parseColor(r4)     // Catch:{ Exception -> 0x013f }
            r2[r3] = r4     // Catch:{ Exception -> 0x013f }
            int r3 = r3 + 1
            goto L_0x00be
        L_0x00d1:
            r11.setRhythmColors(r2)     // Catch:{ Exception -> 0x013f }
        L_0x00d4:
            boolean r1 = r12.has(r0)     // Catch:{ Exception -> 0x013f }
            if (r1 == 0) goto L_0x010d
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Exception -> 0x013f }
            r1.<init>()     // Catch:{ Exception -> 0x013f }
            org.json.JSONArray r0 = r12.getJSONArray(r0)     // Catch:{ Exception -> 0x013f }
            r2 = 0
        L_0x00e4:
            int r3 = r0.length()     // Catch:{ Exception -> 0x013f }
            if (r2 >= r3) goto L_0x010a
            java.lang.Object r3 = r0.get(r2)     // Catch:{ Exception -> 0x013f }
            org.json.JSONObject r3 = (org.json.JSONObject) r3     // Catch:{ Exception -> 0x013f }
            java.lang.String r4 = "index"
            org.json.JSONArray r4 = r3.getJSONArray(r4)     // Catch:{ Exception -> 0x013f }
            r13 = 0
        L_0x00f7:
            int r14 = r4.length()     // Catch:{ Exception -> 0x013f }
            if (r13 >= r14) goto L_0x0107
            java.lang.Object r14 = r4.get(r13)     // Catch:{ Exception -> 0x013f }
            r1.add(r14)     // Catch:{ Exception -> 0x013f }
            int r13 = r13 + 1
            goto L_0x00f7
        L_0x0107:
            int r2 = r2 + 1
            goto L_0x00e4
        L_0x010a:
            r11.setRhythmLightIndexes(r1)     // Catch:{ Exception -> 0x013f }
        L_0x010d:
            boolean r0 = r12.has(r7)     // Catch:{ Exception -> 0x013f }
            if (r0 != 0) goto L_0x0132
            boolean r0 = r12.has(r6)     // Catch:{ Exception -> 0x013f }
            if (r0 != 0) goto L_0x0132
            boolean r0 = r12.has(r5)     // Catch:{ Exception -> 0x013f }
            if (r0 == 0) goto L_0x0120
            goto L_0x0132
        L_0x0120:
            boolean r0 = r12.has(r9)     // Catch:{ Exception -> 0x013f }
            if (r0 == 0) goto L_0x012c
            com.leedarson.bean.IRhyDevice$RhythmThemeType r0 = com.leedarson.bean.IRhyDevice.RhythmThemeType.rhythmThemeTypeDefault     // Catch:{ Exception -> 0x013f }
            r11.setRhythmThemeType(r0)     // Catch:{ Exception -> 0x013f }
            goto L_0x0137
        L_0x012c:
            com.leedarson.bean.IRhyDevice$RhythmThemeType r0 = com.leedarson.bean.IRhyDevice.RhythmThemeType.rhythmThemeTypeUnknown     // Catch:{ Exception -> 0x013f }
            r11.setRhythmThemeType(r0)     // Catch:{ Exception -> 0x013f }
            goto L_0x0137
        L_0x0132:
            com.leedarson.bean.IRhyDevice$RhythmThemeType r0 = com.leedarson.bean.IRhyDevice.RhythmThemeType.rhythmThemeTypeCustomize     // Catch:{ Exception -> 0x013f }
            r11.setRhythmThemeType(r0)     // Catch:{ Exception -> 0x013f }
        L_0x0137:
            r11.setEffectArray()     // Catch:{ Exception -> 0x013f }
            r0 = 1
            r11.setUpdateEffect(r0)     // Catch:{ Exception -> 0x013f }
            goto L_0x0143
        L_0x013f:
            r0 = move-exception
            r0.getMessage()
        L_0x0143:
            if (r11 == 0) goto L_0x0170
            boolean r0 = r11 instanceof com.leedarson.bean.RhyBLEMeshDevice
            if (r0 == 0) goto L_0x0170
            boolean r0 = r12.has(r8)
            if (r0 == 0) goto L_0x0170
            java.lang.String r0 = "mac"
            boolean r0 = r12.has(r0)
            if (r0 == 0) goto L_0x0158
            return
        L_0x0158:
            java.lang.String r0 = r11.getRhythmType()
            java.lang.String r1 = "sync"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x0165
            return
        L_0x0165:
            java.lang.String r0 = "律动切换主题，下发律动主题配置"
            meshsdk.MeshLog.logMusicRhythm(r0)
            r0 = r11
            com.leedarson.bean.RhyBLEMeshDevice r0 = (com.leedarson.bean.RhyBLEMeshDevice) r0
            r0.setRhythmTheme()
        L_0x0170:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.m.E(org.json.JSONObject, com.leedarson.bean.IRhyDevice):void");
    }

    private void w(double amplitude) {
        Object[] objArr = {new Double(amplitude)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6088, new Class[]{Double.TYPE}, Void.TYPE).isSupported) {
            try {
                CopyOnWriteArrayList<IRhyDevice> copyOnWriteArrayList = this.p;
                if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
                    Iterator<IRhyDevice> it = this.p.iterator();
                    while (it.hasNext()) {
                        IRhyDevice dev = it.next();
                        if (dev.isSendCommand(amplitude)) {
                            dev.send();
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6089, new Class[0], Void.TYPE).isSupported) {
            try {
                CopyOnWriteArrayList<IRhyDevice> copyOnWriteArrayList = this.p;
                if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
                    Iterator<IRhyDevice> it = this.p.iterator();
                    while (it.hasNext()) {
                        it.next().send();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* compiled from: RhythmManager */
    public class a extends AsyncTask<String, String, String> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{objArr}, this, changeQuickRedirect, false, 6094, new Class[]{Object[].class}, Object.class);
            return proxy.isSupported ? proxy.result : a((String[]) objArr);
        }

        public String a(String... strArr) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{strArr}, this, changeQuickRedirect, false, 6093, new Class[]{String[].class}, String.class);
            if (proxy.isSupported) {
                return (String) proxy.result;
            }
            String[] strArr2 = strArr;
            if (m.this.b == null) {
                return null;
            }
            try {
                m.this.b.startRecording();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            int i = 1024;
            short[] buffer = new short[1024];
            while (m.this.c) {
                int length = m.this.b.read(buffer, 0, i);
                if (length < 0 || length == 0) {
                    try {
                        m.this.b.startRecording();
                    } catch (IllegalStateException e2) {
                        e2.printStackTrace();
                    }
                }
                double unused = m.this.i = p.e(buffer, length);
                FftResult fftResult = p.c(buffer, 44100);
                float frequency = fftResult.resultFreq;
                m mVar = m.this;
                double amplitude = mVar.l(mVar.i);
                if (amplitude >= 0.0d) {
                    short[] buffer2 = buffer;
                    double amplitude2 = amplitude;
                    f.c().f(new AmpDotBean(frequency, m.this.i, System.currentTimeMillis()));
                    if (!m.this.l.get()) {
                        m.h(m.this);
                        m.i(m.this, amplitude2);
                        if (m.this.s != null && m.this.h == 18) {
                            int unused2 = m.this.h = 0;
                            m.this.s.a(fftResult);
                        }
                    }
                    buffer = buffer2;
                    i = 1024;
                }
            }
            return null;
        }
    }

    public double l(double andDb) {
        Object[] objArr = {new Double(andDb)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Double.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6090, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Double) proxy.result).doubleValue();
        }
        return (Math.max(andDb - 20.0d, 0.0d) - 20.0d) / 70.0d;
    }

    public void z(b listener, Context context) {
        this.s = listener;
        this.d = context;
        this.m = 0;
        this.g = 0;
    }

    public boolean q() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6091, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        HashMap<String, SchemeBean> hashMap = this.o;
        return hashMap != null && hashMap.size() > 0;
    }
}
