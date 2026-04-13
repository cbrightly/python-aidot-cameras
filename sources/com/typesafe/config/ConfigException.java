package com.typesafe.config;

import com.typesafe.config.impl.g;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

public abstract class ConfigException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 1;
    private final transient f origin;

    protected ConfigException(f origin2, String message, Throwable cause) {
        super(origin2.a() + ": " + message, cause);
        this.origin = origin2;
    }

    protected ConfigException(f origin2, String message) {
        this(origin2.a() + ": " + message, (Throwable) null);
    }

    protected ConfigException(String message, Throwable cause) {
        super(message, cause);
        this.origin = null;
    }

    protected ConfigException(String message) {
        this(message, (Throwable) null);
    }

    public f origin() {
        return this.origin;
    }

    private void writeObject(ObjectOutputStream out) {
        out.defaultWriteObject();
        g.j(out, this.origin);
    }

    private void readObject(ObjectInputStream in) {
        in.defaultReadObject();
        f origin2 = g.e(in);
        try {
            Field f = ConfigException.class.getDeclaredField("origin");
            f.setAccessible(true);
            try {
                f.set(this, origin2);
            } catch (IllegalArgumentException e) {
                throw new IOException("unable to set origin field", e);
            } catch (IllegalAccessException e2) {
                throw new IOException("unable to set origin field", e2);
            }
        } catch (NoSuchFieldException e3) {
            throw new IOException("ConfigException has no origin field?", e3);
        } catch (SecurityException e4) {
            throw new IOException("unable to fill out origin field in ConfigException", e4);
        }
    }

    public static class WrongType extends ConfigException {
        private static final long serialVersionUID = 1;

        public WrongType(f origin, String path, String expected, String actual, Throwable cause) {
            super(origin, path + " has type " + actual + " rather than " + expected, cause);
        }

        public WrongType(f origin, String path, String expected, String actual) {
            this(origin, path, expected, actual, (Throwable) null);
        }

        public WrongType(f origin, String message, Throwable cause) {
            super(origin, message, cause);
        }

        public WrongType(f origin, String message) {
            super(origin, message, (Throwable) null);
        }
    }

    public static class Missing extends ConfigException {
        private static final long serialVersionUID = 1;

        public Missing(String path, Throwable cause) {
            super("No configuration setting found for key '" + path + "'", cause);
        }

        public Missing(String path) {
            this(path, (Throwable) null);
        }

        protected Missing(f origin, String message, Throwable cause) {
            super(origin, message, cause);
        }

        protected Missing(f origin, String message) {
            this(origin, message, (Throwable) null);
        }
    }

    public static class Null extends Missing {
        private static final long serialVersionUID = 1;

        private static String a(String path, String expected) {
            if (expected != null) {
                return "Configuration key '" + path + "' is set to null but expected " + expected;
            }
            return "Configuration key '" + path + "' is null";
        }

        public Null(f origin, String path, String expected, Throwable cause) {
            super(origin, a(path, expected), cause);
        }

        public Null(f origin, String path, String expected) {
            this(origin, path, expected, (Throwable) null);
        }
    }

    public static class BadValue extends ConfigException {
        private static final long serialVersionUID = 1;

        public BadValue(f origin, String path, String message, Throwable cause) {
            super(origin, "Invalid value at '" + path + "': " + message, cause);
        }

        public BadValue(f origin, String path, String message) {
            this(origin, path, message, (Throwable) null);
        }

        public BadValue(String path, String message, Throwable cause) {
            super("Invalid value at '" + path + "': " + message, cause);
        }

        public BadValue(String path, String message) {
            this(path, message, (Throwable) null);
        }
    }

    public static class BadPath extends ConfigException {
        private static final long serialVersionUID = 1;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public BadPath(com.typesafe.config.f r3, java.lang.String r4, java.lang.String r5, java.lang.Throwable r6) {
            /*
                r2 = this;
                if (r4 == 0) goto L_0x001c
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Invalid path '"
                r0.append(r1)
                r0.append(r4)
                java.lang.String r1 = "': "
                r0.append(r1)
                r0.append(r5)
                java.lang.String r0 = r0.toString()
                goto L_0x001d
            L_0x001c:
                r0 = r5
            L_0x001d:
                r2.<init>(r3, r0, r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.typesafe.config.ConfigException.BadPath.<init>(com.typesafe.config.f, java.lang.String, java.lang.String, java.lang.Throwable):void");
        }

        public BadPath(f origin, String path, String message) {
            this(origin, path, message, (Throwable) null);
        }

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public BadPath(java.lang.String r3, java.lang.String r4, java.lang.Throwable r5) {
            /*
                r2 = this;
                if (r3 == 0) goto L_0x001c
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r1 = "Invalid path '"
                r0.append(r1)
                r0.append(r3)
                java.lang.String r1 = "': "
                r0.append(r1)
                r0.append(r4)
                java.lang.String r0 = r0.toString()
                goto L_0x001d
            L_0x001c:
                r0 = r4
            L_0x001d:
                r2.<init>((java.lang.String) r0, (java.lang.Throwable) r5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.typesafe.config.ConfigException.BadPath.<init>(java.lang.String, java.lang.String, java.lang.Throwable):void");
        }

        public BadPath(String path, String message) {
            this(path, message, (Throwable) null);
        }

        public BadPath(f origin, String message) {
            this(origin, (String) null, message);
        }
    }

    public static class BugOrBroken extends ConfigException {
        private static final long serialVersionUID = 1;

        public BugOrBroken(String message, Throwable cause) {
            super(message, cause);
        }

        public BugOrBroken(String message) {
            this(message, (Throwable) null);
        }
    }

    public static class IO extends ConfigException {
        private static final long serialVersionUID = 1;

        public IO(f origin, String message, Throwable cause) {
            super(origin, message, cause);
        }

        public IO(f origin, String message) {
            this(origin, message, (Throwable) null);
        }
    }

    public static class Parse extends ConfigException {
        private static final long serialVersionUID = 1;

        public Parse(f origin, String message, Throwable cause) {
            super(origin, message, cause);
        }

        public Parse(f origin, String message) {
            this(origin, message, (Throwable) null);
        }
    }

    public static class UnresolvedSubstitution extends Parse {
        private static final long serialVersionUID = 1;

        public UnresolvedSubstitution(f origin, String detail, Throwable cause) {
            super(origin, "Could not resolve substitution to a value: " + detail, cause);
        }

        public UnresolvedSubstitution(f origin, String detail) {
            this(origin, detail, (Throwable) null);
        }
    }

    public static class NotResolved extends BugOrBroken {
        private static final long serialVersionUID = 1;

        public NotResolved(String message, Throwable cause) {
            super(message, cause);
        }

        public NotResolved(String message) {
            this(message, (Throwable) null);
        }
    }

    public static class a {
        private final String a;
        private final f b;
        private final String c;

        public a(String path, f origin, String problem) {
            this.a = path;
            this.b = origin;
            this.c = problem;
        }

        public String b() {
            return this.a;
        }

        public f a() {
            return this.b;
        }

        public String c() {
            return this.c;
        }

        public String toString() {
            return "ValidationProblem(" + this.a + "," + this.b + "," + this.c + ")";
        }
    }

    public static class ValidationFailed extends ConfigException {
        private static final long serialVersionUID = 1;
        private final Iterable<a> problems;

        public ValidationFailed(Iterable<a> problems2) {
            super(a(problems2), (Throwable) null);
            this.problems = problems2;
        }

        public Iterable<a> problems() {
            return this.problems;
        }

        private static String a(Iterable<a> problems2) {
            StringBuilder sb = new StringBuilder();
            for (a p : problems2) {
                sb.append(p.a().a());
                sb.append(": ");
                sb.append(p.b());
                sb.append(": ");
                sb.append(p.c());
                sb.append(", ");
            }
            if (sb.length() != 0) {
                sb.setLength(sb.length() - 2);
                return sb.toString();
            }
            throw new BugOrBroken("ValidationFailed must have a non-empty list of problems");
        }
    }

    public static class BadBean extends BugOrBroken {
        private static final long serialVersionUID = 1;

        public BadBean(String message, Throwable cause) {
            super(message, cause);
        }

        public BadBean(String message) {
            this(message, (Throwable) null);
        }
    }

    public static class Generic extends ConfigException {
        private static final long serialVersionUID = 1;

        public Generic(String message, Throwable cause) {
            super(message, cause);
        }

        public Generic(String message) {
            this(message, (Throwable) null);
        }
    }
}
