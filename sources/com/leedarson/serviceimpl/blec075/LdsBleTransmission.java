package com.leedarson.serviceimpl.blec075;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.text.TextUtils;
import com.clj.fastble.data.BleDevice;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.leedarson.base.beans.CommonBleReadConfigBean;
import com.leedarson.base.utils.b;
import com.leedarson.base.utils.f;
import com.leedarson.base.utils.p;
import com.leedarson.serviceimpl.ble.manager.g;
import com.leedarson.serviceimpl.blec075.beans.BleWriteRequestBean;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.Constants;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.greenrobot.eventbus.c;
import org.json.JSONArray;
import org.json.JSONObject;
import timber.log.a;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class LdsBleTransmission {
    public static ChangeQuickRedirect changeQuickRedirect;
    private BleC075ServiceImpl a;
    /* access modifiers changed from: private */
    public String b;
    /* access modifiers changed from: private */
    public JSONArray c;

    public static class BlindMatchBlueDeviceInfoBean {
        public String _serviceId = "";
        public String _uuid = "";
        public boolean listenTypeIsIndicate = false;
    }

    static /* synthetic */ void b(LdsBleTransmission x0, BleDevice x1) {
        Class[] clsArr = {LdsBleTransmission.class, BleDevice.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6442, clsArr, Void.TYPE).isSupported) {
            x0.f(x1);
        }
    }

    public LdsBleTransmission(BleC075ServiceImpl bleC075Service, String TAG) {
        this.a = bleC075Service;
        this.b = TAG;
    }

    public void j(String str, String str2) {
        BlindMatchBlueDeviceInfoBean _blindDeviceBean;
        boolean calculateCRCFlag;
        byte[] paramBytes;
        byte[] writeData;
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2}, this, changeQuickRedirect, false, 6433, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            String data = str2;
            String callbackKey = str;
            try {
                LinkedTreeMap treeMap = c.a(data);
                Gson gson = new Gson();
                BleWriteRequestBean _requestBean = (BleWriteRequestBean) gson.fromJson(data, BleWriteRequestBean.class);
                String mac = _requestBean.mac;
                BleDevice _tempBleDevice = this.a.r(mac);
                StringBuilder detailContent = new StringBuilder();
                detailContent.append("[蓝牙写入数据] 收到业务请求request=" + data);
                detailContent.append("\n");
                if (_tempBleDevice == null) {
                    detailContent.append("[蓝牙连接通道检测]  通道未连接成功");
                    c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(-1, detailContent.toString()).toString()));
                    return;
                }
                detailContent.append("[蓝牙连接通道检测]  连接成功");
                detailContent.append("\n");
                if (_requestBean.checkCharacterOrServiceUuidIsShortMode()) {
                    Gson gson2 = gson;
                    _blindDeviceBean = d(this.a.r(mac), _requestBean.serviceUUID, treeMap.get("characteristicsUUID").toString(), detailContent);
                } else {
                    _blindDeviceBean = new BlindMatchBlueDeviceInfoBean();
                    _blindDeviceBean._uuid = _requestBean.characteristicsUUID;
                    _blindDeviceBean._serviceId = _requestBean.serviceUUID;
                }
                if (_blindDeviceBean == null) {
                    String tips = ("Ble通道数据写入失败失败(uuid&&serviceId未找到匹配项-存在原因:未找到设备暴露的characterUuid or 传入目标值不对)  serviceUUID=" + _requestBean.serviceUUID + " ,characteristicsUUID=" + treeMap.get("characteristicsUUID").toString() + " matchConnected=" + this.a.r(mac)) + "\n" + detailContent;
                    c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(-1, tips).toString()));
                    timber.log.a.g(this.b).c(tips, new Object[0]);
                    return;
                }
                String str3 = _blindDeviceBean._serviceId;
                Locale locale = Locale.US;
                UUID serviceId = UUID.fromString(str3.toLowerCase(locale));
                UUID characterUUID = UUID.fromString(_blindDeviceBean._uuid.toLowerCase(locale));
                String encrypt = _requestBean.encrypt;
                String encryptKey = _requestBean.encryptKey;
                String encryptIV = _requestBean.encryptIV;
                String paramsType = (!treeMap.containsKey("paramsType") || treeMap.get("paramsType") == null) ? null : treeMap.get("paramsType").toString();
                String params = treeMap.get("params").toString();
                LinkedTreeMap linkedTreeMap = treeMap;
                JSONObject _originData = new JSONObject(data);
                if (_originData.has("calculateCRC")) {
                    calculateCRCFlag = _originData.getBoolean("calculateCRC");
                } else {
                    calculateCRCFlag = false;
                }
                BlindMatchBlueDeviceInfoBean blindMatchBlueDeviceInfoBean = _blindDeviceBean;
                if (Constants.BYTE.equals(paramsType)) {
                    paramBytes = g.c(params);
                } else {
                    paramBytes = params.getBytes("UTF-8");
                }
                byte[] writeData2 = paramBytes;
                if (!TextUtils.isEmpty(encryptKey)) {
                    String str4 = paramsType;
                    if (CommonBleReadConfigBean.ENCRYPT_AES_256.equals(encrypt)) {
                        boolean z = calculateCRCFlag;
                        String str5 = encrypt;
                        writeData = h.g(encryptKey, paramBytes);
                    } else if (CommonBleReadConfigBean.ENCRYPT_AES_128.equals(encrypt)) {
                        boolean z2 = calculateCRCFlag;
                        String str6 = encrypt;
                        writeData = b.d(encryptKey, paramBytes);
                    } else if (CommonBleReadConfigBean.ENCRYPT_AES_256_CTR.equals(encrypt)) {
                        String str7 = encrypt;
                        if (!calculateCRCFlag) {
                            if (TextUtils.isEmpty(params) || !params.contains("{") || !params.contains("}")) {
                                writeData = f.c(encryptKey, params, encryptIV);
                            } else {
                                writeData = f.c(encryptKey, new JSONObject(data).get("params").toString(), encryptIV);
                            }
                        } else if (TextUtils.isEmpty(params) || !params.contains("{") || !params.contains("}")) {
                            timber.log.a.g(this.b).c("参数校验失败-终止写入,传进来参数是非JsonObject对象,无法进行crc相关的校验", new Object[0]);
                            boolean z3 = calculateCRCFlag;
                            c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(-1, "参数校验失败-终止写入,传进来参数是非JsonObject对象,无法进行crc相关的校验").toString()));
                            return;
                        } else {
                            JSONObject paramsObj = new JSONObject(_originData.get("params").toString());
                            f.a(paramsObj);
                            boolean z4 = calculateCRCFlag;
                            writeData = f.c(encryptKey, paramsObj.toString(), encryptIV);
                        }
                    } else {
                        String str8 = encrypt;
                    }
                    String str9 = params;
                    String str10 = encryptIV;
                    this.a.commonWrite(mac, (BluetoothDevice) null, serviceId, characterUUID, encryptKey, writeData, callbackKey, (BleC075Service.CommonBleCallback) null, true, -1);
                }
                String str11 = paramsType;
                String str12 = encrypt;
                writeData = writeData2;
                String str92 = params;
                String str102 = encryptIV;
                this.a.commonWrite(mac, (BluetoothDevice) null, serviceId, characterUUID, encryptKey, writeData, callbackKey, (BleC075Service.CommonBleCallback) null, true, -1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void g(String str, String str2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2}, this, changeQuickRedirect, false, 6434, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            String data = str2;
            String callbackKey = str;
            try {
                LinkedTreeMap treeMap1 = c.a(data);
                String mac = (!treeMap1.containsKey("mac") || treeMap1.get("mac") == null) ? null : treeMap1.get("mac").toString();
                StringBuilder detailContent = new StringBuilder();
                detailContent.append("[收到服务请求] requestData=" + data);
                detailContent.append("\n");
                if (this.a.r(mac) == null) {
                    detailContent.append("[蓝牙连接通道检测]  通道未连接成功");
                    c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(-1, detailContent.toString()).toString()));
                    return;
                }
                detailContent.append("[蓝牙连接通道检测]  连接成功");
                detailContent.append("\n");
                BlindMatchBlueDeviceInfoBean _blindMatchBean = d(this.a.r(mac), "", treeMap1.get("characteristicsUUID").toString(), detailContent);
                if (_blindMatchBean == null) {
                    String tips = ("读取Ble通道数据失败(uuid&&serviceId未找到匹配项-存在原因:传入目标值不对 Or 从已连接设备中未找到匹配的特征)  serviceUUID=characteristicsUUID=" + treeMap1.get("characteristicsUUID").toString()) + "\n" + detailContent.toString();
                    c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(-1, tips).toString()));
                    timber.log.a.g(this.b).c(tips, new Object[0]);
                    return;
                }
                UUID serviceId1 = UUID.fromString(_blindMatchBean._serviceId);
                UUID characterUUID1 = UUID.fromString(_blindMatchBean._uuid);
                String encrypt = treeMap1.containsKey("encrypt") ? treeMap1.get("encrypt").toString() : null;
                String encryptKey = treeMap1.containsKey("encryptKey") ? treeMap1.get("encryptKey").toString() : null;
                CommonBleReadConfigBean _bleConfig = new CommonBleReadConfigBean();
                _bleConfig.mac = mac;
                _bleConfig.serviceUUID = serviceId1;
                _bleConfig.characterUUID = characterUUID1;
                _bleConfig.encryptKey = encryptKey;
                _bleConfig.jsbridgeCallbackKey = callbackKey;
                _bleConfig.retryWhenInterrupt = true;
                _bleConfig.encrypt = encrypt;
                this.a.commonRead(_bleConfig);
            } catch (Exception e) {
                e.printStackTrace();
                timber.log.a.g(this.b).h("read Exception=" + e.toString(), new Object[0]);
            }
        }
    }

    public void h(String str, String str2) {
        Exception e;
        BlindMatchBlueDeviceInfoBean _blindMatchBean;
        String listenType;
        String sb;
        Class<String> cls = String.class;
        String str3 = "notify";
        if (!PatchProxy.proxy(new Object[]{str, str2}, this, changeQuickRedirect, false, 6435, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            String data = str2;
            String callbackKey = str;
            StringBuilder detailsContent = new StringBuilder();
            try {
                LinkedTreeMap treeMap2 = c.a(data);
                Gson gson = new Gson();
                BleWriteRequestBean _requestBean = (BleWriteRequestBean) gson.fromJson(data, BleWriteRequestBean.class);
                String mac = _requestBean.mac;
                BleDevice _tempBleDevice = this.a.r(mac);
                Gson gson2 = gson;
                StringBuilder sb2 = new StringBuilder();
                String str4 = "desc";
                try {
                    sb2.append("[收到监听请求Request]");
                    sb2.append(data);
                    detailsContent.append(sb2.toString());
                    detailsContent.append("\n");
                    if (_tempBleDevice != null) {
                        detailsContent.append("[连接状态检测] 目标设备(" + mac + ")已连接");
                        String str5 = data;
                        try {
                            timber.log.a.i("lds.startListenTraceId mac=" + _tempBleDevice.c() + "    getmBleAdvertisementData=" + _tempBleDevice.h() + "   rssid=" + _tempBleDevice.e(), new Object[0]);
                            detailsContent.append("\n");
                            if (_requestBean.checkCharacterOrServiceUuidIsShortMode()) {
                                detailsContent.append("[服务识别模式]  短码模糊匹配");
                                _blindMatchBean = d(_tempBleDevice, "", treeMap2.get("characteristicsUUID").toString(), detailsContent);
                            } else {
                                detailsContent.append("[服务识别模式]  长码绝对匹配");
                                _blindMatchBean = new BlindMatchBlueDeviceInfoBean();
                                _blindMatchBean._uuid = _requestBean.characteristicsUUID;
                                _blindMatchBean._serviceId = _requestBean.serviceUUID;
                            }
                            detailsContent.append("\n");
                            if (_blindMatchBean == null) {
                                String tips = ("读取Ble通道数据监听失败(uuid&&serviceId未找到匹配项-存在原因: 传入目标值不对 Or 目标设备中未存在匹配的特征值)  serviceUUID=characteristicsUUID=" + treeMap2.get("characteristicsUUID").toString()) + "\n" + detailsContent.toString();
                                c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(-1, tips).toString()));
                                timber.log.a.g(this.b).c(tips, new Object[0]);
                                return;
                            }
                            UUID serviceId2 = UUID.fromString(_blindMatchBean._serviceId);
                            UUID characterUUID2 = UUID.fromString(_blindMatchBean._uuid);
                            BleDevice bleDevice = _tempBleDevice;
                            String encrypt = treeMap2.containsKey("encrypt") ? treeMap2.get("encrypt").toString() : null;
                            String encryptKey3 = treeMap2.containsKey("encryptKey") ? treeMap2.get("encryptKey").toString() : null;
                            List<BleDevice> allConnectedDevice = com.clj.fastble.a.o().g();
                            if (allConnectedDevice != null) {
                                allConnectedDevice.size();
                            }
                            String str6 = str3;
                            if (treeMap2.containsKey(IjkMediaMeta.IJKM_KEY_TYPE)) {
                                listenType = treeMap2.get(IjkMediaMeta.IJKM_KEY_TYPE).toString();
                            } else {
                                listenType = _blindMatchBean.listenTypeIsIndicate ? "indicate" : str3;
                            }
                            detailsContent.append("\n");
                            detailsContent.append("listenType=" + listenType);
                            CommonBleReadConfigBean _configBean = new CommonBleReadConfigBean();
                            _configBean.mac = mac;
                            _configBean.serviceUUID = serviceId2;
                            _configBean.characterUUID = characterUUID2;
                            _configBean.encryptKey = encryptKey3;
                            _configBean.commonBleCallback = null;
                            _configBean.jsbridgeCallbackKey = callbackKey;
                            _configBean.encrypt = encrypt;
                            if (str3.equals(listenType)) {
                                this.a.commonNotifyConfig(_configBean);
                            } else {
                                this.a.commonIndicateConfig(_configBean);
                                try {
                                    JSONObject j2 = new JSONObject();
                                    j2.put("code", 200);
                                    StringBuilder sb3 = new StringBuilder();
                                    String str7 = encryptKey3;
                                    try {
                                        sb3.append("监听成功：");
                                        sb3.append(detailsContent.toString());
                                        sb = sb3.toString();
                                        str3 = str4;
                                    } catch (Exception e2) {
                                        e = e2;
                                        BlindMatchBlueDeviceInfoBean blindMatchBlueDeviceInfoBean = _blindMatchBean;
                                        str3 = str4;
                                        String str8 = encrypt;
                                        try {
                                            e.printStackTrace();
                                            timber.log.a.c("lds.startListenTraceId start notify Exception: " + e.toString(), new Object[0]);
                                        } catch (Exception e3) {
                                            e = e3;
                                            e = e;
                                            timber.log.a.g(this.b).c("LdsBleTransmission.startListen exception111=" + e.toString(), new Object[0]);
                                            try {
                                                JSONObject j22 = new JSONObject();
                                                j22.put("code", -1);
                                                j22.put(str3, (Object) "LdsBleTransmission.startListen exception:" + e.toString());
                                                c.c().l(new JsBridgeCallbackEvent(callbackKey, j22.toString()));
                                            } catch (Exception e1) {
                                                e1.printStackTrace();
                                                timber.log.a.c("lds.startListenTraceId start notify Exception1: " + e.toString(), new Object[0]);
                                                return;
                                            }
                                        }
                                    }
                                    try {
                                        j2.put(str3, (Object) sb);
                                        String str9 = encrypt;
                                        try {
                                            BlindMatchBlueDeviceInfoBean blindMatchBlueDeviceInfoBean2 = _blindMatchBean;
                                            try {
                                                c.c().l(new JsBridgeCallbackEvent(callbackKey, j2.toString()));
                                            } catch (Exception e4) {
                                                e = e4;
                                            }
                                        } catch (Exception e5) {
                                            e = e5;
                                            BlindMatchBlueDeviceInfoBean blindMatchBlueDeviceInfoBean3 = _blindMatchBean;
                                            e.printStackTrace();
                                            timber.log.a.c("lds.startListenTraceId start notify Exception: " + e.toString(), new Object[0]);
                                        }
                                    } catch (Exception e6) {
                                        e = e6;
                                        String str10 = encrypt;
                                        BlindMatchBlueDeviceInfoBean blindMatchBlueDeviceInfoBean4 = _blindMatchBean;
                                        e.printStackTrace();
                                        timber.log.a.c("lds.startListenTraceId start notify Exception: " + e.toString(), new Object[0]);
                                    }
                                } catch (Exception e7) {
                                    e = e7;
                                    String str11 = encryptKey3;
                                    BlindMatchBlueDeviceInfoBean blindMatchBlueDeviceInfoBean5 = _blindMatchBean;
                                    str3 = str4;
                                    String str12 = encrypt;
                                    e.printStackTrace();
                                    timber.log.a.c("lds.startListenTraceId start notify Exception: " + e.toString(), new Object[0]);
                                }
                            }
                        } catch (Exception e8) {
                            e = e8;
                            str3 = str4;
                            e = e;
                            timber.log.a.g(this.b).c("LdsBleTransmission.startListen exception111=" + e.toString(), new Object[0]);
                            JSONObject j222 = new JSONObject();
                            j222.put("code", -1);
                            j222.put(str3, (Object) "LdsBleTransmission.startListen exception:" + e.toString());
                            c.c().l(new JsBridgeCallbackEvent(callbackKey, j222.toString()));
                        }
                    } else {
                        BleDevice bleDevice2 = _tempBleDevice;
                        String str13 = data;
                        String str14 = str4;
                        detailsContent.append("[连接状态检测] 目标设备(" + mac + ") 未连接-离线 - 监听失败");
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("lds.startListenTraceId  根据mac地址，未发现到有 mac=");
                        sb4.append(mac);
                        timber.log.a.i(sb4.toString(), new Object[0]);
                        c.c().l(new JsBridgeCallbackEvent(callbackKey, p.a(-1, detailsContent.toString()).toString()));
                    }
                } catch (Exception e9) {
                    e = e9;
                    String str15 = data;
                    str3 = str4;
                    e = e;
                    timber.log.a.g(this.b).c("LdsBleTransmission.startListen exception111=" + e.toString(), new Object[0]);
                    JSONObject j2222 = new JSONObject();
                    j2222.put("code", -1);
                    j2222.put(str3, (Object) "LdsBleTransmission.startListen exception:" + e.toString());
                    c.c().l(new JsBridgeCallbackEvent(callbackKey, j2222.toString()));
                }
            } catch (Exception e10) {
                e = e10;
                str3 = "desc";
                String str16 = data;
                e = e;
                timber.log.a.g(this.b).c("LdsBleTransmission.startListen exception111=" + e.toString(), new Object[0]);
                JSONObject j22222 = new JSONObject();
                j22222.put("code", -1);
                j22222.put(str3, (Object) "LdsBleTransmission.startListen exception:" + e.toString());
                c.c().l(new JsBridgeCallbackEvent(callbackKey, j22222.toString()));
            }
        }
    }

    public void i(String str, String data) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, data}, this, changeQuickRedirect, false, 6436, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            String callbackKey = str;
            try {
                LinkedTreeMap treeMap2 = c.a(data);
                UUID serviceId2 = UUID.fromString(treeMap2.get("serviceUUID").toString());
                UUID characterUUID2 = UUID.fromString(treeMap2.get("characteristicsUUID").toString());
                BleDevice currentDev = this.a.r((!treeMap2.containsKey("mac") || treeMap2.get("mac") == null) ? null : treeMap2.get("mac").toString());
                String listenType = "notify";
                if (treeMap2.containsKey(IjkMediaMeta.IJKM_KEY_TYPE)) {
                    listenType = treeMap2.get(IjkMediaMeta.IJKM_KEY_TYPE).toString();
                }
                JSONObject j2 = new JSONObject();
                if (currentDev == null) {
                    j2.put("code", 401);
                    j2.put("desc", (Object) "could not find current ble device ");
                } else {
                    if ("notify".equals(listenType)) {
                        com.clj.fastble.a.o().L(currentDev, serviceId2.toString(), characterUUID2.toString());
                    } else {
                        com.clj.fastble.a.o().J(currentDev, serviceId2.toString(), characterUUID2.toString());
                    }
                    j2.put("code", 200);
                    j2.put("desc", (Object) "");
                }
                c.c().l(new JsBridgeCallbackEvent(callbackKey, j2.toString()));
            } catch (Exception e1) {
                e1.printStackTrace();
                timber.log.a.c("stop notify Exception: " + e1.toString(), new Object[0]);
                c.c().l(new JsBridgeCallbackEvent(callbackKey, "{\"code\":200}"));
            }
        }
    }

    public void e(BleDevice device, String callbackKey) {
        if (!PatchProxy.proxy(new Object[]{device, callbackKey}, this, changeQuickRedirect, false, 6437, new Class[]{BleDevice.class, String.class}, Void.TYPE).isSupported) {
            this.c = new JSONArray();
            if (device == null) {
                c.c().l(new JsBridgeCallbackEvent(callbackKey, p.d(this.c).toString()));
            } else if (com.clj.fastble.a.o().A(device)) {
                f(device);
                c.c().l(new JsBridgeCallbackEvent(callbackKey, p.d(this.c).toString()));
            } else {
                a.b g = timber.log.a.g(this.b);
                g.a("getCharacteristicsUUID 连接蓝牙:" + device.c(), new Object[0]);
                com.clj.fastble.a.o().b(device, new a(device, callbackKey));
            }
        }
    }

    public class a extends com.clj.fastble.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ BleDevice a;
        final /* synthetic */ String b;

        a(BleDevice bleDevice, String str) {
            this.a = bleDevice;
            this.b = str;
        }

        public void e() {
        }

        public void b(BleDevice bleDevice, com.clj.fastble.exception.a exception) {
        }

        public void c(BleDevice bleDevice, BluetoothGatt bluetoothGatt, int i) {
            if (!PatchProxy.proxy(new Object[]{bleDevice, bluetoothGatt, new Integer(i)}, this, changeQuickRedirect, false, 6448, new Class[]{BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(LdsBleTransmission.this.b);
                g.a("getCharacteristicsUUID 连接蓝牙成功:" + this.a.c(), new Object[0]);
                LdsBleTransmission.b(LdsBleTransmission.this, bleDevice);
                com.clj.fastble.a.o().d(bleDevice);
            }
        }

        public void d(boolean z, BleDevice device, BluetoothGatt bluetoothGatt, int i) {
            Object[] objArr = {new Byte(z ? (byte) 1 : 0), device, bluetoothGatt, new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6449, new Class[]{Boolean.TYPE, BleDevice.class, BluetoothGatt.class, Integer.TYPE}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(LdsBleTransmission.this.b);
                g.a("getCharacteristicsUUID 蓝牙释放成功:" + device.c(), new Object[0]);
                c.c().l(new JsBridgeCallbackEvent(this.b, p.d(LdsBleTransmission.this.c).toString()));
            }
        }

        public void a(int code, String desc) {
        }
    }

    private void f(BleDevice device) {
        if (!PatchProxy.proxy(new Object[]{device}, this, changeQuickRedirect, false, 6438, new Class[]{BleDevice.class}, Void.TYPE).isSupported) {
            List<BluetoothGattService> list = com.clj.fastble.a.o().k(device);
            if (list != null) {
                for (BluetoothGattService service : list) {
                    for (BluetoothGattCharacteristic charItem : com.clj.fastble.a.o().j(service)) {
                        this.c.put((Object) charItem.getUuid().toString().toUpperCase(Locale.US));
                    }
                }
            }
        }
    }

    public static BlindMatchBlueDeviceInfoBean d(BleDevice bleDevice, String str, String str2, StringBuilder sb) {
        BlindMatchBlueDeviceInfoBean bleDevice2;
        int retryMaxCount;
        String serviceId;
        BleDevice device;
        String tipStart;
        Class<String> cls = String.class;
        int i = 0;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bleDevice, str, str2, sb}, (Object) null, changeQuickRedirect, true, 6439, new Class[]{BleDevice.class, cls, cls, StringBuilder.class}, BlindMatchBlueDeviceInfoBean.class);
        if (proxy.isSupported) {
            return (BlindMatchBlueDeviceInfoBean) proxy.result;
        }
        BleDevice device2 = bleDevice;
        String charicuuId = str2;
        String serviceId2 = str;
        StringBuilder matchStepDetails = sb;
        int retryCount = 0;
        int retryMaxCount2 = 3;
        BlindMatchBlueDeviceInfoBean bleDevice3 = null;
        while (retryCount < retryMaxCount2) {
            retryCount++;
            matchStepDetails.append("\n");
            String tipStart2 = "[模糊匹配服务] 开始模糊匹配对应的已连接设备 && 其characterId 中有匹配的mac=" + device2.c() + "  characterUuid=" + charicuuId + "  retryCount=" + retryCount;
            timber.log.a.g("LdsBleTransmission").h(tipStart2, new Object[i]);
            matchStepDetails.append(tipStart2);
            matchStepDetails.append("\n");
            List<BluetoothGattService> list = com.clj.fastble.a.o().k(device2);
            if (list != null) {
                for (BluetoothGattService service : list) {
                    List<BluetoothGattCharacteristic> characteristicList = com.clj.fastble.a.o().j(service);
                    BleDevice device3 = device2;
                    StringBuilder sb2 = new StringBuilder();
                    String serviceId3 = serviceId2;
                    sb2.append("[模糊匹配服务] 当前正在匹配： serviceUuid=");
                    sb2.append(service.getUuid());
                    String tipServiceCurrentMatch = sb2.toString();
                    matchStepDetails.append(tipServiceCurrentMatch);
                    matchStepDetails.append("\n");
                    int retryMaxCount3 = retryMaxCount2;
                    BlindMatchBlueDeviceInfoBean bleDevice4 = bleDevice3;
                    timber.log.a.g("LdsBleTransmission").h(tipServiceCurrentMatch, new Object[0]);
                    if (characteristicList != null) {
                        Iterator<BluetoothGattCharacteristic> it = characteristicList.iterator();
                        while (it.hasNext()) {
                            BluetoothGattCharacteristic charItem = it.next();
                            StringBuilder sb3 = new StringBuilder();
                            String tipServiceCurrentMatch2 = tipServiceCurrentMatch;
                            sb3.append("[模糊匹配服务] 当前正在匹配： characterUuid=");
                            Locale locale = Locale.US;
                            Iterator<BluetoothGattCharacteristic> it2 = it;
                            sb3.append(charicuuId.toLowerCase(locale));
                            String tipCharacterCurrentMatch = sb3.toString();
                            matchStepDetails.append(tipCharacterCurrentMatch);
                            matchStepDetails.append("\n");
                            List<BluetoothGattCharacteristic> characteristicList2 = characteristicList;
                            String tipStart3 = tipStart2;
                            timber.log.a.g("LdsBleTransmission").h(tipCharacterCurrentMatch, new Object[0]);
                            if (charItem.getUuid().toString().contains(charicuuId.toUpperCase(locale)) || charItem.getUuid().toString().contains(charicuuId.toLowerCase(locale))) {
                                BlindMatchBlueDeviceInfoBean bleDevice5 = new BlindMatchBlueDeviceInfoBean();
                                bleDevice5._serviceId = service.getUuid().toString();
                                bleDevice5._uuid = charItem.getUuid().toString();
                                bleDevice5.listenTypeIsIndicate = (charItem.getProperties() & 32) != 0;
                                String successMatch = "[模糊匹配服务] 找到了对应设备--》找到对应的要匹配的目标设备--->serviceId=" + service.getUuid().toString() + "  characterUuid=" + charItem.getUuid().toString() + "  retryCount=" + retryCount;
                                timber.log.a.g("LdsBleTransmission").h(successMatch, new Object[0]);
                                matchStepDetails.append(successMatch);
                                matchStepDetails.append("\n");
                                return bleDevice5;
                            }
                            matchStepDetails.append("[模糊匹配服务] 当前character未匹配成功 characterUuid=" + charItem.getUuid().toString());
                            matchStepDetails.append("\n");
                            tipServiceCurrentMatch = tipServiceCurrentMatch2;
                            it = it2;
                            characteristicList = characteristicList2;
                            tipStart2 = tipStart3;
                        }
                        List<BluetoothGattCharacteristic> list2 = characteristicList;
                        tipStart = tipStart2;
                    } else {
                        List<BluetoothGattCharacteristic> list3 = characteristicList;
                        tipStart = tipStart2;
                        matchStepDetails.append("[模糊匹配服务] 当前Service =" + service.getUuid() + " 下属没有发现任何characterUuid");
                        matchStepDetails.append("\n");
                    }
                    device2 = device3;
                    serviceId2 = serviceId3;
                    retryMaxCount2 = retryMaxCount3;
                    bleDevice3 = bleDevice4;
                    tipStart2 = tipStart;
                }
                device = device2;
                serviceId = serviceId2;
                retryMaxCount = retryMaxCount2;
                bleDevice2 = bleDevice3;
                String str3 = tipStart2;
            } else {
                device = device2;
                serviceId = serviceId2;
                retryMaxCount = retryMaxCount2;
                bleDevice2 = bleDevice3;
                String str4 = tipStart2;
                String tipNotFoundService = "[模糊匹配服务] 对不起--》找不到对应的要匹配的目标设备---> retryCount=" + retryCount;
                timber.log.a.g("LdsBleTransmission").h(tipNotFoundService, new Object[0]);
                matchStepDetails.append(tipNotFoundService);
                matchStepDetails.append("\n");
            }
            device2 = device;
            serviceId2 = serviceId;
            retryMaxCount2 = retryMaxCount;
            bleDevice3 = bleDevice2;
            i = 0;
        }
        String str5 = serviceId2;
        int i2 = retryMaxCount2;
        matchStepDetails.append("[模糊匹配服务] 经多次尝试 - 最终都未能发现目标service && characterId");
        matchStepDetails.append("\n");
        timber.log.a.g("LdsBleTransmission").c("[模糊匹配服务] 经多次尝试 - 最终都未能发现目标service && characterId", new Object[0]);
        return null;
    }
}
