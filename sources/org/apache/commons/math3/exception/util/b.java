package org.apache.commons.math3.exception.util;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* compiled from: ExceptionContext */
public class b implements Serializable {
    private static final long serialVersionUID = -6024911025449780478L;
    private Map<String, Object> context = new HashMap();
    private List<Object[]> msgArguments = new ArrayList();
    private List<c> msgPatterns = new ArrayList();
    private Throwable throwable;

    public b(Throwable throwable2) {
        this.throwable = throwable2;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public void addMessage(c pattern, Object... arguments) {
        this.msgPatterns.add(pattern);
        this.msgArguments.add(a.a(arguments));
    }

    public void setValue(String key, Object value) {
        this.context.put(key, value);
    }

    public Object getValue(String key) {
        return this.context.get(key);
    }

    public Set<String> getKeys() {
        return this.context.keySet();
    }

    public String getMessage() {
        return getMessage(Locale.US);
    }

    public String getLocalizedMessage() {
        return getMessage(Locale.getDefault());
    }

    public String getMessage(Locale locale) {
        return a(locale, ": ");
    }

    public String getMessage(Locale locale, String separator) {
        return a(locale, separator);
    }

    private String a(Locale locale, String separator) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        int len = this.msgPatterns.size();
        for (int i = 0; i < len; i++) {
            sb.append(new MessageFormat(this.msgPatterns.get(i).getLocalizedString(locale), locale).format(this.msgArguments.get(i)));
            count++;
            if (count < len) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    private void writeObject(ObjectOutputStream out) {
        out.writeObject(this.throwable);
        f(out);
        e(out);
    }

    private void readObject(ObjectInputStream in) {
        this.throwable = (Throwable) in.readObject();
        c(in);
        b(in);
    }

    private void f(ObjectOutputStream out) {
        int len = this.msgPatterns.size();
        out.writeInt(len);
        for (int i = 0; i < len; i++) {
            out.writeObject(this.msgPatterns.get(i));
            Object[] args = this.msgArguments.get(i);
            int aLen = args.length;
            out.writeInt(aLen);
            for (int j = 0; j < aLen; j++) {
                if (args[j] instanceof Serializable) {
                    out.writeObject(args[j]);
                } else {
                    out.writeObject(d(args[j]));
                }
            }
        }
    }

    private void c(ObjectInputStream in) {
        int len = in.readInt();
        this.msgPatterns = new ArrayList(len);
        this.msgArguments = new ArrayList(len);
        for (int i = 0; i < len; i++) {
            this.msgPatterns.add((c) in.readObject());
            int aLen = in.readInt();
            Object[] args = new Object[aLen];
            for (int j = 0; j < aLen; j++) {
                args[j] = in.readObject();
            }
            this.msgArguments.add(args);
        }
    }

    private void e(ObjectOutputStream out) {
        out.writeInt(this.context.size());
        for (Map.Entry<String, Object> entry : this.context.entrySet()) {
            out.writeObject(entry.getKey());
            Object value = entry.getValue();
            if (value instanceof Serializable) {
                out.writeObject(value);
            } else {
                out.writeObject(d(value));
            }
        }
    }

    private void b(ObjectInputStream in) {
        int len = in.readInt();
        this.context = new HashMap();
        for (int i = 0; i < len; i++) {
            Object value = in.readObject();
            this.context.put((String) in.readObject(), value);
        }
    }

    private String d(Object obj) {
        return "[Object could not be serialized: " + obj.getClass().getName() + "]";
    }
}
