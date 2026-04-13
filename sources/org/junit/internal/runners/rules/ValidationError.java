package org.junit.internal.runners.rules;

import java.lang.annotation.Annotation;
import org.junit.runners.model.a;

public class ValidationError extends Exception {
    public ValidationError(a<?> member, Class<? extends Annotation> annotation, String suffix) {
        super(String.format("The @%s '%s' %s", new Object[]{annotation.getSimpleName(), member.a(), suffix}));
    }
}
