package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vf implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vf a = new vf();

    private /* synthetic */ vf() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TestClusterCluster) baseChipCluster).readBitmap8Attribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
