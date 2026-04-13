package meshsdk.model;

public class TestEvent {
    public String level;
    public String msg;
    public String tag = "Logger.onMessage";

    public TestEvent(String level2, String msg2) {
        this.msg = msg2;
        this.level = level2;
    }
}
