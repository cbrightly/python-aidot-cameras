package androidx.lifecycle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: ViewModelProvider.kt */
public final /* synthetic */ class g {
    @NotNull
    public static <T extends ViewModel> ViewModel a(@NotNull ViewModelProvider.Factory _this, Class modelClass) {
        k.e(modelClass, "modelClass");
        throw new UnsupportedOperationException("Factory.create(String) is unsupported.  This Factory requires `CreationExtras` to be passed into `create` method.");
    }

    @NotNull
    public static <T extends ViewModel> ViewModel b(@NotNull ViewModelProvider.Factory _this, @NotNull Class modelClass, CreationExtras extras) {
        k.e(modelClass, "modelClass");
        k.e(extras, "extras");
        return _this.create(modelClass);
    }
}
