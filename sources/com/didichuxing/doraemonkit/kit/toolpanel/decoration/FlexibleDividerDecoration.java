package com.didichuxing.doraemonkit.kit.toolpanel.decoration;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class FlexibleDividerDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = {16843284};
    private static final int DEFAULT_SIZE = 2;
    protected ColorProvider mColorProvider;
    protected DividerType mDividerType;
    protected DrawableProvider mDrawableProvider;
    private Paint mPaint;
    protected PaintProvider mPaintProvider;
    protected boolean mPositionInsideItem;
    protected boolean mShowLastDivider;
    protected SizeProvider mSizeProvider;
    protected SizeProvider mSpaceProvider;
    protected VisibilityProvider mVisibilityProvider;

    public interface ColorProvider {
        int dividerColor(int i, RecyclerView recyclerView);
    }

    public enum DividerType {
        DRAWABLE,
        PAINT,
        COLOR,
        SPACE
    }

    public interface DrawableProvider {
        Drawable drawableProvider(int i, RecyclerView recyclerView);
    }

    public interface PaintProvider {
        Paint dividerPaint(int i, RecyclerView recyclerView);
    }

    public interface SizeProvider {
        int dividerSize(int i, RecyclerView recyclerView);
    }

    public interface VisibilityProvider {
        boolean shouldHideDivider(int i, RecyclerView recyclerView);
    }

    /* access modifiers changed from: protected */
    public abstract Rect getDividerBound(int i, RecyclerView recyclerView, View view);

    /* access modifiers changed from: protected */
    public abstract void setItemOffsets(Rect rect, int i, RecyclerView recyclerView);

    protected FlexibleDividerDecoration(Builder builder) {
        DividerType dividerType = DividerType.DRAWABLE;
        this.mDividerType = dividerType;
        if (builder.mPaintProvider != null) {
            this.mDividerType = DividerType.PAINT;
            this.mPaintProvider = builder.mPaintProvider;
        } else if (builder.mColorProvider != null) {
            this.mDividerType = DividerType.COLOR;
            this.mColorProvider = builder.mColorProvider;
            this.mPaint = new Paint();
            setSizeProvider(builder);
        } else if (builder.mSpaceProvider != null) {
            this.mDividerType = DividerType.SPACE;
            this.mSpaceProvider = builder.mSpaceProvider;
        } else {
            this.mDividerType = dividerType;
            if (builder.mDrawableProvider == null) {
                TypedArray a = builder.mContext.obtainStyledAttributes(ATTRS);
                final Drawable divider = a.getDrawable(0);
                a.recycle();
                this.mDrawableProvider = new DrawableProvider() {
                    public Drawable drawableProvider(int position, RecyclerView parent) {
                        return divider;
                    }
                };
            } else {
                this.mDrawableProvider = builder.mDrawableProvider;
            }
            this.mSizeProvider = builder.mSizeProvider;
        }
        this.mVisibilityProvider = builder.mVisibilityProvider;
        this.mShowLastDivider = builder.mShowLastDivider;
        this.mPositionInsideItem = builder.mPositionInsideItem;
    }

    private void setSizeProvider(Builder builder) {
        SizeProvider access$500 = builder.mSizeProvider;
        this.mSizeProvider = access$500;
        if (access$500 == null) {
            this.mSizeProvider = new SizeProvider() {
                public int dividerSize(int position, RecyclerView parent) {
                    return 2;
                }
            };
        }
    }

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        RecyclerView recyclerView = parent;
        if (parent.getAdapter() != null) {
            int validChildCount = parent.getChildCount();
            for (int i = 0; i < validChildCount; i++) {
                View child = recyclerView.getChildAt(i);
                int childPosition = recyclerView.getChildAdapterPosition(child);
                if (hasDivider(recyclerView, childPosition)) {
                    if (!this.mVisibilityProvider.shouldHideDivider(childPosition, recyclerView)) {
                        Rect bounds = getDividerBound(childPosition, recyclerView, child);
                        switch (AnonymousClass3.$SwitchMap$com$didichuxing$doraemonkit$kit$toolpanel$decoration$FlexibleDividerDecoration$DividerType[this.mDividerType.ordinal()]) {
                            case 1:
                                Drawable drawable = this.mDrawableProvider.drawableProvider(childPosition, recyclerView);
                                drawable.setBounds(bounds);
                                drawable.draw(c);
                                break;
                            case 2:
                                Paint dividerPaint = this.mPaintProvider.dividerPaint(childPosition, recyclerView);
                                this.mPaint = dividerPaint;
                                c.drawLine((float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, dividerPaint);
                                break;
                            case 3:
                                this.mPaint.setColor(this.mColorProvider.dividerColor(childPosition, recyclerView));
                                this.mPaint.setStrokeWidth((float) this.mSizeProvider.dividerSize(childPosition, recyclerView));
                                c.drawLine((float) bounds.left, (float) bounds.top, (float) bounds.right, (float) bounds.bottom, this.mPaint);
                                break;
                            default:
                                Canvas canvas = c;
                                break;
                        }
                    } else {
                        Canvas canvas2 = c;
                    }
                } else {
                    Canvas canvas3 = c;
                }
            }
            Canvas canvas4 = c;
        }
    }

    /* renamed from: com.didichuxing.doraemonkit.kit.toolpanel.decoration.FlexibleDividerDecoration$3  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$didichuxing$doraemonkit$kit$toolpanel$decoration$FlexibleDividerDecoration$DividerType;

        static {
            int[] iArr = new int[DividerType.values().length];
            $SwitchMap$com$didichuxing$doraemonkit$kit$toolpanel$decoration$FlexibleDividerDecoration$DividerType = iArr;
            try {
                iArr[DividerType.DRAWABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$kit$toolpanel$decoration$FlexibleDividerDecoration$DividerType[DividerType.PAINT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$kit$toolpanel$decoration$FlexibleDividerDecoration$DividerType[DividerType.COLOR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$kit$toolpanel$decoration$FlexibleDividerDecoration$DividerType[DividerType.SPACE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public boolean hasDivider(RecyclerView parent, int childPosition) {
        if (this.mShowLastDivider) {
            return true;
        }
        if (this instanceof VerticalDividerItemDecoration) {
            return hasVerticalDivider(parent, childPosition);
        }
        if (this instanceof HorizontalDividerItemDecoration) {
            return hasHorizontalDivider(parent, childPosition);
        }
        return false;
    }

    private boolean hasVerticalDivider(RecyclerView parent, int position) {
        int[] lastPosition;
        int i = position;
        int itemCount = parent.getAdapter().getItemCount();
        int lastDividerOffset = getLastDividerOffset(parent);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = manager.getSpanCount();
            if (manager.getOrientation() != 1) {
                if (manager.getReverseLayout()) {
                    if (manager.getSpanSizeLookup().getSpanGroupIndex(i, spanCount) != 0) {
                        return true;
                    }
                    return false;
                } else if (i < itemCount - lastDividerOffset) {
                    return true;
                } else {
                    return false;
                }
            } else if (positionTotalSpanSize(manager, i) != spanCount) {
                return true;
            } else {
                return false;
            }
        } else {
            if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager2 = (StaggeredGridLayoutManager) parent.getLayoutManager();
                int spanCount2 = manager2.getSpanCount();
                int spanIndex = ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(i).getLayoutParams()).getSpanIndex();
                if (manager2.getOrientation() != 1) {
                    if (manager2.getReverseLayout()) {
                        lastPosition = manager2.findFirstVisibleItemPositions((int[]) null);
                    } else {
                        lastPosition = manager2.findLastVisibleItemPositions((int[]) null);
                    }
                    for (int p : lastPosition) {
                        if (p != i && p != -1 && ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(p).getLayoutParams()).getSpanIndex() == spanIndex) {
                            return true;
                        }
                    }
                    return false;
                } else if (spanIndex < spanCount2 - 1) {
                    return true;
                } else {
                    return false;
                }
            } else if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
                return false;
            } else {
                if (((LinearLayoutManager) parent.getLayoutManager()).getReverseLayout()) {
                    if (i > 0) {
                        return true;
                    }
                    return false;
                } else if (i < itemCount - 1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private boolean hasHorizontalDivider(RecyclerView parent, int position) {
        int i = position;
        int itemCount = parent.getAdapter().getItemCount();
        int lastDividerOffset = getLastDividerOffset(parent);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = manager.getSpanCount();
            if (manager.getOrientation() == 1) {
                if (manager.getReverseLayout()) {
                    if (manager.getSpanSizeLookup().getSpanGroupIndex(i, spanCount) != 0) {
                        return true;
                    }
                    return false;
                } else if (i < itemCount - lastDividerOffset) {
                    return true;
                } else {
                    return false;
                }
            } else if (positionTotalSpanSize(manager, i) != spanCount) {
                return true;
            } else {
                return false;
            }
        } else {
            if (parent.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager2 = (StaggeredGridLayoutManager) parent.getLayoutManager();
                int spanCount2 = manager2.getSpanCount();
                int spanIndex = ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(i).getLayoutParams()).getSpanIndex();
                if (manager2.getOrientation() == 1) {
                    if (!manager2.getReverseLayout()) {
                        for (int p : manager2.findLastVisibleItemPositions((int[]) null)) {
                            if (p != i && p != -1 && ((StaggeredGridLayoutManager.LayoutParams) manager2.findViewByPosition(p).getLayoutParams()).getSpanIndex() == spanIndex) {
                                return true;
                            }
                        }
                        return false;
                    } else if (i > spanCount2 - 1) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (spanIndex < spanCount2 - 1) {
                    return true;
                } else {
                    return false;
                }
            } else if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
                return false;
            } else {
                if (((LinearLayoutManager) parent.getLayoutManager()).getReverseLayout()) {
                    if (i > 0) {
                        return true;
                    }
                    return false;
                } else if (i < itemCount - 1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public int positionTotalSpanSize(GridLayoutManager manager, int position) {
        int totalSpanSize = 0;
        GridLayoutManager.SpanSizeLookup spanSizeLookup = manager.getSpanSizeLookup();
        int spanCount = manager.getSpanCount();
        int groupIndex = spanSizeLookup.getSpanGroupIndex(position, spanCount);
        int i = position;
        while (i >= 0 && spanSizeLookup.getSpanGroupIndex(i, spanCount) == groupIndex) {
            totalSpanSize += spanSizeLookup.getSpanSize(i);
            i--;
        }
        return totalSpanSize;
    }

    public void getItemOffsets(Rect rect, View v, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(v);
        if (hasDivider(parent, position) && !this.mVisibilityProvider.shouldHideDivider(position, parent)) {
            setItemOffsets(rect, position, parent);
        }
    }

    private int getLastDividerOffset(RecyclerView parent) {
        if (!(parent.getLayoutManager() instanceof GridLayoutManager)) {
            return 1;
        }
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        GridLayoutManager.SpanSizeLookup spanSizeLookup = layoutManager.getSpanSizeLookup();
        int spanCount = layoutManager.getSpanCount();
        int itemCount = parent.getAdapter().getItemCount();
        for (int i = itemCount - 1; i >= 0; i--) {
            if (spanSizeLookup.getSpanIndex(i, spanCount) == 0) {
                return itemCount - i;
            }
        }
        return 1;
    }

    public static class Builder<T extends Builder> {
        /* access modifiers changed from: private */
        public ColorProvider mColorProvider;
        /* access modifiers changed from: private */
        public Context mContext;
        /* access modifiers changed from: private */
        public DrawableProvider mDrawableProvider;
        /* access modifiers changed from: private */
        public PaintProvider mPaintProvider;
        /* access modifiers changed from: private */
        public boolean mPositionInsideItem = false;
        protected Resources mResources;
        /* access modifiers changed from: private */
        public boolean mShowLastDivider = false;
        /* access modifiers changed from: private */
        public SizeProvider mSizeProvider;
        /* access modifiers changed from: private */
        public SizeProvider mSpaceProvider;
        /* access modifiers changed from: private */
        public VisibilityProvider mVisibilityProvider = new VisibilityProvider() {
            public boolean shouldHideDivider(int position, RecyclerView parent) {
                return false;
            }
        };

        public Builder(Context context) {
            this.mContext = context;
            this.mResources = context.getResources();
        }

        public T paint(final Paint paint) {
            return paintProvider(new PaintProvider() {
                public Paint dividerPaint(int position, RecyclerView parent) {
                    return paint;
                }
            });
        }

        public T paintProvider(PaintProvider provider) {
            this.mPaintProvider = provider;
            return this;
        }

        public T color(final int color) {
            return colorProvider(new ColorProvider() {
                public int dividerColor(int position, RecyclerView parent) {
                    return color;
                }
            });
        }

        public T colorResId(@ColorRes int colorId) {
            return color(ContextCompat.getColor(this.mContext, colorId));
        }

        public T colorProvider(ColorProvider provider) {
            this.mColorProvider = provider;
            return this;
        }

        public T drawable(@DrawableRes int id) {
            return drawable(ContextCompat.getDrawable(this.mContext, id));
        }

        public T drawable(final Drawable drawable) {
            return drawableProvider(new DrawableProvider() {
                public Drawable drawableProvider(int position, RecyclerView parent) {
                    return drawable;
                }
            });
        }

        public T drawableProvider(DrawableProvider provider) {
            this.mDrawableProvider = provider;
            return this;
        }

        public T size(final int size) {
            return sizeProvider(new SizeProvider() {
                public int dividerSize(int position, RecyclerView parent) {
                    return size;
                }
            });
        }

        public T sizeResId(@DimenRes int sizeId) {
            return size(this.mResources.getDimensionPixelSize(sizeId));
        }

        public T sizeProvider(SizeProvider provider) {
            this.mSizeProvider = provider;
            return this;
        }

        public T space(final int space) {
            return spaceProvider(new SizeProvider() {
                public int dividerSize(int position, RecyclerView parent) {
                    return space;
                }
            });
        }

        public T spaceResId(@DimenRes int spaceId) {
            return space(this.mResources.getDimensionPixelSize(spaceId));
        }

        public T spaceProvider(SizeProvider provider) {
            this.mSpaceProvider = provider;
            return this;
        }

        public T visibilityProvider(VisibilityProvider provider) {
            this.mVisibilityProvider = provider;
            return this;
        }

        public T showLastDivider() {
            this.mShowLastDivider = true;
            return this;
        }

        public T positionInsideItem(boolean positionInsideItem) {
            this.mPositionInsideItem = positionInsideItem;
            return this;
        }

        /* access modifiers changed from: protected */
        public void checkBuilderParams() {
            if (this.mPaintProvider == null) {
                return;
            }
            if (this.mColorProvider != null) {
                throw new IllegalArgumentException("Use setColor method of Paint class to specify line color. Do not provider ColorProvider if you set PaintProvider.");
            } else if (this.mSizeProvider != null) {
                throw new IllegalArgumentException("Use setStrokeWidth method of Paint class to specify line size. Do not provider SizeProvider if you set PaintProvider.");
            }
        }
    }
}
