package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.o;

/* compiled from: AbstractMessageLite */
public abstract class a implements o {
    protected int memoizedHashCode = 0;

    public void writeDelimitedTo(OutputStream output) {
        int serialized = getSerializedSize();
        CodedOutputStream codedOutput = CodedOutputStream.J(output, CodedOutputStream.u(CodedOutputStream.v(serialized) + serialized));
        codedOutput.o0(serialized);
        writeTo(codedOutput);
        codedOutput.I();
    }

    /* access modifiers changed from: package-private */
    public UninitializedMessageException newUninitializedMessageException() {
        return new UninitializedMessageException(this);
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.protobuf.a$a  reason: collision with other inner class name */
    /* compiled from: AbstractMessageLite */
    public static abstract class C0398a<BuilderType extends C0398a> implements o.a {
        /* renamed from: a */
        public abstract BuilderType J(e eVar, f fVar);

        /* renamed from: kotlin.reflect.jvm.internal.impl.protobuf.a$a$a  reason: collision with other inner class name */
        /* compiled from: AbstractMessageLite */
        public static final class C0399a extends FilterInputStream {
            private int c;

            C0399a(InputStream in, int limit) {
                super(in);
                this.c = limit;
            }

            public int available() {
                return Math.min(super.available(), this.c);
            }

            public int read() {
                if (this.c <= 0) {
                    return -1;
                }
                int result = super.read();
                if (result >= 0) {
                    this.c--;
                }
                return result;
            }

            public int read(byte[] b, int off, int len) {
                int i = this.c;
                if (i <= 0) {
                    return -1;
                }
                int result = super.read(b, off, Math.min(len, i));
                if (result >= 0) {
                    this.c -= result;
                }
                return result;
            }

            public long skip(long n) {
                long result = super.skip(Math.min(n, (long) this.c));
                if (result >= 0) {
                    this.c = (int) (((long) this.c) - result);
                }
                return result;
            }
        }

        protected static UninitializedMessageException b(o message) {
            return new UninitializedMessageException(message);
        }
    }
}
