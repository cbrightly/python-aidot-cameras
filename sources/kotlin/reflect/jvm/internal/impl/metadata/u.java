package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.q;
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
public final class u extends h.d<u> implements p {
    public static q<u> PARSER = new a();
    private static final u c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int flags_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public int name_;
    /* access modifiers changed from: private */
    public int typeId_;
    /* access modifiers changed from: private */
    public q type_;
    /* access modifiers changed from: private */
    public final d unknownFields;
    /* access modifiers changed from: private */
    public int varargElementTypeId_;
    /* access modifiers changed from: private */
    public q varargElementType_;

    private u(h.c<u, ?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private u(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static u getDefaultInstance() {
        return c;
    }

    public u getDefaultInstanceForType() {
        return c;
    }

    private u(e input, f extensionRegistry) {
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
                        this.flags_ = input.s();
                        break;
                    case 16:
                        this.bitField0_ |= 2;
                        this.name_ = input.s();
                        break;
                    case 26:
                        q.c subBuilder = (this.bitField0_ & 4) == 4 ? this.type_.toBuilder() : null;
                        q qVar = (q) input.u(q.PARSER, extensionRegistry);
                        this.type_ = qVar;
                        if (subBuilder != null) {
                            subBuilder.e(qVar);
                            this.type_ = subBuilder.o();
                        }
                        this.bitField0_ |= 4;
                        break;
                    case 34:
                        q.c subBuilder2 = (this.bitField0_ & 16) == 16 ? this.varargElementType_.toBuilder() : null;
                        q qVar2 = (q) input.u(q.PARSER, extensionRegistry);
                        this.varargElementType_ = qVar2;
                        if (subBuilder2 != null) {
                            subBuilder2.e(qVar2);
                            this.varargElementType_ = subBuilder2.o();
                        }
                        this.bitField0_ |= 16;
                        break;
                    case 40:
                        this.bitField0_ |= 8;
                        this.typeId_ = input.s();
                        break;
                    case 48:
                        this.bitField0_ |= 32;
                        this.varargElementTypeId_ = input.s();
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<u> {
        a() {
        }

        /* renamed from: m */
        public u c(e input, f extensionRegistry) {
            return new u(input, extensionRegistry);
        }
    }

    static {
        u uVar = new u(true);
        c = uVar;
        uVar.c();
    }

    public kotlin.reflect.jvm.internal.impl.protobuf.q<u> getParserForType() {
        return PARSER;
    }

    public boolean hasFlags() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getFlags() {
        return this.flags_;
    }

    public boolean hasName() {
        return (this.bitField0_ & 2) == 2;
    }

    public int getName() {
        return this.name_;
    }

    public boolean hasType() {
        return (this.bitField0_ & 4) == 4;
    }

    public q getType() {
        return this.type_;
    }

    public boolean hasTypeId() {
        return (this.bitField0_ & 8) == 8;
    }

    public int getTypeId() {
        return this.typeId_;
    }

    public boolean hasVarargElementType() {
        return (this.bitField0_ & 16) == 16;
    }

    public q getVarargElementType() {
        return this.varargElementType_;
    }

    public boolean hasVarargElementTypeId() {
        return (this.bitField0_ & 32) == 32;
    }

    public int getVarargElementTypeId() {
        return this.varargElementTypeId_;
    }

    private void c() {
        this.flags_ = 0;
        this.name_ = 0;
        this.type_ = q.getDefaultInstance();
        this.typeId_ = 0;
        this.varargElementType_ = q.getDefaultInstance();
        this.varargElementTypeId_ = 0;
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        if (!hasName()) {
            this.memoizedIsInitialized = 0;
            return false;
        } else if (hasType() && !getType().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
        } else if (hasVarargElementType() && !getVarargElementType().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
        } else if (!extensionsAreInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
        } else {
            this.memoizedIsInitialized = 1;
            return true;
        }
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.ValueParameter>.ExtensionWriter extensionWriter = newExtensionWriter();
        if ((this.bitField0_ & 1) == 1) {
            output.a0(1, this.flags_);
        }
        if ((this.bitField0_ & 2) == 2) {
            output.a0(2, this.name_);
        }
        if ((this.bitField0_ & 4) == 4) {
            output.d0(3, this.type_);
        }
        if ((this.bitField0_ & 16) == 16) {
            output.d0(4, this.varargElementType_);
        }
        if ((this.bitField0_ & 8) == 8) {
            output.a0(5, this.typeId_);
        }
        if ((this.bitField0_ & 32) == 32) {
            output.a0(6, this.varargElementTypeId_);
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
            size2 = 0 + CodedOutputStream.o(1, this.flags_);
        }
        if ((this.bitField0_ & 2) == 2) {
            size2 += CodedOutputStream.o(2, this.name_);
        }
        if ((this.bitField0_ & 4) == 4) {
            size2 += CodedOutputStream.s(3, this.type_);
        }
        if ((this.bitField0_ & 16) == 16) {
            size2 += CodedOutputStream.s(4, this.varargElementType_);
        }
        if ((this.bitField0_ & 8) == 8) {
            size2 += CodedOutputStream.o(5, this.typeId_);
        }
        if ((this.bitField0_ & 32) == 32) {
            size2 += CodedOutputStream.o(6, this.varargElementTypeId_);
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

    public static b newBuilder(u prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<u, b> implements p {
        private q a1 = q.getDefaultInstance();
        private int p0;
        private int p1;
        private int q;
        private int x;
        private int y;
        private q z = q.getDefaultInstance();

        private b() {
            w();
        }

        private void w() {
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
        public u build() {
            u result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public u o() {
            u result = new u((h.c) this);
            int from_bitField0_ = this.q;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            int unused = result.flags_ = this.x;
            if ((from_bitField0_ & 2) == 2) {
                to_bitField0_ |= 2;
            }
            int unused2 = result.name_ = this.y;
            if ((from_bitField0_ & 4) == 4) {
                to_bitField0_ |= 4;
            }
            q unused3 = result.type_ = this.z;
            if ((from_bitField0_ & 8) == 8) {
                to_bitField0_ |= 8;
            }
            int unused4 = result.typeId_ = this.p0;
            if ((from_bitField0_ & 16) == 16) {
                to_bitField0_ |= 16;
            }
            q unused5 = result.varargElementType_ = this.a1;
            if ((from_bitField0_ & 32) == 32) {
                to_bitField0_ |= 32;
            }
            int unused6 = result.varargElementTypeId_ = this.p1;
            int unused7 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: y */
        public b e(u other) {
            if (other == u.getDefaultInstance()) {
                return this;
            }
            if (other.hasFlags()) {
                C(other.getFlags());
            }
            if (other.hasName()) {
                D(other.getName());
            }
            if (other.hasType()) {
                A(other.getType());
            }
            if (other.hasTypeId()) {
                F(other.getTypeId());
            }
            if (other.hasVarargElementType()) {
                B(other.getVarargElementType());
            }
            if (other.hasVarargElementTypeId()) {
                G(other.getVarargElementTypeId());
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (!t()) {
                return false;
            }
            if (u() && !r().isInitialized()) {
                return false;
            }
            if ((!v() || s().isInitialized()) && k()) {
                return true;
            }
            return false;
        }

        /* renamed from: z */
        public b a(e input, f extensionRegistry) {
            try {
                u parsedMessage = u.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                u parsedMessage2 = (u) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((u) null);
                }
                throw th;
            }
        }

        public b C(int value) {
            this.q |= 1;
            this.x = value;
            return this;
        }

        public boolean t() {
            return (this.q & 2) == 2;
        }

        public b D(int value) {
            this.q |= 2;
            this.y = value;
            return this;
        }

        public boolean u() {
            return (this.q & 4) == 4;
        }

        public q r() {
            return this.z;
        }

        public b A(q value) {
            if ((this.q & 4) != 4 || this.z == q.getDefaultInstance()) {
                this.z = value;
            } else {
                this.z = q.newBuilder(this.z).e(value).o();
            }
            this.q |= 4;
            return this;
        }

        public b F(int value) {
            this.q |= 8;
            this.p0 = value;
            return this;
        }

        public boolean v() {
            return (this.q & 16) == 16;
        }

        public q s() {
            return this.a1;
        }

        public b B(q value) {
            if ((this.q & 16) != 16 || this.a1 == q.getDefaultInstance()) {
                this.a1 = value;
            } else {
                this.a1 = q.newBuilder(this.a1).e(value).o();
            }
            this.q |= 16;
            return this;
        }

        public b G(int value) {
            this.q |= 32;
            this.p1 = value;
            return this;
        }
    }
}
