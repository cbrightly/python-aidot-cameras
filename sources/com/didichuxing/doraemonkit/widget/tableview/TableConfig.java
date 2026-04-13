package com.didichuxing.doraemonkit.widget.tableview;

import android.graphics.Paint;
import com.didichuxing.doraemonkit.widget.tableview.style.FontStyle;
import com.didichuxing.doraemonkit.widget.tableview.style.LineStyle;

public class TableConfig {
    public static final int INVALID_COLOR = 0;
    private static final FontStyle defaultFontStyle = new FontStyle();
    private static final LineStyle defaultGridStyle = new LineStyle();
    private static TableConfig tableConfig;
    public LineStyle SequenceGridStyle;
    public FontStyle YSequenceStyle;
    public LineStyle columnTitleGridStyle;
    private int columnTitleHorizontalPadding = 40;
    public FontStyle columnTitleStyle;
    private int columnTitleVerticalPadding = 10;
    public LineStyle contentGridStyle;
    public FontStyle contentStyle;
    private boolean fixedCountRow = true;
    private boolean fixedTitle = false;
    private boolean fixedXSequence = false;
    private boolean fixedYSequence = false;
    private int horizontalPadding = 40;
    private boolean isShowColumnTitle = true;
    private boolean isShowTableTitle = true;
    private boolean isShowYSequence = true;
    private int leftAndTopBackgroundColor;
    private int minTableWidth = -1;
    private Paint paint;
    private int sequenceHorizontalPadding = 40;
    public FontStyle tableTitleStyle;
    private int textLeftOffset = 0;
    private int verticalPadding = 10;
    private float zoom = 1.0f;

    public static TableConfig getInstance() {
        if (tableConfig == null) {
            tableConfig = new TableConfig();
        }
        return tableConfig;
    }

    private TableConfig() {
        FontStyle fontStyle = defaultFontStyle;
        this.contentStyle = fontStyle;
        this.YSequenceStyle = fontStyle;
        this.columnTitleStyle = fontStyle;
        this.tableTitleStyle = fontStyle;
        LineStyle lineStyle = defaultGridStyle;
        this.columnTitleGridStyle = lineStyle;
        this.SequenceGridStyle = lineStyle;
        this.contentGridStyle = lineStyle;
    }

    public int getVerticalPadding() {
        return this.verticalPadding;
    }

    public TableConfig setVerticalPadding(int verticalPadding2) {
        this.verticalPadding = verticalPadding2;
        return this;
    }

    public int getHorizontalPadding() {
        return this.horizontalPadding;
    }

    public TableConfig setHorizontalPadding(int horizontalPadding2) {
        this.horizontalPadding = horizontalPadding2;
        return this;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public void setPaint(Paint paint2) {
        this.paint = paint2;
    }

    public boolean isFixedYSequence() {
        return this.fixedYSequence;
    }

    public TableConfig setFixedYSequence(boolean fixedYSequence2) {
        this.fixedYSequence = fixedYSequence2;
        return this;
    }

    public boolean isFixedXSequence() {
        return this.fixedXSequence;
    }

    public TableConfig setFixedXSequence(boolean fixedXSequence2) {
        this.fixedXSequence = fixedXSequence2;
        return this;
    }

    public boolean isFixedTitle() {
        return this.fixedTitle;
    }

    public boolean isFixedCountRow() {
        return this.fixedCountRow;
    }

    public TableConfig setFixedCountRow(boolean fixedCountRow2) {
        this.fixedCountRow = fixedCountRow2;
        return this;
    }

    public boolean isShowYSequence() {
        return this.isShowYSequence;
    }

    public TableConfig setShowYSequence(boolean showYSequence) {
        this.isShowYSequence = showYSequence;
        return this;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setZoom(float zoom2) {
        this.zoom = zoom2;
    }

    public int getColumnTitleHorizontalPadding() {
        return this.columnTitleHorizontalPadding;
    }

    public TableConfig setColumnTitleHorizontalPadding(int columnTitleHorizontalPadding2) {
        this.columnTitleHorizontalPadding = columnTitleHorizontalPadding2;
        return this;
    }

    public boolean isShowTableTitle() {
        return this.isShowTableTitle;
    }

    public TableConfig setShowTableTitle(boolean showTableTitle) {
        this.isShowTableTitle = showTableTitle;
        return this;
    }

    public boolean isShowColumnTitle() {
        return this.isShowColumnTitle;
    }

    public int getLeftAndTopBackgroundColor() {
        return this.leftAndTopBackgroundColor;
    }

    public TableConfig setLeftAndTopBackgroundColor(int leftAndTopBackgroundColor2) {
        this.leftAndTopBackgroundColor = leftAndTopBackgroundColor2;
        return this;
    }

    public TableConfig setShowColumnTitle(boolean showColumnTitle) {
        this.isShowColumnTitle = showColumnTitle;
        return this;
    }

    public TableConfig setMinTableWidth(int minTableWidth2) {
        this.minTableWidth = minTableWidth2;
        return this;
    }

    public int getMinTableWidth() {
        return this.minTableWidth;
    }

    public int getColumnTitleVerticalPadding() {
        return this.columnTitleVerticalPadding;
    }

    public TableConfig setColumnTitleVerticalPadding(int columnTitleVerticalPadding2) {
        this.columnTitleVerticalPadding = columnTitleVerticalPadding2;
        return this;
    }

    public int getSequenceHorizontalPadding() {
        return this.sequenceHorizontalPadding;
    }

    public TableConfig setSequenceHorizontalPadding(int sequenceHorizontalPadding2) {
        this.sequenceHorizontalPadding = sequenceHorizontalPadding2;
        return this;
    }

    public int getTextLeftOffset() {
        return this.textLeftOffset;
    }

    public TableConfig setTextLeftOffset(int textLeftOffset2) {
        this.textLeftOffset = textLeftOffset2;
        return this;
    }
}
