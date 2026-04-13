package jakarta.activation;

import java.io.IOException;

public class UnsupportedDataTypeException extends IOException {
    private static final long serialVersionUID = -3584600599376858820L;

    public UnsupportedDataTypeException() {
    }

    public UnsupportedDataTypeException(String s) {
        super(s);
    }
}
