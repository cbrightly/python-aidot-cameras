package com.leedarson.serviceimpl.bodyfat;

import android.bluetooth.BluetoothDevice;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.bodyfat.callback.c;
import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import timber.log.a;

/* compiled from: BFCommandDispatcher */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int a = 16;
    /* access modifiers changed from: private */
    public CountDownLatch b;
    /* access modifiers changed from: private */
    public AtomicInteger c;
    private int d;
    /* access modifiers changed from: private */
    public Exception e;

    public void f(int i, byte[] bArr, String str, String str2, String str3, c cVar) {
        int frameNo;
        byte[] dataSection;
        byte[] dataSection2;
        Class<String> cls = String.class;
        Object[] objArr = {new Integer(i), bArr, str, str2, str3, cVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6882, new Class[]{Integer.TYPE, byte[].class, cls, cls, cls, c.class}, Void.TYPE).isSupported) {
            byte[] dataByes = bArr;
            c bfWriteCallback = cVar;
            String serviceUUID = str2;
            int unit = i;
            String mac = str;
            String characterUUID = str3;
            int i2 = 16;
            if (dataByes.length % 16 == 0) {
                frameNo = dataByes.length / 16;
            } else {
                frameNo = (dataByes.length / 16) + 1;
            }
            this.d = frameNo;
            this.b = new CountDownLatch(frameNo);
            this.c = new AtomicInteger();
            int sequenceNum = com.leedarson.serviceimpl.ble.manager.c.b().d();
            int i3 = 0;
            while (i3 < frameNo) {
                if (i3 == frameNo - 1) {
                    if (dataByes.length % i2 == 0) {
                        dataSection2 = new byte[i2];
                    } else {
                        dataSection2 = new byte[(dataByes.length % i2)];
                    }
                    System.arraycopy(dataByes, i3 * 16, dataSection2, 0, dataSection2.length);
                    dataSection = dataSection2;
                } else {
                    byte[] dataSection3 = new byte[16];
                    System.arraycopy(dataByes, i3 * 16, dataSection3, 0, dataSection3.length);
                    dataSection = dataSection3;
                }
                byte[] sendFrame = e(sequenceNum, unit, dataSection, i3);
                a.b g = timber.log.a.g("BodyFat");
                g.h("BFCommandDispatcher send data:" + h.b(sendFrame), new Object[0]);
                BleC075Service bleC075Service = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
                if (bleC075Service != null) {
                    bleC075Service.commonWrite(mac, (BluetoothDevice) null, UUID.fromString(serviceUUID), UUID.fromString(characterUUID), "", sendFrame, (String) null, new C0132a(), false, -1);
                }
                i3++;
                i2 = 16;
            }
            try {
                timber.log.a.g("BodyFat").h("await......", new Object[0]);
                this.b.await(KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
                if (this.c.get() == this.d) {
                    bfWriteCallback.b(dataByes.length, dataByes.length, dataByes, "");
                    return;
                }
                if (this.e == null) {
                    this.e = new Exception("part of data send fail");
                }
                bfWriteCallback.a(this.e, "");
            } catch (InterruptedException e2) {
                e2.printStackTrace();
                bfWriteCallback.a(e2, "");
            }
        }
    }

    /* renamed from: com.leedarson.serviceimpl.bodyfat.a$a  reason: collision with other inner class name */
    /* compiled from: BFCommandDispatcher */
    public class C0132a extends c {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0132a() {
        }

        public void b(int i, int i2, byte[] bArr, String str) {
            Object[] objArr = {new Integer(i), new Integer(i2), bArr, str};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6884, new Class[]{cls, cls, byte[].class, String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("BodyFat").h("BFCommandDispatcher  onBFWriteSuccess......", new Object[0]);
                a.this.c.incrementAndGet();
                a.this.b.countDown();
            }
        }

        public void a(Exception exception, String str) {
            if (!PatchProxy.proxy(new Object[]{exception, str}, this, changeQuickRedirect, false, 6885, new Class[]{Exception.class, String.class}, Void.TYPE).isSupported) {
                timber.log.a.g("BodyFat").h("BFCommandDispatcher  onBFWriteFailure......", new Object[0]);
                a.this.b.countDown();
                Exception unused = a.this.e = exception;
            }
        }
    }

    private byte[] e(int pkgNo, int unit, byte[] bArr, int frameSeq) {
        Object[] objArr = {new Integer(pkgNo), new Integer(unit), bArr, new Integer(frameSeq)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls, byte[].class, cls};
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6883, clsArr, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        byte[] dataFrame = bArr;
        int len = dataFrame.length;
        byte[] packet = new byte[(dataFrame.length + 4)];
        packet[0] = (byte) pkgNo;
        packet[1] = (byte) len;
        packet[2] = (byte) frameSeq;
        System.arraycopy(dataFrame, 0, packet, 3, dataFrame.length);
        packet[packet.length - 1] = d(unit, dataFrame);
        return packet;
    }

    private static byte d(int unit, byte[] dataFrame) {
        int sum = 0;
        for (short i = 0; i < dataFrame.length; i = (short) (i + 1)) {
            sum += dataFrame[i];
        }
        return (byte) (((((byte) unit) & 7) << 5) | ((byte) (sum & 31)));
    }
}
