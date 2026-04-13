package okhttp3.internal.http2;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okio.e;
import org.jetbrains.annotations.NotNull;

/* compiled from: PushObserver.kt */
public interface k {
    @NotNull
    public static final k a = new a.C0466a();
    public static final a b = new a((DefaultConstructorMarker) null);

    boolean a(int i, @NotNull List<b> list);

    boolean b(int i, @NotNull List<b> list, boolean z);

    boolean c(int i, @NotNull e eVar, int i2, boolean z);

    void d(int i, @NotNull a aVar);

    /* compiled from: PushObserver.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /* renamed from: okhttp3.internal.http2.k$a$a  reason: collision with other inner class name */
        /* compiled from: PushObserver.kt */
        public static final class C0466a implements k {
            public boolean a(int streamId, @NotNull List<b> requestHeaders) {
                kotlin.jvm.internal.k.f(requestHeaders, "requestHeaders");
                return true;
            }

            public boolean b(int streamId, @NotNull List<b> responseHeaders, boolean last) {
                kotlin.jvm.internal.k.f(responseHeaders, "responseHeaders");
                return true;
            }

            public boolean c(int streamId, @NotNull e source, int byteCount, boolean last) {
                kotlin.jvm.internal.k.f(source, "source");
                source.skip((long) byteCount);
                return true;
            }

            public void d(int streamId, @NotNull a errorCode) {
                kotlin.jvm.internal.k.f(errorCode, "errorCode");
            }
        }
    }
}
