package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class kv implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ kv a = new kv();

    private /* synthetic */ kv() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.MediaPlaybackCluster) baseChipCluster).readSeekRangeStartAttribute((ChipClusters.MediaPlaybackCluster.SeekRangeStartAttributeCallback) obj);
    }
}
