package jakarta.websocket;

import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;

public interface Decoder {

    public interface Text<T> extends Decoder {
        T decode(String str);

        boolean willDecode(String str);
    }

    public interface a<T> extends Decoder {
        T decode(ByteBuffer byteBuffer);

        boolean willDecode(ByteBuffer byteBuffer);
    }

    public interface b<T> extends Decoder {
        T b(InputStream inputStream);
    }

    public interface c<T> extends Decoder {
        T a(Reader reader);
    }

    void destroy();

    void init(EndpointConfig endpointConfig);
}
