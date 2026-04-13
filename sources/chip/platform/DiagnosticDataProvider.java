package chip.platform;

public interface DiagnosticDataProvider {
    NetworkInterface[] getNetworkInterfaces();

    int getRebootCount();
}
