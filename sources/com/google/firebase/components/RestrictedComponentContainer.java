package com.google.firebase.components;

import com.google.firebase.events.Event;
import com.google.firebase.events.Publisher;
import com.google.firebase.inject.Deferred;
import com.google.firebase.inject.Provider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class RestrictedComponentContainer implements ComponentContainer {
    private final Set<Qualified<?>> allowedDeferredInterfaces;
    private final Set<Qualified<?>> allowedDirectInterfaces;
    private final Set<Qualified<?>> allowedProviderInterfaces;
    private final Set<Class<?>> allowedPublishedEvents;
    private final Set<Qualified<?>> allowedSetDirectInterfaces;
    private final Set<Qualified<?>> allowedSetProviderInterfaces;
    private final ComponentContainer delegateContainer;

    public /* synthetic */ Set setOf(Class cls) {
        return s.f(this, cls);
    }

    RestrictedComponentContainer(Component<?> component, ComponentContainer container) {
        Set<Qualified<?>> directInterfaces = new HashSet<>();
        Set<Qualified<?>> providerInterfaces = new HashSet<>();
        Set<Qualified<?>> deferredInterfaces = new HashSet<>();
        Set<Qualified<?>> setDirectInterfaces = new HashSet<>();
        Set<Qualified<?>> setProviderInterfaces = new HashSet<>();
        for (Dependency dependency : component.getDependencies()) {
            if (dependency.isDirectInjection()) {
                if (dependency.isSet()) {
                    setDirectInterfaces.add(dependency.getInterface());
                } else {
                    directInterfaces.add(dependency.getInterface());
                }
            } else if (dependency.isDeferred()) {
                deferredInterfaces.add(dependency.getInterface());
            } else if (dependency.isSet()) {
                setProviderInterfaces.add(dependency.getInterface());
            } else {
                providerInterfaces.add(dependency.getInterface());
            }
        }
        if (!component.getPublishedEvents().isEmpty()) {
            directInterfaces.add(Qualified.unqualified(Publisher.class));
        }
        this.allowedDirectInterfaces = Collections.unmodifiableSet(directInterfaces);
        this.allowedProviderInterfaces = Collections.unmodifiableSet(providerInterfaces);
        this.allowedDeferredInterfaces = Collections.unmodifiableSet(deferredInterfaces);
        this.allowedSetDirectInterfaces = Collections.unmodifiableSet(setDirectInterfaces);
        this.allowedSetProviderInterfaces = Collections.unmodifiableSet(setProviderInterfaces);
        this.allowedPublishedEvents = component.getPublishedEvents();
        this.delegateContainer = container;
    }

    public <T> T get(Class<T> anInterface) {
        if (this.allowedDirectInterfaces.contains(Qualified.unqualified(anInterface))) {
            T value = this.delegateContainer.get(anInterface);
            if (!anInterface.equals(Publisher.class)) {
                return value;
            }
            return new RestrictedPublisher(this.allowedPublishedEvents, (Publisher) value);
        }
        throw new DependencyException(String.format("Attempting to request an undeclared dependency %s.", new Object[]{anInterface}));
    }

    public <T> T get(Qualified<T> anInterface) {
        if (this.allowedDirectInterfaces.contains(anInterface)) {
            return this.delegateContainer.get(anInterface);
        }
        throw new DependencyException(String.format("Attempting to request an undeclared dependency %s.", new Object[]{anInterface}));
    }

    public <T> Provider<T> getProvider(Class<T> anInterface) {
        return getProvider(Qualified.unqualified(anInterface));
    }

    public <T> Deferred<T> getDeferred(Class<T> anInterface) {
        return getDeferred(Qualified.unqualified(anInterface));
    }

    public <T> Provider<Set<T>> setOfProvider(Class<T> anInterface) {
        return setOfProvider(Qualified.unqualified(anInterface));
    }

    public <T> Provider<T> getProvider(Qualified<T> anInterface) {
        if (this.allowedProviderInterfaces.contains(anInterface)) {
            return this.delegateContainer.getProvider(anInterface);
        }
        throw new DependencyException(String.format("Attempting to request an undeclared dependency Provider<%s>.", new Object[]{anInterface}));
    }

    public <T> Deferred<T> getDeferred(Qualified<T> anInterface) {
        if (this.allowedDeferredInterfaces.contains(anInterface)) {
            return this.delegateContainer.getDeferred(anInterface);
        }
        throw new DependencyException(String.format("Attempting to request an undeclared dependency Deferred<%s>.", new Object[]{anInterface}));
    }

    public <T> Provider<Set<T>> setOfProvider(Qualified<T> anInterface) {
        if (this.allowedSetProviderInterfaces.contains(anInterface)) {
            return this.delegateContainer.setOfProvider(anInterface);
        }
        throw new DependencyException(String.format("Attempting to request an undeclared dependency Provider<Set<%s>>.", new Object[]{anInterface}));
    }

    public <T> Set<T> setOf(Qualified<T> anInterface) {
        if (this.allowedSetDirectInterfaces.contains(anInterface)) {
            return this.delegateContainer.setOf(anInterface);
        }
        throw new DependencyException(String.format("Attempting to request an undeclared dependency Set<%s>.", new Object[]{anInterface}));
    }

    public static class RestrictedPublisher implements Publisher {
        private final Set<Class<?>> allowedPublishedEvents;
        private final Publisher delegate;

        public RestrictedPublisher(Set<Class<?>> allowedPublishedEvents2, Publisher delegate2) {
            this.allowedPublishedEvents = allowedPublishedEvents2;
            this.delegate = delegate2;
        }

        public void publish(Event<?> event) {
            if (this.allowedPublishedEvents.contains(event.getType())) {
                this.delegate.publish(event);
            } else {
                throw new DependencyException(String.format("Attempting to publish an undeclared event %s.", new Object[]{event}));
            }
        }
    }
}
