package com.google.android.datatransport.cct.internal;

import android.util.JsonReader;
import android.util.JsonToken;
import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;
import java.io.IOException;
import java.io.Reader;

@AutoValue
public abstract class LogResponse {
    private static final String LOG_TAG = "LogResponseInternal";

    public abstract long getNextRequestWaitMillis();

    static LogResponse create(long nextRequestWaitMillis) {
        return new AutoValue_LogResponse(nextRequestWaitMillis);
    }

    @NonNull
    public static LogResponse fromJson(@NonNull Reader reader) {
        JsonReader jsonReader = new JsonReader(reader);
        try {
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                if (!jsonReader.nextName().equals("nextRequestWaitMillis")) {
                    jsonReader.skipValue();
                } else if (jsonReader.peek() == JsonToken.STRING) {
                    return create(Long.parseLong(jsonReader.nextString()));
                } else {
                    LogResponse create = create(jsonReader.nextLong());
                    jsonReader.close();
                    return create;
                }
            }
            throw new IOException("Response is missing nextRequestWaitMillis field.");
        } finally {
            jsonReader.close();
        }
    }
}
