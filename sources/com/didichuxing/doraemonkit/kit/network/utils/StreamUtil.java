package com.didichuxing.doraemonkit.kit.network.utils;

import android.util.Pair;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StreamUtil {
    public static void copy(InputStream input, OutputStream output, byte[] buffer) {
        while (true) {
            int read = input.read(buffer);
            int n = read;
            if (read != -1) {
                output.write(buffer, 0, n);
            } else {
                return;
            }
        }
    }

    public static void close(Closeable closeable, boolean hideException) {
        if (closeable == null) {
            return;
        }
        if (hideException) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            closeable.close();
        }
    }

    public static ArrayList<Pair<String, String>> convertHeaders(Map<String, List<String>> map) {
        ArrayList<Pair<String, String>> array = new ArrayList<>();
        if (map == null) {
            return array;
        }
        for (Map.Entry<String, List<String>> mapEntry : map.entrySet()) {
            for (String mapEntryValue : mapEntry.getValue()) {
                if (mapEntry.getKey() != null) {
                    array.add(Pair.create(mapEntry.getKey(), mapEntryValue));
                }
            }
        }
        return array;
    }
}
