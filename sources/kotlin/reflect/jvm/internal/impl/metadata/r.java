package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public final class r extends h.d<r> implements p {
    public static q<r> PARSER = new a();
    private static final r c;
    /* access modifiers changed from: private */
    public List<b> annotation_;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int expandedTypeId_;
    /* access modifiers changed from: private */
    public q expandedType_;
    /* access modifiers changed from: private */
    public int flags_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public int name_;
    /* access modifiers changed from: private */
    public List<s> typeParameter_;
    /* access modifiers changed from: private */
    public int underlyingTypeId_;
    /* access modifiers changed from: private */
    public q underlyingType_;
    /* access modifiers changed from: private */
    public final d unknownFields;
    /* access modifiers changed from: private */
    public List<Integer> versionRequirement_;

    private r(h.c<r, ?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private r(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static r getDefaultInstance() {
        return c;
    }

    public r getDefaultInstanceForType() {
        return c;
    }

    private r(e input, f extensionRegistry) {
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
                        this.bitField0_ |= 1;
                        this.flags_ = input.s();
                        break;
                    case 16:
                        this.bitField0_ |= 2;
                        this.name_ = input.s();
                        break;
                    case 26:
                        if ((mutable_bitField0_ & 4) != 4) {
                            this.typeParameter_ = new ArrayList();
                            mutable_bitField0_ |= 4;
                        }
                        this.typeParameter_.add(input.u(s.PARSER, extensionRegistry));
                        break;
                    case 34:
                        q.c subBuilder = (this.bitField0_ & 4) == 4 ? this.underlyingType_.toBuilder() : null;
                        q qVar = (q) input.u(q.PARSER, extensionRegistry);
                        this.underlyingType_ = qVar;
                        if (subBuilder != null) {
                            subBuilder.e(qVar);
                            this.underlyingType_ = subBuilder.o();
                        }
                        this.bitField0_ |= 4;
                        break;
                    case 40:
                        this.bitField0_ |= 8;
                        this.underlyingTypeId_ = input.s();
                        break;
                    case 50:
                        q.c subBuilder2 = (this.bitField0_ & 16) == 16 ? this.expandedType_.toBuilder() : null;
                        q qVar2 = (q) input.u(q.PARSER, extensionRegistry);
                        this.expandedType_ = qVar2;
                        if (subBuilder2 != null) {
                            subBuilder2.e(qVar2);
                            this.expandedType_ = subBuilder2.o();
                        }
                        this.bitField0_ |= 16;
                        break;
                    case 56:
                        this.bitField0_ |= 32;
                        this.expandedTypeId_ = input.s();
                        break;
                    case 66:
                        if ((mutable_bitField0_ & 128) != 128) {
                            this.annotation_ = new ArrayList();
                            mutable_bitField0_ |= 128;
                        }
                        this.annotation_.add(input.u(b.PARSER, extensionRegistry));
                        break;
                    case 248:
                        if ((mutable_bitField0_ & 256) != 256) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 256;
                        }
                        this.versionRequirement_.add(Integer.valueOf(input.s()));
                        break;
                    case 250:
                        int limit = input.j(input.A());
                        if ((mutable_bitField0_ & 256) != 256 && input.e() > 0) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 256;
                        }
                        while (input.e() > 0) {
                            this.versionRequirement_.add(Integer.valueOf(input.s()));
                        }
                        input.i(limit);
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
                if ((mutable_bitField0_ & 4) == 4) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                }
                if ((mutable_bitField0_ & 128) == 128) {
                    this.annotation_ = Collections.unmodifiableList(this.annotation_);
                }
                if ((mutable_bitField0_ & 256) == 256) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
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
        if ((mutable_bitField0_ & 4) == 4) {
            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
        }
        if ((mutable_bitField0_ & 128) == 128) {
            this.annotation_ = Collections.unmodifiableList(this.annotation_);
        }
        if ((mutable_bitField0_ & 256) == 256) {
            this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<r> {
        a() {
        }

        /* renamed from: m */
        public r c(e input, f extensionRegistry) {
            return new r(input, extensionRegistry);
        }
    }

    static {
        r rVar = new r(true);
        c = rVar;
        rVar.c();
    }

    public kotlin.reflect.jvm.internal.impl.protobuf.q<r> getParserForType() {
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

    public List<s> getTypeParameterList() {
        return this.typeParameter_;
    }

    public int getTypeParameterCount() {
        return this.typeParameter_.size();
    }

    public s getTypeParameter(int index) {
        return this.typeParameter_.get(index);
    }

    public boolean hasUnderlyingType() {
        return (this.bitField0_ & 4) == 4;
    }

    public q getUnderlyingType() {
        return this.underlyingType_;
    }

    public boolean hasUnderlyingTypeId() {
        return (this.bitField0_ & 8) == 8;
    }

    public int getUnderlyingTypeId() {
        return this.underlyingTypeId_;
    }

    public boolean hasExpandedType() {
        return (this.bitField0_ & 16) == 16;
    }

    public q getExpandedType() {
        return this.expandedType_;
    }

    public boolean hasExpandedTypeId() {
        return (this.bitField0_ & 32) == 32;
    }

    public int getExpandedTypeId() {
        return this.expandedTypeId_;
    }

    public List<b> getAnnotationList() {
        return this.annotation_;
    }

    public int getAnnotationCount() {
        return this.annotation_.size();
    }

    public b getAnnotation(int index) {
        return this.annotation_.get(index);
    }

    public List<Integer> getVersionRequirementList() {
        return this.versionRequirement_;
    }

    private void c() {
        this.flags_ = 6;
        this.name_ = 0;
        this.typeParameter_ = Collections.emptyList();
        this.underlyingType_ = q.getDefaultInstance();
        this.underlyingTypeId_ = 0;
        this.expandedType_ = q.getDefaultInstance();
        this.expandedTypeId_ = 0;
        this.annotation_ = Collections.emptyList();
        this.versionRequirement_ = Collections.emptyList();
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
        }
        for (int i = 0; i < getTypeParameterCount(); i++) {
            if (!getTypeParameter(i).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        if (hasUnderlyingType() != 0 && !getUnderlyingType().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
        } else if (!hasExpandedType() || getExpandedType().isInitialized()) {
            for (int i2 = 0; i2 < getAnnotationCount(); i2++) {
                if (!getAnnotation(i2).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            if (extensionsAreInitialized() == 0) {
                this.memoizedIsInitialized = 0;
                return false;
            }
            this.memoizedIsInitialized = 1;
            return true;
        } else {
            this.memoizedIsInitialized = 0;
            return false;
        }
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.TypeAlias>.ExtensionWriter extensionWriter = newExtensionWriter();
        if ((this.bitField0_ & 1) == 1) {
            output.a0(1, this.flags_);
        }
        if ((this.bitField0_ & 2) == 2) {
            output.a0(2, this.name_);
        }
        for (int i = 0; i < this.typeParameter_.size(); i++) {
            output.d0(3, this.typeParameter_.get(i));
        }
        if ((this.bitField0_ & 4) == 4) {
            output.d0(4, this.underlyingType_);
        }
        if ((this.bitField0_ & 8) == 8) {
            output.a0(5, this.underlyingTypeId_);
        }
        if ((this.bitField0_ & 16) == 16) {
            output.d0(6, this.expandedType_);
        }
        if ((this.bitField0_ & 32) == 32) {
            output.a0(7, this.expandedTypeId_);
        }
        for (int i2 = 0; i2 < this.annotation_.size(); i2++) {
            output.d0(8, this.annotation_.get(i2));
        }
        for (int i3 = 0; i3 < this.versionRequirement_.size(); i3++) {
            output.a0(31, this.versionRequirement_.get(i3).intValue());
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
        for (int i = 0; i < this.typeParameter_.size(); i++) {
            size2 += CodedOutputStream.s(3, this.typeParameter_.get(i));
        }
        if ((this.bitField0_ & 4) == 4) {
            size2 += CodedOutputStream.s(4, this.underlyingType_);
        }
        if ((this.bitField0_ & 8) == 8) {
            size2 += CodedOutputStream.o(5, this.underlyingTypeId_);
        }
        if ((this.bitField0_ & 16) == 16) {
            size2 += CodedOutputStream.s(6, this.expandedType_);
        }
        if ((this.bitField0_ & 32) == 32) {
            size2 += CodedOutputStream.o(7, this.expandedTypeId_);
        }
        for (int i2 = 0; i2 < this.annotation_.size(); i2++) {
            size2 += CodedOutputStream.s(8, this.annotation_.get(i2));
        }
        int dataSize = 0;
        for (int i3 = 0; i3 < this.versionRequirement_.size(); i3++) {
            dataSize += CodedOutputStream.p(this.versionRequirement_.get(i3).intValue());
        }
        int size3 = size2 + dataSize + (getVersionRequirementList().size() * 2) + extensionsSerializedSize() + this.unknownFields.size();
        this.memoizedSerializedSize = size3;
        return size3;
    }

    public static r parseDelimitedFrom(InputStream input, f extensionRegistry) {
        return PARSER.d(input, extensionRegistry);
    }

    public static b newBuilder() {
        return b.q();
    }

    public b newBuilderForType() {
        return newBuilder();
    }

    public static b newBuilder(r prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<r, b> implements p {
        private int a1;
        private int a2;
        private q p0 = q.getDefaultInstance();
        private q p1 = q.getDefaultInstance();
        private List<b> p2 = Collections.emptyList();
        private List<Integer> p3 = Collections.emptyList();
        private int q;
        private int x = 6;
        private int y;
        private List<s> z = Collections.emptyList();

        private b() {
            F();
        }

        private void F() {
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
        public r build() {
            r result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public r o() {
            r result = new r((h.c) this);
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
            if ((this.q & 4) == 4) {
                this.z = Collections.unmodifiableList(this.z);
                this.q &= -5;
            }
            List unused3 = result.typeParameter_ = this.z;
            if ((from_bitField0_ & 8) == 8) {
                to_bitField0_ |= 4;
            }
            q unused4 = result.underlyingType_ = this.p0;
            if ((from_bitField0_ & 16) == 16) {
                to_bitField0_ |= 8;
            }
            int unused5 = result.underlyingTypeId_ = this.a1;
            if ((from_bitField0_ & 32) == 32) {
                to_bitField0_ |= 16;
            }
            q unused6 = result.expandedType_ = this.p1;
            if ((from_bitField0_ & 64) == 64) {
                to_bitField0_ |= 32;
            }
            int unused7 = result.expandedTypeId_ = this.a2;
            if ((this.q & 128) == 128) {
                this.p2 = Collections.unmodifiableList(this.p2);
                this.q &= -129;
            }
            List unused8 = result.annotation_ = this.p2;
            if ((this.q & 256) == 256) {
                this.p3 = Collections.unmodifiableList(this.p3);
                this.q &= -257;
            }
            List unused9 = result.versionRequirement_ = this.p3;
            int unused10 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: H */
        public b e(r other) {
            if (other == r.getDefaultInstance()) {
                return this;
            }
            if (other.hasFlags()) {
                M(other.getFlags());
            }
            if (other.hasName()) {
                N(other.getName());
            }
            if (!other.typeParameter_.isEmpty()) {
                if (this.z.isEmpty()) {
                    this.z = other.typeParameter_;
                    this.q &= -5;
                } else {
                    s();
                    this.z.addAll(other.typeParameter_);
                }
            }
            if (other.hasUnderlyingType()) {
                K(other.getUnderlyingType());
            }
            if (other.hasUnderlyingTypeId()) {
                O(other.getUnderlyingTypeId());
            }
            if (other.hasExpandedType()) {
                G(other.getExpandedType());
            }
            if (other.hasExpandedTypeId()) {
                L(other.getExpandedTypeId());
            }
            if (!other.annotation_.isEmpty()) {
                if (this.p2.isEmpty()) {
                    this.p2 = other.annotation_;
                    this.q &= -129;
                } else {
                    r();
                    this.p2.addAll(other.annotation_);
                }
            }
            if (!other.versionRequirement_.isEmpty()) {
                if (this.p3.isEmpty()) {
                    this.p3 = other.versionRequirement_;
                    this.q &= -257;
                } else {
                    t();
                    this.p3.addAll(other.versionRequirement_);
                }
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (!C()) {
                return false;
            }
            for (int i = 0; i < z(); i++) {
                if (!y(i).isInitialized()) {
                    return false;
                }
            }
            if (D() != 0 && !A().isInitialized()) {
                return false;
            }
            if (B() && !w().isInitialized()) {
                return false;
            }
            for (int i2 = 0; i2 < v(); i2++) {
                if (!u(i2).isInitialized()) {
                    return false;
                }
            }
            if (k() == 0) {
                return false;
            }
            return true;
        }

        /* renamed from: I */
        public b a(e input, f extensionRegistry) {
            try {
                r parsedMessage = r.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                r parsedMessage2 = (r) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((r) null);
                }
                throw th;
            }
        }

        public b M(int value) {
            this.q |= 1;
            this.x = value;
            return this;
        }

        public boolean C() {
            return (this.q & 2) == 2;
        }

        public b N(int value) {
            this.q |= 2;
            this.y = value;
            return this;
        }

        private void s() {
            if ((this.q & 4) != 4) {
                this.z = new ArrayList(this.z);
                this.q |= 4;
            }
        }

        public int z() {
            return this.z.size();
        }

        public s y(int index) {
            return this.z.get(index);
        }

        public boolean D() {
            return (this.q & 8) == 8;
        }

        public q A() {
            return this.p0;
        }

        public b K(q value) {
            if ((this.q & 8) != 8 || this.p0 == q.getDefaultInstance()) {
                this.p0 = value;
            } else {
                this.p0 = q.newBuilder(this.p0).e(value).o();
            }
            this.q |= 8;
            return this;
        }

        public b O(int value) {
            this.q |= 16;
            this.a1 = value;
            return this;
        }

        public boolean B() {
            return (this.q & 32) == 32;
        }

        public q w() {
            return this.p1;
        }

        public b G(q value) {
            if ((this.q & 32) != 32 || this.p1 == q.getDefaultInstance()) {
                this.p1 = value;
            } else {
                this.p1 = q.newBuilder(this.p1).e(value).o();
            }
            this.q |= 32;
            return this;
        }

        public b L(int value) {
            this.q |= 64;
            this.a2 = value;
            return this;
        }

        private void r() {
            if ((this.q & 128) != 128) {
                this.p2 = new ArrayList(this.p2);
                this.q |= 128;
            }
        }

        public int v() {
            return this.p2.size();
        }

        public b u(int index) {
            return this.p2.get(index);
        }

        private void t() {
            if ((this.q & 256) != 256) {
                this.p3 = new ArrayList(this.p3);
                this.q |= 256;
            }
        }
    }
}
