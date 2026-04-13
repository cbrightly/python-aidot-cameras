package kotlin.reflect.jvm.internal.impl.descriptors;

import java.util.Collection;
import org.jetbrains.annotations.NotNull;

/* compiled from: CallableMemberDescriptor */
public interface b extends a, v {
    @NotNull
    b B0(m mVar, w wVar, a1 a1Var, a aVar, boolean z);

    @NotNull
    b a();

    @NotNull
    Collection<? extends b> d();

    @NotNull
    a h();

    void y0(@NotNull Collection<? extends b> collection);

    /* compiled from: CallableMemberDescriptor */
    public enum a {
        DECLARATION,
        FAKE_OVERRIDE,
        DELEGATION,
        SYNTHESIZED;

        public boolean isReal() {
            return this != FAKE_OVERRIDE;
        }
    }
}
