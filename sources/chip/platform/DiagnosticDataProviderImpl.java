package chip.platform;

import android.content.Context;
import android.util.Log;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class DiagnosticDataProviderImpl implements DiagnosticDataProvider {
    private static final String TAG = DiagnosticDataProviderImpl.class.getSimpleName();
    private Context mContext;

    public DiagnosticDataProviderImpl(Context context) {
        this.mContext = context;
    }

    public int getRebootCount() {
        return 100;
    }

    public NetworkInterface[] getNetworkInterfaces() {
        int i;
        try {
            ArrayList<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            int size = networkInterfaces.size();
            List<NetworkInterface> destInterfaces = new ArrayList<>();
            for (int i2 = 0; i2 < size; i2++) {
                NetworkInterface nif = networkInterfaces.get(i2);
                String name = nif.getName();
                if (name != null) {
                    if (!name.startsWith("eth")) {
                        if (!name.startsWith("wlan")) {
                        }
                    }
                    NetworkInterface anInterface = new NetworkInterface();
                    anInterface.isOperational = nif.isUp();
                    anInterface.hardwareAddress = nif.getHardwareAddress();
                    anInterface.name = nif.getName();
                    if (name.startsWith("wlan")) {
                        i = 1;
                    } else {
                        i = 2;
                    }
                    anInterface.type = i;
                    Enumeration<InetAddress> inetAddress = nif.getInetAddresses();
                    while (inetAddress.hasMoreElements()) {
                        InetAddress ip = inetAddress.nextElement();
                        if (ip instanceof Inet4Address) {
                            if (anInterface.ipv4Address == null) {
                                anInterface.ipv4Address = ip.getAddress();
                            }
                        } else if ((ip instanceof InetAddress) && anInterface.ipv6Address == null) {
                            anInterface.ipv6Address = ip.getAddress();
                        }
                    }
                    destInterfaces.add(anInterface);
                }
            }
            NetworkInterface[] inetArray = new NetworkInterface[destInterfaces.size()];
            destInterfaces.toArray(inetArray);
            return inetArray;
        } catch (SocketException e) {
            String str = TAG;
            Log.e(str, "getNetworkInterfaces: " + e.getMessage());
            return new NetworkInterface[0];
        }
    }
}
