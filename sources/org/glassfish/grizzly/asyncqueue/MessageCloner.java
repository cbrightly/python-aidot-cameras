package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;

public interface MessageCloner<E> {
    E clone(Connection connection, E e);
}
