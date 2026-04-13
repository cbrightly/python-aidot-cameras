package com.leedarson.sender;

import android.bluetooth.BluetoothDevice;
import android.graphics.Color;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alibaba.android.arouter.launcher.a;
import com.leedarson.base.utils.w;
import com.leedarson.bean.IRhyDevice;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.utils.m;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: BLESender */
public class d extends e {
    public static ChangeQuickRedirect changeQuickRedirect;
    private BluetoothDevice a;
    private String b;
    private BleC075Service c;
    private final String d = "5cfefeab-cdc1-4a6d-b1af-6f18294c99bc";
    private final String e = "5cfefeab-cdc1-4a6d-b1af-6f18294c1003";
    private final String f = "5cfefeab-cdc1-4a6d-b1af-6f18294c1002";
    private AtomicInteger g;

    public d(String groupId) {
        this.b = groupId;
        this.c = (BleC075Service) a.c().g(BleC075Service.class);
    }

    public void h(BluetoothDevice bluetoothDevice) {
        this.a = bluetoothDevice;
    }

    public BluetoothDevice e() {
        return this.a;
    }

    public void a(String rhythmType, JSONObject jsonObject) {
        byte[] params;
        if (!PatchProxy.proxy(new Object[]{rhythmType, jsonObject}, this, changeQuickRedirect, false, 5687, new Class[]{String.class, JSONObject.class}, Void.TYPE).isSupported) {
            if (this.c != null) {
                try {
                    if (IRhyDevice.RHYTHM_TYPE_ASYNC.equals(rhythmType)) {
                        params = d(jsonObject);
                    } else if (jsonObject.getBoolean("isOldVersion")) {
                        params = j(jsonObject);
                    } else {
                        params = i(jsonObject);
                    }
                    String data = w.b(params);
                    if (!TextUtils.isEmpty(data)) {
                        a.b g2 = timber.log.a.g("Rhythm");
                        g2.a("params length:" + params.length + ", sendCommand params:" + data, new Object[0]);
                        this.c.commonWriteWithoutResponse(this.a.getAddress(), this.a, UUID.fromString("5cfefeab-cdc1-4a6d-b1af-6f18294c99bc"), UUID.fromString("5cfefeab-cdc1-4a6d-b1af-6f18294c1003"), data.getBytes("UTF-8"));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    private byte[] j(JSONObject jsonObject) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jsonObject}, this, changeQuickRedirect, false, 5688, new Class[]{JSONObject.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        m action = new m();
        try {
            action.s = jsonObject.getString("mode");
            action.r = jsonObject.getInt("soundWaveType");
            int color = jsonObject.getInt(TypedValues.Custom.S_COLOR);
            action.t = Color.red(color);
            action.u = Color.green(color);
            action.v = Color.blue(color);
            if (jsonObject.has("OnOff")) {
                action.l = jsonObject.getInt("OnOff");
            } else {
                action.o = (float) jsonObject.getInt("HSLHue");
                action.m = jsonObject.getInt("Dimming");
                action.q = (float) jsonObject.getInt("HSLLightness");
                action.p = (float) jsonObject.getInt("HSLSaturation");
            }
        } catch (Exception e2) {
            a.b g2 = timber.log.a.g("Rhythm");
            g2.a("setRhythm error=>" + e2.toString(), new Object[0]);
            e2.printStackTrace();
        }
        byte type = action.c();
        byte[] actionBytes = action.d();
        byte[] params = new byte[(4 + 5)];
        params[0] = 10;
        params[1] = 0;
        params[2] = (byte) f();
        params[3] = type;
        params[4] = action.b();
        System.arraycopy(actionBytes, 0, params, 5, actionBytes.length);
        a.b g3 = timber.log.a.g("Rhythm");
        g3.a("BluetoothDevice :" + this.a + ", sendCommand params:" + params, new Object[0]);
        return params;
    }

    private byte[] i(JSONObject jSONObject) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jSONObject}, this, changeQuickRedirect, false, 5689, new Class[]{JSONObject.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        JSONObject jsonObject = jSONObject;
        try {
            byte[] syncDataBytes = new byte[17];
            syncDataBytes[0] = 1;
            JSONArray effectArray = (JSONArray) jsonObject.get("effects");
            int typeLength = (effectArray.length() * 6) + 4;
            syncDataBytes[0 + 1] = (byte) typeLength;
            syncDataBytes[0 + 2] = 1;
            syncDataBytes[0 + 3] = -1;
            syncDataBytes[0 + 4] = (byte) effectArray.length();
            int typeIndex = 0 + 4;
            int e2 = 0;
            while (e2 < effectArray.length()) {
                JSONObject effectObj = (JSONObject) effectArray.get(e2);
                int color = effectObj.getInt(TypedValues.Custom.S_COLOR);
                int r = Color.red(color);
                int g2 = Color.green(color);
                int b2 = Color.blue(color);
                syncDataBytes[typeIndex + 1] = (byte) r;
                syncDataBytes[typeIndex + 2] = (byte) g2;
                syncDataBytes[typeIndex + 3] = (byte) b2;
                int dimming = effectObj.getInt("dimming");
                syncDataBytes[typeIndex + 4] = (byte) dimming;
                int i = dimming;
                byte[] faddingBytes = g((long) effectObj.getInt("fading"), 2);
                syncDataBytes[typeIndex + 5] = faddingBytes[0];
                syncDataBytes[typeIndex + 6] = faddingBytes[1];
                typeIndex += 6;
                e2++;
                effectArray = effectArray;
                typeLength = typeLength;
            }
            int i2 = typeLength;
            a.b g3 = timber.log.a.g("Rhythm");
            g3.a("setRhythm typeIndex =" + typeIndex, new Object[0]);
            int dataLen = syncDataBytes.length;
            byte[] params = new byte[(dataLen + 6 + 2)];
            params[0] = 11;
            params[1] = 0;
            params[2] = (byte) f();
            params[3] = 1;
            byte[] lenBytes = g((long) dataLen, 2);
            params[4] = lenBytes[0];
            params[5] = lenBytes[1];
            System.arraycopy(syncDataBytes, 0, params, 6, syncDataBytes.length);
            byte[] crcBytes = w.q(syncDataBytes);
            System.arraycopy(crcBytes, 0, params, dataLen + 6, crcBytes.length);
            return params;
        } catch (Exception e3) {
            a.b g4 = timber.log.a.g("Rhythm");
            g4.a("setRhythm error=>" + e3.toString(), new Object[0]);
            e3.printStackTrace();
            return new byte[0];
        }
    }

    private byte[] d(JSONObject jSONObject) {
        String str;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{jSONObject}, this, changeQuickRedirect, false, 5690, new Class[]{JSONObject.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        JSONObject jsonObject = jSONObject;
        try {
            JSONArray asyncData = jsonObject.getJSONArray("asyncData");
            int length = 0;
            int i = 0;
            while (true) {
                str = "effects";
                if (i >= asyncData.length()) {
                    break;
                }
                try {
                    JSONObject asyncObj = (JSONObject) asyncData.get(i);
                    length = (((JSONArray) asyncObj.get(str)).length() * 6) + length + ((JSONArray) asyncObj.get("lightIds")).length() + 3;
                    i++;
                } catch (Exception e2) {
                    e = e2;
                    JSONObject jSONObject2 = jsonObject;
                    a.b g2 = timber.log.a.g("Rhythm");
                    g2.a("setRhythm error=>" + e.toString(), new Object[0]);
                    e.printStackTrace();
                    return new byte[0];
                }
            }
            byte[] asyncDataBytes = new byte[(length + 1)];
            asyncDataBytes[0] = (byte) asyncData.length();
            int typeIndex = 0;
            int i2 = 0;
            while (i2 < asyncData.length()) {
                JSONObject asyncObj2 = (JSONObject) asyncData.get(i2);
                JSONArray lightIdArray = (JSONArray) asyncObj2.get("lightIds");
                JSONArray effectArray = (JSONArray) asyncObj2.get(str);
                asyncDataBytes[typeIndex + 1] = (byte) ((effectArray.length() * 6) + lightIdArray.length() + 3);
                asyncDataBytes[typeIndex + 2] = (byte) lightIdArray.length();
                int r = 0;
                while (r < lightIdArray.length()) {
                    a.b g3 = timber.log.a.g("Rhythm");
                    StringBuilder sb = new StringBuilder();
                    sb.append("lightId:");
                    JSONArray lightIdArray2 = lightIdArray;
                    JSONObject jsonObject2 = jsonObject;
                    try {
                        sb.append(lightIdArray2.get(r));
                        g3.a(sb.toString(), new Object[0]);
                        asyncDataBytes[typeIndex + r + 3] = (byte) ((Integer) lightIdArray2.get(r)).intValue();
                        r++;
                        jsonObject = jsonObject2;
                        lightIdArray = lightIdArray2;
                        asyncData = asyncData;
                    } catch (Exception e3) {
                        e = e3;
                        a.b g22 = timber.log.a.g("Rhythm");
                        g22.a("setRhythm error=>" + e.toString(), new Object[0]);
                        e.printStackTrace();
                        return new byte[0];
                    }
                }
                JSONArray lightIdArray3 = lightIdArray;
                JSONObject jsonObject3 = jsonObject;
                JSONArray asyncData2 = asyncData;
                asyncDataBytes[lightIdArray3.length() + typeIndex + 3] = (byte) effectArray.length();
                int e4 = 0;
                typeIndex = lightIdArray3.length() + typeIndex + 3;
                while (e4 < effectArray.length()) {
                    JSONArray effectArray2 = effectArray;
                    JSONObject effectObj = (JSONObject) effectArray2.get(e4);
                    int color = effectObj.getInt(TypedValues.Custom.S_COLOR);
                    int r2 = Color.red(color);
                    int g4 = Color.green(color);
                    int b2 = Color.blue(color);
                    JSONArray effectArray3 = effectArray2;
                    asyncDataBytes[typeIndex + 1] = (byte) r2;
                    int length2 = length;
                    int length3 = g4;
                    String str2 = str;
                    asyncDataBytes[typeIndex + 2] = (byte) length3;
                    int b3 = b2;
                    int b4 = length3;
                    asyncDataBytes[typeIndex + 3] = (byte) b3;
                    int dimming = effectObj.getInt("dimming");
                    int i3 = b3;
                    asyncDataBytes[typeIndex + 4] = (byte) dimming;
                    int i4 = dimming;
                    byte[] faddingBytes = g((long) effectObj.getInt("fading"), 2);
                    asyncDataBytes[typeIndex + 5] = faddingBytes[0];
                    asyncDataBytes[typeIndex + 6] = faddingBytes[1];
                    typeIndex += 6;
                    e4++;
                    length = length2;
                    str = str2;
                    effectArray = effectArray3;
                    lightIdArray3 = lightIdArray3;
                }
                String str3 = str;
                JSONArray jSONArray = lightIdArray3;
                JSONArray jSONArray2 = effectArray;
                int i5 = length;
                i2++;
                jsonObject = jsonObject3;
                asyncData = asyncData2;
            }
            JSONArray jSONArray3 = asyncData;
            int i6 = length;
            a.b g5 = timber.log.a.g("Rhythm");
            g5.a("setRhythm typeIndex =" + typeIndex, new Object[0]);
            int dataLen = asyncDataBytes.length;
            byte[] params = new byte[(dataLen + 6 + 2)];
            params[0] = 11;
            params[1] = 0;
            params[2] = (byte) f();
            params[3] = 1;
            byte[] lenBytes = g((long) dataLen, 2);
            params[4] = lenBytes[0];
            params[5] = lenBytes[1];
            System.arraycopy(asyncDataBytes, 0, params, 6, asyncDataBytes.length);
            byte[] crcBytes = w.q(asyncDataBytes);
            System.arraycopy(crcBytes, 0, params, dataLen + 6, crcBytes.length);
            return params;
        } catch (Exception e5) {
            e = e5;
            JSONObject jSONObject3 = jsonObject;
            a.b g222 = timber.log.a.g("Rhythm");
            g222.a("setRhythm error=>" + e.toString(), new Object[0]);
            e.printStackTrace();
            return new byte[0];
        }
    }

    public void b(String mac, String groupId, byte able, com.leedarson.base.http.listener.a callbackListener) {
    }

    public void c(String mac, String groupId, int[] colors) {
    }

    private int f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5691, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.g == null) {
            this.g = new AtomicInteger(1);
        }
        if (this.g.get() != 255) {
            return this.g.incrementAndGet();
        }
        this.g.set(0);
        return this.g.get();
    }

    public static byte[] g(long u, int length) {
        byte[] arr = new byte[length];
        if (length == 2) {
            arr[1] = (byte) ((int) ((u >> 8) & 255));
            arr[0] = (byte) ((int) (u & 255));
        } else if (length == 4) {
            arr[3] = (byte) ((int) ((u >> 24) & 255));
            arr[2] = (byte) ((int) ((u >> 16) & 255));
            arr[1] = (byte) ((int) ((u >> 8) & 255));
            arr[0] = (byte) ((int) (u & 255));
        }
        return arr;
    }
}
