package zendesk.conversationkit.android.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.extension.c;
import zendesk.conversationkit.android.internal.rest.model.MessageActionDto;
import zendesk.conversationkit.android.internal.rest.model.MessageDto;
import zendesk.conversationkit.android.internal.rest.model.MessageFieldDto;
import zendesk.conversationkit.android.internal.rest.model.MessageItemDto;
import zendesk.conversationkit.android.internal.rest.model.MessageSourceDto;
import zendesk.conversationkit.android.internal.rest.model.SendMessageDto;
import zendesk.conversationkit.android.model.MessageContent;

/* compiled from: Message.kt */
public final class t {

    /* compiled from: Message.kt */
    public final /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[v.values().length];
            iArr[v.TEXT.ordinal()] = 1;
            iArr[v.FILE.ordinal()] = 2;
            iArr[v.FORM.ordinal()] = 3;
            iArr[v.FORM_RESPONSE.ordinal()] = 4;
            iArr[v.CAROUSEL.ordinal()] = 5;
            iArr[v.IMAGE.ordinal()] = 6;
            a = iArr;
        }
    }

    public static /* synthetic */ Message c(MessageDto messageDto, Date date, String str, int i, Object obj) {
        String str2 = null;
        if ((i & 1) != 0) {
            date = null;
        }
        if ((i & 2) != 0) {
            MessageSourceDto u = messageDto.u();
            if (u != null) {
                str2 = u.b();
            }
            str = str2 == null ? messageDto.i() : str2;
        }
        return b(messageDto, date, str);
    }

    @NotNull
    public static final Message b(@NotNull MessageDto $this$toMessage, @Nullable Date created, @NotNull String localId) {
        String str;
        String a2;
        k.e($this$toMessage, "<this>");
        k.e(localId, "localId");
        String i = $this$toMessage.i();
        String c = $this$toMessage.c();
        g a3 = g.Companion.a($this$toMessage.t());
        String p = $this$toMessage.p();
        if (p == null) {
            p = "";
        }
        Author author = new Author(c, a3, p, $this$toMessage.d());
        u uVar = u.SENT;
        Date a4 = c.a($this$toMessage.s());
        MessageContent d = d($this$toMessage);
        Map<String, Object> o = $this$toMessage.o();
        MessageSourceDto u = $this$toMessage.u();
        if (u == null || (a2 = u.a()) == null) {
            str = "";
        } else {
            str = a2;
        }
        return new Message(i, author, uVar, created, a4, d, o, str, localId, $this$toMessage.q());
    }

    @NotNull
    public static final MessageContent d(@NotNull MessageDto $this$toMessageContent) {
        k.e($this$toMessageContent, "<this>");
        long j = 0;
        boolean z = false;
        String str = "";
        switch (a.a[v.Companion.a($this$toMessageContent.x()).ordinal()]) {
            case 1:
                String v = $this$toMessageContent.v();
                if (v != null) {
                    str = v;
                }
                Iterable<MessageActionDto> $this$mapNotNull$iv = $this$toMessageContent.a();
                if ($this$mapNotNull$iv == null) {
                    $this$mapNotNull$iv = q.g();
                }
                ArrayList arrayList = new ArrayList();
                for (MessageActionDto it : $this$mapNotNull$iv) {
                    Object it$iv$iv = p.a(it);
                    if (it$iv$iv != null) {
                        arrayList.add(it$iv$iv);
                    }
                }
                return new MessageContent.Text(str, arrayList);
            case 2:
                String v2 = $this$toMessageContent.v();
                String str2 = v2 != null ? v2 : str;
                String b = $this$toMessageContent.b();
                String str3 = b != null ? b : str;
                String n = $this$toMessageContent.n();
                String str4 = n != null ? n : str;
                String m = $this$toMessageContent.m();
                String str5 = m != null ? m : str;
                Long l = $this$toMessageContent.l();
                if (l != null) {
                    j = l.longValue();
                }
                return new MessageContent.File(str2, str3, str4, str5, j);
            case 3:
                Iterable<MessageFieldDto> $this$mapNotNull$iv2 = $this$toMessageContent.h();
                if ($this$mapNotNull$iv2 == null) {
                    $this$mapNotNull$iv2 = q.g();
                }
                ArrayList arrayList2 = new ArrayList();
                for (MessageFieldDto it2 : $this$mapNotNull$iv2) {
                    Object it$iv$iv2 = m.a(it2);
                    if (it$iv$iv2 != null) {
                        arrayList2.add(it$iv$iv2);
                    }
                }
                Boolean e = $this$toMessageContent.e();
                if (e != null) {
                    z = e.booleanValue();
                }
                return new MessageContent.Form(arrayList2, z);
            case 4:
                Iterable<MessageFieldDto> $this$mapNotNull$iv3 = $this$toMessageContent.h();
                if ($this$mapNotNull$iv3 == null) {
                    $this$mapNotNull$iv3 = q.g();
                }
                ArrayList arrayList3 = new ArrayList();
                for (MessageFieldDto it3 : $this$mapNotNull$iv3) {
                    Object it$iv$iv3 = m.a(it3);
                    if (it$iv$iv3 != null) {
                        arrayList3.add(it$iv$iv3);
                    }
                }
                String r = $this$toMessageContent.r();
                if (r != null) {
                    str = r;
                }
                return new MessageContent.FormResponse(str, arrayList3);
            case 5:
                Iterable<MessageItemDto> $this$map$iv = $this$toMessageContent.j();
                if ($this$map$iv == null) {
                    $this$map$iv = q.g();
                }
                ArrayList arrayList4 = new ArrayList(r.r($this$map$iv, 10));
                for (MessageItemDto it4 : $this$map$iv) {
                    arrayList4.add(r.a(it4));
                }
                return new MessageContent.Carousel(arrayList4);
            case 6:
                String v3 = $this$toMessageContent.v();
                String str6 = v3 != null ? v3 : str;
                String n2 = $this$toMessageContent.n();
                String str7 = n2 != null ? n2 : str;
                String m2 = $this$toMessageContent.m();
                String str8 = m2 != null ? m2 : str;
                Long l2 = $this$toMessageContent.l();
                if (l2 != null) {
                    j = l2.longValue();
                }
                return new MessageContent.Image(str6, str7, (String) null, str8, j);
            default:
                String w = $this$toMessageContent.w();
                if (w == null || w.A(w)) {
                    z = true;
                }
                if (z) {
                    return new MessageContent.Unsupported((String) null, 1, (DefaultConstructorMarker) null);
                }
                return new MessageContent.Text($this$toMessageContent.w(), (List) null, 2, (DefaultConstructorMarker) null);
        }
    }

    @NotNull
    public static final SendMessageDto e(@NotNull Message $this$toSendMessageDto) {
        k.e($this$toSendMessageDto, "<this>");
        MessageContent d = $this$toSendMessageDto.d();
        if (d instanceof MessageContent.Text) {
            return new SendMessageDto.Text($this$toSendMessageDto.c().e().getValue$zendesk_conversationkit_conversationkit_android(), $this$toSendMessageDto.i(), $this$toSendMessageDto.j(), ((MessageContent.Text) $this$toSendMessageDto.d()).c());
        }
        if (d instanceof MessageContent.FormResponse) {
            String value$zendesk_conversationkit_conversationkit_android = $this$toSendMessageDto.c().e().getValue$zendesk_conversationkit_conversationkit_android();
            Map<String, Object> i = $this$toSendMessageDto.i();
            String j = $this$toSendMessageDto.j();
            Iterable<Field> $this$mapTo$iv$iv = ((MessageContent.FormResponse) $this$toSendMessageDto.d()).d();
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (Field field : $this$mapTo$iv$iv) {
                arrayList.add(m.b(field));
            }
            return new SendMessageDto.FormResponse(value$zendesk_conversationkit_conversationkit_android, i, j, arrayList, ((MessageContent.FormResponse) $this$toSendMessageDto.d()).e());
        }
        throw new IllegalArgumentException("Message with the " + $this$toSendMessageDto.d().a() + " content type cannot be sent by this SDK");
    }

    /* JADX WARNING: type inference failed for: r0v13, types: [zendesk.conversationkit.android.model.MessageContent] */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.NotNull
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final zendesk.conversationkit.android.model.Message a(@org.jetbrains.annotations.NotNull zendesk.conversationkit.android.model.Message r33, @org.jetbrains.annotations.NotNull zendesk.conversationkit.android.model.Conversation r34) {
        /*
            r13 = r33
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.k.e(r13, r0)
            java.lang.String r0 = "conversation"
            r14 = r34
            kotlin.jvm.internal.k.e(r14, r0)
            zendesk.conversationkit.android.model.MessageContent r0 = r33.d()
            boolean r0 = r0 instanceof zendesk.conversationkit.android.model.MessageContent.FormResponse
            if (r0 == 0) goto L_0x01e1
            java.util.List r0 = r34.k()
            r1 = 0
            java.util.Iterator r2 = r0.iterator()
        L_0x001f:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0042
            java.lang.Object r3 = r2.next()
            r5 = r3
            zendesk.conversationkit.android.model.Message r5 = (zendesk.conversationkit.android.model.Message) r5
            r6 = 0
            java.lang.String r7 = r5.g()
            zendesk.conversationkit.android.model.MessageContent r8 = r33.d()
            zendesk.conversationkit.android.model.MessageContent$FormResponse r8 = (zendesk.conversationkit.android.model.MessageContent.FormResponse) r8
            java.lang.String r8 = r8.e()
            boolean r5 = kotlin.jvm.internal.k.a(r7, r8)
            if (r5 == 0) goto L_0x001f
            goto L_0x0043
        L_0x0042:
            r3 = 0
        L_0x0043:
            zendesk.conversationkit.android.model.Message r3 = (zendesk.conversationkit.android.model.Message) r3
            if (r3 != 0) goto L_0x0049
            r0 = 0
            goto L_0x004d
        L_0x0049:
            zendesk.conversationkit.android.model.MessageContent r0 = r3.d()
        L_0x004d:
            r15 = r0
            boolean r0 = r15 instanceof zendesk.conversationkit.android.model.MessageContent.Form
            if (r0 != 0) goto L_0x0054
            return r13
        L_0x0054:
            zendesk.conversationkit.android.model.MessageContent r0 = r33.d()
            zendesk.conversationkit.android.model.MessageContent$FormResponse r0 = (zendesk.conversationkit.android.model.MessageContent.FormResponse) r0
            zendesk.conversationkit.android.model.MessageContent r7 = r33.d()
            zendesk.conversationkit.android.model.MessageContent$FormResponse r7 = (zendesk.conversationkit.android.model.MessageContent.FormResponse) r7
            java.util.List r7 = r7.d()
            r8 = 0
            java.util.ArrayList r9 = new java.util.ArrayList
            r10 = 10
            int r10 = kotlin.collections.r.r(r7, r10)
            r9.<init>(r10)
            r10 = r7
            r11 = 0
            java.util.Iterator r12 = r10.iterator()
        L_0x0077:
            boolean r16 = r12.hasNext()
            if (r16 == 0) goto L_0x01c6
            java.lang.Object r16 = r12.next()
            r6 = r16
            zendesk.conversationkit.android.model.Field r6 = (zendesk.conversationkit.android.model.Field) r6
            r17 = 0
            boolean r5 = r6 instanceof zendesk.conversationkit.android.model.Field.Text
            if (r5 == 0) goto L_0x00f2
            r5 = r15
            zendesk.conversationkit.android.model.MessageContent$Form r5 = (zendesk.conversationkit.android.model.MessageContent.Form) r5
            java.util.List r5 = r5.c()
            r18 = 0
            java.util.Iterator r19 = r5.iterator()
        L_0x0099:
            boolean r20 = r19.hasNext()
            if (r20 == 0) goto L_0x00b8
            java.lang.Object r20 = r19.next()
            r21 = r20
            zendesk.conversationkit.android.model.Field r21 = (zendesk.conversationkit.android.model.Field) r21
            r22 = 0
            java.lang.String r3 = r21.a()
            java.lang.String r2 = r6.a()
            boolean r2 = kotlin.jvm.internal.k.a(r3, r2)
            if (r2 == 0) goto L_0x0099
            goto L_0x00ba
        L_0x00b8:
            r20 = 0
        L_0x00ba:
            r2 = r20
            zendesk.conversationkit.android.model.Field r2 = (zendesk.conversationkit.android.model.Field) r2
            boolean r3 = r2 instanceof zendesk.conversationkit.android.model.Field.Text
            if (r3 != 0) goto L_0x00c4
            goto L_0x01bb
        L_0x00c4:
            r23 = r6
            zendesk.conversationkit.android.model.Field$Text r23 = (zendesk.conversationkit.android.model.Field.Text) r23
            r3 = r2
            zendesk.conversationkit.android.model.Field$Text r3 = (zendesk.conversationkit.android.model.Field.Text) r3
            int r28 = r3.h()
            r3 = r2
            zendesk.conversationkit.android.model.Field$Text r3 = (zendesk.conversationkit.android.model.Field.Text) r3
            int r29 = r3.g()
            r3 = r2
            zendesk.conversationkit.android.model.Field$Text r3 = (zendesk.conversationkit.android.model.Field.Text) r3
            java.lang.String r27 = r3.d()
            r24 = 0
            r25 = 0
            r26 = 0
            r30 = 0
            r31 = 71
            r32 = 0
            zendesk.conversationkit.android.model.Field$Text r2 = zendesk.conversationkit.android.model.Field.Text.f(r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)
            goto L_0x01ba
        L_0x00f2:
            boolean r2 = r6 instanceof zendesk.conversationkit.android.model.Field.Email
            if (r2 == 0) goto L_0x014a
            r2 = r15
            zendesk.conversationkit.android.model.MessageContent$Form r2 = (zendesk.conversationkit.android.model.MessageContent.Form) r2
            java.util.List r2 = r2.c()
            r3 = 0
            java.util.Iterator r5 = r2.iterator()
        L_0x0102:
            boolean r18 = r5.hasNext()
            if (r18 == 0) goto L_0x0121
            java.lang.Object r18 = r5.next()
            r19 = r18
            zendesk.conversationkit.android.model.Field r19 = (zendesk.conversationkit.android.model.Field) r19
            r20 = 0
            java.lang.String r1 = r19.a()
            java.lang.String r4 = r6.a()
            boolean r1 = kotlin.jvm.internal.k.a(r1, r4)
            if (r1 == 0) goto L_0x0102
            goto L_0x0123
        L_0x0121:
            r18 = 0
        L_0x0123:
            r1 = r18
            zendesk.conversationkit.android.model.Field r1 = (zendesk.conversationkit.android.model.Field) r1
            boolean r2 = r1 instanceof zendesk.conversationkit.android.model.Field.Email
            if (r2 != 0) goto L_0x012d
            goto L_0x01bb
        L_0x012d:
            r23 = r6
            zendesk.conversationkit.android.model.Field$Email r23 = (zendesk.conversationkit.android.model.Field.Email) r23
            r24 = 0
            r25 = 0
            r26 = 0
            r2 = r1
            zendesk.conversationkit.android.model.Field$Email r2 = (zendesk.conversationkit.android.model.Field.Email) r2
            java.lang.String r27 = r2.d()
            r28 = 0
            r29 = 23
            r30 = 0
            zendesk.conversationkit.android.model.Field$Email r2 = zendesk.conversationkit.android.model.Field.Email.f(r23, r24, r25, r26, r27, r28, r29, r30)
            goto L_0x01ba
        L_0x014a:
            boolean r1 = r6 instanceof zendesk.conversationkit.android.model.Field.Select
            if (r1 == 0) goto L_0x01c0
            r1 = r15
            zendesk.conversationkit.android.model.MessageContent$Form r1 = (zendesk.conversationkit.android.model.MessageContent.Form) r1
            java.util.List r1 = r1.c()
            r2 = 0
            java.util.Iterator r3 = r1.iterator()
        L_0x015a:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0181
            java.lang.Object r4 = r3.next()
            r5 = r4
            zendesk.conversationkit.android.model.Field r5 = (zendesk.conversationkit.android.model.Field) r5
            r18 = 0
            r19 = r1
            java.lang.String r1 = r5.a()
            r20 = r2
            java.lang.String r2 = r6.a()
            boolean r1 = kotlin.jvm.internal.k.a(r1, r2)
            if (r1 == 0) goto L_0x017c
            goto L_0x0186
        L_0x017c:
            r1 = r19
            r2 = r20
            goto L_0x015a
        L_0x0181:
            r19 = r1
            r20 = r2
            r4 = 0
        L_0x0186:
            r1 = r4
            zendesk.conversationkit.android.model.Field r1 = (zendesk.conversationkit.android.model.Field) r1
            boolean r2 = r1 instanceof zendesk.conversationkit.android.model.Field.Select
            if (r2 != 0) goto L_0x018e
            goto L_0x01bb
        L_0x018e:
            r23 = r6
            zendesk.conversationkit.android.model.Field$Select r23 = (zendesk.conversationkit.android.model.Field.Select) r23
            r2 = r1
            zendesk.conversationkit.android.model.Field$Select r2 = (zendesk.conversationkit.android.model.Field.Select) r2
            java.lang.String r27 = r2.d()
            r2 = r1
            zendesk.conversationkit.android.model.Field$Select r2 = (zendesk.conversationkit.android.model.Field.Select) r2
            int r29 = r2.i()
            r2 = r1
            zendesk.conversationkit.android.model.Field$Select r2 = (zendesk.conversationkit.android.model.Field.Select) r2
            java.util.List r28 = r2.g()
            r24 = 0
            r25 = 0
            r26 = 0
            r30 = 0
            r31 = 71
            r32 = 0
            zendesk.conversationkit.android.model.Field$Select r2 = zendesk.conversationkit.android.model.Field.Select.f(r23, r24, r25, r26, r27, r28, r29, r30, r31, r32)
        L_0x01ba:
            r6 = r2
        L_0x01bb:
            r9.add(r6)
            goto L_0x0077
        L_0x01c0:
            kotlin.NoWhenBranchMatchedException r0 = new kotlin.NoWhenBranchMatchedException
            r0.<init>()
            throw r0
        L_0x01c6:
            r1 = 1
            r2 = 0
            zendesk.conversationkit.android.model.MessageContent$FormResponse r6 = zendesk.conversationkit.android.model.MessageContent.FormResponse.c(r0, r2, r9, r1, r2)
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r11 = 991(0x3df, float:1.389E-42)
            r12 = 0
            r0 = r33
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            zendesk.conversationkit.android.model.Message r0 = zendesk.conversationkit.android.model.Message.b(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12)
            goto L_0x01e2
        L_0x01e1:
            r0 = r13
        L_0x01e2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.conversationkit.android.model.t.a(zendesk.conversationkit.android.model.Message, zendesk.conversationkit.android.model.Conversation):zendesk.conversationkit.android.model.Message");
    }
}
