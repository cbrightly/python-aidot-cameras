package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.value.a;
import java.util.Arrays;
import java.util.List;

/* compiled from: BaseAnimatableValue */
public abstract class n<V, O> implements m<V, O> {
    final List<a<V>> a;

    n(List<a<V>> keyframes) {
        this.a = keyframes;
    }

    public List<a<V>> k() {
        return this.a;
    }

    public boolean i() {
        return this.a.isEmpty() || (this.a.size() == 1 && this.a.get(0).i());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.a.isEmpty()) {
            sb.append("values=");
            sb.append(Arrays.toString(this.a.toArray()));
        }
        return sb.toString();
    }
}
