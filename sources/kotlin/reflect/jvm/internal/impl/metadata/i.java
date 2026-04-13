package kotlin.reflect.jvm.internal.impl.metadata;

import com.luck.picture.lib.camera.CustomCameraView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.e;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.t;
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
public final class i extends h.d<i> implements p {
    public static q<i> PARSER = new a();
    private static final i c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public e contract_;
    /* access modifiers changed from: private */
    public int flags_;
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
    public List<s> typeParameter_;
    /* access modifiers changed from: private */
    public t typeTable_;
    /* access modifiers changed from: private */
    public final d unknownFields;
    /* access modifiers changed from: private */
    public List<u> valueParameter_;
    /* access modifiers changed from: private */
    public List<Integer> versionRequirement_;

    private i(h.c<i, ?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private i(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static i getDefaultInstance() {
        return c;
    }

    public i getDefaultInstanceForType() {
        return c;
    }

    private i(e input, f extensionRegistry) {
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
                        if ((mutable_bitField0_ & 256) != 256) {
                            this.valueParameter_ = new ArrayList();
                            mutable_bitField0_ |= 256;
                        }
                        this.valueParameter_.add(input.u(u.PARSER, extensionRegistry));
                        break;
                    case 56:
                        this.bitField0_ |= 16;
                        this.returnTypeId_ = input.s();
                        break;
                    case 64:
                        this.bitField0_ |= 64;
                        this.receiverTypeId_ = input.s();
                        break;
                    case 72:
                        this.bitField0_ |= 1;
                        this.flags_ = input.s();
                        break;
                    case 242:
                        t.b subBuilder3 = (this.bitField0_ & 128) == 128 ? this.typeTable_.toBuilder() : null;
                        t tVar = (t) input.u(t.PARSER, extensionRegistry);
                        this.typeTable_ = tVar;
                        if (subBuilder3 != null) {
                            subBuilder3.e(tVar);
                            this.typeTable_ = subBuilder3.j();
                        }
                        this.bitField0_ |= 128;
                        break;
                    case 248:
                        if ((mutable_bitField0_ & 1024) != 1024) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 1024;
                        }
                        this.versionRequirement_.add(Integer.valueOf(input.s()));
                        break;
                    case 250:
                        int limit = input.j(input.A());
                        if ((mutable_bitField0_ & 1024) != 1024 && input.e() > 0) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 1024;
                        }
                        while (input.e() > 0) {
                            this.versionRequirement_.add(Integer.valueOf(input.s()));
                        }
                        input.i(limit);
                        break;
                    case CustomCameraView.BUTTON_STATE_ONLY_RECORDER:
                        e.b subBuilder4 = (this.bitField0_ & 256) == 256 ? this.contract_.toBuilder() : null;
                        e eVar = (e) input.u(e.PARSER, extensionRegistry);
                        this.contract_ = eVar;
                        if (subBuilder4 != null) {
                            subBuilder4.e(eVar);
                            this.contract_ = subBuilder4.j();
                        }
                        this.bitField0_ |= 256;
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
                if ((mutable_bitField0_ & 256) == 256) {
                    this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                }
                if ((mutable_bitField0_ & 1024) == 1024) {
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
        if ((mutable_bitField0_ & 256) == 256) {
            this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
        }
        if ((mutable_bitField0_ & 1024) == 1024) {
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<i> {
        a() {
        }

        /* renamed from: m */
        public i c(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
            return new i(input, extensionRegistry);
        }
    }

    static {
        i iVar = new i(true);
        c = iVar;
        iVar.c();
    }

    public kotlin.reflect.jvm.internal.impl.protobuf.q<i> getParserForType() {
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

    public List<u> getValueParameterList() {
        return this.valueParameter_;
    }

    public int getValueParameterCount() {
        return this.valueParameter_.size();
    }

    public u getValueParameter(int index) {
        return this.valueParameter_.get(index);
    }

    public boolean hasTypeTable() {
        return (this.bitField0_ & 128) == 128;
    }

    public t getTypeTable() {
        return this.typeTable_;
    }

    public List<Integer> getVersionRequirementList() {
        return this.versionRequirement_;
    }

    public boolean hasContract() {
        return (this.bitField0_ & 256) == 256;
    }

    public e getContract() {
        return this.contract_;
    }

    private void c() {
        this.flags_ = 6;
        this.oldFlags_ = 6;
        this.name_ = 0;
        this.returnType_ = q.getDefaultInstance();
        this.returnTypeId_ = 0;
        this.typeParameter_ = Collections.emptyList();
        this.receiverType_ = q.getDefaultInstance();
        this.receiverTypeId_ = 0;
        this.valueParameter_ = Collections.emptyList();
        this.typeTable_ = t.getDefaultInstance();
        this.versionRequirement_ = Collections.emptyList();
        this.contract_ = e.getDefaultInstance();
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
            if (hasReceiverType() == 0 || getReceiverType().isInitialized()) {
                for (int i2 = 0; i2 < getValueParameterCount(); i2++) {
                    if (!getValueParameter(i2).isInitialized()) {
                        this.memoizedIsInitialized = 0;
                        return false;
                    }
                }
                if (hasTypeTable() != 0 && !getTypeTable().isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                } else if (hasContract() && !getContract().isInitialized()) {
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
        } else {
            this.memoizedIsInitialized = 0;
            return false;
        }
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.Function>.ExtensionWriter extensionWriter = newExtensionWriter();
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
        for (int i2 = 0; i2 < this.valueParameter_.size(); i2++) {
            output.d0(6, this.valueParameter_.get(i2));
        }
        if ((this.bitField0_ & 16) == 16) {
            output.a0(7, this.returnTypeId_);
        }
        if ((this.bitField0_ & 64) == 64) {
            output.a0(8, this.receiverTypeId_);
        }
        if ((this.bitField0_ & 1) == 1) {
            output.a0(9, this.flags_);
        }
        if ((this.bitField0_ & 128) == 128) {
            output.d0(30, this.typeTable_);
        }
        for (int i3 = 0; i3 < this.versionRequirement_.size(); i3++) {
            output.a0(31, this.versionRequirement_.get(i3).intValue());
        }
        if ((this.bitField0_ & 256) == 256) {
            output.d0(32, this.contract_);
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
        for (int i2 = 0; i2 < this.valueParameter_.size(); i2++) {
            size2 += CodedOutputStream.s(6, this.valueParameter_.get(i2));
        }
        if ((this.bitField0_ & 16) == 16) {
            size2 += CodedOutputStream.o(7, this.returnTypeId_);
        }
        if ((this.bitField0_ & 64) == 64) {
            size2 += CodedOutputStream.o(8, this.receiverTypeId_);
        }
        if ((this.bitField0_ & 1) == 1) {
            size2 += CodedOutputStream.o(9, this.flags_);
        }
        if ((this.bitField0_ & 128) == 128) {
            size2 += CodedOutputStream.s(30, this.typeTable_);
        }
        int dataSize = 0;
        for (int i3 = 0; i3 < this.versionRequirement_.size(); i3++) {
            dataSize += CodedOutputStream.p(this.versionRequirement_.get(i3).intValue());
        }
        int size3 = size2 + dataSize + (getVersionRequirementList().size() * 2);
        if ((this.bitField0_ & 256) == 256) {
            size3 += CodedOutputStream.s(32, this.contract_);
        }
        int size4 = size3 + extensionsSerializedSize() + this.unknownFields.size();
        this.memoizedSerializedSize = size4;
        return size4;
    }

    public static i parseFrom(InputStream input, f extensionRegistry) {
        return PARSER.a(input, extensionRegistry);
    }

    public static b newBuilder() {
        return b.q();
    }

    public b newBuilderForType() {
        return newBuilder();
    }

    public static b newBuilder(i prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<i, b> implements p {
        private e A4 = e.getDefaultInstance();
        private int a1;
        private q a2 = q.getDefaultInstance();
        private q p0 = q.getDefaultInstance();
        private List<s> p1 = Collections.emptyList();
        private int p2;
        private List<u> p3 = Collections.emptyList();
        private t p4 = t.getDefaultInstance();
        private int q;
        private int x = 6;
        private int y = 6;
        private int z;
        private List<Integer> z4 = Collections.emptyList();

        private b() {
            K();
        }

        private void K() {
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
        public i build() {
            i result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public i o() {
            i result = new i((h.c) this);
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
            if ((this.q & 256) == 256) {
                this.p3 = Collections.unmodifiableList(this.p3);
                this.q &= -257;
            }
            List unused9 = result.valueParameter_ = this.p3;
            if ((from_bitField0_ & 512) == 512) {
                to_bitField0_ |= 128;
            }
            t unused10 = result.typeTable_ = this.p4;
            if ((this.q & 1024) == 1024) {
                this.z4 = Collections.unmodifiableList(this.z4);
                this.q &= -1025;
            }
            List unused11 = result.versionRequirement_ = this.z4;
            if ((from_bitField0_ & 2048) == 2048) {
                to_bitField0_ |= 256;
            }
            e unused12 = result.contract_ = this.A4;
            int unused13 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: M */
        public b e(i other) {
            if (other == i.getDefaultInstance()) {
                return this;
            }
            if (other.hasFlags()) {
                S(other.getFlags());
            }
            if (other.hasOldFlags()) {
                V(other.getOldFlags());
            }
            if (other.hasName()) {
                U(other.getName());
            }
            if (other.hasReturnType()) {
                Q(other.getReturnType());
            }
            if (other.hasReturnTypeId()) {
                Y(other.getReturnTypeId());
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
                O(other.getReceiverType());
            }
            if (other.hasReceiverTypeId()) {
                X(other.getReceiverTypeId());
            }
            if (!other.valueParameter_.isEmpty()) {
                if (this.p3.isEmpty()) {
                    this.p3 = other.valueParameter_;
                    this.q &= -257;
                } else {
                    s();
                    this.p3.addAll(other.valueParameter_);
                }
            }
            if (other.hasTypeTable()) {
                R(other.getTypeTable());
            }
            if (!other.versionRequirement_.isEmpty()) {
                if (this.z4.isEmpty()) {
                    this.z4 = other.versionRequirement_;
                    this.q &= -1025;
                } else {
                    t();
                    this.z4.addAll(other.versionRequirement_);
                }
            }
            if (other.hasContract()) {
                L(other.getContract());
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (!F()) {
                return false;
            }
            if (H() && !w().isInitialized()) {
                return false;
            }
            for (int i = 0; i < z(); i++) {
                if (!y(i).isInitialized()) {
                    return false;
                }
            }
            if (G() != 0 && !v().isInitialized()) {
                return false;
            }
            for (int i2 = 0; i2 < C(); i2++) {
                if (!B(i2).isInitialized()) {
                    return false;
                }
            }
            if (I() != 0 && !A().isInitialized()) {
                return false;
            }
            if ((!D() || u().isInitialized()) && k()) {
                return true;
            }
            return false;
        }

        /* renamed from: N */
        public b a(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
            try {
                i parsedMessage = i.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                i parsedMessage2 = (i) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((i) null);
                }
                throw th;
            }
        }

        public b S(int value) {
            this.q |= 1;
            this.x = value;
            return this;
        }

        public b V(int value) {
            this.q |= 2;
            this.y = value;
            return this;
        }

        public boolean F() {
            return (this.q & 4) == 4;
        }

        public b U(int value) {
            this.q |= 4;
            this.z = value;
            return this;
        }

        public boolean H() {
            return (this.q & 8) == 8;
        }

        public q w() {
            return this.p0;
        }

        public b Q(q value) {
            if ((this.q & 8) != 8 || this.p0 == q.getDefaultInstance()) {
                this.p0 = value;
            } else {
                this.p0 = q.newBuilder(this.p0).e(value).o();
            }
            this.q |= 8;
            return this;
        }

        public b Y(int value) {
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

        public int z() {
            return this.p1.size();
        }

        public s y(int index) {
            return this.p1.get(index);
        }

        public boolean G() {
            return (this.q & 64) == 64;
        }

        public q v() {
            return this.a2;
        }

        public b O(q value) {
            if ((this.q & 64) != 64 || this.a2 == q.getDefaultInstance()) {
                this.a2 = value;
            } else {
                this.a2 = q.newBuilder(this.a2).e(value).o();
            }
            this.q |= 64;
            return this;
        }

        public b X(int value) {
            this.q |= 128;
            this.p2 = value;
            return this;
        }

        private void s() {
            if ((this.q & 256) != 256) {
                this.p3 = new ArrayList(this.p3);
                this.q |= 256;
            }
        }

        public int C() {
            return this.p3.size();
        }

        public u B(int index) {
            return this.p3.get(index);
        }

        public boolean I() {
            return (this.q & 512) == 512;
        }

        public t A() {
            return this.p4;
        }

        public b R(t value) {
            if ((this.q & 512) != 512 || this.p4 == t.getDefaultInstance()) {
                this.p4 = value;
            } else {
                this.p4 = t.newBuilder(this.p4).e(value).j();
            }
            this.q |= 512;
            return this;
        }

        private void t() {
            if ((this.q & 1024) != 1024) {
                this.z4 = new ArrayList(this.z4);
                this.q |= 1024;
            }
        }

        public boolean D() {
            return (this.q & 2048) == 2048;
        }

        public e u() {
            return this.A4;
        }

        public b L(e value) {
            if ((this.q & 2048) != 2048 || this.A4 == e.getDefaultInstance()) {
                this.A4 = value;
            } else {
                this.A4 = e.newBuilder(this.A4).e(value).j();
            }
            this.q |= 2048;
            return this;
        }
    }
}
