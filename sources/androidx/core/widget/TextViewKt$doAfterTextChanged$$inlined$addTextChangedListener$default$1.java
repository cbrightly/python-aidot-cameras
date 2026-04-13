package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import kotlin.jvm.functions.l;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextView.kt */
public final class TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$default$1 implements TextWatcher {
    final /* synthetic */ l $afterTextChanged;

    public TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$default$1(l $afterTextChanged2) {
        this.$afterTextChanged = $afterTextChanged2;
    }

    public void afterTextChanged(@Nullable Editable s) {
        this.$afterTextChanged.invoke(s);
    }

    public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
        CharSequence charSequence = text;
        int i = count;
        int i2 = start;
        int i3 = after;
    }

    public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
        CharSequence charSequence = text;
        int i = before;
        int i2 = start;
        int i3 = count;
    }
}
