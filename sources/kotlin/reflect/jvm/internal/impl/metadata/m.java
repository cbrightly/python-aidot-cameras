package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.metadata.l;
import kotlin.reflect.jvm.internal.impl.metadata.o;
import kotlin.reflect.jvm.internal.impl.metadata.p;
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
public final class m extends h.d<m> implements p {
    public static q<m> PARSER = new a();
    private static final m c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public List<c> class__;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public l package_;
    /* access modifiers changed from: private */
    public o qualifiedNames_;
    /* access modifiers changed from: private */
    public p strings_;
    /* access modifiers changed from: private */
    public final d unknownFields;

    private m(h.c<m, ?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private m(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static m getDefaultInstance() {
        return c;
    }

    public m getDefaultInstanceForType() {
        return c;
    }

    private m(e input, f extensionRegistry) {
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
                    case 10:
                        p.b subBuilder = (this.bitField0_ & 1) == 1 ? this.strings_.toBuilder() : null;
                        p pVar = (p) input.u(p.PARSER, extensionRegistry);
                        this.strings_ = pVar;
                        if (subBuilder != null) {
                            subBuilder.e(pVar);
                            this.strings_ = subBuilder.j();
                        }
                        this.bitField0_ |= 1;
                        break;
                    case 18:
                        o.b subBuilder2 = (this.bitField0_ & 2) == 2 ? this.qualifiedNames_.toBuilder() : null;
                        o oVar = (o) input.u(o.PARSER, extensionRegistry);
                        this.qualifiedNames_ = oVar;
                        if (subBuilder2 != null) {
                            subBuilder2.e(oVar);
                            this.qualifiedNames_ = subBuilder2.j();
                        }
                        this.bitField0_ |= 2;
                        break;
                    case 26:
                        l.b subBuilder3 = (this.bitField0_ & 4) == 4 ? this.package_.toBuilder() : null;
                        l lVar = (l) input.u(l.PARSER, extensionRegistry);
                        this.package_ = lVar;
                        if (subBuilder3 != null) {
                            subBuilder3.e(lVar);
                            this.package_ = subBuilder3.o();
                        }
                        this.bitField0_ |= 4;
                        break;
                    case 34:
                        if ((mutable_bitField0_ & 8) != 8) {
                            this.class__ = new ArrayList();
                            mutable_bitField0_ |= 8;
                        }
                        this.class__.add(input.u(c.PARSER, extensionRegistry));
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
                if ((mutable_bitField0_ & 8) == 8) {
                    this.class__ = Collections.unmodifiableList(this.class__);
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
        if ((mutable_bitField0_ & 8) == 8) {
            this.class__ = Collections.unmodifiableList(this.class__);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<m> {
        a() {
        }

        /* renamed from: m */
        public m c(e input, f extensionRegistry) {
            return new m(input, extensionRegistry);
        }
    }

    static {
        m mVar = new m(true);
        c = mVar;
        mVar.c();
    }

    public q<m> getParserForType() {
        return PARSER;
    }

    public boolean hasStrings() {
        return (this.bitField0_ & 1) == 1;
    }

    public p getStrings() {
        return this.strings_;
    }

    public boolean hasQualifiedNames() {
        return (this.bitField0_ & 2) == 2;
    }

    public o getQualifiedNames() {
        return this.qualifiedNames_;
    }

    public boolean hasPackage() {
        return (this.bitField0_ & 4) == 4;
    }

    public l getPackage() {
        return this.package_;
    }

    public List<c> getClass_List() {
        return this.class__;
    }

    public int getClass_Count() {
        return this.class__.size();
    }

    public c getClass_(int index) {
        return this.class__.get(index);
    }

    private void c() {
        this.strings_ = p.getDefaultInstance();
        this.qualifiedNames_ = o.getDefaultInstance();
        this.package_ = l.getDefaultInstance();
        this.class__ = Collections.emptyList();
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        if (hasQualifiedNames() && !getQualifiedNames().isInitialized()) {
            this.memoizedIsInitialized = 0;
            return false;
        } else if (!hasPackage() || getPackage().isInitialized()) {
            for (int i = 0; i < getClass_Count(); i++) {
                if (!getClass_(i).isInitialized()) {
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
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.PackageFragment>.ExtensionWriter extensionWriter = newExtensionWriter();
        if ((this.bitField0_ & 1) == 1) {
            output.d0(1, this.strings_);
        }
        if ((this.bitField0_ & 2) == 2) {
            output.d0(2, this.qualifiedNames_);
        }
        if ((this.bitField0_ & 4) == 4) {
            output.d0(3, this.package_);
        }
        for (int i = 0; i < this.class__.size(); i++) {
            output.d0(4, this.class__.get(i));
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
            size2 = 0 + CodedOutputStream.s(1, this.strings_);
        }
        if ((this.bitField0_ & 2) == 2) {
            size2 += CodedOutputStream.s(2, this.qualifiedNames_);
        }
        if ((this.bitField0_ & 4) == 4) {
            size2 += CodedOutputStream.s(3, this.package_);
        }
        for (int i = 0; i < this.class__.size(); i++) {
            size2 += CodedOutputStream.s(4, this.class__.get(i));
        }
        int size3 = size2 + extensionsSerializedSize() + this.unknownFields.size();
        this.memoizedSerializedSize = size3;
        return size3;
    }

    public static m parseFrom(InputStream input, f extensionRegistry) {
        return PARSER.a(input, extensionRegistry);
    }

    public static b newBuilder() {
        return b.q();
    }

    public b newBuilderForType() {
        return newBuilder();
    }

    public static b newBuilder(m prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<m, b> implements kotlin.reflect.jvm.internal.impl.protobuf.p {
        private List<c> p0 = Collections.emptyList();
        private int q;
        private p x = p.getDefaultInstance();
        private o y = o.getDefaultInstance();
        private l z = l.getDefaultInstance();

        private b() {
            z();
        }

        private void z() {
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
        public m build() {
            m result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public m o() {
            m result = new m((h.c) this);
            int from_bitField0_ = this.q;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            p unused = result.strings_ = this.x;
            if ((from_bitField0_ & 2) == 2) {
                to_bitField0_ |= 2;
            }
            o unused2 = result.qualifiedNames_ = this.y;
            if ((from_bitField0_ & 4) == 4) {
                to_bitField0_ |= 4;
            }
            l unused3 = result.package_ = this.z;
            if ((this.q & 8) == 8) {
                this.p0 = Collections.unmodifiableList(this.p0);
                this.q &= -9;
            }
            List unused4 = result.class__ = this.p0;
            int unused5 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: A */
        public b e(m other) {
            if (other == m.getDefaultInstance()) {
                return this;
            }
            if (other.hasStrings()) {
                F(other.getStrings());
            }
            if (other.hasQualifiedNames()) {
                D(other.getQualifiedNames());
            }
            if (other.hasPackage()) {
                C(other.getPackage());
            }
            if (!other.class__.isEmpty()) {
                if (this.p0.isEmpty()) {
                    this.p0 = other.class__;
                    this.q &= -9;
                } else {
                    r();
                    this.p0.addAll(other.class__);
                }
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (y() && !v().isInitialized()) {
                return false;
            }
            if (w() && !u().isInitialized()) {
                return false;
            }
            for (int i = 0; i < t(); i++) {
                if (!s(i).isInitialized()) {
                    return false;
                }
            }
            if (k() == 0) {
                return false;
            }
            return true;
        }

        /* renamed from: B */
        public b a(e input, f extensionRegistry) {
            try {
                m parsedMessage = m.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                m parsedMessage2 = (m) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((m) null);
                }
                throw th;
            }
        }

        public b F(p value) {
            if ((this.q & 1) != 1 || this.x == p.getDefaultInstance()) {
                this.x = value;
            } else {
                this.x = p.newBuilder(this.x).e(value).j();
            }
            this.q |= 1;
            return this;
        }

        public boolean y() {
            return (this.q & 2) == 2;
        }

        public o v() {
            return this.y;
        }

        public b D(o value) {
            if ((this.q & 2) != 2 || this.y == o.getDefaultInstance()) {
                this.y = value;
            } else {
                this.y = o.newBuilder(this.y).e(value).j();
            }
            this.q |= 2;
            return this;
        }

        public boolean w() {
            return (this.q & 4) == 4;
        }

        public l u() {
            return this.z;
        }

        public b C(l value) {
            if ((this.q & 4) != 4 || this.z == l.getDefaultInstance()) {
                this.z = value;
            } else {
                this.z = l.newBuilder(this.z).e(value).o();
            }
            this.q |= 4;
            return this;
        }

        private void r() {
            if ((this.q & 8) != 8) {
                this.p0 = new ArrayList(this.p0);
                this.q |= 8;
            }
        }

        public int t() {
            return this.p0.size();
        }

        public c s(int index) {
            return this.p0.get(index);
        }
    }
}
