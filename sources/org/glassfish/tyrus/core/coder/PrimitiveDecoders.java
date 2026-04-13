package org.glassfish.tyrus.core.coder;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.ReflectionHelper;

public abstract class PrimitiveDecoders<T> extends CoderAdapter implements Decoder.Text<T> {
    public static final List<Class<? extends Decoder>> ALL = Collections.unmodifiableList(Arrays.asList(new Class[]{BooleanDecoder.class, ByteDecoder.class, CharacterDecoder.class, DoubleDecoder.class, FloatDecoder.class, IntegerDecoder.class, LongDecoder.class, ShortDecoder.class}));
    public static final Map<Class<?>, Decoder.Text<?>> ALL_INSTANCES = getAllInstances();

    public abstract /* synthetic */ T decode(String str);

    public boolean willDecode(String s) {
        return true;
    }

    public static class BooleanDecoder extends PrimitiveDecoders<Boolean> {
        public Boolean decode(String s) {
            try {
                return Boolean.valueOf(s);
            } catch (Exception e) {
                throw new DecodeException(s, "Decoding failed", (Throwable) e);
            }
        }
    }

    public static class ByteDecoder extends PrimitiveDecoders<Byte> {
        public Byte decode(String s) {
            try {
                return Byte.valueOf(s);
            } catch (Exception e) {
                throw new DecodeException(s, "Decoding failed", (Throwable) e);
            }
        }
    }

    public static class CharacterDecoder extends PrimitiveDecoders<Character> {
        public Character decode(String s) {
            try {
                return Character.valueOf(s.charAt(0));
            } catch (Exception e) {
                throw new DecodeException(s, "Decoding failed", (Throwable) e);
            }
        }
    }

    public static class DoubleDecoder extends PrimitiveDecoders<Double> {
        public Double decode(String s) {
            try {
                return Double.valueOf(s);
            } catch (Exception e) {
                throw new DecodeException(s, "Decoding failed", (Throwable) e);
            }
        }
    }

    public static class FloatDecoder extends PrimitiveDecoders<Float> {
        public Float decode(String s) {
            try {
                return Float.valueOf(s);
            } catch (Exception e) {
                throw new DecodeException(s, "Decoding failed", (Throwable) e);
            }
        }
    }

    public static class IntegerDecoder extends PrimitiveDecoders<Integer> {
        public Integer decode(String s) {
            try {
                return Integer.valueOf(s);
            } catch (Exception e) {
                throw new DecodeException(s, "Decoding failed", (Throwable) e);
            }
        }
    }

    public static class LongDecoder extends PrimitiveDecoders<Long> {
        public Long decode(String s) {
            try {
                return Long.valueOf(s);
            } catch (Exception e) {
                throw new DecodeException(s, "Decoding failed", (Throwable) e);
            }
        }
    }

    public static class ShortDecoder extends PrimitiveDecoders<Short> {
        public Short decode(String s) {
            try {
                return Short.valueOf(s);
            } catch (Exception e) {
                throw new DecodeException(s, "Decoding failed", (Throwable) e);
            }
        }
    }

    private static Map<Class<?>, Decoder.Text<?>> getAllInstances() {
        Map<Class<?>, Decoder.Text<?>> map = new HashMap<>();
        for (Class<? extends Decoder> dec : ALL) {
            try {
                map.put(ReflectionHelper.getClassType(dec, Decoder.Text.class), (Decoder.Text) dec.newInstance());
            } catch (Exception e) {
                Logger.getLogger(PrimitiveDecoders.class.getName()).log(Level.WARNING, String.format("Decoder %s could not have been instantiated.", new Object[]{dec}));
            }
        }
        return map;
    }
}
