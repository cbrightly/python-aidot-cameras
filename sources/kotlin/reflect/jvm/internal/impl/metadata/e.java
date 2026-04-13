package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.a;
import kotlin.reflect.jvm.internal.impl.protobuf.d;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.protobuf.p;
import kotlin.reflect.jvm.internal.impl.protobuf.q;

/* compiled from: ProtoBuf */
public final class e extends h implements p {
    public static q<e> PARSER = new a();
    private static final e c;
    /* access modifiers changed from: private */
    public List<f> effect_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public final d unknownFields;

    private e(h.b builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private e(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static e getDefaultInstance() {
        return c;
    }

    public e getDefaultInstanceForType() {
        return c;
    }

    private e(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
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
                            this.effect_ = new ArrayList();
                            mutable_bitField0_ |= 1;
                        }
                        this.effect_.add(input.u(f.PARSER, extensionRegistry));
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
                    this.effect_ = Collections.unmodifiableList(this.effect_);
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
            this.effect_ = Collections.unmodifiableList(this.effect_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<e> {
        a() {
        }

        /* renamed from: m */
        public e c(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
            return new e(input, extensionRegistry);
        }
    }

    static {
        e eVar = new e(true);
        c = eVar;
        eVar.b();
    }

    public q<e> getParserForType() {
        return PARSER;
    }

    public int getEffectCount() {
        return this.effect_.size();
    }

    public f getEffect(int index) {
        return this.effect_.get(index);
    }

    private void b() {
        this.effect_ = Collections.emptyList();
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        for (int i = 0; i < getEffectCount(); i++) {
            if (!getEffect(i).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        this.memoizedIsInitialized = 1;
        return true;
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        for (int i = 0; i < this.effect_.size(); i++) {
            output.d0(1, this.effect_.get(i));
        }
        output.i0(this.unknownFields);
    }

    public int getSerializedSize() {
        int size = this.memoizedSerializedSize;
        if (size != -1) {
            return size;
        }
        int size2 = 0;
        for (int i = 0; i < this.effect_.size(); i++) {
            size2 += CodedOutputStream.s(1, this.effect_.get(i));
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

    public static b newBuilder(e prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.b<e, b> implements p {
        private int d;
        private List<f> f = Collections.emptyList();

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
        public e build() {
            e result = j();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public e j() {
            e result = new e((h.b) this);
            int i = this.d;
            if ((this.d & 1) == 1) {
                this.f = Collections.unmodifiableList(this.f);
                this.d &= -2;
            }
            List unused = result.effect_ = this.f;
            return result;
        }

        /* renamed from: q */
        public b e(e other) {
            if (other == e.getDefaultInstance()) {
                return this;
            }
            if (!other.effect_.isEmpty()) {
                if (this.f.isEmpty()) {
                    this.f = other.effect_;
                    this.d &= -2;
                } else {
                    m();
                    this.f.addAll(other.effect_);
                }
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
        public b a(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
            try {
                e parsedMessage = e.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                e parsedMessage2 = (e) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((e) null);
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

        public f n(int index) {
            return this.f.get(index);
        }
    }
}
