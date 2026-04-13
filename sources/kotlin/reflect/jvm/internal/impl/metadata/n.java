package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.u;
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
public final class n extends h.d<n> implements p {
    public static q<n> PARSER = new a();
    private static final n c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int flags_;
    /* access modifiers changed from: private */
    public int getterFlags_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public int name_;
    /* access modifiers changed from: private */
    public int oldFlags_;
    /* access modifiers changed from: private */
    public int receiverTypeId_;
    /* access modifiers changed from: private */
    public q receiverType_;
    /* access modifiers changed from: private */
    public int returnTypeId_;
    /* access modifiers changed from: private */
    public q returnType_;
    /* access modifiers changed from: private */
    public int setterFlags_;
    /* access modifiers changed from: private */
    public u setterValueParameter_;
    /* access modifiers changed from: private */
    public List<s> typeParameter_;
    /* access modifiers changed from: private */
    public final d unknownFields;
    /* access modifiers changed from: private */
    public List<Integer> versionRequirement_;

    private n(h.c<n, ?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private n(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static n getDefaultInstance() {
        return c;
    }

    public n getDefaultInstanceForType() {
        return c;
    }

    private n(e input, f extensionRegistry) {
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
                        this.bitField0_ |= 2;
                        this.oldFlags_ = input.s();
                        break;
                    case 16:
                        this.bitField0_ |= 4;
                        this.name_ = input.s();
                        break;
                    case 26:
                        q.c subBuilder = (this.bitField0_ & 8) == 8 ? this.returnType_.toBuilder() : null;
                        q qVar = (q) input.u(q.PARSER, extensionRegistry);
                        this.returnType_ = qVar;
                        if (subBuilder != null) {
                            subBuilder.e(qVar);
                            this.returnType_ = subBuilder.o();
                        }
                        this.bitField0_ |= 8;
                        break;
                    case 34:
                        if ((mutable_bitField0_ & 32) != 32) {
                            this.typeParameter_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        this.typeParameter_.add(input.u(s.PARSER, extensionRegistry));
                        break;
                    case 42:
                        q.c subBuilder2 = (this.bitField0_ & 32) == 32 ? this.receiverType_.toBuilder() : null;
                        q qVar2 = (q) input.u(q.PARSER, extensionRegistry);
                        this.receiverType_ = qVar2;
                        if (subBuilder2 != null) {
                            subBuilder2.e(qVar2);
                            this.receiverType_ = subBuilder2.o();
                        }
                        this.bitField0_ |= 32;
                        break;
                    case 50:
                        u.b subBuilder3 = (this.bitField0_ & 128) == 128 ? this.setterValueParameter_.toBuilder() : null;
                        u uVar = (u) input.u(u.PARSER, extensionRegistry);
                        this.setterValueParameter_ = uVar;
                        if (subBuilder3 != null) {
                            subBuilder3.e(uVar);
                            this.setterValueParameter_ = subBuilder3.o();
                        }
                        this.bitField0_ |= 128;
                        break;
                    case 56:
                        this.bitField0_ |= 256;
                        this.getterFlags_ = input.s();
                        break;
                    case 64:
                        this.bitField0_ |= 512;
                        this.setterFlags_ = input.s();
                        break;
                    case 72:
                        this.bitField0_ |= 16;
                        this.returnTypeId_ = input.s();
                        break;
                    case 80:
                        this.bitField0_ |= 64;
                        this.receiverTypeId_ = input.s();
                        break;
                    case 88:
                        this.bitField0_ |= 1;
                        this.flags_ = input.s();
                        break;
                    case 248:
                        if ((mutable_bitField0_ & 2048) != 2048) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 2048;
                        }
                        this.versionRequirement_.add(Integer.valueOf(input.s()));
                        break;
                    case 250:
                        int limit = input.j(input.A());
                        if ((mutable_bitField0_ & 2048) != 2048 && input.e() > 0) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 2048;
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
                if ((mutable_bitField0_ & 32) == 32) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                }
                if ((mutable_bitField0_ & 2048) == 2048) {
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
        if ((mutable_bitField0_ & 32) == 32) {
            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
        }
        if ((mutable_bitField0_ & 2048) == 2048) {
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<n> {
        a() {
        }

        /* renamed from: m */
        public n c(e input, f extensionRegistry) {
            return new n(input, extensionRegistry);
        }
    }

    static {
        n nVar = new n(true);
        c = nVar;
        nVar.c();
    }

    public kotlin.reflect.jvm.internal.impl.protobuf.q<n> getParserForType() {
        return PARSER;
    }

    public boolean hasFlags() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getFlags() {
        return this.flags_;
    }

    public boolean hasOldFlags() {
        return (this.bitField0_ & 2) == 2;
    }

    public int getOldFlags() {
        return this.oldFlags_;
    }

    public boolean hasName() {
        return (this.bitField0_ & 4) == 4;
    }

    public int getName() {
        return this.name_;
    }

    public boolean hasReturnType() {
        return (this.bitField0_ & 8) == 8;
    }

    public q getReturnType() {
        return this.returnType_;
    }

    public boolean hasReturnTypeId() {
        return (this.bitField0_ & 16) == 16;
    }

    public int getReturnTypeId() {
        return this.returnTypeId_;
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

    public boolean hasReceiverType() {
        return (this.bitField0_ & 32) == 32;
    }

    public q getReceiverType() {
        return this.receiverType_;
    }

    public boolean hasReceiverTypeId() {
        return (this.bitField0_ & 64) == 64;
    }

    public int getReceiverTypeId() {
        return this.receiverTypeId_;
    }

    public boolean hasSetterValueParameter() {
        return (this.bitField0_ & 128) == 128;
    }

    public u getSetterValueParameter() {
        return this.setterValueParameter_;
    }

    public boolean hasGetterFlags() {
        return (this.bitField0_ & 256) == 256;
    }

    public int getGetterFlags() {
        return this.getterFlags_;
    }

    public boolean hasSetterFlags() {
        return (this.bitField0_ & 512) == 512;
    }

    public int getSetterFlags() {
        return this.setterFlags_;
    }

    public List<Integer> getVersionRequirementList() {
        return this.versionRequirement_;
    }

    private void c() {
        this.flags_ = 518;
        this.oldFlags_ = 2054;
        this.name_ = 0;
        this.returnType_ = q.getDefaultInstance();
        this.returnTypeId_ = 0;
        this.typeParameter_ = Collections.emptyList();
        this.receiverType_ = q.getDefaultInstance();
        this.receiverTypeId_ = 0;
        this.setterValueParameter_ = u.getDefaultInstance();
        this.getterFlags_ = 0;
        this.setterFlags_ = 0;
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
        } else if (!hasReturnType() || getReturnType().isInitialized()) {
            for (int i = 0; i < getTypeParameterCount(); i++) {
                if (!getTypeParameter(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            if (hasReceiverType() != 0 && !getReceiverType().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (hasSetterValueParameter() && !getSetterValueParameter().isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else if (!extensionsAreInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            } else {
                this.memoizedIsInitialized = 1;
                return true;
            }
        } else {
            this.memoizedIsInitialized = 0;
            return false;
        }
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.Property>.ExtensionWriter extensionWriter = newExtensionWriter();
        if ((this.bitField0_ & 2) == 2) {
            output.a0(1, this.oldFlags_);
        }
        if ((this.bitField0_ & 4) == 4) {
            output.a0(2, this.name_);
        }
        if ((this.bitField0_ & 8) == 8) {
            output.d0(3, this.returnType_);
        }
        for (int i = 0; i < this.typeParameter_.size(); i++) {
            output.d0(4, this.typeParameter_.get(i));
        }
        if ((this.bitField0_ & 32) == 32) {
            output.d0(5, this.receiverType_);
        }
        if ((this.bitField0_ & 128) == 128) {
            output.d0(6, this.setterValueParameter_);
        }
        if ((this.bitField0_ & 256) == 256) {
            output.a0(7, this.getterFlags_);
        }
        if ((this.bitField0_ & 512) == 512) {
            output.a0(8, this.setterFlags_);
        }
        if ((this.bitField0_ & 16) == 16) {
            output.a0(9, this.returnTypeId_);
        }
        if ((this.bitField0_ & 64) == 64) {
            output.a0(10, this.receiverTypeId_);
        }
        if ((this.bitField0_ & 1) == 1) {
            output.a0(11, this.flags_);
        }
        for (int i2 = 0; i2 < this.versionRequirement_.size(); i2++) {
            output.a0(31, this.versionRequirement_.get(i2).intValue());
        }
        extensionWriter.a(19000, output);
        output.i0(this.unknownFields);
    }

    public int getSerializedSize() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        if ((this.bitField0_ & 2) == 2) {
            size2 = 0 + CodedOutputStream.o(1, this.oldFlags_);
        }
        if ((this.bitField0_ & 4) == 4) {
            size2 += CodedOutputStream.o(2, this.name_);
        }
        if ((this.bitField0_ & 8) == 8) {
            size2 += CodedOutputStream.s(3, this.returnType_);
        }
        for (int i = 0; i < this.typeParameter_.size(); i++) {
            size2 += CodedOutputStream.s(4, this.typeParameter_.get(i));
        }
        if ((this.bitField0_ & 32) == 32) {
            size2 += CodedOutputStream.s(5, this.receiverType_);
        }
        if ((this.bitField0_ & 128) == 128) {
            size2 += CodedOutputStream.s(6, this.setterValueParameter_);
        }
        if ((this.bitField0_ & 256) == 256) {
            size2 += CodedOutputStream.o(7, this.getterFlags_);
        }
        if ((this.bitField0_ & 512) == 512) {
            size2 += CodedOutputStream.o(8, this.setterFlags_);
        }
        if ((this.bitField0_ & 16) == 16) {
            size2 += CodedOutputStream.o(9, this.returnTypeId_);
        }
        if ((this.bitField0_ & 64) == 64) {
            size2 += CodedOutputStream.o(10, this.receiverTypeId_);
        }
        if ((this.bitField0_ & 1) == 1) {
            size2 += CodedOutputStream.o(11, this.flags_);
        }
        int dataSize = 0;
        for (int i2 = 0; i2 < this.versionRequirement_.size(); i2++) {
            dataSize += CodedOutputStream.p(this.versionRequirement_.get(i2).intValue());
        }
        int size3 = size2 + dataSize + (getVersionRequirementList().size() * 2) + extensionsSerializedSize() + this.unknownFields.size();
        this.memoizedSerializedSize = size3;
        return size3;
    }

    public static b newBuilder() {
        return b.q();
    }

    public b newBuilderForType() {
        return newBuilder();
    }

    public static b newBuilder(n prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<n, b> implements p {
        private List<Integer> A4 = Collections.emptyList();
        private int a1;
        private q a2 = q.getDefaultInstance();
        private q p0 = q.getDefaultInstance();
        private List<s> p1 = Collections.emptyList();
        private int p2;
        private u p3 = u.getDefaultInstance();
        private int p4;
        private int q;
        private int x = 518;
        private int y = 2054;
        private int z;
        private int z4;

        private b() {
            D();
        }

        private void D() {
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
        public n build() {
            n result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public n o() {
            n result = new n((h.c) this);
            int from_bitField0_ = this.q;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            int unused = result.flags_ = this.x;
            if ((from_bitField0_ & 2) == 2) {
                to_bitField0_ |= 2;
            }
            int unused2 = result.oldFlags_ = this.y;
            if ((from_bitField0_ & 4) == 4) {
                to_bitField0_ |= 4;
            }
            int unused3 = result.name_ = this.z;
            if ((from_bitField0_ & 8) == 8) {
                to_bitField0_ |= 8;
            }
            q unused4 = result.returnType_ = this.p0;
            if ((from_bitField0_ & 16) == 16) {
                to_bitField0_ |= 16;
            }
            int unused5 = result.returnTypeId_ = this.a1;
            if ((this.q & 32) == 32) {
                this.p1 = Collections.unmodifiableList(this.p1);
                this.q &= -33;
            }
            List unused6 = result.typeParameter_ = this.p1;
            if ((from_bitField0_ & 64) == 64) {
                to_bitField0_ |= 32;
            }
            q unused7 = result.receiverType_ = this.a2;
            if ((from_bitField0_ & 128) == 128) {
                to_bitField0_ |= 64;
            }
            int unused8 = result.receiverTypeId_ = this.p2;
            if ((from_bitField0_ & 256) == 256) {
                to_bitField0_ |= 128;
            }
            u unused9 = result.setterValueParameter_ = this.p3;
            if ((from_bitField0_ & 512) == 512) {
                to_bitField0_ |= 256;
            }
            int unused10 = result.getterFlags_ = this.p4;
            if ((from_bitField0_ & 1024) == 1024) {
                to_bitField0_ |= 512;
            }
            int unused11 = result.setterFlags_ = this.z4;
            if ((this.q & 2048) == 2048) {
                this.A4 = Collections.unmodifiableList(this.A4);
                this.q &= -2049;
            }
            List unused12 = result.versionRequirement_ = this.A4;
            int unused13 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: F */
        public b e(n other) {
            if (other == n.getDefaultInstance()) {
                return this;
            }
            if (other.hasFlags()) {
                L(other.getFlags());
            }
            if (other.hasOldFlags()) {
                O(other.getOldFlags());
            }
            if (other.hasName()) {
                N(other.getName());
            }
            if (other.hasReturnType()) {
                I(other.getReturnType());
            }
            if (other.hasReturnTypeId()) {
                R(other.getReturnTypeId());
            }
            if (!other.typeParameter_.isEmpty()) {
                if (this.p1.isEmpty()) {
                    this.p1 = other.typeParameter_;
                    this.q &= -33;
                } else {
                    r();
                    this.p1.addAll(other.typeParameter_);
                }
            }
            if (other.hasReceiverType()) {
                H(other.getReceiverType());
            }
            if (other.hasReceiverTypeId()) {
                Q(other.getReceiverTypeId());
            }
            if (other.hasSetterValueParameter()) {
                K(other.getSetterValueParameter());
            }
            if (other.hasGetterFlags()) {
                M(other.getGetterFlags());
            }
            if (other.hasSetterFlags()) {
                S(other.getSetterFlags());
            }
            if (!other.versionRequirement_.isEmpty()) {
                if (this.A4.isEmpty()) {
                    this.A4 = other.versionRequirement_;
                    this.q &= -2049;
                } else {
                    s();
                    this.A4.addAll(other.versionRequirement_);
                }
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (!z()) {
                return false;
            }
            if (B() && !u().isInitialized()) {
                return false;
            }
            for (int i = 0; i < y(); i++) {
                if (!w(i).isInitialized()) {
                    return false;
                }
            }
            if (A() != 0 && !t().isInitialized()) {
                return false;
            }
            if ((!C() || v().isInitialized()) && k()) {
                return true;
            }
            return false;
        }

        /* renamed from: G */
        public b a(e input, f extensionRegistry) {
            try {
                n parsedMessage = n.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                n parsedMessage2 = (n) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((n) null);
                }
                throw th;
            }
        }

        public b L(int value) {
            this.q |= 1;
            this.x = value;
            return this;
        }

        public b O(int value) {
            this.q |= 2;
            this.y = value;
            return this;
        }

        public boolean z() {
            return (this.q & 4) == 4;
        }

        public b N(int value) {
            this.q |= 4;
            this.z = value;
            return this;
        }

        public boolean B() {
            return (this.q & 8) == 8;
        }

        public q u() {
            return this.p0;
        }

        public b I(q value) {
            if ((this.q & 8) != 8 || this.p0 == q.getDefaultInstance()) {
                this.p0 = value;
            } else {
                this.p0 = q.newBuilder(this.p0).e(value).o();
            }
            this.q |= 8;
            return this;
        }

        public b R(int value) {
            this.q |= 16;
            this.a1 = value;
            return this;
        }

        private void r() {
            if ((this.q & 32) != 32) {
                this.p1 = new ArrayList(this.p1);
                this.q |= 32;
            }
        }

        public int y() {
            return this.p1.size();
        }

        public s w(int index) {
            return this.p1.get(index);
        }

        public boolean A() {
            return (this.q & 64) == 64;
        }

        public q t() {
            return this.a2;
        }

        public b H(q value) {
            if ((this.q & 64) != 64 || this.a2 == q.getDefaultInstance()) {
                this.a2 = value;
            } else {
                this.a2 = q.newBuilder(this.a2).e(value).o();
            }
            this.q |= 64;
            return this;
        }

        public b Q(int value) {
            this.q |= 128;
            this.p2 = value;
            return this;
        }

        public boolean C() {
            return (this.q & 256) == 256;
        }

        public u v() {
            return this.p3;
        }

        public b K(u value) {
            if ((this.q & 256) != 256 || this.p3 == u.getDefaultInstance()) {
                this.p3 = value;
            } else {
                this.p3 = u.newBuilder(this.p3).e(value).o();
            }
            this.q |= 256;
            return this;
        }

        public b M(int value) {
            this.q |= 512;
            this.p4 = value;
            return this;
        }

        public b S(int value) {
            this.q |= 1024;
            this.z4 = value;
            return this;
        }

        private void s() {
            if ((this.q & 2048) != 2048) {
                this.A4 = new ArrayList(this.A4);
                this.q |= 2048;
            }
        }
    }
}
