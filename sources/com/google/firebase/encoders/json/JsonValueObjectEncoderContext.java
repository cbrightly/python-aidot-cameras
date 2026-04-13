package com.google.firebase.encoders.json;

import android.util.Base64;
import android.util.JsonWriter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.ValueEncoder;
import com.google.firebase.encoders.ValueEncoderContext;
import java.io.Writer;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public final class JsonValueObjectEncoderContext implements ObjectEncoderContext, ValueEncoderContext {
    private boolean active = true;
    private JsonValueObjectEncoderContext childContext = null;
    private final ObjectEncoder<Object> fallbackEncoder;
    private final boolean ignoreNullValues;
    private final JsonWriter jsonWriter;
    private final Map<Class<?>, ObjectEncoder<?>> objectEncoders;
    private final Map<Class<?>, ValueEncoder<?>> valueEncoders;

    JsonValueObjectEncoderContext(@NonNull Writer writer, @NonNull Map<Class<?>, ObjectEncoder<?>> objectEncoders2, @NonNull Map<Class<?>, ValueEncoder<?>> valueEncoders2, ObjectEncoder<Object> fallbackEncoder2, boolean ignoreNullValues2) {
        this.jsonWriter = new JsonWriter(writer);
        this.objectEncoders = objectEncoders2;
        this.valueEncoders = valueEncoders2;
        this.fallbackEncoder = fallbackEncoder2;
        this.ignoreNullValues = ignoreNullValues2;
    }

    private JsonValueObjectEncoderContext(JsonValueObjectEncoderContext anotherContext) {
        this.jsonWriter = anotherContext.jsonWriter;
        this.objectEncoders = anotherContext.objectEncoders;
        this.valueEncoders = anotherContext.valueEncoders;
        this.fallbackEncoder = anotherContext.fallbackEncoder;
        this.ignoreNullValues = anotherContext.ignoreNullValues;
    }

    @NonNull
    public JsonValueObjectEncoderContext add(@NonNull String name, @Nullable Object o) {
        if (this.ignoreNullValues) {
            return internalAddIgnoreNullValues(name, o);
        }
        return internalAdd(name, o);
    }

    @NonNull
    public JsonValueObjectEncoderContext add(@NonNull String name, double value) {
        maybeUnNest();
        this.jsonWriter.name(name);
        return add(value);
    }

    @NonNull
    public JsonValueObjectEncoderContext add(@NonNull String name, int value) {
        maybeUnNest();
        this.jsonWriter.name(name);
        return add(value);
    }

    @NonNull
    public JsonValueObjectEncoderContext add(@NonNull String name, long value) {
        maybeUnNest();
        this.jsonWriter.name(name);
        return add(value);
    }

    @NonNull
    public JsonValueObjectEncoderContext add(@NonNull String name, boolean value) {
        maybeUnNest();
        this.jsonWriter.name(name);
        return add(value);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, @Nullable Object obj) {
        return add(field.getName(), obj);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, float value) {
        return add(field.getName(), (double) value);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, double value) {
        return add(field.getName(), value);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, int value) {
        return add(field.getName(), value);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, long value) {
        return add(field.getName(), value);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, boolean value) {
        return add(field.getName(), value);
    }

    @NonNull
    public ObjectEncoderContext inline(@Nullable Object value) {
        return add(value, true);
    }

    @NonNull
    public ObjectEncoderContext nested(@NonNull String name) {
        maybeUnNest();
        this.childContext = new JsonValueObjectEncoderContext(this);
        this.jsonWriter.name(name);
        this.jsonWriter.beginObject();
        return this.childContext;
    }

    @NonNull
    public ObjectEncoderContext nested(@NonNull FieldDescriptor field) {
        return nested(field.getName());
    }

    @NonNull
    public JsonValueObjectEncoderContext add(@Nullable String value) {
        maybeUnNest();
        this.jsonWriter.value(value);
        return this;
    }

    @NonNull
    public JsonValueObjectEncoderContext add(float value) {
        maybeUnNest();
        this.jsonWriter.value((double) value);
        return this;
    }

    @NonNull
    public JsonValueObjectEncoderContext add(double value) {
        maybeUnNest();
        this.jsonWriter.value(value);
        return this;
    }

    @NonNull
    public JsonValueObjectEncoderContext add(int value) {
        maybeUnNest();
        this.jsonWriter.value((long) value);
        return this;
    }

    @NonNull
    public JsonValueObjectEncoderContext add(long value) {
        maybeUnNest();
        this.jsonWriter.value(value);
        return this;
    }

    @NonNull
    public JsonValueObjectEncoderContext add(boolean value) {
        maybeUnNest();
        this.jsonWriter.value(value);
        return this;
    }

    @NonNull
    public JsonValueObjectEncoderContext add(@Nullable byte[] bytes) {
        maybeUnNest();
        if (bytes == null) {
            this.jsonWriter.nullValue();
        } else {
            this.jsonWriter.value(Base64.encodeToString(bytes, 2));
        }
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public JsonValueObjectEncoderContext add(@Nullable Object o, boolean inline) {
        int i = 0;
        if (inline && cannotBeInline(o)) {
            Object[] objArr = new Object[1];
            objArr[0] = o == null ? null : o.getClass();
            throw new EncodingException(String.format("%s cannot be encoded inline", objArr));
        } else if (o == null) {
            this.jsonWriter.nullValue();
            return this;
        } else if (o instanceof Number) {
            this.jsonWriter.value((Number) o);
            return this;
        } else if (o.getClass().isArray()) {
            if (o instanceof byte[]) {
                return add((byte[]) o);
            }
            this.jsonWriter.beginArray();
            if (o instanceof int[]) {
                int[] iArr = (int[]) o;
                int length = iArr.length;
                while (i < length) {
                    this.jsonWriter.value((long) iArr[i]);
                    i++;
                }
            } else if (o instanceof long[]) {
                long[] jArr = (long[]) o;
                int length2 = jArr.length;
                while (i < length2) {
                    add(jArr[i]);
                    i++;
                }
            } else if (o instanceof double[]) {
                double[] dArr = (double[]) o;
                int length3 = dArr.length;
                while (i < length3) {
                    this.jsonWriter.value(dArr[i]);
                    i++;
                }
            } else if (o instanceof boolean[]) {
                boolean[] zArr = (boolean[]) o;
                int length4 = zArr.length;
                while (i < length4) {
                    this.jsonWriter.value(zArr[i]);
                    i++;
                }
            } else if (o instanceof Number[]) {
                for (Number item : (Number[]) o) {
                    add((Object) item, false);
                }
            } else {
                for (Object item2 : (Object[]) o) {
                    add(item2, false);
                }
            }
            this.jsonWriter.endArray();
            return this;
        } else if (o instanceof Collection) {
            this.jsonWriter.beginArray();
            for (Object elem : (Collection) o) {
                add(elem, false);
            }
            this.jsonWriter.endArray();
            return this;
        } else if (o instanceof Map) {
            this.jsonWriter.beginObject();
            for (Map.Entry<Object, Object> entry : ((Map) o).entrySet()) {
                Object key = entry.getKey();
                try {
                    add((String) key, entry.getValue());
                } catch (ClassCastException ex) {
                    throw new EncodingException(String.format("Only String keys are currently supported in maps, got %s of type %s instead.", new Object[]{key, key.getClass()}), ex);
                }
            }
            this.jsonWriter.endObject();
            return this;
        } else {
            ObjectEncoder<Object> objectEncoder = this.objectEncoders.get(o.getClass());
            if (objectEncoder != null) {
                return doEncode(objectEncoder, o, inline);
            }
            ValueEncoder<Object> valueEncoder = this.valueEncoders.get(o.getClass());
            if (valueEncoder != null) {
                valueEncoder.encode(o, this);
                return this;
            } else if (!(o instanceof Enum)) {
                return doEncode(this.fallbackEncoder, o, inline);
            } else {
                add(((Enum) o).name());
                return this;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public JsonValueObjectEncoderContext doEncode(ObjectEncoder<Object> encoder, Object o, boolean inline) {
        if (!inline) {
            this.jsonWriter.beginObject();
        }
        encoder.encode(o, this);
        if (!inline) {
            this.jsonWriter.endObject();
        }
        return this;
    }

    private boolean cannotBeInline(Object value) {
        return value == null || value.getClass().isArray() || (value instanceof Collection) || (value instanceof Date) || (value instanceof Enum) || (value instanceof Number);
    }

    /* access modifiers changed from: package-private */
    public void close() {
        maybeUnNest();
        this.jsonWriter.flush();
    }

    private void maybeUnNest() {
        if (this.active) {
            JsonValueObjectEncoderContext jsonValueObjectEncoderContext = this.childContext;
            if (jsonValueObjectEncoderContext != null) {
                jsonValueObjectEncoderContext.maybeUnNest();
                this.childContext.active = false;
                this.childContext = null;
                this.jsonWriter.endObject();
                return;
            }
            return;
        }
        throw new IllegalStateException("Parent context used since this context was created. Cannot use this context anymore.");
    }

    private JsonValueObjectEncoderContext internalAdd(@NonNull String name, @Nullable Object o) {
        maybeUnNest();
        this.jsonWriter.name(name);
        if (o != null) {
            return add(o, false);
        }
        this.jsonWriter.nullValue();
        return this;
    }

    private JsonValueObjectEncoderContext internalAddIgnoreNullValues(@NonNull String name, @Nullable Object o) {
        if (o == null) {
            return this;
        }
        maybeUnNest();
        this.jsonWriter.name(name);
        return add(o, false);
    }
}
