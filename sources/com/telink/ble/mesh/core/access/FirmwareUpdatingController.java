package com.telink.ble.mesh.core.access;

import android.os.Handler;
import android.os.HandlerThread;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.config.ConfigStatus;
import com.telink.ble.mesh.core.message.config.ModelSubscriptionStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.FirmwareMetadataStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.FirmwareUpdateCancelMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.FirmwareUpdateInfoStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.FirmwareUpdateStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.UpdatePhase;
import com.telink.ble.mesh.core.message.firmwareupdate.UpdateStatus;
import com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer.BlobBlockStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer.BlobChunkTransferMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer.BlobInfoStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer.BlobTransferStatusMessage;
import com.telink.ble.mesh.core.message.firmwareupdate.blobtransfer.TransferStatus;
import com.telink.ble.mesh.core.networking.NetworkingController;
import com.telink.ble.mesh.entity.FirmwareUpdateConfiguration;
import com.telink.ble.mesh.entity.MeshUpdatingDevice;
import com.telink.ble.mesh.util.MeshLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import meshsdk.MeshLog;

public class FirmwareUpdatingController {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "FwUpdate";
    private int b = 0;
    private List<MeshUpdatingDevice> c;
    private int d;
    private int e;
    private int f;
    private long g;
    private byte[] h = new byte[8];
    private int i = 0;
    private MeshFirmwareParser j = new MeshFirmwareParser();
    private byte[] k;
    private ArrayList<Integer> l = new ArrayList<>();
    private int m = 0;
    private int n = -1;
    private Handler o;
    private AccessBridge p;
    private boolean q = false;
    private int r = -1;
    private int s = 11;
    boolean t = true;
    public String u = "";
    private Runnable v = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12222, new Class[0], Void.TYPE).isSupported) {
                FirmwareUpdatingController.this.D();
            }
        }
    };
    private Runnable w = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12223, new Class[0], Void.TYPE).isSupported) {
                FirmwareUpdatingController.a(FirmwareUpdatingController.this);
                FirmwareUpdatingController.b(FirmwareUpdatingController.this);
            }
        }
    };
    private Runnable x = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12224, new Class[0], Void.TYPE).isSupported) {
                FirmwareUpdatingController.c(FirmwareUpdatingController.this);
            }
        }
    };

    static /* synthetic */ int a(FirmwareUpdatingController x0) {
        int i2 = x0.m;
        x0.m = i2 + 1;
        return i2;
    }

    static /* synthetic */ void b(FirmwareUpdatingController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12220, new Class[]{FirmwareUpdatingController.class}, Void.TYPE).isSupported) {
            x0.F();
        }
    }

    static /* synthetic */ void c(FirmwareUpdatingController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 12221, new Class[]{FirmwareUpdatingController.class}, Void.TYPE).isSupported) {
            x0.G();
        }
    }

    public FirmwareUpdatingController(HandlerThread handlerThread) {
        this.o = new Handler(handlerThread.getLooper());
    }

    public void E(AccessBridge accessBridge) {
        this.p = accessBridge;
    }

    public void d(FirmwareUpdateConfiguration configuration) {
        if (!PatchProxy.proxy(new Object[]{configuration}, this, changeQuickRedirect, false, 12188, new Class[]{FirmwareUpdateConfiguration.class}, Void.TYPE).isSupported) {
            if (configuration == null) {
                C(4, "updating params null");
                return;
            }
            this.t = true;
            this.q = configuration.h();
            this.s = configuration.c();
            byte[] d2 = configuration.d();
            this.k = d2;
            if (d2.length >= 6) {
                this.h = configuration.f();
                k(" config -- " + configuration.toString());
                k("isGattMode? " + this.q);
                this.e = configuration.a();
                this.d = configuration.e();
                this.c = configuration.g();
                this.g = configuration.b();
                this.f = 0;
                List<MeshUpdatingDevice> list = this.c;
                if (list == null || list.size() == 0) {
                    C(4, "params err when action begin");
                    return;
                }
                if (this.q) {
                    this.r = this.c.get(0).a();
                }
                this.b = 1;
                g();
            }
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12189, new Class[0], Void.TYPE).isSupported) {
            Handler handler = this.o;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
            this.b = 0;
        }
    }

    private void G() {
        boolean segment = false;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12191, new Class[0], Void.TYPE).isSupported) {
            byte[] chunkData = this.j.h();
            int chunkIndex = this.j.c();
            if (chunkData != null) {
                H();
                BlobChunkTransferMessage blobChunkTransferMessage = h(chunkIndex, chunkData);
                k("next chunk transfer msg: " + blobChunkTransferMessage.toString());
                v(blobChunkTransferMessage);
                if (!this.q) {
                    this.o.postDelayed(this.x, i());
                    return;
                }
                if (chunkData.length + 3 > this.s) {
                    segment = true;
                }
                if (!segment) {
                    G();
                    return;
                }
                return;
            }
            k("chunks sent complete at: block -- " + this.j.b() + " chunk -- " + this.j.c());
            e();
        }
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12192, new Class[0], Void.TYPE).isSupported) {
            k("check missing chunks");
            this.l.clear();
            this.n = -1;
            this.b = 10;
            this.f = 0;
            g();
        }
    }

    private void F() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12193, new Class[0], Void.TYPE).isSupported) {
            if (this.m >= this.l.size()) {
                k("all missing chunks sent complete: " + this.m);
                e();
                return;
            }
            int chunkNumber = this.l.get(this.m).intValue();
            k("missing chunks: " + chunkNumber);
            byte[] chunkData = this.j.a(chunkNumber);
            if (chunkData == null) {
                k("chunk index overflow when resending chunk: " + chunkNumber);
                return;
            }
            v(h(chunkNumber, chunkData));
            this.o.postDelayed(this.w, i());
        }
    }

    private long i() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12194, new Class[0], Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        if (this.q) {
            return 100;
        }
        int segmentCnt = 1;
        int chunkMsgLen = this.j.d() + 1;
        int unsegLen = NetworkingController.a;
        int segLen = unsegLen + 1;
        if (chunkMsgLen != unsegLen) {
            segmentCnt = chunkMsgLen % segLen == 0 ? chunkMsgLen / segLen : 1 + (chunkMsgLen / segLen);
        }
        long interval = Math.max(unsegLen == 11 ? KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS : 300, ((long) segmentCnt) * 240);
        k("chunk sending interval: " + interval);
        return interval;
    }

    private BlobChunkTransferMessage h(int chunkNumber, byte[] chunkData) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(chunkNumber), chunkData}, this, changeQuickRedirect, false, 12195, new Class[]{Integer.TYPE, byte[].class}, BlobChunkTransferMessage.class);
        if (proxy.isSupported) {
            return (BlobChunkTransferMessage) proxy.result;
        }
        return BlobChunkTransferMessage.I(this.q ? this.r : this.d, this.e, chunkNumber, chunkData);
    }

    private void g() {
    }

    private void v(MeshMessage meshMessage) {
        if (!PatchProxy.proxy(new Object[]{meshMessage}, this, changeQuickRedirect, false, 12197, new Class[]{MeshMessage.class}, Void.TYPE).isSupported) {
            meshMessage.E(10);
            StringBuilder sb = new StringBuilder();
            sb.append("com.telink.ble.mesh message prepared: ");
            sb.append(meshMessage.getClass().getSimpleName());
            Locale locale = Locale.US;
            sb.append(String.format(locale, " opcode: 0x%04X -- dst: 0x%04X", new Object[]{Integer.valueOf(meshMessage.k()), Integer.valueOf(meshMessage.j())}));
            k(sb.toString());
            AccessBridge accessBridge = this.p;
            if (accessBridge != null && !accessBridge.j(meshMessage, 2)) {
                if (meshMessage instanceof BlobChunkTransferMessage) {
                    C(-1, "chunk transfer message sent error");
                    return;
                }
                int size = this.c.size();
                int i2 = this.f;
                if (size > i2) {
                    r(this.c.get(i2), String.format(locale, "com.telink.ble.mesh message sent error -- opcode: 0x%04X", new Object[]{Integer.valueOf(meshMessage.k())}));
                }
            }
        }
    }

    public void y(boolean success) {
        Object[] objArr = {new Byte(success ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12198, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (this.q && this.b == 9) {
                if (success) {
                    G();
                } else {
                    C(4, "chunk send fail -- segment message send fail");
                }
            }
        }
    }

    public void w(NotificationMessage message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 12199, new Class[]{NotificationMessage.class}, Void.TYPE).isSupported) {
            Opcode opcode = Opcode.valueOf(message.a());
            k("message notification: " + opcode);
            if (this.b == 0) {
                k("notification when idle");
            } else if (opcode != null) {
                int src = message.c();
                int size = this.c.size();
                int i2 = this.f;
                if (size <= i2) {
                    l("node index overflow", 3);
                } else if (this.c.get(i2).a() != src) {
                    l("unexpected notification src", 3);
                } else {
                    switch (AnonymousClass4.a[opcode.ordinal()]) {
                        case 1:
                            t((FirmwareUpdateInfoStatusMessage) message.d());
                            return;
                        case 2:
                            A((ModelSubscriptionStatusMessage) message.d());
                            return;
                        case 3:
                            p((BlobInfoStatusMessage) message.d());
                            return;
                        case 4:
                            x((FirmwareMetadataStatusMessage) message.d());
                            return;
                        case 5:
                            u((FirmwareUpdateStatusMessage) message.d());
                            return;
                        case 6:
                            q((BlobTransferStatusMessage) message.d());
                            return;
                        case 7:
                            o(message);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    /* renamed from: com.telink.ble.mesh.core.access.FirmwareUpdatingController$4  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Opcode.values().length];
            a = iArr;
            try {
                iArr[Opcode.FIRMWARE_UPDATE_INFORMATION_STATUS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[Opcode.CFG_MODEL_SUB_STATUS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[Opcode.BLOB_INFORMATION_STATUS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[Opcode.FIRMWARE_UPDATE_FIRMWARE_METADATA_STATUS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[Opcode.FIRMWARE_UPDATE_STATUS.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[Opcode.BLOB_TRANSFER_STATUS.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[Opcode.BLOB_BLOCK_STATUS.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    private void t(FirmwareUpdateInfoStatusMessage firmwareInfoStatusMessage) {
        if (!PatchProxy.proxy(new Object[]{firmwareInfoStatusMessage}, this, changeQuickRedirect, false, 12200, new Class[]{FirmwareUpdateInfoStatusMessage.class}, Void.TYPE).isSupported) {
            k("firmware info status: " + firmwareInfoStatusMessage.toString());
            if (this.b != 2) {
                k("not at STEP_GET_FIRMWARE_INFO");
                return;
            }
            int d2 = firmwareInfoStatusMessage.d();
            int e2 = firmwareInfoStatusMessage.e();
            List<FirmwareUpdateInfoStatusMessage.FirmwareInformationEntry> c2 = firmwareInfoStatusMessage.c();
            this.f++;
            g();
        }
    }

    private void A(ModelSubscriptionStatusMessage subscriptionStatusMessage) {
        if (!PatchProxy.proxy(new Object[]{subscriptionStatusMessage}, this, changeQuickRedirect, false, 12201, new Class[]{ModelSubscriptionStatusMessage.class}, Void.TYPE).isSupported) {
            k("subscription status: " + subscriptionStatusMessage.toString());
            if (this.b != 1) {
                k("not at STEP_SET_SUBSCRIPTION");
                return;
            }
            if (subscriptionStatusMessage.c() != ConfigStatus.SUCCESS.code) {
                r(this.c.get(this.f), "grouping status err " + subscriptionStatusMessage.c());
            }
            this.f++;
            g();
        }
    }

    private void p(BlobInfoStatusMessage objectInfoStatusMessage) {
        if (!PatchProxy.proxy(new Object[]{objectInfoStatusMessage}, this, changeQuickRedirect, false, 12202, new Class[]{BlobInfoStatusMessage.class}, Void.TYPE).isSupported) {
            k("object info status: " + objectInfoStatusMessage.toString());
            if (this.b != 6) {
                k("not at STEP_GET_BLOB_INFO");
                return;
            }
            int blockSize = (int) Math.pow(2.0d, (double) objectInfoStatusMessage.c());
            int chunkSize = objectInfoStatusMessage.d();
            k("chunk size : " + chunkSize + " block size: " + blockSize);
            this.j.j(this.k, blockSize, chunkSize);
            this.f = this.f + 1;
            g();
        }
    }

    private void x(FirmwareMetadataStatusMessage metadataStatusMessage) {
        if (!PatchProxy.proxy(new Object[]{metadataStatusMessage}, this, changeQuickRedirect, false, 12203, new Class[]{FirmwareMetadataStatusMessage.class}, Void.TYPE).isSupported) {
            UpdateStatus status = UpdateStatus.valueOf(metadataStatusMessage.c());
            if (this.b == 3) {
                if (status != UpdateStatus.SUCCESS) {
                    r(this.c.get(this.f), "metadata check error: " + status.desc);
                }
                this.f++;
                g();
                return;
            }
            l("metadata received when not checking", 3);
        }
    }

    private void u(FirmwareUpdateStatusMessage firmwareUpdateStatusMessage) {
        if (!PatchProxy.proxy(new Object[]{firmwareUpdateStatusMessage}, this, changeQuickRedirect, false, 12204, new Class[]{FirmwareUpdateStatusMessage.class}, Void.TYPE).isSupported) {
            k("firmware update status:  at: " + j(this.b) + " -- " + firmwareUpdateStatusMessage.toString());
            if (UpdateStatus.valueOf(firmwareUpdateStatusMessage.d() & 255) != UpdateStatus.SUCCESS) {
                r(this.c.get(this.f), "firmware update status err");
            } else {
                int step = this.b;
                if (1 == 0) {
                    r(this.c.get(this.f), "firmware update phase err");
                } else {
                    UpdatePhase phase = UpdatePhase.valueOf(firmwareUpdateStatusMessage.c() & 255);
                    if (step == 12) {
                        if (phase == UpdatePhase.VERIFICATION_SUCCESS || phase == UpdatePhase.APPLYING_UPDATE) {
                            s(this.c.get(this.f));
                        } else {
                            r(this.c.get(this.f), "phase error when update apply");
                        }
                    }
                }
            }
            this.f++;
            g();
        }
    }

    private void q(BlobTransferStatusMessage transferStatusMessage) {
        if (!PatchProxy.proxy(new Object[]{transferStatusMessage}, this, changeQuickRedirect, false, 12205, new Class[]{BlobTransferStatusMessage.class}, Void.TYPE).isSupported) {
            k("object transfer status: " + transferStatusMessage.toString());
            int i2 = this.b;
            if (i2 == 7 || i2 == 5) {
                if (UpdateStatus.valueOf(transferStatusMessage.c()) != UpdateStatus.SUCCESS) {
                    r(this.c.get(this.f), "object transfer status err");
                }
                this.f++;
                g();
            }
        }
    }

    private void o(NotificationMessage message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 12207, new Class[]{NotificationMessage.class}, Void.TYPE).isSupported) {
            int i2 = this.b;
            if (i2 == 10 || i2 == 8) {
                BlobBlockStatusMessage blobBlockStatusMessage = (BlobBlockStatusMessage) message.d();
                k("block status: " + blobBlockStatusMessage.toString());
                int srcAddress = message.c();
                if (TransferStatus.valueOf(blobBlockStatusMessage.f() & 255) == TransferStatus.SUCCESS) {
                    if (this.b == 10) {
                        int format = blobBlockStatusMessage.d();
                        m(format);
                        switch (format) {
                            case 0:
                                k(String.format(Locale.US, "all chunks missing: %04X", new Object[]{Integer.valueOf(srcAddress)}));
                                break;
                            case 1:
                                k(String.format(Locale.US, "no chunks missing: %04X", new Object[]{Integer.valueOf(srcAddress)}));
                                break;
                            case 2:
                                n(blobBlockStatusMessage.e());
                                break;
                            case 3:
                                n(blobBlockStatusMessage.c());
                                break;
                        }
                    }
                } else {
                    r(this.c.get(this.f), "block status err");
                }
                this.f++;
                g();
            }
        }
    }

    private void m(int format) {
        int i2 = this.n;
        if (i2 != 0) {
            if (i2 == -1) {
                this.n = format;
            } else if (i2 != format && format != 1) {
                this.n = format;
            }
        }
    }

    private void n(List<Integer> chunks) {
        if (!PatchProxy.proxy(new Object[]{chunks}, this, changeQuickRedirect, false, 12208, new Class[]{List.class}, Void.TYPE).isSupported) {
            if (this.n != 0 && chunks != null) {
                for (Integer intValue : chunks) {
                    int chunkNumber = intValue.intValue();
                    if (!this.l.contains(Integer.valueOf(chunkNumber))) {
                        this.l.add(Integer.valueOf(chunkNumber));
                    }
                }
            }
        }
    }

    public void B(boolean z, int i2, int i3, int i4) {
        Object[] objArr = {new Byte(z ? (byte) 1 : 0), new Integer(i2), new Integer(i3), new Integer(i4)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12209, new Class[]{Boolean.TYPE, cls, cls, cls}, Void.TYPE).isSupported) {
            int opcode = i2;
            int i5 = i4;
            boolean success = z;
            int i6 = i3;
            Locale locale = Locale.US;
            k(String.format(locale, "updating command complete: opcode-%04X success?-%b", new Object[]{Integer.valueOf(opcode), Boolean.valueOf(success)}));
            if (!success) {
                if ((opcode == Opcode.CFG_MODEL_SUB_ADD.value && this.b == 1) || (opcode == Opcode.FIRMWARE_UPDATE_INFORMATION_GET.value && this.b == 2) || ((opcode == Opcode.FIRMWARE_UPDATE_FIRMWARE_METADATA_CHECK.value && this.b == 3) || ((opcode == Opcode.FIRMWARE_UPDATE_START.value && this.b == 4) || ((opcode == Opcode.BLOB_TRANSFER_GET.value && this.b == 5) || ((opcode == Opcode.BLOB_INFORMATION_GET.value && this.b == 6) || ((opcode == Opcode.BLOB_TRANSFER_START.value && this.b == 7) || ((opcode == Opcode.BLOB_BLOCK_START.value && this.b == 8) || ((opcode == Opcode.BLOB_BLOCK_GET.value && this.b == 10) || ((opcode == Opcode.FIRMWARE_UPDATE_GET.value && this.b == 11) || (opcode == Opcode.FIRMWARE_UPDATE_APPLY.value && this.b == 12)))))))))) {
                    r(this.c.get(this.f), String.format(locale, "device failed at step: %02d when sending: 0x%04X", new Object[]{Integer.valueOf(this.b), Integer.valueOf(opcode)}));
                    this.f++;
                    g();
                }
            }
        }
    }

    private void H() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12210, new Class[0], Void.TYPE).isSupported) {
            if (this.j.k()) {
                int progress = this.j.f();
                k("chunk sending progress: " + progress);
                z(1, "progress update", Integer.valueOf(progress));
            }
        }
    }

    private void r(MeshUpdatingDevice device, String desc) {
        if (!PatchProxy.proxy(new Object[]{device, desc}, this, changeQuickRedirect, false, 12212, new Class[]{MeshUpdatingDevice.class, String.class}, Void.TYPE).isSupported) {
            Locale locale = Locale.US;
            k(String.format(locale, "node updating fail: %04X -- " + desc, new Object[]{Integer.valueOf(device.a())}));
            device.b(2);
            z(3, String.format(locale, "node updating fail: %04X -- ", new Object[]{Integer.valueOf(device.a())}), device);
        }
    }

    private void s(MeshUpdatingDevice device) {
        if (!PatchProxy.proxy(new Object[]{device}, this, changeQuickRedirect, false, 12213, new Class[]{MeshUpdatingDevice.class}, Void.TYPE).isSupported) {
            Locale locale = Locale.US;
            k(String.format(locale, "node updating success: %04X -- ", new Object[]{Integer.valueOf(device.a())}));
            device.b(1);
            z(2, String.format(locale, "node updating success: %04X -- ", new Object[]{Integer.valueOf(device.a())}), device);
        }
    }

    private void C(int state, String desc) {
        if (!PatchProxy.proxy(new Object[]{new Integer(state), desc}, this, changeQuickRedirect, false, 12215, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            k("updating failed: " + state + " -- " + desc);
            this.b = 0;
            z(state, desc, (Object) null);
            v(FirmwareUpdateCancelMessage.I(65535, this.e));
        }
    }

    public void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12216, new Class[0], Void.TYPE).isSupported) {
            k("updating stopped");
            this.b = 0;
            z(5, "updating stopped", (Object) null);
        }
    }

    private void z(int state, String desc, Object obj) {
        AccessBridge accessBridge;
        Object[] objArr = {new Integer(state), desc, obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12217, new Class[]{Integer.TYPE, String.class, Object.class}, Void.TYPE).isSupported && (accessBridge = this.p) != null) {
            accessBridge.g(state, desc, 2, obj);
        }
    }

    private String j(int step) {
        switch (step) {
            case 0:
                return "initial";
            case 1:
                return "set-subscription";
            case 2:
                return "get-firmware-info";
            case 3:
                return "metadata-check";
            case 4:
                return "update-start";
            case 5:
                return "blob transfer get";
            case 6:
                return "get-blob-info";
            case 7:
                return "blob-transfer-start";
            case 8:
                return "block-transfer-start";
            case 9:
                return "blob-chunk-sending";
            case 10:
                return "get-blob-block";
            case 11:
                return "update-get";
            case 12:
                return "update-apply";
            case 13:
                return "update-complete";
            default:
                return "unknown";
        }
    }

    private void k(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 12218, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i(logMessage + " FirmWareUpdatingController.macAddress=" + this.u);
        }
    }

    private void l(String logMessage, int level) {
        if (!PatchProxy.proxy(new Object[]{logMessage, new Integer(level)}, this, changeQuickRedirect, false, 12219, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            MeshLogger.g(logMessage + " FirmWareUpdatingController.macAddress=" + this.u, "FwUpdate", level);
        }
    }
}
