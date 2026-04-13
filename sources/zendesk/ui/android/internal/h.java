package zendesk.ui.android.internal;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import org.jetbrains.annotations.Nullable;

/* compiled from: ThrottledAfterTextChanged.kt */
public abstract class h implements TextWatcher {
    private final long c;
    @Nullable
    private Long d;

    public abstract void a(@Nullable Editable editable);

    public h(long minimumIntervalMillis) {
        this.c = minimumIntervalMillis;
    }

    public void beforeTextChanged(@Nullable CharSequence p0, int p1, int p2, int p3) {
    }

    public void onTextChanged(@Nullable CharSequence p0, int p1, int p2, int p3) {
    }

    public void afterTextChanged(@Nullable Editable text) {
        Long previousTextChangedTimeStamp = this.d;
        long currentTimestamp = SystemClock.uptimeMillis();
        this.d = Long.valueOf(currentTimestamp);
        if (previousTextChangedTimeStamp == null || Math.abs(currentTimestamp - previousTextChangedTimeStamp.longValue()) > this.c) {
            a(text);
        }
    }
}
