package coil.memory;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.MainThread;
import coil.request.g;
import coil.request.m;
import coil.target.b;
import kotlin.coroutines.d;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: TargetDelegate.kt */
public abstract class s {
    public /* synthetic */ s(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Nullable
    @MainThread
    public Object b(@NotNull g gVar, @NotNull d<? super x> dVar) {
        return x.a;
    }

    @Nullable
    @MainThread
    public Object f(@NotNull m mVar, @NotNull d<? super x> dVar) {
        return x.a;
    }

    private s() {
    }

    @Nullable
    public b d() {
        return null;
    }

    @MainThread
    public void e(@Nullable Drawable placeholder, @Nullable Bitmap cached) {
    }

    @MainThread
    public void a() {
    }
}
