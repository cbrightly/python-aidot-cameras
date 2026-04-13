package org.apache.commons.fileupload.util.mime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* compiled from: MimeUtility */
public final class b {
    private static final Map<String, String> a;

    static {
        HashMap hashMap = new HashMap();
        a = hashMap;
        hashMap.put("iso-2022-cn", "ISO2022CN");
        hashMap.put("iso-2022-kr", "ISO2022KR");
        hashMap.put("utf-8", "UTF8");
        hashMap.put("utf8", "UTF8");
        hashMap.put("ja_jp.iso2022-7", "ISO2022JP");
        hashMap.put("ja_jp.eucjp", "EUCJIS");
        hashMap.put("euc-kr", "KSC5601");
        hashMap.put("euckr", "KSC5601");
        hashMap.put("us-ascii", "ISO-8859-1");
        hashMap.put("x-us-ascii", "ISO-8859-1");
    }

    public static String a(String text) {
        if (text.indexOf("=?") < 0) {
            return text;
        }
        int offset = 0;
        int endOffset = text.length();
        int startWhiteSpace = -1;
        int endWhiteSpace = -1;
        StringBuilder decodedText = new StringBuilder(text.length());
        boolean previousTokenEncoded = false;
        while (offset < endOffset) {
            if (" \t\r\n".indexOf(text.charAt(offset)) != -1) {
                startWhiteSpace = offset;
                while (true) {
                    if (offset < endOffset) {
                        if (" \t\r\n".indexOf(text.charAt(offset)) == -1) {
                            endWhiteSpace = offset;
                            break;
                        }
                        offset++;
                    } else {
                        break;
                    }
                }
            } else {
                int wordStart = offset;
                while (offset < endOffset && " \t\r\n".indexOf(text.charAt(offset)) == -1) {
                    offset++;
                }
                String word = text.substring(wordStart, offset);
                if (word.startsWith("=?")) {
                    try {
                        String decodedWord = b(word);
                        if (!previousTokenEncoded && startWhiteSpace != -1) {
                            decodedText.append(text.substring(startWhiteSpace, endWhiteSpace));
                            startWhiteSpace = -1;
                        }
                        previousTokenEncoded = true;
                        decodedText.append(decodedWord);
                    } catch (ParseException e) {
                    }
                }
                if (startWhiteSpace != -1) {
                    decodedText.append(text.substring(startWhiteSpace, endWhiteSpace));
                    startWhiteSpace = -1;
                }
                previousTokenEncoded = false;
                decodedText.append(word);
            }
        }
        return decodedText.toString();
    }

    private static String b(String word) {
        if (word.startsWith("=?")) {
            int charsetPos = word.indexOf(63, 2);
            if (charsetPos != -1) {
                String charset = word.substring(2, charsetPos).toLowerCase(Locale.ENGLISH);
                int encodingPos = word.indexOf(63, charsetPos + 1);
                if (encodingPos != -1) {
                    String encoding = word.substring(charsetPos + 1, encodingPos);
                    int encodedTextPos = word.indexOf("?=", encodingPos + 1);
                    if (encodedTextPos != -1) {
                        String encodedText = word.substring(encodingPos + 1, encodedTextPos);
                        if (encodedText.length() == 0) {
                            return "";
                        }
                        try {
                            ByteArrayOutputStream out = new ByteArrayOutputStream(encodedText.length());
                            byte[] encodedData = encodedText.getBytes("US-ASCII");
                            if (encoding.equals("B")) {
                                a.a(encodedData, out);
                            } else if (encoding.equals("Q")) {
                                c.a(encodedData, out);
                            } else {
                                throw new UnsupportedEncodingException("Unknown RFC 2047 encoding: " + encoding);
                            }
                            return new String(out.toByteArray(), c(charset));
                        } catch (IOException e) {
                            throw new UnsupportedEncodingException("Invalid RFC 2047 encoding");
                        }
                    } else {
                        throw new ParseException("Missing encoded text in RFC 2047 encoded-word: " + word);
                    }
                } else {
                    throw new ParseException("Missing encoding in RFC 2047 encoded-word: " + word);
                }
            } else {
                throw new ParseException("Missing charset in RFC 2047 encoded-word: " + word);
            }
        } else {
            throw new ParseException("Invalid RFC 2047 encoded-word: " + word);
        }
    }

    private static String c(String charset) {
        if (charset == null) {
            return null;
        }
        String mappedCharset = a.get(charset.toLowerCase(Locale.ENGLISH));
        if (mappedCharset == null) {
            return charset;
        }
        return mappedCharset;
    }
}
