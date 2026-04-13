package com.telink.ble.mesh.core.ble;

import android.os.ParcelUuid;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import org.glassfish.grizzly.http.server.util.MappingData;

public final class MeshBluetoothUUID {
    public static final ParcelUuid a;
    public static final ParcelUuid b;
    public static final ParcelUuid c;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static final ParcelUuid d;
    public static final ParcelUuid e = ParcelUuid.fromString("00001112-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid f;
    public static final ParcelUuid g = ParcelUuid.fromString("0000111F-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid h;
    public static final ParcelUuid i;
    public static final ParcelUuid j;
    public static final ParcelUuid k = ParcelUuid.fromString("00001124-0000-1000-8000-00805f9b34fb");
    public static final ParcelUuid l = ParcelUuid.fromString("00001812-0000-1000-8000-00805f9b34fb");
    public static final ParcelUuid m;
    public static final ParcelUuid n;
    public static final ParcelUuid o = ParcelUuid.fromString("0000000f-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid p = ParcelUuid.fromString("0000112f-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid q;
    public static final ParcelUuid r;
    public static final ParcelUuid s;
    public static final ParcelUuid t = ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");
    public static final ParcelUuid[] u;

    static {
        ParcelUuid fromString = ParcelUuid.fromString("0000110B-0000-1000-8000-00805F9B34FB");
        a = fromString;
        ParcelUuid fromString2 = ParcelUuid.fromString("0000110A-0000-1000-8000-00805F9B34FB");
        b = fromString2;
        ParcelUuid fromString3 = ParcelUuid.fromString("0000110D-0000-1000-8000-00805F9B34FB");
        c = fromString3;
        ParcelUuid fromString4 = ParcelUuid.fromString("00001108-0000-1000-8000-00805F9B34FB");
        d = fromString4;
        ParcelUuid fromString5 = ParcelUuid.fromString("0000111E-0000-1000-8000-00805F9B34FB");
        f = fromString5;
        ParcelUuid fromString6 = ParcelUuid.fromString("0000110E-0000-1000-8000-00805F9B34FB");
        h = fromString6;
        ParcelUuid fromString7 = ParcelUuid.fromString("0000110C-0000-1000-8000-00805F9B34FB");
        i = fromString7;
        ParcelUuid fromString8 = ParcelUuid.fromString("00001105-0000-1000-8000-00805f9b34fb");
        j = fromString8;
        ParcelUuid fromString9 = ParcelUuid.fromString("00001115-0000-1000-8000-00805F9B34FB");
        m = fromString9;
        ParcelUuid fromString10 = ParcelUuid.fromString("00001116-0000-1000-8000-00805F9B34FB");
        n = fromString10;
        ParcelUuid fromString11 = ParcelUuid.fromString("00001134-0000-1000-8000-00805F9B34FB");
        q = fromString11;
        ParcelUuid fromString12 = ParcelUuid.fromString("00001133-0000-1000-8000-00805F9B34FB");
        r = fromString12;
        ParcelUuid fromString13 = ParcelUuid.fromString("00001132-0000-1000-8000-00805F9B34FB");
        s = fromString13;
        u = new ParcelUuid[]{fromString, fromString2, fromString3, fromString4, fromString5, fromString6, fromString7, fromString8, fromString9, fromString10, fromString11, fromString12, fromString13};
    }

    public static ParcelUuid a(byte[] uuidBytes) {
        long shortUuid;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{uuidBytes}, (Object) null, changeQuickRedirect, true, 12412, new Class[]{byte[].class}, ParcelUuid.class);
        if (proxy.isSupported) {
            return (ParcelUuid) proxy.result;
        }
        if (uuidBytes != null) {
            int length = uuidBytes.length;
            if (length != 2 && length != 4 && length != 16) {
                throw new IllegalArgumentException("uuidBytes length invalid - " + length);
            } else if (length == 16) {
                ByteBuffer buf = ByteBuffer.wrap(uuidBytes).order(ByteOrder.LITTLE_ENDIAN);
                return new ParcelUuid(new UUID(buf.getLong(8), buf.getLong(0)));
            } else {
                if (length == 2) {
                    shortUuid = ((long) (uuidBytes[0] & 255)) + ((long) ((uuidBytes[1] & 255) << 8));
                } else {
                    shortUuid = ((long) ((uuidBytes[3] & 255) << 24)) + ((long) (uuidBytes[0] & 255)) + ((long) ((uuidBytes[1] & 255) << 8)) + ((long) ((uuidBytes[2] & 255) << MappingData.PATH));
                }
                ParcelUuid parcelUuid = t;
                return new ParcelUuid(new UUID(parcelUuid.getUuid().getMostSignificantBits() + (shortUuid << 32), parcelUuid.getUuid().getLeastSignificantBits()));
            }
        } else {
            throw new IllegalArgumentException("uuidBytes cannot be null");
        }
    }
}
