package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ct0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ct0 a = new ct0();

    private /* synthetic */ ct0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.MediaPlaybackCluster) baseChipCluster).readCurrentStateAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
