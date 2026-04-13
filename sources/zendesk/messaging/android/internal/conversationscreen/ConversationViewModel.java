package zendesk.messaging.android.internal.conversationscreen;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.ui.android.conversation.form.DisplayedField;

/* compiled from: ConversationViewModel.kt */
public final class ConversationViewModel extends ViewModel {
    @NotNull
    private static final a a = new a((DefaultConstructorMarker) null);
    @NotNull
    private final SavedStateHandle b;
    @NotNull
    private final Map<Integer, DisplayedField> c = new LinkedHashMap();

    public ConversationViewModel(@NotNull SavedStateHandle savedStateHandle) {
        k.e(savedStateHandle, "savedStateHandle");
        this.b = savedStateHandle;
    }

    @NotNull
    public final Map<Integer, DisplayedField> a() {
        Map savedDisplayedFields = (Map) this.b.get("mapOfDisplayedFields");
        if (savedDisplayedFields == null || savedDisplayedFields.isEmpty()) {
            return this.c;
        }
        return savedDisplayedFields;
    }

    public final void b(@NotNull DisplayedField displayedField) {
        k.e(displayedField, "displayedField");
        this.c.put(Integer.valueOf(displayedField.a()), displayedField);
        this.b.set("mapOfDisplayedFields", this.c);
    }

    /* compiled from: ConversationViewModel.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
