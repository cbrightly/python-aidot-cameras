package jakarta.websocket;

import java.nio.ByteBuffer;

public interface RemoteEndpoint {

    public interface Async extends RemoteEndpoint {
    }

    public interface Basic extends RemoteEndpoint {
        void sendBinary(ByteBuffer byteBuffer);

        void sendBinary(ByteBuffer byteBuffer, boolean z);

        void sendObject(Object obj);

        void sendText(String str);

        void sendText(String str, boolean z);
    }

    void sendPing(ByteBuffer byteBuffer);

    void sendPong(ByteBuffer byteBuffer);
}
