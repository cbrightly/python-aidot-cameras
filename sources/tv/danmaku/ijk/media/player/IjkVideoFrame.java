package tv.danmaku.ijk.media.player;

public class IjkVideoFrame {
    private byte[] mBuffer;
    private int mHeight = 0;
    private int mWidth = 0;

    public IjkVideoFrame(int width, int height, byte[] buffer) {
        this.mWidth = width;
        this.mHeight = height;
        this.mBuffer = buffer;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public byte[] getBuffer() {
        return this.mBuffer;
    }
}
