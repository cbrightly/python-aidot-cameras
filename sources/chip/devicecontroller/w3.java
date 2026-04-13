package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class w3 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ w3 a = new w3();

    private /* synthetic */ w3() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OperationalCredentialsCluster) baseChipCluster).attestationRequest((ChipClusters.OperationalCredentialsCluster.AttestationResponseCallback) obj, (byte[]) map.get("attestationNonce"));
    }
}
