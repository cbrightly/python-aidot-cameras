package com.telink.ble.mesh.core.access;

import android.os.Handler;
import android.os.HandlerThread;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.telink.ble.mesh.core.message.MeshMessage;
import com.telink.ble.mesh.core.message.NotificationMessage;
import com.telink.ble.mesh.core.message.Opcode;
import com.telink.ble.mesh.core.message.rp.LinkCloseMessage;
import com.telink.ble.mesh.core.message.rp.LinkStatusMessage;
import com.telink.ble.mesh.core.message.rp.ProvisioningPDUOutboundReportMessage;
import com.telink.ble.mesh.core.message.rp.ProvisioningPDUReportMessage;
import com.telink.ble.mesh.core.provisioning.ProvisioningBridge;
import com.telink.ble.mesh.core.provisioning.ProvisioningController;
import com.telink.ble.mesh.entity.RemoteProvisioningDevice;
import meshsdk.MeshLog;

public class RemoteProvisioningController implements ProvisioningBridge {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final String a = "RemotePv";
    private int b;
    private ProvisioningController c;
    private RemoteProvisioningDevice d;
    private AccessBridge e;
    private int f = 1;
    private int g = 0;
    /* access modifiers changed from: private */
    public boolean h = false;
    /* access modifiers changed from: private */
    public final Object i = new Object();
    private byte[] j = null;
    /* access modifiers changed from: private */
    public byte[] k = null;
    private boolean l = false;
    private Handler m;
    public String n = "";
    private Runnable o = new Runnable() {
        public static ChangeQuickRedirect changeQuickRedirect;

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12252, new Class[0], Void.TYPE).isSupported) {
                RemoteProvisioningController remoteProvisioningController = RemoteProvisioningController.this;
                RemoteProvisioningController.d(remoteProvisioningController, "resend provision pdu: waitingOutbound?" + RemoteProvisioningController.this.h);
                if (RemoteProvisioningController.this.k != null) {
                    synchronized (RemoteProvisioningController.this.i) {
                        if (RemoteProvisioningController.this.h) {
                            boolean unused = RemoteProvisioningController.this.h = false;
                            RemoteProvisioningController remoteProvisioningController2 = RemoteProvisioningController.this;
                            remoteProvisioningController2.b((byte) 3, remoteProvisioningController2.k, "resend provision pdu: waitingOutbound");
                        }
                    }
                    return;
                }
                RemoteProvisioningController.d(RemoteProvisioningController.this, "transmitting pdu error");
            }
        }
    };

    static /* synthetic */ void d(RemoteProvisioningController x0, String x1) {
        Class[] clsArr = {RemoteProvisioningController.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 12251, clsArr, Void.TYPE).isSupported) {
            x0.k(x1);
        }
    }

    public RemoteProvisioningController(HandlerThread handlerThread) {
        this.m = new Handler(handlerThread.getLooper());
    }

    public void u(AccessBridge accessBridge) {
        this.e = accessBridge;
    }

    public void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12234, new Class[0], Void.TYPE).isSupported) {
            this.b = 0;
            this.j = null;
            this.k = null;
            this.c = null;
            Handler handler = this.m;
            if (handler != null) {
                handler.removeCallbacksAndMessages((Object) null);
            }
        }
    }

    public RemoteProvisioningDevice i() {
        return this.d;
    }

    private void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12235, new Class[0], Void.TYPE).isSupported) {
            this.b = 2;
            ProvisioningController provisioningController = this.c;
            if (provisioningController != null) {
                provisioningController.G(this);
                this.c.b(this.d);
            }
        }
    }

    private void j(boolean success) {
        byte reason = 0;
        if (!PatchProxy.proxy(new Object[]{new Byte(success ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 12237, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.b = 5;
            int serverAddress = this.d.x();
            if (!success) {
                reason = 2;
            }
            n(LinkCloseMessage.I(serverAddress, 1, reason));
        }
    }

    private void m(LinkStatusMessage linkStatusMessage) {
        if (!PatchProxy.proxy(new Object[]{linkStatusMessage}, this, changeQuickRedirect, false, 12238, new Class[]{LinkStatusMessage.class}, Void.TYPE).isSupported) {
            k("link status : " + linkStatusMessage.toString());
            int i2 = this.b;
            if (i2 == 1) {
                v();
            } else if (i2 == 2) {
                k("link status when provisioning");
            } else if (i2 == 5) {
                this.b = this.l ? 3 : 4;
                t();
            }
        }
    }

    private void r(ProvisioningPDUReportMessage provisioningPDUReportMessage) {
        if (!PatchProxy.proxy(new Object[]{provisioningPDUReportMessage}, this, changeQuickRedirect, false, 12239, new Class[]{ProvisioningPDUReportMessage.class}, Void.TYPE).isSupported) {
            k("provisioning pdu report : " + provisioningPDUReportMessage.toString() + " -- " + this.g);
            byte[] pduData = provisioningPDUReportMessage.d();
            if ((provisioningPDUReportMessage.c() & 255) <= this.g) {
                k("repeated provisioning pdu received");
                return;
            }
            ProvisioningController provisioningController = this.c;
            if (provisioningController != null) {
                provisioningController.B(pduData);
            }
        }
    }

    private void p(ProvisioningPDUOutboundReportMessage outboundReportMessage) {
        if (!PatchProxy.proxy(new Object[]{outboundReportMessage}, this, changeQuickRedirect, false, 12241, new Class[]{ProvisioningPDUOutboundReportMessage.class}, Void.TYPE).isSupported) {
            int outboundPDUNumber = outboundReportMessage.c() & 255;
            k("outbound report message received: " + outboundPDUNumber + " waiting? " + this.h);
            if (this.f == outboundPDUNumber) {
                synchronized (this.i) {
                    this.m.removeCallbacks(this.o);
                    this.k = null;
                    this.h = false;
                    k("stop outbound waiting: " + this.f);
                    this.f = this.f + 1;
                    byte[] bArr = this.j;
                    if (bArr != null) {
                        b((byte) 3, bArr, "outbound report message received");
                        this.j = null;
                    } else {
                        k("no cached provisioning pdu: waiting for provisioning response");
                    }
                }
            } else if (this.h) {
                k("outbound number not pair");
            }
        }
    }

    public void o(NotificationMessage message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 12242, new Class[]{NotificationMessage.class}, Void.TYPE).isSupported) {
            Opcode opcode = Opcode.valueOf(message.a());
            if (opcode != null) {
                switch (AnonymousClass2.a[opcode.ordinal()]) {
                    case 1:
                        m((LinkStatusMessage) message.d());
                        return;
                    case 2:
                        r((ProvisioningPDUReportMessage) message.d());
                        return;
                    case 3:
                        p((ProvisioningPDUOutboundReportMessage) message.d());
                        return;
                    default:
                        return;
                }
            }
        }
    }

    /* renamed from: com.telink.ble.mesh.core.access.RemoteProvisioningController$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[Opcode.values().length];
            a = iArr;
            try {
                iArr[Opcode.REMOTE_PROV_LINK_STS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[Opcode.REMOTE_PROV_PDU_REPORT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[Opcode.REMOTE_PROV_PDU_OUTBOUND_REPORT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public void s(boolean success, int opcode, int i2, int i3) {
        Object[] objArr = {new Byte(success ? (byte) 1 : 0), new Integer(opcode), new Integer(i2), new Integer(i3)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12243, new Class[]{Boolean.TYPE, cls, cls, cls}, Void.TYPE).isSupported) {
            if (!success) {
                l(opcode);
            }
        }
    }

    private void l(int opcode) {
        if (!PatchProxy.proxy(new Object[]{new Integer(opcode)}, this, changeQuickRedirect, false, 12244, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (opcode == Opcode.REMOTE_PROV_LINK_OPEN.value) {
                q(false, "link open err");
            } else if (opcode == Opcode.REMOTE_PROV_LINK_CLOSE.value) {
                this.b = this.l ? 3 : 4;
                t();
            } else if (opcode == Opcode.REMOTE_PROV_PDU_SEND.value) {
                k("provisioning pdu send error");
                q(false, "provision pdu send error");
            }
        }
    }

    private void q(boolean success, String str) {
        ProvisioningController provisioningController;
        Object[] objArr = {new Byte(success ? (byte) 1 : 0), str};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 12245, new Class[]{Boolean.TYPE, String.class}, Void.TYPE).isSupported) {
            this.m.removeCallbacksAndMessages((Object) null);
            this.l = success;
            if (!success && (provisioningController = this.c) != null) {
                provisioningController.d();
            }
            j(success);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void t() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 12246(0x2fd6, float:1.716E-41)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            com.telink.ble.mesh.core.access.AccessBridge r1 = r0.e
            if (r1 == 0) goto L_0x0026
            int r2 = r0.b
            r3 = 3
            com.telink.ble.mesh.entity.RemoteProvisioningDevice r4 = r0.d
            java.lang.String r5 = "remote provisioning complete"
            r1.g(r2, r5, r3, r4)
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.access.RemoteProvisioningController.t():void");
    }

    private void n(MeshMessage meshMessage) {
    }

    public void e(int state, String desc) {
        if (!PatchProxy.proxy(new Object[]{new Integer(state), desc}, this, changeQuickRedirect, false, 12247, new Class[]{Integer.TYPE, String.class}, Void.TYPE).isSupported) {
            k("provisioning state changed: " + state + " -- " + desc);
            if (state == 4107) {
                q(true, desc);
            } else if (state == 4108) {
                q(false, desc);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void b(byte r11, byte[] r12, java.lang.String r13) {
        /*
            r10 = this;
            r0 = 3
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.Byte r2 = new java.lang.Byte
            r2.<init>(r11)
            r8 = 0
            r1[r8] = r2
            r2 = 1
            r1[r2] = r12
            r3 = 2
            r1[r3] = r13
            com.meituan.robust.ChangeQuickRedirect r4 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r5 = java.lang.Byte.TYPE
            r6[r8] = r5
            java.lang.Class<byte[]> r5 = byte[].class
            r6[r2] = r5
            java.lang.Class<java.lang.String> r2 = java.lang.String.class
            r6[r3] = r2
            java.lang.Class r7 = java.lang.Void.TYPE
            r5 = 0
            r9 = 12248(0x2fd8, float:1.7163E-41)
            r2 = r10
            r3 = r4
            r4 = r5
            r5 = r9
            com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r1 = r1.isSupported
            if (r1 == 0) goto L_0x0033
            return
        L_0x0033:
            r1 = r10
            if (r11 == r0) goto L_0x0037
            return
        L_0x0037:
            java.lang.Object r0 = r1.i
            monitor-enter(r0)
            boolean r2 = r1.h     // Catch:{ all -> 0x0084 }
            if (r2 == 0) goto L_0x004c
            byte[] r2 = r1.j     // Catch:{ all -> 0x0084 }
            if (r2 != 0) goto L_0x0045
            r1.j = r12     // Catch:{ all -> 0x0084 }
            goto L_0x004a
        L_0x0045:
            java.lang.String r2 = "cache pdu already exists"
            r1.k(r2)     // Catch:{ all -> 0x0084 }
        L_0x004a:
            monitor-exit(r0)     // Catch:{ all -> 0x0084 }
            return
        L_0x004c:
            monitor-exit(r0)     // Catch:{ all -> 0x0084 }
            java.lang.Object r0 = r12.clone()
            byte[] r0 = (byte[]) r0
            r1.k = r0
            com.telink.ble.mesh.entity.RemoteProvisioningDevice r0 = r1.d
            int r0 = r0.x()
            int r2 = r1.f
            byte r2 = (byte) r2
            byte[] r3 = r1.k
            com.telink.ble.mesh.core.message.rp.ProvisioningPduSendMessage r0 = com.telink.ble.mesh.core.message.rp.ProvisioningPduSendMessage.I(r0, r8, r2, r3)
            r2 = 8
            r0.E(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "send provisioning pdu: "
            r2.append(r3)
            int r3 = r1.f
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r1.k(r2)
            r1.n(r0)
            return
        L_0x0084:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0084 }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.telink.ble.mesh.core.access.RemoteProvisioningController.b(byte, byte[], java.lang.String):void");
    }

    private void k(String logMessage) {
        if (!PatchProxy.proxy(new Object[]{logMessage}, this, changeQuickRedirect, false, 12249, new Class[]{String.class}, Void.TYPE).isSupported) {
            MeshLog.i(logMessage + "  RemoteProvisioning.macAddress=" + this.n);
        }
    }
}
