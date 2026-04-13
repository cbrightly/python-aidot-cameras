package org.glassfish.grizzly.http.util;

import com.amazonaws.regions.ServiceAbbreviations;
import com.didichuxing.doraemonkit.util.FileUtil;
import com.didichuxing.doraemonkit.util.SystemUtil;
import com.yanzhenjie.andserver.util.h;
import java.util.HashMap;
import java.util.Map;

public class MimeType {
    private static final Map<String, String> contentTypes;

    static {
        HashMap hashMap = new HashMap();
        contentTypes = hashMap;
        hashMap.put("abs", "audio/x-mpeg");
        hashMap.put("ai", "application/postscript");
        hashMap.put("aif", "audio/x-aiff");
        hashMap.put("aifc", "audio/x-aiff");
        hashMap.put("aiff", "audio/x-aiff");
        hashMap.put("aim", "application/x-aim");
        hashMap.put("art", "image/x-jg");
        hashMap.put("asf", "video/x-ms-asf");
        hashMap.put("asx", "video/x-ms-asf");
        hashMap.put("au", "audio/basic");
        hashMap.put("avi", "video/x-msvideo");
        hashMap.put("avx", "video/x-rad-screenplay");
        hashMap.put("bcpio", "application/x-bcpio");
        hashMap.put("bin", h.APPLICATION_OCTET_STREAM_VALUE);
        hashMap.put("bmp", "image/bmp");
        hashMap.put("body", h.TEXT_HTML_VALUE);
        hashMap.put("cdf", "application/x-cdf");
        hashMap.put("cer", "application/x-x509-ca-cert");
        hashMap.put("class", "application/java");
        hashMap.put("cpio", "application/x-cpio");
        hashMap.put("csh", "application/x-csh");
        hashMap.put("css", "text/css");
        hashMap.put("dib", "image/bmp");
        hashMap.put("doc", "application/msword");
        hashMap.put("dtd", "application/xml-dtd");
        hashMap.put("dv", "video/x-dv");
        hashMap.put("dvi", "application/x-dvi");
        hashMap.put("eps", "application/postscript");
        hashMap.put("etx", "text/x-setext");
        hashMap.put("exe", h.APPLICATION_OCTET_STREAM_VALUE);
        hashMap.put("gif", h.IMAGE_GIF_VALUE);
        hashMap.put("gk", h.APPLICATION_OCTET_STREAM_VALUE);
        hashMap.put("gtar", "application/x-gtar");
        hashMap.put("gz", "application/x-gzip");
        hashMap.put("hdf", "application/x-hdf");
        hashMap.put("hqx", "application/mac-binhex40");
        hashMap.put(SystemUtil.PHONE_HTC, "text/x-component");
        hashMap.put("htm", h.TEXT_HTML_VALUE);
        hashMap.put("html", h.TEXT_HTML_VALUE);
        hashMap.put("hqx", "application/mac-binhex40");
        hashMap.put("ief", "image/ief");
        hashMap.put("jad", "text/vnd.sun.j2me.app-descriptor");
        hashMap.put("jar", "application/java-archive");
        hashMap.put("java", h.TEXT_PLAIN_VALUE);
        hashMap.put("jnlp", "application/x-java-jnlp-file");
        hashMap.put("jpe", "image/jpeg");
        hashMap.put("jpeg", "image/jpeg");
        hashMap.put(FileUtil.JPG, "image/jpeg");
        hashMap.put("js", "text/javascript");
        hashMap.put("kar", "audio/x-midi");
        hashMap.put("latex", "application/x-latex");
        hashMap.put("m3u", "audio/x-mpegurl");
        hashMap.put("mac", "image/x-macpaint");
        hashMap.put("man", "application/x-troff-man");
        hashMap.put("mathml", "application/mathml+xml");
        hashMap.put("me", "application/x-troff-me");
        hashMap.put("mid", "audio/x-midi");
        hashMap.put("midi", "audio/x-midi");
        hashMap.put("mif", "application/x-mif");
        hashMap.put("mov", "video/quicktime");
        hashMap.put("movie", "video/x-sgi-movie");
        hashMap.put("mp1", "audio/x-mpeg");
        hashMap.put("mp2", "audio/x-mpeg");
        hashMap.put("mp3", "audio/x-mpeg");
        hashMap.put("mpa", "audio/x-mpeg");
        hashMap.put("mpe", "video/mpeg");
        hashMap.put("mpeg", "video/mpeg");
        hashMap.put("mpega", "audio/x-mpeg");
        hashMap.put("mpg", "video/mpeg");
        hashMap.put("mpv2", "video/mpeg2");
        hashMap.put("ms", "application/x-wais-source");
        hashMap.put("nc", "application/x-netcdf");
        hashMap.put("oda", "application/oda");
        hashMap.put("ogg", "application/ogg");
        hashMap.put("pbm", "image/x-portable-bitmap");
        hashMap.put("pct", "image/pict");
        hashMap.put("pdf", h.APPLICATION_PDF_VALUE);
        hashMap.put("pgm", "image/x-portable-graymap");
        hashMap.put("pic", "image/pict");
        hashMap.put("pict", "image/pict");
        hashMap.put("pls", "audio/x-scpls");
        hashMap.put("png", "image/png");
        hashMap.put("pnm", "image/x-portable-anymap");
        hashMap.put("pnt", "image/x-macpaint");
        hashMap.put("ppm", "image/x-portable-pixmap");
        hashMap.put("ppt", "application/powerpoint");
        hashMap.put("ps", "application/postscript");
        hashMap.put("psd", "image/x-photoshop");
        hashMap.put("qt", "video/quicktime");
        hashMap.put("qti", "image/x-quicktime");
        hashMap.put("qtif", "image/x-quicktime");
        hashMap.put("ras", "image/x-cmu-raster");
        hashMap.put("rdf", "application/rdf+xml");
        hashMap.put("rgb", "image/x-rgb");
        hashMap.put("rm", "application/vnd.rn-realmedia");
        hashMap.put("roff", "application/x-troff");
        hashMap.put("rtf", "application/rtf");
        hashMap.put("rtx", "text/richtext");
        hashMap.put("sh", "application/x-sh");
        hashMap.put("shar", "application/x-shar");
        hashMap.put("shtml", "text/x-server-parsed-html");
        hashMap.put("sit", "application/x-stuffit");
        hashMap.put("smf", "audio/x-midi");
        hashMap.put("snd", "audio/basic");
        hashMap.put("src", "application/x-wais-source");
        hashMap.put("sv4cpio", "application/x-sv4cpio");
        hashMap.put("sv4crc", "application/x-sv4crc");
        hashMap.put("svg", "image/svg+xml");
        hashMap.put("svgz", "image/svg+xml");
        hashMap.put(ServiceAbbreviations.SimpleWorkflow, "application/x-shockwave-flash");
        hashMap.put("t", "application/x-troff");
        hashMap.put("tar", "application/x-tar");
        hashMap.put("tcl", "application/x-tcl");
        hashMap.put("tex", "application/x-tex");
        hashMap.put("texi", "application/x-texinfo");
        hashMap.put("texinfo", "application/x-texinfo");
        hashMap.put("tif", "image/tiff");
        hashMap.put("tiff", "image/tiff");
        hashMap.put("tr", "application/x-troff");
        hashMap.put("tsv", "text/tab-separated-values");
        hashMap.put(FileUtil.TXT, h.TEXT_PLAIN_VALUE);
        hashMap.put("ulw", "audio/basic");
        hashMap.put("ustar", "application/x-ustar");
        hashMap.put("xbm", "image/x-xbitmap");
        hashMap.put("xml", h.APPLICATION_XML_VALUE);
        hashMap.put("xpm", "image/x-xpixmap");
        hashMap.put("xsl", h.APPLICATION_XML_VALUE);
        hashMap.put("xslt", "application/xslt+xml");
        hashMap.put("xwd", "image/x-xwindowdump");
        hashMap.put("vsd", "application/x-visio");
        hashMap.put("vxml", "application/voicexml+xml");
        hashMap.put("wav", "audio/x-wav");
        hashMap.put("wbmp", "image/vnd.wap.wbmp");
        hashMap.put("wml", "text/vnd.wap.wml");
        hashMap.put("wmlc", "application/vnd.wap.wmlc");
        hashMap.put("wmls", "text/vnd.wap.wmls");
        hashMap.put("wmlscriptc", "application/vnd.wap.wmlscriptc");
        hashMap.put("wrl", "x-world/x-vrml");
        hashMap.put("xht", h.APPLICATION_XHTML_XML_VALUE);
        hashMap.put("xhtml", h.APPLICATION_XHTML_XML_VALUE);
        hashMap.put("xls", "application/vnd.ms-excel");
        hashMap.put("xul", "application/vnd.mozilla.xul+xml");
        hashMap.put("Z", "application/x-compress");
        hashMap.put("z", "application/x-compress");
        hashMap.put("zip", "application/zip");
    }

    public static String get(String extension) {
        return get(extension, h.TEXT_PLAIN_VALUE);
    }

    public static String get(String extension, String defaultCt) {
        String mime = contentTypes.get(extension);
        return mime == null ? defaultCt : mime;
    }

    public static boolean contains(String extension) {
        return contentTypes.containsKey(extension);
    }

    public static void add(String extension, String contentType) {
        if (extension != null && extension.length() != 0 && contentType != null && contentType.length() != 0) {
            contentTypes.put(extension, contentType);
        }
    }

    public static String getByFilename(String fileName) {
        String extn = getExtension(fileName);
        if (extn != null) {
            return get(extn);
        }
        return null;
    }

    private static String getExtension(String fileName) {
        int length = fileName.length();
        int newEnd = fileName.lastIndexOf(35);
        if (newEnd == -1) {
            newEnd = length;
        }
        int i = fileName.lastIndexOf(46, newEnd);
        if (i != -1) {
            return fileName.substring(i + 1, newEnd);
        }
        return null;
    }
}
