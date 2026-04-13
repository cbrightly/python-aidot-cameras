package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class tw0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ tw0 a = new tw0();

    private /* synthetic */ tw0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OnOffCluster) baseChipCluster).readGlobalSceneControlAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
