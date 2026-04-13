package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextObjectSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.amazonaws.kinesisvideo.producer.Time;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import java.util.TimeZone;

public class Jdk8DateCodec extends ContextObjectDeserializer implements ObjectSerializer, ContextObjectSerializer, ObjectDeserializer {
    private static final DateTimeFormatter ISO_FIXED_FORMAT = DateTimeFormatter.ofPattern(defaultPatttern).withZone(ZoneId.systemDefault());
    private static final DateTimeFormatter defaultFormatter = DateTimeFormatter.ofPattern(defaultPatttern);
    private static final DateTimeFormatter defaultFormatter_23 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String defaultPatttern = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter formatter_d10_cn = DateTimeFormatter.ofPattern("yyyy年M月d日");
    private static final DateTimeFormatter formatter_d10_de = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter formatter_d10_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter formatter_d10_in = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter formatter_d10_kr = DateTimeFormatter.ofPattern("yyyy년M월d일");
    private static final DateTimeFormatter formatter_d10_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter formatter_d10_us = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter formatter_d8 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter formatter_dt19_cn = DateTimeFormatter.ofPattern("yyyy年M月d日 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_cn_1 = DateTimeFormatter.ofPattern("yyyy年M月d日 H时m分s秒");
    private static final DateTimeFormatter formatter_dt19_de = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_eur = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_in = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_kr = DateTimeFormatter.ofPattern("yyyy년M월d일 HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_tw = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter formatter_dt19_us = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private static final DateTimeFormatter formatter_iso8601 = DateTimeFormatter.ofPattern(formatter_iso8601_pattern);
    private static final String formatter_iso8601_pattern = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String formatter_iso8601_pattern_23 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final String formatter_iso8601_pattern_29 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS";
    public static final Jdk8DateCodec instance = new Jdk8DateCodec();

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName, String format, int feature) {
        DefaultJSONParser defaultJSONParser = parser;
        Type type2 = type;
        String str = format;
        JSONLexer lexer = defaultJSONParser.lexer;
        if (lexer.token() == 8) {
            lexer.nextToken();
            return null;
        } else if (lexer.token() == 4) {
            String text = lexer.stringVal();
            lexer.nextToken();
            DateTimeFormatter formatter = null;
            if (str != null) {
                if (defaultPatttern.equals(str)) {
                    formatter = defaultFormatter;
                } else {
                    formatter = DateTimeFormatter.ofPattern(format);
                }
            }
            if ("".equals(text)) {
                return null;
            }
            if (type2 == LocalDateTime.class) {
                if (text.length() == 10 || text.length() == 8) {
                    return LocalDateTime.of(parseLocalDate(text, str, formatter), LocalTime.MIN);
                }
                return parseDateTime(text, formatter);
            } else if (type2 == LocalDate.class) {
                if (text.length() != 23) {
                    return parseLocalDate(text, str, formatter);
                }
                LocalDateTime localDateTime = LocalDateTime.parse(text);
                return LocalDate.of(localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
            } else if (type2 == LocalTime.class) {
                if (text.length() == 23) {
                    LocalDateTime localDateTime2 = LocalDateTime.parse(text);
                    return LocalTime.of(localDateTime2.getHour(), localDateTime2.getMinute(), localDateTime2.getSecond(), localDateTime2.getNano());
                }
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
                if (!digit || text.length() <= 8 || text.length() >= 19) {
                    return LocalTime.parse(text);
                }
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(text)), JSON.defaultTimeZone.toZoneId()).toLocalTime();
            } else if (type2 == ZonedDateTime.class) {
                if (formatter == defaultFormatter) {
                    formatter = ISO_FIXED_FORMAT;
                }
                if (formatter == null && text.length() <= 19) {
                    JSONScanner s = new JSONScanner(text);
                    TimeZone timeZone = defaultJSONParser.lexer.getTimeZone();
                    s.setTimeZone(timeZone);
                    if (s.scanISO8601DateIfMatch(false)) {
                        return ZonedDateTime.ofInstant(s.getCalendar().getTime().toInstant(), timeZone.toZoneId());
                    }
                }
                return parseZonedDateTime(text, formatter);
            } else if (type2 == OffsetDateTime.class) {
                return OffsetDateTime.parse(text);
            } else {
                if (type2 == OffsetTime.class) {
                    return OffsetTime.parse(text);
                }
                if (type2 == ZoneId.class) {
                    return ZoneId.of(text);
                }
                if (type2 == Period.class) {
                    return Period.parse(text);
                }
                if (type2 == Duration.class) {
                    return Duration.parse(text);
                }
                if (type2 != Instant.class) {
                    return null;
                }
                boolean digit2 = true;
                int i2 = 0;
                while (true) {
                    if (i2 >= text.length()) {
                        break;
                    }
                    char ch2 = text.charAt(i2);
                    if (ch2 < '0' || ch2 > '9') {
                        digit2 = false;
                    } else {
                        i2++;
                    }
                }
                if (!digit2 || text.length() <= 8 || text.length() >= 19) {
                    return Instant.parse(text);
                }
                return Instant.ofEpochMilli(Long.parseLong(text));
            }
        } else if (lexer.token() == 2) {
            long millis = lexer.longValue();
            lexer.nextToken();
            if ("unixtime".equals(str)) {
                millis *= 1000;
            } else if ("yyyyMMddHHmmss".equals(str)) {
                int yyyy = (int) (millis / 10000000000L);
                int MM = (int) ((millis / 100000000) % 100);
                int dd = (int) ((millis / Time.NANOS_IN_A_MILLISECOND) % 100);
                int HH = (int) ((millis / 10000) % 100);
                int mm = (int) ((millis / 100) % 100);
                int ss = (int) (millis % 100);
                if (type2 == LocalDateTime.class) {
                    int i3 = mm;
                    int i4 = HH;
                    return LocalDateTime.of(yyyy, MM, dd, HH, mm, ss);
                }
                int i5 = mm;
                int i6 = HH;
            }
            if (type2 == LocalDateTime.class) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), JSON.defaultTimeZone.toZoneId());
            }
            if (type2 == LocalDate.class) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), JSON.defaultTimeZone.toZoneId()).toLocalDate();
            }
            if (type2 == LocalTime.class) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), JSON.defaultTimeZone.toZoneId()).toLocalTime();
            }
            if (type2 == ZonedDateTime.class) {
                return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), JSON.defaultTimeZone.toZoneId());
            }
            if (type2 == Instant.class) {
                return Instant.ofEpochMilli(millis);
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
        DateTimeFormatter formatter4;
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
                            formatter3 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                        } else if (c10 == ' ') {
                            formatter3 = defaultFormatter;
                        }
                    } else if (c4 == '/' && c7 == '/') {
                        formatter3 = formatter_dt19_tw;
                    } else {
                        char c0 = str.charAt(0);
                        char c1 = str.charAt(1);
                        char c2 = str.charAt(2);
                        char c3 = str.charAt(3);
                        char c5 = str.charAt(5);
                        if (c2 == '/' && c5 == '/') {
                            int v1 = ((c3 - '0') * 10) + (c4 - '0');
                            if (((c0 - '0') * 10) + (c1 - '0') > 12) {
                                formatter4 = formatter_dt19_eur;
                            } else if (v1 > 12) {
                                formatter4 = formatter_dt19_us;
                            } else {
                                String country = Locale.getDefault().getCountry();
                                if (country.equals("US")) {
                                    formatter4 = formatter_dt19_us;
                                } else if (country.equals("BR") || country.equals("AU")) {
                                    formatter4 = formatter_dt19_eur;
                                } else {
                                    formatter4 = formatter;
                                }
                            }
                            formatter3 = formatter4;
                        } else if (c2 == '.' && c5 == '.') {
                            formatter3 = formatter_dt19_de;
                        } else if (c2 == '-' && c5 == '-') {
                            formatter3 = formatter_dt19_in;
                        }
                    }
                }
                formatter3 = formatter;
            } else {
                if (text.length() == 23) {
                    char c42 = str.charAt(4);
                    char c72 = str.charAt(7);
                    char c102 = str.charAt(10);
                    char c132 = str.charAt(13);
                    char c162 = str.charAt(16);
                    char c19 = str.charAt(19);
                    if (c132 == ':' && c162 == ':' && c42 == '-' && c72 == '-' && c102 == ' ' && c19 == '.') {
                        formatter3 = defaultFormatter_23;
                    }
                }
                formatter3 = formatter;
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
            formatter2 = formatter3;
        } else {
            formatter2 = formatter;
        }
        if (formatter2 == null) {
            JSONScanner dateScanner = new JSONScanner(str);
            if (dateScanner.scanISO8601DateIfMatch(false)) {
                return LocalDateTime.ofInstant(dateScanner.getCalendar().toInstant(), ZoneId.systemDefault());
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
            if (digit && text.length() > 8 && text.length() < 19) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(text)), JSON.defaultTimeZone.toZoneId());
            }
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
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(text)), JSON.defaultTimeZone.toZoneId()).toLocalDate();
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
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00f9  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x010a A[EDGE_INSN: B:78:0x010a->B:66:0x010a ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.time.ZonedDateTime parseZonedDateTime(java.lang.String r17, java.time.format.DateTimeFormatter r18) {
        /*
            r16 = this;
            r0 = r17
            if (r18 != 0) goto L_0x012f
            int r1 = r17.length()
            r2 = 4
            r3 = 19
            r4 = 1
            if (r1 != r3) goto L_0x00c2
            char r1 = r0.charAt(r2)
            r5 = 7
            char r5 = r0.charAt(r5)
            r6 = 10
            char r7 = r0.charAt(r6)
            r8 = 13
            char r8 = r0.charAt(r8)
            r9 = 16
            char r9 = r0.charAt(r9)
            r10 = 58
            if (r8 != r10) goto L_0x00c2
            if (r9 != r10) goto L_0x00c2
            r10 = 45
            if (r1 != r10) goto L_0x0047
            if (r5 != r10) goto L_0x0047
            r6 = 84
            if (r7 != r6) goto L_0x003e
            java.time.format.DateTimeFormatter r6 = java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
            r4 = r6
            goto L_0x00c4
        L_0x003e:
            r6 = 32
            if (r7 != r6) goto L_0x00c2
            java.time.format.DateTimeFormatter r6 = defaultFormatter
            r4 = r6
            goto L_0x00c4
        L_0x0047:
            r11 = 47
            if (r1 != r11) goto L_0x0052
            if (r5 != r11) goto L_0x0052
            java.time.format.DateTimeFormatter r6 = formatter_dt19_tw
            r4 = r6
            goto L_0x00c4
        L_0x0052:
            r12 = 0
            char r12 = r0.charAt(r12)
            char r13 = r0.charAt(r4)
            r14 = 2
            char r14 = r0.charAt(r14)
            r15 = 3
            char r15 = r0.charAt(r15)
            r3 = 5
            char r3 = r0.charAt(r3)
            if (r14 != r11) goto L_0x00b2
            if (r3 != r11) goto L_0x00b2
            int r10 = r12 + -48
            int r10 = r10 * r6
            int r11 = r13 + -48
            int r10 = r10 + r11
            int r11 = r15 + -48
            int r11 = r11 * r6
            int r6 = r1 + -48
            int r11 = r11 + r6
            r6 = 12
            if (r10 <= r6) goto L_0x0082
            java.time.format.DateTimeFormatter r6 = formatter_dt19_eur
            r4 = r6
            goto L_0x00b1
        L_0x0082:
            if (r11 <= r6) goto L_0x0088
            java.time.format.DateTimeFormatter r6 = formatter_dt19_us
            r4 = r6
            goto L_0x00b1
        L_0x0088:
            java.util.Locale r6 = java.util.Locale.getDefault()
            java.lang.String r6 = r6.getCountry()
            java.lang.String r4 = "US"
            boolean r4 = r6.equals(r4)
            if (r4 == 0) goto L_0x009b
            java.time.format.DateTimeFormatter r4 = formatter_dt19_us
            goto L_0x00b1
        L_0x009b:
            java.lang.String r4 = "BR"
            boolean r4 = r6.equals(r4)
            if (r4 != 0) goto L_0x00af
            java.lang.String r4 = "AU"
            boolean r4 = r6.equals(r4)
            if (r4 == 0) goto L_0x00ac
            goto L_0x00af
        L_0x00ac:
            r4 = r18
            goto L_0x00b1
        L_0x00af:
            java.time.format.DateTimeFormatter r4 = formatter_dt19_eur
        L_0x00b1:
            goto L_0x00c4
        L_0x00b2:
            r4 = 46
            if (r14 != r4) goto L_0x00bb
            if (r3 != r4) goto L_0x00bb
            java.time.format.DateTimeFormatter r4 = formatter_dt19_de
            goto L_0x00c4
        L_0x00bb:
            if (r14 != r10) goto L_0x00c2
            if (r3 != r10) goto L_0x00c2
            java.time.format.DateTimeFormatter r4 = formatter_dt19_in
            goto L_0x00c4
        L_0x00c2:
            r4 = r18
        L_0x00c4:
            int r1 = r17.length()
            r3 = 17
            if (r1 < r3) goto L_0x00f0
            char r1 = r0.charAt(r2)
            r2 = 24180(0x5e74, float:3.3883E-41)
            if (r1 != r2) goto L_0x00e8
            int r2 = r17.length()
            r3 = 1
            int r2 = r2 - r3
            char r2 = r0.charAt(r2)
            r3 = 31186(0x79d2, float:4.3701E-41)
            if (r2 != r3) goto L_0x00e5
            java.time.format.DateTimeFormatter r2 = formatter_dt19_cn_1
            goto L_0x00f1
        L_0x00e5:
            java.time.format.DateTimeFormatter r2 = formatter_dt19_cn
            goto L_0x00f1
        L_0x00e8:
            r2 = 45380(0xb144, float:6.3591E-41)
            if (r1 != r2) goto L_0x00f0
            java.time.format.DateTimeFormatter r2 = formatter_dt19_kr
            goto L_0x00f1
        L_0x00f0:
            r2 = r4
        L_0x00f1:
            r1 = 1
            r3 = 0
        L_0x00f3:
            int r4 = r17.length()
            if (r3 >= r4) goto L_0x010a
            char r4 = r0.charAt(r3)
            r5 = 48
            if (r4 < r5) goto L_0x0109
            r5 = 57
            if (r4 <= r5) goto L_0x0106
            goto L_0x0109
        L_0x0106:
            int r3 = r3 + 1
            goto L_0x00f3
        L_0x0109:
            r1 = 0
        L_0x010a:
            if (r1 == 0) goto L_0x0131
            int r3 = r17.length()
            r4 = 8
            if (r3 <= r4) goto L_0x0131
            int r3 = r17.length()
            r4 = 19
            if (r3 >= r4) goto L_0x0131
            long r3 = java.lang.Long.parseLong(r17)
            java.time.Instant r5 = java.time.Instant.ofEpochMilli(r3)
            java.util.TimeZone r6 = com.alibaba.fastjson.JSON.defaultTimeZone
            java.time.ZoneId r6 = r6.toZoneId()
            java.time.ZonedDateTime r5 = java.time.ZonedDateTime.ofInstant(r5, r6)
            return r5
        L_0x012f:
            r2 = r18
        L_0x0131:
            if (r2 != 0) goto L_0x0138
            java.time.ZonedDateTime r1 = java.time.ZonedDateTime.parse(r17)
            goto L_0x013c
        L_0x0138:
            java.time.ZonedDateTime r1 = java.time.ZonedDateTime.parse(r0, r2)
        L_0x013c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.Jdk8DateCodec.parseZonedDateTime(java.lang.String, java.time.format.DateTimeFormatter):java.time.ZonedDateTime");
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
                } else {
                    int nano = dateTime.getNano();
                    if (nano == 0) {
                        format = formatter_iso8601_pattern;
                    } else if (nano % 1000000 == 0) {
                        format = formatter_iso8601_pattern_23;
                    } else {
                        format = formatter_iso8601_pattern_29;
                    }
                }
            }
            if (format != null) {
                write(out, (TemporalAccessor) dateTime, format);
            } else {
                out.writeLong(dateTime.atZone(JSON.defaultTimeZone.toZoneId()).toInstant().toEpochMilli());
            }
        } else {
            out.writeString(object.toString());
        }
    }

    public void write(JSONSerializer serializer, Object object, BeanContext context) {
        write(serializer.out, (TemporalAccessor) object, context.getFormat());
    }

    private void write(SerializeWriter out, TemporalAccessor object, String format) {
        DateTimeFormatter formatter;
        if ("unixtime".equals(format)) {
            if (object instanceof ChronoZonedDateTime) {
                out.writeInt((int) ((ChronoZonedDateTime) object).toEpochSecond());
                return;
            } else if (object instanceof LocalDateTime) {
                out.writeInt((int) ((LocalDateTime) object).atZone(JSON.defaultTimeZone.toZoneId()).toEpochSecond());
                return;
            }
        }
        if ("millis".equals(format)) {
            Instant instant = null;
            if (object instanceof ChronoZonedDateTime) {
                instant = ((ChronoZonedDateTime) object).toInstant();
            } else if (object instanceof LocalDateTime) {
                instant = ((LocalDateTime) object).atZone(JSON.defaultTimeZone.toZoneId()).toInstant();
            }
            if (instant != null) {
                out.writeLong(instant.toEpochMilli());
                return;
            }
        }
        if (format == formatter_iso8601_pattern) {
            formatter = formatter_iso8601;
        } else {
            formatter = DateTimeFormatter.ofPattern(format);
        }
        out.writeString(formatter.format(object));
    }

    public static Object castToLocalDateTime(Object value, String format) {
        if (value == null) {
            return null;
        }
        if (format == null) {
            format = defaultPatttern;
        }
        return LocalDateTime.parse(value.toString(), DateTimeFormatter.ofPattern(format));
    }
}
