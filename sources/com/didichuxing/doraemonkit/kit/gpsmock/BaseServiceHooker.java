package com.didichuxing.doraemonkit.kit.gpsmock;

import android.content.Context;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public abstract class BaseServiceHooker implements InvocationHandler {
    protected static final String METHOD_ASINTERFACE = "asInterface";
    private Object mOriginService;

    public interface MethodHandler {
        @Nullable
        Object onInvoke(Object obj, Object obj2, Method method, Object[] objArr);
    }

    public abstract Map<String, MethodHandler> getMethodHandlers();

    public abstract String getServiceName();

    public abstract String getStubName();

    public abstract void replaceBinder(Context context, IBinder iBinder);

    public Object invoke(Object proxy, Method method, Object[] args) {
        if (this.mOriginService == null && proxy == null) {
            return null;
        }
        if (!getMethodHandlers().containsKey(method.getName()) || getMethodHandlers().get(method.getName()) == null) {
            return method.invoke(this.mOriginService, args);
        }
        return getMethodHandlers().get(method.getName()).onInvoke(this.mOriginService, proxy, method, args);
    }

    /* access modifiers changed from: package-private */
    public void setBinder(IBinder binder) {
        try {
            this.mOriginService = Class.forName(getStubName()).getDeclaredMethod(METHOD_ASINTERFACE, new Class[]{IBinder.class}).invoke((Object) null, new Object[]{binder});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
