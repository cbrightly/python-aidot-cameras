package io.ktor.http;

import com.google.android.gms.actions.SearchIntents;
import io.ktor.http.y;
import java.nio.charset.Charset;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.a;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: Query.kt */
public final class d0 {
    public static /* synthetic */ y d(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = 1000;
        }
        return c(str, i, i2);
    }

    @NotNull
    public static final y c(@NotNull String query, int startIndex, int limit) {
        k.f(query, SearchIntents.EXTRA_QUERY);
        if (startIndex > x.Z(query)) {
            return y.b.a();
        }
        y.a aVar = y.b;
        z $this$build = new z(0, 1, (DefaultConstructorMarker) null);
        b($this$build, query, startIndex, limit);
        return $this$build.m();
    }

    private static final void b(@NotNull z $this$parse, String query, int startIndex, int limit) {
        int count = 0;
        int nameIndex = startIndex;
        int equalIndex = -1;
        int Z = x.Z(query);
        if (startIndex <= Z) {
            int index = startIndex;
            while (count != limit) {
                switch (query.charAt(index)) {
                    case '&':
                        a($this$parse, query, nameIndex, equalIndex, index);
                        nameIndex = index + 1;
                        equalIndex = -1;
                        count++;
                        break;
                    case '=':
                        if (equalIndex == -1) {
                            equalIndex = index;
                            break;
                        }
                        break;
                }
                if (index != Z) {
                    index++;
                }
            }
            return;
        }
        if (count != limit) {
            a($this$parse, query, nameIndex, equalIndex, query.length());
        }
    }

    private static final void a(@NotNull z $this$appendParam, String query, int nameIndex, int equalIndex, int endIndex) {
        z zVar = $this$appendParam;
        String str = query;
        int i = nameIndex;
        int i2 = equalIndex;
        int i3 = endIndex;
        if (i2 == -1) {
            int spaceNameIndex = f(i, i3, str);
            int spaceEndIndex = e(spaceNameIndex, i3, str);
            if (spaceEndIndex > spaceNameIndex) {
                zVar.c(a.g(query, spaceNameIndex, spaceEndIndex, false, (Charset) null, 12, (Object) null), q.g());
                return;
            }
            return;
        }
        int spaceNameIndex2 = f(i, i2, str);
        int spaceEqualIndex = e(spaceNameIndex2, i2, str);
        if (spaceEqualIndex > spaceNameIndex2) {
            String name = a.g(query, spaceNameIndex2, spaceEqualIndex, false, (Charset) null, 12, (Object) null);
            int spaceValueIndex = f(i2 + 1, i3, str);
            zVar.a(name, a.g(query, spaceValueIndex, e(spaceValueIndex, i3, str), true, (Charset) null, 8, (Object) null));
        }
    }

    private static final int e(int start, int end, CharSequence text) {
        int spaceIndex = end;
        while (spaceIndex > start && a.c(text.charAt(spaceIndex - 1))) {
            spaceIndex--;
        }
        return spaceIndex;
    }

    private static final int f(int start, int end, CharSequence query) {
        int spaceIndex = start;
        while (spaceIndex < end && a.c(query.charAt(spaceIndex))) {
            spaceIndex++;
        }
        return spaceIndex;
    }
}
