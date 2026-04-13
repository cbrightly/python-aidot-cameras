package com.telink.bluetooth;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.UUID;

/* compiled from: Command */
public class a {
    public static ChangeQuickRedirect changeQuickRedirect;
    public UUID a;
    public UUID b;
    public b c;
    public byte[] d;
    public Object e;
    public int f;

    /* renamed from: com.telink.bluetooth.a$a  reason: collision with other inner class name */
    /* compiled from: Command */
    public interface C0214a {
        void a(c cVar, a aVar, Object obj);

        boolean b(c cVar, a aVar);

        void c(c cVar, a aVar, String str);
    }

    /* compiled from: Command */
    public enum b {
        READ,
        WRITE,
        WRITE_NO_RESPONSE,
        ENABLE_NOTIFY,
        DISABLE_NOTIFY;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public a() {
        this((UUID) null, (UUID) null, b.WRITE);
    }

    public a(UUID serviceUUID, UUID characteristicUUID, b type) {
        this(serviceUUID, characteristicUUID, type, (byte[]) null);
    }

    public a(UUID serviceUUID, UUID characteristicUUID, b type, byte[] data) {
        this(serviceUUID, characteristicUUID, type, data, (Object) null);
    }

    public a(UUID serviceUUID, UUID characteristicUUID, b type, byte[] data, Object tag) {
        this.a = serviceUUID;
        this.b = characteristicUUID;
        this.c = type;
        this.d = data;
        this.e = tag;
    }

    public static a a() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 13417, new Class[0], a.class);
        return proxy.isSupported ? (a) proxy.result : new a();
    }

    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 13418, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String d2 = "";
        byte[] bArr = this.d;
        if (bArr != null) {
            d2 = com.telink.util.a.a(bArr, ",");
        }
        return "{ tag : " + this.e + ", type : " + this.c + " characteristicUUID :" + this.b.toString() + " data: " + d2 + " delay :" + this.f + "}";
    }
}
