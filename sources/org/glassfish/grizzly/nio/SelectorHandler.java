package org.glassfish.grizzly.nio;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.Set;
import org.glassfish.grizzly.CompletionHandler;

public interface SelectorHandler {
    public static final SelectorHandler DEFAULT_SELECTOR_HANDLER = new DefaultSelectorHandler();

    public interface Task {
        boolean run();
    }

    void deregisterChannel(SelectorRunner selectorRunner, SelectableChannel selectableChannel);

    void deregisterChannelAsync(SelectorRunner selectorRunner, SelectableChannel selectableChannel, CompletionHandler<RegisterChannelResult> completionHandler);

    void deregisterKeyInterest(SelectorRunner selectorRunner, SelectionKey selectionKey, int i);

    void enque(SelectorRunner selectorRunner, Task task, CompletionHandler<Task> completionHandler);

    void execute(SelectorRunner selectorRunner, Task task, CompletionHandler<Task> completionHandler);

    long getSelectTimeout();

    boolean onSelectorClosed(SelectorRunner selectorRunner);

    void postSelect(SelectorRunner selectorRunner);

    boolean preSelect(SelectorRunner selectorRunner);

    void registerChannel(SelectorRunner selectorRunner, SelectableChannel selectableChannel, int i, Object obj);

    void registerChannelAsync(SelectorRunner selectorRunner, SelectableChannel selectableChannel, int i, Object obj, CompletionHandler<RegisterChannelResult> completionHandler);

    void registerKeyInterest(SelectorRunner selectorRunner, SelectionKey selectionKey, int i);

    Set<SelectionKey> select(SelectorRunner selectorRunner);
}
