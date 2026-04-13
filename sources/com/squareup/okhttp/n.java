package com.squareup.okhttp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/* compiled from: Dns */
public interface n {
    public static final n a = new a();

    List<InetAddress> lookup(String str);

    /* compiled from: Dns */
    public static final class a implements n {
        a() {
        }

        public List<InetAddress> lookup(String hostname) {
            if (hostname != null) {
                return Arrays.asList(InetAddress.getAllByName(hostname));
            }
            throw new UnknownHostException("hostname == null");
        }
    }
}
