package com.didichuxing.doraemonkit.kit.toolpanel.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.DimenRes;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.didichuxing.doraemonkit.kit.toolpanel.decoration.FlexibleDividerDecoration;

public class VerticalDividerItemDecoration extends FlexibleDividerDecoration {
    private MarginProvider mMarginProvider;

    public interface MarginProvider {
        int dividerBottomMargin(int i, RecyclerView recyclerView);

        int dividerTopMargin(int i, RecyclerView recyclerView);
    }

    protected VerticalDividerItemDecoration(Builder builder) {
        super(builder);
        this.mMarginProvider = builder.mMarginProvider;
    }

    /* access modifiers changed from: protected */
    public Rect getDividerBound(int position, RecyclerView parent, View child) {
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        bounds.top = child.getTop() + transitionY;
        bounds.bottom = child.getBottom() + transitionY;
        int dividerSize = getDividerSize(position, parent);
        FlexibleDividerDecoration.DividerType dividerType = this.mDividerType;
        if (dividerType == FlexibleDividerDecoration.DividerType.DRAWABLE || dividerType == FlexibleDividerDecoration.DividerType.SPACE) {
            if (alignTopEdge(parent, position) != 0) {
                bounds.top += this.mMarginProvider.dividerTopMargin(position, parent);
            }
            if (alignBottomEdge(parent, position)) {
                bounds.bottom -= this.mMarginProvider.dividerBottomMargin(position, parent);
            }
            int right = child.getRight() + params.rightMargin + transitionX;
            bounds.left = right;
            bounds.right = right + dividerSize;
        } else {
            int right2 = child.getRight() + params.rightMargin + (dividerSize / 2) + transitionX;
            bounds.left = right2;
            bounds.right = right2;
        }
        if (this.mPositionInsideItem) {
            bounds.left -= dividerSize;
            bounds.right -= dividerSize;
        }
        return bounds;
    }

    private boolean alignTopEdge(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int i = 0;
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
            int spanCount = manager.getSpanCount();
            if (manager.getOrientation() == 1) {
                if (manager.getReverseLayout()) {
                    if (lookup.getSpanGroupIndex(position, spanCount) == lookup.getSpanGroupIndex(parent.getAdapter().getItemCount() - 1, spanCount)) {
                        return true;
                    }
                } else if (lookup.getSpanGroupIndex(position, spanCount) == 0) {
                    return true;
                }
            } else if (lookup.getSpanIndex(position, spanCount) == 0) {
                return true;
            } else {
                return false;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager2 = (StaggeredGridLayoutManager) layoutManager;
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(position).getLayoutParams();
            int spanCount2 = manager2.getSpanCount();
            int spanIndex = params.getSpanIndex();
            if (manager2.getOrientation() == 1) {
                if (manager2.getReverseLayout()) {
                    int[] lastPosition = manager2.findLastVisibleItemPositions((int[]) null);
                    boolean hasTop = false;
                    int length = lastPosition.length;
                    while (true) {
                        if (i < length) {
                            int p = lastPosition[i];
                            if (p != position && p != -1 && ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(p).getLayoutParams()).getSpanIndex() == spanIndex) {
                                hasTop = true;
                                break;
                            }
                            i++;
                        } else {
                            break;
                        }
                    }
                    return !hasTop;
                } else if (position < spanCount2) {
                    return true;
                } else {
                    return false;
                }
            } else if (params.getSpanIndex() == 0) {
                return true;
            } else {
                return false;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return true;
        }
        return false;
    }

    private boolean alignBottomEdge(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int i = 0;
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
            int spanCount = manager.getSpanCount();
            int itemCount = parent.getAdapter().getItemCount();
            if (manager.getOrientation() == 1) {
                if (!manager.getReverseLayout()) {
                    int lastRowFirstPosition = 0;
                    int i2 = itemCount - 1;
                    while (true) {
                        if (i2 < 0) {
                            break;
                        } else if (lookup.getSpanIndex(i2, spanCount) == 0) {
                            lastRowFirstPosition = i2;
                            break;
                        } else {
                            i2--;
                        }
                    }
                    if (position >= lastRowFirstPosition) {
                        return true;
                    }
                } else if (lookup.getSpanGroupIndex(position, spanCount) == 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (positionTotalSpanSize(manager, position) == spanCount) {
                return true;
            } else {
                return false;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager2 = (StaggeredGridLayoutManager) layoutManager;
            int spanCount2 = manager2.getSpanCount();
            int spanIndex = ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(position).getLayoutParams()).getSpanIndex();
            if (manager2.getOrientation() == 1) {
                if (!manager2.getReverseLayout()) {
                    int[] lastPosition = manager2.findLastVisibleItemPositions((int[]) null);
                    boolean hasBottom = false;
                    int length = lastPosition.length;
                    while (true) {
                        if (i < length) {
                            int p = lastPosition[i];
                            if (p != position && p != -1 && ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(p).getLayoutParams()).getSpanIndex() == spanIndex) {
                                hasBottom = true;
                                break;
                            }
                            i++;
                        } else {
                            break;
                        }
                    }
                    return !hasBottom;
                } else if (position < spanCount2) {
                    return true;
                } else {
                    return false;
                }
            } else if (spanIndex == spanCount2 - 1) {
                return true;
            } else {
                return false;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void setItemOffsets(Rect outRect, int position, RecyclerView parent) {
        if (this.mPositionInsideItem) {
            outRect.set(0, 0, 0, 0);
        } else {
            outRect.set(0, 0, getDividerSize(position, parent), 0);
        }
    }

    private int getDividerSize(int position, RecyclerView parent) {
        FlexibleDividerDecoration.PaintProvider paintProvider = this.mPaintProvider;
        if (paintProvider != null) {
            return (int) paintProvider.dividerPaint(position, parent).getStrokeWidth();
        }
        FlexibleDividerDecoration.SizeProvider sizeProvider = this.mSizeProvider;
        if (sizeProvider != null) {
            return sizeProvider.dividerSize(position, parent);
        }
        FlexibleDividerDecoration.DrawableProvider drawableProvider = this.mDrawableProvider;
        if (drawableProvider != null) {
            return drawableProvider.drawableProvider(position, parent).getIntrinsicWidth();
        }
        throw new RuntimeException("failed to get size");
    }

    public static class Builder extends FlexibleDividerDecoration.Builder<Builder> {
        /* access modifiers changed from: private */
        public MarginProvider mMarginProvider = new MarginProvider() {
            public int dividerTopMargin(int position, RecyclerView parent) {
                return 0;
            }

            public int dividerBottomMargin(int position, RecyclerView parent) {
                return 0;
            }
        };

        public Builder(Context context) {
            super(context);
        }

        public Builder margin(final int topMargin, final int bottomMargin) {
            return marginProvider(new MarginProvider() {
                public int dividerTopMargin(int position, RecyclerView parent) {
                    return topMargin;
                }

                public int dividerBottomMargin(int position, RecyclerView parent) {
                    return bottomMargin;
                }
            });
        }

        public Builder margin(int verticalMargin) {
            return margin(verticalMargin, verticalMargin);
        }

        public Builder marginResId(@DimenRes int topMarginId, @DimenRes int bottomMarginId) {
            return margin(this.mResources.getDimensionPixelSize(topMarginId), this.mResources.getDimensionPixelSize(bottomMarginId));
        }

        public Builder marginResId(@DimenRes int verticalMarginId) {
            return marginResId(verticalMarginId, verticalMarginId);
        }

        public Builder marginProvider(MarginProvider provider) {
            this.mMarginProvider = provider;
            return this;
        }

        public VerticalDividerItemDecoration build() {
            checkBuilderParams();
            return new VerticalDividerItemDecoration(this);
        }
    }
}
