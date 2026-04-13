package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import java.util.Iterator;

public class AlignVerticallyReference extends HelperReference {
    private float mBias = 0.5f;

    public AlignVerticallyReference(State state) {
        super(state, State.Helper.ALIGN_VERTICALLY);
    }

    public void apply() {
        Iterator<Object> it = this.mReferences.iterator();
        while (it.hasNext()) {
            ConstraintReference reference = this.mState.constraints(it.next());
            reference.clearVertical();
            Object obj = this.mTopToTop;
            if (obj != null) {
                reference.topToTop(obj);
            } else {
                Object obj2 = this.mTopToBottom;
                if (obj2 != null) {
                    reference.topToBottom(obj2);
                } else {
                    reference.topToTop(State.PARENT);
                }
            }
            Object obj3 = this.mBottomToTop;
            if (obj3 != null) {
                reference.bottomToTop(obj3);
            } else {
                Object obj4 = this.mBottomToBottom;
                if (obj4 != null) {
                    reference.bottomToBottom(obj4);
                } else {
                    reference.bottomToBottom(State.PARENT);
                }
            }
            float f = this.mBias;
            if (f != 0.5f) {
                reference.verticalBias(f);
            }
        }
    }
}
