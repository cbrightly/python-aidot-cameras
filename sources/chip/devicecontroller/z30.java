package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class z30 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ z30 a = new z30();

    private /* synthetic */ z30() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OnOffCluster) baseChipCluster).readOnTimeAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
