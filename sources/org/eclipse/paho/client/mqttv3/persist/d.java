package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FilenameFilter;

/* compiled from: PersistanceFileNameFilter */
public class d implements FilenameFilter {
    private final String a;

    public d(String fileExtension) {
        this.a = fileExtension;
    }

    public boolean accept(File dir, String name) {
        return name.endsWith(this.a);
    }
}
