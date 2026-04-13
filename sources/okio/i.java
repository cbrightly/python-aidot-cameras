package okio;

import java.io.FileInputStream;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: JvmOkio.kt */
public final class i extends o implements g {
    private final FileInputStream f;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public i(@NotNull FileInputStream input) {
        super(input, new f0());
        k.e(input, "input");
        this.f = input;
    }

    @NotNull
    public g cursor() {
        return this;
    }
}
