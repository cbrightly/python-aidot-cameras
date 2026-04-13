package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.GeneratedMessageLite;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.a;
import kotlin.reflect.jvm.internal.impl.protobuf.d;
import kotlin.reflect.jvm.internal.impl.protobuf.e;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.protobuf.i;
import kotlin.reflect.jvm.internal.impl.protobuf.p;

/* compiled from: ProtoBuf */
public final class q extends h.d<q> implements p {
    public static kotlin.reflect.jvm.internal.impl.protobuf.q<q> PARSER = new a();
    private static final q c;
    /* access modifiers changed from: private */
    public int abbreviatedTypeId_;
    /* access modifiers changed from: private */
    public q abbreviatedType_;
    /* access modifiers changed from: private */
    public List<b> argument_;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int className_;
    /* access modifiers changed from: private */
    public int flags_;
    /* access modifiers changed from: private */
    public int flexibleTypeCapabilitiesId_;
    /* access modifiers changed from: private */
    public int flexibleUpperBoundId_;
    /* access modifiers changed from: private */
    public q flexibleUpperBound_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public boolean nullable_;
    /* access modifiers changed from: private */
    public int outerTypeId_;
    /* access modifiers changed from: private */
    public q outerType_;
    /* access modifiers changed from: private */
    public int typeAliasName_;
    /* access modifiers changed from: private */
    public int typeParameterName_;
    /* access modifiers changed from: private */
    public int typeParameter_;
    /* access modifiers changed from: private */
    public final d unknownFields;

    private q(h.c<q, ?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private q(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static q getDefaultInstance() {
        return c;
    }

    public q getDefaultInstanceForType() {
        return c;
    }

    private q(e input, f extensionRegistry) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        c();
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
                        this.bitField0_ |= 4096;
                        this.flags_ = input.s();
                        break;
                    case 18:
                        if ((mutable_bitField0_ & 1) != 1) {
                            this.argument_ = new ArrayList();
                            mutable_bitField0_ |= 1;
                        }
                        this.argument_.add(input.u(b.PARSER, extensionRegistry));
                        break;
                    case 24:
                        this.bitField0_ |= 1;
                        this.nullable_ = input.k();
                        break;
                    case 32:
                        this.bitField0_ |= 2;
                        this.flexibleTypeCapabilitiesId_ = input.s();
                        break;
                    case 42:
                        c subBuilder = (this.bitField0_ & 4) == 4 ? this.flexibleUpperBound_.toBuilder() : null;
                        q qVar = (q) input.u(PARSER, extensionRegistry);
                        this.flexibleUpperBound_ = qVar;
                        if (subBuilder != null) {
                            subBuilder.e(qVar);
                            this.flexibleUpperBound_ = subBuilder.o();
                        }
                        this.bitField0_ |= 4;
                        break;
                    case 48:
                        this.bitField0_ |= 16;
                        this.className_ = input.s();
                        break;
                    case 56:
                        this.bitField0_ |= 32;
                        this.typeParameter_ = input.s();
                        break;
                    case 64:
                        this.bitField0_ |= 8;
                        this.flexibleUpperBoundId_ = input.s();
                        break;
                    case 72:
                        this.bitField0_ |= 64;
                        this.typeParameterName_ = input.s();
                        break;
                    case 82:
                        c subBuilder2 = (this.bitField0_ & 256) == 256 ? this.outerType_.toBuilder() : null;
                        q qVar2 = (q) input.u(PARSER, extensionRegistry);
                        this.outerType_ = qVar2;
                        if (subBuilder2 != null) {
                            subBuilder2.e(qVar2);
                            this.outerType_ = subBuilder2.o();
                        }
                        this.bitField0_ |= 256;
                        break;
                    case 88:
                        this.bitField0_ |= 512;
                        this.outerTypeId_ = input.s();
                        break;
                    case 96:
                        this.bitField0_ |= 128;
                        this.typeAliasName_ = input.s();
                        break;
                    case 106:
                        c subBuilder3 = (this.bitField0_ & 1024) == 1024 ? this.abbreviatedType_.toBuilder() : null;
                        q qVar3 = (q) input.u(PARSER, extensionRegistry);
                        this.abbreviatedType_ = qVar3;
                        if (subBuilder3 != null) {
                            subBuilder3.e(qVar3);
                            this.abbreviatedType_ = subBuilder3.o();
                        }
                        this.bitField0_ |= 1024;
                        break;
                    case 112:
                        this.bitField0_ |= 2048;
                        this.abbreviatedTypeId_ = input.s();
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
        if ((mutable_bitField0_ & 1) == 1) {
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<q> {
        a() {
        }

        /* renamed from: m */
        public q c(e input, f extensionRegistry) {
            return new q(input, extensionRegistry);
        }
    }

    static {
        q qVar = new q(true);
        c = qVar;
        qVar.c();
    }

    public kotlin.reflect.jvm.internal.impl.protobuf.q<q> getParserForType() {
        return PARSER;
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h implements p {
        public static kotlin.reflect.jvm.internal.impl.protobuf.q<b> PARSER = new a();
        private static final b c;
        /* access modifiers changed from: private */
        public int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        /* access modifiers changed from: private */
        public c projection_;
        /* access modifiers changed from: private */
        public int typeId_;
        /* access modifiers changed from: private */
        public q type_;
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
                            c value = c.valueOf(rawValue);
                            if (value != null) {
                                this.bitField0_ |= 1;
                                this.projection_ = value;
                                break;
                            } else {
                                unknownFieldsCodedOutput.o0(tag);
                                unknownFieldsCodedOutput.o0(rawValue);
                                break;
                            }
                        case 18:
                            c subBuilder = (this.bitField0_ & 2) == 2 ? this.type_.toBuilder() : null;
                            q qVar = (q) input.u(q.PARSER, extensionRegistry);
                            this.type_ = qVar;
                            if (subBuilder != null) {
                                subBuilder.e(qVar);
                                this.type_ = subBuilder.o();
                            }
                            this.bitField0_ |= 2;
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.typeId_ = input.s();
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

        public kotlin.reflect.jvm.internal.impl.protobuf.q<b> getParserForType() {
            return PARSER;
        }

        /* compiled from: ProtoBuf */
        public enum c implements i.a {
            IN(0, 0),
            OUT(1, 1),
            INV(2, 2),
            STAR(3, 3);
            
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
                        return IN;
                    case 1:
                        return OUT;
                    case 2:
                        return INV;
                    case 3:
                        return STAR;
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

        public boolean hasProjection() {
            return (this.bitField0_ & 1) == 1;
        }

        public c getProjection() {
            return this.projection_;
        }

        public boolean hasType() {
            return (this.bitField0_ & 2) == 2;
        }

        public q getType() {
            return this.type_;
        }

        public boolean hasTypeId() {
            return (this.bitField0_ & 4) == 4;
        }

        public int getTypeId() {
            return this.typeId_;
        }

        private void b() {
            this.projection_ = c.INV;
            this.type_ = q.getDefaultInstance();
            this.typeId_ = 0;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == 1) {
                return true;
            }
            if (isInitialized == 0) {
                return false;
            }
            if (!hasType() || getType().isInitialized()) {
                this.memoizedIsInitialized = 1;
                return true;
            }
            this.memoizedIsInitialized = 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.S(1, this.projection_.getNumber());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.d0(2, this.type_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.a0(3, this.typeId_);
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
                size2 = 0 + CodedOutputStream.h(1, this.projection_.getNumber());
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.s(2, this.type_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.o(3, this.typeId_);
            }
            int size3 = size2 + this.unknownFields.size();
            this.memoizedSerializedSize = size3;
            return size3;
        }

        public static C0397b newBuilder() {
            return C0397b.l();
        }

        public C0397b newBuilderForType() {
            return newBuilder();
        }

        public static C0397b newBuilder(b prototype) {
            return newBuilder().e(prototype);
        }

        public C0397b toBuilder() {
            return newBuilder(this);
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.q$b$b  reason: collision with other inner class name */
        /* compiled from: ProtoBuf */
        public static final class C0397b extends h.b<b, C0397b> implements p {
            private int d;
            private c f = c.INV;
            private q q = q.getDefaultInstance();
            private int x;

            private C0397b() {
                o();
            }

            private void o() {
            }

            /* access modifiers changed from: private */
            public static C0397b l() {
                return new C0397b();
            }

            /* renamed from: k */
            public C0397b clone() {
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
                int from_bitField0_ = this.d;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                c unused = result.projection_ = this.f;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                q unused2 = result.type_ = this.q;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                int unused3 = result.typeId_ = this.x;
                int unused4 = result.bitField0_ = to_bitField0_;
                return result;
            }

            /* renamed from: p */
            public C0397b e(b other) {
                if (other == b.getDefaultInstance()) {
                    return this;
                }
                if (other.hasProjection()) {
                    s(other.getProjection());
                }
                if (other.hasType()) {
                    r(other.getType());
                }
                if (other.hasTypeId()) {
                    t(other.getTypeId());
                }
                f(d().b(other.unknownFields));
                return this;
            }

            public final boolean isInitialized() {
                if (!n() || m().isInitialized()) {
                    return true;
                }
                return false;
            }

            /* renamed from: q */
            public C0397b a(e input, f extensionRegistry) {
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

            public C0397b s(c value) {
                if (value != null) {
                    this.d |= 1;
                    this.f = value;
                    return this;
                }
                throw new NullPointerException();
            }

            public boolean n() {
                return (this.d & 2) == 2;
            }

            public q m() {
                return this.q;
            }

            public C0397b r(q value) {
                if ((this.d & 2) != 2 || this.q == q.getDefaultInstance()) {
                    this.q = value;
                } else {
                    this.q = q.newBuilder(this.q).e(value).o();
                }
                this.d |= 2;
                return this;
            }

            public C0397b t(int value) {
                this.d |= 4;
                this.x = value;
                return this;
            }
        }
    }

    public List<b> getArgumentList() {
        return this.argument_;
    }

    public int getArgumentCount() {
        return this.argument_.size();
    }

    public b getArgument(int index) {
        return this.argument_.get(index);
    }

    public boolean hasNullable() {
        return (this.bitField0_ & 1) == 1;
    }

    public boolean getNullable() {
        return this.nullable_;
    }

    public boolean hasFlexibleTypeCapabilitiesId() {
        return (this.bitField0_ & 2) == 2;
    }

    public int getFlexibleTypeCapabilitiesId() {
        return this.flexibleTypeCapabilitiesId_;
    }

    public boolean hasFlexibleUpperBound() {
        return (this.bitField0_ & 4) == 4;
    }

    public q getFlexibleUpperBound() {
        return this.flexibleUpperBound_;
    }

    public boolean hasFlexibleUpperBoundId() {
        return (this.bitField0_ & 8) == 8;
    }

    public int getFlexibleUpperBoundId() {
        return this.flexibleUpperBoundId_;
    }

    public boolean hasClassName() {
        return (this.bitField0_ & 16) == 16;
    }

    public int getClassName() {
        return this.className_;
    }

    public boolean hasTypeParameter() {
        return (this.bitField0_ & 32) == 32;
    }

    public int getTypeParameter() {
        return this.typeParameter_;
    }

    public boolean hasTypeParameterName() {
        return (this.bitField0_ & 64) == 64;
    }

    public int getTypeParameterName() {
        return this.typeParameterName_;
    }

    public boolean hasTypeAliasName() {
        return (this.bitField0_ & 128) == 128;
    }

    public int getTypeAliasName() {
        return this.typeAliasName_;
    }

    public boolean hasOuterType() {
        return (this.bitField0_ & 256) == 256;
    }

    public q getOuterType() {
        return this.outerType_;
    }

    public boolean hasOuterTypeId() {
        return (this.bitField0_ & 512) == 512;
    }

    public int getOuterTypeId() {
        return this.outerTypeId_;
    }

    public boolean hasAbbreviatedType() {
        return (this.bitField0_ & 1024) == 1024;
    }

    public q getAbbreviatedType() {
        return this.abbreviatedType_;
    }

    public boolean hasAbbreviatedTypeId() {
        return (this.bitField0_ & 2048) == 2048;
    }

    public int getAbbreviatedTypeId() {
        return this.abbreviatedTypeId_;
    }

    public boolean hasFlags() {
        return (this.bitField0_ & 4096) == 4096;
    }

    public int getFlags() {
        return this.flags_;
    }

    private void c() {
        this.argument_ = Collections.emptyList();
        this.nullable_ = false;
        this.flexibleTypeCapabilitiesId_ = 0;
        this.flexibleUpperBound_ = getDefaultInstance();
        this.flexibleUpperBoundId_ = 0;
        this.className_ = 0;
        this.typeParameter_ = 0;
        this.typeParameterName_ = 0;
        this.typeAliasName_ = 0;
        this.outerType_ = getDefaultInstance();
        this.outerTypeId_ = 0;
        this.abbreviatedType_ = getDefaultInstance();
        this.abbreviatedTypeId_ = 0;
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
        for (int i = 0; i < getArgumentCount(); i++) {
            if (!getArgument(i).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        if (hasFlexibleUpperBound() != 0 && !getFlexibleUpperBound().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
        } else if (hasOuterType() && !getOuterType().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
        } else if (hasAbbreviatedType() && !getAbbreviatedType().isInitialized()) {
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
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.Type>.ExtensionWriter extensionWriter = newExtensionWriter();
        if ((this.bitField0_ & 4096) == 4096) {
            output.a0(1, this.flags_);
        }
        for (int i = 0; i < this.argument_.size(); i++) {
            output.d0(2, this.argument_.get(i));
        }
        if ((this.bitField0_ & 1) == 1) {
            output.L(3, this.nullable_);
        }
        if ((this.bitField0_ & 2) == 2) {
            output.a0(4, this.flexibleTypeCapabilitiesId_);
        }
        if ((this.bitField0_ & 4) == 4) {
            output.d0(5, this.flexibleUpperBound_);
        }
        if ((this.bitField0_ & 16) == 16) {
            output.a0(6, this.className_);
        }
        if ((this.bitField0_ & 32) == 32) {
            output.a0(7, this.typeParameter_);
        }
        if ((this.bitField0_ & 8) == 8) {
            output.a0(8, this.flexibleUpperBoundId_);
        }
        if ((this.bitField0_ & 64) == 64) {
            output.a0(9, this.typeParameterName_);
        }
        if ((this.bitField0_ & 256) == 256) {
            output.d0(10, this.outerType_);
        }
        if ((this.bitField0_ & 512) == 512) {
            output.a0(11, this.outerTypeId_);
        }
        if ((this.bitField0_ & 128) == 128) {
            output.a0(12, this.typeAliasName_);
        }
        if ((this.bitField0_ & 1024) == 1024) {
            output.d0(13, this.abbreviatedType_);
        }
        if ((this.bitField0_ & 2048) == 2048) {
            output.a0(14, this.abbreviatedTypeId_);
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
        if ((this.bitField0_ & 4096) == 4096) {
            size2 = 0 + CodedOutputStream.o(1, this.flags_);
        }
        for (int i = 0; i < this.argument_.size(); i++) {
            size2 += CodedOutputStream.s(2, this.argument_.get(i));
        }
        if ((this.bitField0_ & 1) == 1) {
            size2 += CodedOutputStream.a(3, this.nullable_);
        }
        if ((this.bitField0_ & 2) == 2) {
            size2 += CodedOutputStream.o(4, this.flexibleTypeCapabilitiesId_);
        }
        if ((this.bitField0_ & 4) == 4) {
            size2 += CodedOutputStream.s(5, this.flexibleUpperBound_);
        }
        if ((this.bitField0_ & 16) == 16) {
            size2 += CodedOutputStream.o(6, this.className_);
        }
        if ((this.bitField0_ & 32) == 32) {
            size2 += CodedOutputStream.o(7, this.typeParameter_);
        }
        if ((this.bitField0_ & 8) == 8) {
            size2 += CodedOutputStream.o(8, this.flexibleUpperBoundId_);
        }
        if ((this.bitField0_ & 64) == 64) {
            size2 += CodedOutputStream.o(9, this.typeParameterName_);
        }
        if ((this.bitField0_ & 256) == 256) {
            size2 += CodedOutputStream.s(10, this.outerType_);
        }
        if ((this.bitField0_ & 512) == 512) {
            size2 += CodedOutputStream.o(11, this.outerTypeId_);
        }
        if ((this.bitField0_ & 128) == 128) {
            size2 += CodedOutputStream.o(12, this.typeAliasName_);
        }
        if ((this.bitField0_ & 1024) == 1024) {
            size2 += CodedOutputStream.s(13, this.abbreviatedType_);
        }
        if ((this.bitField0_ & 2048) == 2048) {
            size2 += CodedOutputStream.o(14, this.abbreviatedTypeId_);
        }
        int size3 = size2 + extensionsSerializedSize() + this.unknownFields.size();
        this.memoizedSerializedSize = size3;
        return size3;
    }

    public static c newBuilder() {
        return c.q();
    }

    public c newBuilderForType() {
        return newBuilder();
    }

    public static c newBuilder(q prototype) {
        return newBuilder().e(prototype);
    }

    public c toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class c extends h.c<q, c> implements p {
        private q A4 = q.getDefaultInstance();
        private int B4;
        private int C4;
        private int a1;
        private int a2;
        private q p0 = q.getDefaultInstance();
        private int p1;
        private int p2;
        private int p3;
        private q p4 = q.getDefaultInstance();
        private int q;
        private List<b> x = Collections.emptyList();
        private boolean y;
        private int z;
        private int z4;

        private c() {
            B();
        }

        private void B() {
        }

        /* access modifiers changed from: private */
        public static c q() {
            return new c();
        }

        /* renamed from: p */
        public c clone() {
            return q().e(o());
        }

        /* renamed from: n */
        public q build() {
            q result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public q o() {
            q result = new q((h.c) this);
            int from_bitField0_ = this.q;
            int to_bitField0_ = 0;
            if ((this.q & 1) == 1) {
                this.x = Collections.unmodifiableList(this.x);
                this.q &= -2;
            }
            List unused = result.argument_ = this.x;
            if ((from_bitField0_ & 2) == 2) {
                to_bitField0_ = 0 | 1;
            }
            boolean unused2 = result.nullable_ = this.y;
            if ((from_bitField0_ & 4) == 4) {
                to_bitField0_ |= 2;
            }
            int unused3 = result.flexibleTypeCapabilitiesId_ = this.z;
            if ((from_bitField0_ & 8) == 8) {
                to_bitField0_ |= 4;
            }
            q unused4 = result.flexibleUpperBound_ = this.p0;
            if ((from_bitField0_ & 16) == 16) {
                to_bitField0_ |= 8;
            }
            int unused5 = result.flexibleUpperBoundId_ = this.a1;
            if ((from_bitField0_ & 32) == 32) {
                to_bitField0_ |= 16;
            }
            int unused6 = result.className_ = this.p1;
            if ((from_bitField0_ & 64) == 64) {
                to_bitField0_ |= 32;
            }
            int unused7 = result.typeParameter_ = this.a2;
            if ((from_bitField0_ & 128) == 128) {
                to_bitField0_ |= 64;
            }
            int unused8 = result.typeParameterName_ = this.p2;
            if ((from_bitField0_ & 256) == 256) {
                to_bitField0_ |= 128;
            }
            int unused9 = result.typeAliasName_ = this.p3;
            if ((from_bitField0_ & 512) == 512) {
                to_bitField0_ |= 256;
            }
            q unused10 = result.outerType_ = this.p4;
            if ((from_bitField0_ & 1024) == 1024) {
                to_bitField0_ |= 512;
            }
            int unused11 = result.outerTypeId_ = this.z4;
            if ((from_bitField0_ & 2048) == 2048) {
                to_bitField0_ |= 1024;
            }
            q unused12 = result.abbreviatedType_ = this.A4;
            if ((from_bitField0_ & 4096) == 4096) {
                to_bitField0_ |= 2048;
            }
            int unused13 = result.abbreviatedTypeId_ = this.B4;
            if ((from_bitField0_ & 8192) == 8192) {
                to_bitField0_ |= 4096;
            }
            int unused14 = result.flags_ = this.C4;
            int unused15 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: F */
        public c e(q other) {
            if (other == q.getDefaultInstance()) {
                return this;
            }
            if (!other.argument_.isEmpty()) {
                if (this.x.isEmpty()) {
                    this.x = other.argument_;
                    this.q &= -2;
                } else {
                    r();
                    this.x.addAll(other.argument_);
                }
            }
            if (other.hasNullable()) {
                O(other.getNullable());
            }
            if (other.hasFlexibleTypeCapabilitiesId()) {
                M(other.getFlexibleTypeCapabilitiesId());
            }
            if (other.hasFlexibleUpperBound()) {
                D(other.getFlexibleUpperBound());
            }
            if (other.hasFlexibleUpperBoundId()) {
                N(other.getFlexibleUpperBoundId());
            }
            if (other.hasClassName()) {
                K(other.getClassName());
            }
            if (other.hasTypeParameter()) {
                S(other.getTypeParameter());
            }
            if (other.hasTypeParameterName()) {
                U(other.getTypeParameterName());
            }
            if (other.hasTypeAliasName()) {
                R(other.getTypeAliasName());
            }
            if (other.hasOuterType()) {
                H(other.getOuterType());
            }
            if (other.hasOuterTypeId()) {
                Q(other.getOuterTypeId());
            }
            if (other.hasAbbreviatedType()) {
                C(other.getAbbreviatedType());
            }
            if (other.hasAbbreviatedTypeId()) {
                I(other.getAbbreviatedTypeId());
            }
            if (other.hasFlags()) {
                L(other.getFlags());
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            for (int i = 0; i < u(); i++) {
                if (!t(i).isInitialized()) {
                    return false;
                }
            }
            if (z() != 0 && !v().isInitialized()) {
                return false;
            }
            if (A() && !w().isInitialized()) {
                return false;
            }
            if ((!y() || s().isInitialized()) && k()) {
                return true;
            }
            return false;
        }

        /* renamed from: G */
        public c a(e input, f extensionRegistry) {
            try {
                q parsedMessage = q.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                q parsedMessage2 = (q) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((q) null);
                }
                throw th;
            }
        }

        private void r() {
            if ((this.q & 1) != 1) {
                this.x = new ArrayList(this.x);
                this.q |= 1;
            }
        }

        public int u() {
            return this.x.size();
        }

        public b t(int index) {
            return this.x.get(index);
        }

        public c O(boolean value) {
            this.q |= 2;
            this.y = value;
            return this;
        }

        public c M(int value) {
            this.q |= 4;
            this.z = value;
            return this;
        }

        public boolean z() {
            return (this.q & 8) == 8;
        }

        public q v() {
            return this.p0;
        }

        public c D(q value) {
            if ((this.q & 8) != 8 || this.p0 == q.getDefaultInstance()) {
                this.p0 = value;
            } else {
                this.p0 = q.newBuilder(this.p0).e(value).o();
            }
            this.q |= 8;
            return this;
        }

        public c N(int value) {
            this.q |= 16;
            this.a1 = value;
            return this;
        }

        public c K(int value) {
            this.q |= 32;
            this.p1 = value;
            return this;
        }

        public c S(int value) {
            this.q |= 64;
            this.a2 = value;
            return this;
        }

        public c U(int value) {
            this.q |= 128;
            this.p2 = value;
            return this;
        }

        public c R(int value) {
            this.q |= 256;
            this.p3 = value;
            return this;
        }

        public boolean A() {
            return (this.q & 512) == 512;
        }

        public q w() {
            return this.p4;
        }

        public c H(q value) {
            if ((this.q & 512) != 512 || this.p4 == q.getDefaultInstance()) {
                this.p4 = value;
            } else {
                this.p4 = q.newBuilder(this.p4).e(value).o();
            }
            this.q |= 512;
            return this;
        }

        public c Q(int value) {
            this.q |= 1024;
            this.z4 = value;
            return this;
        }

        public boolean y() {
            return (this.q & 2048) == 2048;
        }

        public q s() {
            return this.A4;
        }

        public c C(q value) {
            if ((this.q & 2048) != 2048 || this.A4 == q.getDefaultInstance()) {
                this.A4 = value;
            } else {
                this.A4 = q.newBuilder(this.A4).e(value).o();
            }
            this.q |= 2048;
            return this;
        }

        public c I(int value) {
            this.q |= 4096;
            this.B4 = value;
            return this;
        }

        public c L(int value) {
            this.q |= 8192;
            this.C4 = value;
            return this;
        }
    }
}
