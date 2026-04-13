package com.google.firebase.components;

import com.google.firebase.inject.Deferred;
import com.google.firebase.inject.Provider;
import java.util.Set;

/* compiled from: ComponentContainer */
public final /* synthetic */ class s {
    public static <T> Object b(ComponentContainer _this, Class anInterface) {
        return _this.get(Qualified.unqualified(anInterface));
    }

    public static <T> Provider d(ComponentContainer _this, Class anInterface) {
        return _this.getProvider(Qualified.unqualified(anInterface));
    }

    public static <T> Deferred c(ComponentContainer _this, Class anInterface) {
        return _this.getDeferred(Qualified.unqualified(anInterface));
    }

    public static <T> Set f(ComponentContainer _this, Class anInterface) {
        return _this.setOf(Qualified.unqualified(anInterface));
    }

    public static <T> Provider g(ComponentContainer _this, Class anInterface) {
        return _this.setOfProvider(Qualified.unqualified(anInterface));
    }

    public static <T> Object a(ComponentContainer _this, Qualified anInterface) {
        Provider<T> provider = _this.getProvider(anInterface);
        if (provider == null) {
            return null;
        }
        return provider.get();
    }

    public static <T> Set e(ComponentContainer _this, Qualified anInterface) {
        return (Set) _this.setOfProvider(anInterface).get();
    }
}
