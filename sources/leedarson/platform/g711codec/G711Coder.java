package leedarson.platform.g711codec;

public class G711Coder {
    public static final native int g711aDecode(byte[] bArr, int i, CoderResult coderResult);

    public static final native int g711aEncode(byte[] bArr, int i, CoderResult coderResult);

    public static final native int g711uDecode(byte[] bArr, int i, CoderResult coderResult);

    public static final native int g711uEncode(byte[] bArr, int i, CoderResult coderResult);

    public static final native int init();

    public static final native void unInit();

    static {
        System.loadLibrary("G711");
    }

    public static class CoderResult {
        public byte[] dataArr;
        public int dataLen;

        public void setData(byte[] dataArr2, int dataLen2) {
            this.dataArr = dataArr2;
            this.dataLen = dataLen2;
        }
    }
}
