package com.leedarson.sender;

import android.bluetooth.BluetoothDevice;
import com.alibaba.android.arouter.launcher.a;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.leedarson.serviceinterface.BleC075Service;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.UUID;
import org.json.JSONObject;
import timber.log.a;

/* compiled from: BLEQhmSender */
public class c extends e {
    public static int a = 150;
    public static int b = 50;
    public static int c = 0;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static int d = 150;
    public static int e = 50;
    public static int f = 0;
    public static int g = 0;
    public static int h = 5;
    private String i;
    private String j;
    private BleC075Service k;
    private int l = 0;
    private int m = 0;
    private int n = 0;
    private boolean o = true;
    private double p = 80.0d;
    private double q = 5.0d;
    private double r = 1.0d;
    public int s = 30;
    private int t = 100;
    private int u = 0;
    private final String v = "0000ffd5-0000-1000-8000-00805f9b34fb";
    private final String w = "0000ffd9-0000-1000-8000-00805f9b34fb";

    public c(String groupId) {
        this.j = groupId;
        this.k = (BleC075Service) a.c().g(BleC075Service.class);
    }

    public void g(String mac) {
        this.i = mac;
    }

    public void a(String str, JSONObject jSONObject) {
        if (!PatchProxy.proxy(new Object[]{str, jSONObject}, this, changeQuickRedirect, false, 5684, new Class[]{String.class, JSONObject.class}, Void.TYPE).isSupported) {
            JSONObject jsonObject = jSONObject;
            String str2 = str;
            if (this.k != null) {
                try {
                    double db = jsonObject.getDouble(FileUtil.DB);
                    this.s = jsonObject.getInt("sensitivity");
                    this.t = jsonObject.getInt("light");
                    f(db);
                    byte[] params = {-121, (byte) (this.l & 255), (byte) (this.m & 255), (byte) (this.n & 255), 0, -16, -18};
                    a.b g2 = timber.log.a.g("Rhythm");
                    g2.a("sendCommand params db:" + db + ",dred = " + this.l + ",dgreen = " + this.m + ",dblue = " + this.n, new Object[0]);
                    this.k.commonWriteWithoutResponse(this.i, (BluetoothDevice) null, UUID.fromString("0000ffd5-0000-1000-8000-00805f9b34fb"), UUID.fromString("0000ffd9-0000-1000-8000-00805f9b34fb"), params);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void b(String mac, String groupId, byte able, com.leedarson.base.http.listener.a listener) {
    }

    public void c(String mac, String groupId, int[] colors) {
    }

    private synchronized void f(double db) {
        if (!PatchProxy.proxy(new Object[]{new Double(db)}, this, changeQuickRedirect, false, 5685, new Class[]{Double.TYPE}, Void.TYPE).isSupported) {
            d = 0;
            e = 0;
            f = 0;
            g = 0;
            if (db > ((double) (80 - this.s))) {
                e();
                if (Math.abs(db) >= 3.0d || this.o) {
                    int i2 = a;
                    d = (int) (((double) i2) + (((double) (i2 / 5)) * db));
                    int i3 = b;
                    e = (int) (((double) i3) + (((double) (i3 / 5)) * db));
                    int i4 = c;
                    f = (int) (((double) i4) + (((double) (i4 / 5)) * db));
                } else {
                    d = (int) (((double) a) + (((double) (this.l / 12)) * db));
                    e = (int) (((double) b) + (((double) (this.m / 12)) * db));
                    f = (int) (((double) c) + (((double) (this.n / 12)) * db));
                }
                if (db > 30.0d) {
                    this.p = (this.p + db) / 2.0d;
                    double d2 = (this.q + db) / 2.0d;
                    this.q = d2;
                    db = d2;
                    this.r = (this.r + Math.abs(db)) / 2.0d;
                }
                if (db >= 0.0d) {
                    a.b g2 = timber.log.a.g("Rhythm");
                    g2.a("sendCommand params db:" + db, new Object[0]);
                    if (db > 1.5d && db <= 2.0d) {
                        d = (int) (((double) d) * 1.1d);
                        e = (int) (((double) e) * 1.1d);
                        f = (int) (((double) f) * 1.1d);
                    } else if (db > 2.0d && db < 3.0d) {
                        d = (int) (((double) d) * 1.2d);
                        e = (int) (((double) e) * 1.2d);
                        f = (int) (((double) f) * 1.2d);
                    } else if (db > 3.0d && db < 5.0d) {
                        d = (int) (((double) d) * 1.4d);
                        e = (int) (((double) e) * 1.4d);
                        f = (int) (((double) f) * 1.4d);
                    } else if (db >= 5.0d) {
                        d = (int) (((double) d) * 1.5d);
                        e = (int) (((double) e) * 1.5d);
                        f = (int) (((double) f) * 1.5d);
                    }
                } else if (db < -1.0d && db > -1.5d) {
                    d = (int) (((double) d) * 0.7d);
                    e = (int) (((double) e) * 0.7d);
                    f = (int) (((double) f) * 0.7d);
                } else if (db <= -1.5d && db > -2.0d) {
                    d = (int) (((double) d) * 0.6d);
                    e = (int) (((double) e) * 0.6d);
                    f = (int) (((double) f) * 0.6d);
                } else if (db <= -2.0d && db > -3.0d) {
                    d = (int) (((double) d) * 0.5d);
                    e = (int) (((double) e) * 0.5d);
                    f = (int) (((double) f) * 0.5d);
                } else if (db <= -3.0d && db > -4.0d) {
                    d = (int) (((double) d) * 0.4d);
                    e = (int) (((double) e) * 0.4d);
                    f = (int) (((double) f) * 0.4d);
                } else if (db <= -5.0d) {
                    d = 0;
                    e = 0;
                    f = 0;
                }
                d = d(d, 0);
                e = d(e, 0);
                int d3 = d(f, 0);
                f = d3;
                int i5 = d;
                int i6 = this.t;
                this.l = (int) (((double) (i5 * i6)) * 0.01d);
                this.m = (int) (((double) (e * i6)) * 0.01d);
                this.n = (int) (((double) (d3 * i6)) * 0.01d);
            }
        }
    }

    private int d(int src, int add) {
        int color = src + add;
        if (color > 255) {
            color = 255;
        }
        if (color < 0) {
            return 0;
        }
        return color;
    }

    private void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5686, new Class[0], Void.TYPE).isSupported) {
            int floor = (int) Math.floor(Math.random() * 6.0d);
            this.u = floor;
            if (floor < 0) {
                floor = 0;
            }
            this.u = floor;
            if (floor > 5) {
                floor = 5;
            }
            this.u = floor;
            if (floor == 0) {
                a = 254;
                c = 0;
                int i2 = b + h;
                b = i2;
                if (i2 >= 254) {
                    b = 254;
                    this.u = 1;
                }
            } else if (floor == 1) {
                b = 254;
                int i3 = a - h;
                a = i3;
                c = 0;
                if (i3 <= 0) {
                    a = 0;
                    this.u = 2;
                }
            } else if (floor == 2) {
                b = 254;
                int i4 = c + h;
                c = i4;
                a = 0;
                if (i4 >= 254) {
                    c = 200;
                    this.u = 3;
                }
            } else if (floor == 3) {
                c = 254;
                int i5 = b - h;
                b = i5;
                a = 0;
                if (i5 <= 0) {
                    b = 0;
                    this.u = 4;
                }
            } else if (floor == 4) {
                c = 254;
                int i6 = a + h;
                a = i6;
                b = 0;
                if (i6 >= 254) {
                    a = 254;
                    this.u = 5;
                }
            } else if (floor == 5) {
                a = 254;
                int i7 = c - h;
                c = i7;
                b = 0;
                if (i7 <= 0) {
                    c = 0;
                    this.u = 0;
                }
            } else if (floor == 6) {
                int i8 = h;
                a = 100 - i8;
                c = 100 - i8;
                b = 100 - i8;
            }
        }
    }
}
