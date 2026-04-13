package zendesk.messaging.android.internal.conversationscreen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.MessageAction;
import zendesk.conversationkit.android.model.MessageContent;
import zendesk.conversationkit.android.model.u;
import zendesk.conversationkit.android.model.v;
import zendesk.messaging.android.internal.model.b;
import zendesk.messaging.android.internal.model.c;
import zendesk.messaging.android.internal.model.d;
import zendesk.messaging.android.internal.model.e;
import zendesk.messaging.android.internal.model.f;

/* compiled from: MessageContainerFactory.kt */
public final class n {
    @NotNull
    private final q a;
    @NotNull
    private final r b;
    @NotNull
    private final kotlin.jvm.functions.a<Long> c;

    /* compiled from: MessageContainerFactory.kt */
    public final /* synthetic */ class b {
        public static final /* synthetic */ int[] a;
        public static final /* synthetic */ int[] b;

        static {
            int[] iArr = new int[v.values().length];
            iArr[v.UNSUPPORTED.ordinal()] = 1;
            iArr[v.LIST.ordinal()] = 2;
            iArr[v.LOCATION.ordinal()] = 3;
            iArr[v.FORM.ordinal()] = 4;
            iArr[v.FORM_RESPONSE.ordinal()] = 5;
            iArr[v.IMAGE.ordinal()] = 6;
            iArr[v.FILE.ordinal()] = 7;
            iArr[v.FILE_UPLOAD.ordinal()] = 8;
            iArr[v.CAROUSEL.ordinal()] = 9;
            iArr[v.TEXT.ordinal()] = 10;
            a = iArr;
            int[] iArr2 = new int[u.values().length];
            iArr2[u.PENDING.ordinal()] = 1;
            iArr2[u.SENT.ordinal()] = 2;
            iArr2[u.FAILED.ordinal()] = 3;
            b = iArr2;
        }
    }

    public n(@NotNull q labelProvider, @NotNull r timestampFormatter, @NotNull kotlin.jvm.functions.a<Long> currentTimeProvider) {
        k.e(labelProvider, "labelProvider");
        k.e(timestampFormatter, "timestampFormatter");
        k.e(currentTimeProvider, "currentTimeProvider");
        this.a = labelProvider;
        this.b = timestampFormatter;
        this.c = currentTimeProvider;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ n(q qVar, r rVar, kotlin.jvm.functions.a aVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(qVar, rVar, (i & 4) != 0 ? a.INSTANCE : aVar);
    }

    /* compiled from: MessageContainerFactory.kt */
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

    private final Date d(Message $this$date) {
        Date e = $this$date.e();
        return e == null ? $this$date.k() : e;
    }

    @NotNull
    public final List<zendesk.messaging.android.internal.model.b> a(@NotNull Message message, @NotNull zendesk.messaging.android.internal.model.a direction, @NotNull c position, @NotNull e shape, boolean isLatestMessage) {
        k.e(message, "message");
        k.e(direction, "direction");
        k.e(position, "position");
        k.e(shape, "shape");
        switch (b.a[message.d().a().ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return c(message, direction, position, shape, isLatestMessage);
            case 10:
                return b(message, direction, position, shape, isLatestMessage);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    private final List<zendesk.messaging.android.internal.model.b> c(Message message, zendesk.messaging.android.internal.model.a direction, c position, e shape, boolean isLatestMessage) {
        zendesk.messaging.android.internal.model.a aVar = direction;
        c cVar = position;
        MessageContent d = message.d();
        MessageContent.FormResponse formResponse = d instanceof MessageContent.FormResponse ? (MessageContent.FormResponse) d : null;
        String e = formResponse == null ? null : formResponse.e();
        if (e == null) {
            e = message.g();
        }
        String str = e;
        String d2 = message.c().d();
        String str2 = d2;
        c cVar2 = c.STANDALONE;
        boolean z = true;
        String str3 = (cVar == cVar2 || cVar == c.GROUP_TOP) && aVar == zendesk.messaging.android.internal.model.a.INBOUND ? d2 : null;
        String c2 = message.c().c();
        String str4 = c2;
        String str5 = (cVar == cVar2 || cVar == c.GROUP_BOTTOM) && aVar == zendesk.messaging.android.internal.model.a.INBOUND ? c2 : null;
        u m = message.m();
        d e2 = e(message, direction);
        d dVar = e2;
        if (!isLatestMessage && message.m() != u.FAILED) {
            z = false;
        }
        return p.b(new b.a(str, str3, str5, direction, position, shape, m, message, z ? e2 : null));
    }

    private final List<zendesk.messaging.android.internal.model.b> b(Message message, zendesk.messaging.android.internal.model.a direction, c position, e shape, boolean isLatestMessage) {
        Iterable $this$filterIsInstance$iv;
        zendesk.messaging.android.internal.model.a aVar = direction;
        c cVar = position;
        List arrayList = new ArrayList();
        List $this$createMultipleMessageContainer_u24lambda_u2d6 = arrayList;
        String g = message.g();
        String d = message.c().d();
        String str = d;
        c cVar2 = c.STANDALONE;
        List list = null;
        String str2 = (cVar == cVar2 || cVar == c.GROUP_TOP) && aVar == zendesk.messaging.android.internal.model.a.INBOUND ? d : null;
        String c2 = message.c().c();
        String str3 = c2;
        String str4 = (cVar == cVar2 || cVar == c.GROUP_BOTTOM) && aVar == zendesk.messaging.android.internal.model.a.INBOUND ? c2 : null;
        u m = message.m();
        d e = e(message, direction);
        d dVar = e;
        b.a aVar2 = r0;
        b.a aVar3 = new b.a(g, str2, str4, direction, position, shape, m, message, isLatestMessage || message.m() == u.FAILED ? e : null);
        $this$createMultipleMessageContainer_u24lambda_u2d6.add(aVar2);
        if (isLatestMessage) {
            MessageContent d2 = message.d();
            MessageContent.Text text = d2 instanceof MessageContent.Text ? (MessageContent.Text) d2 : null;
            if (!(text == null || ($this$filterIsInstance$iv = text.b()) == null)) {
                List arrayList2 = new ArrayList();
                for (Object element$iv$iv : $this$filterIsInstance$iv) {
                    if (element$iv$iv instanceof MessageAction.Reply) {
                        arrayList2.add(element$iv$iv);
                    }
                }
                list = arrayList2;
            }
            List replies = list;
            if (!(replies == null || replies.isEmpty())) {
                $this$createMultipleMessageContainer_u24lambda_u2d6.add(new b.C0546b(message.g(), replies));
            }
        }
        return arrayList;
    }

    private final d e(Message message, zendesk.messaging.android.internal.model.a direction) {
        String str;
        f fVar;
        Date timestamp = d(message);
        u status = message.m();
        boolean isWithinLastSixtySeconds = this.c.invoke().longValue() - timestamp.getTime() <= 60000;
        if (direction == zendesk.messaging.android.internal.model.a.OUTBOUND) {
            if (status == u.PENDING) {
                str = this.a.d();
            } else if (status == u.FAILED) {
                str = this.a.g();
            } else if (isWithinLastSixtySeconds) {
                str = this.a.f();
            } else {
                str = this.a.e(this.b.b(timestamp));
            }
        } else if (status == u.FAILED && (message.d().a() == v.FORM || message.d().a() == v.FORM_RESPONSE)) {
            str = this.a.a();
        } else if (isWithinLastSixtySeconds) {
            str = this.a.b();
        } else {
            str = this.b.b(timestamp);
        }
        switch (b.b[status.ordinal()]) {
            case 1:
                fVar = f.TAIL_SENDING;
                break;
            case 2:
                fVar = f.TAIL_SENT;
                break;
            case 3:
                fVar = f.FAILED;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        return new d(str, fVar);
    }
}
