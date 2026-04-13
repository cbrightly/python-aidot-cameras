package zendesk.conversationkit.android.model;

import org.jetbrains.annotations.NotNull;

/* compiled from: ActivityData.kt */
public enum a {
    TYPING_START("typing:start"),
    TYPING_STOP("typing:stop"),
    CONVERSATION_READ("conversation:read");
    
    @NotNull
    private final String type;

    private a(String type2) {
        this.type = type2;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }
}
