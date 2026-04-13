package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.State;
import java.util.Iterator;

public class HorizontalChainReference extends ChainReference {
    public HorizontalChainReference(State state) {
        super(state, State.Helper.HORIZONTAL_CHAIN);
    }

    public void apply() {
        ConstraintReference first = null;
        ConstraintReference previous = null;
        Iterator<Object> it = this.mReferences.iterator();
        while (it.hasNext()) {
            this.mState.constraints(it.next()).clearHorizontal();
        }
        Iterator<Object> it2 = this.mReferences.iterator();
        while (it2.hasNext()) {
            ConstraintReference reference = this.mState.constraints(it2.next());
            if (first == null) {
                first = reference;
                Object obj = this.mStartToStart;
                if (obj != null) {
                    first.startToStart(obj).margin(this.mMarginStart);
                } else {
                    Object obj2 = this.mStartToEnd;
                    if (obj2 != null) {
                        first.startToEnd(obj2).margin(this.mMarginStart);
                    } else {
                        Object obj3 = this.mLeftToLeft;
                        if (obj3 != null) {
                            first.startToStart(obj3).margin(this.mMarginLeft);
                        } else {
                            Object obj4 = this.mLeftToRight;
                            if (obj4 != null) {
                                first.startToEnd(obj4).margin(this.mMarginLeft);
                            } else {
                                first.startToStart(State.PARENT);
                            }
                        }
                    }
                }
            }
            if (previous != null) {
                previous.endToStart(reference.getKey());
                reference.startToEnd(previous.getKey());
            }
            previous = reference;
        }
        if (previous != null) {
            Object obj5 = this.mEndToStart;
            if (obj5 != null) {
                previous.endToStart(obj5).margin(this.mMarginEnd);
            } else {
                Object obj6 = this.mEndToEnd;
                if (obj6 != null) {
                    previous.endToEnd(obj6).margin(this.mMarginEnd);
                } else {
                    Object obj7 = this.mRightToLeft;
                    if (obj7 != null) {
                        previous.endToStart(obj7).margin(this.mMarginRight);
                    } else {
                        Object obj8 = this.mRightToRight;
                        if (obj8 != null) {
                            previous.endToEnd(obj8).margin(this.mMarginRight);
                        } else {
                            previous.endToEnd(State.PARENT);
                        }
                    }
                }
            }
        }
        if (first != null) {
            float f = this.mBias;
            if (f != 0.5f) {
                first.horizontalBias(f);
            }
            switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$state$State$Chain[this.mStyle.ordinal()]) {
                case 1:
                    first.setHorizontalChainStyle(0);
                    return;
                case 2:
                    first.setHorizontalChainStyle(1);
                    return;
                case 3:
                    first.setHorizontalChainStyle(2);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: androidx.constraintlayout.core.state.helpers.HorizontalChainReference$1  reason: invalid class name */
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
