package org.apache.commons.lang3.exception;

import java.util.function.Predicate;
import org.apache.commons.lang3.tuple.b;

/* compiled from: lambda */
public final /* synthetic */ class a implements Predicate {
    public final /* synthetic */ String a;

    public /* synthetic */ a(String str) {
        this.a = str;
    }

    public final boolean test(Object obj) {
        return org.apache.commons.lang3.a.a(this.a, (CharSequence) ((b) obj).getKey());
    }
}
