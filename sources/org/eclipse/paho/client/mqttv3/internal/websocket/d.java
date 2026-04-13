package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import org.glassfish.tyrus.spi.UpgradeRequest;

/* compiled from: WebSocketHandshake */
public class d {
    InputStream a;
    OutputStream b;
    String c;
    String d;
    int e;
    Properties f;

    public d(InputStream input, OutputStream output, String uri, String host, int port, Properties customWebSocketHeaders) {
        this.a = input;
        this.b = output;
        this.c = uri;
        this.d = host;
        this.e = port;
        this.f = customWebSocketHeaders;
    }

    public void a() {
        byte[] key = new byte[16];
        System.arraycopy(UUID.randomUUID().toString().getBytes(), 0, key, 0, 16);
        String b64Key = a.b(key);
        d(b64Key);
        c(b64Key);
    }

    private void d(String key) {
        String path = "/mqtt";
        try {
            URI srvUri = new URI(this.c);
            if (srvUri.getRawPath() != null && !srvUri.getRawPath().isEmpty()) {
                path = srvUri.getRawPath();
                if (srvUri.getRawQuery() != null && !srvUri.getRawQuery().isEmpty()) {
                    path = String.valueOf(path) + "?" + srvUri.getRawQuery();
                }
            }
            PrintWriter pw = new PrintWriter(this.b);
            pw.print("GET " + path + " HTTP/1.1" + "\r\n");
            if (this.e != 80) {
                pw.print("Host: " + this.d + ":" + this.e + "\r\n");
            } else {
                pw.print("Host: " + this.d + "\r\n");
            }
            pw.print("Upgrade: websocket\r\n");
            pw.print("Connection: Upgrade\r\n");
            pw.print("Sec-WebSocket-Key: " + key + "\r\n");
            pw.print("Sec-WebSocket-Protocol: mqtt\r\n");
            pw.print("Sec-WebSocket-Version: 13\r\n");
            Properties properties = this.f;
            if (properties != null) {
                for (String k : properties.keySet()) {
                    pw.print(String.valueOf(k) + ": " + this.f.getProperty(k) + "\r\n");
                }
            }
            String userInfo = srvUri.getUserInfo();
            if (userInfo != null) {
                pw.print("Authorization: Basic " + a.a(userInfo) + "\r\n");
            }
            pw.print("\r\n");
            pw.flush();
        } catch (URISyntaxException e2) {
            throw new IllegalStateException(e2.getMessage());
        }
    }

    private void c(String key) {
        BufferedReader in = new BufferedReader(new InputStreamReader(this.a));
        ArrayList<String> responseLines = new ArrayList<>();
        String line = in.readLine();
        if (line != null) {
            while (!line.equals("")) {
                responseLines.add(line);
                line = in.readLine();
            }
            Map<String, String> headerMap = b(responseLines);
            String connectionHeader = headerMap.get("connection");
            if (connectionHeader == null || connectionHeader.equalsIgnoreCase("upgrade")) {
                throw new IOException("WebSocket Response header: Incorrect connection header");
            }
            String upgradeHeader = headerMap.get("upgrade");
            if (upgradeHeader == null || !upgradeHeader.toLowerCase().contains(UpgradeRequest.WEBSOCKET)) {
                throw new IOException("WebSocket Response header: Incorrect upgrade.");
            } else if (headerMap.get("sec-websocket-protocol") == null) {
                throw new IOException("WebSocket Response header: empty sec-websocket-protocol");
            } else if (headerMap.containsKey("sec-websocket-accept")) {
                try {
                    f(key, headerMap.get("sec-websocket-accept"));
                } catch (NoSuchAlgorithmException e2) {
                    throw new IOException(e2.getMessage());
                } catch (HandshakeFailedException e3) {
                    throw new IOException("WebSocket Response header: Incorrect Sec-WebSocket-Key");
                }
            } else {
                throw new IOException("WebSocket Response header: Missing Sec-WebSocket-Accept");
            }
        } else {
            throw new IOException("WebSocket Response header: Invalid response from Server, It may not support WebSockets.");
        }
    }

    private Map<String, String> b(ArrayList<String> headers) {
        Map<String, String> headerMap = new HashMap<>();
        for (int i = 1; i < headers.size(); i++) {
            String[] header = headers.get(i).split(":");
            headerMap.put(header[0].toLowerCase(), header[1]);
        }
        return headerMap;
    }

    private void f(String key, String accept) {
        if (!a.b(e(String.valueOf(key) + UpgradeRequest.SERVER_KEY_HASH)).trim().equals(accept.trim())) {
            throw new HandshakeFailedException();
        }
    }

    private byte[] e(String input) {
        return MessageDigest.getInstance("SHA1").digest(input.getBytes());
    }
}
