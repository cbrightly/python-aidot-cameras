package io.ktor.utils.io;

import io.ktor.utils.io.core.a0;
import org.jetbrains.annotations.Nullable;

/* compiled from: ReadSession.kt */
public interface u {
    @Nullable
    a0 a(int i);

    /* compiled from: ReadSession.kt */
    public static final class a {
        public static /* synthetic */ a0 a(u uVar, int i, int i2, Object obj) {
            if (obj == null) {
                if ((i2 & 1) != 0) {
                    i = 1;
                }
                return uVar.a(i);
            }
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: request");
        }
    }
}
