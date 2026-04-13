package io.reactivex.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class CompositeException extends RuntimeException {
    private static final long serialVersionUID = 3026362227162912146L;
    private Throwable cause;
    private final List<Throwable> exceptions;
    private final String message;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public CompositeException(Throwable... exceptions2) {
        this((Iterable<? extends Throwable>) exceptions2 == null ? Collections.singletonList(new NullPointerException("exceptions was null")) : Arrays.asList(exceptions2));
    }

    public CompositeException(Iterable<? extends Throwable> errors) {
        Set<Throwable> deDupedExceptions = new LinkedHashSet<>();
        List<Throwable> localExceptions = new ArrayList<>();
        if (errors != null) {
            for (Throwable ex : errors) {
                if (ex instanceof CompositeException) {
                    deDupedExceptions.addAll(((CompositeException) ex).getExceptions());
                } else if (ex != null) {
                    deDupedExceptions.add(ex);
                } else {
                    deDupedExceptions.add(new NullPointerException("Throwable was null!"));
                }
            }
        } else {
            deDupedExceptions.add(new NullPointerException("errors was null"));
        }
        if (!deDupedExceptions.isEmpty()) {
            localExceptions.addAll(deDupedExceptions);
            List<T> unmodifiableList = Collections.unmodifiableList(localExceptions);
            this.exceptions = unmodifiableList;
            this.message = unmodifiableList.size() + " exceptions occurred. ";
            return;
        }
        throw new IllegalArgumentException("errors is empty");
    }

    public List<Throwable> getExceptions() {
        return this.exceptions;
    }

    public String getMessage() {
        return this.message;
    }

    public synchronized Throwable getCause() {
        if (this.cause == null) {
            Throwable localCause = new CompositeExceptionCausalChain();
            Set<Throwable> seenCauses = new HashSet<>();
            Throwable chain = localCause;
            Iterator<Throwable> it = this.exceptions.iterator();
            while (it.hasNext()) {
                Throwable e = it.next();
                if (!seenCauses.contains(e)) {
                    seenCauses.add(e);
                    for (Throwable child : b(e)) {
                        if (seenCauses.contains(child)) {
                            e = new RuntimeException("Duplicate found in causal chain so cropping to prevent loop ...");
                        } else {
                            seenCauses.add(child);
                        }
                    }
                    try {
                        chain.initCause(e);
                    } catch (Throwable th) {
                    }
                    chain = getRootCause(chain);
                }
            }
            this.cause = localCause;
        }
        return this.cause;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream s) {
        c(new b(s));
    }

    public void printStackTrace(PrintWriter s) {
        c(new c(s));
    }

    private void c(a s) {
        StringBuilder b2 = new StringBuilder(128);
        b2.append(this);
        b2.append(10);
        for (StackTraceElement myStackElement : getStackTrace()) {
            b2.append("\tat ");
            b2.append(myStackElement);
            b2.append(10);
        }
        int i = 1;
        for (Throwable ex : this.exceptions) {
            b2.append("  ComposedException ");
            b2.append(i);
            b2.append(" :\n");
            a(b2, ex, "\t");
            i++;
        }
        s.a(b2.toString());
    }

    private void a(StringBuilder b2, Throwable ex, String prefix) {
        b2.append(prefix);
        b2.append(ex);
        b2.append(10);
        for (StackTraceElement stackElement : ex.getStackTrace()) {
            b2.append("\t\tat ");
            b2.append(stackElement);
            b2.append(10);
        }
        if (ex.getCause() != null) {
            b2.append("\tCaused by: ");
            a(b2, ex.getCause(), "");
        }
    }

    public static abstract class a {
        /* access modifiers changed from: package-private */
        public abstract void a(Object obj);

        a() {
        }
    }

    public static final class b extends a {
        private final PrintStream a;

        b(PrintStream printStream) {
            this.a = printStream;
        }

        /* access modifiers changed from: package-private */
        public void a(Object o) {
            this.a.println(o);
        }
    }

    public static final class c extends a {
        private final PrintWriter a;

        c(PrintWriter printWriter) {
            this.a = printWriter;
        }

        /* access modifiers changed from: package-private */
        public void a(Object o) {
            this.a.println(o);
        }
    }

    public static final class CompositeExceptionCausalChain extends RuntimeException {
        static final String MESSAGE = "Chain of Causes for CompositeException In Order Received =>";
        private static final long serialVersionUID = 3875212506787802066L;

        CompositeExceptionCausalChain() {
        }

        public String getMessage() {
            return MESSAGE;
        }
    }

    private List<Throwable> b(Throwable ex) {
        List<Throwable> list = new ArrayList<>();
        Throwable root = ex.getCause();
        if (root == null || root == ex) {
            return list;
        }
        while (true) {
            list.add(root);
            Throwable cause2 = root.getCause();
            if (cause2 == null || cause2 == root) {
                return list;
            }
            root = cause2;
        }
        return list;
    }

    public int size() {
        return this.exceptions.size();
    }

    /* access modifiers changed from: package-private */
    public Throwable getRootCause(Throwable e) {
        Throwable root = e.getCause();
        if (root == null || e == root) {
            return e;
        }
        while (true) {
            Throwable cause2 = root.getCause();
            if (cause2 == null || cause2 == root) {
                return root;
            }
            root = cause2;
        }
        return root;
    }
}
