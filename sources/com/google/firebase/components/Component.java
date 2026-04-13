package com.google.firebase.components;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Component<T> {
    private final Set<Dependency> dependencies;
    private final ComponentFactory<T> factory;
    private final int instantiation;
    private final String name;
    private final Set<Qualified<? super T>> providedInterfaces;
    private final Set<Class<?>> publishedEvents;
    private final int type;

    private Component(@Nullable String name2, Set<Qualified<? super T>> providedInterfaces2, Set<Dependency> dependencies2, int instantiation2, int type2, ComponentFactory<T> factory2, Set<Class<?>> publishedEvents2) {
        this.name = name2;
        this.providedInterfaces = Collections.unmodifiableSet(providedInterfaces2);
        this.dependencies = Collections.unmodifiableSet(dependencies2);
        this.instantiation = instantiation2;
        this.type = type2;
        this.factory = factory2;
        this.publishedEvents = Collections.unmodifiableSet(publishedEvents2);
    }

    @Nullable
    public String getName() {
        return this.name;
    }

    public Set<Qualified<? super T>> getProvidedInterfaces() {
        return this.providedInterfaces;
    }

    public Set<Dependency> getDependencies() {
        return this.dependencies;
    }

    public ComponentFactory<T> getFactory() {
        return this.factory;
    }

    public Set<Class<?>> getPublishedEvents() {
        return this.publishedEvents;
    }

    public boolean isLazy() {
        return this.instantiation == 0;
    }

    public boolean isAlwaysEager() {
        return this.instantiation == 1;
    }

    public boolean isEagerInDefaultApp() {
        return this.instantiation == 2;
    }

    public boolean isValue() {
        return this.type == 0;
    }

    public Component<T> withFactory(ComponentFactory<T> factory2) {
        return new Component(this.name, this.providedInterfaces, this.dependencies, this.instantiation, this.type, factory2, this.publishedEvents);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Component<");
        sb.append(Arrays.toString(this.providedInterfaces.toArray()));
        sb.append(">{");
        sb.append(this.instantiation);
        sb.append(", type=");
        sb.append(this.type);
        sb.append(", deps=");
        sb.append(Arrays.toString(this.dependencies.toArray()));
        return sb.append("}").toString();
    }

    public static <T> Builder<T> builder(Class<T> anInterface) {
        return new Builder<>((Class) anInterface, new Class[0]);
    }

    @SafeVarargs
    public static <T> Builder<T> builder(Class<T> anInterface, Class<? super T>... additionalInterfaces) {
        return new Builder<>((Class) anInterface, (Class[]) additionalInterfaces);
    }

    public static <T> Builder<T> builder(Qualified<T> anInterface) {
        return new Builder<>((Qualified) anInterface, new Qualified[0]);
    }

    @SafeVarargs
    public static <T> Builder<T> builder(Qualified<T> anInterface, Qualified<? super T>... additionalInterfaces) {
        return new Builder<>((Qualified) anInterface, (Qualified[]) additionalInterfaces);
    }

    static /* synthetic */ Object lambda$of$0(Object value, ComponentContainer args) {
        return value;
    }

    @Deprecated
    public static <T> Component<T> of(Class<T> anInterface, T value) {
        return builder(anInterface).factory(new c(value)).build();
    }

    static /* synthetic */ Object lambda$of$1(Object value, ComponentContainer args) {
        return value;
    }

    @SafeVarargs
    public static <T> Component<T> of(T value, Class<T> anInterface, Class<? super T>... additionalInterfaces) {
        return builder(anInterface, additionalInterfaces).factory(new d(value)).build();
    }

    static /* synthetic */ Object lambda$of$2(Object value, ComponentContainer args) {
        return value;
    }

    @SafeVarargs
    public static <T> Component<T> of(T value, Qualified<T> anInterface, Qualified<? super T>... additionalInterfaces) {
        return builder(anInterface, additionalInterfaces).factory(new e(value)).build();
    }

    public static <T> Builder<T> intoSetBuilder(Class<T> anInterface) {
        return builder(anInterface).intoSet();
    }

    public static <T> Builder<T> intoSetBuilder(Qualified<T> anInterface) {
        return builder(anInterface).intoSet();
    }

    public static <T> Component<T> intoSet(T value, Class<T> anInterface) {
        return intoSetBuilder(anInterface).factory(new a(value)).build();
    }

    static /* synthetic */ Object lambda$intoSet$3(Object value, ComponentContainer c) {
        return value;
    }

    public static <T> Component<T> intoSet(T value, Qualified<T> anInterface) {
        return intoSetBuilder(anInterface).factory(new b(value)).build();
    }

    static /* synthetic */ Object lambda$intoSet$4(Object value, ComponentContainer c) {
        return value;
    }

    public static class Builder<T> {
        private final Set<Dependency> dependencies;
        private ComponentFactory<T> factory;
        private int instantiation;
        private String name;
        private final Set<Qualified<? super T>> providedInterfaces;
        private final Set<Class<?>> publishedEvents;
        private int type;

        @SafeVarargs
        private Builder(Class<T> anInterface, Class<? super T>... additionalInterfaces) {
            this.name = null;
            HashSet hashSet = new HashSet();
            this.providedInterfaces = hashSet;
            this.dependencies = new HashSet();
            this.instantiation = 0;
            this.type = 0;
            this.publishedEvents = new HashSet();
            Preconditions.checkNotNull(anInterface, "Null interface");
            hashSet.add(Qualified.unqualified(anInterface));
            for (Class<? super T> iface : additionalInterfaces) {
                Preconditions.checkNotNull(iface, "Null interface");
                this.providedInterfaces.add(Qualified.unqualified(iface));
            }
        }

        @SafeVarargs
        private Builder(Qualified<T> anInterface, Qualified<? super T>... additionalInterfaces) {
            this.name = null;
            HashSet hashSet = new HashSet();
            this.providedInterfaces = hashSet;
            this.dependencies = new HashSet();
            this.instantiation = 0;
            this.type = 0;
            this.publishedEvents = new HashSet();
            Preconditions.checkNotNull(anInterface, "Null interface");
            hashSet.add(anInterface);
            for (Qualified<? super T> iface : additionalInterfaces) {
                Preconditions.checkNotNull(iface, "Null interface");
            }
            Collections.addAll(this.providedInterfaces, additionalInterfaces);
        }

        public Builder<T> name(@NonNull String name2) {
            this.name = name2;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<T> add(Dependency dependency) {
            Preconditions.checkNotNull(dependency, "Null dependency");
            validateInterface(dependency.getInterface());
            this.dependencies.add(dependency);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<T> alwaysEager() {
            return setInstantiation(1);
        }

        @CanIgnoreReturnValue
        public Builder<T> eagerInDefaultApp() {
            return setInstantiation(2);
        }

        @CanIgnoreReturnValue
        public Builder<T> publishes(Class<?> eventType) {
            this.publishedEvents.add(eventType);
            return this;
        }

        @CanIgnoreReturnValue
        private Builder<T> setInstantiation(int instantiation2) {
            Preconditions.checkState(this.instantiation == 0, "Instantiation type has already been set.");
            this.instantiation = instantiation2;
            return this;
        }

        private void validateInterface(Qualified<?> anInterface) {
            Preconditions.checkArgument(!this.providedInterfaces.contains(anInterface), "Components are not allowed to depend on interfaces they themselves provide.");
        }

        @CanIgnoreReturnValue
        public Builder<T> factory(ComponentFactory<T> value) {
            this.factory = (ComponentFactory) Preconditions.checkNotNull(value, "Null factory");
            return this;
        }

        /* access modifiers changed from: private */
        @CanIgnoreReturnValue
        public Builder<T> intoSet() {
            this.type = 1;
            return this;
        }

        public Component<T> build() {
            Preconditions.checkState(this.factory != null, "Missing required property: factory.");
            return new Component(this.name, new HashSet(this.providedInterfaces), new HashSet(this.dependencies), this.instantiation, this.type, this.factory, this.publishedEvents);
        }
    }
}
