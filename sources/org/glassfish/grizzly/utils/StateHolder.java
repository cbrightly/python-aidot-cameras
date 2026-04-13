package org.glassfish.grizzly.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.impl.ReadyFutureImpl;
import org.glassfish.grizzly.impl.SafeFutureImpl;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.conditions.Condition;

public final class StateHolder<E> {
    private static final Logger _logger = Grizzly.logger(StateHolder.class);
    private final Collection<ConditionElement<E>> conditionListeners;
    private final ReentrantReadWriteLock readWriteLock;
    /* access modifiers changed from: private */
    public volatile E state;

    public StateHolder() {
        this((Object) null);
    }

    public StateHolder(E initialState) {
        this.state = initialState;
        this.readWriteLock = new ReentrantReadWriteLock();
        this.conditionListeners = new ConcurrentLinkedQueue();
    }

    public E getState() {
        return this.state;
    }

    public void setState(E state2) {
        this.readWriteLock.writeLock().lock();
        try {
            this.state = state2;
            this.readWriteLock.readLock().lock();
            this.readWriteLock.writeLock().unlock();
            notifyConditionListeners();
        } finally {
            this.readWriteLock.readLock().unlock();
        }
    }

    public ReentrantReadWriteLock getStateLocker() {
        return this.readWriteLock;
    }

    public Future<E> notifyWhenStateIsEqual(final E state2, CompletionHandler<E> completionHandler) {
        return notifyWhenConditionMatchState(new Condition() {
            public boolean check() {
                return state2 == StateHolder.this.state;
            }
        }, completionHandler);
    }

    public Future<E> notifyWhenStateIsNotEqual(final E state2, CompletionHandler<E> completionHandler) {
        return notifyWhenConditionMatchState(new Condition() {
            public boolean check() {
                return state2 != StateHolder.this.state;
            }
        }, completionHandler);
    }

    public Future<E> notifyWhenConditionMatchState(Condition condition, CompletionHandler<E> completionHandler) {
        FutureImpl<E> future;
        this.readWriteLock.readLock().lock();
        try {
            if (condition.check()) {
                if (completionHandler != null) {
                    completionHandler.completed(this.state);
                }
                future = ReadyFutureImpl.create(this.state);
            } else {
                future = SafeFutureImpl.create();
                this.conditionListeners.add(new ConditionElement<>(condition, future, completionHandler));
                FutureImpl<E> futureImpl = future;
            }
            return future;
        } finally {
            this.readWriteLock.readLock().unlock();
        }
    }

    /* access modifiers changed from: protected */
    public void notifyConditionListeners() {
        Iterator<ConditionElement<E>> it = this.conditionListeners.iterator();
        while (it.hasNext()) {
            ConditionElement<E> element = it.next();
            try {
                if (element.condition.check()) {
                    it.remove();
                    if (element.completionHandler != null) {
                        element.completionHandler.completed(this.state);
                    }
                    element.future.result(this.state);
                }
            } catch (Exception e) {
                _logger.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_STATE_HOLDER_CALLING_CONDITIONLISTENER_EXCEPTION(), e);
            }
        }
    }

    public static final class ConditionElement<E> {
        /* access modifiers changed from: private */
        public final CompletionHandler<E> completionHandler;
        /* access modifiers changed from: private */
        public final Condition condition;
        /* access modifiers changed from: private */
        public final FutureImpl<E> future;

        public ConditionElement(Condition condition2, FutureImpl<E> future2, CompletionHandler<E> completionHandler2) {
            this.condition = condition2;
            this.future = future2;
            this.completionHandler = completionHandler2;
        }

        public CompletionHandler<E> getCompletionHandler() {
            return this.completionHandler;
        }

        public Condition getCondition() {
            return this.condition;
        }

        public FutureImpl<E> getFuture() {
            return this.future;
        }
    }
}
