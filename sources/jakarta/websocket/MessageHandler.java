package jakarta.websocket;

public interface MessageHandler {

    public interface Partial<T> extends MessageHandler {
        void onMessage(T t, boolean z);
    }

    public interface Whole<T> extends MessageHandler {
        void onMessage(T t);
    }
}
