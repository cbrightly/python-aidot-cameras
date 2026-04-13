package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.util.Pair;
import androidx.core.util.Preconditions;
import com.google.android.material.R;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public class RangeDateSelector implements DateSelector<Pair<Long, Long>> {
    public static final Parcelable.Creator<RangeDateSelector> CREATOR = new Parcelable.Creator<RangeDateSelector>() {
        @NonNull
        public RangeDateSelector createFromParcel(@NonNull Parcel source) {
            Class<Long> cls = Long.class;
            RangeDateSelector rangeDateSelector = new RangeDateSelector();
            Long unused = rangeDateSelector.selectedStartItem = (Long) source.readValue(cls.getClassLoader());
            Long unused2 = rangeDateSelector.selectedEndItem = (Long) source.readValue(cls.getClassLoader());
            return rangeDateSelector;
        }

        @NonNull
        public RangeDateSelector[] newArray(int size) {
            return new RangeDateSelector[size];
        }
    };
    private final String invalidRangeEndError = " ";
    private String invalidRangeStartError;
    /* access modifiers changed from: private */
    @Nullable
    public Long proposedTextEnd = null;
    /* access modifiers changed from: private */
    @Nullable
    public Long proposedTextStart = null;
    /* access modifiers changed from: private */
    @Nullable
    public Long selectedEndItem = null;
    /* access modifiers changed from: private */
    @Nullable
    public Long selectedStartItem = null;

    public void select(long selection) {
        Long l = this.selectedStartItem;
        if (l == null) {
            this.selectedStartItem = Long.valueOf(selection);
        } else if (this.selectedEndItem != null || !isValidRange(l.longValue(), selection)) {
            this.selectedEndItem = null;
            this.selectedStartItem = Long.valueOf(selection);
        } else {
            this.selectedEndItem = Long.valueOf(selection);
        }
    }

    public boolean isSelectionComplete() {
        Long l = this.selectedStartItem;
        return (l == null || this.selectedEndItem == null || !isValidRange(l.longValue(), this.selectedEndItem.longValue())) ? false : true;
    }

    public void setSelection(@NonNull Pair<Long, Long> selection) {
        Long l;
        F f = selection.first;
        if (!(f == null || selection.second == null)) {
            Preconditions.checkArgument(isValidRange(((Long) f).longValue(), ((Long) selection.second).longValue()));
        }
        F f2 = selection.first;
        Long l2 = null;
        if (f2 == null) {
            l = null;
        } else {
            l = Long.valueOf(UtcDates.canonicalYearMonthDay(((Long) f2).longValue()));
        }
        this.selectedStartItem = l;
        S s = selection.second;
        if (s != null) {
            l2 = Long.valueOf(UtcDates.canonicalYearMonthDay(((Long) s).longValue()));
        }
        this.selectedEndItem = l2;
    }

    @NonNull
    public Pair<Long, Long> getSelection() {
        return new Pair<>(this.selectedStartItem, this.selectedEndItem);
    }

    @NonNull
    public Collection<Pair<Long, Long>> getSelectedRanges() {
        if (this.selectedStartItem == null || this.selectedEndItem == null) {
            return new ArrayList();
        }
        ArrayList<Pair<Long, Long>> ranges = new ArrayList<>();
        ranges.add(new Pair<>(this.selectedStartItem, this.selectedEndItem));
        return ranges;
    }

    @NonNull
    public Collection<Long> getSelectedDays() {
        ArrayList<Long> selections = new ArrayList<>();
        Long l = this.selectedStartItem;
        if (l != null) {
            selections.add(l);
        }
        Long l2 = this.selectedEndItem;
        if (l2 != null) {
            selections.add(l2);
        }
        return selections;
    }

    public int getDefaultThemeResId(@NonNull Context context) {
        Resources res = context.getResources();
        DisplayMetrics display = res.getDisplayMetrics();
        return MaterialAttributes.resolveOrThrow(context, Math.min(display.widthPixels, display.heightPixels) > res.getDimensionPixelSize(R.dimen.mtrl_calendar_maximum_default_fullscreen_minor_axis) ? R.attr.materialCalendarTheme : R.attr.materialCalendarFullscreenTheme, MaterialDatePicker.class.getCanonicalName());
    }

    @NonNull
    public String getSelectionDisplayString(@NonNull Context context) {
        Resources res = context.getResources();
        Long l = this.selectedStartItem;
        if (l == null && this.selectedEndItem == null) {
            return res.getString(R.string.mtrl_picker_range_header_unselected);
        }
        Long l2 = this.selectedEndItem;
        if (l2 == null) {
            return res.getString(R.string.mtrl_picker_range_header_only_start_selected, new Object[]{DateStrings.getDateString(l.longValue())});
        } else if (l == null) {
            return res.getString(R.string.mtrl_picker_range_header_only_end_selected, new Object[]{DateStrings.getDateString(l2.longValue())});
        } else {
            Pair<String, String> dateRangeStrings = DateStrings.getDateRangeString(l, l2);
            return res.getString(R.string.mtrl_picker_range_header_selected, new Object[]{dateRangeStrings.first, dateRangeStrings.second});
        }
    }

    public int getDefaultTitleResId() {
        return R.string.mtrl_picker_range_header_title;
    }

    public View onCreateTextInputView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle, CalendarConstraints constraints, @NonNull OnSelectionChangedListener<Pair<Long, Long>> listener) {
        View root = layoutInflater.inflate(R.layout.mtrl_picker_text_input_date_range, viewGroup, false);
        TextInputLayout startTextInput = (TextInputLayout) root.findViewById(R.id.mtrl_picker_text_input_range_start);
        TextInputLayout endTextInput = (TextInputLayout) root.findViewById(R.id.mtrl_picker_text_input_range_end);
        EditText startEditText = startTextInput.getEditText();
        EditText endEditText = endTextInput.getEditText();
        if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
            startEditText.setInputType(17);
            endEditText.setInputType(17);
        }
        this.invalidRangeStartError = root.getResources().getString(R.string.mtrl_picker_invalid_range);
        SimpleDateFormat format = UtcDates.getTextInputFormat();
        Long l = this.selectedStartItem;
        if (l != null) {
            startEditText.setText(format.format(l));
            this.proposedTextStart = this.selectedStartItem;
        }
        Long l2 = this.selectedEndItem;
        if (l2 != null) {
            endEditText.setText(format.format(l2));
            this.proposedTextEnd = this.selectedEndItem;
        }
        String formatHint = UtcDates.getTextInputHint(root.getResources(), format);
        startTextInput.setPlaceholderText(formatHint);
        endTextInput.setPlaceholderText(formatHint);
        AnonymousClass1 r9 = r0;
        CalendarConstraints calendarConstraints = constraints;
        String formatHint2 = formatHint;
        final TextInputLayout textInputLayout = startTextInput;
        SimpleDateFormat format2 = format;
        final TextInputLayout textInputLayout2 = endTextInput;
        EditText endEditText2 = endEditText;
        final OnSelectionChangedListener<Pair<Long, Long>> onSelectionChangedListener = listener;
        AnonymousClass1 r0 = new DateFormatTextWatcher(formatHint, format, startTextInput, calendarConstraints) {
            /* access modifiers changed from: package-private */
            public void onValidDate(@Nullable Long day) {
                Long unused = RangeDateSelector.this.proposedTextStart = day;
                RangeDateSelector.this.updateIfValidTextProposal(textInputLayout, textInputLayout2, onSelectionChangedListener);
            }

            /* access modifiers changed from: package-private */
            public void onInvalidDate() {
                Long unused = RangeDateSelector.this.proposedTextStart = null;
                RangeDateSelector.this.updateIfValidTextProposal(textInputLayout, textInputLayout2, onSelectionChangedListener);
            }
        };
        startEditText.addTextChangedListener(r9);
        endEditText2.addTextChangedListener(new DateFormatTextWatcher(formatHint2, format2, endTextInput, calendarConstraints) {
            /* access modifiers changed from: package-private */
            public void onValidDate(@Nullable Long day) {
                Long unused = RangeDateSelector.this.proposedTextEnd = day;
                RangeDateSelector.this.updateIfValidTextProposal(textInputLayout, textInputLayout2, onSelectionChangedListener);
            }

            /* access modifiers changed from: package-private */
            public void onInvalidDate() {
                Long unused = RangeDateSelector.this.proposedTextEnd = null;
                RangeDateSelector.this.updateIfValidTextProposal(textInputLayout, textInputLayout2, onSelectionChangedListener);
            }
        });
        ViewUtils.requestFocusAndShowKeyboard(startEditText);
        return root;
    }

    private boolean isValidRange(long start, long end) {
        return start <= end;
    }

    /* access modifiers changed from: private */
    public void updateIfValidTextProposal(@NonNull TextInputLayout startTextInput, @NonNull TextInputLayout endTextInput, @NonNull OnSelectionChangedListener<Pair<Long, Long>> listener) {
        Long l = this.proposedTextStart;
        if (l == null || this.proposedTextEnd == null) {
            clearInvalidRange(startTextInput, endTextInput);
            listener.onIncompleteSelectionChanged();
        } else if (isValidRange(l.longValue(), this.proposedTextEnd.longValue())) {
            this.selectedStartItem = this.proposedTextStart;
            this.selectedEndItem = this.proposedTextEnd;
            listener.onSelectionChanged(getSelection());
        } else {
            setInvalidRange(startTextInput, endTextInput);
            listener.onIncompleteSelectionChanged();
        }
    }

    private void clearInvalidRange(@NonNull TextInputLayout start, @NonNull TextInputLayout end) {
        if (start.getError() != null && this.invalidRangeStartError.contentEquals(start.getError())) {
            start.setError((CharSequence) null);
        }
        if (end.getError() != null && " ".contentEquals(end.getError())) {
            end.setError((CharSequence) null);
        }
    }

    private void setInvalidRange(@NonNull TextInputLayout start, @NonNull TextInputLayout end) {
        start.setError(this.invalidRangeStartError);
        end.setError(" ");
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeValue(this.selectedStartItem);
        dest.writeValue(this.selectedEndItem);
    }
}
