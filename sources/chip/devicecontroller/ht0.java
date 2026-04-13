package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ht0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ht0 a = new ht0();

    private /* synthetic */ ht0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BridgedDeviceBasicCluster) baseChipCluster).readFeatureMapAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
