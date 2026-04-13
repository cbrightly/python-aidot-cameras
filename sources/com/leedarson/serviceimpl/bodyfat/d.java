package com.leedarson.serviceimpl.bodyfat;

import android.bluetooth.BluetoothGattService;
import androidx.core.view.MotionEventCompat;
import com.icomon.icbodyfatalgorithms.ICBodyFatAlgorithms;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.bodyfat.model.BFDeviceNotifyDataBean;
import com.leedarson.serviceimpl.bodyfat.model.BodyFatDataBean;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.l;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import org.glassfish.grizzly.http.server.util.MappingData;
import timber.log.a;

/* compiled from: BodyFatManager */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    String a = "BodyFat";
    private final byte b = -79;
    private final byte c = -78;
    private final byte d = -96;
    private final byte e = -95;
    private final byte f = -94;
    private final byte g = -93;
    private final byte h = -92;
    private final String i = "0000FFB0-0000-1000-8000-00805F9B34FB";
    private final String j = "0000FFB1-0000-1000-8000-00805F9B34FB";
    private final String k = "0000FFB2-0000-1000-8000-00805F9B34FB";
    private final String l = "0000FFB3-0000-1000-8000-00805F9B34FB";
    private ExecutorService m;

    static /* synthetic */ void a(d x0, byte[] x1, com.leedarson.serviceimpl.bodyfat.callback.a x2) {
        Class[] clsArr = {d.class, byte[].class, com.leedarson.serviceimpl.bodyfat.callback.a.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 6906, clsArr, Void.TYPE).isSupported) {
            x0.g(x1, x2);
        }
    }

    public void i(int i2, String str, List<com.leedarson.serviceimpl.bodyfat.model.a> list, com.leedarson.serviceimpl.bodyfat.callback.c cVar) {
        char c2 = 0;
        if (!PatchProxy.proxy(new Object[]{new Integer(i2), str, list, cVar}, this, changeQuickRedirect, false, 6896, new Class[]{Integer.TYPE, String.class, List.class, com.leedarson.serviceimpl.bodyfat.callback.c.class}, Void.TYPE).isSupported) {
            String mac = str;
            com.leedarson.serviceimpl.bodyfat.callback.c bfWriteCallback = cVar;
            int unit = i2;
            List<com.leedarson.serviceimpl.bodyfat.model.a> list2 = list;
            if (list2 != null && list2.size() != 0) {
                byte[] messageBytes = new byte[((list2.size() * 4) + 2)];
                messageBytes[0] = -78;
                messageBytes[1] = (byte) list2.size();
                int i3 = 0;
                while (i3 < list2.size()) {
                    com.leedarson.serviceimpl.bodyfat.model.a user = list2.get(i3);
                    messageBytes[(4 * i3) + 2] = (byte) user.c;
                    byte[] weightBytes = c.b((int) (user.e * 100.0f), 2);
                    messageBytes[(4 * i3) + 3] = weightBytes[c2];
                    messageBytes[(4 * i3) + 4] = weightBytes[1];
                    messageBytes[(4 * i3) + 5] = c(user.a, user.b);
                    i3++;
                    c2 = 0;
                }
                e().execute(new a(messageBytes, unit, mac, bfWriteCallback));
            }
        }
    }

    /* compiled from: BodyFatManager */
    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ byte[] c;
        final /* synthetic */ int d;
        final /* synthetic */ String f;
        final /* synthetic */ com.leedarson.serviceimpl.bodyfat.callback.c q;

        a(byte[] bArr, int i, String str, com.leedarson.serviceimpl.bodyfat.callback.c cVar) {
            this.c = bArr;
            this.d = i;
            this.f = str;
            this.q = cVar;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6907, new Class[0], Void.TYPE).isSupported) {
                a.b g = timber.log.a.g(d.this.a);
                g.a("BodyFat.setUserInfoList:" + h.b(this.c), new Object[0]);
                new a().f(this.d, this.c, this.f, "0000FFB0-0000-1000-8000-00805F9B34FB", "0000FFB1-0000-1000-8000-00805F9B34FB", this.q);
            }
        }
    }

    public void h(String mac, byte FFB3pkgNum, com.leedarson.serviceimpl.bodyfat.callback.c bfWriteCallback) {
        if (!PatchProxy.proxy(new Object[]{mac, new Byte(FFB3pkgNum), bfWriteCallback}, this, changeQuickRedirect, false, 6898, new Class[]{String.class, Byte.TYPE, com.leedarson.serviceimpl.bodyfat.callback.c.class}, Void.TYPE).isSupported) {
            byte[] messageBytes = new byte[3];
            try {
                messageBytes[0] = -96;
                messageBytes[1] = FFB3pkgNum;
                messageBytes[2] = 0;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            e().execute(new b(messageBytes, mac, bfWriteCallback));
        }
    }

    /* compiled from: BodyFatManager */
    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ byte[] c;
        final /* synthetic */ String d;
        final /* synthetic */ com.leedarson.serviceimpl.bodyfat.callback.c f;

        b(byte[] bArr, String str, com.leedarson.serviceimpl.bodyfat.callback.c cVar) {
            this.c = bArr;
            this.d = str;
            this.f = cVar;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6909, new Class[0], Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("BodyFat");
                g.a("setReady:" + h.b(this.c), new Object[0]);
                new a().f(0, this.c, this.d, "0000FFB0-0000-1000-8000-00805F9B34FB", "0000FFB1-0000-1000-8000-00805F9B34FB", this.f);
            }
        }
    }

    private byte c(int age, int sex) {
        byte b2 = (byte) age;
        if (sex == 1) {
            return (byte) (b2 | OTACommand.RESP_ID_VERSION);
        }
        return b2;
    }

    public synchronized void d(int height, float weight, int age, int sex, boolean isSportman, int impedance, com.leedarson.serviceimpl.bodyfat.callback.d callback) {
        Object[] objArr = {new Integer(height), new Float(weight), new Integer(age), new Integer(sex), new Byte(isSportman ? (byte) 1 : 0), new Integer(impedance), callback};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6899, new Class[]{cls, Float.TYPE, cls, cls, Boolean.TYPE, cls, com.leedarson.serviceimpl.bodyfat.callback.d.class}, Void.TYPE).isSupported) {
            l.F(b(height, weight, age, sex, isSportman, impedance)).b0(io.reactivex.schedulers.a.c()).J(io.reactivex.schedulers.a.c()).Y(new c(callback), new C0133d());
        }
    }

    /* compiled from: BodyFatManager */
    public class c implements io.reactivex.functions.e<BodyFatDataBean> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.bodyfat.callback.d c;

        c(com.leedarson.serviceimpl.bodyfat.callback.d dVar) {
            this.c = dVar;
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6911, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((BodyFatDataBean) obj);
            }
        }

        public void a(BodyFatDataBean bodyFatData) {
            if (!PatchProxy.proxy(new Object[]{bodyFatData}, this, changeQuickRedirect, false, 6910, new Class[]{BodyFatDataBean.class}, Void.TYPE).isSupported) {
                this.c.a(bodyFatData);
            }
        }
    }

    /* renamed from: com.leedarson.serviceimpl.bodyfat.d$d  reason: collision with other inner class name */
    /* compiled from: BodyFatManager */
    public class C0133d implements io.reactivex.functions.e<Throwable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        C0133d() {
        }

        public /* bridge */ /* synthetic */ void accept(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 6913, new Class[]{Object.class}, Void.TYPE).isSupported) {
                a((Throwable) obj);
            }
        }

        public void a(Throwable throwable) {
            if (!PatchProxy.proxy(new Object[]{throwable}, this, changeQuickRedirect, false, 6912, new Class[]{Throwable.class}, Void.TYPE).isSupported) {
                timber.log.a.d(throwable);
            }
        }
    }

    private BodyFatDataBean b(int i2, float f2, int i3, int i4, boolean z, int i5) {
        Object[] objArr = {new Integer(i2), new Float(f2), new Integer(i3), new Integer(i4), new Byte(z ? (byte) 1 : 0), new Integer(i5)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6900, new Class[]{cls, Float.TYPE, cls, cls, Boolean.TYPE, cls}, BodyFatDataBean.class);
        if (proxy.isSupported) {
            return (BodyFatDataBean) proxy.result;
        }
        float weight = f2;
        int impedance = i5;
        int sex = i4;
        int height = i2;
        int age = i3;
        boolean isSportman = z;
        long l2 = System.currentTimeMillis();
        com.icomon.icbodyfatalgorithms.c type = com.icomon.icbodyfatalgorithms.c.ICBodyFatAlgorithmsTypeWLA07;
        com.icomon.icbodyfatalgorithms.a peopleType = isSportman ? com.icomon.icbodyfatalgorithms.a.ICBodyFatAlgorithmsPeopleTypeSportsMan : com.icomon.icbodyfatalgorithms.a.ICBodyFatAlgorithmsPeopleTypeNormal;
        com.icomon.icbodyfatalgorithms.b sexType = sex == 1 ? com.icomon.icbodyfatalgorithms.b.Male : com.icomon.icbodyfatalgorithms.b.Female;
        com.icomon.icbodyfatalgorithms.a peopleType2 = peopleType;
        boolean z2 = isSportman;
        double bmi = ICBodyFatAlgorithms.a((double) weight, height, type, peopleType);
        double bodyFatPercent = ICBodyFatAlgorithms.c((double) weight, height, age, (double) impedance, 0.0d, sexType, type, peopleType2);
        int i6 = height;
        int i7 = age;
        com.icomon.icbodyfatalgorithms.b bVar = sexType;
        com.icomon.icbodyfatalgorithms.c cVar = type;
        com.icomon.icbodyfatalgorithms.a aVar = peopleType2;
        double subcutaneousFatPercent = ICBodyFatAlgorithms.j((double) weight, i6, i7, (double) impedance, 0.0d, bVar, cVar, aVar);
        double visceralFat = ICBodyFatAlgorithms.k((double) weight, i6, i7, (double) impedance, 0.0d, bVar, cVar, aVar);
        double musclePercent = ICBodyFatAlgorithms.f((double) weight, i6, i7, (double) impedance, 0.0d, bVar, cVar, aVar);
        int bmr = ICBodyFatAlgorithms.b((double) weight, i6, i7, (double) impedance, 0.0d, bVar, cVar, aVar);
        double boneMass = ICBodyFatAlgorithms.d((double) weight, i6, i7, (double) impedance, 0.0d, bVar, cVar, aVar);
        double moisturePercent = ICBodyFatAlgorithms.e((double) weight, i6, i7, (double) impedance, 0.0d, bVar, cVar, aVar);
        int physicalAge = ICBodyFatAlgorithms.g((double) weight, i6, i7, (double) impedance, 0.0d, bVar, cVar, aVar);
        double bodyFatPercent2 = bodyFatPercent;
        double protein = ICBodyFatAlgorithms.h((double) weight, i6, i7, (double) impedance, 0.0d, bVar, cVar, aVar);
        double skeletalMuscle = ICBodyFatAlgorithms.i((double) weight, i6, i7, (double) impedance, 0.0d, bVar, cVar, aVar);
        BodyFatDataBean dataBean = new BodyFatDataBean();
        dataBean.BMI = (float) bmi;
        double d2 = bmi;
        dataBean.bodyFat = (float) bodyFatPercent2;
        dataBean.subcutaneousFat = (float) subcutaneousFatPercent;
        dataBean.visceralFat = (float) visceralFat;
        dataBean.muscle = (float) musclePercent;
        dataBean.boneMass = (float) boneMass;
        dataBean.moisture = (float) moisturePercent;
        dataBean.physicalAge = physicalAge;
        dataBean.protein = (float) protein;
        dataBean.skeletalMuscle = (float) skeletalMuscle;
        dataBean.BMR = bmr;
        dataBean.calNewParams(weight, sex, height);
        double d3 = skeletalMuscle;
        long duration = System.currentTimeMillis() - l2;
        int i8 = impedance;
        StringBuilder sb = new StringBuilder();
        int i9 = sex;
        sb.append("getBodyFatData-->\nduration:");
        sb.append(duration);
        sb.append("\n");
        sb.append(dataBean.toString());
        timber.log.a.a(sb.toString(), new Object[0]);
        return dataBean;
    }

    public boolean f(ArrayList<BluetoothGattService> list) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 6901, new Class[]{ArrayList.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (list == null || list.size() == 0) {
            return false;
        }
        Iterator<BluetoothGattService> it = list.iterator();
        while (it.hasNext()) {
            if ("0000FFB0-0000-1000-8000-00805F9B34FB".equalsIgnoreCase(it.next().getUuid().toString())) {
                return true;
            }
        }
        return false;
    }

    public void k(String str, com.leedarson.serviceimpl.bodyfat.callback.a bfNotifyDataCallback) {
        Class[] clsArr = {String.class, com.leedarson.serviceimpl.bodyfat.callback.a.class};
        if (!PatchProxy.proxy(new Object[]{str, bfNotifyDataCallback}, this, changeQuickRedirect, false, 6902, clsArr, Void.TYPE).isSupported) {
            String mac = str;
            BleC075Service bleC075Service = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
            if (bleC075Service != null) {
                bleC075Service.commonNotify(mac, UUID.fromString("0000FFB0-0000-1000-8000-00805F9B34FB"), UUID.fromString("0000FFB2-0000-1000-8000-00805F9B34FB"), "", "", new e(bfNotifyDataCallback));
            }
        }
    }

    /* compiled from: BodyFatManager */
    public class e extends com.leedarson.serviceimpl.bodyfat.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ com.leedarson.serviceimpl.bodyfat.callback.a a;

        e(com.leedarson.serviceimpl.bodyfat.callback.a aVar) {
            this.a = aVar;
        }

        public void a(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6914, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                if (data != null && data.length == 20) {
                    byte[] frameData = new byte[16];
                    System.arraycopy(data, 3, frameData, 0, frameData.length);
                    d.a(d.this, frameData, this.a);
                }
            }
        }
    }

    public void j(String mac, com.leedarson.serviceimpl.bodyfat.callback.a bfNotifyDataCallback) {
        Class[] clsArr = {String.class, com.leedarson.serviceimpl.bodyfat.callback.a.class};
        if (!PatchProxy.proxy(new Object[]{mac, bfNotifyDataCallback}, this, changeQuickRedirect, false, 6903, clsArr, Void.TYPE).isSupported) {
            BleC075Service bleC075Service = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
            if (bleC075Service != null) {
                bleC075Service.commonIndicate(mac, UUID.fromString("0000FFB0-0000-1000-8000-00805F9B34FB"), UUID.fromString("0000FFB3-0000-1000-8000-00805F9B34FB"), "", new f(mac, bfNotifyDataCallback));
            }
        }
    }

    /* compiled from: BodyFatManager */
    public class f extends com.leedarson.serviceimpl.bodyfat.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String a;
        final /* synthetic */ com.leedarson.serviceimpl.bodyfat.callback.a b;

        f(String str, com.leedarson.serviceimpl.bodyfat.callback.a aVar) {
            this.a = str;
            this.b = aVar;
        }

        public void a(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 6915, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                a.b g = timber.log.a.g("BodyFat");
                g.a("FFB3 notify-->" + h.b(data), new Object[0]);
                if (data != null && data.length == 20) {
                    byte[] frameData = new byte[16];
                    System.arraycopy(data, 3, frameData, 0, frameData.length);
                    if (frameData[0] == -95) {
                        d.this.h(this.a, data[0], new a());
                        return;
                    }
                    d.a(d.this, frameData, this.b);
                }
            }
        }

        /* compiled from: BodyFatManager */
        public class a extends com.leedarson.serviceimpl.bodyfat.callback.c {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void b(int i, int i2, byte[] bArr, String str) {
                Object[] objArr = {new Integer(i), new Integer(i2), bArr, str};
                ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
                Class cls = Integer.TYPE;
                if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 6916, new Class[]{cls, cls, byte[].class, String.class}, Void.TYPE).isSupported) {
                    timber.log.a.g("BodyFat").a("setReady-->onBFWriteSuccess", new Object[0]);
                }
            }

            public void a(Exception exception, String str) {
                if (!PatchProxy.proxy(new Object[]{exception, str}, this, changeQuickRedirect, false, 6917, new Class[]{Exception.class, String.class}, Void.TYPE).isSupported) {
                    a.b g = timber.log.a.g("BodyFat");
                    g.a("setReady-->onBFWriteFailure" + exception.getMessage(), new Object[0]);
                }
            }
        }
    }

    private void g(byte[] bArr, com.leedarson.serviceimpl.bodyfat.callback.a aVar) {
        if (!PatchProxy.proxy(new Object[]{bArr, aVar}, this, changeQuickRedirect, false, 6904, new Class[]{byte[].class, com.leedarson.serviceimpl.bodyfat.callback.a.class}, Void.TYPE).isSupported) {
            com.leedarson.serviceimpl.bodyfat.callback.a bfNotifyDataCallback = aVar;
            byte[] frameData = bArr;
            if (frameData != null && frameData.length == 16) {
                byte cmd = frameData[0];
                if (cmd == -96) {
                    byte b2 = frameData[1];
                    byte b3 = frameData[2];
                } else if (cmd == -94) {
                    BFDeviceNotifyDataBean notifyData = new BFDeviceNotifyDataBean(1);
                    byte devState = frameData[1];
                    byte[] weightAndAlgorithmsType = new byte[4];
                    System.arraycopy(frameData, 2, weightAndAlgorithmsType, 0, weightAndAlgorithmsType.length);
                    notifyData.weight1000 = (weightAndAlgorithmsType[3] & 255) | ((weightAndAlgorithmsType[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((weightAndAlgorithmsType[1] << MappingData.PATH) & 16711680);
                    notifyData.algorithmsType = weightAndAlgorithmsType[0];
                    notifyData.state = devState;
                    notifyData.heartRate = frameData[6];
                    bfNotifyDataCallback.a(notifyData);
                } else if (cmd == -93) {
                    BFDeviceNotifyDataBean notifyData2 = new BFDeviceNotifyDataBean(2);
                    byte[] weightAndAlgorithmsType2 = new byte[4];
                    System.arraycopy(frameData, 1, weightAndAlgorithmsType2, 0, weightAndAlgorithmsType2.length);
                    notifyData2.weight1000 = (weightAndAlgorithmsType2[3] & 255) | ((weightAndAlgorithmsType2[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((weightAndAlgorithmsType2[1] << MappingData.PATH) & 16711680);
                    notifyData2.algorithmsType = weightAndAlgorithmsType2[0];
                    notifyData2.heartRate = frameData[5];
                    notifyData2.impedance = ((frameData[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (frameData[7] & 255);
                    bfNotifyDataCallback.a(notifyData2);
                } else if (cmd == -92) {
                    BFDeviceNotifyDataBean notifyData3 = new BFDeviceNotifyDataBean(3);
                    byte[] timestampBytes = new byte[4];
                    System.arraycopy(frameData, 1, timestampBytes, 0, timestampBytes.length);
                    long timeSecond = c.a(timestampBytes, 4);
                    byte[] weightAndAlgorithmsType3 = new byte[4];
                    System.arraycopy(frameData, 5, weightAndAlgorithmsType3, 0, weightAndAlgorithmsType3.length);
                    notifyData3.weight1000 = (weightAndAlgorithmsType3[3] & 255) | ((weightAndAlgorithmsType3[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | ((weightAndAlgorithmsType3[1] << MappingData.PATH) & 16711680);
                    notifyData3.algorithmsType = weightAndAlgorithmsType3[0];
                    notifyData3.heartRate = frameData[9];
                    notifyData3.impedance = 65280 | (frameData[10] << 8) | frameData[11] | 255;
                    notifyData3.timestamp = timeSecond;
                    bfNotifyDataCallback.a(notifyData3);
                }
            }
        }
    }

    private ExecutorService e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6905, new Class[0], ExecutorService.class);
        if (proxy.isSupported) {
            return (ExecutorService) proxy.result;
        }
        if (this.m == null) {
            this.m = com.leedarson.base.http.observer.l.i(1, "bodyfat-manager", 2);
        }
        return this.m;
    }
}
