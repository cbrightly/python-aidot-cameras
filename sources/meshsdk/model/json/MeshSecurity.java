package meshsdk.model.json;

public enum MeshSecurity {
    Secure("secure"),
    Insecure("insecure");
    
    private String desc;

    public String getDesc() {
        return this.desc;
    }

    private MeshSecurity(String desc2) {
        this.desc = desc2;
    }
}
