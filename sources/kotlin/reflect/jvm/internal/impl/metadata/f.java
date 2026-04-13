package kotlin.reflect.jvm.internal.impl.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.h;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.a;
import kotlin.reflect.jvm.internal.impl.protobuf.d;
import kotlin.reflect.jvm.internal.impl.protobuf.e;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.protobuf.i;
import kotlin.reflect.jvm.internal.impl.protobuf.p;
import kotlin.reflect.jvm.internal.impl.protobuf.q;

/* compiled from: ProtoBuf */
public final class f extends h implements p {
    public static q<f> PARSER = new a();
    private static final f c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public h conclusionOfConditionalEffect_;
    /* access modifiers changed from: private */
    public List<h> effectConstructorArgument_;
    /* access modifiers changed from: private */
    public c effectType_;
    /* access modifiers changed from: private */
    public d kind_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    /* access modifiers changed from: private */
    public final kotlin.reflect.jvm.internal.impl.protobuf.d unknownFields;

    private f(h.b builder) {
        super(builder);
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private f(boolean noInit) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = kotlin.reflect.jvm.internal.impl.protobuf.d.c;
    }

    public static f getDefaultInstance() {
        return c;
    }

    public f getDefaultInstanceForType() {
        return c;
    }

    private f(e input, kotlin.reflect.jvm.internal.impl.protobuf.f extensionRegistry) {
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        b();
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
                        int rawValue = input.n();
                        c value = c.valueOf(rawValue);
                        if (value != null) {
                            this.bitField0_ |= 1;
                            this.effectType_ = value;
                            break;
                        } else {
                            unknownFieldsCodedOutput.o0(tag);
                            unknownFieldsCodedOutput.o0(rawValue);
                            break;
                        }
                    case 18:
                        if ((mutable_bitField0_ & 2) != 2) {
                            this.effectConstructorArgument_ = new ArrayList();
                            mutable_bitField0_ |= 2;
                        }
                        this.effectConstructorArgument_.add(input.u(h.PARSER, extensionRegistry));
                        break;
                    case 26:
                        h.b subBuilder = (this.bitField0_ & 2) == 2 ? this.conclusionOfConditionalEffect_.toBuilder() : null;
                        h hVar = (h) input.u(h.PARSER, extensionRegistry);
                        this.conclusionOfConditionalEffect_ = hVar;
                        if (subBuilder != null) {
                            subBuilder.e(hVar);
                            this.conclusionOfConditionalEffect_ = subBuilder.j();
                        }
                        this.bitField0_ |= 2;
                        break;
                    case 32:
                        int rawValue2 = input.n();
                        d value2 = d.valueOf(rawValue2);
                        if (value2 != null) {
                            this.bitField0_ |= 4;
                            this.kind_ = value2;
                            break;
                        } else {
                            unknownFieldsCodedOutput.o0(tag);
                            unknownFieldsCodedOutput.o0(rawValue2);
                            break;
                        }
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
                    this.effectConstructorArgument_ = Collections.unmodifiableList(this.effectConstructorArgument_);
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
            this.effectConstructorArgument_ = Collections.unmodifiableList(this.effectConstructorArgument_);
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
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<f> {
        a() {
        }

        /* renamed from: m */
        public f c(e input, kotlin.reflect.jvm.internal.impl.protobuf.f extensionRegistry) {
            return new f(input, extensionRegistry);
        }
    }

    static {
        f fVar = new f(true);
        c = fVar;
        fVar.b();
    }

    public q<f> getParserForType() {
        return PARSER;
    }

    /* compiled from: ProtoBuf */
    public enum c implements i.a {
        RETURNS_CONSTANT(0, 0),
        CALLS(1, 1),
        RETURNS_NOT_NULL(2, 2);
        
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
                    return RETURNS_CONSTANT;
                case 1:
                    return CALLS;
                case 2:
                    return RETURNS_NOT_NULL;
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

    /* compiled from: ProtoBuf */
    public enum d implements i.a {
        AT_MOST_ONCE(0, 0),
        EXACTLY_ONCE(1, 1),
        AT_LEAST_ONCE(2, 2);
        
        private static i.b<d> c;
        private final int value;

        static {
            c = new a();
        }

        public final int getNumber() {
            return this.value;
        }

        public static d valueOf(int value2) {
            switch (value2) {
                case 0:
                    return AT_MOST_ONCE;
                case 1:
                    return EXACTLY_ONCE;
                case 2:
                    return AT_LEAST_ONCE;
                default:
                    return null;
            }
        }

        /* compiled from: ProtoBuf */
        public static final class a implements i.b<d> {
            a() {
            }

            /* renamed from: b */
            public d a(int number) {
                return d.valueOf(number);
            }
        }

        private d(int index, int value2) {
            this.value = value2;
        }
    }

    public boolean hasEffectType() {
        return (this.bitField0_ & 1) == 1;
    }

    public c getEffectType() {
        return this.effectType_;
    }

    public int getEffectConstructorArgumentCount() {
        return this.effectConstructorArgument_.size();
    }

    public h getEffectConstructorArgument(int index) {
        return this.effectConstructorArgument_.get(index);
    }

    public boolean hasConclusionOfConditionalEffect() {
        return (this.bitField0_ & 2) == 2;
    }

    public h getConclusionOfConditionalEffect() {
        return this.conclusionOfConditionalEffect_;
    }

    public boolean hasKind() {
        return (this.bitField0_ & 4) == 4;
    }

    public d getKind() {
        return this.kind_;
    }

    private void b() {
        this.effectType_ = c.RETURNS_CONSTANT;
        this.effectConstructorArgument_ = Collections.emptyList();
        this.conclusionOfConditionalEffect_ = h.getDefaultInstance();
        this.kind_ = d.AT_MOST_ONCE;
    }

    public final boolean isInitialized() {
        byte isInitialized = this.memoizedIsInitialized;
        if (isInitialized == 1) {
            return true;
        }
        if (isInitialized == 0) {
            return false;
        }
        for (int i = 0; i < getEffectConstructorArgumentCount(); i++) {
            if (!getEffectConstructorArgument(i).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        if (hasConclusionOfConditionalEffect() == 0 || getConclusionOfConditionalEffect().isInitialized()) {
            this.memoizedIsInitialized = 1;
            return true;
        }
        this.memoizedIsInitialized = 0;
        return false;
    }

    public void writeTo(CodedOutputStream output) {
        getSerializedSize();
        if ((this.bitField0_ & 1) == 1) {
            output.S(1, this.effectType_.getNumber());
        }
        for (int i = 0; i < this.effectConstructorArgument_.size(); i++) {
            output.d0(2, this.effectConstructorArgument_.get(i));
        }
        if ((this.bitField0_ & 2) == 2) {
            output.d0(3, this.conclusionOfConditionalEffect_);
        }
        if ((this.bitField0_ & 4) == 4) {
            output.S(4, this.kind_.getNumber());
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
            size2 = 0 + CodedOutputStream.h(1, this.effectType_.getNumber());
        }
        for (int i = 0; i < this.effectConstructorArgument_.size(); i++) {
            size2 += CodedOutputStream.s(2, this.effectConstructorArgument_.get(i));
        }
        if ((this.bitField0_ & 2) == 2) {
            size2 += CodedOutputStream.s(3, this.conclusionOfConditionalEffect_);
        }
        if ((this.bitField0_ & 4) == 4) {
            size2 += CodedOutputStream.h(4, this.kind_.getNumber());
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

    public static b newBuilder(f prototype) {
        return newBuilder().e(prototype);
    }

    public b toBuilder() {
        return newBuilder(this);
    }

    /* compiled from: ProtoBuf */
    public static final class b extends h.b<f, b> implements p {
        private int d;
        private c f = c.RETURNS_CONSTANT;
        private List<h> q = Collections.emptyList();
        private h x = h.getDefaultInstance();
        private d y = d.AT_MOST_ONCE;

        private b() {
            r();
        }

        private void r() {
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
        public f build() {
            f result = j();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public f j() {
            f result = new f((h.b) this);
            int from_bitField0_ = this.d;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            c unused = result.effectType_ = this.f;
            if ((this.d & 2) == 2) {
                this.q = Collections.unmodifiableList(this.q);
                this.d &= -3;
            }
            List unused2 = result.effectConstructorArgument_ = this.q;
            if ((from_bitField0_ & 4) == 4) {
                to_bitField0_ |= 2;
            }
            h unused3 = result.conclusionOfConditionalEffect_ = this.x;
            if ((from_bitField0_ & 8) == 8) {
                to_bitField0_ |= 4;
            }
            d unused4 = result.kind_ = this.y;
            int unused5 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: t */
        public b e(f other) {
            if (other == f.getDefaultInstance()) {
                return this;
            }
            if (other.hasEffectType()) {
                v(other.getEffectType());
            }
            if (!other.effectConstructorArgument_.isEmpty()) {
                if (this.q.isEmpty()) {
                    this.q = other.effectConstructorArgument_;
                    this.d &= -3;
                } else {
                    m();
                    this.q.addAll(other.effectConstructorArgument_);
                }
            }
            if (other.hasConclusionOfConditionalEffect()) {
                s(other.getConclusionOfConditionalEffect());
            }
            if (other.hasKind()) {
                w(other.getKind());
            }
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            for (int i = 0; i < p(); i++) {
                if (!o(i).isInitialized()) {
                    return false;
                }
            }
            if (q() == 0 || n().isInitialized()) {
                return true;
            }
            return false;
        }

        /* renamed from: u */
        public b a(e input, kotlin.reflect.jvm.internal.impl.protobuf.f extensionRegistry) {
            try {
                f parsedMessage = f.PARSER.c(input, extensionRegistry);
                if (parsedMessage != null) {
                    e(parsedMessage);
                }
                return this;
            } catch (InvalidProtocolBufferException e) {
                f parsedMessage2 = (f) e.getUnfinishedMessage();
                throw e;
            } catch (Throwable th) {
                if (0 != 0) {
                    e((f) null);
                }
                throw th;
            }
        }

        public b v(c value) {
            if (value != null) {
                this.d |= 1;
                this.f = value;
                return this;
            }
            throw new NullPointerException();
        }

        private void m() {
            if ((this.d & 2) != 2) {
                this.q = new ArrayList(this.q);
                this.d |= 2;
            }
        }

        public int p() {
            return this.q.size();
        }

        public h o(int index) {
            return this.q.get(index);
        }

        public boolean q() {
            return (this.d & 4) == 4;
        }

        public h n() {
            return this.x;
        }

        public b s(h value) {
            if ((this.d & 4) != 4 || this.x == h.getDefaultInstance()) {
                this.x = value;
            } else {
                this.x = h.newBuilder(this.x).e(value).j();
            }
            this.d |= 4;
            return this;
        }

        public b w(d value) {
            if (value != null) {
                this.d |= 8;
                this.y = value;
                return this;
            }
            throw new NullPointerException();
        }
    }
}
