package kotlin.io;

import java.io.File;
import kotlin.jvm.internal.k;

/* compiled from: Exceptions.kt */
public final class c {
    /* access modifiers changed from: private */
    public static final String b(File file, File other, String reason) {
        StringBuilder sb = new StringBuilder(file.toString());
        if (other != null) {
            sb.append(" -> " + other);
        }
        if (reason != null) {
            sb.append(": " + reason);
        }
        String sb2 = sb.toString();
        k.d(sb2, "sb.toString()");
        return sb2;
    }
}
