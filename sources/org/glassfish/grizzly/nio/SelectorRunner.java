package org.glassfish.grizzly.nio;

import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import java.io.IOException;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOStrategy;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.threadpool.Threads;
import org.glassfish.grizzly.utils.StateHolder;

public final class SelectorRunner implements Runnable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Logger LOGGER = Grizzly.logger(SelectorRunner.class);
    private static final String THREAD_MARKER = " SelectorRunner";
    private Queue<SelectorHandlerTask> currentPostponedTasks;
    private volatile int dumbVolatile = 1;
    private int emptySpinCounter;
    private final Queue<SelectorHandlerTask> evenPostponedTasks;
    volatile boolean hasPendingTasks;
    private boolean isResume;
    private Iterator<SelectionKey> iterator;
    private SelectionKey key = null;
    private int keyReadyOps;
    private int lastSelectedKeysCount;
    private long lastSpinTimestamp;
    private final Queue<SelectorHandlerTask> oddPostponedTasks;
    private final Queue<SelectorHandlerTask> pendingTasks;
    private Set<SelectionKey> readyKeySet;
    private final AtomicInteger runnerThreadActivityCounter = new AtomicInteger();
    private Selector selector;
    private Thread selectorRunnerThread;
    private final AtomicBoolean selectorWakeupFlag = new AtomicBoolean();
    private final Map<Selector, Long> spinnedSelectorsHistory = new WeakHashMap();
    private final AtomicReference<Transport.State> stateHolder;
    private final NIOTransport transport;

    static {
        Class<SelectorRunner> cls = SelectorRunner.class;
    }

    public static SelectorRunner create(NIOTransport transport2) {
        return new SelectorRunner(transport2, Selectors.newSelector(transport2.getSelectorProvider()));
    }

    private SelectorRunner(NIOTransport transport2, Selector selector2) {
        this.transport = transport2;
        this.selector = selector2;
        this.stateHolder = new AtomicReference<>(Transport.State.STOPPED);
        this.pendingTasks = new ConcurrentLinkedQueue();
        ArrayDeque arrayDeque = new ArrayDeque();
        this.evenPostponedTasks = arrayDeque;
        this.oddPostponedTasks = new ArrayDeque();
        this.currentPostponedTasks = arrayDeque;
    }

    /* access modifiers changed from: package-private */
    public void addPendingTask(SelectorHandlerTask task) {
        this.pendingTasks.offer(task);
        this.hasPendingTasks = true;
        wakeupSelector();
    }

    private void wakeupSelector() {
        Selector localSelector = getSelector();
        if (localSelector != null && this.selectorWakeupFlag.compareAndSet(false, true)) {
            try {
                localSelector.wakeup();
            } catch (Exception e) {
                LOGGER.log(Level.FINE, "Error during selector wakeup", e);
            }
        }
    }

    public NIOTransport getTransport() {
        return this.transport;
    }

    public Selector getSelector() {
        if (this.dumbVolatile != 0) {
            return this.selector;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setSelector(Selector selector2) {
        this.selector = selector2;
        this.dumbVolatile++;
    }

    private void setRunnerThread(Thread runnerThread) {
        this.selectorRunnerThread = runnerThread;
        this.dumbVolatile++;
    }

    public Thread getRunnerThread() {
        if (this.dumbVolatile != 0) {
            return this.selectorRunnerThread;
        }
        return null;
    }

    public Transport.State getState() {
        return this.stateHolder.get();
    }

    public void postpone() {
        Thread thread = this.selectorRunnerThread;
        if (thread != null) {
            removeThreadNameMarker(thread);
            Threads.setService(false);
            this.runnerThreadActivityCounter.compareAndSet(1, 0);
            this.selectorRunnerThread = null;
            this.isResume = true;
            this.dumbVolatile++;
            return;
        }
        throw new AssertionError();
    }

    public synchronized void start() {
        if (!this.stateHolder.compareAndSet(Transport.State.STOPPED, Transport.State.STARTING)) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SELECTOR_RUNNER_NOT_IN_STOPPED_STATE_EXCEPTION());
        } else {
            this.transport.getKernelThreadPool().execute(this);
        }
    }

    public synchronized void stop() {
        this.stateHolder.set(Transport.State.STOPPING);
        wakeupSelector();
        if (this.runnerThreadActivityCounter.compareAndSet(0, -1)) {
            shutdownSelector();
        }
    }

    private void shutdownSelector() {
        Selector localSelector = getSelector();
        if (localSelector != null) {
            try {
                SelectionKey[] keys = new SelectionKey[0];
                while (true) {
                    try {
                        break;
                    } catch (ConcurrentModificationException e) {
                    }
                }
                for (SelectionKey selectionKey : (SelectionKey[]) localSelector.keys().toArray(keys)) {
                    this.transport.getSelectionKeyHandler().getConnectionForKey(selectionKey).terminateSilently();
                }
                try {
                    localSelector.close();
                } catch (Exception e2) {
                }
            } catch (ClosedSelectorException e3) {
                localSelector.close();
            } catch (Throwable th) {
                try {
                    localSelector.close();
                } catch (Exception e4) {
                }
                throw th;
            }
        }
        abortTasksInQueue(this.pendingTasks);
        abortTasksInQueue(this.evenPostponedTasks);
        abortTasksInQueue(this.oddPostponedTasks);
    }

    public void run() {
        if (this.runnerThreadActivityCounter.compareAndSet(0, 1)) {
            Thread currentThread = Thread.currentThread();
            try {
                if (!this.isResume) {
                    if (this.stateHolder.compareAndSet(Transport.State.STARTING, Transport.State.STARTED)) {
                        addThreadNameMarker(currentThread);
                    } else {
                        return;
                    }
                }
                setRunnerThread(currentThread);
                Threads.setService(true);
                StateHolder<Transport.State> transportStateHolder = this.transport.getState();
                boolean isSkipping = false;
                while (!isSkipping && !isStop()) {
                    Transport.State state = transportStateHolder.getState();
                    Transport.State state2 = Transport.State.PAUSED;
                    if (state != state2) {
                        isSkipping = !doSelect();
                    } else {
                        try {
                            transportStateHolder.notifyWhenStateIsNotEqual(state2, (CompletionHandler<Transport.State>) null).get(KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
                        } catch (Exception e) {
                        }
                    }
                }
                this.runnerThreadActivityCounter.compareAndSet(1, 0);
                if (isStop()) {
                    this.stateHolder.set(Transport.State.STOPPED);
                    setRunnerThread((Thread) null);
                    if (this.runnerThreadActivityCounter.compareAndSet(0, -1)) {
                        shutdownSelector();
                    }
                }
                removeThreadNameMarker(currentThread);
                Threads.setService(false);
            } finally {
                this.runnerThreadActivityCounter.compareAndSet(1, 0);
                if (isStop()) {
                    this.stateHolder.set(Transport.State.STOPPED);
                    setRunnerThread((Thread) null);
                    if (this.runnerThreadActivityCounter.compareAndSet(0, -1)) {
                        shutdownSelector();
                    }
                }
                removeThreadNameMarker(currentThread);
                Threads.setService(false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean doSelect() {
        SelectorHandler selectorHandler = this.transport.getSelectorHandler();
        try {
            if (this.isResume) {
                this.isResume = false;
                if (this.readyKeySet != null) {
                    if ((this.keyReadyOps != 0 && !iterateKeyEvents()) || !iterateKeys()) {
                        return false;
                    }
                    this.readyKeySet.clear();
                }
            }
            this.lastSelectedKeysCount = 0;
            if (!selectorHandler.preSelect(this)) {
                return false;
            }
            this.readyKeySet = selectorHandler.select(this);
            this.selectorWakeupFlag.set(false);
            if (this.stateHolder.get() == Transport.State.STOPPING) {
                return true;
            }
            int size = this.readyKeySet.size();
            this.lastSelectedKeysCount = size;
            if (size != 0) {
                this.iterator = this.readyKeySet.iterator();
                if (!iterateKeys()) {
                    return false;
                }
                this.readyKeySet.clear();
            }
            this.readyKeySet = null;
            this.iterator = null;
            selectorHandler.postSelect(this);
            return true;
        } catch (ClosedSelectorException e) {
            ClosedSelectorException e2 = e;
            if (isRunning() && selectorHandler.onSelectorClosed(this)) {
                return true;
            }
            dropConnectionDueToException(this.key, "Selector was unexpectedly closed", e2, Level.SEVERE, Level.FINE);
        } catch (Exception e3) {
            dropConnectionDueToException(this.key, "doSelect exception", e3, Level.SEVERE, Level.FINE);
        } catch (Throwable t) {
            LOGGER.log(Level.SEVERE, "doSelect exception", t);
            this.transport.notifyTransportError(t);
        }
    }

    private boolean iterateKeys() {
        Iterator<SelectionKey> it = this.iterator;
        while (it.hasNext()) {
            try {
                SelectionKey next = it.next();
                this.key = next;
                this.keyReadyOps = next.readyOps();
                if (!iterateKeyEvents()) {
                    return false;
                }
            } catch (IOException e) {
                this.keyReadyOps = 0;
                dropConnectionDueToException(this.key, "Unexpected IOException. Channel " + this.key.channel() + " will be closed.", e, Level.WARNING, Level.FINE);
            } catch (CancelledKeyException e2) {
                this.keyReadyOps = 0;
                Level level = Level.FINE;
                dropConnectionDueToException(this.key, "Unexpected CancelledKeyException. Channel " + this.key.channel() + " will be closed.", e2, level, level);
            }
        }
        return true;
    }

    private boolean iterateKeyEvents() {
        SelectionKey keyLocal = this.key;
        SelectionKeyHandler selectionKeyHandler = this.transport.getSelectionKeyHandler();
        IOStrategy ioStrategy = this.transport.getIOStrategy();
        IOEvent[] ioEvents = selectionKeyHandler.getIOEvents(this.keyReadyOps);
        NIOConnection connection = selectionKeyHandler.getConnectionForKey(keyLocal);
        for (IOEvent ioEvent : ioEvents) {
            NIOConnection.notifyIOEventReady(connection, ioEvent);
            int interest = ioEvent.getSelectionKeyInterest();
            this.keyReadyOps &= ~interest;
            if (selectionKeyHandler.onProcessInterest(keyLocal, interest) && !ioStrategy.executeIoEvent(connection, ioEvent)) {
                return false;
            }
        }
        return true;
    }

    public Queue<SelectorHandlerTask> getPendingTasks() {
        this.hasPendingTasks = false;
        return this.pendingTasks;
    }

    public Queue<SelectorHandlerTask> getPostponedTasks() {
        return this.currentPostponedTasks;
    }

    public Queue<SelectorHandlerTask> obtainPostponedTasks() {
        Queue<SelectorHandlerTask> tasksToReturn = this.currentPostponedTasks;
        Queue<SelectorHandlerTask> queue = this.currentPostponedTasks;
        Queue<SelectorHandlerTask> queue2 = this.evenPostponedTasks;
        if (queue == queue2) {
            queue2 = this.oddPostponedTasks;
        }
        this.currentPostponedTasks = queue2;
        return tasksToReturn;
    }

    /* access modifiers changed from: package-private */
    public boolean isStop() {
        Transport.State state = this.stateHolder.get();
        return state == Transport.State.STOPPED || state == Transport.State.STOPPING;
    }

    private boolean isRunning() {
        return this.stateHolder.get() == Transport.State.STARTED;
    }

    private void dropConnectionDueToException(SelectionKey key2, String description, Exception e, Level runLogLevel, Level stoppedLogLevel) {
        if (isRunning()) {
            LOGGER.log(runLogLevel, description, e);
            if (key2 != null) {
                try {
                    Connection connection = this.transport.getSelectionKeyHandler().getConnectionForKey(key2);
                    if (connection != null) {
                        connection.closeSilently();
                    } else {
                        SelectableChannel channel = key2.channel();
                        this.transport.getSelectionKeyHandler().cancel(key2);
                        channel.close();
                    }
                } catch (IOException cancelException) {
                    LOGGER.log(Level.FINE, "IOException during cancelling key", cancelException);
                }
            }
            this.transport.notifyTransportError(e);
            return;
        }
        LOGGER.log(stoppedLogLevel, description, e);
    }

    public int getLastSelectedKeysCount() {
        return this.lastSelectedKeysCount;
    }

    /* access modifiers changed from: protected */
    public void switchToNewSelector() {
        Selector oldSelector = this.selector;
        Selector newSelector = Selectors.newSelector(this.transport.getSelectorProvider());
        Set<SelectionKey> keys = oldSelector.keys();
        SelectionKeyHandler selectionKeyHandler = this.transport.getSelectionKeyHandler();
        for (SelectionKey selectionKey : keys) {
            if (selectionKey.isValid()) {
                try {
                    selectionKeyHandler.getConnectionForKey(selectionKey).onSelectionKeyUpdated(selectionKey.channel().register(newSelector, selectionKey.interestOps(), selectionKey.attachment()));
                } catch (Exception e) {
                    LOGGER.log(Level.FINE, "Error switching channel to a new selector", e);
                }
            }
        }
        setSelector(newSelector);
        try {
            oldSelector.close();
        } catch (Exception e2) {
        }
    }

    private void abortTasksInQueue(Queue<SelectorHandlerTask> taskQueue) {
        while (true) {
            SelectorHandlerTask poll = taskQueue.poll();
            SelectorHandlerTask task = poll;
            if (poll != null) {
                try {
                    task.cancel();
                } catch (Exception e) {
                }
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void resetSpinCounter() {
        this.emptySpinCounter = 0;
    }

    /* access modifiers changed from: package-private */
    public int incSpinCounter() {
        int i = this.emptySpinCounter;
        int i2 = i + 1;
        this.emptySpinCounter = i2;
        if (i == 0) {
            this.lastSpinTimestamp = System.nanoTime();
        } else if (i2 == 1000) {
            int contspinspersec = (int) (1000000000000L / (System.nanoTime() - this.lastSpinTimestamp));
            this.emptySpinCounter = 0;
            return contspinspersec;
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public SelectionKey checkIfSpinnedKey(SelectionKey key2) {
        if (key2.isValid() || !key2.channel().isOpen() || !this.spinnedSelectorsHistory.containsKey(key2.selector())) {
            return key2;
        }
        SelectionKey newKey = key2.channel().keyFor(getSelector());
        newKey.attach(key2.attachment());
        return newKey;
    }

    /* access modifiers changed from: package-private */
    public void workaroundSelectorSpin() {
        Logger logger = LOGGER;
        Level level = Level.FINE;
        if (logger.isLoggable(level)) {
            logger.log(level, "Workaround selector spin. selector={0}", getSelector());
        }
        this.spinnedSelectorsHistory.put(getSelector(), Long.valueOf(System.currentTimeMillis()));
        switchToNewSelector();
    }

    /* access modifiers changed from: package-private */
    public void checkSelectorSpin(boolean hasSelectedKeys, int spinRateThreshold) {
        if (hasSelectedKeys) {
            resetSpinCounter();
        } else if (((long) incSpinCounter()) > ((long) spinRateThreshold)) {
            workaroundSelectorSpin();
        }
    }

    private void addThreadNameMarker(Thread currentThread) {
        String name = currentThread.getName();
        if (!name.endsWith(THREAD_MARKER)) {
            currentThread.setName(name + THREAD_MARKER);
        }
    }

    private void removeThreadNameMarker(Thread currentThread) {
        String name = currentThread.getName();
        if (name.endsWith(THREAD_MARKER)) {
            currentThread.setName(name.substring(0, name.length() - THREAD_MARKER.length()));
        }
    }
}
