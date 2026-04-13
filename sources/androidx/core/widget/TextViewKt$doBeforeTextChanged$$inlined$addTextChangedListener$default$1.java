package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import kotlin.jvm.functions.r;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextView.kt */
public final class TextViewKt$doBeforeTextChanged$$inlined$addTextChangedListener$default$1 implements TextWatcher {
    final /* synthetic */ r $beforeTextChanged;

    public TextViewKt$doBeforeTextChanged$$inlined$addTextChangedListener$default$1(r $beforeTextChanged2) {
        this.$beforeTextChanged = $beforeTextChanged2;
    }

    public void afterTextChanged(@Nullable Editable s) {
        Editable editable = s;
    }

    public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
        this.$beforeTextChanged.invoke(text, Integer.valueOf(start), Integer.valueOf(count), Integer.valueOf(after));
    }

    public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
        CharSequence charSequence = text;
        int i = before;
        int i2 = start;
        int i3 = count;
    }
}
