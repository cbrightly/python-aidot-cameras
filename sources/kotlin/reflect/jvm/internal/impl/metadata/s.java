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
import kotlin.reflect.jvm.internal.impl.protobuf.q;

/* compiled from: ProtoBuf */
public final class s extends h.d<s> implements p {
    public static q<s> PARSER = new a();
    private static final s c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int id_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public int name_;
    /* access modifiers changed from: private */
    public boolean reified_;
    /* access modifiers changed from: private */
    public final d unknownFields;
    private int upperBoundIdMemoizedSerializedSize;
    /* access modifiers changed from: private */
    public List<Integer> upperBoundId_;
    /* access modifiers changed from: private */
    public List<q> upperBound_;
    /* access modifiers changed from: private */
    public c variance_;

    private s(h.c<s, ?> builder) {
        super(builder);
        this.upperBoundIdMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private s(boolean noInit) {
        this.upperBoundIdMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static s getDefaultInstance() {
        return c;
    }

    public s getDefaultInstanceForType() {
        return c;
    }

    private s(e input, f extensionRegistry) {
        this.upperBoundIdMemoizedSerializedSize = -1;
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
                        this.id_ = input.s();
                        break;
                    case 16:
                        this.bitField0_ |= 2;
                        this.name_ = input.s();
                        break;
                    case 24:
                        this.bitField0_ |= 4;
                        this.reified_ = input.k();
                        break;
                    case 32:
                        int rawValue = input.n();
                        c value = c.valueOf(rawValue);
                        if (value != null) {
                            this.bitField0_ |= 8;
                            this.variance_ = value;
                            break;
                        } else {
                            unknownFieldsCodedOutput.o0(tag);
                            unknownFieldsCodedOutput.o0(rawValue);
                            break;
                        }
                    case 42:
                        if ((mutable_bitField0_ & 16) != 16) {
                            this.upperBound_ = new ArrayList();
                            mutable_bitField0_ |= 16;
                        }
                        this.upperBound_.add(input.u(q.PARSER, extensionRegistry));
                        break;
                    case 48:
                        if ((mutable_bitField0_ & 32) != 32) {
                            this.upperBoundId_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        this.upperBoundId_.add(Integer.valueOf(input.s()));
                        break;
                    case 50:
                        int limit = input.j(input.A());
                        if ((mutable_bitField0_ & 32) != 32 && input.e() > 0) {
                            this.upperBoundId_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        while (input.e() > 0) {
                            this.upperBoundId_.add(Integer.valueOf(input.s()));
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
                if ((mutable_bitField0_ & 16) == 16) {
                    this.upperBound_ = Collections.unmodifiableList(this.upperBound_);
                }
                if ((mutable_bitField0_ & 32) == 32) {
                    this.upperBoundId_ = Collections.unmodifiableList(this.upperBoundId_);
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
        if ((mutable_bitField0_ & 16) == 16) {
            this.upperBound_ = Collections.unmodifiableList(this.upperBound_);
        }
        if ((mutable_bitField0_ & 32) == 32) {
            this.upperBoundId_ = Collections.unmodifiableList(this.upperBoundId_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<s> {
        a() {
        }

        /* renamed from: m */
        public s c(e input, f extensionRegistry) {
            return new s(input, extensionRegistry);
        }
    }

    static {
        s sVar = new s(true);
        c = sVar;
        sVar.c();
    }

    public q<s> getParserForType() {
        return PARSER;
    }

    /* compiled from: ProtoBuf */
    public enum c implements i.a {
        IN(0, 0),
        OUT(1, 1),
        INV(2, 2);
        
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

    public boolean hasId() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getId() {
        return this.id_;
    }

    public boolean hasName() {
        return (this.bitField0_ & 2) == 2;
    }

    public int getName() {
        return this.name_;
    }

    public boolean hasReified() {
        return (this.bitField0_ & 4) == 4;
    }

    public boolean getReified() {
        return this.reified_;
    }

    public boolean hasVariance() {
        return (this.bitField0_ & 8) == 8;
    }

    public c getVariance() {
        return this.variance_;
    }

    public List<q> getUpperBoundList() {
        return this.upperBound_;
    }

    public int getUpperBoundCount() {
        return this.upperBound_.size();
    }

    public q getUpperBound(int index) {
        return this.upperBound_.get(index);
    }

    public List<Integer> getUpperBoundIdList() {
        return this.upperBoundId_;
    }

    private void c() {
        this.id_ = 0;
        this.name_ = 0;
        this.reified_ = false;
        this.variance_ = c.INV;
        this.upperBound_ = Collections.emptyList();
        this.upperBoundId_ = Collections.emptyList();
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
        } else if (!hasName()) {
            this.memoizedIsInitialized = 0;
            return false;
        } else {
            for (int i = 0; i < getUpperBoundCount(); i++) {
                if (!getUpperBound(i).isInitialized()) {
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
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.TypeParameter>.ExtensionWriter extensionWriter = newExtensionWriter();
        if ((this.bitField0_ & 1) == 1) {
            output.a0(1, this.id_);
        }
        if ((this.bitField0_ & 2) == 2) {
            output.a0(2, this.name_);
        }
        if ((this.bitField0_ & 4) == 4) {
            output.L(3, this.reified_);
        }
        if ((this.bitField0_ & 8) == 8) {
            output.S(4, this.variance_.getNumber());
        }
        for (int i = 0; i < this.upperBound_.size(); i++) {
            output.d0(5, this.upperBound_.get(i));
        }
        if (getUpperBoundIdList().size() > 0) {
            output.o0(50);
            output.o0(this.upperBoundIdMemoizedSerializedSize);
        }
        for (int i2 = 0; i2 < this.upperBoundId_.size(); i2++) {
            output.b0(this.upperBoundId_.get(i2).intValue());
        }
        extensionWriter.a(1000, output);
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
        if ((this.bitField0_ & 2) == 2) {
            size2 += CodedOutputStream.o(2, this.name_);
        }
        if ((this.bitField0_ & 4) == 4) {
            size2 += CodedOutputStream.a(3, this.reified_);
        }
        if ((this.bitField0_ & 8) == 8) {
            size2 += CodedOutputStream.h(4, this.variance_.getNumber());
        }
        for (int i = 0; i < this.upperBound_.size(); i++) {
            size2 += CodedOutputStream.s(5, this.upperBound_.get(i));
        }
        int dataSize = 0;
        for (int i2 = 0; i2 < this.upperBoundId_.size(); i2++) {
            dataSize += CodedOutputStream.p(this.upperBoundId_.get(i2).intValue());
        }
        int size3 = size2 + dataSize;
        if (!getUpperBoundIdList().isEmpty()) {
            size3 = size3 + 1 + CodedOutputStream.p(dataSize);
        }
        this.upperBoundIdMemoizedSerializedSize = dataSize;
        int size4 = size3 + extensionsSerializedSize() + this.unknownFields.size();
        this.memoizedSerializedSize = size4;
        return size4;
    }

    public static b newBuilder() {
        return b.q();
    }

    public b newBuilderForType() {
        return newBuilder();
    }

    public static b newBuilder(s prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<s, b> implements p {
        private List<q> a1 = Collections.emptyList();
        private c p0 = c.INV;
        private List<Integer> p1 = Collections.emptyList();
        private int q;
        private int x;
        private int y;
        private boolean z;

        private b() {
            y();
        }

        private void y() {
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
        public s build() {
            s result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public s o() {
            s result = new s((h.c) this);
            int from_bitField0_ = this.q;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            int unused = result.id_ = this.x;
            if ((from_bitField0_ & 2) == 2) {
                to_bitField0_ |= 2;
            }
            int unused2 = result.name_ = this.y;
            if ((from_bitField0_ & 4) == 4) {
                to_bitField0_ |= 4;
            }
            boolean unused3 = result.reified_ = this.z;
            if ((from_bitField0_ & 8) == 8) {
                to_bitField0_ |= 8;
            }
            c unused4 = result.variance_ = this.p0;
            if ((this.q & 16) == 16) {
                this.a1 = Collections.unmodifiableList(this.a1);
                this.q &= -17;
            }
            List unused5 = result.upperBound_ = this.a1;
            if ((this.q & 32) == 32) {
                this.p1 = Collections.unmodifiableList(this.p1);
                this.q &= -33;
            }
            List unused6 = result.upperBoundId_ = this.p1;
            int unused7 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: z */
        public b e(s other) {
            if (other == s.getDefaultInstance()) {
                return this;
            }
            if (other.hasId()) {
                B(other.getId());
            }
            if (other.hasName()) {
                C(other.getName());
            }
            if (other.hasReified()) {
                D(other.getReified());
            }
            if (other.hasVariance()) {
                F(other.getVariance());
            }
            if (!other.upperBound_.isEmpty()) {
                if (this.a1.isEmpty()) {
                    this.a1 = other.upperBound_;
                    this.q &= -17;
                } else {
                    s();
                    this.a1.addAll(other.upperBound_);
                }
            }
            if (!other.upperBoundId_.isEmpty()) {
                if (this.p1.isEmpty()) {
                    this.p1 = other.upperBoundId_;
                    this.q &= -33;
                } else {
                    r();
                    this.p1.addAll(other.upperBoundId_);
                }
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (!v() || !w()) {
                return false;
            }
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

        /* renamed from: A */
        public b a(e input, f extensionRegistry) {
            try {
                s parsedMessage = s.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                s parsedMessage2 = (s) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((s) null);
                }
                throw th;
            }
        }

        public boolean v() {
            return (this.q & 1) == 1;
        }

        public b B(int value) {
            this.q |= 1;
            this.x = value;
            return this;
        }

        public boolean w() {
            return (this.q & 2) == 2;
        }

        public b C(int value) {
            this.q |= 2;
            this.y = value;
            return this;
        }

        public b D(boolean value) {
            this.q |= 4;
            this.z = value;
            return this;
        }

        public b F(c value) {
            if (value != null) {
                this.q |= 8;
                this.p0 = value;
                return this;
            }
            throw new NullPointerException();
        }

        private void s() {
            if ((this.q & 16) != 16) {
                this.a1 = new ArrayList(this.a1);
                this.q |= 16;
            }
        }

        public int u() {
            return this.a1.size();
        }

        public q t(int index) {
            return this.a1.get(index);
        }

        private void r() {
            if ((this.q & 32) != 32) {
                this.p1 = new ArrayList(this.p1);
                this.q |= 32;
            }
        }
    }
}
