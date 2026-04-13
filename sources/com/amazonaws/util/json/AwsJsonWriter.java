package com.amazonaws.util.json;

import java.nio.ByteBuffer;
import java.util.Date;

public interface AwsJsonWriter {
    AwsJsonWriter beginArray();

    AwsJsonWriter beginObject();

    void close();

    AwsJsonWriter endArray();

    AwsJsonWriter endObject();

    void flush();

    AwsJsonWriter name(String str);

    AwsJsonWriter value();

    AwsJsonWriter value(double d);

    AwsJsonWriter value(long j);

    AwsJsonWriter value(Number number);

    AwsJsonWriter value(String str);

    AwsJsonWriter value(ByteBuffer byteBuffer);

    AwsJsonWriter value(Date date);

    AwsJsonWriter value(boolean z);
}
