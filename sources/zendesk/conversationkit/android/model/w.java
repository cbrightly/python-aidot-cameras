package zendesk.conversationkit.android.model;

import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.conversationkit.android.internal.extension.c;
import zendesk.conversationkit.android.internal.rest.model.ParticipantDto;

/* compiled from: Participant.kt */
public final class w {
    @NotNull
    public static final Participant a(@NotNull ParticipantDto $this$toParticipant) {
        k.e($this$toParticipant, "<this>");
        String b = $this$toParticipant.b();
        String a = $this$toParticipant.a();
        Integer d = $this$toParticipant.d();
        return new Participant(b, a, d == null ? 0 : d.intValue(), c.b($this$toParticipant.c()));
    }
}
