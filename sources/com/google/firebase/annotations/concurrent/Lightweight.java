package com.google.firebase.annotations.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
public @interface Lightweight {
}
