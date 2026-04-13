package com.amazonaws.util.json;

import com.amazonaws.util.BinaryUtils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.tencent.bugly.Bugly;
import java.io.EOFException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Date;

public final class GsonFactory implements AwsJsonFactory {
    GsonFactory() {
    }

    public AwsJsonReader getJsonReader(Reader in) {
        return new GsonReader(in);
    }

    public AwsJsonWriter getJsonWriter(Writer out) {
        return new GsonWriter(out);
    }

    public static final class GsonReader implements AwsJsonReader {
        private final JsonReader reader;

        public GsonReader(Reader in) {
            this.reader = new JsonReader(in);
        }

        public void beginArray() {
            this.reader.beginArray();
        }

        public void endArray() {
            this.reader.endArray();
        }

        public void beginObject() {
            this.reader.beginObject();
        }

        public void endObject() {
            this.reader.endObject();
        }

        public boolean isContainer() {
            JsonToken token = this.reader.peek();
            return JsonToken.BEGIN_ARRAY.equals(token) || JsonToken.BEGIN_OBJECT.equals(token);
        }

        public boolean hasNext() {
            return this.reader.hasNext();
        }

        public String nextName() {
            return this.reader.nextName();
        }

        public String nextString() {
            JsonToken token = this.reader.peek();
            if (JsonToken.NULL.equals(token)) {
                this.reader.nextNull();
                return null;
            } else if (JsonToken.BOOLEAN.equals(token)) {
                return this.reader.nextBoolean() ? "true" : Bugly.SDK_IS_DEV;
            } else {
                return this.reader.nextString();
            }
        }

        public void skipValue() {
            this.reader.skipValue();
        }

        public AwsJsonToken peek() {
            try {
                return GsonFactory.convert(this.reader.peek());
            } catch (EOFException e) {
                return null;
            }
        }

        public void close() {
            this.reader.close();
        }
    }

    /* access modifiers changed from: private */
    public static AwsJsonToken convert(JsonToken token) {
        if (token == null) {
            return null;
        }
        switch (AnonymousClass1.$SwitchMap$com$google$gson$stream$JsonToken[token.ordinal()]) {
            case 1:
                return AwsJsonToken.BEGIN_ARRAY;
            case 2:
                return AwsJsonToken.END_ARRAY;
            case 3:
                return AwsJsonToken.BEGIN_OBJECT;
            case 4:
                return AwsJsonToken.END_OBJECT;
            case 5:
                return AwsJsonToken.FIELD_NAME;
            case 6:
                return AwsJsonToken.VALUE_BOOLEAN;
            case 7:
                return AwsJsonToken.VALUE_NUMBER;
            case 8:
                return AwsJsonToken.VALUE_NULL;
            case 9:
                return AwsJsonToken.VALUE_STRING;
            case 10:
                return null;
            default:
                return AwsJsonToken.UNKNOWN;
        }
    }

    /* renamed from: com.amazonaws.util.json.GsonFactory$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$gson$stream$JsonToken;

        static {
            int[] iArr = new int[JsonToken.values().length];
            $SwitchMap$com$google$gson$stream$JsonToken = iArr;
            try {
                iArr[JsonToken.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.END_ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BEGIN_OBJECT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.END_OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NAME.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.BOOLEAN.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NUMBER.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.NULL.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.STRING.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$google$gson$stream$JsonToken[JsonToken.END_DOCUMENT.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    public static final class GsonWriter implements AwsJsonWriter {
        private static final int NEGATIVE_THREE = -3;
        private final JsonWriter writer;

        public GsonWriter(Writer out) {
            this.writer = new JsonWriter(out);
        }

        public AwsJsonWriter beginArray() {
            this.writer.beginArray();
            return this;
        }

        public AwsJsonWriter endArray() {
            this.writer.endArray();
            return this;
        }

        public AwsJsonWriter beginObject() {
            this.writer.beginObject();
            return this;
        }

        public AwsJsonWriter endObject() {
            this.writer.endObject();
            return this;
        }

        public AwsJsonWriter name(String name) {
            this.writer.name(name);
            return this;
        }

        public AwsJsonWriter value(String value) {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(boolean value) {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(double value) {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(long value) {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(Number value) {
            this.writer.value(value);
            return this;
        }

        public AwsJsonWriter value(Date value) {
            this.writer.value((Number) BigDecimal.valueOf(value.getTime()).scaleByPowerOfTen(-3));
            return this;
        }

        public AwsJsonWriter value(ByteBuffer value) {
            value.mark();
            byte[] bytes = new byte[value.remaining()];
            value.get(bytes, 0, bytes.length);
            value.reset();
            this.writer.value(BinaryUtils.toBase64(bytes));
            return this;
        }

        public AwsJsonWriter value() {
            this.writer.nullValue();
            return this;
        }

        public void flush() {
            this.writer.flush();
        }

        public void close() {
            this.writer.close();
        }
    }
}
