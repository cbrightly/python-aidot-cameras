package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import java.util.Iterator;

public class AlignHorizontallyReference extends HelperReference {
    private float mBias = 0.5f;

    public AlignHorizontallyReference(State state) {
        super(state, State.Helper.ALIGN_VERTICALLY);
    }

    public void apply() {
        Iterator<Object> it = this.mReferences.iterator();
        while (it.hasNext()) {
            ConstraintReference reference = this.mState.constraints(it.next());
            reference.clearHorizontal();
            Object obj = this.mStartToStart;
            if (obj != null) {
                reference.startToStart(obj);
            } else {
                Object obj2 = this.mStartToEnd;
                if (obj2 != null) {
                    reference.startToEnd(obj2);
                } else {
                    reference.startToStart(State.PARENT);
                }
            }
            Object obj3 = this.mEndToStart;
            if (obj3 != null) {
                reference.endToStart(obj3);
            } else {
                Object obj4 = this.mEndToEnd;
                if (obj4 != null) {
                    reference.endToEnd(obj4);
                } else {
                    reference.endToEnd(State.PARENT);
                }
            }
            float f = this.mBias;
            if (f != 0.5f) {
                reference.horizontalBias(f);
            }
        }
    }
}
