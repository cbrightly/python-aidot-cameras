package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ChainHead;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import java.util.ArrayList;

public class Direct {
    private static final boolean APPLY_MATCH_PARENT = false;
    private static final boolean DEBUG = false;
    private static final boolean EARLY_TERMINATION = true;
    private static int hcount = 0;
    private static BasicMeasure.Measure measure = new BasicMeasure.Measure();
    private static int vcount = 0;

    public static void solvingPass(ConstraintWidgetContainer layout, BasicMeasure.Measurer measurer) {
        ConstraintWidgetContainer constraintWidgetContainer = layout;
        BasicMeasure.Measurer measurer2 = measurer;
        ConstraintWidget.DimensionBehaviour horizontal = layout.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour vertical = layout.getVerticalDimensionBehaviour();
        hcount = 0;
        vcount = 0;
        layout.resetFinalResolution();
        ArrayList<ConstraintWidget> children = layout.getChildren();
        int count = children.size();
        for (int i = 0; i < count; i++) {
            children.get(i).resetFinalResolution();
        }
        boolean isRtl = layout.isRtl();
        if (horizontal == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalHorizontal(0, layout.getWidth());
        } else {
            constraintWidgetContainer.setFinalLeft(0);
        }
        boolean hasGuideline = false;
        boolean hasBarrier = false;
        for (int i2 = 0; i2 < count; i2++) {
            ConstraintWidget child = children.get(i2);
            if (child instanceof Guideline) {
                Guideline guideline = (Guideline) child;
                if (guideline.getOrientation() == 1) {
                    if (guideline.getRelativeBegin() != -1) {
                        guideline.setFinalValue(guideline.getRelativeBegin());
                    } else if (guideline.getRelativeEnd() != -1 && layout.isResolvedHorizontally()) {
                        guideline.setFinalValue(layout.getWidth() - guideline.getRelativeEnd());
                    } else if (layout.isResolvedHorizontally()) {
                        guideline.setFinalValue((int) ((guideline.getRelativePercent() * ((float) layout.getWidth())) + 0.5f));
                    }
                    hasGuideline = true;
                }
            } else if ((child instanceof Barrier) && ((Barrier) child).getOrientation() == 0) {
                hasBarrier = true;
            }
        }
        if (hasGuideline) {
            for (int i3 = 0; i3 < count; i3++) {
                ConstraintWidget child2 = children.get(i3);
                if (child2 instanceof Guideline) {
                    Guideline guideline2 = (Guideline) child2;
                    if (guideline2.getOrientation() == 1) {
                        horizontalSolvingPass(0, guideline2, measurer2, isRtl);
                    }
                }
            }
        }
        horizontalSolvingPass(0, constraintWidgetContainer, measurer2, isRtl);
        if (hasBarrier) {
            for (int i4 = 0; i4 < count; i4++) {
                ConstraintWidget child3 = children.get(i4);
                if (child3 instanceof Barrier) {
                    Barrier barrier = (Barrier) child3;
                    if (barrier.getOrientation() == 0) {
                        solveBarrier(0, barrier, measurer2, 0, isRtl);
                    }
                }
            }
        }
        if (vertical == ConstraintWidget.DimensionBehaviour.FIXED) {
            constraintWidgetContainer.setFinalVertical(0, layout.getHeight());
        } else {
            constraintWidgetContainer.setFinalTop(0);
        }
        boolean hasGuideline2 = false;
        boolean hasBarrier2 = false;
        for (int i5 = 0; i5 < count; i5++) {
            ConstraintWidget child4 = children.get(i5);
            if (child4 instanceof Guideline) {
                Guideline guideline3 = (Guideline) child4;
                if (guideline3.getOrientation() == 0) {
                    if (guideline3.getRelativeBegin() != -1) {
                        guideline3.setFinalValue(guideline3.getRelativeBegin());
                    } else if (guideline3.getRelativeEnd() != -1 && layout.isResolvedVertically()) {
                        guideline3.setFinalValue(layout.getHeight() - guideline3.getRelativeEnd());
                    } else if (layout.isResolvedVertically()) {
                        guideline3.setFinalValue((int) ((guideline3.getRelativePercent() * ((float) layout.getHeight())) + 0.5f));
                    }
                    hasGuideline2 = true;
                }
            } else if ((child4 instanceof Barrier) && ((Barrier) child4).getOrientation() == 1) {
                hasBarrier2 = true;
            }
        }
        if (hasGuideline2) {
            for (int i6 = 0; i6 < count; i6++) {
                ConstraintWidget child5 = children.get(i6);
                if (child5 instanceof Guideline) {
                    Guideline guideline4 = (Guideline) child5;
                    if (guideline4.getOrientation() == 0) {
                        verticalSolvingPass(1, guideline4, measurer2);
                    }
                }
            }
        }
        verticalSolvingPass(0, constraintWidgetContainer, measurer2);
        if (hasBarrier2) {
            for (int i7 = 0; i7 < count; i7++) {
                ConstraintWidget child6 = children.get(i7);
                if (child6 instanceof Barrier) {
                    Barrier barrier2 = (Barrier) child6;
                    if (barrier2.getOrientation() == 1) {
                        solveBarrier(0, barrier2, measurer2, 1, isRtl);
                    }
                }
            }
        }
        for (int i8 = 0; i8 < count; i8++) {
            ConstraintWidget child7 = children.get(i8);
            if (child7.isMeasureRequested()) {
                if (canMeasure(0, child7)) {
                    ConstraintWidgetContainer.measure(0, child7, measurer2, measure, BasicMeasure.Measure.SELF_DIMENSIONS);
                    if (!(child7 instanceof Guideline)) {
                        horizontalSolvingPass(0, child7, measurer2, isRtl);
                        verticalSolvingPass(0, child7, measurer2);
                    } else if (((Guideline) child7).getOrientation() == 0) {
                        verticalSolvingPass(0, child7, measurer2);
                    } else {
                        horizontalSolvingPass(0, child7, measurer2, isRtl);
                    }
                }
            }
        }
    }

    private static void solveBarrier(int level, Barrier barrier, BasicMeasure.Measurer measurer, int orientation, boolean isRtl) {
        if (!barrier.allSolved()) {
            return;
        }
        if (orientation == 0) {
            horizontalSolvingPass(level + 1, barrier, measurer, isRtl);
        } else {
            verticalSolvingPass(level + 1, barrier, measurer);
        }
    }

    public static String ls(int level) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append(JustifyTextView.TWO_CHINESE_BLANK);
        }
        builder.append("+-(" + level + ") ");
        return builder.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008b, code lost:
        r9 = r13.mRight.mTarget;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x009b, code lost:
        r9 = r13.mLeft.mTarget;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void horizontalSolvingPass(int r18, androidx.constraintlayout.core.widgets.ConstraintWidget r19, androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measurer r20, boolean r21) {
        /*
            r0 = r19
            r1 = r20
            r2 = r21
            boolean r3 = r19.isHorizontalSolvingPassDone()
            if (r3 == 0) goto L_0x000d
            return
        L_0x000d:
            int r3 = hcount
            r4 = 1
            int r3 = r3 + r4
            hcount = r3
            boolean r3 = r0 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r3 != 0) goto L_0x0031
            boolean r3 = r19.isMeasureRequested()
            if (r3 == 0) goto L_0x0031
            int r3 = r18 + 1
            boolean r3 = canMeasure(r3, r0)
            if (r3 == 0) goto L_0x0031
            androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure r3 = new androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure
            r3.<init>()
            int r5 = r18 + 1
            int r6 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.SELF_DIMENSIONS
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer.measure(r5, r0, r1, r3, r6)
        L_0x0031:
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r0.getAnchor(r3)
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r0.getAnchor(r5)
            int r6 = r3.getFinalValue()
            int r7 = r5.getFinalValue()
            java.util.HashSet r8 = r3.getDependents()
            if (r8 == 0) goto L_0x0145
            boolean r8 = r3.hasFinalValue()
            if (r8 == 0) goto L_0x0145
            java.util.HashSet r8 = r3.getDependents()
            java.util.Iterator r8 = r8.iterator()
        L_0x0059:
            boolean r12 = r8.hasNext()
            if (r12 == 0) goto L_0x0145
            java.lang.Object r12 = r8.next()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r12 = (androidx.constraintlayout.core.widgets.ConstraintAnchor) r12
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r12.mOwner
            r14 = 0
            r15 = 0
            int r4 = r18 + 1
            boolean r4 = canMeasure(r4, r13)
            boolean r16 = r13.isMeasureRequested()
            if (r16 == 0) goto L_0x0087
            if (r4 == 0) goto L_0x0087
            androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure r16 = new androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure
            r16.<init>()
            r17 = r16
            int r11 = r18 + 1
            int r9 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.SELF_DIMENSIONS
            r10 = r17
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer.measure(r11, r13, r1, r10, r9)
        L_0x0087:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r13.mLeft
            if (r12 != r9) goto L_0x0097
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r13.mRight
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r9.mTarget
            if (r9 == 0) goto L_0x0097
            boolean r9 = r9.hasFinalValue()
            if (r9 != 0) goto L_0x00a7
        L_0x0097:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r13.mRight
            if (r12 != r9) goto L_0x00a9
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r13.mLeft
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r9.mTarget
            if (r9 == 0) goto L_0x00a9
            boolean r9 = r9.hasFinalValue()
            if (r9 == 0) goto L_0x00a9
        L_0x00a7:
            r9 = 1
            goto L_0x00aa
        L_0x00a9:
            r9 = 0
        L_0x00aa:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r10 = r13.getHorizontalDimensionBehaviour()
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r10 != r11) goto L_0x00f2
            if (r4 == 0) goto L_0x00b5
            goto L_0x00f2
        L_0x00b5:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r10 = r13.getHorizontalDimensionBehaviour()
            if (r10 != r11) goto L_0x0142
            int r10 = r13.mMatchConstraintMaxWidth
            if (r10 < 0) goto L_0x0142
            int r10 = r13.mMatchConstraintMinWidth
            if (r10 < 0) goto L_0x0142
            int r10 = r13.getVisibility()
            r11 = 8
            if (r10 == r11) goto L_0x00d8
            int r10 = r13.mMatchConstraintDefaultWidth
            if (r10 != 0) goto L_0x0142
            float r10 = r13.getDimensionRatio()
            r11 = 0
            int r10 = (r10 > r11 ? 1 : (r10 == r11 ? 0 : -1))
            if (r10 != 0) goto L_0x0142
        L_0x00d8:
            boolean r10 = r13.isInHorizontalChain()
            if (r10 != 0) goto L_0x0142
            boolean r10 = r13.isInVirtualLayout()
            if (r10 != 0) goto L_0x0142
            if (r9 == 0) goto L_0x0142
            boolean r10 = r13.isInHorizontalChain()
            if (r10 != 0) goto L_0x0142
            int r10 = r18 + 1
            solveHorizontalMatchConstraint(r10, r0, r1, r13, r2)
            goto L_0x0142
        L_0x00f2:
            boolean r10 = r13.isMeasureRequested()
            if (r10 == 0) goto L_0x00fb
            r4 = 1
            goto L_0x0059
        L_0x00fb:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r13.mLeft
            if (r12 != r10) goto L_0x0118
            androidx.constraintlayout.core.widgets.ConstraintAnchor r11 = r13.mRight
            androidx.constraintlayout.core.widgets.ConstraintAnchor r11 = r11.mTarget
            if (r11 != 0) goto L_0x0118
            int r10 = r10.getMargin()
            int r10 = r10 + r6
            int r11 = r13.getWidth()
            int r11 = r11 + r10
            r13.setFinalHorizontal(r10, r11)
            int r14 = r18 + 1
            horizontalSolvingPass(r14, r13, r1, r2)
            goto L_0x0142
        L_0x0118:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r11 = r13.mRight
            if (r12 != r11) goto L_0x0135
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r10.mTarget
            if (r10 != 0) goto L_0x0135
            int r10 = r11.getMargin()
            int r10 = r6 - r10
            int r11 = r13.getWidth()
            int r11 = r10 - r11
            r13.setFinalHorizontal(r11, r10)
            int r14 = r18 + 1
            horizontalSolvingPass(r14, r13, r1, r2)
            goto L_0x0142
        L_0x0135:
            if (r9 == 0) goto L_0x0142
            boolean r10 = r13.isInHorizontalChain()
            if (r10 != 0) goto L_0x0142
            int r10 = r18 + 1
            solveHorizontalCenterConstraints(r10, r1, r13, r2)
        L_0x0142:
            r4 = 1
            goto L_0x0059
        L_0x0145:
            boolean r4 = r0 instanceof androidx.constraintlayout.core.widgets.Guideline
            if (r4 == 0) goto L_0x014a
            return
        L_0x014a:
            java.util.HashSet r4 = r5.getDependents()
            if (r4 == 0) goto L_0x0259
            boolean r4 = r5.hasFinalValue()
            if (r4 == 0) goto L_0x0259
            java.util.HashSet r4 = r5.getDependents()
            java.util.Iterator r4 = r4.iterator()
        L_0x015e:
            boolean r8 = r4.hasNext()
            if (r8 == 0) goto L_0x0259
            java.lang.Object r8 = r4.next()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = (androidx.constraintlayout.core.widgets.ConstraintAnchor) r8
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r8.mOwner
            int r10 = r18 + 1
            boolean r10 = canMeasure(r10, r9)
            boolean r11 = r9.isMeasureRequested()
            if (r11 == 0) goto L_0x0186
            if (r10 == 0) goto L_0x0186
            androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure r11 = new androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure
            r11.<init>()
            int r12 = r18 + 1
            int r13 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.SELF_DIMENSIONS
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer.measure(r12, r9, r1, r11, r13)
        L_0x0186:
            r11 = 0
            r12 = 0
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r9.mLeft
            if (r8 != r13) goto L_0x0198
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r9.mRight
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x0198
            boolean r13 = r13.hasFinalValue()
            if (r13 != 0) goto L_0x01a8
        L_0x0198:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r9.mRight
            if (r8 != r13) goto L_0x01aa
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r9.mLeft
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x01aa
            boolean r13 = r13.hasFinalValue()
            if (r13 == 0) goto L_0x01aa
        L_0x01a8:
            r13 = 1
            goto L_0x01ab
        L_0x01aa:
            r13 = 0
        L_0x01ab:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r14 = r9.getHorizontalDimensionBehaviour()
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r14 != r15) goto L_0x0204
            if (r10 == 0) goto L_0x01ba
            r15 = 8
            r17 = 0
            goto L_0x0208
        L_0x01ba:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r14 = r9.getHorizontalDimensionBehaviour()
            if (r14 != r15) goto L_0x01ff
            int r14 = r9.mMatchConstraintMaxWidth
            if (r14 < 0) goto L_0x01ff
            int r14 = r9.mMatchConstraintMinWidth
            if (r14 < 0) goto L_0x01ff
            int r14 = r9.getVisibility()
            r15 = 8
            if (r14 == r15) goto L_0x01e3
            int r14 = r9.mMatchConstraintDefaultWidth
            if (r14 != 0) goto L_0x01df
            float r14 = r9.getDimensionRatio()
            r17 = 0
            int r14 = (r14 > r17 ? 1 : (r14 == r17 ? 0 : -1))
            if (r14 != 0) goto L_0x0257
            goto L_0x01e5
        L_0x01df:
            r17 = 0
            goto L_0x0257
        L_0x01e3:
            r17 = 0
        L_0x01e5:
            boolean r14 = r9.isInHorizontalChain()
            if (r14 != 0) goto L_0x0257
            boolean r14 = r9.isInVirtualLayout()
            if (r14 != 0) goto L_0x0257
            if (r13 == 0) goto L_0x0257
            boolean r14 = r9.isInHorizontalChain()
            if (r14 != 0) goto L_0x0257
            int r14 = r18 + 1
            solveHorizontalMatchConstraint(r14, r0, r1, r9, r2)
            goto L_0x0257
        L_0x01ff:
            r15 = 8
            r17 = 0
            goto L_0x0257
        L_0x0204:
            r15 = 8
            r17 = 0
        L_0x0208:
            boolean r14 = r9.isMeasureRequested()
            if (r14 == 0) goto L_0x0210
            goto L_0x015e
        L_0x0210:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r9.mLeft
            if (r8 != r14) goto L_0x022d
            androidx.constraintlayout.core.widgets.ConstraintAnchor r15 = r9.mRight
            androidx.constraintlayout.core.widgets.ConstraintAnchor r15 = r15.mTarget
            if (r15 != 0) goto L_0x022d
            int r14 = r14.getMargin()
            int r14 = r14 + r7
            int r11 = r9.getWidth()
            int r11 = r11 + r14
            r9.setFinalHorizontal(r14, r11)
            int r12 = r18 + 1
            horizontalSolvingPass(r12, r9, r1, r2)
            goto L_0x0257
        L_0x022d:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r15 = r9.mRight
            if (r8 != r15) goto L_0x024a
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r14.mTarget
            if (r14 != 0) goto L_0x024a
            int r14 = r15.getMargin()
            int r12 = r7 - r14
            int r14 = r9.getWidth()
            int r11 = r12 - r14
            r9.setFinalHorizontal(r11, r12)
            int r14 = r18 + 1
            horizontalSolvingPass(r14, r9, r1, r2)
            goto L_0x0257
        L_0x024a:
            if (r13 == 0) goto L_0x0257
            boolean r14 = r9.isInHorizontalChain()
            if (r14 != 0) goto L_0x0257
            int r14 = r18 + 1
            solveHorizontalCenterConstraints(r14, r1, r9, r2)
        L_0x0257:
            goto L_0x015e
        L_0x0259:
            r19.markHorizontalSolvingPassDone()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.Direct.horizontalSolvingPass(int, androidx.constraintlayout.core.widgets.ConstraintWidget, androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measurer, boolean):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:119:0x01d4, code lost:
        if (r8.getDimensionRatio() == 0.0f) goto L_0x01db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x008b, code lost:
        r3 = r12.mBottom.mTarget;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x009b, code lost:
        r3 = r12.mTop.mTarget;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void verticalSolvingPass(int r18, androidx.constraintlayout.core.widgets.ConstraintWidget r19, androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measurer r20) {
        /*
            r0 = r19
            r1 = r20
            boolean r2 = r19.isVerticalSolvingPassDone()
            if (r2 == 0) goto L_0x000b
            return
        L_0x000b:
            int r2 = vcount
            r3 = 1
            int r2 = r2 + r3
            vcount = r2
            boolean r2 = r0 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r2 != 0) goto L_0x002f
            boolean r2 = r19.isMeasureRequested()
            if (r2 == 0) goto L_0x002f
            int r2 = r18 + 1
            boolean r2 = canMeasure(r2, r0)
            if (r2 == 0) goto L_0x002f
            androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure r2 = new androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure
            r2.<init>()
            int r4 = r18 + 1
            int r5 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.SELF_DIMENSIONS
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer.measure(r4, r0, r1, r2, r5)
        L_0x002f:
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r0.getAnchor(r2)
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r4 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r0.getAnchor(r4)
            int r5 = r2.getFinalValue()
            int r6 = r4.getFinalValue()
            java.util.HashSet r7 = r2.getDependents()
            r9 = 8
            if (r7 == 0) goto L_0x0143
            boolean r7 = r2.hasFinalValue()
            if (r7 == 0) goto L_0x0143
            java.util.HashSet r7 = r2.getDependents()
            java.util.Iterator r7 = r7.iterator()
        L_0x0059:
            boolean r11 = r7.hasNext()
            if (r11 == 0) goto L_0x0143
            java.lang.Object r11 = r7.next()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r11 = (androidx.constraintlayout.core.widgets.ConstraintAnchor) r11
            androidx.constraintlayout.core.widgets.ConstraintWidget r12 = r11.mOwner
            r13 = 0
            r14 = 0
            int r15 = r18 + 1
            boolean r15 = canMeasure(r15, r12)
            boolean r16 = r12.isMeasureRequested()
            if (r16 == 0) goto L_0x0087
            if (r15 == 0) goto L_0x0087
            androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure r16 = new androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure
            r16.<init>()
            r17 = r16
            int r3 = r18 + 1
            int r10 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.SELF_DIMENSIONS
            r8 = r17
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer.measure(r3, r12, r1, r8, r10)
        L_0x0087:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r12.mTop
            if (r11 != r3) goto L_0x0097
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r12.mBottom
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x0097
            boolean r3 = r3.hasFinalValue()
            if (r3 != 0) goto L_0x00a7
        L_0x0097:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r12.mBottom
            if (r11 != r3) goto L_0x00a9
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r12.mTop
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x00a9
            boolean r3 = r3.hasFinalValue()
            if (r3 == 0) goto L_0x00a9
        L_0x00a7:
            r3 = 1
            goto L_0x00aa
        L_0x00a9:
            r3 = 0
        L_0x00aa:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r8 = r12.getVerticalDimensionBehaviour()
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r10 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r8 != r10) goto L_0x00f0
            if (r15 == 0) goto L_0x00b5
            goto L_0x00f0
        L_0x00b5:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r8 = r12.getVerticalDimensionBehaviour()
            if (r8 != r10) goto L_0x0140
            int r8 = r12.mMatchConstraintMaxHeight
            if (r8 < 0) goto L_0x0140
            int r8 = r12.mMatchConstraintMinHeight
            if (r8 < 0) goto L_0x0140
            int r8 = r12.getVisibility()
            if (r8 == r9) goto L_0x00d6
            int r8 = r12.mMatchConstraintDefaultHeight
            if (r8 != 0) goto L_0x0140
            float r8 = r12.getDimensionRatio()
            r10 = 0
            int r8 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r8 != 0) goto L_0x0140
        L_0x00d6:
            boolean r8 = r12.isInVerticalChain()
            if (r8 != 0) goto L_0x0140
            boolean r8 = r12.isInVirtualLayout()
            if (r8 != 0) goto L_0x0140
            if (r3 == 0) goto L_0x0140
            boolean r8 = r12.isInVerticalChain()
            if (r8 != 0) goto L_0x0140
            int r8 = r18 + 1
            solveVerticalMatchConstraint(r8, r0, r1, r12)
            goto L_0x0140
        L_0x00f0:
            boolean r8 = r12.isMeasureRequested()
            if (r8 == 0) goto L_0x00f9
            r3 = 1
            goto L_0x0059
        L_0x00f9:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r12.mTop
            if (r11 != r8) goto L_0x0116
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r12.mBottom
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r10.mTarget
            if (r10 != 0) goto L_0x0116
            int r8 = r8.getMargin()
            int r8 = r8 + r5
            int r10 = r12.getHeight()
            int r10 = r10 + r8
            r12.setFinalVertical(r8, r10)
            int r13 = r18 + 1
            verticalSolvingPass(r13, r12, r1)
            goto L_0x0140
        L_0x0116:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r10 = r12.mBottom
            if (r11 != r10) goto L_0x0133
            androidx.constraintlayout.core.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 != 0) goto L_0x0133
            int r8 = r10.getMargin()
            int r8 = r5 - r8
            int r10 = r12.getHeight()
            int r10 = r8 - r10
            r12.setFinalVertical(r10, r8)
            int r13 = r18 + 1
            verticalSolvingPass(r13, r12, r1)
            goto L_0x0140
        L_0x0133:
            if (r3 == 0) goto L_0x0140
            boolean r8 = r12.isInVerticalChain()
            if (r8 != 0) goto L_0x0140
            int r8 = r18 + 1
            solveVerticalCenterConstraints(r8, r1, r12)
        L_0x0140:
            r3 = 1
            goto L_0x0059
        L_0x0143:
            boolean r3 = r0 instanceof androidx.constraintlayout.core.widgets.Guideline
            if (r3 == 0) goto L_0x0148
            return
        L_0x0148:
            java.util.HashSet r3 = r4.getDependents()
            if (r3 == 0) goto L_0x024b
            boolean r3 = r4.hasFinalValue()
            if (r3 == 0) goto L_0x024b
            java.util.HashSet r3 = r4.getDependents()
            java.util.Iterator r3 = r3.iterator()
        L_0x015c:
            boolean r7 = r3.hasNext()
            if (r7 == 0) goto L_0x024b
            java.lang.Object r7 = r3.next()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = (androidx.constraintlayout.core.widgets.ConstraintAnchor) r7
            androidx.constraintlayout.core.widgets.ConstraintWidget r8 = r7.mOwner
            int r10 = r18 + 1
            boolean r10 = canMeasure(r10, r8)
            boolean r11 = r8.isMeasureRequested()
            if (r11 == 0) goto L_0x0184
            if (r10 == 0) goto L_0x0184
            androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure r11 = new androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure
            r11.<init>()
            int r12 = r18 + 1
            int r13 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.SELF_DIMENSIONS
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer.measure(r12, r8, r1, r11, r13)
        L_0x0184:
            r11 = 0
            r12 = 0
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r8.mTop
            if (r7 != r13) goto L_0x0196
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r8.mBottom
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x0196
            boolean r13 = r13.hasFinalValue()
            if (r13 != 0) goto L_0x01a6
        L_0x0196:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r8.mBottom
            if (r7 != r13) goto L_0x01a8
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r8.mTop
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x01a8
            boolean r13 = r13.hasFinalValue()
            if (r13 == 0) goto L_0x01a8
        L_0x01a6:
            r13 = 1
            goto L_0x01a9
        L_0x01a8:
            r13 = 0
        L_0x01a9:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r14 = r8.getVerticalDimensionBehaviour()
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r14 != r15) goto L_0x01f7
            if (r10 == 0) goto L_0x01b5
            r15 = 0
            goto L_0x01f8
        L_0x01b5:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r14 = r8.getVerticalDimensionBehaviour()
            if (r14 != r15) goto L_0x01f5
            int r14 = r8.mMatchConstraintMaxHeight
            if (r14 < 0) goto L_0x01f5
            int r14 = r8.mMatchConstraintMinHeight
            if (r14 < 0) goto L_0x01f5
            int r14 = r8.getVisibility()
            if (r14 == r9) goto L_0x01da
            int r14 = r8.mMatchConstraintDefaultHeight
            if (r14 != 0) goto L_0x01d7
            float r14 = r8.getDimensionRatio()
            r15 = 0
            int r14 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
            if (r14 != 0) goto L_0x0247
            goto L_0x01db
        L_0x01d7:
            r15 = 0
            goto L_0x0247
        L_0x01da:
            r15 = 0
        L_0x01db:
            boolean r14 = r8.isInVerticalChain()
            if (r14 != 0) goto L_0x0247
            boolean r14 = r8.isInVirtualLayout()
            if (r14 != 0) goto L_0x0247
            if (r13 == 0) goto L_0x0247
            boolean r14 = r8.isInVerticalChain()
            if (r14 != 0) goto L_0x0247
            int r14 = r18 + 1
            solveVerticalMatchConstraint(r14, r0, r1, r8)
            goto L_0x0247
        L_0x01f5:
            r15 = 0
            goto L_0x0247
        L_0x01f7:
            r15 = 0
        L_0x01f8:
            boolean r14 = r8.isMeasureRequested()
            if (r14 == 0) goto L_0x0200
            goto L_0x015c
        L_0x0200:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r8.mTop
            if (r7 != r14) goto L_0x021d
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r8.mBottom
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r9.mTarget
            if (r9 != 0) goto L_0x021d
            int r9 = r14.getMargin()
            int r9 = r9 + r6
            int r11 = r8.getHeight()
            int r11 = r11 + r9
            r8.setFinalVertical(r9, r11)
            int r12 = r18 + 1
            verticalSolvingPass(r12, r8, r1)
            goto L_0x0247
        L_0x021d:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r8.mBottom
            if (r7 != r9) goto L_0x023a
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r14.mTarget
            if (r14 != 0) goto L_0x023a
            int r9 = r9.getMargin()
            int r9 = r6 - r9
            int r12 = r8.getHeight()
            int r11 = r9 - r12
            r8.setFinalVertical(r11, r9)
            int r12 = r18 + 1
            verticalSolvingPass(r12, r8, r1)
            goto L_0x0247
        L_0x023a:
            if (r13 == 0) goto L_0x0247
            boolean r9 = r8.isInVerticalChain()
            if (r9 != 0) goto L_0x0247
            int r9 = r18 + 1
            solveVerticalCenterConstraints(r9, r1, r8)
        L_0x0247:
            r9 = 8
            goto L_0x015c
        L_0x024b:
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.BASELINE
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r0.getAnchor(r3)
            java.util.HashSet r7 = r3.getDependents()
            if (r7 == 0) goto L_0x02b4
            boolean r7 = r3.hasFinalValue()
            if (r7 == 0) goto L_0x02b4
            int r7 = r3.getFinalValue()
            java.util.HashSet r8 = r3.getDependents()
            java.util.Iterator r8 = r8.iterator()
        L_0x0269:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x02b4
            java.lang.Object r9 = r8.next()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = (androidx.constraintlayout.core.widgets.ConstraintAnchor) r9
            androidx.constraintlayout.core.widgets.ConstraintWidget r10 = r9.mOwner
            int r11 = r18 + 1
            boolean r11 = canMeasure(r11, r10)
            boolean r12 = r10.isMeasureRequested()
            if (r12 == 0) goto L_0x0291
            if (r11 == 0) goto L_0x0291
            androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure r12 = new androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure
            r12.<init>()
            int r13 = r18 + 1
            int r14 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.SELF_DIMENSIONS
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer.measure(r13, r10, r1, r12, r14)
        L_0x0291:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r12 = r10.getVerticalDimensionBehaviour()
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r13 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r12 != r13) goto L_0x029b
            if (r11 == 0) goto L_0x02b3
        L_0x029b:
            boolean r12 = r10.isMeasureRequested()
            if (r12 == 0) goto L_0x02a2
            goto L_0x0269
        L_0x02a2:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r12 = r10.mBaseline
            if (r9 != r12) goto L_0x02b3
            int r12 = r9.getMargin()
            int r12 = r12 + r7
            r10.setFinalBaseline(r12)
            int r12 = r18 + 1
            verticalSolvingPass(r12, r10, r1)     // Catch:{ all -> 0x02b8 }
        L_0x02b3:
            goto L_0x0269
        L_0x02b4:
            r19.markVerticalSolvingPassDone()
            return
        L_0x02b8:
            r0 = move-exception
            r1 = r0
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.Direct.verticalSolvingPass(int, androidx.constraintlayout.core.widgets.ConstraintWidget, androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measurer):void");
    }

    private static void solveHorizontalCenterConstraints(int level, BasicMeasure.Measurer measurer, ConstraintWidget widget, boolean isRtl) {
        int d1;
        float bias = widget.getHorizontalBiasPercent();
        int start = widget.mLeft.mTarget.getFinalValue();
        int end = widget.mRight.mTarget.getFinalValue();
        int s1 = widget.mLeft.getMargin() + start;
        int s2 = end - widget.mRight.getMargin();
        if (start == end) {
            bias = 0.5f;
            s1 = start;
            s2 = end;
        }
        int width = widget.getWidth();
        int distance = (s2 - s1) - width;
        if (s1 > s2) {
            distance = (s1 - s2) - width;
        }
        if (distance > 0) {
            d1 = (int) ((((float) distance) * bias) + 0.5f);
        } else {
            d1 = (int) (((float) distance) * bias);
        }
        int x1 = s1 + d1;
        int x2 = x1 + width;
        if (s1 > s2) {
            x1 = s1 + d1;
            x2 = x1 - width;
        }
        widget.setFinalHorizontal(x1, x2);
        horizontalSolvingPass(level + 1, widget, measurer, isRtl);
    }

    private static void solveVerticalCenterConstraints(int level, BasicMeasure.Measurer measurer, ConstraintWidget widget) {
        int d1;
        float bias = widget.getVerticalBiasPercent();
        int start = widget.mTop.mTarget.getFinalValue();
        int end = widget.mBottom.mTarget.getFinalValue();
        int s1 = widget.mTop.getMargin() + start;
        int s2 = end - widget.mBottom.getMargin();
        if (start == end) {
            bias = 0.5f;
            s1 = start;
            s2 = end;
        }
        int height = widget.getHeight();
        int distance = (s2 - s1) - height;
        if (s1 > s2) {
            distance = (s1 - s2) - height;
        }
        if (distance > 0) {
            d1 = (int) ((((float) distance) * bias) + 0.5f);
        } else {
            d1 = (int) (((float) distance) * bias);
        }
        int y1 = s1 + d1;
        int y2 = y1 + height;
        if (s1 > s2) {
            y1 = s1 - d1;
            y2 = y1 - height;
        }
        widget.setFinalVertical(y1, y2);
        verticalSolvingPass(level + 1, widget, measurer);
    }

    private static void solveHorizontalMatchConstraint(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, ConstraintWidget widget, boolean isRtl) {
        int parentWidth;
        float bias = widget.getHorizontalBiasPercent();
        int s1 = widget.mLeft.mTarget.getFinalValue() + widget.mLeft.getMargin();
        int s2 = widget.mRight.mTarget.getFinalValue() - widget.mRight.getMargin();
        if (s2 >= s1) {
            int width = widget.getWidth();
            if (widget.getVisibility() != 8) {
                int i = widget.mMatchConstraintDefaultWidth;
                if (i == 2) {
                    if (layout instanceof ConstraintWidgetContainer) {
                        parentWidth = layout.getWidth();
                    } else {
                        parentWidth = layout.getParent().getWidth();
                    }
                    width = (int) (widget.getHorizontalBiasPercent() * 0.5f * ((float) parentWidth));
                } else if (i == 0) {
                    width = s2 - s1;
                }
                width = Math.max(widget.mMatchConstraintMinWidth, width);
                int i2 = widget.mMatchConstraintMaxWidth;
                if (i2 > 0) {
                    width = Math.min(i2, width);
                }
            }
            int x1 = s1 + ((int) ((((float) ((s2 - s1) - width)) * bias) + 0.5f));
            widget.setFinalHorizontal(x1, x1 + width);
            horizontalSolvingPass(level + 1, widget, measurer, isRtl);
        }
    }

    private static void solveVerticalMatchConstraint(int level, ConstraintWidget layout, BasicMeasure.Measurer measurer, ConstraintWidget widget) {
        int parentHeight;
        float bias = widget.getVerticalBiasPercent();
        int s1 = widget.mTop.mTarget.getFinalValue() + widget.mTop.getMargin();
        int s2 = widget.mBottom.mTarget.getFinalValue() - widget.mBottom.getMargin();
        if (s2 >= s1) {
            int height = widget.getHeight();
            if (widget.getVisibility() != 8) {
                int i = widget.mMatchConstraintDefaultHeight;
                if (i == 2) {
                    if (layout instanceof ConstraintWidgetContainer) {
                        parentHeight = layout.getHeight();
                    } else {
                        parentHeight = layout.getParent().getHeight();
                    }
                    height = (int) (bias * 0.5f * ((float) parentHeight));
                } else if (i == 0) {
                    height = s2 - s1;
                }
                height = Math.max(widget.mMatchConstraintMinHeight, height);
                int i2 = widget.mMatchConstraintMaxHeight;
                if (i2 > 0) {
                    height = Math.min(i2, height);
                }
            }
            int y1 = s1 + ((int) ((((float) ((s2 - s1) - height)) * bias) + 0.5f));
            widget.setFinalVertical(y1, y1 + height);
            verticalSolvingPass(level + 1, widget, measurer);
        }
    }

    private static boolean canMeasure(int level, ConstraintWidget layout) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2;
        ConstraintWidget.DimensionBehaviour horizontalBehaviour = layout.getHorizontalDimensionBehaviour();
        ConstraintWidget.DimensionBehaviour verticalBehaviour = layout.getVerticalDimensionBehaviour();
        ConstraintWidgetContainer parent = layout.getParent() != null ? (ConstraintWidgetContainer) layout.getParent() : null;
        if (parent == null || parent.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
        }
        if (parent == null || parent.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
        }
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.FIXED;
        boolean isHorizontalFixed = horizontalBehaviour == dimensionBehaviour3 || layout.isResolvedHorizontally() || horizontalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (horizontalBehaviour == (dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && layout.mMatchConstraintDefaultWidth == 0 && layout.mDimensionRatio == 0.0f && layout.hasDanglingDimension(0)) || (horizontalBehaviour == dimensionBehaviour2 && layout.mMatchConstraintDefaultWidth == 1 && layout.hasResolvedTargets(0, layout.getWidth()));
        boolean isVerticalFixed = verticalBehaviour == dimensionBehaviour3 || layout.isResolvedVertically() || verticalBehaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (verticalBehaviour == (dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) && layout.mMatchConstraintDefaultHeight == 0 && layout.mDimensionRatio == 0.0f && layout.hasDanglingDimension(1)) || (verticalBehaviour == dimensionBehaviour && layout.mMatchConstraintDefaultHeight == 1 && layout.hasResolvedTargets(1, layout.getHeight()));
        if (layout.mDimensionRatio > 0.0f && (isHorizontalFixed || isVerticalFixed)) {
            return true;
        }
        if (!isHorizontalFixed || !isVerticalFixed) {
            return false;
        }
        return true;
    }

    public static boolean solveChain(ConstraintWidgetContainer container, LinearSystem system, int orientation, int offset, ChainHead chainHead, boolean isChainSpread, boolean isChainSpreadInside, boolean isChainPacked) {
        ConstraintWidget widget;
        int i;
        ConstraintWidget next;
        boolean done;
        int current;
        float bias;
        ConstraintAnchor begin;
        boolean done2;
        BasicMeasure.Measure measure2;
        int totalSize;
        ConstraintWidget next2;
        if (isChainPacked) {
            return false;
        }
        if (orientation == 0) {
            if (!container.isResolvedHorizontally()) {
                return false;
            }
        } else if (!container.isResolvedVertically()) {
            return false;
        }
        boolean isRtl = container.isRtl();
        ConstraintWidget first = chainHead.getFirst();
        ConstraintWidget next3 = chainHead.getLast();
        ConstraintWidget firstVisibleWidget = chainHead.getFirstVisibleWidget();
        ConstraintWidget lastVisibleWidget = chainHead.getLastVisibleWidget();
        ConstraintWidget head = chainHead.getHead();
        ConstraintWidget widget2 = first;
        boolean done3 = false;
        ConstraintAnchor begin2 = first.mListAnchors[offset];
        ConstraintAnchor end = next3.mListAnchors[offset + 1];
        ConstraintAnchor constraintAnchor = begin2.mTarget;
        if (constraintAnchor == null) {
            ConstraintWidget constraintWidget = next3;
            ConstraintWidget constraintWidget2 = head;
            ConstraintAnchor constraintAnchor2 = begin2;
        } else if (end.mTarget == null) {
            ConstraintWidget constraintWidget3 = first;
            ConstraintWidget constraintWidget4 = next3;
            ConstraintWidget constraintWidget5 = head;
            ConstraintAnchor constraintAnchor3 = begin2;
        } else {
            if (!constraintAnchor.hasFinalValue()) {
                ConstraintWidget constraintWidget6 = next3;
                ConstraintWidget constraintWidget7 = head;
                ConstraintAnchor constraintAnchor4 = begin2;
            } else if (!end.mTarget.hasFinalValue()) {
                ConstraintWidget constraintWidget8 = first;
                ConstraintWidget constraintWidget9 = next3;
                ConstraintWidget constraintWidget10 = head;
                ConstraintAnchor constraintAnchor5 = begin2;
            } else {
                if (firstVisibleWidget == null) {
                    ConstraintWidget constraintWidget11 = next3;
                    ConstraintWidget constraintWidget12 = head;
                    ConstraintAnchor constraintAnchor6 = begin2;
                } else if (lastVisibleWidget == null) {
                    ConstraintWidget constraintWidget13 = first;
                    ConstraintWidget constraintWidget14 = next3;
                    ConstraintWidget constraintWidget15 = head;
                    ConstraintAnchor constraintAnchor7 = begin2;
                } else {
                    int startPoint = begin2.mTarget.getFinalValue() + firstVisibleWidget.mListAnchors[offset].getMargin();
                    int endPoint = end.mTarget.getFinalValue() - lastVisibleWidget.mListAnchors[offset + 1].getMargin();
                    int distance = endPoint - startPoint;
                    if (distance <= 0) {
                        return false;
                    }
                    int totalSize2 = 0;
                    BasicMeasure.Measure measure3 = new BasicMeasure.Measure();
                    int numWidgets = 0;
                    int numVisibleWidgets = 0;
                    while (!done3) {
                        boolean canMeasure = canMeasure(0 + 1, widget2);
                        if (!canMeasure) {
                            return false;
                        }
                        boolean z = canMeasure;
                        ConstraintWidget last = next3;
                        if (widget2.mListDimensionBehaviors[orientation] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            return false;
                        }
                        if (widget2.isMeasureRequested()) {
                            done2 = done3;
                            begin = begin2;
                            measure2 = measure3;
                            ConstraintWidgetContainer.measure(0 + 1, widget2, container.getMeasurer(), measure2, BasicMeasure.Measure.SELF_DIMENSIONS);
                        } else {
                            done2 = done3;
                            begin = begin2;
                            measure2 = measure3;
                        }
                        int totalSize3 = totalSize2 + widget2.mListAnchors[offset].getMargin();
                        if (orientation == 0) {
                            totalSize = totalSize3 + widget2.getWidth();
                        } else {
                            totalSize = totalSize3 + widget2.getHeight();
                        }
                        totalSize2 = totalSize + widget2.mListAnchors[offset + 1].getMargin();
                        numWidgets++;
                        if (widget2.getVisibility() != 8) {
                            numVisibleWidgets++;
                        } else {
                            int i2 = numVisibleWidgets;
                        }
                        ConstraintAnchor nextAnchor = widget2.mListAnchors[offset + 1].mTarget;
                        if (nextAnchor != null) {
                            next2 = nextAnchor.mOwner;
                            ConstraintAnchor[] constraintAnchorArr = next2.mListAnchors;
                            ConstraintAnchor constraintAnchor8 = nextAnchor;
                            if (constraintAnchorArr[offset].mTarget == null || constraintAnchorArr[offset].mTarget.mOwner != widget2) {
                                next2 = null;
                            }
                        } else {
                            next2 = null;
                        }
                        if (next2 != null) {
                            widget2 = next2;
                            done3 = done2;
                        } else {
                            done3 = true;
                        }
                        measure3 = measure2;
                        next3 = last;
                        begin2 = begin;
                    }
                    ConstraintWidget last2 = next3;
                    boolean done4 = done3;
                    ConstraintAnchor constraintAnchor9 = begin2;
                    BasicMeasure.Measure measure4 = measure3;
                    int numWidgets2 = numWidgets;
                    int numVisibleWidgets2 = numVisibleWidgets;
                    if (numVisibleWidgets2 == 0 || numVisibleWidgets2 != numWidgets2 || distance < totalSize2) {
                        return false;
                    }
                    int gap = distance - totalSize2;
                    int i3 = numWidgets2;
                    if (isChainSpread) {
                        gap /= numVisibleWidgets2 + 1;
                        widget = widget2;
                        i = 1;
                    } else if (!isChainSpreadInside) {
                        widget = widget2;
                        i = 1;
                    } else if (numVisibleWidgets2 > 2) {
                        widget = widget2;
                        i = 1;
                        gap = (gap / numVisibleWidgets2) - 1;
                    } else {
                        widget = widget2;
                        i = 1;
                    }
                    if (numVisibleWidgets2 == i) {
                        if (orientation == 0) {
                            bias = head.getHorizontalBiasPercent();
                        } else {
                            bias = head.getVerticalBiasPercent();
                        }
                        ConstraintWidget constraintWidget16 = head;
                        int p1 = (int) (((float) startPoint) + 0.5f + (((float) gap) * bias));
                        if (orientation == 0) {
                            firstVisibleWidget.setFinalHorizontal(p1, firstVisibleWidget.getWidth() + p1);
                        } else {
                            firstVisibleWidget.setFinalVertical(p1, firstVisibleWidget.getHeight() + p1);
                        }
                        float f = bias;
                        horizontalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer(), isRtl);
                        return true;
                    }
                    if (isChainSpread) {
                        boolean done5 = false;
                        int current2 = startPoint + gap;
                        ConstraintWidget widget3 = first;
                        while (!done5) {
                            boolean done6 = done5;
                            ConstraintWidget first2 = first;
                            if (widget3.getVisibility() != 8) {
                                int current3 = current2 + widget3.mListAnchors[offset].getMargin();
                                if (orientation == 0) {
                                    widget3.setFinalHorizontal(current3, widget3.getWidth() + current3);
                                    horizontalSolvingPass(0 + 1, widget3, container.getMeasurer(), isRtl);
                                    current = current3 + widget3.getWidth();
                                } else {
                                    widget3.setFinalVertical(current3, widget3.getHeight() + current3);
                                    verticalSolvingPass(0 + 1, widget3, container.getMeasurer());
                                    current = current3 + widget3.getHeight();
                                }
                                current2 = current + widget3.mListAnchors[offset + 1].getMargin() + gap;
                            } else if (orientation == 0) {
                                widget3.setFinalHorizontal(current2, current2);
                                horizontalSolvingPass(0 + 1, widget3, container.getMeasurer(), isRtl);
                            } else {
                                widget3.setFinalVertical(current2, current2);
                                verticalSolvingPass(0 + 1, widget3, container.getMeasurer());
                            }
                            widget3.addToSolver(system, false);
                            ConstraintAnchor nextAnchor2 = widget3.mListAnchors[offset + 1].mTarget;
                            if (nextAnchor2 != null) {
                                ConstraintWidget next4 = nextAnchor2.mOwner;
                                ConstraintAnchor constraintAnchor10 = nextAnchor2;
                                ConstraintAnchor[] constraintAnchorArr2 = next4.mListAnchors;
                                ConstraintWidget next5 = next4;
                                if (constraintAnchorArr2[offset].mTarget == null || constraintAnchorArr2[offset].mTarget.mOwner != widget3) {
                                    next = null;
                                } else {
                                    next = next5;
                                }
                            } else {
                                next = null;
                            }
                            if (next != null) {
                                widget3 = next;
                                done = done6;
                            } else {
                                done = true;
                            }
                            done5 = done;
                            first = first2;
                        }
                        boolean z2 = done5;
                        ConstraintWidget constraintWidget17 = first;
                        return true;
                    }
                    if (!isChainSpreadInside) {
                        ConstraintWidget constraintWidget18 = widget;
                        boolean z3 = done4;
                        return true;
                    } else if (numVisibleWidgets2 != 2) {
                        return false;
                    } else {
                        if (orientation == 0) {
                            firstVisibleWidget.setFinalHorizontal(startPoint, firstVisibleWidget.getWidth() + startPoint);
                            lastVisibleWidget.setFinalHorizontal(endPoint - lastVisibleWidget.getWidth(), endPoint);
                            horizontalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer(), isRtl);
                            horizontalSolvingPass(0 + 1, lastVisibleWidget, container.getMeasurer(), isRtl);
                            return true;
                        }
                        firstVisibleWidget.setFinalVertical(startPoint, firstVisibleWidget.getHeight() + startPoint);
                        lastVisibleWidget.setFinalVertical(endPoint - lastVisibleWidget.getHeight(), endPoint);
                        verticalSolvingPass(0 + 1, firstVisibleWidget, container.getMeasurer());
                        verticalSolvingPass(0 + 1, lastVisibleWidget, container.getMeasurer());
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }
}
