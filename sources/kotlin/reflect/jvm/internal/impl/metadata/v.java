package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.a;
import kotlin.reflect.jvm.internal.impl.protobuf.d;
import kotlin.reflect.jvm.internal.impl.protobuf.e;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.protobuf.i;
import kotlin.reflect.jvm.internal.impl.protobuf.p;
import kotlin.reflect.jvm.internal.impl.protobuf.q;

/* compiled from: ProtoBuf */
public final class v extends h implements p {
    public static q<v> PARSER = new a();
    private static final v c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int errorCode_;
    /* access modifiers changed from: private */
    public c level_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public int message_;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.protobuf.d unknownFields;
    /* access modifiers changed from: private */
    public int versionFull_;
    /* access modifiers changed from: private */
    public d versionKind_;
    /* access modifiers changed from: private */
    public int version_;

    private v(h.b builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private v(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = kotlin.reflect.jvm.internal.impl.protobuf.d.c;
    }

    public static v getDefaultInstance() {
        return c;
    }

    public v getDefaultInstanceForType() {
        return c;
    }

    private v(e input, f extensionRegistry) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        b();
        d.b unknownFieldsOutput = kotlin.reflect.jvm.internal.impl.protobuf.d.r();
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
                        this.version_ = input.s();
                        break;
                    case 16:
                        this.bitField0_ |= 2;
                        this.versionFull_ = input.s();
                        break;
                    case 24:
                        int rawValue = input.n();
                        c value = c.valueOf(rawValue);
                        if (value != null) {
                            this.bitField0_ |= 4;
                            this.level_ = value;
                            break;
                        } else {
                            unknownFieldsCodedOutput.o0(tag);
                            unknownFieldsCodedOutput.o0(rawValue);
                            break;
                        }
                    case 32:
                        this.bitField0_ |= 8;
                        this.errorCode_ = input.s();
                        break;
                    case 40:
                        this.bitField0_ |= 16;
                        this.message_ = input.s();
                        break;
                    case 48:
                        int rawValue2 = input.n();
                        d value2 = d.valueOf(rawValue2);
                        if (value2 != null) {
                            this.bitField0_ |= 32;
                            this.versionKind_ = value2;
                            break;
                        } else {
                            unknownFieldsCodedOutput.o0(tag);
                            unknownFieldsCodedOutput.o0(rawValue2);
                            break;
                        }
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<v> {
        a() {
        }

        /* renamed from: m */
        public v c(e input, f extensionRegistry) {
            return new v(input, extensionRegistry);
        }
    }

    static {
        v vVar = new v(true);
        c = vVar;
        vVar.b();
    }

    public q<v> getParserForType() {
        return PARSER;
    }

    /* compiled from: ProtoBuf */
    public enum c implements i.a {
        WARNING(0, 0),
        ERROR(1, 1),
        HIDDEN(2, 2);
        
        private static i.b<c> c;
        private final int value;

        static {
            c = new a();
        }

        public final int getNumber() {
            return this.value;
        }

        public static c valueOf(int value2) {
            switch (value2) {
                case 0:
                    return WARNING;
                case 1:
                    return ERROR;
                case 2:
                    return HIDDEN;
                default:
                    return null;
            }
        }

        /* compiled from: ProtoBuf */
        public static final class a implements i.b<c> {
            a() {
            }

            /* renamed from: b */
            public c a(int number) {
                return c.valueOf(number);
            }
        }

        private c(int index, int value2) {
            this.value = value2;
        }
    }

    /* compiled from: ProtoBuf */
    public enum d implements i.a {
        LANGUAGE_VERSION(0, 0),
        COMPILER_VERSION(1, 1),
        API_VERSION(2, 2);
        
        private static i.b<d> c;
        private final int value;

        static {
            c = new a();
        }

        public final int getNumber() {
            return this.value;
        }

        public static d valueOf(int value2) {
            switch (value2) {
                case 0:
                    return LANGUAGE_VERSION;
                case 1:
                    return COMPILER_VERSION;
                case 2:
                    return API_VERSION;
                default:
                    return null;
            }
        }

        /* compiled from: ProtoBuf */
        public static final class a implements i.b<d> {
            a() {
            }

            /* renamed from: b */
            public d a(int number) {
                return d.valueOf(number);
            }
        }

        private d(int index, int value2) {
            this.value = value2;
        }
    }

    public boolean hasVersion() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getVersion() {
        return this.version_;
    }

    public boolean hasVersionFull() {
        return (this.bitField0_ & 2) == 2;
    }

    public int getVersionFull() {
        return this.versionFull_;
    }

    public boolean hasLevel() {
        return (this.bitField0_ & 4) == 4;
    }

    public c getLevel() {
        return this.level_;
    }

    public boolean hasErrorCode() {
        return (this.bitField0_ & 8) == 8;
    }

    public int getErrorCode() {
        return this.errorCode_;
    }

    public boolean hasMessage() {
        return (this.bitField0_ & 16) == 16;
    }

    public int getMessage() {
        return this.message_;
    }

    public boolean hasVersionKind() {
        return (this.bitField0_ & 32) == 32;
    }

    public d getVersionKind() {
        return this.versionKind_;
    }

    private void b() {
        this.version_ = 0;
        this.versionFull_ = 0;
        this.level_ = c.ERROR;
        this.errorCode_ = 0;
        this.message_ = 0;
        this.versionKind_ = d.LANGUAGE_VERSION;
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
        if ((this.bitField0_ & 1) == 1) {
            output.a0(1, this.version_);
        }
        if ((this.bitField0_ & 2) == 2) {
            output.a0(2, this.versionFull_);
        }
        if ((this.bitField0_ & 4) == 4) {
            output.S(3, this.level_.getNumber());
        }
        if ((this.bitField0_ & 8) == 8) {
            output.a0(4, this.errorCode_);
        }
        if ((this.bitField0_ & 16) == 16) {
            output.a0(5, this.message_);
        }
        if ((this.bitField0_ & 32) == 32) {
            output.S(6, this.versionKind_.getNumber());
        }
        output.i0(this.unknownFields);
    }

    public int getSerializedSize() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if ((this.bitField0_ & 1) == 1) {
            size2 = 0 + CodedOutputStream.o(1, this.version_);
        }
        if ((this.bitField0_ & 2) == 2) {
            size2 += CodedOutputStream.o(2, this.versionFull_);
        }
        if ((this.bitField0_ & 4) == 4) {
            size2 += CodedOutputStream.h(3, this.level_.getNumber());
        }
        if ((this.bitField0_ & 8) == 8) {
            size2 += CodedOutputStream.o(4, this.errorCode_);
        }
        if ((this.bitField0_ & 16) == 16) {
            size2 += CodedOutputStream.o(5, this.message_);
        }
        if ((this.bitField0_ & 32) == 32) {
            size2 += CodedOutputStream.h(6, this.versionKind_.getNumber());
        }
        int size3 = size2 + this.unknownFields.size();
        this.memoizedSerializedSize = size3;
        return size3;
    }

    public static b newBuilder() {
        return b.l();
    }

    public b newBuilderForType() {
        return newBuilder();
    }

    public static b newBuilder(v prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.b<v, b> implements p {
        private int d;
        private int f;
        private d p0 = d.LANGUAGE_VERSION;
        private int q;
        private c x = c.ERROR;
        private int y;
        private int z;

        private b() {
            m();
        }

        private void m() {
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
        public v build() {
            v result = j();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public v j() {
            v result = new v((h.b) this);
            int from_bitField0_ = this.d;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            int unused = result.version_ = this.f;
            if ((from_bitField0_ & 2) == 2) {
                to_bitField0_ |= 2;
            }
            int unused2 = result.versionFull_ = this.q;
            if ((from_bitField0_ & 4) == 4) {
                to_bitField0_ |= 4;
            }
            c unused3 = result.level_ = this.x;
            if ((from_bitField0_ & 8) == 8) {
                to_bitField0_ |= 8;
            }
            int unused4 = result.errorCode_ = this.y;
            if ((from_bitField0_ & 16) == 16) {
                to_bitField0_ |= 16;
            }
            int unused5 = result.message_ = this.z;
            if ((from_bitField0_ & 32) == 32) {
                to_bitField0_ |= 32;
            }
            d unused6 = result.versionKind_ = this.p0;
            int unused7 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: n */
        public b e(v other) {
            if (other == v.getDefaultInstance()) {
                return this;
            }
            if (other.hasVersion()) {
                s(other.getVersion());
            }
            if (other.hasVersionFull()) {
                t(other.getVersionFull());
            }
            if (other.hasLevel()) {
                q(other.getLevel());
            }
            if (other.hasErrorCode()) {
                p(other.getErrorCode());
            }
            if (other.hasMessage()) {
                r(other.getMessage());
            }
            if (other.hasVersionKind()) {
                u(other.getVersionKind());
            }
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            return true;
        }

        /* renamed from: o */
        public b a(e input, f extensionRegistry) {
            try {
                v parsedMessage = v.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                v parsedMessage2 = (v) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((v) null);
                }
                throw th;
            }
        }

        public b s(int value) {
            this.d |= 1;
            this.f = value;
            return this;
        }

        public b t(int value) {
            this.d |= 2;
            this.q = value;
            return this;
        }

        public b q(c value) {
            if (value != null) {
                this.d |= 4;
                this.x = value;
                return this;
            }
            throw new NullPointerException();
        }

        public b p(int value) {
            this.d |= 8;
            this.y = value;
            return this;
        }

        public b r(int value) {
            this.d |= 16;
            this.z = value;
            return this;
        }

        public b u(d value) {
            if (value != null) {
                this.d |= 32;
                this.p0 = value;
                return this;
            }
            throw new NullPointerException();
        }
    }
}
