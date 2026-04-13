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

public class HorizontalDividerItemDecoration extends FlexibleDividerDecoration {
    private MarginProvider mMarginProvider;

    public interface MarginProvider {
        int dividerLeftMargin(int i, RecyclerView recyclerView);

        int dividerRightMargin(int i, RecyclerView recyclerView);
    }

    protected HorizontalDividerItemDecoration(Builder builder) {
        super(builder);
        this.mMarginProvider = builder.mMarginProvider;
    }

    /* access modifiers changed from: protected */
    public Rect getDividerBound(int position, RecyclerView parent, View child) {
        Rect bounds = new Rect(0, 0, 0, 0);
        int transitionX = (int) ViewCompat.getTranslationX(child);
        int transitionY = (int) ViewCompat.getTranslationY(child);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        bounds.left = child.getLeft() + transitionX;
        bounds.right = child.getRight() + transitionX;
        int dividerSize = getDividerSize(position, parent);
        FlexibleDividerDecoration.DividerType dividerType = this.mDividerType;
        if (dividerType == FlexibleDividerDecoration.DividerType.DRAWABLE || dividerType == FlexibleDividerDecoration.DividerType.SPACE) {
            if (alignLeftEdge(parent, position) != 0) {
                bounds.left += this.mMarginProvider.dividerLeftMargin(position, parent);
            }
            if (alignRightEdge(parent, position)) {
                bounds.right -= this.mMarginProvider.dividerRightMargin(position, parent);
            } else {
                bounds.right += getDividerSize(position, parent);
            }
            int bottom = child.getBottom() + params.bottomMargin + transitionY;
            bounds.top = bottom;
            bounds.bottom = bottom + dividerSize;
        } else {
            int bottom2 = child.getBottom() + params.bottomMargin + (dividerSize / 2) + transitionY;
            bounds.top = bottom2;
            bounds.bottom = bottom2;
        }
        if (this.mPositionInsideItem) {
            bounds.top -= dividerSize;
            bounds.bottom -= dividerSize;
        }
        return bounds;
    }

    private boolean alignLeftEdge(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int i = 0;
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
            int spanCount = manager.getSpanCount();
            if (manager.getOrientation() == 1) {
                if (lookup.getSpanIndex(position, spanCount) == 0) {
                    return true;
                }
            } else if (manager.getReverseLayout()) {
                if (lookup.getSpanGroupIndex(position, spanCount) == lookup.getSpanGroupIndex(parent.getAdapter().getItemCount() - 1, spanCount)) {
                    return true;
                }
                return false;
            } else if (lookup.getSpanGroupIndex(position, spanCount) == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager2 = (StaggeredGridLayoutManager) layoutManager;
            int spanCount2 = manager2.getSpanCount();
            int spanIndex = ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(position).getLayoutParams()).getSpanIndex();
            if (manager2.getOrientation() == 1) {
                if (spanIndex == 0) {
                    return true;
                }
                return false;
            } else if (manager2.getReverseLayout()) {
                int[] lastPosition = manager2.findLastVisibleItemPositions((int[]) null);
                boolean hasDirectionAlign = false;
                int length = lastPosition.length;
                while (true) {
                    if (i < length) {
                        int p = lastPosition[i];
                        if (p != position && p != -1 && ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(p).getLayoutParams()).getSpanIndex() == spanIndex) {
                            hasDirectionAlign = true;
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
                return !hasDirectionAlign;
            } else if (position < spanCount2) {
                return true;
            } else {
                return false;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            return true;
        }
        return false;
    }

    private boolean alignRightEdge(RecyclerView parent, int position) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int i = 0;
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) layoutManager;
            GridLayoutManager.SpanSizeLookup lookup = manager.getSpanSizeLookup();
            int spanCount = manager.getSpanCount();
            int itemCount = parent.getAdapter().getItemCount();
            if (manager.getOrientation() == 1) {
                if (positionTotalSpanSize(manager, position) == spanCount) {
                    return true;
                }
            } else if (!manager.getReverseLayout()) {
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
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager2 = (StaggeredGridLayoutManager) layoutManager;
            int spanCount2 = manager2.getSpanCount();
            int spanIndex = ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(position).getLayoutParams()).getSpanIndex();
            if (manager2.getOrientation() == 1) {
                if (spanIndex == spanCount2 - 1) {
                    return true;
                }
                return false;
            } else if (!manager2.getReverseLayout()) {
                int[] lastPosition = manager2.findLastVisibleItemPositions((int[]) null);
                boolean hasRight = false;
                int length = lastPosition.length;
                while (true) {
                    if (i < length) {
                        int p = lastPosition[i];
                        if (p != position && p != -1 && ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(p).getLayoutParams()).getSpanIndex() == spanIndex) {
                            hasRight = true;
                            break;
                        }
                        i++;
                    } else {
                        break;
                    }
                }
                return !hasRight;
            } else if (position < spanCount2) {
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
            outRect.set(0, 0, 0, getDividerSize(position, parent));
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
            return drawableProvider.drawableProvider(position, parent).getIntrinsicHeight();
        }
        FlexibleDividerDecoration.SizeProvider sizeProvider2 = this.mSpaceProvider;
        if (sizeProvider2 != null) {
            return sizeProvider2.dividerSize(position, parent);
        }
        throw new RuntimeException("failed to get size");
    }

    public static class Builder extends FlexibleDividerDecoration.Builder<Builder> {
        /* access modifiers changed from: private */
        public MarginProvider mMarginProvider = new MarginProvider() {
            public int dividerLeftMargin(int position, RecyclerView parent) {
                return 0;
            }

            public int dividerRightMargin(int position, RecyclerView parent) {
                return 0;
            }
        };

        public Builder(Context context) {
            super(context);
        }

        public Builder margin(final int leftMargin, final int rightMargin) {
            return marginProvider(new MarginProvider() {
                public int dividerLeftMargin(int position, RecyclerView parent) {
                    return leftMargin;
                }

                public int dividerRightMargin(int position, RecyclerView parent) {
                    return rightMargin;
                }
            });
        }

        public Builder margin(int horizontalMargin) {
            return margin(horizontalMargin, horizontalMargin);
        }

        public Builder marginResId(@DimenRes int leftMarginId, @DimenRes int rightMarginId) {
            return margin(this.mResources.getDimensionPixelSize(leftMarginId), this.mResources.getDimensionPixelSize(rightMarginId));
        }

        public Builder marginResId(@DimenRes int horizontalMarginId) {
            return marginResId(horizontalMarginId, horizontalMarginId);
        }

        public Builder marginProvider(MarginProvider provider) {
            this.mMarginProvider = provider;
            return this;
        }

        public HorizontalDividerItemDecoration build() {
            checkBuilderParams();
            return new HorizontalDividerItemDecoration(this);
        }
    }
}
