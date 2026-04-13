package zendesk.ui.android.conversation.connectionbanner;

import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class a implements View.OnClickListener {
    public final /* synthetic */ ConnectionBannerView c;

    public /* synthetic */ a(ConnectionBannerView connectionBannerView) {
        this.c = connectionBannerView;
    }

    public final void onClick(View view) {
        ConnectionBannerView.e(this.c, view);
    }
}
