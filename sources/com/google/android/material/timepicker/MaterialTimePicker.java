package com.google.android.material.timepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.timepicker.TimePickerView;
import java.util.LinkedHashSet;
import java.util.Set;

public final class MaterialTimePicker extends DialogFragment {
    public static final int INPUT_MODE_CLOCK = 0;
    static final String INPUT_MODE_EXTRA = "TIME_PICKER_INPUT_MODE";
    public static final int INPUT_MODE_KEYBOARD = 1;
    static final String OVERRIDE_THEME_RES_ID = "TIME_PICKER_OVERRIDE_THEME_RES_ID";
    static final String TIME_MODEL_EXTRA = "TIME_PICKER_TIME_MODEL";
    static final String TITLE_RES_EXTRA = "TIME_PICKER_TITLE_RES";
    static final String TITLE_TEXT_EXTRA = "TIME_PICKER_TITLE_TEXT";
    @Nullable
    private TimePickerPresenter activePresenter;
    private final Set<DialogInterface.OnCancelListener> cancelListeners = new LinkedHashSet();
    @DrawableRes
    private int clockIcon;
    private final Set<DialogInterface.OnDismissListener> dismissListeners = new LinkedHashSet();
    /* access modifiers changed from: private */
    public int inputMode = 0;
    @DrawableRes
    private int keyboardIcon;
    /* access modifiers changed from: private */
    public MaterialButton modeButton;
    /* access modifiers changed from: private */
    public final Set<View.OnClickListener> negativeButtonListeners = new LinkedHashSet();
    private int overrideThemeResId = 0;
    /* access modifiers changed from: private */
    public final Set<View.OnClickListener> positiveButtonListeners = new LinkedHashSet();
    private ViewStub textInputStub;
    private TimeModel time;
    @Nullable
    private TimePickerClockPresenter timePickerClockPresenter;
    /* access modifiers changed from: private */
    @Nullable
    public TimePickerTextInputPresenter timePickerTextInputPresenter;
    private TimePickerView timePickerView;
    private int titleResId = 0;
    private String titleText;

    /* access modifiers changed from: private */
    @NonNull
    public static MaterialTimePicker newInstance(@NonNull Builder options) {
        MaterialTimePicker fragment = new MaterialTimePicker();
        Bundle args = new Bundle();
        args.putParcelable(TIME_MODEL_EXTRA, options.time);
        args.putInt(INPUT_MODE_EXTRA, options.inputMode);
        args.putInt(TITLE_RES_EXTRA, options.titleTextResId);
        args.putInt(OVERRIDE_THEME_RES_ID, options.overrideThemeResId);
        if (options.titleText != null) {
            args.putString(TITLE_TEXT_EXTRA, options.titleText.toString());
        }
        fragment.setArguments(args);
        return fragment;
    }

    @IntRange(from = 0, to = 60)
    public int getMinute() {
        return this.time.minute;
    }

    @IntRange(from = 0, to = 23)
    public int getHour() {
        return this.time.hour % 24;
    }

    public int getInputMode() {
        return this.inputMode;
    }

    @NonNull
    public final Dialog onCreateDialog(@Nullable Bundle bundle) {
        Dialog dialog = new Dialog(requireContext(), getThemeResId());
        Context context = dialog.getContext();
        int surfaceColor = MaterialAttributes.resolveOrThrow(context, R.attr.colorSurface, MaterialTimePicker.class.getCanonicalName());
        int i = R.attr.materialTimePickerStyle;
        int i2 = R.style.Widget_MaterialComponents_TimePicker;
        MaterialShapeDrawable background = new MaterialShapeDrawable(context, (AttributeSet) null, i, i2);
        TypedArray a = context.obtainStyledAttributes((AttributeSet) null, R.styleable.MaterialTimePicker, i, i2);
        this.clockIcon = a.getResourceId(R.styleable.MaterialTimePicker_clockIcon, 0);
        this.keyboardIcon = a.getResourceId(R.styleable.MaterialTimePicker_keyboardIcon, 0);
        a.recycle();
        background.initializeElevationOverlay(context);
        background.setFillColor(ColorStateList.valueOf(surfaceColor));
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(background);
        window.requestFeature(1);
        window.setLayout(-2, -2);
        return dialog;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        restoreState(bundle == null ? getArguments() : bundle);
    }

    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(TIME_MODEL_EXTRA, this.time);
        bundle.putInt(INPUT_MODE_EXTRA, this.inputMode);
        bundle.putInt(TITLE_RES_EXTRA, this.titleResId);
        bundle.putString(TITLE_TEXT_EXTRA, this.titleText);
        bundle.putInt(OVERRIDE_THEME_RES_ID, this.overrideThemeResId);
    }

    private void restoreState(@Nullable Bundle bundle) {
        if (bundle != null) {
            TimeModel timeModel = (TimeModel) bundle.getParcelable(TIME_MODEL_EXTRA);
            this.time = timeModel;
            if (timeModel == null) {
                this.time = new TimeModel();
            }
            this.inputMode = bundle.getInt(INPUT_MODE_EXTRA, 0);
            this.titleResId = bundle.getInt(TITLE_RES_EXTRA, 0);
            this.titleText = bundle.getString(TITLE_TEXT_EXTRA);
            this.overrideThemeResId = bundle.getInt(OVERRIDE_THEME_RES_ID, 0);
        }
    }

    @NonNull
    public final View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ViewGroup root = (ViewGroup) layoutInflater.inflate(R.layout.material_timepicker_dialog, viewGroup);
        TimePickerView timePickerView2 = (TimePickerView) root.findViewById(R.id.material_timepicker_view);
        this.timePickerView = timePickerView2;
        timePickerView2.setOnDoubleTapListener(new TimePickerView.OnDoubleTapListener() {
            public void onDoubleTap() {
                int unused = MaterialTimePicker.this.inputMode = 1;
                MaterialTimePicker materialTimePicker = MaterialTimePicker.this;
                materialTimePicker.updateInputMode(materialTimePicker.modeButton);
                MaterialTimePicker.this.timePickerTextInputPresenter.resetChecked();
            }
        });
        this.textInputStub = (ViewStub) root.findViewById(R.id.material_textinput_timepicker);
        this.modeButton = (MaterialButton) root.findViewById(R.id.material_timepicker_mode_button);
        TextView headerTitle = (TextView) root.findViewById(R.id.header_title);
        if (!TextUtils.isEmpty(this.titleText)) {
            headerTitle.setText(this.titleText);
        }
        int i = this.titleResId;
        if (i != 0) {
            headerTitle.setText(i);
        }
        updateInputMode(this.modeButton);
        ((Button) root.findViewById(R.id.material_timepicker_ok_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (View.OnClickListener listener : MaterialTimePicker.this.positiveButtonListeners) {
                    listener.onClick(v);
                }
                MaterialTimePicker.this.dismiss();
            }
        });
        ((Button) root.findViewById(R.id.material_timepicker_cancel_button)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (View.OnClickListener listener : MaterialTimePicker.this.negativeButtonListeners) {
                    listener.onClick(v);
                }
                MaterialTimePicker.this.dismiss();
            }
        });
        this.modeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MaterialTimePicker materialTimePicker = MaterialTimePicker.this;
                int unused = materialTimePicker.inputMode = materialTimePicker.inputMode == 0 ? 1 : 0;
                MaterialTimePicker materialTimePicker2 = MaterialTimePicker.this;
                materialTimePicker2.updateInputMode(materialTimePicker2.modeButton);
            }
        });
        return root;
    }

    public void onStop() {
        super.onStop();
        this.activePresenter = null;
        this.timePickerClockPresenter = null;
        this.timePickerTextInputPresenter = null;
        this.timePickerView = null;
    }

    public final void onCancel(@NonNull DialogInterface dialogInterface) {
        for (DialogInterface.OnCancelListener listener : this.cancelListeners) {
            listener.onCancel(dialogInterface);
        }
        super.onCancel(dialogInterface);
    }

    public final void onDismiss(@NonNull DialogInterface dialogInterface) {
        for (DialogInterface.OnDismissListener listener : this.dismissListeners) {
            listener.onDismiss(dialogInterface);
        }
        super.onDismiss(dialogInterface);
    }

    /* access modifiers changed from: private */
    public void updateInputMode(MaterialButton modeButton2) {
        TimePickerPresenter timePickerPresenter = this.activePresenter;
        if (timePickerPresenter != null) {
            timePickerPresenter.hide();
        }
        TimePickerPresenter initializeOrRetrieveActivePresenterForMode = initializeOrRetrieveActivePresenterForMode(this.inputMode);
        this.activePresenter = initializeOrRetrieveActivePresenterForMode;
        initializeOrRetrieveActivePresenterForMode.show();
        this.activePresenter.invalidate();
        Pair<Integer, Integer> buttonData = dataForMode(this.inputMode);
        modeButton2.setIconResource(((Integer) buttonData.first).intValue());
        modeButton2.setContentDescription(getResources().getString(((Integer) buttonData.second).intValue()));
    }

    private TimePickerPresenter initializeOrRetrieveActivePresenterForMode(int mode) {
        if (mode == 0) {
            TimePickerClockPresenter timePickerClockPresenter2 = this.timePickerClockPresenter;
            if (timePickerClockPresenter2 == null) {
                timePickerClockPresenter2 = new TimePickerClockPresenter(this.timePickerView, this.time);
            }
            this.timePickerClockPresenter = timePickerClockPresenter2;
            return timePickerClockPresenter2;
        }
        if (this.timePickerTextInputPresenter == null) {
            this.timePickerTextInputPresenter = new TimePickerTextInputPresenter((LinearLayout) this.textInputStub.inflate(), this.time);
        }
        this.timePickerTextInputPresenter.clearCheck();
        return this.timePickerTextInputPresenter;
    }

    private Pair<Integer, Integer> dataForMode(int mode) {
        switch (mode) {
            case 0:
                return new Pair<>(Integer.valueOf(this.keyboardIcon), Integer.valueOf(R.string.material_timepicker_text_input_mode_description));
            case 1:
                return new Pair<>(Integer.valueOf(this.clockIcon), Integer.valueOf(R.string.material_timepicker_clock_mode_description));
            default:
                throw new IllegalArgumentException("no icon for mode: " + mode);
        }
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public TimePickerClockPresenter getTimePickerClockPresenter() {
        return this.timePickerClockPresenter;
    }

    public boolean addOnPositiveButtonClickListener(@NonNull View.OnClickListener listener) {
        return this.positiveButtonListeners.add(listener);
    }

    public boolean removeOnPositiveButtonClickListener(@NonNull View.OnClickListener listener) {
        return this.positiveButtonListeners.remove(listener);
    }

    public void clearOnPositiveButtonClickListeners() {
        this.positiveButtonListeners.clear();
    }

    public boolean addOnNegativeButtonClickListener(@NonNull View.OnClickListener listener) {
        return this.negativeButtonListeners.add(listener);
    }

    public boolean removeOnNegativeButtonClickListener(@NonNull View.OnClickListener listener) {
        return this.negativeButtonListeners.remove(listener);
    }

    public void clearOnNegativeButtonClickListeners() {
        this.negativeButtonListeners.clear();
    }

    public boolean addOnCancelListener(@NonNull DialogInterface.OnCancelListener listener) {
        return this.cancelListeners.add(listener);
    }

    public boolean removeOnCancelListener(@NonNull DialogInterface.OnCancelListener listener) {
        return this.cancelListeners.remove(listener);
    }

    public void clearOnCancelListeners() {
        this.cancelListeners.clear();
    }

    public boolean addOnDismissListener(@NonNull DialogInterface.OnDismissListener listener) {
        return this.dismissListeners.add(listener);
    }

    public boolean removeOnDismissListener(@NonNull DialogInterface.OnDismissListener listener) {
        return this.dismissListeners.remove(listener);
    }

    public void clearOnDismissListeners() {
        this.dismissListeners.clear();
    }

    private int getThemeResId() {
        int i = this.overrideThemeResId;
        if (i != 0) {
            return i;
        }
        TypedValue value = MaterialAttributes.resolve(requireContext(), R.attr.materialTimePickerTheme);
        if (value == null) {
            return 0;
        }
        return value.data;
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public int inputMode;
        /* access modifiers changed from: private */
        public int overrideThemeResId = 0;
        /* access modifiers changed from: private */
        public TimeModel time = new TimeModel();
        /* access modifiers changed from: private */
        public CharSequence titleText;
        /* access modifiers changed from: private */
        public int titleTextResId = 0;

        @NonNull
        public Builder setInputMode(int inputMode2) {
            this.inputMode = inputMode2;
            return this;
        }

        @NonNull
        public Builder setHour(@IntRange(from = 0, to = 23) int hour) {
            this.time.setHourOfDay(hour);
            return this;
        }

        @NonNull
        public Builder setMinute(@IntRange(from = 0, to = 60) int minute) {
            this.time.setMinute(minute);
            return this;
        }

        @NonNull
        public Builder setTimeFormat(int format) {
            TimeModel timeModel = this.time;
            int hour = timeModel.hour;
            int minute = timeModel.minute;
            TimeModel timeModel2 = new TimeModel(format);
            this.time = timeModel2;
            timeModel2.setMinute(minute);
            this.time.setHourOfDay(hour);
            return this;
        }

        @NonNull
        public Builder setTitleText(@StringRes int titleTextResId2) {
            this.titleTextResId = titleTextResId2;
            return this;
        }

        @NonNull
        public Builder setTitleText(@Nullable CharSequence charSequence) {
            this.titleText = charSequence;
            return this;
        }

        @NonNull
        public Builder setTheme(@StyleRes int themeResId) {
            this.overrideThemeResId = themeResId;
            return this;
        }

        @NonNull
        public MaterialTimePicker build() {
            return MaterialTimePicker.newInstance(this);
        }
    }
}
