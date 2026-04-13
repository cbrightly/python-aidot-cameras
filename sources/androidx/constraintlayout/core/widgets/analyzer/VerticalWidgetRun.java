package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;

public class VerticalWidgetRun extends WidgetRun {
    public DependencyNode baseline;
    DimensionDependency baselineDimension = null;

    public VerticalWidgetRun(ConstraintWidget widget) {
        super(widget);
        DependencyNode dependencyNode = new DependencyNode(this);
        this.baseline = dependencyNode;
        this.start.type = DependencyNode.Type.TOP;
        this.end.type = DependencyNode.Type.BOTTOM;
        dependencyNode.type = DependencyNode.Type.BASELINE;
        this.orientation = 1;
    }

    public String toString() {
        return "VerticalRun " + this.widget.getDebugName();
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
        this.baseline.clear();
        this.dimension.clear();
        this.resolved = false;
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.resolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.baseline.clear();
        this.baseline.resolved = false;
        this.dimension.resolved = false;
    }

    /* access modifiers changed from: package-private */
    public boolean supportsWrapComputation() {
        if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultHeight == 0) {
            return true;
        }
        return false;
    }

    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType;

        static {
            int[] iArr = new int[WidgetRun.RunType.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType = iArr;
            try {
                iArr[WidgetRun.RunType.START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.END.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[WidgetRun.RunType.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public void update(Dependency dependency) {
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$analyzer$WidgetRun$RunType[this.mRunType.ordinal()]) {
            case 1:
                updateRunStart(dependency);
                break;
            case 2:
                updateRunEnd(dependency);
                break;
            case 3:
                ConstraintWidget constraintWidget = this.widget;
                updateRunCenter(dependency, constraintWidget.mTop, constraintWidget.mBottom, 1);
                return;
        }
        DimensionDependency dimensionDependency = this.dimension;
        if (dimensionDependency.readyToSolve && !dimensionDependency.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget2 = this.widget;
            switch (constraintWidget2.mMatchConstraintDefaultHeight) {
                case 2:
                    ConstraintWidget parent = constraintWidget2.getParent();
                    if (parent != null) {
                        DimensionDependency dimensionDependency2 = parent.verticalRun.dimension;
                        if (dimensionDependency2.resolved) {
                            float percent = this.widget.mMatchConstraintPercentHeight;
                            this.dimension.resolve((int) ((((float) dimensionDependency2.value) * percent) + 0.5f));
                            break;
                        }
                    }
                    break;
                case 3:
                    if (constraintWidget2.horizontalRun.dimension.resolved) {
                        int size = 0;
                        switch (constraintWidget2.getDimensionRatioSide()) {
                            case -1:
                                ConstraintWidget constraintWidget3 = this.widget;
                                size = (int) ((((float) constraintWidget3.horizontalRun.dimension.value) / constraintWidget3.getDimensionRatio()) + 0.5f);
                                break;
                            case 0:
                                ConstraintWidget constraintWidget4 = this.widget;
                                size = (int) ((((float) constraintWidget4.horizontalRun.dimension.value) * constraintWidget4.getDimensionRatio()) + 0.5f);
                                break;
                            case 1:
                                ConstraintWidget constraintWidget5 = this.widget;
                                size = (int) ((((float) constraintWidget5.horizontalRun.dimension.value) / constraintWidget5.getDimensionRatio()) + 0.5f);
                                break;
                        }
                        this.dimension.resolve(size);
                        break;
                    }
                    break;
            }
        }
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.readyToSolve) {
            DependencyNode dependencyNode2 = this.end;
            if (dependencyNode2.readyToSolve) {
                if (!dependencyNode.resolved || !dependencyNode2.resolved || !this.dimension.resolved) {
                    if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        ConstraintWidget constraintWidget6 = this.widget;
                        if (constraintWidget6.mMatchConstraintDefaultWidth == 0 && !constraintWidget6.isInVerticalChain()) {
                            int i = this.start.targets.get(0).value;
                            DependencyNode dependencyNode3 = this.start;
                            int startPos = i + dependencyNode3.margin;
                            int endPos = this.end.targets.get(0).value + this.end.margin;
                            dependencyNode3.resolve(startPos);
                            this.end.resolve(endPos);
                            this.dimension.resolve(endPos - startPos);
                            return;
                        }
                    }
                    if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
                        int availableSpace = (this.end.targets.get(0).value + this.end.margin) - (this.start.targets.get(0).value + this.start.margin);
                        DimensionDependency dimensionDependency3 = this.dimension;
                        int i2 = dimensionDependency3.wrapValue;
                        if (availableSpace < i2) {
                            dimensionDependency3.resolve(availableSpace);
                        } else {
                            dimensionDependency3.resolve(i2);
                        }
                    }
                    if (this.dimension.resolved && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
                        DependencyNode startTarget = this.start.targets.get(0);
                        DependencyNode endTarget = this.end.targets.get(0);
                        int startPos2 = startTarget.value + this.start.margin;
                        int endPos2 = endTarget.value + this.end.margin;
                        float bias = this.widget.getVerticalBiasPercent();
                        if (startTarget == endTarget) {
                            startPos2 = startTarget.value;
                            endPos2 = endTarget.value;
                            bias = 0.5f;
                        }
                        this.start.resolve((int) (((float) startPos2) + 0.5f + (((float) ((endPos2 - startPos2) - this.dimension.value)) * bias)));
                        this.end.resolve(this.start.value + this.dimension.value);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void apply() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        ConstraintWidget constraintWidget = this.widget;
        if (constraintWidget.measured) {
            this.dimension.resolve(constraintWidget.getHeight());
        }
        if (!this.dimension.resolved) {
            this.dimensionBehavior = this.widget.getVerticalDimensionBehaviour();
            if (this.widget.hasBaseline()) {
                this.baselineDimension = new BaselineDimensionDependency(this);
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = this.dimensionBehavior;
            if (dimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                if (dimensionBehaviour == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent2 = this.widget.getParent()) != null && parent2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
                    int resolvedDimension = (parent2.getHeight() - this.widget.mTop.getMargin()) - this.widget.mBottom.getMargin();
                    addTarget(this.start, parent2.verticalRun.start, this.widget.mTop.getMargin());
                    addTarget(this.end, parent2.verticalRun.end, -this.widget.mBottom.getMargin());
                    this.dimension.resolve(resolvedDimension);
                    return;
                } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.dimension.resolve(this.widget.getHeight());
                }
            }
        } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && (parent = this.widget.getParent()) != null && parent.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED) {
            addTarget(this.start, parent.verticalRun.start, this.widget.mTop.getMargin());
            addTarget(this.end, parent.verticalRun.end, -this.widget.mBottom.getMargin());
            return;
        }
        DimensionDependency dimensionDependency = this.dimension;
        boolean z = dimensionDependency.resolved;
        if (z) {
            ConstraintWidget constraintWidget2 = this.widget;
            if (constraintWidget2.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget2.mListAnchors;
                if (constraintAnchorArr[2].mTarget != null && constraintAnchorArr[3].mTarget != null) {
                    if (constraintWidget2.isInVerticalChain()) {
                        this.start.margin = this.widget.mListAnchors[2].getMargin();
                        this.end.margin = -this.widget.mListAnchors[3].getMargin();
                    } else {
                        DependencyNode startTarget = getTarget(this.widget.mListAnchors[2]);
                        if (startTarget != null) {
                            addTarget(this.start, startTarget, this.widget.mListAnchors[2].getMargin());
                        }
                        DependencyNode endTarget = getTarget(this.widget.mListAnchors[3]);
                        if (endTarget != null) {
                            addTarget(this.end, endTarget, -this.widget.mListAnchors[3].getMargin());
                        }
                        this.start.delegateToWidgetRun = true;
                        this.end.delegateToWidgetRun = true;
                    }
                    if (this.widget.hasBaseline()) {
                        addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
                        return;
                    }
                    return;
                } else if (constraintAnchorArr[2].mTarget != null) {
                    DependencyNode target = getTarget(constraintAnchorArr[2]);
                    if (target != null) {
                        addTarget(this.start, target, this.widget.mListAnchors[2].getMargin());
                        addTarget(this.end, this.start, this.dimension.value);
                        if (this.widget.hasBaseline()) {
                            addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
                            return;
                        }
                        return;
                    }
                    return;
                } else if (constraintAnchorArr[3].mTarget != null) {
                    DependencyNode target2 = getTarget(constraintAnchorArr[3]);
                    if (target2 != null) {
                        addTarget(this.end, target2, -this.widget.mListAnchors[3].getMargin());
                        addTarget(this.start, this.end, -this.dimension.value);
                    }
                    if (this.widget.hasBaseline()) {
                        addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
                        return;
                    }
                    return;
                } else if (constraintAnchorArr[4].mTarget != null) {
                    DependencyNode target3 = getTarget(constraintAnchorArr[4]);
                    if (target3 != null) {
                        addTarget(this.baseline, target3, 0);
                        addTarget(this.start, this.baseline, -this.widget.getBaselineDistance());
                        addTarget(this.end, this.start, this.dimension.value);
                        return;
                    }
                    return;
                } else if (!(constraintWidget2 instanceof Helper) && constraintWidget2.getParent() != null && this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
                    addTarget(this.start, this.widget.getParent().verticalRun.start, this.widget.getY());
                    addTarget(this.end, this.start, this.dimension.value);
                    if (this.widget.hasBaseline()) {
                        addTarget(this.baseline, this.start, this.widget.getBaselineDistance());
                        return;
                    }
                    return;
                } else {
                    return;
                }
            }
        }
        if (!z && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget3 = this.widget;
            switch (constraintWidget3.mMatchConstraintDefaultHeight) {
                case 2:
                    ConstraintWidget parent3 = constraintWidget3.getParent();
                    if (parent3 != null) {
                        DependencyNode targetDimension = parent3.verticalRun.dimension;
                        this.dimension.targets.add(targetDimension);
                        targetDimension.dependencies.add(this.dimension);
                        DimensionDependency dimensionDependency2 = this.dimension;
                        dimensionDependency2.delegateToWidgetRun = true;
                        dimensionDependency2.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                        break;
                    }
                    break;
                case 3:
                    if (!constraintWidget3.isInVerticalChain()) {
                        ConstraintWidget constraintWidget4 = this.widget;
                        if (constraintWidget4.mMatchConstraintDefaultWidth != 3) {
                            DependencyNode targetDimension2 = constraintWidget4.horizontalRun.dimension;
                            this.dimension.targets.add(targetDimension2);
                            targetDimension2.dependencies.add(this.dimension);
                            DimensionDependency dimensionDependency3 = this.dimension;
                            dimensionDependency3.delegateToWidgetRun = true;
                            dimensionDependency3.dependencies.add(this.start);
                            this.dimension.dependencies.add(this.end);
                            break;
                        }
                    }
                    break;
            }
        } else {
            dimensionDependency.addDependency(this);
        }
        ConstraintWidget constraintWidget5 = this.widget;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget5.mListAnchors;
        if (constraintAnchorArr2[2].mTarget != null && constraintAnchorArr2[3].mTarget != null) {
            if (constraintWidget5.isInVerticalChain()) {
                this.start.margin = this.widget.mListAnchors[2].getMargin();
                this.end.margin = -this.widget.mListAnchors[3].getMargin();
            } else {
                DependencyNode startTarget2 = getTarget(this.widget.mListAnchors[2]);
                DependencyNode endTarget2 = getTarget(this.widget.mListAnchors[3]);
                if (startTarget2 != null) {
                    startTarget2.addDependency(this);
                }
                if (endTarget2 != null) {
                    endTarget2.addDependency(this);
                }
                this.mRunType = WidgetRun.RunType.CENTER;
            }
            if (this.widget.hasBaseline()) {
                addTarget(this.baseline, this.start, 1, this.baselineDimension);
            }
        } else if (constraintAnchorArr2[2].mTarget != null) {
            DependencyNode target4 = getTarget(constraintAnchorArr2[2]);
            if (target4 != null) {
                addTarget(this.start, target4, this.widget.mListAnchors[2].getMargin());
                addTarget(this.end, this.start, 1, this.dimension);
                if (this.widget.hasBaseline()) {
                    addTarget(this.baseline, this.start, 1, this.baselineDimension);
                }
                ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.dimensionBehavior;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
                if (dimensionBehaviour2 == dimensionBehaviour3 && this.widget.getDimensionRatio() > 0.0f) {
                    HorizontalWidgetRun horizontalWidgetRun = this.widget.horizontalRun;
                    if (horizontalWidgetRun.dimensionBehavior == dimensionBehaviour3) {
                        horizontalWidgetRun.dimension.dependencies.add(this.dimension);
                        this.dimension.targets.add(this.widget.horizontalRun.dimension);
                        this.dimension.updateDelegate = this;
                    }
                }
            }
        } else if (constraintAnchorArr2[3].mTarget != null) {
            DependencyNode target5 = getTarget(constraintAnchorArr2[3]);
            if (target5 != null) {
                addTarget(this.end, target5, -this.widget.mListAnchors[3].getMargin());
                addTarget(this.start, this.end, -1, this.dimension);
                if (this.widget.hasBaseline()) {
                    addTarget(this.baseline, this.start, 1, this.baselineDimension);
                }
            }
        } else if (constraintAnchorArr2[4].mTarget != null) {
            DependencyNode target6 = getTarget(constraintAnchorArr2[4]);
            if (target6 != null) {
                addTarget(this.baseline, target6, 0);
                addTarget(this.start, this.baseline, -1, this.baselineDimension);
                addTarget(this.end, this.start, 1, this.dimension);
            }
        } else if (!(constraintWidget5 instanceof Helper) && constraintWidget5.getParent() != null) {
            addTarget(this.start, this.widget.getParent().verticalRun.start, this.widget.getY());
            addTarget(this.end, this.start, 1, this.dimension);
            if (this.widget.hasBaseline()) {
                addTarget(this.baseline, this.start, 1, this.baselineDimension);
            }
            ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = this.dimensionBehavior;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            if (dimensionBehaviour4 == dimensionBehaviour5 && this.widget.getDimensionRatio() > 0.0f) {
                HorizontalWidgetRun horizontalWidgetRun2 = this.widget.horizontalRun;
                if (horizontalWidgetRun2.dimensionBehavior == dimensionBehaviour5) {
                    horizontalWidgetRun2.dimension.dependencies.add(this.dimension);
                    this.dimension.targets.add(this.widget.horizontalRun.dimension);
                    this.dimension.updateDelegate = this;
                }
            }
        }
        if (this.dimension.targets.size() == 0) {
            this.dimension.readyToSolve = true;
        }
    }

    public void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.widget.setY(dependencyNode.value);
        }
    }
}
