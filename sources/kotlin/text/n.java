package kotlin.text;

import org.jetbrains.annotations.NotNull;

/* compiled from: StringNumberConversionsJVM.kt */
public final class n {
    @NotNull
    public static final j a;
    @NotNull
    public static final n b = new n();

    static {
        String Exp = "[eE][+-]?" + "(\\p{Digit}+)";
        a = new j("[\\x00-\\x20]*[+-]?(NaN|Infinity|((" + ('(' + "(\\p{Digit}+)" + "(\\.)?(" + "(\\p{Digit}+)" + "?)(" + Exp + ")?)|" + "(\\.(" + "(\\p{Digit}+)" + ")(" + Exp + ")?)|" + "((" + ("(0[xX]" + "(\\p{XDigit}+)" + "(\\.)?)|" + "(0[xX]" + "(\\p{XDigit}+)" + "?(\\.)" + "(\\p{XDigit}+)" + ')') + ")[pP][+-]?" + "(\\p{Digit}+)" + ')') + ")[fFdD]?))[\\x00-\\x20]*");
    }

    private n() {
    }
}
