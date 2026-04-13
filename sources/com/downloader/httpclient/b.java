package com.downloader.httpclient;

import com.downloader.request.a;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/* compiled from: HttpClient */
public interface b extends Cloneable {
    String E(String str);

    int F0();

    void P(a aVar);

    Map<String, List<String>> W();

    b clone();

    void close();

    long getContentLength();

    InputStream getInputStream();

    InputStream x();
}
