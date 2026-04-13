package zendesk.conversationkit.android.model;

import java.util.ArrayList;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.q;
import kotlin.collections.r;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.internal.rest.model.MessageFieldDto;
import zendesk.conversationkit.android.internal.rest.model.MessageFieldOptionDto;
import zendesk.conversationkit.android.internal.rest.model.SendFieldResponseDto;
import zendesk.conversationkit.android.internal.rest.model.SendFieldSelectDto;
import zendesk.conversationkit.android.model.Field;

/* compiled from: Field.kt */
public final class m {

    /* compiled from: Field.kt */
    public final /* synthetic */ class a {
        public static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[n.values().length];
            iArr[n.TEXT.ordinal()] = 1;
            iArr[n.EMAIL.ordinal()] = 2;
            iArr[n.SELECT.ordinal()] = 3;
            a = iArr;
        }
    }

    @Nullable
    public static final Field a(@NotNull MessageFieldDto $this$toField) {
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        k.e($this$toField, "<this>");
        n a2 = n.Companion.a($this$toField.m());
        switch (a2 == null ? -1 : a.a[a2.ordinal()]) {
            case 1:
                String b = $this$toField.b();
                String g = $this$toField.g();
                String c = $this$toField.c();
                String i = $this$toField.i();
                if (i != null) {
                    str = i;
                } else {
                    str = "";
                }
                Integer f = $this$toField.f();
                int intValue = f == null ? 1 : f.intValue();
                Integer d = $this$toField.d();
                int intValue2 = d == null ? 128 : d.intValue();
                String l = $this$toField.l();
                if (l != null) {
                    str2 = l;
                } else {
                    str2 = "";
                }
                return new Field.Text(b, g, c, str, intValue, intValue2, str2);
            case 2:
                String b2 = $this$toField.b();
                String g2 = $this$toField.g();
                String c2 = $this$toField.c();
                String i2 = $this$toField.i();
                if (i2 != null) {
                    str3 = i2;
                } else {
                    str3 = "";
                }
                String a3 = $this$toField.a();
                if (a3 != null) {
                    str4 = a3;
                } else {
                    str4 = "";
                }
                return new Field.Email(b2, g2, c2, str3, str4);
            case 3:
                String b3 = $this$toField.b();
                String g3 = $this$toField.g();
                String c3 = $this$toField.c();
                String i3 = $this$toField.i();
                if (i3 != null) {
                    str5 = i3;
                } else {
                    str5 = "";
                }
                Iterable<MessageFieldOptionDto> $this$map$iv = $this$toField.h();
                if ($this$map$iv == null) {
                    $this$map$iv = q.g();
                }
                ArrayList arrayList = new ArrayList(r.r($this$map$iv, 10));
                for (MessageFieldOptionDto it : $this$map$iv) {
                    arrayList.add(new FieldOption(it.b(), it.a()));
                    $this$map$iv = $this$map$iv;
                }
                Integer k = $this$toField.k();
                int intValue3 = k == null ? 1 : k.intValue();
                Iterable<MessageFieldOptionDto> $this$map$iv2 = $this$toField.j();
                if ($this$map$iv2 == null) {
                    $this$map$iv2 = q.g();
                }
                int $i$f$map = false;
                ArrayList arrayList2 = new ArrayList(r.r($this$map$iv2, 10));
                for (MessageFieldOptionDto it2 : $this$map$iv2) {
                    arrayList2.add(new FieldOption(it2.b(), it2.a()));
                    MessageFieldDto messageFieldDto = $this$toField;
                    $this$map$iv2 = $this$map$iv2;
                    $i$f$map = $i$f$map;
                }
                int i4 = $i$f$map;
                return new Field.Select(b3, g3, c3, str5, arrayList, intValue3, arrayList2);
            default:
                return null;
        }
    }

    @NotNull
    public static final SendFieldResponseDto b(@NotNull Field $this$toSendFieldResponseDto) {
        k.e($this$toSendFieldResponseDto, "<this>");
        if ($this$toSendFieldResponseDto instanceof Field.Text) {
            return new SendFieldResponseDto.Text($this$toSendFieldResponseDto.a(), $this$toSendFieldResponseDto.c(), $this$toSendFieldResponseDto.b(), ((Field.Text) $this$toSendFieldResponseDto).i());
        }
        if ($this$toSendFieldResponseDto instanceof Field.Email) {
            return new SendFieldResponseDto.Email($this$toSendFieldResponseDto.a(), $this$toSendFieldResponseDto.c(), $this$toSendFieldResponseDto.b(), ((Field.Email) $this$toSendFieldResponseDto).g());
        }
        if ($this$toSendFieldResponseDto instanceof Field.Select) {
            String a2 = $this$toSendFieldResponseDto.a();
            String c = $this$toSendFieldResponseDto.c();
            String b = $this$toSendFieldResponseDto.b();
            Iterable<FieldOption> $this$mapTo$iv$iv = ((Field.Select) $this$toSendFieldResponseDto).h();
            ArrayList arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
            for (FieldOption it : $this$mapTo$iv$iv) {
                arrayList.add(new SendFieldSelectDto(it.b(), it.a()));
            }
            return new SendFieldResponseDto.Select(a2, c, b, arrayList);
        }
        throw new NoWhenBranchMatchedException();
    }
}
