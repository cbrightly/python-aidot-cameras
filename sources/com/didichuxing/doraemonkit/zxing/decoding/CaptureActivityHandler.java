package com.didichuxing.doraemonkit.zxing.decoding;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.zxing.activity.CaptureActivity;
import com.didichuxing.doraemonkit.zxing.camera.CameraManager;
import com.didichuxing.doraemonkit.zxing.view.ViewfinderResultPointCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import java.util.Vector;

public final class CaptureActivityHandler extends Handler {
    private static final String TAG = CaptureActivityHandler.class.getSimpleName();
    private final CaptureActivity activity;
    private final DecodeThread decodeThread;
    private State state = State.SUCCESS;

    public enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    public CaptureActivityHandler(CaptureActivity activity2, Vector<BarcodeFormat> decodeFormats, String characterSet) {
        this.activity = activity2;
        DecodeThread decodeThread2 = new DecodeThread(activity2, decodeFormats, characterSet, new ViewfinderResultPointCallback(activity2.getViewfinderView()));
        this.decodeThread = decodeThread2;
        decodeThread2.start();
        CameraManager.get().startPreview();
        restartPreviewAndDecode();
    }

    public void handleMessage(Message message) {
        Bitmap barcode;
        int i = message.what;
        int i2 = R.id.auto_focus;
        if (i == i2) {
            if (this.state == State.PREVIEW) {
                CameraManager.get().requestAutoFocus(this, i2);
            }
        } else if (i == R.id.restart_preview) {
            Log.d(TAG, "Got restart preview message");
            restartPreviewAndDecode();
        } else if (i == R.id.decode_succeeded) {
            Log.d(TAG, "Got decode succeeded message");
            this.state = State.SUCCESS;
            Bundle bundle = message.getData();
            if (bundle == null) {
                barcode = null;
            } else {
                barcode = (Bitmap) bundle.getParcelable(DecodeThread.BARCODE_BITMAP);
            }
            this.activity.handleDecode((Result) message.obj, barcode);
        } else if (i == R.id.decode_failed) {
            this.state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), R.id.decode);
        } else if (i == R.id.return_scan_result) {
            Log.d(TAG, "Got return scan result message");
            this.activity.setResult(-1, (Intent) message.obj);
            this.activity.finish();
        } else if (i == R.id.launch_product_query) {
            Log.d(TAG, "Got product query message");
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String) message.obj));
            intent.addFlags(524288);
            this.activity.startActivity(intent);
        }
    }

    public void quitSynchronously() {
        this.state = State.DONE;
        CameraManager.get().stopPreview();
        Message.obtain(this.decodeThread.getHandler(), R.id.quit).sendToTarget();
        try {
            this.decodeThread.join();
        } catch (InterruptedException e) {
        }
        removeMessages(R.id.decode_succeeded);
        removeMessages(R.id.decode_failed);
    }

    private void restartPreviewAndDecode() {
        if (this.state == State.SUCCESS) {
            this.state = State.PREVIEW;
            CameraManager.get().requestPreviewFrame(this.decodeThread.getHandler(), R.id.decode);
            CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
            this.activity.drawViewfinder();
        }
    }
}
