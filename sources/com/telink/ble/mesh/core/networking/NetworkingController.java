package com.telink.ble.mesh.core.networking;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import com.leedarson.base.utils.e;
import com.leedarson.log.elk.a;
import com.leedarson.serviceimpl.reporters.c;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.Encipher;
import com.telink.ble.mesh.core.MeshUtils;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.Priority;
import com.telink.ble.mesh.core.networking.NetworkLayerPDU;
import com.telink.ble.mesh.core.networking.beacon.MeshBeaconPDU;
import com.telink.ble.mesh.core.networking.beacon.SecureNetworkBeacon;
import com.telink.ble.mesh.core.networking.transport.lower.LowerTransportPDU;
import com.telink.ble.mesh.core.networking.transport.lower.SegmentAcknowledgmentMessage;
import com.telink.ble.mesh.core.networking.transport.lower.SegmentedAccessMessagePDU;
import com.telink.ble.mesh.core.networking.transport.lower.UnsegmentedAccessMessagePDU;
import com.telink.ble.mesh.core.networking.transport.lower.UnsegmentedControlMessagePDU;
import com.telink.ble.mesh.core.networking.transport.upper.UpperTransportAccessPDU;
import com.telink.ble.mesh.core.proxy.ProxyAddAddressMessage;
import com.telink.ble.mesh.core.proxy.ProxyConfigurationMessage;
import com.telink.ble.mesh.core.proxy.ProxyConfigurationPDU;
import com.telink.ble.mesh.core.proxy.ProxyFilterStatusMessage;
import com.telink.ble.mesh.core.proxy.ProxyFilterType;
import com.telink.ble.mesh.core.proxy.ProxySetFilterTypeMessage;
import com.telink.ble.mesh.foundation.MeshConfiguration;
import com.telink.ble.mesh.foundation.MeshController;
import com.telink.ble.mesh.foundation.MeshService;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.ctrl.GroupCtrlAdapter;
import meshsdk.datamgr.MeshDataManager;
import meshsdk.model.NodeInfo;
import meshsdk.util.LDSMeshUtil;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public class NetworkingController {
    public static int a = 225;
    public static ChangeQuickRedirect changeQuickRedirect;
    private Handler A;
    private SegmentAckMessageSentTask B = new SegmentAckMessageSentTask();
    private SegmentBlockWaitingTask C = new SegmentBlockWaitingTask();
    /* access modifiers changed from: private */
    public boolean D = false;
    private Runnable E = new SegmentedMessageTimeoutTask();
    /* access modifiers changed from: private */
    public MeshMessage F;
    /* access modifiers changed from: private */
    public boolean G = false;
    /* access modifiers changed from: private */
    public final Object H = new Object();
    private Set<Integer> I = new LinkedHashSet();
    private int J = 0;
    private final Queue<NetWorkingBytes> K = new ConcurrentLinkedQueue();
    private final Object L = new Object();
    private boolean M = false;
    public String N = "";
    private NetWorkingBytes O;
    private Runnable P = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12898, new Class[0], Void.TYPE).isSupported) {
                MeshLog.i("Networking 240毫秒后从队列中取轮询下一条指令");
                NetworkingController.r(NetworkingController.this);
            }
        }
    };
    private Runnable Q = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12899, new Class[0], Void.TYPE).isSupported) {
                NetworkingController.s(NetworkingController.this, "setFilter 超时了");
                NetworkingController.t(NetworkingController.this, false);
            }
        }
    };
    private int R = 0;
    private int S = 2;
    private Runnable T = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12900, new Class[0], Void.TYPE).isSupported) {
                MeshMessage meshMessage = NetworkingController.this.F;
                if (meshMessage != null) {
                    NetworkingController networkingController = NetworkingController.this;
                    Locale locale = Locale.US;
                    NetworkingController.s(networkingController, String.format(locale, "reliable message retry segmentRxComplete? %B retryCnt: %d %s opcode: %06X", new Object[]{Boolean.valueOf(networkingController.x), Integer.valueOf(meshMessage.p()), meshMessage.getClass().getSimpleName(), Integer.valueOf(meshMessage.k())}));
                    if (!NetworkingController.this.x) {
                        NetworkingController.j(NetworkingController.this);
                    } else if (meshMessage.p() <= 0) {
                        NetworkingController networkingController2 = NetworkingController.this;
                        NetworkingController.s(networkingController2, String.format(locale, "请求的消息:" + meshMessage.getClass().getSimpleName() + "(opcode: %06X)-(propertyId: %04X),重shi后，在超时范围内還是未返回, callback to fail", new Object[]{Integer.valueOf(meshMessage.k()), Integer.valueOf(meshMessage.m())}));
                        NetworkingController.d(NetworkingController.this, false);
                    } else {
                        meshMessage.E(meshMessage.p() - 1);
                        synchronized (NetworkingController.this.H) {
                            boolean unused = NetworkingController.this.G = false;
                            if (NetworkingController.this.D && meshMessage.v()) {
                                NetworkingController.h(NetworkingController.this, true, false);
                            }
                        }
                        NetworkingController.i(NetworkingController.this, meshMessage, true);
                    }
                }
            }
        }
    };
    private Runnable U = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12901, new Class[0], Void.TYPE).isSupported) {
                NetworkingController.k(NetworkingController.this);
                NetworkingController networkingController = NetworkingController.this;
                NetworkingController.s(networkingController, String.format(Locale.US, "segment timeout : lastSeqAuth: 0x%014X -- src: %02d", new Object[]{Long.valueOf(networkingController.v), Integer.valueOf(NetworkingController.this.w)}));
                boolean unused = NetworkingController.this.x = true;
                int unused2 = NetworkingController.this.w = 0;
                long unused3 = NetworkingController.this.v = 0;
            }
        }
    };
    private final String b = "Networking";
    private boolean c = false;
    private AtomicInteger d = new AtomicInteger(1);
    private boolean e = false;
    private byte f;
    private byte[] g;
    private byte[] h;
    private int i;
    private SparseArray<byte[]> j;
    private SparseIntArray k = new SparseIntArray();
    private SparseArray<byte[]> l;
    private int m = 0;
    private long n = 0;
    private int o = 32767;
    private int p = 0;
    private AtomicInteger q = new AtomicInteger(0);
    private SparseArray<SegmentedAccessMessagePDU> r = new SparseArray<>();
    private SparseLongArray s = new SparseLongArray();
    private SparseLongArray t = new SparseLongArray();
    private SparseArray<SegmentedAccessMessagePDU> u = new SparseArray<>();
    /* access modifiers changed from: private */
    public long v = 0;
    /* access modifiers changed from: private */
    public int w = 0;
    /* access modifiers changed from: private */
    public boolean x = true;
    private NetworkingBridge y;
    private int z = 256;

    static /* synthetic */ void d(NetworkingController x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 12891, new Class[]{NetworkingController.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.f0(x1);
        }
    }

    static /* synthetic */ void h(NetworkingController x0, boolean x1, boolean x2) {
        Object[] objArr = {x0, new Byte(x1 ? (byte) 1 : 0), new Byte(x2 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12892, new Class[]{NetworkingController.class, cls, cls}, Void.TYPE).isSupported) {
            x0.T0(x1, x2);
        }
    }

    static /* synthetic */ boolean i(NetworkingController x0, MeshMessage x1, boolean x2) {
        Object[] objArr = {x0, x1, new Byte(x2 ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12893, new Class[]{NetworkingController.class, MeshMessage.class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : x0.r0(x1, x2);
    }

    static /* synthetic */ void j(NetworkingController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12894, new Class[]{NetworkingController.class}, Void.TYPE).isSupported) {
            x0.w0();
        }
    }

    static /* synthetic */ void k(NetworkingController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12895, new Class[]{NetworkingController.class}, Void.TYPE).isSupported) {
            x0.R0();
        }
    }

    static /* synthetic */ void p(NetworkingController x0, int x1, int x2) {
        Object[] objArr = {x0, new Integer(x1), new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12896, new Class[]{NetworkingController.class, cls, cls}, Void.TYPE).isSupported) {
            x0.H0(x1, x2);
        }
    }

    static /* synthetic */ void q(NetworkingController x0, int x1, int x2) {
        Object[] objArr = {x0, new Integer(x1), new Integer(x2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 12897, new Class[]{NetworkingController.class, cls, cls}, Void.TYPE).isSupported) {
            x0.u0(x1, x2);
        }
    }

    static /* synthetic */ void r(NetworkingController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12888, new Class[]{NetworkingController.class}, Void.TYPE).isSupported) {
            x0.q0();
        }
    }

    static /* synthetic */ void s(NetworkingController x0, String x1) {
        Class[] clsArr = {NetworkingController.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12889, clsArr, Void.TYPE).isSupported) {
            x0.W(x1);
        }
    }

    static /* synthetic */ void t(NetworkingController x0, boolean x1) {
        if (!PatchProxy.proxy(new Object[]{x0, new Byte(x1 ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 12890, new Class[]{NetworkingController.class, Boolean.TYPE}, Void.TYPE).isSupported) {
            x0.e0(x1);
        }
    }

    public NetworkingController(HandlerThread handlerThread) {
        this.A = new Handler(handlerThread.getLooper());
        this.l = new SparseArray<>();
        this.j = new SparseArray<>();
    }

    public void M0(NetworkingBridge networkingBridge) {
        this.y = networkingBridge;
    }

    public void O0(MeshConfiguration configuration) {
        if (!PatchProxy.proxy(new Object[]{configuration}, this, changeQuickRedirect, false, 12813, new Class[]{MeshConfiguration.class}, Void.TYPE).isSupported) {
            z();
            int i2 = configuration.d;
            this.m = i2;
            this.n = ((long) i2) & 4294967295L;
            this.d.set(T(configuration.e));
            this.i = configuration.a;
            byte[][] k2Output = Encipher.c(configuration.b);
            this.f = (byte) (k2Output[0][15] & Byte.MAX_VALUE);
            this.g = k2Output[1];
            this.h = k2Output[2];
            this.l = configuration.c;
            this.j = configuration.g;
            this.o = configuration.f;
            MeshLog.d("setupMeshConfiguration setup localAddress:" + this.o + "(" + String.format("0x%04X", new Object[]{Integer.valueOf(this.o)}) + "),sequenceNumber:" + this.d + ",networkkey:" + e.a(configuration.b) + ",encryptionKey:" + e.a(this.g) + ",appkey:" + e.a(I(0)) + ",mac:" + this.N);
        }
    }

    public int P() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12814, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.d.get();
    }

    public void N0(int seq) {
        Object[] objArr = {new Integer(seq)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12815, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.d.set(seq);
        }
    }

    public void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12816, new Class[0], Void.TYPE).isSupported) {
            Handler handler = this.A;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            this.M = false;
            this.D = false;
            this.G = false;
            this.K.clear();
            this.v = 0;
            this.w = 0;
            this.p = 0;
            this.x = true;
            this.k.clear();
            this.r.clear();
            this.u.clear();
            this.I.clear();
            this.e = false;
            this.x = true;
        }
    }

    public void u(int unicastAddress, byte[] deviceKey) {
        Object[] objArr = {new Integer(unicastAddress), deviceKey};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12817, new Class[]{Integer.TYPE, byte[].class}, Void.TYPE).isSupported) {
            this.j.put(unicastAddress, deviceKey);
            W("11111-更新devicekey checkDeviceKey NetworkingController addDeviceKey key:" + unicastAddress + ",value:" + e.b(deviceKey, ""));
        }
    }

    public void G(boolean enable) {
        if (!PatchProxy.proxy(new Object[]{new Byte(enable ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 12818, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.c = enable;
            a = enable ? 225 : 11;
            W("enableDLE: " + enable + " -- value : " + a);
        }
    }

    public int N() {
        return a;
    }

    public void t0(int unicastAddress) {
        Object[] objArr = {new Integer(unicastAddress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12819, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.j.remove(unicastAddress);
        }
    }

    private synchronized void z0(int src, long seqAuth) {
        if (!PatchProxy.proxy(new Object[]{new Integer(src), new Long(seqAuth)}, this, changeQuickRedirect, false, 12820, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
            W(String.format(Locale.US, "save complete seqAuth src: 0x%04X -- seqAuth: 0x%014X", new Object[]{Integer.valueOf(src), Long.valueOf(seqAuth)}));
            this.s.put(src, seqAuth);
        }
    }

    private boolean V(int src, long seqAuth) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(src), new Long(seqAuth)}, this, changeQuickRedirect, false, 12821, new Class[]{Integer.TYPE, Long.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return this.s.get(src, 0) == seqAuth;
    }

    private synchronized void y0(int src, long seqAuth) {
        if (!PatchProxy.proxy(new Object[]{new Integer(src), new Long(seqAuth)}, this, changeQuickRedirect, false, 12822, new Class[]{Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
            W(String.format(Locale.US, "save busy seqAuth src: 0x%04X -- seqAuth: 0x%014X", new Object[]{Integer.valueOf(src), Long.valueOf(seqAuth)}));
            this.t.put(src, seqAuth);
        }
    }

    private boolean U(int src, long seqAuth) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(src), new Long(seqAuth)}, this, changeQuickRedirect, false, 12823, new Class[]{Integer.TYPE, Long.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return this.t.get(src, 0) == seqAuth;
    }

    public void y(byte[] networkId, byte[] beaconKey) {
        Class<byte[]> cls = byte[].class;
        boolean z2 = false;
        if (!PatchProxy.proxy(new Object[]{networkId, beaconKey}, this, changeQuickRedirect, false, 12824, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            if (this.d.get() >= 12582912) {
                z2 = true;
            }
            boolean updatingNeeded = z2;
            SecureNetworkBeacon networkBeacon = SecureNetworkBeacon.b((int) this.n, networkId, beaconKey, updatingNeeded);
            this.e = updatingNeeded;
            if (updatingNeeded) {
                this.n++;
            }
            W(networkBeacon.toString());
            A0(networkBeacon);
        }
    }

    private void b0(long newIvIndex) {
        if (!PatchProxy.proxy(new Object[]{new Long(newIvIndex)}, this, changeQuickRedirect, false, 12825, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            if (newIvIndex > ((long) this.m)) {
                W(String.format(Locale.US, " iv updated to %08X", new Object[]{Long.valueOf(newIvIndex)}));
                this.k.clear();
                this.d.set(0);
                NetworkingBridge networkingBridge = this.y;
                if (networkingBridge != null) {
                    networkingBridge.d(this.d.get(), (int) newIvIndex);
                    return;
                }
                return;
            }
            W(" iv not updated");
        }
    }

    private void a0(long remoteIvIndex, boolean updating) {
        if (!PatchProxy.proxy(new Object[]{new Long(remoteIvIndex), new Byte(updating ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 12826, new Class[]{Long.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
            W(String.format(Locale.US, "iv index received iv: %08X -- updating: %b -- localIv: %08X -- updating: %b ", new Object[]{Long.valueOf(remoteIvIndex), Boolean.valueOf(updating), Long.valueOf(this.n), Boolean.valueOf(this.e)}));
            long dVal = remoteIvIndex - this.n;
            if (dVal == 0) {
                if (!updating && this.e) {
                    this.e = false;
                    b0(remoteIvIndex);
                }
            } else if (dVal > 0) {
                W("larger iv index received");
                if (dVal <= 42) {
                    this.e = updating;
                    this.n = remoteIvIndex;
                    b0(updating ? remoteIvIndex - 1 : remoteIvIndex);
                    return;
                }
                W("iv index dVal greater than 42");
            } else {
                X(" smaller iv index received", 3);
            }
        }
    }

    private int T(int sequenceNumber) {
        Object[] objArr = {new Integer(sequenceNumber)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12827, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int i2 = this.z;
        if (i2 == 0 || i2 == 1) {
            return sequenceNumber;
        }
        int initSno = ((sequenceNumber / i2) + 1) * i2;
        W("init seqNum: " + initSno);
        i0(initSno);
        return initSno;
    }

    private void E0(ProxyConfigurationMessage message, String step) {
        Class[] clsArr = {ProxyConfigurationMessage.class, String.class};
        if (!PatchProxy.proxy(new Object[]{message, step}, this, changeQuickRedirect, false, 12828, clsArr, Void.TYPE).isSupported) {
            F0(C(message.a(), this.o, Q(), this.d.get()), step);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r1 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void w() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 12829(0x321d, float:1.7977E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0016
            return
        L_0x0016:
            r1 = r8
            com.telink.ble.mesh.core.message.MeshMessage r2 = r1.F
            if (r2 == 0) goto L_0x0073
            boolean r2 = r2.t()
            r3 = 1
            r4 = 2
            if (r2 == 0) goto L_0x004a
            java.util.Locale r2 = java.util.Locale.US
            java.lang.Object[] r4 = new java.lang.Object[r4]
            com.telink.ble.mesh.core.message.MeshMessage r5 = r1.F
            int r5 = r5.m()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r0] = r5
            com.telink.ble.mesh.core.message.MeshMessage r5 = r1.F
            int r5 = r5.k()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r3] = r5
            java.lang.String r3 = "取消当前mesh正在发送的消息,propertyId:0x%04X,opcode:0x%04X"
            java.lang.String r2 = java.lang.String.format(r2, r3, r4)
            r1.W(r2)
            goto L_0x0070
        L_0x004a:
            java.util.Locale r2 = java.util.Locale.US
            java.lang.Object[] r4 = new java.lang.Object[r4]
            com.telink.ble.mesh.core.message.MeshMessage r5 = r1.F
            java.lang.Class r5 = r5.getClass()
            java.lang.String r5 = r5.getSimpleName()
            r4[r0] = r5
            com.telink.ble.mesh.core.message.MeshMessage r5 = r1.F
            int r5 = r5.k()
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r3] = r5
            java.lang.String r3 = "取消当前mesh正在发送的消息:%s,opcode:0x%04X"
            java.lang.String r2 = java.lang.String.format(r2, r3, r4)
            r1.W(r2)
        L_0x0070:
            r1.f0(r0)
        L_0x0073:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.networking.NetworkingController.w():void");
    }

    public MeshMessage K() {
        return this.F;
    }

    public boolean B0(MeshMessage meshMessage) {
        byte[] encryptionKey;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{meshMessage}, this, changeQuickRedirect, false, 12830, new Class[]{MeshMessage.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int dst = meshMessage.j();
        if (!V0(dst)) {
            X("发送消息失败 invalid dst address: " + String.format(Locale.US, "%04X", new Object[]{Integer.valueOf(dst)}), 3);
            return false;
        }
        AccessType accessType = meshMessage.g();
        if (accessType == AccessType.APPLICATION) {
            encryptionKey = I(meshMessage.h());
        } else {
            encryptionKey = L(meshMessage.j());
            W("checkDeviceKey sendMeshMessage destinAddress:" + meshMessage.j() + ",encryptionKey(devicekey):" + e.b(encryptionKey, ":"));
        }
        if (encryptionKey == null) {
            X("发送消息失败 access key not found : " + accessType, 3);
            return false;
        }
        meshMessage.x(encryptionKey);
        return r0(meshMessage, false);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v15, resolved type: java.lang.Object} */
    /* JADX WARNING: type inference failed for: r4v13 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean r0(com.telink.ble.mesh.core.message.MeshMessage r34, boolean r35) {
        /*
            r33 = this;
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r34
            java.lang.Byte r2 = new java.lang.Byte
            r9 = r35
            r2.<init>(r9)
            r10 = 1
            r1[r10] = r2
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<com.telink.ble.mesh.core.message.MeshMessage> r0 = com.telink.ble.mesh.core.message.MeshMessage.class
            r6[r8] = r0
            java.lang.Class r7 = java.lang.Boolean.TYPE
            r6[r10] = r7
            r4 = 0
            r5 = 12831(0x321f, float:1.798E-41)
            r2 = r33
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r0.isSupported
            if (r1 == 0) goto L_0x0032
            java.lang.Object r0 = r0.result
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            return r0
        L_0x0032:
            r7 = r33
            r9 = r35
            r6 = r34
            int r5 = r6.j()
            int r4 = r7.o
            int r21 = r6.q()
            com.telink.ble.mesh.core.networking.AccessType r0 = r6.g()
            byte r3 = r0.akf
            com.telink.ble.mesh.core.networking.AccessType r0 = r6.g()
            com.telink.ble.mesh.core.networking.AccessType r1 = com.telink.ble.mesh.core.networking.AccessType.APPLICATION
            if (r0 != r1) goto L_0x005a
            byte[] r0 = r6.f()
            byte r0 = com.telink.ble.mesh.core.Encipher.p(r0)
            r2 = r0
            goto L_0x005c
        L_0x005a:
            r0 = 0
            r2 = r0
        L_0x005c:
            java.util.concurrent.atomic.AtomicInteger r0 = r7.d
            int r1 = r0.get()
            byte[] r15 = r6.l()
            int r14 = r6.r()
            if (r15 == 0) goto L_0x0081
            if (r14 < 0) goto L_0x0081
            int r0 = r15.length
            if (r0 <= r14) goto L_0x0081
            java.util.concurrent.atomic.AtomicInteger r0 = r7.q
            if (r9 == 0) goto L_0x007a
            int r0 = r0.get()
            goto L_0x007e
        L_0x007a:
            int r0 = r0.incrementAndGet()
        L_0x007e:
            byte r0 = (byte) r0
            r15[r14] = r0
        L_0x0081:
            com.telink.ble.mesh.core.networking.AccessLayerPDU r0 = new com.telink.ble.mesh.core.networking.AccessLayerPDU
            int r11 = r6.k()
            r0.<init>(r11, r15)
            r22 = r0
            byte[] r13 = r22.b()
            int r0 = r13.length
            int r11 = a
            if (r0 <= r11) goto L_0x0097
            r0 = r10
            goto L_0x0098
        L_0x0097:
            r0 = r8
        L_0x0098:
            r12 = r0
            r6.F(r12)
            if (r12 == 0) goto L_0x00b2
            java.lang.Object r11 = r7.H
            monitor-enter(r11)
            boolean r0 = r7.D     // Catch:{ all -> 0x00af }
            if (r0 == 0) goto L_0x00ad
            java.lang.String r0 = "segment message send err: segmented busy"
            r7.W(r0)     // Catch:{ all -> 0x00af }
            monitor-exit(r11)     // Catch:{ all -> 0x00af }
            return r8
        L_0x00ad:
            monitor-exit(r11)     // Catch:{ all -> 0x00af }
            goto L_0x00b2
        L_0x00af:
            r0 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x00af }
            throw r0
        L_0x00b2:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r11 = "post access pdu: "
            r0.append(r11)
            byte[] r11 = r22.b()
            java.lang.String r10 = ""
            java.lang.String r10 = com.leedarson.base.utils.e.b(r11, r10)
            r0.append(r10)
            java.lang.String r10 = ",sequenceNumber="
            r0.append(r10)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r7.W(r0)
            int r10 = r7.Q()
            byte[] r0 = r6.f()
            int r11 = r6.q()
            byte r11 = (byte) r11
            com.telink.ble.mesh.core.networking.AccessType r16 = r6.g()
            r17 = r11
            r11 = r7
            r23 = r12
            r12 = r13
            r24 = r13
            r13 = r0
            r25 = r14
            r14 = r17
            r26 = r15
            r15 = r16
            r16 = r10
            r17 = r1
            r18 = r4
            r19 = r5
            com.telink.ble.mesh.core.networking.transport.upper.UpperTransportAccessPDU r27 = r11.F(r12, r13, r14, r15, r16, r17, r18, r19)
            r0 = 3
            if (r27 != 0) goto L_0x0111
            java.lang.String r11 = "create upper transport pdu err: encrypt err"
            r7.X(r11, r0)
            return r8
        L_0x0111:
            boolean r28 = r6.u()
            if (r23 != 0) goto L_0x017b
            java.lang.String r11 = "networking 来了指令-不需要分包"
            r7.W(r11)
            if (r28 == 0) goto L_0x0145
            boolean r11 = r7.G
            if (r11 == 0) goto L_0x013d
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.String r12 = "unsegmented reliable message:"
            r11.append(r12)
            r11.append(r6)
            java.lang.String r12 = ", send err1: busy"
            r11.append(r12)
            java.lang.String r11 = r11.toString()
            r7.X(r11, r0)
            return r8
        L_0x013d:
            r0 = 1
            r7.G = r0
            r7.F = r6
            r7.w0()
        L_0x0145:
            byte[] r0 = r27.e()
            com.telink.ble.mesh.core.networking.transport.lower.UnsegmentedAccessMessagePDU r0 = r7.E(r0, r3, r2)
            byte[] r12 = r0.e()
            int r13 = r6.i()
            int r14 = r6.s()
            com.telink.ble.mesh.core.message.Priority r19 = r6.b()
            java.lang.String r20 = r6.a()
            r11 = r7
            r15 = r4
            r16 = r5
            r17 = r10
            r18 = r1
            com.telink.ble.mesh.core.networking.NetworkLayerPDU r8 = r11.B(r12, r13, r14, r15, r16, r17, r18, r19, r20)
            r7.C0(r8)
            r29 = r1
            r30 = r2
            r31 = r3
            r3 = r4
            r32 = r6
            goto L_0x0236
        L_0x017b:
            java.lang.Object r15 = r7.H
            monitor-enter(r15)
            if (r28 == 0) goto L_0x019f
            boolean r11 = r7.G     // Catch:{ all -> 0x0192 }
            if (r11 == 0) goto L_0x018c
            java.lang.String r11 = "segmented reliable message send err: busy"
            r7.X(r11, r0)     // Catch:{ all -> 0x0192 }
            monitor-exit(r15)     // Catch:{ all -> 0x0192 }
            return r8
        L_0x018c:
            r0 = 1
            r7.G = r0     // Catch:{ all -> 0x0192 }
            r7.F = r6     // Catch:{ all -> 0x0192 }
            goto L_0x019f
        L_0x0192:
            r0 = move-exception
            r29 = r1
            r30 = r2
            r31 = r3
            r3 = r4
            r32 = r6
            r4 = r15
            goto L_0x0248
        L_0x019f:
            byte[] r0 = r27.e()     // Catch:{ all -> 0x023d }
            r29 = r1
            r1 = r7
            r30 = r2
            r2 = r0
            r31 = r3
            r14 = r4
            r4 = r30
            r13 = r5
            r5 = r21
            r32 = r6
            r6 = r29
            android.util.SparseArray r0 = r1.D(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0238 }
            int r1 = r0.size()     // Catch:{ all -> 0x0238 }
            if (r1 != 0) goto L_0x01c7
            monitor-exit(r15)     // Catch:{ all -> 0x01c1 }
            return r8
        L_0x01c1:
            r0 = move-exception
            r5 = r13
            r3 = r14
            r4 = r15
            goto L_0x0248
        L_0x01c7:
            java.lang.String r1 = "send segmented access message"
            r7.W(r1)     // Catch:{ all -> 0x0238 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0238 }
            r1.<init>()     // Catch:{ all -> 0x0238 }
            r2 = 0
        L_0x01d3:
            int r3 = r0.size()     // Catch:{ all -> 0x0238 }
            if (r2 >= r3) goto L_0x020f
            java.lang.Object r3 = r0.get(r2)     // Catch:{ all -> 0x0238 }
            com.telink.ble.mesh.core.networking.transport.lower.SegmentedAccessMessagePDU r3 = (com.telink.ble.mesh.core.networking.transport.lower.SegmentedAccessMessagePDU) r3     // Catch:{ all -> 0x0238 }
            byte[] r12 = r3.p()     // Catch:{ all -> 0x0238 }
            int r3 = r32.i()     // Catch:{ all -> 0x0238 }
            int r4 = r32.s()     // Catch:{ all -> 0x0238 }
            int r18 = r29 + r2
            com.telink.ble.mesh.core.message.Priority r19 = r32.b()     // Catch:{ all -> 0x0238 }
            java.lang.String r20 = r32.a()     // Catch:{ all -> 0x0238 }
            r11 = r7
            r5 = r13
            r13 = r3
            r3 = r14
            r14 = r4
            r4 = r15
            r15 = r3
            r16 = r5
            r17 = r10
            com.telink.ble.mesh.core.networking.NetworkLayerPDU r6 = r11.B(r12, r13, r14, r15, r16, r17, r18, r19, r20)     // Catch:{ all -> 0x024a }
            r1.add(r6)     // Catch:{ all -> 0x024a }
            int r2 = r2 + 1
            r14 = r3
            r15 = r4
            r13 = r5
            goto L_0x01d3
        L_0x020f:
            r5 = r13
            r3 = r14
            r4 = r15
            boolean r2 = com.telink.ble.mesh.core.MeshUtils.r(r5)     // Catch:{ all -> 0x024a }
            if (r2 == 0) goto L_0x022d
            android.util.SparseArray r2 = r0.clone()     // Catch:{ all -> 0x024a }
            r7.u = r2     // Catch:{ all -> 0x024a }
            r7.Q0()     // Catch:{ all -> 0x024a }
            int r2 = r32.i()     // Catch:{ all -> 0x024a }
            int r6 = r32.s()     // Catch:{ all -> 0x024a }
            r7.P0(r2, r6, r3, r5)     // Catch:{ all -> 0x024a }
            goto L_0x0232
        L_0x022d:
            if (r28 == 0) goto L_0x0232
            r7.w0()     // Catch:{ all -> 0x024a }
        L_0x0232:
            r7.D0(r1)     // Catch:{ all -> 0x024a }
            monitor-exit(r4)     // Catch:{ all -> 0x024a }
        L_0x0236:
            r0 = 1
            return r0
        L_0x0238:
            r0 = move-exception
            r5 = r13
            r3 = r14
            r4 = r15
            goto L_0x0248
        L_0x023d:
            r0 = move-exception
            r29 = r1
            r30 = r2
            r31 = r3
            r3 = r4
            r32 = r6
            r4 = r15
        L_0x0248:
            monitor-exit(r4)     // Catch:{ all -> 0x024a }
            throw r0
        L_0x024a:
            r0 = move-exception
            goto L_0x0248
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.networking.NetworkingController.r0(com.telink.ble.mesh.core.message.MeshMessage, boolean):boolean");
    }

    public void s0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12832, new Class[0], Void.TYPE).isSupported) {
            this.J = 0;
            this.A.removeCallbacks(this.Q);
            this.A.postDelayed(this.Q, GroupCtrlAdapter.RETRY_TIMEOUT);
            W("bind大步骤--setFilter小步骤1--- (小小步骤1）发送设置白名单消息，开启3s超时");
            L0(ProxyFilterType.WhiteList);
        }
    }

    public void L0(ProxyFilterType filterType) {
        if (!PatchProxy.proxy(new Object[]{filterType}, this, changeQuickRedirect, false, 12833, new Class[]{ProxyFilterType.class}, Void.TYPE).isSupported) {
            E0(new ProxySetFilterTypeMessage(filterType.value), "filterType");
        }
    }

    private void v(int[] addressArray) {
        if (!PatchProxy.proxy(new Object[]{addressArray}, this, changeQuickRedirect, false, 12834, new Class[]{int[].class}, Void.TYPE).isSupported) {
            E0(new ProxyAddAddressMessage(addressArray), "addFilterAddress");
        }
    }

    private boolean V0(int address) {
        return address != 0;
    }

    private byte[] I(int appKeyIndex) {
        Object[] objArr = {new Integer(appKeyIndex)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 12835, new Class[]{Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        SparseArray<byte[]> sparseArray = this.l;
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(appKeyIndex);
    }

    private byte[] L(int unicastAddress) {
        Object[] objArr = {new Integer(unicastAddress)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 12836, new Class[]{Integer.TYPE}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        SparseArray<byte[]> sparseArray = this.j;
        if (sparseArray == null) {
            return null;
        }
        return sparseArray.get(unicastAddress);
    }

    private void Q0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12837, new Class[0], Void.TYPE).isSupported) {
            this.D = true;
            this.A.removeCallbacks(this.E);
            this.A.postDelayed(this.E, 15000);
        }
    }

    private void P0(int ctl, int ttl, int src, int dst) {
        Object[] objArr = {new Integer(ctl), new Integer(ttl), new Integer(src), new Integer(dst)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12838, clsArr, Void.TYPE).isSupported) {
            this.A.removeCallbacks(this.C);
            this.C.e(ctl, ttl, src, dst);
            this.A.postDelayed(this.C, O(ttl, true));
        }
    }

    private void T0(boolean complete, boolean success) {
        Object[] objArr = {new Byte(complete ? (byte) 1 : 0), new Byte(success ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12839, clsArr, Void.TYPE).isSupported) {
            W(String.format(Locale.US, "stop segmented block waiting, complete - %B success - %B", new Object[]{Boolean.valueOf(complete), Boolean.valueOf(success)}));
            this.A.removeCallbacks(this.C);
            if (complete) {
                h0(success);
            }
        }
    }

    private void h0(boolean success) {
        if (!PatchProxy.proxy(new Object[]{new Byte(success ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 12840, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            W("segmented message complete, success? : " + success);
            A(success);
            if (!this.G) {
                return;
            }
            if (success) {
                w0();
                return;
            }
            W("SUFUN.NetworkingController onSegmentedMessageComplete segmented message complete but Fail");
            f0(false);
        }
    }

    private void A(boolean success) {
        if (!PatchProxy.proxy(new Object[]{new Byte(success ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 12841, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.D = false;
            this.A.removeCallbacks(this.E);
            this.u.clear();
            NetworkingBridge networkingBridge = this.y;
            if (networkingBridge != null) {
                networkingBridge.f(success);
            }
        }
    }

    private long O(int ttl, boolean outer) {
        long timeout;
        int queueSize;
        Object[] objArr = {new Integer(ttl), new Byte(outer ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 12842, new Class[]{Integer.TYPE, Boolean.TYPE}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        int segmentAckTimeout = (ttl * 50) + 200;
        if (outer) {
            synchronized (this.K) {
                queueSize = this.K.size();
            }
            timeout = ((long) (segmentAckTimeout + IjkMediaCodecInfo.RANK_SECURE)) + (((long) queueSize) * 240);
        } else {
            timeout = (long) (segmentAckTimeout + IjkMediaCodecInfo.RANK_SECURE);
        }
        W("get segment ack timeout: " + timeout);
        return timeout;
    }

    private long M() {
        int queueSize;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12843, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        synchronized (this.K) {
            queueSize = this.K.size();
        }
        long timeout = ((long) (this.c ? 2560 : AVIOCTRLDEFs.IOTYPE_USER_IPCAM_GET_SAVE_DROPBOX_REQ)) + (((long) queueSize) * 240);
        W("set reliable message timeout:" + timeout);
        return timeout;
    }

    private void S() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12844, new Class[0], Void.TYPE).isSupported) {
            i0(this.d.incrementAndGet());
        }
    }

    private void D0(List<NetworkLayerPDU> networkPduList) {
        if (!PatchProxy.proxy(new Object[]{networkPduList}, this, changeQuickRedirect, false, 12845, new Class[]{List.class}, Void.TYPE).isSupported) {
            W("networking 来了指令-需要分包,size:" + networkPduList.size());
            if (this.y != null) {
                for (NetworkLayerPDU networkLayerPDU : networkPduList) {
                    byte[] networkPduPayload = networkLayerPDU.e();
                    W("multi network pdu: " + e.b(networkPduPayload, ":"));
                    c0(networkPduPayload, networkLayerPDU.h(), networkLayerPDU.k(), networkLayerPDU.i());
                }
            }
        }
    }

    private void C0(NetworkLayerPDU networkPdu) {
        if (!PatchProxy.proxy(new Object[]{networkPdu}, this, changeQuickRedirect, false, 12846, new Class[]{NetworkLayerPDU.class}, Void.TYPE).isSupported) {
            if (this.y != null) {
                c0(networkPdu.e(), networkPdu.h(), networkPdu.k(), networkPdu.i());
            }
        }
    }

    private void F0(ProxyConfigurationPDU networkPdu, String step) {
        Class[] clsArr = {ProxyConfigurationPDU.class, String.class};
        if (!PatchProxy.proxy(new Object[]{networkPdu, step}, this, changeQuickRedirect, false, 12847, clsArr, Void.TYPE).isSupported) {
            if (this.y != null) {
                this.y.b((byte) 2, networkPdu.e(), step);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x007f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0081, code lost:
        r2 = r0.K;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0083, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r1 = new com.telink.ble.mesh.core.networking.NetWorkingBytes(r11, r12, r13, r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x008d, code lost:
        if (r13.b != 150) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x008f, code lost:
        r3 = new java.util.concurrent.ConcurrentLinkedQueue<>();
        r3.addAll(r0.K);
        r0.K.clear();
        r0.K.add(r1);
        r0.K.addAll(r3);
        r3.clear();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ac, code lost:
        r0.K.add(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00b1, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b2, code lost:
        r1 = r0.L;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00b4, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00b7, code lost:
        if (r0.M != false) goto L_0x00be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00b9, code lost:
        r0.M = true;
        q0();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00be, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c0(byte[] r11, int r12, com.telink.ble.mesh.core.message.Priority r13, java.lang.String r14) {
        /*
            r10 = this;
            r0 = 4
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r11
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r12)
            r9 = 1
            r1[r9] = r2
            r2 = 2
            r1[r2] = r13
            r3 = 3
            r1[r3] = r14
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<byte[]> r0 = byte[].class
            r6[r8] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r9] = r0
            java.lang.Class<com.telink.ble.mesh.core.message.Priority> r0 = com.telink.ble.mesh.core.message.Priority.class
            r6[r2] = r0
            java.lang.Class<java.lang.String> r0 = java.lang.String.class
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 12848(0x3230, float:1.8004E-41)
            r2 = r10
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0039
            return
        L_0x0039:
            r0 = r10
            com.telink.ble.mesh.foundation.MeshService r1 = com.telink.ble.mesh.foundation.MeshService.k()
            boolean r1 = r1.p()
            if (r1 == 0) goto L_0x00c9
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "networking 来了一条指令extra:"
            r1.append(r2)
            r1.append(r14)
            java.lang.String r2 = " 添加到network层队列 当前忙?-"
            r1.append(r2)
            boolean r2 = r0.M
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.W(r1)
            java.lang.Object r1 = r0.L
            monitor-enter(r1)
            boolean r2 = r0.M     // Catch:{ all -> 0x00c6 }
            if (r2 != 0) goto L_0x0080
            int r2 = r0.p     // Catch:{ all -> 0x00c6 }
            if (r12 != r2) goto L_0x006f
            r2 = r9
            goto L_0x0070
        L_0x006f:
            r2 = r8
        L_0x0070:
            if (r2 == 0) goto L_0x0080
            java.lang.String r3 = "networking 主节点直接发送 "
            r0.W(r3)     // Catch:{ all -> 0x00c6 }
            com.telink.ble.mesh.core.networking.NetworkingBridge r3 = r0.y     // Catch:{ all -> 0x00c6 }
            if (r3 == 0) goto L_0x007e
            r3.b(r8, r11, r14)     // Catch:{ all -> 0x00c6 }
        L_0x007e:
            monitor-exit(r1)     // Catch:{ all -> 0x00c6 }
            return
        L_0x0080:
            monitor-exit(r1)     // Catch:{ all -> 0x00c6 }
            java.util.Queue<com.telink.ble.mesh.core.networking.NetWorkingBytes> r2 = r0.K
            monitor-enter(r2)
            com.telink.ble.mesh.core.networking.NetWorkingBytes r1 = new com.telink.ble.mesh.core.networking.NetWorkingBytes     // Catch:{ all -> 0x00c3 }
            r1.<init>(r11, r12, r13, r14)     // Catch:{ all -> 0x00c3 }
            int r3 = r13.b     // Catch:{ all -> 0x00c3 }
            r4 = 150(0x96, float:2.1E-43)
            if (r3 != r4) goto L_0x00ac
            java.util.concurrent.ConcurrentLinkedQueue r3 = new java.util.concurrent.ConcurrentLinkedQueue     // Catch:{ all -> 0x00c3 }
            r3.<init>()     // Catch:{ all -> 0x00c3 }
            java.util.Queue<com.telink.ble.mesh.core.networking.NetWorkingBytes> r4 = r0.K     // Catch:{ all -> 0x00c3 }
            r3.addAll(r4)     // Catch:{ all -> 0x00c3 }
            java.util.Queue<com.telink.ble.mesh.core.networking.NetWorkingBytes> r4 = r0.K     // Catch:{ all -> 0x00c3 }
            r4.clear()     // Catch:{ all -> 0x00c3 }
            java.util.Queue<com.telink.ble.mesh.core.networking.NetWorkingBytes> r4 = r0.K     // Catch:{ all -> 0x00c3 }
            r4.add(r1)     // Catch:{ all -> 0x00c3 }
            java.util.Queue<com.telink.ble.mesh.core.networking.NetWorkingBytes> r4 = r0.K     // Catch:{ all -> 0x00c3 }
            r4.addAll(r3)     // Catch:{ all -> 0x00c3 }
            r3.clear()     // Catch:{ all -> 0x00c3 }
            goto L_0x00b1
        L_0x00ac:
            java.util.Queue<com.telink.ble.mesh.core.networking.NetWorkingBytes> r3 = r0.K     // Catch:{ all -> 0x00c3 }
            r3.add(r1)     // Catch:{ all -> 0x00c3 }
        L_0x00b1:
            monitor-exit(r2)     // Catch:{ all -> 0x00c3 }
            java.lang.Object r1 = r0.L
            monitor-enter(r1)
            boolean r2 = r0.M     // Catch:{ all -> 0x00c0 }
            if (r2 != 0) goto L_0x00be
            r0.M = r9     // Catch:{ all -> 0x00c0 }
            r0.q0()     // Catch:{ all -> 0x00c0 }
        L_0x00be:
            monitor-exit(r1)     // Catch:{ all -> 0x00c0 }
            goto L_0x00ee
        L_0x00c0:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00c0 }
            throw r2
        L_0x00c3:
            r1 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x00c3 }
            throw r1
        L_0x00c6:
            r2 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00c6 }
            throw r2
        L_0x00c9:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "networking 来了一条指令extra:"
            r1.append(r2)
            r1.append(r14)
            java.lang.String r2 = " 直接发送  当前忙?-"
            r1.append(r2)
            boolean r2 = r0.M
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.W(r1)
            com.telink.ble.mesh.core.networking.NetworkingBridge r1 = r0.y
            if (r1 == 0) goto L_0x00ee
            r1.b(r8, r11, r14)
        L_0x00ee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.networking.NetworkingController.c0(byte[], int, com.telink.ble.mesh.core.message.Priority, java.lang.String):void");
    }

    public Queue<NetWorkingBytes> R() {
        return this.K;
    }

    private void q0() {
        NetWorkingBytes netWorkingBytes;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12849, new Class[0], Void.TYPE).isSupported) {
            boolean hasDeviceFocusCmd = false;
            synchronized (this.K) {
                if (this.K.peek() != null) {
                    NetWorkingBytes result = null;
                    int order = -1;
                    for (NetWorkingBytes itemwork : this.K) {
                        if (MeshDataManager.deviceFocus != null && itemwork.a() == MeshDataManager.deviceFocus.meshAddress && (!hasDeviceFocusCmd || itemwork.d().b > order)) {
                            hasDeviceFocusCmd = true;
                            MeshLog.i(String.format("networking 匹配到focus的当前指令target: 0x%04X order:%d ,extra:" + itemwork.b(), new Object[]{Integer.valueOf(itemwork.a()), Integer.valueOf(itemwork.d().b)}));
                            result = itemwork;
                            order = itemwork.d().b;
                        }
                        if (!hasDeviceFocusCmd && itemwork.d().b > order) {
                            order = itemwork.d().b;
                            result = itemwork;
                            hasDeviceFocusCmd = false;
                        }
                    }
                    if (result != null) {
                        boolean ret = this.K.remove(result);
                        if (!ret) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("networking 指令优先级--移除执行的指令:");
                            sb.append(result);
                            sb.append(" ");
                            sb.append(ret ? "成功" : "失败");
                            MeshLog.e(sb.toString());
                        }
                        Queue<NetWorkingBytes> tempQueue = new ConcurrentLinkedQueue<>();
                        tempQueue.addAll(this.K);
                        this.K.clear();
                        boolean ret2 = this.K.add(result);
                        this.K.addAll(tempQueue);
                        tempQueue.clear();
                    }
                }
                netWorkingBytes = this.K.poll();
            }
            if (netWorkingBytes == null) {
                synchronized (this.L) {
                    this.M = false;
                    MeshLog.e(" networking 无下一条消息，networkingBusy =false");
                }
                return;
            }
            byte[] payload = netWorkingBytes.c();
            W("networking 取到指令 执行发送,isDeviceFocusCmd:" + hasDeviceFocusCmd + ", order:" + netWorkingBytes.d().b + ",extra:" + netWorkingBytes.b());
            NetworkingBridge networkingBridge = this.y;
            if (networkingBridge != null) {
                networkingBridge.b((byte) 0, payload, netWorkingBytes.b());
            }
            this.O = netWorkingBytes;
            this.A.removeCallbacks(this.P);
            this.A.postDelayed(this.P, 240);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0024, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void i0(int r9) {
        /*
            r8 = this;
            r0 = 1
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Integer r2 = new java.lang.Integer
            r2.<init>(r9)
            r3 = 0
            r1[r3] = r2
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r0 = 0
            r5 = 12850(0x3232, float:1.8007E-41)
            r2 = r8
            r3 = r4
            r4 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r8
            com.telink.ble.mesh.core.networking.NetworkingBridge r1 = r0.y
            if (r1 == 0) goto L_0x0037
            int r2 = r0.z
            if (r2 == 0) goto L_0x0031
            int r2 = r9 % r2
            if (r2 != 0) goto L_0x0037
        L_0x0031:
            long r2 = r0.n
            int r2 = (int) r2
            r1.d(r9, r2)
        L_0x0037:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.networking.NetworkingController.i0(int):void");
    }

    public void l0(byte[] payload, byte[] networkId, byte[] networkBeaconKey) {
        Class<byte[]> cls = byte[].class;
        Class[] clsArr = {cls, cls, cls};
        if (!PatchProxy.proxy(new Object[]{payload, networkId, networkBeaconKey}, this, changeQuickRedirect, false, 12851, clsArr, Void.TYPE).isSupported) {
            SecureNetworkBeacon networkBeacon = SecureNetworkBeacon.c(payload);
            if (networkBeacon != null) {
                W("SecureNetworkBeacon received: " + networkBeacon.toString());
                a y2 = a.y(this);
                y2.p("SecureNetworkBeacon received: " + networkBeacon.toString()).t("LdsBleMesh").o("info").s("parseMeshBeacon").a().b();
                if (networkBeacon.j(networkId, networkBeaconKey)) {
                    a0(((long) networkBeacon.g()) & 4294967295L, networkBeacon.i());
                    return;
                }
                W("network beacon check err");
                return;
            }
            W("network beacon parse err");
        }
    }

    private int H(int ivi) {
        boolean ivChecked = true;
        Object[] objArr = {new Integer(ivi)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12852, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        W(String.format(Locale.US, "getAcceptedIvIndex : %08X", new Object[]{Long.valueOf(this.n)}) + " ivi: " + ivi);
        long j2 = this.n;
        if ((j2 & 1) != ((long) ivi)) {
            ivChecked = false;
        }
        if (!ivChecked) {
            j2--;
        }
        return (int) j2;
    }

    private int Q() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12853, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int re = (int) (!this.e ? this.n : this.n - 1);
        W(String.format(Locale.US, "getTransmitIvIndex : %08X", new Object[]{Integer.valueOf(re)}));
        return re;
    }

    private void A0(MeshBeaconPDU meshBeaconPDU) {
        if (!PatchProxy.proxy(new Object[]{meshBeaconPDU}, this, changeQuickRedirect, false, 12854, new Class[]{MeshBeaconPDU.class}, Void.TYPE).isSupported) {
            if (this.y != null) {
                if (meshBeaconPDU instanceof SecureNetworkBeacon) {
                    SecureNetworkBeacon secureNetworkBeacon = (SecureNetworkBeacon) meshBeaconPDU;
                    c.f("checksequenceNumber param==> ivIndex:" + secureNetworkBeacon.g() + ",beaconType:" + secureNetworkBeacon.e() + ",flags:" + secureNetworkBeacon.f() + ",networkID:" + e.a(secureNetworkBeacon.h()) + ",authenticationValue:" + e.a(secureNetworkBeacon.d()));
                }
                this.y.b((byte) 1, meshBeaconPDU.a(), "sendMeshBeaconPdu");
            }
        }
    }

    public void m0(byte[] payload) {
        if (!PatchProxy.proxy(new Object[]{payload}, this, changeQuickRedirect, false, 12855, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            NetworkLayerPDU networkLayerPDU = new NetworkLayerPDU(new NetworkLayerPDU.NetworkEncryptionSuite(H((payload[0] & 255) >> 7), this.g, this.h, this.f));
            if (!networkLayerPDU.p(payload)) {
                X("network layer parse err", 3);
                a y2 = a.y(this);
                y2.p("network layer parse err payload: " + e.a(payload)).t("LdsBleMesh").o("info").s("parseNetworkPdu").a().b();
            } else if (!W0(networkLayerPDU)) {
                X("network pdu sequence number check err", 3);
            } else if (networkLayerPDU.g() == 0) {
                j0(networkLayerPDU);
            } else {
                k0(networkLayerPDU);
            }
        }
    }

    public void n0(byte[] payload) {
        if (!PatchProxy.proxy(new Object[]{payload}, this, changeQuickRedirect, false, 12856, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            ProxyConfigurationPDU proxyNetworkPdu = new ProxyConfigurationPDU(new NetworkLayerPDU.NetworkEncryptionSuite(H((payload[0] & 255) >> 7), this.g, this.h, this.f));
            if (!proxyNetworkPdu.p(payload)) {
                return;
            }
            if (!W0(proxyNetworkPdu)) {
                X("proxy config pdu sequence number check err", 3);
                return;
            }
            W(String.format(Locale.US, "proxy network pdu src: %04X dst: %04X", new Object[]{Integer.valueOf(proxyNetworkPdu.m()), Integer.valueOf(proxyNetworkPdu.h())}));
            d0(proxyNetworkPdu.n(), proxyNetworkPdu.m());
        }
    }

    private void d0(byte[] proxyConfigMessage, int src) {
        if (!PatchProxy.proxy(new Object[]{proxyConfigMessage, new Integer(src)}, this, changeQuickRedirect, false, 12857, new Class[]{byte[].class, Integer.TYPE}, Void.TYPE).isSupported) {
            W("onProxyConfigurationNotify: " + e.b(proxyConfigMessage, ":"));
            ProxyFilterStatusMessage proxyFilterStatusMessage = ProxyFilterStatusMessage.b(proxyConfigMessage);
            if (proxyFilterStatusMessage != null && proxyFilterStatusMessage.c() == ProxyFilterType.WhiteList.value) {
                int i2 = this.J;
                if (i2 < 0) {
                    X("filter init action not started!", 3);
                    return;
                }
                this.p = src;
                int i3 = i2 + 1;
                this.J = i3;
                if (i3 == 1) {
                    W("bind大步骤--setFilter小步骤1--- (小小步骤2）发送addFilterAddress消息");
                    v(new int[]{this.o, 65535});
                } else if (i3 == 2) {
                    W("bind大步骤--setFilter小步骤1--- success");
                    e0(true);
                }
            }
        }
    }

    public void v0() {
        this.R = 0;
    }

    private void e0(boolean success) {
        if (!PatchProxy.proxy(new Object[]{new Byte(success ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 12858, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.J = -1;
            if (success) {
                this.A.removeCallbacks(this.Q);
                if (this.y != null) {
                    W("bind setFilter 成功回调 ");
                    this.y.k(true, this.p);
                }
            } else if (MeshDataManager.flagNetConfingAdddevices) {
                W("proxyFilterInit 超时了 当前正在配网，bind setFilter失败, 主meshControl mode: " + MeshService.k().f());
                int i2 = this.R;
                if (i2 >= this.S) {
                    this.R = 0;
                    if (this.y != null) {
                        W("proxyFilterInit 超时了 bind setFilter 重试2次 还是失败，回调bindfail ");
                        this.y.k(success, this.p);
                        return;
                    }
                    return;
                }
                this.R = i2 + 1;
                W("proxyFilterInit 超时了 bind setFilter 失败，重试第:" + this.R + "次");
                NetworkingBridge networkingBridge = this.y;
                if (networkingBridge != null) {
                    networkingBridge.h(true);
                }
            } else if (MeshService.k().f() != MeshController.Mode.MODE_BIND) {
                int currenNum = this.d.get();
                int i3 = this.z;
                int latestValue = ((currenNum / i3) + 1) * i3;
                this.d.set(latestValue);
                W("上线 setFilter失败 连接mesh网络步进更新squNum为：" + latestValue + "  macAddress=" + this.N);
                MeshService.k().n(false, "onProxyInitComplete fail");
                i0(latestValue);
            }
        }
    }

    private boolean W0(NetworkLayerPDU networkLayerPDU) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{networkLayerPDU}, this, changeQuickRedirect, false, 12859, new Class[]{NetworkLayerPDU.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        int src = networkLayerPDU.m();
        int pduSequenceNumber = networkLayerPDU.l();
        int deviceSequenceNumber = this.k.get(src, -1);
        if (deviceSequenceNumber == -1) {
            this.k.put(src, pduSequenceNumber);
            return true;
        } else if (pduSequenceNumber > deviceSequenceNumber) {
            this.k.put(src, pduSequenceNumber);
            return true;
        } else {
            Locale locale = Locale.US;
            W(String.format(locale, "validate sequence number error  src: %04X -- pdu-sno: %06X -- dev-sno: %06X", new Object[]{Integer.valueOf(src), Integer.valueOf(pduSequenceNumber), Integer.valueOf(deviceSequenceNumber)}));
            a.y(this).p(String.format(locale, "validate sequence number error  src: %04X -- pdu-sno: %06X -- dev-sno: %06X", new Object[]{Integer.valueOf(src), Integer.valueOf(pduSequenceNumber), Integer.valueOf(deviceSequenceNumber)})).t("LdsBleMesh").o("info").s("parseNetworkPdu").a().b();
            return false;
        }
    }

    private void k0(NetworkLayerPDU networkLayerPDU) {
        if (!PatchProxy.proxy(new Object[]{networkLayerPDU}, this, changeQuickRedirect, false, 12860, new Class[]{NetworkLayerPDU.class}, Void.TYPE).isSupported) {
            byte[] lowerTransportPduData = networkLayerPDU.n();
            int segOpcode = lowerTransportPduData[0] & 255;
            int seg = segOpcode >> 7;
            int opcode = segOpcode & NeedPermissionEvent.PER_IPC_SPEAK_PERM;
            W("parse control message  seg:" + seg + " -- opcode:" + opcode);
            if (seg != LowerTransportPDU.e) {
                return;
            }
            if (opcode == 0) {
                SegmentAcknowledgmentMessage segmentAckMessage = new SegmentAcknowledgmentMessage();
                if (segmentAckMessage.d(lowerTransportPduData)) {
                    g0(segmentAckMessage);
                }
            } else if (opcode == 10) {
                Z(networkLayerPDU.m(), networkLayerPDU.h(), lowerTransportPduData);
            }
        }
    }

    private void Z(int src, int dst, byte[] transportPdu) {
        Object[] objArr = {new Integer(src), new Integer(dst), transportPdu};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12861, new Class[]{cls, cls, byte[].class}, Void.TYPE).isSupported) {
            W("on heart beat notify: " + e.b(transportPdu, ":"));
            NetworkingBridge networkingBridge = this.y;
            if (networkingBridge != null) {
                networkingBridge.i(src, dst, transportPdu);
            }
        }
    }

    private void g0(SegmentAcknowledgmentMessage segmentAckMessage) {
        if (!PatchProxy.proxy(new Object[]{segmentAckMessage}, this, changeQuickRedirect, false, 12862, new Class[]{SegmentAcknowledgmentMessage.class}, Void.TYPE).isSupported) {
            W("onSegmentAckMessageReceived: " + segmentAckMessage.toString());
            if (this.D) {
                u0(segmentAckMessage.c(), segmentAckMessage.b());
            } else {
                X("Segment Acknowledgment Message err: segmented messages not sending", 3);
            }
        }
    }

    private void u0(int i2, int i3) {
        int messageSegN;
        int i4;
        List<NetworkLayerPDU> networkLayerPduList;
        int i5 = 0;
        int i6 = 1;
        Object[] objArr = {new Integer(i2), new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12863, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            int blockAck = i3;
            int seqZero = i2;
            SparseArray<SegmentedAccessMessagePDU> messageBuffer = this.u.clone();
            W("resendSegmentedMessages: seqZero: " + seqZero + " block ack: " + blockAck + " buffer size: " + messageBuffer.size());
            if (messageBuffer.size() != 0) {
                SegmentedAccessMessagePDU message0 = messageBuffer.get(messageBuffer.keyAt(0));
                int messageSeqZero = message0.f();
                if (seqZero != -1) {
                    if (seqZero == messageSeqZero) {
                        T0(false, false);
                    } else {
                        return;
                    }
                }
                int ctl = this.C.c;
                int ttl = this.C.d;
                int src = this.C.f;
                int dst = this.C.q;
                int messageSegN2 = message0.c();
                int ivIndex = Q();
                int sequenceNumber = this.d.get();
                List<NetworkLayerPDU> networkLayerPduList2 = new ArrayList<>();
                int i7 = 0;
                int addedValue = 0;
                while (i7 <= messageSegN2) {
                    if (((MeshUtils.a(i7) & blockAck) != 0 ? i6 : i5) == 0) {
                        SegmentedAccessMessagePDU messagePDU = messageBuffer.get(i7);
                        byte[] lowerTransportPdu = messagePDU.p();
                        W("resend segmented message: seqZero:" + messagePDU.f() + " -- segO:" + messagePDU.d());
                        Priority a2 = Priority.a(3, i5);
                        i4 = i7;
                        networkLayerPduList = networkLayerPduList2;
                        messageSegN = messageSegN2;
                        addedValue++;
                        networkLayerPduList.add(B(lowerTransportPdu, ctl, ttl, src, dst, ivIndex, sequenceNumber + addedValue, a2, "resendSegmentedMessages"));
                    } else {
                        i4 = i7;
                        networkLayerPduList = networkLayerPduList2;
                        messageSegN = messageSegN2;
                    }
                    i7 = i4 + 1;
                    networkLayerPduList2 = networkLayerPduList;
                    messageSegN2 = messageSegN;
                    i5 = 0;
                    i6 = 1;
                }
                int i8 = i7;
                List<NetworkLayerPDU> networkLayerPduList3 = networkLayerPduList2;
                int i9 = messageSegN2;
                if (networkLayerPduList3.size() == 0) {
                    T0(true, true);
                    return;
                }
                P0(ctl, ttl, src, dst);
                D0(networkLayerPduList3);
            }
        }
    }

    private void j0(NetworkLayerPDU networkLayerPDU) {
        AccessLayerPDU accessPDU;
        if (!PatchProxy.proxy(new Object[]{networkLayerPDU}, this, changeQuickRedirect, false, 12864, new Class[]{NetworkLayerPDU.class}, Void.TYPE).isSupported) {
            W("parse access message");
            int src = networkLayerPDU.m();
            int dst = networkLayerPDU.h();
            if (!MeshUtils.r(dst) || dst == this.o) {
                if (((networkLayerPDU.n()[0] >> 7) & 1) == 1) {
                    accessPDU = o0(networkLayerPDU);
                } else {
                    accessPDU = p0(networkLayerPDU);
                }
                if (accessPDU != null) {
                    Y(src, dst, accessPDU);
                }
            }
        }
    }

    private void Y(int src, int dst, AccessLayerPDU accessPDU) {
        Object[] objArr = {new Integer(src), new Integer(dst), accessPDU};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12865, new Class[]{cls, cls, AccessLayerPDU.class}, Void.TYPE).isSupported) {
            Opcode opcode = Opcode.valueOf(accessPDU.a);
            NodeInfo nodeInfo = LDSMeshUtil.findMeshNode(SIGMesh.getInstance().getMeshInfo().nodes, src);
            String receivedMac = "";
            if (nodeInfo != null) {
                receivedMac = nodeInfo.macAddress;
            }
            StringBuilder sb = new StringBuilder();
            Locale locale = Locale.US;
            sb.append(String.format(locale, "收到来自于mesh网络数据的响应 0x%04X(" + receivedMac + "): responseOpcode -- 0x%04X", new Object[]{Integer.valueOf(src), Integer.valueOf(accessPDU.a)}));
            sb.append(",响应请求消息:");
            sb.append(opcode);
            sb.append(",params -- ");
            sb.append(e.b(accessPDU.b, ""));
            W(sb.toString());
            U0(src, accessPDU);
            NetworkingBridge networkingBridge = this.y;
            if (networkingBridge != null) {
                networkingBridge.c(src, dst, accessPDU.a, accessPDU.b);
            }
        }
    }

    private void U0(int src, AccessLayerPDU accessLayerPDU) {
        MeshMessage meshMessage;
        Object[] objArr = {new Integer(src), accessLayerPDU};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12866, new Class[]{Integer.TYPE, AccessLayerPDU.class}, Void.TYPE).isSupported) {
            if (this.G && (meshMessage = this.F) != null && meshMessage.o() == accessLayerPDU.a) {
                this.I.add(Integer.valueOf(src));
                if (this.I.size() >= this.F.n()) {
                    f0(true);
                }
            }
        }
    }

    private void f0(boolean success) {
        if (!PatchProxy.proxy(new Object[]{new Byte(success ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 12867, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.A.removeCallbacks(this.T);
            int opcode = this.F.k();
            int rspMax = this.F.n();
            int rspCount = this.I.size();
            W(String.format(Locale.US, "Reliable Message Complete: %06X success?: %b", new Object[]{Integer.valueOf(opcode), Boolean.valueOf(success)}));
            this.I.clear();
            synchronized (this.H) {
                this.G = false;
                if (success && this.D && this.F.v()) {
                    this.D = false;
                    T0(true, true);
                }
            }
            NetworkingBridge networkingBridge = this.y;
            if (networkingBridge != null) {
                networkingBridge.l(success, opcode, rspMax, rspCount);
            }
        }
    }

    private void w0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12868, new Class[0], Void.TYPE).isSupported) {
            MeshMessage meshMessage = this.F;
            String currentSendingMsg = meshMessage != null ? meshMessage.getClass().getSimpleName() : "";
            W("restart reliable message:" + currentSendingMsg + " timeout task, immediate");
            this.A.removeCallbacks(this.T);
            this.A.postDelayed(this.T, M());
        }
    }

    private AccessLayerPDU p0(NetworkLayerPDU networkLayerPDU) {
        UpperTransportAccessPDU.UpperTransportEncryptionSuite upperTransportEncryptionSuite;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{networkLayerPDU}, this, changeQuickRedirect, false, 12869, new Class[]{NetworkLayerPDU.class}, AccessLayerPDU.class);
        if (proxy.isSupported) {
            return (AccessLayerPDU) proxy.result;
        }
        int akf = 1 & (networkLayerPDU.n()[0] >> 6);
        int ivIndex = networkLayerPDU.k.a;
        UnsegmentedAccessMessagePDU unsegmentedAccessMessagePDU = new UnsegmentedAccessMessagePDU();
        if (!unsegmentedAccessMessagePDU.d(networkLayerPDU)) {
            return null;
        }
        if (AccessType.DEVICE.akf == akf) {
            upperTransportEncryptionSuite = new UpperTransportAccessPDU.UpperTransportEncryptionSuite(L(networkLayerPDU.m()), ivIndex);
        } else {
            upperTransportEncryptionSuite = new UpperTransportAccessPDU.UpperTransportEncryptionSuite(J(), ivIndex);
        }
        UpperTransportAccessPDU upperTransportAccessPDU = new UpperTransportAccessPDU(upperTransportEncryptionSuite);
        if (upperTransportAccessPDU.g(unsegmentedAccessMessagePDU, networkLayerPDU.l(), networkLayerPDU.m(), networkLayerPDU.h())) {
            return AccessLayerPDU.a(upperTransportAccessPDU.d());
        }
        X("unsegmented access message parse err", 3);
        return null;
    }

    private List<byte[]> J() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12870, new Class[0], List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        SparseArray<byte[]> sparseArray = this.l;
        if (sparseArray == null || sparseArray.size() == 0) {
            return null;
        }
        List<byte[]> appKeyList = new ArrayList<>();
        for (int i2 = 0; i2 < this.l.size(); i2++) {
            SparseArray<byte[]> sparseArray2 = this.l;
            appKeyList.add(sparseArray2.get(sparseArray2.keyAt(i2)));
        }
        return appKeyList;
    }

    private void x(boolean immediate, int ttl, int src) {
        Object[] objArr = {new Byte(immediate ? (byte) 1 : 0), new Integer(ttl), new Integer(src)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12871, new Class[]{Boolean.TYPE, cls, cls}, Void.TYPE).isSupported) {
            if (immediate) {
                S0();
            } else {
                x0();
            }
            this.A.removeCallbacks(this.B);
            long timeout = immediate ? 0 : O(ttl, false);
            int unused = this.B.c = src;
            int unused2 = this.B.d = ttl;
            W("check segment block: immediate-" + immediate + " ttl-" + ttl + " src-" + src + " timeout-" + timeout);
            this.A.postDelayed(this.B, timeout);
        }
    }

    private void R0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12872, new Class[0], Void.TYPE).isSupported) {
            this.A.removeCallbacks(this.B);
        }
    }

    private void H0(int src, int ttl) {
        boolean complete = true;
        Object[] objArr = {new Integer(src), new Integer(ttl)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12873, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            W("send segment block ack:" + src);
            SparseArray<SegmentedAccessMessagePDU> messages = this.r.clone();
            if (messages.size() > 0) {
                int seqZero = -1;
                int blockAck = 0;
                int segN = -1;
                for (int i2 = 0; i2 < messages.size(); i2++) {
                    int segO = messages.keyAt(i2);
                    SegmentedAccessMessagePDU message = messages.get(segO);
                    if (segN == -1) {
                        segN = message.c();
                    }
                    if (seqZero == -1) {
                        seqZero = message.f();
                    }
                    blockAck |= 1 << segO;
                }
                G0(new SegmentAcknowledgmentMessage(seqZero, blockAck), src);
                if (messages.size() != segN + 1) {
                    complete = false;
                }
                if (!complete) {
                    this.A.removeCallbacks(this.B);
                    this.A.postDelayed(this.B, O(ttl, false));
                }
            }
        }
    }

    private void I0(int src, int seqZero, long seqAuth) {
        Object[] objArr = {new Integer(src), new Integer(seqZero), new Long(seqAuth)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12874, new Class[]{cls, cls, Long.TYPE}, Void.TYPE).isSupported) {
            y0(src, seqAuth);
            G0(new SegmentAcknowledgmentMessage(seqZero, 0), src);
        }
    }

    private void G0(SegmentAcknowledgmentMessage segmentAcknowledgmentMessage, int dst) {
        if (!PatchProxy.proxy(new Object[]{segmentAcknowledgmentMessage, new Integer(dst)}, this, changeQuickRedirect, false, 12875, new Class[]{SegmentAcknowledgmentMessage.class, Integer.TYPE}, Void.TYPE).isSupported) {
            K0(segmentAcknowledgmentMessage, dst);
        }
    }

    private void K0(UnsegmentedControlMessagePDU controlMessagePDU, int dst) {
        Object[] objArr = {controlMessagePDU, new Integer(dst)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12876, new Class[]{UnsegmentedControlMessagePDU.class, Integer.TYPE}, Void.TYPE).isSupported) {
            byte[] data = controlMessagePDU.a();
            int src = this.o;
            C0(B(data, 1, 5, src, dst, Q(), this.d.get(), Priority.a(3, 0), ""));
        }
    }

    private void x0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12877, new Class[0], Void.TYPE).isSupported) {
            this.A.removeCallbacks(this.U);
            this.A.postDelayed(this.U, 10000);
        }
    }

    private void S0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12878, new Class[0], Void.TYPE).isSupported) {
            this.A.removeCallbacks(this.U);
        }
    }

    private void J0(int src, int segN, int seqZero) {
        Object[] objArr = {new Integer(src), new Integer(segN), new Integer(seqZero)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12879, new Class[]{cls, cls, cls}, Void.TYPE).isSupported) {
            int blockAck = 0;
            for (int i2 = 0; i2 < segN + 1; i2++) {
                blockAck |= 1 << i2;
            }
            G0(new SegmentAcknowledgmentMessage(seqZero, blockAck), src);
        }
    }

    private AccessLayerPDU o0(NetworkLayerPDU networkLayerPDU) {
        int seqHigherBitValue;
        UpperTransportAccessPDU.UpperTransportEncryptionSuite encryptionSuite;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{networkLayerPDU}, this, changeQuickRedirect, false, 12880, new Class[]{NetworkLayerPDU.class}, AccessLayerPDU.class);
        if (proxy.isSupported) {
            return (AccessLayerPDU) proxy.result;
        }
        NetworkLayerPDU networkLayerPDU2 = networkLayerPDU;
        SegmentedAccessMessagePDU message = new SegmentedAccessMessagePDU();
        message.h(networkLayerPDU2);
        int src = networkLayerPDU2.m();
        int o2 = networkLayerPDU2.o() & 255;
        int sequenceNumber = networkLayerPDU2.l();
        int seqLowerBitValue = sequenceNumber & AVIOCTRLDEFs.IOTYPE_USER_IPCAM_EVENT_REPORT;
        int seqZero = message.f();
        if (seqLowerBitValue < seqZero) {
            seqHigherBitValue = 16769024 & (sequenceNumber - 8192);
        } else {
            seqHigherBitValue = 16769024 & sequenceNumber;
        }
        int transportSeqNo = seqHigherBitValue | seqZero;
        int ivIndex = networkLayerPDU2.k.a;
        int seqZero2 = seqZero;
        long seqAuth = ((((long) ivIndex) & 2147483647L) << 24) | (((long) transportSeqNo) & 16777215);
        int segO = message.d();
        int segN = message.c();
        if (U(src, seqAuth)) {
            W("busy auth exists");
            I0(src, seqZero2, seqAuth);
            return null;
        }
        int seqZero3 = seqZero2;
        if (V(src, seqAuth)) {
            W("complete auth exists");
            J0(src, segN, seqZero3);
            return null;
        }
        int i2 = sequenceNumber;
        int i3 = seqLowerBitValue;
        long j2 = this.v;
        if (!(seqAuth == j2 && this.w == src)) {
            if (this.x) {
                z0(this.w, j2);
                this.x = false;
                this.v = seqAuth;
                this.w = src;
                this.r.clear();
            } else {
                I0(src, seqZero3, seqAuth);
                return null;
            }
        }
        this.r.put(segO, message);
        int messageCnt = this.r.size();
        if (messageCnt != segN + 1) {
            this.v = seqAuth;
            x(false, o2, src);
            SegmentedAccessMessagePDU segmentedAccessMessagePDU = message;
            byte b2 = o2;
            return null;
        }
        this.x = true;
        x(true, o2, src);
        if (V(src, seqAuth)) {
            W(" seqAuth already received: " + seqAuth);
            byte b3 = o2;
            int i4 = messageCnt;
            this.v = 0;
            return null;
        }
        int i5 = o2;
        int i6 = messageCnt;
        if (message.b() == AccessType.APPLICATION.akf) {
            encryptionSuite = new UpperTransportAccessPDU.UpperTransportEncryptionSuite(J(), ivIndex);
            byte b4 = i5;
        } else {
            byte[] deviceKey = L(src);
            if (deviceKey == null) {
                byte b5 = i5;
                X("Device key not found when decrypt segmented access message", 3);
                return null;
            }
            int ttl = i5;
            encryptionSuite = new UpperTransportAccessPDU.UpperTransportEncryptionSuite(deviceKey, ivIndex);
        }
        UpperTransportAccessPDU upperTransportAccessPDU = new UpperTransportAccessPDU(encryptionSuite);
        SegmentedAccessMessagePDU segmentedAccessMessagePDU2 = message;
        upperTransportAccessPDU.f(this.r.clone(), transportSeqNo, src, networkLayerPDU2.h());
        byte[] completeTransportPdu = upperTransportAccessPDU.d();
        if (completeTransportPdu != null) {
            return AccessLayerPDU.a(completeTransportPdu);
        }
        UpperTransportAccessPDU upperTransportAccessPDU2 = upperTransportAccessPDU;
        X("upper pdu decryption error: ", 3);
        return null;
    }

    private UpperTransportAccessPDU F(byte[] bArr, byte[] bArr2, byte b2, AccessType accessType, int i2, int i3, int i4, int i5) {
        UpperTransportAccessPDU.UpperTransportEncryptionSuite encryptionSuite;
        Class<byte[]> cls = byte[].class;
        Object[] objArr = {bArr, bArr2, new Byte(b2), accessType, new Integer(i2), new Integer(i3), new Integer(i4), new Integer(i5)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls2 = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12881, new Class[]{cls, cls, Byte.TYPE, AccessType.class, cls2, cls2, cls2, cls2}, UpperTransportAccessPDU.class);
        if (proxy.isSupported) {
            return (UpperTransportAccessPDU) proxy.result;
        }
        byte[] key = bArr2;
        int seqNo = i3;
        AccessType accessType2 = accessType;
        int dst = i5;
        byte[] accessPDU = bArr;
        byte szmic = b2;
        int src = i4;
        int ivIndex = i2;
        if (accessType2 == AccessType.APPLICATION) {
            List<byte[]> appKeyList = new ArrayList<>();
            appKeyList.add(key);
            encryptionSuite = new UpperTransportAccessPDU.UpperTransportEncryptionSuite(appKeyList, ivIndex);
        } else {
            encryptionSuite = new UpperTransportAccessPDU.UpperTransportEncryptionSuite(key, ivIndex);
        }
        UpperTransportAccessPDU upperTransportAccessPDU = new UpperTransportAccessPDU(encryptionSuite);
        UpperTransportAccessPDU upperTransportAccessPDU2 = upperTransportAccessPDU;
        if (upperTransportAccessPDU.c(accessPDU, szmic, accessType2, seqNo, src, dst)) {
            return upperTransportAccessPDU2;
        }
        return null;
    }

    private SparseArray<SegmentedAccessMessagePDU> D(byte[] bArr, byte b2, byte b3, int i2, int sequenceNumber) {
        Object[] objArr = {bArr, new Byte(b2), new Byte(b3), new Integer(i2), new Integer(sequenceNumber)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Byte.TYPE;
        Class cls2 = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12882, new Class[]{byte[].class, cls, cls, cls2, cls2}, SparseArray.class);
        if (proxy.isSupported) {
            return (SparseArray) proxy.result;
        }
        NetworkingController networkingController = this;
        byte akf = b2;
        int aszmic = i2;
        byte[] encryptedUpperTransportPDU = bArr;
        byte aid = b3;
        int segmentedAccessLen = a + 1;
        byte[] seqNoBuffer = MeshUtils.l(sequenceNumber, 3, ByteOrder.BIG_ENDIAN);
        int seqZero = ((seqNoBuffer[1] & 31) << 8) | (seqNoBuffer[2] & 255);
        int segNum = (int) Math.ceil(((double) encryptedUpperTransportPDU.length) / ((double) segmentedAccessLen));
        int segN = segNum - 1;
        networkingController.W("create segmented access message: seqZero - " + seqZero + " segN - " + segN);
        SparseArray<SegmentedAccessMessagePDU> lowerTransportPDUArray = new SparseArray<>();
        int offset = 0;
        int segOffset = 0;
        while (segOffset < segNum) {
            int segmentedLength = Math.min(encryptedUpperTransportPDU.length - offset, segmentedAccessLen);
            SegmentedAccessMessagePDU lowerTransportPDU = new SegmentedAccessMessagePDU();
            lowerTransportPDU.j(akf);
            lowerTransportPDU.i(aid);
            lowerTransportPDU.o(aszmic);
            lowerTransportPDU.n(seqZero);
            lowerTransportPDU.l(segOffset);
            lowerTransportPDU.k(segN);
            lowerTransportPDU.m(ByteBuffer.allocate(segmentedLength).put(encryptedUpperTransportPDU, offset, segmentedLength).array());
            offset += segmentedLength;
            lowerTransportPDUArray.put(segOffset, lowerTransportPDU);
            segOffset++;
            networkingController = networkingController;
        }
        return lowerTransportPDUArray;
    }

    private UnsegmentedAccessMessagePDU E(byte[] upperTransportPDU, byte akf, byte aid) {
        Object[] objArr = {upperTransportPDU, new Byte(akf), new Byte(aid)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Byte.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12883, new Class[]{byte[].class, cls, cls}, UnsegmentedAccessMessagePDU.class);
        if (proxy.isSupported) {
            return (UnsegmentedAccessMessagePDU) proxy.result;
        }
        return new UnsegmentedAccessMessagePDU(akf, aid, upperTransportPDU);
    }

    private NetworkLayerPDU B(byte[] transportPdu, int ctl, int ttl, int src, int dst, int i2, int sequenceNumber, Priority priority, String extra) {
        Object[] objArr = {transportPdu, new Integer(ctl), new Integer(ttl), new Integer(src), new Integer(dst), new Integer(i2), new Integer(sequenceNumber), priority, extra};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {byte[].class, cls, cls, cls, cls, cls, cls, Priority.class, String.class};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12884, clsArr, NetworkLayerPDU.class);
        if (proxy.isSupported) {
            return (NetworkLayerPDU) proxy.result;
        }
        int ivIndex = i2;
        NetworkLayerPDU networkLayerPDU = new NetworkLayerPDU(new NetworkLayerPDU.NetworkEncryptionSuite(ivIndex, this.g, this.h, this.f));
        networkLayerPDU.u((byte) (ivIndex & 1));
        networkLayerPDU.v(this.f);
        networkLayerPDU.r((byte) ctl);
        networkLayerPDU.A((byte) ttl);
        networkLayerPDU.x(sequenceNumber);
        networkLayerPDU.y(src);
        networkLayerPDU.s(dst);
        networkLayerPDU.z(transportPdu);
        networkLayerPDU.w(priority);
        networkLayerPDU.t(extra);
        S();
        return networkLayerPDU;
    }

    private ProxyConfigurationPDU C(byte[] transportPdu, int src, int ivIndex, int sequenceNumber) {
        Object[] objArr = {transportPdu, new Integer(src), new Integer(ivIndex), new Integer(sequenceNumber)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 12885, new Class[]{byte[].class, cls, cls, cls}, ProxyConfigurationPDU.class);
        if (proxy.isSupported) {
            return (ProxyConfigurationPDU) proxy.result;
        }
        ProxyConfigurationPDU networkLayerPDU = new ProxyConfigurationPDU(new NetworkLayerPDU.NetworkEncryptionSuite(ivIndex, this.g, this.h, this.f));
        networkLayerPDU.u((byte) (ivIndex & 1));
        networkLayerPDU.v(this.f);
        networkLayerPDU.r((byte) 1);
        networkLayerPDU.A((byte) 0);
        networkLayerPDU.x(sequenceNumber);
        networkLayerPDU.y(src);
        networkLayerPDU.s(0);
        networkLayerPDU.z(transportPdu);
        S();
        return networkLayerPDU;
    }

    public class SegmentAckMessageSentTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public int d;

        private SegmentAckMessageSentTask() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12902, new Class[0], Void.TYPE).isSupported) {
                NetworkingController.p(NetworkingController.this, this.c, this.d);
            }
        }
    }

    public class SegmentedMessageTimeoutTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private SegmentedMessageTimeoutTask() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12904, new Class[0], Void.TYPE).isSupported) {
                NetworkingController.s(NetworkingController.this, "segmented message timeout");
                NetworkingController.h(NetworkingController.this, true, false);
            }
        }
    }

    public class SegmentBlockWaitingTask implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public int c;
        /* access modifiers changed from: private */
        public int d;
        /* access modifiers changed from: private */
        public int f;
        /* access modifiers changed from: private */
        public int q;

        private SegmentBlockWaitingTask() {
        }

        public void e(int ctl, int ttl, int src, int dst) {
            this.c = ctl;
            this.d = ttl;
            this.f = src;
            this.q = dst;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12903, new Class[0], Void.TYPE).isSupported) {
                NetworkingController.q(NetworkingController.this, -1, 0);
            }
        }
    }

    private void W(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 12886, new Class[]{String.class}, Void.TYPE).isSupported) {
            NetworkingBridge networkingBridge = this.y;
            String macAddress = "";
            String meshControllerName = networkingBridge != null ? networkingBridge.a() : macAddress;
            if (!TextUtils.isEmpty(this.N)) {
                macAddress = ", macAddress=" + this.N;
            }
            X(logMessage + "," + meshControllerName + macAddress, 2);
        }
    }

    private void X(String logMessage, int i2) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(i2)}, this, changeQuickRedirect, false, 12887, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLog.i(logMessage + ",thread:" + Thread.currentThread().getName());
        }
    }
}
