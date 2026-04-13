package org.apache.http.impl.cookie;

@Deprecated
/* compiled from: BestMatchSpec */
public class k extends q {
    public k(String[] datepatterns, boolean oneHeader) {
        super(datepatterns, oneHeader);
    }

    public k() {
        this((String[]) null, false);
    }

    public String toString() {
        return "best-match";
    }
}
