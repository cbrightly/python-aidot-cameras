package com.bumptech.glide.load.engine;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.f;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GlideException extends Exception {
    private static final StackTraceElement[] c = new StackTraceElement[0];
    private static final long serialVersionUID = 1;
    private final List<Throwable> causes;
    private Class<?> dataClass;
    private com.bumptech.glide.load.a dataSource;
    private String detailMessage;
    @Nullable
    private Exception exception;
    private f key;

    public GlideException(String message) {
        this(message, (List<Throwable>) Collections.emptyList());
    }

    public GlideException(String detailMessage2, Throwable cause) {
        this(detailMessage2, (List<Throwable>) Collections.singletonList(cause));
    }

    public GlideException(String detailMessage2, List<Throwable> causes2) {
        this.detailMessage = detailMessage2;
        setStackTrace(c);
        this.causes = causes2;
    }

    /* access modifiers changed from: package-private */
    public void setLoggingDetails(f key2, com.bumptech.glide.load.a dataSource2) {
        setLoggingDetails(key2, dataSource2, (Class<?>) null);
    }

    /* access modifiers changed from: package-private */
    public void setLoggingDetails(f key2, com.bumptech.glide.load.a dataSource2, Class<?> dataClass2) {
        this.key = key2;
        this.dataSource = dataSource2;
        this.dataClass = dataClass2;
    }

    public void setOrigin(@Nullable Exception exception2) {
        this.exception = exception2;
    }

    @Nullable
    public Exception getOrigin() {
        return this.exception;
    }

    public Throwable fillInStackTrace() {
        return this;
    }

    public List<Throwable> getCauses() {
        return this.causes;
    }

    public List<Throwable> getRootCauses() {
        List<Throwable> rootCauses = new ArrayList<>();
        a(this, rootCauses);
        return rootCauses;
    }

    public void logRootCauses(String tag) {
        List<Throwable> causes2 = getRootCauses();
        int size = causes2.size();
        for (int i = 0; i < size; i++) {
            Log.i(tag, "Root cause (" + (i + 1) + " of " + size + ")", causes2.get(i));
        }
    }

    private void a(Throwable throwable, List<Throwable> rootCauses) {
        if (throwable instanceof GlideException) {
            for (Throwable t : ((GlideException) throwable).getCauses()) {
                a(t, rootCauses);
            }
            return;
        }
        rootCauses.add(throwable);
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream err) {
        e(err);
    }

    public void printStackTrace(PrintWriter err) {
        e(err);
    }

    private void e(Appendable appendable) {
        d(this, appendable);
        b(getCauses(), new a(appendable));
    }

    public String getMessage() {
        String str;
        String str2;
        StringBuilder sb = new StringBuilder(71);
        sb.append(this.detailMessage);
        String str3 = "";
        if (this.dataClass != null) {
            str = ", " + this.dataClass;
        } else {
            str = str3;
        }
        sb.append(str);
        if (this.dataSource != null) {
            str2 = ", " + this.dataSource;
        } else {
            str2 = str3;
        }
        sb.append(str2);
        if (this.key != null) {
            str3 = ", " + this.key;
        }
        StringBuilder result = sb.append(str3);
        List<Throwable> rootCauses = getRootCauses();
        if (rootCauses.isEmpty()) {
            return result.toString();
        }
        if (rootCauses.size() == 1) {
            result.append("\nThere was 1 root cause:");
        } else {
            result.append("\nThere were ");
            result.append(rootCauses.size());
            result.append(" root causes:");
        }
        for (Throwable cause : rootCauses) {
            result.append(10);
            result.append(cause.getClass().getName());
            result.append('(');
            result.append(cause.getMessage());
            result.append(')');
        }
        result.append("\n call GlideException#logRootCauses(String) for more detail");
        return result.toString();
    }

    private static void d(Throwable t, Appendable appendable) {
        try {
            appendable.append(t.getClass().toString()).append(": ").append(t.getMessage()).append(10);
        } catch (IOException e) {
            throw new RuntimeException(t);
        }
    }

    private static void b(List<Throwable> causes2, Appendable appendable) {
        try {
            c(causes2, appendable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void c(List<Throwable> causes2, Appendable appendable) {
        int size = causes2.size();
        for (int i = 0; i < size; i++) {
            appendable.append("Cause (").append(String.valueOf(i + 1)).append(" of ").append(String.valueOf(size)).append("): ");
            Throwable cause = causes2.get(i);
            if (cause instanceof GlideException) {
                ((GlideException) cause).e(appendable);
            } else {
                d(cause, appendable);
            }
        }
    }

    public static final class a implements Appendable {
        private final Appendable c;
        private boolean d = true;

        a(Appendable appendable) {
            this.c = appendable;
        }

        public Appendable append(char c2) {
            boolean z = false;
            if (this.d) {
                this.d = false;
                this.c.append(JustifyTextView.TWO_CHINESE_BLANK);
            }
            if (c2 == 10) {
                z = true;
            }
            this.d = z;
            this.c.append(c2);
            return this;
        }

        public Appendable append(@Nullable CharSequence charSequence) {
            CharSequence charSequence2 = a(charSequence);
            return append(charSequence2, 0, charSequence2.length());
        }

        public Appendable append(@Nullable CharSequence charSequence, int start, int end) {
            CharSequence charSequence2 = a(charSequence);
            boolean z = false;
            if (this.d) {
                this.d = false;
                this.c.append(JustifyTextView.TWO_CHINESE_BLANK);
            }
            if (charSequence2.length() > 0 && charSequence2.charAt(end - 1) == 10) {
                z = true;
            }
            this.d = z;
            this.c.append(charSequence2, start, end);
            return this;
        }

        @NonNull
        private CharSequence a(@Nullable CharSequence sequence) {
            if (sequence == null) {
                return "";
            }
            return sequence;
        }
    }
}
