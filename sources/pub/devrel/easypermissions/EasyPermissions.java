package pub.devrel.easypermissions;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import pub.devrel.easypermissions.helper.e;

public class EasyPermissions {

    public interface PermissionCallbacks extends ActivityCompat.OnRequestPermissionsResultCallback {
        void Q(int i, @NonNull List<String> list);

        void a1(int i, @NonNull List<String> list);
    }

    public interface a {
        void a(int i);

        void b(int i);
    }

    public static boolean a(@NonNull Context context, @Size(min = 1) @NonNull String... perms) {
        if (Build.VERSION.SDK_INT < 23) {
            Log.w("EasyPermissions", "hasPermissions: API version < M, returning true by default");
            return true;
        }
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(context, perm) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean h(Activity activity, String perm) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, perm);
    }

    public static void f(c request) {
        if (a(request.b().b(), request.d())) {
            c(request.b().c(), request.g(), request.d());
        } else {
            request.b().g(request.f(), request.e(), request.c(), request.h(), request.g(), request.a(), request.d());
        }
    }

    public static void d(@IntRange(from = 0, to = 255) int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, @NonNull Object... receivers) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == 0) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }
        for (PermissionCallbacks permissionCallbacks : receivers) {
            if (!granted.isEmpty() && (permissionCallbacks instanceof PermissionCallbacks)) {
                permissionCallbacks.a1(requestCode, granted);
            }
            if (!denied.isEmpty() && (permissionCallbacks instanceof PermissionCallbacks)) {
                permissionCallbacks.Q(requestCode, denied);
            }
            if (!granted.isEmpty() && denied.isEmpty()) {
                g(permissionCallbacks, requestCode);
            }
        }
    }

    public static boolean j(@NonNull Activity host, @NonNull List<String> deniedPermissions) {
        return e.d(host).l(deniedPermissions);
    }

    public static boolean e(@NonNull Activity host, @NonNull String deniedPermission) {
        return e.d(host).f(deniedPermission);
    }

    public static boolean i(@NonNull Activity host, @NonNull String... perms) {
        return e.d(host).k(perms);
    }

    private static void c(@NonNull Object object, int requestCode, @NonNull String[] perms) {
        int[] grantResults = new int[perms.length];
        for (int i = 0; i < perms.length; i++) {
            grantResults[i] = 0;
        }
        d(requestCode, perms, grantResults, object);
    }

    private static void g(@NonNull Object object, int requestCode) {
        Class clazz = object.getClass();
        if (b(object)) {
            clazz = clazz.getSuperclass();
        }
        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                a ann = (a) method.getAnnotation(a.class);
                if (ann != null && ann.value() == requestCode) {
                    if (method.getParameterTypes().length <= 0) {
                        try {
                            if (!method.isAccessible()) {
                                method.setAccessible(true);
                            }
                            method.invoke(object, new Object[0]);
                        } catch (IllegalAccessException e) {
                            Log.e("EasyPermissions", "runDefaultMethod:IllegalAccessException", e);
                        } catch (InvocationTargetException e2) {
                            Log.e("EasyPermissions", "runDefaultMethod:InvocationTargetException", e2);
                        }
                    } else {
                        throw new RuntimeException("Cannot execute method " + method.getName() + " because it is non-void method and/or has input parameters.");
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    private static boolean b(@NonNull Object object) {
        if (!object.getClass().getSimpleName().endsWith("_")) {
            return false;
        }
        try {
            return Class.forName("org.androidannotations.api.view.HasViews").isInstance(object);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
