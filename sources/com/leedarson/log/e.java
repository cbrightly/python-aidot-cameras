package com.leedarson.log;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import timber.log.a;

/* compiled from: FileUtils */
public class e {
    public static ChangeQuickRedirect changeQuickRedirect;

    public static String c(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 1142, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (!file.exists()) {
            return "";
        }
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = br.readLine();
                String line = readLine;
                if (readLine != null) {
                    sb.append(line);
                } else {
                    br.close();
                    fr.close();
                    return sb.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            a.d(e);
            return "";
        }
    }

    public static File d(File dir, String filename, String content) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir, filename, content}, (Object) null, changeQuickRedirect2, true, 1143, new Class[]{File.class, cls, cls}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            a.d(e);
            return null;
        }
    }

    public static File e(String abFilename, String content, boolean append) {
        Class<String> cls = String.class;
        Object[] objArr = {abFilename, content, new Byte(append ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect3, true, 1144, new Class[]{cls, cls, Boolean.TYPE}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        File file = new File(abFilename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file, append);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            a.d(e);
            return null;
        }
    }

    public static void b(File srcFile, File zipFile) {
        Class[] clsArr = {File.class, File.class};
        if (!PatchProxy.proxy(new Object[]{srcFile, zipFile}, (Object) null, changeQuickRedirect, true, 1146, clsArr, Void.TYPE).isSupported) {
            try {
                ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(zipFile));
                a(srcFile.getParent() + File.separator, srcFile.getName(), outZip);
                outZip.finish();
                outZip.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private static void a(String srcFileParentName, String srcFileName, ZipOutputStream zipOutputSteam) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, ZipOutputStream.class};
        if (!PatchProxy.proxy(new Object[]{srcFileParentName, srcFileName, zipOutputSteam}, (Object) null, changeQuickRedirect, true, 1147, clsArr, Void.TYPE).isSupported && zipOutputSteam != null) {
            File file = new File(srcFileParentName + srcFileName);
            if (file.isFile()) {
                ZipEntry zipEntry = new ZipEntry(srcFileName);
                FileInputStream inputStream = new FileInputStream(file);
                zipOutputSteam.putNextEntry(zipEntry);
                byte[] buffer = new byte[10485760];
                while (true) {
                    int read = inputStream.read(buffer);
                    int len = read;
                    if (read != -1) {
                        zipOutputSteam.write(buffer, 0, len);
                    } else {
                        zipOutputSteam.closeEntry();
                        return;
                    }
                }
            }
        }
    }
}
