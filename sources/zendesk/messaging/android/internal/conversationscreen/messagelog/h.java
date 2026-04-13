package zendesk.messaging.android.internal.conversationscreen.messagelog;

import androidx.annotation.ColorInt;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.messaging.android.internal.model.b;
import zendesk.ui.android.conversation.form.DisplayedField;

/* compiled from: MessageLogState.kt */
public final class h {
    @NotNull
    private final List<b> a;
    private final boolean b;
    @NotNull
    private final Map<Integer, DisplayedField> c;
    @Nullable
    private final Integer d;
    @Nullable
    private final Integer e;
    @Nullable
    private final Integer f;

    public h() {
        this((List) null, false, (Map) null, (Integer) null, (Integer) null, (Integer) null, 63, (DefaultConstructorMarker) null);
    }

    @NotNull
    public final h a(@NotNull List<? extends b> list, boolean z, @NotNull Map<Integer, DisplayedField> map, @Nullable @ColorInt Integer num, @Nullable @ColorInt Integer num2, @Nullable @ColorInt Integer num3) {
        k.e(list, "messageLogEntryList");
        k.e(map, "mapOfDisplayedFields");
        return new h(list, z, map, num, num2, num3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        return k.a(this.a, hVar.a) && this.b == hVar.b && k.a(this.c, hVar.c) && k.a(this.d, hVar.d) && k.a(this.e, hVar.e) && k.a(this.f, hVar.f);
    }

    public int hashCode() {
        int hashCode = this.a.hashCode() * 31;
        boolean z = this.b;
        if (z) {
            z = true;
        }
        int hashCode2 = (((hashCode + (z ? 1 : 0)) * 31) + this.c.hashCode()) * 31;
        Integer num = this.d;
        int i = 0;
        int hashCode3 = (hashCode2 + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.e;
        int hashCode4 = (hashCode3 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.f;
        if (num3 != null) {
            i = num3.hashCode();
        }
        return hashCode4 + i;
    }

    @NotNull
    public String toString() {
        return "MessageLogState(messageLogEntryList=" + this.a + ", shouldScrollToBottom=" + this.b + ", mapOfDisplayedFields=" + this.c + ", outboundMessageColor=" + this.d + ", actionColor=" + this.e + ", notifyColor=" + this.f + ')';
    }

    public h(@NotNull List<? extends b> messageLogEntryList, boolean shouldScrollToBottom, @NotNull Map<Integer, DisplayedField> mapOfDisplayedFields, @Nullable @ColorInt Integer outboundMessageColor, @Nullable @ColorInt Integer actionColor, @Nullable @ColorInt Integer notifyColor) {
        k.e(messageLogEntryList, "messageLogEntryList");
        k.e(mapOfDisplayedFields, "mapOfDisplayedFields");
        this.a = messageLogEntryList;
        this.b = shouldScrollToBottom;
        this.c = mapOfDisplayedFields;
        this.d = outboundMessageColor;
        this.e = actionColor;
        this.f = notifyColor;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ h(java.util.List r5, boolean r6, java.util.Map r7, java.lang.Integer r8, java.lang.Integer r9, java.lang.Integer r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r4 = this;
            r12 = r11 & 1
            if (r12 == 0) goto L_0x0008
            java.util.List r5 = kotlin.collections.q.g()
        L_0x0008:
            r12 = r11 & 2
            if (r12 == 0) goto L_0x000f
            r6 = 0
            r12 = r6
            goto L_0x0010
        L_0x000f:
            r12 = r6
        L_0x0010:
            r6 = r11 & 4
            if (r6 == 0) goto L_0x001b
            java.util.HashMap r7 = new java.util.HashMap
            r7.<init>()
            r0 = r7
            goto L_0x001c
        L_0x001b:
            r0 = r7
        L_0x001c:
            r6 = r11 & 8
            r7 = 0
            if (r6 == 0) goto L_0x0023
            r1 = r7
            goto L_0x0024
        L_0x0023:
            r1 = r8
        L_0x0024:
            r6 = r11 & 16
            if (r6 == 0) goto L_0x002a
            r2 = r7
            goto L_0x002b
        L_0x002a:
            r2 = r9
        L_0x002b:
            r6 = r11 & 32
            if (r6 == 0) goto L_0x0031
            r3 = r7
            goto L_0x0032
        L_0x0031:
            r3 = r10
        L_0x0032:
            r6 = r4
            r7 = r5
            r8 = r12
            r9 = r0
            r10 = r1
            r11 = r2
            r12 = r3
            r6.<init>(r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.messagelog.h.<init>(java.util.List, boolean, java.util.Map, java.lang.Integer, java.lang.Integer, java.lang.Integer, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @NotNull
    public final List<b> d() {
        return this.a;
    }

    public final boolean g() {
        return this.b;
    }

    @NotNull
    public final Map<Integer, DisplayedField> c() {
        return this.c;
    }

    @Nullable
    public final Integer f() {
        return this.d;
    }

    @Nullable
    public final Integer b() {
        return this.e;
    }

    @Nullable
    public final Integer e() {
        return this.f;
    }
}
