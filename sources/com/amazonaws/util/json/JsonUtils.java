package com.amazonaws.util.json;

import com.amazonaws.AmazonClientException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
    private static volatile AwsJsonFactory factory = new GsonFactory();

    public enum JsonEngine {
        Gson,
        Jackson
    }

    /* renamed from: com.amazonaws.util.json.JsonUtils$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$amazonaws$util$json$JsonUtils$JsonEngine;

        static {
            int[] iArr = new int[JsonEngine.values().length];
            $SwitchMap$com$amazonaws$util$json$JsonUtils$JsonEngine = iArr;
            try {
                iArr[JsonEngine.Gson.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$amazonaws$util$json$JsonUtils$JsonEngine[JsonEngine.Jackson.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static void setJsonEngine(JsonEngine jsonEngine) {
        switch (AnonymousClass1.$SwitchMap$com$amazonaws$util$json$JsonUtils$JsonEngine[jsonEngine.ordinal()]) {
            case 1:
                factory = new GsonFactory();
                return;
            case 2:
                factory = new JacksonFactory();
                return;
            default:
                throw new RuntimeException("Unsupported json engine");
        }
    }

    static void setJsonEngine(AwsJsonFactory factory2) {
        if (factory2 != null) {
            factory = factory2;
            return;
        }
        throw new IllegalArgumentException("factory can't be null");
    }

    public static AwsJsonReader getJsonReader(Reader in) {
        if (factory != null) {
            return factory.getJsonReader(in);
        }
        throw new IllegalStateException("Json engine is unavailable.");
    }

    public static AwsJsonWriter getJsonWriter(Writer out) {
        if (factory != null) {
            return factory.getJsonWriter(out);
        }
        throw new IllegalStateException("Json engine is unavailable.");
    }

    public static Map<String, String> jsonToMap(Reader in) {
        AwsJsonReader reader = getJsonReader(in);
        try {
            if (reader.peek() == null) {
                return Collections.EMPTY_MAP;
            }
            Map<String, String> map = new HashMap<>();
            reader.beginObject();
            while (reader.hasNext()) {
                String key = reader.nextName();
                if (reader.isContainer()) {
                    reader.skipValue();
                } else {
                    map.put(key, reader.nextString());
                }
            }
            reader.endObject();
            reader.close();
            return Collections.unmodifiableMap(map);
        } catch (IOException e) {
            throw new AmazonClientException("Unable to parse JSON String.", e);
        }
    }

    public static Map<String, String> jsonToMap(String json) {
        if (json == null || json.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        return jsonToMap((Reader) new StringReader(json));
    }

    public static String mapToString(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "{}";
        }
        try {
            StringWriter out = new StringWriter();
            AwsJsonWriter writer = getJsonWriter(out);
            writer.beginObject();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                writer.name(entry.getKey()).value(entry.getValue());
            }
            writer.endObject();
            writer.close();
            return out.toString();
        } catch (IOException e) {
            throw new AmazonClientException("Unable to serialize to JSON String.", e);
        }
    }

    private static boolean isClassAvailable(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
