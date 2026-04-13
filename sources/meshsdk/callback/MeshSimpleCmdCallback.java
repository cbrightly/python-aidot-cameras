package meshsdk.callback;

public abstract class MeshSimpleCmdCallback extends MeshCustomcmdCallback {
    private String mac;

    public MeshSimpleCmdCallback(String mac2) {
        this.mac = mac2;
    }

    public String getMac() {
        return this.mac;
    }
}
