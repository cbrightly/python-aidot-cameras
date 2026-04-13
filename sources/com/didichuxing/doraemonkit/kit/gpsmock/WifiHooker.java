package com.didichuxing.doraemonkit.kit.gpsmock;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import com.didichuxing.doraemonkit.kit.gpsmock.BaseServiceHooker;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WifiHooker extends BaseServiceHooker {
    public String getServiceName() {
        return "wifi";
    }

    public String getStubName() {
        return "android.net.wifi.IWifiManager$Stub";
    }

    public Map<String, BaseServiceHooker.MethodHandler> getMethodHandlers() {
        Map<String, BaseServiceHooker.MethodHandler> methodHandlers = new HashMap<>();
        methodHandlers.put("getScanResults", new GetScanResultsMethodHandler());
        methodHandlers.put("getConnectionInfo", new GetConnectionInfoMethodHandler());
        return methodHandlers;
    }

    public void replaceBinder(Context context, IBinder proxy) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService("wifi");
        if (wifiManager != null) {
            Field mServiceField = wifiManager.getClass().getDeclaredField("mService");
            mServiceField.setAccessible(true);
            mServiceField.set(wifiManager, Class.forName(getStubName()).getDeclaredMethod("asInterface", new Class[]{IBinder.class}).invoke((Object) null, new Object[]{proxy}));
            mServiceField.setAccessible(false);
        }
    }

    public static class GetScanResultsMethodHandler implements BaseServiceHooker.MethodHandler {
        GetScanResultsMethodHandler() {
        }

        public Object onInvoke(Object originService, Object proxy, Method method, Object[] args) {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originService, args);
            }
            return new ArrayList();
        }
    }

    public static class GetConnectionInfoMethodHandler implements BaseServiceHooker.MethodHandler {
        GetConnectionInfoMethodHandler() {
        }

        public Object onInvoke(Object originObject, Object proxyObject, Method method, Object[] args) {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originObject, args);
            }
            try {
                return Class.forName("android.net.wifi.WifiInfo").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                return method.invoke(originObject, args);
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
                return method.invoke(originObject, args);
            }
        }
    }
}
