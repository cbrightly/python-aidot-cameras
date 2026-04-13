package com.alibaba.fastjson.support.moneta;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.netty.util.internal.StringUtil;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.money.Monetary;
import org.javamoney.moneta.Money;

public class MonetaCodec implements ObjectSerializer, ObjectDeserializer {
    public static final MonetaCodec instance = new MonetaCodec();

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        Money money = (Money) object;
        if (money == null) {
            serializer.writeNull();
            return;
        }
        SerializeWriter out = serializer.out;
        out.writeFieldValue('{', "numberStripped", money.getNumberStripped());
        out.writeFieldValue((char) StringUtil.COMMA, FirebaseAnalytics.Param.CURRENCY, money.getCurrency().getCurrencyCode());
        out.write(125);
    }

    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        JSONObject object = parser.parseObject();
        Object currency = object.get(FirebaseAnalytics.Param.CURRENCY);
        String currencyCode = null;
        if (currency instanceof JSONObject) {
            currencyCode = ((JSONObject) currency).getString("currencyCode");
        } else if (currency instanceof String) {
            currencyCode = (String) currency;
        }
        Object numberStripped = object.get("numberStripped");
        if ((numberStripped instanceof BigDecimal) || (numberStripped instanceof Integer) || (numberStripped instanceof BigInteger)) {
            return Money.of((Number) numberStripped, Monetary.getCurrency(currencyCode, new String[0]));
        }
        throw new UnsupportedOperationException();
    }

    public int getFastMatchToken() {
        return 0;
    }
}
