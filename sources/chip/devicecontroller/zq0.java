package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class zq0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ zq0 a = new zq0();

    private /* synthetic */ zq0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.WindowCoveringCluster) baseChipCluster).readTypeAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
