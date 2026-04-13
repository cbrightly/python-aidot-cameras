package kotlin.reflect.jvm.internal.impl.metadata.jvm;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import kotlin.reflect.jvm.internal.impl.metadata.l;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.protobuf.CodedOutputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.InvalidProtocolBufferException;
import kotlin.reflect.jvm.internal.impl.protobuf.a;
import kotlin.reflect.jvm.internal.impl.protobuf.d;
import kotlin.reflect.jvm.internal.impl.protobuf.f;
import kotlin.reflect.jvm.internal.impl.protobuf.h;
import kotlin.reflect.jvm.internal.impl.protobuf.i;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import kotlin.reflect.jvm.internal.impl.protobuf.p;
import kotlin.reflect.jvm.internal.impl.protobuf.w;

/* compiled from: JvmProtoBuf */
public final class a {
    public static final h.f<kotlin.reflect.jvm.internal.impl.metadata.d, c> a;
    public static final h.f<i, c> b;
    public static final h.f<i, Integer> c;
    public static final h.f<n, d> d;
    public static final h.f<n, Integer> e;
    public static final h.f<q, List<kotlin.reflect.jvm.internal.impl.metadata.b>> f;
    public static final h.f<q, Boolean> g = h.newSingularGeneratedExtension(q.getDefaultInstance(), false, (o) null, (i.b<?>) null, 101, w.b.BOOL, Boolean.class);
    public static final h.f<s, List<kotlin.reflect.jvm.internal.impl.metadata.b>> h;
    public static final h.f<kotlin.reflect.jvm.internal.impl.metadata.c, Integer> i;
    public static final h.f<kotlin.reflect.jvm.internal.impl.metadata.c, List<n>> j;
    public static final h.f<kotlin.reflect.jvm.internal.impl.metadata.c, Integer> k;
    public static final h.f<l, Integer> l;
    public static final h.f<l, List<n>> m;

    public static void a(f registry) {
        registry.a(a);
        registry.a(b);
        registry.a(c);
        registry.a(d);
        registry.a(e);
        registry.a(f);
        registry.a(g);
        registry.a(h);
        registry.a(i);
        registry.a(j);
        registry.a(k);
        registry.a(l);
        registry.a(m);
    }

    /* compiled from: JvmProtoBuf */
    public static final class e extends h implements p {
        public static kotlin.reflect.jvm.internal.impl.protobuf.q<e> PARSER = new C0392a();
        private static final e c;
        private int localNameMemoizedSerializedSize;
        /* access modifiers changed from: private */
        public List<Integer> localName_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        /* access modifiers changed from: private */
        public List<c> record_;
        /* access modifiers changed from: private */
        public final kotlin.reflect.jvm.internal.impl.protobuf.d unknownFields;

        private e(h.b builder) {
            super(builder);
            this.localNameMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.d();
        }

        private e(boolean noInit) {
            this.localNameMemoizedSerializedSize = -1;
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = kotlin.reflect.jvm.internal.impl.protobuf.d.c;
        }

        public static e getDefaultInstance() {
            return c;
        }

        public e getDefaultInstanceForType() {
            return c;
        }

        private e(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
            this.localNameMemoizedSerializedSize = -1;
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
                        case 10:
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.record_ = new ArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.record_.add(input.u(c.PARSER, extensionRegistry));
                            break;
                        case 40:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.localName_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.localName_.add(Integer.valueOf(input.s()));
                            break;
                        case 42:
                            int limit = input.j(input.A());
                            if ((mutable_bitField0_ & 2) != 2 && input.e() > 0) {
                                this.localName_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            while (input.e() > 0) {
                                this.localName_.add(Integer.valueOf(input.s()));
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
                    if ((mutable_bitField0_ & 1) == 1) {
                        this.record_ = Collections.unmodifiableList(this.record_);
                    }
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.localName_ = Collections.unmodifiableList(this.localName_);
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
                this.record_ = Collections.unmodifiableList(this.record_);
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.localName_ = Collections.unmodifiableList(this.localName_);
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

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.jvm.a$e$a  reason: collision with other inner class name */
        /* compiled from: JvmProtoBuf */
        public static final class C0392a extends kotlin.reflect.jvm.internal.impl.protobuf.b<e> {
            C0392a() {
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

        public kotlin.reflect.jvm.internal.impl.protobuf.q<e> getParserForType() {
            return PARSER;
        }

        /* compiled from: JvmProtoBuf */
        public static final class c extends h implements p {
            public static kotlin.reflect.jvm.internal.impl.protobuf.q<c> PARSER = new C0393a();
            private static final c c;
            /* access modifiers changed from: private */
            public int bitField0_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            /* access modifiers changed from: private */
            public C0394c operation_;
            /* access modifiers changed from: private */
            public int predefinedIndex_;
            /* access modifiers changed from: private */
            public int range_;
            private int replaceCharMemoizedSerializedSize;
            /* access modifiers changed from: private */
            public List<Integer> replaceChar_;
            /* access modifiers changed from: private */
            public Object string_;
            private int substringIndexMemoizedSerializedSize;
            /* access modifiers changed from: private */
            public List<Integer> substringIndex_;
            /* access modifiers changed from: private */
            public final kotlin.reflect.jvm.internal.impl.protobuf.d unknownFields;

            private c(h.b builder) {
                super(builder);
                this.substringIndexMemoizedSerializedSize = -1;
                this.replaceCharMemoizedSerializedSize = -1;
                this.memoizedIsInitialized = -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.d();
            }

            private c(boolean noInit) {
                this.substringIndexMemoizedSerializedSize = -1;
                this.replaceCharMemoizedSerializedSize = -1;
                this.memoizedIsInitialized = -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = kotlin.reflect.jvm.internal.impl.protobuf.d.c;
            }

            public static c getDefaultInstance() {
                return c;
            }

            public c getDefaultInstanceForType() {
                return c;
            }

            private c(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
                this.substringIndexMemoizedSerializedSize = -1;
                this.replaceCharMemoizedSerializedSize = -1;
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
                                this.bitField0_ |= 1;
                                this.range_ = input.s();
                                break;
                            case 16:
                                this.bitField0_ |= 2;
                                this.predefinedIndex_ = input.s();
                                break;
                            case 24:
                                int rawValue = input.n();
                                C0394c value = C0394c.valueOf(rawValue);
                                if (value != null) {
                                    this.bitField0_ |= 8;
                                    this.operation_ = value;
                                    break;
                                } else {
                                    unknownFieldsCodedOutput.o0(tag);
                                    unknownFieldsCodedOutput.o0(rawValue);
                                    break;
                                }
                            case 32:
                                if ((mutable_bitField0_ & 16) != 16) {
                                    this.substringIndex_ = new ArrayList();
                                    mutable_bitField0_ |= 16;
                                }
                                this.substringIndex_.add(Integer.valueOf(input.s()));
                                break;
                            case 34:
                                int limit = input.j(input.A());
                                if ((mutable_bitField0_ & 16) != 16 && input.e() > 0) {
                                    this.substringIndex_ = new ArrayList();
                                    mutable_bitField0_ |= 16;
                                }
                                while (input.e() > 0) {
                                    this.substringIndex_.add(Integer.valueOf(input.s()));
                                }
                                input.i(limit);
                                break;
                            case 40:
                                if ((mutable_bitField0_ & 32) != 32) {
                                    this.replaceChar_ = new ArrayList();
                                    mutable_bitField0_ |= 32;
                                }
                                this.replaceChar_.add(Integer.valueOf(input.s()));
                                break;
                            case 42:
                                int limit2 = input.j(input.A());
                                if ((mutable_bitField0_ & 32) != 32 && input.e() > 0) {
                                    this.replaceChar_ = new ArrayList();
                                    mutable_bitField0_ |= 32;
                                }
                                while (input.e() > 0) {
                                    this.replaceChar_.add(Integer.valueOf(input.s()));
                                }
                                input.i(limit2);
                                break;
                            case 50:
                                kotlin.reflect.jvm.internal.impl.protobuf.d bs = input.l();
                                this.bitField0_ |= 4;
                                this.string_ = bs;
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
                            this.substringIndex_ = Collections.unmodifiableList(this.substringIndex_);
                        }
                        if ((mutable_bitField0_ & 32) == 32) {
                            this.replaceChar_ = Collections.unmodifiableList(this.replaceChar_);
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
                    this.substringIndex_ = Collections.unmodifiableList(this.substringIndex_);
                }
                if ((mutable_bitField0_ & 32) == 32) {
                    this.replaceChar_ = Collections.unmodifiableList(this.replaceChar_);
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

            /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.jvm.a$e$c$a  reason: collision with other inner class name */
            /* compiled from: JvmProtoBuf */
            public static final class C0393a extends kotlin.reflect.jvm.internal.impl.protobuf.b<c> {
                C0393a() {
                }

                /* renamed from: m */
                public c c(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
                    return new c(input, extensionRegistry);
                }
            }

            static {
                c cVar = new c(true);
                c = cVar;
                cVar.b();
            }

            public kotlin.reflect.jvm.internal.impl.protobuf.q<c> getParserForType() {
                return PARSER;
            }

            /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.jvm.a$e$c$c  reason: collision with other inner class name */
            /* compiled from: JvmProtoBuf */
            public enum C0394c implements i.a {
                NONE(0, 0),
                INTERNAL_TO_CLASS_ID(1, 1),
                DESC_TO_CLASS_ID(2, 2);
                
                private static i.b<C0394c> c;
                private final int value;

                static {
                    c = new C0395a();
                }

                public final int getNumber() {
                    return this.value;
                }

                public static C0394c valueOf(int value2) {
                    switch (value2) {
                        case 0:
                            return NONE;
                        case 1:
                            return INTERNAL_TO_CLASS_ID;
                        case 2:
                            return DESC_TO_CLASS_ID;
                        default:
                            return null;
                    }
                }

                /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.jvm.a$e$c$c$a  reason: collision with other inner class name */
                /* compiled from: JvmProtoBuf */
                public static final class C0395a implements i.b<C0394c> {
                    C0395a() {
                    }

                    /* renamed from: b */
                    public C0394c a(int number) {
                        return C0394c.valueOf(number);
                    }
                }

                private C0394c(int index, int value2) {
                    this.value = value2;
                }
            }

            public boolean hasRange() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getRange() {
                return this.range_;
            }

            public boolean hasPredefinedIndex() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getPredefinedIndex() {
                return this.predefinedIndex_;
            }

            public boolean hasString() {
                return (this.bitField0_ & 4) == 4;
            }

            public String getString() {
                Object ref = this.string_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                kotlin.reflect.jvm.internal.impl.protobuf.d bs = (kotlin.reflect.jvm.internal.impl.protobuf.d) ref;
                String s = bs.x();
                if (bs.o()) {
                    this.string_ = s;
                }
                return s;
            }

            public kotlin.reflect.jvm.internal.impl.protobuf.d getStringBytes() {
                Object ref = this.string_;
                if (!(ref instanceof String)) {
                    return (kotlin.reflect.jvm.internal.impl.protobuf.d) ref;
                }
                kotlin.reflect.jvm.internal.impl.protobuf.d b2 = kotlin.reflect.jvm.internal.impl.protobuf.d.g((String) ref);
                this.string_ = b2;
                return b2;
            }

            public boolean hasOperation() {
                return (this.bitField0_ & 8) == 8;
            }

            public C0394c getOperation() {
                return this.operation_;
            }

            public List<Integer> getSubstringIndexList() {
                return this.substringIndex_;
            }

            public int getSubstringIndexCount() {
                return this.substringIndex_.size();
            }

            public List<Integer> getReplaceCharList() {
                return this.replaceChar_;
            }

            public int getReplaceCharCount() {
                return this.replaceChar_.size();
            }

            private void b() {
                this.range_ = 1;
                this.predefinedIndex_ = 0;
                this.string_ = "";
                this.operation_ = C0394c.NONE;
                this.substringIndex_ = Collections.emptyList();
                this.replaceChar_ = Collections.emptyList();
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
                if ((this.bitField0_ & 1) == 1) {
                    output.a0(1, this.range_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.a0(2, this.predefinedIndex_);
                }
                if ((this.bitField0_ & 8) == 8) {
                    output.S(3, this.operation_.getNumber());
                }
                if (getSubstringIndexList().size() > 0) {
                    output.o0(34);
                    output.o0(this.substringIndexMemoizedSerializedSize);
                }
                for (int i = 0; i < this.substringIndex_.size(); i++) {
                    output.b0(this.substringIndex_.get(i).intValue());
                }
                if (getReplaceCharList().size() > 0) {
                    output.o0(42);
                    output.o0(this.replaceCharMemoizedSerializedSize);
                }
                for (int i2 = 0; i2 < this.replaceChar_.size(); i2++) {
                    output.b0(this.replaceChar_.get(i2).intValue());
                }
                if ((this.bitField0_ & 4) == 4) {
                    output.O(6, getStringBytes());
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
                    size2 = 0 + CodedOutputStream.o(1, this.range_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    size2 += CodedOutputStream.o(2, this.predefinedIndex_);
                }
                if ((this.bitField0_ & 8) == 8) {
                    size2 += CodedOutputStream.h(3, this.operation_.getNumber());
                }
                int dataSize = 0;
                for (int i = 0; i < this.substringIndex_.size(); i++) {
                    dataSize += CodedOutputStream.p(this.substringIndex_.get(i).intValue());
                }
                int size3 = size2 + dataSize;
                if (!getSubstringIndexList().isEmpty()) {
                    size3 = size3 + 1 + CodedOutputStream.p(dataSize);
                }
                this.substringIndexMemoizedSerializedSize = dataSize;
                int dataSize2 = 0;
                for (int i2 = 0; i2 < this.replaceChar_.size(); i2++) {
                    dataSize2 += CodedOutputStream.p(this.replaceChar_.get(i2).intValue());
                }
                int size4 = size3 + dataSize2;
                if (!getReplaceCharList().isEmpty()) {
                    size4 = size4 + 1 + CodedOutputStream.p(dataSize2);
                }
                this.replaceCharMemoizedSerializedSize = dataSize2;
                if ((this.bitField0_ & 4) == 4) {
                    size4 += CodedOutputStream.d(6, getStringBytes());
                }
                int size5 = size4 + this.unknownFields.size();
                this.memoizedSerializedSize = size5;
                return size5;
            }

            public static b newBuilder() {
                return b.l();
            }

            public b newBuilderForType() {
                return newBuilder();
            }

            public static b newBuilder(c prototype) {
                return newBuilder().e(prototype);
            }

            public b toBuilder() {
                return newBuilder(this);
            }

            /* compiled from: JvmProtoBuf */
            public static final class b extends h.b<c, b> implements p {
                private int d;
                private int f = 1;
                private List<Integer> p0 = Collections.emptyList();
                private int q;
                private Object x = "";
                private C0394c y = C0394c.NONE;
                private List<Integer> z = Collections.emptyList();

                private b() {
                    o();
                }

                private void o() {
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
                public c build() {
                    c result = j();
                    if (result.isInitialized()) {
                        return result;
                    }
                    throw a.C0398a.b(result);
                }

                public c j() {
                    c result = new c((h.b) this);
                    int from_bitField0_ = this.d;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ = 0 | 1;
                    }
                    int unused = result.range_ = this.f;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    int unused2 = result.predefinedIndex_ = this.q;
                    if ((from_bitField0_ & 4) == 4) {
                        to_bitField0_ |= 4;
                    }
                    Object unused3 = result.string_ = this.x;
                    if ((from_bitField0_ & 8) == 8) {
                        to_bitField0_ |= 8;
                    }
                    C0394c unused4 = result.operation_ = this.y;
                    if ((this.d & 16) == 16) {
                        this.z = Collections.unmodifiableList(this.z);
                        this.d &= -17;
                    }
                    List unused5 = result.substringIndex_ = this.z;
                    if ((this.d & 32) == 32) {
                        this.p0 = Collections.unmodifiableList(this.p0);
                        this.d &= -33;
                    }
                    List unused6 = result.replaceChar_ = this.p0;
                    int unused7 = result.bitField0_ = to_bitField0_;
                    return result;
                }

                /* renamed from: p */
                public b e(c other) {
                    if (other == c.getDefaultInstance()) {
                        return this;
                    }
                    if (other.hasRange()) {
                        t(other.getRange());
                    }
                    if (other.hasPredefinedIndex()) {
                        s(other.getPredefinedIndex());
                    }
                    if (other.hasString()) {
                        this.d |= 4;
                        this.x = other.string_;
                    }
                    if (other.hasOperation()) {
                        r(other.getOperation());
                    }
                    if (!other.substringIndex_.isEmpty()) {
                        if (this.z.isEmpty()) {
                            this.z = other.substringIndex_;
                            this.d &= -17;
                        } else {
                            n();
                            this.z.addAll(other.substringIndex_);
                        }
                    }
                    if (!other.replaceChar_.isEmpty()) {
                        if (this.p0.isEmpty()) {
                            this.p0 = other.replaceChar_;
                            this.d &= -33;
                        } else {
                            m();
                            this.p0.addAll(other.replaceChar_);
                        }
                    }
                    f(d().b(other.unknownFields));
                    return this;
                }

                public final boolean isInitialized() {
                    return true;
                }

                /* renamed from: q */
                public b a(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
                    try {
                        c parsedMessage = c.PARSER.c(input, extensionRegistry);
                        if (parsedMessage != null) {
                            e(parsedMessage);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        c parsedMessage2 = (c) e.getUnfinishedMessage();
                        throw e;
                    } catch (Throwable th) {
                        if (0 != 0) {
                            e((c) null);
                        }
                        throw th;
                    }
                }

                public b t(int value) {
                    this.d |= 1;
                    this.f = value;
                    return this;
                }

                public b s(int value) {
                    this.d |= 2;
                    this.q = value;
                    return this;
                }

                public b r(C0394c value) {
                    if (value != null) {
                        this.d |= 8;
                        this.y = value;
                        return this;
                    }
                    throw new NullPointerException();
                }

                private void n() {
                    if ((this.d & 16) != 16) {
                        this.z = new ArrayList(this.z);
                        this.d |= 16;
                    }
                }

                private void m() {
                    if ((this.d & 32) != 32) {
                        this.p0 = new ArrayList(this.p0);
                        this.d |= 32;
                    }
                }
            }
        }

        public List<c> getRecordList() {
            return this.record_;
        }

        public List<Integer> getLocalNameList() {
            return this.localName_;
        }

        private void b() {
            this.record_ = Collections.emptyList();
            this.localName_ = Collections.emptyList();
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
            for (int i = 0; i < this.record_.size(); i++) {
                output.d0(1, this.record_.get(i));
            }
            if (getLocalNameList().size() > 0) {
                output.o0(42);
                output.o0(this.localNameMemoizedSerializedSize);
            }
            for (int i2 = 0; i2 < this.localName_.size(); i2++) {
                output.b0(this.localName_.get(i2).intValue());
            }
            output.i0(this.unknownFields);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            int size2 = 0;
            for (int i = 0; i < this.record_.size(); i++) {
                size2 += CodedOutputStream.s(1, this.record_.get(i));
            }
            int dataSize = 0;
            for (int i2 = 0; i2 < this.localName_.size(); i2++) {
                dataSize += CodedOutputStream.p(this.localName_.get(i2).intValue());
            }
            int size3 = size2 + dataSize;
            if (!getLocalNameList().isEmpty()) {
                size3 = size3 + 1 + CodedOutputStream.p(dataSize);
            }
            this.localNameMemoizedSerializedSize = dataSize;
            int size4 = size3 + this.unknownFields.size();
            this.memoizedSerializedSize = size4;
            return size4;
        }

        public static e parseDelimitedFrom(InputStream input, f extensionRegistry) {
            return PARSER.d(input, extensionRegistry);
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

        /* compiled from: JvmProtoBuf */
        public static final class b extends h.b<e, b> implements p {
            private int d;
            private List<c> f = Collections.emptyList();
            private List<Integer> q = Collections.emptyList();

            private b() {
                o();
            }

            private void o() {
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
                List unused = result.record_ = this.f;
                if ((this.d & 2) == 2) {
                    this.q = Collections.unmodifiableList(this.q);
                    this.d &= -3;
                }
                List unused2 = result.localName_ = this.q;
                return result;
            }

            /* renamed from: p */
            public b e(e other) {
                if (other == e.getDefaultInstance()) {
                    return this;
                }
                if (!other.record_.isEmpty()) {
                    if (this.f.isEmpty()) {
                        this.f = other.record_;
                        this.d &= -2;
                    } else {
                        n();
                        this.f.addAll(other.record_);
                    }
                }
                if (!other.localName_.isEmpty()) {
                    if (this.q.isEmpty()) {
                        this.q = other.localName_;
                        this.d &= -3;
                    } else {
                        m();
                        this.q.addAll(other.localName_);
                    }
                }
                f(d().b(other.unknownFields));
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            /* renamed from: q */
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

            private void n() {
                if ((this.d & 1) != 1) {
                    this.f = new ArrayList(this.f);
                    this.d |= 1;
                }
            }

            private void m() {
                if ((this.d & 2) != 2) {
                    this.q = new ArrayList(this.q);
                    this.d |= 2;
                }
            }
        }
    }

    /* compiled from: JvmProtoBuf */
    public static final class c extends h implements p {
        public static kotlin.reflect.jvm.internal.impl.protobuf.q<c> PARSER = new C0390a();
        private static final c c;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int desc_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        /* access modifiers changed from: private */
        public int name_;
        /* access modifiers changed from: private */
        public final kotlin.reflect.jvm.internal.impl.protobuf.d unknownFields;

        private c(h.b builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.d();
        }

        private c(boolean noInit) {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = kotlin.reflect.jvm.internal.impl.protobuf.d.c;
        }

        public static c getDefaultInstance() {
            return c;
        }

        public c getDefaultInstanceForType() {
            return c;
        }

        private c(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            b();
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
                            this.name_ = input.s();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.desc_ = input.s();
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

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.jvm.a$c$a  reason: collision with other inner class name */
        /* compiled from: JvmProtoBuf */
        public static final class C0390a extends kotlin.reflect.jvm.internal.impl.protobuf.b<c> {
            C0390a() {
            }

            /* renamed from: m */
            public c c(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
                return new c(input, extensionRegistry);
            }
        }

        static {
            c cVar = new c(true);
            c = cVar;
            cVar.b();
        }

        public kotlin.reflect.jvm.internal.impl.protobuf.q<c> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getName() {
            return this.name_;
        }

        public boolean hasDesc() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getDesc() {
            return this.desc_;
        }

        private void b() {
            this.name_ = 0;
            this.desc_ = 0;
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
            if ((this.bitField0_ & 1) == 1) {
                output.a0(1, this.name_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.a0(2, this.desc_);
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
                size2 = 0 + CodedOutputStream.o(1, this.name_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.o(2, this.desc_);
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

        public static b newBuilder(c prototype) {
            return newBuilder().e(prototype);
        }

        public b toBuilder() {
            return newBuilder(this);
        }

        /* compiled from: JvmProtoBuf */
        public static final class b extends h.b<c, b> implements p {
            private int d;
            private int f;
            private int q;

            private b() {
                m();
            }

            private void m() {
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
            public c build() {
                c result = j();
                if (result.isInitialized()) {
                    return result;
                }
                throw a.C0398a.b(result);
            }

            public c j() {
                c result = new c((h.b) this);
                int from_bitField0_ = this.d;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                int unused = result.name_ = this.f;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                int unused2 = result.desc_ = this.q;
                int unused3 = result.bitField0_ = to_bitField0_;
                return result;
            }

            /* renamed from: n */
            public b e(c other) {
                if (other == c.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    q(other.getName());
                }
                if (other.hasDesc()) {
                    p(other.getDesc());
                }
                f(d().b(other.unknownFields));
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            /* renamed from: o */
            public b a(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
                try {
                    c parsedMessage = c.PARSER.c(input, extensionRegistry);
                    if (parsedMessage != null) {
                        e(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    c parsedMessage2 = (c) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (0 != 0) {
                        e((c) null);
                    }
                    throw th;
                }
            }

            public b q(int value) {
                this.d |= 1;
                this.f = value;
                return this;
            }

            public b p(int value) {
                this.d |= 2;
                this.q = value;
                return this;
            }
        }
    }

    /* compiled from: JvmProtoBuf */
    public static final class b extends h implements p {
        public static kotlin.reflect.jvm.internal.impl.protobuf.q<b> PARSER = new C0388a();
        private static final b c;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public int desc_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        /* access modifiers changed from: private */
        public int name_;
        /* access modifiers changed from: private */
        public final kotlin.reflect.jvm.internal.impl.protobuf.d unknownFields;

        private b(h.b builder) {
            super(builder);
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.d();
        }

        private b(boolean noInit) {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = kotlin.reflect.jvm.internal.impl.protobuf.d.c;
        }

        public static b getDefaultInstance() {
            return c;
        }

        public b getDefaultInstanceForType() {
            return c;
        }

        private b(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            b();
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
                            this.name_ = input.s();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.desc_ = input.s();
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

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.jvm.a$b$a  reason: collision with other inner class name */
        /* compiled from: JvmProtoBuf */
        public static final class C0388a extends kotlin.reflect.jvm.internal.impl.protobuf.b<b> {
            C0388a() {
            }

            /* renamed from: m */
            public b c(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
                return new b(input, extensionRegistry);
            }
        }

        static {
            b bVar = new b(true);
            c = bVar;
            bVar.b();
        }

        public kotlin.reflect.jvm.internal.impl.protobuf.q<b> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getName() {
            return this.name_;
        }

        public boolean hasDesc() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getDesc() {
            return this.desc_;
        }

        private void b() {
            this.name_ = 0;
            this.desc_ = 0;
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
            if ((this.bitField0_ & 1) == 1) {
                output.a0(1, this.name_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.a0(2, this.desc_);
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
                size2 = 0 + CodedOutputStream.o(1, this.name_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.o(2, this.desc_);
            }
            int size3 = size2 + this.unknownFields.size();
            this.memoizedSerializedSize = size3;
            return size3;
        }

        public static C0389b newBuilder() {
            return C0389b.l();
        }

        public C0389b newBuilderForType() {
            return newBuilder();
        }

        public static C0389b newBuilder(b prototype) {
            return newBuilder().e(prototype);
        }

        public C0389b toBuilder() {
            return newBuilder(this);
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.jvm.a$b$b  reason: collision with other inner class name */
        /* compiled from: JvmProtoBuf */
        public static final class C0389b extends h.b<b, C0389b> implements p {
            private int d;
            private int f;
            private int q;

            private C0389b() {
                m();
            }

            private void m() {
            }

            /* access modifiers changed from: private */
            public static C0389b l() {
                return new C0389b();
            }

            /* renamed from: k */
            public C0389b clone() {
                return l().e(j());
            }

            /* renamed from: i */
            public b build() {
                b result = j();
                if (result.isInitialized()) {
                    return result;
                }
                throw a.C0398a.b(result);
            }

            public b j() {
                b result = new b((h.b) this);
                int from_bitField0_ = this.d;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                int unused = result.name_ = this.f;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                int unused2 = result.desc_ = this.q;
                int unused3 = result.bitField0_ = to_bitField0_;
                return result;
            }

            /* renamed from: n */
            public C0389b e(b other) {
                if (other == b.getDefaultInstance()) {
                    return this;
                }
                if (other.hasName()) {
                    q(other.getName());
                }
                if (other.hasDesc()) {
                    p(other.getDesc());
                }
                f(d().b(other.unknownFields));
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            /* renamed from: o */
            public C0389b a(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
                try {
                    b parsedMessage = b.PARSER.c(input, extensionRegistry);
                    if (parsedMessage != null) {
                        e(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    b parsedMessage2 = (b) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (0 != 0) {
                        e((b) null);
                    }
                    throw th;
                }
            }

            public C0389b q(int value) {
                this.d |= 1;
                this.f = value;
                return this;
            }

            public C0389b p(int value) {
                this.d |= 2;
                this.q = value;
                return this;
            }
        }
    }

    /* compiled from: JvmProtoBuf */
    public static final class d extends h implements p {
        public static kotlin.reflect.jvm.internal.impl.protobuf.q<d> PARSER = new C0391a();
        private static final d c;
        /* access modifiers changed from: private */
        public int bitField0_;
        /* access modifiers changed from: private */
        public b field_;
        /* access modifiers changed from: private */
        public c getter_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        /* access modifiers changed from: private */
        public c setter_;
        /* access modifiers changed from: private */
        public c syntheticMethod_;
        /* access modifiers changed from: private */
        public final kotlin.reflect.jvm.internal.impl.protobuf.d unknownFields;

        private d(h.b builder) {
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

        private d(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
            this.memoizedIsInitialized = -1;
            this.memoizedSerializedSize = -1;
            b();
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
                        case 10:
                            b.C0389b subBuilder = (this.bitField0_ & 1) == 1 ? this.field_.toBuilder() : null;
                            b bVar = (b) input.u(b.PARSER, extensionRegistry);
                            this.field_ = bVar;
                            if (subBuilder != null) {
                                subBuilder.e(bVar);
                                this.field_ = subBuilder.j();
                            }
                            this.bitField0_ |= 1;
                            break;
                        case 18:
                            c.b subBuilder2 = (this.bitField0_ & 2) == 2 ? this.syntheticMethod_.toBuilder() : null;
                            c cVar = (c) input.u(c.PARSER, extensionRegistry);
                            this.syntheticMethod_ = cVar;
                            if (subBuilder2 != null) {
                                subBuilder2.e(cVar);
                                this.syntheticMethod_ = subBuilder2.j();
                            }
                            this.bitField0_ |= 2;
                            break;
                        case 26:
                            c.b subBuilder3 = (this.bitField0_ & 4) == 4 ? this.getter_.toBuilder() : null;
                            c cVar2 = (c) input.u(c.PARSER, extensionRegistry);
                            this.getter_ = cVar2;
                            if (subBuilder3 != null) {
                                subBuilder3.e(cVar2);
                                this.getter_ = subBuilder3.j();
                            }
                            this.bitField0_ |= 4;
                            break;
                        case 34:
                            c.b subBuilder4 = (this.bitField0_ & 8) == 8 ? this.setter_.toBuilder() : null;
                            c cVar3 = (c) input.u(c.PARSER, extensionRegistry);
                            this.setter_ = cVar3;
                            if (subBuilder4 != null) {
                                subBuilder4.e(cVar3);
                                this.setter_ = subBuilder4.j();
                            }
                            this.bitField0_ |= 8;
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

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.jvm.a$d$a  reason: collision with other inner class name */
        /* compiled from: JvmProtoBuf */
        public static final class C0391a extends kotlin.reflect.jvm.internal.impl.protobuf.b<d> {
            C0391a() {
            }

            /* renamed from: m */
            public d c(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
                return new d(input, extensionRegistry);
            }
        }

        static {
            d dVar = new d(true);
            c = dVar;
            dVar.b();
        }

        public kotlin.reflect.jvm.internal.impl.protobuf.q<d> getParserForType() {
            return PARSER;
        }

        public boolean hasField() {
            return (this.bitField0_ & 1) == 1;
        }

        public b getField() {
            return this.field_;
        }

        public boolean hasSyntheticMethod() {
            return (this.bitField0_ & 2) == 2;
        }

        public c getSyntheticMethod() {
            return this.syntheticMethod_;
        }

        public boolean hasGetter() {
            return (this.bitField0_ & 4) == 4;
        }

        public c getGetter() {
            return this.getter_;
        }

        public boolean hasSetter() {
            return (this.bitField0_ & 8) == 8;
        }

        public c getSetter() {
            return this.setter_;
        }

        private void b() {
            this.field_ = b.getDefaultInstance();
            this.syntheticMethod_ = c.getDefaultInstance();
            this.getter_ = c.getDefaultInstance();
            this.setter_ = c.getDefaultInstance();
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
            if ((this.bitField0_ & 1) == 1) {
                output.d0(1, this.field_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.d0(2, this.syntheticMethod_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.d0(3, this.getter_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.d0(4, this.setter_);
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
                size2 = 0 + CodedOutputStream.s(1, this.field_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size2 += CodedOutputStream.s(2, this.syntheticMethod_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size2 += CodedOutputStream.s(3, this.getter_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size2 += CodedOutputStream.s(4, this.setter_);
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

        public static b newBuilder(d prototype) {
            return newBuilder().e(prototype);
        }

        public b toBuilder() {
            return newBuilder(this);
        }

        /* compiled from: JvmProtoBuf */
        public static final class b extends h.b<d, b> implements p {
            private int d;
            private b f = b.getDefaultInstance();
            private c q = c.getDefaultInstance();
            private c x = c.getDefaultInstance();
            private c y = c.getDefaultInstance();

            private b() {
                m();
            }

            private void m() {
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
            public d build() {
                d result = j();
                if (result.isInitialized()) {
                    return result;
                }
                throw a.C0398a.b(result);
            }

            public d j() {
                d result = new d((h.b) this);
                int from_bitField0_ = this.d;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                b unused = result.field_ = this.f;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                c unused2 = result.syntheticMethod_ = this.q;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                c unused3 = result.getter_ = this.x;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                c unused4 = result.setter_ = this.y;
                int unused5 = result.bitField0_ = to_bitField0_;
                return result;
            }

            /* renamed from: o */
            public b e(d other) {
                if (other == d.getDefaultInstance()) {
                    return this;
                }
                if (other.hasField()) {
                    n(other.getField());
                }
                if (other.hasSyntheticMethod()) {
                    s(other.getSyntheticMethod());
                }
                if (other.hasGetter()) {
                    q(other.getGetter());
                }
                if (other.hasSetter()) {
                    r(other.getSetter());
                }
                f(d().b(other.unknownFields));
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            /* renamed from: p */
            public b a(kotlin.reflect.jvm.internal.impl.protobuf.e input, f extensionRegistry) {
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

            public b n(b value) {
                if ((this.d & 1) != 1 || this.f == b.getDefaultInstance()) {
                    this.f = value;
                } else {
                    this.f = b.newBuilder(this.f).e(value).j();
                }
                this.d |= 1;
                return this;
            }

            public b s(c value) {
                if ((this.d & 2) != 2 || this.q == c.getDefaultInstance()) {
                    this.q = value;
                } else {
                    this.q = c.newBuilder(this.q).e(value).j();
                }
                this.d |= 2;
                return this;
            }

            public b q(c value) {
                if ((this.d & 4) != 4 || this.x == c.getDefaultInstance()) {
                    this.x = value;
                } else {
                    this.x = c.newBuilder(this.x).e(value).j();
                }
                this.d |= 4;
                return this;
            }

            public b r(c value) {
                if ((this.d & 8) != 8 || this.y == c.getDefaultInstance()) {
                    this.y = value;
                } else {
                    this.y = c.newBuilder(this.y).e(value).j();
                }
                this.d |= 8;
                return this;
            }
        }
    }

    static {
        kotlin.reflect.jvm.internal.impl.metadata.d defaultInstance = kotlin.reflect.jvm.internal.impl.metadata.d.getDefaultInstance();
        c defaultInstance2 = c.getDefaultInstance();
        c defaultInstance3 = c.getDefaultInstance();
        w.b bVar = w.b.MESSAGE;
        a = h.newSingularGeneratedExtension(defaultInstance, defaultInstance2, defaultInstance3, (i.b<?>) null, 100, bVar, c.class);
        w.b bVar2 = bVar;
        b = h.newSingularGeneratedExtension(kotlin.reflect.jvm.internal.impl.metadata.i.getDefaultInstance(), c.getDefaultInstance(), c.getDefaultInstance(), (i.b<?>) null, 100, bVar2, c.class);
        kotlin.reflect.jvm.internal.impl.metadata.i defaultInstance4 = kotlin.reflect.jvm.internal.impl.metadata.i.getDefaultInstance();
        w.b bVar3 = w.b.INT32;
        c = h.newSingularGeneratedExtension(defaultInstance4, null, (o) null, (i.b<?>) null, 101, bVar3, Integer.class);
        d = h.newSingularGeneratedExtension(n.getDefaultInstance(), d.getDefaultInstance(), d.getDefaultInstance(), (i.b<?>) null, 100, bVar2, d.class);
        e = h.newSingularGeneratedExtension(n.getDefaultInstance(), null, (o) null, (i.b<?>) null, 101, bVar3, Integer.class);
        f = h.newRepeatedGeneratedExtension(q.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 100, bVar, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        h = h.newRepeatedGeneratedExtension(s.getDefaultInstance(), kotlin.reflect.jvm.internal.impl.metadata.b.getDefaultInstance(), (i.b<?>) null, 100, bVar, false, kotlin.reflect.jvm.internal.impl.metadata.b.class);
        i = h.newSingularGeneratedExtension(kotlin.reflect.jvm.internal.impl.metadata.c.getDefaultInstance(), null, (o) null, (i.b<?>) null, 101, bVar3, Integer.class);
        j = h.newRepeatedGeneratedExtension(kotlin.reflect.jvm.internal.impl.metadata.c.getDefaultInstance(), n.getDefaultInstance(), (i.b<?>) null, 102, bVar, false, n.class);
        w.b bVar4 = bVar3;
        k = h.newSingularGeneratedExtension(kotlin.reflect.jvm.internal.impl.metadata.c.getDefaultInstance(), null, (o) null, (i.b<?>) null, 103, bVar4, Integer.class);
        l = h.newSingularGeneratedExtension(l.getDefaultInstance(), null, (o) null, (i.b<?>) null, 101, bVar4, Integer.class);
        m = h.newRepeatedGeneratedExtension(l.getDefaultInstance(), n.getDefaultInstance(), (i.b<?>) null, 102, bVar, false, n.class);
    }
}
