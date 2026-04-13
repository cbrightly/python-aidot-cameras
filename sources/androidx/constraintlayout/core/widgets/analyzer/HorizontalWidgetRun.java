package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.Helper;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;

public class HorizontalWidgetRun extends WidgetRun {
    private static int[] tempDimensions = new int[2];

    public HorizontalWidgetRun(ConstraintWidget widget) {
        super(widget);
        this.start.type = DependencyNode.Type.LEFT;
        this.end.type = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }

    public String toString() {
        return "HorizontalRun " + this.widget.getDebugName();
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
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
        this.dimension.resolved = false;
    }

    /* access modifiers changed from: package-private */
    public boolean supportsWrapComputation() {
        if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void apply() {
        ConstraintWidget parent;
        ConstraintWidget parent2;
        ConstraintWidget constraintWidget = this.widget;
        if (constraintWidget.measured) {
            this.dimension.resolve(constraintWidget.getWidth());
        }
        if (!this.dimension.resolved) {
            ConstraintWidget.DimensionBehaviour horizontalDimensionBehaviour = this.widget.getHorizontalDimensionBehaviour();
            this.dimensionBehavior = horizontalDimensionBehaviour;
            if (horizontalDimensionBehaviour != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                if (horizontalDimensionBehaviour == dimensionBehaviour && (parent2 = this.widget.getParent()) != null && (parent2.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent2.getHorizontalDimensionBehaviour() == dimensionBehaviour)) {
                    int resolvedDimension = (parent2.getWidth() - this.widget.mLeft.getMargin()) - this.widget.mRight.getMargin();
                    addTarget(this.start, parent2.horizontalRun.start, this.widget.mLeft.getMargin());
                    addTarget(this.end, parent2.horizontalRun.end, -this.widget.mRight.getMargin());
                    this.dimension.resolve(resolvedDimension);
                    return;
                } else if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.FIXED) {
                    this.dimension.resolve(this.widget.getWidth());
                }
            }
        } else {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = this.dimensionBehavior;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
            if (dimensionBehaviour2 == dimensionBehaviour3 && (parent = this.widget.getParent()) != null && (parent.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.FIXED || parent.getHorizontalDimensionBehaviour() == dimensionBehaviour3)) {
                addTarget(this.start, parent.horizontalRun.start, this.widget.mLeft.getMargin());
                addTarget(this.end, parent.horizontalRun.end, -this.widget.mRight.getMargin());
                return;
            }
        }
        DimensionDependency dimensionDependency = this.dimension;
        if (dimensionDependency.resolved) {
            ConstraintWidget constraintWidget2 = this.widget;
            if (constraintWidget2.measured) {
                ConstraintAnchor[] constraintAnchorArr = constraintWidget2.mListAnchors;
                if (constraintAnchorArr[0].mTarget == null || constraintAnchorArr[1].mTarget == null) {
                    if (constraintAnchorArr[0].mTarget != null) {
                        DependencyNode target = getTarget(constraintAnchorArr[0]);
                        if (target != null) {
                            addTarget(this.start, target, this.widget.mListAnchors[0].getMargin());
                            addTarget(this.end, this.start, this.dimension.value);
                            return;
                        }
                        return;
                    } else if (constraintAnchorArr[1].mTarget != null) {
                        DependencyNode target2 = getTarget(constraintAnchorArr[1]);
                        if (target2 != null) {
                            addTarget(this.end, target2, -this.widget.mListAnchors[1].getMargin());
                            addTarget(this.start, this.end, -this.dimension.value);
                            return;
                        }
                        return;
                    } else if (!(constraintWidget2 instanceof Helper) && constraintWidget2.getParent() != null && this.widget.getAnchor(ConstraintAnchor.Type.CENTER).mTarget == null) {
                        addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
                        addTarget(this.end, this.start, this.dimension.value);
                        return;
                    } else {
                        return;
                    }
                } else if (constraintWidget2.isInHorizontalChain()) {
                    this.start.margin = this.widget.mListAnchors[0].getMargin();
                    this.end.margin = -this.widget.mListAnchors[1].getMargin();
                    return;
                } else {
                    DependencyNode startTarget = getTarget(this.widget.mListAnchors[0]);
                    if (startTarget != null) {
                        addTarget(this.start, startTarget, this.widget.mListAnchors[0].getMargin());
                    }
                    DependencyNode endTarget = getTarget(this.widget.mListAnchors[1]);
                    if (endTarget != null) {
                        addTarget(this.end, endTarget, -this.widget.mListAnchors[1].getMargin());
                    }
                    this.start.delegateToWidgetRun = true;
                    this.end.delegateToWidgetRun = true;
                    return;
                }
            }
        }
        if (this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget3 = this.widget;
            switch (constraintWidget3.mMatchConstraintDefaultWidth) {
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
                    if (constraintWidget3.mMatchConstraintDefaultHeight != 3) {
                        DependencyNode targetDimension2 = constraintWidget3.verticalRun.dimension;
                        dimensionDependency.targets.add(targetDimension2);
                        targetDimension2.dependencies.add(this.dimension);
                        this.widget.verticalRun.start.dependencies.add(this.dimension);
                        this.widget.verticalRun.end.dependencies.add(this.dimension);
                        DimensionDependency dimensionDependency3 = this.dimension;
                        dimensionDependency3.delegateToWidgetRun = true;
                        dimensionDependency3.dependencies.add(this.start);
                        this.dimension.dependencies.add(this.end);
                        this.start.targets.add(this.dimension);
                        this.end.targets.add(this.dimension);
                        break;
                    } else {
                        this.start.updateDelegate = this;
                        this.end.updateDelegate = this;
                        VerticalWidgetRun verticalWidgetRun = constraintWidget3.verticalRun;
                        verticalWidgetRun.start.updateDelegate = this;
                        verticalWidgetRun.end.updateDelegate = this;
                        dimensionDependency.updateDelegate = this;
                        if (!constraintWidget3.isInVerticalChain()) {
                            if (!this.widget.isInHorizontalChain()) {
                                this.widget.verticalRun.dimension.targets.add(this.dimension);
                                break;
                            } else {
                                this.widget.verticalRun.dimension.targets.add(this.dimension);
                                this.dimension.dependencies.add(this.widget.verticalRun.dimension);
                                break;
                            }
                        } else {
                            this.dimension.targets.add(this.widget.verticalRun.dimension);
                            this.widget.verticalRun.dimension.dependencies.add(this.dimension);
                            VerticalWidgetRun verticalWidgetRun2 = this.widget.verticalRun;
                            verticalWidgetRun2.dimension.updateDelegate = this;
                            this.dimension.targets.add(verticalWidgetRun2.start);
                            this.dimension.targets.add(this.widget.verticalRun.end);
                            this.widget.verticalRun.start.dependencies.add(this.dimension);
                            this.widget.verticalRun.end.dependencies.add(this.dimension);
                            break;
                        }
                    }
            }
        }
        ConstraintWidget constraintWidget4 = this.widget;
        ConstraintAnchor[] constraintAnchorArr2 = constraintWidget4.mListAnchors;
        if (constraintAnchorArr2[0].mTarget == null || constraintAnchorArr2[1].mTarget == null) {
            if (constraintAnchorArr2[0].mTarget != null) {
                DependencyNode target3 = getTarget(constraintAnchorArr2[0]);
                if (target3 != null) {
                    addTarget(this.start, target3, this.widget.mListAnchors[0].getMargin());
                    addTarget(this.end, this.start, 1, this.dimension);
                }
            } else if (constraintAnchorArr2[1].mTarget != null) {
                DependencyNode target4 = getTarget(constraintAnchorArr2[1]);
                if (target4 != null) {
                    addTarget(this.end, target4, -this.widget.mListAnchors[1].getMargin());
                    addTarget(this.start, this.end, -1, this.dimension);
                }
            } else if (!(constraintWidget4 instanceof Helper) && constraintWidget4.getParent() != null) {
                addTarget(this.start, this.widget.getParent().horizontalRun.start, this.widget.getX());
                addTarget(this.end, this.start, 1, this.dimension);
            }
        } else if (constraintWidget4.isInHorizontalChain()) {
            this.start.margin = this.widget.mListAnchors[0].getMargin();
            this.end.margin = -this.widget.mListAnchors[1].getMargin();
        } else {
            DependencyNode startTarget2 = getTarget(this.widget.mListAnchors[0]);
            DependencyNode endTarget2 = getTarget(this.widget.mListAnchors[1]);
            if (startTarget2 != null) {
                startTarget2.addDependency(this);
            }
            if (endTarget2 != null) {
                endTarget2.addDependency(this);
            }
            this.mRunType = WidgetRun.RunType.CENTER;
        }
    }

    private void computeInsetRatio(int[] dimensions, int x1, int x2, int y1, int y2, float ratio, int side) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        switch (side) {
            case -1:
                int candidateX1 = (int) ((((float) dy) * ratio) + 0.5f);
                int candidateY1 = dy;
                int candidateX2 = dx;
                int candidateY2 = (int) ((((float) dx) / ratio) + 0.5f);
                if (candidateX1 <= dx && candidateY1 <= dy) {
                    dimensions[0] = candidateX1;
                    dimensions[1] = candidateY1;
                    return;
                } else if (candidateX2 <= dx && candidateY2 <= dy) {
                    dimensions[0] = candidateX2;
                    dimensions[1] = candidateY2;
                    return;
                } else {
                    return;
                }
            case 0:
                dimensions[0] = (int) ((((float) dy) * ratio) + 0.5f);
                dimensions[1] = dy;
                return;
            case 1:
                dimensions[0] = dx;
                dimensions[1] = (int) ((((float) dx) * ratio) + 0.5f);
                return;
            default:
                return;
        }
    }

    /* renamed from: androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun$1  reason: invalid class name */
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
                Dependency dependency2 = dependency;
                updateRunStart(dependency);
                break;
            case 2:
                Dependency dependency3 = dependency;
                updateRunEnd(dependency);
                break;
            case 3:
                ConstraintWidget constraintWidget = this.widget;
                updateRunCenter(dependency, constraintWidget.mLeft, constraintWidget.mRight, 0);
                return;
            default:
                Dependency dependency4 = dependency;
                break;
        }
        if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            ConstraintWidget constraintWidget2 = this.widget;
            switch (constraintWidget2.mMatchConstraintDefaultWidth) {
                case 2:
                    ConstraintWidget parent = constraintWidget2.getParent();
                    if (parent != null) {
                        DimensionDependency dimensionDependency = parent.horizontalRun.dimension;
                        if (dimensionDependency.resolved) {
                            this.dimension.resolve((int) ((((float) dimensionDependency.value) * this.widget.mMatchConstraintPercentWidth) + 0.5f));
                            break;
                        }
                    }
                    break;
                case 3:
                    int i = constraintWidget2.mMatchConstraintDefaultHeight;
                    if (i != 0 && i != 3) {
                        int size = 0;
                        switch (constraintWidget2.getDimensionRatioSide()) {
                            case -1:
                                ConstraintWidget constraintWidget3 = this.widget;
                                size = (int) ((((float) constraintWidget3.verticalRun.dimension.value) * constraintWidget3.getDimensionRatio()) + 0.5f);
                                break;
                            case 0:
                                ConstraintWidget constraintWidget4 = this.widget;
                                size = (int) ((((float) constraintWidget4.verticalRun.dimension.value) / constraintWidget4.getDimensionRatio()) + 0.5f);
                                break;
                            case 1:
                                ConstraintWidget constraintWidget5 = this.widget;
                                size = (int) ((((float) constraintWidget5.verticalRun.dimension.value) * constraintWidget5.getDimensionRatio()) + 0.5f);
                                break;
                        }
                        this.dimension.resolve(size);
                        break;
                    } else {
                        VerticalWidgetRun verticalWidgetRun = constraintWidget2.verticalRun;
                        DependencyNode secondStart = verticalWidgetRun.start;
                        DependencyNode secondEnd = verticalWidgetRun.end;
                        boolean s1 = constraintWidget2.mLeft.mTarget != null;
                        boolean s2 = constraintWidget2.mTop.mTarget != null;
                        boolean e1 = constraintWidget2.mRight.mTarget != null;
                        boolean e2 = constraintWidget2.mBottom.mTarget != null;
                        int definedSide = constraintWidget2.getDimensionRatioSide();
                        if (!s1 || !s2 || !e1 || !e2) {
                            if (s1 && e1) {
                                if (this.start.readyToSolve && this.end.readyToSolve) {
                                    float ratio = this.widget.getDimensionRatio();
                                    int x1 = this.start.targets.get(0).value + this.start.margin;
                                    int x2 = this.end.targets.get(0).value - this.end.margin;
                                    switch (definedSide) {
                                        case -1:
                                        case 0:
                                            int ldx = getLimitedDimension(x2 - x1, 0);
                                            int dy = (int) ((((float) ldx) * ratio) + 0.5f);
                                            int ldy = getLimitedDimension(dy, 1);
                                            if (dy != ldy) {
                                                ldx = (int) ((((float) ldy) / ratio) + 0.5f);
                                            }
                                            this.dimension.resolve(ldx);
                                            this.widget.verticalRun.dimension.resolve(ldy);
                                            break;
                                        case 1:
                                            int ldx2 = getLimitedDimension(x2 - x1, 0);
                                            int dy2 = (int) ((((float) ldx2) / ratio) + 0.5f);
                                            int ldy2 = getLimitedDimension(dy2, 1);
                                            if (dy2 != ldy2) {
                                                ldx2 = (int) ((((float) ldy2) * ratio) + 0.5f);
                                            }
                                            this.dimension.resolve(ldx2);
                                            this.widget.verticalRun.dimension.resolve(ldy2);
                                            break;
                                    }
                                } else {
                                    return;
                                }
                            } else if (s2 && e2) {
                                if (secondStart.readyToSolve && secondEnd.readyToSolve) {
                                    float ratio2 = this.widget.getDimensionRatio();
                                    int y1 = secondStart.targets.get(0).value + secondStart.margin;
                                    int y2 = secondEnd.targets.get(0).value - secondEnd.margin;
                                    switch (definedSide) {
                                        case -1:
                                        case 1:
                                            int ldy3 = getLimitedDimension(y2 - y1, 1);
                                            int dx = (int) ((((float) ldy3) / ratio2) + 0.5f);
                                            int ldx3 = getLimitedDimension(dx, 0);
                                            if (dx != ldx3) {
                                                ldy3 = (int) ((((float) ldx3) * ratio2) + 0.5f);
                                            }
                                            this.dimension.resolve(ldx3);
                                            this.widget.verticalRun.dimension.resolve(ldy3);
                                            break;
                                        case 0:
                                            int ldy4 = getLimitedDimension(y2 - y1, 1);
                                            int dx2 = (int) ((((float) ldy4) * ratio2) + 0.5f);
                                            int ldx4 = getLimitedDimension(dx2, 0);
                                            if (dx2 != ldx4) {
                                                ldy4 = (int) ((((float) ldx4) / ratio2) + 0.5f);
                                            }
                                            this.dimension.resolve(ldx4);
                                            this.widget.verticalRun.dimension.resolve(ldy4);
                                            break;
                                    }
                                } else {
                                    return;
                                }
                            }
                        } else {
                            float ratio3 = this.widget.getDimensionRatio();
                            if (!secondStart.resolved || !secondEnd.resolved) {
                                DependencyNode dependencyNode = this.start;
                                if (dependencyNode.resolved) {
                                    DependencyNode dependencyNode2 = this.end;
                                    if (dependencyNode2.resolved) {
                                        if (secondStart.readyToSolve && secondEnd.readyToSolve) {
                                            computeInsetRatio(tempDimensions, dependencyNode.value + dependencyNode.margin, dependencyNode2.value - dependencyNode2.margin, secondStart.targets.get(0).value + secondStart.margin, secondEnd.targets.get(0).value - secondEnd.margin, ratio3, definedSide);
                                            this.dimension.resolve(tempDimensions[0]);
                                            this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                        } else {
                                            return;
                                        }
                                    }
                                }
                                DependencyNode dependencyNode3 = this.start;
                                if (dependencyNode3.readyToSolve && this.end.readyToSolve && secondStart.readyToSolve && secondEnd.readyToSolve) {
                                    computeInsetRatio(tempDimensions, dependencyNode3.targets.get(0).value + this.start.margin, this.end.targets.get(0).value - this.end.margin, secondStart.targets.get(0).value + secondStart.margin, secondEnd.targets.get(0).value - secondEnd.margin, ratio3, definedSide);
                                    this.dimension.resolve(tempDimensions[0]);
                                    this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                    break;
                                } else {
                                    return;
                                }
                            } else {
                                DependencyNode dependencyNode4 = this.start;
                                if (dependencyNode4.readyToSolve && this.end.readyToSolve) {
                                    computeInsetRatio(tempDimensions, dependencyNode4.targets.get(0).value + this.start.margin, this.end.targets.get(0).value - this.end.margin, secondStart.value + secondStart.margin, secondEnd.value - secondEnd.margin, ratio3, definedSide);
                                    this.dimension.resolve(tempDimensions[0]);
                                    this.widget.verticalRun.dimension.resolve(tempDimensions[1]);
                                    return;
                                }
                                return;
                            }
                        }
                    }
                    break;
            }
        }
        DependencyNode dependencyNode5 = this.start;
        if (dependencyNode5.readyToSolve) {
            DependencyNode dependencyNode6 = this.end;
            if (dependencyNode6.readyToSolve) {
                if (!dependencyNode5.resolved || !dependencyNode6.resolved || !this.dimension.resolved) {
                    if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        ConstraintWidget constraintWidget6 = this.widget;
                        if (constraintWidget6.mMatchConstraintDefaultWidth == 0 && !constraintWidget6.isInHorizontalChain()) {
                            int i2 = this.start.targets.get(0).value;
                            DependencyNode dependencyNode7 = this.start;
                            int startPos = i2 + dependencyNode7.margin;
                            int endPos = this.end.targets.get(0).value + this.end.margin;
                            dependencyNode7.resolve(startPos);
                            this.end.resolve(endPos);
                            this.dimension.resolve(endPos - startPos);
                            return;
                        }
                    }
                    if (!this.dimension.resolved && this.dimensionBehavior == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && this.matchConstraintsType == 1 && this.start.targets.size() > 0 && this.end.targets.size() > 0) {
                        int value = Math.min((this.end.targets.get(0).value + this.end.margin) - (this.start.targets.get(0).value + this.start.margin), this.dimension.wrapValue);
                        ConstraintWidget constraintWidget7 = this.widget;
                        int max = constraintWidget7.mMatchConstraintMaxWidth;
                        int value2 = Math.max(constraintWidget7.mMatchConstraintMinWidth, value);
                        if (max > 0) {
                            value2 = Math.min(max, value2);
                        }
                        this.dimension.resolve(value2);
                    }
                    if (this.dimension.resolved) {
                        DependencyNode startTarget = this.start.targets.get(0);
                        DependencyNode endTarget = this.end.targets.get(0);
                        int startPos2 = startTarget.value + this.start.margin;
                        int endPos2 = endTarget.value + this.end.margin;
                        float bias = this.widget.getHorizontalBiasPercent();
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

    public void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            this.widget.setX(dependencyNode.value);
        }
    }
}
