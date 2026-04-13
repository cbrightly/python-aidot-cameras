package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.didichuxing.doraemonkit.kit.network.utils.CostTimeUtil;
import com.google.android.material.timepicker.TimeModel;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateCodec extends AbstractDateDeserializer implements ObjectSerializer, ObjectDeserializer {
    public static final DateCodec instance = new DateCodec();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        Date date;
        char[] buf;
        JSONSerializer jSONSerializer = serializer;
        Object obj = object;
        SerializeWriter out = jSONSerializer.out;
        if (obj == null) {
            out.writeNull();
            return;
        }
        Class<?> clazz = object.getClass();
        if (clazz != java.sql.Date.class || out.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
            int i = features;
        } else {
            long millis = ((java.sql.Date) obj).getTime();
            if ((((long) jSONSerializer.timeZone.getOffset(millis)) + millis) % CostTimeUtil.DAY == 0) {
                if (!SerializerFeature.isEnabled(out.features, features, SerializerFeature.WriteClassName)) {
                    out.writeString(object.toString());
                    return;
                }
            } else {
                int i2 = features;
            }
        }
        if (clazz == Time.class) {
            long millis2 = ((Time) obj).getTime();
            if ("unixtime".equals(serializer.getDateFormatPattern())) {
                out.writeLong(millis2 / 1000);
                return;
            } else if ("millis".equals(serializer.getDateFormatPattern())) {
                long j = millis2;
                out.writeLong(millis2);
                return;
            } else if (millis2 < CostTimeUtil.DAY) {
                out.writeString(object.toString());
                return;
            }
        }
        int nanos = 0;
        if (clazz == Timestamp.class) {
            nanos = ((Timestamp) obj).getNanos();
        }
        if (obj instanceof Date) {
            date = (Date) obj;
        } else {
            date = TypeUtils.castToDate(object);
        }
        if ("unixtime".equals(serializer.getDateFormatPattern())) {
            out.writeLong(date.getTime() / 1000);
        } else if ("millis".equals(serializer.getDateFormatPattern())) {
            out.writeLong(date.getTime());
        } else if (out.isEnabled(SerializerFeature.WriteDateUseDateFormat)) {
            DateFormat format = serializer.getDateFormat();
            if (format == null) {
                String dateFormatPattern = serializer.getFastJsonConfigDateFormatPattern();
                if (dateFormatPattern == null) {
                    dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
                }
                format = new SimpleDateFormat(dateFormatPattern, jSONSerializer.locale);
                format.setTimeZone(jSONSerializer.timeZone);
            }
            out.writeString(format.format(date));
        } else {
            if (!out.isEnabled(SerializerFeature.WriteClassName)) {
                Type type = fieldType;
            } else if (clazz != fieldType) {
                if (clazz == Date.class) {
                    out.write("new Date(");
                    out.writeLong(((Date) obj).getTime());
                    out.write(41);
                    return;
                }
                out.write(123);
                out.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                jSONSerializer.write(clazz.getName());
                out.writeFieldValue((char) StringUtil.COMMA, "val", ((Date) obj).getTime());
                out.write(125);
                return;
            }
            long time = date.getTime();
            if (out.isEnabled(SerializerFeature.UseISO8601DateFormat)) {
                char quote = out.isEnabled(SerializerFeature.UseSingleQuotes) ? '\'' : StringUtil.DOUBLE_QUOTE;
                out.write((int) quote);
                Calendar calendar = Calendar.getInstance(jSONSerializer.timeZone, jSONSerializer.locale);
                calendar.setTimeInMillis(time);
                int year = calendar.get(1);
                int month = calendar.get(2) + 1;
                int day = calendar.get(5);
                int hour = calendar.get(11);
                int minute = calendar.get(12);
                Class<?> cls = clazz;
                int second = calendar.get(13);
                int millis3 = calendar.get(14);
                Date date2 = date;
                if (nanos > 0) {
                    buf = "0000-00-00 00:00:00.000000000".toCharArray();
                    IOUtils.getChars(nanos, 29, buf);
                    IOUtils.getChars(second, 19, buf);
                    IOUtils.getChars(minute, 16, buf);
                    IOUtils.getChars(hour, 13, buf);
                    IOUtils.getChars(day, 10, buf);
                    IOUtils.getChars(month, 7, buf);
                    IOUtils.getChars(year, 4, buf);
                } else if (millis3 != 0) {
                    buf = "0000-00-00T00:00:00.000".toCharArray();
                    IOUtils.getChars(millis3, 23, buf);
                    IOUtils.getChars(second, 19, buf);
                    IOUtils.getChars(minute, 16, buf);
                    IOUtils.getChars(hour, 13, buf);
                    IOUtils.getChars(day, 10, buf);
                    IOUtils.getChars(month, 7, buf);
                    IOUtils.getChars(year, 4, buf);
                } else if (second == 0 && minute == 0 && hour == 0) {
                    buf = "0000-00-00".toCharArray();
                    IOUtils.getChars(day, 10, buf);
                    IOUtils.getChars(month, 7, buf);
                    IOUtils.getChars(year, 4, buf);
                } else {
                    buf = "0000-00-00T00:00:00".toCharArray();
                    IOUtils.getChars(second, 19, buf);
                    IOUtils.getChars(minute, 16, buf);
                    IOUtils.getChars(hour, 13, buf);
                    IOUtils.getChars(day, 10, buf);
                    IOUtils.getChars(month, 7, buf);
                    IOUtils.getChars(year, 4, buf);
                }
                if (nanos > 0) {
                    int i3 = 0;
                    while (true) {
                        int minute2 = minute;
                        if (i3 >= 9) {
                            break;
                        }
                        int off = (buf.length - i3) - 1;
                        int millis4 = millis3;
                        int i4 = off;
                        if (buf[off] != '0') {
                            break;
                        }
                        i3++;
                        minute = minute2;
                        millis3 = millis4;
                    }
                    out.write(buf, 0, buf.length - i3);
                    out.write((int) quote);
                    return;
                }
                int i5 = millis3;
                out.write(buf);
                int i6 = second;
                int i7 = nanos;
                float timeZoneF = ((float) calendar.getTimeZone().getOffset(calendar.getTimeInMillis())) / 3600000.0f;
                int timeZone = (int) timeZoneF;
                char[] cArr = buf;
                if (((double) timeZone) == 0.0d) {
                    out.write(90);
                    float f = timeZoneF;
                } else {
                    if (timeZone > 9) {
                        out.write(43);
                        out.writeInt(timeZone);
                    } else if (timeZone > 0) {
                        out.write(43);
                        out.write(48);
                        out.writeInt(timeZone);
                    } else if (timeZone < -9) {
                        out.write(45);
                        out.writeInt(-timeZone);
                    } else if (timeZone < 0) {
                        out.write(45);
                        out.write(48);
                        out.writeInt(-timeZone);
                    }
                    out.write(58);
                    float f2 = timeZoneF;
                    out.append((CharSequence) String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, new Object[]{Integer.valueOf((int) (Math.abs(timeZoneF - ((float) timeZone)) * 60.0f))}));
                }
                out.write((int) quote);
                return;
            }
            int i8 = nanos;
            Date date3 = date;
            out.writeLong(time);
        }
    }

    public <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Date) {
            return val;
        }
        if (val instanceof BigDecimal) {
            return new Date(TypeUtils.longValue((BigDecimal) val));
        }
        if (val instanceof Number) {
            return new Date(((Number) val).longValue());
        }
        if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.length() == 23 && strVal.endsWith(" 000")) {
                strVal = strVal.substring(0, 19);
            }
            JSONScanner dateLexer = new JSONScanner(strVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    Calendar calendar = dateLexer.getCalendar();
                    if (clazz == Calendar.class) {
                        return calendar;
                    }
                    T time = calendar.getTime();
                    dateLexer.close();
                    return time;
                }
                dateLexer.close();
                String dateFomartPattern = parser.getDateFomartPattern();
                if (strVal.length() == dateFomartPattern.length() || (strVal.length() == 22 && dateFomartPattern.equals("yyyyMMddHHmmssSSSZ")) || (strVal.indexOf(84) != -1 && dateFomartPattern.contains("'T'") && strVal.length() + 2 == dateFomartPattern.length())) {
                    try {
                        return parser.getDateFormat().parse(strVal);
                    } catch (ParseException e) {
                    }
                }
                if (strVal.startsWith("/Date(") && strVal.endsWith(")/")) {
                    strVal = strVal.substring(6, strVal.length() - 2);
                }
                if ("0000-00-00".equals(strVal) || "0000-00-00T00:00:00".equalsIgnoreCase(strVal) || "0001-01-01T00:00:00+08:00".equalsIgnoreCase(strVal)) {
                    return null;
                }
                int index = strVal.lastIndexOf(124);
                if (index > 20) {
                    TimeZone timeZone = TimeZone.getTimeZone(strVal.substring(index + 1));
                    if (!"GMT".equals(timeZone.getID())) {
                        JSONScanner dateLexer2 = new JSONScanner(strVal.substring(0, index));
                        try {
                            if (dateLexer2.scanISO8601DateIfMatch(false)) {
                                Calendar calendar2 = dateLexer2.getCalendar();
                                calendar2.setTimeZone(timeZone);
                                if (clazz == Calendar.class) {
                                    return calendar2;
                                }
                                T time2 = calendar2.getTime();
                                dateLexer2.close();
                                return time2;
                            }
                            dateLexer2.close();
                        } finally {
                            dateLexer2.close();
                        }
                    }
                }
                return new Date(Long.parseLong(strVal));
            } finally {
                dateLexer.close();
            }
        } else {
            throw new JSONException("parse error");
        }
    }

    public int getFastMatchToken() {
        return 2;
    }
}
