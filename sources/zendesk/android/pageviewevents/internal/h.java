package zendesk.android.pageviewevents.internal;

import com.google.android.libraries.places.api.model.PlaceTypes;
import java.util.concurrent.TimeUnit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.storage.android.c;

/* compiled from: PageViewStorage.kt */
public final class h {
    @NotNull
    public static final a a = new a((DefaultConstructorMarker) null);
    private static final long b = TimeUnit.MINUTES.toMillis(30);
    @NotNull
    private final c c;
    @NotNull
    private final zendesk.android.internal.h d;

    public h(@NotNull c storage, @NotNull zendesk.android.internal.h dispatchers) {
        k.e(storage, PlaceTypes.STORAGE);
        k.e(dispatchers, "dispatchers");
        this.c = storage;
        this.d = dispatchers;
    }

    /* compiled from: PageViewStorage.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
