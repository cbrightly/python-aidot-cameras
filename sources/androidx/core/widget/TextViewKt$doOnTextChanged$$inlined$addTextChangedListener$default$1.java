package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import kotlin.jvm.functions.r;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextView.kt */
public final class TextViewKt$doOnTextChanged$$inlined$addTextChangedListener$default$1 implements TextWatcher {
    final /* synthetic */ r $onTextChanged;

    public TextViewKt$doOnTextChanged$$inlined$addTextChangedListener$default$1(r $onTextChanged2) {
        this.$onTextChanged = $onTextChanged2;
    }

    public void afterTextChanged(@Nullable Editable s) {
        Editable editable = s;
    }

    public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
        CharSequence charSequence = text;
        int i = count;
        int i2 = start;
        int i3 = after;
    }

    public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
        this.$onTextChanged.invoke(text, Integer.valueOf(start), Integer.valueOf(before), Integer.valueOf(count));
    }
}
