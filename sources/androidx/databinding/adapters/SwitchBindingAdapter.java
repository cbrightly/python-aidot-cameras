package androidx.databinding.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.widget.Switch;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

@TargetApi(14)
@BindingMethods({@BindingMethod(attribute = "android:thumb", method = "setThumbDrawable", type = Switch.class), @BindingMethod(attribute = "android:track", method = "setTrackDrawable", type = Switch.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
public class SwitchBindingAdapter {
    @BindingAdapter({"android:switchTextAppearance"})
    public static void setSwitchTextAppearance(Switch view, int value) {
        view.setSwitchTextAppearance((Context) null, value);
    }
}
