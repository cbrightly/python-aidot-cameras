package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.nio.SelectorHandler;
import org.glassfish.grizzly.utils.Futures;

public class DefaultSelectorHandler implements SelectorHandler {
    private static final long DEFAULT_SELECT_TIMEOUT_MILLIS = 30000;
    public static final boolean IS_WORKAROUND_SELECTOR_SPIN;
    private static final int SPIN_RATE_THRESHOLD = 2000;
    /* access modifiers changed from: private */
    public static final Logger logger;
    protected final long selectTimeout;

    static {
        Class<DefaultSelectorHandler> cls = DefaultSelectorHandler.class;
        logger = Grizzly.logger(cls);
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getName());
        sb.append(".force-selector-spin-detection");
        IS_WORKAROUND_SELECTOR_SPIN = Boolean.getBoolean(sb.toString()) || System.getProperty("os.name").equalsIgnoreCase("linux");
    }

    public DefaultSelectorHandler() {
        this(30000, TimeUnit.MILLISECONDS);
    }

    public DefaultSelectorHandler(long selectTimeout2, TimeUnit timeunit) {
        this.selectTimeout = TimeUnit.MILLISECONDS.convert(selectTimeout2, timeunit);
    }

    public long getSelectTimeout() {
        return this.selectTimeout;
    }

    public boolean preSelect(SelectorRunner selectorRunner) {
        return processPendingTasks(selectorRunner);
    }

    public Set<SelectionKey> select(SelectorRunner selectorRunner) {
        Selector selector = selectorRunner.getSelector();
        boolean z = true;
        boolean hasPostponedTasks = !selectorRunner.getPostponedTasks().isEmpty();
        if (!hasPostponedTasks) {
            selector.select(this.selectTimeout);
        } else {
            selector.selectNow();
        }
        Set<SelectionKey> selectedKeys = selector.selectedKeys();
        if (IS_WORKAROUND_SELECTOR_SPIN) {
            if (selectedKeys.isEmpty() && !hasPostponedTasks) {
                z = false;
            }
            selectorRunner.checkSelectorSpin(z, 2000);
        }
        return selectedKeys;
    }

    public void postSelect(SelectorRunner selectorRunner) {
    }

    public void registerKeyInterest(SelectorRunner selectorRunner, SelectionKey key, int interest) {
        if (isSelectorRunnerThread(selectorRunner)) {
            registerKey0(key, interest);
        } else {
            selectorRunner.addPendingTask(new RegisterKeyTask(key, interest));
        }
    }

    /* access modifiers changed from: private */
    public static void registerKey0(SelectionKey selectionKey, int interest) {
        if (selectionKey.isValid()) {
            int currentOps = selectionKey.interestOps();
            if ((currentOps & interest) != interest) {
                selectionKey.interestOps(currentOps | interest);
            }
        }
    }

    public void deregisterKeyInterest(SelectorRunner selectorRunner, SelectionKey key, int interest) {
        if (key.isValid()) {
            int currentOps = key.interestOps();
            if ((currentOps & interest) != 0) {
                key.interestOps((~interest) & currentOps);
            }
        }
    }

    public void registerChannel(SelectorRunner selectorRunner, SelectableChannel channel, int interest, Object attachment) {
        FutureImpl<RegisterChannelResult> future = SafeFutureImpl.create();
        registerChannelAsync(selectorRunner, channel, interest, attachment, Futures.toCompletionHandler(future));
        try {
            future.get(this.selectTimeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void registerChannelAsync(SelectorRunner selectorRunner, SelectableChannel channel, int interest, Object attachment, CompletionHandler<RegisterChannelResult> completionHandler) {
        if (isSelectorRunnerThread(selectorRunner)) {
            registerChannel0(selectorRunner, channel, interest, attachment, completionHandler);
        } else {
            addPendingTask(selectorRunner, new RegisterChannelOperation(channel, interest, attachment, completionHandler));
        }
    }

    public void deregisterChannel(SelectorRunner selectorRunner, SelectableChannel channel) {
        FutureImpl<RegisterChannelResult> future = SafeFutureImpl.create();
        deregisterChannelAsync(selectorRunner, channel, Futures.toCompletionHandler(future));
        try {
            future.get(this.selectTimeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    public void deregisterChannelAsync(SelectorRunner selectorRunner, SelectableChannel channel, CompletionHandler<RegisterChannelResult> completionHandler) {
        if (isSelectorRunnerThread(selectorRunner)) {
            deregisterChannel0(selectorRunner, channel, completionHandler);
        } else {
            addPendingTask(selectorRunner, new DeregisterChannelOperation(channel, completionHandler));
        }
    }

    public void execute(SelectorRunner selectorRunner, SelectorHandler.Task task, CompletionHandler<SelectorHandler.Task> completionHandler) {
        if (isSelectorRunnerThread(selectorRunner)) {
            try {
                task.run();
                if (completionHandler != null) {
                    completionHandler.completed(task);
                }
            } catch (Exception e) {
                if (completionHandler != null) {
                    completionHandler.failed(e);
                }
            }
        } else {
            addPendingTask(selectorRunner, new RunnableTask(task, completionHandler));
        }
    }

    public void enque(SelectorRunner selectorRunner, SelectorHandler.Task task, CompletionHandler<SelectorHandler.Task> completionHandler) {
        if (isSelectorRunnerThread(selectorRunner)) {
            selectorRunner.getPostponedTasks().offer(new RunnableTask(task, completionHandler));
        } else {
            addPendingTask(selectorRunner, new RunnableTask(task, completionHandler));
        }
    }

    private void addPendingTask(SelectorRunner selectorRunner, SelectorHandlerTask task) {
        if (selectorRunner == null) {
            task.cancel();
            return;
        }
        selectorRunner.addPendingTask(task);
        if (selectorRunner.isStop() && selectorRunner.getPendingTasks().remove(task)) {
            task.cancel();
        }
    }

    private boolean processPendingTasks(SelectorRunner selectorRunner) {
        return processPendingTaskQueue(selectorRunner, selectorRunner.obtainPostponedTasks()) && (!selectorRunner.hasPendingTasks || processPendingTaskQueue(selectorRunner, selectorRunner.getPendingTasks()));
    }

    private boolean processPendingTaskQueue(SelectorRunner selectorRunner, Queue<SelectorHandlerTask> selectorHandlerTasks) {
        SelectorHandlerTask selectorHandlerTask;
        do {
            SelectorHandlerTask poll = selectorHandlerTasks.poll();
            selectorHandlerTask = poll;
            if (poll == null) {
                return true;
            }
        } while (selectorHandlerTask.run(selectorRunner));
        return false;
    }

    /* access modifiers changed from: private */
    public static void registerChannel0(SelectorRunner selectorRunner, SelectableChannel channel, int interest, Object attachment, CompletionHandler<RegisterChannelResult> completionHandler) {
        try {
            if (channel.isOpen()) {
                Selector selector = selectorRunner.getSelector();
                SelectionKey key = channel.keyFor(selector);
                if (key != null) {
                    if (!key.isValid()) {
                        selectorRunner.getPostponedTasks().add(new RegisterChannelOperation(channel, interest, attachment, completionHandler));
                        return;
                    }
                }
                SelectionKey registeredSelectionKey = channel.register(selector, interest, attachment);
                selectorRunner.getTransport().getSelectionKeyHandler().onKeyRegistered(registeredSelectionKey);
                RegisterChannelResult result = new RegisterChannelResult(selectorRunner, registeredSelectionKey, channel);
                if (completionHandler != null) {
                    completionHandler.completed(result);
                }
                return;
            }
            failChannelRegistration(completionHandler, new ClosedChannelException());
        } catch (IOException e) {
            failChannelRegistration(completionHandler, e);
        }
    }

    private static void failChannelRegistration(CompletionHandler<RegisterChannelResult> completionHandler, Throwable error) {
        if (completionHandler != null) {
            completionHandler.failed(error);
        }
    }

    /* access modifiers changed from: private */
    public static void deregisterChannel0(SelectorRunner selectorRunner, SelectableChannel channel, CompletionHandler<RegisterChannelResult> completionHandler) {
        Throwable error;
        try {
            if (channel.isOpen()) {
                SelectionKey key = channel.keyFor(selectorRunner.getSelector());
                if (key != null) {
                    selectorRunner.getTransport().getSelectionKeyHandler().cancel(key);
                    selectorRunner.getTransport().getSelectionKeyHandler().onKeyDeregistered(key);
                    RegisterChannelResult result = new RegisterChannelResult(selectorRunner, key, channel);
                    if (completionHandler != null) {
                        completionHandler.completed(result);
                        return;
                    }
                    return;
                }
                error = new IllegalStateException("Channel is not registered");
            } else {
                error = new ClosedChannelException();
            }
            Futures.notifyFailure((FutureImpl) null, completionHandler, error);
        } catch (IOException e) {
            Futures.notifyFailure((FutureImpl) null, completionHandler, e);
        }
    }

    public boolean onSelectorClosed(SelectorRunner selectorRunner) {
        try {
            selectorRunner.workaroundSelectorSpin();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isSelectorRunnerThread(SelectorRunner selectorRunner) {
        return selectorRunner != null && Thread.currentThread() == selectorRunner.getRunnerThread();
    }

    public static final class RegisterKeyTask implements SelectorHandlerTask {
        private final int interest;
        private final SelectionKey selectionKey;

        public RegisterKeyTask(SelectionKey selectionKey2, int interest2) {
            this.selectionKey = selectionKey2;
            this.interest = interest2;
        }

        public boolean run(SelectorRunner selectorRunner) {
            SelectionKey localSelectionKey = this.selectionKey;
            if (DefaultSelectorHandler.IS_WORKAROUND_SELECTOR_SPIN) {
                localSelectionKey = selectorRunner.checkIfSpinnedKey(this.selectionKey);
            }
            DefaultSelectorHandler.registerKey0(localSelectionKey, this.interest);
            return true;
        }

        public void cancel() {
        }
    }

    public static final class RegisterChannelOperation implements SelectorHandlerTask {
        private final Object attachment;
        private final SelectableChannel channel;
        private final CompletionHandler<RegisterChannelResult> completionHandler;
        private final int interest;

        private RegisterChannelOperation(SelectableChannel channel2, int interest2, Object attachment2, CompletionHandler<RegisterChannelResult> completionHandler2) {
            this.channel = channel2;
            this.interest = interest2;
            this.attachment = attachment2;
            this.completionHandler = completionHandler2;
        }

        public boolean run(SelectorRunner selectorRunner) {
            DefaultSelectorHandler.registerChannel0(selectorRunner, this.channel, this.interest, this.attachment, this.completionHandler);
            return true;
        }

        public void cancel() {
            CompletionHandler<RegisterChannelResult> completionHandler2 = this.completionHandler;
            if (completionHandler2 != null) {
                completionHandler2.failed(new IOException("Selector is closed"));
            }
        }
    }

    public static final class RunnableTask implements SelectorHandlerTask {
        private final CompletionHandler<SelectorHandler.Task> completionHandler;
        private final SelectorHandler.Task task;

        private RunnableTask(SelectorHandler.Task task2, CompletionHandler<SelectorHandler.Task> completionHandler2) {
            this.task = task2;
            this.completionHandler = completionHandler2;
        }

        public boolean run(SelectorRunner selectorRunner) {
            boolean continueExecution = true;
            try {
                continueExecution = this.task.run();
                CompletionHandler<SelectorHandler.Task> completionHandler2 = this.completionHandler;
                if (completionHandler2 != null) {
                    completionHandler2.completed(this.task);
                }
            } catch (Throwable t) {
                DefaultSelectorHandler.logger.log(Level.FINEST, "doExecutePendiongIO failed.", t);
                CompletionHandler<SelectorHandler.Task> completionHandler3 = this.completionHandler;
                if (completionHandler3 != null) {
                    completionHandler3.failed(t);
                }
            }
            return continueExecution;
        }

        public void cancel() {
            CompletionHandler<SelectorHandler.Task> completionHandler2 = this.completionHandler;
            if (completionHandler2 != null) {
                completionHandler2.failed(new IOException("Selector is closed"));
            }
        }
    }

    public static final class DeregisterChannelOperation implements SelectorHandlerTask {
        private final SelectableChannel channel;
        private final CompletionHandler<RegisterChannelResult> completionHandler;

        private DeregisterChannelOperation(SelectableChannel channel2, CompletionHandler<RegisterChannelResult> completionHandler2) {
            this.channel = channel2;
            this.completionHandler = completionHandler2;
        }

        public boolean run(SelectorRunner selectorRunner) {
            DefaultSelectorHandler.deregisterChannel0(selectorRunner, this.channel, this.completionHandler);
            return true;
        }

        public void cancel() {
            CompletionHandler<RegisterChannelResult> completionHandler2 = this.completionHandler;
            if (completionHandler2 != null) {
                completionHandler2.failed(new IOException("Selector is closed"));
            }
        }
    }
}
