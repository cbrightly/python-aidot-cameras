package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public final class o extends h implements p {
    public static q<o> PARSER = new a();
    private static final o c;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public List<c> qualifiedName_;
    /* access modifiers changed from: private */
    public final d unknownFields;

    private o(h.b builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private o(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static o getDefaultInstance() {
        return c;
    }

    public o getDefaultInstanceForType() {
        return c;
    }

    private o(e input, f extensionRegistry) {
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
                        if ((mutable_bitField0_ & 1) != 1) {
                            this.qualifiedName_ = new ArrayList();
                            mutable_bitField0_ |= 1;
                        }
                        this.qualifiedName_.add(input.u(c.PARSER, extensionRegistry));
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
                    this.qualifiedName_ = Collections.unmodifiableList(this.qualifiedName_);
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
            this.qualifiedName_ = Collections.unmodifiableList(this.qualifiedName_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<o> {
        a() {
        }

        /* renamed from: m */
        public o c(e input, f extensionRegistry) {
            return new o(input, extensionRegistry);
        }
    }

    static {
        o oVar = new o(true);
        c = oVar;
        oVar.b();
    }

    public q<o> getParserForType() {
        return PARSER;
    }

    /* compiled from: ProtoBuf */
    public static final class c extends h implements p {
        public static q<c> PARSER = new a();
        private static final c c;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public C0396c kind_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        /* access modifiers changed from: private */
        public int parentQualifiedName_;
        /* access modifiers changed from: private */
        public int shortName_;
        /* access modifiers changed from: private */
        public final d unknownFields;

        private c(h.b builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.d();
        }

        private c(boolean noInit) {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = d.c;
        }

        public static c getDefaultInstance() {
            return c;
        }

        public c getDefaultInstanceForType() {
            return c;
        }

        private c(e input, f extensionRegistry) {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            b();
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
                            this.parentQualifiedName_ = input.s();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.shortName_ = input.s();
                            break;
                        case 24:
                            int rawValue = input.n();
                            C0396c value = C0396c.valueOf(rawValue);
                            if (value != null) {
                                this.bitField0_ |= 4;
                                this.kind_ = value;
                                break;
                            } else {
                                unknownFieldsCodedOutput.o0(tag);
                                unknownFieldsCodedOutput.o0(rawValue);
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
        public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<c> {
            a() {
            }

            /* renamed from: m */
            public c c(e input, f extensionRegistry) {
                return new c(input, extensionRegistry);
            }
        }

        static {
            c cVar = new c(true);
            c = cVar;
            cVar.b();
        }

        public q<c> getParserForType() {
            return PARSER;
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.o$c$c  reason: collision with other inner class name */
        /* compiled from: ProtoBuf */
        public enum C0396c implements i.a {
            CLASS(0, 0),
            PACKAGE(1, 1),
            LOCAL(2, 2);
            
            private static i.b<C0396c> c;
            private final int value;

            static {
                c = new a();
            }

            public final int getNumber() {
                return this.value;
            }

            public static C0396c valueOf(int value2) {
                switch (value2) {
                    case 0:
                        return CLASS;
                    case 1:
                        return PACKAGE;
                    case 2:
                        return LOCAL;
                    default:
                        return null;
                }
            }

            /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.o$c$c$a */
            /* compiled from: ProtoBuf */
            public static final class a implements i.b<C0396c> {
                a() {
                }

                /* renamed from: b */
                public C0396c a(int number) {
                    return C0396c.valueOf(number);
                }
            }

            private C0396c(int index, int value2) {
                this.value = value2;
            }
        }

        public boolean hasParentQualifiedName() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getParentQualifiedName() {
            return this.parentQualifiedName_;
        }

        public boolean hasShortName() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getShortName() {
            return this.shortName_;
        }

        public boolean hasKind() {
            return (this.bitField0_ & 4) == 4;
        }

        public C0396c getKind() {
            return this.kind_;
        }

        private void b() {
            this.parentQualifiedName_ = -1;
            this.shortName_ = 0;
            this.kind_ = C0396c.PACKAGE;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasShortName()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.a0(1, this.parentQualifiedName_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.a0(2, this.shortName_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.S(3, this.kind_.getNumber());
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
                size2 = 0 + CodedOutputStream.o(1, this.parentQualifiedName_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.o(2, this.shortName_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.h(3, this.kind_.getNumber());
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

        public static b newBuilder(c prototype) {
            return newBuilder().e(prototype);
        }

        public b toBuilder() {
            return newBuilder(this);
        }

        /* compiled from: ProtoBuf */
        public static final class b extends h.b<c, b> implements p {
            private int d;
            private int f = -1;
            private int q;
            private C0396c x = C0396c.PACKAGE;

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
            public c build() {
                c result = j();
                if (result.isInitialized()) {
                    return result;
                }
                throw a.C0398a.b(result);
            }

            public c j() {
                c result = new c((h.b) this);
                int from_bitField0_ = this.d;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                int unused = result.parentQualifiedName_ = this.f;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                int unused2 = result.shortName_ = this.q;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                C0396c unused3 = result.kind_ = this.x;
                int unused4 = result.bitField0_ = to_bitField0_;
                return result;
            }

            /* renamed from: o */
            public b e(c other) {
                if (other == c.getDefaultInstance()) {
                    return this;
                }
                if (other.hasParentQualifiedName()) {
                    r(other.getParentQualifiedName());
                }
                if (other.hasShortName()) {
                    s(other.getShortName());
                }
                if (other.hasKind()) {
                    q(other.getKind());
                }
                f(d().b(other.unknownFields));
                return this;
            }

            public final boolean isInitialized() {
                if (!m()) {
                    return false;
                }
                return true;
            }

            /* renamed from: p */
            public b a(e input, f extensionRegistry) {
                try {
                    c parsedMessage = c.PARSER.c(input, extensionRegistry);
                    if (parsedMessage != null) {
                        e(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    c parsedMessage2 = (c) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (0 != 0) {
                        e((c) null);
                    }
                    throw th;
                }
            }

            public b r(int value) {
                this.d |= 1;
                this.f = value;
                return this;
            }

            public boolean m() {
                return (this.d & 2) == 2;
            }

            public b s(int value) {
                this.d |= 2;
                this.q = value;
                return this;
            }

            public b q(C0396c value) {
                if (value != null) {
                    this.d |= 4;
                    this.x = value;
                    return this;
                }
                throw new NullPointerException();
            }
        }
    }

    public int getQualifiedNameCount() {
        return this.qualifiedName_.size();
    }

    public c getQualifiedName(int index) {
        return this.qualifiedName_.get(index);
    }

    private void b() {
        this.qualifiedName_ = Collections.emptyList();
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        for (int i = 0; i < getQualifiedNameCount(); i++) {
            if (!getQualifiedName(i).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        for (int i = 0; i < this.qualifiedName_.size(); i++) {
            output.d0(1, this.qualifiedName_.get(i));
        }
        output.i0(this.unknownFields);
    }

    public int getSerializedSize() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        for (int i = 0; i < this.qualifiedName_.size(); i++) {
            size2 += CodedOutputStream.s(1, this.qualifiedName_.get(i));
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

    public static b newBuilder(o prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.b<o, b> implements p {
        private int d;
        private List<c> f = Collections.emptyList();

        private b() {
            p();
        }

        private void p() {
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
        public o build() {
            o result = j();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public o j() {
            o result = new o((h.b) this);
            int i = this.d;
            if ((this.d & 1) == 1) {
                this.f = Collections.unmodifiableList(this.f);
                this.d &= -2;
            }
            List unused = result.qualifiedName_ = this.f;
            return result;
        }

        /* renamed from: q */
        public b e(o other) {
            if (other == o.getDefaultInstance()) {
                return this;
            }
            if (!other.qualifiedName_.isEmpty()) {
                if (this.f.isEmpty()) {
                    this.f = other.qualifiedName_;
                    this.d &= -2;
                } else {
                    m();
                    this.f.addAll(other.qualifiedName_);
                }
            }
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            for (int i = 0; i < o(); i++) {
                if (!n(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: r */
        public b a(e input, f extensionRegistry) {
            try {
                o parsedMessage = o.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                o parsedMessage2 = (o) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((o) null);
                }
                throw th;
            }
        }

        private void m() {
            if ((this.d & 1) != 1) {
                this.f = new ArrayList(this.f);
                this.d |= 1;
            }
        }

        public int o() {
            return this.f.size();
        }

        public c n(int index) {
            return this.f.get(index);
        }
    }
}
