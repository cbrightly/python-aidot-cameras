package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.TimeZone;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Period;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class JodaCodec implements ObjectSerializer, ContextObjectSerializer, ObjectDeserializer {
    private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormat.forPattern(defaultPatttern).withZone(DateTimeZone.getDefault());
    private static final DateTimeFormatter defaultFormatter = DateTimeFormat.forPattern(defaultPatttern);
    private static final DateTimeFormatter defaultFormatter_23 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String defaultPatttern = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter_d10_cn = DateTimeFormat.forPattern("yyyy年M月d日");
    private static final DateTimeFormatter formatter_d10_de = DateTimeFormat.forPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatter_d10_eur = DateTimeFormat.forPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatter_d10_in = DateTimeFormat.forPattern("dd-MM-yyyy");
    private static final DateTimeFormatter formatter_d10_kr = DateTimeFormat.forPattern("yyyy년M월d일");
    private static final DateTimeFormatter formatter_d10_tw = DateTimeFormat.forPattern("yyyy/MM/dd");
    private static final DateTimeFormatter formatter_d10_us = DateTimeFormat.forPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter_d8 = DateTimeFormat.forPattern("yyyyMMdd");
    private static final DateTimeFormatter formatter_dt19_cn = DateTimeFormat.forPattern("yyyy年M月d日 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn_1 = DateTimeFormat.forPattern("yyyy年M月d日 H时m分s秒");
    private static final DateTimeFormatter formatter_dt19_de = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_eur = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_in = DateTimeFormat.forPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_kr = DateTimeFormat.forPattern("yyyy년M월d일 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_tw = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_us = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_iso8601 = DateTimeFormat.forPattern(formatter_iso8601_pattern);
    private static final String formatter_iso8601_pattern = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String formatter_iso8601_pattern_23 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String formatter_iso8601_pattern_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    public static final JodaCodec instance = new JodaCodec();

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return deserialze(parser, type, fieldName, (String) null, 0);
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName, String format, int feature) {
        JSONLexer lexer = parser.lexer;
        if (lexer.token() == 8) {
            lexer.nextToken();
            return null;
        } else if (lexer.token() == 4) {
            String text = lexer.stringVal();
            lexer.nextToken();
            DateTimeFormatter formatter = null;
            if (format != null) {
                if (defaultPatttern.equals(format)) {
                    formatter = defaultFormatter;
                } else {
                    formatter = DateTimeFormat.forPattern(format);
                }
            }
            if ("".equals(text)) {
                return null;
            }
            if (type == LocalDateTime.class) {
                if (text.length() == 10 || text.length() == 8) {
                    return parseLocalDate(text, format, formatter).toLocalDateTime(LocalTime.MIDNIGHT);
                }
                return parseDateTime(text, formatter);
            } else if (type == LocalDate.class) {
                if (text.length() == 23) {
                    return LocalDateTime.parse(text).toLocalDate();
                }
                return parseLocalDate(text, format, formatter);
            } else if (type == LocalTime.class) {
                if (text.length() == 23) {
                    return LocalDateTime.parse(text).toLocalTime();
                }
                return LocalTime.parse(text);
            } else if (type == DateTime.class) {
                if (formatter == defaultFormatter) {
                    formatter = ISO_FIXED_FORMAT;
                }
                return parseZonedDateTime(text, formatter);
            } else if (type == DateTimeZone.class) {
                return DateTimeZone.forID(text);
            } else {
                if (type == Period.class) {
                    return Period.parse(text);
                }
                if (type == Duration.class) {
                    return Duration.parse(text);
                }
                if (type == Instant.class) {
                    boolean digit = true;
                    int i = 0;
                    while (true) {
                        if (i >= text.length()) {
                            break;
                        }
                        char ch = text.charAt(i);
                        if (ch < '0' || ch > '9') {
                            digit = false;
                        } else {
                            i++;
                        }
                    }
                    digit = false;
                    if (!digit || text.length() <= 8 || text.length() >= 19) {
                        return Instant.parse(text);
                    }
                    return new Instant(Long.parseLong(text));
                } else if (type == DateTimeFormatter.class) {
                    return DateTimeFormat.forPattern(text);
                } else {
                    return null;
                }
            }
        } else if (lexer.token() == 2) {
            long millis = lexer.longValue();
            lexer.nextToken();
            TimeZone timeZone = JSON.defaultTimeZone;
            if (timeZone == null) {
                timeZone = TimeZone.getDefault();
            }
            if (type == DateTime.class) {
                return new DateTime(millis, DateTimeZone.forTimeZone(timeZone));
            }
            LocalDateTime localDateTime = new LocalDateTime(millis, DateTimeZone.forTimeZone(timeZone));
            if (type == LocalDateTime.class) {
                return localDateTime;
            }
            if (type == LocalDate.class) {
                return localDateTime.toLocalDate();
            }
            if (type == LocalTime.class) {
                return localDateTime.toLocalTime();
            }
            if (type == Instant.class) {
                return new Instant(millis);
            }
            throw new UnsupportedOperationException();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /* access modifiers changed from: protected */
    public LocalDateTime parseDateTime(String text, DateTimeFormatter formatter) {
        DateTimeFormatter formatter2;
        DateTimeFormatter formatter3;
        String str = text;
        if (formatter == null) {
            if (text.length() == 19) {
                char c4 = str.charAt(4);
                char c7 = str.charAt(7);
                char c10 = str.charAt(10);
                char c13 = str.charAt(13);
                char c16 = str.charAt(16);
                if (c13 == ':' && c16 == ':') {
                    if (c4 == '-' && c7 == '-') {
                        if (c10 == 'T') {
                            formatter2 = formatter_iso8601;
                        } else if (c10 == ' ') {
                            formatter2 = defaultFormatter;
                        }
                    } else if (c4 == '/' && c7 == '/') {
                        formatter2 = formatter_dt19_tw;
                    } else {
                        char c0 = str.charAt(0);
                        char c1 = str.charAt(1);
                        char c2 = str.charAt(2);
                        char c3 = str.charAt(3);
                        char c5 = str.charAt(5);
                        if (c2 == '/' && c5 == '/') {
                            int v1 = ((c3 - '0') * 10) + (c4 - '0');
                            if (((c0 - '0') * 10) + (c1 - '0') > 12) {
                                formatter3 = formatter_dt19_eur;
                            } else if (v1 > 12) {
                                formatter3 = formatter_dt19_us;
                            } else {
                                String country = Locale.getDefault().getCountry();
                                if (country.equals("US")) {
                                    formatter3 = formatter_dt19_us;
                                } else if (country.equals("BR") || country.equals("AU")) {
                                    formatter3 = formatter_dt19_eur;
                                } else {
                                    formatter3 = formatter;
                                }
                            }
                            formatter2 = formatter3;
                        } else if (c2 == '.' && c5 == '.') {
                            formatter2 = formatter_dt19_de;
                        } else if (c2 == '-' && c5 == '-') {
                            formatter2 = formatter_dt19_in;
                        }
                    }
                }
                formatter2 = formatter;
            } else {
                if (text.length() == 23) {
                    char c42 = str.charAt(4);
                    char c72 = str.charAt(7);
                    char c102 = str.charAt(10);
                    char c132 = str.charAt(13);
                    char c162 = str.charAt(16);
                    char c19 = str.charAt(19);
                    if (c132 == ':' && c162 == ':' && c42 == '-' && c72 == '-' && c102 == ' ' && c19 == '.') {
                        formatter2 = defaultFormatter_23;
                    }
                }
                formatter2 = formatter;
            }
            if (text.length() >= 17) {
                char c43 = str.charAt(4);
                if (c43 == 24180) {
                    if (str.charAt(text.length() - 1) == 31186) {
                        formatter2 = formatter_dt19_cn_1;
                    } else {
                        formatter2 = formatter_dt19_cn;
                    }
                } else if (c43 == 45380) {
                    formatter2 = formatter_dt19_kr;
                }
            }
            boolean digit = true;
            int i = 0;
            while (true) {
                if (i >= text.length()) {
                    break;
                }
                char ch = str.charAt(i);
                if (ch < '0' || ch > '9') {
                    digit = false;
                } else {
                    i++;
                }
            }
            digit = false;
            if (digit && text.length() > 8 && text.length() < 19) {
                return new LocalDateTime(Long.parseLong(text), DateTimeZone.forTimeZone(JSON.defaultTimeZone));
            }
        } else {
            formatter2 = formatter;
        }
        if (formatter2 == null) {
            return LocalDateTime.parse(text);
        }
        return LocalDateTime.parse(str, formatter2);
    }

    /* access modifiers changed from: protected */
    public LocalDate parseLocalDate(String text, String format, DateTimeFormatter formatter) {
        DateTimeFormatter formatter2;
        String str = text;
        if (formatter == null) {
            if (text.length() == 8) {
                formatter2 = formatter_d8;
            } else {
                formatter2 = formatter;
            }
            if (text.length() == 10) {
                char c4 = str.charAt(4);
                char c7 = str.charAt(7);
                if (c4 == '/' && c7 == '/') {
                    formatter2 = formatter_d10_tw;
                }
                char c0 = str.charAt(0);
                char c1 = str.charAt(1);
                char c2 = str.charAt(2);
                char c3 = str.charAt(3);
                char c5 = str.charAt(5);
                if (c2 == '/' && c5 == '/') {
                    int v1 = ((c3 - '0') * 10) + (c4 - '0');
                    if (((c0 - '0') * 10) + (c1 - '0') > 12) {
                        formatter2 = formatter_d10_eur;
                    } else if (v1 > 12) {
                        formatter2 = formatter_d10_us;
                    } else {
                        String country = Locale.getDefault().getCountry();
                        if (country.equals("US")) {
                            formatter2 = formatter_d10_us;
                        } else if (country.equals("BR") || country.equals("AU")) {
                            formatter2 = formatter_d10_eur;
                        }
                    }
                } else if (c2 == '.' && c5 == '.') {
                    formatter2 = formatter_d10_de;
                } else if (c2 == '-' && c5 == '-') {
                    formatter2 = formatter_d10_in;
                }
            }
            if (text.length() >= 9) {
                char c42 = str.charAt(4);
                if (c42 == 24180) {
                    formatter2 = formatter_d10_cn;
                } else if (c42 == 45380) {
                    formatter2 = formatter_d10_kr;
                }
            }
            boolean digit = true;
            int i = 0;
            while (true) {
                if (i >= text.length()) {
                    break;
                }
                char ch = str.charAt(i);
                if (ch < '0' || ch > '9') {
                    digit = false;
                } else {
                    i++;
                }
            }
            digit = false;
            if (digit && text.length() > 8 && text.length() < 19) {
                return new LocalDateTime(Long.parseLong(text), DateTimeZone.forTimeZone(JSON.defaultTimeZone)).toLocalDate();
            }
        } else {
            formatter2 = formatter;
        }
        if (formatter2 == null) {
            return LocalDate.parse(text);
        }
        return LocalDate.parse(str, formatter2);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00cc  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.joda.time.DateTime parseZonedDateTime(java.lang.String r17, org.joda.time.format.DateTimeFormatter r18) {
        /*
            r16 = this;
            r0 = r17
            if (r18 != 0) goto L_0x00f2
            int r1 = r17.length()
            r2 = 19
            r3 = 1
            r4 = 4
            if (r1 != r2) goto L_0x00c2
            char r1 = r0.charAt(r4)
            r2 = 7
            char r2 = r0.charAt(r2)
            r5 = 10
            char r6 = r0.charAt(r5)
            r7 = 13
            char r7 = r0.charAt(r7)
            r8 = 16
            char r8 = r0.charAt(r8)
            r9 = 58
            if (r7 != r9) goto L_0x00c2
            if (r8 != r9) goto L_0x00c2
            r9 = 45
            if (r1 != r9) goto L_0x0047
            if (r2 != r9) goto L_0x0047
            r5 = 84
            if (r6 != r5) goto L_0x003e
            org.joda.time.format.DateTimeFormatter r5 = formatter_iso8601
            r3 = r5
            goto L_0x00c4
        L_0x003e:
            r5 = 32
            if (r6 != r5) goto L_0x00c2
            org.joda.time.format.DateTimeFormatter r5 = defaultFormatter
            r3 = r5
            goto L_0x00c4
        L_0x0047:
            r10 = 47
            if (r1 != r10) goto L_0x0052
            if (r2 != r10) goto L_0x0052
            org.joda.time.format.DateTimeFormatter r5 = formatter_dt19_tw
            r3 = r5
            goto L_0x00c4
        L_0x0052:
            r11 = 0
            char r11 = r0.charAt(r11)
            char r12 = r0.charAt(r3)
            r13 = 2
            char r13 = r0.charAt(r13)
            r14 = 3
            char r14 = r0.charAt(r14)
            r15 = 5
            char r15 = r0.charAt(r15)
            if (r13 != r10) goto L_0x00b2
            if (r15 != r10) goto L_0x00b2
            int r9 = r11 + -48
            int r9 = r9 * r5
            int r10 = r12 + -48
            int r9 = r9 + r10
            int r10 = r14 + -48
            int r10 = r10 * r5
            int r5 = r1 + -48
            int r10 = r10 + r5
            r5 = 12
            if (r9 <= r5) goto L_0x0082
            org.joda.time.format.DateTimeFormatter r5 = formatter_dt19_eur
            r3 = r5
            goto L_0x00b1
        L_0x0082:
            if (r10 <= r5) goto L_0x0088
            org.joda.time.format.DateTimeFormatter r5 = formatter_dt19_us
            r3 = r5
            goto L_0x00b1
        L_0x0088:
            java.util.Locale r5 = java.util.Locale.getDefault()
            java.lang.String r5 = r5.getCountry()
            java.lang.String r3 = "US"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x009b
            org.joda.time.format.DateTimeFormatter r3 = formatter_dt19_us
            goto L_0x00b1
        L_0x009b:
            java.lang.String r3 = "BR"
            boolean r3 = r5.equals(r3)
            if (r3 != 0) goto L_0x00af
            java.lang.String r3 = "AU"
            boolean r3 = r5.equals(r3)
            if (r3 == 0) goto L_0x00ac
            goto L_0x00af
        L_0x00ac:
            r3 = r18
            goto L_0x00b1
        L_0x00af:
            org.joda.time.format.DateTimeFormatter r3 = formatter_dt19_eur
        L_0x00b1:
            goto L_0x00c4
        L_0x00b2:
            r3 = 46
            if (r13 != r3) goto L_0x00bb
            if (r15 != r3) goto L_0x00bb
            org.joda.time.format.DateTimeFormatter r3 = formatter_dt19_de
            goto L_0x00c4
        L_0x00bb:
            if (r13 != r9) goto L_0x00c2
            if (r15 != r9) goto L_0x00c2
            org.joda.time.format.DateTimeFormatter r3 = formatter_dt19_in
            goto L_0x00c4
        L_0x00c2:
            r3 = r18
        L_0x00c4:
            int r1 = r17.length()
            r2 = 17
            if (r1 < r2) goto L_0x00f0
            char r1 = r0.charAt(r4)
            r2 = 24180(0x5e74, float:3.3883E-41)
            if (r1 != r2) goto L_0x00e8
            int r2 = r17.length()
            r4 = 1
            int r2 = r2 - r4
            char r2 = r0.charAt(r2)
            r4 = 31186(0x79d2, float:4.3701E-41)
            if (r2 != r4) goto L_0x00e5
            org.joda.time.format.DateTimeFormatter r2 = formatter_dt19_cn_1
            goto L_0x00f4
        L_0x00e5:
            org.joda.time.format.DateTimeFormatter r2 = formatter_dt19_cn
            goto L_0x00f4
        L_0x00e8:
            r2 = 45380(0xb144, float:6.3591E-41)
            if (r1 != r2) goto L_0x00f0
            org.joda.time.format.DateTimeFormatter r2 = formatter_dt19_kr
            goto L_0x00f4
        L_0x00f0:
            r2 = r3
            goto L_0x00f4
        L_0x00f2:
            r2 = r18
        L_0x00f4:
            if (r2 != 0) goto L_0x00fb
            org.joda.time.DateTime r1 = org.joda.time.DateTime.parse(r17)
            goto L_0x00ff
        L_0x00fb:
            org.joda.time.DateTime r1 = org.joda.time.DateTime.parse(r0, r2)
        L_0x00ff:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JodaCodec.parseZonedDateTime(java.lang.String, org.joda.time.format.DateTimeFormatter):org.joda.time.DateTime");
    }

    public int getFastMatchToken() {
        return 4;
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        if (fieldType == null) {
            fieldType = object.getClass();
        }
        if (fieldType == LocalDateTime.class) {
            SerializerFeature serializerFeature = SerializerFeature.UseISO8601DateFormat;
            int mask = serializerFeature.getMask();
            LocalDateTime dateTime = (LocalDateTime) object;
            String format = serializer.getDateFormatPattern();
            if (format == null) {
                if ((features & mask) != 0 || serializer.isEnabled(serializerFeature)) {
                    format = formatter_iso8601_pattern;
                } else if (serializer.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
                    format = JSON.DEFFAULT_DATE_FORMAT;
                } else if (dateTime.getMillisOfSecond() == 0) {
                    format = formatter_iso8601_pattern_23;
                } else {
                    format = formatter_iso8601_pattern_29;
                }
            }
            if (format != null) {
                write(out, (ReadablePartial) dateTime, format);
            } else {
                out.writeLong(dateTime.toDateTime(DateTimeZone.forTimeZone(JSON.defaultTimeZone)).toInstant().getMillis());
            }
        } else {
            out.writeString(object.toString());
        }
    }

    public void write(JSONSerializer serializer, Object object, BeanContext context) {
        write(serializer.out, (ReadablePartial) object, context.getFormat());
    }

    private void write(SerializeWriter out, ReadablePartial object, String format) {
        DateTimeFormatter formatter;
        if (format.equals(formatter_iso8601_pattern)) {
            formatter = formatter_iso8601;
        } else {
            formatter = DateTimeFormat.forPattern(format);
        }
        out.writeString(formatter.print(object));
    }
}
