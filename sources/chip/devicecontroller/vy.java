package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vy implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vy a = new vy();

    private /* synthetic */ vy() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ApplicationBasicCluster) baseChipCluster).readVendorIDAttribute((ChipClusters.ApplicationBasicCluster.VendorIDAttributeCallback) obj);
    }
}
