package androidx.core.net;

import androidx.annotation.NonNull;

public class ParseException extends RuntimeException {
    @NonNull
    public final String response;

    ParseException(@NonNull String response2) {
        super(response2);
        this.response = response2;
    }
}
