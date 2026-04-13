package androidx.databinding.adapters;

import android.view.ViewStub;
import androidx.annotation.RestrictTo;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import androidx.databinding.Untaggable;
import androidx.databinding.ViewStubProxy;

@BindingMethods({@BindingMethod(attribute = "android:layout", method = "setLayoutResource", type = ViewStub.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
@Untaggable({"android.view.ViewStub"})
public class ViewStubBindingAdapter {
    @BindingAdapter({"android:onInflate"})
    public static void setOnInflateListener(ViewStubProxy viewStubProxy, ViewStub.OnInflateListener listener) {
        viewStubProxy.setOnInflateListener(listener);
    }
}
