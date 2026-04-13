package com.google.firebase.components;

import java.lang.annotation.Annotation;

public final class Qualified<T> {
    private final Class<? extends Annotation> qualifier;
    private final Class<T> type;

    public @interface Unqualified {
    }

    public Qualified(Class<? extends Annotation> qualifier2, Class<T> type2) {
        this.qualifier = qualifier2;
        this.type = type2;
    }

    public static <T> Qualified<T> unqualified(Class<T> type2) {
        return new Qualified<>(Unqualified.class, type2);
    }

    public static <T> Qualified<T> qualified(Class<? extends Annotation> qualifier2, Class<T> type2) {
        return new Qualified<>(qualifier2, type2);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Qualified<?> qualified = (Qualified) o;
        if (!this.type.equals(qualified.type)) {
            return false;
        }
        return this.qualifier.equals(qualified.qualifier);
    }

    public int hashCode() {
        return (this.type.hashCode() * 31) + this.qualifier.hashCode();
    }

    public String toString() {
        if (this.qualifier == Unqualified.class) {
            return this.type.getName();
        }
        return "@" + this.qualifier.getName() + " " + this.type.getName();
    }
}
