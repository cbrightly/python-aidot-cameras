package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class wn0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ wn0 a = new wn0();

    private /* synthetic */ wn0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OccupancySensingCluster) baseChipCluster).readOccupancySensorTypeBitmapAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
