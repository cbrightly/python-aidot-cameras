package com.telink.ble.mesh.core.ble;

import com.google.maps.android.BuildConfig;
import com.leedarson.base.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.UUID;

public class GattRequest {
    public static ChangeQuickRedirect changeQuickRedirect;
    public UUID a;
    public UUID b;
    public UUID c;
    public RequestType d;
    public byte[] e;
    public Object f;
    public int g;
    public Callback h;

    public interface Callback {
        void a(GattRequest gattRequest, String str);

        void b(GattRequest gattRequest, Object obj);

        boolean c(GattRequest gattRequest);
    }

    public enum RequestType {
        READ,
        READ_DESCRIPTOR,
        WRITE,
        WRITE_NO_RESPONSE,
        WRITE_DESCRIPTOR,
        ENABLE_NOTIFY,
        DISABLE_NOTIFY;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public GattRequest() {
        this((UUID) null, (UUID) null, RequestType.WRITE);
    }

    public GattRequest(UUID serviceUUID, UUID characteristicUUID, RequestType type) {
        this(serviceUUID, characteristicUUID, type, (byte[]) null);
    }

    public GattRequest(UUID serviceUUID, UUID characteristicUUID, RequestType type, byte[] data) {
        this(serviceUUID, characteristicUUID, type, data, (Object) null);
    }

    public GattRequest(UUID serviceUUID, UUID characteristicUUID, RequestType type, byte[] data, Object tag) {
        this.a = serviceUUID;
        this.b = characteristicUUID;
        this.d = type;
        this.e = data;
        this.f = tag;
    }

    public static GattRequest b() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 12389, new Class[0], GattRequest.class);
        return proxy.isSupported ? (GattRequest) proxy.result : new GattRequest();
    }

    public void a() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.e = null;
    }

    public String toString() {
        String d2;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 12390, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        byte[] bArr = this.e;
        if (bArr != null) {
            d2 = e.a(bArr);
        } else {
            d2 = BuildConfig.TRAVIS;
        }
        return "{ tag : " + this.f + ", type : " + this.d + " CHARACTERISTIC_UUID :" + this.b.toString() + " data: " + d2 + " delay :" + this.g + "}";
    }
}
