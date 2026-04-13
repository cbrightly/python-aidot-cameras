package com.downloader.internal;

import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.downloader.httpclient.b;
import com.downloader.j;
import com.downloader.k;
import com.downloader.l;
import com.downloader.request.a;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import meshsdk.cache.CacheHandler;

/* compiled from: DownloadTask */
public class d {
    private final a a;
    private com.downloader.handler.a b;
    private long c;
    private long d;
    private InputStream e;
    private com.downloader.internal.stream.a f;
    private b g;
    private long h;
    private int i;
    private String j;
    private boolean k;
    private String l;

    private d(a request) {
        this.a = request;
    }

    static d d(a request) {
        return new d(request);
    }

    /* access modifiers changed from: package-private */
    public k k() {
        k response = new k();
        l z = this.a.z();
        l lVar = l.CANCELLED;
        if (z == lVar) {
            response.e(true);
            return response;
        }
        l z2 = this.a.z();
        l lVar2 = l.PAUSED;
        if (z2 == lVar2) {
            response.g(true);
            return response;
        }
        try {
            if (this.a.u() != null) {
                this.b = new com.downloader.handler.a(this.a.u());
            }
            this.l = com.downloader.utils.a.e(this.a.p(), this.a.s());
            File file = new File(this.l);
            com.downloader.database.d model = g();
            if (model != null) {
                if (file.exists()) {
                    this.a.J(model.g());
                    this.a.D(model.b());
                } else {
                    j();
                    this.a.D(0);
                    this.a.J(0);
                    model = null;
                }
            }
            b c2 = a.d().c();
            this.g = c2;
            c2.P(this.a);
            if (this.a.z() == lVar) {
                response.e(true);
                b(this.f);
                return response;
            } else if (this.a.z() == lVar2) {
                response.g(true);
                b(this.f);
                return response;
            } else {
                b d2 = com.downloader.utils.a.d(this.g, this.a);
                this.g = d2;
                this.i = d2.F0();
                this.j = this.g.E(HttpHeaders.HEAD_KEY_E_TAG);
                if (a(model)) {
                    model = null;
                }
                if (!i()) {
                    com.downloader.a error = new com.downloader.a();
                    error.e(true);
                    error.f(c(this.g.x()));
                    error.c(this.g.W());
                    error.d(this.i);
                    response.f(error);
                    b(this.f);
                    return response;
                }
                m();
                this.h = this.a.A();
                if (!this.k) {
                    f();
                }
                if (this.h == 0) {
                    long contentLength = this.g.getContentLength();
                    this.h = contentLength;
                    this.a.J(contentLength);
                }
                if (this.k && model == null) {
                    e();
                }
                if (this.a.z() == lVar) {
                    response.e(true);
                    b(this.f);
                    return response;
                } else if (this.a.z() == lVar2) {
                    response.g(true);
                    b(this.f);
                    return response;
                } else {
                    this.a.j();
                    this.e = this.g.getInputStream();
                    byte[] buff = new byte[4096];
                    if (!file.exists()) {
                        if (file.getParentFile() == null || file.getParentFile().exists()) {
                            file.createNewFile();
                        } else if (file.getParentFile().mkdirs()) {
                            file.createNewFile();
                        }
                    }
                    this.f = com.downloader.internal.stream.b.c(file);
                    if (this.k && this.a.r() != 0) {
                        this.f.b(this.a.r());
                    }
                    if (this.a.z() == lVar) {
                        response.e(true);
                        b(this.f);
                        return response;
                    } else if (this.a.z() == lVar2) {
                        response.g(true);
                        b(this.f);
                        return response;
                    } else {
                        do {
                            int byteCount = this.e.read(buff, 0, 4096);
                            if (byteCount == -1) {
                                com.downloader.utils.a.h(this.l, com.downloader.utils.a.c(this.a.p(), this.a.s()));
                                response.h(true);
                                if (this.k) {
                                    j();
                                }
                                b(this.f);
                                return response;
                            }
                            this.f.write(buff, 0, byteCount);
                            a aVar = this.a;
                            aVar.D(aVar.r() + ((long) byteCount));
                            l();
                            o(this.f);
                            if (this.a.z() == l.CANCELLED) {
                                response.e(true);
                                b(this.f);
                                return response;
                            }
                        } while (this.a.z() != l.PAUSED);
                        n(this.f);
                        response.g(true);
                        b(this.f);
                        return response;
                    }
                }
            }
        } catch (IOException | IllegalAccessException e2) {
            if (!this.k) {
                f();
            }
            com.downloader.a error2 = new com.downloader.a();
            error2.a(true);
            error2.b(e2);
            response.f(error2);
        } catch (Throwable th) {
            b(this.f);
            throw th;
        }
    }

    private void f() {
        File file = new File(this.l);
        if (file.exists()) {
            file.delete();
        }
    }

    private boolean i() {
        int i2 = this.i;
        return i2 >= 200 && i2 < 300;
    }

    private void m() {
        this.k = this.i == 206;
    }

    private boolean a(com.downloader.database.d model) {
        if (this.i != 416 && !h(model)) {
            return false;
        }
        if (model != null) {
            j();
        }
        f();
        this.a.D(0);
        this.a.J(0);
        b c2 = a.d().c();
        this.g = c2;
        c2.P(this.a);
        b d2 = com.downloader.utils.a.d(this.g, this.a);
        this.g = d2;
        this.i = d2.F0();
        return true;
    }

    private boolean h(com.downloader.database.d model) {
        return (this.j == null || model == null || model.c() == null || model.c().equals(this.j)) ? false : true;
    }

    private com.downloader.database.d g() {
        return a.d().b().a(this.a.q());
    }

    private void e() {
        com.downloader.database.d model = new com.downloader.database.d();
        model.m(this.a.q());
        model.p(this.a.B());
        model.k(this.j);
        model.i(this.a.p());
        model.l(this.a.s());
        model.j(this.a.r());
        model.o(this.h);
        model.n(System.currentTimeMillis());
        a.d().b().d(model);
    }

    private void j() {
        a.d().b().remove(this.a.q());
    }

    private void l() {
        com.downloader.handler.a aVar;
        if (this.a.z() != l.CANCELLED && (aVar = this.b) != null) {
            aVar.obtainMessage(1, new j(this.a.r(), this.h)).sendToTarget();
        }
    }

    private void o(com.downloader.internal.stream.a outputStream) {
        long currentBytes = this.a.r();
        long currentTime = System.currentTimeMillis();
        long timeDelta = currentTime - this.c;
        if (currentBytes - this.d > 65536 && timeDelta > CacheHandler.delayTime) {
            n(outputStream);
            this.d = currentBytes;
            this.c = currentTime;
        }
    }

    private void n(com.downloader.internal.stream.a outputStream) {
        boolean success;
        try {
            outputStream.a();
            success = true;
        } catch (IOException e2) {
            e2.printStackTrace();
            success = false;
        }
        if (success && this.k) {
            a.d().b().b(this.a.q(), this.a.r(), System.currentTimeMillis());
        }
    }

    private void b(com.downloader.internal.stream.a outputStream) {
        b bVar = this.g;
        if (bVar != null) {
            try {
                bVar.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        InputStream inputStream = this.e;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                n(outputStream);
            } catch (Exception e4) {
                e4.printStackTrace();
            } catch (Throwable th) {
                try {
                    outputStream.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                throw th;
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
        }
    }

    private String c(InputStream stream) {
        StringBuilder stringBuilder = new StringBuilder();
        if (stream != null) {
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(stream));
                while (true) {
                    String readLine = bufferedReader2.readLine();
                    String line = readLine;
                    if (readLine != null) {
                        stringBuilder.append(line);
                    } else {
                        try {
                            break;
                        } catch (IOException | NullPointerException e2) {
                        }
                    }
                }
                bufferedReader2.close();
            } catch (IOException e3) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException | NullPointerException e4) {
                    }
                }
            } catch (Throwable th) {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException | NullPointerException e5) {
                    }
                }
                throw th;
            }
        }
        return stringBuilder.toString();
    }
}
