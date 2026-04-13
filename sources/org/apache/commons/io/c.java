package org.apache.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;

/* compiled from: FileUtils */
public class c {
    public static final BigInteger a;
    public static final BigInteger b;
    public static final BigInteger c;
    public static final BigInteger d;
    public static final BigInteger e;
    public static final BigInteger f;
    public static final BigInteger g;
    public static final BigInteger h;
    public static final File[] i = new File[0];

    static {
        BigInteger valueOf = BigInteger.valueOf(1024);
        a = valueOf;
        BigInteger multiply = valueOf.multiply(valueOf);
        b = multiply;
        BigInteger multiply2 = valueOf.multiply(multiply);
        c = multiply2;
        BigInteger multiply3 = valueOf.multiply(multiply2);
        d = multiply3;
        BigInteger multiply4 = valueOf.multiply(multiply3);
        e = multiply4;
        f = valueOf.multiply(multiply4);
        BigInteger multiply5 = BigInteger.valueOf(1024).multiply(BigInteger.valueOf(1152921504606846976L));
        g = multiply5;
        h = valueOf.multiply(multiply5);
    }

    public static void c(File srcFile, File destFile) {
        d(srcFile, destFile, true);
    }

    public static void d(File srcFile, File destFile, boolean preserveFileDate) {
        a(srcFile, destFile);
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        } else if (!srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            File parentFile = destFile.getParentFile();
            if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
            } else if (!destFile.exists() || destFile.canWrite()) {
                g(srcFile, destFile, preserveFileDate);
            } else {
                throw new IOException("Destination '" + destFile + "' exists but is read-only");
            }
        } else {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }
    }

    private static void g(File srcFile, File destFile, boolean preserveFileDate) {
        Throwable input;
        Throwable fos;
        Throwable output;
        Throwable th;
        File file = srcFile;
        File file2 = destFile;
        if (!destFile.exists() || !destFile.isDirectory()) {
            FileInputStream fis = new FileInputStream(file);
            try {
                th = fis.getChannel();
                try {
                    th = new FileOutputStream(file2);
                    try {
                        th = fos.getChannel();
                        try {
                            long size = input.size();
                            long pos = 0;
                            while (pos < size) {
                                long remain = size - pos;
                                long count = remain > 31457280 ? 31457280 : remain;
                                long bytesCopied = output.transferFrom(input, pos, count);
                                if (bytesCopied == 0) {
                                    break;
                                }
                                pos += bytesCopied;
                                long j = count;
                            }
                            if (output != null) {
                                output.close();
                            }
                            fos.close();
                            input.close();
                            fis.close();
                            long srcLen = srcFile.length();
                            long dstLen = destFile.length();
                            if (srcLen != dstLen) {
                                throw new IOException("Failed to copy full contents from '" + file + "' to '" + file2 + "' Expected length: " + srcLen + " Actual: " + dstLen);
                            } else if (preserveFileDate) {
                                file2.setLastModified(srcFile.lastModified());
                            }
                        } catch (Throwable th2) {
                            Throwable th3 = th2;
                            if (output != null) {
                                output.close();
                            }
                            throw th3;
                        }
                    } catch (Throwable th4) {
                        th.addSuppressed(th4);
                    } finally {
                        output = th4;
                        try {
                        } catch (Throwable th5) {
                            Throwable th6 = th5;
                            fos.close();
                            throw th6;
                        }
                    }
                } catch (Throwable th7) {
                    output.addSuppressed(th7);
                } finally {
                    fos = th7;
                    try {
                    } catch (Throwable th8) {
                        Throwable th9 = th8;
                        if (input != null) {
                            input.close();
                        }
                        throw th9;
                    }
                }
            } catch (Throwable th10) {
                fos.addSuppressed(th10);
            } finally {
                input = th10;
                try {
                } catch (Throwable th11) {
                    Throwable th12 = th11;
                    try {
                        fis.close();
                    } catch (Throwable th13) {
                        input.addSuppressed(th13);
                    }
                    throw th12;
                }
            }
        } else {
            throw new IOException("Destination '" + file2 + "' exists but is a directory");
        }
    }

    private static void a(File src, File dest) {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        } else if (dest == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!src.exists()) {
            throw new FileNotFoundException("Source '" + src + "' does not exist");
        }
    }

    public static void e(File directory) {
        if (directory.exists()) {
            if (!k(directory)) {
                b(directory);
            }
            if (!directory.delete()) {
                throw new IOException("Unable to delete directory " + directory + ".");
            }
        }
    }

    public static boolean f(File file) {
        if (file == null) {
            return false;
        }
        try {
            if (file.isDirectory()) {
                b(file);
            }
        } catch (Exception e2) {
        }
        try {
            return file.delete();
        } catch (Exception e3) {
            return false;
        }
    }

    public static void b(File directory) {
        IOException exception = null;
        for (File file : m(directory)) {
            try {
                h(file);
            } catch (IOException ioe) {
                exception = ioe;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    private static File[] m(File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        } else if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                return files;
            }
            throw new IOException("Failed to list contents of " + directory);
        } else {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
    }

    public static void h(File file) {
        if (file.isDirectory()) {
            e(file);
            return;
        }
        boolean filePresent = file.exists();
        if (file.delete()) {
            return;
        }
        if (!filePresent) {
            throw new FileNotFoundException("File does not exist: " + file);
        }
        throw new IOException("Unable to delete file: " + file);
    }

    public static void i(File directory) {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                throw new IOException("File " + directory + " exists and is not a directory. Unable to create directory.");
            }
        } else if (!directory.mkdirs() && !directory.isDirectory()) {
            throw new IOException("Unable to create directory " + directory);
        }
    }

    public static void j(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            i(parent);
        }
    }

    public static void l(File srcFile, File destFile) {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        } else if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        } else if (destFile.exists()) {
            throw new FileExistsException("Destination '" + destFile + "' already exists");
        } else if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        } else if (!srcFile.renameTo(destFile)) {
            c(srcFile, destFile);
            if (!srcFile.delete()) {
                f(destFile);
                throw new IOException("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + "'");
            }
        }
    }

    public static boolean k(File file) {
        if (file != null) {
            return Files.isSymbolicLink(file.toPath());
        }
        throw new NullPointerException("File must not be null");
    }
}
