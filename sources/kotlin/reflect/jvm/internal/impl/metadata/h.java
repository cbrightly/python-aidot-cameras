package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.q;
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
public final class h extends kotlin.reflect.jvm.internal.impl.protobuf.h implements p {
    public static q<h> PARSER = new a();
    private static final h c;
    /* access modifiers changed from: private */
    public List<h> andArgument_;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public c constantValue_;
    /* access modifiers changed from: private */
    public int flags_;
    /* access modifiers changed from: private */
    public int isInstanceTypeId_;
    /* access modifiers changed from: private */
    public q isInstanceType_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public List<h> orArgument_;
    /* access modifiers changed from: private */
    public final d unknownFields;
    /* access modifiers changed from: private */
    public int valueParameterReference_;

    private h(h.b builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private h(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static h getDefaultInstance() {
        return c;
    }

    public h getDefaultInstanceForType() {
        return c;
    }

    private h(e input, f extensionRegistry) {
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
                        this.flags_ = input.s();
                        break;
                    case 16:
                        this.bitField0_ |= 2;
                        this.valueParameterReference_ = input.s();
                        break;
                    case 24:
                        int rawValue = input.n();
                        c value = c.valueOf(rawValue);
                        if (value != null) {
                            this.bitField0_ |= 4;
                            this.constantValue_ = value;
                            break;
                        } else {
                            unknownFieldsCodedOutput.o0(tag);
                            unknownFieldsCodedOutput.o0(rawValue);
                            break;
                        }
                    case 34:
                        q.c subBuilder = (this.bitField0_ & 8) == 8 ? this.isInstanceType_.toBuilder() : null;
                        q qVar = (q) input.u(q.PARSER, extensionRegistry);
                        this.isInstanceType_ = qVar;
                        if (subBuilder != null) {
                            subBuilder.e(qVar);
                            this.isInstanceType_ = subBuilder.o();
                        }
                        this.bitField0_ |= 8;
                        break;
                    case 40:
                        this.bitField0_ |= 16;
                        this.isInstanceTypeId_ = input.s();
                        break;
                    case 50:
                        if ((mutable_bitField0_ & 32) != 32) {
                            this.andArgument_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        this.andArgument_.add(input.u(PARSER, extensionRegistry));
                        break;
                    case 58:
                        if ((mutable_bitField0_ & 64) != 64) {
                            this.orArgument_ = new ArrayList();
                            mutable_bitField0_ |= 64;
                        }
                        this.orArgument_.add(input.u(PARSER, extensionRegistry));
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
                    this.andArgument_ = Collections.unmodifiableList(this.andArgument_);
                }
                if ((mutable_bitField0_ & 64) == 64) {
                    this.orArgument_ = Collections.unmodifiableList(this.orArgument_);
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
            this.andArgument_ = Collections.unmodifiableList(this.andArgument_);
        }
        if ((mutable_bitField0_ & 64) == 64) {
            this.orArgument_ = Collections.unmodifiableList(this.orArgument_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<h> {
        a() {
        }

        /* renamed from: m */
        public h c(e input, f extensionRegistry) {
            return new h(input, extensionRegistry);
        }
    }

    static {
        h hVar = new h(true);
        c = hVar;
        hVar.b();
    }

    public kotlin.reflect.jvm.internal.impl.protobuf.q<h> getParserForType() {
        return PARSER;
    }

    /* compiled from: ProtoBuf */
    public enum c implements i.a {
        TRUE(0, 0),
        FALSE(1, 1),
        NULL(2, 2);
        
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
                    return TRUE;
                case 1:
                    return FALSE;
                case 2:
                    return NULL;
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

    public boolean hasFlags() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getFlags() {
        return this.flags_;
    }

    public boolean hasValueParameterReference() {
        return (this.bitField0_ & 2) == 2;
    }

    public int getValueParameterReference() {
        return this.valueParameterReference_;
    }

    public boolean hasConstantValue() {
        return (this.bitField0_ & 4) == 4;
    }

    public c getConstantValue() {
        return this.constantValue_;
    }

    public boolean hasIsInstanceType() {
        return (this.bitField0_ & 8) == 8;
    }

    public q getIsInstanceType() {
        return this.isInstanceType_;
    }

    public boolean hasIsInstanceTypeId() {
        return (this.bitField0_ & 16) == 16;
    }

    public int getIsInstanceTypeId() {
        return this.isInstanceTypeId_;
    }

    public int getAndArgumentCount() {
        return this.andArgument_.size();
    }

    public h getAndArgument(int index) {
        return this.andArgument_.get(index);
    }

    public int getOrArgumentCount() {
        return this.orArgument_.size();
    }

    public h getOrArgument(int index) {
        return this.orArgument_.get(index);
    }

    private void b() {
        this.flags_ = 0;
        this.valueParameterReference_ = 0;
        this.constantValue_ = c.TRUE;
        this.isInstanceType_ = q.getDefaultInstance();
        this.isInstanceTypeId_ = 0;
        this.andArgument_ = Collections.emptyList();
        this.orArgument_ = Collections.emptyList();
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        if (!hasIsInstanceType() || getIsInstanceType().isInitialized()) {
            for (int i = 0; i < getAndArgumentCount(); i++) {
                if (!getAndArgument(i).isInitialized()) {
                    this.memoizedIsInitialized = 0;
                    return false;
                }
            }
            for (int i2 = 0; i2 < getOrArgumentCount(); i2++) {
                if (!getOrArgument(i2).isInitialized()) {
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
            output.a0(1, this.flags_);
        }
        if ((this.bitField0_ & 2) == 2) {
            output.a0(2, this.valueParameterReference_);
        }
        if ((this.bitField0_ & 4) == 4) {
            output.S(3, this.constantValue_.getNumber());
        }
        if ((this.bitField0_ & 8) == 8) {
            output.d0(4, this.isInstanceType_);
        }
        if ((this.bitField0_ & 16) == 16) {
            output.a0(5, this.isInstanceTypeId_);
        }
        for (int i = 0; i < this.andArgument_.size(); i++) {
            output.d0(6, this.andArgument_.get(i));
        }
        for (int i2 = 0; i2 < this.orArgument_.size(); i2++) {
            output.d0(7, this.orArgument_.get(i2));
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
            size2 = 0 + CodedOutputStream.o(1, this.flags_);
        }
        if ((this.bitField0_ & 2) == 2) {
            size2 += CodedOutputStream.o(2, this.valueParameterReference_);
        }
        if ((this.bitField0_ & 4) == 4) {
            size2 += CodedOutputStream.h(3, this.constantValue_.getNumber());
        }
        if ((this.bitField0_ & 8) == 8) {
            size2 += CodedOutputStream.s(4, this.isInstanceType_);
        }
        if ((this.bitField0_ & 16) == 16) {
            size2 += CodedOutputStream.o(5, this.isInstanceTypeId_);
        }
        for (int i = 0; i < this.andArgument_.size(); i++) {
            size2 += CodedOutputStream.s(6, this.andArgument_.get(i));
        }
        for (int i2 = 0; i2 < this.orArgument_.size(); i2++) {
            size2 += CodedOutputStream.s(7, this.orArgument_.get(i2));
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

    public static b newBuilder(h prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.b<h, b> implements p {
        private List<h> a1 = Collections.emptyList();
        private int d;
        private int f;
        private List<h> p0 = Collections.emptyList();
        private int q;
        private c x = c.TRUE;
        private q y = q.getDefaultInstance();
        private int z;

        private b() {
            u();
        }

        private void u() {
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
        public h build() {
            h result = j();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public h j() {
            h result = new h((h.b) this);
            int from_bitField0_ = this.d;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            int unused = result.flags_ = this.f;
            if ((from_bitField0_ & 2) == 2) {
                to_bitField0_ |= 2;
            }
            int unused2 = result.valueParameterReference_ = this.q;
            if ((from_bitField0_ & 4) == 4) {
                to_bitField0_ |= 4;
            }
            c unused3 = result.constantValue_ = this.x;
            if ((from_bitField0_ & 8) == 8) {
                to_bitField0_ |= 8;
            }
            q unused4 = result.isInstanceType_ = this.y;
            if ((from_bitField0_ & 16) == 16) {
                to_bitField0_ |= 16;
            }
            int unused5 = result.isInstanceTypeId_ = this.z;
            if ((this.d & 32) == 32) {
                this.p0 = Collections.unmodifiableList(this.p0);
                this.d &= -33;
            }
            List unused6 = result.andArgument_ = this.p0;
            if ((this.d & 64) == 64) {
                this.a1 = Collections.unmodifiableList(this.a1);
                this.d &= -65;
            }
            List unused7 = result.orArgument_ = this.a1;
            int unused8 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: v */
        public b e(h other) {
            if (other == h.getDefaultInstance()) {
                return this;
            }
            if (other.hasFlags()) {
                A(other.getFlags());
            }
            if (other.hasValueParameterReference()) {
                C(other.getValueParameterReference());
            }
            if (other.hasConstantValue()) {
                z(other.getConstantValue());
            }
            if (other.hasIsInstanceType()) {
                y(other.getIsInstanceType());
            }
            if (other.hasIsInstanceTypeId()) {
                B(other.getIsInstanceTypeId());
            }
            if (!other.andArgument_.isEmpty()) {
                if (this.p0.isEmpty()) {
                    this.p0 = other.andArgument_;
                    this.d &= -33;
                } else {
                    m();
                    this.p0.addAll(other.andArgument_);
                }
            }
            if (!other.orArgument_.isEmpty()) {
                if (this.a1.isEmpty()) {
                    this.a1 = other.orArgument_;
                    this.d &= -65;
                } else {
                    n();
                    this.a1.addAll(other.orArgument_);
                }
            }
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (t() && !q().isInitialized()) {
                return false;
            }
            for (int i = 0; i < p(); i++) {
                if (!o(i).isInitialized()) {
                    return false;
                }
            }
            for (int i2 = 0; i2 < s(); i2++) {
                if (!r(i2).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: w */
        public b a(e input, f extensionRegistry) {
            try {
                h parsedMessage = h.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                h parsedMessage2 = (h) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((h) null);
                }
                throw th;
            }
        }

        public b A(int value) {
            this.d |= 1;
            this.f = value;
            return this;
        }

        public b C(int value) {
            this.d |= 2;
            this.q = value;
            return this;
        }

        public b z(c value) {
            if (value != null) {
                this.d |= 4;
                this.x = value;
                return this;
            }
            throw new NullPointerException();
        }

        public boolean t() {
            return (this.d & 8) == 8;
        }

        public q q() {
            return this.y;
        }

        public b y(q value) {
            if ((this.d & 8) != 8 || this.y == q.getDefaultInstance()) {
                this.y = value;
            } else {
                this.y = q.newBuilder(this.y).e(value).o();
            }
            this.d |= 8;
            return this;
        }

        public b B(int value) {
            this.d |= 16;
            this.z = value;
            return this;
        }

        private void m() {
            if ((this.d & 32) != 32) {
                this.p0 = new ArrayList(this.p0);
                this.d |= 32;
            }
        }

        public int p() {
            return this.p0.size();
        }

        public h o(int index) {
            return this.p0.get(index);
        }

        private void n() {
            if ((this.d & 64) != 64) {
                this.a1 = new ArrayList(this.a1);
                this.d |= 64;
            }
        }

        public int s() {
            return this.a1.size();
        }

        public h r(int index) {
            return this.a1.get(index);
        }
    }
}
