package com.didichuxing.doraemonkit.okgo.utils;

import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {
    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                OkLogger.printStackTrace(e);
            }
        }
    }

    public static void flushQuietly(Flushable flushable) {
        if (flushable != null) {
            try {
                flushable.flush();
            } catch (Exception e) {
                OkLogger.printStackTrace(e);
            }
        }
    }

    public static InputStream toInputStream(CharSequence input) {
        return new ByteArrayInputStream(input.toString().getBytes());
    }

    public static InputStream toInputStream(CharSequence input, String encoding) {
        return new ByteArrayInputStream(input.toString().getBytes(encoding));
    }

    public static BufferedInputStream toBufferedInputStream(InputStream inputStream) {
        return inputStream instanceof BufferedInputStream ? (BufferedInputStream) inputStream : new BufferedInputStream(inputStream);
    }

    public static BufferedOutputStream toBufferedOutputStream(OutputStream outputStream) {
        return outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream);
    }

    public static BufferedReader toBufferedReader(Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static BufferedWriter toBufferedWriter(Writer writer) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
    }

    public static String toString(InputStream input) {
        return new String(toByteArray(input));
    }

    public static String toString(InputStream input, String encoding) {
        return new String(toByteArray(input), encoding);
    }

    public static String toString(Reader input) {
        return new String(toByteArray(input));
    }

    public static String toString(Reader input, String encoding) {
        return new String(toByteArray(input), encoding);
    }

    public static String toString(byte[] byteArray) {
        return new String(byteArray);
    }

    public static String toString(byte[] byteArray, String encoding) {
        try {
            return new String(byteArray, encoding);
        } catch (UnsupportedEncodingException e) {
            return new String(byteArray);
        }
    }

    /* JADX INFO: finally extract failed */
    public static byte[] toByteArray(Object input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(input);
            oos.flush();
            byte[] byteArray = baos.toByteArray();
            closeQuietly(oos);
            closeQuietly(baos);
            return byteArray;
        } catch (IOException e) {
            OkLogger.printStackTrace(e);
            closeQuietly((Closeable) null);
            closeQuietly((Closeable) null);
            return null;
        } catch (Throwable th) {
            closeQuietly((Closeable) null);
            closeQuietly((Closeable) null);
            throw th;
        }
    }

    public static Object toObject(byte[] input) {
        if (input == null) {
            return null;
        }
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(input);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return null;
        } finally {
            closeQuietly(ois);
            closeQuietly(bais);
        }
    }

    public static byte[] toByteArray(CharSequence input) {
        if (input == null) {
            return new byte[0];
        }
        return input.toString().getBytes();
    }

    public static byte[] toByteArray(CharSequence input, String encoding) {
        if (input == null) {
            return new byte[0];
        }
        return input.toString().getBytes(encoding);
    }

    public static byte[] toByteArray(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(input, (OutputStream) output);
        output.close();
        return output.toByteArray();
    }

    public static byte[] toByteArray(Reader input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(input, (OutputStream) output);
        output.close();
        return output.toByteArray();
    }

    public static byte[] toByteArray(Reader input, String encoding) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        write(input, (OutputStream) output, encoding);
        output.close();
        return output.toByteArray();
    }

    public static char[] toCharArray(CharSequence input) {
        CharArrayWriter output = new CharArrayWriter();
        write(input, (Writer) output);
        return output.toCharArray();
    }

    public static char[] toCharArray(InputStream input) {
        CharArrayWriter output = new CharArrayWriter();
        write(input, (Writer) output);
        return output.toCharArray();
    }

    public static char[] toCharArray(InputStream input, String encoding) {
        CharArrayWriter output = new CharArrayWriter();
        write(input, (Writer) output, encoding);
        return output.toCharArray();
    }

    public static char[] toCharArray(Reader input) {
        CharArrayWriter output = new CharArrayWriter();
        write(input, (Writer) output);
        return output.toCharArray();
    }

    public static List<String> readLines(InputStream input, String encoding) {
        return readLines(new InputStreamReader(input, encoding));
    }

    public static List<String> readLines(InputStream input) {
        return readLines(new InputStreamReader(input));
    }

    public static List<String> readLines(Reader input) {
        BufferedReader reader = toBufferedReader(input);
        List<String> list = new ArrayList<>();
        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            list.add(line);
        }
        return list;
    }

    public static void write(byte[] data, OutputStream output) {
        if (data != null) {
            output.write(data);
        }
    }

    public static void write(byte[] data, Writer output) {
        if (data != null) {
            output.write(new String(data));
        }
    }

    public static void write(byte[] data, Writer output, String encoding) {
        if (data != null) {
            output.write(new String(data, encoding));
        }
    }

    public static void write(char[] data, Writer output) {
        if (data != null) {
            output.write(data);
        }
    }

    public static void write(char[] data, OutputStream output) {
        if (data != null) {
            output.write(new String(data).getBytes());
        }
    }

    public static void write(char[] data, OutputStream output, String encoding) {
        if (data != null) {
            output.write(new String(data).getBytes(encoding));
        }
    }

    public static void write(CharSequence data, Writer output) {
        if (data != null) {
            output.write(data.toString());
        }
    }

    public static void write(CharSequence data, OutputStream output) {
        if (data != null) {
            output.write(data.toString().getBytes());
        }
    }

    public static void write(CharSequence data, OutputStream output, String encoding) {
        if (data != null) {
            output.write(data.toString().getBytes(encoding));
        }
    }

    public static void write(InputStream inputStream, OutputStream outputStream) {
        byte[] buffer = new byte[4096];
        while (true) {
            int read = inputStream.read(buffer);
            int len = read;
            if (read != -1) {
                outputStream.write(buffer, 0, len);
            } else {
                return;
            }
        }
    }

    public static void write(Reader input, OutputStream output) {
        Writer out = new OutputStreamWriter(output);
        write(input, out);
        out.flush();
    }

    public static void write(InputStream input, Writer output) {
        write(new InputStreamReader(input), output);
    }

    public static void write(Reader input, OutputStream output, String encoding) {
        Writer out = new OutputStreamWriter(output, encoding);
        write(input, out);
        out.flush();
    }

    public static void write(InputStream input, OutputStream output, String encoding) {
        write(new InputStreamReader(input, encoding), output);
    }

    public static void write(InputStream input, Writer output, String encoding) {
        write(new InputStreamReader(input, encoding), output);
    }

    public static void write(Reader input, Writer output) {
        char[] buffer = new char[4096];
        while (true) {
            int read = input.read(buffer);
            int len = read;
            if (-1 != read) {
                output.write(buffer, 0, len);
            } else {
                return;
            }
        }
    }

    public static boolean contentEquals(InputStream input1, InputStream input2) {
        InputStream input12 = toBufferedInputStream(input1);
        InputStream input22 = toBufferedInputStream(input2);
        for (int ch = input12.read(); -1 != ch; ch = input12.read()) {
            if (ch != input22.read()) {
                return false;
            }
        }
        if (input22.read() == -1) {
            return true;
        }
        return false;
    }

    public static boolean contentEquals(Reader input1, Reader input2) {
        Reader input12 = toBufferedReader(input1);
        Reader input22 = toBufferedReader(input2);
        for (int ch = input12.read(); -1 != ch; ch = input12.read()) {
            if (ch != input22.read()) {
                return false;
            }
        }
        if (input22.read() == -1) {
            return true;
        }
        return false;
    }

    public static boolean contentEqualsIgnoreEOL(Reader input1, Reader input2) {
        BufferedReader br1 = toBufferedReader(input1);
        BufferedReader br2 = toBufferedReader(input2);
        String line1 = br1.readLine();
        String line2 = br2.readLine();
        while (line1 != null && line2 != null && line1.equals(line2)) {
            line1 = br1.readLine();
            line2 = br2.readLine();
        }
        return line1 != null && (line2 == null || line1.equals(line2));
    }

    public static long getDirSize(String path) {
        try {
            StatFs stat = new StatFs(path);
            if (Build.VERSION.SDK_INT >= 18) {
                return getStatFsSize(stat, "getBlockSizeLong", "getAvailableBlocksLong");
            }
            return getStatFsSize(stat, "getBlockSize", "getAvailableBlocks");
        } catch (Exception e) {
            OkLogger.printStackTrace(e);
            return 0;
        }
    }

    private static long getStatFsSize(StatFs statFs, String blockSizeMethod, String availableBlocksMethod) {
        try {
            Method getBlockSizeMethod = statFs.getClass().getMethod(blockSizeMethod, new Class[0]);
            getBlockSizeMethod.setAccessible(true);
            Method getAvailableBlocksMethod = statFs.getClass().getMethod(availableBlocksMethod, new Class[0]);
            getAvailableBlocksMethod.setAccessible(true);
            return ((Long) getBlockSizeMethod.invoke(statFs, new Object[0])).longValue() * ((Long) getAvailableBlocksMethod.invoke(statFs, new Object[0])).longValue();
        } catch (Throwable e) {
            OkLogger.printStackTrace(e);
            return 0;
        }
    }

    public static boolean canWrite(String path) {
        return new File(path).canWrite();
    }

    public static boolean canRead(String path) {
        return new File(path).canRead();
    }

    public static boolean createFolder(String folderPath) {
        if (!TextUtils.isEmpty(folderPath)) {
            return createFolder(new File(folderPath));
        }
        return false;
    }

    public static boolean createFolder(File targetFolder) {
        if (targetFolder.exists()) {
            if (targetFolder.isDirectory()) {
                return true;
            }
            targetFolder.delete();
        }
        return targetFolder.mkdirs();
    }

    public static boolean createNewFolder(String folderPath) {
        return delFileOrFolder(folderPath) && createFolder(folderPath);
    }

    public static boolean createNewFolder(File targetFolder) {
        return delFileOrFolder(targetFolder) && createFolder(targetFolder);
    }

    public static boolean createFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            return createFile(new File(filePath));
        }
        return false;
    }

    public static boolean createFile(File targetFile) {
        if (targetFile.exists()) {
            if (targetFile.isFile()) {
                return true;
            }
            delFileOrFolder(targetFile);
        }
        try {
            return targetFile.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean createNewFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            return createNewFile(new File(filePath));
        }
        return false;
    }

    public static boolean createNewFile(File targetFile) {
        if (targetFile.exists()) {
            delFileOrFolder(targetFile);
        }
        try {
            return targetFile.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean delFileOrFolder(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        return delFileOrFolder(new File(path));
    }

    public static boolean delFileOrFolder(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        if (file.isFile()) {
            file.delete();
            return true;
        } else if (!file.isDirectory()) {
            return true;
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File sonFile : files) {
                    delFileOrFolder(sonFile);
                }
            }
            file.delete();
            return true;
        }
    }
}
