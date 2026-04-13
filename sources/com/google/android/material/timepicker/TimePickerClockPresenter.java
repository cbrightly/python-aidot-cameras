package com.google.android.material.timepicker;

import android.os.Build;
import android.view.accessibility.AccessibilityManager;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.material.R;
import com.google.android.material.timepicker.ClockHandView;
import com.google.android.material.timepicker.TimePickerView;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;

public class TimePickerClockPresenter implements ClockHandView.OnRotateListener, TimePickerView.OnSelectionChange, TimePickerView.OnPeriodChangeListener, ClockHandView.OnActionUpListener, TimePickerPresenter {
    private static final int DEGREES_PER_HOUR = 30;
    private static final int DEGREES_PER_MINUTE = 6;
    private static final String[] HOUR_CLOCK_24_VALUES = {"00", ExifInterface.GPS_MEASUREMENT_2D, "4", "6", "8", "10", "12", "14", "16", "18", "20", "22"};
    private static final String[] HOUR_CLOCK_VALUES = {"12", "1", ExifInterface.GPS_MEASUREMENT_2D, ExifInterface.GPS_MEASUREMENT_3D, "4", "5", "6", "7", "8", DbParams.GZIP_DATA_ENCRYPT, "10", "11"};
    private static final String[] MINUTE_CLOCK_VALUES = {"00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
    private boolean broadcasting = false;
    private float hourRotation;
    private float minuteRotation;
    private TimeModel time;
    private TimePickerView timePickerView;

    public TimePickerClockPresenter(TimePickerView timePickerView2, TimeModel time2) {
        this.timePickerView = timePickerView2;
        this.time = time2;
        initialize();
    }

    public void initialize() {
        if (this.time.format == 0) {
            this.timePickerView.showToggle();
        }
        this.timePickerView.addOnRotateListener(this);
        this.timePickerView.setOnSelectionChangeListener(this);
        this.timePickerView.setOnPeriodChangeListener(this);
        this.timePickerView.setOnActionUpListener(this);
        updateValues();
        invalidate();
    }

    public void invalidate() {
        this.hourRotation = (float) (getDegreesPerHour() * this.time.getHourForDisplay());
        TimeModel timeModel = this.time;
        this.minuteRotation = (float) (timeModel.minute * 6);
        setSelection(timeModel.selection, false);
        updateTime();
    }

    public void show() {
        this.timePickerView.setVisibility(0);
    }

    public void hide() {
        this.timePickerView.setVisibility(8);
    }

    private String[] getHourClockValues() {
        return this.time.format == 1 ? HOUR_CLOCK_24_VALUES : HOUR_CLOCK_VALUES;
    }

    private int getDegreesPerHour() {
        return this.time.format == 1 ? 15 : 30;
    }

    public void onRotate(float rotation, boolean animating) {
        if (!this.broadcasting) {
            TimeModel timeModel = this.time;
            int prevHour = timeModel.hour;
            int prevMinute = timeModel.minute;
            int rotationInt = Math.round(rotation);
            TimeModel timeModel2 = this.time;
            if (timeModel2.selection == 12) {
                timeModel2.setMinute((rotationInt + 3) / 6);
                this.minuteRotation = (float) Math.floor((double) (this.time.minute * 6));
            } else {
                this.time.setHour((rotationInt + (getDegreesPerHour() / 2)) / getDegreesPerHour());
                this.hourRotation = (float) (this.time.getHourForDisplay() * getDegreesPerHour());
            }
            if (!animating) {
                updateTime();
                performHapticFeedback(prevHour, prevMinute);
            }
        }
    }

    private void performHapticFeedback(int prevHour, int prevMinute) {
        TimeModel timeModel = this.time;
        if (timeModel.minute != prevMinute || timeModel.hour != prevHour) {
            this.timePickerView.performHapticFeedback(Build.VERSION.SDK_INT >= 21 ? 4 : 1);
        }
    }

    public void onSelectionChanged(int selection) {
        setSelection(selection, true);
    }

    public void onPeriodChange(int period) {
        this.time.setPeriod(period);
    }

    /* access modifiers changed from: package-private */
    public void setSelection(int selection, boolean animate) {
        String[] strArr;
        boolean isMinute = selection == 12;
        this.timePickerView.setAnimateOnTouchUp(isMinute);
        this.time.selection = selection;
        TimePickerView timePickerView2 = this.timePickerView;
        if (isMinute) {
            strArr = MINUTE_CLOCK_VALUES;
        } else {
            strArr = getHourClockValues();
        }
        timePickerView2.setValues(strArr, isMinute ? R.string.material_minute_suffix : R.string.material_hour_suffix);
        this.timePickerView.setHandRotation(isMinute ? this.minuteRotation : this.hourRotation, animate);
        this.timePickerView.setActiveSelection(selection);
        this.timePickerView.setMinuteHourDelegate(new ClickActionDelegate(this.timePickerView.getContext(), R.string.material_hour_selection));
        this.timePickerView.setHourClickDelegate(new ClickActionDelegate(this.timePickerView.getContext(), R.string.material_minute_selection));
    }

    public void onActionUp(float rotation, boolean moveInEventStream) {
        this.broadcasting = true;
        TimeModel timeModel = this.time;
        int prevMinute = timeModel.minute;
        int prevHour = timeModel.hour;
        if (timeModel.selection == 10) {
            this.timePickerView.setHandRotation(this.hourRotation, false);
            if (!((AccessibilityManager) ContextCompat.getSystemService(this.timePickerView.getContext(), AccessibilityManager.class)).isTouchExplorationEnabled()) {
                setSelection(12, true);
            }
        } else {
            int rotationInt = Math.round(rotation);
            if (!moveInEventStream) {
                this.time.setMinute(((rotationInt + 15) / 30) * 5);
                this.minuteRotation = (float) (this.time.minute * 6);
            }
            this.timePickerView.setHandRotation(this.minuteRotation, moveInEventStream);
        }
        this.broadcasting = false;
        updateTime();
        performHapticFeedback(prevHour, prevMinute);
    }

    private void updateTime() {
        TimePickerView timePickerView2 = this.timePickerView;
        TimeModel timeModel = this.time;
        timePickerView2.updateTime(timeModel.period, timeModel.getHourForDisplay(), this.time.minute);
    }

    private void updateValues() {
        updateValues(HOUR_CLOCK_VALUES, TimeModel.NUMBER_FORMAT);
        updateValues(HOUR_CLOCK_24_VALUES, TimeModel.NUMBER_FORMAT);
        updateValues(MINUTE_CLOCK_VALUES, TimeModel.ZERO_LEADING_NUMBER_FORMAT);
    }

    private void updateValues(String[] values, String format) {
        for (int i = 0; i < values.length; i++) {
            values[i] = TimeModel.formatText(this.timePickerView.getResources(), values[i], format);
        }
    }
}
