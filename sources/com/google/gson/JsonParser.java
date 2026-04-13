package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    public static JsonElement parseString(String json) {
        return parseReader((Reader) new StringReader(json));
    }

    public static JsonElement parseReader(Reader reader) {
        try {
            JsonReader jsonReader = new JsonReader(reader);
            JsonElement element = parseReader(jsonReader);
            if (!element.isJsonNull()) {
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonSyntaxException("Did not consume the entire document.");
                }
            }
            return element;
        } catch (MalformedJsonException e) {
            throw new JsonSyntaxException((Throwable) e);
        } catch (IOException e2) {
            throw new JsonIOException((Throwable) e2);
        } catch (NumberFormatException e3) {
            throw new JsonSyntaxException((Throwable) e3);
        }
    }

    public static JsonElement parseReader(JsonReader reader) {
        boolean lenient = reader.isLenient();
        reader.setLenient(true);
        try {
            JsonElement parse = Streams.parse(reader);
            reader.setLenient(lenient);
            return parse;
        } catch (StackOverflowError e) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
        } catch (OutOfMemoryError e2) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e2);
        } catch (Throwable th) {
            reader.setLenient(lenient);
            throw th;
        }
    }

    @Deprecated
    public JsonElement parse(String json) {
        return parseString(json);
    }

    @Deprecated
    public JsonElement parse(Reader json) {
        return parseReader(json);
    }

    @Deprecated
    public JsonElement parse(JsonReader json) {
        return parseReader(json);
    }
}
