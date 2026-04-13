package androidx.databinding.adapters;

import android.widget.CalendarView;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;

@InverseBindingMethods({@InverseBindingMethod(attribute = "android:date", type = CalendarView.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
public class CalendarViewBindingAdapter {
    @BindingAdapter({"android:date"})
    public static void setDate(CalendarView view, long date) {
        if (view.getDate() != date) {
            view.setDate(date);
        }
    }

    @BindingAdapter(requireAll = false, value = {"android:onSelectedDayChange", "android:dateAttrChanged"})
    public static void setListeners(CalendarView view, final CalendarView.OnDateChangeListener onDayChange, final InverseBindingListener attrChange) {
        if (attrChange == null) {
            view.setOnDateChangeListener(onDayChange);
        } else {
            view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    CalendarView.OnDateChangeListener onDateChangeListener = onDayChange;
                    if (onDateChangeListener != null) {
                        onDateChangeListener.onSelectedDayChange(view, year, month, dayOfMonth);
                    }
                    attrChange.onChange();
                }
            });
        }
    }
}
