package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import kotlin.jvm.functions.l;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.incremental.components.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ResolutionScope.kt */
public interface j {
    @Nullable
    h c(@NotNull f fVar, @NotNull b bVar);

    @NotNull
    Collection<m> d(@NotNull d dVar, @NotNull l<? super f, Boolean> lVar);

    /* compiled from: ResolutionScope.kt */
    public static final class a {
        public static /* synthetic */ Collection a(j jVar, d dVar, l<f, Boolean> lVar, int i, Object obj) {
            if (obj == null) {
                if ((i & 1) != 0) {
                    dVar = d.l;
                }
                if ((i & 2) != 0) {
                    lVar = h.a.a();
                }
                return jVar.d(dVar, lVar);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getContributedDescriptors");
        }
    }
}
