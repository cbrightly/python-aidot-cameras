package org.glassfish.tyrus.core;

import jakarta.websocket.Decoder;
import jakarta.websocket.MessageHandler;
import jakarta.websocket.PongMessage;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.glassfish.tyrus.core.coder.CoderWrapper;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class MessageHandlerManager {
    private static final List<Class<?>> PARTIAL_BINARY_HANDLER_TYPES;
    private static final Class<?> PARTIAL_TEXT_HANDLER_TYPE;
    private static final Class<?> PONG_HANDLER_TYPE = PongMessage.class;
    private static final List<Class<?>> WHOLE_BINARY_HANDLER_TYPES;
    private static final List<Class<?>> WHOLE_TEXT_HANDLER_TYPES;
    private boolean binaryHandlerPresent;
    private boolean binaryWholeHandlerPresent;
    private final List<Class<? extends Decoder>> decoders;
    private boolean inputStreamHandlerPresent;
    private Set<MessageHandler> messageHandlerCache;
    private boolean pongHandlerPresent;
    private boolean readerHandlerPresent;
    private final Map<Class<?>, MessageHandler> registeredHandlers;
    private boolean textHandlerPresent;
    private boolean textWholeHandlerPresent;

    static {
        Class<byte[]> cls = byte[].class;
        Class<String> cls2 = String.class;
        WHOLE_TEXT_HANDLER_TYPES = Arrays.asList(new Class[]{cls2, Reader.class});
        PARTIAL_TEXT_HANDLER_TYPE = cls2;
        WHOLE_BINARY_HANDLER_TYPES = Arrays.asList(new Class[]{ByteBuffer.class, InputStream.class, cls});
        PARTIAL_BINARY_HANDLER_TYPES = Arrays.asList(new Class[]{ByteBuffer.class, cls});
    }

    public MessageHandlerManager() {
        this(Collections.emptyList());
    }

    MessageHandlerManager(List<Class<? extends Decoder>> decoders2) {
        this.registeredHandlers = new HashMap();
        this.decoders = decoders2;
    }

    static MessageHandlerManager fromDecoderInstances(List<Decoder> decoders2) {
        List<Class<? extends Decoder>> decoderList = new ArrayList<>();
        for (Decoder decoder : decoders2) {
            if (decoder instanceof CoderWrapper) {
                decoderList.add(((CoderWrapper) decoder).getCoderClass());
            } else {
                decoderList.add(decoder.getClass());
            }
        }
        return new MessageHandlerManager(decoderList);
    }

    public static MessageHandlerManager fromDecoderClasses(List<Class<? extends Decoder>> decoderClasses) {
        return new MessageHandlerManager(decoderClasses);
    }

    public void addMessageHandler(MessageHandler handler) {
        Class<?> handlerClass = getHandlerType(handler);
        if (handler instanceof MessageHandler.Whole) {
            addMessageHandler(handlerClass, (MessageHandler.Whole) handler);
        } else if (handler instanceof MessageHandler.Partial) {
            addMessageHandler(handlerClass, (MessageHandler.Partial) handler);
        } else {
            throwException(LocalizationMessages.MESSAGE_HANDLER_WHOLE_OR_PARTIAL());
        }
    }

    public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Whole<T> handler) {
        if (WHOLE_TEXT_HANDLER_TYPES.contains(clazz)) {
            if (this.textHandlerPresent) {
                throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_TEXT());
            } else {
                if (Reader.class.isAssignableFrom(clazz)) {
                    this.readerHandlerPresent = true;
                }
                this.textHandlerPresent = true;
                this.textWholeHandlerPresent = true;
            }
        } else if (WHOLE_BINARY_HANDLER_TYPES.contains(clazz)) {
            if (this.binaryHandlerPresent) {
                throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_BINARY());
            } else {
                if (InputStream.class.isAssignableFrom(clazz)) {
                    this.inputStreamHandlerPresent = true;
                }
                this.binaryHandlerPresent = true;
                this.binaryWholeHandlerPresent = true;
            }
        } else if (!PONG_HANDLER_TYPE.equals(clazz)) {
            boolean viable = false;
            if (checkTextDecoders(clazz)) {
                if (this.textHandlerPresent) {
                    throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_TEXT());
                } else {
                    this.textHandlerPresent = true;
                    this.textWholeHandlerPresent = true;
                    viable = true;
                }
            }
            if (checkBinaryDecoders(clazz)) {
                if (this.binaryHandlerPresent) {
                    throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_BINARY());
                } else {
                    this.binaryHandlerPresent = true;
                    this.binaryWholeHandlerPresent = true;
                    viable = true;
                }
            }
            if (!viable) {
                throwException(LocalizationMessages.MESSAGE_HANDLER_DECODER_NOT_REGISTERED(clazz));
            }
        } else if (this.pongHandlerPresent) {
            throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_PONG());
        } else {
            this.pongHandlerPresent = true;
        }
        registerMessageHandler(clazz, handler);
    }

    public <T> void addMessageHandler(Class<T> clazz, MessageHandler.Partial<T> handler) {
        boolean viable = false;
        if (PARTIAL_TEXT_HANDLER_TYPE.equals(clazz)) {
            if (this.textHandlerPresent) {
                throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_TEXT());
            } else {
                this.textHandlerPresent = true;
                viable = true;
            }
        }
        if (PARTIAL_BINARY_HANDLER_TYPES.contains(clazz)) {
            if (this.binaryHandlerPresent) {
                throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_BINARY());
            } else {
                this.binaryHandlerPresent = true;
                viable = true;
            }
        }
        if (!viable) {
            throwException(LocalizationMessages.MESSAGE_HANDLER_PARTIAL_INVALID_TYPE(clazz.getName()));
        }
        registerMessageHandler(clazz, handler);
    }

    private <T> void registerMessageHandler(Class<T> clazz, MessageHandler handler) {
        if (this.registeredHandlers.containsKey(clazz)) {
            throwException(LocalizationMessages.MESSAGE_HANDLER_ALREADY_REGISTERED_TYPE(clazz));
        } else {
            this.registeredHandlers.put(clazz, handler);
        }
        this.messageHandlerCache = null;
    }

    private void throwException(String text) {
        throw new IllegalStateException(text);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.Class} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void removeMessageHandler(jakarta.websocket.MessageHandler r5) {
        /*
            r4 = this;
            java.util.Map<java.lang.Class<?>, jakarta.websocket.MessageHandler> r0 = r4.registeredHandlers
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
            r1 = 0
        L_0x000b:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x0032
            java.lang.Object r2 = r0.next()
            java.util.Map$Entry r2 = (java.util.Map.Entry) r2
            java.lang.Object r3 = r2.getValue()
            jakarta.websocket.MessageHandler r3 = (jakarta.websocket.MessageHandler) r3
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0031
            java.lang.Object r3 = r2.getKey()
            r1 = r3
            java.lang.Class r1 = (java.lang.Class) r1
            r0.remove()
            r3 = 0
            r4.messageHandlerCache = r3
            goto L_0x0032
        L_0x0031:
            goto L_0x000b
        L_0x0032:
            if (r1 != 0) goto L_0x0035
            return
        L_0x0035:
            boolean r2 = r5 instanceof jakarta.websocket.MessageHandler.Whole
            r3 = 0
            if (r2 == 0) goto L_0x0075
            java.util.List<java.lang.Class<?>> r2 = WHOLE_TEXT_HANDLER_TYPES
            boolean r2 = r2.contains(r1)
            if (r2 == 0) goto L_0x0047
            r4.textHandlerPresent = r3
            r4.textWholeHandlerPresent = r3
            goto L_0x008a
        L_0x0047:
            java.util.List<java.lang.Class<?>> r2 = WHOLE_BINARY_HANDLER_TYPES
            boolean r2 = r2.contains(r1)
            if (r2 == 0) goto L_0x0054
            r4.binaryHandlerPresent = r3
            r4.binaryWholeHandlerPresent = r3
            goto L_0x008a
        L_0x0054:
            java.lang.Class<?> r2 = PONG_HANDLER_TYPE
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x005f
            r4.pongHandlerPresent = r3
            goto L_0x008a
        L_0x005f:
            boolean r2 = r4.checkTextDecoders(r1)
            if (r2 == 0) goto L_0x006a
            r4.textHandlerPresent = r3
            r4.textWholeHandlerPresent = r3
            goto L_0x008a
        L_0x006a:
            boolean r2 = r4.checkBinaryDecoders(r1)
            if (r2 == 0) goto L_0x008a
            r4.binaryHandlerPresent = r3
            r4.binaryWholeHandlerPresent = r3
            goto L_0x008a
        L_0x0075:
            java.lang.Class<?> r2 = PARTIAL_TEXT_HANDLER_TYPE
            boolean r2 = r2.equals(r1)
            if (r2 == 0) goto L_0x0080
            r4.textHandlerPresent = r3
            goto L_0x008a
        L_0x0080:
            java.util.List<java.lang.Class<?>> r2 = PARTIAL_BINARY_HANDLER_TYPES
            boolean r2 = r2.contains(r1)
            if (r2 == 0) goto L_0x008a
            r4.binaryHandlerPresent = r3
        L_0x008a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.core.MessageHandlerManager.removeMessageHandler(jakarta.websocket.MessageHandler):void");
    }

    /* access modifiers changed from: package-private */
    public Map<Class<?>, MessageHandler> getRegisteredHandlers() {
        return new HashMap(this.registeredHandlers);
    }

    public Set<MessageHandler> getMessageHandlers() {
        if (this.messageHandlerCache == null) {
            this.messageHandlerCache = Collections.unmodifiableSet(new HashSet(this.registeredHandlers.values()));
        }
        return this.messageHandlerCache;
    }

    public List<Map.Entry<Class<?>, MessageHandler>> getOrderedWholeMessageHandlers() {
        List<Map.Entry<Class<?>, MessageHandler>> result = new ArrayList<>();
        for (Map.Entry<Class<?>, MessageHandler> entry : this.registeredHandlers.entrySet()) {
            if (entry.getValue() instanceof MessageHandler.Whole) {
                result.add(entry);
            }
        }
        Collections.sort(result, new MessageHandlerComparator());
        return result;
    }

    private static Class<?> getHandlerType(MessageHandler handler) {
        Class cls;
        if (handler instanceof AsyncMessageHandler) {
            return ((AsyncMessageHandler) handler).getType();
        }
        if (handler instanceof BasicMessageHandler) {
            return ((BasicMessageHandler) handler).getType();
        }
        if (handler instanceof MessageHandler.Partial) {
            cls = MessageHandler.Partial.class;
        } else if (handler instanceof MessageHandler.Whole) {
            cls = MessageHandler.Whole.class;
        } else {
            throw new IllegalArgumentException(LocalizationMessages.MESSAGE_HANDLER_ILLEGAL_ARGUMENT(handler));
        }
        Class<?> result = ReflectionHelper.getClassType(handler.getClass(), cls);
        return result == null ? Object.class : result;
    }

    private boolean checkTextDecoders(Class<?> requiredType) {
        for (Class<? extends Decoder> decoderClass : this.decoders) {
            if (isTextDecoder(decoderClass) && requiredType.isAssignableFrom(AnnotatedEndpoint.getDecoderClassType(decoderClass))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBinaryDecoders(Class<?> requiredType) {
        for (Class<? extends Decoder> decoderClass : this.decoders) {
            if (isBinaryDecoder(decoderClass) && requiredType.isAssignableFrom(AnnotatedEndpoint.getDecoderClassType(decoderClass))) {
                return true;
            }
        }
        return false;
    }

    private boolean isTextDecoder(Class<? extends Decoder> decoderClass) {
        return Decoder.Text.class.isAssignableFrom(decoderClass) || Decoder.c.class.isAssignableFrom(decoderClass);
    }

    private boolean isBinaryDecoder(Class<? extends Decoder> decoderClass) {
        return Decoder.a.class.isAssignableFrom(decoderClass) || Decoder.b.class.isAssignableFrom(decoderClass);
    }

    /* access modifiers changed from: package-private */
    public boolean isWholeTextHandlerPresent() {
        return this.textWholeHandlerPresent;
    }

    /* access modifiers changed from: package-private */
    public boolean isWholeBinaryHandlerPresent() {
        return this.binaryWholeHandlerPresent;
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialTextHandlerPresent() {
        return this.textHandlerPresent && !this.textWholeHandlerPresent;
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialBinaryHandlerPresent() {
        return this.binaryHandlerPresent && !this.binaryWholeHandlerPresent;
    }

    public boolean isReaderHandlerPresent() {
        return this.readerHandlerPresent;
    }

    public boolean isInputStreamHandlerPresent() {
        return this.inputStreamHandlerPresent;
    }

    /* access modifiers changed from: package-private */
    public boolean isPongHandlerPresent() {
        return this.pongHandlerPresent;
    }

    public static class MessageHandlerComparator implements Comparator<Map.Entry<Class<?>, MessageHandler>>, Serializable {
        private static final long serialVersionUID = -5136634876439146784L;

        private MessageHandlerComparator() {
        }

        public int compare(Map.Entry<Class<?>, MessageHandler> o1, Map.Entry<Class<?>, MessageHandler> o2) {
            if (o1.getKey().isAssignableFrom(o2.getKey())) {
                return 1;
            }
            if (o2.getKey().isAssignableFrom(o1.getKey())) {
                return -1;
            }
            return 0;
        }
    }
}
