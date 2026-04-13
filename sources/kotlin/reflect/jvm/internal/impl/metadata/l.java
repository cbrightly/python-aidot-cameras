package kotlin.reflect.jvm.internal.impl.metadata;

import com.luck.picture.lib.camera.CustomCameraView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.t;
import kotlin.reflect.jvm.internal.impl.metadata.w;
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
public final class l extends h.d<l> implements p {
    public static q<l> PARSER = new a();
    private static final l c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public List<i> function_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public List<n> property_;
    /* access modifiers changed from: private */
    public List<r> typeAlias_;
    /* access modifiers changed from: private */
    public t typeTable_;
    /* access modifiers changed from: private */
    public final d unknownFields;
    /* access modifiers changed from: private */
    public w versionRequirementTable_;

    private l(h.c<l, ?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private l(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static l getDefaultInstance() {
        return c;
    }

    public l getDefaultInstanceForType() {
        return c;
    }

    private l(e input, f extensionRegistry) {
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
                    case 26:
                        if ((mutable_bitField0_ & 1) != 1) {
                            this.function_ = new ArrayList();
                            mutable_bitField0_ |= 1;
                        }
                        this.function_.add(input.u(i.PARSER, extensionRegistry));
                        break;
                    case 34:
                        if ((mutable_bitField0_ & 2) != 2) {
                            this.property_ = new ArrayList();
                            mutable_bitField0_ |= 2;
                        }
                        this.property_.add(input.u(n.PARSER, extensionRegistry));
                        break;
                    case 42:
                        if ((mutable_bitField0_ & 4) != 4) {
                            this.typeAlias_ = new ArrayList();
                            mutable_bitField0_ |= 4;
                        }
                        this.typeAlias_.add(input.u(r.PARSER, extensionRegistry));
                        break;
                    case 242:
                        t.b subBuilder = (this.bitField0_ & 1) == 1 ? this.typeTable_.toBuilder() : null;
                        t tVar = (t) input.u(t.PARSER, extensionRegistry);
                        this.typeTable_ = tVar;
                        if (subBuilder != null) {
                            subBuilder.e(tVar);
                            this.typeTable_ = subBuilder.j();
                        }
                        this.bitField0_ |= 1;
                        break;
                    case CustomCameraView.BUTTON_STATE_ONLY_RECORDER:
                        w.b subBuilder2 = (this.bitField0_ & 2) == 2 ? this.versionRequirementTable_.toBuilder() : null;
                        w wVar = (w) input.u(w.PARSER, extensionRegistry);
                        this.versionRequirementTable_ = wVar;
                        if (subBuilder2 != null) {
                            subBuilder2.e(wVar);
                            this.versionRequirementTable_ = subBuilder2.j();
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
                if ((mutable_bitField0_ & 1) == 1) {
                    this.function_ = Collections.unmodifiableList(this.function_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.property_ = Collections.unmodifiableList(this.property_);
                }
                if ((mutable_bitField0_ & 4) == 4) {
                    this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
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
            this.function_ = Collections.unmodifiableList(this.function_);
        }
        if ((mutable_bitField0_ & 2) == 2) {
            this.property_ = Collections.unmodifiableList(this.property_);
        }
        if ((mutable_bitField0_ & 4) == 4) {
            this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<l> {
        a() {
        }

        /* renamed from: m */
        public l c(e input, f extensionRegistry) {
            return new l(input, extensionRegistry);
        }
    }

    static {
        l lVar = new l(true);
        c = lVar;
        lVar.c();
    }

    public q<l> getParserForType() {
        return PARSER;
    }

    public List<i> getFunctionList() {
        return this.function_;
    }

    public int getFunctionCount() {
        return this.function_.size();
    }

    public i getFunction(int index) {
        return this.function_.get(index);
    }

    public List<n> getPropertyList() {
        return this.property_;
    }

    public int getPropertyCount() {
        return this.property_.size();
    }

    public n getProperty(int index) {
        return this.property_.get(index);
    }

    public List<r> getTypeAliasList() {
        return this.typeAlias_;
    }

    public int getTypeAliasCount() {
        return this.typeAlias_.size();
    }

    public r getTypeAlias(int index) {
        return this.typeAlias_.get(index);
    }

    public boolean hasTypeTable() {
        return (this.bitField0_ & 1) == 1;
    }

    public t getTypeTable() {
        return this.typeTable_;
    }

    public boolean hasVersionRequirementTable() {
        return (this.bitField0_ & 2) == 2;
    }

    public w getVersionRequirementTable() {
        return this.versionRequirementTable_;
    }

    private void c() {
        this.function_ = Collections.emptyList();
        this.property_ = Collections.emptyList();
        this.typeAlias_ = Collections.emptyList();
        this.typeTable_ = t.getDefaultInstance();
        this.versionRequirementTable_ = w.getDefaultInstance();
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        for (int i = 0; i < getFunctionCount(); i++) {
            if (!getFunction(i).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        for (int i2 = 0; i2 < getPropertyCount(); i2++) {
            if (!getProperty(i2).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        for (int i3 = 0; i3 < getTypeAliasCount(); i3++) {
            if (!getTypeAlias(i3).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        if (hasTypeTable() != 0 && !getTypeTable().isInitialized()) {
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
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.Package>.ExtensionWriter extensionWriter = newExtensionWriter();
        for (int i = 0; i < this.function_.size(); i++) {
            output.d0(3, this.function_.get(i));
        }
        for (int i2 = 0; i2 < this.property_.size(); i2++) {
            output.d0(4, this.property_.get(i2));
        }
        for (int i3 = 0; i3 < this.typeAlias_.size(); i3++) {
            output.d0(5, this.typeAlias_.get(i3));
        }
        if ((this.bitField0_ & 1) == 1) {
            output.d0(30, this.typeTable_);
        }
        if ((this.bitField0_ & 2) == 2) {
            output.d0(32, this.versionRequirementTable_);
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
        for (int i = 0; i < this.function_.size(); i++) {
            size2 += CodedOutputStream.s(3, this.function_.get(i));
        }
        for (int i2 = 0; i2 < this.property_.size(); i2++) {
            size2 += CodedOutputStream.s(4, this.property_.get(i2));
        }
        for (int i3 = 0; i3 < this.typeAlias_.size(); i3++) {
            size2 += CodedOutputStream.s(5, this.typeAlias_.get(i3));
        }
        if ((this.bitField0_ & 1) == 1) {
            size2 += CodedOutputStream.s(30, this.typeTable_);
        }
        if ((this.bitField0_ & 2) == 2) {
            size2 += CodedOutputStream.s(32, this.versionRequirementTable_);
        }
        int size3 = size2 + extensionsSerializedSize() + this.unknownFields.size();
        this.memoizedSerializedSize = size3;
        return size3;
    }

    public static l parseFrom(InputStream input, f extensionRegistry) {
        return PARSER.a(input, extensionRegistry);
    }

    public static b newBuilder() {
        return b.q();
    }

    public b newBuilderForType() {
        return newBuilder();
    }

    public static b newBuilder(l prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<l, b> implements p {
        private w a1 = w.getDefaultInstance();
        private t p0 = t.getDefaultInstance();
        private int q;
        private List<i> x = Collections.emptyList();
        private List<n> y = Collections.emptyList();
        private List<r> z = Collections.emptyList();

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
        public l build() {
            l result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public l o() {
            l result = new l((h.c) this);
            int from_bitField0_ = this.q;
            int to_bitField0_ = 0;
            if ((this.q & 1) == 1) {
                this.x = Collections.unmodifiableList(this.x);
                this.q &= -2;
            }
            List unused = result.function_ = this.x;
            if ((this.q & 2) == 2) {
                this.y = Collections.unmodifiableList(this.y);
                this.q &= -3;
            }
            List unused2 = result.property_ = this.y;
            if ((this.q & 4) == 4) {
                this.z = Collections.unmodifiableList(this.z);
                this.q &= -5;
            }
            List unused3 = result.typeAlias_ = this.z;
            if ((from_bitField0_ & 8) == 8) {
                to_bitField0_ = 0 | 1;
            }
            t unused4 = result.typeTable_ = this.p0;
            if ((from_bitField0_ & 16) == 16) {
                to_bitField0_ |= 2;
            }
            w unused5 = result.versionRequirementTable_ = this.a1;
            int unused6 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: F */
        public b e(l other) {
            if (other == l.getDefaultInstance()) {
                return this;
            }
            if (!other.function_.isEmpty()) {
                if (this.x.isEmpty()) {
                    this.x = other.function_;
                    this.q &= -2;
                } else {
                    r();
                    this.x.addAll(other.function_);
                }
            }
            if (!other.property_.isEmpty()) {
                if (this.y.isEmpty()) {
                    this.y = other.property_;
                    this.q &= -3;
                } else {
                    s();
                    this.y.addAll(other.property_);
                }
            }
            if (!other.typeAlias_.isEmpty()) {
                if (this.z.isEmpty()) {
                    this.z = other.typeAlias_;
                    this.q &= -5;
                } else {
                    t();
                    this.z.addAll(other.typeAlias_);
                }
            }
            if (other.hasTypeTable()) {
                H(other.getTypeTable());
            }
            if (other.hasVersionRequirementTable()) {
                I(other.getVersionRequirementTable());
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            for (int i = 0; i < v(); i++) {
                if (!u(i).isInitialized()) {
                    return false;
                }
            }
            for (int i2 = 0; i2 < y(); i2++) {
                if (!w(i2).isInitialized()) {
                    return false;
                }
            }
            for (int i3 = 0; i3 < A(); i3++) {
                if (!z(i3).isInitialized()) {
                    return false;
                }
            }
            if ((C() == 0 || B().isInitialized()) && k()) {
                return true;
            }
            return false;
        }

        /* renamed from: G */
        public b a(e input, f extensionRegistry) {
            try {
                l parsedMessage = l.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                l parsedMessage2 = (l) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((l) null);
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

        public int v() {
            return this.x.size();
        }

        public i u(int index) {
            return this.x.get(index);
        }

        private void s() {
            if ((this.q & 2) != 2) {
                this.y = new ArrayList(this.y);
                this.q |= 2;
            }
        }

        public int y() {
            return this.y.size();
        }

        public n w(int index) {
            return this.y.get(index);
        }

        private void t() {
            if ((this.q & 4) != 4) {
                this.z = new ArrayList(this.z);
                this.q |= 4;
            }
        }

        public int A() {
            return this.z.size();
        }

        public r z(int index) {
            return this.z.get(index);
        }

        public boolean C() {
            return (this.q & 8) == 8;
        }

        public t B() {
            return this.p0;
        }

        public b H(t value) {
            if ((this.q & 8) != 8 || this.p0 == t.getDefaultInstance()) {
                this.p0 = value;
            } else {
                this.p0 = t.newBuilder(this.p0).e(value).j();
            }
            this.q |= 8;
            return this;
        }

        public b I(w value) {
            if ((this.q & 16) != 16 || this.a1 == w.getDefaultInstance()) {
                this.a1 = value;
            } else {
                this.a1 = w.newBuilder(this.a1).e(value).j();
            }
            this.q |= 16;
            return this;
        }
    }
}
