package com.telink.bluetooth.event;

import android.app.Application;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.telink.bluetooth.light.NotificationInfo;
import com.telink.bluetooth.light.i;
import java.util.HashMap;
import java.util.Map;

/* compiled from: NotificationEvent */
public class f extends a<NotificationInfo> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private static final Map<Byte, String> e = new HashMap();
    protected int f;
    protected int g;

    static {
        g(i.BLE_GATT_OP_CTRL_DC, "com.telink.bluetooth.light.EVENT_ONLINE_STATUS");
        g(i.BLE_GATT_OP_CTRL_D4, "com.telink.bluetooth.light.EVENT_GET_GROUP");
        g(i.BLE_GATT_OP_CTRL_E7, "com.telink.bluetooth.light.EVENT_GET_ALARM");
        g(i.BLE_GATT_OP_CTRL_E9, "com.telink.bluetooth.light.EVENT_GET_TIME");
        g(i.BLE_GATT_OP_CTRL_C1, "com.telink.bluetooth.light.EVENT_GET_SCENE");
        g(i.BLE_GATT_OP_CTRL_C8, "com.telink.bluetooth.light.GET_DEVICE_STATE");
        g(i.BLE_GATT_OP_CTRL_EB, "com.telink.bluetooth.light.USER_ALL_NOTIFY");
    }

    public f(Object sender, String type, NotificationInfo args) {
        super(sender, type, args);
        this.f = args.c;
        this.g = args.d;
    }

    public static boolean f(byte opcode, String eventType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Byte(opcode), eventType}, (Object) null, changeQuickRedirect, true, 13501, new Class[]{Byte.TYPE, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        byte opcode2 = (byte) (opcode & 255);
        synchronized (f.class) {
            Map<Byte, String> map = e;
            if (map.containsKey(Byte.valueOf(opcode2))) {
                return false;
            }
            map.put(Byte.valueOf(opcode2), eventType);
            return true;
        }
    }

    public static boolean g(i opcode, String eventType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{opcode, eventType}, (Object) null, changeQuickRedirect, true, 13502, new Class[]{i.class, String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : f(opcode.getValue(), eventType);
    }

    public static String d(byte opcode) {
        Object[] objArr = {new Byte(opcode)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 13503, new Class[]{Byte.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        byte opcode2 = (byte) (opcode & 255);
        synchronized (f.class) {
            Map<Byte, String> map = e;
            if (!map.containsKey(Byte.valueOf(opcode2))) {
                return null;
            }
            String str = map.get(Byte.valueOf(opcode2));
            return str;
        }
    }

    public static f e(Application sender, String type, NotificationInfo args) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{sender, type, args}, (Object) null, changeQuickRedirect, true, 13505, new Class[]{Application.class, String.class, NotificationInfo.class}, f.class);
        return proxy.isSupported ? (f) proxy.result : new f(sender, type, args);
    }
}
