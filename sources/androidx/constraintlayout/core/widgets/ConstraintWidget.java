package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.Cache;
import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.SolverVariable;
import androidx.constraintlayout.core.state.WidgetFrame;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.analyzer.ChainRun;
import androidx.constraintlayout.core.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun;
import androidx.constraintlayout.core.widgets.analyzer.WidgetRun;
import androidx.exifinterface.media.ExifInterface;
import com.didichuxing.doraemonkit.widget.JustifyTextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ConstraintWidget {
    public static final int ANCHOR_BASELINE = 4;
    public static final int ANCHOR_BOTTOM = 3;
    public static final int ANCHOR_LEFT = 0;
    public static final int ANCHOR_RIGHT = 1;
    public static final int ANCHOR_TOP = 2;
    private static final boolean AUTOTAG_CENTER = false;
    public static final int BOTH = 2;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static float DEFAULT_BIAS = 0.5f;
    static final int DIMENSION_HORIZONTAL = 0;
    static final int DIMENSION_VERTICAL = 1;
    protected static final int DIRECT = 2;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int INVISIBLE = 4;
    public static final int MATCH_CONSTRAINT_PERCENT = 2;
    public static final int MATCH_CONSTRAINT_RATIO = 3;
    public static final int MATCH_CONSTRAINT_RATIO_RESOLVED = 4;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    protected static final int SOLVER = 1;
    public static final int UNKNOWN = -1;
    private static final boolean USE_WRAP_DIMENSION_FOR_SPREAD = false;
    public static final int VERTICAL = 1;
    public static final int VISIBLE = 0;
    private static final int WRAP = -2;
    public static final int WRAP_BEHAVIOR_HORIZONTAL_ONLY = 1;
    public static final int WRAP_BEHAVIOR_INCLUDED = 0;
    public static final int WRAP_BEHAVIOR_SKIPPED = 3;
    public static final int WRAP_BEHAVIOR_VERTICAL_ONLY = 2;
    private boolean OPTIMIZE_WRAP;
    private boolean OPTIMIZE_WRAP_ON_RESOLVED;
    public WidgetFrame frame;
    private boolean hasBaseline;
    public ChainRun horizontalChainRun;
    public int horizontalGroup;
    public HorizontalWidgetRun horizontalRun;
    private boolean horizontalSolvingPass;
    private boolean inPlaceholder;
    public boolean[] isTerminalWidget;
    protected ArrayList<ConstraintAnchor> mAnchors;
    public ConstraintAnchor mBaseline;
    int mBaselineDistance;
    public ConstraintAnchor mBottom;
    boolean mBottomHasCentered;
    public ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX;
    ConstraintAnchor mCenterY;
    private float mCircleConstraintAngle;
    private Object mCompanionWidget;
    private int mContainerItemSkip;
    private String mDebugName;
    public float mDimensionRatio;
    protected int mDimensionRatioSide;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    boolean mGroupsToSolver;
    int mHeight;
    private int mHeightOverride;
    float mHorizontalBiasPercent;
    boolean mHorizontalChainFixedPosition;
    int mHorizontalChainStyle;
    ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    boolean mHorizontalWrapVisited;
    private boolean mInVirtualLayout;
    public boolean mIsHeightWrapContent;
    private boolean[] mIsInBarrier;
    public boolean mIsWidthWrapContent;
    private int mLastHorizontalMeasureSpec;
    private int mLastVerticalMeasureSpec;
    public ConstraintAnchor mLeft;
    boolean mLeftHasCentered;
    public ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    protected ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight;
    public int mMatchConstraintDefaultWidth;
    public int mMatchConstraintMaxHeight;
    public int mMatchConstraintMaxWidth;
    public int mMatchConstraintMinHeight;
    public int mMatchConstraintMinWidth;
    public float mMatchConstraintPercentHeight;
    public float mMatchConstraintPercentWidth;
    private int[] mMaxDimension;
    private boolean mMeasureRequested;
    protected int mMinHeight;
    protected int mMinWidth;
    protected ConstraintWidget[] mNextChainWidget;
    protected int mOffsetX;
    protected int mOffsetY;
    public ConstraintWidget mParent;
    int mRelX;
    int mRelY;
    float mResolvedDimensionRatio;
    int mResolvedDimensionRatioSide;
    boolean mResolvedHasRatio;
    public int[] mResolvedMatchConstraintDefault;
    public ConstraintAnchor mRight;
    boolean mRightHasCentered;
    public ConstraintAnchor mTop;
    boolean mTopHasCentered;
    private String mType;
    float mVerticalBiasPercent;
    boolean mVerticalChainFixedPosition;
    int mVerticalChainStyle;
    ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    boolean mVerticalWrapVisited;
    private int mVisibility;
    public float[] mWeight;
    int mWidth;
    private int mWidthOverride;
    private int mWrapBehaviorInParent;
    protected int mX;
    protected int mY;
    public boolean measured;
    private boolean resolvedHorizontal;
    private boolean resolvedVertical;
    public WidgetRun[] run;
    public String stringId;
    public ChainRun verticalChainRun;
    public int verticalGroup;
    public VerticalWidgetRun verticalRun;
    private boolean verticalSolvingPass;

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public WidgetRun getRun(int orientation) {
        if (orientation == 0) {
            return this.horizontalRun;
        }
        if (orientation == 1) {
            return this.verticalRun;
        }
        return null;
    }

    public void setFinalFrame(int left, int top, int right, int bottom, int baseline, int orientation) {
        setFrame(left, top, right, bottom);
        setBaselineDistance(baseline);
        if (orientation == 0) {
            this.resolvedHorizontal = true;
            this.resolvedVertical = false;
        } else if (orientation == 1) {
            this.resolvedHorizontal = false;
            this.resolvedVertical = true;
        } else if (orientation == 2) {
            this.resolvedHorizontal = true;
            this.resolvedVertical = true;
        } else {
            this.resolvedHorizontal = false;
            this.resolvedVertical = false;
        }
    }

    public void setFinalLeft(int x1) {
        this.mLeft.setFinalValue(x1);
        this.mX = x1;
    }

    public void setFinalTop(int y1) {
        this.mTop.setFinalValue(y1);
        this.mY = y1;
    }

    public void resetSolvingPassFlag() {
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
    }

    public boolean isHorizontalSolvingPassDone() {
        return this.horizontalSolvingPass;
    }

    public boolean isVerticalSolvingPassDone() {
        return this.verticalSolvingPass;
    }

    public void markHorizontalSolvingPassDone() {
        this.horizontalSolvingPass = true;
    }

    public void markVerticalSolvingPassDone() {
        this.verticalSolvingPass = true;
    }

    public void setFinalHorizontal(int x1, int x2) {
        if (!this.resolvedHorizontal) {
            this.mLeft.setFinalValue(x1);
            this.mRight.setFinalValue(x2);
            this.mX = x1;
            this.mWidth = x2 - x1;
            this.resolvedHorizontal = true;
        }
    }

    public void setFinalVertical(int y1, int y2) {
        if (!this.resolvedVertical) {
            this.mTop.setFinalValue(y1);
            this.mBottom.setFinalValue(y2);
            this.mY = y1;
            this.mHeight = y2 - y1;
            if (this.hasBaseline) {
                this.mBaseline.setFinalValue(this.mBaselineDistance + y1);
            }
            this.resolvedVertical = true;
        }
    }

    public void setFinalBaseline(int baselineValue) {
        if (this.hasBaseline) {
            int y1 = baselineValue - this.mBaselineDistance;
            this.mY = y1;
            this.mTop.setFinalValue(y1);
            this.mBottom.setFinalValue(this.mHeight + y1);
            this.mBaseline.setFinalValue(baselineValue);
            this.resolvedVertical = true;
        }
    }

    public boolean isResolvedHorizontally() {
        return this.resolvedHorizontal || (this.mLeft.hasFinalValue() && this.mRight.hasFinalValue());
    }

    public boolean isResolvedVertically() {
        return this.resolvedVertical || (this.mTop.hasFinalValue() && this.mBottom.hasFinalValue());
    }

    public void resetFinalResolution() {
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        int mAnchorsSize = this.mAnchors.size();
        for (int i = 0; i < mAnchorsSize; i++) {
            this.mAnchors.get(i).resetFinalResolution();
        }
    }

    public void ensureMeasureRequested() {
        this.mMeasureRequested = true;
    }

    public boolean hasDependencies() {
        int mAnchorsSize = this.mAnchors.size();
        for (int i = 0; i < mAnchorsSize; i++) {
            if (this.mAnchors.get(i).hasDependents()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDanglingDimension(int orientation) {
        if (orientation == 0) {
            if ((this.mLeft.mTarget != null ? 1 : 0) + (this.mRight.mTarget != null ? 1 : 0) < 2) {
                return true;
            }
            return false;
        }
        if ((this.mTop.mTarget != null ? 1 : 0) + (this.mBottom.mTarget != null ? 1 : 0) + (this.mBaseline.mTarget != null ? 1 : 0) < 2) {
            return true;
        }
        return false;
    }

    public boolean hasResolvedTargets(int orientation, int size) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        if (orientation == 0) {
            ConstraintAnchor constraintAnchor3 = this.mLeft.mTarget;
            if (constraintAnchor3 != null && constraintAnchor3.hasFinalValue() && (constraintAnchor2 = this.mRight.mTarget) != null && constraintAnchor2.hasFinalValue()) {
                if ((this.mRight.mTarget.getFinalValue() - this.mRight.getMargin()) - (this.mLeft.mTarget.getFinalValue() + this.mLeft.getMargin()) >= size) {
                    return true;
                }
                return false;
            }
        } else {
            ConstraintAnchor constraintAnchor4 = this.mTop.mTarget;
            if (constraintAnchor4 != null && constraintAnchor4.hasFinalValue() && (constraintAnchor = this.mBottom.mTarget) != null && constraintAnchor.hasFinalValue()) {
                if ((this.mBottom.mTarget.getFinalValue() - this.mBottom.getMargin()) - (this.mTop.mTarget.getFinalValue() + this.mTop.getMargin()) >= size) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public boolean isInVirtualLayout() {
        return this.mInVirtualLayout;
    }

    public void setInVirtualLayout(boolean inVirtualLayout) {
        this.mInVirtualLayout = inVirtualLayout;
    }

    public int getMaxHeight() {
        return this.mMaxDimension[1];
    }

    public int getMaxWidth() {
        return this.mMaxDimension[0];
    }

    public void setMaxWidth(int maxWidth) {
        this.mMaxDimension[0] = maxWidth;
    }

    public void setMaxHeight(int maxHeight) {
        this.mMaxDimension[1] = maxHeight;
    }

    public boolean isSpreadWidth() {
        return this.mMatchConstraintDefaultWidth == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinWidth == 0 && this.mMatchConstraintMaxWidth == 0 && this.mListDimensionBehaviors[0] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public boolean isSpreadHeight() {
        return this.mMatchConstraintDefaultHeight == 0 && this.mDimensionRatio == 0.0f && this.mMatchConstraintMinHeight == 0 && this.mMatchConstraintMaxHeight == 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT;
    }

    public void setHasBaseline(boolean hasBaseline2) {
        this.hasBaseline = hasBaseline2;
    }

    public boolean getHasBaseline() {
        return this.hasBaseline;
    }

    public boolean isInPlaceholder() {
        return this.inPlaceholder;
    }

    public void setInPlaceholder(boolean inPlaceholder2) {
        this.inPlaceholder = inPlaceholder2;
    }

    /* access modifiers changed from: protected */
    public void setInBarrier(int orientation, boolean value) {
        this.mIsInBarrier[orientation] = value;
    }

    public boolean isInBarrier(int orientation) {
        return this.mIsInBarrier[orientation];
    }

    public void setMeasureRequested(boolean measureRequested) {
        this.mMeasureRequested = measureRequested;
    }

    public boolean isMeasureRequested() {
        return this.mMeasureRequested && this.mVisibility != 8;
    }

    public void setWrapBehaviorInParent(int behavior) {
        if (behavior >= 0 && behavior <= 3) {
            this.mWrapBehaviorInParent = behavior;
        }
    }

    public int getWrapBehaviorInParent() {
        return this.mWrapBehaviorInParent;
    }

    public int getLastHorizontalMeasureSpec() {
        return this.mLastHorizontalMeasureSpec;
    }

    public int getLastVerticalMeasureSpec() {
        return this.mLastVerticalMeasureSpec;
    }

    public void setLastMeasureSpec(int horizontal, int vertical) {
        this.mLastHorizontalMeasureSpec = horizontal;
        this.mLastVerticalMeasureSpec = vertical;
        setMeasureRequested(false);
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = 0.0f;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        dimensionBehaviourArr[0] = dimensionBehaviour;
        dimensionBehaviourArr[1] = dimensionBehaviour;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mType = null;
        this.mHorizontalWrapVisited = false;
        this.mVerticalWrapVisited = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mHorizontalChainFixedPosition = false;
        this.mVerticalChainFixedPosition = false;
        float[] fArr = this.mWeight;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        int[] iArr = this.mMaxDimension;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedHasRatio = false;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mGroupsToSolver = false;
        boolean[] zArr = this.isTerminalWidget;
        zArr[0] = true;
        zArr[1] = true;
        this.mInVirtualLayout = false;
        boolean[] zArr2 = this.mIsInBarrier;
        zArr2[0] = false;
        zArr2[1] = false;
        this.mMeasureRequested = true;
        int[] iArr2 = this.mResolvedMatchConstraintDefault;
        iArr2[0] = 0;
        iArr2[1] = 0;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
    }

    private void serializeAnchor(StringBuilder ret, String side, ConstraintAnchor a) {
        if (a.mTarget != null) {
            ret.append(side);
            ret.append(" : [ '");
            ret.append(a.mTarget);
            ret.append("',");
            ret.append(a.mMargin);
            ret.append(",");
            ret.append(a.mGoneMargin);
            ret.append(",");
            ret.append(" ] ,\n");
        }
    }

    private void serializeCircle(StringBuilder ret, ConstraintAnchor a, float angle) {
        if (a.mTarget != null) {
            ret.append("circle : [ '");
            ret.append(a.mTarget);
            ret.append("',");
            ret.append(a.mMargin);
            ret.append(",");
            ret.append(angle);
            ret.append(",");
            ret.append(" ] ,\n");
        }
    }

    private void serializeAttribute(StringBuilder ret, String type, float value, float def) {
        if (value != def) {
            ret.append(type);
            ret.append(" :   ");
            ret.append(value);
            ret.append(",\n");
        }
    }

    private void serializeAttribute(StringBuilder ret, String type, int value, int def) {
        if (value != def) {
            ret.append(type);
            ret.append(" :   ");
            ret.append(value);
            ret.append(",\n");
        }
    }

    private void serializeDimensionRatio(StringBuilder ret, String type, float value, int whichSide) {
        if (value != 0.0f) {
            ret.append(type);
            ret.append(" :  [");
            ret.append(value);
            ret.append(",");
            ret.append(whichSide);
            ret.append("");
            ret.append("],\n");
        }
    }

    private void serializeSize(StringBuilder ret, String type, int size, int min, int max, int override, int matchConstraintMin, int matchConstraintDefault, float MatchConstraintPercent, float weight) {
        ret.append(type);
        ret.append(" :  {\n");
        serializeAttribute(ret, "size", size, Integer.MIN_VALUE);
        serializeAttribute(ret, "min", min, 0);
        serializeAttribute(ret, "max", max, Integer.MAX_VALUE);
        serializeAttribute(ret, "matchMin", matchConstraintMin, 0);
        serializeAttribute(ret, "matchDef", matchConstraintDefault, 0);
        serializeAttribute(ret, "matchPercent", matchConstraintDefault, 1);
        ret.append("},\n");
    }

    public StringBuilder serialize(StringBuilder ret) {
        ret.append("{\n");
        serializeAnchor(ret, "left", this.mLeft);
        serializeAnchor(ret, "top", this.mTop);
        serializeAnchor(ret, "right", this.mRight);
        serializeAnchor(ret, "bottom", this.mBottom);
        serializeAnchor(ret, "baseline", this.mBaseline);
        serializeAnchor(ret, "centerX", this.mCenterX);
        serializeAnchor(ret, "centerY", this.mCenterY);
        serializeCircle(ret, this.mCenter, this.mCircleConstraintAngle);
        serializeSize(ret, "width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
        serializeSize(ret, "height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
        serializeDimensionRatio(ret, "dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
        serializeAttribute(ret, "horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(ret, "verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
        ret.append("}\n");
        return ret;
    }

    public boolean oppositeDimensionDependsOn(int orientation) {
        int oppositeOrientation = orientation == 0 ? 1 : 0;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[orientation];
        DimensionBehaviour oppositeDimensionBehaviour = dimensionBehaviourArr[oppositeOrientation];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.MATCH_CONSTRAINT;
        if (dimensionBehaviour == dimensionBehaviour2 && oppositeDimensionBehaviour == dimensionBehaviour2) {
            return true;
        }
        return false;
    }

    public boolean oppositeDimensionsTied() {
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.MATCH_CONSTRAINT;
        return dimensionBehaviour == dimensionBehaviour2 && dimensionBehaviourArr[1] == dimensionBehaviour2;
    }

    public boolean hasDimensionOverride() {
        return (this.mWidthOverride == -1 && this.mHeightOverride == -1) ? false : true;
    }

    public ConstraintWidget() {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mResolvedHasRatio = false;
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP = false;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
    }

    public ConstraintWidget(String debugName) {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mResolvedHasRatio = false;
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP = false;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        addAnchors();
        setDebugName(debugName);
    }

    public ConstraintWidget(int x, int y, int width, int height) {
        this.measured = false;
        this.run = new WidgetRun[2];
        this.horizontalRun = null;
        this.verticalRun = null;
        this.isTerminalWidget = new boolean[]{true, true};
        this.mResolvedHasRatio = false;
        this.mMeasureRequested = true;
        this.OPTIMIZE_WRAP = false;
        this.OPTIMIZE_WRAP_ON_RESOLVED = true;
        this.mWidthOverride = -1;
        this.mHeightOverride = -1;
        this.frame = new WidgetFrame(this);
        this.resolvedHorizontal = false;
        this.resolvedVertical = false;
        this.horizontalSolvingPass = false;
        this.verticalSolvingPass = false;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        this.mWrapBehaviorInParent = 0;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mResolvedMatchConstraintDefault = new int[2];
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMaxWidth = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintMinHeight = 0;
        this.mMatchConstraintMaxHeight = 0;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        this.mMaxDimension = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        this.mCircleConstraintAngle = 0.0f;
        this.hasBaseline = false;
        this.mInVirtualLayout = false;
        this.mLastHorizontalMeasureSpec = 0;
        this.mLastVerticalMeasureSpec = 0;
        this.mLeft = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mTop = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mRight = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor;
        this.mListAnchors = new ConstraintAnchor[]{this.mLeft, this.mRight, this.mTop, this.mBottom, this.mBaseline, constraintAnchor};
        this.mAnchors = new ArrayList<>();
        this.mIsInBarrier = new boolean[2];
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.mX = 0;
        this.mY = 0;
        this.mRelX = 0;
        this.mRelY = 0;
        this.mOffsetX = 0;
        this.mOffsetY = 0;
        this.mBaselineDistance = 0;
        float f = DEFAULT_BIAS;
        this.mHorizontalBiasPercent = f;
        this.mVerticalBiasPercent = f;
        this.mContainerItemSkip = 0;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mGroupsToSolver = false;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.horizontalGroup = -1;
        this.verticalGroup = -1;
        this.mX = x;
        this.mY = y;
        this.mWidth = width;
        this.mHeight = height;
        addAnchors();
    }

    public ConstraintWidget(String debugName, int x, int y, int width, int height) {
        this(x, y, width, height);
        setDebugName(debugName);
    }

    public ConstraintWidget(int width, int height) {
        this(0, 0, width, height);
    }

    public void ensureWidgetRuns() {
        if (this.horizontalRun == null) {
            this.horizontalRun = new HorizontalWidgetRun(this);
        }
        if (this.verticalRun == null) {
            this.verticalRun = new VerticalWidgetRun(this);
        }
    }

    public ConstraintWidget(String debugName, int width, int height) {
        this(width, height);
        setDebugName(debugName);
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    public boolean isRoot() {
        return this.mParent == null;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public void setParent(ConstraintWidget widget) {
        this.mParent = widget;
    }

    public void setWidthWrapContent(boolean widthWrapContent) {
        this.mIsWidthWrapContent = widthWrapContent;
    }

    public boolean isWidthWrapContent() {
        return this.mIsWidthWrapContent;
    }

    public void setHeightWrapContent(boolean heightWrapContent) {
        this.mIsHeightWrapContent = heightWrapContent;
    }

    public boolean isHeightWrapContent() {
        return this.mIsHeightWrapContent;
    }

    public void connectCircularConstraint(ConstraintWidget target, float angle, int radius) {
        ConstraintAnchor.Type type = ConstraintAnchor.Type.CENTER;
        immediateConnect(type, target, type, radius, 0);
        this.mCircleConstraintAngle = angle;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public void setDebugName(String name) {
        this.mDebugName = name;
    }

    public void setDebugSolverName(LinearSystem system, String name) {
        this.mDebugName = name;
        SolverVariable left = system.createObjectVariable(this.mLeft);
        SolverVariable top = system.createObjectVariable(this.mTop);
        SolverVariable right = system.createObjectVariable(this.mRight);
        SolverVariable bottom = system.createObjectVariable(this.mBottom);
        left.setName(name + ".left");
        top.setName(name + ".top");
        right.setName(name + ".right");
        bottom.setName(name + ".bottom");
        SolverVariable baseline = system.createObjectVariable(this.mBaseline);
        baseline.setName(name + ".baseline");
    }

    public void createObjectVariables(LinearSystem system) {
        SolverVariable createObjectVariable = system.createObjectVariable(this.mLeft);
        SolverVariable createObjectVariable2 = system.createObjectVariable(this.mTop);
        SolverVariable createObjectVariable3 = system.createObjectVariable(this.mRight);
        SolverVariable createObjectVariable4 = system.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            system.createObjectVariable(this.mBaseline);
        }
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (this.mType != null) {
            str = "type: " + this.mType + " ";
        } else {
            str = str2;
        }
        sb.append(str);
        if (this.mDebugName != null) {
            str2 = "id: " + this.mDebugName + " ";
        }
        sb.append(str2);
        sb.append("(");
        sb.append(this.mX);
        sb.append(", ");
        sb.append(this.mY);
        sb.append(") - (");
        sb.append(this.mWidth);
        sb.append(" x ");
        sb.append(this.mHeight);
        sb.append(")");
        return sb.toString();
    }

    public int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) {
            return this.mX;
        }
        return ((ConstraintWidgetContainer) constraintWidget).mPaddingLeft + this.mX;
    }

    public int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) {
            return this.mY;
        }
        return ((ConstraintWidgetContainer) constraintWidget).mPaddingTop + this.mY;
    }

    public int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public int getOptimizerWrapWidth() {
        int w;
        int w2 = this.mWidth;
        if (this.mListDimensionBehaviors[0] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return w2;
        }
        if (this.mMatchConstraintDefaultWidth == 1) {
            w = Math.max(this.mMatchConstraintMinWidth, w2);
        } else if (this.mMatchConstraintMinWidth > 0) {
            w = this.mMatchConstraintMinWidth;
            this.mWidth = w;
        } else {
            w = 0;
        }
        int i = this.mMatchConstraintMaxWidth;
        if (i <= 0 || i >= w) {
            return w;
        }
        return this.mMatchConstraintMaxWidth;
    }

    public int getOptimizerWrapHeight() {
        int h;
        int h2 = this.mHeight;
        if (this.mListDimensionBehaviors[1] != DimensionBehaviour.MATCH_CONSTRAINT) {
            return h2;
        }
        if (this.mMatchConstraintDefaultHeight == 1) {
            h = Math.max(this.mMatchConstraintMinHeight, h2);
        } else if (this.mMatchConstraintMinHeight > 0) {
            h = this.mMatchConstraintMinHeight;
            this.mHeight = h;
        } else {
            h = 0;
        }
        int i = this.mMatchConstraintMaxHeight;
        if (i <= 0 || i >= h) {
            return h;
        }
        return this.mMatchConstraintMaxHeight;
    }

    public int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    public int getLength(int orientation) {
        if (orientation == 0) {
            return getWidth();
        }
        if (orientation == 1) {
            return getHeight();
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int getRootX() {
        return this.mX + this.mOffsetX;
    }

    /* access modifiers changed from: protected */
    public int getRootY() {
        return this.mY + this.mOffsetY;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getLeft() {
        return getX();
    }

    public int getTop() {
        return getY();
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public int getHorizontalMargin() {
        int margin = 0;
        ConstraintAnchor constraintAnchor = this.mLeft;
        if (constraintAnchor != null) {
            margin = 0 + constraintAnchor.mMargin;
        }
        ConstraintAnchor constraintAnchor2 = this.mRight;
        if (constraintAnchor2 != null) {
            return margin + constraintAnchor2.mMargin;
        }
        return margin;
    }

    public int getVerticalMargin() {
        int margin = 0;
        if (this.mLeft != null) {
            margin = 0 + this.mTop.mMargin;
        }
        if (this.mRight != null) {
            return margin + this.mBottom.mMargin;
        }
        return margin;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public float getBiasPercent(int orientation) {
        if (orientation == 0) {
            return this.mHorizontalBiasPercent;
        }
        if (orientation == 1) {
            return this.mVerticalBiasPercent;
        }
        return -1.0f;
    }

    public boolean hasBaseline() {
        return this.hasBaseline;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setX(int x) {
        this.mX = x;
    }

    public void setY(int y) {
        this.mY = y;
    }

    public void setOrigin(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public void setOffset(int x, int y) {
        this.mOffsetX = x;
        this.mOffsetY = y;
    }

    public void setGoneMargin(ConstraintAnchor.Type type, int goneMargin) {
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[type.ordinal()]) {
            case 1:
                this.mLeft.mGoneMargin = goneMargin;
                return;
            case 2:
                this.mTop.mGoneMargin = goneMargin;
                return;
            case 3:
                this.mRight.mGoneMargin = goneMargin;
                return;
            case 4:
                this.mBottom.mGoneMargin = goneMargin;
                return;
            case 5:
                this.mBaseline.mGoneMargin = goneMargin;
                return;
            default:
                return;
        }
    }

    public void setWidth(int w) {
        this.mWidth = w;
        int i = this.mMinWidth;
        if (w < i) {
            this.mWidth = i;
        }
    }

    public void setHeight(int h) {
        this.mHeight = h;
        int i = this.mMinHeight;
        if (h < i) {
            this.mHeight = i;
        }
    }

    public void setLength(int length, int orientation) {
        if (orientation == 0) {
            setWidth(length);
        } else if (orientation == 1) {
            setHeight(length);
        }
    }

    public void setHorizontalMatchStyle(int horizontalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultWidth = horizontalMatchStyle;
        this.mMatchConstraintMinWidth = min;
        this.mMatchConstraintMaxWidth = max == Integer.MAX_VALUE ? 0 : max;
        this.mMatchConstraintPercentWidth = percent;
        if (percent > 0.0f && percent < 1.0f && horizontalMatchStyle == 0) {
            this.mMatchConstraintDefaultWidth = 2;
        }
    }

    public void setVerticalMatchStyle(int verticalMatchStyle, int min, int max, float percent) {
        this.mMatchConstraintDefaultHeight = verticalMatchStyle;
        this.mMatchConstraintMinHeight = min;
        this.mMatchConstraintMaxHeight = max == Integer.MAX_VALUE ? 0 : max;
        this.mMatchConstraintPercentHeight = percent;
        if (percent > 0.0f && percent < 1.0f && verticalMatchStyle == 0) {
            this.mMatchConstraintDefaultHeight = 2;
        }
    }

    public void setDimensionRatio(String ratio) {
        int commaIndex;
        if (ratio == null || ratio.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int dimensionRatioSide = -1;
        float dimensionRatio = 0.0f;
        int len = ratio.length();
        int commaIndex2 = ratio.indexOf(44);
        if (commaIndex2 <= 0 || commaIndex2 >= len - 1) {
            commaIndex = 0;
        } else {
            String dimension = ratio.substring(0, commaIndex2);
            if (dimension.equalsIgnoreCase(ExifInterface.LONGITUDE_WEST)) {
                dimensionRatioSide = 0;
            } else if (dimension.equalsIgnoreCase("H")) {
                dimensionRatioSide = 1;
            }
            commaIndex = commaIndex2 + 1;
        }
        int colonIndex = ratio.indexOf(58);
        if (colonIndex < 0 || colonIndex >= len - 1) {
            String r = ratio.substring(commaIndex);
            if (r.length() > 0) {
                try {
                    dimensionRatio = Float.parseFloat(r);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            String nominator = ratio.substring(commaIndex, colonIndex);
            String denominator = ratio.substring(colonIndex + 1);
            if (nominator.length() > 0 && denominator.length() > 0) {
                try {
                    float nominatorValue = Float.parseFloat(nominator);
                    float denominatorValue = Float.parseFloat(denominator);
                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                        dimensionRatio = dimensionRatioSide == 1 ? Math.abs(denominatorValue / nominatorValue) : Math.abs(nominatorValue / denominatorValue);
                    }
                } catch (NumberFormatException e2) {
                }
            }
        }
        if (dimensionRatio > 0.0f) {
            this.mDimensionRatio = dimensionRatio;
            this.mDimensionRatioSide = dimensionRatioSide;
        }
    }

    public void setDimensionRatio(float ratio, int dimensionRatioSide) {
        this.mDimensionRatio = ratio;
        this.mDimensionRatioSide = dimensionRatioSide;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public void setHorizontalBiasPercent(float horizontalBiasPercent) {
        this.mHorizontalBiasPercent = horizontalBiasPercent;
    }

    public void setVerticalBiasPercent(float verticalBiasPercent) {
        this.mVerticalBiasPercent = verticalBiasPercent;
    }

    public void setMinWidth(int w) {
        if (w < 0) {
            this.mMinWidth = 0;
        } else {
            this.mMinWidth = w;
        }
    }

    public void setMinHeight(int h) {
        if (h < 0) {
            this.mMinHeight = 0;
        } else {
            this.mMinHeight = h;
        }
    }

    public void setDimension(int w, int h) {
        this.mWidth = w;
        int i = this.mMinWidth;
        if (w < i) {
            this.mWidth = i;
        }
        this.mHeight = h;
        int i2 = this.mMinHeight;
        if (h < i2) {
            this.mHeight = i2;
        }
    }

    public void setFrame(int left, int top, int right, int bottom) {
        int w = right - left;
        int h = bottom - top;
        this.mX = left;
        this.mY = top;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.FIXED;
        if (dimensionBehaviour == dimensionBehaviour2 && w < this.mWidth) {
            w = this.mWidth;
        }
        if (dimensionBehaviourArr[1] == dimensionBehaviour2 && h < this.mHeight) {
            h = this.mHeight;
        }
        this.mWidth = w;
        this.mHeight = h;
        int i = this.mMinHeight;
        if (h < i) {
            this.mHeight = i;
        }
        int i2 = this.mMinWidth;
        if (w < i2) {
            this.mWidth = i2;
        }
        int i3 = this.mMatchConstraintMaxWidth;
        if (i3 > 0 && dimensionBehaviourArr[0] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mWidth = Math.min(this.mWidth, i3);
        }
        int i4 = this.mMatchConstraintMaxHeight;
        if (i4 > 0 && this.mListDimensionBehaviors[1] == DimensionBehaviour.MATCH_CONSTRAINT) {
            this.mHeight = Math.min(this.mHeight, i4);
        }
        int i5 = this.mWidth;
        if (w != i5) {
            this.mWidthOverride = i5;
        }
        int i6 = this.mHeight;
        if (h != i6) {
            this.mHeightOverride = i6;
        }
    }

    public void setFrame(int start, int end, int orientation) {
        if (orientation == 0) {
            setHorizontalDimension(start, end);
        } else if (orientation == 1) {
            setVerticalDimension(start, end);
        }
    }

    public void setHorizontalDimension(int left, int right) {
        this.mX = left;
        int i = right - left;
        this.mWidth = i;
        int i2 = this.mMinWidth;
        if (i < i2) {
            this.mWidth = i2;
        }
    }

    public void setVerticalDimension(int top, int bottom) {
        this.mY = top;
        int i = bottom - top;
        this.mHeight = i;
        int i2 = this.mMinHeight;
        if (i < i2) {
            this.mHeight = i2;
        }
    }

    /* access modifiers changed from: package-private */
    public int getRelativePositioning(int orientation) {
        if (orientation == 0) {
            return this.mRelX;
        }
        if (orientation == 1) {
            return this.mRelY;
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void setRelativePositioning(int offset, int orientation) {
        if (orientation == 0) {
            this.mRelX = offset;
        } else if (orientation == 1) {
            this.mRelY = offset;
        }
    }

    public void setBaselineDistance(int baseline) {
        this.mBaselineDistance = baseline;
        this.hasBaseline = baseline > 0;
    }

    public void setCompanionWidget(Object companion) {
        this.mCompanionWidget = companion;
    }

    public void setContainerItemSkip(int skip) {
        if (skip >= 0) {
            this.mContainerItemSkip = skip;
        } else {
            this.mContainerItemSkip = 0;
        }
    }

    public int getContainerItemSkip() {
        return this.mContainerItemSkip;
    }

    public void setHorizontalWeight(float horizontalWeight) {
        this.mWeight[0] = horizontalWeight;
    }

    public void setVerticalWeight(float verticalWeight) {
        this.mWeight[1] = verticalWeight;
    }

    public void setHorizontalChainStyle(int horizontalChainStyle) {
        this.mHorizontalChainStyle = horizontalChainStyle;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public void setVerticalChainStyle(int verticalChainStyle) {
        this.mVerticalChainStyle = verticalChainStyle;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public boolean allowedInBarrier() {
        return this.mVisibility != 8;
    }

    public void immediateConnect(ConstraintAnchor.Type startType, ConstraintWidget target, ConstraintAnchor.Type endType, int margin, int goneMargin) {
        getAnchor(startType).connect(target.getAnchor(endType), margin, goneMargin, true);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin) {
        if (from.getOwner() == this) {
            connect(from.getType(), to.getOwner(), to.getType(), margin);
        }
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo) {
        connect(constraintFrom, target, constraintTo, 0);
    }

    public void connect(ConstraintAnchor.Type constraintFrom, ConstraintWidget target, ConstraintAnchor.Type constraintTo, int margin) {
        ConstraintAnchor.Type type;
        ConstraintAnchor.Type type2;
        ConstraintAnchor.Type type3 = constraintFrom;
        ConstraintWidget constraintWidget = target;
        ConstraintAnchor.Type type4 = constraintTo;
        ConstraintAnchor.Type type5 = ConstraintAnchor.Type.CENTER;
        if (type3 != type5) {
            ConstraintAnchor.Type type6 = ConstraintAnchor.Type.CENTER_X;
            if (type3 == type6 && (type4 == (type2 = ConstraintAnchor.Type.LEFT) || type4 == ConstraintAnchor.Type.RIGHT)) {
                ConstraintAnchor left = getAnchor(type2);
                ConstraintAnchor targetAnchor = target.getAnchor(constraintTo);
                ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
                left.connect(targetAnchor, 0);
                right.connect(targetAnchor, 0);
                getAnchor(type6).connect(targetAnchor, 0);
                int i = margin;
                return;
            }
            ConstraintAnchor.Type type7 = ConstraintAnchor.Type.CENTER_Y;
            if (type3 == type7 && (type4 == (type = ConstraintAnchor.Type.TOP) || type4 == ConstraintAnchor.Type.BOTTOM)) {
                ConstraintAnchor targetAnchor2 = target.getAnchor(constraintTo);
                getAnchor(type).connect(targetAnchor2, 0);
                getAnchor(ConstraintAnchor.Type.BOTTOM).connect(targetAnchor2, 0);
                getAnchor(type7).connect(targetAnchor2, 0);
                int i2 = margin;
            } else if (type3 == type6 && type4 == type6) {
                ConstraintAnchor.Type type8 = ConstraintAnchor.Type.LEFT;
                getAnchor(type8).connect(constraintWidget.getAnchor(type8), 0);
                ConstraintAnchor.Type type9 = ConstraintAnchor.Type.RIGHT;
                getAnchor(type9).connect(constraintWidget.getAnchor(type9), 0);
                getAnchor(type6).connect(target.getAnchor(constraintTo), 0);
                int i3 = margin;
            } else if (type3 == type7 && type4 == type7) {
                ConstraintAnchor.Type type10 = ConstraintAnchor.Type.TOP;
                getAnchor(type10).connect(constraintWidget.getAnchor(type10), 0);
                ConstraintAnchor.Type type11 = ConstraintAnchor.Type.BOTTOM;
                getAnchor(type11).connect(constraintWidget.getAnchor(type11), 0);
                getAnchor(type7).connect(target.getAnchor(constraintTo), 0);
                int i4 = margin;
            } else {
                ConstraintAnchor fromAnchor = getAnchor(constraintFrom);
                ConstraintAnchor toAnchor = target.getAnchor(constraintTo);
                if (fromAnchor.isValidConnection(toAnchor)) {
                    ConstraintAnchor.Type type12 = ConstraintAnchor.Type.BASELINE;
                    if (type3 == type12) {
                        ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
                        ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
                        if (top != null) {
                            top.reset();
                        }
                        if (bottom != null) {
                            bottom.reset();
                        }
                    } else if (type3 == ConstraintAnchor.Type.TOP || type3 == ConstraintAnchor.Type.BOTTOM) {
                        ConstraintAnchor baseline = getAnchor(type12);
                        if (baseline != null) {
                            baseline.reset();
                        }
                        ConstraintAnchor center = getAnchor(type5);
                        if (center.getTarget() != toAnchor) {
                            center.reset();
                        }
                        ConstraintAnchor opposite = getAnchor(constraintFrom).getOpposite();
                        ConstraintAnchor centerY = getAnchor(type7);
                        if (centerY.isConnected()) {
                            opposite.reset();
                            centerY.reset();
                        }
                    } else if (type3 == ConstraintAnchor.Type.LEFT || type3 == ConstraintAnchor.Type.RIGHT) {
                        ConstraintAnchor center2 = getAnchor(type5);
                        if (center2.getTarget() != toAnchor) {
                            center2.reset();
                        }
                        ConstraintAnchor opposite2 = getAnchor(constraintFrom).getOpposite();
                        ConstraintAnchor centerX = getAnchor(type6);
                        if (centerX.isConnected()) {
                            opposite2.reset();
                            centerX.reset();
                        }
                    }
                    fromAnchor.connect(toAnchor, margin);
                    return;
                }
                int i5 = margin;
            }
        } else if (type4 == type5) {
            ConstraintAnchor.Type type13 = ConstraintAnchor.Type.LEFT;
            ConstraintAnchor left2 = getAnchor(type13);
            ConstraintAnchor.Type type14 = ConstraintAnchor.Type.RIGHT;
            ConstraintAnchor right2 = getAnchor(type14);
            ConstraintAnchor.Type type15 = ConstraintAnchor.Type.TOP;
            ConstraintAnchor top2 = getAnchor(type15);
            ConstraintAnchor.Type type16 = ConstraintAnchor.Type.BOTTOM;
            ConstraintAnchor bottom2 = getAnchor(type16);
            boolean centerX2 = false;
            boolean centerY2 = false;
            if ((left2 == null || !left2.isConnected()) && (right2 == null || !right2.isConnected())) {
                connect(type13, constraintWidget, type13, 0);
                connect(type14, constraintWidget, type14, 0);
                centerX2 = true;
            }
            if ((top2 == null || !top2.isConnected()) && (bottom2 == null || !bottom2.isConnected())) {
                connect(type15, constraintWidget, type15, 0);
                connect(type16, constraintWidget, type16, 0);
                centerY2 = true;
            }
            if (centerX2 && centerY2) {
                getAnchor(type5).connect(constraintWidget.getAnchor(type5), 0);
            } else if (centerX2) {
                ConstraintAnchor.Type type17 = ConstraintAnchor.Type.CENTER_X;
                getAnchor(type17).connect(constraintWidget.getAnchor(type17), 0);
            } else if (centerY2) {
                ConstraintAnchor.Type type18 = ConstraintAnchor.Type.CENTER_Y;
                getAnchor(type18).connect(constraintWidget.getAnchor(type18), 0);
            }
            int i6 = margin;
        } else {
            ConstraintAnchor.Type type19 = ConstraintAnchor.Type.LEFT;
            if (type4 == type19 || type4 == ConstraintAnchor.Type.RIGHT) {
                connect(type19, constraintWidget, type4, 0);
                try {
                    connect(ConstraintAnchor.Type.RIGHT, constraintWidget, type4, 0);
                    getAnchor(type5).connect(target.getAnchor(constraintTo), 0);
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                ConstraintAnchor.Type type20 = ConstraintAnchor.Type.TOP;
                if (type4 == type20 || type4 == ConstraintAnchor.Type.BOTTOM) {
                    connect(type20, constraintWidget, type4, 0);
                    connect(ConstraintAnchor.Type.BOTTOM, constraintWidget, type4, 0);
                    getAnchor(type5).connect(target.getAnchor(constraintTo), 0);
                    int i7 = margin;
                    return;
                }
            }
            int i8 = margin;
        }
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
    }

    public void resetAnchor(ConstraintAnchor anchor) {
        if (getParent() == null || !(getParent() instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            ConstraintAnchor left = getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor right = getAnchor(ConstraintAnchor.Type.RIGHT);
            ConstraintAnchor top = getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor bottom = getAnchor(ConstraintAnchor.Type.BOTTOM);
            ConstraintAnchor center = getAnchor(ConstraintAnchor.Type.CENTER);
            ConstraintAnchor centerX = getAnchor(ConstraintAnchor.Type.CENTER_X);
            ConstraintAnchor centerY = getAnchor(ConstraintAnchor.Type.CENTER_Y);
            if (anchor == center) {
                if (left.isConnected() && right.isConnected() && left.getTarget() == right.getTarget()) {
                    left.reset();
                    right.reset();
                }
                if (top.isConnected() && bottom.isConnected() && top.getTarget() == bottom.getTarget()) {
                    top.reset();
                    bottom.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == centerX) {
                if (left.isConnected() && right.isConnected() && left.getTarget().getOwner() == right.getTarget().getOwner()) {
                    left.reset();
                    right.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
            } else if (anchor == centerY) {
                if (top.isConnected() && bottom.isConnected() && top.getTarget().getOwner() == bottom.getTarget().getOwner()) {
                    top.reset();
                    bottom.reset();
                }
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == left || anchor == right) {
                if (left.isConnected() && left.getTarget() == right.getTarget()) {
                    center.reset();
                }
            } else if ((anchor == top || anchor == bottom) && top.isConnected() && top.getTarget() == bottom.getTarget()) {
                center.reset();
            }
            anchor.reset();
        }
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int mAnchorsSize = this.mAnchors.size();
            for (int i = 0; i < mAnchorsSize; i++) {
                this.mAnchors.get(i).reset();
            }
        }
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type anchorType) {
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[anchorType.ordinal()]) {
            case 1:
                return this.mLeft;
            case 2:
                return this.mTop;
            case 3:
                return this.mRight;
            case 4:
                return this.mBottom;
            case 5:
                return this.mBaseline;
            case 6:
                return this.mCenter;
            case 7:
                return this.mCenterX;
            case 8:
                return this.mCenterY;
            case 9:
                return null;
            default:
                throw new AssertionError(anchorType.name());
        }
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mListDimensionBehaviors[0];
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mListDimensionBehaviors[1];
    }

    public DimensionBehaviour getDimensionBehaviour(int orientation) {
        if (orientation == 0) {
            return getHorizontalDimensionBehaviour();
        }
        if (orientation == 1) {
            return getVerticalDimensionBehaviour();
        }
        return null;
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mListDimensionBehaviors[0] = behaviour;
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mListDimensionBehaviors[1] = behaviour;
    }

    public boolean isInHorizontalChain() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        r0 = r3.mTop;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.constraintlayout.core.widgets.ConstraintWidget getPreviousChainMember(int r4) {
        /*
            r3 = this;
            if (r4 != 0) goto L_0x000f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r3.mLeft
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r0.mTarget
            if (r1 == 0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 != r0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r1.mOwner
            return r0
        L_0x000f:
            r0 = 1
            if (r4 != r0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r3.mTop
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r0.mTarget
            if (r1 == 0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 != r0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r1.mOwner
            return r0
        L_0x001f:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.ConstraintWidget.getPreviousChainMember(int):androidx.constraintlayout.core.widgets.ConstraintWidget");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        r0 = r3.mBottom;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public androidx.constraintlayout.core.widgets.ConstraintWidget getNextChainMember(int r4) {
        /*
            r3 = this;
            if (r4 != 0) goto L_0x000f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r3.mRight
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r0.mTarget
            if (r1 == 0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 != r0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r1.mOwner
            return r0
        L_0x000f:
            r0 = 1
            if (r4 != r0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r3.mBottom
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r0.mTarget
            if (r1 == 0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 != r0) goto L_0x001f
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r1.mOwner
            return r0
        L_0x001f:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.ConstraintWidget.getNextChainMember(int):androidx.constraintlayout.core.widgets.ConstraintWidget");
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInHorizontalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.LEFT);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.RIGHT).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    public boolean isInVerticalChain() {
        ConstraintAnchor constraintAnchor = this.mTop;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mBottom;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return false;
        }
        return true;
    }

    public ConstraintWidget getVerticalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInVerticalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(ConstraintAnchor.Type.TOP);
            ConstraintAnchor targetAnchor = null;
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            if (target != null) {
                targetAnchor = target.getAnchor(ConstraintAnchor.Type.BOTTOM).getTarget();
            }
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    private boolean isChainHead(int orientation) {
        int offset = orientation * 2;
        ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
        return (constraintAnchorArr[offset].mTarget == null || constraintAnchorArr[offset].mTarget.mTarget == constraintAnchorArr[offset] || constraintAnchorArr[offset + 1].mTarget == null || constraintAnchorArr[offset + 1].mTarget.mTarget != constraintAnchorArr[offset + 1]) ? false : true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v31, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r37v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v32, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v34, resolved type: int} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:191:0x031b  */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x031e  */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x032a  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x032d  */
    /* JADX WARNING: Removed duplicated region for block: B:204:0x033c  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x033e  */
    /* JADX WARNING: Removed duplicated region for block: B:208:0x0343  */
    /* JADX WARNING: Removed duplicated region for block: B:209:0x0347  */
    /* JADX WARNING: Removed duplicated region for block: B:212:0x0352  */
    /* JADX WARNING: Removed duplicated region for block: B:213:0x0356  */
    /* JADX WARNING: Removed duplicated region for block: B:218:0x036b A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:241:0x0425  */
    /* JADX WARNING: Removed duplicated region for block: B:242:0x042d  */
    /* JADX WARNING: Removed duplicated region for block: B:245:0x0433  */
    /* JADX WARNING: Removed duplicated region for block: B:246:0x043a  */
    /* JADX WARNING: Removed duplicated region for block: B:249:0x0463  */
    /* JADX WARNING: Removed duplicated region for block: B:250:0x0466  */
    /* JADX WARNING: Removed duplicated region for block: B:252:0x04c2  */
    /* JADX WARNING: Removed duplicated region for block: B:255:0x04dd  */
    /* JADX WARNING: Removed duplicated region for block: B:273:0x0538  */
    /* JADX WARNING: Removed duplicated region for block: B:277:0x054b  */
    /* JADX WARNING: Removed duplicated region for block: B:278:0x054e  */
    /* JADX WARNING: Removed duplicated region for block: B:282:0x0555  */
    /* JADX WARNING: Removed duplicated region for block: B:316:0x05f7  */
    /* JADX WARNING: Removed duplicated region for block: B:317:0x05fa  */
    /* JADX WARNING: Removed duplicated region for block: B:319:0x0636  */
    /* JADX WARNING: Removed duplicated region for block: B:321:0x063a  */
    /* JADX WARNING: Removed duplicated region for block: B:327:0x0666  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToSolver(androidx.constraintlayout.core.LinearSystem r76, boolean r77) {
        /*
            r75 = this;
            r15 = r75
            r14 = r76
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mLeft
            androidx.constraintlayout.core.SolverVariable r13 = r14.createObjectVariable(r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mRight
            androidx.constraintlayout.core.SolverVariable r12 = r14.createObjectVariable(r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mTop
            androidx.constraintlayout.core.SolverVariable r11 = r14.createObjectVariable(r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mBottom
            androidx.constraintlayout.core.SolverVariable r10 = r14.createObjectVariable(r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mBaseline
            androidx.constraintlayout.core.SolverVariable r9 = r14.createObjectVariable(r0)
            r0 = 0
            r1 = 0
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            r8 = 1
            r5 = 0
            if (r2 == 0) goto L_0x005b
            if (r2 == 0) goto L_0x0036
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r2.mListDimensionBehaviors
            r3 = r3[r5]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r3 != r4) goto L_0x0036
            r3 = r8
            goto L_0x0037
        L_0x0036:
            r3 = r5
        L_0x0037:
            r0 = r3
            if (r2 == 0) goto L_0x0044
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r2.mListDimensionBehaviors
            r2 = r2[r8]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r2 != r3) goto L_0x0044
            r2 = r8
            goto L_0x0045
        L_0x0044:
            r2 = r5
        L_0x0045:
            r1 = r2
            int r2 = r15.mWrapBehaviorInParent
            switch(r2) {
                case 1: goto L_0x0057;
                case 2: goto L_0x0053;
                case 3: goto L_0x004e;
                default: goto L_0x004b;
            }
        L_0x004b:
            r4 = r0
            r3 = r1
            goto L_0x005d
        L_0x004e:
            r0 = 0
            r1 = 0
            r4 = r0
            r3 = r1
            goto L_0x005d
        L_0x0053:
            r0 = 0
            r4 = r0
            r3 = r1
            goto L_0x005d
        L_0x0057:
            r1 = 0
            r4 = r0
            r3 = r1
            goto L_0x005d
        L_0x005b:
            r4 = r0
            r3 = r1
        L_0x005d:
            int r0 = r15.mVisibility
            r2 = 8
            if (r0 != r2) goto L_0x0074
            boolean r0 = r75.hasDependencies()
            if (r0 != 0) goto L_0x0074
            boolean[] r0 = r15.mIsInBarrier
            boolean r1 = r0[r5]
            if (r1 != 0) goto L_0x0074
            boolean r0 = r0[r8]
            if (r0 != 0) goto L_0x0074
            return
        L_0x0074:
            boolean r0 = r15.resolvedHorizontal
            if (r0 != 0) goto L_0x007c
            boolean r1 = r15.resolvedVertical
            if (r1 == 0) goto L_0x00fb
        L_0x007c:
            if (r0 == 0) goto L_0x00ac
            int r0 = r15.mX
            r14.addEquality(r13, r0)
            int r0 = r15.mX
            int r1 = r15.mWidth
            int r0 = r0 + r1
            r14.addEquality(r12, r0)
            if (r4 == 0) goto L_0x00ac
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x00ac
            boolean r1 = r15.OPTIMIZE_WRAP_ON_RESOLVED
            if (r1 == 0) goto L_0x00a2
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r0 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r0
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mLeft
            r0.addHorizontalWrapMinVariable(r1)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mRight
            r0.addHorizontalWrapMaxVariable(r1)
            goto L_0x00ac
        L_0x00a2:
            r1 = 5
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mRight
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r14.addGreaterThan(r0, r12, r5, r1)
        L_0x00ac:
            boolean r0 = r15.resolvedVertical
            if (r0 == 0) goto L_0x00ee
            int r0 = r15.mY
            r14.addEquality(r11, r0)
            int r0 = r15.mY
            int r1 = r15.mHeight
            int r0 = r0 + r1
            r14.addEquality(r10, r0)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r15.mBaseline
            boolean r0 = r0.hasDependents()
            if (r0 == 0) goto L_0x00cd
            int r0 = r15.mY
            int r1 = r15.mBaselineDistance
            int r0 = r0 + r1
            r14.addEquality(r9, r0)
        L_0x00cd:
            if (r3 == 0) goto L_0x00ee
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x00ee
            boolean r1 = r15.OPTIMIZE_WRAP_ON_RESOLVED
            if (r1 == 0) goto L_0x00e4
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r0 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r0
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mTop
            r0.addVerticalWrapMinVariable(r1)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mBottom
            r0.addVerticalWrapMaxVariable(r1)
            goto L_0x00ee
        L_0x00e4:
            r1 = 5
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mBottom
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r14.addGreaterThan(r0, r10, r5, r1)
        L_0x00ee:
            boolean r0 = r15.resolvedHorizontal
            if (r0 == 0) goto L_0x00fb
            boolean r0 = r15.resolvedVertical
            if (r0 == 0) goto L_0x00fb
            r15.resolvedHorizontal = r5
            r15.resolvedVertical = r5
            return
        L_0x00fb:
            androidx.constraintlayout.core.Metrics r0 = androidx.constraintlayout.core.LinearSystem.sMetrics
            r6 = 1
            if (r0 == 0) goto L_0x0109
            r16 = r3
            long r2 = r0.widgets
            long r2 = r2 + r6
            r0.widgets = r2
            goto L_0x010b
        L_0x0109:
            r16 = r3
        L_0x010b:
            if (r77 == 0) goto L_0x019d
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r1 = r15.horizontalRun
            if (r1 == 0) goto L_0x019d
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r2 = r15.verticalRun
            if (r2 == 0) goto L_0x019d
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r3 = r1.start
            boolean r8 = r3.resolved
            if (r8 == 0) goto L_0x019d
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r1.end
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x019d
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r2.start
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x019d
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r2.end
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x019d
            if (r0 == 0) goto L_0x0134
            long r1 = r0.graphSolved
            long r1 = r1 + r6
            r0.graphSolved = r1
        L_0x0134:
            int r0 = r3.value
            r14.addEquality(r13, r0)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            int r0 = r0.value
            r14.addEquality(r12, r0)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r0 = r15.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.start
            int r0 = r0.value
            r14.addEquality(r11, r0)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r0 = r15.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            int r0 = r0.value
            r14.addEquality(r10, r0)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r0 = r15.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.baseline
            int r0 = r0.value
            r14.addEquality(r9, r0)
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x0198
            if (r4 == 0) goto L_0x017c
            boolean[] r0 = r15.isTerminalWidget
            boolean r0 = r0[r5]
            if (r0 == 0) goto L_0x017c
            boolean r0 = r75.isInHorizontalChain()
            if (r0 != 0) goto L_0x017c
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mRight
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r1 = 8
            r14.addGreaterThan(r0, r12, r5, r1)
        L_0x017c:
            if (r16 == 0) goto L_0x0198
            boolean[] r0 = r15.isTerminalWidget
            r1 = 1
            boolean r0 = r0[r1]
            if (r0 == 0) goto L_0x0198
            boolean r0 = r75.isInVerticalChain()
            if (r0 != 0) goto L_0x0198
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mBottom
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r1 = 8
            r14.addGreaterThan(r0, r10, r5, r1)
        L_0x0198:
            r15.resolvedHorizontal = r5
            r15.resolvedVertical = r5
            return
        L_0x019d:
            if (r0 == 0) goto L_0x01a4
            long r1 = r0.linearSolved
            long r1 = r1 + r6
            r0.linearSolved = r1
        L_0x01a4:
            r0 = 0
            r1 = 0
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            if (r2 == 0) goto L_0x021e
            boolean r2 = r15.isChainHead(r5)
            if (r2 == 0) goto L_0x01b9
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r2 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r2
            r2.addChain(r15, r5)
            r0 = 1
            goto L_0x01bd
        L_0x01b9:
            boolean r0 = r75.isInHorizontalChain()
        L_0x01bd:
            r2 = 1
            boolean r3 = r15.isChainHead(r2)
            if (r3 == 0) goto L_0x01cd
            androidx.constraintlayout.core.widgets.ConstraintWidget r3 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r3 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r3
            r3.addChain(r15, r2)
            r1 = 1
            goto L_0x01d1
        L_0x01cd:
            boolean r1 = r75.isInVerticalChain()
        L_0x01d1:
            if (r0 != 0) goto L_0x01f3
            if (r4 == 0) goto L_0x01f3
            int r2 = r15.mVisibility
            r3 = 8
            if (r2 == r3) goto L_0x01f3
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mLeft
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 != 0) goto L_0x01f3
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mRight
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 != 0) goto L_0x01f3
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mRight
            androidx.constraintlayout.core.SolverVariable r2 = r14.createObjectVariable(r2)
            r3 = 1
            r14.addGreaterThan(r2, r12, r5, r3)
        L_0x01f3:
            if (r1 != 0) goto L_0x0219
            if (r16 == 0) goto L_0x0219
            int r2 = r15.mVisibility
            r3 = 8
            if (r2 == r3) goto L_0x0219
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mTop
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 != 0) goto L_0x0219
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mBottom
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 != 0) goto L_0x0219
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mBaseline
            if (r2 != 0) goto L_0x0219
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mBottom
            androidx.constraintlayout.core.SolverVariable r2 = r14.createObjectVariable(r2)
            r3 = 1
            r14.addGreaterThan(r2, r10, r5, r3)
        L_0x0219:
            r56 = r0
            r57 = r1
            goto L_0x0222
        L_0x021e:
            r56 = r0
            r57 = r1
        L_0x0222:
            int r0 = r15.mWidth
            int r1 = r15.mMinWidth
            if (r0 >= r1) goto L_0x022a
            int r0 = r15.mMinWidth
        L_0x022a:
            int r1 = r15.mHeight
            int r2 = r15.mMinHeight
            if (r1 >= r2) goto L_0x0232
            int r1 = r15.mMinHeight
        L_0x0232:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r3 = r2[r5]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r3 == r8) goto L_0x023c
            r3 = 1
            goto L_0x023d
        L_0x023c:
            r3 = r5
        L_0x023d:
            r6 = 1
            r7 = r2[r6]
            if (r7 == r8) goto L_0x0244
            r6 = 1
            goto L_0x0245
        L_0x0244:
            r6 = r5
        L_0x0245:
            r7 = 0
            int r5 = r15.mDimensionRatioSide
            r15.mResolvedDimensionRatioSide = r5
            r17 = r0
            float r0 = r15.mDimensionRatio
            r15.mResolvedDimensionRatio = r0
            r21 = r1
            int r1 = r15.mMatchConstraintDefaultWidth
            r22 = r7
            int r7 = r15.mMatchConstraintDefaultHeight
            r23 = 0
            int r23 = (r0 > r23 ? 1 : (r0 == r23 ? 0 : -1))
            r27 = r9
            if (r23 <= 0) goto L_0x02f8
            int r9 = r15.mVisibility
            r28 = r10
            r10 = 8
            if (r9 == r10) goto L_0x02f3
            r9 = 1
            r22 = r9
            r10 = 0
            r9 = r2[r10]
            if (r9 != r8) goto L_0x0273
            if (r1 != 0) goto L_0x0273
            r1 = 3
        L_0x0273:
            r9 = 1
            r10 = r2[r9]
            if (r10 != r8) goto L_0x027b
            if (r7 != 0) goto L_0x027b
            r7 = 3
        L_0x027b:
            r9 = 0
            r10 = r2[r9]
            if (r10 != r8) goto L_0x0293
            r10 = 1
            r9 = r2[r10]
            if (r9 != r8) goto L_0x0293
            r9 = 3
            if (r1 != r9) goto L_0x0293
            if (r7 != r9) goto L_0x0293
            r9 = r16
            r15.setupDimensionRatio(r4, r9, r3, r6)
            r16 = r3
            goto L_0x02fe
        L_0x0293:
            r9 = r16
            r16 = r3
            r10 = 0
            r3 = r2[r10]
            if (r3 != r8) goto L_0x02be
            r3 = 3
            if (r1 != r3) goto L_0x02be
            r15.mResolvedDimensionRatioSide = r10
            int r3 = r15.mHeight
            float r3 = (float) r3
            float r0 = r0 * r3
            int r0 = (int) r0
            r3 = 1
            r2 = r2[r3]
            if (r2 == r8) goto L_0x02b5
            r1 = 4
            r2 = 0
            r58 = r1
            r10 = r2
            r59 = r7
            r29 = r21
            goto L_0x0308
        L_0x02b5:
            r58 = r1
            r59 = r7
            r29 = r21
            r10 = r22
            goto L_0x0308
        L_0x02be:
            r3 = 1
            r10 = r2[r3]
            if (r10 != r8) goto L_0x02fe
            r10 = 3
            if (r7 != r10) goto L_0x02fe
            r15.mResolvedDimensionRatioSide = r3
            r3 = -1
            if (r5 != r3) goto L_0x02d0
            r3 = 1065353216(0x3f800000, float:1.0)
            float r3 = r3 / r0
            r15.mResolvedDimensionRatio = r3
        L_0x02d0:
            float r0 = r15.mResolvedDimensionRatio
            int r3 = r15.mWidth
            float r3 = (float) r3
            float r0 = r0 * r3
            int r0 = (int) r0
            r3 = 0
            r2 = r2[r3]
            if (r2 == r8) goto L_0x02e8
            r7 = 4
            r2 = 0
            r29 = r0
            r58 = r1
            r10 = r2
            r59 = r7
            r0 = r17
            goto L_0x0308
        L_0x02e8:
            r29 = r0
            r58 = r1
            r59 = r7
            r0 = r17
            r10 = r22
            goto L_0x0308
        L_0x02f3:
            r9 = r16
            r16 = r3
            goto L_0x02fe
        L_0x02f8:
            r28 = r10
            r9 = r16
            r16 = r3
        L_0x02fe:
            r58 = r1
            r59 = r7
            r0 = r17
            r29 = r21
            r10 = r22
        L_0x0308:
            int[] r1 = r15.mResolvedMatchConstraintDefault
            r2 = 0
            r1[r2] = r58
            r2 = 1
            r1[r2] = r59
            r15.mResolvedHasRatio = r10
            if (r10 == 0) goto L_0x031e
            int r1 = r15.mResolvedDimensionRatioSide
            if (r1 == 0) goto L_0x031b
            r2 = -1
            if (r1 != r2) goto L_0x031e
        L_0x031b:
            r17 = 1
            goto L_0x0320
        L_0x031e:
            r17 = 0
        L_0x0320:
            if (r10 == 0) goto L_0x032d
            int r1 = r15.mResolvedDimensionRatioSide
            r2 = 1
            if (r1 == r2) goto L_0x032a
            r2 = -1
            if (r1 != r2) goto L_0x032d
        L_0x032a:
            r45 = 1
            goto L_0x032f
        L_0x032d:
            r45 = 0
        L_0x032f:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r15.mListDimensionBehaviors
            r2 = 0
            r1 = r1[r2]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r1 != r5) goto L_0x033e
            boolean r1 = r15 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r1 == 0) goto L_0x033e
            r1 = 1
            goto L_0x033f
        L_0x033e:
            r1 = 0
        L_0x033f:
            r30 = r1
            if (r30 == 0) goto L_0x0347
            r0 = 0
            r60 = r0
            goto L_0x0349
        L_0x0347:
            r60 = r0
        L_0x0349:
            r0 = 1
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mCenter
            boolean r1 = r1.isConnected()
            if (r1 == 0) goto L_0x0356
            r0 = 0
            r31 = r0
            goto L_0x0358
        L_0x0356:
            r31 = r0
        L_0x0358:
            boolean[] r0 = r15.mIsInBarrier
            r1 = 0
            boolean r61 = r0[r1]
            r1 = 1
            boolean r62 = r0[r1]
            int r0 = r15.mHorizontalResolution
            r3 = 2
            r32 = 0
            if (r0 == r3) goto L_0x04c2
            boolean r0 = r15.resolvedHorizontal
            if (r0 != 0) goto L_0x04c2
            if (r77 == 0) goto L_0x041f
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            if (r0 == 0) goto L_0x041f
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.start
            boolean r2 = r1.resolved
            if (r2 == 0) goto L_0x041f
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            boolean r0 = r0.resolved
            if (r0 != 0) goto L_0x0381
            r2 = 8
            goto L_0x0421
        L_0x0381:
            if (r77 == 0) goto L_0x0403
            int r0 = r1.value
            r14.addEquality(r13, r0)
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r0 = r15.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r0.end
            int r0 = r0.value
            r14.addEquality(r12, r0)
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x03e7
            if (r4 == 0) goto L_0x03cb
            boolean[] r0 = r15.isTerminalWidget
            r1 = 0
            boolean r0 = r0[r1]
            if (r0 == 0) goto L_0x03cb
            boolean r0 = r75.isInHorizontalChain()
            if (r0 != 0) goto L_0x03cb
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mRight
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r2 = 8
            r14.addGreaterThan(r0, r12, r1, r2)
            r66 = r4
            r67 = r5
            r63 = r6
            r68 = r8
            r64 = r9
            r71 = r10
            r72 = r11
            r73 = r12
            r74 = r13
            r65 = r16
            r69 = r27
            r70 = r28
            goto L_0x04da
        L_0x03cb:
            r2 = 8
            r66 = r4
            r67 = r5
            r63 = r6
            r68 = r8
            r64 = r9
            r71 = r10
            r72 = r11
            r73 = r12
            r74 = r13
            r65 = r16
            r69 = r27
            r70 = r28
            goto L_0x04da
        L_0x03e7:
            r2 = 8
            r66 = r4
            r67 = r5
            r63 = r6
            r68 = r8
            r64 = r9
            r71 = r10
            r72 = r11
            r73 = r12
            r74 = r13
            r65 = r16
            r69 = r27
            r70 = r28
            goto L_0x04da
        L_0x0403:
            r2 = 8
            r66 = r4
            r67 = r5
            r63 = r6
            r68 = r8
            r64 = r9
            r71 = r10
            r72 = r11
            r73 = r12
            r74 = r13
            r65 = r16
            r69 = r27
            r70 = r28
            goto L_0x04da
        L_0x041f:
            r2 = 8
        L_0x0421:
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x042d
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mRight
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            r7 = r0
            goto L_0x042f
        L_0x042d:
            r7 = r32
        L_0x042f:
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r15.mParent
            if (r0 == 0) goto L_0x043a
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.mLeft
            androidx.constraintlayout.core.SolverVariable r0 = r14.createObjectVariable(r0)
            goto L_0x043c
        L_0x043a:
            r0 = r32
        L_0x043c:
            r63 = r6
            r6 = r0
            r18 = 1
            boolean[] r0 = r15.isTerminalWidget
            r20 = 0
            boolean r21 = r0[r20]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r15.mListDimensionBehaviors
            r22 = r0[r20]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r15.mLeft
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r15.mRight
            r33 = r2
            int r2 = r15.mX
            r34 = r2
            int r2 = r15.mMinWidth
            int[] r3 = r15.mMaxDimension
            r36 = r3[r20]
            float r3 = r15.mHorizontalBiasPercent
            r19 = 1
            r0 = r0[r19]
            if (r0 != r8) goto L_0x0466
            r37 = r19
            goto L_0x0468
        L_0x0466:
            r37 = r20
        L_0x0468:
            int r0 = r15.mMatchConstraintMinWidth
            r24 = r0
            int r0 = r15.mMatchConstraintMaxWidth
            r25 = r0
            float r0 = r15.mMatchConstraintPercentWidth
            r26 = r0
            r0 = r75
            r38 = r1
            r1 = r76
            r23 = r33
            r33 = r34
            r34 = r2
            r2 = r18
            r64 = r9
            r65 = r16
            r9 = 2
            r16 = r3
            r3 = r4
            r66 = r4
            r4 = r64
            r67 = r5
            r5 = r21
            r68 = r8
            r8 = r22
            r69 = r27
            r9 = r30
            r71 = r10
            r70 = r28
            r10 = r38
            r72 = r11
            r11 = r23
            r73 = r12
            r12 = r33
            r74 = r13
            r13 = r60
            r14 = r34
            r15 = r36
            r18 = r37
            r19 = r56
            r20 = r57
            r21 = r61
            r22 = r58
            r23 = r59
            r27 = r31
            r0.applyConstraints(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27)
            goto L_0x04da
        L_0x04c2:
            r66 = r4
            r67 = r5
            r63 = r6
            r68 = r8
            r64 = r9
            r71 = r10
            r72 = r11
            r73 = r12
            r74 = r13
            r65 = r16
            r69 = r27
            r70 = r28
        L_0x04da:
            r0 = 1
            if (r77 == 0) goto L_0x0538
            r7 = r75
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r7.verticalRun
            if (r1 == 0) goto L_0x053a
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r2 = r1.start
            boolean r3 = r2.resolved
            if (r3 == 0) goto L_0x053a
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r1.end
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x053a
            int r1 = r2.value
            r8 = r76
            r9 = r72
            r8.addEquality(r9, r1)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r7.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r1.end
            int r1 = r1.value
            r10 = r70
            r8.addEquality(r10, r1)
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r7.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r1.baseline
            int r1 = r1.value
            r11 = r69
            r8.addEquality(r11, r1)
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r7.mParent
            if (r1 == 0) goto L_0x0532
            if (r57 != 0) goto L_0x052d
            if (r64 == 0) goto L_0x052d
            boolean[] r2 = r7.isTerminalWidget
            r3 = 1
            boolean r2 = r2[r3]
            if (r2 == 0) goto L_0x052a
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r1.mBottom
            androidx.constraintlayout.core.SolverVariable r1 = r8.createObjectVariable(r1)
            r2 = 8
            r12 = 0
            r8.addGreaterThan(r1, r10, r12, r2)
            goto L_0x0536
        L_0x052a:
            r2 = 8
            goto L_0x0530
        L_0x052d:
            r2 = 8
            r3 = 1
        L_0x0530:
            r12 = 0
            goto L_0x0536
        L_0x0532:
            r2 = 8
            r3 = 1
            r12 = 0
        L_0x0536:
            r0 = 0
            goto L_0x0546
        L_0x0538:
            r7 = r75
        L_0x053a:
            r8 = r76
            r11 = r69
            r10 = r70
            r9 = r72
            r2 = 8
            r3 = 1
            r12 = 0
        L_0x0546:
            int r1 = r7.mVerticalResolution
            r4 = 2
            if (r1 != r4) goto L_0x054e
            r0 = 0
            r13 = r0
            goto L_0x054f
        L_0x054e:
            r13 = r0
        L_0x054f:
            if (r13 == 0) goto L_0x0636
            boolean r0 = r7.resolvedVertical
            if (r0 != 0) goto L_0x0636
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r7.mListDimensionBehaviors
            r0 = r0[r3]
            r1 = r67
            if (r0 != r1) goto L_0x0563
            boolean r0 = r7 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x0563
            r0 = r3
            goto L_0x0564
        L_0x0563:
            r0 = r12
        L_0x0564:
            if (r0 == 0) goto L_0x056b
            r29 = 0
            r1 = r29
            goto L_0x056d
        L_0x056b:
            r1 = r29
        L_0x056d:
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r7.mParent
            if (r4 == 0) goto L_0x0578
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.mBottom
            androidx.constraintlayout.core.SolverVariable r4 = r8.createObjectVariable(r4)
            goto L_0x057a
        L_0x0578:
            r4 = r32
        L_0x057a:
            androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r7.mParent
            if (r5 == 0) goto L_0x0587
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTop
            androidx.constraintlayout.core.SolverVariable r5 = r8.createObjectVariable(r5)
            r34 = r5
            goto L_0x0589
        L_0x0587:
            r34 = r32
        L_0x0589:
            int r5 = r7.mBaselineDistance
            if (r5 > 0) goto L_0x0591
            int r5 = r7.mVisibility
            if (r5 != r2) goto L_0x05d3
        L_0x0591:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r7.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r5.mTarget
            if (r6 == 0) goto L_0x05c0
            int r5 = r75.getBaselineDistance()
            r8.addEquality(r11, r9, r5, r2)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r7.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r5.mTarget
            androidx.constraintlayout.core.SolverVariable r5 = r8.createObjectVariable(r5)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r7.mBaseline
            int r6 = r6.getMargin()
            r8.addEquality(r11, r5, r6, r2)
            r31 = 0
            if (r64 == 0) goto L_0x05bd
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r7.mBottom
            androidx.constraintlayout.core.SolverVariable r2 = r8.createObjectVariable(r2)
            r14 = 5
            r8.addGreaterThan(r4, r2, r12, r14)
        L_0x05bd:
            r2 = r31
            goto L_0x05d5
        L_0x05c0:
            int r6 = r7.mVisibility
            if (r6 != r2) goto L_0x05cc
            int r5 = r5.getMargin()
            r8.addEquality(r11, r9, r5, r2)
            goto L_0x05d3
        L_0x05cc:
            int r5 = r75.getBaselineDistance()
            r8.addEquality(r11, r9, r5, r2)
        L_0x05d3:
            r2 = r31
        L_0x05d5:
            r30 = 0
            boolean[] r5 = r7.isTerminalWidget
            boolean r33 = r5[r3]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r7.mListDimensionBehaviors
            r36 = r5[r3]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r7.mTop
            androidx.constraintlayout.core.widgets.ConstraintAnchor r14 = r7.mBottom
            int r15 = r7.mY
            int r12 = r7.mMinHeight
            r69 = r11
            int[] r11 = r7.mMaxDimension
            r43 = r11[r3]
            float r11 = r7.mVerticalBiasPercent
            r16 = 0
            r5 = r5[r16]
            r3 = r68
            if (r5 != r3) goto L_0x05fa
            r46 = 1
            goto L_0x05fc
        L_0x05fa:
            r46 = 0
        L_0x05fc:
            int r3 = r7.mMatchConstraintMinHeight
            r52 = r3
            int r3 = r7.mMatchConstraintMaxHeight
            r53 = r3
            float r3 = r7.mMatchConstraintPercentHeight
            r54 = r3
            r28 = r75
            r29 = r76
            r31 = r64
            r32 = r66
            r35 = r4
            r37 = r0
            r38 = r6
            r39 = r14
            r40 = r15
            r41 = r1
            r42 = r12
            r44 = r11
            r47 = r57
            r48 = r56
            r49 = r62
            r50 = r59
            r51 = r58
            r55 = r2
            r28.applyConstraints(r29, r30, r31, r32, r33, r34, r35, r36, r37, r38, r39, r40, r41, r42, r43, r44, r45, r46, r47, r48, r49, r50, r51, r52, r53, r54, r55)
            r30 = r0
            r29 = r1
            r31 = r2
            goto L_0x0638
        L_0x0636:
            r69 = r11
        L_0x0638:
            if (r71 == 0) goto L_0x065e
            r11 = 8
            int r0 = r7.mResolvedDimensionRatioSide
            r1 = 1
            if (r0 != r1) goto L_0x0650
            float r5 = r7.mResolvedDimensionRatio
            r0 = r76
            r1 = r10
            r2 = r9
            r3 = r73
            r4 = r74
            r6 = r11
            r0.addRatio(r1, r2, r3, r4, r5, r6)
            goto L_0x065e
        L_0x0650:
            float r5 = r7.mResolvedDimensionRatio
            r0 = r76
            r1 = r73
            r2 = r74
            r3 = r10
            r4 = r9
            r6 = r11
            r0.addRatio(r1, r2, r3, r4, r5, r6)
        L_0x065e:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r7.mCenter
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x0684
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r7.mCenter
            androidx.constraintlayout.core.widgets.ConstraintAnchor r0 = r0.getTarget()
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = r0.getOwner()
            float r1 = r7.mCircleConstraintAngle
            r2 = 1119092736(0x42b40000, float:90.0)
            float r1 = r1 + r2
            double r1 = (double) r1
            double r1 = java.lang.Math.toRadians(r1)
            float r1 = (float) r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r7.mCenter
            int r2 = r2.getMargin()
            r8.addCenterPoint(r7, r0, r1, r2)
        L_0x0684:
            r0 = 0
            r7.resolvedHorizontal = r0
            r7.resolvedVertical = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.ConstraintWidget.addToSolver(androidx.constraintlayout.core.LinearSystem, boolean):void");
    }

    /* access modifiers changed from: package-private */
    public boolean addFirst() {
        return (this instanceof VirtualLayout) || (this instanceof Guideline);
    }

    public void setupDimensionRatio(boolean hParentWrapContent, boolean vParentWrapContent, boolean horizontalDimensionFixed, boolean verticalDimensionFixed) {
        if (this.mResolvedDimensionRatioSide == -1) {
            if (horizontalDimensionFixed && !verticalDimensionFixed) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (!horizontalDimensionFixed && verticalDimensionFixed) {
                this.mResolvedDimensionRatioSide = 1;
                if (this.mDimensionRatioSide == -1) {
                    this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                }
            }
        }
        if (this.mResolvedDimensionRatioSide == 0 && (!this.mTop.isConnected() || !this.mBottom.isConnected())) {
            this.mResolvedDimensionRatioSide = 1;
        } else if (this.mResolvedDimensionRatioSide == 1 && (!this.mLeft.isConnected() || !this.mRight.isConnected())) {
            this.mResolvedDimensionRatioSide = 0;
        }
        if (this.mResolvedDimensionRatioSide == -1 && (!this.mTop.isConnected() || !this.mBottom.isConnected() || !this.mLeft.isConnected() || !this.mRight.isConnected())) {
            if (this.mTop.isConnected() && this.mBottom.isConnected()) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (this.mLeft.isConnected() && this.mRight.isConnected()) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
        if (this.mResolvedDimensionRatioSide == -1) {
            int i = this.mMatchConstraintMinWidth;
            if (i > 0 && this.mMatchConstraintMinHeight == 0) {
                this.mResolvedDimensionRatioSide = 0;
            } else if (i == 0 && this.mMatchConstraintMinHeight > 0) {
                this.mResolvedDimensionRatio = 1.0f / this.mResolvedDimensionRatio;
                this.mResolvedDimensionRatioSide = 1;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:267:0x0609, code lost:
        if ((r3 instanceof androidx.constraintlayout.core.widgets.Barrier) != false) goto L_0x060e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:111:0x020a  */
    /* JADX WARNING: Removed duplicated region for block: B:244:0x0593  */
    /* JADX WARNING: Removed duplicated region for block: B:254:0x05da  */
    /* JADX WARNING: Removed duplicated region for block: B:259:0x05f8 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:260:0x05f9  */
    /* JADX WARNING: Removed duplicated region for block: B:335:0x06d8 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void applyConstraints(androidx.constraintlayout.core.LinearSystem r38, boolean r39, boolean r40, boolean r41, boolean r42, androidx.constraintlayout.core.SolverVariable r43, androidx.constraintlayout.core.SolverVariable r44, androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour r45, boolean r46, androidx.constraintlayout.core.widgets.ConstraintAnchor r47, androidx.constraintlayout.core.widgets.ConstraintAnchor r48, int r49, int r50, int r51, int r52, float r53, boolean r54, boolean r55, boolean r56, boolean r57, boolean r58, int r59, int r60, int r61, int r62, float r63, boolean r64) {
        /*
            r37 = this;
            r0 = r37
            r10 = r38
            r11 = r43
            r12 = r44
            r13 = r47
            r14 = r48
            r15 = r51
            r9 = r52
            r8 = r60
            r1 = r61
            r2 = r62
            androidx.constraintlayout.core.SolverVariable r7 = r10.createObjectVariable(r13)
            androidx.constraintlayout.core.SolverVariable r6 = r10.createObjectVariable(r14)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r47.getTarget()
            androidx.constraintlayout.core.SolverVariable r5 = r10.createObjectVariable(r3)
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r48.getTarget()
            androidx.constraintlayout.core.SolverVariable r4 = r10.createObjectVariable(r3)
            androidx.constraintlayout.core.Metrics r3 = androidx.constraintlayout.core.LinearSystem.getMetrics()
            if (r3 == 0) goto L_0x0040
            androidx.constraintlayout.core.Metrics r3 = androidx.constraintlayout.core.LinearSystem.getMetrics()
            long r11 = r3.nonresolvedWidgets
            r16 = 1
            long r11 = r11 + r16
            r3.nonresolvedWidgets = r11
        L_0x0040:
            boolean r11 = r47.isConnected()
            boolean r12 = r48.isConnected()
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r0.mCenter
            boolean r16 = r3.isConnected()
            r3 = 0
            r17 = 0
            if (r11 == 0) goto L_0x0055
            int r17 = r17 + 1
        L_0x0055:
            if (r12 == 0) goto L_0x0059
            int r17 = r17 + 1
        L_0x0059:
            if (r16 == 0) goto L_0x0060
            int r17 = r17 + 1
            r8 = r17
            goto L_0x0062
        L_0x0060:
            r8 = r17
        L_0x0062:
            if (r54 == 0) goto L_0x0069
            r17 = 3
            r14 = r17
            goto L_0x006b
        L_0x0069:
            r14 = r59
        L_0x006b:
            int[] r17 = androidx.constraintlayout.core.widgets.ConstraintWidget.AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour
            int r18 = r45.ordinal()
            r17 = r17[r18]
            switch(r17) {
                case 1: goto L_0x0083;
                case 2: goto L_0x0081;
                case 3: goto L_0x007f;
                case 4: goto L_0x0077;
                default: goto L_0x0076;
            }
        L_0x0076:
            goto L_0x0085
        L_0x0077:
            r13 = 4
            if (r14 == r13) goto L_0x007c
            r13 = 1
            goto L_0x007d
        L_0x007c:
            r13 = 0
        L_0x007d:
            r3 = r13
            goto L_0x0085
        L_0x007f:
            r3 = 0
            goto L_0x0085
        L_0x0081:
            r3 = 0
            goto L_0x0085
        L_0x0083:
            r3 = 0
        L_0x0085:
            int r13 = r0.mWidthOverride
            r18 = r4
            r4 = -1
            if (r13 == r4) goto L_0x0094
            if (r39 == 0) goto L_0x0094
            r3 = 0
            int r13 = r0.mWidthOverride
            r0.mWidthOverride = r4
            goto L_0x0096
        L_0x0094:
            r13 = r50
        L_0x0096:
            r50 = r3
            int r3 = r0.mHeightOverride
            if (r3 == r4) goto L_0x00a4
            if (r39 != 0) goto L_0x00a4
            r3 = 0
            int r13 = r0.mHeightOverride
            r0.mHeightOverride = r4
            goto L_0x00a6
        L_0x00a4:
            r3 = r50
        L_0x00a6:
            int r4 = r0.mVisibility
            r50 = r13
            r13 = 8
            if (r4 != r13) goto L_0x00b3
            r4 = 0
            r3 = 0
            r20 = r3
            goto L_0x00b7
        L_0x00b3:
            r4 = r50
            r20 = r3
        L_0x00b7:
            if (r64 == 0) goto L_0x00d2
            if (r11 != 0) goto L_0x00c5
            if (r12 != 0) goto L_0x00c5
            if (r16 != 0) goto L_0x00c5
            r3 = r49
            r10.addEquality(r7, r3)
            goto L_0x00d2
        L_0x00c5:
            r3 = r49
            if (r11 == 0) goto L_0x00d2
            if (r12 != 0) goto L_0x00d2
            int r3 = r47.getMargin()
            r10.addEquality(r7, r5, r3, r13)
        L_0x00d2:
            r3 = 3
            if (r20 != 0) goto L_0x0104
            if (r46 == 0) goto L_0x00ee
            r13 = 0
            r10.addEquality(r6, r7, r13, r3)
            if (r15 <= 0) goto L_0x00e3
            r3 = 8
            r10.addGreaterThan(r6, r7, r15, r3)
            goto L_0x00e5
        L_0x00e3:
            r3 = 8
        L_0x00e5:
            r13 = 2147483647(0x7fffffff, float:NaN)
            if (r9 >= r13) goto L_0x00f3
            r10.addLowerThan(r6, r7, r9, r3)
            goto L_0x00f3
        L_0x00ee:
            r3 = 8
            r10.addEquality(r6, r7, r4, r3)
        L_0x00f3:
            r13 = r42
            r26 = r2
            r19 = r4
            r30 = r5
            r27 = r8
            r9 = r18
            r18 = r1
            r8 = r6
            goto L_0x0225
        L_0x0104:
            r3 = 2
            if (r8 == r3) goto L_0x0130
            if (r54 != 0) goto L_0x0130
            r3 = 1
            if (r14 == r3) goto L_0x010e
            if (r14 != 0) goto L_0x0130
        L_0x010e:
            r20 = 0
            int r3 = java.lang.Math.max(r1, r4)
            if (r2 <= 0) goto L_0x011a
            int r3 = java.lang.Math.min(r2, r3)
        L_0x011a:
            r13 = 8
            r10.addEquality(r6, r7, r3, r13)
            r13 = r42
            r26 = r2
            r19 = r4
            r30 = r5
            r27 = r8
            r9 = r18
            r18 = r1
            r8 = r6
            goto L_0x0225
        L_0x0130:
            r3 = -2
            if (r1 != r3) goto L_0x0136
            r1 = r4
            r13 = r1
            goto L_0x0137
        L_0x0136:
            r13 = r1
        L_0x0137:
            if (r2 != r3) goto L_0x013c
            r1 = r4
            r3 = r1
            goto L_0x013d
        L_0x013c:
            r3 = r2
        L_0x013d:
            if (r4 <= 0) goto L_0x0143
            r1 = 1
            if (r14 == r1) goto L_0x0143
            r4 = 0
        L_0x0143:
            if (r13 <= 0) goto L_0x014e
            r1 = 8
            r10.addGreaterThan(r6, r7, r13, r1)
            int r4 = java.lang.Math.max(r4, r13)
        L_0x014e:
            if (r3 <= 0) goto L_0x0163
            r1 = 1
            if (r40 == 0) goto L_0x0157
            r2 = 1
            if (r14 != r2) goto L_0x0157
            r1 = 0
        L_0x0157:
            if (r1 == 0) goto L_0x015e
            r2 = 8
            r10.addLowerThan(r6, r7, r3, r2)
        L_0x015e:
            int r2 = java.lang.Math.min(r4, r3)
            r4 = r2
        L_0x0163:
            r1 = 1
            if (r14 != r1) goto L_0x0193
            if (r40 == 0) goto L_0x016f
            r1 = 8
            r10.addEquality(r6, r7, r4, r1)
            r2 = 5
            goto L_0x0182
        L_0x016f:
            r1 = 8
            if (r56 == 0) goto L_0x017b
            r2 = 5
            r10.addEquality(r6, r7, r4, r2)
            r10.addLowerThan(r6, r7, r4, r1)
            goto L_0x0182
        L_0x017b:
            r2 = 5
            r10.addEquality(r6, r7, r4, r2)
            r10.addLowerThan(r6, r7, r4, r1)
        L_0x0182:
            r26 = r3
            r19 = r4
            r30 = r5
            r27 = r8
            r9 = r18
            r8 = r6
            r18 = r13
            r13 = r42
            goto L_0x0225
        L_0x0193:
            r2 = 5
            r1 = 2
            if (r14 != r1) goto L_0x0214
            r1 = 0
            r24 = 0
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = r47.getType()
            r61 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r1 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.TOP
            if (r2 == r1) goto L_0x01cc
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = r47.getType()
            r62 = r3
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.BOTTOM
            if (r2 != r3) goto L_0x01af
            goto L_0x01ce
        L_0x01af:
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r2 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r1.getAnchor(r2)
            androidx.constraintlayout.core.SolverVariable r1 = r10.createObjectVariable(r1)
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.getAnchor(r3)
            androidx.constraintlayout.core.SolverVariable r2 = r10.createObjectVariable(r2)
            r24 = r1
            r25 = r2
            goto L_0x01e8
        L_0x01cc:
            r62 = r3
        L_0x01ce:
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r2.getAnchor(r1)
            androidx.constraintlayout.core.SolverVariable r1 = r10.createObjectVariable(r1)
            androidx.constraintlayout.core.widgets.ConstraintWidget r2 = r0.mParent
            androidx.constraintlayout.core.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.core.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.getAnchor(r3)
            androidx.constraintlayout.core.SolverVariable r2 = r10.createObjectVariable(r2)
            r24 = r1
            r25 = r2
        L_0x01e8:
            androidx.constraintlayout.core.ArrayRow r1 = r38.createRow()
            r3 = 5
            r2 = r6
            r26 = r62
            r27 = r8
            r8 = r3
            r3 = r7
            r9 = r18
            r18 = r4
            r4 = r25
            r30 = r5
            r5 = r24
            r8 = r6
            r6 = r63
            androidx.constraintlayout.core.ArrayRow r1 = r1.createRowDimensionRatio(r2, r3, r4, r5, r6)
            r10.addConstraint(r1)
            if (r40 == 0) goto L_0x020d
            r1 = 0
            r20 = r1
        L_0x020d:
            r19 = r18
            r18 = r13
            r13 = r42
            goto L_0x0225
        L_0x0214:
            r26 = r3
            r30 = r5
            r27 = r8
            r9 = r18
            r18 = r4
            r8 = r6
            r1 = 1
            r19 = r18
            r18 = r13
            r13 = r1
        L_0x0225:
            if (r64 == 0) goto L_0x070a
            if (r56 == 0) goto L_0x0241
            r4 = r44
            r3 = r48
            r1 = r0
            r6 = r7
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r7 = r30
            r2 = 0
            r11 = r43
            r12 = r9
            goto L_0x0720
        L_0x0241:
            r6 = 5
            if (r11 != 0) goto L_0x025c
            if (r12 != 0) goto L_0x025c
            if (r16 != 0) goto L_0x025c
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r43
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d4
        L_0x025c:
            if (r11 == 0) goto L_0x0284
            if (r12 != 0) goto L_0x0284
            r5 = r47
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r5.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r1.mOwner
            if (r40 == 0) goto L_0x026e
            boolean r2 = r1 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r2 == 0) goto L_0x026e
            r6 = 8
        L_0x026e:
            r23 = r40
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r43
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d6
        L_0x0284:
            r5 = r47
            r4 = 0
            if (r11 != 0) goto L_0x02f0
            if (r12 == 0) goto L_0x02f0
            int r1 = r48.getMargin()
            int r1 = -r1
            r2 = 8
            r10.addEquality(r8, r9, r1, r2)
            if (r40 == 0) goto L_0x02db
            boolean r1 = r0.OPTIMIZE_WRAP
            if (r1 == 0) goto L_0x02c2
            boolean r1 = r7.isFinalValue
            if (r1 == 0) goto L_0x02c2
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.mParent
            if (r1 == 0) goto L_0x02c2
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r1 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r1
            if (r39 == 0) goto L_0x02ab
            r1.addHorizontalWrapMinVariable(r5)
            goto L_0x02ae
        L_0x02ab:
            r1.addVerticalWrapMinVariable(r5)
        L_0x02ae:
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r43
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d4
        L_0x02c2:
            r3 = r43
            r1 = 5
            r10.addGreaterThan(r7, r3, r4, r1)
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r3
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d4
        L_0x02db:
            r3 = r43
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r3
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
            goto L_0x06d4
        L_0x02f0:
            r3 = r43
            if (r11 == 0) goto L_0x06c3
            if (r12 == 0) goto L_0x06c3
            r23 = 1
            r1 = 0
            r24 = 0
            r2 = 0
            r25 = 5
            r31 = 4
            r32 = 6
            if (r40 == 0) goto L_0x0306
            r25 = 5
        L_0x0306:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r5.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r4.mOwner
            r42 = r1
            r33 = r11
            r11 = r14
            r14 = r48
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r14.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r1.mOwner
            r34 = r12
            androidx.constraintlayout.core.widgets.ConstraintWidget r12 = r37.getParent()
            if (r20 == 0) goto L_0x0514
            if (r11 != 0) goto L_0x0399
            if (r26 != 0) goto L_0x0350
            if (r18 != 0) goto L_0x0350
            r24 = 1
            r17 = 8
            r22 = 8
            r50 = r2
            r15 = r30
            boolean r2 = r15.isFinalValue
            if (r2 == 0) goto L_0x0347
            boolean r2 = r9.isFinalValue
            if (r2 == 0) goto L_0x0347
            int r2 = r47.getMargin()
            r3 = 8
            r10.addEquality(r7, r15, r2, r3)
            int r2 = r48.getMargin()
            int r2 = -r2
            r10.addEquality(r8, r9, r2, r3)
            return
        L_0x0347:
            r2 = r42
            r25 = r17
            r31 = r22
            r22 = r50
            goto L_0x0360
        L_0x0350:
            r50 = r2
            r15 = r30
            r2 = 1
            r3 = 5
            r17 = 5
            r23 = 1
            r22 = 1
            r25 = r3
            r31 = r17
        L_0x0360:
            boolean r3 = r4 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r3 != 0) goto L_0x0380
            boolean r3 = r1 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r3 == 0) goto L_0x0369
            goto L_0x0380
        L_0x0369:
            r36 = r1
            r17 = r2
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r2 = r22
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x0380:
            r31 = 4
            r36 = r1
            r17 = r2
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r2 = r22
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x0399:
            r50 = r2
            r15 = r30
            r2 = 2
            if (r11 != r2) goto L_0x03e1
            r2 = 1
            r25 = 5
            r31 = 5
            r23 = 1
            r3 = 1
            r42 = r2
            boolean r2 = r4 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r2 != 0) goto L_0x03c9
            boolean r2 = r1 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r2 == 0) goto L_0x03b3
            goto L_0x03c9
        L_0x03b3:
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x03c9:
            r31 = 4
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x03e1:
            r2 = 1
            if (r11 != r2) goto L_0x03fe
            r2 = 1
            r3 = 1
            r25 = 8
            r36 = r1
            r17 = r2
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x03fe:
            r3 = 3
            if (r11 != r3) goto L_0x04fb
            int r2 = r0.mResolvedDimensionRatioSide
            r3 = -1
            if (r2 != r3) goto L_0x045c
            r2 = 1
            r3 = 1
            r24 = 1
            r25 = 8
            r31 = 5
            if (r57 == 0) goto L_0x0444
            r31 = 5
            r32 = 4
            if (r40 == 0) goto L_0x042e
            r32 = 5
            r36 = r1
            r17 = r2
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x042e:
            r36 = r1
            r17 = r2
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x0444:
            r32 = 8
            r36 = r1
            r17 = r2
            r2 = r3
            r29 = r4
            r42 = r7
            r0 = r8
            r61 = r11
            r50 = r12
            r35 = r27
            r11 = r6
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x045c:
            r2 = 1
            r3 = 1
            r24 = 1
            if (r54 == 0) goto L_0x0490
            r42 = r2
            r61 = r11
            r35 = r27
            r2 = 2
            r11 = r60
            if (r11 == r2) goto L_0x0474
            r2 = 1
            if (r11 != r2) goto L_0x0471
            goto L_0x0474
        L_0x0471:
            r17 = 0
            goto L_0x0476
        L_0x0474:
            r17 = 1
        L_0x0476:
            r2 = r17
            if (r2 != 0) goto L_0x047e
            r25 = 8
            r31 = 5
        L_0x047e:
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x0490:
            r42 = r2
            r61 = r11
            r35 = r27
            r11 = r60
            r25 = 5
            if (r26 <= 0) goto L_0x04b0
            r31 = 5
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x04b0:
            if (r26 != 0) goto L_0x04e9
            if (r18 != 0) goto L_0x04e9
            if (r57 != 0) goto L_0x04ca
            r31 = 8
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x04ca:
            if (r4 == r12) goto L_0x04d2
            if (r1 == r12) goto L_0x04d2
            r2 = 4
            r25 = r2
            goto L_0x04d5
        L_0x04d2:
            r2 = 5
            r25 = r2
        L_0x04d5:
            r31 = 4
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x04e9:
            r17 = r42
            r36 = r1
            r2 = r3
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x04fb:
            r61 = r11
            r35 = r27
            r11 = r60
            r17 = r42
            r2 = r50
            r36 = r1
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            goto L_0x057b
        L_0x0514:
            r50 = r2
            r61 = r11
            r35 = r27
            r15 = r30
            r11 = r60
            r17 = 1
            r22 = 1
            boolean r2 = r15.isFinalValue
            if (r2 == 0) goto L_0x056c
            boolean r2 = r9.isFinalValue
            if (r2 == 0) goto L_0x056c
            int r21 = r47.getMargin()
            int r27 = r48.getMargin()
            r28 = 8
            r3 = r1
            r1 = r38
            r2 = r7
            r11 = r43
            r36 = r3
            r3 = r15
            r0 = r4
            r4 = r21
            r5 = r53
            r11 = r6
            r6 = r9
            r42 = r7
            r7 = r8
            r29 = r0
            r0 = r8
            r8 = r27
            r50 = r12
            r12 = r9
            r9 = r28
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            if (r40 == 0) goto L_0x0569
            if (r13 == 0) goto L_0x0569
            r1 = 0
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r14.mTarget
            if (r2 == 0) goto L_0x0561
            int r1 = r48.getMargin()
        L_0x0561:
            r9 = r44
            if (r12 == r9) goto L_0x056b
            r10.addGreaterThan(r9, r0, r1, r11)
            goto L_0x056b
        L_0x0569:
            r9 = r44
        L_0x056b:
            return
        L_0x056c:
            r36 = r1
            r29 = r4
            r11 = r6
            r42 = r7
            r0 = r8
            r50 = r12
            r12 = r9
            r9 = r44
            r2 = r22
        L_0x057b:
            if (r2 == 0) goto L_0x058b
            if (r15 != r12) goto L_0x058b
            r7 = r50
            r8 = r29
            if (r8 == r7) goto L_0x058f
            r2 = 0
            r23 = 0
            r22 = r2
            goto L_0x0591
        L_0x058b:
            r7 = r50
            r8 = r29
        L_0x058f:
            r22 = r2
        L_0x0591:
            if (r17 == 0) goto L_0x05da
            if (r20 != 0) goto L_0x05af
            if (r55 != 0) goto L_0x05af
            if (r57 != 0) goto L_0x05af
            r6 = r11
            r11 = r43
            if (r15 != r11) goto L_0x05b2
            if (r12 != r9) goto L_0x05b2
            r1 = 8
            r2 = 8
            r3 = 0
            r4 = 0
            r32 = r1
            r27 = r2
            r25 = r3
            r23 = r4
            goto L_0x05b8
        L_0x05af:
            r6 = r11
            r11 = r43
        L_0x05b2:
            r27 = r25
            r25 = r23
            r23 = r40
        L_0x05b8:
            int r4 = r47.getMargin()
            int r29 = r48.getMargin()
            r1 = r38
            r2 = r42
            r5 = 3
            r3 = r15
            r14 = 0
            r14 = r5
            r5 = r53
            r14 = r6
            r6 = r12
            r50 = r13
            r13 = r7
            r7 = r0
            r30 = r8
            r8 = r29
            r9 = r32
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x05e8
        L_0x05da:
            r30 = r8
            r14 = r11
            r50 = r13
            r11 = r43
            r13 = r7
            r27 = r25
            r25 = r23
            r23 = r40
        L_0x05e8:
            r1 = r37
            r2 = r30
            int r3 = r1.mVisibility
            r4 = 8
            if (r3 != r4) goto L_0x05f9
            boolean r3 = r48.hasDependents()
            if (r3 != 0) goto L_0x05f9
            return
        L_0x05f9:
            if (r22 == 0) goto L_0x0628
            if (r23 == 0) goto L_0x0610
            if (r15 == r12) goto L_0x0610
            if (r20 != 0) goto L_0x0610
            boolean r3 = r2 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r3 != 0) goto L_0x060c
            r3 = r36
            boolean r4 = r3 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 == 0) goto L_0x0612
            goto L_0x060e
        L_0x060c:
            r3 = r36
        L_0x060e:
            r4 = 6
            goto L_0x0614
        L_0x0610:
            r3 = r36
        L_0x0612:
            r4 = r27
        L_0x0614:
            int r5 = r47.getMargin()
            r6 = r42
            r10.addGreaterThan(r6, r15, r5, r4)
            int r5 = r48.getMargin()
            int r5 = -r5
            r10.addLowerThan(r0, r12, r5, r4)
            r27 = r4
            goto L_0x062c
        L_0x0628:
            r6 = r42
            r3 = r36
        L_0x062c:
            if (r23 == 0) goto L_0x0645
            if (r58 == 0) goto L_0x0645
            boolean r4 = r2 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 != 0) goto L_0x0645
            boolean r4 = r3 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r4 != 0) goto L_0x0645
            if (r3 == r13) goto L_0x0645
            r31 = 6
            r27 = 6
            r25 = 1
            r4 = r27
            r5 = r31
            goto L_0x0649
        L_0x0645:
            r4 = r27
            r5 = r31
        L_0x0649:
            if (r25 == 0) goto L_0x068e
            if (r24 == 0) goto L_0x0670
            if (r57 == 0) goto L_0x0651
            if (r41 == 0) goto L_0x0670
        L_0x0651:
            r7 = r5
            if (r2 == r13) goto L_0x0656
            if (r3 != r13) goto L_0x0657
        L_0x0656:
            r7 = 6
        L_0x0657:
            boolean r8 = r2 instanceof androidx.constraintlayout.core.widgets.Guideline
            if (r8 != 0) goto L_0x065f
            boolean r8 = r3 instanceof androidx.constraintlayout.core.widgets.Guideline
            if (r8 == 0) goto L_0x0660
        L_0x065f:
            r7 = 5
        L_0x0660:
            boolean r8 = r2 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r8 != 0) goto L_0x0668
            boolean r8 = r3 instanceof androidx.constraintlayout.core.widgets.Barrier
            if (r8 == 0) goto L_0x0669
        L_0x0668:
            r7 = 5
        L_0x0669:
            if (r57 == 0) goto L_0x066c
            r7 = 5
        L_0x066c:
            int r5 = java.lang.Math.max(r7, r5)
        L_0x0670:
            if (r23 == 0) goto L_0x067f
            int r5 = java.lang.Math.min(r4, r5)
            if (r54 == 0) goto L_0x067f
            if (r57 != 0) goto L_0x067f
            if (r2 == r13) goto L_0x067e
            if (r3 != r13) goto L_0x067f
        L_0x067e:
            r5 = 4
        L_0x067f:
            int r7 = r47.getMargin()
            r10.addEquality(r6, r15, r7, r5)
            int r7 = r48.getMargin()
            int r7 = -r7
            r10.addEquality(r0, r12, r7, r5)
        L_0x068e:
            if (r23 == 0) goto L_0x069c
            r7 = 0
            if (r11 != r15) goto L_0x0697
            int r7 = r47.getMargin()
        L_0x0697:
            if (r15 == r11) goto L_0x069c
            r10.addGreaterThan(r6, r11, r7, r14)
        L_0x069c:
            if (r23 == 0) goto L_0x06bf
            if (r20 == 0) goto L_0x06bf
            r7 = r15
            if (r51 != 0) goto L_0x06bc
            if (r18 != 0) goto L_0x06bc
            if (r20 == 0) goto L_0x06b5
            r8 = r61
            r9 = 3
            if (r8 != r9) goto L_0x06b3
            r9 = 0
            r15 = 8
            r10.addGreaterThan(r0, r6, r9, r15)
            goto L_0x06d6
        L_0x06b3:
            r9 = 0
            goto L_0x06b8
        L_0x06b5:
            r8 = r61
            r9 = 0
        L_0x06b8:
            r10.addGreaterThan(r0, r6, r9, r14)
            goto L_0x06d6
        L_0x06bc:
            r8 = r61
            goto L_0x06d6
        L_0x06bf:
            r8 = r61
            r7 = r15
            goto L_0x06d6
        L_0x06c3:
            r1 = r0
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r11 = r3
            r14 = r6
            r6 = r7
            r12 = r9
            r7 = r30
        L_0x06d4:
            r23 = r40
        L_0x06d6:
            if (r23 == 0) goto L_0x0705
            if (r50 == 0) goto L_0x0705
            r2 = 0
            r3 = r48
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r3.mTarget
            if (r4 == 0) goto L_0x06e5
            int r2 = r48.getMargin()
        L_0x06e5:
            r4 = r44
            if (r12 == r4) goto L_0x0709
            boolean r5 = r1.OPTIMIZE_WRAP
            if (r5 == 0) goto L_0x0701
            boolean r5 = r0.isFinalValue
            if (r5 == 0) goto L_0x0701
            androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r1.mParent
            if (r5 == 0) goto L_0x0701
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r5 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r5
            if (r39 == 0) goto L_0x06fd
            r5.addHorizontalWrapMaxVariable(r3)
            goto L_0x0700
        L_0x06fd:
            r5.addVerticalWrapMaxVariable(r3)
        L_0x0700:
            return
        L_0x0701:
            r10.addGreaterThan(r4, r0, r2, r14)
            goto L_0x0709
        L_0x0705:
            r4 = r44
            r3 = r48
        L_0x0709:
            return
        L_0x070a:
            r4 = r44
            r3 = r48
            r1 = r0
            r6 = r7
            r0 = r8
            r33 = r11
            r34 = r12
            r50 = r13
            r8 = r14
            r35 = r27
            r7 = r30
            r2 = 0
            r11 = r43
            r12 = r9
        L_0x0720:
            r5 = r35
            r9 = 2
            if (r5 >= r9) goto L_0x0764
            if (r40 == 0) goto L_0x0764
            if (r50 == 0) goto L_0x0764
            r9 = 8
            r10.addGreaterThan(r6, r11, r2, r9)
            if (r39 != 0) goto L_0x0739
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r1.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r9.mTarget
            if (r9 != 0) goto L_0x0737
            goto L_0x0739
        L_0x0737:
            r13 = r2
            goto L_0x073a
        L_0x0739:
            r13 = 1
        L_0x073a:
            r9 = r13
            if (r39 != 0) goto L_0x075c
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r1.mBaseline
            androidx.constraintlayout.core.widgets.ConstraintAnchor r13 = r13.mTarget
            if (r13 == 0) goto L_0x075c
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r13.mOwner
            float r14 = r13.mDimensionRatio
            r15 = 0
            int r14 = (r14 > r15 ? 1 : (r14 == r15 ? 0 : -1))
            if (r14 == 0) goto L_0x075b
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r14 = r13.mListDimensionBehaviors
            r15 = r14[r2]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r15 != r2) goto L_0x075b
            r15 = 1
            r14 = r14[r15]
            if (r14 != r2) goto L_0x075b
            r9 = 1
            goto L_0x075c
        L_0x075b:
            r9 = 0
        L_0x075c:
            if (r9 == 0) goto L_0x0764
            r2 = 0
            r13 = 8
            r10.addGreaterThan(r4, r0, r2, r13)
        L_0x0764:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.ConstraintWidget.applyConstraints(androidx.constraintlayout.core.LinearSystem, boolean, boolean, boolean, boolean, androidx.constraintlayout.core.SolverVariable, androidx.constraintlayout.core.SolverVariable, androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour, boolean, androidx.constraintlayout.core.widgets.ConstraintAnchor, androidx.constraintlayout.core.widgets.ConstraintAnchor, int, int, int, int, float, boolean, boolean, boolean, boolean, boolean, int, int, int, int, float, boolean):void");
    }

    /* renamed from: androidx.constraintlayout.core.widgets.ConstraintWidget$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type;
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour;

        static {
            int[] iArr = new int[DimensionBehaviour.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour = iArr;
            try {
                iArr[DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour[DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr2 = new int[ConstraintAnchor.Type.values().length];
            $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type = iArr2;
            try {
                iArr2[ConstraintAnchor.Type.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.TOP.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER.ordinal()] = 6;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_X.ordinal()] = 7;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.CENTER_Y.ordinal()] = 8;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintAnchor$Type[ConstraintAnchor.Type.NONE.ordinal()] = 9;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    public void updateFromSolver(LinearSystem system, boolean optimize) {
        VerticalWidgetRun verticalWidgetRun;
        HorizontalWidgetRun horizontalWidgetRun;
        int left = system.getObjectVariableValue(this.mLeft);
        int top = system.getObjectVariableValue(this.mTop);
        int right = system.getObjectVariableValue(this.mRight);
        int bottom = system.getObjectVariableValue(this.mBottom);
        if (optimize && (horizontalWidgetRun = this.horizontalRun) != null) {
            DependencyNode dependencyNode = horizontalWidgetRun.start;
            if (dependencyNode.resolved) {
                DependencyNode dependencyNode2 = horizontalWidgetRun.end;
                if (dependencyNode2.resolved) {
                    left = dependencyNode.value;
                    right = dependencyNode2.value;
                }
            }
        }
        if (optimize && (verticalWidgetRun = this.verticalRun) != null) {
            DependencyNode dependencyNode3 = verticalWidgetRun.start;
            if (dependencyNode3.resolved) {
                DependencyNode dependencyNode4 = verticalWidgetRun.end;
                if (dependencyNode4.resolved) {
                    top = dependencyNode3.value;
                    bottom = dependencyNode4.value;
                }
            }
        }
        int h = bottom - top;
        if (right - left < 0 || h < 0 || left == Integer.MIN_VALUE || left == Integer.MAX_VALUE || top == Integer.MIN_VALUE || top == Integer.MAX_VALUE || right == Integer.MIN_VALUE || right == Integer.MAX_VALUE || bottom == Integer.MIN_VALUE || bottom == Integer.MAX_VALUE) {
            left = 0;
            top = 0;
            right = 0;
            bottom = 0;
        }
        setFrame(left, top, right, bottom);
    }

    public void copy(ConstraintWidget src, HashMap<ConstraintWidget, ConstraintWidget> map) {
        this.mHorizontalResolution = src.mHorizontalResolution;
        this.mVerticalResolution = src.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = src.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = src.mMatchConstraintDefaultHeight;
        int[] iArr = this.mResolvedMatchConstraintDefault;
        int[] iArr2 = src.mResolvedMatchConstraintDefault;
        iArr[0] = iArr2[0];
        iArr[1] = iArr2[1];
        this.mMatchConstraintMinWidth = src.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = src.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = src.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = src.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = src.mMatchConstraintPercentHeight;
        this.mIsWidthWrapContent = src.mIsWidthWrapContent;
        this.mIsHeightWrapContent = src.mIsHeightWrapContent;
        this.mResolvedDimensionRatioSide = src.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = src.mResolvedDimensionRatio;
        int[] iArr3 = src.mMaxDimension;
        this.mMaxDimension = Arrays.copyOf(iArr3, iArr3.length);
        this.mCircleConstraintAngle = src.mCircleConstraintAngle;
        this.hasBaseline = src.hasBaseline;
        this.inPlaceholder = src.inPlaceholder;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        ConstraintWidget constraintWidget = null;
        this.mParent = this.mParent == null ? null : map.get(src.mParent);
        this.mWidth = src.mWidth;
        this.mHeight = src.mHeight;
        this.mDimensionRatio = src.mDimensionRatio;
        this.mDimensionRatioSide = src.mDimensionRatioSide;
        this.mX = src.mX;
        this.mY = src.mY;
        this.mRelX = src.mRelX;
        this.mRelY = src.mRelY;
        this.mOffsetX = src.mOffsetX;
        this.mOffsetY = src.mOffsetY;
        this.mBaselineDistance = src.mBaselineDistance;
        this.mMinWidth = src.mMinWidth;
        this.mMinHeight = src.mMinHeight;
        this.mHorizontalBiasPercent = src.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = src.mVerticalBiasPercent;
        this.mCompanionWidget = src.mCompanionWidget;
        this.mContainerItemSkip = src.mContainerItemSkip;
        this.mVisibility = src.mVisibility;
        this.mDebugName = src.mDebugName;
        this.mType = src.mType;
        this.mDistToTop = src.mDistToTop;
        this.mDistToLeft = src.mDistToLeft;
        this.mDistToRight = src.mDistToRight;
        this.mDistToBottom = src.mDistToBottom;
        this.mLeftHasCentered = src.mLeftHasCentered;
        this.mRightHasCentered = src.mRightHasCentered;
        this.mTopHasCentered = src.mTopHasCentered;
        this.mBottomHasCentered = src.mBottomHasCentered;
        this.mHorizontalWrapVisited = src.mHorizontalWrapVisited;
        this.mVerticalWrapVisited = src.mVerticalWrapVisited;
        this.mHorizontalChainStyle = src.mHorizontalChainStyle;
        this.mVerticalChainStyle = src.mVerticalChainStyle;
        this.mHorizontalChainFixedPosition = src.mHorizontalChainFixedPosition;
        this.mVerticalChainFixedPosition = src.mVerticalChainFixedPosition;
        float[] fArr = this.mWeight;
        float[] fArr2 = src.mWeight;
        fArr[0] = fArr2[0];
        fArr[1] = fArr2[1];
        ConstraintWidget[] constraintWidgetArr = this.mListNextMatchConstraintsWidget;
        ConstraintWidget[] constraintWidgetArr2 = src.mListNextMatchConstraintsWidget;
        constraintWidgetArr[0] = constraintWidgetArr2[0];
        constraintWidgetArr[1] = constraintWidgetArr2[1];
        ConstraintWidget[] constraintWidgetArr3 = this.mNextChainWidget;
        ConstraintWidget[] constraintWidgetArr4 = src.mNextChainWidget;
        constraintWidgetArr3[0] = constraintWidgetArr4[0];
        constraintWidgetArr3[1] = constraintWidgetArr4[1];
        ConstraintWidget constraintWidget2 = src.mHorizontalNextWidget;
        this.mHorizontalNextWidget = constraintWidget2 == null ? null : map.get(constraintWidget2);
        ConstraintWidget constraintWidget3 = src.mVerticalNextWidget;
        if (constraintWidget3 != null) {
            constraintWidget = map.get(constraintWidget3);
        }
        this.mVerticalNextWidget = constraintWidget;
    }

    public void updateFromRuns(boolean updateHorizontal, boolean updateVertical) {
        boolean updateHorizontal2 = updateHorizontal & this.horizontalRun.isResolved();
        boolean updateVertical2 = updateVertical & this.verticalRun.isResolved();
        HorizontalWidgetRun horizontalWidgetRun = this.horizontalRun;
        int left = horizontalWidgetRun.start.value;
        VerticalWidgetRun verticalWidgetRun = this.verticalRun;
        int top = verticalWidgetRun.start.value;
        int right = horizontalWidgetRun.end.value;
        int bottom = verticalWidgetRun.end.value;
        int h = bottom - top;
        if (right - left < 0 || h < 0 || left == Integer.MIN_VALUE || left == Integer.MAX_VALUE || top == Integer.MIN_VALUE || top == Integer.MAX_VALUE || right == Integer.MIN_VALUE || right == Integer.MAX_VALUE || bottom == Integer.MIN_VALUE || bottom == Integer.MAX_VALUE) {
            left = 0;
            top = 0;
            right = 0;
            bottom = 0;
        }
        int w = right - left;
        int h2 = bottom - top;
        if (updateHorizontal2) {
            this.mX = left;
        }
        if (updateVertical2) {
            this.mY = top;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (updateHorizontal2) {
            if (this.mListDimensionBehaviors[0] == DimensionBehaviour.FIXED && w < this.mWidth) {
                w = this.mWidth;
            }
            this.mWidth = w;
            int i = this.mMinWidth;
            if (w < i) {
                this.mWidth = i;
            }
        }
        if (updateVertical2) {
            if (this.mListDimensionBehaviors[1] == DimensionBehaviour.FIXED && h2 < this.mHeight) {
                h2 = this.mHeight;
            }
            this.mHeight = h2;
            int i2 = this.mMinHeight;
            if (h2 < i2) {
                this.mHeight = i2;
            }
        }
    }

    public void addChildrenToSolverByDependency(ConstraintWidgetContainer container, LinearSystem system, HashSet<ConstraintWidget> widgets, int orientation, boolean addSelf) {
        if (addSelf) {
            if (widgets.contains(this)) {
                Optimizer.checkMatchParent(container, system, this);
                widgets.remove(this);
                addToSolver(system, container.optimizeFor(64));
            } else {
                return;
            }
        }
        if (orientation == 0) {
            HashSet<ConstraintAnchor> dependents = this.mLeft.getDependents();
            if (dependents != null) {
                Iterator<ConstraintAnchor> it = dependents.iterator();
                while (it.hasNext()) {
                    it.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
                }
            }
            HashSet<ConstraintAnchor> dependents2 = this.mRight.getDependents();
            if (dependents2 != null) {
                Iterator<ConstraintAnchor> it2 = dependents2.iterator();
                while (it2.hasNext()) {
                    it2.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
                }
                return;
            }
            return;
        }
        HashSet<ConstraintAnchor> dependents3 = this.mTop.getDependents();
        if (dependents3 != null) {
            Iterator<ConstraintAnchor> it3 = dependents3.iterator();
            while (it3.hasNext()) {
                it3.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
            }
        }
        HashSet<ConstraintAnchor> dependents4 = this.mBottom.getDependents();
        if (dependents4 != null) {
            Iterator<ConstraintAnchor> it4 = dependents4.iterator();
            while (it4.hasNext()) {
                it4.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
            }
        }
        HashSet<ConstraintAnchor> dependents5 = this.mBaseline.getDependents();
        if (dependents5 != null) {
            Iterator<ConstraintAnchor> it5 = dependents5.iterator();
            while (it5.hasNext()) {
                it5.next().mOwner.addChildrenToSolverByDependency(container, system, widgets, orientation, true);
            }
        }
    }

    public void getSceneString(StringBuilder ret) {
        ret.append(JustifyTextView.TWO_CHINESE_BLANK + this.stringId + ":{\n");
        StringBuilder sb = new StringBuilder();
        sb.append("    actualWidth:");
        sb.append(this.mWidth);
        ret.append(sb.toString());
        ret.append("\n");
        ret.append("    actualHeight:" + this.mHeight);
        ret.append("\n");
        ret.append("    actualLeft:" + this.mX);
        ret.append("\n");
        ret.append("    actualTop:" + this.mY);
        ret.append("\n");
        getSceneString(ret, "left", this.mLeft);
        getSceneString(ret, "top", this.mTop);
        getSceneString(ret, "right", this.mRight);
        getSceneString(ret, "bottom", this.mBottom);
        getSceneString(ret, "baseline", this.mBaseline);
        getSceneString(ret, "centerX", this.mCenterX);
        getSceneString(ret, "centerY", this.mCenterY);
        getSceneString(ret, "    width", this.mWidth, this.mMinWidth, this.mMaxDimension[0], this.mWidthOverride, this.mMatchConstraintMinWidth, this.mMatchConstraintDefaultWidth, this.mMatchConstraintPercentWidth, this.mWeight[0]);
        getSceneString(ret, "    height", this.mHeight, this.mMinHeight, this.mMaxDimension[1], this.mHeightOverride, this.mMatchConstraintMinHeight, this.mMatchConstraintDefaultHeight, this.mMatchConstraintPercentHeight, this.mWeight[1]);
        serializeDimensionRatio(ret, "    dimensionRatio", this.mDimensionRatio, this.mDimensionRatioSide);
        serializeAttribute(ret, "    horizontalBias", this.mHorizontalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(ret, "    verticalBias", this.mVerticalBiasPercent, DEFAULT_BIAS);
        serializeAttribute(ret, "    horizontalChainStyle", this.mHorizontalChainStyle, 0);
        serializeAttribute(ret, "    verticalChainStyle", this.mVerticalChainStyle, 0);
        ret.append("  }");
    }

    private void getSceneString(StringBuilder ret, String type, int size, int min, int max, int override, int matchConstraintMin, int matchConstraintDefault, float MatchConstraintPercent, float weight) {
        ret.append(type);
        ret.append(" :  {\n");
        serializeAttribute(ret, "      size", size, 0);
        serializeAttribute(ret, "      min", min, 0);
        serializeAttribute(ret, "      max", max, Integer.MAX_VALUE);
        serializeAttribute(ret, "      matchMin", matchConstraintMin, 0);
        serializeAttribute(ret, "      matchDef", matchConstraintDefault, 0);
        serializeAttribute(ret, "      matchPercent", MatchConstraintPercent, 1.0f);
        ret.append("    },\n");
    }

    private void getSceneString(StringBuilder ret, String side, ConstraintAnchor a) {
        if (a.mTarget != null) {
            ret.append("    ");
            ret.append(side);
            ret.append(" : [ '");
            ret.append(a.mTarget);
            ret.append("'");
            if (!(a.mGoneMargin == Integer.MIN_VALUE && a.mMargin == 0)) {
                ret.append(",");
                ret.append(a.mMargin);
                if (a.mGoneMargin != Integer.MIN_VALUE) {
                    ret.append(",");
                    ret.append(a.mGoneMargin);
                    ret.append(",");
                }
            }
            ret.append(" ] ,\n");
        }
    }
}
