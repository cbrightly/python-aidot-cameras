package androidx.constraintlayout.widget;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ConstraintProperties {
    public static final int BASELINE = 5;
    public static final int BOTTOM = 4;
    public static final int END = 7;
    public static final int LEFT = 1;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    public static final int PARENT_ID = 0;
    public static final int RIGHT = 2;
    public static final int START = 6;
    public static final int TOP = 3;
    public static final int UNSET = -1;
    public static final int WRAP_CONTENT = -2;
    ConstraintLayout.LayoutParams mParams;
    View mView;

    public ConstraintProperties center(int firstID, int firstSide, int firstMargin, int secondId, int secondSide, int secondMargin, float bias) {
        if (firstMargin < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (secondMargin < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (bias <= 0.0f || bias > 1.0f) {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        } else {
            if (firstSide == 1 || firstSide == 2) {
                connect(1, firstID, firstSide, firstMargin);
                connect(2, secondId, secondSide, secondMargin);
                this.mParams.horizontalBias = bias;
            } else if (firstSide == 6 || firstSide == 7) {
                connect(6, firstID, firstSide, firstMargin);
                connect(7, secondId, secondSide, secondMargin);
                this.mParams.horizontalBias = bias;
            } else {
                connect(3, firstID, firstSide, firstMargin);
                connect(4, secondId, secondSide, secondMargin);
                this.mParams.verticalBias = bias;
            }
            return this;
        }
    }

    public ConstraintProperties centerHorizontally(int leftId, int leftSide, int leftMargin, int rightId, int rightSide, int rightMargin, float bias) {
        connect(1, leftId, leftSide, leftMargin);
        connect(2, rightId, rightSide, rightMargin);
        this.mParams.horizontalBias = bias;
        return this;
    }

    public ConstraintProperties centerHorizontallyRtl(int startId, int startSide, int startMargin, int endId, int endSide, int endMargin, float bias) {
        connect(6, startId, startSide, startMargin);
        connect(7, endId, endSide, endMargin);
        this.mParams.horizontalBias = bias;
        return this;
    }

    public ConstraintProperties centerVertically(int topId, int topSide, int topMargin, int bottomId, int bottomSide, int bottomMargin, float bias) {
        connect(3, topId, topSide, topMargin);
        connect(4, bottomId, bottomSide, bottomMargin);
        this.mParams.verticalBias = bias;
        return this;
    }

    public ConstraintProperties centerHorizontally(int toView) {
        if (toView == 0) {
            center(0, 1, 0, 0, 2, 0, 0.5f);
        } else {
            center(toView, 2, 0, toView, 1, 0, 0.5f);
        }
        return this;
    }

    public ConstraintProperties centerHorizontallyRtl(int toView) {
        if (toView == 0) {
            center(0, 6, 0, 0, 7, 0, 0.5f);
        } else {
            center(toView, 7, 0, toView, 6, 0, 0.5f);
        }
        return this;
    }

    public ConstraintProperties centerVertically(int toView) {
        if (toView == 0) {
            center(0, 3, 0, 0, 4, 0, 0.5f);
        } else {
            center(toView, 4, 0, toView, 3, 0, 0.5f);
        }
        return this;
    }

    public ConstraintProperties removeConstraints(int anchor) {
        switch (anchor) {
            case 1:
                ConstraintLayout.LayoutParams layoutParams = this.mParams;
                layoutParams.leftToRight = -1;
                layoutParams.leftToLeft = -1;
                layoutParams.leftMargin = -1;
                layoutParams.goneLeftMargin = Integer.MIN_VALUE;
                break;
            case 2:
                ConstraintLayout.LayoutParams layoutParams2 = this.mParams;
                layoutParams2.rightToRight = -1;
                layoutParams2.rightToLeft = -1;
                layoutParams2.rightMargin = -1;
                layoutParams2.goneRightMargin = Integer.MIN_VALUE;
                break;
            case 3:
                ConstraintLayout.LayoutParams layoutParams3 = this.mParams;
                layoutParams3.topToBottom = -1;
                layoutParams3.topToTop = -1;
                layoutParams3.topMargin = -1;
                layoutParams3.goneTopMargin = Integer.MIN_VALUE;
                break;
            case 4:
                ConstraintLayout.LayoutParams layoutParams4 = this.mParams;
                layoutParams4.bottomToTop = -1;
                layoutParams4.bottomToBottom = -1;
                layoutParams4.bottomMargin = -1;
                layoutParams4.goneBottomMargin = Integer.MIN_VALUE;
                break;
            case 5:
                this.mParams.baselineToBaseline = -1;
                break;
            case 6:
                ConstraintLayout.LayoutParams layoutParams5 = this.mParams;
                layoutParams5.startToEnd = -1;
                layoutParams5.startToStart = -1;
                layoutParams5.setMarginStart(-1);
                this.mParams.goneStartMargin = Integer.MIN_VALUE;
                break;
            case 7:
                ConstraintLayout.LayoutParams layoutParams6 = this.mParams;
                layoutParams6.endToStart = -1;
                layoutParams6.endToEnd = -1;
                layoutParams6.setMarginEnd(-1);
                this.mParams.goneEndMargin = Integer.MIN_VALUE;
                break;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
        return this;
    }

    public ConstraintProperties margin(int anchor, int value) {
        switch (anchor) {
            case 1:
                this.mParams.leftMargin = value;
                break;
            case 2:
                this.mParams.rightMargin = value;
                break;
            case 3:
                this.mParams.topMargin = value;
                break;
            case 4:
                this.mParams.bottomMargin = value;
                break;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                this.mParams.setMarginStart(value);
                break;
            case 7:
                this.mParams.setMarginEnd(value);
                break;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
        return this;
    }

    public ConstraintProperties goneMargin(int anchor, int value) {
        switch (anchor) {
            case 1:
                this.mParams.goneLeftMargin = value;
                break;
            case 2:
                this.mParams.goneRightMargin = value;
                break;
            case 3:
                this.mParams.goneTopMargin = value;
                break;
            case 4:
                this.mParams.goneBottomMargin = value;
                break;
            case 5:
                throw new IllegalArgumentException("baseline does not support margins");
            case 6:
                this.mParams.goneStartMargin = value;
                break;
            case 7:
                this.mParams.goneEndMargin = value;
                break;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
        return this;
    }

    public ConstraintProperties horizontalBias(float bias) {
        this.mParams.horizontalBias = bias;
        return this;
    }

    public ConstraintProperties verticalBias(float bias) {
        this.mParams.verticalBias = bias;
        return this;
    }

    public ConstraintProperties dimensionRatio(String ratio) {
        this.mParams.dimensionRatio = ratio;
        return this;
    }

    public ConstraintProperties visibility(int visibility) {
        this.mView.setVisibility(visibility);
        return this;
    }

    public ConstraintProperties alpha(float alpha) {
        this.mView.setAlpha(alpha);
        return this;
    }

    public ConstraintProperties elevation(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mView.setElevation(elevation);
        }
        return this;
    }

    public ConstraintProperties rotation(float rotation) {
        this.mView.setRotation(rotation);
        return this;
    }

    public ConstraintProperties rotationX(float rotationX) {
        this.mView.setRotationX(rotationX);
        return this;
    }

    public ConstraintProperties rotationY(float rotationY) {
        this.mView.setRotationY(rotationY);
        return this;
    }

    public ConstraintProperties scaleX(float scaleX) {
        this.mView.setScaleY(scaleX);
        return this;
    }

    public ConstraintProperties scaleY(float scaleY) {
        return this;
    }

    public ConstraintProperties transformPivotX(float transformPivotX) {
        this.mView.setPivotX(transformPivotX);
        return this;
    }

    public ConstraintProperties transformPivotY(float transformPivotY) {
        this.mView.setPivotY(transformPivotY);
        return this;
    }

    public ConstraintProperties transformPivot(float transformPivotX, float transformPivotY) {
        this.mView.setPivotX(transformPivotX);
        this.mView.setPivotY(transformPivotY);
        return this;
    }

    public ConstraintProperties translationX(float translationX) {
        this.mView.setTranslationX(translationX);
        return this;
    }

    public ConstraintProperties translationY(float translationY) {
        this.mView.setTranslationY(translationY);
        return this;
    }

    public ConstraintProperties translation(float translationX, float translationY) {
        this.mView.setTranslationX(translationX);
        this.mView.setTranslationY(translationY);
        return this;
    }

    public ConstraintProperties translationZ(float translationZ) {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mView.setTranslationZ(translationZ);
        }
        return this;
    }

    public ConstraintProperties constrainHeight(int height) {
        this.mParams.height = height;
        return this;
    }

    public ConstraintProperties constrainWidth(int width) {
        this.mParams.width = width;
        return this;
    }

    public ConstraintProperties constrainMaxHeight(int height) {
        this.mParams.matchConstraintMaxHeight = height;
        return this;
    }

    public ConstraintProperties constrainMaxWidth(int width) {
        this.mParams.matchConstraintMaxWidth = width;
        return this;
    }

    public ConstraintProperties constrainMinHeight(int height) {
        this.mParams.matchConstraintMinHeight = height;
        return this;
    }

    public ConstraintProperties constrainMinWidth(int width) {
        this.mParams.matchConstraintMinWidth = width;
        return this;
    }

    public ConstraintProperties constrainDefaultHeight(int height) {
        this.mParams.matchConstraintDefaultHeight = height;
        return this;
    }

    public ConstraintProperties constrainDefaultWidth(int width) {
        this.mParams.matchConstraintDefaultWidth = width;
        return this;
    }

    public ConstraintProperties horizontalWeight(float weight) {
        this.mParams.horizontalWeight = weight;
        return this;
    }

    public ConstraintProperties verticalWeight(float weight) {
        this.mParams.verticalWeight = weight;
        return this;
    }

    public ConstraintProperties horizontalChainStyle(int chainStyle) {
        this.mParams.horizontalChainStyle = chainStyle;
        return this;
    }

    public ConstraintProperties verticalChainStyle(int chainStyle) {
        this.mParams.verticalChainStyle = chainStyle;
        return this;
    }

    public ConstraintProperties addToHorizontalChain(int leftId, int rightId) {
        connect(1, leftId, leftId == 0 ? 1 : 2, 0);
        connect(2, rightId, rightId == 0 ? 2 : 1, 0);
        if (leftId != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(leftId)).connect(2, this.mView.getId(), 1, 0);
        }
        if (rightId != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(rightId)).connect(1, this.mView.getId(), 2, 0);
        }
        return this;
    }

    public ConstraintProperties addToHorizontalChainRTL(int leftId, int rightId) {
        connect(6, leftId, leftId == 0 ? 6 : 7, 0);
        connect(7, rightId, rightId == 0 ? 7 : 6, 0);
        if (leftId != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(leftId)).connect(7, this.mView.getId(), 6, 0);
        }
        if (rightId != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(rightId)).connect(6, this.mView.getId(), 7, 0);
        }
        return this;
    }

    public ConstraintProperties addToVerticalChain(int topId, int bottomId) {
        connect(3, topId, topId == 0 ? 3 : 4, 0);
        connect(4, bottomId, bottomId == 0 ? 4 : 3, 0);
        if (topId != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(topId)).connect(4, this.mView.getId(), 3, 0);
        }
        if (bottomId != 0) {
            new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(bottomId)).connect(3, this.mView.getId(), 4, 0);
        }
        return this;
    }

    public ConstraintProperties removeFromVerticalChain() {
        ConstraintLayout.LayoutParams layoutParams = this.mParams;
        int topId = layoutParams.topToBottom;
        int bottomId = layoutParams.bottomToTop;
        if (!(topId == -1 && bottomId == -1)) {
            ConstraintProperties topProp = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(topId));
            ConstraintProperties bottomProp = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(bottomId));
            ConstraintLayout.LayoutParams layoutParams2 = this.mParams;
            if (topId != -1 && bottomId != -1) {
                topProp.connect(4, bottomId, 3, 0);
                bottomProp.connect(3, topId, 4, 0);
            } else if (!(topId == -1 && bottomId == -1)) {
                int i = layoutParams2.bottomToBottom;
                if (i != -1) {
                    topProp.connect(4, i, 4, 0);
                } else {
                    int i2 = layoutParams2.topToTop;
                    if (i2 != -1) {
                        bottomProp.connect(3, i2, 3, 0);
                    }
                }
            }
        }
        removeConstraints(3);
        removeConstraints(4);
        return this;
    }

    public ConstraintProperties removeFromHorizontalChain() {
        ConstraintLayout.LayoutParams layoutParams = this.mParams;
        int leftId = layoutParams.leftToRight;
        int rightId = layoutParams.rightToLeft;
        if (leftId == -1 && rightId == -1) {
            int startId = layoutParams.startToEnd;
            int endId = layoutParams.endToStart;
            if (!(startId == -1 && endId == -1)) {
                ConstraintProperties startProp = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(startId));
                ConstraintProperties endProp = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(endId));
                ConstraintLayout.LayoutParams layoutParams2 = this.mParams;
                if (startId != -1 && endId != -1) {
                    startProp.connect(7, endId, 6, 0);
                    endProp.connect(6, leftId, 7, 0);
                } else if (!(leftId == -1 && endId == -1)) {
                    int i = layoutParams2.rightToRight;
                    if (i != -1) {
                        startProp.connect(7, i, 7, 0);
                    } else {
                        int i2 = layoutParams2.leftToLeft;
                        if (i2 != -1) {
                            endProp.connect(6, i2, 6, 0);
                        }
                    }
                }
            }
            removeConstraints(6);
            removeConstraints(7);
        } else {
            ConstraintProperties leftProp = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(leftId));
            ConstraintProperties rightProp = new ConstraintProperties(((ViewGroup) this.mView.getParent()).findViewById(rightId));
            ConstraintLayout.LayoutParams layoutParams3 = this.mParams;
            if (leftId != -1 && rightId != -1) {
                leftProp.connect(2, rightId, 1, 0);
                rightProp.connect(1, leftId, 2, 0);
            } else if (!(leftId == -1 && rightId == -1)) {
                int i3 = layoutParams3.rightToRight;
                if (i3 != -1) {
                    leftProp.connect(2, i3, 2, 0);
                } else {
                    int i4 = layoutParams3.leftToLeft;
                    if (i4 != -1) {
                        rightProp.connect(1, i4, 1, 0);
                    }
                }
            }
            removeConstraints(1);
            removeConstraints(2);
        }
        return this;
    }

    public ConstraintProperties connect(int startSide, int endID, int endSide, int margin) {
        switch (startSide) {
            case 1:
                if (endSide == 1) {
                    ConstraintLayout.LayoutParams layoutParams = this.mParams;
                    layoutParams.leftToLeft = endID;
                    layoutParams.leftToRight = -1;
                } else if (endSide == 2) {
                    ConstraintLayout.LayoutParams layoutParams2 = this.mParams;
                    layoutParams2.leftToRight = endID;
                    layoutParams2.leftToLeft = -1;
                } else {
                    throw new IllegalArgumentException("Left to " + sideToString(endSide) + " undefined");
                }
                this.mParams.leftMargin = margin;
                break;
            case 2:
                if (endSide == 1) {
                    ConstraintLayout.LayoutParams layoutParams3 = this.mParams;
                    layoutParams3.rightToLeft = endID;
                    layoutParams3.rightToRight = -1;
                } else if (endSide == 2) {
                    ConstraintLayout.LayoutParams layoutParams4 = this.mParams;
                    layoutParams4.rightToRight = endID;
                    layoutParams4.rightToLeft = -1;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                this.mParams.rightMargin = margin;
                break;
            case 3:
                if (endSide == 3) {
                    ConstraintLayout.LayoutParams layoutParams5 = this.mParams;
                    layoutParams5.topToTop = endID;
                    layoutParams5.topToBottom = -1;
                    layoutParams5.baselineToBaseline = -1;
                    layoutParams5.baselineToTop = -1;
                    layoutParams5.baselineToBottom = -1;
                } else if (endSide == 4) {
                    ConstraintLayout.LayoutParams layoutParams6 = this.mParams;
                    layoutParams6.topToBottom = endID;
                    layoutParams6.topToTop = -1;
                    layoutParams6.baselineToBaseline = -1;
                    layoutParams6.baselineToTop = -1;
                    layoutParams6.baselineToBottom = -1;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                this.mParams.topMargin = margin;
                break;
            case 4:
                if (endSide == 4) {
                    ConstraintLayout.LayoutParams layoutParams7 = this.mParams;
                    layoutParams7.bottomToBottom = endID;
                    layoutParams7.bottomToTop = -1;
                    layoutParams7.baselineToBaseline = -1;
                    layoutParams7.baselineToTop = -1;
                    layoutParams7.baselineToBottom = -1;
                } else if (endSide == 3) {
                    ConstraintLayout.LayoutParams layoutParams8 = this.mParams;
                    layoutParams8.bottomToTop = endID;
                    layoutParams8.bottomToBottom = -1;
                    layoutParams8.baselineToBaseline = -1;
                    layoutParams8.baselineToTop = -1;
                    layoutParams8.baselineToBottom = -1;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                this.mParams.bottomMargin = margin;
                break;
            case 5:
                if (endSide == 5) {
                    ConstraintLayout.LayoutParams layoutParams9 = this.mParams;
                    layoutParams9.baselineToBaseline = endID;
                    layoutParams9.bottomToBottom = -1;
                    layoutParams9.bottomToTop = -1;
                    layoutParams9.topToTop = -1;
                    layoutParams9.topToBottom = -1;
                }
                if (endSide == 3) {
                    ConstraintLayout.LayoutParams layoutParams10 = this.mParams;
                    layoutParams10.baselineToTop = endID;
                    layoutParams10.bottomToBottom = -1;
                    layoutParams10.bottomToTop = -1;
                    layoutParams10.topToTop = -1;
                    layoutParams10.topToBottom = -1;
                } else if (endSide == 4) {
                    ConstraintLayout.LayoutParams layoutParams11 = this.mParams;
                    layoutParams11.baselineToBottom = endID;
                    layoutParams11.bottomToBottom = -1;
                    layoutParams11.bottomToTop = -1;
                    layoutParams11.topToTop = -1;
                    layoutParams11.topToBottom = -1;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                this.mParams.baselineMargin = margin;
                break;
            case 6:
                if (endSide == 6) {
                    ConstraintLayout.LayoutParams layoutParams12 = this.mParams;
                    layoutParams12.startToStart = endID;
                    layoutParams12.startToEnd = -1;
                } else if (endSide == 7) {
                    ConstraintLayout.LayoutParams layoutParams13 = this.mParams;
                    layoutParams13.startToEnd = endID;
                    layoutParams13.startToStart = -1;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                if (Build.VERSION.SDK_INT >= 17) {
                    this.mParams.setMarginStart(margin);
                    break;
                }
                break;
            case 7:
                if (endSide == 7) {
                    ConstraintLayout.LayoutParams layoutParams14 = this.mParams;
                    layoutParams14.endToEnd = endID;
                    layoutParams14.endToStart = -1;
                } else if (endSide == 6) {
                    ConstraintLayout.LayoutParams layoutParams15 = this.mParams;
                    layoutParams15.endToStart = endID;
                    layoutParams15.endToEnd = -1;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                if (Build.VERSION.SDK_INT >= 17) {
                    this.mParams.setMarginEnd(margin);
                    break;
                }
                break;
            default:
                throw new IllegalArgumentException(sideToString(startSide) + " to " + sideToString(endSide) + " unknown");
        }
        return this;
    }

    private String sideToString(int side) {
        switch (side) {
            case 1:
                return "left";
            case 2:
                return "right";
            case 3:
                return "top";
            case 4:
                return "bottom";
            case 5:
                return "baseline";
            case 6:
                return "start";
            case 7:
                return "end";
            default:
                return "undefined";
        }
    }

    public ConstraintProperties(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ConstraintLayout.LayoutParams) {
            this.mParams = (ConstraintLayout.LayoutParams) params;
            this.mView = view;
            return;
        }
        throw new RuntimeException("Only children of ConstraintLayout.LayoutParams supported");
    }

    public void apply() {
    }
}
