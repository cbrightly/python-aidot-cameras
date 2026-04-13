package org.glassfish.grizzly;

import java.util.concurrent.Future;

public interface SocketAcceptor {
    Future<Connection> accept();
}
