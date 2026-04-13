package androidx.fragment.app;

import android.util.AndroidRuntimeException;

public final class SuperNotCalledException extends AndroidRuntimeException {
    public SuperNotCalledException(String msg) {
        super(msg);
    }
}
