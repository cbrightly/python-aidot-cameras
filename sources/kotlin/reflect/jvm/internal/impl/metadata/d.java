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
import kotlin.reflect.jvm.internal.impl.protobuf.p;
import kotlin.reflect.jvm.internal.impl.protobuf.q;

/* compiled from: ProtoBuf */
public final class d extends h.d<d> implements p {
    public static q<d> PARSER = new a();
    private static final d c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int flags_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.protobuf.d unknownFields;
    /* access modifiers changed from: private */
    public List<u> valueParameter_;
    /* access modifiers changed from: private */
    public List<Integer> versionRequirement_;

    private d(h.c<d, ?> builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private d(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = kotlin.reflect.jvm.internal.impl.protobuf.d.c;
    }

    public static d getDefaultInstance() {
        return c;
    }

    public d getDefaultInstanceForType() {
        return c;
    }

    private d(e input, f extensionRegistry) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        c();
        int mutable_bitField0_ = 0;
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
                        this.flags_ = input.s();
                        break;
                    case 18:
                        if ((mutable_bitField0_ & 2) != 2) {
                            this.valueParameter_ = new ArrayList();
                            mutable_bitField0_ |= 2;
                        }
                        this.valueParameter_.add(input.u(u.PARSER, extensionRegistry));
                        break;
                    case 248:
                        if ((mutable_bitField0_ & 4) != 4) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 4;
                        }
                        this.versionRequirement_.add(Integer.valueOf(input.s()));
                        break;
                    case 250:
                        int limit = input.j(input.A());
                        if ((mutable_bitField0_ & 4) != 4 && input.e() > 0) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 4;
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
                if ((mutable_bitField0_ & 2) == 2) {
                    this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
                }
                if ((mutable_bitField0_ & 4) == 4) {
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
        if ((mutable_bitField0_ & 2) == 2) {
            this.valueParameter_ = Collections.unmodifiableList(this.valueParameter_);
        }
        if ((mutable_bitField0_ & 4) == 4) {
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<d> {
        a() {
        }

        /* renamed from: m */
        public d c(e input, f extensionRegistry) {
            return new d(input, extensionRegistry);
        }
    }

    static {
        d dVar = new d(true);
        c = dVar;
        dVar.c();
    }

    public q<d> getParserForType() {
        return PARSER;
    }

    public boolean hasFlags() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getFlags() {
        return this.flags_;
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

    public List<Integer> getVersionRequirementList() {
        return this.versionRequirement_;
    }

    private void c() {
        this.flags_ = 6;
        this.valueParameter_ = Collections.emptyList();
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
        for (int i = 0; i < getValueParameterCount(); i++) {
            if (!getValueParameter(i).isInitialized()) {
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
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.Constructor>.ExtensionWriter extensionWriter = newExtensionWriter();
        if ((this.bitField0_ & 1) == 1) {
            output.a0(1, this.flags_);
        }
        for (int i = 0; i < this.valueParameter_.size(); i++) {
            output.d0(2, this.valueParameter_.get(i));
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
        if ((this.bitField0_ & 1) == 1) {
            size2 = 0 + CodedOutputStream.o(1, this.flags_);
        }
        for (int i = 0; i < this.valueParameter_.size(); i++) {
            size2 += CodedOutputStream.s(2, this.valueParameter_.get(i));
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

    public static b newBuilder(d prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<d, b> implements p {
        private int q;
        private int x = 6;
        private List<u> y = Collections.emptyList();
        private List<Integer> z = Collections.emptyList();

        private b() {
            v();
        }

        private void v() {
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
        public d build() {
            d result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public d o() {
            d result = new d((h.c) this);
            int to_bitField0_ = 0;
            if ((this.q & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            int unused = result.flags_ = this.x;
            if ((this.q & 2) == 2) {
                this.y = Collections.unmodifiableList(this.y);
                this.q &= -3;
            }
            List unused2 = result.valueParameter_ = this.y;
            if ((this.q & 4) == 4) {
                this.z = Collections.unmodifiableList(this.z);
                this.q &= -5;
            }
            List unused3 = result.versionRequirement_ = this.z;
            int unused4 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: w */
        public b e(d other) {
            if (other == d.getDefaultInstance()) {
                return this;
            }
            if (other.hasFlags()) {
                z(other.getFlags());
            }
            if (!other.valueParameter_.isEmpty()) {
                if (this.y.isEmpty()) {
                    this.y = other.valueParameter_;
                    this.q &= -3;
                } else {
                    r();
                    this.y.addAll(other.valueParameter_);
                }
            }
            if (!other.versionRequirement_.isEmpty()) {
                if (this.z.isEmpty()) {
                    this.z = other.versionRequirement_;
                    this.q &= -5;
                } else {
                    s();
                    this.z.addAll(other.versionRequirement_);
                }
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
            if (k() == 0) {
                return false;
            }
            return true;
        }

        /* renamed from: y */
        public b a(e input, f extensionRegistry) {
            try {
                d parsedMessage = d.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                d parsedMessage2 = (d) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((d) null);
                }
                throw th;
            }
        }

        public b z(int value) {
            this.q |= 1;
            this.x = value;
            return this;
        }

        private void r() {
            if ((this.q & 2) != 2) {
                this.y = new ArrayList(this.y);
                this.q |= 2;
            }
        }

        public int u() {
            return this.y.size();
        }

        public u t(int index) {
            return this.y.get(index);
        }

        private void s() {
            if ((this.q & 4) != 4) {
                this.z = new ArrayList(this.z);
                this.q |= 4;
            }
        }
    }
}
