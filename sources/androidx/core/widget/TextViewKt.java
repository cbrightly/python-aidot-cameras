package androidx.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.r;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: TextView.kt */
public final class TextViewKt {
    @NotNull
    public static final TextWatcher doBeforeTextChanged(@NotNull TextView $this$doBeforeTextChanged, @NotNull r<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, x> action) {
        k.e($this$doBeforeTextChanged, "<this>");
        k.e(action, "action");
        TextViewKt$doBeforeTextChanged$$inlined$addTextChangedListener$default$1 textWatcher$iv = new TextViewKt$doBeforeTextChanged$$inlined$addTextChangedListener$default$1(action);
        $this$doBeforeTextChanged.addTextChangedListener(textWatcher$iv);
        return textWatcher$iv;
    }

    @NotNull
    public static final TextWatcher doOnTextChanged(@NotNull TextView $this$doOnTextChanged, @NotNull r<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, x> action) {
        k.e($this$doOnTextChanged, "<this>");
        k.e(action, "action");
        TextViewKt$doOnTextChanged$$inlined$addTextChangedListener$default$1 textWatcher$iv = new TextViewKt$doOnTextChanged$$inlined$addTextChangedListener$default$1(action);
        $this$doOnTextChanged.addTextChangedListener(textWatcher$iv);
        return textWatcher$iv;
    }

    @NotNull
    public static final TextWatcher doAfterTextChanged(@NotNull TextView $this$doAfterTextChanged, @NotNull l<? super Editable, x> action) {
        k.e($this$doAfterTextChanged, "<this>");
        k.e(action, "action");
        TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$default$1 textWatcher$iv = new TextViewKt$doAfterTextChanged$$inlined$addTextChangedListener$default$1(action);
        $this$doAfterTextChanged.addTextChangedListener(textWatcher$iv);
        return textWatcher$iv;
    }

    public static /* synthetic */ TextWatcher addTextChangedListener$default(TextView $this$addTextChangedListener_u24default, r beforeTextChanged, r onTextChanged, l afterTextChanged, int i, Object obj) {
        if ((i & 1) != 0) {
            beforeTextChanged = TextViewKt$addTextChangedListener$1.INSTANCE;
        }
        if ((i & 2) != 0) {
            onTextChanged = TextViewKt$addTextChangedListener$2.INSTANCE;
        }
        if ((i & 4) != 0) {
            afterTextChanged = TextViewKt$addTextChangedListener$3.INSTANCE;
        }
        k.e($this$addTextChangedListener_u24default, "<this>");
        k.e(beforeTextChanged, "beforeTextChanged");
        k.e(onTextChanged, "onTextChanged");
        k.e(afterTextChanged, "afterTextChanged");
        TextViewKt$addTextChangedListener$textWatcher$1 textWatcher = new TextViewKt$addTextChangedListener$textWatcher$1(afterTextChanged, beforeTextChanged, onTextChanged);
        $this$addTextChangedListener_u24default.addTextChangedListener(textWatcher);
        return textWatcher;
    }

    @NotNull
    public static final TextWatcher addTextChangedListener(@NotNull TextView $this$addTextChangedListener, @NotNull r<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, x> beforeTextChanged, @NotNull r<? super CharSequence, ? super Integer, ? super Integer, ? super Integer, x> onTextChanged, @NotNull l<? super Editable, x> afterTextChanged) {
        k.e($this$addTextChangedListener, "<this>");
        k.e(beforeTextChanged, "beforeTextChanged");
        k.e(onTextChanged, "onTextChanged");
        k.e(afterTextChanged, "afterTextChanged");
        TextViewKt$addTextChangedListener$textWatcher$1 textWatcher = new TextViewKt$addTextChangedListener$textWatcher$1(afterTextChanged, beforeTextChanged, onTextChanged);
        $this$addTextChangedListener.addTextChangedListener(textWatcher);
        return textWatcher;
    }
}
