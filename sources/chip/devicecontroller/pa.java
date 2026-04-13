package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class pa implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ pa a = new pa();

    private /* synthetic */ pa() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OnOffCluster) baseChipCluster).offWithEffect((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("effectId"), (Integer) map.get("effectVariant"));
    }
}
