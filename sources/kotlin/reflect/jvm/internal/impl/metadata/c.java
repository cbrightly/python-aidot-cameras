package kotlin.reflect.jvm.internal.impl.metadata;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
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
import kotlin.reflect.jvm.internal.impl.protobuf.i;
import kotlin.reflect.jvm.internal.impl.protobuf.p;
import kotlin.reflect.jvm.internal.impl.protobuf.q;

/* compiled from: ProtoBuf */
public final class c extends h.d<c> implements p {
    public static q<c> PARSER = new a();
    private static final c c;
    /* access modifiers changed from: private */
    public int bitField0_;
    /* access modifiers changed from: private */
    public int companionObjectName_;
    /* access modifiers changed from: private */
    public List<d> constructor_;
    /* access modifiers changed from: private */
    public List<g> enumEntry_;
    /* access modifiers changed from: private */
    public int flags_;
    /* access modifiers changed from: private */
    public int fqName_;
    /* access modifiers changed from: private */
    public List<i> function_;
    private byte memoizedIsInitialized;
    private int memoizedSerializedSize;
    private int nestedClassNameMemoizedSerializedSize;
    /* access modifiers changed from: private */
    public List<Integer> nestedClassName_;
    /* access modifiers changed from: private */
    public List<n> property_;
    private int sealedSubclassFqNameMemoizedSerializedSize;
    /* access modifiers changed from: private */
    public List<Integer> sealedSubclassFqName_;
    private int supertypeIdMemoizedSerializedSize;
    /* access modifiers changed from: private */
    public List<Integer> supertypeId_;
    /* access modifiers changed from: private */
    public List<q> supertype_;
    /* access modifiers changed from: private */
    public List<r> typeAlias_;
    /* access modifiers changed from: private */
    public List<s> typeParameter_;
    /* access modifiers changed from: private */
    public t typeTable_;
    /* access modifiers changed from: private */
    public final d unknownFields;
    /* access modifiers changed from: private */
    public w versionRequirementTable_;
    /* access modifiers changed from: private */
    public List<Integer> versionRequirement_;

    private c(h.c<c, ?> builder) {
        super(builder);
        this.supertypeIdMemoizedSerializedSize = -1;
        this.nestedClassNameMemoizedSerializedSize = -1;
        this.sealedSubclassFqNameMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = builder.d();
    }

    private c(boolean noInit) {
        this.supertypeIdMemoizedSerializedSize = -1;
        this.nestedClassNameMemoizedSerializedSize = -1;
        this.sealedSubclassFqNameMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        this.unknownFields = d.c;
    }

    public static c getDefaultInstance() {
        return c;
    }

    public c getDefaultInstanceForType() {
        return c;
    }

    private c(e input, f extensionRegistry) {
        boolean z;
        w.b subBuilder;
        e eVar = input;
        f fVar = extensionRegistry;
        this.supertypeIdMemoizedSerializedSize = -1;
        this.nestedClassNameMemoizedSerializedSize = -1;
        this.sealedSubclassFqNameMemoizedSerializedSize = -1;
        this.memoizedIsInitialized = -1;
        this.memoizedSerializedSize = -1;
        c();
        d.b unknownFieldsOutput = d.r();
        CodedOutputStream unknownFieldsCodedOutput = CodedOutputStream.J(unknownFieldsOutput, 1);
        int mutable_bitField0_ = 0;
        boolean done = false;
        while (!done) {
            try {
                int tag = input.K();
                switch (tag) {
                    case 0:
                        z = true;
                        done = true;
                        break;
                    case 8:
                        z = true;
                        this.bitField0_ |= 1;
                        this.flags_ = input.s();
                        break;
                    case 16:
                        if ((mutable_bitField0_ & 32) != 32) {
                            this.supertypeId_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        this.supertypeId_.add(Integer.valueOf(input.s()));
                        z = true;
                        break;
                    case 18:
                        int limit = eVar.j(input.A());
                        if ((mutable_bitField0_ & 32) != 32 && input.e() > 0) {
                            this.supertypeId_ = new ArrayList();
                            mutable_bitField0_ |= 32;
                        }
                        while (input.e() > 0) {
                            this.supertypeId_.add(Integer.valueOf(input.s()));
                        }
                        eVar.i(limit);
                        z = true;
                        break;
                    case 24:
                        this.bitField0_ |= 2;
                        this.fqName_ = input.s();
                        z = true;
                        break;
                    case 32:
                        this.bitField0_ |= 4;
                        this.companionObjectName_ = input.s();
                        z = true;
                        break;
                    case 42:
                        if ((mutable_bitField0_ & 8) != 8) {
                            this.typeParameter_ = new ArrayList();
                            mutable_bitField0_ |= 8;
                        }
                        this.typeParameter_.add(eVar.u(s.PARSER, fVar));
                        z = true;
                        break;
                    case 50:
                        if ((mutable_bitField0_ & 16) != 16) {
                            this.supertype_ = new ArrayList();
                            mutable_bitField0_ |= 16;
                        }
                        this.supertype_.add(eVar.u(q.PARSER, fVar));
                        z = true;
                        break;
                    case 56:
                        if ((mutable_bitField0_ & 64) != 64) {
                            this.nestedClassName_ = new ArrayList();
                            mutable_bitField0_ |= 64;
                        }
                        this.nestedClassName_.add(Integer.valueOf(input.s()));
                        z = true;
                        break;
                    case 58:
                        int limit2 = eVar.j(input.A());
                        if ((mutable_bitField0_ & 64) != 64 && input.e() > 0) {
                            this.nestedClassName_ = new ArrayList();
                            mutable_bitField0_ |= 64;
                        }
                        while (input.e() > 0) {
                            this.nestedClassName_.add(Integer.valueOf(input.s()));
                        }
                        eVar.i(limit2);
                        z = true;
                        break;
                    case 66:
                        if ((mutable_bitField0_ & 128) != 128) {
                            this.constructor_ = new ArrayList();
                            mutable_bitField0_ |= 128;
                        }
                        this.constructor_.add(eVar.u(d.PARSER, fVar));
                        z = true;
                        break;
                    case 74:
                        if ((mutable_bitField0_ & 256) != 256) {
                            this.function_ = new ArrayList();
                            mutable_bitField0_ |= 256;
                        }
                        this.function_.add(eVar.u(i.PARSER, fVar));
                        z = true;
                        break;
                    case 82:
                        if ((mutable_bitField0_ & 512) != 512) {
                            this.property_ = new ArrayList();
                            mutable_bitField0_ |= 512;
                        }
                        this.property_.add(eVar.u(n.PARSER, fVar));
                        z = true;
                        break;
                    case 90:
                        if ((mutable_bitField0_ & 1024) != 1024) {
                            this.typeAlias_ = new ArrayList();
                            mutable_bitField0_ |= 1024;
                        }
                        this.typeAlias_.add(eVar.u(r.PARSER, fVar));
                        z = true;
                        break;
                    case 106:
                        if ((mutable_bitField0_ & 2048) != 2048) {
                            this.enumEntry_ = new ArrayList();
                            mutable_bitField0_ |= 2048;
                        }
                        this.enumEntry_.add(eVar.u(g.PARSER, fVar));
                        z = true;
                        break;
                    case 128:
                        if ((mutable_bitField0_ & 4096) != 4096) {
                            this.sealedSubclassFqName_ = new ArrayList();
                            mutable_bitField0_ |= 4096;
                        }
                        this.sealedSubclassFqName_.add(Integer.valueOf(input.s()));
                        z = true;
                        break;
                    case NeedPermissionEvent.PER_IPC_ALBUM_PERM:
                        int limit3 = eVar.j(input.A());
                        if ((mutable_bitField0_ & 4096) != 4096 && input.e() > 0) {
                            this.sealedSubclassFqName_ = new ArrayList();
                            mutable_bitField0_ |= 4096;
                        }
                        while (input.e() > 0) {
                            this.sealedSubclassFqName_.add(Integer.valueOf(input.s()));
                        }
                        eVar.i(limit3);
                        z = true;
                        break;
                    case 242:
                        t.b subBuilder2 = (this.bitField0_ & 8) == 8 ? this.typeTable_.toBuilder() : null;
                        t tVar = (t) eVar.u(t.PARSER, fVar);
                        this.typeTable_ = tVar;
                        if (subBuilder2 != null) {
                            subBuilder2.e(tVar);
                            this.typeTable_ = subBuilder2.j();
                        }
                        this.bitField0_ |= 8;
                        z = true;
                        break;
                    case 248:
                        if ((mutable_bitField0_ & 16384) != 16384) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 16384;
                        }
                        this.versionRequirement_.add(Integer.valueOf(input.s()));
                        z = true;
                        break;
                    case 250:
                        int limit4 = eVar.j(input.A());
                        if ((mutable_bitField0_ & 16384) != 16384 && input.e() > 0) {
                            this.versionRequirement_ = new ArrayList();
                            mutable_bitField0_ |= 16384;
                        }
                        while (input.e() > 0) {
                            this.versionRequirement_.add(Integer.valueOf(input.s()));
                        }
                        eVar.i(limit4);
                        z = true;
                        break;
                    case CustomCameraView.BUTTON_STATE_ONLY_RECORDER:
                        if ((this.bitField0_ & 16) == 16) {
                            subBuilder = this.versionRequirementTable_.toBuilder();
                        } else {
                            subBuilder = null;
                        }
                        w wVar = (w) eVar.u(w.PARSER, fVar);
                        this.versionRequirementTable_ = wVar;
                        if (subBuilder != null) {
                            subBuilder.e(wVar);
                            this.versionRequirementTable_ = subBuilder.j();
                        }
                        this.bitField0_ |= 16;
                        z = true;
                        break;
                    default:
                        z = true;
                        if (parseUnknownField(eVar, unknownFieldsCodedOutput, fVar, tag)) {
                            break;
                        } else {
                            done = true;
                            break;
                        }
                }
                boolean z2 = z;
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (IOException e2) {
                throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
            } catch (Throwable th) {
                Throwable th2 = th;
                if ((mutable_bitField0_ & 32) == 32) {
                    this.supertypeId_ = Collections.unmodifiableList(this.supertypeId_);
                }
                if ((mutable_bitField0_ & 8) == 8) {
                    this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
                }
                if ((mutable_bitField0_ & 16) == 16) {
                    this.supertype_ = Collections.unmodifiableList(this.supertype_);
                }
                if ((mutable_bitField0_ & 64) == 64) {
                    this.nestedClassName_ = Collections.unmodifiableList(this.nestedClassName_);
                }
                if ((mutable_bitField0_ & 128) == 128) {
                    this.constructor_ = Collections.unmodifiableList(this.constructor_);
                }
                if ((mutable_bitField0_ & 256) == 256) {
                    this.function_ = Collections.unmodifiableList(this.function_);
                }
                if ((mutable_bitField0_ & 512) == 512) {
                    this.property_ = Collections.unmodifiableList(this.property_);
                }
                if ((mutable_bitField0_ & 1024) == 1024) {
                    this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
                }
                if ((mutable_bitField0_ & 2048) == 2048) {
                    this.enumEntry_ = Collections.unmodifiableList(this.enumEntry_);
                }
                if ((mutable_bitField0_ & 4096) == 4096) {
                    this.sealedSubclassFqName_ = Collections.unmodifiableList(this.sealedSubclassFqName_);
                }
                if ((mutable_bitField0_ & 16384) == 16384) {
                    this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
                }
                try {
                    unknownFieldsCodedOutput.I();
                } catch (IOException e3) {
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    this.unknownFields = unknownFieldsOutput.j();
                    throw th4;
                }
                this.unknownFields = unknownFieldsOutput.j();
                makeExtensionsImmutable();
                throw th2;
            }
        }
        if ((mutable_bitField0_ & 32) == 32) {
            this.supertypeId_ = Collections.unmodifiableList(this.supertypeId_);
        }
        if ((mutable_bitField0_ & 8) == 8) {
            this.typeParameter_ = Collections.unmodifiableList(this.typeParameter_);
        }
        if ((mutable_bitField0_ & 16) == 16) {
            this.supertype_ = Collections.unmodifiableList(this.supertype_);
        }
        if ((mutable_bitField0_ & 64) == 64) {
            this.nestedClassName_ = Collections.unmodifiableList(this.nestedClassName_);
        }
        if ((mutable_bitField0_ & 128) == 128) {
            this.constructor_ = Collections.unmodifiableList(this.constructor_);
        }
        if ((mutable_bitField0_ & 256) == 256) {
            this.function_ = Collections.unmodifiableList(this.function_);
        }
        if ((mutable_bitField0_ & 512) == 512) {
            this.property_ = Collections.unmodifiableList(this.property_);
        }
        if ((mutable_bitField0_ & 1024) == 1024) {
            this.typeAlias_ = Collections.unmodifiableList(this.typeAlias_);
        }
        if ((mutable_bitField0_ & 2048) == 2048) {
            this.enumEntry_ = Collections.unmodifiableList(this.enumEntry_);
        }
        if ((mutable_bitField0_ & 4096) == 4096) {
            this.sealedSubclassFqName_ = Collections.unmodifiableList(this.sealedSubclassFqName_);
        }
        if ((mutable_bitField0_ & 16384) == 16384) {
            this.versionRequirement_ = Collections.unmodifiableList(this.versionRequirement_);
        }
        try {
            unknownFieldsCodedOutput.I();
        } catch (IOException e4) {
        } catch (Throwable th5) {
            Throwable th6 = th5;
            this.unknownFields = unknownFieldsOutput.j();
            throw th6;
        }
        this.unknownFields = unknownFieldsOutput.j();
        makeExtensionsImmutable();
    }

    /* compiled from: ProtoBuf */
    public static final class a extends kotlin.reflect.jvm.internal.impl.protobuf.b<c> {
        a() {
        }

        /* renamed from: m */
        public c c(e input, f extensionRegistry) {
            return new c(input, extensionRegistry);
        }
    }

    static {
        c cVar = new c(true);
        c = cVar;
        cVar.c();
    }

    public q<c> getParserForType() {
        return PARSER;
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.c$c  reason: collision with other inner class name */
    /* compiled from: ProtoBuf */
    public enum C0384c implements i.a {
        CLASS(0, 0),
        INTERFACE(1, 1),
        ENUM_CLASS(2, 2),
        ENUM_ENTRY(3, 3),
        ANNOTATION_CLASS(4, 4),
        OBJECT(5, 5),
        COMPANION_OBJECT(6, 6);
        
        private static i.b<C0384c> c;
        private final int value;

        static {
            c = new a();
        }

        public final int getNumber() {
            return this.value;
        }

        public static C0384c valueOf(int value2) {
            switch (value2) {
                case 0:
                    return CLASS;
                case 1:
                    return INTERFACE;
                case 2:
                    return ENUM_CLASS;
                case 3:
                    return ENUM_ENTRY;
                case 4:
                    return ANNOTATION_CLASS;
                case 5:
                    return OBJECT;
                case 6:
                    return COMPANION_OBJECT;
                default:
                    return null;
            }
        }

        /* renamed from: kotlin.reflect.jvm.internal.impl.metadata.c$c$a */
        /* compiled from: ProtoBuf */
        public static final class a implements i.b<C0384c> {
            a() {
            }

            /* renamed from: b */
            public C0384c a(int number) {
                return C0384c.valueOf(number);
            }
        }

        private C0384c(int index, int value2) {
            this.value = value2;
        }
    }

    public boolean hasFlags() {
        return (this.bitField0_ & 1) == 1;
    }

    public int getFlags() {
        return this.flags_;
    }

    public boolean hasFqName() {
        return (this.bitField0_ & 2) == 2;
    }

    public int getFqName() {
        return this.fqName_;
    }

    public boolean hasCompanionObjectName() {
        return (this.bitField0_ & 4) == 4;
    }

    public int getCompanionObjectName() {
        return this.companionObjectName_;
    }

    public List<s> getTypeParameterList() {
        return this.typeParameter_;
    }

    public int getTypeParameterCount() {
        return this.typeParameter_.size();
    }

    public s getTypeParameter(int index) {
        return this.typeParameter_.get(index);
    }

    public List<q> getSupertypeList() {
        return this.supertype_;
    }

    public int getSupertypeCount() {
        return this.supertype_.size();
    }

    public q getSupertype(int index) {
        return this.supertype_.get(index);
    }

    public List<Integer> getSupertypeIdList() {
        return this.supertypeId_;
    }

    public List<Integer> getNestedClassNameList() {
        return this.nestedClassName_;
    }

    public List<d> getConstructorList() {
        return this.constructor_;
    }

    public int getConstructorCount() {
        return this.constructor_.size();
    }

    public d getConstructor(int index) {
        return this.constructor_.get(index);
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

    public List<g> getEnumEntryList() {
        return this.enumEntry_;
    }

    public int getEnumEntryCount() {
        return this.enumEntry_.size();
    }

    public g getEnumEntry(int index) {
        return this.enumEntry_.get(index);
    }

    public List<Integer> getSealedSubclassFqNameList() {
        return this.sealedSubclassFqName_;
    }

    public boolean hasTypeTable() {
        return (this.bitField0_ & 8) == 8;
    }

    public t getTypeTable() {
        return this.typeTable_;
    }

    public List<Integer> getVersionRequirementList() {
        return this.versionRequirement_;
    }

    public boolean hasVersionRequirementTable() {
        return (this.bitField0_ & 16) == 16;
    }

    public w getVersionRequirementTable() {
        return this.versionRequirementTable_;
    }

    private void c() {
        this.flags_ = 6;
        this.fqName_ = 0;
        this.companionObjectName_ = 0;
        this.typeParameter_ = Collections.emptyList();
        this.supertype_ = Collections.emptyList();
        this.supertypeId_ = Collections.emptyList();
        this.nestedClassName_ = Collections.emptyList();
        this.constructor_ = Collections.emptyList();
        this.function_ = Collections.emptyList();
        this.property_ = Collections.emptyList();
        this.typeAlias_ = Collections.emptyList();
        this.enumEntry_ = Collections.emptyList();
        this.sealedSubclassFqName_ = Collections.emptyList();
        this.typeTable_ = t.getDefaultInstance();
        this.versionRequirement_ = Collections.emptyList();
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
        if (!hasFqName()) {
            this.memoizedIsInitialized = 0;
            return false;
        }
        for (int i = 0; i < getTypeParameterCount(); i++) {
            if (!getTypeParameter(i).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        for (int i2 = 0; i2 < getSupertypeCount(); i2++) {
            if (!getSupertype(i2).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        for (int i3 = 0; i3 < getConstructorCount(); i3++) {
            if (!getConstructor(i3).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        for (int i4 = 0; i4 < getFunctionCount(); i4++) {
            if (!getFunction(i4).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        for (int i5 = 0; i5 < getPropertyCount(); i5++) {
            if (!getProperty(i5).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        for (int i6 = 0; i6 < getTypeAliasCount(); i6++) {
            if (!getTypeAlias(i6).isInitialized()) {
                this.memoizedIsInitialized = 0;
                return false;
            }
        }
        for (int i7 = 0; i7 < getEnumEntryCount(); i7++) {
            if (!getEnumEntry(i7).isInitialized()) {
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
        GeneratedMessageLite.ExtendableMessage<ProtoBuf.Class>.ExtensionWriter extensionWriter = newExtensionWriter();
        if ((this.bitField0_ & 1) == 1) {
            output.a0(1, this.flags_);
        }
        if (getSupertypeIdList().size() > 0) {
            output.o0(18);
            output.o0(this.supertypeIdMemoizedSerializedSize);
        }
        for (int i = 0; i < this.supertypeId_.size(); i++) {
            output.b0(this.supertypeId_.get(i).intValue());
        }
        if ((this.bitField0_ & 2) == 2) {
            output.a0(3, this.fqName_);
        }
        if ((this.bitField0_ & 4) == 4) {
            output.a0(4, this.companionObjectName_);
        }
        for (int i2 = 0; i2 < this.typeParameter_.size(); i2++) {
            output.d0(5, this.typeParameter_.get(i2));
        }
        for (int i3 = 0; i3 < this.supertype_.size(); i3++) {
            output.d0(6, this.supertype_.get(i3));
        }
        if (getNestedClassNameList().size() > 0) {
            output.o0(58);
            output.o0(this.nestedClassNameMemoizedSerializedSize);
        }
        for (int i4 = 0; i4 < this.nestedClassName_.size(); i4++) {
            output.b0(this.nestedClassName_.get(i4).intValue());
        }
        for (int i5 = 0; i5 < this.constructor_.size(); i5++) {
            output.d0(8, this.constructor_.get(i5));
        }
        for (int i6 = 0; i6 < this.function_.size(); i6++) {
            output.d0(9, this.function_.get(i6));
        }
        for (int i7 = 0; i7 < this.property_.size(); i7++) {
            output.d0(10, this.property_.get(i7));
        }
        for (int i8 = 0; i8 < this.typeAlias_.size(); i8++) {
            output.d0(11, this.typeAlias_.get(i8));
        }
        for (int i9 = 0; i9 < this.enumEntry_.size(); i9++) {
            output.d0(13, this.enumEntry_.get(i9));
        }
        if (getSealedSubclassFqNameList().size() > 0) {
            output.o0(NeedPermissionEvent.PER_IPC_ALBUM_PERM);
            output.o0(this.sealedSubclassFqNameMemoizedSerializedSize);
        }
        for (int i10 = 0; i10 < this.sealedSubclassFqName_.size(); i10++) {
            output.b0(this.sealedSubclassFqName_.get(i10).intValue());
        }
        if ((this.bitField0_ & 8) == 8) {
            output.d0(30, this.typeTable_);
        }
        for (int i11 = 0; i11 < this.versionRequirement_.size(); i11++) {
            output.a0(31, this.versionRequirement_.get(i11).intValue());
        }
        if ((this.bitField0_ & 16) == 16) {
            output.d0(32, this.versionRequirementTable_);
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
        int dataSize = 0;
        for (int i = 0; i < this.supertypeId_.size(); i++) {
            dataSize += CodedOutputStream.p(this.supertypeId_.get(i).intValue());
        }
        int size3 = size2 + dataSize;
        if (!getSupertypeIdList().isEmpty()) {
            size3 = size3 + 1 + CodedOutputStream.p(dataSize);
        }
        this.supertypeIdMemoizedSerializedSize = dataSize;
        if ((this.bitField0_ & 2) == 2) {
            size3 += CodedOutputStream.o(3, this.fqName_);
        }
        if ((this.bitField0_ & 4) == 4) {
            size3 += CodedOutputStream.o(4, this.companionObjectName_);
        }
        for (int i2 = 0; i2 < this.typeParameter_.size(); i2++) {
            size3 += CodedOutputStream.s(5, this.typeParameter_.get(i2));
        }
        for (int i3 = 0; i3 < this.supertype_.size(); i3++) {
            size3 += CodedOutputStream.s(6, this.supertype_.get(i3));
        }
        int dataSize2 = 0;
        for (int i4 = 0; i4 < this.nestedClassName_.size(); i4++) {
            dataSize2 += CodedOutputStream.p(this.nestedClassName_.get(i4).intValue());
        }
        int size4 = size3 + dataSize2;
        if (!getNestedClassNameList().isEmpty()) {
            size4 = size4 + 1 + CodedOutputStream.p(dataSize2);
        }
        this.nestedClassNameMemoizedSerializedSize = dataSize2;
        for (int i5 = 0; i5 < this.constructor_.size(); i5++) {
            size4 += CodedOutputStream.s(8, this.constructor_.get(i5));
        }
        for (int i6 = 0; i6 < this.function_.size(); i6++) {
            size4 += CodedOutputStream.s(9, this.function_.get(i6));
        }
        for (int i7 = 0; i7 < this.property_.size(); i7++) {
            size4 += CodedOutputStream.s(10, this.property_.get(i7));
        }
        for (int i8 = 0; i8 < this.typeAlias_.size(); i8++) {
            size4 += CodedOutputStream.s(11, this.typeAlias_.get(i8));
        }
        for (int i9 = 0; i9 < this.enumEntry_.size(); i9++) {
            size4 += CodedOutputStream.s(13, this.enumEntry_.get(i9));
        }
        int dataSize3 = 0;
        for (int i10 = 0; i10 < this.sealedSubclassFqName_.size(); i10++) {
            dataSize3 += CodedOutputStream.p(this.sealedSubclassFqName_.get(i10).intValue());
        }
        int size5 = size4 + dataSize3;
        if (!getSealedSubclassFqNameList().isEmpty()) {
            size5 = size5 + 2 + CodedOutputStream.p(dataSize3);
        }
        this.sealedSubclassFqNameMemoizedSerializedSize = dataSize3;
        if ((this.bitField0_ & 8) == 8) {
            size5 += CodedOutputStream.s(30, this.typeTable_);
        }
        int dataSize4 = 0;
        for (int i11 = 0; i11 < this.versionRequirement_.size(); i11++) {
            dataSize4 += CodedOutputStream.p(this.versionRequirement_.get(i11).intValue());
        }
        int size6 = size5 + dataSize4 + (getVersionRequirementList().size() * 2);
        if ((this.bitField0_ & 16) == 16) {
            size6 += CodedOutputStream.s(32, this.versionRequirementTable_);
        }
        int size7 = size6 + extensionsSerializedSize() + this.unknownFields.size();
        this.memoizedSerializedSize = size7;
        return size7;
    }

    public static c parseFrom(InputStream input, f extensionRegistry) {
        return PARSER.a(input, extensionRegistry);
    }

    public static b newBuilder() {
        return b.q();
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

    /* compiled from: ProtoBuf */
    public static final class b extends h.c<c, b> implements p {
        private List<g> A4 = Collections.emptyList();
        private List<Integer> B4 = Collections.emptyList();
        private t C4 = t.getDefaultInstance();
        private List<Integer> D4 = Collections.emptyList();
        private w E4 = w.getDefaultInstance();
        private List<q> a1 = Collections.emptyList();
        private List<Integer> a2 = Collections.emptyList();
        private List<s> p0 = Collections.emptyList();
        private List<Integer> p1 = Collections.emptyList();
        private List<d> p2 = Collections.emptyList();
        private List<i> p3 = Collections.emptyList();
        private List<n> p4 = Collections.emptyList();
        private int q;
        private int x = 6;
        private int y;
        private int z;
        private List<r> z4 = Collections.emptyList();

        private b() {
            Z();
        }

        private void Z() {
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
        public c build() {
            c result = o();
            if (result.isInitialized()) {
                return result;
            }
            throw a.C0398a.b(result);
        }

        public c o() {
            c result = new c((h.c) this);
            int from_bitField0_ = this.q;
            int to_bitField0_ = 0;
            if ((from_bitField0_ & 1) == 1) {
                to_bitField0_ = 0 | 1;
            }
            int unused = result.flags_ = this.x;
            if ((from_bitField0_ & 2) == 2) {
                to_bitField0_ |= 2;
            }
            int unused2 = result.fqName_ = this.y;
            if ((from_bitField0_ & 4) == 4) {
                to_bitField0_ |= 4;
            }
            int unused3 = result.companionObjectName_ = this.z;
            if ((this.q & 8) == 8) {
                this.p0 = Collections.unmodifiableList(this.p0);
                this.q &= -9;
            }
            List unused4 = result.typeParameter_ = this.p0;
            if ((this.q & 16) == 16) {
                this.a1 = Collections.unmodifiableList(this.a1);
                this.q &= -17;
            }
            List unused5 = result.supertype_ = this.a1;
            if ((this.q & 32) == 32) {
                this.p1 = Collections.unmodifiableList(this.p1);
                this.q &= -33;
            }
            List unused6 = result.supertypeId_ = this.p1;
            if ((this.q & 64) == 64) {
                this.a2 = Collections.unmodifiableList(this.a2);
                this.q &= -65;
            }
            List unused7 = result.nestedClassName_ = this.a2;
            if ((this.q & 128) == 128) {
                this.p2 = Collections.unmodifiableList(this.p2);
                this.q &= -129;
            }
            List unused8 = result.constructor_ = this.p2;
            if ((this.q & 256) == 256) {
                this.p3 = Collections.unmodifiableList(this.p3);
                this.q &= -257;
            }
            List unused9 = result.function_ = this.p3;
            if ((this.q & 512) == 512) {
                this.p4 = Collections.unmodifiableList(this.p4);
                this.q &= -513;
            }
            List unused10 = result.property_ = this.p4;
            if ((this.q & 1024) == 1024) {
                this.z4 = Collections.unmodifiableList(this.z4);
                this.q &= -1025;
            }
            List unused11 = result.typeAlias_ = this.z4;
            if ((this.q & 2048) == 2048) {
                this.A4 = Collections.unmodifiableList(this.A4);
                this.q &= -2049;
            }
            List unused12 = result.enumEntry_ = this.A4;
            if ((this.q & 4096) == 4096) {
                this.B4 = Collections.unmodifiableList(this.B4);
                this.q &= -4097;
            }
            List unused13 = result.sealedSubclassFqName_ = this.B4;
            if ((from_bitField0_ & 8192) == 8192) {
                to_bitField0_ |= 8;
            }
            t unused14 = result.typeTable_ = this.C4;
            if ((this.q & 16384) == 16384) {
                this.D4 = Collections.unmodifiableList(this.D4);
                this.q &= -16385;
            }
            List unused15 = result.versionRequirement_ = this.D4;
            if ((from_bitField0_ & 32768) == 32768) {
                to_bitField0_ |= 16;
            }
            w unused16 = result.versionRequirementTable_ = this.E4;
            int unused17 = result.bitField0_ = to_bitField0_;
            return result;
        }

        /* renamed from: a0 */
        public b e(c other) {
            if (other == c.getDefaultInstance()) {
                return this;
            }
            if (other.hasFlags()) {
                f0(other.getFlags());
            }
            if (other.hasFqName()) {
                g0(other.getFqName());
            }
            if (other.hasCompanionObjectName()) {
                e0(other.getCompanionObjectName());
            }
            if (!other.typeParameter_.isEmpty()) {
                if (this.p0.isEmpty()) {
                    this.p0 = other.typeParameter_;
                    this.q &= -9;
                } else {
                    B();
                    this.p0.addAll(other.typeParameter_);
                }
            }
            if (!other.supertype_.isEmpty()) {
                if (this.a1.isEmpty()) {
                    this.a1 = other.supertype_;
                    this.q &= -17;
                } else {
                    z();
                    this.a1.addAll(other.supertype_);
                }
            }
            if (!other.supertypeId_.isEmpty()) {
                if (this.p1.isEmpty()) {
                    this.p1 = other.supertypeId_;
                    this.q &= -33;
                } else {
                    y();
                    this.p1.addAll(other.supertypeId_);
                }
            }
            if (!other.nestedClassName_.isEmpty()) {
                if (this.a2.isEmpty()) {
                    this.a2 = other.nestedClassName_;
                    this.q &= -65;
                } else {
                    u();
                    this.a2.addAll(other.nestedClassName_);
                }
            }
            if (!other.constructor_.isEmpty()) {
                if (this.p2.isEmpty()) {
                    this.p2 = other.constructor_;
                    this.q &= -129;
                } else {
                    r();
                    this.p2.addAll(other.constructor_);
                }
            }
            if (!other.function_.isEmpty()) {
                if (this.p3.isEmpty()) {
                    this.p3 = other.function_;
                    this.q &= -257;
                } else {
                    t();
                    this.p3.addAll(other.function_);
                }
            }
            if (!other.property_.isEmpty()) {
                if (this.p4.isEmpty()) {
                    this.p4 = other.property_;
                    this.q &= -513;
                } else {
                    v();
                    this.p4.addAll(other.property_);
                }
            }
            if (!other.typeAlias_.isEmpty()) {
                if (this.z4.isEmpty()) {
                    this.z4 = other.typeAlias_;
                    this.q &= -1025;
                } else {
                    A();
                    this.z4.addAll(other.typeAlias_);
                }
            }
            if (!other.enumEntry_.isEmpty()) {
                if (this.A4.isEmpty()) {
                    this.A4 = other.enumEntry_;
                    this.q &= -2049;
                } else {
                    s();
                    this.A4.addAll(other.enumEntry_);
                }
            }
            if (!other.sealedSubclassFqName_.isEmpty()) {
                if (this.B4.isEmpty()) {
                    this.B4 = other.sealedSubclassFqName_;
                    this.q &= -4097;
                } else {
                    w();
                    this.B4.addAll(other.sealedSubclassFqName_);
                }
            }
            if (other.hasTypeTable()) {
                c0(other.getTypeTable());
            }
            if (!other.versionRequirement_.isEmpty()) {
                if (this.D4.isEmpty()) {
                    this.D4 = other.versionRequirement_;
                    this.q &= -16385;
                } else {
                    C();
                    this.D4.addAll(other.versionRequirement_);
                }
            }
            if (other.hasVersionRequirementTable()) {
                d0(other.getVersionRequirementTable());
            }
            l(other);
            f(d().b(other.unknownFields));
            return this;
        }

        public final boolean isInitialized() {
            if (!X()) {
                return false;
            }
            for (int i = 0; i < U(); i++) {
                if (!S(i).isInitialized()) {
                    return false;
                }
            }
            for (int i2 = 0; i2 < O(); i2++) {
                if (!N(i2).isInitialized()) {
                    return false;
                }
            }
            for (int i3 = 0; i3 < F(); i3++) {
                if (!D(i3).isInitialized()) {
                    return false;
                }
            }
            for (int i4 = 0; i4 < K(); i4++) {
                if (!I(i4).isInitialized()) {
                    return false;
                }
            }
            for (int i5 = 0; i5 < M(); i5++) {
                if (!L(i5).isInitialized()) {
                    return false;
                }
            }
            for (int i6 = 0; i6 < R(); i6++) {
                if (!Q(i6).isInitialized()) {
                    return false;
                }
            }
            for (int i7 = 0; i7 < H(); i7++) {
                if (!G(i7).isInitialized()) {
                    return false;
                }
            }
            if ((Y() == 0 || V().isInitialized()) && k()) {
                return true;
            }
            return false;
        }

        /* renamed from: b0 */
        public b a(e input, f extensionRegistry) {
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

        public b f0(int value) {
            this.q |= 1;
            this.x = value;
            return this;
        }

        public boolean X() {
            return (this.q & 2) == 2;
        }

        public b g0(int value) {
            this.q |= 2;
            this.y = value;
            return this;
        }

        public b e0(int value) {
            this.q |= 4;
            this.z = value;
            return this;
        }

        private void B() {
            if ((this.q & 8) != 8) {
                this.p0 = new ArrayList(this.p0);
                this.q |= 8;
            }
        }

        public int U() {
            return this.p0.size();
        }

        public s S(int index) {
            return this.p0.get(index);
        }

        private void z() {
            if ((this.q & 16) != 16) {
                this.a1 = new ArrayList(this.a1);
                this.q |= 16;
            }
        }

        public int O() {
            return this.a1.size();
        }

        public q N(int index) {
            return this.a1.get(index);
        }

        private void y() {
            if ((this.q & 32) != 32) {
                this.p1 = new ArrayList(this.p1);
                this.q |= 32;
            }
        }

        private void u() {
            if ((this.q & 64) != 64) {
                this.a2 = new ArrayList(this.a2);
                this.q |= 64;
            }
        }

        private void r() {
            if ((this.q & 128) != 128) {
                this.p2 = new ArrayList(this.p2);
                this.q |= 128;
            }
        }

        public int F() {
            return this.p2.size();
        }

        public d D(int index) {
            return this.p2.get(index);
        }

        private void t() {
            if ((this.q & 256) != 256) {
                this.p3 = new ArrayList(this.p3);
                this.q |= 256;
            }
        }

        public int K() {
            return this.p3.size();
        }

        public i I(int index) {
            return this.p3.get(index);
        }

        private void v() {
            if ((this.q & 512) != 512) {
                this.p4 = new ArrayList(this.p4);
                this.q |= 512;
            }
        }

        public int M() {
            return this.p4.size();
        }

        public n L(int index) {
            return this.p4.get(index);
        }

        private void A() {
            if ((this.q & 1024) != 1024) {
                this.z4 = new ArrayList(this.z4);
                this.q |= 1024;
            }
        }

        public int R() {
            return this.z4.size();
        }

        public r Q(int index) {
            return this.z4.get(index);
        }

        private void s() {
            if ((this.q & 2048) != 2048) {
                this.A4 = new ArrayList(this.A4);
                this.q |= 2048;
            }
        }

        public int H() {
            return this.A4.size();
        }

        public g G(int index) {
            return this.A4.get(index);
        }

        private void w() {
            if ((this.q & 4096) != 4096) {
                this.B4 = new ArrayList(this.B4);
                this.q |= 4096;
            }
        }

        public boolean Y() {
            return (this.q & 8192) == 8192;
        }

        public t V() {
            return this.C4;
        }

        public b c0(t value) {
            if ((this.q & 8192) != 8192 || this.C4 == t.getDefaultInstance()) {
                this.C4 = value;
            } else {
                this.C4 = t.newBuilder(this.C4).e(value).j();
            }
            this.q |= 8192;
            return this;
        }

        private void C() {
            if ((this.q & 16384) != 16384) {
                this.D4 = new ArrayList(this.D4);
                this.q |= 16384;
            }
        }

        public b d0(w value) {
            if ((this.q & 32768) != 32768 || this.E4 == w.getDefaultInstance()) {
                this.E4 = value;
            } else {
                this.E4 = w.newBuilder(this.E4).e(value).j();
            }
            this.q |= 32768;
            return this;
        }
    }
}
