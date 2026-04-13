package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class SqlDateDeserializer extends AbstractDateDeserializer implements ObjectDeserializer {
    public static final SqlDateDeserializer instance = new SqlDateDeserializer();
    public static final SqlDateDeserializer instance_timestamp = new SqlDateDeserializer(true);
    private boolean timestamp;

    public SqlDateDeserializer() {
        this.timestamp = false;
    }

    public SqlDateDeserializer(boolean timestmap) {
        this.timestamp = false;
        this.timestamp = true;
    }

    /* access modifiers changed from: protected */
    public <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
        long longVal;
        if (this.timestamp) {
            return castTimestamp(parser, clazz, fieldName, val);
        }
        if (val == null) {
            return null;
        }
        if (val instanceof Date) {
            return new java.sql.Date(((Date) val).getTime());
        }
        if (val instanceof BigDecimal) {
            return new java.sql.Date(TypeUtils.longValue((BigDecimal) val));
        }
        if (val instanceof Number) {
            return new java.sql.Date(((Number) val).longValue());
        }
        if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }
            JSONScanner dateLexer = new JSONScanner(strVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch()) {
                    longVal = dateLexer.getCalendar().getTimeInMillis();
                } else {
                    java.sql.Date sqlDate = new java.sql.Date(parser.getDateFormat().parse(strVal).getTime());
                    dateLexer.close();
                    return sqlDate;
                }
            } catch (ParseException e) {
                longVal = Long.parseLong(strVal);
            } catch (Throwable th) {
                dateLexer.close();
                throw th;
            }
            dateLexer.close();
            return new java.sql.Date(longVal);
        }
        throw new JSONException("parse error : " + val);
    }

    /* access modifiers changed from: protected */
    public <T> T castTimestamp(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
        long longVal;
        if (val == null) {
            return null;
        }
        if (val instanceof Date) {
            return new Timestamp(((Date) val).getTime());
        }
        if (val instanceof BigDecimal) {
            return new Timestamp(TypeUtils.longValue((BigDecimal) val));
        }
        if (val instanceof Number) {
            return new Timestamp(((Number) val).longValue());
        }
        if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }
            JSONScanner dateLexer = new JSONScanner(strVal);
            try {
                if (strVal.length() > 19 && strVal.charAt(4) == '-' && strVal.charAt(7) == '-' && strVal.charAt(10) == ' ' && strVal.charAt(13) == ':' && strVal.charAt(16) == ':' && strVal.charAt(19) == '.') {
                    String dateFomartPattern = parser.getDateFomartPattern();
                    if (dateFomartPattern.length() != strVal.length() && dateFomartPattern == JSON.DEFFAULT_DATE_FORMAT) {
                        T valueOf = Timestamp.valueOf(strVal);
                        dateLexer.close();
                        return valueOf;
                    }
                }
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    longVal = dateLexer.getCalendar().getTimeInMillis();
                } else {
                    Timestamp sqlDate = new Timestamp(parser.getDateFormat().parse(strVal).getTime());
                    dateLexer.close();
                    return sqlDate;
                }
            } catch (ParseException e) {
                longVal = Long.parseLong(strVal);
            } catch (Throwable th) {
                dateLexer.close();
                throw th;
            }
            dateLexer.close();
            return new Timestamp(longVal);
        }
        throw new JSONException("parse error");
    }

    public int getFastMatchToken() {
        return 2;
    }
}
