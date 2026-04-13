package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fa implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fa a = new fa();

    private /* synthetic */ fa() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.AudioOutputCluster) baseChipCluster).renameOutput((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get(FirebaseAnalytics.Param.INDEX), (String) map.get("name"));
    }
}
