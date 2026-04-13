package com.leedarson.serviceimpl;

import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.leedarson.bean.AmpDotBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.Constants;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import meshsdk.BaseResp;
import timber.log.a;

/* compiled from: AutoIdentifier */
public class f {
    private static int a = BaseResp.ERR_MSG_SEND_FAIL;
    private static int b = 1500;
    private static f c;
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int d = 5000;
    private final String e = "lyric";
    private final String f = "happy";
    private final String g = "neutral";
    private String h = "neutral";
    private double i;
    private double j;
    private final String k = "Rhythm-Auto";
    private CopyOnWriteArrayList<AmpDotBean> l = new CopyOnWriteArrayList<>();
    private double m;

    public static f c() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 5702, new Class[0], f.class);
        if (proxy.isSupported) {
            return (f) proxy.result;
        }
        if (c == null) {
            synchronized (f.class) {
                if (c == null) {
                    c = new f();
                }
            }
        }
        return c;
    }

    public int b() {
        int i2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5703, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.h.equals("happy")) {
            int i3 = a;
            if (i3 >= 350) {
                a = i3 - 10;
            }
        } else if (this.h.equals("lyric") && (i2 = a) <= 500) {
            a = i2 + 10;
        }
        a.b g2 = a.g("Rhythm-Auto");
        g2.a("getAutoFading:" + a + ",style:" + this.h, new Object[0]);
        return a;
    }

    public int a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5704, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.h.equals("happy")) {
            b = Math.max(500, b - 100);
        } else if (this.h.equals("lyric")) {
            b = Math.min(2500, b + 100);
        }
        a.b g2 = a.g("Rhythm-Auto");
        g2.a("getAutoChangeColorSpeed:" + b + ",style:" + this.h, new Object[0]);
        return b;
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5705, new Class[0], Void.TYPE).isSupported) {
            a.g("Rhythm-Auto").a("startAutoIdentify", new Object[0]);
            this.l.clear();
            a = 500;
            b = 1500;
        }
    }

    public void f(AmpDotBean dotBean) {
        if (!PatchProxy.proxy(new Object[]{dotBean}, this, changeQuickRedirect, false, 5706, new Class[]{AmpDotBean.class}, Void.TYPE).isSupported) {
            this.l.add(dotBean);
            double d2 = dotBean.amplitude;
            if (d2 > this.m) {
                this.m = d2;
            }
            long firstTime = this.l.get(0).timestamp;
            CopyOnWriteArrayList<AmpDotBean> copyOnWriteArrayList = this.l;
            long lastTime = copyOnWriteArrayList.get(copyOnWriteArrayList.size() - 1).timestamp;
            if (lastTime - firstTime >= KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS) {
                e(lastTime - firstTime);
            }
        }
    }

    private void e(long j2) {
        Object[] objArr = {new Long(j2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5707, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            int lowFreqNum = 0;
            int dbJumpNum = 0;
            for (int i2 = 0; i2 < this.l.size(); i2++) {
                if (this.l.get(i2).frequency > 258.0f && this.l.get(i2).frequency < 500.0f) {
                    lowFreqNum++;
                }
                if (i2 < this.l.size() - 1 && d(i2, this.l)) {
                    dbJumpNum++;
                }
            }
            this.i = (double) ((((float) lowFreqNum) * 1.0f) / ((float) this.l.size()));
            double size = (double) ((((float) dbJumpNum) * 1.0f) / ((float) this.l.size()));
            this.j = size;
            double d2 = this.i;
            if (d2 > size && d2 - size >= 0.03d) {
                this.h = "lyric";
            } else if (size <= d2 || size - d2 < 0.03d) {
                this.h = "neutral";
            } else {
                this.h = "happy";
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i3 = 0; i3 < this.l.size(); i3++) {
                if (i3 == 0) {
                    stringBuffer.append(Constants.ARRAY_TYPE);
                }
                stringBuffer.append("(");
                stringBuffer.append((int) this.l.get(i3).frequency);
                stringBuffer.append(",");
                stringBuffer.append((int) this.l.get(i3).amplitude);
                stringBuffer.append(")");
                if (i3 == this.l.size() - 1) {
                    stringBuffer.append("]");
                } else {
                    stringBuffer.append(",");
                }
            }
            this.l.clear();
            this.m = 0.0d;
        }
    }

    private boolean d(int i2, List<AmpDotBean> list) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i2), list}, this, changeQuickRedirect, false, 5708, new Class[]{Integer.TYPE, List.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        List<AmpDotBean> ampList = list;
        int index = i2;
        if (ampList.size() <= 1) {
            return false;
        }
        if (index == ampList.size() - 1) {
            if (ampList.get(index).amplitude - ampList.get(index - 1).amplitude > 0.0d) {
                return true;
            }
        } else if (index != 0) {
            double a2 = ampList.get(index).amplitude - ampList.get(index - 1).amplitude;
            double b2 = ampList.get(index).amplitude - ampList.get(index + 1).amplitude;
            if (a2 > 0.0d && b2 > 0.0d && (a2 + b2) / 2.0d > 0.0d) {
                return true;
            }
            if (a2 <= 0.0d || b2 <= 0.0d) {
                return false;
            }
            return true;
        } else if (ampList.get(index).amplitude - ampList.get(index + 1).amplitude > 0.0d) {
            return true;
        }
        return false;
    }
}
