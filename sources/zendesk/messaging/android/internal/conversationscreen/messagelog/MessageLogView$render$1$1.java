package zendesk.messaging.android.internal.conversationscreen.messagelog;

import android.widget.EdgeEffect;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: MessageLogView.kt */
public final class MessageLogView$render$1$1 extends RecyclerView.EdgeEffectFactory {
    final /* synthetic */ int a;

    MessageLogView$render$1$1(int $customColor) {
        this.a = $customColor;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public EdgeEffect createEdgeEffect(@NotNull RecyclerView view, int direction) {
        k.e(view, "view");
        EdgeEffect $this$createEdgeEffect_u24lambda_u2d0 = new EdgeEffect(view.getContext());
        $this$createEdgeEffect_u24lambda_u2d0.setColor(this.a);
        return $this$createEdgeEffect_u24lambda_u2d0;
    }
}
