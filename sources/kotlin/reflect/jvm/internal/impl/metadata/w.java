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
public final class w extends h implements p {
    public static q<w> PARSER = new a();
    private static final w c;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public List<v> requirement_;
    /* access modifiers changed from: private */
    public final d unknownFields;

    private w(h.b builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private w(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static w getDefaultInstance() {
        return c;
    }

    public w getDefaultInstanceForType() {
        return c;
    }

    private w(e input, f extensionRegistry) {
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
                            this.requirement_ = new ArrayList();
                            mutable_bitField0_ |= 1;
                        }
                        this.requirement_.add(input.u(v.PARSER, extensionRegistry));
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
                    this.requirement_ = Collections.unmodifiableList(this.requirement_);
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
            this.requirement_ = Collections.unmodifiableList(this.requirement_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<w> {
        a() {
        }

        /* renamed from: m */
        public w c(e input, f extensionRegistry) {
            return new w(input, extensionRegistry);
        }
    }

    static {
        w wVar = new w(true);
        c = wVar;
        wVar.b();
    }

    public q<w> getParserForType() {
        return PARSER;
    }

    public List<v> getRequirementList() {
        return this.requirement_;
    }

    public int getRequirementCount() {
        return this.requirement_.size();
    }

    private void b() {
        this.requirement_ = Collections.emptyList();
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        for (int i = 0; i < this.requirement_.size(); i++) {
            output.d0(1, this.requirement_.get(i));
        }
        output.i0(this.unknownFields);
    }

    public int getSerializedSize() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        for (int i = 0; i < this.requirement_.size(); i++) {
            size2 += CodedOutputStream.s(1, this.requirement_.get(i));
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

    public static b newBuilder(w prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.b<w, b> implements p {
        private int d;
        private List<v> f = Collections.emptyList();

        private b() {
            n();
        }

        private void n() {
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
        public w build() {
            w result = j();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public w j() {
            w result = new w((h.b) this);
            int i = this.d;
            if ((this.d & 1) == 1) {
                this.f = Collections.unmodifiableList(this.f);
                this.d &= -2;
            }
            List unused = result.requirement_ = this.f;
            return result;
        }

        /* renamed from: o */
        public b e(w other) {
            if (other == w.getDefaultInstance()) {
                return this;
            }
            if (!other.requirement_.isEmpty()) {
                if (this.f.isEmpty()) {
                    this.f = other.requirement_;
                    this.d &= -2;
                } else {
                    m();
                    this.f.addAll(other.requirement_);
                }
            }
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            return true;
        }

        /* renamed from: p */
        public b a(e input, f extensionRegistry) {
            try {
                w parsedMessage = w.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                w parsedMessage2 = (w) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((w) null);
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
    }
}
