package jakarta.websocket;

import java.nio.ByteBuffer;

public class DecodeException extends Exception {
    private static final long serialVersionUID = 6;
    private final ByteBuffer bb;
    private final String encodedString;

    public DecodeException(ByteBuffer bb2, String message, Throwable cause) {
        super(message, cause);
        this.encodedString = null;
        this.bb = bb2;
    }

    public DecodeException(String encodedString2, String message, Throwable cause) {
        super(message, cause);
        this.encodedString = encodedString2;
        this.bb = null;
    }

    public DecodeException(ByteBuffer bb2, String message) {
        super(message);
        this.encodedString = null;
        this.bb = bb2;
    }

    public DecodeException(String encodedString2, String message) {
        super(message);
        this.encodedString = encodedString2;
        this.bb = null;
    }

    public ByteBuffer getBytes() {
        return this.bb;
    }

    public String getText() {
        return this.encodedString;
    }
}
