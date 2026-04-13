package org.glassfish.grizzly;

import java.util.concurrent.Future;

public interface ConnectorHandler<E> {
    Future<Connection> connect(E e);

    Future<Connection> connect(E e, E e2);

    void connect(E e, E e2, CompletionHandler<Connection> completionHandler);

    void connect(E e, CompletionHandler<Connection> completionHandler);
}
