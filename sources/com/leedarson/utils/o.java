package com.leedarson.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;

/* compiled from: ViewAnimUtils */
public class o {
    static AnimatorSet a;
    static AnimatorSet b;
    public static ChangeQuickRedirect changeQuickRedirect;

    /* compiled from: ViewAnimUtils */
    public interface b {
        void a();

        void onAnimationEnd();
    }

    public static void d(View view, long duration) {
        Object[] objArr = {view, new Long(duration)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11537, new Class[]{View.class, Long.TYPE}, Void.TYPE).isSupported) {
            view.setVisibility(0);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f, 1.0f});
            objectAnimator.setDuration(duration);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(objectAnimator);
            animatorSet.start();
        }
    }

    public static void c(View view, long duration, float oldScale, float scale) {
        Object[] objArr = {view, new Long(duration), new Float(oldScale), new Float(scale)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11541, new Class[]{View.class, Long.TYPE, cls, cls}, Void.TYPE).isSupported) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "scaleX", new float[]{oldScale, scale});
            objectAnimator.setDuration(duration);
            ObjectAnimator objectAnimatory = ObjectAnimator.ofFloat(view, "scaleY", new float[]{oldScale, scale});
            objectAnimatory.setDuration(duration);
            AnimatorSet animatorSet = new AnimatorSet();
            a = animatorSet;
            animatorSet.playTogether(new Animator[]{objectAnimator, objectAnimatory});
            a.start();
        }
    }

    public static void b(View view, int i, float f, float f2, float oldTransX, float f3, float oldTransY, float transY, b animationEnd) {
        Object[] objArr = {view, new Integer(i), new Float(f), new Float(f2), new Float(oldTransX), new Float(f3), new Float(oldTransY), new Float(transY), animationEnd};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Float.TYPE;
        if (!PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11542, new Class[]{View.class, Integer.TYPE, cls, cls, cls, cls, cls, cls, b.class}, Void.TYPE).isSupported) {
            View view2 = view;
            float oldScale = f;
            int duration = i;
            float scale = f2;
            float transX = f3;
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view2, "scaleX", new float[]{oldScale, scale});
            objectAnimator.setDuration((long) duration);
            ObjectAnimator objectAnimatory = ObjectAnimator.ofFloat(view2, "scaleY", new float[]{oldScale, scale});
            objectAnimatory.setDuration((long) duration);
            View view3 = view;
            ObjectAnimator transXAnima = ObjectAnimator.ofFloat(view3, "translationX", new float[]{oldTransX, transX});
            float f4 = transX;
            transXAnima.setDuration((long) duration);
            ObjectAnimator transYAnima = ObjectAnimator.ofFloat(view3, "translationY", new float[]{oldTransY, transY});
            transYAnima.setDuration((long) duration);
            AnimatorSet animatorSet = new AnimatorSet();
            b = animatorSet;
            animatorSet.addListener(new a(animationEnd));
            b.playTogether(new Animator[]{objectAnimator, objectAnimatory, transXAnima, transYAnima});
            b.start();
        }
    }

    /* compiled from: ViewAnimUtils */
    public class a implements Animator.AnimatorListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ b c;

        a(b bVar) {
            this.c = bVar;
        }

        public void onAnimationStart(Animator animator) {
            b bVar;
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 11546, new Class[]{Animator.class}, Void.TYPE).isSupported && (bVar = this.c) != null) {
                bVar.a();
            }
        }

        public void onAnimationEnd(Animator animator) {
            b bVar;
            if (!PatchProxy.proxy(new Object[]{animator}, this, changeQuickRedirect, false, 11547, new Class[]{Animator.class}, Void.TYPE).isSupported && (bVar = this.c) != null) {
                bVar.onAnimationEnd();
            }
        }

        public void onAnimationCancel(Animator animation) {
        }

        public void onAnimationRepeat(Animator animation) {
        }
    }

    public static void a() {
        if (!PatchProxy.proxy(new Object[0], (Object) null, changeQuickRedirect, true, 11543, new Class[0], Void.TYPE).isSupported) {
            AnimatorSet animatorSet = a;
            if (animatorSet != null && animatorSet.isStarted()) {
                a.end();
            }
            AnimatorSet animatorSet2 = b;
            if (animatorSet2 != null && animatorSet2.isStarted()) {
                b.end();
            }
        }
    }
}
