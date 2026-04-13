package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.metadata.q;
import kotlin.reflect.jvm.internal.impl.metadata.s;
import kotlin.reflect.jvm.internal.impl.metadata.u;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

/* compiled from: protoTypeTableUtil.kt */
public final class g {
    @NotNull
    public static final List<q> k(@NotNull c $this$supertypes, @NotNull h typeTable) {
        k.f($this$supertypes, "$this$supertypes");
        k.f(typeTable, "typeTable");
        List<q> p1 = $this$supertypes.getSupertypeList();
        if (!(!p1.isEmpty())) {
            p1 = null;
        }
        if (p1 != null) {
            return p1;
        }
        Iterable<Integer> $this$mapTo$iv$iv = $this$supertypes.getSupertypeIdList();
        k.b($this$mapTo$iv$iv, "supertypeIdList");
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Integer it : $this$mapTo$iv$iv) {
            k.b(it, "it");
            arrayList.add(typeTable.a(it.intValue()));
        }
        return arrayList;
    }

    @Nullable
    public static final q l(@NotNull q.b $this$type, @NotNull h typeTable) {
        k.f($this$type, "$this$type");
        k.f(typeTable, "typeTable");
        if ($this$type.hasType()) {
            return $this$type.getType();
        }
        if ($this$type.hasTypeId()) {
            return typeTable.a($this$type.getTypeId());
        }
        return null;
    }

    @Nullable
    public static final q c(@NotNull q $this$flexibleUpperBound, @NotNull h typeTable) {
        k.f($this$flexibleUpperBound, "$this$flexibleUpperBound");
        k.f(typeTable, "typeTable");
        if ($this$flexibleUpperBound.hasFlexibleUpperBound()) {
            return $this$flexibleUpperBound.getFlexibleUpperBound();
        }
        if ($this$flexibleUpperBound.hasFlexibleUpperBoundId()) {
            return typeTable.a($this$flexibleUpperBound.getFlexibleUpperBoundId());
        }
        return null;
    }

    @NotNull
    public static final List<q> o(@NotNull s $this$upperBounds, @NotNull h typeTable) {
        k.f($this$upperBounds, "$this$upperBounds");
        k.f(typeTable, "typeTable");
        List<q> p1 = $this$upperBounds.getUpperBoundList();
        if (!(!p1.isEmpty())) {
            p1 = null;
        }
        if (p1 != null) {
            return p1;
        }
        Iterable<Integer> $this$mapTo$iv$iv = $this$upperBounds.getUpperBoundIdList();
        k.b($this$mapTo$iv$iv, "upperBoundIdList");
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Integer it : $this$mapTo$iv$iv) {
            k.b(it, "it");
            arrayList.add(typeTable.a(it.intValue()));
        }
        return arrayList;
    }

    @NotNull
    public static final q i(@NotNull i $this$returnType, @NotNull h typeTable) {
        k.f($this$returnType, "$this$returnType");
        k.f(typeTable, "typeTable");
        if ($this$returnType.hasReturnType()) {
            q returnType = $this$returnType.getReturnType();
            k.b(returnType, "returnType");
            return returnType;
        } else if ($this$returnType.hasReturnTypeId()) {
            return typeTable.a($this$returnType.getReturnTypeId());
        } else {
            throw new IllegalStateException("No returnType in ProtoBuf.Function".toString());
        }
    }

    public static final boolean d(@NotNull i $this$hasReceiver) {
        k.f($this$hasReceiver, "$this$hasReceiver");
        return $this$hasReceiver.hasReceiverType() || $this$hasReceiver.hasReceiverTypeId();
    }

    @Nullable
    public static final q g(@NotNull i $this$receiverType, @NotNull h typeTable) {
        k.f($this$receiverType, "$this$receiverType");
        k.f(typeTable, "typeTable");
        if ($this$receiverType.hasReceiverType()) {
            return $this$receiverType.getReceiverType();
        }
        if ($this$receiverType.hasReceiverTypeId()) {
            return typeTable.a($this$receiverType.getReceiverTypeId());
        }
        return null;
    }

    @NotNull
    public static final q j(@NotNull n $this$returnType, @NotNull h typeTable) {
        k.f($this$returnType, "$this$returnType");
        k.f(typeTable, "typeTable");
        if ($this$returnType.hasReturnType()) {
            q returnType = $this$returnType.getReturnType();
            k.b(returnType, "returnType");
            return returnType;
        } else if ($this$returnType.hasReturnTypeId()) {
            return typeTable.a($this$returnType.getReturnTypeId());
        } else {
            throw new IllegalStateException("No returnType in ProtoBuf.Property".toString());
        }
    }

    public static final boolean e(@NotNull n $this$hasReceiver) {
        k.f($this$hasReceiver, "$this$hasReceiver");
        return $this$hasReceiver.hasReceiverType() || $this$hasReceiver.hasReceiverTypeId();
    }

    @Nullable
    public static final q h(@NotNull n $this$receiverType, @NotNull h typeTable) {
        k.f($this$receiverType, "$this$receiverType");
        k.f(typeTable, "typeTable");
        if ($this$receiverType.hasReceiverType()) {
            return $this$receiverType.getReceiverType();
        }
        if ($this$receiverType.hasReceiverTypeId()) {
            return typeTable.a($this$receiverType.getReceiverTypeId());
        }
        return null;
    }

    @NotNull
    public static final q m(@NotNull u $this$type, @NotNull h typeTable) {
        k.f($this$type, "$this$type");
        k.f(typeTable, "typeTable");
        if ($this$type.hasType()) {
            q type = $this$type.getType();
            k.b(type, IjkMediaMeta.IJKM_KEY_TYPE);
            return type;
        } else if ($this$type.hasTypeId()) {
            return typeTable.a($this$type.getTypeId());
        } else {
            throw new IllegalStateException("No type in ProtoBuf.ValueParameter".toString());
        }
    }

    @Nullable
    public static final q p(@NotNull u $this$varargElementType, @NotNull h typeTable) {
        k.f($this$varargElementType, "$this$varargElementType");
        k.f(typeTable, "typeTable");
        if ($this$varargElementType.hasVarargElementType()) {
            return $this$varargElementType.getVarargElementType();
        }
        if ($this$varargElementType.hasVarargElementTypeId()) {
            return typeTable.a($this$varargElementType.getVarargElementTypeId());
        }
        return null;
    }

    @Nullable
    public static final q f(@NotNull q $this$outerType, @NotNull h typeTable) {
        k.f($this$outerType, "$this$outerType");
        k.f(typeTable, "typeTable");
        if ($this$outerType.hasOuterType()) {
            return $this$outerType.getOuterType();
        }
        if ($this$outerType.hasOuterTypeId()) {
            return typeTable.a($this$outerType.getOuterTypeId());
        }
        return null;
    }

    @Nullable
    public static final q a(@NotNull q $this$abbreviatedType, @NotNull h typeTable) {
        k.f($this$abbreviatedType, "$this$abbreviatedType");
        k.f(typeTable, "typeTable");
        if ($this$abbreviatedType.hasAbbreviatedType()) {
            return $this$abbreviatedType.getAbbreviatedType();
        }
        if ($this$abbreviatedType.hasAbbreviatedTypeId()) {
            return typeTable.a($this$abbreviatedType.getAbbreviatedTypeId());
        }
        return null;
    }

    @NotNull
    public static final q n(@NotNull kotlin.reflect.jvm.internal.impl.metadata.r $this$underlyingType, @NotNull h typeTable) {
        k.f($this$underlyingType, "$this$underlyingType");
        k.f(typeTable, "typeTable");
        if ($this$underlyingType.hasUnderlyingType()) {
            q underlyingType = $this$underlyingType.getUnderlyingType();
            k.b(underlyingType, "underlyingType");
            return underlyingType;
        } else if ($this$underlyingType.hasUnderlyingTypeId()) {
            return typeTable.a($this$underlyingType.getUnderlyingTypeId());
        } else {
            throw new IllegalStateException("No underlyingType in ProtoBuf.TypeAlias".toString());
        }
    }

    @NotNull
    public static final q b(@NotNull kotlin.reflect.jvm.internal.impl.metadata.r $this$expandedType, @NotNull h typeTable) {
        k.f($this$expandedType, "$this$expandedType");
        k.f(typeTable, "typeTable");
        if ($this$expandedType.hasExpandedType()) {
            q expandedType = $this$expandedType.getExpandedType();
            k.b(expandedType, "expandedType");
            return expandedType;
        } else if ($this$expandedType.hasExpandedTypeId()) {
            return typeTable.a($this$expandedType.getExpandedTypeId());
        } else {
            throw new IllegalStateException("No expandedType in ProtoBuf.TypeAlias".toString());
        }
    }
}
