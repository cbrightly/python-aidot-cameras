package zendesk.ui.android.conversation.textcell;

import android.text.style.URLSpan;
import android.view.View;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: TextCellView.kt */
public final class TextCellView$prepareClickableElements$1$1 extends URLSpan {
    final /* synthetic */ TextCellView c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    TextCellView$prepareClickableElements$1$1(TextCellView $receiver, String $super_call_param$1) {
        super($super_call_param$1);
        this.c = $receiver;
    }

    @SensorsDataInstrumented
    public void onClick(@NotNull View view) {
        x xVar;
        View widget = view;
        k.e(widget, "widget");
        l<String, x> b = this.c.d.b();
        if (b == null) {
            xVar = null;
        } else {
            String url = getURL();
            k.d(url, "url");
            b.invoke(url);
            xVar = x.a;
        }
        if (xVar == null) {
            super.onClick(widget);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
