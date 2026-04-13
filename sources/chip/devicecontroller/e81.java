package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class e81 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ e81 a = new e81();

    private /* synthetic */ e81() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.AccessControlCluster) baseChipCluster).readSubjectsPerAccessControlEntryAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
