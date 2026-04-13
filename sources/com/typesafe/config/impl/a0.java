package com.typesafe.config.impl;

import java.util.Collection;

/* compiled from: ResolveStatus */
public enum a0 {
    UNRESOLVED,
    RESOLVED;

    static final a0 fromValues(Collection<? extends AbstractConfigValue> values) {
        for (AbstractConfigValue v : values) {
            a0 resolveStatus = v.resolveStatus();
            a0 a0Var = UNRESOLVED;
            if (resolveStatus == a0Var) {
                return a0Var;
            }
        }
        return RESOLVED;
    }

    static final a0 fromBoolean(boolean resolved) {
        return resolved ? RESOLVED : UNRESOLVED;
    }
}
