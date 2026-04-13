package jakarta.websocket;

import java.util.List;

public interface Extension {

    public interface Parameter {
        String getName();

        String getValue();
    }

    String getName();

    List<Parameter> getParameters();
}
