package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileFilter;

/* compiled from: PersistanceFileFilter */
public class c implements FileFilter {
    private final String a;

    public c(String fileExtension) {
        this.a = fileExtension;
    }

    public boolean accept(File pathname) {
        return pathname.getName().endsWith(this.a);
    }
}
