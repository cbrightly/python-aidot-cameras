package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class f1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ f1 a = new f1();

    private /* synthetic */ f1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.MediaPlaybackCluster) baseChipCluster).fastForward((ChipClusters.MediaPlaybackCluster.PlaybackResponseCallback) obj);
    }
}
