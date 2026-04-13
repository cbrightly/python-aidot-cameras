package com.didichuxing.doraemonkit.kit.gpsmock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.IBinder;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceHookManager {
    private static final String CLASS_SERVICE_MANAGER = "android.os.ServiceManager";
    private static final String FIELD_S_CACHE = "sCache";
    private static final String METHOD_GET_SERVICE = "getService";
    private boolean isHookSuccess;
    private List<BaseServiceHooker> mHookers;

    public static class Holder {
        /* access modifiers changed from: private */
        public static ServiceHookManager INSTANCE = new ServiceHookManager();

        private Holder() {
        }
    }

    public static ServiceHookManager getInstance() {
        return Holder.INSTANCE;
    }

    private ServiceHookManager() {
        this.mHookers = new ArrayList();
        init();
    }

    private void init() {
        this.mHookers.add(new WifiHooker());
        this.mHookers.add(new LocationHooker());
        this.mHookers.add(new TelephonyHooker());
    }

    @SuppressLint({"PrivateApi"})
    public void install(Context context) {
        try {
            Class serviceManager = Class.forName(CLASS_SERVICE_MANAGER);
            Method getService = serviceManager.getDeclaredMethod(METHOD_GET_SERVICE, new Class[]{String.class});
            for (BaseServiceHooker hooker : this.mHookers) {
                IBinder binder = (IBinder) getService.invoke((Object) null, new Object[]{hooker.getServiceName()});
                if (binder != null) {
                    BinderHookHandler handler = new BinderHookHandler(binder, hooker);
                    hooker.setBinder(binder);
                    IBinder proxy = (IBinder) Proxy.newProxyInstance(binder.getClass().getClassLoader(), new Class[]{IBinder.class}, handler);
                    hooker.replaceBinder(context, proxy);
                    Field sCache = serviceManager.getDeclaredField(FIELD_S_CACHE);
                    sCache.setAccessible(true);
                    ((Map) sCache.get((Object) null)).put(hooker.getServiceName(), proxy);
                    sCache.setAccessible(false);
                } else {
                    return;
                }
            }
            this.isHookSuccess = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e2) {
            e2.printStackTrace();
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        } catch (NoSuchFieldException e5) {
            e5.printStackTrace();
        }
    }

    public boolean isHookSuccess() {
        return this.isHookSuccess;
    }
}
