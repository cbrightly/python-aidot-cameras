package zendesk.conversationkit.android.model;

import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.model.MessageActionDto;
import zendesk.conversationkit.android.model.MessageAction;

/* compiled from: MessageAction.kt */
public final class p {

    /* compiled from: MessageAction.kt */
    public final /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[q.values().length];
            iArr[q.BUY.ordinal()] = 1;
            iArr[q.LINK.ordinal()] = 2;
            iArr[q.LOCATION_REQUEST.ordinal()] = 3;
            iArr[q.POSTBACK.ordinal()] = 4;
            iArr[q.REPLY.ordinal()] = 5;
            iArr[q.SHARE.ordinal()] = 6;
            iArr[q.WEBVIEW.ordinal()] = 7;
            a = iArr;
        }
    }

    @Nullable
    public static final MessageAction a(@NotNull MessageActionDto $this$toAction) {
        k.e($this$toAction, "<this>");
        q a2 = q.Companion.a($this$toAction.k());
        boolean z = false;
        String str = "";
        switch (a2 == null ? -1 : a.a[a2.ordinal()]) {
            case -1:
                return null;
            case 1:
                String f = $this$toAction.f();
                Map<String, Object> g = $this$toAction.g();
                String j = $this$toAction.j();
                String str2 = j == null ? str : j;
                String l = $this$toAction.l();
                String str3 = l == null ? str : l;
                Long a3 = $this$toAction.a();
                long longValue = a3 == null ? 0 : a3.longValue();
                String b = $this$toAction.b();
                return new MessageAction.Buy(f, g, str2, str3, longValue, b == null ? str : b, k.a($this$toAction.i(), "paid") ? o.PAID : o.OFFERED);
            case 2:
                String f2 = $this$toAction.f();
                Map<String, Object> g2 = $this$toAction.g();
                String j2 = $this$toAction.j();
                String str4 = j2 == null ? str : j2;
                String l2 = $this$toAction.l();
                String str5 = l2 == null ? str : l2;
                Boolean c = $this$toAction.c();
                if (c != null) {
                    z = c.booleanValue();
                }
                return new MessageAction.Link(f2, g2, str4, str5, z);
            case 3:
                String f3 = $this$toAction.f();
                Map<String, Object> g3 = $this$toAction.g();
                String j3 = $this$toAction.j();
                if (j3 != null) {
                    str = j3;
                }
                return new MessageAction.LocationRequest(f3, g3, str);
            case 4:
                String f4 = $this$toAction.f();
                Map<String, Object> g4 = $this$toAction.g();
                String j4 = $this$toAction.j();
                if (j4 == null) {
                    j4 = str;
                }
                String h = $this$toAction.h();
                if (h != null) {
                    str = h;
                }
                return new MessageAction.Postback(f4, g4, j4, str);
            case 5:
                String f5 = $this$toAction.f();
                Map<String, Object> g5 = $this$toAction.g();
                String j5 = $this$toAction.j();
                String str6 = j5 == null ? str : j5;
                String e = $this$toAction.e();
                String h2 = $this$toAction.h();
                return new MessageAction.Reply(f5, g5, str6, e, h2 == null ? str : h2);
            case 6:
                return new MessageAction.Share($this$toAction.f(), $this$toAction.g());
            case 7:
                String f6 = $this$toAction.f();
                Map<String, Object> g6 = $this$toAction.g();
                String j6 = $this$toAction.j();
                String str7 = j6 == null ? str : j6;
                String l3 = $this$toAction.l();
                String str8 = l3 == null ? str : l3;
                String d = $this$toAction.d();
                String str9 = d == null ? str : d;
                Boolean c2 = $this$toAction.c();
                if (c2 != null) {
                    z = c2.booleanValue();
                }
                return new MessageAction.WebView(f6, g6, str7, str8, str9, z);
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
