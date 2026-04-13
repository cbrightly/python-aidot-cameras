package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class xl implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ xl a = new xl();

    private /* synthetic */ xl() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BridgedDeviceBasicCluster) baseChipCluster).readReachableAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
