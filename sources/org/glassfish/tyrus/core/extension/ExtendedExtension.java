package org.glassfish.tyrus.core.extension;

import jakarta.websocket.Extension;
import java.util.List;
import java.util.Map;
import org.glassfish.tyrus.core.frame.Frame;

public interface ExtendedExtension extends Extension {

    public interface ExtensionContext {
        Map<String, Object> getProperties();
    }

    void destroy(ExtensionContext extensionContext);

    /* synthetic */ String getName();

    /* synthetic */ List<Extension.Parameter> getParameters();

    List<Extension.Parameter> onExtensionNegotiation(ExtensionContext extensionContext, List<Extension.Parameter> list);

    void onHandshakeResponse(ExtensionContext extensionContext, List<Extension.Parameter> list);

    Frame processIncoming(ExtensionContext extensionContext, Frame frame);

    Frame processOutgoing(ExtensionContext extensionContext, Frame frame);
}
