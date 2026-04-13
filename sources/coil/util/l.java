package coil.util;

import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ImageLoaderOptions.kt */
public final class l {
    private final boolean a;
    private final boolean b;
    private final boolean c;

    public l() {
        this(false, false, false, 7, (DefaultConstructorMarker) null);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof l)) {
            return false;
        }
        l lVar = (l) obj;
        return this.a == lVar.a && this.b == lVar.b && this.c == lVar.c;
    }

    public int hashCode() {
        boolean z = this.a;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i = (z ? 1 : 0) * true;
        boolean z3 = this.b;
        if (z3) {
            z3 = true;
        }
        int i2 = (i + (z3 ? 1 : 0)) * 31;
        boolean z4 = this.c;
        if (!z4) {
            z2 = z4;
        }
        return i2 + (z2 ? 1 : 0);
    }

    @NotNull
    public String toString() {
        return "ImageLoaderOptions(addLastModifiedToFileCacheKey=" + this.a + ", launchInterceptorChainOnMainThread=" + this.b + ", networkObserverEnabled=" + this.c + ')';
    }

    public l(boolean addLastModifiedToFileCacheKey, boolean launchInterceptorChainOnMainThread, boolean networkObserverEnabled) {
        this.a = addLastModifiedToFileCacheKey;
        this.b = launchInterceptorChainOnMainThread;
        this.c = networkObserverEnabled;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ l(boolean z, boolean z2, boolean z3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z, (i & 2) != 0 ? true : z2, (i & 4) != 0 ? true : z3);
    }

    public final boolean a() {
        return this.a;
    }

    public final boolean b() {
        return this.b;
    }

    public final boolean c() {
        return this.c;
    }
}
