package org.glassfish.grizzly;

import java.net.SocketAddress;

public interface SocketBinder {
    Connection<?> bind(int i);

    Connection<?> bind(String str, int i);

    Connection<?> bind(String str, int i, int i2);

    Connection<?> bind(String str, PortRange portRange, int i);

    Connection<?> bind(String str, PortRange portRange, boolean z, int i);

    Connection<?> bind(SocketAddress socketAddress);

    Connection<?> bind(SocketAddress socketAddress, int i);

    Connection<?> bindToInherited();

    void unbind(Connection<?> connection);

    void unbindAll();
}
