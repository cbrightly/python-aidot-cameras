package zendesk.messaging.android.internal.conversationscreen;

import java.util.Map;
import kotlin.jvm.functions.l;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.MessageContent;
import zendesk.conversationkit.android.model.u;

/* compiled from: MessageLogEntryMapper.kt */
public final class p {
    /* access modifiers changed from: private */
    public static final Message b(Message $this$overrideWithQuotedMessageDetails, l<? super String, Message> quotedMessageFinder) {
        Message invoke;
        MessageContent content = $this$overrideWithQuotedMessageDetails.d();
        if (!(content instanceof MessageContent.FormResponse) || (invoke = quotedMessageFinder.invoke(((MessageContent.FormResponse) content).e())) == null) {
            return $this$overrideWithQuotedMessageDetails;
        }
        Message form = invoke;
        return Message.b($this$overrideWithQuotedMessageDetails, (String) null, form.c(), (u) null, form.e(), form.k(), (MessageContent) null, (Map) null, (String) null, (String) null, (String) null, 997, (Object) null);
    }
}
