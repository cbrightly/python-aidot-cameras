package org.glassfish.grizzly.nio;

import java.nio.channels.SelectionKey;
import org.glassfish.grizzly.IOEvent;

public interface SelectionKeyHandler {
    public static final SelectionKeyHandler DEFAULT_SELECTION_KEY_HANDLER = SelectionKeyHandlerInitializer.initHandler();

    void cancel(SelectionKey selectionKey);

    NIOConnection getConnectionForKey(SelectionKey selectionKey);

    IOEvent[] getIOEvents(int i);

    int ioEvent2SelectionKeyInterest(IOEvent iOEvent);

    void onKeyDeregistered(SelectionKey selectionKey);

    void onKeyRegistered(SelectionKey selectionKey);

    boolean onProcessInterest(SelectionKey selectionKey, int i);

    IOEvent selectionKeyInterest2IoEvent(int i);

    void setConnectionForKey(NIOConnection nIOConnection, SelectionKey selectionKey);
}
