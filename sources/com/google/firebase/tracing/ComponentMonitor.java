package com.google.firebase.tracing;

import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.ComponentRegistrarProcessor;
import java.util.ArrayList;
import java.util.List;

public class ComponentMonitor implements ComponentRegistrarProcessor {
    public List<Component<?>> processRegistrar(ComponentRegistrar registrar) {
        List<Component<?>> components = new ArrayList<>();
        for (Component comp : registrar.getComponents()) {
            String name = comp.getName();
            if (name != null) {
                Component old = comp;
                comp = old.withFactory(new a(name, old));
            }
            components.add(comp);
        }
        return components;
    }

    static /* synthetic */ Object lambda$processRegistrar$0(String name, Component old, ComponentContainer c) {
        try {
            FirebaseTrace.pushTrace(name);
            return old.getFactory().create(c);
        } finally {
            FirebaseTrace.popTrace();
        }
    }
}
