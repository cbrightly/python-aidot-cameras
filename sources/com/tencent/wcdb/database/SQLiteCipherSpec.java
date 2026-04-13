package com.tencent.wcdb.database;

public class SQLiteCipherSpec {
    public static final String CIPHER_AES256CBC = "aes-256-cbc";
    public static final String CIPHER_DEVLOCK = "devlock";
    public static final String CIPHER_XXTEA = "xxtea";
    public String cipher;
    public boolean hmacEnabled = true;
    public int kdfIteration;
    public int pageSize = SQLiteGlobal.a;

    public SQLiteCipherSpec() {
    }

    public SQLiteCipherSpec(SQLiteCipherSpec rhs) {
        this.cipher = rhs.cipher;
        this.kdfIteration = rhs.kdfIteration;
        this.hmacEnabled = rhs.hmacEnabled;
        this.pageSize = rhs.pageSize;
    }
}
