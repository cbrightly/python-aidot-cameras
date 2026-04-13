package org.glassfish.grizzly.http.server.accesslog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;

public class RotatingFileAppender implements AccessLogAppender {
    private static final Logger LOGGER = Grizzly.logger(HttpServer.class);
    private FileAppender appender;
    private final SimpleDateFormatThreadLocal archiveFormat;
    private boolean closed;
    private File currentArchive;
    private File currentFile;
    private final File directory;
    private final SimpleDateFormatThreadLocal fileFormat;

    public RotatingFileAppender(File directory2, String filePattern) {
        this(filePattern, filePattern, directory2);
        Logger logger = LOGGER;
        logger.fine("Creating rotating log appender in \"" + directory2 + "\" with file pattern \"" + filePattern + "\"");
    }

    public RotatingFileAppender(File directory2, String fileName, String archivePattern) {
        this(escape(fileName), archivePattern, directory2);
        Logger logger = LOGGER;
        logger.fine("Creating rotating log appender in \"" + directory2 + "\" writing to \"" + fileName + "\" and archive pattern \"" + archivePattern + "\"");
    }

    private static String escape(String fileName) {
        if (fileName != null) {
            return "'" + fileName.replace("'", "''") + "'";
        }
        throw new NullPointerException("Null file name");
    }

    private RotatingFileAppender(String filePattern, String archivePattern, File directory2) {
        File canonicalFile = directory2.getCanonicalFile();
        this.directory = canonicalFile;
        SimpleDateFormatThreadLocal simpleDateFormatThreadLocal = new SimpleDateFormatThreadLocal(archivePattern);
        this.archiveFormat = simpleDateFormatThreadLocal;
        SimpleDateFormatThreadLocal simpleDateFormatThreadLocal2 = new SimpleDateFormatThreadLocal(filePattern);
        this.fileFormat = simpleDateFormatThreadLocal2;
        Date now = new Date();
        this.currentArchive = new File(directory2, ((SimpleDateFormat) simpleDateFormatThreadLocal.get()).format(now)).getCanonicalFile();
        this.currentFile = new File(directory2, ((SimpleDateFormat) simpleDateFormatThreadLocal2.get()).format(now)).getCanonicalFile();
        if (!canonicalFile.equals(this.currentArchive.getParentFile())) {
            throw new IllegalArgumentException("Archive file \"" + this.currentArchive + "\" is not a child of the configured directory \"" + canonicalFile + "\"");
        } else if (!canonicalFile.equals(this.currentFile.getParentFile())) {
            throw new IllegalArgumentException("Access log file \"" + this.currentFile + "\" is not a child of the configured directory \"" + canonicalFile + "\"");
        } else if (!this.currentArchive.equals(this.currentFile)) {
            this.appender = new FileAppender(this.currentFile, true);
        } else {
            throw new IllegalArgumentException("Access log file and archive file point to the same file \"" + this.currentFile + "\"");
        }
    }

    public void append(String accessLogEntry) {
        if (!this.closed) {
            Date date = new Date();
            synchronized (this) {
                File archive = new File(this.directory, ((SimpleDateFormat) this.archiveFormat.get()).format(date));
                if (!archive.equals(this.currentArchive)) {
                    try {
                        this.appender.close();
                        if (!this.currentFile.equals(this.currentArchive)) {
                            Logger logger = LOGGER;
                            logger.info("Archiving \"" + this.currentFile + "\" to \"" + this.currentArchive + "\"");
                            if (!this.currentFile.renameTo(this.currentArchive)) {
                                throw new IOException("Unable to rename \"" + this.currentFile + "\" to \"" + this.currentArchive + "\"");
                            }
                        }
                        this.currentArchive = archive;
                        this.currentFile = new File(this.directory, ((SimpleDateFormat) this.fileFormat.get()).format(date));
                        this.appender = new FileAppender(this.currentFile, true);
                    } catch (IOException exception) {
                        LOGGER.log(Level.WARNING, "I/O error rotating access log file", exception);
                    }
                }
                this.appender.append(accessLogEntry);
            }
        }
    }

    public void close() {
        this.closed = true;
        this.appender.close();
    }
}
