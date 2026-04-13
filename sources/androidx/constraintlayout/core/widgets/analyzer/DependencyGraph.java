package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import com.meituan.robust.Constants;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class DependencyGraph {
    private static final boolean USE_GROUPS = true;
    private ConstraintWidgetContainer container;
    private ConstraintWidgetContainer mContainer;
    ArrayList<RunGroup> mGroups = new ArrayList<>();
    private BasicMeasure.Measure mMeasure = new BasicMeasure.Measure();
    private BasicMeasure.Measurer mMeasurer = null;
    private boolean mNeedBuildGraph = true;
    private boolean mNeedRedoMeasures = true;
    private ArrayList<WidgetRun> mRuns = new ArrayList<>();
    private ArrayList<RunGroup> runGroups = new ArrayList<>();

    public DependencyGraph(ConstraintWidgetContainer container2) {
        this.container = container2;
        this.mContainer = container2;
    }

    public void setMeasurer(BasicMeasure.Measurer measurer) {
        this.mMeasurer = measurer;
    }

    private int computeWrap(ConstraintWidgetContainer container2, int orientation) {
        int count = this.mGroups.size();
        long wrapSize = 0;
        for (int i = 0; i < count; i++) {
            wrapSize = Math.max(wrapSize, this.mGroups.get(i).computeWrapSize(container2, orientation));
        }
        return (int) wrapSize;
    }

    public void defineTerminalWidgets(ConstraintWidget.DimensionBehaviour horizontalBehavior, ConstraintWidget.DimensionBehaviour verticalBehavior) {
        if (this.mNeedBuildGraph) {
            buildGraph();
            boolean hasBarrier = false;
            Iterator<ConstraintWidget> it = this.container.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget widget = it.next();
                boolean[] zArr = widget.isTerminalWidget;
                zArr[0] = true;
                zArr[1] = true;
                if (widget instanceof Barrier) {
                    hasBarrier = true;
                }
            }
            if (!hasBarrier) {
                Iterator<RunGroup> it2 = this.mGroups.iterator();
                while (it2.hasNext()) {
                    RunGroup group = it2.next();
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                    group.defineTerminalWidgets(horizontalBehavior == dimensionBehaviour, verticalBehavior == dimensionBehaviour);
                }
            }
        }
    }

    public boolean directMeasure(boolean optimizeWrap) {
        boolean optimizeWrap2 = optimizeWrap & true;
        if (this.mNeedBuildGraph || this.mNeedRedoMeasures) {
            Iterator<ConstraintWidget> it = this.container.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget widget = it.next();
                widget.ensureWidgetRuns();
                widget.measured = false;
                widget.horizontalRun.reset();
                widget.verticalRun.reset();
            }
            this.container.ensureWidgetRuns();
            ConstraintWidgetContainer constraintWidgetContainer = this.container;
            constraintWidgetContainer.measured = false;
            constraintWidgetContainer.horizontalRun.reset();
            this.container.verticalRun.reset();
            this.mNeedRedoMeasures = false;
        }
        if (basicMeasureWidgets(this.mContainer)) {
            return false;
        }
        this.container.setX(0);
        this.container.setY(0);
        ConstraintWidget.DimensionBehaviour originalHorizontalDimension = this.container.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour originalVerticalDimension = this.container.getDimensionBehaviour(1);
        if (this.mNeedBuildGraph) {
            buildGraph();
        }
        int x1 = this.container.getX();
        int y1 = this.container.getY();
        this.container.horizontalRun.start.resolve(x1);
        this.container.verticalRun.start.resolve(y1);
        measureWidgets();
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (originalHorizontalDimension == dimensionBehaviour || originalVerticalDimension == dimensionBehaviour) {
            if (optimizeWrap2) {
                Iterator<WidgetRun> it2 = this.mRuns.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (!it2.next().supportsWrapComputation()) {
                            optimizeWrap2 = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            if (optimizeWrap2 && originalHorizontalDimension == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer2 = this.container;
                constraintWidgetContainer2.setWidth(computeWrap(constraintWidgetContainer2, 0));
                ConstraintWidgetContainer constraintWidgetContainer3 = this.container;
                constraintWidgetContainer3.horizontalRun.dimension.resolve(constraintWidgetContainer3.getWidth());
            }
            if (optimizeWrap2 && originalVerticalDimension == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer4 = this.container;
                constraintWidgetContainer4.setHeight(computeWrap(constraintWidgetContainer4, 1));
                ConstraintWidgetContainer constraintWidgetContainer5 = this.container;
                constraintWidgetContainer5.verticalRun.dimension.resolve(constraintWidgetContainer5.getHeight());
            }
        }
        boolean checkRoot = false;
        ConstraintWidgetContainer constraintWidgetContainer6 = this.container;
        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidgetContainer6.mListDimensionBehaviors;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
        ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.FIXED;
        if (dimensionBehaviour2 == dimensionBehaviour3 || dimensionBehaviourArr[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
            int x2 = constraintWidgetContainer6.getWidth() + x1;
            this.container.horizontalRun.end.resolve(x2);
            this.container.horizontalRun.dimension.resolve(x2 - x1);
            measureWidgets();
            ConstraintWidgetContainer constraintWidgetContainer7 = this.container;
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidgetContainer7.mListDimensionBehaviors;
            if (dimensionBehaviourArr2[1] == dimensionBehaviour3 || dimensionBehaviourArr2[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int y2 = constraintWidgetContainer7.getHeight() + y1;
                this.container.verticalRun.end.resolve(y2);
                this.container.verticalRun.dimension.resolve(y2 - y1);
            }
            measureWidgets();
            checkRoot = true;
        }
        Iterator<WidgetRun> it3 = this.mRuns.iterator();
        while (it3.hasNext()) {
            WidgetRun run = it3.next();
            if (run.widget != this.container || run.resolved) {
                run.applyToWidget();
            }
        }
        boolean allResolved = true;
        Iterator<WidgetRun> it4 = this.mRuns.iterator();
        while (true) {
            if (!it4.hasNext()) {
                break;
            }
            WidgetRun run2 = it4.next();
            if (checkRoot || run2.widget != this.container) {
                if (run2.start.resolved) {
                    if (run2.end.resolved || (run2 instanceof GuidelineReference)) {
                        if (!run2.dimension.resolved && !(run2 instanceof ChainRun) && !(run2 instanceof GuidelineReference)) {
                            allResolved = false;
                            break;
                        }
                    } else {
                        allResolved = false;
                        break;
                    }
                } else {
                    allResolved = false;
                    break;
                }
            }
        }
        this.container.setHorizontalDimensionBehaviour(originalHorizontalDimension);
        this.container.setVerticalDimensionBehaviour(originalVerticalDimension);
        return allResolved;
    }

    public boolean directMeasureSetup(boolean optimizeWrap) {
        if (this.mNeedBuildGraph) {
            Iterator<ConstraintWidget> it = this.container.mChildren.iterator();
            while (it.hasNext()) {
                ConstraintWidget widget = it.next();
                widget.ensureWidgetRuns();
                widget.measured = false;
                HorizontalWidgetRun horizontalWidgetRun = widget.horizontalRun;
                horizontalWidgetRun.dimension.resolved = false;
                horizontalWidgetRun.resolved = false;
                horizontalWidgetRun.reset();
                VerticalWidgetRun verticalWidgetRun = widget.verticalRun;
                verticalWidgetRun.dimension.resolved = false;
                verticalWidgetRun.resolved = false;
                verticalWidgetRun.reset();
            }
            this.container.ensureWidgetRuns();
            ConstraintWidgetContainer constraintWidgetContainer = this.container;
            constraintWidgetContainer.measured = false;
            HorizontalWidgetRun horizontalWidgetRun2 = constraintWidgetContainer.horizontalRun;
            horizontalWidgetRun2.dimension.resolved = false;
            horizontalWidgetRun2.resolved = false;
            horizontalWidgetRun2.reset();
            VerticalWidgetRun verticalWidgetRun2 = this.container.verticalRun;
            verticalWidgetRun2.dimension.resolved = false;
            verticalWidgetRun2.resolved = false;
            verticalWidgetRun2.reset();
            buildGraph();
        }
        if (basicMeasureWidgets(this.mContainer)) {
            return false;
        }
        this.container.setX(0);
        this.container.setY(0);
        this.container.horizontalRun.start.resolve(0);
        this.container.verticalRun.start.resolve(0);
        return true;
    }

    public boolean directMeasureWithOrientation(boolean optimizeWrap, int orientation) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        boolean optimizeWrap2 = optimizeWrap & true;
        ConstraintWidget.DimensionBehaviour originalHorizontalDimension = this.container.getDimensionBehaviour(0);
        ConstraintWidget.DimensionBehaviour originalVerticalDimension = this.container.getDimensionBehaviour(1);
        int x1 = this.container.getX();
        int y1 = this.container.getY();
        if (optimizeWrap2 && (originalHorizontalDimension == (dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) || originalVerticalDimension == dimensionBehaviour)) {
            Iterator<WidgetRun> it = this.mRuns.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                WidgetRun run = it.next();
                if (run.orientation == orientation && !run.supportsWrapComputation()) {
                    optimizeWrap2 = false;
                    break;
                }
            }
            if (orientation == 0) {
                if (optimizeWrap2 && originalHorizontalDimension == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    this.container.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                    ConstraintWidgetContainer constraintWidgetContainer = this.container;
                    constraintWidgetContainer.setWidth(computeWrap(constraintWidgetContainer, 0));
                    ConstraintWidgetContainer constraintWidgetContainer2 = this.container;
                    constraintWidgetContainer2.horizontalRun.dimension.resolve(constraintWidgetContainer2.getWidth());
                }
            } else if (optimizeWrap2 && originalVerticalDimension == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                this.container.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                ConstraintWidgetContainer constraintWidgetContainer3 = this.container;
                constraintWidgetContainer3.setHeight(computeWrap(constraintWidgetContainer3, 1));
                ConstraintWidgetContainer constraintWidgetContainer4 = this.container;
                constraintWidgetContainer4.verticalRun.dimension.resolve(constraintWidgetContainer4.getHeight());
            }
        }
        boolean checkRoot = false;
        if (orientation == 0) {
            ConstraintWidgetContainer constraintWidgetContainer5 = this.container;
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidgetContainer5.mListDimensionBehaviors;
            if (dimensionBehaviourArr[0] == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviourArr[0] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int x2 = constraintWidgetContainer5.getWidth() + x1;
                this.container.horizontalRun.end.resolve(x2);
                this.container.horizontalRun.dimension.resolve(x2 - x1);
                checkRoot = true;
            }
        } else {
            ConstraintWidgetContainer constraintWidgetContainer6 = this.container;
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidgetContainer6.mListDimensionBehaviors;
            if (dimensionBehaviourArr2[1] == ConstraintWidget.DimensionBehaviour.FIXED || dimensionBehaviourArr2[1] == ConstraintWidget.DimensionBehaviour.MATCH_PARENT) {
                int y2 = constraintWidgetContainer6.getHeight() + y1;
                this.container.verticalRun.end.resolve(y2);
                this.container.verticalRun.dimension.resolve(y2 - y1);
                checkRoot = true;
            }
        }
        measureWidgets();
        Iterator<WidgetRun> it2 = this.mRuns.iterator();
        while (it2.hasNext()) {
            WidgetRun run2 = it2.next();
            if (run2.orientation == orientation && (run2.widget != this.container || run2.resolved)) {
                run2.applyToWidget();
            }
        }
        boolean allResolved = true;
        Iterator<WidgetRun> it3 = this.mRuns.iterator();
        while (true) {
            if (!it3.hasNext()) {
                break;
            }
            WidgetRun run3 = it3.next();
            if (run3.orientation == orientation && (checkRoot || run3.widget != this.container)) {
                if (run3.start.resolved) {
                    if (run3.end.resolved) {
                        if (!(run3 instanceof ChainRun) && !run3.dimension.resolved) {
                            allResolved = false;
                            break;
                        }
                    } else {
                        allResolved = false;
                        break;
                    }
                } else {
                    allResolved = false;
                    break;
                }
            }
        }
        this.container.setHorizontalDimensionBehaviour(originalHorizontalDimension);
        this.container.setVerticalDimensionBehaviour(originalVerticalDimension);
        return allResolved;
    }

    private void measure(ConstraintWidget widget, ConstraintWidget.DimensionBehaviour horizontalBehavior, int horizontalDimension, ConstraintWidget.DimensionBehaviour verticalBehavior, int verticalDimension) {
        BasicMeasure.Measure measure = this.mMeasure;
        measure.horizontalBehavior = horizontalBehavior;
        measure.verticalBehavior = verticalBehavior;
        measure.horizontalDimension = horizontalDimension;
        measure.verticalDimension = verticalDimension;
        this.mMeasurer.measure(widget, measure);
        widget.setWidth(this.mMeasure.measuredWidth);
        widget.setHeight(this.mMeasure.measuredHeight);
        widget.setHasBaseline(this.mMeasure.measuredHasBaseline);
        widget.setBaselineDistance(this.mMeasure.measuredBaseline);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0292, code lost:
        r4 = r0.mListDimensionBehaviors;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean basicMeasureWidgets(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r17) {
        /*
            r16 = this;
            r0 = r17
            java.util.ArrayList<androidx.constraintlayout.core.widgets.ConstraintWidget> r1 = r0.mChildren
            java.util.Iterator r1 = r1.iterator()
        L_0x0008:
            boolean r2 = r1.hasNext()
            r3 = 0
            if (r2 == 0) goto L_0x034a
            java.lang.Object r2 = r1.next()
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = (androidx.constraintlayout.core.widgets.ConstraintWidget) r2
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r2.mListDimensionBehaviors
            r5 = r4[r3]
            r10 = 1
            r4 = r4[r10]
            int r6 = r2.getVisibility()
            r7 = 8
            if (r6 != r7) goto L_0x0027
            r2.measured = r10
            goto L_0x0008
        L_0x0027:
            float r6 = r2.mMatchConstraintPercentWidth
            r11 = 1065353216(0x3f800000, float:1.0)
            int r6 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
            r7 = 2
            if (r6 >= 0) goto L_0x0036
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 != r6) goto L_0x0036
            r2.mMatchConstraintDefaultWidth = r7
        L_0x0036:
            float r6 = r2.mMatchConstraintPercentHeight
            int r6 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
            if (r6 >= 0) goto L_0x0042
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 != r6) goto L_0x0042
            r2.mMatchConstraintDefaultHeight = r7
        L_0x0042:
            float r6 = r2.getDimensionRatio()
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            r8 = 3
            if (r6 <= 0) goto L_0x0078
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 != r6) goto L_0x005b
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r4 == r9) goto L_0x0058
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r4 != r9) goto L_0x005b
        L_0x0058:
            r2.mMatchConstraintDefaultWidth = r8
            goto L_0x0078
        L_0x005b:
            if (r4 != r6) goto L_0x0068
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r5 == r9) goto L_0x0065
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r5 != r9) goto L_0x0068
        L_0x0065:
            r2.mMatchConstraintDefaultHeight = r8
            goto L_0x0078
        L_0x0068:
            if (r5 != r6) goto L_0x0078
            if (r4 != r6) goto L_0x0078
            int r6 = r2.mMatchConstraintDefaultWidth
            if (r6 != 0) goto L_0x0072
            r2.mMatchConstraintDefaultWidth = r8
        L_0x0072:
            int r6 = r2.mMatchConstraintDefaultHeight
            if (r6 != 0) goto L_0x0078
            r2.mMatchConstraintDefaultHeight = r8
        L_0x0078:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r5 != r6) goto L_0x0090
            int r9 = r2.mMatchConstraintDefaultWidth
            if (r9 != r10) goto L_0x0090
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r2.mLeft
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r9.mTarget
            if (r9 == 0) goto L_0x008c
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r2.mRight
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r9.mTarget
            if (r9 != 0) goto L_0x0090
        L_0x008c:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r12 = r5
            goto L_0x0091
        L_0x0090:
            r12 = r5
        L_0x0091:
            if (r4 != r6) goto L_0x00a7
            int r5 = r2.mMatchConstraintDefaultHeight
            if (r5 != r10) goto L_0x00a7
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r2.mTop
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x00a3
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r2.mBottom
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 != 0) goto L_0x00a7
        L_0x00a3:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r13 = r4
            goto L_0x00a8
        L_0x00a7:
            r13 = r4
        L_0x00a8:
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r4 = r2.horizontalRun
            r4.dimensionBehavior = r12
            int r5 = r2.mMatchConstraintDefaultWidth
            r4.matchConstraintsType = r5
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r4 = r2.verticalRun
            r4.dimensionBehavior = r13
            int r9 = r2.mMatchConstraintDefaultHeight
            r4.matchConstraintsType = r9
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
            if (r12 == r4) goto L_0x00c4
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r14 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r12 == r14) goto L_0x00c4
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r14 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r12 != r14) goto L_0x00d0
        L_0x00c4:
            if (r13 == r4) goto L_0x02f5
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r14 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r13 == r14) goto L_0x02f5
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r14 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r13 != r14) goto L_0x00d0
            goto L_0x02f5
        L_0x00d0:
            r14 = 1056964608(0x3f000000, float:0.5)
            if (r12 != r6) goto L_0x01a3
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r13 == r15) goto L_0x00dc
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r13 != r11) goto L_0x01a3
        L_0x00dc:
            if (r5 != r8) goto L_0x0119
            if (r13 != r15) goto L_0x00ea
            r7 = 0
            r9 = 0
            r4 = r16
            r5 = r2
            r6 = r15
            r8 = r15
            r4.measure(r5, r6, r7, r8, r9)
        L_0x00ea:
            int r3 = r2.getHeight()
            float r4 = (float) r3
            float r5 = r2.mDimensionRatio
            float r4 = r4 * r5
            float r4 = r4 + r14
            int r11 = (int) r4
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r4 = r16
            r5 = r2
            r6 = r8
            r7 = r11
            r9 = r3
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r4 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getWidth()
            r4.resolve(r5)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r4 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getHeight()
            r4.resolve(r5)
            r2.measured = r10
            goto L_0x0008
        L_0x0119:
            if (r5 != r10) goto L_0x0131
            r7 = 0
            r9 = 0
            r4 = r16
            r5 = r2
            r6 = r15
            r8 = r13
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r3 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r4 = r2.getWidth()
            r3.wrapValue = r4
            goto L_0x0008
        L_0x0131:
            if (r5 != r7) goto L_0x0171
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r11 = r0.mListDimensionBehaviors
            r15 = r11[r3]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r15 == r7) goto L_0x013f
            r11 = r11[r3]
            if (r11 != r4) goto L_0x01a3
        L_0x013f:
            float r3 = r2.mMatchConstraintPercentWidth
            int r4 = r17.getWidth()
            float r4 = (float) r4
            float r4 = r4 * r3
            float r4 = r4 + r14
            int r11 = (int) r4
            int r14 = r2.getHeight()
            r4 = r16
            r5 = r2
            r6 = r7
            r7 = r11
            r8 = r13
            r9 = r14
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r4 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getWidth()
            r4.resolve(r5)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r4 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getHeight()
            r4.resolve(r5)
            r2.measured = r10
            goto L_0x0008
        L_0x0171:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r2.mListAnchors
            r11 = r7[r3]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r11 = r11.mTarget
            if (r11 == 0) goto L_0x017f
            r7 = r7[r10]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = r7.mTarget
            if (r7 != 0) goto L_0x01a3
        L_0x017f:
            r7 = 0
            r9 = 0
            r4 = r16
            r5 = r2
            r6 = r15
            r8 = r13
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r3 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r4 = r2.getWidth()
            r3.resolve(r4)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r3 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r4 = r2.getHeight()
            r3.resolve(r4)
            r2.measured = r10
            goto L_0x0008
        L_0x01a3:
            if (r13 != r6) goto L_0x0284
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r12 == r11) goto L_0x01ad
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r12 != r7) goto L_0x0284
        L_0x01ad:
            if (r9 != r8) goto L_0x01f8
            if (r12 != r11) goto L_0x01bb
            r7 = 0
            r9 = 0
            r4 = r16
            r5 = r2
            r6 = r11
            r8 = r11
            r4.measure(r5, r6, r7, r8, r9)
        L_0x01bb:
            int r3 = r2.getWidth()
            float r4 = r2.mDimensionRatio
            int r5 = r2.getDimensionRatioSide()
            r6 = -1
            if (r5 != r6) goto L_0x01ce
            r5 = 1065353216(0x3f800000, float:1.0)
            float r4 = r5 / r4
            r11 = r4
            goto L_0x01cf
        L_0x01ce:
            r11 = r4
        L_0x01cf:
            float r4 = (float) r3
            float r4 = r4 * r11
            float r4 = r4 + r14
            int r14 = (int) r4
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r4 = r16
            r5 = r2
            r6 = r8
            r7 = r3
            r9 = r14
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r4 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getWidth()
            r4.resolve(r5)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r4 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getHeight()
            r4.resolve(r5)
            r2.measured = r10
            goto L_0x0008
        L_0x01f8:
            if (r9 != r10) goto L_0x0210
            r7 = 0
            r9 = 0
            r4 = r16
            r5 = r2
            r6 = r12
            r8 = r11
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r3 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r4 = r2.getHeight()
            r3.wrapValue = r4
            goto L_0x0008
        L_0x0210:
            r7 = 2
            if (r9 != r7) goto L_0x0251
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r7 = r0.mListDimensionBehaviors
            r8 = r7[r10]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r8 == r11) goto L_0x021f
            r7 = r7[r10]
            if (r7 != r4) goto L_0x0284
        L_0x021f:
            float r3 = r2.mMatchConstraintPercentHeight
            int r15 = r2.getWidth()
            int r4 = r17.getHeight()
            float r4 = (float) r4
            float r4 = r4 * r3
            float r4 = r4 + r14
            int r14 = (int) r4
            r4 = r16
            r5 = r2
            r6 = r12
            r7 = r15
            r8 = r11
            r9 = r14
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r4 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getWidth()
            r4.resolve(r5)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r4 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getHeight()
            r4.resolve(r5)
            r2.measured = r10
            goto L_0x0008
        L_0x0251:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r2.mListAnchors
            r7 = 2
            r15 = r4[r7]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = r15.mTarget
            if (r7 == 0) goto L_0x0260
            r4 = r4[r8]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 != 0) goto L_0x0284
        L_0x0260:
            r7 = 0
            r9 = 0
            r4 = r16
            r5 = r2
            r6 = r11
            r8 = r13
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r3 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r4 = r2.getWidth()
            r3.resolve(r4)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r3 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r4 = r2.getHeight()
            r3.resolve(r4)
            r2.measured = r10
            goto L_0x0008
        L_0x0284:
            if (r12 != r6) goto L_0x02f3
            if (r13 != r6) goto L_0x02f3
            if (r5 == r10) goto L_0x02d4
            if (r9 != r10) goto L_0x028d
            goto L_0x02d4
        L_0x028d:
            r4 = 2
            if (r9 != r4) goto L_0x02f3
            if (r5 != r4) goto L_0x02f3
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r3 = r4[r3]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            if (r3 != r8) goto L_0x02f3
            r3 = r4[r10]
            if (r3 != r8) goto L_0x02f3
            float r3 = r2.mMatchConstraintPercentWidth
            float r11 = r2.mMatchConstraintPercentHeight
            int r4 = r17.getWidth()
            float r4 = (float) r4
            float r4 = r4 * r3
            float r4 = r4 + r14
            int r15 = (int) r4
            int r4 = r17.getHeight()
            float r4 = (float) r4
            float r4 = r4 * r11
            float r4 = r4 + r14
            int r14 = (int) r4
            r4 = r16
            r5 = r2
            r6 = r8
            r7 = r15
            r9 = r14
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r4 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getWidth()
            r4.resolve(r5)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r4 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getHeight()
            r4.resolve(r5)
            r2.measured = r10
            goto L_0x02f3
        L_0x02d4:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r7 = 0
            r9 = 0
            r4 = r16
            r5 = r2
            r6 = r8
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r3 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r4 = r2.getWidth()
            r3.wrapValue = r4
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r3 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r4 = r2.getHeight()
            r3.wrapValue = r4
        L_0x02f3:
            goto L_0x0008
        L_0x02f5:
            int r3 = r2.getWidth()
            if (r12 != r4) goto L_0x030c
            int r5 = r17.getWidth()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r2.mLeft
            int r6 = r6.mMargin
            int r5 = r5 - r6
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r2.mRight
            int r6 = r6.mMargin
            int r3 = r5 - r6
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
        L_0x030c:
            int r5 = r2.getHeight()
            if (r13 != r4) goto L_0x0325
            int r4 = r17.getHeight()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r2.mTop
            int r6 = r6.mMargin
            int r4 = r4 - r6
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r2.mBottom
            int r6 = r6.mMargin
            int r5 = r4 - r6
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r13 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r11 = r5
            goto L_0x0326
        L_0x0325:
            r11 = r5
        L_0x0326:
            r4 = r16
            r5 = r2
            r6 = r12
            r7 = r3
            r8 = r13
            r9 = r11
            r4.measure(r5, r6, r7, r8, r9)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r4 = r2.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getWidth()
            r4.resolve(r5)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r4 = r2.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r4 = r4.dimension
            int r5 = r2.getHeight()
            r4.resolve(r5)
            r2.measured = r10
            goto L_0x0008
        L_0x034a:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.DependencyGraph.basicMeasureWidgets(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer):boolean");
    }

    public void measureWidgets() {
        DimensionDependency dimensionDependency;
        Iterator<ConstraintWidget> it = this.container.mChildren.iterator();
        while (it.hasNext()) {
            ConstraintWidget widget = it.next();
            if (!widget.measured) {
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = widget.mListDimensionBehaviors;
                boolean z = false;
                ConstraintWidget.DimensionBehaviour horiz = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour vert = dimensionBehaviourArr[1];
                int horizMatchConstraintsType = widget.mMatchConstraintDefaultWidth;
                int vertMatchConstraintsType = widget.mMatchConstraintDefaultHeight;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                boolean horizWrap = horiz == dimensionBehaviour || (horiz == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && horizMatchConstraintsType == 1);
                if (vert == dimensionBehaviour || (vert == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && vertMatchConstraintsType == 1)) {
                    z = true;
                }
                boolean vertWrap = z;
                DimensionDependency dimensionDependency2 = widget.horizontalRun.dimension;
                boolean horizResolved = dimensionDependency2.resolved;
                DimensionDependency dimensionDependency3 = widget.verticalRun.dimension;
                boolean vertResolved = dimensionDependency3.resolved;
                if (!horizResolved || !vertResolved) {
                    boolean vertResolved2 = vertResolved;
                    if (horizResolved && vertWrap) {
                        measure(widget, ConstraintWidget.DimensionBehaviour.FIXED, dimensionDependency2.value, dimensionBehaviour, dimensionDependency3.value);
                        if (vert == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            widget.verticalRun.dimension.wrapValue = widget.getHeight();
                        } else {
                            widget.verticalRun.dimension.resolve(widget.getHeight());
                            widget.measured = true;
                        }
                    } else if (vertResolved2 && horizWrap) {
                        measure(widget, dimensionBehaviour, dimensionDependency2.value, ConstraintWidget.DimensionBehaviour.FIXED, dimensionDependency3.value);
                        if (horiz == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            widget.horizontalRun.dimension.wrapValue = widget.getWidth();
                        } else {
                            widget.horizontalRun.dimension.resolve(widget.getWidth());
                            widget.measured = true;
                        }
                    }
                } else {
                    ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.FIXED;
                    boolean z2 = vertResolved;
                    boolean z3 = horizResolved;
                    measure(widget, dimensionBehaviour2, dimensionDependency2.value, dimensionBehaviour2, dimensionDependency3.value);
                    widget.measured = true;
                }
                if (widget.measured && (dimensionDependency = widget.verticalRun.baselineDimension) != null) {
                    dimensionDependency.resolve(widget.getBaselineDistance());
                }
            }
        }
    }

    public void invalidateGraph() {
        this.mNeedBuildGraph = true;
    }

    public void invalidateMeasures() {
        this.mNeedRedoMeasures = true;
    }

    public void buildGraph() {
        buildGraph(this.mRuns);
        this.mGroups.clear();
        RunGroup.index = 0;
        findGroup(this.container.horizontalRun, 0, this.mGroups);
        findGroup(this.container.verticalRun, 1, this.mGroups);
        this.mNeedBuildGraph = false;
    }

    public void buildGraph(ArrayList<WidgetRun> runs) {
        runs.clear();
        this.mContainer.horizontalRun.clear();
        this.mContainer.verticalRun.clear();
        runs.add(this.mContainer.horizontalRun);
        runs.add(this.mContainer.verticalRun);
        HashSet<ChainRun> chainRuns = null;
        Iterator<ConstraintWidget> it = this.mContainer.mChildren.iterator();
        while (it.hasNext()) {
            ConstraintWidget widget = it.next();
            if (widget instanceof Guideline) {
                runs.add(new GuidelineReference(widget));
            } else {
                if (widget.isInHorizontalChain()) {
                    if (widget.horizontalChainRun == null) {
                        widget.horizontalChainRun = new ChainRun(widget, 0);
                    }
                    if (chainRuns == null) {
                        chainRuns = new HashSet<>();
                    }
                    chainRuns.add(widget.horizontalChainRun);
                } else {
                    runs.add(widget.horizontalRun);
                }
                if (widget.isInVerticalChain()) {
                    if (widget.verticalChainRun == null) {
                        widget.verticalChainRun = new ChainRun(widget, 1);
                    }
                    if (chainRuns == null) {
                        chainRuns = new HashSet<>();
                    }
                    chainRuns.add(widget.verticalChainRun);
                } else {
                    runs.add(widget.verticalRun);
                }
                if (widget instanceof HelperWidget) {
                    runs.add(new HelperReferences(widget));
                }
            }
        }
        if (chainRuns != null) {
            runs.addAll(chainRuns);
        }
        Iterator<WidgetRun> it2 = runs.iterator();
        while (it2.hasNext()) {
            it2.next().clear();
        }
        Iterator<WidgetRun> it3 = runs.iterator();
        while (it3.hasNext()) {
            WidgetRun run = it3.next();
            if (run.widget != this.mContainer) {
                run.apply();
            }
        }
    }

    private void displayGraph() {
        String content = "digraph {\n";
        Iterator<WidgetRun> it = this.mRuns.iterator();
        while (it.hasNext()) {
            content = generateDisplayGraph(it.next(), content);
        }
        PrintStream printStream = System.out;
        printStream.println("content:<<\n" + (content + "\n}\n") + "\n>>");
    }

    private void applyGroup(DependencyNode node, int orientation, int direction, DependencyNode end, ArrayList<RunGroup> groups, RunGroup group) {
        RunGroup group2;
        int i = orientation;
        DependencyNode dependencyNode = end;
        WidgetRun run = node.run;
        if (run.runGroup == null) {
            ConstraintWidgetContainer constraintWidgetContainer = this.container;
            if (run != constraintWidgetContainer.horizontalRun) {
                if (run == constraintWidgetContainer.verticalRun) {
                    int i2 = direction;
                    ArrayList<RunGroup> arrayList = groups;
                    return;
                }
                if (group == null) {
                    RunGroup group3 = new RunGroup(run, direction);
                    groups.add(group3);
                    group2 = group3;
                } else {
                    int i3 = direction;
                    ArrayList<RunGroup> arrayList2 = groups;
                    group2 = group;
                }
                run.runGroup = group2;
                group2.add(run);
                for (Dependency dependent : run.start.dependencies) {
                    if (dependent instanceof DependencyNode) {
                        Dependency dependency = dependent;
                        applyGroup((DependencyNode) dependent, orientation, 0, end, groups, group2);
                    }
                }
                for (Dependency dependent2 : run.end.dependencies) {
                    if (dependent2 instanceof DependencyNode) {
                        Dependency dependency2 = dependent2;
                        applyGroup((DependencyNode) dependent2, orientation, 1, end, groups, group2);
                    }
                }
                if (i == 1 && (run instanceof VerticalWidgetRun)) {
                    for (Dependency dependent3 : ((VerticalWidgetRun) run).baseline.dependencies) {
                        if (dependent3 instanceof DependencyNode) {
                            Dependency dependency3 = dependent3;
                            applyGroup((DependencyNode) dependent3, orientation, 2, end, groups, group2);
                        }
                    }
                }
                for (DependencyNode target : run.start.targets) {
                    if (target == dependencyNode) {
                        group2.dual = true;
                    }
                    DependencyNode dependencyNode2 = target;
                    applyGroup(target, orientation, 0, end, groups, group2);
                }
                for (DependencyNode target2 : run.end.targets) {
                    if (target2 == dependencyNode) {
                        group2.dual = true;
                    }
                    DependencyNode dependencyNode3 = target2;
                    applyGroup(target2, orientation, 1, end, groups, group2);
                }
                if (i == 1 && (run instanceof VerticalWidgetRun)) {
                    for (DependencyNode applyGroup : ((VerticalWidgetRun) run).baseline.targets) {
                        try {
                            applyGroup(applyGroup, orientation, 2, end, groups, group2);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    return;
                }
                return;
            }
        }
        int i4 = direction;
        ArrayList<RunGroup> arrayList3 = groups;
    }

    private void findGroup(WidgetRun run, int orientation, ArrayList<RunGroup> groups) {
        for (Dependency dependent : run.start.dependencies) {
            if (dependent instanceof DependencyNode) {
                applyGroup((DependencyNode) dependent, orientation, 0, run.end, groups, (RunGroup) null);
            } else if (dependent instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependent).start, orientation, 0, run.end, groups, (RunGroup) null);
            }
        }
        for (Dependency dependent2 : run.end.dependencies) {
            if (dependent2 instanceof DependencyNode) {
                applyGroup((DependencyNode) dependent2, orientation, 1, run.start, groups, (RunGroup) null);
            } else if (dependent2 instanceof WidgetRun) {
                applyGroup(((WidgetRun) dependent2).end, orientation, 1, run.start, groups, (RunGroup) null);
            }
        }
        if (orientation == 1) {
            for (Dependency dependent3 : ((VerticalWidgetRun) run).baseline.dependencies) {
                if (dependent3 instanceof DependencyNode) {
                    applyGroup((DependencyNode) dependent3, orientation, 2, (DependencyNode) null, groups, (RunGroup) null);
                }
            }
        }
    }

    private String generateDisplayNode(DependencyNode node, boolean centeredConnection, String content) {
        StringBuilder contentBuilder = new StringBuilder(content);
        for (DependencyNode target : node.targets) {
            String constraint = ("\n" + node.name()) + " -> " + target.name();
            if (node.margin > 0 || centeredConnection || (node.run instanceof HelperReferences)) {
                String constraint2 = constraint + Constants.ARRAY_TYPE;
                if (node.margin > 0) {
                    constraint2 = constraint2 + "label=\"" + node.margin + "\"";
                    if (centeredConnection) {
                        constraint2 = constraint2 + ",";
                    }
                }
                if (centeredConnection) {
                    constraint2 = constraint2 + " style=dashed ";
                }
                if (node.run instanceof HelperReferences) {
                    constraint2 = constraint2 + " style=bold,color=gray ";
                }
                constraint = constraint2 + "]";
            }
            contentBuilder.append(constraint + "\n");
        }
        return contentBuilder.toString();
    }

    private String nodeDefinition(WidgetRun run) {
        ConstraintWidget.DimensionBehaviour behaviour;
        int orientation = run instanceof VerticalWidgetRun;
        String name = run.widget.getDebugName();
        StringBuilder definition = new StringBuilder(name);
        ConstraintWidget constraintWidget = run.widget;
        if (orientation == 0) {
            behaviour = constraintWidget.getHorizontalDimensionBehaviour();
        } else {
            behaviour = constraintWidget.getVerticalDimensionBehaviour();
        }
        RunGroup runGroup = run.runGroup;
        if (orientation == 0) {
            definition.append("_HORIZONTAL");
        } else {
            definition.append("_VERTICAL");
        }
        definition.append(" [shape=none, label=<");
        definition.append("<TABLE BORDER=\"0\" CELLSPACING=\"0\" CELLPADDING=\"2\">");
        definition.append("  <TR>");
        if (orientation == 0) {
            definition.append("    <TD ");
            if (run.start.resolved) {
                definition.append(" BGCOLOR=\"green\"");
            }
            definition.append(" PORT=\"LEFT\" BORDER=\"1\">L</TD>");
        } else {
            definition.append("    <TD ");
            if (run.start.resolved) {
                definition.append(" BGCOLOR=\"green\"");
            }
            definition.append(" PORT=\"TOP\" BORDER=\"1\">T</TD>");
        }
        definition.append("    <TD BORDER=\"1\" ");
        boolean z = run.dimension.resolved;
        if (z && !run.widget.measured) {
            definition.append(" BGCOLOR=\"green\" ");
        } else if (z) {
            definition.append(" BGCOLOR=\"lightgray\" ");
        } else if (run.widget.measured) {
            definition.append(" BGCOLOR=\"yellow\" ");
        }
        if (behaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            definition.append("style=\"dashed\"");
        }
        definition.append(">");
        definition.append(name);
        if (runGroup != null) {
            definition.append(" [");
            definition.append(runGroup.groupIndex + 1);
            definition.append("/");
            definition.append(RunGroup.index);
            definition.append("]");
        }
        definition.append(" </TD>");
        if (orientation == 0) {
            definition.append("    <TD ");
            if (run.end.resolved) {
                definition.append(" BGCOLOR=\"green\"");
            }
            definition.append(" PORT=\"RIGHT\" BORDER=\"1\">R</TD>");
        } else {
            definition.append("    <TD ");
            if (((VerticalWidgetRun) run).baseline.resolved) {
                definition.append(" BGCOLOR=\"green\"");
            }
            definition.append(" PORT=\"BASELINE\" BORDER=\"1\">b</TD>");
            definition.append("    <TD ");
            if (run.end.resolved) {
                definition.append(" BGCOLOR=\"green\"");
            }
            definition.append(" PORT=\"BOTTOM\" BORDER=\"1\">B</TD>");
        }
        definition.append("  </TR></TABLE>");
        definition.append(">];\n");
        return definition.toString();
    }

    private String generateChainDisplayGraph(ChainRun chain, String content) {
        int orientation = chain.orientation;
        StringBuilder subgroup = new StringBuilder("subgraph ");
        subgroup.append("cluster_");
        subgroup.append(chain.widget.getDebugName());
        if (orientation == 0) {
            subgroup.append("_h");
        } else {
            subgroup.append("_v");
        }
        subgroup.append(" {\n");
        String definitions = "";
        Iterator<WidgetRun> it = chain.widgets.iterator();
        while (it.hasNext()) {
            WidgetRun run = it.next();
            subgroup.append(run.widget.getDebugName());
            if (orientation == 0) {
                subgroup.append("_HORIZONTAL");
            } else {
                subgroup.append("_VERTICAL");
            }
            subgroup.append(";\n");
            definitions = generateDisplayGraph(run, definitions);
        }
        subgroup.append("}\n");
        return content + definitions + subgroup;
    }

    private boolean isCenteredConnection(DependencyNode start, DependencyNode end) {
        int startTargets = 0;
        int endTargets = 0;
        for (DependencyNode s : start.targets) {
            if (s != end) {
                startTargets++;
            }
        }
        for (DependencyNode e : end.targets) {
            if (e != start) {
                endTargets++;
            }
        }
        return startTargets > 0 && endTargets > 0;
    }

    private String generateDisplayGraph(WidgetRun root, String content) {
        DependencyNode start = root.start;
        DependencyNode end = root.end;
        StringBuilder sb = new StringBuilder(content);
        if (!(root instanceof HelperReferences) && start.dependencies.isEmpty() && (end.dependencies.isEmpty() && start.targets.isEmpty()) && end.targets.isEmpty()) {
            return content;
        }
        sb.append(nodeDefinition(root));
        boolean centeredConnection = isCenteredConnection(start, end);
        String content2 = generateDisplayNode(end, centeredConnection, generateDisplayNode(start, centeredConnection, content));
        if (root instanceof VerticalWidgetRun) {
            content2 = generateDisplayNode(((VerticalWidgetRun) root).baseline, centeredConnection, content2);
        }
        if ((root instanceof HorizontalWidgetRun) || ((root instanceof ChainRun) && ((ChainRun) root).orientation == 0)) {
            ConstraintWidget.DimensionBehaviour behaviour = root.widget.getHorizontalDimensionBehaviour();
            if (behaviour == ConstraintWidget.DimensionBehaviour.FIXED || behaviour == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                if (!start.targets.isEmpty() && end.targets.isEmpty()) {
                    sb.append("\n");
                    sb.append(end.name());
                    sb.append(" -> ");
                    sb.append(start.name());
                    sb.append("\n");
                } else if (start.targets.isEmpty() && !end.targets.isEmpty()) {
                    sb.append("\n");
                    sb.append(start.name());
                    sb.append(" -> ");
                    sb.append(end.name());
                    sb.append("\n");
                }
            } else if (behaviour == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && root.widget.getDimensionRatio() > 0.0f) {
                sb.append("\n");
                sb.append(root.widget.getDebugName());
                sb.append("_HORIZONTAL -> ");
                sb.append(root.widget.getDebugName());
                sb.append("_VERTICAL;\n");
            }
        } else if ((root instanceof VerticalWidgetRun) || ((root instanceof ChainRun) && ((ChainRun) root).orientation == 1)) {
            ConstraintWidget.DimensionBehaviour behaviour2 = root.widget.getVerticalDimensionBehaviour();
            if (behaviour2 == ConstraintWidget.DimensionBehaviour.FIXED || behaviour2 == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                if (!start.targets.isEmpty() && end.targets.isEmpty()) {
                    sb.append("\n");
                    sb.append(end.name());
                    sb.append(" -> ");
                    sb.append(start.name());
                    sb.append("\n");
                } else if (start.targets.isEmpty() && !end.targets.isEmpty()) {
                    sb.append("\n");
                    sb.append(start.name());
                    sb.append(" -> ");
                    sb.append(end.name());
                    sb.append("\n");
                }
            } else if (behaviour2 == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && root.widget.getDimensionRatio() > 0.0f) {
                sb.append("\n");
                sb.append(root.widget.getDebugName());
                sb.append("_VERTICAL -> ");
                sb.append(root.widget.getDebugName());
                sb.append("_HORIZONTAL;\n");
            }
        }
        if (root instanceof ChainRun) {
            return generateChainDisplayGraph((ChainRun) root, content2);
        }
        return sb.toString();
    }
}
