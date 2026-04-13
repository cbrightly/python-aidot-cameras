package org.glassfish.tyrus.core;

import jakarta.websocket.Extension;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.frame.Frame;

public class TyrusServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
    private final ComponentProviderService componentProviderService = ComponentProviderService.create();

    public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
        if (requested == null) {
            return "";
        }
        for (String clientProtocol : requested) {
            if (supported.contains(clientProtocol)) {
                return clientProtocol;
            }
        }
        return "";
    }

    public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
        List<Extension> installed2 = new ArrayList<>(installed);
        List<Extension> result = new ArrayList<>();
        if (requested != null) {
            for (final Extension requestedExtension : requested) {
                for (Extension extension : installed2) {
                    final String name = extension.getName();
                    if (name != null && name.equals(requestedExtension.getName())) {
                        boolean alreadyAdded = false;
                        for (Extension e : result) {
                            if (e.getName().equals(name)) {
                                alreadyAdded = true;
                            }
                        }
                        if (!alreadyAdded) {
                            if (extension instanceof ExtendedExtension) {
                                final ExtendedExtension extendedExtension = (ExtendedExtension) extension;
                                result.add(new ExtendedExtension() {
                                    public Frame processIncoming(ExtendedExtension.ExtensionContext context, Frame frame) {
                                        return extendedExtension.processIncoming(context, frame);
                                    }

                                    public Frame processOutgoing(ExtendedExtension.ExtensionContext context, Frame frame) {
                                        return extendedExtension.processOutgoing(context, frame);
                                    }

                                    public List<Extension.Parameter> onExtensionNegotiation(ExtendedExtension.ExtensionContext context, List<Extension.Parameter> list) {
                                        return extendedExtension.onExtensionNegotiation(context, requestedExtension.getParameters());
                                    }

                                    public void onHandshakeResponse(ExtendedExtension.ExtensionContext context, List<Extension.Parameter> responseParameters) {
                                        extendedExtension.onHandshakeResponse(context, responseParameters);
                                    }

                                    public void destroy(ExtendedExtension.ExtensionContext context) {
                                        extendedExtension.destroy(context);
                                    }

                                    public String getName() {
                                        return name;
                                    }

                                    public List<Extension.Parameter> getParameters() {
                                        return extendedExtension.getParameters();
                                    }
                                });
                            } else {
                                result.add(requestedExtension);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public boolean checkOrigin(String originHeaderValue) {
        return true;
    }

    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
    }

    public <T> T getEndpointInstance(Class<T> endpointClass) {
        return this.componentProviderService.getEndpointInstance(endpointClass);
    }
}
