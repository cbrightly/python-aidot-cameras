package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.State;
import java.util.Iterator;

public class VerticalChainReference extends ChainReference {
    public VerticalChainReference(State state) {
        super(state, State.Helper.VERTICAL_CHAIN);
    }

    public void apply() {
        ConstraintReference first = null;
        ConstraintReference previous = null;
        Iterator<Object> it = this.mReferences.iterator();
        while (it.hasNext()) {
            this.mState.constraints(it.next()).clearVertical();
        }
        Iterator<Object> it2 = this.mReferences.iterator();
        while (it2.hasNext()) {
            ConstraintReference reference = this.mState.constraints(it2.next());
            if (first == null) {
                first = reference;
                Object obj = this.mTopToTop;
                if (obj != null) {
                    first.topToTop(obj);
                } else {
                    Object obj2 = this.mTopToBottom;
                    if (obj2 != null) {
                        first.topToBottom(obj2);
                    } else {
                        first.topToTop(State.PARENT);
                    }
                }
            }
            if (previous != null) {
                previous.bottomToTop(reference.getKey());
                reference.topToBottom(previous.getKey());
            }
            previous = reference;
        }
        if (previous != null) {
            Object obj3 = this.mBottomToTop;
            if (obj3 != null) {
                previous.bottomToTop(obj3);
            } else {
                Object obj4 = this.mBottomToBottom;
                if (obj4 != null) {
                    previous.bottomToBottom(obj4);
                } else {
                    previous.bottomToBottom(State.PARENT);
                }
            }
        }
        if (first != null) {
            float f = this.mBias;
            if (f != 0.5f) {
                first.verticalBias(f);
            }
            switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$state$State$Chain[this.mStyle.ordinal()]) {
                case 1:
                    first.setVerticalChainStyle(0);
                    return;
                case 2:
                    first.setVerticalChainStyle(1);
                    return;
                case 3:
                    first.setVerticalChainStyle(2);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: androidx.constraintlayout.core.state.helpers.VerticalChainReference$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$state$State$Chain;

        static {
            int[] iArr = new int[State.Chain.values().length];
            $SwitchMap$androidx$constraintlayout$core$state$State$Chain = iArr;
            try {
                iArr[State.Chain.SPREAD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$state$State$Chain[State.Chain.SPREAD_INSIDE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$state$State$Chain[State.Chain.PACKED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }
}
