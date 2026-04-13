package zendesk.messaging.android.internal.conversationscreen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.collections.y;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.n;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.MessageContent;
import zendesk.conversationkit.android.model.Participant;
import zendesk.conversationkit.android.model.v;
import zendesk.messaging.android.internal.model.b;
import zendesk.messaging.android.internal.model.g;

/* compiled from: MessageLogEntryMapper.kt */
public final class o {
    @NotNull
    private final n a;
    @NotNull
    private final q b;
    @NotNull
    private final r c;
    @NotNull
    private final kotlin.jvm.functions.a<Long> d;
    @NotNull
    private final kotlin.jvm.functions.a<String> e;
    @NotNull
    private final List<v> f;

    public o(@NotNull n messageContainerFactory, @NotNull q labelProvider, @NotNull r timestampFormatter, @NotNull kotlin.jvm.functions.a<Long> currentTimeProvider, @NotNull kotlin.jvm.functions.a<String> idProvider) {
        k.e(messageContainerFactory, "messageContainerFactory");
        k.e(labelProvider, "labelProvider");
        k.e(timestampFormatter, "timestampFormatter");
        k.e(currentTimeProvider, "currentTimeProvider");
        k.e(idProvider, "idProvider");
        this.a = messageContainerFactory;
        this.b = labelProvider;
        this.c = timestampFormatter;
        this.d = currentTimeProvider;
        this.e = idProvider;
        this.f = q.j(v.TEXT, v.FILE, v.IMAGE, v.UNSUPPORTED);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ o(zendesk.messaging.android.internal.conversationscreen.n r7, zendesk.messaging.android.internal.conversationscreen.q r8, zendesk.messaging.android.internal.conversationscreen.r r9, kotlin.jvm.functions.a r10, kotlin.jvm.functions.a r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r6 = this;
            r13 = r12 & 8
            if (r13 == 0) goto L_0x0008
            zendesk.messaging.android.internal.conversationscreen.o$a r10 = zendesk.messaging.android.internal.conversationscreen.o.a.INSTANCE
            r4 = r10
            goto L_0x0009
        L_0x0008:
            r4 = r10
        L_0x0009:
            r10 = r12 & 16
            if (r10 == 0) goto L_0x0011
            zendesk.messaging.android.internal.conversationscreen.o$b r11 = zendesk.messaging.android.internal.conversationscreen.o.b.INSTANCE
            r5 = r11
            goto L_0x0012
        L_0x0011:
            r5 = r11
        L_0x0012:
            r0 = r6
            r1 = r7
            r2 = r8
            r3 = r9
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.o.<init>(zendesk.messaging.android.internal.conversationscreen.n, zendesk.messaging.android.internal.conversationscreen.q, zendesk.messaging.android.internal.conversationscreen.r, kotlin.jvm.functions.a, kotlin.jvm.functions.a, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* compiled from: MessageLogEntryMapper.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<Long> {
        public static final a INSTANCE = new a();

        a() {
            super(0);
        }

        @NotNull
        public final Long invoke() {
            return Long.valueOf(System.currentTimeMillis());
        }
    }

    /* compiled from: MessageLogEntryMapper.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<String> {
        public static final b INSTANCE = new b();

        b() {
            super(0);
        }

        @NotNull
        public final String invoke() {
            String uuid = UUID.randomUUID().toString();
            k.d(uuid, "randomUUID().toString()");
            return uuid;
        }
    }

    private final Date c(Message $this$date) {
        Date e2 = $this$date.e();
        return e2 == null ? $this$date.k() : e2;
    }

    @NotNull
    public final List<zendesk.messaging.android.internal.model.b> g(@NotNull Conversation conversation, @Nullable Date newMessageDividerDate, @NotNull g typingUser) {
        n nVar;
        g gVar;
        List $this$map_u24lambda_u2d6;
        Conversation conversation2 = conversation;
        Date date = newMessageDividerDate;
        g gVar2 = typingUser;
        k.e(conversation2, "conversation");
        k.e(gVar2, "typingUser");
        List arrayList = new ArrayList();
        List $this$map_u24lambda_u2d62 = arrayList;
        Iterable<Message> $this$mapNotNull$iv = conversation.k();
        ArrayList arrayList2 = new ArrayList();
        for (Message it : $this$mapNotNull$iv) {
            MessageContent d2 = it.d();
            Iterable $this$mapNotNull$iv2 = $this$mapNotNull$iv;
            MessageContent.FormResponse formResponse = d2 instanceof MessageContent.FormResponse ? (MessageContent.FormResponse) d2 : null;
            Object it$iv$iv = formResponse == null ? null : formResponse.e();
            if (it$iv$iv != null) {
                arrayList2.add(it$iv$iv);
            }
            $this$mapNotNull$iv = $this$mapNotNull$iv2;
        }
        ArrayList arrayList3 = arrayList2;
        Iterable $this$filterNotTo$iv$iv = conversation.k();
        ArrayList arrayList4 = new ArrayList();
        for (T next : $this$filterNotTo$iv$iv) {
            Message message = (Message) next;
            if (!(message.d().a() == v.FORM && arrayList3.contains(message.g()))) {
                arrayList4.add(next);
            }
        }
        Iterable<Message> $this$mapTo$iv$iv = arrayList4;
        ArrayList $this$sortedBy$iv = new ArrayList(r.r($this$mapTo$iv$iv, 10));
        for (Message message2 : $this$mapTo$iv$iv) {
            $this$sortedBy$iv.add(p.b(message2, new d(conversation2)));
        }
        List messagesToShow = y.u0($this$sortedBy$iv, new e());
        if (messagesToShow.isEmpty()) {
            g gVar3 = gVar2;
        } else {
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            if (date == null) {
                nVar = null;
            } else {
                Date it2 = newMessageDividerDate;
                int i = false;
                ArrayList first$iv = new ArrayList();
                ArrayList second$iv = new ArrayList();
                for (Object element$iv : messagesToShow) {
                    Date it3 = it2;
                    int i2 = i;
                    if (c((Message) element$iv).compareTo(date) < 0) {
                        first$iv.add(element$iv);
                    } else {
                        second$iv.add(element$iv);
                    }
                    it2 = it3;
                    i = i2;
                }
                int i3 = i;
                nVar = new n(first$iv, second$iv);
            }
            if (nVar == null) {
                nVar = new n(messagesToShow, q.g());
            }
            List readMessages = (List) nVar.component1();
            List unreadMessages = (List) nVar.component2();
            i(this, readMessages, conversation.l(), (Message) null, (Message) (unreadMessages.isEmpty() ? y.d0(readMessages) : y.d0(unreadMessages)), linkedHashSet, $this$map_u24lambda_u2d62, 2, (Object) null);
            if (!unreadMessages.isEmpty()) {
                if (!((Message) y.S(unreadMessages)).n(conversation.l())) {
                    String date2 = date == null ? null : newMessageDividerDate.toString();
                    if (date2 == null) {
                        date2 = this.e.invoke();
                    }
                    k.d(date2, "newMessageDividerDate?.toString() ?: idProvider()");
                    $this$map_u24lambda_u2d62.add(new b.e(date2, this.b.c()));
                }
                List list = messagesToShow;
                ArrayList arrayList5 = arrayList3;
                $this$map_u24lambda_u2d6 = $this$map_u24lambda_u2d62;
                gVar = gVar2;
                h(unreadMessages, conversation.l(), (Message) y.f0(readMessages), (Message) y.d0(unreadMessages), linkedHashSet, $this$map_u24lambda_u2d6);
            } else {
                ArrayList arrayList6 = arrayList3;
                $this$map_u24lambda_u2d6 = $this$map_u24lambda_u2d62;
                gVar = gVar2;
            }
            if (gVar instanceof g.b) {
                $this$map_u24lambda_u2d6.add(new b.d((String) null, ((g.b) gVar).a(), 1, (DefaultConstructorMarker) null));
            }
        }
        return arrayList;
    }

    /* compiled from: MessageLogEntryMapper.kt */
    public static final class d extends l implements kotlin.jvm.functions.l<String, Message> {
        final /* synthetic */ Conversation $conversation;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(Conversation conversation) {
            super(1);
            this.$conversation = conversation;
        }

        @Nullable
        public final Message invoke(@NotNull String quotedMessageId) {
            T t;
            k.e(quotedMessageId, "quotedMessageId");
            Iterator<T> it = this.$conversation.k().iterator();
            while (true) {
                if (!it.hasNext()) {
                    t = null;
                    break;
                }
                t = it.next();
                if (k.a(((Message) t).g(), quotedMessageId)) {
                    break;
                }
            }
            return (Message) t;
        }
    }

    /* compiled from: Comparisons.kt */
    public static final class e<T> implements Comparator {
        public final int compare(T a, T b) {
            Message it = (Message) a;
            Date e = it.e();
            if (e == null) {
                e = it.k();
            }
            Message it2 = b;
            Date e2 = it2.e();
            if (e2 == null) {
                e2 = it2.k();
            }
            return kotlin.comparisons.a.c(e, e2);
        }
    }

    static /* synthetic */ void i(o oVar, List list, Participant participant, Message message, Message message2, Set set, List list2, int i, Object obj) {
        Message message3;
        if ((i & 2) != 0) {
            message3 = null;
        } else {
            message3 = message;
        }
        oVar.h(list, participant, message3, message2, set, list2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v0, resolved type: zendesk.conversationkit.android.model.Message} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void h(java.util.List<zendesk.conversationkit.android.model.Message> r25, zendesk.conversationkit.android.model.Participant r26, zendesk.conversationkit.android.model.Message r27, zendesk.conversationkit.android.model.Message r28, java.util.Set<java.util.Date> r29, java.util.List<zendesk.messaging.android.internal.model.b> r30) {
        /*
            r24 = this;
            r0 = r24
            r1 = r25
            r2 = r30
            r3 = r25
            r4 = 0
            r5 = 0
            java.util.Iterator r6 = r3.iterator()
        L_0x000e:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x009b
            java.lang.Object r7 = r6.next()
            int r8 = r5 + 1
            if (r5 >= 0) goto L_0x001f
            kotlin.collections.q.q()
        L_0x001f:
            r15 = r7
            zendesk.conversationkit.android.model.Message r15 = (zendesk.conversationkit.android.model.Message) r15
            r16 = 0
            int r9 = r5 + -1
            java.lang.Object r9 = kotlin.collections.y.V(r1, r9)
            r14 = r9
            zendesk.conversationkit.android.model.Message r14 = (zendesk.conversationkit.android.model.Message) r14
            zendesk.messaging.android.internal.conversationscreen.o$c r13 = r0.b(r15, r14)
            int r9 = r5 + 1
            java.lang.Object r9 = kotlin.collections.y.V(r1, r9)
            r12 = r9
            zendesk.conversationkit.android.model.Message r12 = (zendesk.conversationkit.android.model.Message) r12
            zendesk.messaging.android.internal.conversationscreen.o$c r10 = r0.a(r15, r12)
            r9 = r26
            boolean r11 = r15.n(r9)
            if (r11 == 0) goto L_0x0049
            zendesk.messaging.android.internal.model.a r11 = zendesk.messaging.android.internal.model.a.OUTBOUND
            goto L_0x004b
        L_0x0049:
            zendesk.messaging.android.internal.model.a r11 = zendesk.messaging.android.internal.model.a.INBOUND
        L_0x004b:
            zendesk.messaging.android.internal.model.c r1 = r0.d(r13, r10)
            zendesk.messaging.android.internal.model.e r17 = r0.e(r15, r1, r13, r10)
            r18 = r3
            if (r14 != 0) goto L_0x0065
            r3 = r27
            goto L_0x0066
        L_0x0065:
            r3 = r14
        L_0x0066:
            r19 = r4
            r4 = r29
            r0.f(r2, r15, r3, r4)
            zendesk.messaging.android.internal.conversationscreen.n r3 = r0.a
            r0 = r28
            boolean r20 = kotlin.jvm.internal.k.a(r0, r15)
            r9 = r3
            r3 = r10
            r10 = r15
            r21 = r12
            r12 = r1
            r22 = r13
            r13 = r17
            r23 = r14
            r14 = r20
            java.util.List r9 = r9.a(r10, r11, r12, r13, r14)
            r2.addAll(r9)
            r0 = r24
            r1 = r25
            r5 = r8
            r3 = r18
            r4 = r19
            goto L_0x000e
        L_0x009b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.o.h(java.util.List, zendesk.conversationkit.android.model.Participant, zendesk.conversationkit.android.model.Message, zendesk.conversationkit.android.model.Message, java.util.Set, java.util.List):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final zendesk.messaging.android.internal.conversationscreen.o.c b(zendesk.conversationkit.android.model.Message r10, zendesk.conversationkit.android.model.Message r11) {
        /*
            r9 = this;
            zendesk.conversationkit.android.model.u r0 = r10.m()
            zendesk.conversationkit.android.model.u r1 = zendesk.conversationkit.android.model.u.PENDING
            r2 = 0
            if (r0 == r1) goto L_0x0025
            if (r11 != 0) goto L_0x000d
            r0 = r2
            goto L_0x0011
        L_0x000d:
            zendesk.conversationkit.android.model.u r0 = r11.m()
        L_0x0011:
            if (r0 != r1) goto L_0x0014
            goto L_0x0025
        L_0x0014:
            zendesk.conversationkit.android.model.Author r0 = r10.c()
            if (r11 != 0) goto L_0x001c
            r3 = r2
            goto L_0x0020
        L_0x001c:
            zendesk.conversationkit.android.model.Author r3 = r11.c()
        L_0x0020:
            boolean r0 = kotlin.jvm.internal.k.a(r0, r3)
            goto L_0x0040
        L_0x0025:
            zendesk.conversationkit.android.model.Author r0 = r10.c()
            java.lang.String r0 = r0.f()
            if (r11 != 0) goto L_0x0031
        L_0x002f:
            r3 = r2
            goto L_0x003c
        L_0x0031:
            zendesk.conversationkit.android.model.Author r3 = r11.c()
            if (r3 != 0) goto L_0x0038
            goto L_0x002f
        L_0x0038:
            java.lang.String r3 = r3.f()
        L_0x003c:
            boolean r0 = kotlin.jvm.internal.k.a(r0, r3)
        L_0x0040:
            r3 = 1
            r4 = 0
            if (r11 != 0) goto L_0x0047
            r1 = r4
            goto L_0x0067
        L_0x0047:
            zendesk.conversationkit.android.model.u r5 = r10.m()
            if (r5 == r1) goto L_0x0055
            zendesk.conversationkit.android.model.u r5 = r10.m()
            zendesk.conversationkit.android.model.u r6 = zendesk.conversationkit.android.model.u.SENT
            if (r5 != r6) goto L_0x0064
        L_0x0055:
            zendesk.conversationkit.android.model.u r5 = r11.m()
            if (r5 == r1) goto L_0x0066
            zendesk.conversationkit.android.model.u r1 = r11.m()
            zendesk.conversationkit.android.model.u r5 = zendesk.conversationkit.android.model.u.SENT
            if (r1 != r5) goto L_0x0064
            goto L_0x0066
        L_0x0064:
            r1 = r4
            goto L_0x0067
        L_0x0066:
            r1 = r3
        L_0x0067:
            if (r11 != 0) goto L_0x006c
            r3 = r4
            goto L_0x0086
        L_0x006c:
            java.util.Date r5 = r9.c(r10)
            long r5 = r5.getTime()
            java.util.Date r7 = r9.c(r11)
            long r7 = r7.getTime()
            long r5 = r5 - r7
            r7 = 900000(0xdbba0, double:4.44659E-318)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x0085
            goto L_0x0086
        L_0x0085:
            r3 = r4
        L_0x0086:
            zendesk.messaging.android.internal.conversationscreen.o$c r4 = new zendesk.messaging.android.internal.conversationscreen.o$c
            java.util.List<zendesk.conversationkit.android.model.v> r5 = r9.f
            if (r11 != 0) goto L_0x0091
        L_0x0090:
            goto L_0x009c
        L_0x0091:
            zendesk.conversationkit.android.model.MessageContent r6 = r11.d()
            if (r6 != 0) goto L_0x0098
            goto L_0x0090
        L_0x0098:
            zendesk.conversationkit.android.model.v r2 = r6.a()
        L_0x009c:
            boolean r2 = kotlin.collections.y.M(r5, r2)
            r4.<init>(r0, r1, r3, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.o.b(zendesk.conversationkit.android.model.Message, zendesk.conversationkit.android.model.Message):zendesk.messaging.android.internal.conversationscreen.o$c");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006a  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x006c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final zendesk.messaging.android.internal.conversationscreen.o.c a(zendesk.conversationkit.android.model.Message r10, zendesk.conversationkit.android.model.Message r11) {
        /*
            r9 = this;
            zendesk.conversationkit.android.model.u r0 = r10.m()
            zendesk.conversationkit.android.model.u r1 = zendesk.conversationkit.android.model.u.PENDING
            r2 = 0
            if (r0 == r1) goto L_0x0025
            if (r11 != 0) goto L_0x000d
            r0 = r2
            goto L_0x0011
        L_0x000d:
            zendesk.conversationkit.android.model.u r0 = r11.m()
        L_0x0011:
            if (r0 != r1) goto L_0x0014
            goto L_0x0025
        L_0x0014:
            zendesk.conversationkit.android.model.Author r0 = r10.c()
            if (r11 != 0) goto L_0x001c
            r3 = r2
            goto L_0x0020
        L_0x001c:
            zendesk.conversationkit.android.model.Author r3 = r11.c()
        L_0x0020:
            boolean r0 = kotlin.jvm.internal.k.a(r0, r3)
            goto L_0x0040
        L_0x0025:
            zendesk.conversationkit.android.model.Author r0 = r10.c()
            java.lang.String r0 = r0.f()
            if (r11 != 0) goto L_0x0031
        L_0x002f:
            r3 = r2
            goto L_0x003c
        L_0x0031:
            zendesk.conversationkit.android.model.Author r3 = r11.c()
            if (r3 != 0) goto L_0x0038
            goto L_0x002f
        L_0x0038:
            java.lang.String r3 = r3.f()
        L_0x003c:
            boolean r0 = kotlin.jvm.internal.k.a(r0, r3)
        L_0x0040:
            r3 = 1
            r4 = 0
            if (r11 != 0) goto L_0x0047
            r1 = r4
            goto L_0x0067
        L_0x0047:
            zendesk.conversationkit.android.model.u r5 = r10.m()
            if (r5 == r1) goto L_0x0055
            zendesk.conversationkit.android.model.u r5 = r10.m()
            zendesk.conversationkit.android.model.u r6 = zendesk.conversationkit.android.model.u.SENT
            if (r5 != r6) goto L_0x0064
        L_0x0055:
            zendesk.conversationkit.android.model.u r5 = r11.m()
            if (r5 == r1) goto L_0x0066
            zendesk.conversationkit.android.model.u r1 = r11.m()
            zendesk.conversationkit.android.model.u r5 = zendesk.conversationkit.android.model.u.SENT
            if (r1 != r5) goto L_0x0064
            goto L_0x0066
        L_0x0064:
            r1 = r4
            goto L_0x0067
        L_0x0066:
            r1 = r3
        L_0x0067:
            if (r11 != 0) goto L_0x006c
            r3 = r4
            goto L_0x0086
        L_0x006c:
            java.util.Date r5 = r9.c(r11)
            long r5 = r5.getTime()
            java.util.Date r7 = r9.c(r10)
            long r7 = r7.getTime()
            long r5 = r5 - r7
            r7 = 900000(0xdbba0, double:4.44659E-318)
            int r5 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r5 >= 0) goto L_0x0085
            goto L_0x0086
        L_0x0085:
            r3 = r4
        L_0x0086:
            zendesk.messaging.android.internal.conversationscreen.o$c r4 = new zendesk.messaging.android.internal.conversationscreen.o$c
            java.util.List<zendesk.conversationkit.android.model.v> r5 = r9.f
            if (r11 != 0) goto L_0x0091
        L_0x0090:
            goto L_0x009c
        L_0x0091:
            zendesk.conversationkit.android.model.MessageContent r6 = r11.d()
            if (r6 != 0) goto L_0x0098
            goto L_0x0090
        L_0x0098:
            zendesk.conversationkit.android.model.v r2 = r6.a()
        L_0x009c:
            boolean r2 = kotlin.collections.y.M(r5, r2)
            r4.<init>(r0, r1, r3, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.o.a(zendesk.conversationkit.android.model.Message, zendesk.conversationkit.android.model.Message):zendesk.messaging.android.internal.conversationscreen.o$c");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a1 A[LOOP:0: B:12:0x0067->B:23:0x00a1, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x009f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void f(java.util.List<zendesk.messaging.android.internal.model.b> r19, zendesk.conversationkit.android.model.Message r20, zendesk.conversationkit.android.model.Message r21, java.util.Set<java.util.Date> r22) {
        /*
            r18 = this;
            r0 = r18
            r1 = r20
            r2 = r21
            java.util.Calendar r3 = java.util.Calendar.getInstance()
            r4 = r3
            r5 = 0
            java.util.Date r6 = r0.c(r1)
            r4.setTime(r6)
            java.lang.String r4 = "getInstance().apply {\n  … = message.date\n        }"
            kotlin.jvm.internal.k.d(r3, r4)
            java.util.Calendar r4 = java.util.Calendar.getInstance()
            r5 = r4
            r6 = 0
            java.util.Date r7 = new java.util.Date
            kotlin.jvm.functions.a<java.lang.Long> r8 = r0.d
            java.lang.Object r8 = r8.invoke()
            java.lang.Number r8 = (java.lang.Number) r8
            long r8 = r8.longValue()
            r7.<init>(r8)
            r5.setTime(r7)
            java.lang.String r5 = "getInstance().apply {\n  …TimeProvider())\n        }"
            kotlin.jvm.internal.k.d(r4, r5)
            r5 = 1
            int r6 = r4.get(r5)
            int r7 = r3.get(r5)
            r8 = 6
            if (r6 != r7) goto L_0x0052
            int r6 = r4.get(r8)
            int r7 = r3.get(r8)
            if (r6 == r7) goto L_0x0050
            goto L_0x0052
        L_0x0050:
            r6 = 0
            goto L_0x0053
        L_0x0052:
            r6 = r5
        L_0x0053:
            r7 = r22
            r10 = 0
            boolean r11 = r7 instanceof java.util.Collection
            if (r11 == 0) goto L_0x0063
            boolean r11 = r7.isEmpty()
            if (r11 == 0) goto L_0x0063
            r9 = 0
            goto L_0x00a4
        L_0x0063:
            java.util.Iterator r11 = r7.iterator()
        L_0x0067:
            boolean r12 = r11.hasNext()
            if (r12 == 0) goto L_0x00a3
            java.lang.Object r12 = r11.next()
            r13 = r12
            java.util.Date r13 = (java.util.Date) r13
            r14 = 0
            java.util.Calendar r15 = java.util.Calendar.getInstance()
            r16 = r15
            r17 = 0
            r9 = r16
            r9.setTime(r13)
            r9 = r15
            int r15 = r9.get(r5)
            int r8 = r3.get(r5)
            if (r15 != r8) goto L_0x009a
            r8 = 6
            int r15 = r9.get(r8)
            int r5 = r3.get(r8)
            if (r15 != r5) goto L_0x009b
            r5 = 1
            goto L_0x009c
        L_0x009a:
            r8 = 6
        L_0x009b:
            r5 = 0
        L_0x009c:
            if (r5 == 0) goto L_0x00a1
            r9 = 1
            goto L_0x00a4
        L_0x00a1:
            r5 = 1
            goto L_0x0067
        L_0x00a3:
            r9 = 0
        L_0x00a4:
            r5 = r9
            if (r2 == 0) goto L_0x00c3
            java.util.Date r7 = r0.c(r1)
            long r7 = r7.getTime()
            java.util.Date r9 = r0.c(r2)
            long r9 = r9.getTime()
            long r7 = r7 - r9
            r9 = 900000(0xdbba0, double:4.44659E-318)
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 < 0) goto L_0x00c0
            goto L_0x00c3
        L_0x00c0:
            r16 = 0
            goto L_0x00c5
        L_0x00c3:
            r16 = 1
        L_0x00c5:
            r7 = r16
            if (r6 == 0) goto L_0x00ed
            if (r5 != 0) goto L_0x00ed
            java.util.Date r8 = r0.c(r1)
            r9 = r22
            r9.add(r8)
            zendesk.messaging.android.internal.model.b$c$a r8 = new zendesk.messaging.android.internal.model.b$c$a
            kotlin.jvm.functions.a<java.lang.String> r10 = r0.e
            java.lang.Object r10 = r10.invoke()
            java.lang.String r10 = (java.lang.String) r10
            zendesk.messaging.android.internal.conversationscreen.r r11 = r0.c
            java.util.Date r12 = r0.c(r1)
            java.lang.String r11 = r11.a(r12)
            r8.<init>(r10, r11)
            goto L_0x0124
        L_0x00ed:
            r9 = r22
            if (r6 == 0) goto L_0x010b
            if (r7 == 0) goto L_0x010b
            zendesk.messaging.android.internal.model.b$c$a r8 = new zendesk.messaging.android.internal.model.b$c$a
            kotlin.jvm.functions.a<java.lang.String> r10 = r0.e
            java.lang.Object r10 = r10.invoke()
            java.lang.String r10 = (java.lang.String) r10
            zendesk.messaging.android.internal.conversationscreen.r r11 = r0.c
            java.util.Date r12 = r0.c(r1)
            java.lang.String r11 = r11.a(r12)
            r8.<init>(r10, r11)
            goto L_0x0124
        L_0x010b:
            if (r7 == 0) goto L_0x012b
            zendesk.messaging.android.internal.model.b$c$b r8 = new zendesk.messaging.android.internal.model.b$c$b
            kotlin.jvm.functions.a<java.lang.String> r10 = r0.e
            java.lang.Object r10 = r10.invoke()
            java.lang.String r10 = (java.lang.String) r10
            zendesk.messaging.android.internal.conversationscreen.r r11 = r0.c
            java.util.Date r12 = r0.c(r1)
            java.lang.String r11 = r11.b(r12)
            r8.<init>(r10, r11)
        L_0x0124:
            r10 = r19
            r10.add(r8)
            return
        L_0x012b:
            r10 = r19
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: zendesk.messaging.android.internal.conversationscreen.o.f(java.util.List, zendesk.conversationkit.android.model.Message, zendesk.conversationkit.android.model.Message, java.util.Set):void");
    }

    private final zendesk.messaging.android.internal.model.c d(c previousNeighbour, c nextNeighbour) {
        if (!previousNeighbour.a() && !nextNeighbour.a()) {
            return zendesk.messaging.android.internal.model.c.STANDALONE;
        }
        if (!previousNeighbour.a() && nextNeighbour.a()) {
            return zendesk.messaging.android.internal.model.c.GROUP_TOP;
        }
        if (!previousNeighbour.a() || nextNeighbour.a()) {
            return zendesk.messaging.android.internal.model.c.GROUP_MIDDLE;
        }
        return zendesk.messaging.android.internal.model.c.GROUP_BOTTOM;
    }

    private final zendesk.messaging.android.internal.model.e e(Message currentMessage, zendesk.messaging.android.internal.model.c currentMessagePosition, c previousNeighbour, c nextNeighbour) {
        boolean isGroupBottom = false;
        boolean isStandalone = currentMessagePosition == zendesk.messaging.android.internal.model.c.STANDALONE || !this.f.contains(currentMessage.d().a()) || (currentMessagePosition == zendesk.messaging.android.internal.model.c.GROUP_TOP && !nextNeighbour.b()) || (currentMessagePosition == zendesk.messaging.android.internal.model.c.GROUP_BOTTOM && !previousNeighbour.b());
        boolean isGroupTop = (currentMessagePosition == zendesk.messaging.android.internal.model.c.GROUP_TOP && nextNeighbour.b()) || (currentMessagePosition == zendesk.messaging.android.internal.model.c.GROUP_MIDDLE && !previousNeighbour.b());
        if ((currentMessagePosition == zendesk.messaging.android.internal.model.c.GROUP_BOTTOM && previousNeighbour.b()) || (currentMessagePosition == zendesk.messaging.android.internal.model.c.GROUP_MIDDLE && !nextNeighbour.b())) {
            isGroupBottom = true;
        }
        if (isStandalone) {
            return zendesk.messaging.android.internal.model.e.STANDALONE;
        }
        if (isGroupTop) {
            return zendesk.messaging.android.internal.model.e.GROUP_TOP;
        }
        if (isGroupBottom) {
            return zendesk.messaging.android.internal.model.e.GROUP_BOTTOM;
        }
        return zendesk.messaging.android.internal.model.e.GROUP_MIDDLE;
    }

    /* compiled from: MessageLogEntryMapper.kt */
    public static final class c {
        private final boolean a;
        private final boolean b;
        private final boolean c;
        private final boolean d;

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            return this.a == cVar.a && this.b == cVar.b && this.c == cVar.c && this.d == cVar.d;
        }

        public int hashCode() {
            boolean z = this.a;
            boolean z2 = true;
            if (z) {
                z = true;
            }
            int i = (z ? 1 : 0) * true;
            boolean z3 = this.b;
            if (z3) {
                z3 = true;
            }
            int i2 = (i + (z3 ? 1 : 0)) * 31;
            boolean z4 = this.c;
            if (z4) {
                z4 = true;
            }
            int i3 = (i2 + (z4 ? 1 : 0)) * 31;
            boolean z5 = this.d;
            if (!z5) {
                z2 = z5;
            }
            return i3 + (z2 ? 1 : 0);
        }

        @NotNull
        public String toString() {
            return "MessageNeighbour(sameAuthor=" + this.a + ", statusAllowGrouping=" + this.b + ", dateAllowsGrouping=" + this.c + ", allowsShapeGrouping=" + this.d + ')';
        }

        public c(boolean sameAuthor, boolean statusAllowGrouping, boolean dateAllowsGrouping, boolean allowsShapeGrouping) {
            this.a = sameAuthor;
            this.b = statusAllowGrouping;
            this.c = dateAllowsGrouping;
            this.d = allowsShapeGrouping;
        }

        public final boolean b() {
            return this.d;
        }

        public final boolean a() {
            return this.a && this.b && this.c;
        }
    }
}
