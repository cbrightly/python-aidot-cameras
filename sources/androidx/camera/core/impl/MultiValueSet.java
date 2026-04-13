package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class MultiValueSet<C> {
    private Set<C> mSet = new HashSet();

    @NonNull
    public abstract MultiValueSet<C> clone();

    public void addAll(@NonNull List<C> value) {
        this.mSet.addAll(value);
    }

    @NonNull
    public List<C> getAllItems() {
        return Collections.unmodifiableList(new ArrayList(this.mSet));
    }
}
