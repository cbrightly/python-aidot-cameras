package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Pair;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class SingleDateSelector implements DateSelector<Long> {
    public static final Parcelable.Creator<SingleDateSelector> CREATOR = new Parcelable.Creator<SingleDateSelector>() {
        @NonNull
        public SingleDateSelector createFromParcel(@NonNull Parcel source) {
            SingleDateSelector singleDateSelector = new SingleDateSelector();
            Long unused = singleDateSelector.selectedItem = (Long) source.readValue(Long.class.getClassLoader());
            return singleDateSelector;
        }

        @NonNull
        public SingleDateSelector[] newArray(int size) {
            return new SingleDateSelector[size];
        }
    };
    /* access modifiers changed from: private */
    @Nullable
    public Long selectedItem;

    public void select(long selection) {
        this.selectedItem = Long.valueOf(selection);
    }

    /* access modifiers changed from: private */
    public void clearSelection() {
        this.selectedItem = null;
    }

    public void setSelection(@Nullable Long selection) {
        this.selectedItem = selection == null ? null : Long.valueOf(UtcDates.canonicalYearMonthDay(selection.longValue()));
    }

    public boolean isSelectionComplete() {
        return this.selectedItem != null;
    }

    @NonNull
    public Collection<Pair<Long, Long>> getSelectedRanges() {
        return new ArrayList();
    }

    @NonNull
    public Collection<Long> getSelectedDays() {
        ArrayList<Long> selections = new ArrayList<>();
        Long l = this.selectedItem;
        if (l != null) {
            selections.add(l);
        }
        return selections;
    }

    @Nullable
    public Long getSelection() {
        return this.selectedItem;
    }

    public View onCreateTextInputView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle, CalendarConstraints constraints, @NonNull OnSelectionChangedListener<Long> listener) {
        View root = layoutInflater.inflate(R.layout.mtrl_picker_text_input_date, viewGroup, false);
        TextInputLayout dateTextInput = (TextInputLayout) root.findViewById(R.id.mtrl_picker_text_input_date);
        EditText dateEditText = dateTextInput.getEditText();
        if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
            dateEditText.setInputType(17);
        }
        SimpleDateFormat format = UtcDates.getTextInputFormat();
        String formatHint = UtcDates.getTextInputHint(root.getResources(), format);
        dateTextInput.setPlaceholderText(formatHint);
        Long l = this.selectedItem;
        if (l != null) {
            dateEditText.setText(format.format(l));
        }
        final OnSelectionChangedListener<Long> onSelectionChangedListener = listener;
        dateEditText.addTextChangedListener(new DateFormatTextWatcher(formatHint, format, dateTextInput, constraints) {
            /* access modifiers changed from: package-private */
            public void onValidDate(@Nullable Long day) {
                if (day == null) {
                    SingleDateSelector.this.clearSelection();
                } else {
                    SingleDateSelector.this.select(day.longValue());
                }
                onSelectionChangedListener.onSelectionChanged(SingleDateSelector.this.getSelection());
            }

            /* access modifiers changed from: package-private */
            public void onInvalidDate() {
                onSelectionChangedListener.onIncompleteSelectionChanged();
            }
        });
        ViewUtils.requestFocusAndShowKeyboard(dateEditText);
        return root;
    }

    public int getDefaultThemeResId(Context context) {
        return MaterialAttributes.resolveOrThrow(context, R.attr.materialCalendarTheme, MaterialDatePicker.class.getCanonicalName());
    }

    @NonNull
    public String getSelectionDisplayString(@NonNull Context context) {
        Resources res = context.getResources();
        Long l = this.selectedItem;
        if (l == null) {
            return res.getString(R.string.mtrl_picker_date_header_unselected);
        }
        String startString = DateStrings.getYearMonthDay(l.longValue());
        return res.getString(R.string.mtrl_picker_date_header_selected, new Object[]{startString});
    }

    public int getDefaultTitleResId() {
        return R.string.mtrl_picker_date_header_title;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeValue(this.selectedItem);
    }
}
