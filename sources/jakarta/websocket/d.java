package jakarta.websocket;

import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

/* compiled from: Encoder */
public interface d {

    /* compiled from: Encoder */
    public interface a<T> extends d {
        ByteBuffer encode(T t);
    }

    /* compiled from: Encoder */
    public interface b<T> extends d {
        void c(T t, OutputStream outputStream);
    }

    /* compiled from: Encoder */
    public interface c<T> extends d {
        String encode(T t);
    }

    /* renamed from: jakarta.websocket.d$d  reason: collision with other inner class name */
    /* compiled from: Encoder */
    public interface C0315d<T> extends d {
        void encode(T t, Writer writer);
    }

    void destroy();

    void init(EndpointConfig endpointConfig);
}
