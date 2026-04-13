package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.a;
import kotlin.reflect.jvm.internal.impl.protobuf.d;
import kotlin.reflect.jvm.internal.impl.protobuf.e;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.protobuf.l;
import kotlin.reflect.jvm.internal.impl.protobuf.m;
import kotlin.reflect.jvm.internal.impl.protobuf.q;
import kotlin.reflect.jvm.internal.impl.protobuf.r;

/* compiled from: ProtoBuf */
public final class p extends h implements kotlin.reflect.jvm.internal.impl.protobuf.p {
    public static q<p> PARSER = new a();
    private static final p c;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public m string_;
    /* access modifiers changed from: private */
    public final d unknownFields;

    private p(h.b builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private p(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static p getDefaultInstance() {
        return c;
    }

    public p getDefaultInstanceForType() {
        return c;
    }

    private p(e input, f extensionRegistry) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        b();
        int mutable_bitField0_ = 0;
        d.b unknownFieldsOutput = d.r();
        CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.J(unknownFieldsOutput, 1);
        boolean done = false;
        while (!done) {
            try {
                int tag = input.K();
                switch (tag) {
                    case 0:
                        done = true;
                        break;
                    case 10:
                        d bs = input.l();
                        if ((mutable_bitField0_ & 1) != 1) {
                            this.string_ = new l();
                            mutable_bitField0_ |= 1;
                        }
                        this.string_.m(bs);
                        break;
                    default:
                        if (parseUnknownField(input, unknownFieldsCodedOutput, extensionRegistry, tag)) {
                            break;
                        } else {
                            done = true;
                            break;
                        }
                }
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
            } catch (Throwable th) {
                if ((mutable_bitField0_ & 1) == 1) {
                    this.string_ = this.string_.u0();
                }
                try {
                    unknownFieldsCodedOutput.I();
                } catch (IOException e3) {
                } catch (Throwable th2) {
                    this.unknownFields = unknownFieldsOutput.j();
                    throw th2;
                }
                this.unknownFields = unknownFieldsOutput.j();
                makeExtensionsImmutable();
                throw th;
            }
        }
        if ((mutable_bitField0_ & 1) == 1) {
            this.string_ = this.string_.u0();
        }
        try {
            unknownFieldsCodedOutput.I();
        } catch (IOException e4) {
        } catch (Throwable th3) {
            this.unknownFields = unknownFieldsOutput.j();
            throw th3;
        }
        this.unknownFields = unknownFieldsOutput.j();
        makeExtensionsImmutable();
    }

    /* compiled from: ProtoBuf */
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<p> {
        a() {
        }

        /* renamed from: m */
        public p c(e input, f extensionRegistry) {
            return new p(input, extensionRegistry);
        }
    }

    static {
        p pVar = new p(true);
        c = pVar;
        pVar.b();
    }

    public q<p> getParserForType() {
        return PARSER;
    }

    public r getStringList() {
        return this.string_;
    }

    public String getString(int index) {
        return (String) this.string_.get(index);
    }

    private void b() {
        this.string_ = l.c;
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        for (int i = 0; i < this.string_.size(); i++) {
            output.O(1, this.string_.l(i));
        }
        output.i0(this.unknownFields);
    }

    public int getSerializedSize() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int dataSize = 0;
        for (int i = 0; i < this.string_.size(); i++) {
            dataSize += CodedOutputStream.e(this.string_.l(i));
        }
        int size2 = 0 + dataSize + (getStringList().size() * 1) + this.unknownFields.size();
        this.memoizedSerializedSize = size2;
        return size2;
    }

    public static b newBuilder() {
        return b.l();
    }

    public b newBuilderForType() {
        return newBuilder();
    }

    public static b newBuilder(p prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.b<p, b> implements kotlin.reflect.jvm.internal.impl.protobuf.p {
        private int d;
        private m f = l.c;

        private b() {
            n();
        }

        private void n() {
        }

        /* access modifiers changed from: private */
        public static b l() {
            return new b();
        }

        /* renamed from: k */
        public b clone() {
            return l().e(j());
        }

        /* renamed from: i */
        public p build() {
            p result = j();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public p j() {
            p result = new p((h.b) this);
            int i = this.d;
            if ((this.d & 1) == 1) {
                this.f = this.f.u0();
                this.d &= -2;
            }
            m unused = result.string_ = this.f;
            return result;
        }

        /* renamed from: o */
        public b e(p other) {
            if (other == p.getDefaultInstance()) {
                return this;
            }
            if (!other.string_.isEmpty()) {
                if (this.f.isEmpty()) {
                    this.f = other.string_;
                    this.d &= -2;
                } else {
                    m();
                    this.f.addAll(other.string_);
                }
            }
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            return true;
        }

        /* renamed from: p */
        public b a(e input, f extensionRegistry) {
            try {
                p parsedMessage = p.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                p parsedMessage2 = (p) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((p) null);
                }
                throw th;
            }
        }

        private void m() {
            if ((this.d & 1) != 1) {
                this.f = new l(this.f);
                this.d |= 1;
            }
        }
    }
}
