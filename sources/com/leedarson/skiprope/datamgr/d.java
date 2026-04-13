package com.leedarson.skiprope.datamgr;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.leedarson.base.utils.e;
import com.leedarson.serviceimpl.blec075.BleC075ServiceImpl;
import com.leedarson.serviceimpl.blec075.h;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import com.leedarson.serviceinterface.BleC075Service;
import com.leedarson.skiprope.bean.SRDeviceHistoryDataBean;
import com.leedarson.skiprope.bean.SRDeviceNotifyDataBean;
import com.leedarson.skiprope.bean.StartConfigBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.tutk.IOTC.IOTCAPIs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.UUID;

/* compiled from: SkipRopeDataParser */
public class d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final byte a = -96;
    private final byte b = -94;
    private final byte c = -95;
    private final byte d = -93;
    private final byte e = -92;
    private final byte f = -91;
    private final byte g = -64;
    private final byte h = -62;
    /* access modifiers changed from: private */
    public boolean i = false;
    private String j;
    private StartConfigBean k;
    private com.leedarson.skiprope.callback.c l;
    private com.leedarson.skiprope.player.d m;
    private com.leedarson.skiprope.bean.a n = com.leedarson.skiprope.bean.a.IDLE;
    private com.leedarson.skiprope.ctrl.c o;
    private Handler p;
    private c q;
    private String r;
    SRDeviceHistoryDataBean s;
    StringBuilder t;
    SRDeviceHistoryDataBean u;
    StringBuilder v;

    static /* synthetic */ void b(d x0, byte x1, byte[] x2) {
        Object[] objArr = {x0, new Byte(x1), x2};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9561, new Class[]{d.class, Byte.TYPE, byte[].class}, Void.TYPE).isSupported) {
            x0.k(x1, x2);
        }
    }

    static /* synthetic */ void c(d x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 9562, new Class[]{d.class}, Void.TYPE).isSupported) {
            x0.i();
        }
    }

    public String f() {
        StartConfigBean startConfigBean = this.k;
        if (startConfigBean != null) {
            return startConfigBean.mac;
        }
        return this.r;
    }

    public d(com.leedarson.skiprope.ctrl.c playerController, String key) {
        this.m = new com.leedarson.skiprope.player.d(playerController);
        this.o = playerController;
        this.p = new Handler(Looper.getMainLooper());
        this.q = new c(this, (a) null);
        this.r = key;
    }

    public void m(String bleMac) {
        this.j = bleMac;
    }

    public void r(String mac, com.leedarson.skiprope.callback.c srNotifyDataCallback) {
        Class[] clsArr = {String.class, com.leedarson.skiprope.callback.c.class};
        if (!PatchProxy.proxy(new Object[]{mac, srNotifyDataCallback}, this, changeQuickRedirect, false, 9543, clsArr, Void.TYPE).isSupported) {
            this.j = mac;
            this.l = srNotifyDataCallback;
            BleC075Service bleC075Service = (BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class);
            com.leedarson.skiprope.util.a.a("startListenNotify ble mac:" + this.j + ",key=" + this.r);
            if (bleC075Service != null) {
                bleC075Service.commonIndicate(mac, UUID.fromString("0000FFB0-0000-1000-8000-00805F9B34FB"), UUID.fromString("0000FFB2-0000-1000-8000-00805F9B34FB"), "", new a());
            }
        }
    }

    /* compiled from: SkipRopeDataParser */
    public class a extends com.leedarson.skiprope.callback.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onNotifySuccess(String str, String mac) {
            Class<String> cls = String.class;
            Class[] clsArr = {cls, cls};
            if (!PatchProxy.proxy(new Object[]{str, mac}, this, changeQuickRedirect, false, 9563, clsArr, Void.TYPE).isSupported) {
                com.leedarson.skiprope.util.a.c("onNotifySuccess:" + mac);
            }
        }

        public void onNotifyFail(Exception exception, String str, String str2, int gattStatus) {
            Class<String> cls = String.class;
            Object[] objArr = {exception, str, str2, new Integer(gattStatus)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9564, new Class[]{Exception.class, cls, cls, Integer.TYPE}, Void.TYPE).isSupported) {
                com.leedarson.skiprope.util.a.b("onNotifyFail:" + exception + ",gattStatus:" + gattStatus);
            }
        }

        public void a(byte[] data) {
            if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 9565, new Class[]{byte[].class}, Void.TYPE).isSupported) {
                if (!d.this.i) {
                    com.leedarson.skiprope.util.a.e("parseNotifyData isStart=false,return");
                    return;
                }
                com.leedarson.skiprope.util.a.a("FFB2 indicate-->" + h.b(data));
                if (data[0] == -84 && data[1] == 96 && data.length > 10) {
                    byte[] frameData = new byte[(data.length - 3)];
                    System.arraycopy(data, 3, frameData, 0, frameData.length);
                    try {
                        d.b(d.this, data[2], frameData);
                    } catch (Exception e) {
                        e.printStackTrace();
                        com.leedarson.log.elk.a t = com.leedarson.log.elk.a.y(d.this).t("LdsSkipRope");
                        t.p("parseNotifyData error:" + e.toString() + ",data[]:" + h.b(data)).a().b();
                    }
                }
            }
        }
    }

    private void k(byte cmd, byte[] frameData) {
        if (!PatchProxy.proxy(new Object[]{new Byte(cmd), frameData}, this, changeQuickRedirect, false, 9544, new Class[]{Byte.TYPE, byte[].class}, Void.TYPE).isSupported) {
            switch (cmd) {
                case -96:
                    this.n = com.leedarson.skiprope.bean.a.REALTIME;
                    SRDeviceNotifyDataBean spDeviceNotifyDataBean = new SRDeviceNotifyDataBean(1);
                    spDeviceNotifyDataBean.mac = f();
                    spDeviceNotifyDataBean.mode = frameData[0];
                    spDeviceNotifyDataBean.target = (int) h.a(new byte[]{frameData[1], frameData[2]}, 2);
                    long jumpedSecond = h.a(new byte[]{frameData[3], frameData[4]}, 2);
                    long jumpedNum = h.a(new byte[]{frameData[5], frameData[6]}, 2);
                    if (frameData[7] == 1) {
                        spDeviceNotifyDataBean.jumpedSecondActually = h.a(new byte[]{frameData[8], frameData[9]}, 2);
                    }
                    spDeviceNotifyDataBean.totalTime = (int) jumpedSecond;
                    spDeviceNotifyDataBean.totalCount = (int) jumpedNum;
                    com.leedarson.skiprope.util.a.c("实时数据:" + spDeviceNotifyDataBean.toString());
                    this.m.c(spDeviceNotifyDataBean);
                    com.leedarson.skiprope.callback.c cVar = this.l;
                    if (cVar != null) {
                        cVar.a(spDeviceNotifyDataBean);
                        return;
                    }
                    return;
                case -95:
                case -92:
                    this.n = com.leedarson.skiprope.bean.a.FINALLY;
                    l(cmd, frameData);
                    return;
                case -93:
                case IOTCAPIs.IOTC_ER_MASTER_INVALID /*-91*/:
                    this.n = com.leedarson.skiprope.bean.a.HISTORY;
                    j(cmd, frameData);
                    return;
                default:
                    return;
            }
        }
    }

    private void j(byte b2, byte[] bArr) {
        if (!PatchProxy.proxy(new Object[]{new Byte(b2), bArr}, this, changeQuickRedirect, false, 9545, new Class[]{Byte.TYPE, byte[].class}, Void.TYPE).isSupported) {
            byte[] frameData = bArr;
            byte cmd = b2;
            byte num = frameData[0];
            byte index = frameData[1];
            if (index == 0) {
                SRDeviceHistoryDataBean sRDeviceHistoryDataBean = new SRDeviceHistoryDataBean();
                this.s = sRDeviceHistoryDataBean;
                sRDeviceHistoryDataBean.timestamp = h.a(new byte[]{frameData[2], frameData[3], frameData[4], frameData[5]}, 4);
                SRDeviceHistoryDataBean sRDeviceHistoryDataBean2 = this.s;
                sRDeviceHistoryDataBean2.mode = frameData[6];
                sRDeviceHistoryDataBean2.target = (int) h.a(new byte[]{frameData[7], frameData[8]}, 2);
                this.s.jumpedSecondActually = (long) ((int) h.a(new byte[]{frameData[9], frameData[10]}, 2));
                this.s.jumpedCountActually = (long) ((int) h.a(new byte[]{frameData[11], frameData[12]}, 2));
                int a2 = (frameData[14] & 240) << 4;
                SRDeviceHistoryDataBean sRDeviceHistoryDataBean3 = this.s;
                byte b3 = frameData[13] | a2;
                sRDeviceHistoryDataBean3.meanFrequency = b3;
                if (b3 < 0) {
                    sRDeviceHistoryDataBean3.meanFrequency = b3 + 256;
                }
                byte b4 = frameData[15] | ((frameData[14] & 15) << 8);
                sRDeviceHistoryDataBean3.maxFrequency = b4;
                if (b4 < 0) {
                    sRDeviceHistoryDataBean3.maxFrequency = b4 + 256;
                }
                com.leedarson.skiprope.util.a.c("首帧历史数据:" + this.s.toString());
            } else if (cmd == -93) {
                if (this.s == null) {
                    this.s = new SRDeviceHistoryDataBean();
                }
                this.s.addHistoryItem(new SRDeviceHistoryDataBean.HistoryItemBean((int) h.a(new byte[]{frameData[2], frameData[3]}, 2), (int) h.a(new byte[]{frameData[4], frameData[5]}, 2)));
                this.s.addHistoryItem(new SRDeviceHistoryDataBean.HistoryItemBean((int) h.a(new byte[]{frameData[6], frameData[7]}, 2), (int) h.a(new byte[]{frameData[8], frameData[9]}, 2)));
                this.s.addHistoryItem(new SRDeviceHistoryDataBean.HistoryItemBean((int) h.a(new byte[]{frameData[10], frameData[11]}, 2), (int) h.a(new byte[]{frameData[12], frameData[13]}, 2)));
                com.leedarson.skiprope.util.a.c("第" + index + "帧历史数据:" + this.s.toString());
                if (index == num - 1) {
                    n();
                    if (this.l != null) {
                        this.l.a(SRDeviceNotifyDataBean.fromHistoryBean(f(), 3, this.s));
                    }
                }
            } else if (cmd == -91) {
                if (this.s == null) {
                    this.s = new SRDeviceHistoryDataBean();
                }
                if (TextUtils.isEmpty(this.t)) {
                    this.t = new StringBuilder();
                }
                String framDataStr = h.b(frameData);
                this.t.append(framDataStr.substring(4, framDataStr.length() - 2));
                if (index == num - 1) {
                    byte[] newFrameData = h.n(this.t.toString());
                    this.t = null;
                    for (int i2 = 2; i2 < newFrameData.length; i2 += 4) {
                        int d1 = (int) h.a(new byte[]{newFrameData[i2], newFrameData[i2 + 1]}, 2);
                        if (d1 == 0) {
                            break;
                        }
                        this.s.addHistoryItem(new SRDeviceHistoryDataBean.HistoryItemBean(d1, (int) h.a(new byte[]{newFrameData[i2 + 2], newFrameData[i2 + 3]}, 2)));
                    }
                    n();
                    if (this.l != null) {
                        this.l.a(SRDeviceNotifyDataBean.fromHistoryBean(f(), 3, this.s));
                    }
                }
            }
        }
    }

    private void l(byte b2, byte[] bArr) {
        if (!PatchProxy.proxy(new Object[]{new Byte(b2), bArr}, this, changeQuickRedirect, false, 9546, new Class[]{Byte.TYPE, byte[].class}, Void.TYPE).isSupported) {
            byte[] frameData = bArr;
            byte cmd = b2;
            byte num = frameData[0];
            byte index = frameData[1];
            if (index == 0) {
                SRDeviceHistoryDataBean sRDeviceHistoryDataBean = new SRDeviceHistoryDataBean();
                this.u = sRDeviceHistoryDataBean;
                sRDeviceHistoryDataBean.timestamp = h.a(new byte[]{frameData[2], frameData[3], frameData[4], frameData[5]}, 4);
                SRDeviceHistoryDataBean sRDeviceHistoryDataBean2 = this.u;
                sRDeviceHistoryDataBean2.mode = frameData[6];
                sRDeviceHistoryDataBean2.target = (int) h.a(new byte[]{frameData[7], frameData[8]}, 2);
                this.u.jumpedSecondActually = (long) ((int) h.a(new byte[]{frameData[9], frameData[10]}, 2));
                this.u.jumpedCountActually = (long) ((int) h.a(new byte[]{frameData[11], frameData[12]}, 2));
                int a2 = (frameData[14] & 240) << 4;
                SRDeviceHistoryDataBean sRDeviceHistoryDataBean3 = this.u;
                byte b3 = frameData[13] | a2;
                sRDeviceHistoryDataBean3.meanFrequency = b3;
                if (b3 < 0) {
                    sRDeviceHistoryDataBean3.meanFrequency = b3 + 256;
                }
                byte b4 = frameData[15] | ((frameData[14] & 15) << 8);
                sRDeviceHistoryDataBean3.maxFrequency = b4;
                if (b4 < 0) {
                    sRDeviceHistoryDataBean3.maxFrequency = b4 + 256;
                }
                com.leedarson.skiprope.util.a.c("首帧结果数据:" + this.u.toString());
                return;
            }
            if (this.u == null) {
                this.u = new SRDeviceHistoryDataBean();
                com.leedarson.log.elk.a e2 = com.leedarson.log.elk.a.y(d.class).t("LdsSkipRope").o("debug").e("parseResult");
                e2.p("收到未从首帧开始的结果数据 frameData:" + e.a(frameData)).a().b();
            }
            if (cmd == -95) {
                this.u.addHistoryItem(new SRDeviceHistoryDataBean.HistoryItemBean((int) h.a(new byte[]{frameData[2], frameData[3]}, 2), (int) h.a(new byte[]{frameData[4], frameData[5]}, 2)));
                this.u.addHistoryItem(new SRDeviceHistoryDataBean.HistoryItemBean((int) h.a(new byte[]{frameData[6], frameData[7]}, 2), (int) h.a(new byte[]{frameData[8], frameData[9]}, 2)));
                this.u.addHistoryItem(new SRDeviceHistoryDataBean.HistoryItemBean((int) h.a(new byte[]{frameData[10], frameData[11]}, 2), (int) h.a(new byte[]{frameData[12], frameData[13]}, 2)));
                com.leedarson.skiprope.util.a.c("第" + index + "帧结果数据:" + this.u.toString());
                if (index == num - 1) {
                    o();
                    i();
                    this.p.removeCallbacks(this.q);
                    if (this.l != null) {
                        SRDeviceNotifyDataBean notifyDataBean = SRDeviceNotifyDataBean.fromHistoryBean(f(), 2, this.u);
                        this.l.a(notifyDataBean);
                        if (!this.m.d()) {
                            this.m.i(notifyDataBean.totalCount);
                        }
                    }
                }
            } else if (cmd == -92) {
                if (TextUtils.isEmpty(this.v)) {
                    this.v = new StringBuilder();
                }
                String framDataStr = h.b(frameData);
                this.v.append(framDataStr.substring(4, framDataStr.length() - 2));
                if (index == num - 1) {
                    byte[] newFrameData = h.n(this.v.toString());
                    this.v = null;
                    for (int i2 = 2; i2 < newFrameData.length; i2 += 4) {
                        int d1 = (int) h.a(new byte[]{newFrameData[i2], newFrameData[i2 + 1]}, 2);
                        if (d1 == 0) {
                            break;
                        }
                        this.u.addHistoryItem(new SRDeviceHistoryDataBean.HistoryItemBean(d1, (int) h.a(new byte[]{newFrameData[i2 + 2], newFrameData[i2 + 3]}, 2)));
                    }
                    o();
                    i();
                    this.p.removeCallbacks(this.q);
                    if (this.l != null) {
                        SRDeviceNotifyDataBean notifyDataBean2 = SRDeviceNotifyDataBean.fromHistoryBean(f(), 2, this.u);
                        this.l.a(notifyDataBean2);
                        if (!this.m.d()) {
                            this.m.i(notifyDataBean2.totalCount);
                        }
                    }
                }
            }
        }
    }

    public static boolean g(ArrayList<BluetoothGattService> list) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{list}, (Object) null, changeQuickRedirect, true, 9547, new Class[]{ArrayList.class}, Boolean.TYPE);
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

    public void q(StartConfigBean configBean) {
        if (!PatchProxy.proxy(new Object[]{configBean}, this, changeQuickRedirect, false, 9548, new Class[]{StartConfigBean.class}, Void.TYPE).isSupported) {
            com.leedarson.skiprope.util.a.a(" 跳绳指令下发start   isStart=true");
            this.i = true;
            this.k = configBean;
            this.m.f(configBean);
            if (configBean.resetDevice) {
                StartConfigBean.TrainingMode trainingMode = configBean.training;
                p(trainingMode.mode, trainingMode.target);
            }
        }
    }

    public void s() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9549, new Class[0], Void.TYPE).isSupported) {
            Handler handler = this.p;
            if (handler != null) {
                handler.removeCallbacks(this.q);
            }
            com.leedarson.skiprope.util.a.a(" 跳绳指令下发  startReceiveData  isStart=" + this.i);
            this.i = true;
        }
    }

    public void v() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9550, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.skiprope.util.a.a(" 跳绳指令下发  stopReceiveData   isStart=" + this.i);
            this.i = false;
        }
    }

    public void t() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9551, new Class[0], Void.TYPE).isSupported) {
            u();
            if (!this.m.d()) {
                this.m.i(-1);
            }
            this.o.n();
            this.p.postDelayed(this.q, 1200);
        }
    }

    private void i() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9552, new Class[0], Void.TYPE).isSupported) {
            this.o.n();
            this.n = com.leedarson.skiprope.bean.a.IDLE;
            this.i = false;
            com.leedarson.skiprope.util.a.a(" 跳绳指令下发  onStop   isStart=" + this.i);
        }
    }

    private void u() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9553, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.skiprope.util.a.c("stopJump");
            byte[] cmdBytes = new byte[18];
            cmdBytes[0] = -62;
            cmdBytes[1] = 1;
            w(cmdBytes);
        }
    }

    private void p(int mode, int target) {
        Object[] objArr = {new Integer(mode), new Integer(target)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9554, clsArr, Void.TYPE).isSupported) {
            com.leedarson.skiprope.util.a.c("setTrainingMode mode:" + mode + ",target:" + target + ",key=" + this.r);
            byte[] cmdBytes = new byte[18];
            cmdBytes[0] = -64;
            cmdBytes[1] = (byte) mode;
            byte[] targetBytes = h.j((long) target, 2);
            cmdBytes[2] = targetBytes[0];
            cmdBytes[3] = targetBytes[1];
            byte[] timeBytes = h.j(System.currentTimeMillis() / 1000, 4);
            System.arraycopy(timeBytes, 0, cmdBytes, 4, timeBytes.length);
            int intTimeZone = e();
            byte[] timeZone = h.j((long) (Math.abs(intTimeZone) * 60), 2);
            if (intTimeZone < 0) {
                timeZone[0] = (byte) (timeZone[0] | OTACommand.RESP_ID_VERSION);
            }
            cmdBytes[8] = timeZone[0];
            cmdBytes[9] = timeZone[1];
            w(cmdBytes);
        }
    }

    private void n() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9555, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.skiprope.util.a.c("下发历史数据Response");
            byte[] cmdBytes = new byte[18];
            cmdBytes[0] = -62;
            cmdBytes[1] = 0;
            cmdBytes[2] = -93;
            w(cmdBytes);
        }
    }

    private void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9556, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.skiprope.util.a.c("下发实时结果Response");
            byte[] cmdBytes = new byte[18];
            cmdBytes[0] = -62;
            cmdBytes[1] = 0;
            cmdBytes[2] = -95;
            w(cmdBytes);
        }
    }

    private void w(byte[] dataFrame) {
        if (!PatchProxy.proxy(new Object[]{dataFrame}, this, changeQuickRedirect, false, 9557, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            int sum = 0;
            for (int i2 = 0; i2 < 17; i2++) {
                sum += dataFrame[i2];
            }
            dataFrame[17] = (byte) (sum & 255);
            byte[] sendData = new byte[20];
            sendData[0] = -84;
            sendData[1] = 96;
            System.arraycopy(dataFrame, 0, sendData, 2, dataFrame.length);
            com.leedarson.skiprope.util.a.c("发送数据:" + h.b(sendData));
            x(sendData);
        }
    }

    private void x(byte[] writeData) {
        if (!PatchProxy.proxy(new Object[]{writeData}, this, changeQuickRedirect, false, 9558, new Class[]{byte[].class}, Void.TYPE).isSupported) {
            if (TextUtils.isEmpty(this.j)) {
                com.leedarson.log.elk.a.y(BleC075ServiceImpl.class).t("LdsSkipRope").o("info").p("bleMac 为空").e("writeBle").a().b();
                return;
            }
            ((BleC075Service) com.alibaba.android.arouter.launcher.a.c().g(BleC075Service.class)).commonWrite(this.j, (BluetoothDevice) null, UUID.fromString("0000FFB0-0000-1000-8000-00805F9B34FB"), UUID.fromString("0000FFB1-0000-1000-8000-00805F9B34FB"), "", writeData, (String) null, new b(writeData), false, -1);
        }
    }

    /* compiled from: SkipRopeDataParser */
    public class b extends com.leedarson.skiprope.callback.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ byte[] a;

        b(byte[] bArr) {
            this.a = bArr;
        }

        public void b(int i, int i2, byte[] justWrite, String str) {
            Object[] objArr = {new Integer(i), new Integer(i2), justWrite, str};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Integer.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 9566, new Class[]{cls, cls, byte[].class, String.class}, Void.TYPE).isSupported) {
                com.leedarson.skiprope.util.a.c("写入成功:" + h.b(justWrite));
            }
        }

        public void a(Exception exception, String str) {
            Class[] clsArr = {Exception.class, String.class};
            if (!PatchProxy.proxy(new Object[]{exception, str}, this, changeQuickRedirect, false, 9567, clsArr, Void.TYPE).isSupported) {
                com.leedarson.skiprope.util.a.b("写入失败:" + h.b(this.a) + "ex:" + exception.toString());
            }
        }
    }

    public static int e() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9559, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        try {
            int i2 = Integer.parseInt(d().substring(3, 6));
            com.leedarson.skiprope.util.a.c("时区:" + i2);
            return i2;
        } catch (Exception e2) {
            timber.log.a.c("getIntTimeZone err:" + e2.getMessage(), new Object[0]);
            return 0;
        }
    }

    public static String d() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 9560, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return TimeZone.getDefault().getDisplayName(false, 0);
    }

    /* compiled from: SkipRopeDataParser */
    public final class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        private c() {
        }

        /* synthetic */ c(d x0, a x1) {
            this();
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9568, new Class[0], Void.TYPE).isSupported) {
                d.c(d.this);
            }
        }
    }

    public boolean h() {
        return this.i;
    }
}
