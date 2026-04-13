package org.glassfish.grizzly.nio;

import java.nio.channels.SelectableChannel;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;

public interface NIOChannelDistributor {
    void registerChannel(SelectableChannel selectableChannel);

    void registerChannel(SelectableChannel selectableChannel, int i);

    void registerChannel(SelectableChannel selectableChannel, int i, Object obj);

    GrizzlyFuture<RegisterChannelResult> registerChannelAsync(SelectableChannel selectableChannel);

    GrizzlyFuture<RegisterChannelResult> registerChannelAsync(SelectableChannel selectableChannel, int i);

    GrizzlyFuture<RegisterChannelResult> registerChannelAsync(SelectableChannel selectableChannel, int i, Object obj);

    void registerChannelAsync(SelectableChannel selectableChannel, int i, Object obj, CompletionHandler<RegisterChannelResult> completionHandler);

    void registerServiceChannelAsync(SelectableChannel selectableChannel, int i, Object obj, CompletionHandler<RegisterChannelResult> completionHandler);
}
