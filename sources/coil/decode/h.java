package coil.decode;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.n;
import okio.c;
import okio.e0;
import okio.f;
import okio.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: FrameDelayRewritingSource.kt */
public final class h extends k {
    @NotNull
    private static final a c = new a((DefaultConstructorMarker) null);
    @NotNull
    @Deprecated
    private static final f d = f.Companion.b("0021F904");
    @NotNull
    private final c f = new c();

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public h(@NotNull e0 delegate) {
        super(delegate);
        kotlin.jvm.internal.k.e(delegate, "delegate");
    }

    public long read(@NotNull c sink, long byteCount) {
        kotlin.jvm.internal.k.e(sink, "sink");
        request(byteCount);
        if (this.f.e1() != 0) {
            long bytesWritten = 0;
            while (true) {
                long index = F(d);
                if (index == -1) {
                    break;
                }
                bytesWritten += a(sink, ((long) 4) + index);
                if (request(5) && this.f.n(4) == 0 && this.f.n(1) < 2) {
                    sink.writeByte(this.f.n(0));
                    sink.writeByte(10);
                    sink.writeByte(0);
                    this.f.skip(3);
                }
            }
            if (bytesWritten < byteCount) {
                bytesWritten += a(sink, byteCount - bytesWritten);
            }
            if (bytesWritten == 0) {
                return -1;
            }
            return bytesWritten;
        } else if (byteCount == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    private final long F(f bytes) {
        long index = -1;
        while (true) {
            index = this.f.o(bytes.getByte(0), 1 + index);
            if (index != -1 && (!request((long) bytes.size()) || !this.f.V(index, bytes))) {
            }
        }
        return index;
    }

    private final long a(c sink, long byteCount) {
        return n.c(this.f.read(sink, byteCount), 0);
    }

    private final boolean request(long byteCount) {
        if (this.f.e1() >= byteCount) {
            return true;
        }
        long toRead = byteCount - this.f.e1();
        if (super.read(this.f, toRead) == toRead) {
            return true;
        }
        return false;
    }

    /* compiled from: FrameDelayRewritingSource.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
