package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import java.util.ArrayList;

public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, ArrayList<ConstraintWidget> widgets, int orientation) {
        ChainHead[] chainsArray;
        int chainsSize;
        int offset;
        if (orientation == 0) {
            offset = 0;
            chainsSize = constraintWidgetContainer.mHorizontalChainsSize;
            chainsArray = constraintWidgetContainer.mHorizontalChainsArray;
        } else {
            offset = 2;
            chainsSize = constraintWidgetContainer.mVerticalChainsSize;
            chainsArray = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i = 0; i < chainsSize; i++) {
            ChainHead first = chainsArray[i];
            first.define();
            if (widgets == null || widgets.contains(first.mFirst)) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:309:0x0653  */
    /* JADX WARNING: Removed duplicated region for block: B:312:0x065e  */
    /* JADX WARNING: Removed duplicated region for block: B:313:0x0661  */
    /* JADX WARNING: Removed duplicated region for block: B:316:0x0667  */
    /* JADX WARNING: Removed duplicated region for block: B:317:0x066a  */
    /* JADX WARNING: Removed duplicated region for block: B:319:0x066d  */
    /* JADX WARNING: Removed duplicated region for block: B:324:0x0683  */
    /* JADX WARNING: Removed duplicated region for block: B:326:0x0687  */
    /* JADX WARNING: Removed duplicated region for block: B:327:0x0690  */
    /* JADX WARNING: Removed duplicated region for block: B:329:0x0694 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r45, androidx.constraintlayout.core.LinearSystem r46, int r47, int r48, androidx.constraintlayout.core.widgets.ChainHead r49) {
        /*
            r0 = r45
            r10 = r46
            r11 = r47
            r12 = r49
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r12.mFirst
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r12.mLast
            androidx.constraintlayout.core.widgets.ConstraintWidget r15 = r12.mFirstVisibleWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r12.mLastVisibleWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r8 = r12.mHead
            r1 = r13
            r2 = 0
            r3 = 0
            float r4 = r12.mTotalWeight
            androidx.constraintlayout.core.widgets.ConstraintWidget r7 = r12.mFirstMatchConstraintWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r12.mLastMatchConstraintWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r0.mListDimensionBehaviors
            r5 = r5[r11]
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r17 = r2
            if (r5 != r1) goto L_0x0029
            r1 = 1
            goto L_0x002a
        L_0x0029:
            r1 = 0
        L_0x002a:
            r19 = r1
            r1 = 0
            r5 = 0
            r20 = 0
            if (r11 != 0) goto L_0x0054
            int r2 = r8.mHorizontalChainStyle
            if (r2 != 0) goto L_0x0039
            r23 = 1
            goto L_0x003b
        L_0x0039:
            r23 = 0
        L_0x003b:
            r1 = r23
            r1 = 1
            if (r2 != r1) goto L_0x0042
            r1 = 1
            goto L_0x0043
        L_0x0042:
            r1 = 0
        L_0x0043:
            r5 = 2
            if (r2 != r5) goto L_0x0048
            r2 = 1
            goto L_0x0049
        L_0x0048:
            r2 = 0
        L_0x0049:
            r22 = r3
            r5 = r16
            r20 = r17
            r16 = r1
            r17 = r2
            goto L_0x0075
        L_0x0054:
            int r2 = r8.mVerticalChainStyle
            if (r2 != 0) goto L_0x005b
            r23 = 1
            goto L_0x005d
        L_0x005b:
            r23 = 0
        L_0x005d:
            r1 = r23
            r1 = 1
            if (r2 != r1) goto L_0x0064
            r1 = 1
            goto L_0x0065
        L_0x0064:
            r1 = 0
        L_0x0065:
            r5 = 2
            if (r2 != r5) goto L_0x006a
            r2 = 1
            goto L_0x006b
        L_0x006a:
            r2 = 0
        L_0x006b:
            r22 = r3
            r5 = r16
            r20 = r17
            r16 = r1
            r17 = r2
        L_0x0075:
            if (r22 != 0) goto L_0x0163
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r5.mListAnchors
            r1 = r1[r48]
            r25 = 4
            if (r17 == 0) goto L_0x0081
            r25 = 1
        L_0x0081:
            int r26 = r1.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r5.mListDimensionBehaviors
            r3 = r3[r11]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r3 != r2) goto L_0x0095
            int[] r3 = r5.mResolvedMatchConstraintDefault
            r3 = r3[r11]
            if (r3 != 0) goto L_0x0095
            r3 = 1
            goto L_0x0096
        L_0x0095:
            r3 = 0
        L_0x0096:
            r29 = r4
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r1.mTarget
            if (r4 == 0) goto L_0x00a7
            if (r5 == r13) goto L_0x00a7
            int r4 = r4.getMargin()
            int r26 = r26 + r4
            r4 = r26
            goto L_0x00a9
        L_0x00a7:
            r4 = r26
        L_0x00a9:
            if (r17 == 0) goto L_0x00b1
            if (r5 == r13) goto L_0x00b1
            if (r5 == r15) goto L_0x00b1
            r25 = 8
        L_0x00b1:
            r26 = r6
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r1.mTarget
            if (r6 == 0) goto L_0x00f3
            if (r5 != r15) goto L_0x00c6
            r30 = r7
            androidx.constraintlayout.core.SolverVariable r7 = r1.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r31 = r8
            r8 = 6
            r10.addGreaterThan(r7, r6, r4, r8)
            goto L_0x00d3
        L_0x00c6:
            r30 = r7
            r31 = r8
            androidx.constraintlayout.core.SolverVariable r7 = r1.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r8 = 8
            r10.addGreaterThan(r7, r6, r4, r8)
        L_0x00d3:
            if (r3 == 0) goto L_0x00d9
            if (r17 != 0) goto L_0x00d9
            r25 = 5
        L_0x00d9:
            if (r5 != r15) goto L_0x00e5
            if (r17 == 0) goto L_0x00e5
            boolean r6 = r5.isInBarrier(r11)
            if (r6 == 0) goto L_0x00e5
            r6 = 5
            goto L_0x00e7
        L_0x00e5:
            r6 = r25
        L_0x00e7:
            androidx.constraintlayout.core.SolverVariable r7 = r1.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r1.mTarget
            androidx.constraintlayout.core.SolverVariable r8 = r8.mSolverVariable
            r10.addEquality(r7, r8, r4, r6)
            r25 = r6
            goto L_0x00f7
        L_0x00f3:
            r30 = r7
            r31 = r8
        L_0x00f7:
            if (r19 == 0) goto L_0x012a
            int r6 = r5.getVisibility()
            r7 = 8
            if (r6 == r7) goto L_0x0118
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r6 = r5.mListDimensionBehaviors
            r6 = r6[r11]
            if (r6 != r2) goto L_0x0118
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r5.mListAnchors
            int r6 = r48 + 1
            r6 = r2[r6]
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r2 = r2[r48]
            androidx.constraintlayout.core.SolverVariable r2 = r2.mSolverVariable
            r7 = 5
            r8 = 0
            r10.addGreaterThan(r6, r2, r8, r7)
        L_0x0118:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r5.mListAnchors
            r2 = r2[r48]
            androidx.constraintlayout.core.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r6 = r0.mListAnchors
            r6 = r6[r48]
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r7 = 8
            r8 = 0
            r10.addGreaterThan(r2, r6, r8, r7)
        L_0x012a:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r5.mListAnchors
            int r6 = r48 + 1
            r2 = r2[r6]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x014e
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r2.mOwner
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r6.mListAnchors
            r8 = r7[r48]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 == 0) goto L_0x014a
            r7 = r7[r48]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = r7.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r7 = r7.mOwner
            if (r7 == r5) goto L_0x0147
            goto L_0x014a
        L_0x0147:
            r20 = r6
            goto L_0x0151
        L_0x014a:
            r6 = 0
            r20 = r6
            goto L_0x0151
        L_0x014e:
            r6 = 0
            r20 = r6
        L_0x0151:
            if (r20 == 0) goto L_0x0156
            r5 = r20
            goto L_0x0159
        L_0x0156:
            r6 = 1
            r22 = r6
        L_0x0159:
            r6 = r26
            r4 = r29
            r7 = r30
            r8 = r31
            goto L_0x0075
        L_0x0163:
            r29 = r4
            r26 = r6
            r30 = r7
            r31 = r8
            r1 = 4
            if (r9 == 0) goto L_0x01d0
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            int r3 = r48 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x01d0
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r9.mListAnchors
            int r3 = r48 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r9.mListDimensionBehaviors
            r3 = r3[r11]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r3 != r4) goto L_0x018e
            int[] r3 = r9.mResolvedMatchConstraintDefault
            r3 = r3[r11]
            if (r3 != 0) goto L_0x018e
            r3 = 1
            goto L_0x018f
        L_0x018e:
            r3 = 0
        L_0x018f:
            if (r3 == 0) goto L_0x01a7
            if (r17 != 0) goto L_0x01a7
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r2.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r4.mOwner
            if (r6 != r0) goto L_0x01a7
            androidx.constraintlayout.core.SolverVariable r6 = r2.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            int r7 = r2.getMargin()
            int r7 = -r7
            r8 = 5
            r10.addEquality(r6, r4, r7, r8)
            goto L_0x01bb
        L_0x01a7:
            if (r17 == 0) goto L_0x01bb
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r2.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r4.mOwner
            if (r6 != r0) goto L_0x01bb
            androidx.constraintlayout.core.SolverVariable r6 = r2.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            int r7 = r2.getMargin()
            int r7 = -r7
            r10.addEquality(r6, r4, r7, r1)
        L_0x01bb:
            androidx.constraintlayout.core.SolverVariable r4 = r2.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r6 = r14.mListAnchors
            int r7 = r48 + 1
            r6 = r6[r7]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r6.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            int r7 = r2.getMargin()
            int r7 = -r7
            r8 = 6
            r10.addLowerThan(r4, r6, r7, r8)
        L_0x01d0:
            if (r19 == 0) goto L_0x01ef
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r0.mListAnchors
            int r3 = r48 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r14.mListAnchors
            int r4 = r48 + 1
            r4 = r3[r4]
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            int r6 = r48 + 1
            r3 = r3[r6]
            int r3 = r3.getMargin()
            r6 = 8
            r10.addGreaterThan(r2, r4, r3, r6)
        L_0x01ef:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.ConstraintWidget> r8 = r12.mWeightedMatchConstraintsWidgets
            if (r8 == 0) goto L_0x02c6
            int r2 = r8.size()
            r3 = 1
            if (r2 <= r3) goto L_0x02bf
            r4 = 0
            r6 = 0
            boolean r7 = r12.mHasUndefinedWeights
            if (r7 == 0) goto L_0x0209
            boolean r7 = r12.mHasComplexMatchWeights
            if (r7 != 0) goto L_0x0209
            int r7 = r12.mWidgetsMatchCount
            float r7 = (float) r7
            r29 = r7
        L_0x0209:
            r7 = 0
        L_0x020a:
            if (r7 >= r2) goto L_0x02b6
            java.lang.Object r21 = r8.get(r7)
            r3 = r21
            androidx.constraintlayout.core.widgets.ConstraintWidget r3 = (androidx.constraintlayout.core.widgets.ConstraintWidget) r3
            float[] r1 = r3.mWeight
            r1 = r1[r11]
            r25 = 0
            int r28 = (r1 > r25 ? 1 : (r1 == r25 ? 0 : -1))
            if (r28 >= 0) goto L_0x0248
            boolean r0 = r12.mHasComplexMatchWeights
            if (r0 == 0) goto L_0x023e
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r3.mListAnchors
            int r25 = r48 + 1
            r28 = r1
            r1 = r0[r25]
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            r0 = r0[r48]
            androidx.constraintlayout.core.SolverVariable r0 = r0.mSolverVariable
            r40 = r2
            r21 = r5
            r2 = 4
            r5 = 0
            r10.addEquality(r1, r0, r5, r2)
            r18 = r8
            r8 = 0
            goto L_0x02a8
        L_0x023e:
            r28 = r1
            r40 = r2
            r21 = r5
            r2 = 4
            r1 = 1065353216(0x3f800000, float:1.0)
            goto L_0x024f
        L_0x0248:
            r28 = r1
            r40 = r2
            r21 = r5
            r2 = 4
        L_0x024f:
            int r0 = (r1 > r25 ? 1 : (r1 == r25 ? 0 : -1))
            if (r0 != 0) goto L_0x0268
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r3.mListAnchors
            int r5 = r48 + 1
            r5 = r0[r5]
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            r0 = r0[r48]
            androidx.constraintlayout.core.SolverVariable r0 = r0.mSolverVariable
            r18 = r8
            r2 = 8
            r8 = 0
            r10.addEquality(r5, r0, r8, r2)
            goto L_0x02a8
        L_0x0268:
            r18 = r8
            r8 = 0
            if (r4 == 0) goto L_0x02a2
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r0 = r4.mListAnchors
            r2 = r0[r48]
            androidx.constraintlayout.core.SolverVariable r2 = r2.mSolverVariable
            int r5 = r48 + 1
            r0 = r0[r5]
            androidx.constraintlayout.core.SolverVariable r0 = r0.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r3.mListAnchors
            r8 = r5[r48]
            androidx.constraintlayout.core.SolverVariable r8 = r8.mSolverVariable
            int r32 = r48 + 1
            r5 = r5[r32]
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            r41 = r4
            androidx.constraintlayout.core.ArrayRow r4 = r46.createRow()
            r32 = r4
            r33 = r6
            r34 = r29
            r35 = r1
            r36 = r2
            r37 = r0
            r38 = r8
            r39 = r5
            r32.createRowEqualMatchDimensions(r33, r34, r35, r36, r37, r38, r39)
            r10.addConstraint(r4)
            goto L_0x02a4
        L_0x02a2:
            r41 = r4
        L_0x02a4:
            r0 = r3
            r2 = r1
            r4 = r0
            r6 = r2
        L_0x02a8:
            int r7 = r7 + 1
            r0 = r45
            r8 = r18
            r5 = r21
            r2 = r40
            r1 = 4
            r3 = 1
            goto L_0x020a
        L_0x02b6:
            r40 = r2
            r41 = r4
            r21 = r5
            r18 = r8
            goto L_0x02ca
        L_0x02bf:
            r40 = r2
            r21 = r5
            r18 = r8
            goto L_0x02ca
        L_0x02c6:
            r21 = r5
            r18 = r8
        L_0x02ca:
            if (r15 == 0) goto L_0x035c
            if (r15 == r9) goto L_0x02da
            if (r17 == 0) goto L_0x02d1
            goto L_0x02da
        L_0x02d1:
            r0 = r9
            r34 = r26
            r26 = r18
            r18 = r31
            goto L_0x0363
        L_0x02da:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            r1 = r1[r48]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            int r3 = r48 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r1.mTarget
            if (r3 == 0) goto L_0x02eb
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            goto L_0x02ec
        L_0x02eb:
            r3 = 0
        L_0x02ec:
            r24 = r3
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r2.mTarget
            if (r3 == 0) goto L_0x02f5
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            goto L_0x02f6
        L_0x02f5:
            r3 = 0
        L_0x02f6:
            r25 = r3
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r15.mListAnchors
            r8 = r3[r48]
            if (r9 == 0) goto L_0x0306
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            int r3 = r48 + 1
            r2 = r1[r3]
            r7 = r2
            goto L_0x0307
        L_0x0306:
            r7 = r2
        L_0x0307:
            if (r24 == 0) goto L_0x034f
            if (r25 == 0) goto L_0x034f
            r1 = 1056964608(0x3f000000, float:0.5)
            if (r11 != 0) goto L_0x0316
            r6 = r31
            float r1 = r6.mHorizontalBiasPercent
            r27 = r1
            goto L_0x031c
        L_0x0316:
            r6 = r31
            float r1 = r6.mVerticalBiasPercent
            r27 = r1
        L_0x031c:
            int r28 = r8.getMargin()
            int r31 = r7.getMargin()
            androidx.constraintlayout.core.SolverVariable r2 = r8.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r5 = r7.mSolverVariable
            r32 = 7
            r1 = r46
            r3 = r24
            r4 = r28
            r33 = r5
            r5 = r27
            r34 = r26
            r26 = r6
            r6 = r25
            r35 = r7
            r7 = r33
            r33 = r8
            r44 = r26
            r26 = r18
            r18 = r44
            r8 = r31
            r0 = r9
            r9 = r32
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x035a
        L_0x034f:
            r35 = r7
            r33 = r8
            r0 = r9
            r34 = r26
            r26 = r18
            r18 = r31
        L_0x035a:
            goto L_0x0645
        L_0x035c:
            r0 = r9
            r34 = r26
            r26 = r18
            r18 = r31
        L_0x0363:
            if (r23 == 0) goto L_0x04aa
            if (r15 == 0) goto L_0x04aa
            r1 = r15
            r2 = r15
            int r3 = r12.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x0374
            int r4 = r12.mWidgetsCount
            if (r4 != r3) goto L_0x0374
            r28 = 1
            goto L_0x0376
        L_0x0374:
            r28 = 0
        L_0x0376:
            r21 = r28
            r8 = r1
            r9 = r2
        L_0x037a:
            if (r8 == 0) goto L_0x04a2
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r1 = r8.mNextChainWidget
            r1 = r1[r11]
            r7 = r1
        L_0x0381:
            if (r7 == 0) goto L_0x0390
            int r1 = r7.getVisibility()
            r3 = 8
            if (r1 != r3) goto L_0x0392
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r1 = r7.mNextChainWidget
            r7 = r1[r11]
            goto L_0x0381
        L_0x0390:
            r3 = 8
        L_0x0392:
            if (r7 != 0) goto L_0x039f
            if (r8 != r0) goto L_0x0397
            goto L_0x039f
        L_0x0397:
            r39 = r7
            r40 = r8
            r41 = r9
            goto L_0x048e
        L_0x039f:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            r6 = r1[r48]
            androidx.constraintlayout.core.SolverVariable r5 = r6.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r6.mTarget
            if (r1 == 0) goto L_0x03ac
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            goto L_0x03ad
        L_0x03ac:
            r1 = 0
        L_0x03ad:
            if (r9 == r8) goto L_0x03ba
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r9.mListAnchors
            int r4 = r48 + 1
            r2 = r2[r4]
            androidx.constraintlayout.core.SolverVariable r1 = r2.mSolverVariable
            r20 = r1
            goto L_0x03d2
        L_0x03ba:
            if (r8 != r15) goto L_0x03d0
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            r4 = r2[r48]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x03cb
            r2 = r2[r48]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            androidx.constraintlayout.core.SolverVariable r2 = r2.mSolverVariable
            goto L_0x03cc
        L_0x03cb:
            r2 = 0
        L_0x03cc:
            r1 = r2
            r20 = r1
            goto L_0x03d2
        L_0x03d0:
            r20 = r1
        L_0x03d2:
            r1 = 0
            r2 = 0
            r4 = 0
            int r24 = r6.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r8.mListAnchors
            int r25 = r48 + 1
            r3 = r3[r25]
            int r3 = r3.getMargin()
            if (r7 == 0) goto L_0x03f2
            r25 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r7.mListAnchors
            r1 = r1[r48]
            androidx.constraintlayout.core.SolverVariable r2 = r1.mSolverVariable
            r25 = r1
            r28 = r2
            goto L_0x0409
        L_0x03f2:
            r25 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r14.mListAnchors
            int r28 = r48 + 1
            r1 = r1[r28]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x0405
            androidx.constraintlayout.core.SolverVariable r2 = r1.mSolverVariable
            r25 = r1
            r28 = r2
            goto L_0x0409
        L_0x0405:
            r25 = r1
            r28 = r2
        L_0x0409:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            int r2 = r48 + 1
            r1 = r1[r2]
            androidx.constraintlayout.core.SolverVariable r4 = r1.mSolverVariable
            if (r25 == 0) goto L_0x041b
            int r1 = r25.getMargin()
            int r3 = r3 + r1
            r31 = r3
            goto L_0x041d
        L_0x041b:
            r31 = r3
        L_0x041d:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            int r2 = r48 + 1
            r1 = r1[r2]
            int r1 = r1.getMargin()
            int r24 = r24 + r1
            if (r5 == 0) goto L_0x0482
            if (r20 == 0) goto L_0x0482
            if (r28 == 0) goto L_0x0482
            if (r4 == 0) goto L_0x0482
            r1 = r24
            if (r8 != r15) goto L_0x0440
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r15.mListAnchors
            r2 = r2[r48]
            int r1 = r2.getMargin()
            r32 = r1
            goto L_0x0442
        L_0x0440:
            r32 = r1
        L_0x0442:
            r1 = r31
            if (r8 != r0) goto L_0x0453
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r0.mListAnchors
            int r3 = r48 + 1
            r2 = r2[r3]
            int r1 = r2.getMargin()
            r33 = r1
            goto L_0x0455
        L_0x0453:
            r33 = r1
        L_0x0455:
            r1 = 5
            if (r21 == 0) goto L_0x045d
            r1 = 8
            r35 = r1
            goto L_0x045f
        L_0x045d:
            r35 = r1
        L_0x045f:
            r37 = 1056964608(0x3f000000, float:0.5)
            r1 = r46
            r2 = r5
            r3 = r20
            r27 = r4
            r4 = r32
            r38 = r5
            r5 = r37
            r37 = r6
            r6 = r28
            r39 = r7
            r7 = r27
            r40 = r8
            r8 = r33
            r41 = r9
            r9 = r35
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x048e
        L_0x0482:
            r27 = r4
            r38 = r5
            r37 = r6
            r39 = r7
            r40 = r8
            r41 = r9
        L_0x048e:
            int r1 = r40.getVisibility()
            r9 = 8
            if (r1 == r9) goto L_0x0499
            r1 = r40
            goto L_0x049b
        L_0x0499:
            r1 = r41
        L_0x049b:
            r8 = r39
            r9 = r1
            r20 = r39
            goto L_0x037a
        L_0x04a2:
            r40 = r8
            r41 = r9
            r21 = r40
            goto L_0x0645
        L_0x04aa:
            r9 = 8
            if (r16 == 0) goto L_0x0645
            if (r15 == 0) goto L_0x0645
            r1 = r15
            r2 = r15
            int r3 = r12.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x04bd
            int r4 = r12.mWidgetsCount
            if (r4 != r3) goto L_0x04bd
            r28 = 1
            goto L_0x04bf
        L_0x04bd:
            r28 = 0
        L_0x04bf:
            r21 = r28
            r7 = r1
            r8 = r2
        L_0x04c3:
            if (r7 == 0) goto L_0x05b4
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r1 = r7.mNextChainWidget
            r1 = r1[r11]
        L_0x04c9:
            if (r1 == 0) goto L_0x04d6
            int r2 = r1.getVisibility()
            if (r2 != r9) goto L_0x04d6
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r2 = r1.mNextChainWidget
            r1 = r2[r11]
            goto L_0x04c9
        L_0x04d6:
            if (r7 == r15) goto L_0x059a
            if (r7 == r0) goto L_0x059a
            if (r1 == 0) goto L_0x059a
            if (r1 != r0) goto L_0x04e1
            r1 = 0
            r6 = r1
            goto L_0x04e2
        L_0x04e1:
            r6 = r1
        L_0x04e2:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r7.mListAnchors
            r5 = r1[r48]
            androidx.constraintlayout.core.SolverVariable r4 = r5.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r5.mTarget
            if (r1 == 0) goto L_0x04ef
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            goto L_0x04f0
        L_0x04ef:
            r1 = 0
        L_0x04f0:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r8.mListAnchors
            int r3 = r48 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.SolverVariable r3 = r2.mSolverVariable
            r1 = 0
            r2 = 0
            r20 = 0
            int r24 = r5.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r9 = r7.mListAnchors
            int r25 = r48 + 1
            r9 = r9[r25]
            int r9 = r9.getMargin()
            if (r6 == 0) goto L_0x0525
            r25 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r6.mListAnchors
            r1 = r1[r48]
            androidx.constraintlayout.core.SolverVariable r2 = r1.mSolverVariable
            r25 = r2
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x051d
            androidx.constraintlayout.core.SolverVariable r2 = r2.mSolverVariable
            goto L_0x051e
        L_0x051d:
            r2 = 0
        L_0x051e:
            r20 = r2
            r27 = r25
            r25 = r1
            goto L_0x053d
        L_0x0525:
            r25 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            r1 = r1[r48]
            if (r1 == 0) goto L_0x052f
            androidx.constraintlayout.core.SolverVariable r2 = r1.mSolverVariable
        L_0x052f:
            r25 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r7.mListAnchors
            int r27 = r48 + 1
            r1 = r1[r27]
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            r20 = r1
            r27 = r2
        L_0x053d:
            if (r25 == 0) goto L_0x0547
            int r1 = r25.getMargin()
            int r9 = r9 + r1
            r28 = r9
            goto L_0x0549
        L_0x0547:
            r28 = r9
        L_0x0549:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            int r2 = r48 + 1
            r1 = r1[r2]
            int r1 = r1.getMargin()
            int r24 = r24 + r1
            r1 = 4
            if (r21 == 0) goto L_0x055d
            r1 = 8
            r31 = r1
            goto L_0x055f
        L_0x055d:
            r31 = r1
        L_0x055f:
            if (r4 == 0) goto L_0x0589
            if (r3 == 0) goto L_0x0589
            if (r27 == 0) goto L_0x0589
            if (r20 == 0) goto L_0x0589
            r9 = 1056964608(0x3f000000, float:0.5)
            r1 = r46
            r2 = r4
            r32 = r3
            r33 = r4
            r4 = r24
            r35 = r5
            r5 = r9
            r37 = r6
            r6 = r27
            r38 = r7
            r7 = r20
            r39 = r8
            r8 = r28
            r11 = 8
            r9 = r31
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0597
        L_0x0589:
            r32 = r3
            r33 = r4
            r35 = r5
            r37 = r6
            r38 = r7
            r39 = r8
            r11 = 8
        L_0x0597:
            r20 = r37
            goto L_0x05a1
        L_0x059a:
            r38 = r7
            r39 = r8
            r11 = r9
            r20 = r1
        L_0x05a1:
            int r1 = r38.getVisibility()
            if (r1 == r11) goto L_0x05ab
            r1 = r38
            r8 = r1
            goto L_0x05ad
        L_0x05ab:
            r8 = r39
        L_0x05ad:
            r7 = r20
            r9 = r11
            r11 = r47
            goto L_0x04c3
        L_0x05b4:
            r38 = r7
            r39 = r8
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            r11 = r1[r48]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            r1 = r1[r48]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r1.mTarget
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            int r2 = r48 + 1
            r8 = r1[r2]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r14.mListAnchors
            int r2 = r48 + 1
            r1 = r1[r2]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = r1.mTarget
            r6 = 5
            if (r9 == 0) goto L_0x0620
            if (r15 == r0) goto L_0x05e9
            androidx.constraintlayout.core.SolverVariable r1 = r11.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r2 = r9.mSolverVariable
            int r3 = r11.getMargin()
            r10.addEquality(r1, r2, r3, r6)
            r28 = r6
            r42 = r7
            r43 = r8
            r24 = r9
            goto L_0x0628
        L_0x05e9:
            if (r7 == 0) goto L_0x0617
            androidx.constraintlayout.core.SolverVariable r2 = r11.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r3 = r9.mSolverVariable
            int r4 = r11.getMargin()
            androidx.constraintlayout.core.SolverVariable r1 = r8.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r5 = r7.mSolverVariable
            int r25 = r8.getMargin()
            r27 = r1
            r1 = r46
            r24 = r5
            r5 = 1056964608(0x3f000000, float:0.5)
            r28 = r6
            r6 = r27
            r42 = r7
            r7 = r24
            r43 = r8
            r8 = r25
            r24 = r9
            r9 = r28
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0628
        L_0x0617:
            r28 = r6
            r42 = r7
            r43 = r8
            r24 = r9
            goto L_0x0628
        L_0x0620:
            r28 = r6
            r42 = r7
            r43 = r8
            r24 = r9
        L_0x0628:
            r1 = r42
            if (r1 == 0) goto L_0x063f
            if (r15 == r0) goto L_0x063f
            r2 = r43
            androidx.constraintlayout.core.SolverVariable r3 = r2.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r4 = r1.mSolverVariable
            int r5 = r2.getMargin()
            int r5 = -r5
            r6 = r28
            r10.addEquality(r3, r4, r5, r6)
            goto L_0x0643
        L_0x063f:
            r6 = r28
            r2 = r43
        L_0x0643:
            r21 = r38
        L_0x0645:
            if (r23 != 0) goto L_0x0649
            if (r16 == 0) goto L_0x06c5
        L_0x0649:
            if (r15 == 0) goto L_0x06c5
            if (r15 == r0) goto L_0x06c5
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            r2 = r1[r48]
            if (r0 != 0) goto L_0x0654
            r0 = r15
        L_0x0654:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r0.mListAnchors
            int r4 = r48 + 1
            r3 = r3[r4]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r2.mTarget
            if (r4 == 0) goto L_0x0661
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            goto L_0x0662
        L_0x0661:
            r4 = 0
        L_0x0662:
            r11 = r4
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r3.mTarget
            if (r4 == 0) goto L_0x066a
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            goto L_0x066b
        L_0x066a:
            r4 = 0
        L_0x066b:
            if (r14 == r0) goto L_0x0683
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r14.mListAnchors
            int r6 = r48 + 1
            r5 = r5[r6]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r5.mTarget
            if (r6 == 0) goto L_0x067c
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r36 = r6
            goto L_0x067e
        L_0x067c:
            r36 = 0
        L_0x067e:
            r4 = r36
            r24 = r4
            goto L_0x0685
        L_0x0683:
            r24 = r4
        L_0x0685:
            if (r15 != r0) goto L_0x0690
            r2 = r1[r48]
            int r4 = r48 + 1
            r3 = r1[r4]
            r9 = r2
            r8 = r3
            goto L_0x0692
        L_0x0690:
            r9 = r2
            r8 = r3
        L_0x0692:
            if (r11 == 0) goto L_0x06c1
            if (r24 == 0) goto L_0x06c1
            r25 = 1056964608(0x3f000000, float:0.5)
            int r27 = r9.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            int r2 = r48 + 1
            r1 = r1[r2]
            int r28 = r1.getMargin()
            androidx.constraintlayout.core.SolverVariable r2 = r9.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r7 = r8.mSolverVariable
            r31 = 5
            r1 = r46
            r3 = r11
            r4 = r27
            r5 = r25
            r6 = r24
            r32 = r8
            r8 = r28
            r33 = r9
            r9 = r31
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x06c5
        L_0x06c1:
            r32 = r8
            r33 = r9
        L_0x06c5:
            r9 = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.Chain.applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer, androidx.constraintlayout.core.LinearSystem, int, int, androidx.constraintlayout.core.widgets.ChainHead):void");
    }
}
