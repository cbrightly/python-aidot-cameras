package com.lzf.easyfloat.interfaces;

import android.view.MotionEvent;
import android.view.View;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.functions.q;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FloatCallbacks.kt */
public final class FloatCallbacks {
    public Builder builder;

    @NotNull
    public final Builder getBuilder() {
        Builder builder2 = this.builder;
        if (builder2 != null) {
            return builder2;
        }
        k.t("builder");
        return null;
    }

    public final void setBuilder(@NotNull Builder builder2) {
        k.e(builder2, "<set-?>");
        this.builder = builder2;
    }

    public final void registerListener(@NotNull l<? super Builder, x> builder2) {
        k.e(builder2, "builder");
        Builder builder3 = new Builder(this);
        builder2.invoke(builder3);
        setBuilder(builder3);
    }

    /* compiled from: FloatCallbacks.kt */
    public final class Builder {
        @Nullable
        private q<? super Boolean, ? super String, ? super View, x> createdResult;
        @Nullable
        private a<x> dismiss;
        @Nullable
        private p<? super View, ? super MotionEvent, x> drag;
        @Nullable
        private l<? super View, x> dragEnd;
        @Nullable
        private l<? super View, x> hide;
        @Nullable
        private l<? super View, x> show;
        final /* synthetic */ FloatCallbacks this$0;
        @Nullable
        private p<? super View, ? super MotionEvent, x> touchEvent;

        public Builder(FloatCallbacks this$02) {
            k.e(this$02, "this$0");
            this.this$0 = this$02;
        }

        @Nullable
        public final q<Boolean, String, View, x> getCreatedResult$easyfloat_release() {
            return this.createdResult;
        }

        public final void setCreatedResult$easyfloat_release(@Nullable q<? super Boolean, ? super String, ? super View, x> qVar) {
            this.createdResult = qVar;
        }

        @Nullable
        public final l<View, x> getShow$easyfloat_release() {
            return this.show;
        }

        public final void setShow$easyfloat_release(@Nullable l<? super View, x> lVar) {
            this.show = lVar;
        }

        @Nullable
        public final l<View, x> getHide$easyfloat_release() {
            return this.hide;
        }

        public final void setHide$easyfloat_release(@Nullable l<? super View, x> lVar) {
            this.hide = lVar;
        }

        @Nullable
        public final a<x> getDismiss$easyfloat_release() {
            return this.dismiss;
        }

        public final void setDismiss$easyfloat_release(@Nullable a<x> aVar) {
            this.dismiss = aVar;
        }

        @Nullable
        public final p<View, MotionEvent, x> getTouchEvent$easyfloat_release() {
            return this.touchEvent;
        }

        public final void setTouchEvent$easyfloat_release(@Nullable p<? super View, ? super MotionEvent, x> pVar) {
            this.touchEvent = pVar;
        }

        @Nullable
        public final p<View, MotionEvent, x> getDrag$easyfloat_release() {
            return this.drag;
        }

        public final void setDrag$easyfloat_release(@Nullable p<? super View, ? super MotionEvent, x> pVar) {
            this.drag = pVar;
        }

        @Nullable
        public final l<View, x> getDragEnd$easyfloat_release() {
            return this.dragEnd;
        }

        public final void setDragEnd$easyfloat_release(@Nullable l<? super View, x> lVar) {
            this.dragEnd = lVar;
        }

        public final void createResult(@NotNull q<? super Boolean, ? super String, ? super View, x> action) {
            k.e(action, "action");
            this.createdResult = action;
        }

        public final void show(@NotNull l<? super View, x> action) {
            k.e(action, "action");
            this.show = action;
        }

        public final void hide(@NotNull l<? super View, x> action) {
            k.e(action, "action");
            this.hide = action;
        }

        public final void dismiss(@NotNull a<x> action) {
            k.e(action, "action");
            this.dismiss = action;
        }

        public final void touchEvent(@NotNull p<? super View, ? super MotionEvent, x> action) {
            k.e(action, "action");
            this.touchEvent = action;
        }

        public final void drag(@NotNull p<? super View, ? super MotionEvent, x> action) {
            k.e(action, "action");
            this.drag = action;
        }

        public final void dragEnd(@NotNull l<? super View, x> action) {
            k.e(action, "action");
            this.dragEnd = action;
        }
    }
}
