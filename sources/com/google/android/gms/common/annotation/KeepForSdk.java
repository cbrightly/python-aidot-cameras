package com.google.android.gms.common.annotation;

import com.google.errorprone.annotations.Keep;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Keep
@Documented
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public @interface KeepForSdk {
}
