package com.google.firebase.encoders.proto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.okgo.cache.CacheEntity;
import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.FieldDescriptor;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.ValueEncoder;
import com.google.firebase.encoders.proto.Protobuf;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;

public final class ProtobufDataEncoderContext implements ObjectEncoderContext {
    private static final ObjectEncoder<Map.Entry<Object, Object>> DEFAULT_MAP_ENCODER = a.a;
    private static final FieldDescriptor MAP_KEY_DESC = FieldDescriptor.builder(CacheEntity.KEY).withProperty(AtProtobuf.builder().tag(1).build()).build();
    private static final FieldDescriptor MAP_VALUE_DESC = FieldDescriptor.builder("value").withProperty(AtProtobuf.builder().tag(2).build()).build();
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final ObjectEncoder<Object> fallbackEncoder;
    private final Map<Class<?>, ObjectEncoder<?>> objectEncoders;
    private OutputStream output;
    private final ProtobufValueEncoderContext valueEncoderContext = new ProtobufValueEncoderContext(this);
    private final Map<Class<?>, ValueEncoder<?>> valueEncoders;

    static /* synthetic */ void lambda$static$0(Map.Entry o, ObjectEncoderContext ctx) {
        ctx.add(MAP_KEY_DESC, o.getKey());
        ctx.add(MAP_VALUE_DESC, o.getValue());
    }

    ProtobufDataEncoderContext(OutputStream output2, Map<Class<?>, ObjectEncoder<?>> objectEncoders2, Map<Class<?>, ValueEncoder<?>> valueEncoders2, ObjectEncoder<Object> fallbackEncoder2) {
        this.output = output2;
        this.objectEncoders = objectEncoders2;
        this.valueEncoders = valueEncoders2;
        this.fallbackEncoder = fallbackEncoder2;
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull String name, @Nullable Object obj) {
        return add(FieldDescriptor.of(name), obj);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull String name, double value) {
        return add(FieldDescriptor.of(name), value);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull String name, int value) {
        return add(FieldDescriptor.of(name), value);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull String name, long value) {
        return add(FieldDescriptor.of(name), value);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull String name, boolean value) {
        return add(FieldDescriptor.of(name), value);
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, @Nullable Object obj) {
        return add(field, obj, true);
    }

    /* access modifiers changed from: package-private */
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, @Nullable Object obj, boolean skipDefault) {
        if (obj == null) {
            return this;
        }
        if (obj instanceof CharSequence) {
            CharSequence seq = (CharSequence) obj;
            if (skipDefault && seq.length() == 0) {
                return this;
            }
            writeVarInt32((getTag(field) << 3) | 2);
            byte[] bytes = seq.toString().getBytes(UTF_8);
            writeVarInt32(bytes.length);
            this.output.write(bytes);
            return this;
        } else if (obj instanceof Collection) {
            for (Object value : (Collection) obj) {
                add(field, value, false);
            }
            return this;
        } else if (obj instanceof Map) {
            for (Map.Entry<Object, Object> entry : ((Map) obj).entrySet()) {
                doEncode(DEFAULT_MAP_ENCODER, field, entry, false);
            }
            return this;
        } else if (obj instanceof Double) {
            return add(field, ((Double) obj).doubleValue(), skipDefault);
        } else {
            if (obj instanceof Float) {
                return add(field, ((Float) obj).floatValue(), skipDefault);
            }
            if (obj instanceof Number) {
                return add(field, ((Number) obj).longValue(), skipDefault);
            }
            if (obj instanceof Boolean) {
                return add(field, ((Boolean) obj).booleanValue(), skipDefault);
            }
            if (obj instanceof byte[]) {
                byte[] bytes2 = (byte[]) obj;
                if (skipDefault && bytes2.length == 0) {
                    return this;
                }
                writeVarInt32((getTag(field) << 3) | 2);
                writeVarInt32(bytes2.length);
                this.output.write(bytes2);
                return this;
            }
            ObjectEncoder<Object> objectEncoder = this.objectEncoders.get(obj.getClass());
            if (objectEncoder != null) {
                return doEncode(objectEncoder, field, obj, skipDefault);
            }
            ValueEncoder<Object> valueEncoder = this.valueEncoders.get(obj.getClass());
            if (valueEncoder != null) {
                return doEncode(valueEncoder, field, obj, skipDefault);
            }
            if (obj instanceof ProtoEnum) {
                return add(field, ((ProtoEnum) obj).getNumber());
            }
            if (obj instanceof Enum) {
                return add(field, ((Enum) obj).ordinal());
            }
            return doEncode(this.fallbackEncoder, field, obj, skipDefault);
        }
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, double value) {
        return add(field, value, true);
    }

    /* access modifiers changed from: package-private */
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, double value, boolean skipDefault) {
        if (skipDefault && value == 0.0d) {
            return this;
        }
        writeVarInt32((getTag(field) << 3) | 1);
        this.output.write(allocateBuffer(8).putDouble(value).array());
        return this;
    }

    @NonNull
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, float value) {
        return add(field, value, true);
    }

    /* access modifiers changed from: package-private */
    public ObjectEncoderContext add(@NonNull FieldDescriptor field, float value, boolean skipDefault) {
        if (skipDefault && value == 0.0f) {
            return this;
        }
        writeVarInt32((getTag(field) << 3) | 5);
        this.output.write(allocateBuffer(4).putFloat(value).array());
        return this;
    }

    @NonNull
    public ProtobufDataEncoderContext add(@NonNull FieldDescriptor field, int value) {
        return add(field, value, true);
    }

    /* access modifiers changed from: package-private */
    public ProtobufDataEncoderContext add(@NonNull FieldDescriptor field, int value, boolean skipDefault) {
        if (skipDefault && value == 0) {
            return this;
        }
        Protobuf protobuf = getProtobuf(field);
        switch (AnonymousClass1.$SwitchMap$com$google$firebase$encoders$proto$Protobuf$IntEncoding[protobuf.intEncoding().ordinal()]) {
            case 1:
                writeVarInt32(protobuf.tag() << 3);
                writeVarInt32(value);
                break;
            case 2:
                writeVarInt32(protobuf.tag() << 3);
                writeVarInt32((value << 1) ^ (value >> 31));
                break;
            case 3:
                writeVarInt32((protobuf.tag() << 3) | 5);
                this.output.write(allocateBuffer(4).putInt(value).array());
                break;
        }
        return this;
    }

    /* renamed from: com.google.firebase.encoders.proto.ProtobufDataEncoderContext$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$encoders$proto$Protobuf$IntEncoding;

        static {
            int[] iArr = new int[Protobuf.IntEncoding.values().length];
            $SwitchMap$com$google$firebase$encoders$proto$Protobuf$IntEncoding = iArr;
            try {
                iArr[Protobuf.IntEncoding.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$firebase$encoders$proto$Protobuf$IntEncoding[Protobuf.IntEncoding.SIGNED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$firebase$encoders$proto$Protobuf$IntEncoding[Protobuf.IntEncoding.FIXED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @NonNull
    public ProtobufDataEncoderContext add(@NonNull FieldDescriptor field, long value) {
        return add(field, value, true);
    }

    /* access modifiers changed from: package-private */
    public ProtobufDataEncoderContext add(@NonNull FieldDescriptor field, long value, boolean skipDefault) {
        if (skipDefault && value == 0) {
            return this;
        }
        Protobuf protobuf = getProtobuf(field);
        switch (AnonymousClass1.$SwitchMap$com$google$firebase$encoders$proto$Protobuf$IntEncoding[protobuf.intEncoding().ordinal()]) {
            case 1:
                writeVarInt32(protobuf.tag() << 3);
                writeVarInt64(value);
                break;
            case 2:
                writeVarInt32(protobuf.tag() << 3);
                writeVarInt64((value << 1) ^ (value >> 63));
                break;
            case 3:
                writeVarInt32((protobuf.tag() << 3) | 1);
                this.output.write(allocateBuffer(8).putLong(value).array());
                break;
        }
        return this;
    }

    @NonNull
    public ProtobufDataEncoderContext add(@NonNull FieldDescriptor field, boolean value) {
        return add(field, value, true);
    }

    /* access modifiers changed from: package-private */
    public ProtobufDataEncoderContext add(@NonNull FieldDescriptor field, boolean value, boolean skipDefault) {
        return add(field, (int) value, skipDefault);
    }

    @NonNull
    public ObjectEncoderContext inline(@Nullable Object value) {
        return encode(value);
    }

    /* access modifiers changed from: package-private */
    public ProtobufDataEncoderContext encode(@Nullable Object value) {
        if (value == null) {
            return this;
        }
        ObjectEncoder<Object> objectEncoder = this.objectEncoders.get(value.getClass());
        if (objectEncoder != null) {
            objectEncoder.encode(value, this);
            return this;
        }
        throw new EncodingException("No encoder for " + value.getClass());
    }

    @NonNull
    public ObjectEncoderContext nested(@NonNull String name) {
        return nested(FieldDescriptor.of(name));
    }

    @NonNull
    public ObjectEncoderContext nested(@NonNull FieldDescriptor field) {
        throw new EncodingException("nested() is not implemented for protobuf encoding.");
    }

    private <T> ProtobufDataEncoderContext doEncode(ObjectEncoder<T> encoder, FieldDescriptor field, T obj, boolean skipDefault) {
        long size = determineSize(encoder, obj);
        if (skipDefault && size == 0) {
            return this;
        }
        writeVarInt32((getTag(field) << 3) | 2);
        writeVarInt64(size);
        encoder.encode(obj, this);
        return this;
    }

    private <T> long determineSize(ObjectEncoder<T> encoder, T obj) {
        OutputStream originalStream;
        LengthCountingOutputStream out = new LengthCountingOutputStream();
        try {
            originalStream = this.output;
            this.output = out;
            encoder.encode(obj, this);
            this.output = originalStream;
            long length = out.getLength();
            out.close();
            return length;
        } catch (Throwable th) {
            try {
                out.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private <T> ProtobufDataEncoderContext doEncode(ValueEncoder<T> encoder, FieldDescriptor field, T obj, boolean skipDefault) {
        this.valueEncoderContext.resetContext(field, skipDefault);
        encoder.encode(obj, this.valueEncoderContext);
        return this;
    }

    private static ByteBuffer allocateBuffer(int length) {
        return ByteBuffer.allocate(length).order(ByteOrder.LITTLE_ENDIAN);
    }

    private static int getTag(FieldDescriptor field) {
        Protobuf protobuf = (Protobuf) field.getProperty(Protobuf.class);
        if (protobuf != null) {
            return protobuf.tag();
        }
        throw new EncodingException("Field has no @Protobuf config");
    }

    private static Protobuf getProtobuf(FieldDescriptor field) {
        Protobuf protobuf = (Protobuf) field.getProperty(Protobuf.class);
        if (protobuf != null) {
            return protobuf;
        }
        throw new EncodingException("Field has no @Protobuf config");
    }

    private void writeVarInt32(int value) {
        while (((long) (value & -128)) != 0) {
            this.output.write((value & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
            value >>>= 7;
        }
        this.output.write(value & NeedPermissionEvent.PER_IPC_SPEAK_PERM);
    }

    private void writeVarInt64(long value) {
        while ((-128 & value) != 0) {
            this.output.write((((int) value) & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
            value >>>= 7;
        }
        this.output.write(((int) value) & NeedPermissionEvent.PER_IPC_SPEAK_PERM);
    }
}
