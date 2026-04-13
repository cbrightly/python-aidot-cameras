package zendesk.messaging.android.internal.conversationscreen.delegates;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import zendesk.messaging.R$id;
import zendesk.messaging.R$layout;
import zendesk.messaging.android.internal.adapterdelegate.b;
import zendesk.messaging.android.internal.model.b;

/* compiled from: TimestampDividerAdapterDelegate.kt */
public final class TimestampDividerAdapterDelegate extends b<b.c, zendesk.messaging.android.internal.model.b, ViewHolder> {
    /* access modifiers changed from: protected */
    /* renamed from: h */
    public boolean d(@NotNull zendesk.messaging.android.internal.model.b item, @NotNull List<? extends zendesk.messaging.android.internal.model.b> items, int position) {
        k.e(item, "item");
        k.e(items, FirebaseAnalytics.Param.ITEMS);
        return item instanceof b.c;
    }

    @NotNull
    /* renamed from: j */
    public ViewHolder c(@NotNull ViewGroup parent) {
        k.e(parent, "parent");
        View view = LayoutInflater.from(parent.getContext()).inflate(R$layout.zma_view_message_log_entry_timestamp_divider, parent, false);
        k.d(view, "from(parent.context)\n   …p_divider, parent, false)");
        return new ViewHolder(view);
    }

    /* access modifiers changed from: protected */
    /* renamed from: i */
    public void f(@NotNull b.c item, @NotNull ViewHolder holder, @NotNull List<? extends Object> payloads) {
        k.e(item, "item");
        k.e(holder, "holder");
        k.e(payloads, "payloads");
        holder.a(item);
    }

    /* compiled from: TimestampDividerAdapterDelegate.kt */
    public static final class ViewHolder extends RecyclerView.ViewHolder {
        @NotNull
        private final TextView a;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ViewHolder(@NotNull View itemView) {
            super(itemView);
            k.e(itemView, "itemView");
            View findViewById = itemView.findViewById(R$id.zma_timestamp_text);
            k.d(findViewById, "itemView.findViewById(R.id.zma_timestamp_text)");
            this.a = (TextView) findViewById;
        }

        public final void a(@NotNull b.c item) {
            k.e(item, "item");
            this.a.setText(item.a());
        }
    }
}
