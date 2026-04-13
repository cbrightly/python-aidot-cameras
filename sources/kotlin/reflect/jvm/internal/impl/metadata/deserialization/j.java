package kotlin.reflect.jvm.internal.impl.metadata.deserialization;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.util.ArrayList;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.metadata.c;
import kotlin.reflect.jvm.internal.impl.metadata.d;
import kotlin.reflect.jvm.internal.impl.metadata.i;
import kotlin.reflect.jvm.internal.impl.metadata.n;
import kotlin.reflect.jvm.internal.impl.metadata.r;
import kotlin.reflect.jvm.internal.impl.metadata.v;
import kotlin.reflect.jvm.internal.impl.protobuf.o;
import meshsdk.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: VersionRequirement.kt */
public final class j {
    public static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final b b;
    @NotNull
    private final v.d c;
    @NotNull
    private final kotlin.a d;
    @Nullable
    private final Integer e;
    @Nullable
    private final String f;

    public j(@NotNull b version, @NotNull v.d kind, @NotNull kotlin.a level, @Nullable Integer errorCode, @Nullable String message) {
        k.f(version, ConfigUtil.VERSION_FILE);
        k.f(kind, "kind");
        k.f(level, FirebaseAnalytics.Param.LEVEL);
        this.b = version;
        this.c = kind;
        this.d = level;
        this.e = errorCode;
        this.f = message;
    }

    @NotNull
    public final b b() {
        return this.b;
    }

    @NotNull
    public final v.d a() {
        return this.c;
    }

    /* compiled from: VersionRequirement.kt */
    public static final class b {
        @NotNull
        public static final b a = new b(256, 256, 256);
        public static final a b = new a((DefaultConstructorMarker) null);
        private final int c;
        private final int d;
        private final int e;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return this.c == bVar.c && this.d == bVar.d && this.e == bVar.e;
        }

        public int hashCode() {
            return (((this.c * 31) + this.d) * 31) + this.e;
        }

        public b(int major, int minor, int patch) {
            this.c = major;
            this.d = minor;
            this.e = patch;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ b(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, i2, (i4 & 4) != 0 ? 0 : i3);
        }

        @NotNull
        public final String a() {
            int i;
            StringBuilder sb;
            if (this.e == 0) {
                sb = new StringBuilder();
                sb.append(this.c);
                sb.append('.');
                i = this.d;
            } else {
                sb = new StringBuilder();
                sb.append(this.c);
                sb.append('.');
                sb.append(this.d);
                sb.append('.');
                i = this.e;
            }
            sb.append(i);
            return sb.toString();
        }

        @NotNull
        public String toString() {
            return a();
        }

        /* compiled from: VersionRequirement.kt */
        public static final class a {
            private a() {
            }

            public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
                this();
            }

            @NotNull
            public final b a(@Nullable Integer version, @Nullable Integer versionFull) {
                if (versionFull != null) {
                    return new b(versionFull.intValue() & 255, (versionFull.intValue() >> 8) & 255, (versionFull.intValue() >> 16) & 255);
                }
                if (version != null) {
                    return new b(version.intValue() & 7, (version.intValue() >> 3) & 15, (version.intValue() >> 7) & NeedPermissionEvent.PER_IPC_SPEAK_PERM);
                }
                return b.a;
            }
        }
    }

    @NotNull
    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("since ");
        sb.append(this.b);
        sb.append(' ');
        sb.append(this.d);
        String str2 = "";
        if (this.e != null) {
            str = " error " + this.e;
        } else {
            str = str2;
        }
        sb.append(str);
        if (this.f != null) {
            str2 = ": " + this.f;
        }
        sb.append(str2);
        return sb.toString();
    }

    /* compiled from: VersionRequirement.kt */
    public static final class a {
        private a() {
        }

        public /* synthetic */ a(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final List<j> a(@NotNull o proto, @NotNull c nameResolver, @NotNull k table) {
            List<Integer> ids;
            o oVar = proto;
            c cVar = nameResolver;
            k kVar = table;
            k.f(oVar, "proto");
            k.f(cVar, "nameResolver");
            k.f(kVar, "table");
            if (oVar instanceof c) {
                ids = ((c) oVar).getVersionRequirementList();
            } else if (oVar instanceof d) {
                ids = ((d) oVar).getVersionRequirementList();
            } else if (oVar instanceof i) {
                ids = ((i) oVar).getVersionRequirementList();
            } else if (oVar instanceof n) {
                ids = ((n) oVar).getVersionRequirementList();
            } else if (oVar instanceof r) {
                ids = ((r) oVar).getVersionRequirementList();
            } else {
                throw new IllegalStateException("Unexpected declaration: " + proto.getClass());
            }
            k.b(ids, "ids");
            ArrayList arrayList = new ArrayList();
            for (Integer id : ids) {
                a aVar = j.a;
                List ids2 = ids;
                k.b(id, "id");
                Object it$iv$iv = aVar.b(id.intValue(), cVar, kVar);
                if (it$iv$iv != null) {
                    arrayList.add(it$iv$iv);
                }
                o oVar2 = proto;
                ids = ids2;
            }
            return arrayList;
        }

        @Nullable
        public final j b(int id, @NotNull c nameResolver, @NotNull k table) {
            kotlin.a level;
            k.f(nameResolver, "nameResolver");
            k.f(table, "table");
            v info = table.b(id);
            String message = null;
            if (info == null) {
                return null;
            }
            b version = b.b.a(info.hasVersion() ? Integer.valueOf(info.getVersion()) : null, info.hasVersionFull() ? Integer.valueOf(info.getVersionFull()) : null);
            v.c level2 = info.getLevel();
            if (level2 == null) {
                k.n();
            }
            switch (i.a[level2.ordinal()]) {
                case 1:
                    level = kotlin.a.WARNING;
                    break;
                case 2:
                    level = kotlin.a.ERROR;
                    break;
                case 3:
                    level = kotlin.a.HIDDEN;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            Integer errorCode = info.hasErrorCode() ? Integer.valueOf(info.getErrorCode()) : null;
            if (info.hasMessage()) {
                message = nameResolver.getString(info.getMessage());
            }
            v.d versionKind = info.getVersionKind();
            k.b(versionKind, "info.versionKind");
            return new j(version, versionKind, level, errorCode, message);
        }
    }
}
