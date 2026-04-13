package com.telink.bluetooth.light;

import com.meituan.robust.BuildConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* compiled from: Manufacture */
public final class h {
    private static final h a = new b().a();
    private static h b;
    public static ChangeQuickRedirect changeQuickRedirect;
    private final Map<String, UUID> c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private byte[] i;
    private int j;
    private int k;
    private int l;

    private h(String name, String version, String info, String defaultMeshName, String defaultPassword, byte[] defaultLongTermKey, int vendorId, int otaDelay, int otaSize, UUID serviceUUID, UUID pairUUID, UUID commandUUID, UUID notifyUUID, UUID otaUUID) {
        this.c = new HashMap();
        this.d = name;
        this.e = version;
        this.f = info;
        this.g = defaultMeshName;
        this.h = defaultPassword;
        this.i = Arrays.copyOf(defaultLongTermKey, 16);
        this.j = vendorId;
        this.k = otaDelay;
        this.l = otaSize;
        h(c.SERVICE.getKey(), serviceUUID);
        h(c.PAIR.getKey(), pairUUID);
        h(c.COMMAND.getKey(), commandUUID);
        h(c.OTA.getKey(), otaUUID);
        h(c.NOTIFY.getKey(), notifyUUID);
    }

    public static h a() {
        synchronized (h.class) {
            h hVar = b;
            if (hVar != null) {
                return hVar;
            }
            h hVar2 = a;
            return hVar2;
        }
    }

    public byte[] b() {
        return this.i;
    }

    public int g() {
        return this.j;
    }

    public int c() {
        return this.k;
    }

    public int d() {
        return this.l;
    }

    public UUID e(c uuidType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uuidType}, this, changeQuickRedirect, false, 13815, new Class[]{c.class}, UUID.class);
        return proxy.isSupported ? (UUID) proxy.result : f(uuidType.getKey());
    }

    public UUID f(String key) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{key}, this, changeQuickRedirect, false, 13816, new Class[]{String.class}, UUID.class);
        if (proxy.isSupported) {
            return (UUID) proxy.result;
        }
        UUID result = null;
        synchronized (this.c) {
            if (this.c.containsKey(key)) {
                result = this.c.get(key);
            }
        }
        return result;
    }

    public void h(String key, UUID value) {
        Class[] clsArr = {String.class, UUID.class};
        if (!PatchProxy.proxy(new Object[]{key, value}, this, changeQuickRedirect, false, 13817, clsArr, Void.TYPE).isSupported) {
            synchronized (this.c) {
                if (!this.c.containsKey(key)) {
                    this.c.put(key, value);
                }
            }
        }
    }

    /* compiled from: Manufacture */
    public enum c {
        SERVICE("SERVICE_UUID"),
        PAIR("PAIR_UUID"),
        COMMAND("COMMAND_UUID"),
        OTA("OTA_UUID"),
        NOTIFY("NOTIFY_UUID");
        
        public static ChangeQuickRedirect changeQuickRedirect;
        private final String key;

        private c(String key2) {
            this.key = key2;
        }

        public String getKey() {
            return this.key;
        }
    }

    /* compiled from: Manufacture */
    public static final class b {
        public static ChangeQuickRedirect changeQuickRedirect;
        private String a = "telink";
        private String b = BuildConfig.VERSION_NAME;
        private String c = "TELINK SEMICONDUCTOR (Shanghai) CO, LTD is a fabless IC design company";
        private String d = "telink_mesh1";
        private String e = "123";
        private byte[] f = {-64, -63, -62, -61, -60, -59, -58, -57, -40, -39, -38, -37, -36, -35, -34, -33};
        private int g = 4354;
        private UUID h = k.TELINK_SERVICE.getValue();
        private UUID i = k.TELINK_CHARACTERISTIC_PAIR.getValue();
        private UUID j = k.TELINK_CHARACTERISTIC_COMMAND.getValue();
        private UUID k = k.TELINK_CHARACTERISTIC_NOTIFY.getValue();
        private UUID l = k.TELINK_CHARACTERISTIC_OTA.getValue();
        private int m = 0;
        private int n = 128;

        public h a() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13818, new Class[0], h.class);
            if (proxy.isSupported) {
                return (h) proxy.result;
            }
            return new h(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.m, this.n, this.h, this.i, this.j, this.k, this.l);
        }
    }
}
