package okhttp3.internal.platform.android;

import com.leedarson.serviceinterface.ZendeskService;
import java.lang.reflect.Method;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CloseGuard.kt */
public final class h {
    public static final a a = new a((DefaultConstructorMarker) null);
    private final Method b;
    private final Method c;
    private final Method d;

    public h(@Nullable Method getMethod, @Nullable Method openMethod, @Nullable Method warnIfOpenMethod) {
        this.b = getMethod;
        this.c = openMethod;
        this.d = warnIfOpenMethod;
    }

    @Nullable
    public final Object a(@NotNull String closer) {
        k.f(closer, "closer");
        Method method = this.b;
        if (method != null) {
            try {
                Object closeGuardInstance = method.invoke((Object) null, new Object[0]);
                Method method2 = this.c;
                if (method2 == null) {
                    k.n();
                }
                method2.invoke(closeGuardInstance, new Object[]{closer});
                return closeGuardInstance;
            } catch (Exception e) {
            }
        }
        return null;
    }

    public final boolean b(@Nullable Object closeGuardInstance) {
        if (closeGuardInstance == null) {
            return false;
        }
        try {
            Method method = this.d;
            if (method == null) {
                k.n();
            }
            method.invoke(closeGuardInstance, new Object[0]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /* compiled from: CloseGuard.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final h a() {
            Method warnIfOpenMethod;
            Method openMethod;
            Method getMethod;
            try {
                Class closeGuardClass = Class.forName("dalvik.system.CloseGuard");
                getMethod = closeGuardClass.getMethod("get", new Class[0]);
                openMethod = closeGuardClass.getMethod(ZendeskService.ACTION_OPEN, new Class[]{String.class});
                warnIfOpenMethod = closeGuardClass.getMethod("warnIfOpen", new Class[0]);
            } catch (Exception e) {
                getMethod = null;
                openMethod = null;
                warnIfOpenMethod = null;
            }
            return new h(getMethod, openMethod, warnIfOpenMethod);
        }
    }
}
