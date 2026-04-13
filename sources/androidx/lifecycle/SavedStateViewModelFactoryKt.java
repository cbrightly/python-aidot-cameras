package androidx.lifecycle;

import android.app.Application;
import androidx.annotation.RestrictTo;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.p;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000*\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a6\u0010\u0004\u001a\n\u0012\u0004\u0012\u0002H\u0006\u0018\u00010\u0005\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\u00022\u0010\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001H\u0000\u001aI\u0010\t\u001a\u0002H\u0006\"\n\b\u0000\u0010\u0006*\u0004\u0018\u00010\n2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\u00022\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00060\u00052\u0012\u0010\f\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u000e0\r\"\u00020\u000eH\u0000¢\u0006\u0002\u0010\u000f\"\u0018\u0010\u0000\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"\u0018\u0010\u0003\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"ANDROID_VIEWMODEL_SIGNATURE", "", "Ljava/lang/Class;", "VIEWMODEL_SIGNATURE", "findMatchingConstructor", "Ljava/lang/reflect/Constructor;", "T", "modelClass", "signature", "newInstance", "Landroidx/lifecycle/ViewModel;", "constructor", "params", "", "", "(Ljava/lang/Class;Ljava/lang/reflect/Constructor;[Ljava/lang/Object;)Landroidx/lifecycle/ViewModel;", "lifecycle-viewmodel-savedstate_release"}, k = 2, mv = {1, 6, 0}, xi = 48)
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: SavedStateViewModelFactory.kt */
public final class SavedStateViewModelFactoryKt {
    /* access modifiers changed from: private */
    @NotNull
    public static final List<Class<?>> ANDROID_VIEWMODEL_SIGNATURE;
    /* access modifiers changed from: private */
    @NotNull
    public static final List<Class<?>> VIEWMODEL_SIGNATURE;

    public static final <T extends ViewModel> T newInstance(@NotNull Class<T> modelClass, @NotNull Constructor<T> constructor, @NotNull Object... params) {
        k.e(modelClass, "modelClass");
        k.e(constructor, "constructor");
        k.e(params, "params");
        try {
            return (ViewModel) constructor.newInstance(Arrays.copyOf(params, params.length));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access " + modelClass, e);
        } catch (InstantiationException e2) {
            throw new RuntimeException("A " + modelClass + " cannot be instantiated.", e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException("An exception happened in constructor of " + modelClass, e3.getCause());
        }
    }

    static {
        Class<SavedStateHandle> cls = SavedStateHandle.class;
        ANDROID_VIEWMODEL_SIGNATURE = q.j(Application.class, cls);
        VIEWMODEL_SIGNATURE = p.b(cls);
    }

    @Nullable
    public static final <T> Constructor<T> findMatchingConstructor(@NotNull Class<T> modelClass, @NotNull List<? extends Class<?>> signature) {
        k.e(modelClass, "modelClass");
        k.e(signature, "signature");
        Constructor[] constructors = modelClass.getConstructors();
        k.d(constructors, "modelClass.constructors");
        int length = constructors.length;
        int i = 0;
        while (i < length) {
            Constructor constructor = constructors[i];
            Class[] parameterTypes = constructor.getParameterTypes();
            k.d(parameterTypes, "constructor.parameterTypes");
            List parameterTypes2 = kotlin.collections.l.U(parameterTypes);
            if (k.a(signature, parameterTypes2)) {
                return constructor;
            }
            if (signature.size() != parameterTypes2.size() || !parameterTypes2.containsAll(signature)) {
                i++;
            } else {
                throw new UnsupportedOperationException("Class " + modelClass.getSimpleName() + " must have parameters in the proper order: " + signature);
            }
        }
        return null;
    }
}
