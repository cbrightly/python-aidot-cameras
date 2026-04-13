package com.didichuxing.doraemonkit.kit.gpsmock;

import android.content.Context;
import android.os.IBinder;
import android.telephony.gsm.GsmCellLocation;
import com.didichuxing.doraemonkit.kit.gpsmock.BaseServiceHooker;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TelephonyHooker extends BaseServiceHooker {
    public String getServiceName() {
        return "phone";
    }

    public String getStubName() {
        return "com.android.internal.telephony.ITelephony$Stub";
    }

    public Map<String, BaseServiceHooker.MethodHandler> getMethodHandlers() {
        Map<String, BaseServiceHooker.MethodHandler> methodHandlers = new HashMap<>();
        methodHandlers.put("getAllCellInfo", new GetAllCellInfoMethodHandler());
        methodHandlers.put("getCellLocation", new GetCellLocationMethodHandler());
        methodHandlers.put("listen", new ListenMethodHandler());
        return methodHandlers;
    }

    public void replaceBinder(Context context, IBinder proxy) {
    }

    public static class GetAllCellInfoMethodHandler implements BaseServiceHooker.MethodHandler {
        GetAllCellInfoMethodHandler() {
        }

        public Object onInvoke(Object originObject, Object proxyObject, Method method, Object[] args) {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originObject, args);
            }
            return new ArrayList();
        }
    }

    public static class GetCellLocationMethodHandler implements BaseServiceHooker.MethodHandler {
        GetCellLocationMethodHandler() {
        }

        public Object onInvoke(Object originObject, Object proxyObject, Method method, Object[] args) {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originObject, args);
            }
            return new GsmCellLocation();
        }
    }

    public static class ListenMethodHandler implements BaseServiceHooker.MethodHandler {
        ListenMethodHandler() {
        }

        public Object onInvoke(Object originObject, Object proxyObject, Method method, Object[] args) {
            if (!GpsMockManager.getInstance().isMocking()) {
                return method.invoke(originObject, args);
            }
            return null;
        }
    }
}
