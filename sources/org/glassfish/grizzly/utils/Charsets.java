package org.glassfish.grizzly.utils;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.glassfish.grizzly.ThreadCache;

public final class Charsets {
    public static final Charset ASCII_CHARSET = lookupCharset("ASCII");
    private static final ThreadCache.CachedTypeIndex<CodecsCache> CODECS_CACHE = ThreadCache.obtainIndex(CodecsCache.class, 1);
    public static final int CODECS_CACHE_SIZE = 4;
    /* access modifiers changed from: private */
    public static final CharsetCodecResolver DECODER_RESOLVER = new DecoderResolver();
    public static final String DEFAULT_CHARACTER_ENCODING = Charset.defaultCharset().name();
    public static final Charset DEFAULT_CHARSET = Charset.defaultCharset();
    /* access modifiers changed from: private */
    public static final CharsetCodecResolver ENCODER_RESOLVER = new EncoderResolver();
    public static final Charset UTF8_CHARSET = lookupCharset("UTF-8");
    private static volatile boolean areCharsetsPreloaded;
    private static final ConcurrentMap<String, Charset> charsetAliasMap = new ConcurrentHashMap(8);

    public interface CharsetCodecResolver {
        Charset charset(Object obj);

        Object newElement(Charset charset);
    }

    static {
        if (Boolean.getBoolean(Charsets.class.getName() + ".preloadAllCharsets")) {
            preloadAllCharsets();
        }
    }

    public static Charset lookupCharset(String charsetName) {
        String charsetLowerCase = charsetName.toLowerCase(Locale.US);
        ConcurrentMap<String, Charset> concurrentMap = charsetAliasMap;
        Charset charset = (Charset) concurrentMap.get(charsetLowerCase);
        if (charset != null) {
            return charset;
        }
        if (!areCharsetsPreloaded) {
            Charset newCharset = Charset.forName(charsetLowerCase);
            Charset prevCharset = concurrentMap.putIfAbsent(charsetLowerCase, newCharset);
            return prevCharset == null ? newCharset : prevCharset;
        }
        throw new UnsupportedCharsetException(charsetName);
    }

    public static void preloadAllCharsets() {
        synchronized (charsetAliasMap) {
            for (Charset charset : Charset.availableCharsets().values()) {
                charsetAliasMap.put(charset.name().toLowerCase(Locale.US), charset);
                for (String alias : charset.aliases()) {
                    charsetAliasMap.put(alias.toLowerCase(Locale.US), charset);
                }
            }
            areCharsetsPreloaded = true;
        }
    }

    public static void drainAllCharsets() {
        ConcurrentMap<String, Charset> concurrentMap = charsetAliasMap;
        synchronized (concurrentMap) {
            areCharsetsPreloaded = false;
            concurrentMap.clear();
        }
    }

    public static CharsetDecoder getCharsetDecoder(Charset charset) {
        if (charset != null) {
            CharsetDecoder decoder = obtainCodecsCache().getDecoder(charset);
            decoder.reset();
            return decoder;
        }
        throw new IllegalArgumentException("Charset can not be null");
    }

    public static CharsetEncoder getCharsetEncoder(Charset charset) {
        if (charset != null) {
            CharsetEncoder encoder = obtainCodecsCache().getEncoder(charset);
            encoder.reset();
            return encoder;
        }
        throw new IllegalArgumentException("Charset can not be null");
    }

    private static CodecsCache obtainCodecsCache() {
        ThreadCache.CachedTypeIndex cachedTypeIndex = CODECS_CACHE;
        CodecsCache cache = (CodecsCache) ThreadCache.getFromCache(cachedTypeIndex);
        if (cache != null) {
            return cache;
        }
        CodecsCache cache2 = new CodecsCache();
        ThreadCache.putToCache(cachedTypeIndex, cache2);
        return cache2;
    }

    public static final class CodecsCache {
        private final Object[] decoders;
        private final Object[] encoders;

        private CodecsCache() {
            this.decoders = new Object[4];
            this.encoders = new Object[4];
        }

        public CharsetDecoder getDecoder(Charset charset) {
            return (CharsetDecoder) obtainElementByCharset(this.decoders, charset, Charsets.DECODER_RESOLVER);
        }

        public CharsetEncoder getEncoder(Charset charset) {
            return (CharsetEncoder) obtainElementByCharset(this.encoders, charset, Charsets.ENCODER_RESOLVER);
        }

        private static Object obtainElementByCharset(Object[] array, Charset charset, CharsetCodecResolver resolver) {
            int i = 0;
            while (true) {
                if (i >= array.length) {
                    break;
                }
                Object currentElement = array[i];
                if (currentElement == null) {
                    i++;
                    break;
                } else if (charset.equals(resolver.charset(currentElement))) {
                    makeFirst(array, i, currentElement);
                    return currentElement;
                } else {
                    i++;
                }
            }
            Object newElement = resolver.newElement(charset);
            makeFirst(array, i - 1, newElement);
            return newElement;
        }

        private static void makeFirst(Object[] array, int offs, Object element) {
            System.arraycopy(array, 0, array, 1, (offs - 1) + 1);
            array[0] = element;
        }
    }

    public static final class DecoderResolver implements CharsetCodecResolver {
        private DecoderResolver() {
        }

        public Charset charset(Object element) {
            return ((CharsetDecoder) element).charset();
        }

        public Object newElement(Charset charset) {
            return charset.newDecoder();
        }
    }

    public static final class EncoderResolver implements CharsetCodecResolver {
        private EncoderResolver() {
        }

        public Charset charset(Object element) {
            return ((CharsetEncoder) element).charset();
        }

        public Object newElement(Charset charset) {
            return charset.newEncoder();
        }
    }
}
