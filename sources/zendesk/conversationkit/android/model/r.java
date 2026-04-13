package zendesk.conversationkit.android.model;

import java.util.ArrayList;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.rest.model.MessageActionDto;
import zendesk.conversationkit.android.internal.rest.model.MessageItemDto;

/* compiled from: MessageItem.kt */
public final class r {
    @NotNull
    public static final MessageItem a(@NotNull MessageItemDto $this$toItem) {
        k.e($this$toItem, "<this>");
        String g = $this$toItem.g();
        String b = $this$toItem.b();
        Iterable<MessageActionDto> $this$mapNotNullTo$iv$iv = $this$toItem.a();
        ArrayList arrayList = new ArrayList();
        for (MessageActionDto it : $this$mapNotNullTo$iv$iv) {
            Object it$iv$iv = p.a(it);
            if (it$iv$iv != null) {
                arrayList.add(it$iv$iv);
            }
        }
        return new MessageItem(g, b, arrayList, k.a($this$toItem.f(), "large") ? s.LARGE : s.COMPACT, $this$toItem.e(), $this$toItem.d(), $this$toItem.c());
    }
}
