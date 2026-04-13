package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import androidx.annotation.NonNull;
import com.google.android.material.R;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;

public final class CalendarStyle {
    @NonNull
    final CalendarItemStyle day;
    @NonNull
    final CalendarItemStyle invalidDay;
    @NonNull
    final Paint rangeFill;
    @NonNull
    final CalendarItemStyle selectedDay;
    @NonNull
    final CalendarItemStyle selectedYear;
    @NonNull
    final CalendarItemStyle todayDay;
    @NonNull
    final CalendarItemStyle todayYear;
    @NonNull
    final CalendarItemStyle year;

    CalendarStyle(@NonNull Context context) {
        TypedArray calendarAttributes = context.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(context, R.attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), R.styleable.MaterialCalendar);
        this.day = CalendarItemStyle.create(context, calendarAttributes.getResourceId(R.styleable.MaterialCalendar_dayStyle, 0));
        this.invalidDay = CalendarItemStyle.create(context, calendarAttributes.getResourceId(R.styleable.MaterialCalendar_dayInvalidStyle, 0));
        this.selectedDay = CalendarItemStyle.create(context, calendarAttributes.getResourceId(R.styleable.MaterialCalendar_daySelectedStyle, 0));
        this.todayDay = CalendarItemStyle.create(context, calendarAttributes.getResourceId(R.styleable.MaterialCalendar_dayTodayStyle, 0));
        ColorStateList rangeFillColorList = MaterialResources.getColorStateList(context, calendarAttributes, R.styleable.MaterialCalendar_rangeFillColor);
        this.year = CalendarItemStyle.create(context, calendarAttributes.getResourceId(R.styleable.MaterialCalendar_yearStyle, 0));
        this.selectedYear = CalendarItemStyle.create(context, calendarAttributes.getResourceId(R.styleable.MaterialCalendar_yearSelectedStyle, 0));
        this.todayYear = CalendarItemStyle.create(context, calendarAttributes.getResourceId(R.styleable.MaterialCalendar_yearTodayStyle, 0));
        Paint paint = new Paint();
        this.rangeFill = paint;
        paint.setColor(rangeFillColorList.getDefaultColor());
        calendarAttributes.recycle();
    }
}
