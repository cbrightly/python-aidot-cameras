package zendesk.messaging.android.internal;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.model.Conversation;
import zendesk.conversationkit.android.model.Message;
import zendesk.conversationkit.android.model.Participant;

/* compiled from: NewMessagesDividerHandler.kt */
public final class i {
    @NotNull
    private final Map<String, Date> a = new LinkedHashMap();

    @Nullable
    public final Date b(@NotNull String conversationId) {
        k.e(conversationId, "conversationId");
        return this.a.get(conversationId);
    }

    public final void d(@NotNull Conversation conversation) {
        boolean z;
        k.e(conversation, "conversation");
        Participant l = conversation.l();
        Date lastReadDate = l == null ? null : l.d();
        if (c(conversation) && lastReadDate != null) {
            Map<String, Date> map = this.a;
            String i = conversation.i();
            for (Iterable $this$first$iv : conversation.k()) {
                Message it = (Message) $this$first$iv;
                if (it.n(conversation.l()) || it.k().compareTo(lastReadDate) <= 0) {
                    z = false;
                    continue;
                } else {
                    z = true;
                    continue;
                }
                if (z) {
                    map.put(i, ((Message) $this$first$iv).k());
                    return;
                }
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
    }

    public final void a(@NotNull String conversationId) {
        k.e(conversationId, "conversationId");
        this.a.remove(conversationId);
    }

    private final boolean c(Conversation $this$hasNewInboundMessages) {
        Object maxElem$iv;
        if (!$this$hasNewInboundMessages.k().isEmpty()) {
            Iterable $this$filterTo$iv$iv = $this$hasNewInboundMessages.k();
            ArrayList $this$maxByOrNull$iv = new ArrayList();
            for (T next : $this$filterTo$iv$iv) {
                if (!((Message) next).n($this$hasNewInboundMessages.l())) {
                    $this$maxByOrNull$iv.add(next);
                }
            }
            Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
            Date date = null;
            if (!iterator$iv.hasNext()) {
                maxElem$iv = null;
            } else {
                maxElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    Comparable maxValue$iv = ((Message) maxElem$iv).k();
                    do {
                        Object e$iv = iterator$iv.next();
                        Comparable v$iv = ((Message) e$iv).k();
                        if (maxValue$iv.compareTo(v$iv) < 0) {
                            maxElem$iv = e$iv;
                            maxValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            }
            Message message = (Message) maxElem$iv;
            Date lastMessageTimestamp = message == null ? null : message.k();
            Participant l = $this$hasNewInboundMessages.l();
            if (l != null) {
                date = l.d();
            }
            Date lastRead = date;
            if (lastRead != null) {
                if (lastRead.compareTo(lastMessageTimestamp) < 0) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
