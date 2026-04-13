package com.leedarson.serviceinterface.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HttpsURLConnection;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public final class FileUtils {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final String LINE_SEP = System.getProperty("line.separator");
    public static ChangeQuickRedirect changeQuickRedirect;

    public interface OnReplaceListener {
        boolean onReplace();
    }

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static File getFileByPath(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9329, new Class[]{String.class}, File.class);
        if (proxy.isSupported) {
            return (File) proxy.result;
        }
        if (isSpace(filePath)) {
            return null;
        }
        return new File(filePath);
    }

    public static boolean isFileExists(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9330, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : isFileExists(getFileByPath(filePath));
    }

    public static boolean isFileExists(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9331, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return file != null && file.exists();
    }

    public static boolean rename(String filePath, String newName) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath, newName}, (Object) null, changeQuickRedirect, true, 9332, new Class[]{cls, cls}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : rename(getFileByPath(filePath), newName);
    }

    public static boolean rename(File file, String newName) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, newName}, (Object) null, changeQuickRedirect, true, 9333, new Class[]{File.class, String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file == null || !file.exists() || isSpace(newName)) {
            return false;
        }
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        if (newFile.exists() || !file.renameTo(newFile)) {
            return false;
        }
        return true;
    }

    public static boolean isDir(String dirPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath}, (Object) null, changeQuickRedirect, true, 9334, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : isDir(getFileByPath(dirPath));
    }

    public static boolean isDir(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9335, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return file != null && file.exists() && file.isDirectory();
    }

    public static boolean isFile(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9336, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : isFile(getFileByPath(filePath));
    }

    public static boolean isFile(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9337, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return file != null && file.exists() && file.isFile();
    }

    public static boolean createOrExistsDir(String dirPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath}, (Object) null, changeQuickRedirect, true, 9338, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : createOrExistsDir(getFileByPath(dirPath));
    }

    public static boolean createOrExistsDir(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9339, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file != null) {
            if (file.exists()) {
                if (file.isDirectory()) {
                    return true;
                }
            } else if (file.mkdirs()) {
                return true;
            }
        }
        return false;
    }

    public static boolean createOrExistsFile(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9340, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : createOrExistsFile(getFileByPath(filePath));
    }

    public static boolean createOrExistsFile(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9341, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createFileByDeleteOldFile(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9342, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : createFileByDeleteOldFile(getFileByPath(filePath));
    }

    public static boolean createFileByDeleteOldFile(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9343, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file == null) {
            return false;
        }
        if ((file.exists() && !file.delete()) || !createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyDir(String srcDirPath, String destDirPath) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcDirPath, destDirPath}, (Object) null, changeQuickRedirect, true, 9344, new Class[]{cls, cls}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    public static boolean copyDir(String srcDirPath, String destDirPath, OnReplaceListener listener) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcDirPath, destDirPath, listener}, (Object) null, changeQuickRedirect, true, 9345, new Class[]{cls, cls, OnReplaceListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    public static boolean copyDir(File srcDir, File destDir) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcDir, destDir}, (Object) null, changeQuickRedirect, true, 9346, new Class[]{File.class, File.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveDir(srcDir, destDir, false);
    }

    public static boolean copyDir(File srcDir, File destDir, OnReplaceListener listener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcDir, destDir, listener}, (Object) null, changeQuickRedirect, true, 9347, new Class[]{File.class, File.class, OnReplaceListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveDir(srcDir, destDir, listener, false);
    }

    public static boolean copyFile(String srcFilePath, String destFilePath) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcFilePath, destFilePath}, (Object) null, changeQuickRedirect, true, 9348, new Class[]{cls, cls}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    public static boolean copyFile(String srcFilePath, String destFilePath, OnReplaceListener listener) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcFilePath, destFilePath, listener}, (Object) null, changeQuickRedirect, true, 9349, new Class[]{cls, cls, OnReplaceListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    public static boolean copyFile(File srcFile, File destFile) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcFile, destFile}, (Object) null, changeQuickRedirect, true, 9350, new Class[]{File.class, File.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveFile(srcFile, destFile, false);
    }

    public static boolean copyFile(File srcFile, File destFile, OnReplaceListener listener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcFile, destFile, listener}, (Object) null, changeQuickRedirect, true, 9351, new Class[]{File.class, File.class, OnReplaceListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveFile(srcFile, destFile, listener, false);
    }

    public static boolean moveDir(String srcDirPath, String destDirPath) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcDirPath, destDirPath}, (Object) null, changeQuickRedirect, true, 9352, new Class[]{cls, cls}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    public static boolean moveDir(String srcDirPath, String destDirPath, OnReplaceListener listener) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcDirPath, destDirPath, listener}, (Object) null, changeQuickRedirect, true, 9353, new Class[]{cls, cls, OnReplaceListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    public static boolean moveDir(File srcDir, File destDir) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcDir, destDir}, (Object) null, changeQuickRedirect, true, 9354, new Class[]{File.class, File.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveDir(srcDir, destDir, true);
    }

    public static boolean moveDir(File srcDir, File destDir, OnReplaceListener listener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcDir, destDir, listener}, (Object) null, changeQuickRedirect, true, 9355, new Class[]{File.class, File.class, OnReplaceListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveDir(srcDir, destDir, listener, true);
    }

    public static boolean moveFile(String srcFilePath, String destFilePath) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcFilePath, destFilePath}, (Object) null, changeQuickRedirect, true, 9356, new Class[]{cls, cls}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    public static boolean moveFile(String srcFilePath, String destFilePath, OnReplaceListener listener) {
        Class<String> cls = String.class;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcFilePath, destFilePath, listener}, (Object) null, changeQuickRedirect, true, 9357, new Class[]{cls, cls, OnReplaceListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    public static boolean moveFile(File srcFile, File destFile) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcFile, destFile}, (Object) null, changeQuickRedirect, true, 9358, new Class[]{File.class, File.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveFile(srcFile, destFile, true);
    }

    public static boolean moveFile(File srcFile, File destFile, OnReplaceListener listener) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcFile, destFile, listener}, (Object) null, changeQuickRedirect, true, 9359, new Class[]{File.class, File.class, OnReplaceListener.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveFile(srcFile, destFile, listener, true);
    }

    private static boolean copyOrMoveDir(File srcDir, File destDir, boolean isMove) {
        Object[] objArr = {srcDir, destDir, new Byte(isMove ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9360, new Class[]{File.class, File.class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveDir(srcDir, destDir, new OnReplaceListener() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public boolean onReplace() {
                return true;
            }
        }, isMove);
    }

    private static boolean copyOrMoveDir(File srcDir, File destDir, OnReplaceListener listener, boolean isMove) {
        Object[] objArr = {srcDir, destDir, listener, new Byte(isMove ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9361, new Class[]{File.class, File.class, OnReplaceListener.class, cls}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (srcDir == null || destDir == null) {
            return false;
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(srcDir.getPath());
            String str = File.separator;
            sb.append(str);
            String destPath = destDir.getPath() + str;
            if (!destPath.contains(sb.toString()) && srcDir.exists()) {
                if (srcDir.isDirectory()) {
                    if (destDir.exists()) {
                        if (listener != null) {
                            if (!listener.onReplace()) {
                                return true;
                            }
                        }
                        if (!deleteAllInDir(destDir)) {
                            return false;
                        }
                    }
                    if (!createOrExistsDir(destDir)) {
                        return false;
                    }
                    for (File file : srcDir.listFiles()) {
                        File oneDestFile = new File(destPath + file.getName());
                        if (file.isFile()) {
                            if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) {
                                return false;
                            }
                        } else if (file.isDirectory() && !copyOrMoveDir(file, oneDestFile, listener, isMove)) {
                            return false;
                        }
                    }
                    if (!isMove || deleteDir(srcDir)) {
                        return true;
                    }
                    return false;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean copyOrMoveFile(File srcFile, File destFile, boolean isMove) {
        Object[] objArr = {srcFile, destFile, new Byte(isMove ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9362, new Class[]{File.class, File.class, cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : copyOrMoveFile(srcFile, destFile, new OnReplaceListener() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public boolean onReplace() {
                return true;
            }
        }, isMove);
    }

    private static boolean copyOrMoveFile(File srcFile, File destFile, OnReplaceListener listener, boolean isMove) {
        Object[] objArr = {srcFile, destFile, listener, new Byte(isMove ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 9363, new Class[]{File.class, File.class, OnReplaceListener.class, cls}, cls);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (srcFile == null || destFile == null || srcFile.equals(destFile) || !srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        if (destFile.exists()) {
            if (listener != null && !listener.onReplace()) {
                return true;
            }
            if (!destFile.delete()) {
                return false;
            }
        }
        if (!createOrExistsDir(destFile.getParentFile())) {
            return false;
        }
        try {
            if (!writeFileFromIS(destFile, new FileInputStream(srcFile))) {
                return false;
            }
            if (!isMove || deleteFile(srcFile)) {
                return true;
            }
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9364, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : delete(getFileByPath(filePath));
    }

    public static boolean delete(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9365, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file == null) {
            return false;
        }
        if (file.isDirectory()) {
            return deleteDir(file);
        }
        return deleteFile(file);
    }

    public static boolean deleteDir(String dirPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath}, (Object) null, changeQuickRedirect, true, 9366, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : deleteDir(getFileByPath(dirPath));
    }

    public static boolean deleteDir(File dir) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir}, (Object) null, changeQuickRedirect, true, 9367, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory() && !deleteDir(file)) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static boolean deleteFile(String srcFilePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{srcFilePath}, (Object) null, changeQuickRedirect, true, 9368, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : deleteFile(getFileByPath(srcFilePath));
    }

    public static boolean deleteFile(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9369, new Class[]{File.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (file != null) {
            if (!file.exists()) {
                return true;
            }
            if (file.isFile() && file.delete()) {
                return true;
            }
        }
        return false;
    }

    public static boolean deleteAllInDir(String dirPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath}, (Object) null, changeQuickRedirect, true, 9370, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : deleteAllInDir(getFileByPath(dirPath));
    }

    public static boolean deleteAllInDir(File dir) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir}, (Object) null, changeQuickRedirect, true, 9371, new Class[]{File.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : deleteFilesInDirWithFilter(dir, (FileFilter) new FileFilter() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    public static boolean deleteFilesInDir(String dirPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath}, (Object) null, changeQuickRedirect, true, 9372, new Class[]{String.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : deleteFilesInDir(getFileByPath(dirPath));
    }

    public static boolean deleteFilesInDir(File dir) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir}, (Object) null, changeQuickRedirect, true, 9373, new Class[]{File.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : deleteFilesInDirWithFilter(dir, (FileFilter) new FileFilter() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public boolean accept(File pathname) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{pathname}, this, changeQuickRedirect, false, 9416, new Class[]{File.class}, Boolean.TYPE);
                if (proxy.isSupported) {
                    return ((Boolean) proxy.result).booleanValue();
                }
                return pathname.isFile();
            }
        });
    }

    public static boolean deleteFilesInDirWithFilter(String dirPath, FileFilter filter) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath, filter}, (Object) null, changeQuickRedirect, true, 9374, new Class[]{String.class, FileFilter.class}, Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    public static boolean deleteFilesInDirWithFilter(File dir, FileFilter filter) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir, filter}, (Object) null, changeQuickRedirect, true, 9375, new Class[]{File.class, FileFilter.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) {
                            return false;
                        }
                    } else if (file.isDirectory() && !deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static List<File> listFilesInDir(String dirPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath}, (Object) null, changeQuickRedirect, true, 9376, new Class[]{String.class}, List.class);
        return proxy.isSupported ? (List) proxy.result : listFilesInDir(dirPath, false);
    }

    public static List<File> listFilesInDir(File dir) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir}, (Object) null, changeQuickRedirect, true, 9377, new Class[]{File.class}, List.class);
        return proxy.isSupported ? (List) proxy.result : listFilesInDir(dir, false);
    }

    public static List<File> listFilesInDir(String dirPath, boolean isRecursive) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath, new Byte(isRecursive ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 9378, new Class[]{String.class, Boolean.TYPE}, List.class);
        return proxy.isSupported ? (List) proxy.result : listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    public static List<File> listFilesInDir(File dir, boolean isRecursive) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir, new Byte(isRecursive ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 9379, new Class[]{File.class, Boolean.TYPE}, List.class);
        return proxy.isSupported ? (List) proxy.result : listFilesInDirWithFilter(dir, (FileFilter) new FileFilter() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    public static List<File> listFilesInDirWithFilter(String dirPath, FileFilter filter) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath, filter}, (Object) null, changeQuickRedirect, true, 9380, new Class[]{String.class, FileFilter.class}, List.class);
        return proxy.isSupported ? (List) proxy.result : listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
    }

    public static List<File> listFilesInDirWithFilter(File dir, FileFilter filter) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir, filter}, (Object) null, changeQuickRedirect, true, 9381, new Class[]{File.class, FileFilter.class}, List.class);
        return proxy.isSupported ? (List) proxy.result : listFilesInDirWithFilter(dir, filter, false);
    }

    public static List<File> listFilesInDirWithFilter(String dirPath, FileFilter filter, boolean isRecursive) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath, filter, new Byte(isRecursive ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 9382, new Class[]{String.class, FileFilter.class, Boolean.TYPE}, List.class);
        return proxy.isSupported ? (List) proxy.result : listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    public static List<File> listFilesInDirWithFilter(File dir, FileFilter filter, boolean isRecursive) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir, filter, new Byte(isRecursive ? (byte) 1 : 0)}, (Object) null, changeQuickRedirect, true, 9383, new Class[]{File.class, FileFilter.class, Boolean.TYPE}, List.class);
        if (proxy.isSupported) {
            return (List) proxy.result;
        }
        if (!isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                if (filter.accept(file)) {
                    list.add(file);
                }
                if (isRecursive && file.isDirectory()) {
                    list.addAll(listFilesInDirWithFilter(file, filter, true));
                }
            }
        }
        return list;
    }

    public static long getFileLastModified(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9384, new Class[]{String.class}, Long.TYPE);
        return proxy.isSupported ? ((Long) proxy.result).longValue() : getFileLastModified(getFileByPath(filePath));
    }

    public static long getFileLastModified(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9385, new Class[]{File.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        if (file == null) {
            return -1;
        }
        return file.lastModified();
    }

    public static String getFileCharsetSimple(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9386, new Class[]{String.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : getFileCharsetSimple(getFileByPath(filePath));
    }

    public static String getFileCharsetSimple(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9387, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        int p = 0;
        InputStream is = null;
        try {
            InputStream is2 = new BufferedInputStream(new FileInputStream(file));
            p = (is2.read() << 8) + is2.read();
            try {
                is2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
        switch (p) {
            case 61371:
                return "UTF-8";
            case 65279:
                return "UTF-16BE";
            case 65534:
                return "Unicode";
            default:
                return "GBK";
        }
    }

    public static int getFileLines(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9388, new Class[]{String.class}, Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : getFileLines(getFileByPath(filePath));
    }

    public static int getFileLines(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9389, new Class[]{File.class}, Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int count = 1;
        InputStream is = null;
        try {
            InputStream is2 = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            String str = LINE_SEP;
            if (TextUtils.isEmpty(str) || !str.endsWith("\n")) {
                while (true) {
                    int read = is2.read(buffer, 0, 1024);
                    int readChars = read;
                    if (read != -1) {
                        for (int i = 0; i < readChars; i++) {
                            if (buffer[i] == 13) {
                                count++;
                            }
                        }
                    }
                }
                is2.close();
                return count;
            }
            while (true) {
                int read2 = is2.read(buffer, 0, 1024);
                int readChars2 = read2;
                if (read2 != -1) {
                    for (int i2 = 0; i2 < readChars2; i2++) {
                        if (buffer[i2] == 10) {
                            count++;
                        }
                    }
                }
            }
            is2.close();
            return count;
            try {
                is2.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (is != null) {
                is.close();
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            throw th;
        }
        return count;
    }

    public static String getDirSize(String dirPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath}, (Object) null, changeQuickRedirect, true, 9390, new Class[]{String.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : getDirSize(getFileByPath(dirPath));
    }

    public static String getDirSize(File dir) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir}, (Object) null, changeQuickRedirect, true, 9391, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    public static String getFileSize(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9392, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        long len = getFileLength(filePath);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    public static String getFileSize(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9393, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        long len = getFileLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    public static long getDirLength(String dirPath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dirPath}, (Object) null, changeQuickRedirect, true, 9394, new Class[]{String.class}, Long.TYPE);
        return proxy.isSupported ? ((Long) proxy.result).longValue() : getDirLength(getFileByPath(dirPath));
    }

    public static long getDirLength(File dir) {
        long j;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{dir}, (Object) null, changeQuickRedirect, true, 9395, new Class[]{File.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        if (!isDir(dir)) {
            return -1;
        }
        long len = 0;
        File[] files = dir.listFiles();
        if (!(files == null || files.length == 0)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    j = getDirLength(file);
                } else {
                    j = file.length();
                }
                len += j;
            }
        }
        return len;
    }

    public static long getFileLength(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9396, new Class[]{String.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        if (filePath.matches("[a-zA-z]+://[^\\s]*")) {
            try {
                HttpsURLConnection conn = (HttpsURLConnection) new URL(filePath).openConnection();
                conn.setRequestProperty(HttpHeaders.HEAD_KEY_ACCEPT_ENCODING, "identity");
                conn.connect();
                if (conn.getResponseCode() == 200) {
                    return (long) conn.getContentLength();
                }
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getFileLength(getFileByPath(filePath));
    }

    public static long getFileLength(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9397, new Class[]{File.class}, Long.TYPE);
        if (proxy.isSupported) {
            return ((Long) proxy.result).longValue();
        }
        if (!isFile(file)) {
            return -1;
        }
        return file.length();
    }

    public static String getFileMD5ToString(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9398, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return getFileMD5ToString(isSpace(filePath) ? null : new File(filePath));
    }

    public static String getFileMD5ToString(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9399, new Class[]{File.class}, String.class);
        return proxy.isSupported ? (String) proxy.result : bytes2HexString(getFileMD5(file));
    }

    public static byte[] getFileMD5(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9400, new Class[]{String.class}, byte[].class);
        return proxy.isSupported ? (byte[]) proxy.result : getFileMD5(getFileByPath(filePath));
    }

    public static byte[] getFileMD5(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9401, new Class[]{File.class}, byte[].class);
        if (proxy.isSupported) {
            return (byte[]) proxy.result;
        }
        if (file == null) {
            return null;
        }
        DigestInputStream dis = null;
        try {
            DigestInputStream dis2 = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance("MD5"));
            do {
            } while (dis2.read(new byte[262144]) > 0);
            byte[] digest = dis2.getMessageDigest().digest();
            try {
                dis2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return digest;
        } catch (IOException | NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public static String getDirName(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9402, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (file == null) {
            return "";
        }
        return getDirName(file.getAbsolutePath());
    }

    public static String getDirName(String filePath) {
        int lastSep;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9403, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (!isSpace(filePath) && (lastSep = filePath.lastIndexOf(File.separator)) != -1) {
            return filePath.substring(0, lastSep + 1);
        }
        return "";
    }

    public static String getFileName(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9404, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (file == null) {
            return "";
        }
        return getFileName(file.getAbsolutePath());
    }

    public static String getFileName(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9405, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (isSpace(filePath)) {
            return "";
        }
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    public static String getFileNameNoExtension(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9406, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (file == null) {
            return "";
        }
        return getFileNameNoExtension(file.getPath());
    }

    public static String getFileNameNoExtension(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9407, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (isSpace(filePath)) {
            return "";
        }
        int lastPoi = filePath.lastIndexOf(46);
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return lastPoi == -1 ? filePath : filePath.substring(0, lastPoi);
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }

    public static String getFileExtension(File file) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file}, (Object) null, changeQuickRedirect, true, 9408, new Class[]{File.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (file == null) {
            return "";
        }
        return getFileExtension(file.getPath());
    }

    public static String getFileExtension(String filePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{filePath}, (Object) null, changeQuickRedirect, true, 9409, new Class[]{String.class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (isSpace(filePath)) {
            return "";
        }
        int lastPoi = filePath.lastIndexOf(46);
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) {
            return "";
        }
        return filePath.substring(lastPoi + 1);
    }

    public static void notifySystemToScan(File file, Context context) {
        Class[] clsArr = {File.class, Context.class};
        if (!PatchProxy.proxy(new Object[]{file, context}, (Object) null, changeQuickRedirect, true, 9410, clsArr, Void.TYPE).isSupported && file != null && file.exists()) {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(file));
            context.sendBroadcast(intent);
        }
    }

    public static void notifySystemToScan(String filePath, Context context) {
        Class[] clsArr = {String.class, Context.class};
        if (!PatchProxy.proxy(new Object[]{filePath, context}, (Object) null, changeQuickRedirect, true, 9411, clsArr, Void.TYPE).isSupported) {
            notifySystemToScan(getFileByPath(filePath), context);
        }
    }

    public static String bytes2HexString(byte[] bytes) {
        int len;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{bytes}, (Object) null, changeQuickRedirect, true, 9412, new Class[]{byte[].class}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (bytes == null || (len = bytes.length) <= 0) {
            return "";
        }
        char[] ret = new char[(len << 1)];
        int j = 0;
        for (int i = 0; i < len; i++) {
            int j2 = j + 1;
            char[] cArr = HEX_DIGITS;
            ret[j] = cArr[(bytes[i] >> 4) & 15];
            j = j2 + 1;
            ret[j2] = cArr[bytes[i] & 15];
        }
        return new String(ret);
    }

    private static String byte2FitMemorySize(long byteNum) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Long(byteNum)}, (Object) null, changeQuickRedirect, true, 9413, new Class[]{Long.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        }
        if (byteNum < 1024) {
            return String.format(Locale.US, "%.3fB", new Object[]{Double.valueOf((double) byteNum)});
        } else if (byteNum < 1048576) {
            return String.format(Locale.US, "%.3fKB", new Object[]{Double.valueOf(((double) byteNum) / 1024.0d)});
        } else if (byteNum < IjkMediaMeta.AV_CH_STEREO_RIGHT) {
            return String.format(Locale.US, "%.3fMB", new Object[]{Double.valueOf(((double) byteNum) / 1048576.0d)});
        } else {
            return String.format(Locale.US, "%.3fGB", new Object[]{Double.valueOf(((double) byteNum) / 1.073741824E9d)});
        }
    }

    private static boolean isSpace(String s) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{s}, (Object) null, changeQuickRedirect, true, 9414, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (s == null) {
            return true;
        }
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean writeFileFromIS(File file, InputStream is) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{file, is}, (Object) null, changeQuickRedirect, true, 9415, new Class[]{File.class, InputStream.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        OutputStream os = null;
        try {
            OutputStream os2 = new BufferedOutputStream(new FileOutputStream(file));
            byte[] data = new byte[8192];
            while (true) {
                int read = is.read(data, 0, 8192);
                int len = read;
                if (read != -1) {
                    os2.write(data, 0, len);
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            is.close();
            try {
                os2.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return true;
        } catch (IOException e3) {
            e3.printStackTrace();
            try {
                is.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            try {
                is.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e7) {
                    e7.printStackTrace();
                }
            }
            throw th;
        }
    }
}
