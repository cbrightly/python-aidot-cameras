package zendesk.messaging.android.internal.conversationscreen;

import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.messaging.model.a;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.messaging.android.internal.model.b;
import zendesk.messaging.android.internal.model.g;
import zendesk.ui.android.conversation.form.DisplayedField;

/* compiled from: ConversationScreenState.kt */
public final class h {
    @Nullable
    private final a a;
    @NotNull
    private final String b;
    @NotNull
    private final String c;
    @NotNull
    private final String d;
    @NotNull
    private final List<b> e;
    @Nullable
    private final Conversation f;
    @Nullable
    private final Throwable g;
    private final boolean h;
    private final int i;
    @Nullable
    private final zendesk.conversationkit.android.a j;
    private final boolean k;
    private final boolean l;
    @NotNull
    private final String m;
    @NotNull
    private final Map<Integer, DisplayedField> n;
    @NotNull
    private final g o;

    public h() {
        this((a) null, (String) null, (String) null, (String) null, (List) null, (Conversation) null, (Throwable) null, false, 0, (zendesk.conversationkit.android.a) null, false, false, (String) null, (Map) null, (g) null, 32767, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ h b(h hVar, a aVar, String str, String str2, String str3, List list, Conversation conversation, Throwable th, boolean z, int i2, zendesk.conversationkit.android.a aVar2, boolean z2, boolean z3, String str4, Map map, g gVar, int i3, Object obj) {
        h hVar2 = hVar;
        int i4 = i3;
        return hVar.a((i4 & 1) != 0 ? hVar2.a : aVar, (i4 & 2) != 0 ? hVar2.b : str, (i4 & 4) != 0 ? hVar2.c : str2, (i4 & 8) != 0 ? hVar2.d : str3, (i4 & 16) != 0 ? hVar2.e : list, (i4 & 32) != 0 ? hVar2.f : conversation, (i4 & 64) != 0 ? hVar2.g : th, (i4 & 128) != 0 ? hVar2.h : z, (i4 & 256) != 0 ? hVar2.i : i2, (i4 & 512) != 0 ? hVar2.j : aVar2, (i4 & 1024) != 0 ? hVar2.k : z2, (i4 & 2048) != 0 ? hVar2.l : z3, (i4 & 4096) != 0 ? hVar2.m : str4, (i4 & 8192) != 0 ? hVar2.n : map, (i4 & 16384) != 0 ? hVar2.o : gVar);
    }

    @NotNull
    public final h a(@Nullable a aVar, @NotNull String str, @NotNull String str2, @NotNull String str3, @NotNull List<? extends b> list, @Nullable Conversation conversation, @Nullable Throwable th, boolean z, int i2, @Nullable zendesk.conversationkit.android.a aVar2, boolean z2, boolean z3, @NotNull String str4, @NotNull Map<Integer, DisplayedField> map, @NotNull g gVar) {
        k.e(str, "title");
        k.e(str2, "description");
        k.e(str3, "logoUrl");
        k.e(list, "messageLog");
        k.e(str4, "composerText");
        k.e(map, "mapOfDisplayedFields");
        k.e(gVar, "typingUser");
        return new h(aVar, str, str2, str3, list, conversation, th, z, i2, aVar2, z2, z3, str4, map, gVar);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof h)) {
            return false;
        }
        h hVar = (h) obj;
        return k.a(this.a, hVar.a) && k.a(this.b, hVar.b) && k.a(this.c, hVar.c) && k.a(this.d, hVar.d) && k.a(this.e, hVar.e) && k.a(this.f, hVar.f) && k.a(this.g, hVar.g) && this.h == hVar.h && this.i == hVar.i && this.j == hVar.j && this.k == hVar.k && this.l == hVar.l && k.a(this.m, hVar.m) && k.a(this.n, hVar.n) && k.a(this.o, hVar.o);
    }

    public int hashCode() {
        a aVar = this.a;
        int i2 = 0;
        int hashCode = (((((((((aVar == null ? 0 : aVar.hashCode()) * 31) + this.b.hashCode()) * 31) + this.c.hashCode()) * 31) + this.d.hashCode()) * 31) + this.e.hashCode()) * 31;
        Conversation conversation = this.f;
        int hashCode2 = (hashCode + (conversation == null ? 0 : conversation.hashCode())) * 31;
        Throwable th = this.g;
        int hashCode3 = (hashCode2 + (th == null ? 0 : th.hashCode())) * 31;
        boolean z = this.h;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i3 = (((hashCode3 + (z ? 1 : 0)) * 31) + this.i) * 31;
        zendesk.conversationkit.android.a aVar2 = this.j;
        if (aVar2 != null) {
            i2 = aVar2.hashCode();
        }
        int i4 = (i3 + i2) * 31;
        boolean z3 = this.k;
        if (z3) {
            z3 = true;
        }
        int i5 = (i4 + (z3 ? 1 : 0)) * 31;
        boolean z4 = this.l;
        if (!z4) {
            z2 = z4;
        }
        return ((((((i5 + (z2 ? 1 : 0)) * 31) + this.m.hashCode()) * 31) + this.n.hashCode()) * 31) + this.o.hashCode();
    }

    @NotNull
    public String toString() {
        return "ConversationScreenState(colorTheme=" + this.a + ", title=" + this.b + ", description=" + this.c + ", logoUrl=" + this.d + ", messageLog=" + this.e + ", conversation=" + this.f + ", error=" + this.g + ", blockChatInput=" + this.h + ", messageComposerVisibility=" + this.i + ", connectionStatus=" + this.j + ", gallerySupported=" + this.k + ", cameraSupported=" + this.l + ", composerText=" + this.m + ", mapOfDisplayedFields=" + this.n + ", typingUser=" + this.o + ')';
    }

    public h(@Nullable a colorTheme, @NotNull String title, @NotNull String description, @NotNull String logoUrl, @NotNull List<? extends b> messageLog, @Nullable Conversation conversation, @Nullable Throwable error, boolean blockChatInput, int messageComposerVisibility, @Nullable zendesk.conversationkit.android.a connectionStatus, boolean gallerySupported, boolean cameraSupported, @NotNull String composerText, @NotNull Map<Integer, DisplayedField> mapOfDisplayedFields, @NotNull g typingUser) {
        String str = title;
        String str2 = description;
        String str3 = logoUrl;
        List<? extends b> list = messageLog;
        String str4 = composerText;
        Map<Integer, DisplayedField> map = mapOfDisplayedFields;
        g gVar = typingUser;
        k.e(str, "title");
        k.e(str2, "description");
        k.e(str3, "logoUrl");
        k.e(list, "messageLog");
        k.e(str4, "composerText");
        k.e(map, "mapOfDisplayedFields");
        k.e(gVar, "typingUser");
        this.a = colorTheme;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = list;
        this.f = conversation;
        this.g = error;
        this.h = blockChatInput;
        this.i = messageComposerVisibility;
        this.j = connectionStatus;
        this.k = gallerySupported;
        this.l = cameraSupported;
        this.m = str4;
        this.n = map;
        this.o = gVar;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ h(zendesk.android.messaging.model.a r16, java.lang.String r17, java.lang.String r18, java.lang.String r19, java.util.List r20, zendesk.conversationkit.android.model.Conversation r21, java.lang.Throwable r22, boolean r23, int r24, zendesk.conversationkit.android.a r25, boolean r26, boolean r27, java.lang.String r28, java.util.Map r29, zendesk.messaging.android.internal.model.g r30, int r31, kotlin.jvm.internal.DefaultConstructorMarker r32) {
        /*
            r15 = this;
            r0 = r31
            r1 = r0 & 1
            r2 = 0
            if (r1 == 0) goto L_0x0009
            r1 = r2
            goto L_0x000b
        L_0x0009:
            r1 = r16
        L_0x000b:
            r3 = r0 & 2
            java.lang.String r4 = ""
            if (r3 == 0) goto L_0x0013
            r3 = r4
            goto L_0x0015
        L_0x0013:
            r3 = r17
        L_0x0015:
            r5 = r0 & 4
            if (r5 == 0) goto L_0x001b
            r5 = r4
            goto L_0x001d
        L_0x001b:
            r5 = r18
        L_0x001d:
            r6 = r0 & 8
            if (r6 == 0) goto L_0x0023
            r6 = r4
            goto L_0x0025
        L_0x0023:
            r6 = r19
        L_0x0025:
            r7 = r0 & 16
            if (r7 == 0) goto L_0x002e
            java.util.List r7 = kotlin.collections.q.g()
            goto L_0x0030
        L_0x002e:
            r7 = r20
        L_0x0030:
            r8 = r0 & 32
            if (r8 == 0) goto L_0x0036
            r8 = r2
            goto L_0x0038
        L_0x0036:
            r8 = r21
        L_0x0038:
            r9 = r0 & 64
            if (r9 == 0) goto L_0x003e
            r9 = r2
            goto L_0x0040
        L_0x003e:
            r9 = r22
        L_0x0040:
            r10 = r0 & 128(0x80, float:1.794E-43)
            r11 = 0
            if (r10 == 0) goto L_0x0047
            r10 = r11
            goto L_0x0049
        L_0x0047:
            r10 = r23
        L_0x0049:
            r12 = r0 & 256(0x100, float:3.59E-43)
            if (r12 == 0) goto L_0x004e
            goto L_0x0050
        L_0x004e:
            r11 = r24
        L_0x0050:
            r12 = r0 & 512(0x200, float:7.175E-43)
            if (r12 == 0) goto L_0x0055
            goto L_0x0057
        L_0x0055:
            r2 = r25
        L_0x0057:
            r12 = r0 & 1024(0x400, float:1.435E-42)
            r13 = 1
            if (r12 == 0) goto L_0x005e
            r12 = r13
            goto L_0x0060
        L_0x005e:
            r12 = r26
        L_0x0060:
            r14 = r0 & 2048(0x800, float:2.87E-42)
            if (r14 == 0) goto L_0x0065
            goto L_0x0067
        L_0x0065:
            r13 = r27
        L_0x0067:
            r14 = r0 & 4096(0x1000, float:5.74E-42)
            if (r14 == 0) goto L_0x006c
            goto L_0x006e
        L_0x006c:
            r4 = r28
        L_0x006e:
            r14 = r0 & 8192(0x2000, float:1.14794E-41)
            if (r14 == 0) goto L_0x0078
            java.util.HashMap r14 = new java.util.HashMap
            r14.<init>()
            goto L_0x007a
        L_0x0078:
            r14 = r29
        L_0x007a:
            r0 = r0 & 16384(0x4000, float:2.2959E-41)
            if (r0 == 0) goto L_0x0081
            zendesk.messaging.android.internal.model.g$a r0 = zendesk.messaging.android.internal.model.g.a.a
            goto L_0x0083
        L_0x0081:
            r0 = r30
        L_0x0083:
            r16 = r15
            r17 = r1
            r18 = r3
            r19 = r5
            r20 = r6
            r21 = r7
            r22 = r8
            r23 = r9
            r24 = r10
            r25 = r11
            r26 = r2
            r27 = r12
            r28 = r13
            r29 = r4
            r30 = r14
            r31 = r0
            r16.<init>(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.h.<init>(zendesk.android.messaging.model.a, java.lang.String, java.lang.String, java.lang.String, java.util.List, zendesk.conversationkit.android.model.Conversation, java.lang.Throwable, boolean, int, zendesk.conversationkit.android.a, boolean, boolean, java.lang.String, java.util.Map, zendesk.messaging.android.internal.model.g, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    @Nullable
    public final a e() {
        return this.a;
    }

    @NotNull
    public final String o() {
        return this.b;
    }

    @NotNull
    public final String i() {
        return this.c;
    }

    @NotNull
    public final String k() {
        return this.d;
    }

    @NotNull
    public final List<b> n() {
        return this.e;
    }

    @Nullable
    public final Conversation h() {
        return this.f;
    }

    public final boolean c() {
        return this.h;
    }

    public final int m() {
        return this.i;
    }

    @Nullable
    public final zendesk.conversationkit.android.a g() {
        return this.j;
    }

    public final boolean j() {
        return this.k;
    }

    public final boolean d() {
        return this.l;
    }

    @NotNull
    public final String f() {
        return this.m;
    }

    @NotNull
    public final Map<Integer, DisplayedField> l() {
        return this.n;
    }

    @NotNull
    public final g p() {
        return this.o;
    }
}
