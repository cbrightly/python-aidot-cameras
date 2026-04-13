package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.a;
import kotlin.reflect.jvm.internal.impl.protobuf.d;
import kotlin.reflect.jvm.internal.impl.protobuf.e;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.protobuf.p;
import kotlin.reflect.jvm.internal.impl.protobuf.q;

/* compiled from: ProtoBuf */
public final class g extends h.d<g> implements p {
    public static q<g> PARSER = new a();
    private static final g c;
    /* access modifiers changed from: private */
    public int bitField0_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public int name_;
    /* access modifiers changed from: private */
    public final d unknownFields;

    private g(h.c<g, ?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private g(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static g getDefaultInstance() {
        return c;
    }

    public g getDefaultInstanceForType() {
        return c;
    }

    private g(e input, f extensionRegistry) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        c();
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
                    case 8:
                        this.bitField0_ |= 1;
                        this.name_ = input.s();
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<g> {
        a() {
        }

        /* renamed from: m */
        public g c(e input, f extensionRegistry) {
            return new g(input, extensionRegistry);
        }
    }

    static {
        g gVar = new g(true);
        c = gVar;
        gVar.c();
    }

    public q<g> getParserForType() {
        return PARSER;
    }

    public boolean hasName() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getName() {
        return this.name_;
    }

    private void c() {
        this.name_ = 0;
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        if (!extensionsAreInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.EnumEntry>.ExtensionWriter extensionWriter = newExtensionWriter();
        if ((this.bitField0_ & 1) == 1) {
            output.a0(1, this.name_);
        }
        extensionWriter.a(200, output);
        output.i0(this.unknownFields);
    }

    public int getSerializedSize() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if ((this.bitField0_ & 1) == 1) {
            size2 = 0 + CodedOutputStream.o(1, this.name_);
        }
        int size3 = size2 + extensionsSerializedSize() + this.unknownFields.size();
        this.memoizedSerializedSize = size3;
        return size3;
    }

    public static b newBuilder() {
        return b.q();
    }

    public b newBuilderForType() {
        return newBuilder();
    }

    public static b newBuilder(g prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<g, b> implements p {
        private int q;
        private int x;

        private b() {
            r();
        }

        private void r() {
        }

        /* access modifiers changed from: private */
        public static b q() {
            return new b();
        }

        /* renamed from: p */
        public b clone() {
            return q().e(o());
        }

        /* renamed from: n */
        public g build() {
            g result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public g o() {
            g result = new g((h.c) this);
            int to_bitField0_ = 0;
            if ((this.q & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            int unused = result.name_ = this.x;
            int unused2 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: s */
        public b e(g other) {
            if (other == g.getDefaultInstance()) {
                return this;
            }
            if (other.hasName()) {
                u(other.getName());
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (!k()) {
                return false;
            }
            return true;
        }

        /* renamed from: t */
        public b a(e input, f extensionRegistry) {
            try {
                g parsedMessage = g.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                g parsedMessage2 = (g) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((g) null);
                }
                throw th;
            }
        }

        public b u(int value) {
            this.q |= 1;
            this.x = value;
            return this;
        }
    }
}
