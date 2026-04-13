package com.telink.ble.mesh.foundation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.le.ScanRecord;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.ParcelUuid;
import android.text.TextUtils;
import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.base.utils.w;
import com.leedarson.bean.Constants;
import com.leedarson.log.elk.a;
import com.leedarson.serviceimpl.reporters.AddDeviceStepBean;
import com.leedarson.serviceimpl.reporters.AutoConnectDeviceStepBean;
import com.leedarson.serviceimpl.reporters.b;
import com.leedarson.serviceimpl.reporters.c;
import com.leedarson.serviceimpl.reporters.e;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.listener.ScanDeviceRuleListener;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.aop.push.PushAutoTrackHelper;
import com.telink.ble.mesh.core.Encipher;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.access.AccessBridge;
import com.telink.ble.mesh.core.access.BindingBearer;
import com.telink.ble.mesh.core.access.BindingController;
import com.telink.ble.mesh.core.access.FastProvisioningController;
import com.telink.ble.mesh.core.access.FirmwareUpdatingController;
import com.telink.ble.mesh.core.access.RemoteProvisioningController;
import com.telink.ble.mesh.core.ble.BleReflexHelper;
import com.telink.ble.mesh.core.ble.BleScanner;
import com.telink.ble.mesh.core.ble.GattConnection;
import com.telink.ble.mesh.core.ble.GattOtaController;
import com.telink.ble.mesh.core.ble.LeScanFilter;
import com.telink.ble.mesh.core.ble.MeshScanRecord;
import com.telink.ble.mesh.core.ble.UUIDInfo;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.StatusMessage;
import com.telink.ble.mesh.core.message.config.ConfigStatus;
import com.telink.ble.mesh.core.message.config.NodeIdentity;
import com.telink.ble.mesh.core.message.config.NodeIdentitySetMessage;
import com.telink.ble.mesh.core.message.config.NodeIdentityStatusMessage;
import com.telink.ble.mesh.core.message.config.NodeResetMessage;
import com.telink.ble.mesh.core.networking.NetworkingBridge;
import com.telink.ble.mesh.core.networking.NetworkingController;
import com.telink.ble.mesh.core.provisioning.ProvisioningBridge;
import com.telink.ble.mesh.core.provisioning.ProvisioningController;
import com.telink.ble.mesh.entity.AdvertisingDevice;
import com.telink.ble.mesh.entity.BindingDevice;
import com.telink.ble.mesh.entity.ConnectionFilter;
import com.telink.ble.mesh.entity.FastProvisioningConfiguration;
import com.telink.ble.mesh.entity.FastProvisioningDevice;
import com.telink.ble.mesh.entity.FirmwareUpdateConfiguration;
import com.telink.ble.mesh.entity.MeshUpdatingDevice;
import com.telink.ble.mesh.entity.MsgExtra;
import com.telink.ble.mesh.entity.NetworkingDeviceWrapper;
import com.telink.ble.mesh.entity.ProvisioningDevice;
import com.telink.ble.mesh.entity.RemoteProvisioningDevice;
import com.telink.ble.mesh.foundation.AutoConnectDevicesManager;
import com.telink.ble.mesh.foundation.event.AutoConnectEvent;
import com.telink.ble.mesh.foundation.event.BindingEvent;
import com.telink.ble.mesh.foundation.event.BluetoothEvent;
import com.telink.ble.mesh.foundation.event.FastProvisioningEvent;
import com.telink.ble.mesh.foundation.event.FirmwareUpdatingEvent;
import com.telink.ble.mesh.foundation.event.GattConnectionEvent;
import com.telink.ble.mesh.foundation.event.GattNotificationEvent;
import com.telink.ble.mesh.foundation.event.GattOtaEvent;
import com.telink.ble.mesh.foundation.event.MeshEvent;
import com.telink.ble.mesh.foundation.event.NetworkInfoUpdateEvent;
import com.telink.ble.mesh.foundation.event.OnlineStatusEvent;
import com.telink.ble.mesh.foundation.event.ProvisioningEvent;
import com.telink.ble.mesh.foundation.event.ReliableMessageProcessEvent;
import com.telink.ble.mesh.foundation.event.RemoteProvisioningEvent;
import com.telink.ble.mesh.foundation.event.ScanEvent;
import com.telink.ble.mesh.foundation.event.StatusNotificationEvent;
import com.telink.ble.mesh.foundation.parameter.AutoConnectParameters;
import com.telink.ble.mesh.foundation.parameter.BindingParameters;
import com.telink.ble.mesh.foundation.parameter.GattOtaParameters;
import com.telink.ble.mesh.foundation.parameter.Parameters;
import com.telink.ble.mesh.foundation.parameter.ProvisioningParameters;
import com.telink.ble.mesh.foundation.parameter.ScanParameters;
import com.telink.ble.mesh.util.MeshLogger;
import com.tencent.bugly.Bugly;
import io.reactivex.l;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.MeshScanLog;
import meshsdk.SIGMesh;
import meshsdk.cache.CacheHandler;
import meshsdk.callback.MeshGlobalCallback;
import meshsdk.ctrl.CmdCtrl;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.MeshNetKey;
import meshsdk.model.NodeInfo;
import meshsdk.util.BleCompat;
import meshsdk.util.LDSMeshUtil;
import meshsdk.util.MeshConstants;
import meshsdk.util.ProcedureCollector;
import org.spongycastle.pqc.math.linearalgebra.ByteUtils;

public final class MeshController implements ProvisioningBridge, NetworkingBridge, AccessBridge {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean A = false;
    /* access modifiers changed from: private */
    public boolean B = false;
    /* access modifiers changed from: private */
    public int C = 0;
    private EventCallback D;
    private int E = 0;
    private boolean F = false;
    private BluetoothDevice G;
    private long H = 0;
    private int I = 0;
    public String J = "";
    public boolean K = false;
    /* access modifiers changed from: private */
    public BluetoothDevice L;
    /* access modifiers changed from: private */
    public int M;
    private b N;
    private String O;
    /* access modifiers changed from: private */
    public long P;
    /* access modifiers changed from: private */
    public long Q;
    private long R = CacheHandler.delayTime;
    private io.reactivex.disposables.b S;
    private io.reactivex.disposables.b T;
    private BroadcastReceiver U = new BroadcastReceiver() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void onReceive(Context context, Intent intent) {
            PushAutoTrackHelper.onBroadcastReceiver(this, context, intent);
            if (!PatchProxy.proxy(new Object[]{context, intent}, this, changeQuickRedirect, false, 13232, new Class[]{Context.class, Intent.class}, Void.TYPE).isSupported) {
                String action = intent.getAction();
                if (action != null && "android.bluetooth.adapter.action.STATE_CHANGED".equals(action)) {
                    MeshController.n(MeshController.this, intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0));
                }
            }
        }
    };
    private boolean V = false;
    private GattConnection.ConnectionCallback W = new GattConnection.ConnectionCallback() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void c(String macAddress) {
            if (!PatchProxy.proxy(new Object[]{macAddress}, this, changeQuickRedirect, false, 13233, new Class[]{String.class}, Void.TYPE).isSupported) {
                if (MeshController.this.o == Mode.MODE_OTA) {
                    long unused = MeshController.this.P = 0;
                    MeshLogNew.ota("ble连接成功:" + macAddress);
                } else if (MeshController.this.o == Mode.MODE_AUTO_CONNECT) {
                    long unused2 = MeshController.this.Q = 0;
                }
                MeshController meshController = MeshController.this;
                meshController.c0(new AutoConnectDeviceStepBean("连接成功:" + macAddress, e.CODE_SUCCESS.getCode()).setMac(macAddress).setStepBleConnected(true), MeshConstants.AC_STATE_DEV_CONNECTED);
                AddDeviceStepBean stepBean = new AddDeviceStepBean("ble连接成功");
                stepBean.setStepBleConnected(true);
                MeshController.this.d0(stepBean);
                if (MeshController.this.o == Mode.MODE_PROVISION && MeshController.this.C >= 8) {
                    a t = a.y(MeshController.this).x(MeshConstants.TRACE_ID_ADD_DEVICES).u(NotificationCompat.CATEGORY_EVENT, "connect_ble_success").u(Constants.ACTION_STATE, FirebaseAnalytics.Param.SUCCESS).u("mac", macAddress).u("connectRetry", Integer.valueOf(MeshController.this.C)).c(MeshController.class.getSimpleName()).o(FirebaseAnalytics.Param.SUCCESS).t("LdsBleMesh");
                    t.p("ble 重试:" + MeshController.this.C + "连接成功").a().b();
                }
            }
        }

        public void g(String macAddress, String reason, int status) {
            Class<String> cls = String.class;
            Object[] objArr = {macAddress, reason, new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13234, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                ProcedureCollector.autoConnectState = MeshConstants.AC_STATE_IDLE;
                MeshController.G(MeshController.this, macAddress, reason, status);
            }
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13235, new Class[0], Void.TYPE).isSupported) {
                MeshController.this.c0(new AutoConnectDeviceStepBean("开始发现服务", e.CODE_SUCCESS.getCode()), MeshConstants.AC_STATE_START_FIND_SERVICE);
                MeshController.this.d0(new AddDeviceStepBean("开始发现服务"));
            }
        }

        public void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13236, new Class[0], Void.TYPE).isSupported) {
                MeshController.this.c0(new AutoConnectDeviceStepBean("发现服务成功", e.CODE_SUCCESS.getCode()), MeshConstants.AC_STATE_SERVICE_FOUND);
                MeshController.this.d0(new AddDeviceStepBean("发现服务成功"));
            }
        }

        public void f(boolean sendRequestMtuSuccess) {
            if (!PatchProxy.proxy(new Object[]{new Byte(sendRequestMtuSuccess ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 13237, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
                MeshController meshController = MeshController.this;
                Object[] objArr = new Object[1];
                String str = "成功";
                objArr[0] = sendRequestMtuSuccess ? str : "失败";
                meshController.d0(new AddDeviceStepBean(String.format("设置mtu,调用%s", objArr)));
                MeshController meshController2 = MeshController.this;
                Object[] objArr2 = new Object[1];
                if (!sendRequestMtuSuccess) {
                    str = "失败";
                }
                objArr2[0] = str;
                meshController2.c0(new AutoConnectDeviceStepBean(String.format("设置mtu,调用%s", objArr2)), "");
            }
        }

        public void e(boolean isTimeout, int status, int mtu, List<BluetoothGattService> list) {
            Object[] objArr = {new Byte(isTimeout ? (byte) 1 : 0), new Integer(status), new Integer(mtu), list};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13238, new Class[]{Boolean.TYPE, cls, cls, List.class}, Void.TYPE).isSupported) {
                String step = String.format(isTimeout ? "设置mtu 3s超时, 协商后的值为：%d" : status == 0 ? "mtu设置成功,协商后的值为:%d" : "mut设置失败,协商后的值为:$d", new Object[]{Integer.valueOf(mtu)});
                MeshController.this.d0(new AddDeviceStepBean(step));
                MeshController.this.c0(new AutoConnectDeviceStepBean(step, e.CODE_SUCCESS.getCode()), "");
                MeshController.this.f.removeCallbacksAndMessages((Object) null);
                MeshController.this.f.postDelayed(new Runnable() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void run() {
                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13241, new Class[0], Void.TYPE).isSupported) {
                            MeshController.this.d0(new AddDeviceStepBean(AddDeviceStepBean.STEP_PROVISION_INIT));
                            MeshController.this.c0(new AutoConnectDeviceStepBean(AutoConnectDeviceStepBean.STEP_PROXY_INIT), "");
                            if (MeshController.this.o == Mode.MODE_PROVISION) {
                                MeshController.this.c.g0();
                            } else {
                                MeshController.this.c.h0();
                                MeshController.this.i.y(MeshController.this.x, MeshController.this.z);
                            }
                            MeshController.this.f.postDelayed(new Runnable() {
                                public static ChangeQuickRedirect changeQuickRedirect;

                                public void run() {
                                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13242, new Class[0], Void.TYPE).isSupported) {
                                        int unused = MeshController.this.C = 0;
                                        MeshController.L(MeshController.this);
                                    }
                                }
                            }, 100);
                        }
                    }
                }, 500);
            }
        }

        public void b(UUID serviceUUID, UUID charUUID, byte[] data) {
            Class[] clsArr = {UUID.class, UUID.class, byte[].class};
            if (!PatchProxy.proxy(new Object[]{serviceUUID, charUUID, data}, this, changeQuickRedirect, false, 13240, clsArr, Void.TYPE).isSupported) {
                if (charUUID.equals(UUIDInfo.k)) {
                    MeshLog.i("online status encrypted data: " + com.leedarson.base.utils.e.b(data, ":") + "  macAddress=" + MeshController.this.J + "\nonline data: " + com.leedarson.base.utils.e.a(data) + "\nonline key: " + com.leedarson.base.utils.e.a(MeshController.this.z));
                    byte[] decrypted = Encipher.f(data, MeshController.this.z);
                    StringBuilder sb = new StringBuilder();
                    sb.append("online dec: ");
                    sb.append(com.leedarson.base.utils.e.a(decrypted));
                    MeshLogger.a(sb.toString());
                    if (decrypted != null) {
                        MeshLog.i("online status decrypted data: " + com.leedarson.base.utils.e.b(decrypted, ":") + "  macAddress=" + MeshController.this.J);
                        MeshController.M(MeshController.this, decrypted);
                        return;
                    }
                    MeshLog.i("online status decrypt err  macAddress=" + MeshController.this.J);
                } else if (charUUID.equals(UUIDInfo.i) || charUUID.equals(UUIDInfo.f)) {
                    MeshController.N(MeshController.this, data);
                } else {
                    MeshController.P(MeshController.this, serviceUUID, charUUID, data);
                }
            }
        }
    };
    private GattOtaController.GattOtaCallback X = new GattOtaController.GattOtaCallback() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void a(int state) {
            if (!PatchProxy.proxy(new Object[]{new Integer(state)}, this, changeQuickRedirect, false, 13243, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                switch (state) {
                    case 0:
                        MeshController.Q(MeshController.this, false, "gatt command fail");
                        return;
                    case 1:
                        MeshController.Q(MeshController.this, true, "ota complete");
                        return;
                    case 2:
                        MeshController meshController = MeshController.this;
                        MeshController.S(meshController, meshController.n.g());
                        return;
                    default:
                        return;
                }
            }
        }
    };
    private Runnable Y = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13244, new Class[0], Void.TYPE).isSupported) {
                if (MeshController.this.o == Mode.MODE_AUTO_CONNECT) {
                    MeshController.b0(MeshController.this, "mesh 自动上线 restartScanTask timeout, 重新startScan");
                    MeshController.p(MeshController.this);
                }
            }
        }
    };
    private boolean Z = false;
    private final String a = "MeshController";
    private Runnable a0 = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13245, new Class[0], Void.TYPE).isSupported) {
                Collections.sort(AutoConnectDevicesManager.d().d);
                AutoConnectDevicesManager.AutoConnectDevice autoConnectDevice = AutoConnectDevicesManager.d().c();
                if (autoConnectDevice != null) {
                    com.leedarson.serviceimpl.elkstrays.b.b("autoConnect埋点 获取信号最强的设备:" + autoConnectDevice.toString() + ",尝试连接:" + autoConnectDevice.b().getAddress());
                    MeshController.O(MeshController.this, autoConnectDevice.b(), autoConnectDevice.e(), "onlineConnectRunnableTask");
                    return;
                }
                MeshLog.e("出问题了，自动上线轮训没获取到设备?");
            }
        }
    };
    private BleScanner b;
    private Runnable b0 = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13246, new Class[0], Void.TYPE).isSupported) {
                MeshController.this.i2("执行上线扫描到设备后停止扫描");
            }
        }
    };
    /* access modifiers changed from: private */
    public GattConnection c;
    private BleScanner.ScannerCallback c0 = new BleScanner.ScannerCallback() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            Object[] objArr = {device, new Integer(rssi), scanRecord};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13247, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
                MeshController.T(MeshController.this, device, rssi, scanRecord);
            }
        }

        public void onScanFail(int errorCode) {
            if (!PatchProxy.proxy(new Object[]{new Integer(errorCode)}, this, changeQuickRedirect, false, 13248, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                if (errorCode != 6) {
                    MeshController meshController = MeshController.this;
                    MeshController.U(meshController, false, "onScanFail--->errorCode=" + errorCode);
                    com.leedarson.serviceimpl.elkstrays.b.a("发起扫描失败:  errorCode=" + errorCode);
                }
                StringBuilder sb = new StringBuilder();
                e eVar = e.CODE_BLE_SCAN_SYSTEM_FAIL;
                sb.append(eVar.getDesc());
                sb.append(",蓝牙");
                sb.append(BluetoothAdapter.getDefaultAdapter().isEnabled() ? "开启" : "关闭");
                c.c(new AutoConnectDeviceStepBean(sb.toString(), eVar.getCode()));
                MeshController.V(MeshController.this, errorCode);
                MeshLog.e("mesh controller onScanFail:" + errorCode + "  macAddress=" + MeshController.this.J);
                if (errorCode == 2) {
                    com.leedarson.serviceimpl.elkstrays.b.a("扫描失败(重复触发扫描) errorCode=" + errorCode);
                    a t = a.y(MeshController.this).c(MeshController.class.getName()).t("LdsBleMesh");
                    t.p("mesh controller onScanFail:" + errorCode).a().b();
                    BleReflexHelper.e();
                }
            }
        }
    };
    private Context d;
    private HandlerThread e;
    /* access modifiers changed from: private */
    public Handler f;
    private Handler g;
    private ProvisioningController h;
    /* access modifiers changed from: private */
    public NetworkingController i;
    /* access modifiers changed from: private */
    public BindingController j;
    private FirmwareUpdatingController k;
    /* access modifiers changed from: private */
    public RemoteProvisioningController l;
    private FastProvisioningController m;
    /* access modifiers changed from: private */
    public GattOtaController n;
    /* access modifiers changed from: private */
    public Mode o = Mode.MODE_IDLE;
    /* access modifiers changed from: private */
    public Parameters p;
    /* access modifiers changed from: private */
    public BleC075Service q;
    private MeshGlobalCallback r;
    private boolean s = false;
    private final Object t = new Object();
    private boolean u = false;
    private Set<AdvertisingDevice> v = new LinkedHashSet();
    /* access modifiers changed from: private */
    public MeshConfiguration w;
    /* access modifiers changed from: private */
    public byte[] x = null;
    private byte[] y = null;
    /* access modifiers changed from: private */
    public byte[] z = null;

    public interface EventCallback {
        void onEventPrepared(Event<String> event);
    }

    public enum Mode {
        MODE_IDLE,
        MODE_SCAN,
        MODE_PROVISION,
        MODE_AUTO_CONNECT,
        MODE_OTA,
        MODE_BIND,
        MODE_REMOTE_PROVISION,
        MODE_REMOTE_BIND,
        MODE_FAST_PROVISION,
        MODE_MESH_OTA,
        MODE_GATT_CONNECTION;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public interface ValidateProxyAdvParseListener {
        void a(int i, int i2);
    }

    static /* synthetic */ void C(MeshController x0, String x1, RemoteProvisioningDevice x2, String x3) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 13220, new Class[]{MeshController.class, cls, RemoteProvisioningDevice.class, cls}, Void.TYPE).isSupported) {
            x0.D1(x1, x2, x3);
        }
    }

    static /* synthetic */ void G(MeshController x0, String x1, String x2, int x3) {
        Class<String> cls = String.class;
        Object[] objArr = {x0, x1, x2, new Integer(x3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13221, new Class[]{MeshController.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.k1(x1, x2, x3);
        }
    }

    static /* synthetic */ void L(MeshController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13222, new Class[]{MeshController.class}, Void.TYPE).isSupported) {
            x0.d1();
        }
    }

    static /* synthetic */ void M(MeshController x0, byte[] x1) {
        Class[] clsArr = {MeshController.class, byte[].class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 13223, clsArr, Void.TYPE).isSupported) {
            x0.t1(x1);
        }
    }

    static /* synthetic */ void N(MeshController x0, byte[] x1) {
        Class[] clsArr = {MeshController.class, byte[].class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 13224, clsArr, Void.TYPE).isSupported) {
            x0.l1(x1);
        }
    }

    static /* synthetic */ void O(MeshController x0, BluetoothDevice x1, int x2, String x3) {
        Object[] objArr = {x0, x1, new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13213, new Class[]{MeshController.class, BluetoothDevice.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.I1(x1, x2, x3);
        }
    }

    static /* synthetic */ void P(MeshController x0, UUID x1, UUID x2, byte[] x3) {
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2, x3}, (Object) null, changeQuickRedirect, true, 13225, new Class[]{MeshController.class, UUID.class, UUID.class, byte[].class}, Void.TYPE).isSupported) {
            x0.G1(x1, x2, x3);
        }
    }

    static /* synthetic */ void Q(MeshController x0, boolean x1, String x2) {
        Object[] objArr = {x0, new Byte(x1 ? (byte) 1 : 0), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13226, new Class[]{MeshController.class, Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.u1(x1, x2);
        }
    }

    static /* synthetic */ void S(MeshController x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 13227, new Class[]{MeshController.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.w1(x1);
        }
    }

    static /* synthetic */ void T(MeshController x0, BluetoothDevice x1, int x2, byte[] x3) {
        Object[] objArr = {x0, x1, new Integer(x2), x3};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13228, new Class[]{MeshController.class, BluetoothDevice.class, Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            x0.F1(x1, x2, x3);
        }
    }

    static /* synthetic */ void U(MeshController x0, boolean x1, String x2) {
        Object[] objArr = {x0, new Byte(x1 ? (byte) 1 : 0), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13229, new Class[]{MeshController.class, Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            x0.l2(x1, x2);
        }
    }

    static /* synthetic */ void V(MeshController x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 13230, new Class[]{MeshController.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.E1(x1);
        }
    }

    static /* synthetic */ void W(MeshController x0, String x1) {
        Class[] clsArr = {MeshController.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 13214, clsArr, Void.TYPE).isSupported) {
            x0.V0(x1);
        }
    }

    static /* synthetic */ void b0(MeshController x0, String x1) {
        Class[] clsArr = {MeshController.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 13215, clsArr, Void.TYPE).isSupported) {
            x0.T0(x1);
        }
    }

    static /* synthetic */ void n(MeshController x0, int x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Integer(x1)}, (Object) null, changeQuickRedirect, true, 13212, new Class[]{MeshController.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.c1(x1);
        }
    }

    static /* synthetic */ void p(MeshController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 13216, new Class[]{MeshController.class}, Void.TYPE).isSupported) {
            x0.f2();
        }
    }

    static /* synthetic */ void s(MeshController x0, String x1, int x2) {
        Object[] objArr = {x0, x1, new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13217, new Class[]{MeshController.class, String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.i0(x1, x2);
        }
    }

    static /* synthetic */ void t(MeshController x0, String x1, String x2, int x3) {
        Class<String> cls = String.class;
        Object[] objArr = {x0, x1, x2, new Integer(x3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 13218, new Class[]{MeshController.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            x0.e1(x1, x2, x3);
        }
    }

    static /* synthetic */ int w(MeshController x0) {
        int i2 = x0.C;
        x0.C = i2 + 1;
        return i2;
    }

    static /* synthetic */ void x(MeshController x0, ProvisioningDevice x1) {
        Class[] clsArr = {MeshController.class, ProvisioningDevice.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 13219, clsArr, Void.TYPE).isSupported) {
            x0.M1(x1);
        }
    }

    public MeshController(String name) {
        this.O = name;
    }

    /* access modifiers changed from: package-private */
    public void Z1(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 13085, new Class[]{Context.class}, Void.TYPE).isSupported) {
            MeshLog.e("SUFUN.MeshController====>" + hashCode());
            this.q = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
            this.r = (MeshGlobalCallback) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class);
            HandlerThread handlerThread = new HandlerThread("Mesh-Controller" + this.J);
            this.e = handlerThread;
            handlerThread.start();
            this.f = new Handler(this.e.getLooper());
            this.g = new Handler(Looper.getMainLooper());
            this.d = context.getApplicationContext();
            H0(this.e);
            D0(this.e);
            E0(this.e);
            G0(this.e);
            F0(this.e);
            C0(this.e);
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            this.d.registerReceiver(this.U, filter);
        }
    }

    /* access modifiers changed from: package-private */
    public void h2(String fromBz) {
        if (!PatchProxy.proxy(new Object[]{fromBz}, this, changeQuickRedirect, false, 13086, new Class[]{String.class}, Void.TYPE).isSupported) {
            this.o = Mode.MODE_IDLE;
            R0("actionMode赋值为(stop):" + this.o);
            this.E = 0;
            this.A = false;
            i2(fromBz);
            Set<AdvertisingDevice> set = this.v;
            if (set != null) {
                set.clear();
            }
            GattConnection gattConnection = this.c;
            if (gattConnection != null) {
                gattConnection.z("Control#stop,释放蓝牙gatt资源");
                this.c.r0((GattConnection.ConnectionCallback) null);
                this.c = null;
            }
            ProvisioningController provisioningController = this.h;
            if (provisioningController != null) {
                provisioningController.d();
                this.h = null;
            }
            NetworkingController networkingController = this.i;
            if (networkingController != null) {
                networkingController.z();
                this.i = null;
            }
            BindingController bindingController = this.j;
            if (bindingController != null) {
                bindingController.g();
                this.j = null;
            }
            FirmwareUpdatingController firmwareUpdatingController = this.k;
            if (firmwareUpdatingController != null) {
                firmwareUpdatingController.f();
                this.k = null;
            }
            Handler handler = this.f;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
                this.f = null;
            }
            HandlerThread handlerThread = this.e;
            if (handlerThread != null) {
                handlerThread.quitSafely();
                this.e = null;
            }
            if (!this.V) {
                this.d.unregisterReceiver(this.U);
                this.V = true;
            }
        }
    }

    public Mode s0() {
        return this.o;
    }

    /* access modifiers changed from: package-private */
    public void g0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13087, new Class[0], Void.TYPE).isSupported) {
            c1(BluetoothAdapter.getDefaultAdapter().getState());
        }
    }

    private void c1(int state) {
        String stateInfo;
        Object[] objArr = {new Integer(state)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13088, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            switch (state) {
                case 10:
                    stateInfo = "bluetooth disabled";
                    GattConnection gattConnection = this.c;
                    if (gattConnection != null) {
                        gattConnection.z("蓝牙未打开");
                        break;
                    }
                    break;
                case 11:
                    stateInfo = "bluetooth turning on";
                    break;
                case 12:
                    stateInfo = "bluetooth enabled";
                    break;
                case 13:
                    stateInfo = "bluetooth turning off";
                    break;
                default:
                    stateInfo = "unknown";
                    break;
            }
            b1(state, stateInfo);
        }
    }

    /* access modifiers changed from: package-private */
    public void Y1(MeshConfiguration configuration) {
        if (!PatchProxy.proxy(new Object[]{configuration}, this, changeQuickRedirect, false, 13089, new Class[]{MeshConfiguration.class}, Void.TYPE).isSupported) {
            this.w = configuration;
            this.x = Encipher.o(configuration.b);
            MeshLog.e("setupMeshNetwork#networkId:" + ByteUtils.e(this.x) + ",configNetworkKey:" + ByteUtils.e(configuration.b) + "  macAddress=" + this.J);
            this.y = Encipher.i(configuration.b);
            this.z = Encipher.g(configuration.b);
            this.i.O0(configuration);
        }
    }

    /* access modifiers changed from: package-private */
    public void N1(int meshAddress) {
        Object[] objArr = {new Integer(meshAddress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13090, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.w.g.remove(meshAddress);
            this.i.t0(meshAddress);
            if (this.o == Mode.MODE_AUTO_CONNECT) {
                n2();
            }
        }
    }

    public int w0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13091, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.i.P();
    }

    public void X1(int seq) {
        Object[] objArr = {new Integer(seq)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13092, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.i.N0(seq);
        }
    }

    public byte[] u0() {
        return this.x;
    }

    private void H0(HandlerThread handlerThread) {
        if (!PatchProxy.proxy(new Object[]{handlerThread}, this, changeQuickRedirect, false, 13093, new Class[]{HandlerThread.class}, Void.TYPE).isSupported) {
            BleScanner bleScanner = new BleScanner(handlerThread, this);
            this.b = bleScanner;
            bleScanner.d(this.c0);
        }
    }

    private void D0(HandlerThread handlerThread) {
        if (!PatchProxy.proxy(new Object[]{handlerThread}, this, changeQuickRedirect, false, 13094, new Class[]{HandlerThread.class}, Void.TYPE).isSupported) {
            GattConnection gattConnection = new GattConnection(this.d, handlerThread, this);
            this.c = gattConnection;
            gattConnection.B = this.J;
            gattConnection.r0(this.W);
        }
    }

    private void E0(HandlerThread handlerThread) {
        if (!PatchProxy.proxy(new Object[]{handlerThread}, this, changeQuickRedirect, false, 13095, new Class[]{HandlerThread.class}, Void.TYPE).isSupported) {
            GattOtaController gattOtaController = new GattOtaController(this.c, handlerThread);
            this.n = gattOtaController;
            gattOtaController.w(this.X);
        }
    }

    private void G0(HandlerThread handlerThread) {
        if (!PatchProxy.proxy(new Object[]{handlerThread}, this, changeQuickRedirect, false, 13096, new Class[]{HandlerThread.class}, Void.TYPE).isSupported) {
            ProvisioningController provisioningController = new ProvisioningController(handlerThread);
            this.h = provisioningController;
            provisioningController.v = this.J;
            provisioningController.G(this);
        }
    }

    private void F0(HandlerThread handlerThread) {
        if (!PatchProxy.proxy(new Object[]{handlerThread}, this, changeQuickRedirect, false, 13097, new Class[]{HandlerThread.class}, Void.TYPE).isSupported) {
            NetworkingController networkingController = new NetworkingController(handlerThread);
            this.i = networkingController;
            networkingController.N = this.J;
            networkingController.M0(this);
        }
    }

    private void C0(HandlerThread handlerThread) {
        if (!PatchProxy.proxy(new Object[]{handlerThread}, this, changeQuickRedirect, false, 13098, new Class[]{HandlerThread.class}, Void.TYPE).isSupported) {
            BindingController bindingController = new BindingController(handlerThread);
            this.j = bindingController;
            bindingController.x(this.N);
            BindingController bindingController2 = this.j;
            bindingController2.j = this.J;
            bindingController2.v(this);
            FirmwareUpdatingController firmwareUpdatingController = new FirmwareUpdatingController(handlerThread);
            this.k = firmwareUpdatingController;
            firmwareUpdatingController.u = this.J;
            firmwareUpdatingController.E(this);
            RemoteProvisioningController remoteProvisioningController = new RemoteProvisioningController(handlerThread);
            this.l = remoteProvisioningController;
            remoteProvisioningController.n = this.J;
            remoteProvisioningController.u(this);
            FastProvisioningController fastProvisioningController = new FastProvisioningController(handlerThread);
            this.m = fastProvisioningController;
            fastProvisioningController.k = this.J;
            fastProvisioningController.y(this);
        }
    }

    /* access modifiers changed from: package-private */
    public int r0() {
        return this.E;
    }

    public void A0(boolean disconnect, String reason) {
        if (!PatchProxy.proxy(new Object[]{new Byte(disconnect ? (byte) 1 : 0), reason}, this, changeQuickRedirect, false, 13099, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            B0(disconnect, reason, false);
        }
    }

    public void B0(boolean disconnect, String reason, boolean isDisconnectMainPoint) {
        Object[] objArr = {new Byte(disconnect ? (byte) 1 : 0), reason, new Byte(isDisconnectMainPoint ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13100, new Class[]{cls, String.class, cls}, Void.TYPE).isSupported) {
            R0("---idle--- " + disconnect);
            this.f.removeCallbacksAndMessages((Object) null);
            m2(Mode.MODE_IDLE);
            if (disconnect) {
                this.c.A(reason, isDisconnectMainPoint);
            }
            i2("idle reason:" + reason);
        }
    }

    public String p0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13103, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (this.c.N()) {
            return this.c.J();
        }
        return null;
    }

    public void i2(String fromBz) {
        if (!PatchProxy.proxy(new Object[]{fromBz}, this, changeQuickRedirect, false, 13104, new Class[]{String.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.elkstrays.b.a(" stopScan fromBz:" + fromBz + ",isOnlineTaskHasRun set 为:" + false);
            l2(false, "stopScan");
            this.Z = false;
            this.b.g();
        }
    }

    public void g2(ScanParameters scanParameters, boolean validateActionMode) {
        if (!PatchProxy.proxy(new Object[]{scanParameters, new Byte(validateActionMode ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 13106, new Class[]{ScanParameters.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            if (!validateActionMode || m2(Mode.MODE_SCAN)) {
                this.f.removeCallbacksAndMessages((Object) null);
                this.o = Mode.MODE_SCAN;
                T0("startScan");
                R0("actionMode赋值为(startScan):" + this.o);
                this.v.clear();
                this.p = scanParameters;
                MeshLog.i("####### startScan.scanParameters===>");
                O1();
                f2();
            }
        }
    }

    private void f2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13107, new Class[0], Void.TYPE).isSupported) {
            if (BleCompat.checkNeededPermission(this.d)) {
                if (this.o == Mode.MODE_AUTO_CONNECT) {
                    if (!SIGMesh.getInstance().hasConnected()) {
                        this.r.onCanclePreReportDeviceStatusChanged();
                    }
                    AutoConnectDevicesManager.d().b();
                    this.T = m0(10, new Runnable() {
                        public static ChangeQuickRedirect changeQuickRedirect;

                        public void run() {
                            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13250, new Class[0], Void.TYPE).isSupported) {
                                if (MeshController.this.L != null) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("10s未扫描到设备,继续尝试使用上次连接的设备连接:");
                                    sb.append(MeshController.this.L != null ? MeshController.this.L.getAddress() : "");
                                    sb.append(",enable:");
                                    sb.append(BluetoothAdapter.getDefaultAdapter().isEnabled());
                                    c.f(sb.toString());
                                    MeshController meshController = MeshController.this;
                                    MeshController.O(meshController, meshController.L, MeshController.this.M, "10s未扫描到设备, 继续尝试使用上次连接的设备连接");
                                }
                            }
                        }
                    });
                }
                R0("start scan: " + this.o + "  macAddress=" + this.J);
                ProcedureCollector.autoConnectState = "startScan";
                l2(true, "startScan1111");
                MeshLogNew.v("开始扫描设备startScan");
                T0("startScanV2");
                this.b.e((LeScanFilter) this.p.b("com.telink.ble.com.telink.ble.mesh.light.SCAN_FILTERS"));
                return;
            }
            R0("start scan fail ,no permission:   macAddress=" + this.J);
            a t2 = a.y(this).c(MeshController.class.getName()).t("LdsBleMesh");
            t2.p("start scan fail ,no permission:   macAddress=" + this.J).e("startScan").a().b();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean d2(ProvisioningParameters parameters) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parameters}, this, changeQuickRedirect, false, 13108, new Class[]{ProvisioningParameters.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        MeshLog.i("SUFUN.MeshController.start provision  hashCode=" + hashCode() + "  macAddress=" + this.J);
        Mode mode = Mode.MODE_PROVISION;
        if (!m2(mode)) {
            return false;
        }
        this.f.removeCallbacksAndMessages((Object) null);
        this.o = mode;
        R0("actionMode赋值为(startProvisioning):" + this.o);
        this.h.G(this);
        this.p = parameters;
        O1();
        ProvisioningDevice provisioningDevice = (ProvisioningDevice) parameters.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_PROVISIONING_TARGET");
        M1(provisioningDevice);
        I1(provisioningDevice.c(), provisioningDevice.h(), "startProvisioning 断开");
        return true;
    }

    private void I1(BluetoothDevice device, int rssi, String bz) {
        Object[] objArr = {device, new Integer(rssi), bz};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13109, new Class[]{BluetoothDevice.class, Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            this.L = device;
            this.M = rssi;
            if (this.o == Mode.MODE_OTA && this.P == 0) {
                this.P = System.currentTimeMillis();
            }
            if (this.o == Mode.MODE_AUTO_CONNECT && this.Q == 0) {
                this.Q = System.currentTimeMillis();
            }
            if (this.c.z(bz)) {
                this.s = true;
                d0(new AddDeviceStepBean("等待已连接上的bleGatter断开"));
                c0(new AutoConnectDeviceStepBean("等待已连接上的bleGatter断开"), MeshConstants.AC_STATE_DEV_CONNECTING);
            } else if ("onlineConnectRunnableTask".equals(bz)) {
                l0(device, rssi);
            } else {
                k0(device, rssi);
            }
        }
    }

    private io.reactivex.disposables.b m0(long timeMillseconds, final Runnable runnable) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Long(timeMillseconds), runnable}, this, changeQuickRedirect, false, 13110, new Class[]{Long.TYPE, Runnable.class}, io.reactivex.disposables.b.class);
        return proxy.isSupported ? (io.reactivex.disposables.b) proxy.result : l.F(1).l(timeMillseconds, TimeUnit.MILLISECONDS).X(new io.reactivex.functions.e<Integer>() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public /* bridge */ /* synthetic */ void accept(Object obj) {
                if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 13252, new Class[]{Object.class}, Void.TYPE).isSupported) {
                    a((Integer) obj);
                }
            }

            public void a(Integer num) {
                if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 13251, new Class[]{Integer.class}, Void.TYPE).isSupported) {
                    runnable.run();
                }
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x001d, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void n0(java.lang.String r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r2 = 0
            r1[r2] = r9
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13111(0x3337, float:1.8372E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x001d
            return
        L_0x001d:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.S
            if (r1 == 0) goto L_0x0035
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0035
            com.leedarson.serviceimpl.elkstrays.b.a(r9)
            io.reactivex.disposables.b r1 = r0.S
            r1.dispose()
            java.lang.Runnable r1 = r0.b0
            r1.run()
        L_0x0035:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.foundation.MeshController.n0(java.lang.String):void");
    }

    public void l0(BluetoothDevice device, int rssi) {
        if (!PatchProxy.proxy(new Object[]{device, new Integer(rssi)}, this, changeQuickRedirect, false, 13112, new Class[]{BluetoothDevice.class, Integer.TYPE}, Void.TYPE).isSupported) {
            this.S = m0(this.R, this.b0);
            R0("开始连接:" + device.getAddress());
            this.c.x(device, rssi);
        }
    }

    public void k0(BluetoothDevice device, int rssi) {
        if (!PatchProxy.proxy(new Object[]{device, new Integer(rssi)}, this, changeQuickRedirect, false, 13113, new Class[]{BluetoothDevice.class, Integer.TYPE}, Void.TYPE).isSupported) {
            i2("停止扫描start to connect:" + device.getAddress());
            MeshLogNew.v("### ---- 开始连接:" + device.getAddress() + ",thread:" + Thread.currentThread().getName());
            this.c.x(device, rssi);
        }
    }

    private void M1(ProvisioningDevice provisioningDevice) {
        if (!PatchProxy.proxy(new Object[]{provisioningDevice}, this, changeQuickRedirect, false, 13114, new Class[]{ProvisioningDevice.class}, Void.TYPE).isSupported) {
            provisioningDevice.p(this.w.d);
            provisioningDevice.s(this.w.b);
            provisioningDevice.t(this.w.a);
            provisioningDevice.q((byte) 0);
            provisioningDevice.r((byte) 0);
        }
    }

    /* access modifiers changed from: package-private */
    public void a2(BindingParameters bindingParameters) {
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[]{bindingParameters}, this, changeQuickRedirect, false, 13115, new Class[]{BindingParameters.class}, Void.TYPE).isSupported) {
            Mode mode = Mode.MODE_BIND;
            if (!m2(mode)) {
                MeshLog.i("SUFUN. binding currently  macAddress=" + this.J);
                return;
            }
            d0(new AddDeviceStepBean(AddDeviceStepBean.STEP_BIND_START));
            this.f.removeCallbacksAndMessages((Object) null);
            BindingDevice bindingDevice = (BindingDevice) bindingParameters.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_BINDING_TARGET");
            if (!MeshUtils.r(bindingDevice.d())) {
                e eVar = e.CODE_BIND_START_FAIL;
                d0(new AddDeviceStepBean(eVar.getDesc(), eVar.getCode()));
                MeshLog.i("SUFUN.start bind fail: node address invalid  macAddress=" + this.J);
                Z0("node address invalid", 7);
                return;
            }
            this.p = bindingParameters;
            this.o = mode;
            if (this == MeshService.k().l()) {
                com.leedarson.serviceimpl.elkstrays.b.a("主控制器设置mode为MODE_BIND");
            } else {
                R0("actionMode赋值为(startBinding):" + this.o);
            }
            this.G = null;
            O1();
            int bindingTarget = bindingDevice.d();
            BindingBearer bindingBearer = bindingDevice.b();
            StringBuilder sb = new StringBuilder();
            sb.append("SUFUN.StartBinding  isProxyNodeConnected=");
            sb.append(this.c.O());
            sb.append(" directDeviceAddress=");
            sb.append(this.E);
            sb.append("  bindingBearer == BindingBearer.Any：");
            BindingBearer bindingBearer2 = BindingBearer.Any;
            if (bindingBearer == bindingBearer2) {
                z2 = true;
            }
            sb.append(z2);
            sb.append("\n  bindingBearer =");
            sb.append(bindingBearer);
            sb.append("  BindingBearer.Flex  macAddress=");
            sb.append(this.J);
            MeshLog.i(sb.toString());
            if (!this.c.O() || !(this.E == bindingTarget || bindingBearer == bindingBearer2 || bindingBearer == BindingBearer.Flex)) {
                MeshLog.i("SUFUN.startBinding have not connect   macAddress=" + this.J);
                this.H = System.currentTimeMillis();
                if (this.c.z("startBinding 断开")) {
                    this.s = true;
                    return;
                }
                R0("startBinding startScan");
                f2();
                return;
            }
            MeshLog.i("invoke onConnectSuccess from startBinding");
            d1();
        }
    }

    /* access modifiers changed from: package-private */
    public void e0(AutoConnectParameters parameters) {
        if (!PatchProxy.proxy(new Object[]{parameters}, this, changeQuickRedirect, false, 13116, new Class[]{AutoConnectParameters.class}, Void.TYPE).isSupported) {
            if (!MeshDataManager.flagNetConfingAdddevices || !parameters.b) {
                Mode mode = Mode.MODE_AUTO_CONNECT;
                if (!m2(mode)) {
                    com.leedarson.serviceimpl.elkstrays.b.a("当前已经在auto连接组网中..，不需要重复的调用 macAddress=" + this.J);
                    return;
                }
                this.f.removeCallbacksAndMessages((Object) null);
                this.p = parameters;
                this.o = mode;
                R0("autoConnect actionMode赋值为:" + this.o);
                if (n2()) {
                    if (this.c.O()) {
                        com.leedarson.serviceimpl.elkstrays.b.a("主节点已连接成功");
                        if (this.A) {
                            MeshLogNew.d("主节点已连接登录成功");
                            com.leedarson.serviceimpl.elkstrays.b.a("mesh网络登陆成功,主节点：=" + SIGMesh.getInstance().getLastDirectMac());
                            X0();
                            return;
                        }
                        com.leedarson.serviceimpl.elkstrays.b.a("主节点未登陆,准备初始化代理节点-驱动其登陆");
                        this.c.h0();
                        this.i.y(this.x, this.z);
                        this.f.postDelayed(new a(this), 100);
                    } else if (this.c.z("autoConnect 检测到未连接")) {
                        this.s = true;
                        MeshLogNew.d("开始断开主节点的蓝牙连接");
                        com.leedarson.serviceimpl.elkstrays.b.a("开始断开主节点的蓝牙连接（断开中）");
                    } else {
                        T0("mesh上线，开始重新扫描节点，进行重新连接");
                        c0(new AutoConnectDeviceStepBean("开始ble扫描设备"), "");
                        f2();
                    }
                }
            } else {
                b.e("auto_connect_mesh 当前正在配网，不走自动上线流程");
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: J0 */
    public /* synthetic */ void K0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13211, new Class[0], Void.TYPE).isSupported) {
            h(false);
        }
    }

    /* access modifiers changed from: package-private */
    public void c2(GattOtaParameters otaParameters) {
        if (!PatchProxy.proxy(new Object[]{otaParameters}, this, changeQuickRedirect, false, 13117, new Class[]{GattOtaParameters.class}, Void.TYPE).isSupported) {
            Mode mode = Mode.MODE_OTA;
            if (!m2(mode)) {
                MeshLog.i("ota running currently  macAddress=" + this.J + " return");
                return;
            }
            this.f.removeCallbacksAndMessages((Object) null);
            this.p = otaParameters;
            this.o = mode;
            R0("actionMode赋值为(startGattOta):" + this.o);
            this.F = false;
            this.G = null;
            O1();
            if (o2((ConnectionFilter) otaParameters.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_CONNECTION_FILTER"))) {
                MeshLogNew.ota("主节点ble已连接上");
                d1();
                return;
            }
            MeshLogNew.ota("主节点ble还未连接，扫描");
            e2();
        }
    }

    private boolean o2(ConnectionFilter filter) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filter}, this, changeQuickRedirect, false, 13118, new Class[]{ConnectionFilter.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!this.c.O()) {
            return false;
        }
        int i2 = filter.a;
        if (i2 == 2) {
            String name = (String) filter.b;
            String connectName = this.c.I();
            if (TextUtils.isEmpty(connectName) || TextUtils.isEmpty(name) || !connectName.equals(name)) {
                return false;
            }
            return true;
        } else if (i2 != 1) {
            return i2 == 0;
        } else {
            String mac = (String) filter.b;
            String connectMac = this.c.J();
            if (TextUtils.isEmpty(connectMac) || TextUtils.isEmpty(mac) || !connectMac.equalsIgnoreCase(mac)) {
                return false;
            }
            return true;
        }
    }

    private void e2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13122, new Class[0], Void.TYPE).isSupported) {
            if (this.c.z("startSafetyScan")) {
                this.s = true;
                MeshLogNew.meshMsg("### 注意cacheBluetoothDevice被赋值为null了..");
                this.L = null;
                return;
            }
            f2();
        }
    }

    private boolean m2(Mode targetMode) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{targetMode}, this, changeQuickRedirect, false, 13123, new Class[]{Mode.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        MeshLog.i("validateActionMode  actionMode=" + this.o + ",targetMode=" + targetMode);
        Mode mode = this.o;
        if (mode == targetMode) {
            return false;
        }
        if (mode == Mode.MODE_REMOTE_PROVISION) {
            this.l.h();
        } else if (mode == Mode.MODE_PROVISION) {
            this.h.d();
        } else if (mode == Mode.MODE_BIND) {
            this.j.g();
        } else if (mode == Mode.MODE_MESH_OTA) {
            this.k.f();
        } else if (mode == Mode.MODE_FAST_PROVISION) {
            this.m.m();
            this.A = false;
        }
        this.o = targetMode;
        R0("actionMode赋值为:" + this.o);
        return true;
    }

    private void b2() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13124, new Class[0], Void.TYPE).isSupported) {
            byte[] otaFirmware = (byte[]) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_OTA_FIRMWARE");
            if (otaFirmware == null) {
                u1(false, "firmware not found");
                return;
            }
            V0("startGattOta");
            this.n.d(otaFirmware);
        }
    }

    private boolean n2() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13125, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        SparseArray<byte[]> sparseArray = this.w.g;
        if (sparseArray != null && sparseArray.size() != 0) {
            return true;
        }
        A0(false, "validateAutoConnectTargets 配置信息不完整");
        o1("com.telink.ble.com.telink.ble.mesh.MESH_EMPTY", "com.telink.ble.mesh empty");
        com.leedarson.serviceimpl.elkstrays.b.a("meshConfigureation 配置信息不完整(缺失)，不可以发起mesh组网");
        return false;
    }

    public void j1(boolean success, String desc) {
        if (!PatchProxy.proxy(new Object[]{new Byte(success ? (byte) 1 : 0), desc}, this, changeQuickRedirect, false, 13126, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            O1();
            A0(false, "onGattConnectionComplete");
            h1(new GattConnectionEvent(this, success ? "com.telink.sig.com.telink.ble.mesh.CONNECT_SUCCESS" : "com.telink.sig.com.telink.ble.mesh.CONNECT_FAIL", desc));
        }
    }

    private void b1(int state, String desc) {
        Object[] objArr = {new Integer(state), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13127, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            MeshLog.i("bluetooth event: " + state + " -- " + desc + "  macAddress=" + this.J);
            BluetoothEvent event = new BluetoothEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BLUETOOTH_STATE_CHANGE");
            event.c(state);
            event.b(desc);
            h1(event);
        }
    }

    private void o1(String eventType, String desc) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{eventType, desc}, this, changeQuickRedirect, false, 13128, clsArr, Void.TYPE).isSupported) {
            h1(new MeshEvent(this, eventType, desc, this.o));
        }
    }

    private void C1(MeshMessage meshMessage, String eventType, boolean z2, int opcode, int i2, int rspCount, String desc) {
        Class<String> cls = String.class;
        Object[] objArr = {meshMessage, eventType, new Byte(z2 ? (byte) 1 : 0), new Integer(opcode), new Integer(i2), new Integer(rspCount), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        Class[] clsArr = {MeshMessage.class, cls, Boolean.TYPE, cls2, cls2, cls2, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13129, clsArr, Void.TYPE).isSupported) {
            boolean success = z2;
            int rspMax = i2;
            h1(new ReliableMessageProcessEvent(this, eventType, success, opcode, rspMax, rspCount, desc, meshMessage));
        }
    }

    private void h1(Event event) {
        EventCallback eventCallback;
        if (!PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 13130, new Class[]{Event.class}, Void.TYPE).isSupported && (eventCallback = this.D) != null) {
            eventCallback.onEventPrepared(event);
        }
    }

    private void V0(String bzStart) {
        if (!PatchProxy.proxy(new Object[]{bzStart}, this, changeQuickRedirect, false, 13131, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLogNew.v("设置isActionStarted为true, onActionStart bzStart:" + bzStart + ",controller:" + hashCode() + ",mac:" + this.J);
            this.B = true;
        }
    }

    private void O1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13132, new Class[0], Void.TYPE).isSupported) {
            this.B = false;
            this.C = 0;
            R0("resetAction connectRetry = 0");
        }
    }

    private void X0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13133, new Class[0], Void.TYPE).isSupported) {
            W0("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_AUTO_CONNECT_LOGIN");
            n0("onAutoConnectSuccess");
        }
    }

    private void W0(String eventType) {
        if (!PatchProxy.proxy(new Object[]{eventType}, this, changeQuickRedirect, false, 13134, new Class[]{String.class}, Void.TYPE).isSupported) {
            h1(new AutoConnectEvent(this, eventType, this.E));
        }
    }

    public boolean v0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13135, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return this.A && this.c.D();
    }

    public void Q1(boolean enable) {
        NetworkingController networkingController;
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13136, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported && (networkingController = this.i) != null) {
            networkingController.G(enable);
        }
    }

    public void f0() {
        NetworkingController networkingController;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13137, new Class[0], Void.TYPE).isSupported && (networkingController = this.i) != null) {
            networkingController.w();
        }
    }

    public MeshMessage q0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13138, new Class[0], MeshMessage.class);
        if (proxy.isSupported) {
            return (MeshMessage) proxy.result;
        }
        NetworkingController networkingController = this.i;
        if (networkingController != null) {
            return networkingController.K();
        }
        return null;
    }

    public boolean I0() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13139, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.i.R().isEmpty();
    }

    public boolean T1(MeshMessage meshMessage) {
        String info;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{meshMessage}, this, changeQuickRedirect, false, 13140, new Class[]{MeshMessage.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (!this.A) {
            MeshLog.i("not login when sending message  macAddress=" + this.J);
            return false;
        }
        if (meshMessage.t()) {
            info = String.format(Locale.US, "propertyId --%04X params -- %s - dest -- %04X", new Object[]{Integer.valueOf(meshMessage.m()), com.leedarson.base.utils.e.a(meshMessage.l()), Integer.valueOf(meshMessage.j())}) + " isReliable: " + meshMessage.u() + " retryCnt: " + meshMessage.p() + " rspMax: " + meshMessage.n() + "  macAddress=" + this.J;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(Locale.US, meshMessage.getClass().getSimpleName() + ",opcode -- %04X  params -- %s - dest -- %04X", new Object[]{Integer.valueOf(meshMessage.k()), com.leedarson.base.utils.e.a(meshMessage.l()), Integer.valueOf(meshMessage.j())}));
            sb.append(" isReliable: ");
            sb.append(meshMessage.u());
            sb.append(" retryCnt: ");
            sb.append(meshMessage.p());
            sb.append(" rspMax: ");
            sb.append(meshMessage.n());
            sb.append("  macAddress=");
            sb.append(this.J);
            info = sb.toString();
        }
        boolean sent = this.i.B0(meshMessage);
        if (meshMessage.u()) {
            if (sent) {
                C1(meshMessage, "com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_CMD_PROCESSING", false, meshMessage.k(), meshMessage.n(), 0, "com.telink.ble.mesh message processing");
            } else {
                C1(meshMessage, "com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_CMD_ERROR_BUSY", false, meshMessage.k(), meshMessage.n(), 0, "com.telink.ble.mesh message send fail: busy");
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("send message [");
        sb2.append(sent ? FirebaseAnalytics.Param.SUCCESS : "fail");
        sb2.append("] ");
        sb2.append(info);
        R0(sb2.toString());
        return sent;
    }

    public void h(boolean isRetry) {
        Object[] objArr = {new Byte(isRetry ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13141, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            c0(new AutoConnectDeviceStepBean("bind setFilter", e.CODE_SUCCESS.getCode()), MeshConstants.AC_STATE_SET_WHITELIST);
            d0(new AddDeviceStepBean("bind setFilter").setStepSetFilterRetry(isRetry));
            this.i.s0();
        }
    }

    @Deprecated
    private void t1(byte[] data) {
        MeshLogNew.v("收到事件:上线状态");
        h1(new OnlineStatusEvent((Object) this, data));
    }

    private void l1(byte[] completePacket) {
        if (!PatchProxy.proxy(new Object[]{completePacket}, this, changeQuickRedirect, false, 13142, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (completePacket.length > 1) {
                byte proxyPduType = (byte) (completePacket[0] & 63);
                byte[] payloadData = new byte[(completePacket.length - 1)];
                System.arraycopy(completePacket, 1, payloadData, 0, payloadData.length);
                switch (proxyPduType) {
                    case 0:
                        NetworkingController networkingController = this.i;
                        if (networkingController != null) {
                            networkingController.m0(payloadData);
                            return;
                        }
                        return;
                    case 1:
                        NetworkingController networkingController2 = this.i;
                        if (networkingController2 != null) {
                            networkingController2.l0(payloadData, this.x, this.z);
                            return;
                        }
                        return;
                    case 2:
                        NetworkingController networkingController3 = this.i;
                        if (networkingController3 != null) {
                            networkingController3.n0(payloadData);
                            return;
                        }
                        return;
                    case 3:
                        ProvisioningController provisioningController = this.h;
                        if (provisioningController != null && this.B) {
                            provisioningController.B(payloadData);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private void G1(UUID serviceUUID, UUID characteristicUUID, byte[] data) {
        Class[] clsArr = {UUID.class, UUID.class, byte[].class};
        if (!PatchProxy.proxy(new Object[]{serviceUUID, characteristicUUID, data}, this, changeQuickRedirect, false, 13143, clsArr, Void.TYPE).isSupported) {
            h1(new GattNotificationEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_UNEXPECTED_NOTIFY", serviceUUID, characteristicUUID, data));
        }
    }

    private void d1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13144, new Class[0], Void.TYPE).isSupported) {
            Mode mode = this.o;
            if (mode == Mode.MODE_PROVISION) {
                ProvisioningDevice provisioningDevice = (ProvisioningDevice) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_PROVISIONING_TARGET");
                if (provisioningDevice.g().isProvisionedAndNotKeyBind) {
                    b.d("已provisioned 直接keybind");
                    A1(provisioningDevice, "");
                    return;
                }
                d0(new AddDeviceStepBean(AddDeviceStepBean.STEP_PROVISION_BEGIN));
                x1(provisioningDevice, "provision begin");
                V0("provision onConnectSuccess");
                this.h.b(provisioningDevice);
            } else if (mode == Mode.MODE_FAST_PROVISION) {
                B1();
            } else {
                boolean isFilterInitNeeded = this.p.c("com.telink.ble.com.telink.ble.mesh.light.COMMON_PROXY_FILTER_INIT_NEEDED", false);
                if ((this.A || !isFilterInitNeeded) && this.E != 0) {
                    if (this.o == Mode.MODE_OTA) {
                        MeshLogNew.ota("ota模式，主节点已经登录（mesh网络已通)");
                    }
                    B1();
                    return;
                }
                if (this.o == Mode.MODE_AUTO_CONNECT) {
                    c.f("执行login-setFilter");
                }
                h(false);
            }
        }
    }

    private void W1(int targetAddress) {
        if (!PatchProxy.proxy(new Object[]{new Integer(targetAddress)}, this, changeQuickRedirect, false, 13145, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            R0(String.format(Locale.US, "set node for %04X", new Object[]{Integer.valueOf(targetAddress)}));
            NodeIdentitySetMessage message = new NodeIdentitySetMessage(targetAddress);
            message.e(NeedPermissionEvent.PER_IPC_ALBUM_PERM);
            MeshLogNew.ota("NodeIdentitySetMessage 设置优先级最高");
            message.J(this.w.a);
            message.I(NodeIdentity.RUNNING.code);
            T1(message);
        }
    }

    /* renamed from: com.telink.ble.mesh.foundation.MeshController$16  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass16 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Mode.values().length];
            a = iArr;
            try {
                iArr[Mode.MODE_AUTO_CONNECT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[Mode.MODE_BIND.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[Mode.MODE_OTA.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[Mode.MODE_GATT_CONNECTION.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[Mode.MODE_MESH_OTA.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[Mode.MODE_FAST_PROVISION.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[Mode.MODE_PROVISION.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[Mode.MODE_SCAN.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[Mode.MODE_REMOTE_PROVISION.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    private void B1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13146, new Class[0], Void.TYPE).isSupported) {
            this.A = true;
            switch (AnonymousClass16.a[this.o.ordinal()]) {
                case 1:
                    com.leedarson.serviceimpl.elkstrays.b.a("autoconnect onProxyLoginSuccess mesh网络连接成功");
                    c0(new AutoConnectDeviceStepBean(AutoConnectDeviceStepBean.STEP_CONNECT_MESH_SUCCESS, e.CODE_SUCCESS.getCode()), MeshConstants.AC_STATE_SET_WHITELIST_SUCCESS);
                    X0();
                    U0();
                    return;
                case 2:
                    MeshLogNew.ota("非节点ota - bind onProxyLoginSuccess setFilter success");
                    MeshLog.i("setFilter--- success");
                    d0(new AddDeviceStepBean(AddDeviceStepBean.STEP_BIND_SET_FILTER_SUCCESS));
                    this.f.postDelayed(new Runnable() {
                        public static ChangeQuickRedirect changeQuickRedirect;

                        public void run() {
                            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13253, new Class[0], Void.TYPE).isSupported) {
                                MeshController.W(MeshController.this, "bind onProxyLoginSuccess");
                                BindingDevice bindingDevice = (BindingDevice) MeshController.this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_BINDING_TARGET");
                                int netKeyIndex = bindingDevice.f();
                                if (netKeyIndex == -1) {
                                    netKeyIndex = MeshController.this.w.a;
                                }
                                MeshController.this.j.e(netKeyIndex, MeshController.this.w.c.get(bindingDevice.a()), bindingDevice);
                            }
                        }
                    }, 500);
                    return;
                case 3:
                    MeshLogNew.ota("ota onProxyLoginSuccess setFilter success");
                    int address = h0();
                    if (address != -1) {
                        NodeInfo nodeInfo = LDSMeshUtil.hasExist(address);
                        StringBuilder sb = new StringBuilder();
                        sb.append("非主节点ota, 发送setNodeIdentity指令，寻找匹配要ota的设备,mesh targetAddress:");
                        sb.append(address);
                        sb.append(",mac:");
                        sb.append(nodeInfo != null ? nodeInfo.macAddress : "");
                        MeshLogNew.ota(sb.toString());
                        W1(address);
                        return;
                    }
                    MeshLogNew.ota("主节点ota，开始ota...");
                    b2();
                    return;
                case 4:
                    int address2 = h0();
                    if (address2 != -1) {
                        W1(address2);
                        return;
                    } else {
                        j1(true, "connect success");
                        return;
                    }
                case 5:
                    V0("ota onProxyLoginSuccess");
                    FirmwareUpdateConfiguration configuration = (FirmwareUpdateConfiguration) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_MESH_OTA_CONFIG");
                    L1(configuration);
                    this.k.d(configuration);
                    return;
                case 6:
                    V0("fastProvision onProxyLoginSuccess");
                    this.m.l();
                    return;
                default:
                    return;
            }
        }
    }

    private void U0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13147, new Class[0], Void.TYPE).isSupported) {
            if (this.L != null) {
                for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
                    if (this.L.getAddress().replace(":", "").equals(nodeInfo.macAddress)) {
                        nodeInfo.setOnline(true);
                        this.r.onDeviceStatusChange(nodeInfo.macAddress, 4096, 0);
                        return;
                    }
                }
            }
        }
    }

    public void P1() {
        this.L = null;
    }

    private int h0() {
        int targetAdr;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13148, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        ConnectionFilter filter = (ConnectionFilter) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_CONNECTION_FILTER");
        if (filter.a == 0 && this.E != (targetAdr = ((Integer) filter.b).intValue())) {
            return targetAdr;
        }
        return -1;
    }

    private void L1(FirmwareUpdateConfiguration configuration) {
        if (!PatchProxy.proxy(new Object[]{configuration}, this, changeQuickRedirect, false, 13149, new Class[]{FirmwareUpdateConfiguration.class}, Void.TYPE).isSupported) {
            List<MeshUpdatingDevice> devices = configuration.g();
            Iterator<MeshUpdatingDevice> iterator = devices.iterator();
            MeshUpdatingDevice directDevice = null;
            while (iterator.hasNext()) {
                MeshUpdatingDevice device = iterator.next();
                if (device.a() == this.E) {
                    directDevice = device;
                    iterator.remove();
                }
            }
            if (directDevice != null) {
                devices.add(directDevice);
                if (devices.size() == 1) {
                    configuration.j(true);
                    configuration.i(this.i.N());
                    return;
                }
                configuration.j(false);
            }
        }
    }

    private void f1(String macAddress) {
        if (!PatchProxy.proxy(new Object[]{macAddress}, this, changeQuickRedirect, false, 13150, new Class[]{String.class}, Void.TYPE).isSupported) {
            String desc = "connection interrupt,达到重连次数上限:" + this.C + ", 当前系统蓝牙可用状态：" + BluetoothAdapter.getDefaultAdapter().isEnabled();
            switch (AnonymousClass16.a[this.o.ordinal()]) {
                case 2:
                    d0(new AddDeviceStepBean(desc, e.CODE_BIND_ADD_APPKEY_FAIL.getCode()));
                    Z0(desc, 5);
                    return;
                case 3:
                    u1(false, desc);
                    return;
                case 5:
                    q1();
                    r1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_UPDATING_FAIL", (MeshUpdatingDevice) null, desc, -1);
                    return;
                case 6:
                    i1(false, "connection interrupt");
                    return;
                case 7:
                    ProvisioningDevice device = this.h.j();
                    d0(new AddDeviceStepBean("连接设备超时(ble连接失败)[mac: " + macAddress + ",reason:" + desc + "]", e.CODE_BLE_CONNECT_FAIL.getCode()));
                    z1(device, desc);
                    return;
                default:
                    return;
            }
        }
    }

    private void e1(String macAddress, String reason, int status) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{macAddress, reason, new Integer(status)}, this, changeQuickRedirect, false, 13151, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            String desc = "connect fail " + reason;
            switch (AnonymousClass16.a[this.o.ordinal()]) {
                case 2:
                    AddDeviceStepBean stepBean2 = new AddDeviceStepBean("连接设备超时(ble连接失败),bind(" + macAddress + "," + reason + ")", e.CODE_BLE_CONNECT_FAIL.getCode());
                    stepBean2.setSdkErrorCode(status);
                    d0(stepBean2);
                    Z0(desc, 5);
                    return;
                case 3:
                    u1(false, desc);
                    return;
                case 4:
                    j1(false, "connect fail MODE_GATT_CONNECTION");
                    return;
                case 5:
                    q1();
                    r1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_UPDATING_FAIL", (MeshUpdatingDevice) null, desc, -1);
                    return;
                case 6:
                    i1(false, "connect fail MODE_FAST_PROVISION ");
                    return;
                case 7:
                    AddDeviceStepBean stepBean = new AddDeviceStepBean("连接设备超时(ble连接失败)[mac: " + macAddress + ",reason:" + reason + "]", e.CODE_BLE_CONNECT_FAIL.getCode());
                    stepBean.setSdkErrorCode(status);
                    d0(stepBean);
                    StringBuilder sb = new StringBuilder();
                    sb.append("connect fail MODE_PROVISION reason=");
                    sb.append(reason);
                    z1((ProvisioningDevice) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_PROVISIONING_TARGET"), sb.toString());
                    return;
                default:
                    return;
            }
        }
    }

    private void k1(String str, String str2, int i2) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{str, str2, new Integer(i2)}, this, changeQuickRedirect, false, 13152, new Class[]{cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            final String reason = str2;
            final String macAddress = str;
            final int status = i2;
            R0("收到ble断开的回调  onGattDisconnected:" + macAddress);
            this.f.removeCallbacksAndMessages((Object) null);
            boolean isConnected = SIGMesh.getInstance().hasConnected();
            o1("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DISCONNECTED", "disconnected when: " + this.o);
            this.i.z();
            if (this.s) {
                this.s = false;
                this.C = -1;
            }
            this.A = false;
            this.E = 0;
            Mode mode = this.o;
            if (mode == Mode.MODE_IDLE) {
                return;
            }
            if (mode != Mode.MODE_AUTO_CONNECT) {
                this.f.postDelayed(new Runnable() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void run() {
                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13255, new Class[0], Void.TYPE).isSupported) {
                            switch (AnonymousClass16.a[MeshController.this.o.ordinal()]) {
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                    if (MeshController.this.B) {
                                        MeshController.s(MeshController.this, macAddress, status);
                                        return;
                                    } else if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                                        MeshController.t(MeshController.this, macAddress, "bluetooth disabled", status);
                                        return;
                                    } else {
                                        MeshController.w(MeshController.this);
                                        if (MeshController.this.o == Mode.MODE_PROVISION) {
                                            ProvisioningDevice provisioningDevice = (ProvisioningDevice) MeshController.this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_PROVISIONING_TARGET");
                                            if (ProcedureCollector.startAddDevicesTime <= 0 || System.currentTimeMillis() >= ProcedureCollector.startAddDevicesTime + 100000) {
                                                MeshController meshController = MeshController.this;
                                                String str = macAddress;
                                                MeshController.t(meshController, str, "ble连接100s时间已用完，仍连接失败,MODE=" + MeshController.this.o, status);
                                                return;
                                            } else if (MeshController.this.o0() == null || MeshController.this.o0().b() == null) {
                                                MeshLog.e("出错了吧，配网流程异常空了...");
                                                return;
                                            } else if (System.currentTimeMillis() - MeshController.this.o0().b().startToConnectBleTimeStamp >= KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS) {
                                                MeshController.this.o0().b().startToConnectBleTimeStamp = 0;
                                                AddDeviceStepBean stepBean = new AddDeviceStepBean("连接设备超时(ble连接失败)[mac: " + macAddress + ",reason:" + reason + "],连接失败总时长超过5s,重新扫描设备", e.CODE_BLE_CONNECT_FAIL.getCode());
                                                stepBean.setSdkErrorCode(status);
                                                MeshController.this.d0(stepBean);
                                                MeshController.this.R1(0);
                                                return;
                                            } else {
                                                MeshController.x(MeshController.this, provisioningDevice);
                                                AddDeviceStepBean stepBean2 = new AddDeviceStepBean("连接设备超时(ble连接失败)[mac: " + macAddress + ",reason:" + reason + "],等待300ms,继续发起连接", e.CODE_BLE_CONNECT_FAIL.getCode());
                                                stepBean2.setSdkErrorCode(status);
                                                MeshController.this.d0(stepBean2);
                                                MeshController.this.f.postDelayed(new Runnable() {
                                                    public static ChangeQuickRedirect changeQuickRedirect;

                                                    public void run() {
                                                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13256, new Class[0], Void.TYPE).isSupported) {
                                                            MeshController meshController = MeshController.this;
                                                            MeshController.O(meshController, meshController.L, MeshController.this.M, "onGattDisconnected provision");
                                                        }
                                                    }
                                                }, 300);
                                                return;
                                            }
                                        } else if (MeshController.this.o == Mode.MODE_OTA) {
                                            if (MeshDataManager.startOTATimespan <= 0 || System.currentTimeMillis() >= MeshDataManager.startOTATimespan + 70000) {
                                                long unused = MeshController.this.P = 0;
                                                MeshLogNew.otaWarn("70s内还未连接到蓝牙 ota fail");
                                                MeshController meshController2 = MeshController.this;
                                                String str2 = macAddress;
                                                MeshController.t(meshController2, str2, "Meshota 70s内还未连接到蓝牙 onfail MODE=" + MeshController.this.o, status);
                                                return;
                                            } else if (MeshController.this.L == null) {
                                                MeshLogNew.otaWarn("节点ota,没缓存的连接设备 retryScanOtaDevice");
                                                MeshController.this.S1(MeshController.this.x0());
                                                return;
                                            } else if (System.currentTimeMillis() - MeshController.this.P >= KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS) {
                                                long unused2 = MeshController.this.P = 0;
                                                MeshLogNew.otaWarn("蓝牙连接超过5s, 还未连接上，重新扫描");
                                                MeshController meshController3 = MeshController.this;
                                                meshController3.S1(meshController3.L.getAddress());
                                                return;
                                            } else {
                                                MeshLogNew.otaWarn("连接失败，延迟300ms后继续连接");
                                                MeshController.this.f.postDelayed(new Runnable() {
                                                    public static ChangeQuickRedirect changeQuickRedirect;

                                                    public void run() {
                                                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13257, new Class[0], Void.TYPE).isSupported) {
                                                            MeshController meshController = MeshController.this;
                                                            MeshController.O(meshController, meshController.L, MeshController.this.M, "onGattDisconnected ota");
                                                        }
                                                    }
                                                }, 300);
                                                return;
                                            }
                                        } else if (MeshController.this.C >= 8) {
                                            MeshController meshController4 = MeshController.this;
                                            String str3 = macAddress;
                                            MeshController.t(meshController4, str3, "fail max counts --> connectRetry=" + MeshController.this.C + "   MODE=" + MeshController.this.o, status);
                                            return;
                                        } else if (MeshController.this.o == Mode.MODE_BIND || MeshController.this.o == Mode.MODE_GATT_CONNECTION) {
                                            BluetoothDevice device = MeshController.this.L;
                                            if (device != null) {
                                                MeshLogNew.meshMsg("走到这边connect(device, -10001)逻辑，有待确认有问题? actionMode:" + MeshController.this.o);
                                                MeshController meshController5 = MeshController.this;
                                                meshController5.k0(device, meshController5.M);
                                                return;
                                            }
                                            MeshLog.i("##### SUFUN.stopScan.mDelayHandler  222222222222222");
                                            MeshController.p(MeshController.this);
                                            return;
                                        } else {
                                            MeshLog.i("##### SUFUN.stopScan.mDelayHandler 333333333333333333");
                                            MeshController.p(MeshController.this);
                                            return;
                                        }
                                    }
                                case 8:
                                    MeshController.b0(MeshController.this, "mode scan gatt disconnected, 重新startScan");
                                    MeshController.p(MeshController.this);
                                    return;
                                case 9:
                                    RemoteProvisioningDevice device2 = MeshController.this.l.i();
                                    MeshController.this.l.h();
                                    MeshController.C(MeshController.this, "com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_REMOTE_PROVISIONING_FAIL", device2, "connection interrupt");
                                    return;
                                default:
                                    return;
                            }
                        }
                    }
                }, 500);
            } else if (this.L == null || isConnected) {
                this.Q = 0;
                c.f("主节点断开了，重新扫描 startScan");
                f2();
            } else if (this.Q == 0 || System.currentTimeMillis() - this.Q < KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS) {
                c0(new AutoConnectDeviceStepBean("连接设备超时(ble连接失败)[mac: " + macAddress + ",reason:" + reason + ",连接失败，延迟300ms后继续连接]"), MeshConstants.AC_STATE_IDLE);
                m0(300, new Runnable() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void run() {
                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13254, new Class[0], Void.TYPE).isSupported) {
                            MeshController meshController = MeshController.this;
                            MeshController.O(meshController, meshController.L, MeshController.this.M, "onGattDisconnected autoConnect");
                        }
                    }
                });
            } else {
                this.Q = 0;
                this.Z = false;
                c0(new AutoConnectDeviceStepBean("连接设备超时(ble连接失败)[mac: " + macAddress + ",reason:" + reason + ",蓝牙连接超过5s, 还未连接上，重置isOnlineTaskHasRun=false,重新扫描]"), MeshConstants.AC_STATE_IDLE);
                f2();
            }
        }
    }

    public void R1(long delayTime) {
        Object[] objArr = {new Long(delayTime)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13153, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            final ProvisioningDevice provisioningDevice = (ProvisioningDevice) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_PROVISIONING_TARGET");
            MeshLog.i("第:" + this.C + " 次重试重连，延迟:" + delayTime + "ms 再次扫描设备");
            this.f.postDelayed(new Runnable() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void run() {
                    if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13258, new Class[0], Void.TYPE).isSupported) {
                        if (MeshController.this.q != null) {
                            AtomicBoolean flagAlreadySeek = new AtomicBoolean(false);
                            String macAddress = provisioningDevice.c().getAddress();
                            MeshController meshController = MeshController.this;
                            MeshController.b0(meshController, "retry 重新连接，重新扫描设备:" + macAddress);
                            BleC075Service E = MeshController.this.q;
                            AnonymousClass1 r4 = new ScanDeviceRuleListener() {
                                public static ChangeQuickRedirect changeQuickRedirect;

                                public boolean checkIsTarget(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord, @Nullable ScanRecord scanRecord2) {
                                    Object[] objArr = {bluetoothDevice, new Integer(rssi), scanRecord, scanRecord2};
                                    ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                                    ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
                                    PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 13261, new Class[]{BluetoothDevice.class, Integer.TYPE, byte[].class, ScanRecord.class}, Boolean.TYPE);
                                    if (proxy.isSupported) {
                                        return ((Boolean) proxy.result).booleanValue();
                                    }
                                    NetworkingDeviceWrapper wrapper = LDSMeshUtil.isMeshUnProvisionAdv(bluetoothDevice, rssi, scanRecord);
                                    if (wrapper != null) {
                                        MeshLog.v("checkIsTarget true" + w.b(scanRecord));
                                    }
                                    if (wrapper != null) {
                                        return true;
                                    }
                                    return false;
                                }
                            };
                            io.reactivex.e<R> c2 = E.seekForTargetBloothDevice(r4, true, macAddress, "mesh 配网 retryScanAndConnect(" + macAddress + ")").c(com.leedarson.base.http.observer.l.c());
                            ProvisioningDevice provisioningDevice = provisioningDevice;
                            c2.I(new c(this, provisioningDevice, flagAlreadySeek), new b(this, flagAlreadySeek, provisioningDevice));
                            return;
                        }
                        MeshController.this.k0(provisioningDevice.c(), provisioningDevice.h());
                    }
                }

                /* access modifiers changed from: private */
                /* renamed from: a */
                public /* synthetic */ void b(ProvisioningDevice provisioningDevice, AtomicBoolean flagAlreadySeek, com.leedarson.base.beans.a bluetoothDeviceWrap) {
                    if (!PatchProxy.proxy(new Object[]{provisioningDevice, flagAlreadySeek, bluetoothDeviceWrap}, this, changeQuickRedirect, false, 13260, new Class[]{ProvisioningDevice.class, AtomicBoolean.class, com.leedarson.base.beans.a.class}, Void.TYPE).isSupported) {
                        int unused = MeshController.this.C = 0;
                        MeshLog.v("mesh: 第:" + MeshController.this.C + " 次重试重连 重新扫描获取到新设备: macAddress=" + provisioningDevice.c().getAddress() + "   bluetoothDevice=" + bluetoothDeviceWrap.a);
                        flagAlreadySeek.set(true);
                        provisioningDevice.w(bluetoothDeviceWrap.c);
                        provisioningDevice.m(bluetoothDeviceWrap.a);
                        BluetoothDevice unused2 = MeshController.this.L = bluetoothDeviceWrap.a;
                        MeshController.this.k0(provisioningDevice.c(), provisioningDevice.h());
                    }
                }

                /* access modifiers changed from: private */
                /* renamed from: c */
                public /* synthetic */ void d(AtomicBoolean flagAlreadySeek, ProvisioningDevice provisioningDevice, Throwable throwable) {
                    Class[] clsArr = {AtomicBoolean.class, ProvisioningDevice.class, Throwable.class};
                    if (!PatchProxy.proxy(new Object[]{flagAlreadySeek, provisioningDevice, throwable}, this, changeQuickRedirect, false, 13259, clsArr, Void.TYPE).isSupported) {
                        if (!flagAlreadySeek.get()) {
                            MeshController.this.d0(new AddDeviceStepBean("扫描设备失败，使用缓存中的ble数据进行连接", e.CODE_SCAN_BLE_DEVICE_TIMEOUT.getCode()));
                            MeshLog.e("mesh: retry 重新扫描获取到新设备失败: macAddress=" + provisioningDevice.c().getAddress() + " exception=" + throwable);
                            MeshController.this.k0(provisioningDevice.c(), provisioningDevice.h());
                        }
                    }
                }
            }, delayTime);
        }
    }

    public String x0() {
        NodeInfo nodeInfo;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13154, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        ConnectionFilter filter = (ConnectionFilter) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_CONNECTION_FILTER");
        if (filter.a != 0 || (nodeInfo = LDSMeshUtil.hasExist(((Integer) filter.b).intValue())) == null) {
            return "";
        }
        return LDSMeshUtil.getRemoteMacAddress(nodeInfo.macAddress);
    }

    public void S1(String macAddress) {
        if (!PatchProxy.proxy(new Object[]{macAddress}, this, changeQuickRedirect, false, 13155, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (this.q != null) {
                AtomicBoolean flagAlreadySeek = new AtomicBoolean(false);
                BleC075Service bleC075Service = this.q;
                bleC075Service.seekForTargetBloothDevice((ScanDeviceRuleListener) null, true, macAddress, "ota scan retryScanAndConnect(" + macAddress + ")").c(com.leedarson.base.http.observer.l.c()).I(new f(this, macAddress, flagAlreadySeek), new d(this, flagAlreadySeek, macAddress));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: N0 */
    public /* synthetic */ void O0(String macAddress, AtomicBoolean flagAlreadySeek, com.leedarson.base.beans.a bluetoothDeviceWrap) {
        if (!PatchProxy.proxy(new Object[]{macAddress, flagAlreadySeek, bluetoothDeviceWrap}, this, changeQuickRedirect, false, 13210, new Class[]{String.class, AtomicBoolean.class, com.leedarson.base.beans.a.class}, Void.TYPE).isSupported) {
            MeshLogNew.ota("ota扫描到目标设备:" + macAddress);
            this.C = 0;
            flagAlreadySeek.set(true);
            I1(bluetoothDeviceWrap.a, bluetoothDeviceWrap.c, "retryScanOtaDevice ota");
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: P0 */
    public /* synthetic */ void Q0(AtomicBoolean flagAlreadySeek, String macAddress, Throwable th) {
        Class[] clsArr = {AtomicBoolean.class, String.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{flagAlreadySeek, macAddress, th}, this, changeQuickRedirect, false, 13209, clsArr, Void.TYPE).isSupported) {
            if (flagAlreadySeek.get()) {
                return;
            }
            if (this.L != null) {
                MeshLogNew.otaWarn("ota 未扫描到目标设备:" + macAddress + ",继续使用上一次蓝牙队列连接");
                I1(this.L, this.M, "retryScanOtaDevice ota use cache");
                return;
            }
            MeshLogNew.otaWarn("ota 未扫描到目标设备:" + macAddress + ",尝试使用直连方式连接");
            I1(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macAddress), -999, "retryScanOtaDevice ota...");
        }
    }

    private void i0(String macAddress, int status) {
        if (!PatchProxy.proxy(new Object[]{macAddress, new Integer(status)}, this, changeQuickRedirect, false, 13156, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
                e1(macAddress, "bluetooth disabled", status);
                return;
            }
            int i2 = this.C + 1;
            this.C = i2;
            if (i2 >= 8) {
                f1(macAddress);
                return;
            }
            Mode mode = this.o;
            if (mode == Mode.MODE_PROVISION) {
                final ProvisioningDevice provisioningDevice = (ProvisioningDevice) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_PROVISIONING_TARGET");
                this.f.postDelayed(new Runnable() {
                    public static ChangeQuickRedirect changeQuickRedirect;

                    public void run() {
                        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13262, new Class[0], Void.TYPE).isSupported) {
                            MeshLog.i("provisioning connect retry: " + MeshController.this.C + "  macAddress=" + MeshController.this.J + "  bleToothDevice=" + provisioningDevice.c().getAddress());
                            MeshController.this.k0(provisioningDevice.c(), provisioningDevice.h());
                        }
                    }
                }, 1500);
                return;
            }
            Mode mode2 = Mode.MODE_BIND;
            if (mode == mode2 || mode == Mode.MODE_GATT_CONNECTION || mode == Mode.MODE_OTA) {
                if (mode == mode2) {
                    this.j.w();
                    this.i.v0();
                }
                final BluetoothDevice device = this.L;
                if (device != null) {
                    this.f.postDelayed(new Runnable() {
                        public static ChangeQuickRedirect changeQuickRedirect;

                        public void run() {
                            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13263, new Class[0], Void.TYPE).isSupported) {
                                MeshController.this.d0(new AddDeviceStepBean("ble连接断开了"));
                                MeshLog.i("binding 过程，ble断开了, connect retry: " + MeshController.this.C + "  macAddress=" + MeshController.this.J + ",actionMode:" + MeshController.this.o);
                                MeshController meshController = MeshController.this;
                                meshController.k0(device, meshController.M);
                            }
                        }
                    }, 1000);
                    return;
                }
                MeshLog.i("##### SUFUN.stopScan.mDelayHandler  222222222222222");
                f2();
            }
        }
    }

    private void u1(boolean success, String desc) {
        if (!PatchProxy.proxy(new Object[]{new Byte(success ? (byte) 1 : 0), desc}, this, changeQuickRedirect, false, 13157, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            O1();
            A0(false, "onOtaComplete");
            v1(success ? "com.telink.sig.com.telink.ble.mesh.OTA_SUCCESS" : "com.telink.sig.com.telink.ble.mesh.OTA_FAIL", 0, desc);
        }
    }

    private void w1(int progress) {
        Object[] objArr = {new Integer(progress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13158, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            v1("com.telink.sig.com.telink.ble.mesh.OTA_PROGRESS", progress, "ota progress update");
        }
    }

    private void v1(String eventType, int progress, String desc) {
        Class<String> cls = String.class;
        Object[] objArr = {eventType, new Integer(progress), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13159, new Class[]{cls, Integer.TYPE, cls}, Void.TYPE).isSupported) {
            h1(new GattOtaEvent(this, eventType, progress, desc));
        }
    }

    public void j0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13160, new Class[0], Void.TYPE).isSupported) {
            this.v.clear();
        }
    }

    private void g1(AdvertisingDevice device) {
        if (!PatchProxy.proxy(new Object[]{device}, this, changeQuickRedirect, false, 13161, new Class[]{AdvertisingDevice.class}, Void.TYPE).isSupported) {
            if (this.v.add(device)) {
                h1(new ScanEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_DEVICE_FOUND", device));
            }
        }
    }

    private void E1(int errorCode) {
        Object[] objArr = {new Integer(errorCode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13162, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            ScanEvent scanEvent = new ScanEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_SCAN_FAIL", (AdvertisingDevice) null);
            scanEvent.c(errorCode);
            h1(scanEvent);
        }
    }

    private boolean q2(byte[] bArr, String str, ValidateProxyAdvParseListener... validateProxyAdvParseListenerArr) {
        String str2;
        Map<Byte, MeshUtils.AdvertiseDataUnit> dataUnitMap;
        MeshUtils.AdvertiseDataUnit dataUnit;
        boolean isInMeshJson;
        String str3;
        String k3MeshjsonNetKey;
        String meshjsonNetKey;
        String adverNetworkId;
        AutoConnectDeviceStepBean stepBean;
        int dimming;
        int dimming2;
        byte[] bArr2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bArr, str, validateProxyAdvParseListenerArr}, this, changeQuickRedirect, false, 13166, new Class[]{byte[].class, String.class, ValidateProxyAdvParseListener[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String macAddress = str;
        byte[] scanRecord = bArr;
        ValidateProxyAdvParseListener[] proxyAdvParseListener = validateProxyAdvParseListenerArr;
        String str4 = ":";
        String str5 = "";
        String mac = macAddress.replace(str4, str5);
        boolean isInMeshJson2 = false;
        for (NodeInfo nodeInfo : SIGMesh.getInstance().getMeshInfo().nodes) {
            if (mac.equals(nodeInfo.macAddress)) {
                isInMeshJson2 = true;
            }
        }
        if (!isInMeshJson2) {
            return false;
        }
        byte[] serviceData = MeshUtils.h(scanRecord, false);
        if (serviceData == null || serviceData.length < 9) {
            String str6 = mac;
            boolean z2 = isInMeshJson2;
            return false;
        }
        byte type = serviceData[0];
        if (type == 0) {
            byte[] advertisingNetworkId = new byte[8];
            System.arraycopy(serviceData, 1, advertisingNetworkId, 0, 8);
            boolean networkIdCheck = com.leedarson.base.utils.e.d(this.x, advertisingNetworkId);
            Map<Byte, MeshUtils.AdvertiseDataUnit> dataUnitMap2 = MeshUtils.n(scanRecord);
            MeshUtils.AdvertiseDataUnit dataUnit2 = dataUnitMap2.get((byte) -1);
            if (networkIdCheck) {
                int onoff = -1;
                if (dataUnit2 == null || (bArr2 = dataUnit2.c) == null) {
                    dimming2 = 0;
                    byte[] bArr3 = scanRecord;
                } else {
                    dimming2 = 0;
                    if (bArr2.length > 0) {
                        String advertisingData = LDSMeshUtil.bytesToHex(bArr2);
                        StringBuilder sb = new StringBuilder();
                        byte[] bArr4 = scanRecord;
                        sb.append("averdata:");
                        sb.append(advertisingData);
                        sb.append(",byte length:");
                        sb.append(dataUnit2.c.length);
                        MeshLogNew.e(sb.toString());
                        byte[] bArr5 = dataUnit2.c;
                        String str7 = advertisingData;
                        if (bArr5.length > 25) {
                            byte onOffAndDimming = bArr5[25];
                            int dimming3 = CmdCtrl.getValueByBitPosition(onOffAndDimming, 0, 7);
                            onoff = CmdCtrl.getValueByBitPosition(onOffAndDimming, 7, 1);
                            dimming = dimming3;
                        } else {
                            dimming = 0;
                        }
                        StringBuilder sb2 = new StringBuilder();
                        String str8 = mac;
                        sb2.append("扫描到设备:");
                        sb2.append(macAddress);
                        sb2.append(",匹配到networkId,onOff:");
                        sb2.append(onoff);
                        sb2.append(",dimming:");
                        sb2.append(dimming);
                        c0(new AutoConnectDeviceStepBean(sb2.toString()), MeshConstants.AC_STATE_DEV_FOUND);
                        if (proxyAdvParseListener != null && proxyAdvParseListener.length > 0) {
                            proxyAdvParseListener[0].a(onoff, dimming);
                        }
                        boolean z3 = isInMeshJson2;
                        MeshUtils.AdvertiseDataUnit advertiseDataUnit = dataUnit2;
                        Map<Byte, MeshUtils.AdvertiseDataUnit> map = dataUnitMap2;
                    }
                }
                MeshLogNew.e("先上报mesh设备-扫描到设备，是不带自定义type 0xff的广播，也是可以连接的，只是读不到自定义oxff的数据:" + mac);
                dimming = dimming2;
                StringBuilder sb22 = new StringBuilder();
                String str82 = mac;
                sb22.append("扫描到设备:");
                sb22.append(macAddress);
                sb22.append(",匹配到networkId,onOff:");
                sb22.append(onoff);
                sb22.append(",dimming:");
                sb22.append(dimming);
                c0(new AutoConnectDeviceStepBean(sb22.toString()), MeshConstants.AC_STATE_DEV_FOUND);
                proxyAdvParseListener[0].a(onoff, dimming);
                boolean z32 = isInMeshJson2;
                MeshUtils.AdvertiseDataUnit advertiseDataUnit2 = dataUnit2;
                Map<Byte, MeshUtils.AdvertiseDataUnit> map2 = dataUnitMap2;
            } else {
                String str9 = mac;
                try {
                    Iterator<NodeInfo> iterator = SIGMesh.getInstance().getMeshInfo().nodes.iterator();
                    while (iterator.hasNext()) {
                        NodeInfo nodeInfo2 = iterator.next();
                        Iterator<NodeInfo> iterator2 = iterator;
                        if (TextUtils.isEmpty(nodeInfo2.macAddress) || TextUtils.isEmpty(macAddress)) {
                            str3 = str4;
                            str2 = str5;
                            NodeInfo nodeInfo3 = nodeInfo2;
                            isInMeshJson = isInMeshJson2;
                            dataUnit = dataUnit2;
                            dataUnitMap = dataUnitMap2;
                        } else {
                            NodeInfo nodeInfo4 = nodeInfo2;
                            if (nodeInfo2.macAddress.equals(macAddress.replace(str4, str5))) {
                                byte[] meshJsonNetKey = new byte[0];
                                List<MeshNetKey> netKeys = SIGMesh.getInstance().getMeshInfo().meshNetKeyList;
                                if (netKeys.size() > 0) {
                                    byte[] bArr6 = meshJsonNetKey;
                                    try {
                                        meshJsonNetKey = netKeys.get(0).getKey();
                                    } catch (Exception e2) {
                                        e = e2;
                                        boolean z4 = isInMeshJson2;
                                        MeshUtils.AdvertiseDataUnit advertiseDataUnit3 = dataUnit2;
                                        Map<Byte, MeshUtils.AdvertiseDataUnit> map3 = dataUnitMap2;
                                        MeshLogNew.e("validateProxyAdv exception:" + e.getMessage());
                                        return networkIdCheck;
                                    }
                                } else {
                                    byte[] bArr7 = meshJsonNetKey;
                                }
                                str3 = str4;
                                String currentMeshNetworkId = ByteUtils.e(this.x);
                                String adverNetworkId2 = ByteUtils.e(advertisingNetworkId);
                                String meshjsonNetKey2 = ByteUtils.e(meshJsonNetKey);
                                String k3MeshjsonNetKey2 = ByteUtils.e(Encipher.o(meshJsonNetKey));
                                byte[] bArr8 = meshJsonNetKey;
                                str2 = str5;
                                if (this.o == Mode.MODE_AUTO_CONNECT) {
                                    try {
                                        e eVar = e.CODE_BLE_DEVICE_NETWORK_UNMATCH;
                                        List<MeshNetKey> list = netKeys;
                                        stepBean = new AutoConnectDeviceStepBean(eVar.getDesc(), eVar.getCode());
                                        stepBean.putResponseParams("macAddress", macAddress);
                                        adverNetworkId = adverNetworkId2;
                                        stepBean.putResponseParams("adverNetworkId", adverNetworkId);
                                        stepBean.putResponseParams("currentMeshNetworkId", currentMeshNetworkId);
                                        isInMeshJson = isInMeshJson2;
                                        meshjsonNetKey = meshjsonNetKey2;
                                    } catch (Exception e3) {
                                        e = e3;
                                        boolean z5 = isInMeshJson2;
                                        MeshUtils.AdvertiseDataUnit advertiseDataUnit4 = dataUnit2;
                                        Map<Byte, MeshUtils.AdvertiseDataUnit> map4 = dataUnitMap2;
                                        MeshLogNew.e("validateProxyAdv exception:" + e.getMessage());
                                        return networkIdCheck;
                                    }
                                    try {
                                        stepBean.putResponseParams("meshJsonNetKey", meshjsonNetKey);
                                        dataUnit = dataUnit2;
                                        k3MeshjsonNetKey = k3MeshjsonNetKey2;
                                        try {
                                            stepBean.putResponseParams("k3MeshjsonNetKey", k3MeshjsonNetKey);
                                            c.c(stepBean);
                                        } catch (Exception e4) {
                                            e = e4;
                                            Map<Byte, MeshUtils.AdvertiseDataUnit> map5 = dataUnitMap2;
                                        }
                                    } catch (Exception e5) {
                                        e = e5;
                                        MeshUtils.AdvertiseDataUnit advertiseDataUnit5 = dataUnit2;
                                        Map<Byte, MeshUtils.AdvertiseDataUnit> map6 = dataUnitMap2;
                                        MeshLogNew.e("validateProxyAdv exception:" + e.getMessage());
                                        return networkIdCheck;
                                    }
                                } else {
                                    List<MeshNetKey> list2 = netKeys;
                                    adverNetworkId = adverNetworkId2;
                                    isInMeshJson = isInMeshJson2;
                                    meshjsonNetKey = meshjsonNetKey2;
                                    dataUnit = dataUnit2;
                                    k3MeshjsonNetKey = k3MeshjsonNetKey2;
                                }
                                try {
                                    com.leedarson.serviceimpl.elkstrays.b.a("扫码到设备:" + macAddress + "，但是不匹配networkId, 当前mesh网络networkId:" + currentMeshNetworkId + ",广播包中的networkId:" + adverNetworkId);
                                    dataUnitMap = dataUnitMap2;
                                    try {
                                        a.y(this).u(NotificationCompat.CATEGORY_EVENT, "auto_connect_mesh").u(Constants.ACTION_STATE, Bugly.SDK_IS_DEV).u("mac", macAddress).c(MeshController.class.getSimpleName()).o("failure").t("LdsBleMesh").p("bleMesh 上线失败，扫码到设备，networkid不匹配, 当前mesh网络networkId:" + currentMeshNetworkId + ",广播包中的networkId:" + adverNetworkId + ",meshjson中的netkey:" + meshjsonNetKey + ",转换为networkid为:" + k3MeshjsonNetKey).a().b();
                                    } catch (Exception e6) {
                                        e = e6;
                                    }
                                } catch (Exception e7) {
                                    e = e7;
                                    Map<Byte, MeshUtils.AdvertiseDataUnit> map7 = dataUnitMap2;
                                    MeshLogNew.e("validateProxyAdv exception:" + e.getMessage());
                                    return networkIdCheck;
                                }
                            } else {
                                str3 = str4;
                                str2 = str5;
                                isInMeshJson = isInMeshJson2;
                                dataUnit = dataUnit2;
                                dataUnitMap = dataUnitMap2;
                            }
                        }
                        iterator = iterator2;
                        str4 = str3;
                        isInMeshJson2 = isInMeshJson;
                        dataUnit2 = dataUnit;
                        dataUnitMap2 = dataUnitMap;
                        str5 = str2;
                    }
                    boolean z6 = isInMeshJson2;
                    MeshUtils.AdvertiseDataUnit advertiseDataUnit6 = dataUnit2;
                    Map<Byte, MeshUtils.AdvertiseDataUnit> map8 = dataUnitMap2;
                } catch (Exception e8) {
                    e = e8;
                    boolean z7 = isInMeshJson2;
                    MeshUtils.AdvertiseDataUnit advertiseDataUnit7 = dataUnit2;
                    Map<Byte, MeshUtils.AdvertiseDataUnit> map9 = dataUnitMap2;
                    MeshLogNew.e("validateProxyAdv exception:" + e.getMessage());
                    return networkIdCheck;
                }
            }
            return networkIdCheck;
        }
        String str10 = mac;
        boolean z8 = isInMeshJson2;
        if (type != 1) {
            return false;
        }
        boolean nodeIdentityCheck = p2(serviceData, proxyAdvParseListener);
        StringBuilder sb3 = new StringBuilder();
        sb3.append("扫码到设备，check node identity pass:");
        sb3.append(nodeIdentityCheck ? "匹配" : "不匹配");
        sb3.append(",USE_FOR_ADDRESS_KEY:");
        sb3.append(this.J);
        MeshLogNew.i(sb3.toString());
        return nodeIdentityCheck;
    }

    public boolean p2(byte[] serviceData, ValidateProxyAdvParseListener... proxyAdvParseListener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{serviceData, proxyAdvParseListener}, this, changeQuickRedirect, false, 13167, new Class[]{byte[].class, ValidateProxyAdvParseListener[].class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (this.y == null || this.w.g.size() == 0 || serviceData.length < 17) {
            return false;
        }
        SparseArray<byte[]> deviceKeyMap = this.w.g;
        for (int i2 = 0; i2 < deviceKeyMap.size(); i2++) {
            int nodeAddress = deviceKeyMap.keyAt(i2);
            byte[] advHash = new byte[8];
            System.arraycopy(serviceData, 1, advHash, 0, 8);
            byte[] random = new byte[8];
            System.arraycopy(serviceData, 1 + 8, random, 0, 8);
            if (com.leedarson.base.utils.e.d(advHash, Encipher.k(this.y, random, nodeAddress))) {
                this.E = nodeAddress;
                MeshLog.i("reset direct device address  macAddress=" + this.J);
                if (proxyAdvParseListener != null && proxyAdvParseListener.length > 0) {
                    proxyAdvParseListener[0].a(-1, 0);
                }
                return true;
            }
        }
        return false;
    }

    private boolean r2(byte[] scanRecord, int nodeAddress, NodeInfo nodeInfo) {
        Object[] objArr = {scanRecord, new Integer(nodeAddress), nodeInfo};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 13168, new Class[]{byte[].class, Integer.TYPE, NodeInfo.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte[] serviceData = MeshScanRecord.c(scanRecord).b(ParcelUuid.fromString(UUIDInfo.g.toString()));
        if (nodeInfo == null) {
            MeshLogNew.ota("validateTargetNodeIdentity node null，actionMode:" + this.o);
        } else if (com.leedarson.base.utils.e.a(scanRecord).contains(nodeInfo.macAddress)) {
            MeshLogNew.ota("validateTargetNodeIdentity 匹配到mac, serviceData:" + com.leedarson.base.utils.e.b(serviceData, ""));
            boolean realCheck = m(serviceData, nodeAddress, nodeInfo);
            if (!realCheck) {
                MeshLogNew.ota("validateTargetNodeIdentity mac匹配上了，但是nodeIdentityhash匹配不上,是否设备广播有问题?");
            } else {
                MeshLogNew.ota("validateTargetNodeIdentity mac匹配上了，nodeIdentityhash也匹配上了");
            }
            return realCheck;
        }
        return m(serviceData, nodeAddress, nodeInfo);
    }

    private boolean m(byte[] serviceData, int nodeAddress, NodeInfo nodeInfo) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{serviceData, new Integer(nodeAddress), nodeInfo}, this, changeQuickRedirect, false, 13169, new Class[]{byte[].class, Integer.TYPE, NodeInfo.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (serviceData == null || serviceData.length < 9 || serviceData[0] != 1) {
            return false;
        }
        byte[] advHash = new byte[8];
        System.arraycopy(serviceData, 1, advHash, 0, 8);
        byte[] random = new byte[8];
        System.arraycopy(serviceData, 1 + 8, random, 0, 8);
        boolean result = com.leedarson.base.utils.e.d(advHash, Encipher.k(this.y, random, nodeAddress));
        StringBuilder sb = new StringBuilder();
        sb.append("validateTargetNodeIdentity networkIdentityKey:");
        sb.append(result);
        sb.append(",mac:");
        sb.append(nodeInfo != null ? nodeInfo.macAddress : "");
        MeshLogNew.ota(sb.toString());
        return result;
    }

    @NonNull
    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13170, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return this.O + "-" + this.J + "-" + super.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:89:0x027e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void F1(android.bluetooth.BluetoothDevice r13, int r14, byte[] r15) {
        /*
            r12 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r13
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r14)
            r9 = 1
            r1[r9] = r2
            r2 = 2
            r1[r2] = r15
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.bluetooth.BluetoothDevice> r0 = android.bluetooth.BluetoothDevice.class
            r6[r8] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r9] = r0
            java.lang.Class<byte[]> r0 = byte[].class
            r6[r2] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 13171(0x3373, float:1.8457E-41)
            r2 = r12
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0030
            return
        L_0x0030:
            r0 = r12
            java.lang.Object r1 = r0.t
            monitor-enter(r1)
            boolean r2 = r0.u     // Catch:{ all -> 0x027f }
            if (r2 != 0) goto L_0x003a
            monitor-exit(r1)     // Catch:{ all -> 0x027f }
            return
        L_0x003a:
            r2 = 0
            com.telink.ble.mesh.foundation.MeshController$Mode r3 = r0.o     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.foundation.MeshController$Mode r4 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_AUTO_CONNECT     // Catch:{ all -> 0x027f }
            if (r3 != r4) goto L_0x0055
            java.lang.String r3 = r13.getAddress()     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.foundation.MeshController$ValidateProxyAdvParseListener[] r5 = new com.telink.ble.mesh.foundation.MeshController.ValidateProxyAdvParseListener[r9]     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.foundation.e r6 = new com.telink.ble.mesh.foundation.e     // Catch:{ all -> 0x027f }
            r6.<init>(r0, r13, r14)     // Catch:{ all -> 0x027f }
            r5[r8] = r6     // Catch:{ all -> 0x027f }
            boolean r3 = r0.q2(r15, r3, r5)     // Catch:{ all -> 0x027f }
            r2 = r3
            goto L_0x0221
        L_0x0055:
            com.telink.ble.mesh.foundation.MeshController$Mode r5 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_OTA     // Catch:{ all -> 0x027f }
            if (r3 == r5) goto L_0x010d
            com.telink.ble.mesh.foundation.MeshController$Mode r5 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_GATT_CONNECTION     // Catch:{ all -> 0x027f }
            if (r3 != r5) goto L_0x005f
            goto L_0x010d
        L_0x005f:
            com.telink.ble.mesh.foundation.MeshController$Mode r5 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_BIND     // Catch:{ all -> 0x027f }
            if (r3 != r5) goto L_0x00e2
            com.telink.ble.mesh.foundation.parameter.Parameters r3 = r0.p     // Catch:{ all -> 0x027f }
            java.lang.String r5 = "com.telink.ble.com.telink.ble.mesh.light.ACTION_BINDING_TARGET"
            java.lang.Object r3 = r3.b(r5)     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.entity.BindingDevice r3 = (com.telink.ble.mesh.entity.BindingDevice) r3     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.core.access.BindingBearer r5 = r3.b()     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.core.access.BindingBearer r6 = com.telink.ble.mesh.core.access.BindingBearer.GattOnly     // Catch:{ all -> 0x027f }
            r7 = 0
            if (r5 != r6) goto L_0x009e
            int r5 = r3.d()     // Catch:{ all -> 0x027f }
            boolean r5 = r0.r2(r15, r5, r7)     // Catch:{ all -> 0x027f }
            r2 = r5
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
            r5.<init>()     // Catch:{ all -> 0x027f }
            java.lang.String r6 = "MeshCtroller.onScanFilter bind check node identity pass? "
            r5.append(r6)     // Catch:{ all -> 0x027f }
            r5.append(r2)     // Catch:{ all -> 0x027f }
            java.lang.String r6 = "  macAddress="
            r5.append(r6)     // Catch:{ all -> 0x027f }
            java.lang.String r6 = r0.J     // Catch:{ all -> 0x027f }
            r5.append(r6)     // Catch:{ all -> 0x027f }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x027f }
            meshsdk.MeshLog.v(r5)     // Catch:{ all -> 0x027f }
            goto L_0x00d4
        L_0x009e:
            com.telink.ble.mesh.core.access.BindingBearer r5 = r3.b()     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.core.access.BindingBearer r6 = com.telink.ble.mesh.core.access.BindingBearer.Flex     // Catch:{ all -> 0x027f }
            if (r5 != r6) goto L_0x00c9
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x027f }
            long r10 = r0.H     // Catch:{ all -> 0x027f }
            long r5 = r5 - r10
            r10 = 8000(0x1f40, double:3.9525E-320)
            int r5 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r5 <= 0) goto L_0x00bf
            java.lang.String r5 = r13.getAddress()     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.foundation.MeshController$ValidateProxyAdvParseListener[] r6 = new com.telink.ble.mesh.foundation.MeshController.ValidateProxyAdvParseListener[r8]     // Catch:{ all -> 0x027f }
            boolean r5 = r0.q2(r15, r5, r6)     // Catch:{ all -> 0x027f }
            r2 = r5
            goto L_0x00d4
        L_0x00bf:
            int r5 = r3.d()     // Catch:{ all -> 0x027f }
            boolean r5 = r0.r2(r15, r5, r7)     // Catch:{ all -> 0x027f }
            r2 = r5
            goto L_0x00d4
        L_0x00c9:
            java.lang.String r5 = r13.getAddress()     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.foundation.MeshController$ValidateProxyAdvParseListener[] r6 = new com.telink.ble.mesh.foundation.MeshController.ValidateProxyAdvParseListener[r8]     // Catch:{ all -> 0x027f }
            boolean r5 = r0.q2(r15, r5, r6)     // Catch:{ all -> 0x027f }
            r2 = r5
        L_0x00d4:
            if (r2 == 0) goto L_0x00e0
            int r5 = r0.E     // Catch:{ all -> 0x027f }
            int r6 = r3.d()     // Catch:{ all -> 0x027f }
            if (r5 != r6) goto L_0x00e0
            r0.G = r13     // Catch:{ all -> 0x027f }
        L_0x00e0:
            goto L_0x0221
        L_0x00e2:
            com.telink.ble.mesh.foundation.MeshController$Mode r5 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_FAST_PROVISION     // Catch:{ all -> 0x027f }
            if (r3 != r5) goto L_0x00ef
            r2 = 1
            java.lang.String r3 = "扫描发现快速配网模式,可以尝试去连接"
            com.leedarson.serviceimpl.elkstrays.b.a(r3)     // Catch:{ all -> 0x027f }
            goto L_0x0221
        L_0x00ef:
            com.telink.ble.mesh.foundation.MeshController$Mode r5 = com.telink.ble.mesh.foundation.MeshController.Mode.MODE_SCAN     // Catch:{ all -> 0x027f }
            if (r3 != r5) goto L_0x0221
            com.telink.ble.mesh.foundation.parameter.Parameters r3 = r0.p     // Catch:{ all -> 0x027f }
            java.lang.String r5 = "com.telink.ble.com.telink.ble.mesh.light.SCAN_SINGLE_MODE"
            boolean r3 = r3.c(r5, r8)     // Catch:{ all -> 0x027f }
            if (r3 == 0) goto L_0x0103
            java.lang.String r5 = "onScanFilter MODESCAN"
            r0.i2(r5)     // Catch:{ all -> 0x027f }
        L_0x0103:
            com.telink.ble.mesh.entity.AdvertisingDevice r5 = new com.telink.ble.mesh.entity.AdvertisingDevice     // Catch:{ all -> 0x027f }
            r5.<init>(r13, r14, r15)     // Catch:{ all -> 0x027f }
            r0.g1(r5)     // Catch:{ all -> 0x027f }
            goto L_0x0221
        L_0x010d:
            com.telink.ble.mesh.foundation.parameter.Parameters r3 = r0.p     // Catch:{ all -> 0x027f }
            java.lang.String r5 = "com.telink.ble.com.telink.ble.mesh.light.ACTION_CONNECTION_FILTER"
            java.lang.Object r3 = r3.b(r5)     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.entity.ConnectionFilter r3 = (com.telink.ble.mesh.entity.ConnectionFilter) r3     // Catch:{ all -> 0x027f }
            if (r3 != 0) goto L_0x0134
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
            r4.<init>()     // Catch:{ all -> 0x027f }
            java.lang.String r5 = "扫描设备，分析filter为空，抛弃此次"
            r4.append(r5)     // Catch:{ all -> 0x027f }
            java.lang.String r5 = r13.getAddress()     // Catch:{ all -> 0x027f }
            r4.append(r5)     // Catch:{ all -> 0x027f }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x027f }
            com.leedarson.serviceimpl.elkstrays.b.a(r4)     // Catch:{ all -> 0x027f }
            monitor-exit(r1)     // Catch:{ all -> 0x027f }
            return
        L_0x0134:
            int r5 = r3.a     // Catch:{ all -> 0x027f }
            switch(r5) {
                case 0: goto L_0x01a8;
                case 1: goto L_0x0181;
                case 2: goto L_0x013b;
                default: goto L_0x0139;
            }     // Catch:{ all -> 0x027f }
        L_0x0139:
            goto L_0x0220
        L_0x013b:
            java.lang.Object r5 = r3.b     // Catch:{ all -> 0x027f }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x027f }
            java.lang.String r6 = r13.getName()     // Catch:{ all -> 0x027f }
            boolean r6 = android.text.TextUtils.isEmpty(r6)     // Catch:{ all -> 0x027f }
            if (r6 != 0) goto L_0x015b
            boolean r6 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x027f }
            if (r6 != 0) goto L_0x015b
            java.lang.String r6 = r13.getName()     // Catch:{ all -> 0x027f }
            boolean r6 = r6.equals(r5)     // Catch:{ all -> 0x027f }
            if (r6 == 0) goto L_0x015b
            r6 = r9
            goto L_0x015c
        L_0x015b:
            r6 = r8
        L_0x015c:
            r2 = r6
            if (r2 == 0) goto L_0x0220
            r0.G = r13     // Catch:{ all -> 0x027f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
            r6.<init>()     // Catch:{ all -> 0x027f }
            java.lang.String r7 = "connect by name: "
            r6.append(r7)     // Catch:{ all -> 0x027f }
            r6.append(r5)     // Catch:{ all -> 0x027f }
            java.lang.String r7 = "  macAddress="
            r6.append(r7)     // Catch:{ all -> 0x027f }
            java.lang.String r7 = r0.J     // Catch:{ all -> 0x027f }
            r6.append(r7)     // Catch:{ all -> 0x027f }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.util.MeshLogger.a(r6)     // Catch:{ all -> 0x027f }
            goto L_0x0220
        L_0x0181:
            java.lang.Object r5 = r3.b     // Catch:{ all -> 0x027f }
            java.lang.String r5 = (java.lang.String) r5     // Catch:{ all -> 0x027f }
            java.lang.String r6 = r13.getAddress()     // Catch:{ all -> 0x027f }
            boolean r6 = r5.equalsIgnoreCase(r6)     // Catch:{ all -> 0x027f }
            r2 = r6
            if (r2 == 0) goto L_0x0220
            r0.G = r13     // Catch:{ all -> 0x027f }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
            r6.<init>()     // Catch:{ all -> 0x027f }
            java.lang.String r7 = "connect by mac address: "
            r6.append(r7)     // Catch:{ all -> 0x027f }
            r6.append(r5)     // Catch:{ all -> 0x027f }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.util.MeshLogger.a(r6)     // Catch:{ all -> 0x027f }
            goto L_0x0220
        L_0x01a8:
            java.lang.Object r5 = r3.b     // Catch:{ all -> 0x027f }
            java.lang.Integer r5 = (java.lang.Integer) r5     // Catch:{ all -> 0x027f }
            int r5 = r5.intValue()     // Catch:{ all -> 0x027f }
            boolean r6 = r0.F     // Catch:{ all -> 0x027f }
            if (r6 == 0) goto L_0x020d
            meshsdk.model.NodeInfo r6 = meshsdk.util.LDSMeshUtil.hasExist(r5)     // Catch:{ all -> 0x027f }
            boolean r6 = r0.r2(r15, r5, r6)     // Catch:{ all -> 0x027f }
            r2 = r6
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
            r6.<init>()     // Catch:{ all -> 0x027f }
            java.lang.String r7 = "MeshController.onScanFilter  connection check node identity pass? "
            r6.append(r7)     // Catch:{ all -> 0x027f }
            r6.append(r2)     // Catch:{ all -> 0x027f }
            java.lang.String r7 = "  macAddress="
            r6.append(r7)     // Catch:{ all -> 0x027f }
            java.lang.String r7 = r0.J     // Catch:{ all -> 0x027f }
            r6.append(r7)     // Catch:{ all -> 0x027f }
            java.lang.String r7 = ",scanRecord:"
            r6.append(r7)     // Catch:{ all -> 0x027f }
            java.lang.String r7 = com.leedarson.base.utils.e.a(r15)     // Catch:{ all -> 0x027f }
            r6.append(r7)     // Catch:{ all -> 0x027f }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x027f }
            meshsdk.MeshLog.v(r6)     // Catch:{ all -> 0x027f }
            if (r2 == 0) goto L_0x0218
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
            r6.<init>()     // Catch:{ all -> 0x027f }
            java.lang.String r7 = "非主节点ota，准备去匹配扫描到的设备广播包validateTargetNodeIdentity，匹配:"
            r6.append(r7)     // Catch:{ all -> 0x027f }
            if (r2 == 0) goto L_0x01fa
            java.lang.String r7 = "成功"
            goto L_0x01fd
        L_0x01fa:
            java.lang.String r7 = "失败"
        L_0x01fd:
            r6.append(r7)     // Catch:{ all -> 0x027f }
            java.lang.String r7 = ",开始连接"
            r6.append(r7)     // Catch:{ all -> 0x027f }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x027f }
            meshsdk.MeshLogNew.ota(r6)     // Catch:{ all -> 0x027f }
            goto L_0x0218
        L_0x020d:
            java.lang.String r6 = r13.getAddress()     // Catch:{ all -> 0x027f }
            com.telink.ble.mesh.foundation.MeshController$ValidateProxyAdvParseListener[] r7 = new com.telink.ble.mesh.foundation.MeshController.ValidateProxyAdvParseListener[r8]     // Catch:{ all -> 0x027f }
            boolean r6 = r0.q2(r15, r6, r7)     // Catch:{ all -> 0x027f }
            r2 = r6
        L_0x0218:
            if (r2 == 0) goto L_0x0220
            int r6 = r0.E     // Catch:{ all -> 0x027f }
            if (r6 != r5) goto L_0x0220
            r0.G = r13     // Catch:{ all -> 0x027f }
        L_0x0220:
        L_0x0221:
            if (r2 == 0) goto L_0x027d
            com.telink.ble.mesh.foundation.MeshController$Mode r3 = r0.o     // Catch:{ all -> 0x027f }
            if (r3 != r4) goto L_0x024a
            boolean r3 = r0.Z     // Catch:{ all -> 0x027f }
            if (r3 != 0) goto L_0x0233
            r0.Z = r9     // Catch:{ all -> 0x027f }
            java.lang.Runnable r3 = r0.a0     // Catch:{ all -> 0x027f }
            r3.run()     // Catch:{ all -> 0x027f }
            goto L_0x027d
        L_0x0233:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
            r3.<init>()     // Catch:{ all -> 0x027f }
            java.lang.String r4 = "isOnlineTaskHasRun:"
            r3.append(r4)     // Catch:{ all -> 0x027f }
            boolean r4 = r0.Z     // Catch:{ all -> 0x027f }
            r3.append(r4)     // Catch:{ all -> 0x027f }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x027f }
            com.leedarson.serviceimpl.elkstrays.b.b(r3)     // Catch:{ all -> 0x027f }
            goto L_0x027d
        L_0x024a:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
            r3.<init>()     // Catch:{ all -> 0x027f }
            java.lang.String r4 = " connectIntent="
            r3.append(r4)     // Catch:{ all -> 0x027f }
            r3.append(r2)     // Catch:{ all -> 0x027f }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x027f }
            r0.l2(r8, r3)     // Catch:{ all -> 0x027f }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x027f }
            r3.<init>()     // Catch:{ all -> 0x027f }
            java.lang.String r4 = "扫描到设备,尝试连接：device.getMacAddress="
            r3.append(r4)     // Catch:{ all -> 0x027f }
            java.lang.String r4 = r13.getAddress()     // Catch:{ all -> 0x027f }
            r3.append(r4)     // Catch:{ all -> 0x027f }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x027f }
            com.leedarson.serviceimpl.elkstrays.b.a(r3)     // Catch:{ all -> 0x027f }
            java.lang.String r3 = "onScanFilter"
            r0.I1(r13, r14, r3)     // Catch:{ all -> 0x027f }
        L_0x027d:
            monitor-exit(r1)     // Catch:{ all -> 0x027f }
            return
        L_0x027f:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x027f }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.foundation.MeshController.F1(android.bluetooth.BluetoothDevice, int, byte[]):void");
    }

    /* access modifiers changed from: private */
    /* renamed from: L0 */
    public /* synthetic */ void M0(BluetoothDevice bluetoothDevice, int i2, int i3, int i4) {
        Object[] objArr = {bluetoothDevice, new Integer(i2), new Integer(i3), new Integer(i4)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {BluetoothDevice.class, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13208, clsArr, Void.TYPE).isSupported) {
            int rssi = i2;
            int dimming = i4;
            BluetoothDevice device = bluetoothDevice;
            int onOff = i3;
            io.reactivex.disposables.b bVar = this.T;
            if (bVar != null && !bVar.isDisposed()) {
                this.T.dispose();
            }
            AutoConnectDevicesManager.AutoConnectDevice autoConnectDevice = new AutoConnectDevicesManager.AutoConnectDevice();
            autoConnectDevice.f(device);
            autoConnectDevice.k(rssi);
            autoConnectDevice.j(onOff);
            autoConnectDevice.h(dimming);
            AutoConnectDevicesManager.d().a(autoConnectDevice);
            if (onOff != -1) {
                boolean hasFindAll = true;
                Iterator<NodeInfo> it = SIGMesh.getInstance().getMeshInfo().nodes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    NodeInfo nodeInfo = it.next();
                    boolean hasFind = false;
                    for (AutoConnectDevicesManager.AutoConnectDevice device1 : AutoConnectDevicesManager.d().d) {
                        if (nodeInfo.macAddress.equals(device1.b().getAddress().replace(":", ""))) {
                            hasFind = true;
                        }
                    }
                    if (!hasFind) {
                        hasFindAll = false;
                        break;
                    }
                }
                if (hasFindAll) {
                    n0("提前扫描到所有设备");
                }
                this.r.onPreReportDeviceStatusChange(device.getAddress(), onOff, dimming, false);
            }
        }
    }

    private void l2(boolean _isScanning, String bzReason) {
        if (!PatchProxy.proxy(new Object[]{new Byte(_isScanning ? (byte) 1 : 0), bzReason}, this, changeQuickRedirect, false, 13172, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            timber.log.a.i("updateScanningFlagMeshController " + _isScanning + ",desc:" + bzReason, new Object[0]);
            this.u = _isScanning;
        }
    }

    private void H1(String eventType, ProvisioningDevice provisioningDevice, String desc) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, ProvisioningDevice.class, cls};
        if (!PatchProxy.proxy(new Object[]{eventType, provisioningDevice, desc}, this, changeQuickRedirect, false, 13173, clsArr, Void.TYPE).isSupported) {
            h1(new ProvisioningEvent(this, eventType, provisioningDevice, desc));
        }
    }

    private void x1(ProvisioningDevice device, String desc) {
        Class[] clsArr = {ProvisioningDevice.class, String.class};
        if (!PatchProxy.proxy(new Object[]{device, desc}, this, changeQuickRedirect, false, 13174, clsArr, Void.TYPE).isSupported) {
            H1("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_PROVISION_BEGIN", device, desc);
        }
    }

    private void z1(ProvisioningDevice provisioningDevice, String desc) {
        Class[] clsArr = {ProvisioningDevice.class, String.class};
        if (!PatchProxy.proxy(new Object[]{provisioningDevice, desc}, this, changeQuickRedirect, false, 13175, clsArr, Void.TYPE).isSupported) {
            MeshLog.i("provisioning failed: " + desc + " -- ,rssi:" + provisioningDevice.h() + ",unicastAddress:" + provisioningDevice.i() + ",macAddress=" + this.J);
            y1();
            H1("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_PROVISION_FAIL", provisioningDevice, desc);
        }
    }

    private void A1(ProvisioningDevice provisioningDevice, String desc) {
        Class[] clsArr = {ProvisioningDevice.class, String.class};
        if (!PatchProxy.proxy(new Object[]{provisioningDevice, desc}, this, changeQuickRedirect, false, 13176, clsArr, Void.TYPE).isSupported) {
            d0(new AddDeviceStepBean(AddDeviceStepBean.STEP_PROVISION_SUCCESS));
            y1();
            this.E = provisioningDevice.i();
            if (this.o == Mode.MODE_OTA) {
                MeshLogNew.ota("非主节点ota onProvisionSuccess direcDeviceAddress=" + provisioningDevice.i());
            }
            H1("com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_PROVISION_SUCCESS", provisioningDevice, desc);
        }
    }

    private void y1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13177, new Class[0], Void.TYPE).isSupported) {
            this.B = false;
            A0(false, "onProvisionComplete");
        }
    }

    public void e(int state, String desc) {
        Object[] objArr = {new Integer(state), desc};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13178, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            switch (state) {
                case 4098:
                    MeshLog.i("SUFUN.on device capability received  mac:" + p0() + "  macAddress=" + this.J);
                    return;
                case 4107:
                    ProvisioningDevice device = this.h.j();
                    b.d("provision 成功, 添加meshAddress:" + device.i() + "，对应的deviceKey:" + device.e());
                    k2(device.i(), device.e());
                    A1(device, desc);
                    return;
                case 4108:
                    MeshLog.i("SUFUN.provision failed, " + desc + " mac: " + p0() + "  macAddress=" + this.J);
                    ProvisioningDevice device2 = this.h.j();
                    d0(new AddDeviceStepBean("provisionFail(" + desc + ",state:" + 4108 + ")", e.CODE_PROVISION_FAIL.getCode()));
                    z1(device2, desc);
                    return;
                default:
                    return;
            }
        }
    }

    public void b(byte type, byte[] data, String extra) {
        GattConnection gattConnection;
        Object[] objArr = {new Byte(type), data, extra};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13179, new Class[]{Byte.TYPE, byte[].class, String.class}, Void.TYPE).isSupported && (gattConnection = this.c) != null) {
            gattConnection.m0(type, data, extra);
        }
    }

    public void d(int sequenceNumber, int ivIndex) {
        Object[] objArr = {new Integer(sequenceNumber), new Integer(ivIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13180, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            MeshConfiguration meshConfiguration = this.w;
            meshConfiguration.e = sequenceNumber;
            if (this.o != Mode.MODE_FAST_PROVISION) {
                meshConfiguration.d = ivIndex;
            }
            z0(sequenceNumber, meshConfiguration.d);
        }
    }

    public void z0(int sequenceNumber, int ivIndex) {
        Object[] objArr = {new Integer(sequenceNumber), new Integer(ivIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13181, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            MeshLog.i("handleNetworkInfoUpdate sequenceNumber: " + sequenceNumber + ",ivIndex" + ivIndex + ",macAddress=" + this.J);
            h1(new NetworkInfoUpdateEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_NETWORKD_INFO_UPDATE", sequenceNumber, ivIndex));
        }
    }

    public void c(int src, int dst, int opcode, byte[] params) {
        Object[] objArr = {new Integer(src), new Integer(dst), new Integer(opcode), params};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13182, new Class[]{cls, cls, cls, byte[].class}, Void.TYPE).isSupported) {
            NotificationMessage notificationMessage = new NotificationMessage(src, dst, opcode, params);
            n1(notificationMessage);
            Mode mode = this.o;
            if (mode == Mode.MODE_BIND) {
                this.j.u(notificationMessage);
            } else if (mode == Mode.MODE_MESH_OTA) {
                this.k.w(notificationMessage);
            } else if (mode == Mode.MODE_REMOTE_PROVISION) {
                this.l.o(notificationMessage);
            } else if (mode == Mode.MODE_FAST_PROVISION) {
                this.m.w(notificationMessage);
            }
            p1(notificationMessage);
        }
    }

    public void k(boolean success, int address) {
        Object[] objArr = {new Byte(success ? (byte) 1 : 0), new Integer(address)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13183, new Class[]{Boolean.TYPE, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLog.i("filter init complete, success? " + success + ",mode:" + this.o.name() + "  macAddress=" + this.J + ",address:" + address);
            if (success) {
                this.E = address;
                if (this.o == Mode.MODE_OTA) {
                    MeshLogNew.ota("ota模式，主节点setFilter login成功,mesh网络已通");
                }
                B1();
                return;
            }
            MeshLog.i("setFilter fail timeout!  macAddress=" + this.J);
            if (this.o == Mode.MODE_BIND) {
                e eVar = e.CODE_BIND_SETFILTER_FAIL;
                d0(new AddDeviceStepBean(eVar.getDesc(), eVar.getCode()));
                Z0("setFilter fail timeout!", 4);
            }
            c0(new AutoConnectDeviceStepBean("bind setFilter fail"), MeshConstants.AC_STATE_SET_WHITELIST_FAIL);
            this.c.z("proxy init fail!");
        }
    }

    public void i(int src, int dst, byte[] data) {
    }

    public void l(boolean success, int opcode, int rspMax, int rspCount) {
        Object[] objArr = {new Byte(success ? (byte) 1 : 0), new Integer(opcode), new Integer(rspMax), new Integer(rspCount)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13184, new Class[]{Boolean.TYPE, cls, cls, cls}, Void.TYPE).isSupported) {
            Mode mode = this.o;
            if (mode == Mode.MODE_BIND) {
                this.j.q(success, opcode, rspMax, rspCount);
            } else if (mode == Mode.MODE_MESH_OTA) {
                this.k.B(success, opcode, rspMax, rspCount);
            } else if (mode == Mode.MODE_REMOTE_PROVISION) {
                this.l.s(success, opcode, rspMax, rspCount);
            } else if (mode == Mode.MODE_FAST_PROVISION) {
                this.m.u(success, opcode, rspMax, rspCount);
            }
            if (!success) {
                m1(opcode);
            }
            C1((MeshMessage) null, "com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_CMD_COMPLETE", success, opcode, rspMax, rspCount, "com.telink.ble.mesh message send complete");
        }
    }

    public void f(boolean success) {
        Object[] objArr = {new Byte(success ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13185, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (this.o == Mode.MODE_MESH_OTA) {
                this.k.y(success);
            }
        }
    }

    public String a() {
        return this.O;
    }

    public void m1(int opcode) {
        if (!PatchProxy.proxy(new Object[]{new Integer(opcode)}, this, changeQuickRedirect, false, 13186, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (opcode == Opcode.NODE_ID_SET.value) {
                Mode mode = this.o;
                if (mode == Mode.MODE_OTA) {
                    String targetMacAddress = x0();
                    MeshLogNew.ota("非主节点ota setNodeIdentitySetMessage fail, 扫描目标mac地址:" + targetMacAddress);
                    S1(targetMacAddress);
                } else if (mode == Mode.MODE_GATT_CONNECTION) {
                    j1(false, "node identity set failed");
                }
            }
        }
    }

    private void n1(NotificationMessage notificationMessage) {
        if (!PatchProxy.proxy(new Object[]{notificationMessage}, this, changeQuickRedirect, false, 13187, new Class[]{NotificationMessage.class}, Void.TYPE).isSupported) {
            int src = notificationMessage.c();
            int opcode = notificationMessage.a();
            if (opcode == Opcode.NODE_RESET_STATUS.value) {
                this.w.g.delete(src);
                if (this.o == Mode.MODE_AUTO_CONNECT) {
                    n2();
                }
            } else if (opcode == Opcode.NODE_ID_STATUS.value) {
                Mode mode = this.o;
                if (mode == Mode.MODE_OTA || mode == Mode.MODE_GATT_CONNECTION) {
                    MeshLogNew.ota("当前准备非节点ota，收到了setNodeIdentity指令回执");
                    NodeIdentityStatusMessage identityStatusMessage = (NodeIdentityStatusMessage) notificationMessage.d();
                    ConnectionFilter filter = (ConnectionFilter) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_CONNECTION_FILTER");
                    if (filter.a == 0) {
                        int target = ((Integer) filter.b).intValue();
                        MeshLogNew.ota("当前准备非节点ota，收到了setNodeIdentity指令回执，目标：" + target + ",src:" + src);
                        s1(src, identityStatusMessage, target);
                    }
                }
            }
        }
    }

    private void s1(int src, NodeIdentityStatusMessage identityStatusMessage, int connectionTarget) {
        Object[] objArr = {new Integer(src), identityStatusMessage, new Integer(connectionTarget)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13188, new Class[]{cls, NodeIdentityStatusMessage.class, cls}, Void.TYPE).isSupported) {
            if (src == connectionTarget) {
                int status = identityStatusMessage.d();
                boolean success = false;
                String desc = "";
                if (status == ConfigStatus.SUCCESS.code) {
                    int identity = identityStatusMessage.c();
                    if (identity == NodeIdentity.RUNNING.code) {
                        MeshLogNew.ota("非节点ota success reconnect target device");
                        success = true;
                    } else {
                        desc = "node identity check error: " + identity;
                        MeshLogNew.ota("非节点ota " + desc);
                    }
                } else {
                    desc = "node identity status error: " + status;
                    MeshLogNew.ota("非节点ota " + desc);
                }
                if (success) {
                    this.F = true;
                    MeshLogNew.ota("非节点ota 断开当前主节点:" + this.J + ",重新扫描连接目标节点");
                    e2();
                    return;
                }
                Mode mode = this.o;
                if (mode == Mode.MODE_OTA) {
                    MeshLogNew.ota("非节点ota 失败");
                    u1(false, desc);
                } else if (mode == Mode.MODE_GATT_CONNECTION) {
                    MeshLogNew.ota("非节点ota MODE_GATT_CONNECTION失败");
                    j1(false, desc);
                }
            }
        }
    }

    private void p1(NotificationMessage notificationMessage) {
        String eventType;
        if (!PatchProxy.proxy(new Object[]{notificationMessage}, this, changeQuickRedirect, false, 13189, new Class[]{NotificationMessage.class}, Void.TYPE).isSupported) {
            StatusMessage statusMessage = notificationMessage.d();
            Iterator<NodeInfo> it = SIGMesh.getInstance().getMeshInfo().nodes.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                NodeInfo node = it.next();
                if (node.meshAddress == notificationMessage.c()) {
                    node.setLastActiveTime(System.currentTimeMillis());
                    node.startOfflineCheckTask(NodeInfo.checkOnOffDelayTime);
                    break;
                }
            }
            if (statusMessage == null) {
                eventType = "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_NOTIFICATION_MESSAGE_UNKNOWN";
            } else {
                eventType = statusMessage.getClass().getName();
            }
            MeshLog.i("com.telink.ble.mesh message notification: " + eventType + "  macAddress=" + this.J);
            h1(new StatusNotificationEvent(this, eventType, notificationMessage));
        }
    }

    public boolean j(MeshMessage meshMessage, int i2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{meshMessage, new Integer(i2)}, this, changeQuickRedirect, false, 13190, new Class[]{MeshMessage.class, Integer.TYPE}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : T1(meshMessage);
    }

    private void a1(String desc) {
        if (!PatchProxy.proxy(new Object[]{desc}, this, changeQuickRedirect, false, 13191, new Class[]{String.class}, Void.TYPE).isSupported) {
            Y0();
            BindingDevice bindingDevice = this.j.j();
            h1(new BindingEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BIND_SUCCESS", bindingDevice, desc + "蓝牙重试次数:" + this.C));
        }
    }

    private void Z0(String desc, int i2) {
        if (!PatchProxy.proxy(new Object[]{desc, new Integer(i2)}, this, changeQuickRedirect, false, 13192, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.o == Mode.MODE_AUTO_CONNECT) {
                com.leedarson.serviceimpl.elkstrays.b.a("上线 onBindFail，发送复位设备指令");
            }
            NodeResetMessage message = new NodeResetMessage(this.E);
            message.D(-1);
            message.c(new MsgExtra("onBindingFail后 删除mesh设备 mac:,addr:" + desc, MeshConstants.TRACE_ID_CONTROL));
            boolean B0 = this.i.B0(message);
            R0("onBindingFail，发送重置指令：" + this.E);
            Y0();
            h1(new BindingEvent(this, "com.telink.ble.com.telink.ble.mesh.EVENT_TYPE_BIND_FAIL", (BindingDevice) this.p.b("com.telink.ble.com.telink.ble.mesh.light.ACTION_BINDING_TARGET"), desc));
        }
    }

    private void Y0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13193, new Class[0], Void.TYPE).isSupported) {
            this.i.z();
            this.B = false;
            A0(false, "onBindingComplete");
        }
    }

    private void r1(String eventType, MeshUpdatingDevice device, String desc, int progress) {
        Class<String> cls = String.class;
        Object[] objArr = {eventType, device, desc, new Integer(progress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13194, new Class[]{cls, MeshUpdatingDevice.class, cls, Integer.TYPE}, Void.TYPE).isSupported) {
            FirmwareUpdatingEvent updatingEvent = new FirmwareUpdatingEvent(this, eventType);
            updatingEvent.c(device);
            updatingEvent.a(desc);
            updatingEvent.b(progress);
            h1(updatingEvent);
        }
    }

    private void q1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13195, new Class[0], Void.TYPE).isSupported) {
            this.B = false;
            A0(false, "onMeshUpdatingComplete");
        }
    }

    private void D1(String eventType, RemoteProvisioningDevice device, String desc) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, RemoteProvisioningDevice.class, cls};
        if (!PatchProxy.proxy(new Object[]{eventType, device, desc}, this, changeQuickRedirect, false, 13196, clsArr, Void.TYPE).isSupported) {
            A0(false, "onRemoteProvisioningComplete");
            RemoteProvisioningEvent event = new RemoteProvisioningEvent(this, eventType);
            event.b(device);
            event.a(desc);
            h1(event);
        }
    }

    private void k2(int address, byte[] deviceKey) {
        Object[] objArr = {new Integer(address), deviceKey};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13197, new Class[]{Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            this.i.u(address, deviceKey);
            this.w.g.put(address, deviceKey);
        }
    }

    public void g(int i2, String str, int i3, Object obj) {
        int i4 = i2;
        boolean z2 = false;
        Object[] objArr = {new Integer(i2), str, new Integer(i3), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 13198, new Class[]{cls, String.class, cls, Object.class}, Void.TYPE).isSupported) {
            String desc = str;
            Object obj2 = obj;
            int state = i2;
            int mode = i3;
            MeshLog.i("access state changed: " + state + " -- " + desc + "  macAddress=" + this.J);
            Mode mode2 = this.o;
            if (mode2 == Mode.MODE_BIND && mode == 1) {
                if (state == 0) {
                    Z0(desc, 4);
                } else if (state == 1) {
                    a1(desc);
                }
            } else if (mode2 == Mode.MODE_MESH_OTA && mode == 2) {
                switch (state) {
                    case 0:
                        q1();
                        r1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_UPDATING_SUCCESS", (MeshUpdatingDevice) null, desc, -1);
                        return;
                    case 1:
                        r1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_UPDATING_PROGRESS", (MeshUpdatingDevice) null, desc, ((Integer) obj2).intValue());
                        return;
                    case 2:
                        r1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_DEVICE_SUCCESS", (MeshUpdatingDevice) obj2, desc, -1);
                        return;
                    case 3:
                        r1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_DEVICE_FAIL", (MeshUpdatingDevice) obj2, desc, -1);
                        return;
                    case 4:
                        q1();
                        r1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_UPDATING_FAIL", (MeshUpdatingDevice) null, desc, -1);
                        return;
                    case 5:
                        q1();
                        r1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_UPDATING_STOPPED", (MeshUpdatingDevice) null, desc, -1);
                        return;
                    case 6:
                        r1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_UPDATING_PREPARED", (MeshUpdatingDevice) null, desc, -1);
                        return;
                    default:
                        return;
                }
            } else if (mode2 == Mode.MODE_REMOTE_PROVISION && mode == 3) {
                if (state == 4) {
                    D1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_REMOTE_PROVISIONING_FAIL", (RemoteProvisioningDevice) obj2, "remote provisioning fail");
                } else if (state == 3) {
                    RemoteProvisioningDevice device = (RemoteProvisioningDevice) obj2;
                    k2(device.i(), device.e());
                    D1("com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_REMOTE_PROVISIONING_SUCCESS", device, "remote provisioning success");
                }
            } else if (mode2 != Mode.MODE_FAST_PROVISION || mode != 4) {
            } else {
                if (state == 17) {
                    j2(false);
                } else if (state == 25 || state == 24) {
                    if (state == 25) {
                        z2 = true;
                    }
                    i1(z2, desc);
                } else if (state == 22) {
                    FastProvisioningDevice device2 = (FastProvisioningDevice) obj2;
                    k2(device2.c(), device2.a());
                    FastProvisioningEvent event = new FastProvisioningEvent(this, "com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_FAST_PROVISIONING_ADDRESS_SET");
                    event.b(device2);
                    h1(event);
                }
            }
        }
    }

    private void i1(boolean success, String desc) {
        String eventType;
        if (!PatchProxy.proxy(new Object[]{new Byte(success ? (byte) 1 : 0), desc}, this, changeQuickRedirect, false, 13199, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            j2(true);
            this.A = false;
            A0(false, "onFastProvisioningComplete");
            O1();
            if (success) {
                eventType = "com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_FAST_PROVISIONING_SUCCESS";
            } else {
                eventType = "com.telink.sig.com.telink.ble.mesh.EVENT_TYPE_FAST_PROVISIONING_FAIL";
            }
            FastProvisioningEvent event = new FastProvisioningEvent(this, eventType);
            event.a(desc);
            h1(event);
        }
    }

    public void j2(boolean pvComplete) {
        if (!PatchProxy.proxy(new Object[]{new Byte(pvComplete ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 13200, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            R0("switch networking: " + pvComplete);
            if (pvComplete) {
                MeshLog.i("setup config back: " + this.w.d + "  macAddress=" + this.J);
                this.i.O0(this.w);
                return;
            }
            FastProvisioningConfiguration configuration = this.m.n();
            MeshConfiguration meshConfiguration = new MeshConfiguration();
            meshConfiguration.e = this.w.e;
            meshConfiguration.d = configuration.f();
            meshConfiguration.g = new SparseArray<>();
            meshConfiguration.a = configuration.d();
            meshConfiguration.b = configuration.c();
            SparseArray<byte[]> sparseArray = new SparseArray<>();
            meshConfiguration.c = sparseArray;
            sparseArray.put(configuration.b(), configuration.a());
            meshConfiguration.f = this.w.f;
            MeshLog.d("setupMeshConfiguration switchNetworking localAddress:" + meshConfiguration.f + "(" + String.format("0x%04X", new Object[]{Integer.valueOf(meshConfiguration.f)}) + "),index:" + meshConfiguration.d);
            this.i.O0(meshConfiguration);
        }
    }

    public void U1(b addDevicesReporter) {
        this.N = addDevicesReporter;
    }

    public b o0() {
        return this.N;
    }

    public void V1(EventCallback callback) {
        this.D = callback;
    }

    public void d0(AddDeviceStepBean stepBean) {
        if (!PatchProxy.proxy(new Object[]{stepBean}, this, changeQuickRedirect, false, 13201, new Class[]{AddDeviceStepBean.class}, Void.TYPE).isSupported) {
            if (o0() != null) {
                Mode mode = this.o;
                if (mode == Mode.MODE_PROVISION || mode == Mode.MODE_BIND) {
                    if (stepBean.isStepStartBleConnect() && o0().b().startToConnectBleTimeStamp == 0) {
                        o0().b().startToConnectBleTimeStamp = System.currentTimeMillis();
                    }
                    o0().a(stepBean);
                }
            }
        }
    }

    public void c0(AutoConnectDeviceStepBean stepBean, String autoConnectState) {
        Class[] clsArr = {AutoConnectDeviceStepBean.class, String.class};
        if (!PatchProxy.proxy(new Object[]{stepBean, autoConnectState}, this, changeQuickRedirect, false, 13202, clsArr, Void.TYPE).isSupported && Mode.MODE_AUTO_CONNECT == this.o) {
            c.c(stepBean);
            ProcedureCollector.autoConnectState = autoConnectState;
        }
    }

    public void K1(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 13203, new Class[]{String.class}, Void.TYPE).isSupported && Mode.MODE_AUTO_CONNECT == this.o) {
            c.f(log);
        }
    }

    public void J1(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 13204, new Class[]{String.class}, Void.TYPE).isSupported && Mode.MODE_PROVISION == this.o) {
            b.d(log);
        }
    }

    public String t0() {
        return this.O;
    }

    public GattConnection y0() {
        return this.c;
    }

    private void R0(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 13205, new Class[]{String.class}, Void.TYPE).isSupported) {
            S0(logMessage, 2);
        }
    }

    private void S0(String logMessage, int level) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(level)}, this, changeQuickRedirect, false, 13206, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLogger.g(logMessage + "," + this.O + ",connectRetry:" + this.C + ",actionMode:" + this.o, "MeshController", level);
        }
    }

    private void T0(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 13207, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshScanLog.d(this.O + "-" + message + "," + this.J);
        }
    }
}
