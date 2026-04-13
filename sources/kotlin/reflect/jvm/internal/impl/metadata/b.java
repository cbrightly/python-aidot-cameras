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
public final class b extends h implements p {
    public static q<b> PARSER = new a();
    private static final b c;
    /* access modifiers changed from: private */
    public List<C0379b> argument_;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int id_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public final d unknownFields;

    private b(h.b builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private b(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static b getDefaultInstance() {
        return c;
    }

    public b getDefaultInstanceForType() {
        return c;
    }

    private b(e input, f extensionRegistry) {
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
                    case 8:
                        this.bitField0_ |= 1;
                        this.id_ = input.s();
                        break;
                    case 18:
                        if ((mutable_bitField0_ & 2) != 2) {
                            this.argument_ = new ArrayList();
                            mutable_bitField0_ |= 2;
                        }
                        this.argument_.add(input.u(C0379b.PARSER, extensionRegistry));
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
                if ((mutable_bitField0_ & 2) == 2) {
                    this.argument_ = Collections.unmodifiableList(this.argument_);
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
        if ((mutable_bitField0_ & 2) == 2) {
            this.argument_ = Collections.unmodifiableList(this.argument_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<b> {
        a() {
        }

        /* renamed from: m */
        public b c(e input, f extensionRegistry) {
            return new b(input, extensionRegistry);
        }
    }

    static {
        b bVar = new b(true);
        c = bVar;
        bVar.b();
    }

    public q<b> getParserForType() {
        return PARSER;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.b$b  reason: collision with other inner class name */
    /* compiled from: ProtoBuf */
    public static final class C0379b extends h implements p {
        public static q<C0379b> PARSER = new a();
        private static final C0379b c;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        /* access modifiers changed from: private */
        public int nameId_;
        /* access modifiers changed from: private */
        public final d unknownFields;
        /* access modifiers changed from: private */
        public c value_;

        private C0379b(h.b builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.d();
        }

        private C0379b(boolean noInit) {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = d.c;
        }

        public static C0379b getDefaultInstance() {
            return c;
        }

        public C0379b getDefaultInstanceForType() {
            return c;
        }

        private C0379b(e input, f extensionRegistry) {
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
                            this.nameId_ = input.s();
                            break;
                        case 18:
                            c.C0381b subBuilder = (this.bitField0_ & 2) == 2 ? this.value_.toBuilder() : null;
                            c cVar = (c) input.u(c.PARSER, extensionRegistry);
                            this.value_ = cVar;
                            if (subBuilder != null) {
                                subBuilder.e(cVar);
                                this.value_ = subBuilder.j();
                            }
                            this.bitField0_ |= 2;
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

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.b$b$a */
        /* compiled from: ProtoBuf */
        public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<C0379b> {
            a() {
            }

            /* renamed from: m */
            public C0379b c(e input, f extensionRegistry) {
                return new C0379b(input, extensionRegistry);
            }
        }

        static {
            C0379b bVar = new C0379b(true);
            c = bVar;
            bVar.b();
        }

        public q<C0379b> getParserForType() {
            return PARSER;
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.b$b$c */
        /* compiled from: ProtoBuf */
        public static final class c extends h implements p {
            public static q<c> PARSER = new a();
            private static final c c;
            /* access modifiers changed from: private */
            public b annotation_;
            /* access modifiers changed from: private */
            public int arrayDimensionCount_;
            /* access modifiers changed from: private */
            public List<c> arrayElement_;
            /* access modifiers changed from: private */
            public int bitField0_;
            /* access modifiers changed from: private */
            public int classId_;
            /* access modifiers changed from: private */
            public double doubleValue_;
            /* access modifiers changed from: private */
            public int enumValueId_;
            /* access modifiers changed from: private */
            public int flags_;
            /* access modifiers changed from: private */
            public float floatValue_;
            /* access modifiers changed from: private */
            public long intValue_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            /* access modifiers changed from: private */
            public int stringValue_;
            /* access modifiers changed from: private */
            public C0382c type_;
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
                            case 8:
                                int rawValue = input.n();
                                C0382c value = C0382c.valueOf(rawValue);
                                if (value != null) {
                                    this.bitField0_ |= 1;
                                    this.type_ = value;
                                    break;
                                } else {
                                    unknownFieldsCodedOutput.o0(tag);
                                    unknownFieldsCodedOutput.o0(rawValue);
                                    break;
                                }
                            case 16:
                                this.bitField0_ |= 2;
                                this.intValue_ = input.H();
                                break;
                            case 29:
                                this.bitField0_ |= 4;
                                this.floatValue_ = input.q();
                                break;
                            case 33:
                                this.bitField0_ |= 8;
                                this.doubleValue_ = input.m();
                                break;
                            case 40:
                                this.bitField0_ |= 16;
                                this.stringValue_ = input.s();
                                break;
                            case 48:
                                this.bitField0_ |= 32;
                                this.classId_ = input.s();
                                break;
                            case 56:
                                this.bitField0_ |= 64;
                                this.enumValueId_ = input.s();
                                break;
                            case 66:
                                c subBuilder = (this.bitField0_ & 128) == 128 ? this.annotation_.toBuilder() : null;
                                b bVar = (b) input.u(b.PARSER, extensionRegistry);
                                this.annotation_ = bVar;
                                if (subBuilder != null) {
                                    subBuilder.e(bVar);
                                    this.annotation_ = subBuilder.j();
                                }
                                this.bitField0_ |= 128;
                                break;
                            case 74:
                                if ((mutable_bitField0_ & 256) != 256) {
                                    this.arrayElement_ = new ArrayList();
                                    mutable_bitField0_ |= 256;
                                }
                                this.arrayElement_.add(input.u(PARSER, extensionRegistry));
                                break;
                            case 80:
                                this.bitField0_ |= 512;
                                this.flags_ = input.s();
                                break;
                            case 88:
                                this.bitField0_ |= 256;
                                this.arrayDimensionCount_ = input.s();
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
                        if ((mutable_bitField0_ & 256) == 256) {
                            this.arrayElement_ = Collections.unmodifiableList(this.arrayElement_);
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
                if ((mutable_bitField0_ & 256) == 256) {
                    this.arrayElement_ = Collections.unmodifiableList(this.arrayElement_);
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

            /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.b$b$c$a */
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

            /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.b$b$c$c  reason: collision with other inner class name */
            /* compiled from: ProtoBuf */
            public enum C0382c implements i.a {
                BYTE(0, 0),
                CHAR(1, 1),
                SHORT(2, 2),
                INT(3, 3),
                LONG(4, 4),
                FLOAT(5, 5),
                DOUBLE(6, 6),
                BOOLEAN(7, 7),
                STRING(8, 8),
                CLASS(9, 9),
                ENUM(10, 10),
                ANNOTATION(11, 11),
                ARRAY(12, 12);
                
                private static i.b<C0382c> c;
                private final int value;

                static {
                    c = new a();
                }

                public final int getNumber() {
                    return this.value;
                }

                public static C0382c valueOf(int value2) {
                    switch (value2) {
                        case 0:
                            return BYTE;
                        case 1:
                            return CHAR;
                        case 2:
                            return SHORT;
                        case 3:
                            return INT;
                        case 4:
                            return LONG;
                        case 5:
                            return FLOAT;
                        case 6:
                            return DOUBLE;
                        case 7:
                            return BOOLEAN;
                        case 8:
                            return STRING;
                        case 9:
                            return CLASS;
                        case 10:
                            return ENUM;
                        case 11:
                            return ANNOTATION;
                        case 12:
                            return ARRAY;
                        default:
                            return null;
                    }
                }

                /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.b$b$c$c$a */
                /* compiled from: ProtoBuf */
                public static final class a implements i.b<C0382c> {
                    a() {
                    }

                    /* renamed from: b */
                    public C0382c a(int number) {
                        return C0382c.valueOf(number);
                    }
                }

                private C0382c(int index, int value2) {
                    this.value = value2;
                }
            }

            public boolean hasType() {
                return (this.bitField0_ & 1) == 1;
            }

            public C0382c getType() {
                return this.type_;
            }

            public boolean hasIntValue() {
                return (this.bitField0_ & 2) == 2;
            }

            public long getIntValue() {
                return this.intValue_;
            }

            public boolean hasFloatValue() {
                return (this.bitField0_ & 4) == 4;
            }

            public float getFloatValue() {
                return this.floatValue_;
            }

            public boolean hasDoubleValue() {
                return (this.bitField0_ & 8) == 8;
            }

            public double getDoubleValue() {
                return this.doubleValue_;
            }

            public boolean hasStringValue() {
                return (this.bitField0_ & 16) == 16;
            }

            public int getStringValue() {
                return this.stringValue_;
            }

            public boolean hasClassId() {
                return (this.bitField0_ & 32) == 32;
            }

            public int getClassId() {
                return this.classId_;
            }

            public boolean hasEnumValueId() {
                return (this.bitField0_ & 64) == 64;
            }

            public int getEnumValueId() {
                return this.enumValueId_;
            }

            public boolean hasAnnotation() {
                return (this.bitField0_ & 128) == 128;
            }

            public b getAnnotation() {
                return this.annotation_;
            }

            public List<c> getArrayElementList() {
                return this.arrayElement_;
            }

            public int getArrayElementCount() {
                return this.arrayElement_.size();
            }

            public c getArrayElement(int index) {
                return this.arrayElement_.get(index);
            }

            public boolean hasArrayDimensionCount() {
                return (this.bitField0_ & 256) == 256;
            }

            public int getArrayDimensionCount() {
                return this.arrayDimensionCount_;
            }

            public boolean hasFlags() {
                return (this.bitField0_ & 512) == 512;
            }

            public int getFlags() {
                return this.flags_;
            }

            private void b() {
                this.type_ = C0382c.BYTE;
                this.intValue_ = 0;
                this.floatValue_ = 0.0f;
                this.doubleValue_ = 0.0d;
                this.stringValue_ = 0;
                this.classId_ = 0;
                this.enumValueId_ = 0;
                this.annotation_ = b.getDefaultInstance();
                this.arrayElement_ = Collections.emptyList();
                this.arrayDimensionCount_ = 0;
                this.flags_ = 0;
            }

            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == 1) {
                    return true;
                }
                if (isInitialized == 0) {
                    return false;
                }
                if (!hasAnnotation() || getAnnotation().isInitialized()) {
                    for (int i = 0; i < getArrayElementCount(); i++) {
                        if (!getArrayElement(i).isInitialized()) {
                            this.memoizedIsInitialized = 0;
                            return false;
                        }
                    }
                    this.memoizedIsInitialized = 1;
                    return true;
                }
                this.memoizedIsInitialized = 0;
                return false;
            }

            public void writeTo(CodedOutputStream output) {
                getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    output.S(1, this.type_.getNumber());
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.t0(2, this.intValue_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    output.W(3, this.floatValue_);
                }
                if ((this.bitField0_ & 8) == 8) {
                    output.Q(4, this.doubleValue_);
                }
                if ((this.bitField0_ & 16) == 16) {
                    output.a0(5, this.stringValue_);
                }
                if ((this.bitField0_ & 32) == 32) {
                    output.a0(6, this.classId_);
                }
                if ((this.bitField0_ & 64) == 64) {
                    output.a0(7, this.enumValueId_);
                }
                if ((this.bitField0_ & 128) == 128) {
                    output.d0(8, this.annotation_);
                }
                for (int i = 0; i < this.arrayElement_.size(); i++) {
                    output.d0(9, this.arrayElement_.get(i));
                }
                if ((this.bitField0_ & 512) == 512) {
                    output.a0(10, this.flags_);
                }
                if ((this.bitField0_ & 256) == 256) {
                    output.a0(11, this.arrayDimensionCount_);
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
                    size2 = 0 + CodedOutputStream.h(1, this.type_.getNumber());
                }
                if ((this.bitField0_ & 2) == 2) {
                    size2 += CodedOutputStream.A(2, this.intValue_);
                }
                if ((this.bitField0_ & 4) == 4) {
                    size2 += CodedOutputStream.l(3, this.floatValue_);
                }
                if ((this.bitField0_ & 8) == 8) {
                    size2 += CodedOutputStream.f(4, this.doubleValue_);
                }
                if ((this.bitField0_ & 16) == 16) {
                    size2 += CodedOutputStream.o(5, this.stringValue_);
                }
                if ((this.bitField0_ & 32) == 32) {
                    size2 += CodedOutputStream.o(6, this.classId_);
                }
                if ((this.bitField0_ & 64) == 64) {
                    size2 += CodedOutputStream.o(7, this.enumValueId_);
                }
                if ((this.bitField0_ & 128) == 128) {
                    size2 += CodedOutputStream.s(8, this.annotation_);
                }
                for (int i = 0; i < this.arrayElement_.size(); i++) {
                    size2 += CodedOutputStream.s(9, this.arrayElement_.get(i));
                }
                if ((this.bitField0_ & 512) == 512) {
                    size2 += CodedOutputStream.o(10, this.flags_);
                }
                if ((this.bitField0_ & 256) == 256) {
                    size2 += CodedOutputStream.o(11, this.arrayDimensionCount_);
                }
                int size3 = size2 + this.unknownFields.size();
                this.memoizedSerializedSize = size3;
                return size3;
            }

            public static C0381b newBuilder() {
                return C0381b.l();
            }

            public C0381b newBuilderForType() {
                return newBuilder();
            }

            public static C0381b newBuilder(c prototype) {
                return newBuilder().e(prototype);
            }

            public C0381b toBuilder() {
                return newBuilder(this);
            }

            /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.b$b$c$b  reason: collision with other inner class name */
            /* compiled from: ProtoBuf */
            public static final class C0381b extends h.b<c, C0381b> implements p {
                private int a1;
                private List<c> a2 = Collections.emptyList();
                private int d;
                private C0382c f = C0382c.BYTE;
                private int p0;
                private b p1 = b.getDefaultInstance();
                private int p2;
                private int p3;
                private long q;
                private float x;
                private double y;
                private int z;

                private C0381b() {
                    r();
                }

                private void r() {
                }

                /* access modifiers changed from: private */
                public static C0381b l() {
                    return new C0381b();
                }

                /* renamed from: k */
                public C0381b clone() {
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
                    C0382c unused = result.type_ = this.f;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    long unused2 = result.intValue_ = this.q;
                    if ((from_bitField0_ & 4) == 4) {
                        to_bitField0_ |= 4;
                    }
                    float unused3 = result.floatValue_ = this.x;
                    if ((from_bitField0_ & 8) == 8) {
                        to_bitField0_ |= 8;
                    }
                    double unused4 = result.doubleValue_ = this.y;
                    if ((from_bitField0_ & 16) == 16) {
                        to_bitField0_ |= 16;
                    }
                    int unused5 = result.stringValue_ = this.z;
                    if ((from_bitField0_ & 32) == 32) {
                        to_bitField0_ |= 32;
                    }
                    int unused6 = result.classId_ = this.p0;
                    if ((from_bitField0_ & 64) == 64) {
                        to_bitField0_ |= 64;
                    }
                    int unused7 = result.enumValueId_ = this.a1;
                    if ((from_bitField0_ & 128) == 128) {
                        to_bitField0_ |= 128;
                    }
                    b unused8 = result.annotation_ = this.p1;
                    if ((this.d & 256) == 256) {
                        this.a2 = Collections.unmodifiableList(this.a2);
                        this.d &= -257;
                    }
                    List unused9 = result.arrayElement_ = this.a2;
                    if ((from_bitField0_ & 512) == 512) {
                        to_bitField0_ |= 256;
                    }
                    int unused10 = result.arrayDimensionCount_ = this.p2;
                    if ((from_bitField0_ & 1024) == 1024) {
                        to_bitField0_ |= 512;
                    }
                    int unused11 = result.flags_ = this.p3;
                    int unused12 = result.bitField0_ = to_bitField0_;
                    return result;
                }

                /* renamed from: t */
                public C0381b e(c other) {
                    if (other == c.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasType()) {
                        F(other.getType());
                    }
                    if (other.hasIntValue()) {
                        C(other.getIntValue());
                    }
                    if (other.hasFloatValue()) {
                        B(other.getFloatValue());
                    }
                    if (other.hasDoubleValue()) {
                        y(other.getDoubleValue());
                    }
                    if (other.hasStringValue()) {
                        D(other.getStringValue());
                    }
                    if (other.hasClassId()) {
                        w(other.getClassId());
                    }
                    if (other.hasEnumValueId()) {
                        z(other.getEnumValueId());
                    }
                    if (other.hasAnnotation()) {
                        s(other.getAnnotation());
                    }
                    if (!other.arrayElement_.isEmpty()) {
                        if (this.a2.isEmpty()) {
                            this.a2 = other.arrayElement_;
                            this.d &= -257;
                        } else {
                            m();
                            this.a2.addAll(other.arrayElement_);
                        }
                    }
                    if (other.hasArrayDimensionCount()) {
                        v(other.getArrayDimensionCount());
                    }
                    if (other.hasFlags()) {
                        A(other.getFlags());
                    }
                    f(d().b(other.unknownFields));
                    return this;
                }

                public final boolean isInitialized() {
                    if (q() && !n().isInitialized()) {
                        return false;
                    }
                    for (int i = 0; i < p(); i++) {
                        if (!o(i).isInitialized()) {
                            return false;
                        }
                    }
                    return true;
                }

                /* renamed from: u */
                public C0381b a(e input, f extensionRegistry) {
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

                public C0381b F(C0382c value) {
                    if (value != null) {
                        this.d |= 1;
                        this.f = value;
                        return this;
                    }
                    throw new NullPointerException();
                }

                public C0381b C(long value) {
                    this.d |= 2;
                    this.q = value;
                    return this;
                }

                public C0381b B(float value) {
                    this.d |= 4;
                    this.x = value;
                    return this;
                }

                public C0381b y(double value) {
                    this.d |= 8;
                    this.y = value;
                    return this;
                }

                public C0381b D(int value) {
                    this.d |= 16;
                    this.z = value;
                    return this;
                }

                public C0381b w(int value) {
                    this.d |= 32;
                    this.p0 = value;
                    return this;
                }

                public C0381b z(int value) {
                    this.d |= 64;
                    this.a1 = value;
                    return this;
                }

                public boolean q() {
                    return (this.d & 128) == 128;
                }

                public b n() {
                    return this.p1;
                }

                public C0381b s(b value) {
                    if ((this.d & 128) != 128 || this.p1 == b.getDefaultInstance()) {
                        this.p1 = value;
                    } else {
                        this.p1 = b.newBuilder(this.p1).e(value).j();
                    }
                    this.d |= 128;
                    return this;
                }

                private void m() {
                    if ((this.d & 256) != 256) {
                        this.a2 = new ArrayList(this.a2);
                        this.d |= 256;
                    }
                }

                public int p() {
                    return this.a2.size();
                }

                public c o(int index) {
                    return this.a2.get(index);
                }

                public C0381b v(int value) {
                    this.d |= 512;
                    this.p2 = value;
                    return this;
                }

                public C0381b A(int value) {
                    this.d |= 1024;
                    this.p3 = value;
                    return this;
                }
            }
        }

        public boolean hasNameId() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getNameId() {
            return this.nameId_;
        }

        public boolean hasValue() {
            return (this.bitField0_ & 2) == 2;
        }

        public c getValue() {
            return this.value_;
        }

        private void b() {
            this.nameId_ = 0;
            this.value_ = c.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasNameId()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!hasValue()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!getValue().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        }

        public void writeTo(CodedOutputStream output) {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.a0(1, this.nameId_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.d0(2, this.value_);
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
                size2 = 0 + CodedOutputStream.o(1, this.nameId_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.s(2, this.value_);
            }
            int size3 = size2 + this.unknownFields.size();
            this.memoizedSerializedSize = size3;
            return size3;
        }

        public static C0380b newBuilder() {
            return C0380b.l();
        }

        public C0380b newBuilderForType() {
            return newBuilder();
        }

        public static C0380b newBuilder(C0379b prototype) {
            return newBuilder().e(prototype);
        }

        public C0380b toBuilder() {
            return newBuilder(this);
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.b$b$b  reason: collision with other inner class name */
        /* compiled from: ProtoBuf */
        public static final class C0380b extends h.b<C0379b, C0380b> implements p {
            private int d;
            private int f;
            private c q = c.getDefaultInstance();

            private C0380b() {
                p();
            }

            private void p() {
            }

            /* access modifiers changed from: private */
            public static C0380b l() {
                return new C0380b();
            }

            /* renamed from: k */
            public C0380b clone() {
                return l().e(j());
            }

            /* renamed from: i */
            public C0379b build() {
                C0379b result = j();
                if (result.isInitialized()) {
                    return result;
                }
                throw a.C0398a.b(result);
            }

            public C0379b j() {
                C0379b result = new C0379b((h.b) this);
                int from_bitField0_ = this.d;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                int unused = result.nameId_ = this.f;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                c unused2 = result.value_ = this.q;
                int unused3 = result.bitField0_ = to_bitField0_;
                return result;
            }

            /* renamed from: q */
            public C0380b e(C0379b other) {
                if (other == C0379b.getDefaultInstance()) {
                    return this;
                }
                if (other.hasNameId()) {
                    t(other.getNameId());
                }
                if (other.hasValue()) {
                    s(other.getValue());
                }
                f(d().b(other.unknownFields));
                return this;
            }

            public final boolean isInitialized() {
                if (n() && o() && m().isInitialized()) {
                    return true;
                }
                return false;
            }

            /* renamed from: r */
            public C0380b a(e input, f extensionRegistry) {
                try {
                    C0379b parsedMessage = C0379b.PARSER.c(input, extensionRegistry);
                    if (parsedMessage != null) {
                        e(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    C0379b parsedMessage2 = (C0379b) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (0 != 0) {
                        e((C0379b) null);
                    }
                    throw th;
                }
            }

            public boolean n() {
                return (this.d & 1) == 1;
            }

            public C0380b t(int value) {
                this.d |= 1;
                this.f = value;
                return this;
            }

            public boolean o() {
                return (this.d & 2) == 2;
            }

            public c m() {
                return this.q;
            }

            public C0380b s(c value) {
                if ((this.d & 2) != 2 || this.q == c.getDefaultInstance()) {
                    this.q = value;
                } else {
                    this.q = c.newBuilder(this.q).e(value).j();
                }
                this.d |= 2;
                return this;
            }
        }
    }

    public boolean hasId() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getId() {
        return this.id_;
    }

    public List<C0379b> getArgumentList() {
        return this.argument_;
    }

    public int getArgumentCount() {
        return this.argument_.size();
    }

    public C0379b getArgument(int index) {
        return this.argument_.get(index);
    }

    private void b() {
        this.id_ = 0;
        this.argument_ = Collections.emptyList();
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        if (!hasId()) {
            this.memoizedIsInitialized = 0;
            return false;
        }
        for (int i = 0; i < getArgumentCount(); i++) {
            if (!getArgument(i).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        if ((this.bitField0_ & 1) == 1) {
            output.a0(1, this.id_);
        }
        for (int i = 0; i < this.argument_.size(); i++) {
            output.d0(2, this.argument_.get(i));
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
            size2 = 0 + CodedOutputStream.o(1, this.id_);
        }
        for (int i = 0; i < this.argument_.size(); i++) {
            size2 += CodedOutputStream.s(2, this.argument_.get(i));
        }
        int size3 = size2 + this.unknownFields.size();
        this.memoizedSerializedSize = size3;
        return size3;
    }

    public static c newBuilder() {
        return c.l();
    }

    public c newBuilderForType() {
        return newBuilder();
    }

    public static c newBuilder(b prototype) {
        return newBuilder().e(prototype);
    }

    public c toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class c extends h.b<b, c> implements p {
        private int d;
        private int f;
        private List<C0379b> q = Collections.emptyList();

        private c() {
            q();
        }

        private void q() {
        }

        /* access modifiers changed from: private */
        public static c l() {
            return new c();
        }

        /* renamed from: k */
        public c clone() {
            return l().e(j());
        }

        /* renamed from: i */
        public b build() {
            b result = j();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public b j() {
            b result = new b((h.b) this);
            int to_bitField0_ = 0;
            if ((this.d & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            int unused = result.id_ = this.f;
            if ((this.d & 2) == 2) {
                this.q = Collections.unmodifiableList(this.q);
                this.d &= -3;
            }
            List unused2 = result.argument_ = this.q;
            int unused3 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: r */
        public c e(b other) {
            if (other == b.getDefaultInstance()) {
                return this;
            }
            if (other.hasId()) {
                t(other.getId());
            }
            if (!other.argument_.isEmpty()) {
                if (this.q.isEmpty()) {
                    this.q = other.argument_;
                    this.d &= -3;
                } else {
                    m();
                    this.q.addAll(other.argument_);
                }
            }
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (!p()) {
                return false;
            }
            for (int i = 0; i < o(); i++) {
                if (!n(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: s */
        public c a(e input, f extensionRegistry) {
            try {
                b parsedMessage = b.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                b parsedMessage2 = (b) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((b) null);
                }
                throw th;
            }
        }

        public boolean p() {
            return (this.d & 1) == 1;
        }

        public c t(int value) {
            this.d |= 1;
            this.f = value;
            return this;
        }

        private void m() {
            if ((this.d & 2) != 2) {
                this.q = new ArrayList(this.q);
                this.d |= 2;
            }
        }

        public int o() {
            return this.q.size();
        }

        public C0379b n(int index) {
            return this.q.get(index);
        }
    }
}
