package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.r;
import kotlin.x;
import org.jetbrains.annotations.Nullable;

/* compiled from: TextView.kt */
public final class TextViewKt$addTextChangedListener$textWatcher$1 implements TextWatcher {
    final /* synthetic */ l<Editable, x> $afterTextChanged;
    final /* synthetic */ r<CharSequence, Integer, Integer, Integer, x> $beforeTextChanged;
    final /* synthetic */ r<CharSequence, Integer, Integer, Integer, x> $onTextChanged;

    public TextViewKt$addTextChangedListener$textWatcher$1(l<? super Editable, x> $afterTextChanged2, r<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, x> $beforeTextChanged2, r<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, x> $onTextChanged2) {
        this.$afterTextChanged = $afterTextChanged2;
        this.$beforeTextChanged = $beforeTextChanged2;
        this.$onTextChanged = $onTextChanged2;
    }

    public void afterTextChanged(@Nullable Editable s) {
        this.$afterTextChanged.invoke(s);
    }

    public void beforeTextChanged(@Nullable CharSequence text, int start, int count, int after) {
        this.$beforeTextChanged.invoke(text, Integer.valueOf(start), Integer.valueOf(count), Integer.valueOf(after));
    }

    public void onTextChanged(@Nullable CharSequence text, int start, int before, int count) {
        this.$onTextChanged.invoke(text, Integer.valueOf(start), Integer.valueOf(before), Integer.valueOf(count));
    }
}
