package kotlin.reflect.jvm.internal.impl.protobuf;

import java.io.IOException;
import java.io.InputStream;
import kotlin.reflect.jvm.internal.impl.protobuf.a;
import kotlin.reflect.jvm.internal.impl.protobuf.o;

/* compiled from: AbstractParser */
public abstract class b<MessageType extends o> implements q<MessageType> {
    private static final f a = f.c();

    private UninitializedMessageException f(MessageType message) {
        if (message instanceof a) {
            return ((a) message).newUninitializedMessageException();
        }
        return new UninitializedMessageException(message);
    }

    private MessageType e(MessageType message) {
        if (message == null || message.isInitialized()) {
            return message;
        }
        throw f(message).asInvalidProtocolBufferException().setUnfinishedMessage(message);
    }

    public MessageType l(d data, f extensionRegistry) {
        try {
            e input = data.q();
            MessageType message = (o) c(input, extensionRegistry);
            try {
                input.a(0);
                return message;
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(message);
            } catch (InvalidProtocolBufferException e2) {
                e = e2;
                throw e;
            }
        } catch (InvalidProtocolBufferException e3) {
            e = e3;
            throw e;
        }
    }

    /* renamed from: i */
    public MessageType b(d data, f extensionRegistry) {
        return e(l(data, extensionRegistry));
    }

    public MessageType k(InputStream input, f extensionRegistry) {
        e codedInput = e.g(input);
        MessageType message = (o) c(codedInput, extensionRegistry);
        try {
            codedInput.a(0);
            return message;
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(message);
        }
    }

    /* renamed from: h */
    public MessageType a(InputStream input, f extensionRegistry) {
        return e(k(input, extensionRegistry));
    }

    public MessageType j(InputStream input, f extensionRegistry) {
        try {
            int firstByte = input.read();
            if (firstByte == -1) {
                return null;
            }
            return k(new a.C0398a.C0399a(input, e.B(firstByte, input)), extensionRegistry);
        } catch (IOException e) {
            throw new InvalidProtocolBufferException(e.getMessage());
        }
    }

    /* renamed from: g */
    public MessageType d(InputStream input, f extensionRegistry) {
        return e(j(input, extensionRegistry));
    }
}
