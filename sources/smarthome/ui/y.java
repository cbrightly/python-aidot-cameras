package smarthome.ui;

import android.view.View;
import com.leedarson.serviceinterface.LightsRhythmService;

/* compiled from: lambda */
public final /* synthetic */ class y implements View.OnClickListener {
    public final /* synthetic */ CoreActivity c;
    public final /* synthetic */ LightsRhythmService d;

    public /* synthetic */ y(CoreActivity coreActivity, LightsRhythmService lightsRhythmService) {
        this.c = coreActivity;
        this.d = lightsRhythmService;
    }

    public final void onClick(View view) {
        this.c.c1(this.d, view);
    }
}
