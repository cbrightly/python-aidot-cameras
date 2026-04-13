package com.didichuxing.doraemonkit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.didichuxing.doraemonkit.R;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiLineRadioGroup extends RadioGroup {
    private static final int DEF_VAL_MAX_IN_ROW = 0;
    private static final String XML_DEFAULT_BUTTON_PREFIX_INDEX = "index:";
    private static final String XML_DEFAULT_BUTTON_PREFIX_TEXT = "text:";
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    /* access modifiers changed from: private */
    public RadioButton mCheckedButton;
    private int mMaxInRow;
    /* access modifiers changed from: private */
    public OnCheckedChangeListener mOnCheckedChangeListener;
    /* access modifiers changed from: private */
    public OnClickListener mOnClickListener;
    private List<RadioButton> mRadioButtons;
    private TableLayout mTableLayout;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(ViewGroup viewGroup, RadioButton radioButton);
    }

    public interface OnClickListener {
        void onClick(ViewGroup viewGroup, RadioButton radioButton);
    }

    public MultiLineRadioGroup(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public MultiLineRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.mRadioButtons = new ArrayList();
        TableLayout tableLayout = getTableLayout();
        this.mTableLayout = tableLayout;
        addView(tableLayout);
        if (attrs != null) {
            initAttrs(attrs);
        }
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.dk_multi_line_radio_group, 0, 0);
        try {
            setMaxInRow(typedArray.getInt(R.styleable.dk_multi_line_radio_group_max_in_row, 0));
            addButtons(typedArray.getTextArray(R.styleable.dk_multi_line_radio_group_radio_buttons));
            String string = typedArray.getString(R.styleable.dk_multi_line_radio_group_default_button);
            if (string != null) {
                setDefaultButton(string);
            }
        } finally {
            typedArray.recycle();
        }
    }

    private void setDefaultButton(String string) {
        String indexString;
        int START_OF_INDEX = XML_DEFAULT_BUTTON_PREFIX_INDEX.length();
        int START_OF_TEXT = XML_DEFAULT_BUTTON_PREFIX_TEXT.length();
        if (string.startsWith(XML_DEFAULT_BUTTON_PREFIX_INDEX)) {
            int index = Integer.parseInt(string.substring(START_OF_INDEX));
            if (index < 0 || index >= this.mRadioButtons.size()) {
                StringBuilder sb = new StringBuilder();
                sb.append("index must be between 0 to getRadioButtonCount() - 1 [");
                sb.append(getRadioButtonCount() - 1);
                sb.append("]: ");
                sb.append(index);
                throw new IllegalArgumentException(sb.toString());
            }
            indexString = this.mRadioButtons.get(index).getText().toString();
        } else if (string.startsWith(XML_DEFAULT_BUTTON_PREFIX_TEXT)) {
            indexString = string.substring(START_OF_TEXT);
        } else {
            indexString = string;
        }
        check((CharSequence) indexString);
    }

    /* access modifiers changed from: protected */
    public TableLayout getTableLayout() {
        return (TableLayout) LayoutInflater.from(getContext()).inflate(R.layout.dk_radio_table_layout, this, false);
    }

    /* access modifiers changed from: protected */
    public TableRow getTableRow() {
        return (TableRow) LayoutInflater.from(getContext()).inflate(R.layout.dk_radio_table_row, this.mTableLayout, false);
    }

    /* access modifiers changed from: protected */
    public RadioButton getRadioButton() {
        return (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.dk_radio_button, (ViewGroup) null);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public int getMaxInRow() {
        return this.mMaxInRow;
    }

    public void setMaxInRow(int maxInRow) {
        if (maxInRow >= 0) {
            this.mMaxInRow = maxInRow;
            arrangeButtons();
            return;
        }
        throw new IllegalArgumentException("maxInRow must not be negative: " + maxInRow);
    }

    public void addView(View child) {
        addView(child, -1, child.getLayoutParams());
    }

    public void addView(View child, int index) {
        addView(child, index, child.getLayoutParams());
    }

    public void addView(View child, int width, int height) {
        addView(child, -1, (ViewGroup.LayoutParams) new LinearLayout.LayoutParams(width, height));
    }

    public void addView(View child, ViewGroup.LayoutParams params) {
        addView(child, -1, params);
    }

    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof RadioButton) {
            addButtons(index, (RadioButton) child);
            return;
        }
        if (params == null) {
            params = new ViewGroup.LayoutParams(-1, -1);
        }
        super.addView(child, index, params);
    }

    public void addButtons(CharSequence... radioButtons) {
        addButtons(-1, radioButtons);
    }

    public void addButtons(int index, CharSequence... radioButtons) {
        if (index < -1 || index > this.mRadioButtons.size()) {
            throw new IllegalArgumentException("index must be between -1 to getRadioButtonCount() [" + getRadioButtonCount() + "]: " + index);
        } else if (radioButtons != null) {
            RadioButton[] buttons = new RadioButton[radioButtons.length];
            for (int i = 0; i < buttons.length; i++) {
                RadioButton radioButton = getRadioButton();
                radioButton.setText(radioButtons[i]);
                radioButton.setId(generateId());
                buttons[i] = radioButton;
            }
            addButtons(index, buttons);
        }
    }

    private int generateId() {
        AtomicInteger atomicInteger;
        int result;
        int newValue;
        if (Build.VERSION.SDK_INT >= 17) {
            return View.generateViewId();
        }
        do {
            atomicInteger = sNextGeneratedId;
            result = atomicInteger.get();
            newValue = result + 1;
            if (newValue > 16777215) {
                newValue = 1;
            }
        } while (!atomicInteger.compareAndSet(result, newValue));
        return result;
    }

    public void addButtons(RadioButton... radioButtons) {
        addButtons(-1, radioButtons);
    }

    public void addButtons(int index, RadioButton... radioButtons) {
        if (index < -1 || index > this.mRadioButtons.size()) {
            throw new IllegalArgumentException("index must be between -1 to getRadioButtonCount() [" + getRadioButtonCount() + "]: " + index);
        } else if (radioButtons != null) {
            int realIndex = index != -1 ? index : this.mRadioButtons.size();
            int length = radioButtons.length;
            int i = 0;
            while (i < length) {
                RadioButton radioButton = radioButtons[i];
                initRadioButton(radioButton);
                this.mRadioButtons.add(realIndex, radioButton);
                i++;
                realIndex++;
            }
            arrangeButtons();
        }
    }

    private void initRadioButton(RadioButton radioButton) {
        radioButton.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View v) {
                if (MultiLineRadioGroup.this.checkButton((RadioButton) v) && MultiLineRadioGroup.this.mOnCheckedChangeListener != null) {
                    OnCheckedChangeListener access$100 = MultiLineRadioGroup.this.mOnCheckedChangeListener;
                    MultiLineRadioGroup multiLineRadioGroup = MultiLineRadioGroup.this;
                    access$100.onCheckedChanged(multiLineRadioGroup, multiLineRadioGroup.mCheckedButton);
                }
                if (MultiLineRadioGroup.this.mOnClickListener != null) {
                    OnClickListener access$300 = MultiLineRadioGroup.this.mOnClickListener;
                    MultiLineRadioGroup multiLineRadioGroup2 = MultiLineRadioGroup.this;
                    access$300.onClick(multiLineRadioGroup2, multiLineRadioGroup2.mCheckedButton);
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
            }
        });
    }

    public void removeView(View view) {
        super.removeView(view);
    }

    public void removeViewAt(int index) {
        super.removeViewAt(index);
    }

    public void removeViews(int start, int count) {
        super.removeViews(start, count);
    }

    public void removeAllViews() {
        super.removeAllViews();
    }

    public void removeButton(RadioButton radioButton) {
        if (radioButton != null) {
            removeButton(radioButton.getText());
        }
    }

    public void removeButton(CharSequence text) {
        if (text != null) {
            int index = -1;
            int i = 0;
            int len = this.mRadioButtons.size();
            while (true) {
                if (i >= len) {
                    break;
                } else if (this.mRadioButtons.get(i).getText().equals(text)) {
                    index = i;
                    break;
                } else {
                    i++;
                }
            }
            if (index != -1) {
                removeButton(index);
            }
        }
    }

    public void removeButton(int index) {
        removeButtons(index, 1);
    }

    public void removeButtons(int start, int count) {
        if (count != 0) {
            if (start < 0 || start >= this.mRadioButtons.size()) {
                StringBuilder sb = new StringBuilder();
                sb.append("start index must be between 0 to getRadioButtonCount() - 1 [");
                sb.append(getRadioButtonCount() - 1);
                sb.append("]: ");
                sb.append(start);
                throw new IllegalArgumentException(sb.toString());
            } else if (count >= 0) {
                int endIndex = (start + count) - 1;
                if (endIndex >= this.mRadioButtons.size()) {
                    endIndex = this.mRadioButtons.size() - 1;
                }
                for (int i = endIndex; i >= start; i--) {
                    if (this.mRadioButtons.get(i) == this.mCheckedButton) {
                        this.mCheckedButton = null;
                    }
                    this.mRadioButtons.remove(i);
                }
                arrangeButtons();
            } else {
                throw new IllegalArgumentException("count must not be negative: " + count);
            }
        }
    }

    public void removeAllButtons() {
        removeButtons(0, this.mRadioButtons.size());
    }

    private void arrangeButtons() {
        int len = this.mRadioButtons.size();
        for (int i = 0; i < len; i++) {
            RadioButton radioButtonToPlace = this.mRadioButtons.get(i);
            int i2 = this.mMaxInRow;
            int rowToInsert = i2 != 0 ? i / i2 : 0;
            int columnToInsert = i2 != 0 ? i % i2 : i;
            TableRow tableRowToInsert = this.mTableLayout.getChildCount() <= rowToInsert ? addTableRow() : (TableRow) this.mTableLayout.getChildAt(rowToInsert);
            if (tableRowToInsert.getChildCount() > columnToInsert) {
                RadioButton currentButton = (RadioButton) tableRowToInsert.getChildAt(columnToInsert);
                if (currentButton != radioButtonToPlace) {
                    removeButtonFromParent(currentButton, tableRowToInsert);
                    removeButtonFromParent(radioButtonToPlace, (ViewGroup) radioButtonToPlace.getParent());
                    tableRowToInsert.addView(radioButtonToPlace, columnToInsert);
                }
            } else {
                removeButtonFromParent(radioButtonToPlace, (ViewGroup) radioButtonToPlace.getParent());
                tableRowToInsert.addView(radioButtonToPlace, columnToInsert);
            }
        }
        removeRedundancies();
    }

    private void removeRedundancies() {
        int rows;
        int count;
        int startIndexToRemove;
        if (this.mRadioButtons.size() == 0) {
            rows = 0;
        } else if (this.mMaxInRow == 0) {
            rows = 1;
        } else {
            rows = ((this.mRadioButtons.size() - 1) / this.mMaxInRow) + 1;
        }
        int tableChildCount = this.mTableLayout.getChildCount();
        if (tableChildCount > rows) {
            this.mTableLayout.removeViews(rows, tableChildCount - rows);
        }
        int tableChildCount2 = this.mTableLayout.getChildCount();
        int maxInRow = this.mMaxInRow;
        if (maxInRow == 0) {
            maxInRow = this.mRadioButtons.size();
        }
        for (int i = 0; i < tableChildCount2; i++) {
            TableRow tableRow = (TableRow) this.mTableLayout.getChildAt(i);
            int tableRowChildCount = tableRow.getChildCount();
            if (i == tableChildCount2 - 1) {
                startIndexToRemove = ((this.mRadioButtons.size() - 1) % maxInRow) + 1;
                count = tableRowChildCount - startIndexToRemove;
            } else {
                startIndexToRemove = maxInRow;
                count = tableRowChildCount - maxInRow;
            }
            if (count > 0) {
                tableRow.removeViews(startIndexToRemove, count);
            }
        }
    }

    private TableRow addTableRow() {
        TableRow tableRow = getTableRow();
        this.mTableLayout.addView(tableRow);
        return tableRow;
    }

    private void removeButtonFromParent(RadioButton radioButton, ViewGroup parent) {
        if (radioButton != null && parent != null) {
            parent.removeView(radioButton);
        }
    }

    public int getRadioButtonCount() {
        return this.mRadioButtons.size();
    }

    public RadioButton getRadioButtonAt(int index) {
        if (index < 0 || index >= this.mRadioButtons.size()) {
            return null;
        }
        return this.mRadioButtons.get(index);
    }

    public boolean containsButton(String button) {
        for (RadioButton radioButton : this.mRadioButtons) {
            if (radioButton.getText().equals(button)) {
                return true;
            }
        }
        return false;
    }

    public void check(int id) {
        OnCheckedChangeListener onCheckedChangeListener;
        if (id > 0) {
            for (RadioButton radioButton : this.mRadioButtons) {
                if (radioButton.getId() == id) {
                    if (checkButton(radioButton) && (onCheckedChangeListener = this.mOnCheckedChangeListener) != null) {
                        onCheckedChangeListener.onCheckedChanged(this, radioButton);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public void check(CharSequence text) {
        OnCheckedChangeListener onCheckedChangeListener;
        if (text != null) {
            for (RadioButton radioButton : this.mRadioButtons) {
                if (radioButton.getText().equals(text)) {
                    if (checkButton(radioButton) && (onCheckedChangeListener = this.mOnCheckedChangeListener) != null) {
                        onCheckedChangeListener.onCheckedChanged(this, radioButton);
                        return;
                    }
                    return;
                }
            }
        }
    }

    public void checkAt(int index) {
        OnCheckedChangeListener onCheckedChangeListener;
        if (index >= 0 && index < this.mRadioButtons.size() && checkButton(this.mRadioButtons.get(index)) && (onCheckedChangeListener = this.mOnCheckedChangeListener) != null) {
            onCheckedChangeListener.onCheckedChanged(this, this.mRadioButtons.get(index));
        }
    }

    /* access modifiers changed from: private */
    public boolean checkButton(RadioButton button) {
        RadioButton radioButton;
        if (button == null || button == (radioButton = this.mCheckedButton)) {
            return false;
        }
        if (radioButton != null) {
            radioButton.setChecked(false);
        }
        button.setChecked(true);
        this.mCheckedButton = button;
        return true;
    }

    public void clearCheck() {
        RadioButton radioButton = this.mCheckedButton;
        if (radioButton != null) {
            radioButton.setChecked(false);
            OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, this.mCheckedButton);
            }
        }
        this.mCheckedButton = null;
    }

    public int getCheckedRadioButtonId() {
        RadioButton radioButton = this.mCheckedButton;
        if (radioButton == null) {
            return -1;
        }
        return radioButton.getId();
    }

    public int getCheckedRadioButtonIndex() {
        RadioButton radioButton = this.mCheckedButton;
        if (radioButton == null) {
            return -1;
        }
        return this.mRadioButtons.indexOf(radioButton);
    }

    public CharSequence getCheckedRadioButtonText() {
        RadioButton radioButton = this.mCheckedButton;
        if (radioButton == null) {
            return null;
        }
        return radioButton.getText();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mMaxInRow = this.mMaxInRow;
        savedState.mCheckedButtonIndex = getCheckedRadioButtonIndex();
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setMaxInRow(savedState.mMaxInRow);
        checkAt(savedState.mCheckedButtonIndex);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int mCheckedButtonIndex;
        int mMaxInRow;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            this.mMaxInRow = in.readInt();
            this.mCheckedButtonIndex = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.mMaxInRow);
            out.writeInt(this.mCheckedButtonIndex);
        }
    }
}
