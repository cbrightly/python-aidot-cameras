package kotlin.text;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import kotlin.ranges.i;
import kotlin.ranges.n;

/* compiled from: Regex.kt */
public final class l {
    /* access modifiers changed from: private */
    public static final int j(Iterable<? extends e> $this$toInt) {
        int accumulator$iv = 0;
        for (e option : $this$toInt) {
            accumulator$iv |= option.getValue();
        }
        return accumulator$iv;
    }

    /* access modifiers changed from: private */
    public static final h f(Matcher $this$findNext, int from, CharSequence input) {
        if (!$this$findNext.find(from)) {
            return null;
        }
        return new i($this$findNext, input);
    }

    /* access modifiers changed from: private */
    public static final h g(Matcher $this$matchEntire, CharSequence input) {
        if (!$this$matchEntire.matches()) {
            return null;
        }
        return new i($this$matchEntire, input);
    }

    /* access modifiers changed from: private */
    public static final i h(MatchResult $this$range) {
        return n.l($this$range.start(), $this$range.end());
    }

    /* access modifiers changed from: private */
    public static final i i(MatchResult $this$range, int groupIndex) {
        return n.l($this$range.start(groupIndex), $this$range.end(groupIndex));
    }
}
