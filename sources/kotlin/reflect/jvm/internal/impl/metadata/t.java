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
import kotlin.reflect.jvm.internal.impl.protobuf.p;
import kotlin.reflect.jvm.internal.impl.protobuf.q;

/* compiled from: ProtoBuf */
public final class t extends h implements p {
    public static q<t> PARSER = new a();
    private static final t c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int firstNullable_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public List<q> type_;
    /* access modifiers changed from: private */
    public final d unknownFields;

    private t(h.b builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private t(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static t getDefaultInstance() {
        return c;
    }

    public t getDefaultInstanceForType() {
        return c;
    }

    private t(e input, f extensionRegistry) {
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
                    case 10:
                        if ((mutable_bitField0_ & 1) != 1) {
                            this.type_ = new ArrayList();
                            mutable_bitField0_ |= 1;
                        }
                        this.type_.add(input.u(q.PARSER, extensionRegistry));
                        break;
                    case 16:
                        this.bitField0_ |= 1;
                        this.firstNullable_ = input.s();
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
                    this.type_ = Collections.unmodifiableList(this.type_);
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
            this.type_ = Collections.unmodifiableList(this.type_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<t> {
        a() {
        }

        /* renamed from: m */
        public t c(e input, f extensionRegistry) {
            return new t(input, extensionRegistry);
        }
    }

    static {
        t tVar = new t(true);
        c = tVar;
        tVar.b();
    }

    public q<t> getParserForType() {
        return PARSER;
    }

    public List<q> getTypeList() {
        return this.type_;
    }

    public int getTypeCount() {
        return this.type_.size();
    }

    public q getType(int index) {
        return this.type_.get(index);
    }

    public boolean hasFirstNullable() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getFirstNullable() {
        return this.firstNullable_;
    }

    private void b() {
        this.type_ = Collections.emptyList();
        this.firstNullable_ = -1;
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        for (int i = 0; i < getTypeCount(); i++) {
            if (!getType(i).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        for (int i = 0; i < this.type_.size(); i++) {
            output.d0(1, this.type_.get(i));
        }
        if ((this.bitField0_ & 1) == 1) {
            output.a0(2, this.firstNullable_);
        }
        output.i0(this.unknownFields);
    }

    public int getSerializedSize() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        for (int i = 0; i < this.type_.size(); i++) {
            size2 += CodedOutputStream.s(1, this.type_.get(i));
        }
        if ((this.bitField0_ & 1) == 1) {
            size2 += CodedOutputStream.o(2, this.firstNullable_);
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

    public static b newBuilder(t prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.b<t, b> implements p {
        private int d;
        private List<q> f = Collections.emptyList();
        private int q = -1;

        private b() {
            p();
        }

        private void p() {
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
        public t build() {
            t result = j();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public t j() {
            t result = new t((h.b) this);
            int from_bitField0_ = this.d;
            int to_bitField0_ = 0;
            if ((this.d & 1) == 1) {
                this.f = Collections.unmodifiableList(this.f);
                this.d &= -2;
            }
            List unused = result.type_ = this.f;
            if ((from_bitField0_ & 2) == 2) {
                to_bitField0_ = 0 | 1;
            }
            int unused2 = result.firstNullable_ = this.q;
            int unused3 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: q */
        public b e(t other) {
            if (other == t.getDefaultInstance()) {
                return this;
            }
            if (!other.type_.isEmpty()) {
                if (this.f.isEmpty()) {
                    this.f = other.type_;
                    this.d &= -2;
                } else {
                    m();
                    this.f.addAll(other.type_);
                }
            }
            if (other.hasFirstNullable()) {
                s(other.getFirstNullable());
            }
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            for (int i = 0; i < o(); i++) {
                if (!n(i).isInitialized()) {
                    return false;
                }
            }
            return true;
        }

        /* renamed from: r */
        public b a(e input, f extensionRegistry) {
            try {
                t parsedMessage = t.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                t parsedMessage2 = (t) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((t) null);
                }
                throw th;
            }
        }

        private void m() {
            if ((this.d & 1) != 1) {
                this.f = new ArrayList(this.f);
                this.d |= 1;
            }
        }

        public int o() {
            return this.f.size();
        }

        public q n(int index) {
            return this.f.get(index);
        }

        public b s(int value) {
            this.d |= 2;
            this.q = value;
            return this;
        }
    }
}
