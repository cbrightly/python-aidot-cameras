package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;

@Deprecated
public interface PushBackHandler {
    void onAccept(Connection connection, WritableMessage writableMessage);

    void onPushBack(Connection connection, WritableMessage writableMessage, PushBackContext pushBackContext);
}
