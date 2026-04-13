package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.transition.Transition;

public class TranslationAnimationCreator {
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v0, resolved type: int[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.animation.Animator createAnimation(@androidx.annotation.NonNull android.view.View r17, @androidx.annotation.NonNull androidx.transition.TransitionValues r18, int r19, int r20, float r21, float r22, float r23, float r24, @androidx.annotation.Nullable android.animation.TimeInterpolator r25, @androidx.annotation.NonNull androidx.transition.Transition r26) {
        /*
            r7 = r17
            r8 = r18
            float r9 = r17.getTranslationX()
            float r10 = r17.getTranslationY()
            android.view.View r0 = r8.view
            int r1 = androidx.transition.R.id.transition_position
            java.lang.Object r0 = r0.getTag(r1)
            r11 = r0
            int[] r11 = (int[]) r11
            r0 = 1
            r1 = 0
            if (r11 == 0) goto L_0x002a
            r2 = r11[r1]
            int r2 = r2 - r19
            float r2 = (float) r2
            float r2 = r2 + r9
            r3 = r11[r0]
            int r3 = r3 - r20
            float r3 = (float) r3
            float r3 = r3 + r10
            r12 = r2
            r13 = r3
            goto L_0x002e
        L_0x002a:
            r12 = r21
            r13 = r22
        L_0x002e:
            float r2 = r12 - r9
            int r2 = java.lang.Math.round(r2)
            int r14 = r19 + r2
            float r2 = r13 - r10
            int r2 = java.lang.Math.round(r2)
            int r15 = r20 + r2
            r7.setTranslationX(r12)
            r7.setTranslationY(r13)
            int r2 = (r12 > r23 ? 1 : (r12 == r23 ? 0 : -1))
            if (r2 != 0) goto L_0x004e
            int r2 = (r13 > r24 ? 1 : (r13 == r24 ? 0 : -1))
            if (r2 != 0) goto L_0x004e
            r0 = 0
            return r0
        L_0x004e:
            r2 = 2
            android.animation.PropertyValuesHolder[] r3 = new android.animation.PropertyValuesHolder[r2]
            android.util.Property r4 = android.view.View.TRANSLATION_X
            float[] r5 = new float[r2]
            r5[r1] = r12
            r5[r0] = r23
            android.animation.PropertyValuesHolder r4 = android.animation.PropertyValuesHolder.ofFloat(r4, r5)
            r3[r1] = r4
            android.util.Property r4 = android.view.View.TRANSLATION_Y
            float[] r2 = new float[r2]
            r2[r1] = r13
            r2[r0] = r24
            android.animation.PropertyValuesHolder r1 = android.animation.PropertyValuesHolder.ofFloat(r4, r2)
            r3[r0] = r1
            android.animation.ObjectAnimator r6 = android.animation.ObjectAnimator.ofPropertyValuesHolder(r7, r3)
            androidx.transition.TranslationAnimationCreator$TransitionPositionListener r16 = new androidx.transition.TranslationAnimationCreator$TransitionPositionListener
            android.view.View r2 = r8.view
            r0 = r16
            r1 = r17
            r3 = r14
            r4 = r15
            r5 = r9
            r7 = r6
            r6 = r10
            r0.<init>(r1, r2, r3, r4, r5, r6)
            r1 = r26
            r1.addListener(r0)
            r7.addListener(r0)
            androidx.transition.AnimatorUtils.addPauseListener(r7, r0)
            r2 = r25
            r7.setInterpolator(r2)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.TranslationAnimationCreator.createAnimation(android.view.View, androidx.transition.TransitionValues, int, int, float, float, float, float, android.animation.TimeInterpolator, androidx.transition.Transition):android.animation.Animator");
    }

    public static class TransitionPositionListener extends AnimatorListenerAdapter implements Transition.TransitionListener {
        private final View mMovingView;
        private float mPausedX;
        private float mPausedY;
        private final int mStartX;
        private final int mStartY;
        private final float mTerminalX;
        private final float mTerminalY;
        private int[] mTransitionPosition;
        private final View mViewInHierarchy;

        TransitionPositionListener(View movingView, View viewInHierarchy, int startX, int startY, float terminalX, float terminalY) {
            this.mMovingView = movingView;
            this.mViewInHierarchy = viewInHierarchy;
            this.mStartX = startX - Math.round(movingView.getTranslationX());
            this.mStartY = startY - Math.round(movingView.getTranslationY());
            this.mTerminalX = terminalX;
            this.mTerminalY = terminalY;
            int i = R.id.transition_position;
            int[] iArr = (int[]) viewInHierarchy.getTag(i);
            this.mTransitionPosition = iArr;
            if (iArr != null) {
                viewInHierarchy.setTag(i, (Object) null);
            }
        }

        public void onAnimationCancel(Animator animation) {
            if (this.mTransitionPosition == null) {
                this.mTransitionPosition = new int[2];
            }
            this.mTransitionPosition[0] = Math.round(((float) this.mStartX) + this.mMovingView.getTranslationX());
            this.mTransitionPosition[1] = Math.round(((float) this.mStartY) + this.mMovingView.getTranslationY());
            this.mViewInHierarchy.setTag(R.id.transition_position, this.mTransitionPosition);
        }

        public void onAnimationPause(Animator animator) {
            this.mPausedX = this.mMovingView.getTranslationX();
            this.mPausedY = this.mMovingView.getTranslationY();
            this.mMovingView.setTranslationX(this.mTerminalX);
            this.mMovingView.setTranslationY(this.mTerminalY);
        }

        public void onAnimationResume(Animator animator) {
            this.mMovingView.setTranslationX(this.mPausedX);
            this.mMovingView.setTranslationY(this.mPausedY);
        }

        public void onTransitionStart(@NonNull Transition transition) {
        }

        public void onTransitionEnd(@NonNull Transition transition) {
            this.mMovingView.setTranslationX(this.mTerminalX);
            this.mMovingView.setTranslationY(this.mTerminalY);
            transition.removeListener(this);
        }

        public void onTransitionCancel(@NonNull Transition transition) {
        }

        public void onTransitionPause(@NonNull Transition transition) {
        }

        public void onTransitionResume(@NonNull Transition transition) {
        }
    }

    private TranslationAnimationCreator() {
    }
}
