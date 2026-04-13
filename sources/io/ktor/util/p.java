package io.ktor.util;

import java.util.Map;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.markers.a;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CaseInsensitiveMap.kt */
public final class p<Key, Value> implements Map.Entry<Key, Value>, a {
    private final Key c;
    private Value d;

    public p(Key key, Value value) {
        this.c = key;
        this.d = value;
    }

    public Key getKey() {
        return this.c;
    }

    public void a(Value value) {
        this.d = value;
    }

    public Value getValue() {
        return this.d;
    }

    public Value setValue(Value newValue) {
        a(newValue);
        return getValue();
    }

    public int hashCode() {
        Object key = getKey();
        if (key == null) {
            k.n();
        }
        int hashCode = 527 + key.hashCode();
        Object value = getValue();
        if (value == null) {
            k.n();
        }
        return hashCode + value.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (other == null || !(other instanceof Map.Entry) || !k.a(((Map.Entry) other).getKey(), getKey()) || !k.a(((Map.Entry) other).getValue(), getValue())) {
            return false;
        }
        return true;
    }

    @NotNull
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getKey());
        sb.append('=');
        sb.append(getValue());
        return sb.toString();
    }
}
