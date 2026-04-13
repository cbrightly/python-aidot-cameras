package retrofit2;

import java.lang.annotation.Annotation;

/* compiled from: SkipCallbackExecutorImpl */
public final class w implements v {
    private static final v a = new w();

    w() {
    }

    static Annotation[] a(Annotation[] annotations) {
        if (x.l(annotations, v.class)) {
            return annotations;
        }
        Annotation[] newAnnotations = new Annotation[(annotations.length + 1)];
        newAnnotations[0] = a;
        System.arraycopy(annotations, 0, newAnnotations, 1, annotations.length);
        return newAnnotations;
    }

    public Class<? extends Annotation> annotationType() {
        return v.class;
    }

    public boolean equals(Object obj) {
        return obj instanceof v;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "@" + v.class.getName() + "()";
    }
}
