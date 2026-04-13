package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class jl implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ jl a = new jl();

    private /* synthetic */ jl() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.PowerSourceCluster) baseChipCluster).readBatQuantityAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
