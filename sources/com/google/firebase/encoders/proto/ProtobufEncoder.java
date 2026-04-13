package com.google.firebase.encoders.proto;

import androidx.annotation.NonNull;
import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.ObjectEncoder;
import com.google.firebase.encoders.ObjectEncoderContext;
import com.google.firebase.encoders.ValueEncoder;
import com.google.firebase.encoders.config.Configurator;
import com.google.firebase.encoders.config.EncoderConfig;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ProtobufEncoder {
    private final ObjectEncoder<Object> fallbackEncoder;
    private final Map<Class<?>, ObjectEncoder<?>> objectEncoders;
    private final Map<Class<?>, ValueEncoder<?>> valueEncoders;

    ProtobufEncoder(Map<Class<?>, ObjectEncoder<?>> objectEncoders2, Map<Class<?>, ValueEncoder<?>> valueEncoders2, ObjectEncoder<Object> fallbackEncoder2) {
        this.objectEncoders = objectEncoders2;
        this.valueEncoders = valueEncoders2;
        this.fallbackEncoder = fallbackEncoder2;
    }

    public void encode(@NonNull Object value, @NonNull OutputStream outputStream) {
        new ProtobufDataEncoderContext(outputStream, this.objectEncoders, this.valueEncoders, this.fallbackEncoder).encode(value);
    }

    @NonNull
    public byte[] encode(@NonNull Object value) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            encode(value, output);
        } catch (IOException e) {
        }
        return output.toByteArray();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements EncoderConfig<Builder> {
        private static final ObjectEncoder<Object> DEFAULT_FALLBACK_ENCODER = b.a;
        private ObjectEncoder<Object> fallbackEncoder = DEFAULT_FALLBACK_ENCODER;
        private final Map<Class<?>, ObjectEncoder<?>> objectEncoders = new HashMap();
        private final Map<Class<?>, ValueEncoder<?>> valueEncoders = new HashMap();

        static /* synthetic */ void lambda$static$0(Object o, ObjectEncoderContext ctx) {
            throw new EncodingException("Couldn't find encoder for type " + o.getClass().getCanonicalName());
        }

        @NonNull
        public <U> Builder registerEncoder(@NonNull Class<U> type, @NonNull ObjectEncoder<? super U> encoder) {
            this.objectEncoders.put(type, encoder);
            this.valueEncoders.remove(type);
            return this;
        }

        @NonNull
        public <U> Builder registerEncoder(@NonNull Class<U> type, @NonNull ValueEncoder<? super U> encoder) {
            this.valueEncoders.put(type, encoder);
            this.objectEncoders.remove(type);
            return this;
        }

        @NonNull
        public Builder registerFallbackEncoder(@NonNull ObjectEncoder<Object> fallbackEncoder2) {
            this.fallbackEncoder = fallbackEncoder2;
            return this;
        }

        @NonNull
        public Builder configureWith(@NonNull Configurator config) {
            config.configure(this);
            return this;
        }

        public ProtobufEncoder build() {
            return new ProtobufEncoder(new HashMap(this.objectEncoders), new HashMap(this.valueEncoders), this.fallbackEncoder);
        }
    }
}
