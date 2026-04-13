package com.didichuxing.doraemonkit.widget.bravh.animation;

import android.animation.Animator;
import android.view.View;
import org.jetbrains.annotations.NotNull;

/* compiled from: BaseAnimation.kt */
public interface BaseAnimation {
    @NotNull
    Animator[] animators(@NotNull View view);
}
