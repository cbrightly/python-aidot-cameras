package androidx.core.app;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;

@RequiresApi(api = 28)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class CoreComponentFactory extends AppComponentFactory {

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public interface CompatWrapped {
        Object getWrapper();
    }

    @NonNull
    public Activity instantiateActivity(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) {
        return (Activity) checkCompatWrapper(super.instantiateActivity(cl, className, intent));
    }

    @NonNull
    public Application instantiateApplication(@NonNull ClassLoader cl, @NonNull String className) {
        return (Application) checkCompatWrapper(super.instantiateApplication(cl, className));
    }

    @NonNull
    public BroadcastReceiver instantiateReceiver(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) {
        return (BroadcastReceiver) checkCompatWrapper(super.instantiateReceiver(cl, className, intent));
    }

    @NonNull
    public ContentProvider instantiateProvider(@NonNull ClassLoader cl, @NonNull String className) {
        return (ContentProvider) checkCompatWrapper(super.instantiateProvider(cl, className));
    }

    @NonNull
    public Service instantiateService(@NonNull ClassLoader cl, @NonNull String className, @Nullable Intent intent) {
        return (Service) checkCompatWrapper(super.instantiateService(cl, className, intent));
    }

    static <T> T checkCompatWrapper(T obj) {
        T wrapper;
        if (!(obj instanceof CompatWrapped) || (wrapper = ((CompatWrapped) obj).getWrapper()) == null) {
            return obj;
        }
        return wrapper;
    }
}
