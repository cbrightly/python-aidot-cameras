package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.i;
import org.eclipse.paho.client.mqttv3.internal.j;
import org.eclipse.paho.client.mqttv3.internal.n;
import org.eclipse.paho.client.mqttv3.m;

/* compiled from: MqttDefaultFilePersistence */
public class b implements i {
    private static FilenameFilter c;
    private File d;
    private File f = null;
    private j q = null;

    private static FilenameFilter c() {
        if (c == null) {
            c = new d(".msg");
        }
        return c;
    }

    public b(String directory) {
        this.d = new File(directory);
    }

    public void H0(String clientId, String theConnection) {
        if (this.d.exists() && !this.d.isDirectory()) {
            throw new MqttPersistenceException();
        } else if (!this.d.exists() && !this.d.mkdirs()) {
            throw new MqttPersistenceException();
        } else if (this.d.canWrite()) {
            StringBuffer keyBuffer = new StringBuffer();
            for (int i = 0; i < clientId.length(); i++) {
                char c2 = clientId.charAt(i);
                if (i(c2)) {
                    keyBuffer.append(c2);
                }
            }
            keyBuffer.append("-");
            for (int i2 = 0; i2 < theConnection.length(); i2++) {
                char c3 = theConnection.charAt(i2);
                if (i(c3)) {
                    keyBuffer.append(c3);
                }
            }
            synchronized (this) {
                if (this.f == null) {
                    File file = new File(this.d, keyBuffer.toString());
                    this.f = file;
                    if (!file.exists()) {
                        this.f.mkdir();
                    }
                }
                try {
                    j jVar = this.q;
                    if (jVar != null) {
                        jVar.a();
                    }
                    this.q = new j(this.f, ".lck");
                } catch (Exception e) {
                }
                j(this.f);
            }
        } else {
            throw new MqttPersistenceException();
        }
    }

    private void a() {
        if (this.f == null) {
            throw new MqttPersistenceException();
        }
    }

    public void close() {
        synchronized (this) {
            j jVar = this.q;
            if (jVar != null) {
                jVar.a();
            }
            if (g().length == 0) {
                this.f.delete();
            }
            this.f = null;
        }
    }

    public void t0(String key, m message) {
        a();
        File file = this.f;
        File file2 = new File(file, String.valueOf(key) + ".msg");
        File file3 = this.f;
        File backupFile = new File(file3, String.valueOf(key) + ".msg" + ".bup");
        if (file2.exists() && !file2.renameTo(backupFile)) {
            backupFile.delete();
            file2.renameTo(backupFile);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file2);
            fos.write(message.d(), message.a(), message.f());
            if (message.e() != null) {
                fos.write(message.e(), message.b(), message.c());
            }
            fos.getFD().sync();
            fos.close();
            if (backupFile.exists()) {
                backupFile.delete();
            }
            if (backupFile.exists() && !backupFile.renameTo(file2)) {
                file2.delete();
                backupFile.renameTo(file2);
            }
        } catch (IOException ex) {
            throw new MqttPersistenceException((Throwable) ex);
        } catch (Throwable th) {
            if (backupFile.exists() && !backupFile.renameTo(file2)) {
                file2.delete();
                backupFile.renameTo(file2);
            }
            throw th;
        }
    }

    public m get(String key) {
        a();
        try {
            FileInputStream fis = new FileInputStream(new File(this.f, String.valueOf(key) + ".msg"));
            int size = fis.available();
            byte[] data = new byte[size];
            for (int read = 0; read < size; read += fis.read(data, read, size - read)) {
            }
            fis.close();
            return new n(key, data, 0, data.length, (byte[]) null, 0, 0);
        } catch (IOException ex) {
            throw new MqttPersistenceException((Throwable) ex);
        }
    }

    public void remove(String key) {
        a();
        File file = this.f;
        File file2 = new File(file, String.valueOf(key) + ".msg");
        if (file2.exists()) {
            file2.delete();
        }
    }

    public Enumeration<String> keys() {
        a();
        File[] files = g();
        Vector<String> result = new Vector<>(files.length);
        for (File file : files) {
            String filename = file.getName();
            result.addElement(filename.substring(0, filename.length() - ".msg".length()));
        }
        return result.elements();
    }

    private File[] g() {
        a();
        File[] files = this.f.listFiles(c());
        if (files != null) {
            return files;
        }
        throw new MqttPersistenceException();
    }

    private boolean i(char c2) {
        return Character.isJavaIdentifierPart(c2) || c2 == '-';
    }

    private void j(File dir) {
        File[] files = dir.listFiles(new c(".bup"));
        if (files != null) {
            for (File file : files) {
                File originalFile = new File(dir, file.getName().substring(0, file.getName().length() - ".bup".length()));
                if (!file.renameTo(originalFile)) {
                    originalFile.delete();
                    file.renameTo(originalFile);
                }
            }
            return;
        }
        throw new MqttPersistenceException();
    }

    public boolean U0(String key) {
        a();
        File file = this.f;
        return new File(file, String.valueOf(key) + ".msg").exists();
    }

    public void clear() {
        a();
        for (File file : g()) {
            file.delete();
        }
        this.f.delete();
    }
}
