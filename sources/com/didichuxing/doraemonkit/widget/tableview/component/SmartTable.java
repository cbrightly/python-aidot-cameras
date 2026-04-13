package com.didichuxing.doraemonkit.widget.tableview.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import com.didichuxing.doraemonkit.widget.tableview.TableConfig;
import com.didichuxing.doraemonkit.widget.tableview.TableMeasurer;
import com.didichuxing.doraemonkit.widget.tableview.TableParser;
import com.didichuxing.doraemonkit.widget.tableview.TableProvider;
import com.didichuxing.doraemonkit.widget.tableview.bean.Column;
import com.didichuxing.doraemonkit.widget.tableview.bean.TableData;
import com.didichuxing.doraemonkit.widget.tableview.intface.ISelectFormat;
import com.didichuxing.doraemonkit.widget.tableview.intface.ITableTitle;
import com.didichuxing.doraemonkit.widget.tableview.listener.OnColumnClickListener;
import com.didichuxing.doraemonkit.widget.tableview.listener.OnTableChangeListener;
import com.didichuxing.doraemonkit.widget.tableview.style.FontStyle;
import com.didichuxing.doraemonkit.widget.tableview.utils.MatrixHelper;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public class SmartTable<T> extends View implements OnTableChangeListener {
    private TableConfig config = TableConfig.getInstance();
    private int defaultHeight = IjkMediaCodecInfo.RANK_SECURE;
    private int defaultWidth = IjkMediaCodecInfo.RANK_SECURE;
    private boolean isExactly = true;
    /* access modifiers changed from: private */
    public AtomicBoolean isNotifying = new AtomicBoolean(false);
    private boolean isYSequenceRight;
    private MatrixHelper matrixHelper;
    /* access modifiers changed from: private */
    public TableMeasurer<T> measurer;
    protected Paint paint;
    /* access modifiers changed from: private */
    public TableParser<T> parser;
    private TableProvider<T> provider;
    private Rect showRect;
    /* access modifiers changed from: private */
    public TableData<T> tableData;
    private Rect tableRect;
    private ITableTitle tableTitle;
    /* access modifiers changed from: private */
    public YSequence<T> yAxis;

    public SmartTable(Context context) {
        super(context);
        init();
    }

    public SmartTable(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmartTable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        FontStyle.setDefaultTextSpSize(getContext(), 13);
        this.paint = new Paint(1);
        this.showRect = new Rect();
        this.tableRect = new Rect();
        this.yAxis = new YSequence<>();
        this.parser = new TableParser<>();
        this.provider = new TableProvider<>();
        this.config.setPaint(this.paint);
        this.measurer = new TableMeasurer<>();
        TableTitle tableTitle2 = new TableTitle();
        this.tableTitle = tableTitle2;
        tableTitle2.setDirection(1);
        MatrixHelper matrixHelper2 = new MatrixHelper(getContext());
        this.matrixHelper = matrixHelper2;
        matrixHelper2.setOnTableChangeListener(this);
        this.matrixHelper.register(this.provider);
        this.matrixHelper.setOnInterceptListener(this.provider.getOperation());
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Rect rect;
        if (!this.isNotifying.get()) {
            setScrollY(0);
            this.showRect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
            TableData<T> tableData2 = this.tableData;
            if (tableData2 != null && (rect = tableData2.getTableInfo().getTableRect()) != null) {
                if (this.config.isShowTableTitle()) {
                    this.measurer.measureTableTitle(this.tableData, this.tableTitle, this.showRect);
                }
                this.tableRect.set(rect);
                Rect scaleRect = this.matrixHelper.getZoomProviderRect(this.showRect, this.tableRect, this.tableData.getTableInfo());
                if (this.config.isShowTableTitle()) {
                    this.tableTitle.onMeasure(scaleRect, this.showRect, this.config);
                    this.tableTitle.onDraw(canvas, this.showRect, this.tableData.getTableName(), this.config);
                }
                if (this.config.isShowYSequence()) {
                    this.yAxis.onMeasure(scaleRect, this.showRect, this.config);
                    if (this.isYSequenceRight) {
                        canvas.save();
                        canvas.translate((float) this.showRect.width(), 0.0f);
                        this.yAxis.onDraw(canvas, this.showRect, this.tableData, this.config);
                        canvas.restore();
                    } else {
                        this.yAxis.onDraw(canvas, this.showRect, this.tableData, this.config);
                    }
                }
                if (this.isYSequenceRight) {
                    canvas.save();
                    canvas.translate((float) (-this.yAxis.getWidth()), 0.0f);
                    this.provider.onDraw(canvas, scaleRect, this.showRect, this.tableData);
                    canvas.restore();
                    return;
                }
                this.provider.onDraw(canvas, scaleRect, this.showRect, this.tableData);
            }
        }
    }

    private void drawGridBackground(Canvas canvas, Rect showRect2, Rect scaleRect) {
        this.config.contentGridStyle.fillPaint(this.paint);
        canvas.drawRect((float) Math.max(showRect2.left, scaleRect.left), (float) Math.max(showRect2.top, scaleRect.top), (float) Math.min(showRect2.right, scaleRect.right), (float) Math.min(scaleRect.bottom, showRect2.bottom), this.paint);
    }

    public TableConfig getConfig() {
        return this.config;
    }

    public void setTableData(TableData<T> tableData2) {
        if (tableData2 != null) {
            this.tableData = tableData2;
            notifyDataChanged();
        }
    }

    public ITableTitle getTableTitle() {
        return this.tableTitle;
    }

    public void notifyDataChanged() {
        if (this.tableData != null) {
            this.config.setPaint(this.paint);
            this.isNotifying.set(true);
            new Thread(new Runnable() {
                public void run() {
                    SmartTable.this.parser.parse(SmartTable.this.tableData);
                    SmartTable.this.yAxis.setWidth(SmartTable.this.measurer.measure(SmartTable.this.tableData, SmartTable.this.getWidth() - SmartTable.this.getPaddingRight(), SmartTable.this.getHeight() - SmartTable.this.getPaddingBottom()).getyAxisWidth());
                    SmartTable.this.requestReMeasure();
                    SmartTable.this.postInvalidate();
                    SmartTable.this.isNotifying.set(false);
                }
            }).start();
        }
    }

    public void addData(final List<T> t, final boolean isFoot) {
        if (t != null && t.size() > 0) {
            this.isNotifying.set(true);
            new Thread(new Runnable() {
                public void run() {
                    SmartTable.this.parser.addData(SmartTable.this.tableData, t, isFoot);
                    SmartTable.this.measurer.measure(SmartTable.this.tableData, SmartTable.this.getWidth() - SmartTable.this.getPaddingRight(), SmartTable.this.getHeight() - SmartTable.this.getPaddingBottom());
                    SmartTable.this.requestReMeasure();
                    SmartTable.this.postInvalidate();
                    SmartTable.this.isNotifying.set(false);
                }
            }).start();
        }
    }

    public void invalidate() {
        if (!this.isNotifying.get()) {
            super.invalidate();
        }
    }

    /* access modifiers changed from: private */
    public void requestReMeasure() {
        TableData<T> tableData2;
        if (!this.isExactly && getMeasuredHeight() != 0 && (tableData2 = this.tableData) != null && tableData2.getTableInfo().getTableRect() != null) {
            int defaultHeight2 = this.tableData.getTableInfo().getTableRect().height() + getPaddingTop();
            int defaultWidth2 = this.tableData.getTableInfo().getTableRect().width();
            int[] realSize = new int[2];
            getLocationInWindow(realSize);
            DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
            int screenWidth = dm.widthPixels;
            int defaultHeight3 = Math.min(defaultHeight2, dm.heightPixels - realSize[1]);
            int defaultWidth3 = Math.min(defaultWidth2, screenWidth - realSize[0]);
            if (this.defaultHeight != defaultHeight3 || this.defaultWidth != defaultWidth3) {
                this.defaultHeight = defaultHeight3;
                this.defaultWidth = defaultWidth3;
                post(new Runnable() {
                    public void run() {
                        SmartTable.this.requestLayout();
                    }
                });
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        requestReMeasure();
    }

    private int measureWidth(int widthMeasureSpec) {
        int specMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int specSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == 1073741824) {
            return specSize;
        }
        this.isExactly = false;
        int result = this.defaultWidth;
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(result, specSize);
        }
        return result;
    }

    private int measureHeight(int measureSpec) {
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        if (specMode == 1073741824) {
            return specSize;
        }
        this.isExactly = false;
        int result = this.defaultHeight;
        if (specMode == Integer.MIN_VALUE) {
            return Math.min(result, specSize);
        }
        return result;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return this.matrixHelper.handlerTouchEvent(event);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        this.matrixHelper.onDisallowInterceptEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    public void onTableChanged(float scale, float translateX, float translateY) {
        if (this.tableData != null) {
            this.config.setZoom(scale);
            this.tableData.getTableInfo().setZoom(scale);
            invalidate();
        }
    }

    public OnColumnClickListener getOnColumnClickListener() {
        return this.provider.getOnColumnClickListener();
    }

    public void setOnColumnClickListener(OnColumnClickListener onColumnClickListener) {
        this.provider.setOnColumnClickListener(onColumnClickListener);
    }

    public void setSortColumn(Column column, boolean isReverse) {
        TableData<T> tableData2 = this.tableData;
        if (tableData2 != null && column != null) {
            tableData2.setSortColumn(column);
            setTableData(this.tableData);
        }
    }

    public Rect getShowRect() {
        return this.showRect;
    }

    public TableProvider<T> getProvider() {
        return this.provider;
    }

    public TableData<T> getTableData() {
        return this.tableData;
    }

    public void setZoom(boolean zoom) {
        this.matrixHelper.setCanZoom(zoom);
        invalidate();
    }

    public void setZoom(boolean zoom, float maxZoom, float minZoom) {
        this.matrixHelper.setCanZoom(zoom);
        this.matrixHelper.setMinZoom(minZoom);
        this.matrixHelper.setMaxZoom(maxZoom);
        invalidate();
    }

    public MatrixHelper getMatrixHelper() {
        return this.matrixHelper;
    }

    public void setSelectFormat(ISelectFormat selectFormat) {
        this.provider.setSelectFormat(selectFormat);
    }

    public int computeHorizontalScrollRange() {
        int contentWidth = (getWidth() - getPaddingLeft()) - getPaddingRight();
        int scrollRange = this.matrixHelper.getZoomRect().right;
        int scrollX = -this.matrixHelper.getZoomRect().right;
        int overScrollRight = Math.max(0, scrollRange - contentWidth);
        if (scrollX < 0) {
            return scrollRange - scrollX;
        }
        if (scrollX > overScrollRight) {
            return scrollRange + (scrollX - overScrollRight);
        }
        return scrollRange;
    }

    public boolean canScrollVertically(int direction) {
        if (direction < 0) {
            if (this.matrixHelper.getZoomRect().top != 0) {
                return true;
            }
            return false;
        } else if (this.matrixHelper.getZoomRect().bottom > this.matrixHelper.getOriginalRect().bottom) {
            return true;
        } else {
            return false;
        }
    }

    public int computeHorizontalScrollOffset() {
        return Math.max(0, -this.matrixHelper.getZoomRect().top);
    }

    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    public int computeVerticalScrollRange() {
        int contentHeight = (getHeight() - getPaddingBottom()) - getPaddingTop();
        int scrollRange = this.matrixHelper.getZoomRect().bottom;
        int scrollY = -this.matrixHelper.getZoomRect().left;
        int overScrollBottom = Math.max(0, scrollRange - contentHeight);
        if (scrollY < 0) {
            return scrollRange - scrollY;
        }
        if (scrollY > overScrollBottom) {
            return scrollRange + (scrollY - overScrollBottom);
        }
        return scrollRange;
    }

    public int computeVerticalScrollOffset() {
        return Math.max(0, -this.matrixHelper.getZoomRect().left);
    }

    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    public YSequence getYSequence() {
        return this.yAxis;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.tableData != null && getContext() != null && ((Activity) getContext()).isFinishing()) {
            release();
        }
    }

    private void release() {
        this.matrixHelper.unRegisterAll();
        this.measurer = null;
        this.provider = null;
        this.matrixHelper = null;
        this.provider = null;
        TableData<T> tableData2 = this.tableData;
        if (tableData2 != null) {
            tableData2.clear();
            this.tableData = null;
        }
        this.yAxis = null;
    }

    public boolean isYSequenceRight() {
        return this.isYSequenceRight;
    }

    public void setYSequenceRight(boolean YSequenceRight) {
        this.isYSequenceRight = YSequenceRight;
    }
}
