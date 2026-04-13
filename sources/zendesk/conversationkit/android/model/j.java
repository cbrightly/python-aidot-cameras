package zendesk.conversationkit.android.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import kotlin.collections.l0;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import kotlin.t;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.rest.model.AppUserDto;
import zendesk.conversationkit.android.internal.rest.model.ConversationDto;
import zendesk.conversationkit.android.internal.rest.model.ConversationResponseDto;
import zendesk.conversationkit.android.internal.rest.model.MessageDto;

/* compiled from: Conversation.kt */
public final class j {
    @NotNull
    public static final Conversation c(@NotNull ConversationResponseDto $this$toConversation, @NotNull String currentUserId) {
        k.e($this$toConversation, "<this>");
        k.e(currentUserId, "currentUserId");
        ConversationDto c = $this$toConversation.c();
        Map<String, AppUserDto> k = l0.k($this$toConversation.b(), t.a($this$toConversation.a().d(), $this$toConversation.a()));
        List<MessageDto> e = $this$toConversation.e();
        Boolean d = $this$toConversation.d();
        return b(c, currentUserId, k, e, d == null ? false : d.booleanValue());
    }

    public static /* synthetic */ Conversation d(ConversationDto conversationDto, String str, Map map, List<MessageDto> list, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            list = conversationDto.h();
        }
        if ((i & 8) != 0) {
            z = false;
        }
        return b(conversationDto, str, map, list, z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00ad A[LOOP:1: B:23:0x00a7->B:25:0x00ad, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00c5  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00c7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0105  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x010c  */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final zendesk.conversationkit.android.model.Conversation b(@org.jetbrains.annotations.NotNull zendesk.conversationkit.android.internal.rest.model.ConversationDto r21, @org.jetbrains.annotations.NotNull java.lang.String r22, @org.jetbrains.annotations.NotNull java.util.Map<java.lang.String, zendesk.conversationkit.android.internal.rest.model.AppUserDto> r23, @org.jetbrains.annotations.Nullable java.util.List<zendesk.conversationkit.android.internal.rest.model.MessageDto> r24, boolean r25) {
        /*
            r0 = r22
            java.lang.String r1 = "<this>"
            r2 = r21
            kotlin.jvm.internal.k.e(r2, r1)
            java.lang.String r1 = "currentUserId"
            kotlin.jvm.internal.k.e(r0, r1)
            java.lang.String r1 = "appUsers"
            r3 = r23
            kotlin.jvm.internal.k.e(r3, r1)
            java.lang.String r5 = r21.f()
            java.lang.String r6 = r21.d()
            java.lang.String r7 = r21.c()
            java.lang.String r8 = r21.e()
            java.lang.String r1 = r21.j()
            java.lang.String r4 = "personal"
            boolean r1 = kotlin.jvm.internal.k.a(r1, r4)
            if (r1 == 0) goto L_0x0035
            zendesk.conversationkit.android.model.k r1 = zendesk.conversationkit.android.model.k.PERSONAL
            goto L_0x0037
        L_0x0035:
            zendesk.conversationkit.android.model.k r1 = zendesk.conversationkit.android.model.k.GROUP
        L_0x0037:
            r9 = r1
            boolean r10 = r21.k()
            java.util.List r1 = r21.b()
            if (r1 == 0) goto L_0x0043
            goto L_0x0047
        L_0x0043:
            java.util.List r1 = kotlin.collections.q.g()
        L_0x0047:
            r11 = r1
            java.lang.Double r1 = r21.a()
            java.util.Date r12 = zendesk.conversationkit.android.internal.extension.c.b(r1)
            java.lang.Double r13 = r21.g()
            java.util.List r1 = r21.i()
            if (r1 != 0) goto L_0x005c
        L_0x005a:
            r14 = 0
            goto L_0x0088
        L_0x005c:
            r14 = 0
            java.util.Iterator r15 = r1.iterator()
        L_0x0061:
            boolean r16 = r15.hasNext()
            if (r16 == 0) goto L_0x007c
            java.lang.Object r16 = r15.next()
            r17 = r16
            zendesk.conversationkit.android.internal.rest.model.ParticipantDto r17 = (zendesk.conversationkit.android.internal.rest.model.ParticipantDto) r17
            r18 = 0
            java.lang.String r4 = r17.a()
            boolean r4 = kotlin.jvm.internal.k.a(r4, r0)
            if (r4 == 0) goto L_0x0061
            goto L_0x007e
        L_0x007c:
            r16 = 0
        L_0x007e:
            zendesk.conversationkit.android.internal.rest.model.ParticipantDto r16 = (zendesk.conversationkit.android.internal.rest.model.ParticipantDto) r16
            if (r16 != 0) goto L_0x0083
            goto L_0x005a
        L_0x0083:
            zendesk.conversationkit.android.model.Participant r1 = zendesk.conversationkit.android.model.w.a(r16)
            r14 = r1
        L_0x0088:
            java.util.List r1 = r21.i()
            if (r1 == 0) goto L_0x008f
            goto L_0x0093
        L_0x008f:
            java.util.List r1 = kotlin.collections.q.g()
        L_0x0093:
            r4 = 0
            java.util.ArrayList r15 = new java.util.ArrayList
            r0 = 10
            int r2 = kotlin.collections.r.r(r1, r0)
            r15.<init>(r2)
            r2 = r15
            r15 = r1
            r16 = 0
            java.util.Iterator r17 = r15.iterator()
        L_0x00a7:
            boolean r18 = r17.hasNext()
            if (r18 == 0) goto L_0x00c1
            java.lang.Object r18 = r17.next()
            r19 = r18
            zendesk.conversationkit.android.internal.rest.model.ParticipantDto r19 = (zendesk.conversationkit.android.internal.rest.model.ParticipantDto) r19
            r20 = 0
            zendesk.conversationkit.android.model.Participant r0 = zendesk.conversationkit.android.model.w.a(r19)
            r2.add(r0)
            r0 = 10
            goto L_0x00a7
        L_0x00c1:
            if (r24 != 0) goto L_0x00c7
            r4 = 0
            goto L_0x0103
        L_0x00c7:
            r0 = r24
            r1 = 0
            java.util.ArrayList r4 = new java.util.ArrayList
            r15 = 10
            int r15 = kotlin.collections.r.r(r0, r15)
            r4.<init>(r15)
            r15 = r0
            r16 = 0
            java.util.Iterator r17 = r15.iterator()
        L_0x00dc:
            boolean r18 = r17.hasNext()
            if (r18 == 0) goto L_0x00fe
            java.lang.Object r18 = r17.next()
            r19 = r0
            r0 = r18
            zendesk.conversationkit.android.internal.rest.model.MessageDto r0 = (zendesk.conversationkit.android.internal.rest.model.MessageDto) r0
            r20 = r1
            r1 = 3
            r3 = 0
            zendesk.conversationkit.android.model.Message r0 = zendesk.conversationkit.android.model.t.c(r0, r3, r3, r1, r3)
            r4.add(r0)
            r3 = r23
            r0 = r19
            r1 = r20
            goto L_0x00dc
        L_0x00fe:
            r19 = r0
            r20 = r1
        L_0x0103:
            if (r4 != 0) goto L_0x010c
            java.util.List r0 = kotlin.collections.q.g()
            r16 = r0
            goto L_0x010e
        L_0x010c:
            r16 = r4
        L_0x010e:
            zendesk.conversationkit.android.model.Conversation r0 = new zendesk.conversationkit.android.model.Conversation
            r4 = r0
            r15 = r2
            r17 = r25
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.model.j.b(zendesk.conversationkit.android.internal.rest.model.ConversationDto, java.lang.String, java.util.Map, java.util.List, boolean):zendesk.conversationkit.android.model.Conversation");
    }

    @NotNull
    public static final Conversation a(@NotNull Conversation $this$enrichFormResponseFields) {
        Conversation conversation = $this$enrichFormResponseFields;
        k.e(conversation, "<this>");
        Iterable<Message> $this$mapTo$iv$iv = $this$enrichFormResponseFields.k();
        ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Message it : $this$mapTo$iv$iv) {
            arrayList.add(t.a(it, conversation));
        }
        return Conversation.b($this$enrichFormResponseFields, (String) null, (String) null, (String) null, (String) null, (k) null, false, (List) null, (Date) null, (Double) null, (Participant) null, (List) null, arrayList, false, 6143, (Object) null);
    }
}
