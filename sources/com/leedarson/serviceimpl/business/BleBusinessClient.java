package com.leedarson.serviceimpl.business;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import androidx.core.app.NotificationCompat;
import com.clj.fastble.data.BleDevice;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.beans.CommonBleReadConfigBean;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.b;
import com.leedarson.base.utils.e;
import com.leedarson.base.utils.p;
import com.leedarson.bean.Constants;
import com.leedarson.bean.H5ActionName;
import com.leedarson.serviceimpl.ble.manager.d;
import com.leedarson.serviceimpl.blec075.BleC075ServiceImpl;
import com.leedarson.serviceimpl.blec075.callback.b;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.blec075.reports.BleConnectStepBean;
import com.leedarson.serviceimpl.blec075.reports.a;
import com.leedarson.serviceimpl.blec075.util.BleConnectedDeviceDiscoverUtil;
import com.leedarson.serviceimpl.business.ClientConnection;
import com.leedarson.serviceimpl.business.TRVOta;
import com.leedarson.serviceimpl.business.bean.BleBusinessConnectBean;
import com.leedarson.serviceimpl.business.bean.ClientEncryptBean;
import com.leedarson.serviceimpl.business.bean.CompatNotifyAndWriteInfoBean;
import com.leedarson.serviceimpl.business.bean.TRVCommandBean;
import com.leedarson.serviceimpl.business.bean.TRVState;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.leedarson.serviceinterface.event.JsCallH5ByNativeEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.m;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import meshsdk.BaseResp;
import meshsdk.ctrl.GroupCtrlAdapter;
import org.greenrobot.eventbus.c;
import org.json.JSONException;
import org.json.JSONObject;
import timber.log.a;

public class BleBusinessClient {
    private static final String BleOTANotifyCharUUID = "000010a2-1115-1000-4c44-5341524e4f4f";
    private static final String BleOTAServiceUUID = "000010a0-1115-1000-4c44-5341524e4f4f";
    private static final String BleOTAWriteCharUUID = "000010a1-1115-1000-4c44-5341524e4f4f";
    private static final String NOTIFY_CHARACTERISTIC_UUID = "00001002-1115-1000-4c44-5341524e4f4f";
    private static final String SEND_CHARACTERISTIC_UUID = "00001001-1115-1000-4c44-5341524e4f4f";
    private static final String SEND_SERVICE_UUID = "00001000-1115-1000-4c44-5341524e4f4f";
    private static final String WifiBleNotifyCharUUID = "5cfefeab-cdc1-4a6d-b1af-6f18294cb002";
    private static final String WifiBleOTANotifyCharUUID = "5cfefeab-cdc1-4a6d-b1af-6f18294cdf02";
    private static final String WifiBleOTAWriteCharUUID = "5cfefeab-cdc1-4a6d-b1af-6f18294cdf01";
    private static final String WifiBleWriteCharUUID = "5cfefeab-cdc1-4a6d-b1af-6f18294cb001";
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int CONNECT_STATE_AUTHORED = 2;
    private final int CONNECT_STATE_AUTHORED_FAIL = -2;
    private final int CONNECT_STATE_CONNECTED = 1;
    private final int CONNECT_STATE_CONNECT_FAIL = -1;
    private final int CONNECT_STATE_DISCONNECTED = 4;
    private final int CONNECT_STATE_IDLE = 0;
    /* access modifiers changed from: private */
    public int CURRENT_CONNECT_STATE = 0;
    private final String ENC_TYPE_AES128 = CommonBleReadConfigBean.ENCRYPT_AES_128;
    private final String ENC_TYPE_AES128_CTR = "aes128CTR";
    private final String ENC_TYPE_AES256 = CommonBleReadConfigBean.ENCRYPT_AES_256;
    private final String ENC_TYPE_AES256_CTR = CommonBleReadConfigBean.ENCRYPT_AES_256_CTR;
    private final String RESP_CMD_CONNECT = "90";
    private final String STATUS_SUCCESS = "00";
    /* access modifiers changed from: private */
    public String TAG = "BleC075ServiceImpl#BleBusinessClient";
    /* access modifiers changed from: private */
    public byte[] _cacheData = new byte[0];
    private CompatNotifyAndWriteInfoBean _compatNotifyAndWriteUuidBean;
    private CompatNotifyAndWriteInfoBean _compatOTANotifyAndWriteUuidBean;
    public BleBusinessConnectBean _connectBean;
    /* access modifiers changed from: private */
    public boolean _forceDisconnectFlag = false;
    RetrySendWriteSignFactory _reWriteSignFactory;
    /* access modifiers changed from: private */
    public a _reporterOfSigIn;
    a _reporterOfWriteCommand = null;
    ClientConnection.OnGattConnectEventChangeHandler _wrapConnectListerner;
    private HashMap<Integer, String> callbackMap;
    private boolean checkPairingStatus = false;
    /* access modifiers changed from: private */
    public ClientConnection clientConnection;
    /* access modifiers changed from: private */
    public String connectCallbackKey;
    private TRVState currentState = TRVState.IDLE;
    /* access modifiers changed from: private */
    public String encryptKey;
    private String encryptType;
    private HandlerThread handlerThread;
    /* access modifiers changed from: private */
    public boolean hasNotify;
    /* access modifiers changed from: private */
    public String mac;
    /* access modifiers changed from: private */
    public String randomStr;
    /* access modifiers changed from: private */
    public int retryConnectTimes = 0;
    private int sequenceNum = 1;
    int successCount = 0;
    /* access modifiers changed from: private */
    public ClientEncryptBean tempEncryptBean = null;
    int totalCount = 0;
    /* access modifiers changed from: private */
    public TRVOta trvOta;

    static /* synthetic */ void access$100(BleBusinessClient x0, String x1) {
        Class[] clsArr = {BleBusinessClient.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6960, clsArr, Void.TYPE).isSupported) {
            x0.log(x1);
        }
    }

    static /* synthetic */ void access$1200(BleBusinessClient x0, BluetoothGatt x1) {
        Class[] clsArr = {BleBusinessClient.class, BluetoothGatt.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6961, clsArr, Void.TYPE).isSupported) {
            x0.createCompatWriteCharacterCompat(x1);
        }
    }

    static /* synthetic */ void access$1300(BleBusinessClient x0, ClientEncryptBean x1) {
        Class[] clsArr = {BleBusinessClient.class, ClientEncryptBean.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6962, clsArr, Void.TYPE).isSupported) {
            x0.writeSignIn(x1);
        }
    }

    static /* synthetic */ void access$1500(BleBusinessClient x0, String str, String x2, String str2, String x4, String x5, String x6) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, str, x2, str2, x4, x5, x6}, (Object) null, changeQuickRedirect, true, 6963, new Class[]{BleBusinessClient.class, cls, cls, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            String x1 = str;
            String x3 = str2;
            x0.traceElk(x1, x2, x3, x4, x5, x6);
        }
    }

    static /* synthetic */ void access$2200(BleBusinessClient x0, String x1) {
        Class[] clsArr = {BleBusinessClient.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6964, clsArr, Void.TYPE).isSupported) {
            x0.D(x1);
        }
    }

    static /* synthetic */ byte[] access$3000(BleBusinessClient x0, byte[] x1) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 6965, new Class[]{BleBusinessClient.class, byte[].class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : x0.decrypt(x1);
    }

    static /* synthetic */ void access$3200(BleBusinessClient x0, byte[] x1, String x2) {
        Class[] clsArr = {BleBusinessClient.class, byte[].class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 6966, clsArr, Void.TYPE).isSupported) {
            x0.notifyToBusinessLayout(x1, x2);
        }
    }

    public void setCheckPairingStatus(boolean checkPairingStatus2) {
        this.checkPairingStatus = checkPairingStatus2;
        ClientConnection clientConnection2 = this.clientConnection;
        if (clientConnection2 != null) {
            clientConnection2.checkPairingStatus = checkPairingStatus2;
        }
    }

    public ClientConnection getClientConnection() {
        return this.clientConnection;
    }

    public void setClientConnection(ClientConnection pClientConnection) {
        this.clientConnection = pClientConnection;
    }

    public void setEncrypt(String encryptType2, String encryptKey2, String randomStr2) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{encryptType2, encryptKey2, randomStr2}, this, changeQuickRedirect, false, 6933, clsArr, Void.TYPE).isSupported) {
            log("setEncrypt encryptKey:" + encryptKey2 + ",encryptType:" + encryptType2);
            this.encryptKey = encryptKey2;
            this.encryptType = encryptType2;
            this.randomStr = randomStr2;
        }
    }

    public BleBusinessClient(String mac2) {
        this.mac = mac2;
        this.callbackMap = new HashMap<>();
    }

    private void D(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6934, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.i("LdsConnectDevice.BleBusinessClient mac=" + this.mac + JustifyTextView.TWO_CHINESE_BLANK + msg, new Object[0]);
        }
    }

    public void send(String callbackKey, TRVCommandBean data) {
        Class[] clsArr = {String.class, TRVCommandBean.class};
        if (!PatchProxy.proxy(new Object[]{callbackKey, data}, this, changeQuickRedirect, false, 6935, clsArr, Void.TYPE).isSupported) {
            D("鉴权（数据写入）  callbackKey=" + callbackKey + ",mac=" + this.mac);
            if (this._compatNotifyAndWriteUuidBean == null || this._compatOTANotifyAndWriteUuidBean == null) {
                D("_compatNotifyAndWriteUuidBean,准备使用兼容方案");
                createCompatWriteCharacterCompat(((BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class)).matchConnectedDeviceGatt(this.mac));
            }
            if (this._reporterOfSigIn == null) {
                String str = this.mac;
                String str2 = this._connectBean.modelId;
                this._reporterOfSigIn = a.d(str, str2, System.currentTimeMillis() + "", "BleSignIn");
            }
            notifyByMac(this.mac).b0(l.d).J(l.d).Y(new b(this, callbackKey, data), new a(this));
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$send$0 */
    public /* synthetic */ void b(String callbackKey, TRVCommandBean data, Boolean aBoolean) {
        Class[] clsArr = {String.class, TRVCommandBean.class, Boolean.class};
        if (!PatchProxy.proxy(new Object[]{callbackKey, data, aBoolean}, this, changeQuickRedirect, false, 6959, clsArr, Void.TYPE).isSupported) {
            if (aBoolean.booleanValue()) {
                String str = this.mac;
                String str2 = this._connectBean.modelId;
                this._reporterOfWriteCommand = a.d(str, str2, System.currentTimeMillis() + "", "BleWrite");
                D("开始准备往设备写入广播数据");
                writeTRVCommand(callbackKey, data);
                return;
            }
            D("注册Notif发生失败，准备通知业务层");
            if (!TextUtils.isEmpty(callbackKey)) {
                JSONObject jsonObject = new JSONObject();
                D("注册Notif发生失败，准备通知业务层 - 11111 ");
                try {
                    jsonObject.put("code", (int) BaseResp.ERR_WAIT_RESPONSE);
                    jsonObject.put("desc", (Object) "ble notify fail");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                c.c().l(new JsBridgeCallbackEvent(callbackKey, jsonObject.toString()));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$send$1 */
    public /* synthetic */ void c(Throwable throwable) {
        if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 6958, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
            log("LdsConnectDevice business client write error:" + throwable.toString());
        }
    }

    private void writeTRVCommand(String callbackKey, TRVCommandBean tRVCommandBean) {
        Class[] clsArr = {String.class, TRVCommandBean.class};
        if (!PatchProxy.proxy(new Object[]{callbackKey, tRVCommandBean}, this, changeQuickRedirect, false, 6936, clsArr, Void.TYPE).isSupported) {
            TRVCommandBean commandBean = tRVCommandBean;
            D("收到写入数据请求： BusinessClient#write:" + commandBean.toString());
            int sequenceNum2 = getSequenceNum();
            this.callbackMap.put(Integer.valueOf(sequenceNum2), callbackKey);
            String commandId = commandBean.commandId;
            String payload = commandBean.payload;
            String mac2 = commandBean.mac;
            String sourceId = commandBean.sourceId;
            String destinationId = commandBean.destinationId;
            String categoryId = commandBean.categoryId;
            byte head001 = (byte) sequenceNum2;
            byte head003 = d.f(sourceId);
            byte head004 = d.f(destinationId);
            byte head005 = d.f(categoryId);
            byte b = head001;
            String str = categoryId;
            String str2 = destinationId;
            String str3 = sourceId;
            io.reactivex.l.k(new c(this, payload, head001, head003, head004, head005, d.f(commandId), mac2)).b0(l.d).W();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$writeTRVCommand$2 */
    public /* synthetic */ void e(String str, byte b, byte b2, byte b3, byte b4, byte b5, String str2, m mVar) {
        int sizeSort;
        Class<String> cls = String.class;
        Object[] objArr = {str, new Byte(b), new Byte(b2), new Byte(b3), new Byte(b4), new Byte(b5), str2, mVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Byte.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6957, new Class[]{cls, cls2, cls2, cls2, cls2, cls2, cls, m.class}, Void.TYPE).isSupported) {
            byte head001 = b;
            byte head006 = b5;
            byte head004 = b3;
            final m emitter = mVar;
            String finalPayload = str;
            byte head003 = b2;
            String finalMac = str2;
            byte head005 = b4;
            byte[] writeData = d.e(finalPayload);
            int size = (writeData.length / 128) + 1;
            byte[][] sortData = new byte[size][];
            for (int s = 0; s < size; s++) {
                if (s + 1 == size) {
                    sizeSort = writeData.length % 128;
                } else {
                    sizeSort = 128;
                }
                byte[] bn = new byte[sizeSort];
                for (int i = s * 128; i < (s * 128) + sizeSort; i++) {
                    bn[i % 128] = writeData[i];
                }
                sortData[s] = bn;
            }
            D("收到写入数据请求：待发送数据包length:" + sortData.length + " 原始长度:" + writeData.length);
            this.successCount = 0;
            this.totalCount = sortData.length;
            int ii = 0;
            while (ii < sortData.length) {
                byte[] wData = com.leedarson.serviceimpl.ble.utils.a.a(head001, d.c(sortData.length, ii + 1), head003, head004, head005, head006, sortData[ii]);
                StringBuilder sb = new StringBuilder();
                String finalPayload2 = finalPayload;
                sb.append("加密前数据: data=");
                sb.append(e.a(wData));
                D(sb.toString());
                byte[] wData2 = encrypt(wData);
                D("收到写入数据请求：(开始写入数据commonWrite)  serviceUuId=" + this._compatNotifyAndWriteUuidBean.serviceUuid + "  SEND_CHARACTERISTIC_UUID=" + this._compatNotifyAndWriteUuidBean.writeCharacterUuid + "  加密后数据data=" + e.a(wData2));
                final BleConnectStepBean _writeDataStep = BleConnectStepBean.create("写入数据");
                this._reporterOfWriteCommand.a(_writeDataStep);
                byte[] writeData2 = writeData;
                _writeDataStep.putRequestData("serviceUuId", this._compatNotifyAndWriteUuidBean.serviceUuid);
                _writeDataStep.putRequestData("characterUuId", this._compatNotifyAndWriteUuidBean.writeCharacterUuid);
                ((BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class)).commonWrite(finalMac, (BluetoothDevice) null, UUID.fromString(this._compatNotifyAndWriteUuidBean.serviceUuid), UUID.fromString(this._compatNotifyAndWriteUuidBean.writeCharacterUuid), this.encryptKey, wData2, (String) null, new com.leedarson.serviceimpl.blec075.callback.d() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void onSimpleWriteSuccess(int current, int total, byte[] bArr, String str, String str2) {
                        Class<String> cls = String.class;
                        Object[] objArr = {new Integer(current), new Integer(total), bArr, str, str2};
                        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                        Class cls2 = Integer.TYPE;
                        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6967, new Class[]{cls2, cls2, byte[].class, cls, cls}, Void.TYPE).isSupported) {
                            BleBusinessClient bleBusinessClient = BleBusinessClient.this;
                            BleBusinessClient.access$2200(bleBusinessClient, "数据写入进度(onSimpleWriteSuccess)   current=" + current + "  total=" + total);
                            _writeDataStep.putResponseData("result", "写入数据成功", 200);
                            BleBusinessClient.this._reporterOfWriteCommand.e();
                            emitter.onComplete();
                        }
                    }

                    public void onSimpleWriteFail(Exception exception, String callbackKey1, String mac1, int gatt) {
                        Class<String> cls = String.class;
                        if (!PatchProxy.proxy(new Object[]{exception, callbackKey1, mac1, new Integer(gatt)}, this, changeQuickRedirect, false, 6968, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                            String tip = "数据写入失败(onSimpleWriteFail)   exception=" + exception.toString();
                            BleBusinessClient.access$2200(BleBusinessClient.this, tip);
                            boolean connect = false;
                            try {
                                connect = com.clj.fastble.a.o().B(mac1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            _writeDataStep.putResponseData("result", tip + "  connectStatue=" + connect, connect ? 1010 : 1009);
                            BleBusinessClient.this._reporterOfWriteCommand.e();
                            JSONObject jsonObject = new JSONObject();
                            if (connect) {
                                try {
                                    jsonObject.put("code", 1010);
                                    jsonObject.put("desc", (Object) "BLE has disconnected!!  exception=" + exception.toString() + "   mac=" + mac1 + "  gatt=" + gatt);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                            } else {
                                jsonObject.put("code", 1009);
                                jsonObject.put("desc", (Object) "BLE write fail exception=" + exception.toString() + "  mac=" + mac1 + "  gatt=" + gatt);
                            }
                            jsonObject.put("gattStatus", gatt);
                            jsonObject.put(NotificationCompat.CATEGORY_MESSAGE, (Object) exception.toString());
                            timber.log.a.g(BleBusinessClient.this.TAG).c("writefail======" + exception.toString(), new Object[0]);
                            if (!TextUtils.isEmpty(callbackKey1)) {
                                c.c().l(new JsBridgeCallbackEvent(callbackKey1, jsonObject.toString()));
                            }
                            emitter.onComplete();
                        }
                    }
                }, false, -1);
                ii++;
                finalPayload = finalPayload2;
                writeData = writeData2;
            }
            emitter.onComplete();
        }
    }

    private void _onMessageToWeb(String str, int online, String desc) {
        Class<String> cls = String.class;
        Object[] objArr = {str, new Integer(online), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6937, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            JSONObject notifyMessageBody = new JSONObject();
            try {
                notifyMessageBody.put("mac", (Object) this._connectBean.mac);
                notifyMessageBody.put("status", online);
                notifyMessageBody.put("desc", (Object) desc);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_BUSINESS, H5ActionName.ACTION_KVS_CON_STATUSCHANGE, notifyMessageBody.toString()));
        }
    }

    private io.reactivex.l<Boolean> notifyByMac(String mac2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{mac2}, this, changeQuickRedirect, false, 6938, new Class[]{String.class}, io.reactivex.l.class);
        if (proxy.isSupported) {
            return (io.reactivex.l) proxy.result;
        }
        D("注册监听Notify开始");
        return io.reactivex.l.k(new e(this, mac2));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$notifyByMac$3 */
    public /* synthetic */ void a(String str, m mVar) {
        if (!PatchProxy.proxy(new Object[]{str, mVar}, this, changeQuickRedirect, false, 6956, new Class[]{String.class, m.class}, Void.TYPE).isSupported) {
            final m emitter = mVar;
            String mac2 = str;
            final BleConnectStepBean stepOfStartListener = BleConnectStepBean.create("Ble-Notify监听");
            stepOfStartListener.putRequestData("serviceUuid", this._compatNotifyAndWriteUuidBean.serviceUuid);
            stepOfStartListener.putRequestData("characterUuid", this._compatNotifyAndWriteUuidBean.notifyCharacterUuid);
            this._reporterOfSigIn.a(stepOfStartListener);
            D("注册监听Notify  hasNotify=" + this.hasNotify);
            if (this.hasNotify) {
                stepOfStartListener.putResponseData("data", "Notify 已经注册成功", 200);
                emitter.onNext(true);
                emitter.onComplete();
                return;
            }
            D("注册监听Notify   (还未注册)  开始发起commonNotify serviceUuId=" + this._compatNotifyAndWriteUuidBean.serviceUuid + "   NotifyUUID=" + this._compatNotifyAndWriteUuidBean.notifyCharacterUuid);
            BleC075Service bleC075Service = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
            AnonymousClass2 r9 = new b() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onSimpleNotifyError(Exception exception, String str, String str2, int i) {
                    Class<String> cls = String.class;
                    if (!PatchProxy.proxy(new Object[]{exception, str, str2, new Integer(i)}, this, changeQuickRedirect, false, 6969, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                        String tip = "注册监听Notify  (发生错误) onSimpleNotifyError exception=" + exception.toString();
                        BleBusinessClient.access$2200(BleBusinessClient.this, tip);
                        emitter.onNext(false);
                        stepOfStartListener.putResponseData("data", tip, 1007);
                        BleBusinessClient.this._reporterOfSigIn.e();
                        emitter.onComplete();
                    }
                }

                public void onSimpleNotifySuccess(String str, String str2) {
                    Class<String> cls = String.class;
                    if (!PatchProxy.proxy(new Object[]{str, str2}, this, changeQuickRedirect, false, 6970, new Class[]{cls, cls}, Void.TYPE).isSupported) {
                        BleBusinessClient.access$2200(BleBusinessClient.this, "注册监听Notify  (Notify监听成功) onSimpleNotifySuccess ");
                        emitter.onNext(true);
                        boolean unused = BleBusinessClient.this.hasNotify = true;
                        stepOfStartListener.putResponseData("data", "注册监听Notify  (Notify监听成功) onSimpleNotifySuccess ", 200);
                        emitter.onComplete();
                    }
                }

                public void onSimpleCharacteristicChanged(byte[] data, String str, String mac1) {
                    Class<String> cls = String.class;
                    Class[] clsArr = {byte[].class, cls, cls};
                    if (!PatchProxy.proxy(new Object[]{data, str, mac1}, this, changeQuickRedirect, false, 6971, clsArr, Void.TYPE).isSupported) {
                        try {
                            BleBusinessClient bleBusinessClient = BleBusinessClient.this;
                            BleBusinessClient.access$2200(bleBusinessClient, "收到设备通知写入数据： / 数据拆包中....原数据=" + h.b(data));
                            if (TextUtils.isEmpty(BleBusinessClient.this.encryptKey)) {
                                BleBusinessClient bleBusinessClient2 = BleBusinessClient.this;
                                BleBusinessClient.access$2200(bleBusinessClient2, "收到设备通知写入数据： （数据解析失败-原因encryptkey=）" + BleBusinessClient.this.encryptKey);
                            } else if (BleBusinessClient.this.randomStr == null) {
                                data = BleBusinessClient.access$3000(BleBusinessClient.this, data);
                                BleBusinessClient bleBusinessClient3 = BleBusinessClient.this;
                                BleBusinessClient.access$2200(bleBusinessClient3, "收到设备通知写入数据： 数据拆包中(Channel1) 解密后数据：" + h.b(data));
                            } else {
                                data = com.leedarson.base.utils.b.c(BleBusinessClient.this.randomStr.getBytes(), b.a.AES256, BleBusinessClient.this.encryptKey, data);
                                BleBusinessClient bleBusinessClient4 = BleBusinessClient.this;
                                BleBusinessClient.access$2200(bleBusinessClient4, "收到设备通知写入数据： 数据拆包中(Channel2)  解密后数据：" + h.b(data));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            BleBusinessClient bleBusinessClient5 = BleBusinessClient.this;
                            BleBusinessClient.access$2200(bleBusinessClient5, "LdsConnectDevice LdsBleNotifyCallback  准备发送登陆信息/准备订阅Notify(onSimpleCharacteristicChanged)/解密失败 e=" + e.toString());
                        }
                        byte[] unused = BleBusinessClient.this._cacheData = data;
                        BleBusinessClient.access$3200(BleBusinessClient.this, data, mac1);
                    }
                }
            };
            CompatNotifyAndWriteInfoBean compatNotifyAndWriteInfoBean = this._compatNotifyAndWriteUuidBean;
            if (compatNotifyAndWriteInfoBean.isNotifyChannel) {
                bleC075Service.commonNotify(mac2, UUID.fromString(compatNotifyAndWriteInfoBean.serviceUuid), UUID.fromString(this._compatNotifyAndWriteUuidBean.notifyCharacterUuid), "", "", r9);
                return;
            }
            bleC075Service.commonIndicate(mac2, UUID.fromString(compatNotifyAndWriteInfoBean.serviceUuid), UUID.fromString(this._compatNotifyAndWriteUuidBean.notifyCharacterUuid), "", r9);
        }
    }

    private void notifyToBusinessLayout(byte[] bArr, String str) {
        Class[] clsArr = {byte[].class, String.class};
        if (!PatchProxy.proxy(new Object[]{bArr, str}, this, changeQuickRedirect, false, 6939, clsArr, Void.TYPE).isSupported) {
            String mac2 = str;
            byte[] data = bArr;
            com.leedarson.serviceimpl.ble.bean.a responseHeader = new com.leedarson.serviceimpl.ble.bean.a(data);
            log("ONONONnotify---" + mac2 + " ######### " + responseHeader + "###### " + h.b(data));
            JSONObject joData = new JSONObject();
            try {
                joData.put("mac", (Object) mac2);
                joData.put("sourceId", (Object) responseHeader.f());
                joData.put("destinationId", (Object) responseHeader.c());
                joData.put("categoryId", (Object) responseHeader.a());
                joData.put("commandId", (Object) responseHeader.b());
                joData.put("status", (Object) responseHeader.g());
                joData.put("payload", (Object) responseHeader.d());
                joData.put("sequenceNum", responseHeader.e());
            } catch (Exception e) {
                e.printStackTrace();
                D("NotifytoBusiness: Json转化异常--->e=" + e.toString());
            }
            if (!dispatchMessageParse(responseHeader, joData)) {
                JSONObject joResponse = p.d(joData);
                try {
                    joResponse.put("mac", (Object) mac2);
                    joResponse.put("sourceId", (Object) responseHeader.f());
                    joResponse.put("destinationId", (Object) responseHeader.c());
                    joResponse.put("categoryId", (Object) responseHeader.a());
                    joResponse.put("commandId", (Object) responseHeader.b());
                    joResponse.put("status", (Object) responseHeader.g());
                    joResponse.put("payload", (Object) responseHeader.d());
                    joResponse.put("sequenceNum", responseHeader.e());
                } catch (JSONException exception) {
                    exception.printStackTrace();
                }
                String writeCallback = this.callbackMap.get(Integer.valueOf(responseHeader.e()));
                if (responseHeader.e() <= 0 || TextUtils.isEmpty(writeCallback)) {
                    c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_BUSINESS, "onMessage", joResponse.toString()));
                    D("广播数据回传  传给业务层(Channel222.onMessage)");
                    return;
                }
                c.c().l(new JsBridgeCallbackEvent(writeCallback, joResponse.toString()));
                c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_BUSINESS, "onMessage", joResponse.toString()));
                D("广播数据回传  传给业务层(Channel111)");
            }
        }
    }

    private boolean dispatchMessageParse(com.leedarson.serviceimpl.ble.bean.a responseHeader, JSONObject jSONObject) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{responseHeader, jSONObject}, this, changeQuickRedirect, false, 6940, new Class[]{com.leedarson.serviceimpl.ble.bean.a.class, JSONObject.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        BleConnectStepBean _stepOfResponseFromDevice = BleConnectStepBean.create("分析设备数据回执");
        this._reporterOfSigIn.a(_stepOfResponseFromDevice);
        if (!"90".equals(responseHeader.b())) {
            return false;
        }
        RetrySendWriteSignFactory retrySendWriteSignFactory = this._reWriteSignFactory;
        if (retrySendWriteSignFactory != null) {
            retrySendWriteSignFactory.onReceiveAuthResponse();
        }
        if ("00".equals(responseHeader.g())) {
            _stepOfResponseFromDevice.putRequestData("data", new Gson().toJson((Object) responseHeader));
            ClientEncryptBean clientEncryptBean = this.tempEncryptBean;
            if (!(clientEncryptBean == null || this.connectCallbackKey == null)) {
                setEncrypt(CommonBleReadConfigBean.ENCRYPT_AES_256_CTR, clientEncryptBean.aesKey, clientEncryptBean.randomChar);
            }
            log("告知前端连接成功====>");
            ClientConnection.OnGattConnectEventChangeHandler onGattConnectEventChangeHandler = this._wrapConnectListerner;
            if (onGattConnectEventChangeHandler != null) {
                onGattConnectEventChangeHandler.onConnected(this.mac, this._connectBean, this.clientConnection);
            }
            _onMessageToWeb(this._connectBean.mac, 1, "设备认证成功");
            _stepOfResponseFromDevice.putResponseData("result", "设备认证成功", 200);
        } else {
            log("告知前端连接加密校验失败...");
            String tip = "设备密码鉴权失败 fail: code=" + responseHeader.g();
            _onMessageToWeb(this._connectBean.mac, 1, "设备认证成功");
            _stepOfResponseFromDevice.putResponseData("result", tip, 407);
            c.c().l(new JsBridgeCallbackEvent(this.connectCallbackKey, p.a(-1, tip).toString()));
            _onMessageToWeb(this._connectBean.mac, 0, tip);
        }
        this._reporterOfSigIn.e();
        return true;
    }

    private byte[] encrypt(byte[] paramBytes) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{paramBytes}, this, changeQuickRedirect, false, 6941, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] writeData = paramBytes;
        if (TextUtils.isEmpty(this.encryptType) || TextUtils.isEmpty(this.encryptKey)) {
            D("尝试加密-（缺乏加密参数）  encryptType=" + this.encryptType + ",encryptKey=" + this.encryptKey + " ,currentClient=" + toString());
            return paramBytes;
        } else if (TextUtils.isEmpty(this.encryptKey)) {
            return writeData;
        } else {
            log("写入前加密:" + this.encryptType + "," + this.encryptKey + ",源数据:" + h.b(paramBytes));
            if (CommonBleReadConfigBean.ENCRYPT_AES_256.equals(this.encryptType)) {
                return h.g(this.encryptKey, paramBytes);
            }
            if (CommonBleReadConfigBean.ENCRYPT_AES_128.equals(this.encryptType)) {
                return com.leedarson.base.utils.b.d(this.encryptKey, paramBytes);
            }
            if (CommonBleReadConfigBean.ENCRYPT_AES_256_CTR.equals(this.encryptType)) {
                String str = this.randomStr;
                if (str != null) {
                    return com.leedarson.base.utils.b.g(str.getBytes(), b.a.AES256, this.encryptKey, paramBytes);
                }
                return com.leedarson.base.utils.b.f(b.a.AES256, this.encryptKey, paramBytes);
            } else if (!"aes128CTR".equals(this.encryptType)) {
                return writeData;
            } else {
                String str2 = this.randomStr;
                if (str2 != null) {
                    return com.leedarson.base.utils.b.g(str2.getBytes(), b.a.AES128, this.encryptKey, paramBytes);
                }
                return com.leedarson.base.utils.b.f(b.a.AES128, this.encryptKey, paramBytes);
            }
        }
    }

    private void log(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 6942, new Class[]{String.class}, Void.TYPE).isSupported) {
            D(msg);
        }
    }

    private byte[] decrypt(byte[] srcData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcData}, this, changeQuickRedirect, false, 6943, new Class[]{byte[].class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] res = srcData;
        if (TextUtils.isEmpty(this.encryptType) || TextUtils.isEmpty(this.encryptKey)) {
            return srcData;
        }
        if (TextUtils.isEmpty(this.encryptKey)) {
            return res;
        }
        log("解密:" + this.encryptType + "," + this.encryptKey);
        if (CommonBleReadConfigBean.ENCRYPT_AES_256.equals(this.encryptType)) {
            return h.e(this.encryptKey, srcData);
        }
        if (CommonBleReadConfigBean.ENCRYPT_AES_128.equals(this.encryptType)) {
            return com.leedarson.base.utils.b.a(this.encryptKey, srcData);
        }
        if (CommonBleReadConfigBean.ENCRYPT_AES_256_CTR.equals(this.encryptType)) {
            String str = this.randomStr;
            if (str != null) {
                return com.leedarson.base.utils.b.c(str.getBytes(), b.a.AES256, this.encryptKey, srcData);
            }
            return com.leedarson.base.utils.b.b(b.a.AES256, this.encryptKey, srcData);
        } else if (!"aes128CTR".equals(this.encryptType)) {
            return res;
        } else {
            String str2 = this.randomStr;
            if (str2 != null) {
                return com.leedarson.base.utils.b.c(str2.getBytes(), b.a.AES128, this.encryptKey, srcData);
            }
            return com.leedarson.base.utils.b.b(b.a.AES128, this.encryptKey, srcData);
        }
    }

    public void reset() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6944, new Class[0], Void.TYPE).isSupported) {
            log("BleBusiness.auto BleBusinessClient ,reset client...");
            this.hasNotify = false;
            this.randomStr = null;
            this.tempEncryptBean = null;
            this.encryptKey = null;
            this.encryptType = null;
            this.CURRENT_CONNECT_STATE = 4;
            ClientConnection clientConnection2 = this.clientConnection;
            if (clientConnection2 != null) {
                clientConnection2.disconnect();
            }
        }
    }

    public void resetHasNotify() {
        this.hasNotify = false;
    }

    public void startOTA(String str, Context context, String str2, TRVOta.TRVOTACallback tRVOTACallback) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, context, str2, tRVOTACallback}, this, changeQuickRedirect, false, 6945, new Class[]{cls, Context.class, cls, TRVOta.TRVOTACallback.class}, Void.TYPE).isSupported) {
            Context context2 = context;
            TRVOta.TRVOTACallback trvotaCallback = tRVOTACallback;
            String newVersion = str;
            String url = str2;
            if (this.trvOta == null) {
                this.trvOta = new TRVOta(this.mac, this, this._compatOTANotifyAndWriteUuidBean, this._connectBean);
            }
            if (this.handlerThread == null) {
                HandlerThread handlerThread2 = new HandlerThread(this.mac);
                this.handlerThread = handlerThread2;
                handlerThread2.start();
            }
            this.trvOta.startUpgrade(context2, url, trvotaCallback, this.handlerThread, newVersion);
        }
    }

    public int getSequenceNum() {
        int sn;
        int sn2 = this.sequenceNum;
        if (sn2 % NeedPermissionEvent.PER_IPC_SPEAK_PERM == 0) {
            sn = 1;
        } else {
            sn = sn2 % NeedPermissionEvent.PER_IPC_SPEAK_PERM;
        }
        this.sequenceNum = sn + 1;
        return sn;
    }

    public void setCurrentState(TRVState state) {
        this.currentState = state;
    }

    public io.reactivex.l<Integer> setMtu() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6946, new Class[0], io.reactivex.l.class);
        return proxy.isSupported ? (io.reactivex.l) proxy.result : io.reactivex.l.k(new d(this));
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$setMtu$4 */
    public /* synthetic */ void d(m emitter) {
        if (!PatchProxy.proxy(new Object[]{emitter}, this, changeQuickRedirect, false, 6955, new Class[]{m.class}, Void.TYPE).isSupported) {
            new MtuConfigRetryStragy().startMtuJob(emitter, ((BleC075ServiceImpl) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class)).r(this.mac));
        }
    }

    public class MtuConfigRetryStragy {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public int MAX_RETRY_TIMES;
        private BleDevice _bleDevice;
        /* access modifiers changed from: private */
        public int _currentRetryTimes;
        /* access modifiers changed from: private */
        public m<Integer> _emitter;

        private MtuConfigRetryStragy() {
            this.MAX_RETRY_TIMES = 3;
            this._currentRetryTimes = 0;
        }

        static /* synthetic */ void access$300(MtuConfigRetryStragy x0) {
            if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6979, new Class[]{MtuConfigRetryStragy.class}, Void.TYPE).isSupported) {
                x0._actualSetMtu();
            }
        }

        public void startMtuJob(m<Integer> emitter, BleDevice bleDevice) {
            Class[] clsArr = {m.class, BleDevice.class};
            if (!PatchProxy.proxy(new Object[]{emitter, bleDevice}, this, changeQuickRedirect, false, 6977, clsArr, Void.TYPE).isSupported) {
                this._emitter = emitter;
                this._bleDevice = bleDevice;
                _actualSetMtu();
            }
        }

        private void _actualSetMtu() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6978, new Class[0], Void.TYPE).isSupported) {
                if (this._currentRetryTimes <= this.MAX_RETRY_TIMES) {
                    com.clj.fastble.a.o().H(this._bleDevice, 264, new com.clj.fastble.callback.d() {
                        public static ChangeQuickRedirect changeQuickRedirect;

                        public void onSetMTUFailure(com.clj.fastble.exception.a exception) {
                            if (!PatchProxy.proxy(new Object[]{exception}, this, changeQuickRedirect, false, 6980, new Class[]{com.clj.fastble.exception.a.class}, Void.TYPE).isSupported) {
                                BleBusinessClient bleBusinessClient = BleBusinessClient.this;
                                BleBusinessClient.access$100(bleBusinessClient, "setMtu failed and retryTimes" + MtuConfigRetryStragy.this._currentRetryTimes + " exception=" + exception.toString() + "");
                                MtuConfigRetryStragy mtuConfigRetryStragy = MtuConfigRetryStragy.this;
                                int unused = mtuConfigRetryStragy._currentRetryTimes = mtuConfigRetryStragy._currentRetryTimes + 1;
                                if (MtuConfigRetryStragy.this._currentRetryTimes <= MtuConfigRetryStragy.this.MAX_RETRY_TIMES) {
                                    MtuConfigRetryStragy.access$300(MtuConfigRetryStragy.this);
                                    return;
                                }
                                MtuConfigRetryStragy.this._emitter.onNext(23);
                                MtuConfigRetryStragy.this._emitter.onComplete();
                            }
                        }

                        public void onMtuChanged(int mtu) {
                            Object[] objArr = {new Integer(mtu)};
                            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6981, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                                BleBusinessClient bleBusinessClient = BleBusinessClient.this;
                                BleBusinessClient.access$100(bleBusinessClient, "setMtu success mtu:" + mtu);
                                MtuConfigRetryStragy.this._emitter.onNext(Integer.valueOf(mtu));
                                MtuConfigRetryStragy.this._emitter.onComplete();
                            }
                        }
                    });
                    return;
                }
                this._emitter.onNext(23);
                this._emitter.onComplete();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002d, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean isOta() {
        /*
            r8 = this;
            monitor-enter(r8)
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch:{ all -> 0x002e }
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect     // Catch:{ all -> 0x002e }
            r4 = 0
            r5 = 6947(0x1b23, float:9.735E-42)
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch:{ all -> 0x002e }
            java.lang.Class r7 = java.lang.Boolean.TYPE     // Catch:{ all -> 0x002e }
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ all -> 0x002e }
            boolean r2 = r1.isSupported     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x0020
            java.lang.Object r0 = r1.result     // Catch:{ all -> 0x002e }
            java.lang.Boolean r0 = (java.lang.Boolean) r0     // Catch:{ all -> 0x002e }
            boolean r0 = r0.booleanValue()     // Catch:{ all -> 0x002e }
            monitor-exit(r8)
            return r0
        L_0x0020:
            r1 = r8
            com.leedarson.serviceimpl.business.TRVOta r2 = r1.trvOta     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x002c
            boolean r2 = r2.isOta()     // Catch:{ all -> 0x002e }
            if (r2 == 0) goto L_0x002c
            r0 = 1
        L_0x002c:
            monitor-exit(r8)
            return r0
        L_0x002e:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.serviceimpl.business.BleBusinessClient.isOta():boolean");
    }

    private void traceElk(String str, String str2, String str3, String str4, String str5, String str6) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, str3, str4, str5, str6}, this, changeQuickRedirect, false, 6948, new Class[]{cls, cls, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            String traceId = str2;
            String mac2 = str6;
            String method = str4;
            String message = str;
            String level = str3;
            String callbackKey = str5;
            String key = callbackKey;
            try {
                key = callbackKey.split("@")[1];
            } catch (Exception e) {
            }
            com.leedarson.log.elk.a o = com.leedarson.log.elk.a.y(this).c(BleC075ServiceImpl.class.getSimpleName()).t("LdsBle").x(traceId).o(level);
            o.p(message + ",【callbackKey=" + key + ",mac=" + mac2 + "】").s(method).a().b();
        }
    }

    public void setWrapConnectListerner(ClientConnection.OnGattConnectEventChangeHandler _wrapConnectListerner2) {
        this._wrapConnectListerner = _wrapConnectListerner2;
    }

    public void connect(BleBusinessConnectBean bleBusinessConnectBean, String str, String str2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{bleBusinessConnectBean, str, str2}, this, changeQuickRedirect, false, 6949, new Class[]{BleBusinessConnectBean.class, cls, cls}, Void.TYPE).isSupported) {
            String callbackKey = str;
            BleBusinessConnectBean connectBean = bleBusinessConnectBean;
            String ref = str2;
            this._connectBean = connectBean;
            BleConnectedDeviceDiscoverUtil.BlutoothConnectedDeviceListBean _tempResult = BleConnectedDeviceDiscoverUtil.a(BaseApplication.b());
            traceElk("BleBusinessClient: connectDevice:" + new Gson().toJson((Object) connectBean) + " 当前手机连接蓝牙情况：" + _tempResult.printResult(), "connectDevice", "info", "BleBusinessClient.connect", callbackKey, connectBean.mac);
            if (this.clientConnection == null || this.CURRENT_CONNECT_STATE != 1) {
                this.clientConnection = new ClientConnection(connectBean.mac, connectBean.modelId, connectBean.autoConnect, this.checkPairingStatus, connectBean.needWakeUp);
                timber.log.a.i("BleBusiness.auto new clientConnection=" + this.clientConnection.toString() + "   CURRENT_CONNECT_STATE=" + this.CURRENT_CONNECT_STATE, new Object[0]);
                this.connectCallbackKey = callbackKey;
                this.CURRENT_CONNECT_STATE = 0;
                final BleBusinessConnectBean bleBusinessConnectBean2 = connectBean;
                final String str3 = callbackKey;
                final String str4 = ref;
                final BleConnectedDeviceDiscoverUtil.BlutoothConnectedDeviceListBean blutoothConnectedDeviceListBean = _tempResult;
                this.clientConnection.setGattConnectListener(new ClientConnection.OnGattConnectListener() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void onDisconnect(String desc) {
                        if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 6972, new Class[]{String.class}, Void.TYPE).isSupported) {
                            a.b g = timber.log.a.g(BleBusinessClient.this.TAG);
                            g.m("BleBusiness.auto Client.onDisconnect :" + desc + ",mac:" + BleBusinessClient.this.mac + "  clientConnection=" + BleBusinessClient.this.clientConnection.toString(), new Object[0]);
                            int unused = BleBusinessClient.this.CURRENT_CONNECT_STATE = 4;
                            BleBusinessClient.this.reset();
                            ClientConnection.OnGattConnectEventChangeHandler onGattConnectEventChangeHandler = BleBusinessClient.this._wrapConnectListerner;
                            if (onGattConnectEventChangeHandler != null) {
                                onGattConnectEventChangeHandler.onDisconnect("设备断开" + desc, BleBusinessClient.this.mac, bleBusinessConnectBean2);
                            }
                            if (BleBusinessClient.this.trvOta != null) {
                                BleBusinessClient.this.trvOta.onBleDisconnect(desc);
                            }
                        }
                    }

                    public void onConnected(ClientConnection refConnection) {
                        if (!PatchProxy.proxy(new Object[]{refConnection}, this, changeQuickRedirect, false, 6973, new Class[]{ClientConnection.class}, Void.TYPE).isSupported) {
                            a.b g = timber.log.a.g(BleBusinessClient.this.TAG);
                            g.m("LdsConnectDevice BleBusiness.auto client.onConnected:  clientConnection=" + refConnection.toString(), new Object[0]);
                            int unused = BleBusinessClient.this.retryConnectTimes = 0;
                            int unused2 = BleBusinessClient.this.CURRENT_CONNECT_STATE = 1;
                            ClientEncryptBean unused3 = BleBusinessClient.this.tempEncryptBean = ClientEncryptBean.generateRandomKey(bleBusinessConnectBean2);
                            ClientConnection unused4 = BleBusinessClient.this.clientConnection = refConnection;
                            BleBusinessClient.access$1200(BleBusinessClient.this, refConnection.getBluetoothGatt());
                            BleBusinessClient bleBusinessClient = BleBusinessClient.this;
                            BleBusinessClient.access$1300(bleBusinessClient, bleBusinessClient.tempEncryptBean);
                        }
                    }

                    public void onReconnected(ClientConnection refConnection) {
                        if (!PatchProxy.proxy(new Object[]{refConnection}, this, changeQuickRedirect, false, 6974, new Class[]{ClientConnection.class}, Void.TYPE).isSupported) {
                            a.b g = timber.log.a.g(BleBusinessClient.this.TAG);
                            g.m("LdsConnectDevice BleBusiness.auto client.onReconnected:  clientConnection=" + refConnection.toString(), new Object[0]);
                            int unused = BleBusinessClient.this.CURRENT_CONNECT_STATE = 1;
                            ClientConnection unused2 = BleBusinessClient.this.clientConnection = refConnection;
                            ClientEncryptBean generateRandomKey = ClientEncryptBean.generateRandomKey(bleBusinessConnectBean2);
                        }
                    }

                    public void onConnectFail(int code, String desc) {
                        if (!PatchProxy.proxy(new Object[]{new Integer(code), desc}, this, changeQuickRedirect, false, 6975, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
                            a.b g = timber.log.a.g(BleBusinessClient.this.TAG);
                            g.c("Ble.BusinessClient onConnectFail :" + desc + ",mac:" + BleBusinessClient.this.mac + "  retryTimes=" + BleBusinessClient.this.retryConnectTimes + "  _forceDisconnectFlag=" + BleBusinessClient.this._forceDisconnectFlag, new Object[0]);
                            BleBusinessClient bleBusinessClient = BleBusinessClient.this;
                            ClientConnection.OnGattConnectEventChangeHandler onGattConnectEventChangeHandler = bleBusinessClient._wrapConnectListerner;
                            if (onGattConnectEventChangeHandler != null) {
                                onGattConnectEventChangeHandler.onConnectFail(code, desc, bleBusinessClient.mac, bleBusinessConnectBean2);
                            }
                            int unused = BleBusinessClient.this.CURRENT_CONNECT_STATE = -1;
                            if (BleBusinessClient.this.retryConnectTimes >= 5 || BleBusinessClient.this._forceDisconnectFlag || code == 411) {
                                BleBusinessClient bleBusinessClient2 = BleBusinessClient.this;
                                BleBusinessClient.access$1500(bleBusinessClient2, "BleBusinessClient: connect fail:" + new Gson().toJson((Object) bleBusinessConnectBean2) + " 当前手机连接蓝牙情况：" + blutoothConnectedDeviceListBean.printResult() + "  desc:" + desc, "connectDevice", "info", "BleBusinessClient.connect", str3, bleBusinessConnectBean2.mac);
                                c c = c.c();
                                String access$1600 = BleBusinessClient.this.connectCallbackKey;
                                StringBuilder sb = new StringBuilder();
                                sb.append("ble connect fail:");
                                sb.append(desc);
                                c.l(new JsBridgeCallbackEvent(access$1600, p.a(BaseResp.ERR_MSG_SEND_FAIL, sb.toString()).toString()));
                                return;
                            }
                            BleBusinessClient bleBusinessClient3 = BleBusinessClient.this;
                            int unused2 = bleBusinessClient3.retryConnectTimes = bleBusinessClient3.retryConnectTimes + 1;
                            BleBusinessClient bleBusinessClient4 = BleBusinessClient.this;
                            BleBusinessConnectBean bleBusinessConnectBean = bleBusinessConnectBean2;
                            String str = str3;
                            bleBusinessClient4.connect(bleBusinessConnectBean, str, str4 + "(连接失败onConnectFail) desc=" + desc + " code=" + code);
                        }
                    }

                    public void onWakeupFail(Pair<Integer, String> pair) {
                        if (!PatchProxy.proxy(new Object[]{pair}, this, changeQuickRedirect, false, 6976, new Class[]{Pair.class}, Void.TYPE).isSupported) {
                            c c = c.c();
                            String access$1600 = BleBusinessClient.this.connectCallbackKey;
                            c.l(new JsBridgeCallbackEvent(access$1600, p.a(BaseResp.ERR_MSG_SEND_FAIL, "ble wakeup fail:" + ((String) pair.second)).toString()));
                        }
                    }
                });
                this._forceDisconnectFlag = false;
                ClientConnection clientConnection2 = this.clientConnection;
                clientConnection2.connect(ref + ".BleBusinessClient.connect");
                return;
            }
            this.connectCallbackKey = callbackKey;
            JSONObject jsonObject3 = new JSONObject();
            try {
                jsonObject3.put("mac", (Object) this.mac);
                jsonObject3.put("status", 1);
                c.c().l(new JsCallH5ByNativeEvent(Constants.SERVICE_BLUETOOTH_NEW, H5ActionName.ACTION_KVS_CON_STATUSCHANGE, jsonObject3.toString()));
                ClientConnection.OnGattConnectEventChangeHandler onGattConnectEventChangeHandler = this._wrapConnectListerner;
                if (onGattConnectEventChangeHandler != null) {
                    onGattConnectEventChangeHandler.onConnected(this.mac, connectBean, this.clientConnection);
                }
                timber.log.a.i("BLE.BleBusinessClient  已经连接成功，直接告诉web上线OK   JsBridgeCallbackEvent--> 通知web设备上线成功 mac=" + this.mac, new Object[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.clientConnection.checkPairingStatus = connectBean.checkPairingStatus;
            notifyToBusinessLayout(this._cacheData, this.mac);
        }
    }

    private void createCompatWriteCharacterCompat(BluetoothGatt _pgatt) {
        if (!PatchProxy.proxy(new Object[]{_pgatt}, this, changeQuickRedirect, false, 6950, new Class[]{BluetoothGatt.class}, Void.TYPE).isSupported) {
            this._compatNotifyAndWriteUuidBean = createCompatNotifyAndWriteInfoBean(_pgatt);
            this._compatOTANotifyAndWriteUuidBean = createOTANotifyAndWriteInfoBean(_pgatt);
        }
    }

    private CompatNotifyAndWriteInfoBean createCompatNotifyAndWriteInfoBean(BluetoothGatt bluetoothGatt) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bluetoothGatt}, this, changeQuickRedirect, false, 6951, new Class[]{BluetoothGatt.class}, CompatNotifyAndWriteInfoBean.class);
        if (proxy.isSupported) {
            return (CompatNotifyAndWriteInfoBean) proxy.result;
        }
        BluetoothGatt _pgatt = bluetoothGatt;
        CompatNotifyAndWriteInfoBean temp = new CompatNotifyAndWriteInfoBean();
        temp.serviceUuid = SEND_SERVICE_UUID;
        temp.notifyCharacterUuid = NOTIFY_CHARACTERISTIC_UUID;
        temp.writeCharacterUuid = SEND_CHARACTERISTIC_UUID;
        if (_pgatt == null) {
            D("动态获取CharacterId时，发现gatt为空，使用默认值");
            return temp;
        }
        List<BluetoothGattService> list = _pgatt.getServices();
        if (list != null) {
            for (BluetoothGattService service : list) {
                Iterator<BluetoothGattCharacteristic> it = com.clj.fastble.a.o().j(service).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    BluetoothGattCharacteristic charItem = it.next();
                    if (charItem.getUuid().toString().equals(NOTIFY_CHARACTERISTIC_UUID)) {
                        temp.serviceUuid = service.getUuid().toString();
                        temp.notifyCharacterUuid = NOTIFY_CHARACTERISTIC_UUID;
                        temp.writeCharacterUuid = SEND_CHARACTERISTIC_UUID;
                        temp.isNotifyChannel = charItem.getProperties() == 16;
                    } else if (charItem.getUuid().toString().equals(WifiBleNotifyCharUUID)) {
                        temp.serviceUuid = service.getUuid().toString();
                        temp.notifyCharacterUuid = WifiBleNotifyCharUUID;
                        temp.writeCharacterUuid = WifiBleWriteCharUUID;
                        temp.isNotifyChannel = charItem.getProperties() == 16;
                    }
                }
            }
        }
        return temp;
    }

    private CompatNotifyAndWriteInfoBean createOTANotifyAndWriteInfoBean(BluetoothGatt bluetoothGatt) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bluetoothGatt}, this, changeQuickRedirect, false, 6952, new Class[]{BluetoothGatt.class}, CompatNotifyAndWriteInfoBean.class);
        if (proxy.isSupported) {
            return (CompatNotifyAndWriteInfoBean) proxy.result;
        }
        BluetoothGatt _pgatt = bluetoothGatt;
        CompatNotifyAndWriteInfoBean temp = new CompatNotifyAndWriteInfoBean();
        temp.serviceUuid = BleOTAServiceUUID;
        temp.notifyCharacterUuid = BleOTANotifyCharUUID;
        temp.writeCharacterUuid = BleOTAWriteCharUUID;
        if (_pgatt == null) {
            D("createOTANotifyAndWriteInfoBean gatt为空，准备使用备用方案");
            return temp;
        }
        List<BluetoothGattService> list = _pgatt.getServices();
        if (list != null) {
            for (BluetoothGattService service : list) {
                Iterator<BluetoothGattCharacteristic> it = com.clj.fastble.a.o().j(service).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    BluetoothGattCharacteristic charItem = it.next();
                    if (charItem.getUuid().toString().equals(BleOTANotifyCharUUID)) {
                        temp.serviceUuid = service.getUuid().toString();
                        temp.notifyCharacterUuid = BleOTANotifyCharUUID;
                        temp.writeCharacterUuid = BleOTAWriteCharUUID;
                        temp.isNotifyChannel = charItem.getProperties() == 16;
                    } else if (charItem.getUuid().toString().equals(WifiBleOTANotifyCharUUID)) {
                        temp.serviceUuid = service.getUuid().toString();
                        temp.notifyCharacterUuid = WifiBleOTANotifyCharUUID;
                        temp.writeCharacterUuid = WifiBleOTAWriteCharUUID;
                        temp.isNotifyChannel = charItem.getProperties() == 16;
                    }
                }
            }
        }
        return temp;
    }

    private void writeSignIn(ClientEncryptBean bean) {
        if (!PatchProxy.proxy(new Object[]{bean}, this, changeQuickRedirect, false, 6953, new Class[]{ClientEncryptBean.class}, Void.TYPE).isSupported) {
            if (bean != null && !TextUtils.isEmpty(bean.randomChar)) {
                String str = this.mac;
                String str2 = this._connectBean.modelId;
                this._reporterOfSigIn = com.leedarson.serviceimpl.blec075.reports.a.d(str, str2, System.currentTimeMillis() + "", "BleSignIn");
                byte[] randomBytes = bean.randomChar.getBytes();
                log("LdsConnectDevice 加密：random str:" + e.a(randomBytes) + ",aesKey:" + bean.aesKey);
                try {
                    byte[] signBytes = com.leedarson.base.utils.b.g(randomBytes, b.a.AES256, bean.aesKey, randomBytes);
                    log("LdsConnectDevice sign:" + e.a(signBytes));
                    byte[] payload = new byte[(randomBytes.length + signBytes.length)];
                    System.arraycopy(randomBytes, 0, payload, 0, randomBytes.length);
                    System.arraycopy(signBytes, 0, payload, randomBytes.length, signBytes.length);
                    log("LdsConnectDevice payload 加密： :" + e.a(payload));
                    TRVCommandBean commandBean = new TRVCommandBean(this.mac);
                    commandBean.categoryId = "01";
                    commandBean.commandId = "10";
                    commandBean.payload = e.a(payload);
                    BleConnectStepBean prepareSignInData = BleConnectStepBean.create("开始准备鉴权数据");
                    D("开始进入鉴权流程");
                    prepareSignInData.putRequestData("data", new Gson().toJson((Object) commandBean));
                    prepareSignInData.putResponseData("statue", "OK", 200);
                    this._reporterOfSigIn.a(prepareSignInData);
                    RetrySendWriteSignFactory retrySendWriteSignFactory = this._reWriteSignFactory;
                    if (retrySendWriteSignFactory != null) {
                        retrySendWriteSignFactory.onReceiveAuthResponse();
                    }
                    RetrySendWriteSignFactory retrySendWriteSignFactory2 = new RetrySendWriteSignFactory();
                    this._reWriteSignFactory = retrySendWriteSignFactory2;
                    retrySendWriteSignFactory2.sendAutoInfo(commandBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    a.b g = timber.log.a.g(this.TAG);
                    g.c("encryptCTRWithIv err:" + e.toString(), new Object[0]);
                }
            }
        }
    }

    public class RetrySendWriteSignFactory {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public TRVCommandBean _commandBean;
        /* access modifiers changed from: private */
        public int _currentRetryTimes;
        Handler _handler;
        /* access modifiers changed from: private */
        public boolean _isContinueWatchFlag;
        /* access modifiers changed from: private */
        public int _retryMaxTimes;
        private RetryRunnable _runnable;

        private RetrySendWriteSignFactory() {
            this._retryMaxTimes = 3;
            this._currentRetryTimes = 0;
            this._isContinueWatchFlag = false;
            this._handler = new Handler(Looper.getMainLooper());
            this._runnable = new RetryRunnable();
        }

        static /* synthetic */ void access$2400(RetrySendWriteSignFactory x0) {
            if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 6985, new Class[]{RetrySendWriteSignFactory.class}, Void.TYPE).isSupported) {
                x0._watchMessagePostStatues();
            }
        }

        public void sendAutoInfo(TRVCommandBean commandBean) {
            if (!PatchProxy.proxy(new Object[]{commandBean}, this, changeQuickRedirect, false, 6982, new Class[]{TRVCommandBean.class}, Void.TYPE).isSupported) {
                BleBusinessClient.this.send("", commandBean);
                this._currentRetryTimes++;
                this._isContinueWatchFlag = true;
                this._commandBean = commandBean;
                _watchMessagePostStatues();
            }
        }

        private void _watchMessagePostStatues() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6983, new Class[0], Void.TYPE).isSupported) {
                this._handler.postDelayed(this._runnable, GroupCtrlAdapter.RETRY_TIMEOUT);
            }
        }

        public class RetryRunnable implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            private RetryRunnable() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6986, new Class[0], Void.TYPE).isSupported) {
                    if (RetrySendWriteSignFactory.this._currentRetryTimes < RetrySendWriteSignFactory.this._retryMaxTimes && RetrySendWriteSignFactory.this._isContinueWatchFlag) {
                        BleBusinessClient bleBusinessClient = BleBusinessClient.this;
                        BleBusinessClient.access$2200(bleBusinessClient, "正在尝试重新发送认证信息......currentRetryTimes=" + RetrySendWriteSignFactory.this._currentRetryTimes);
                        RetrySendWriteSignFactory retrySendWriteSignFactory = RetrySendWriteSignFactory.this;
                        BleBusinessClient.this.send("", retrySendWriteSignFactory._commandBean);
                        RetrySendWriteSignFactory.access$2400(RetrySendWriteSignFactory.this);
                        RetrySendWriteSignFactory retrySendWriteSignFactory2 = RetrySendWriteSignFactory.this;
                        int unused = retrySendWriteSignFactory2._currentRetryTimes = retrySendWriteSignFactory2._currentRetryTimes + 1;
                    } else if (RetrySendWriteSignFactory.this._currentRetryTimes >= RetrySendWriteSignFactory.this._retryMaxTimes) {
                        BleBusinessClient.access$2200(BleBusinessClient.this, "发送认证信息，已经尝试好多次，但是依然未收到对方回执，此次认证失败");
                    } else {
                        BleBusinessClient.access$2200(BleBusinessClient.this, "发送认证信息，固件已回执.....");
                    }
                }
            }
        }

        public void onReceiveAuthResponse() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6984, new Class[0], Void.TYPE).isSupported) {
                this._isContinueWatchFlag = false;
                this._handler.removeCallbacks(this._runnable);
            }
        }
    }

    public void disconnect(String str) {
        if (!PatchProxy.proxy(new Object[]{str}, this, changeQuickRedirect, false, 6954, new Class[]{String.class}, Void.TYPE).isSupported) {
            a.b g = timber.log.a.g(this.TAG);
            g.m("BleBusiness.auto  client.disconnect=" + this.clientConnection, new Object[0]);
            ClientConnection clientConnection2 = this.clientConnection;
            if (clientConnection2 != null) {
                this._forceDisconnectFlag = true;
                this.CURRENT_CONNECT_STATE = 4;
                clientConnection2.disconnect();
            }
            reset();
        }
    }
}
