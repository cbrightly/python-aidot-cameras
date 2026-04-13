package io.reactivex.exceptions;

import io.reactivex.internal.util.f;

/* compiled from: Exceptions */
public final class a {
    public static RuntimeException a(Throwable t) {
        throw f.d(t);
    }

    public static void b(Throwable t) {
        if (t instanceof VirtualMachineError) {
            throw ((VirtualMachineError) t);
        } else if (t instanceof ThreadDeath) {
            throw ((ThreadDeath) t);
        } else if (t instanceof LinkageError) {
            throw ((LinkageError) t);
        }
    }
}
