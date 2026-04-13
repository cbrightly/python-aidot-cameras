package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class n0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ n0 a = new n0();

    private /* synthetic */ n0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.MediaPlaybackCluster) baseChipCluster).pause((ChipClusters.MediaPlaybackCluster.PlaybackResponseCallback) obj);
    }
}
