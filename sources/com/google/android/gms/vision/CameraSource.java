package com.google.android.gms.vision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.common.images.Size;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public class CameraSource {
    @SuppressLint({"InlinedApi"})
    public static final int CAMERA_FACING_BACK = 0;
    @SuppressLint({"InlinedApi"})
    public static final int CAMERA_FACING_FRONT = 1;
    /* access modifiers changed from: private */
    public Context zza;
    /* access modifiers changed from: private */
    public final Object zzb;
    /* access modifiers changed from: private */
    @GuardedBy("cameraLock")
    @Nullable
    public Camera zzc;
    /* access modifiers changed from: private */
    public int zzd;
    /* access modifiers changed from: private */
    public int zze;
    /* access modifiers changed from: private */
    public Size zzf;
    /* access modifiers changed from: private */
    public float zzg;
    /* access modifiers changed from: private */
    public int zzh;
    /* access modifiers changed from: private */
    public int zzi;
    /* access modifiers changed from: private */
    public boolean zzj;
    /* access modifiers changed from: private */
    @Nullable
    public String zzk;
    @Nullable
    private SurfaceTexture zzl;
    @Nullable
    private Thread zzm;
    /* access modifiers changed from: private */
    public zza zzn;
    /* access modifiers changed from: private */
    public final IdentityHashMap<byte[], ByteBuffer> zzo;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public interface PictureCallback {
        void onPictureTaken(@RecentlyNonNull byte[] bArr);
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public interface ShutterCallback {
        void onShutter();
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public final class zzb implements Camera.PreviewCallback {
        private zzb() {
        }

        public final void onPreviewFrame(byte[] bArr, Camera camera) {
            CameraSource.this.zzn.zza(bArr, camera);
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public final class zzc implements Camera.PictureCallback {
        /* access modifiers changed from: private */
        @Nullable
        public PictureCallback zza;

        private zzc() {
        }

        public final void onPictureTaken(byte[] bArr, Camera camera) {
            PictureCallback pictureCallback = this.zza;
            if (pictureCallback != null) {
                pictureCallback.onPictureTaken(bArr);
            }
            synchronized (CameraSource.this.zzb) {
                if (CameraSource.this.zzc != null) {
                    CameraSource.this.zzc.startPreview();
                }
            }
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static final class zzd implements Camera.ShutterCallback {
        /* access modifiers changed from: private */
        @Nullable
        public ShutterCallback zza;

        private zzd() {
        }

        public final void onShutter() {
            ShutterCallback shutterCallback = this.zza;
            if (shutterCallback != null) {
                shutterCallback.onShutter();
            }
        }
    }

    public void release() {
        synchronized (this.zzb) {
            stop();
            this.zzn.zza();
        }
    }

    @VisibleForTesting
    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static final class zze {
        private Size zza;
        private Size zzb;

        public zze(Camera.Size size, @Nullable Camera.Size size2) {
            this.zza = new Size(size.width, size.height);
            if (size2 != null) {
                this.zzb = new Size(size2.width, size2.height);
            }
        }

        public final Size zza() {
            return this.zza;
        }

        @Nullable
        public final Size zzb() {
            return this.zzb;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x003e, code lost:
        return r3;
     */
    @androidx.annotation.RecentlyNonNull
    @androidx.annotation.RequiresPermission("android.permission.CAMERA")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.vision.CameraSource start() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.zzb
            monitor-enter(r0)
            android.hardware.Camera r1 = r3.zzc     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            return r3
        L_0x0009:
            android.hardware.Camera r1 = r3.zza()     // Catch:{ all -> 0x003f }
            r3.zzc = r1     // Catch:{ all -> 0x003f }
            android.graphics.SurfaceTexture r1 = new android.graphics.SurfaceTexture     // Catch:{ all -> 0x003f }
            r2 = 100
            r1.<init>(r2)     // Catch:{ all -> 0x003f }
            r3.zzl = r1     // Catch:{ all -> 0x003f }
            android.hardware.Camera r2 = r3.zzc     // Catch:{ all -> 0x003f }
            r2.setPreviewTexture(r1)     // Catch:{ all -> 0x003f }
            android.hardware.Camera r1 = r3.zzc     // Catch:{ all -> 0x003f }
            r1.startPreview()     // Catch:{ all -> 0x003f }
            java.lang.Thread r1 = new java.lang.Thread     // Catch:{ all -> 0x003f }
            com.google.android.gms.vision.CameraSource$zza r2 = r3.zzn     // Catch:{ all -> 0x003f }
            r1.<init>(r2)     // Catch:{ all -> 0x003f }
            r3.zzm = r1     // Catch:{ all -> 0x003f }
            java.lang.String r2 = "gms.vision.CameraSource"
            r1.setName(r2)     // Catch:{ all -> 0x003f }
            com.google.android.gms.vision.CameraSource$zza r1 = r3.zzn     // Catch:{ all -> 0x003f }
            r2 = 1
            r1.zza(r2)     // Catch:{ all -> 0x003f }
            java.lang.Thread r1 = r3.zzm     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x003d
            r1.start()     // Catch:{ all -> 0x003f }
        L_0x003d:
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            return r3
        L_0x003f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.vision.CameraSource.start():com.google.android.gms.vision.CameraSource");
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public final class zza implements Runnable {
        @Nullable
        private Detector<?> zza;
        private long zzb = SystemClock.elapsedRealtime();
        private final Object zzc = new Object();
        private boolean zzd = true;
        private long zze;
        private int zzf = 0;
        @Nullable
        private ByteBuffer zzg;

        zza(Detector<?> detector) {
            this.zza = detector;
        }

        /* access modifiers changed from: package-private */
        @SuppressLint({"Assert"})
        public final void zza() {
            Detector<?> detector = this.zza;
            if (detector != null) {
                detector.release();
                this.zza = null;
            }
        }

        /* access modifiers changed from: package-private */
        public final void zza(boolean z) {
            synchronized (this.zzc) {
                this.zzd = z;
                this.zzc.notifyAll();
            }
        }

        /* access modifiers changed from: package-private */
        public final void zza(byte[] bArr, Camera camera) {
            synchronized (this.zzc) {
                ByteBuffer byteBuffer = this.zzg;
                if (byteBuffer != null) {
                    camera.addCallbackBuffer(byteBuffer.array());
                    this.zzg = null;
                }
                if (!CameraSource.this.zzo.containsKey(bArr)) {
                    Log.d("CameraSource", "Skipping frame. Could not find ByteBuffer associated with the image data from the camera.");
                    return;
                }
                this.zze = SystemClock.elapsedRealtime() - this.zzb;
                this.zzf++;
                this.zzg = (ByteBuffer) CameraSource.this.zzo.get(bArr);
                this.zzc.notifyAll();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            ((com.google.android.gms.vision.Detector) com.google.android.gms.common.internal.Preconditions.checkNotNull(r6.zza)).receiveFrame(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x008c, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:24:0x008e, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
            android.util.Log.e("CameraSource", "Exception thrown from receiver.", r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x00b1, code lost:
            ((android.hardware.Camera) com.google.android.gms.common.internal.Preconditions.checkNotNull(com.google.android.gms.vision.CameraSource.zzb(r6.zzh))).addCallbackBuffer(((java.nio.ByteBuffer) com.google.android.gms.common.internal.Preconditions.checkNotNull(r2)).array());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ca, code lost:
            throw r0;
         */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x001f A[Catch:{ InterruptedException -> 0x0011 }] */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x001d A[SYNTHETIC] */
        @android.annotation.SuppressLint({"InlinedApi"})
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r6 = this;
            L_0x0000:
                java.lang.Object r0 = r6.zzc
                monitor-enter(r0)
            L_0x0003:
                boolean r1 = r6.zzd     // Catch:{ all -> 0x00cb }
                if (r1 == 0) goto L_0x001b
                java.nio.ByteBuffer r2 = r6.zzg     // Catch:{ all -> 0x00cb }
                if (r2 != 0) goto L_0x001b
                java.lang.Object r1 = r6.zzc     // Catch:{ InterruptedException -> 0x0011 }
                r1.wait()     // Catch:{ InterruptedException -> 0x0011 }
                goto L_0x0003
            L_0x0011:
                r1 = move-exception
                java.lang.String r2 = "CameraSource"
                java.lang.String r3 = "Frame processing loop terminated."
                android.util.Log.d(r2, r3, r1)     // Catch:{ all -> 0x00cb }
                monitor-exit(r0)     // Catch:{ all -> 0x00cb }
                return
            L_0x001b:
                if (r1 != 0) goto L_0x001f
                monitor-exit(r0)     // Catch:{ all -> 0x00cb }
                return
            L_0x001f:
                com.google.android.gms.vision.Frame$Builder r1 = new com.google.android.gms.vision.Frame$Builder     // Catch:{ all -> 0x00cb }
                r1.<init>()     // Catch:{ all -> 0x00cb }
                java.nio.ByteBuffer r2 = r6.zzg     // Catch:{ all -> 0x00cb }
                java.lang.Object r2 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r2)     // Catch:{ all -> 0x00cb }
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2     // Catch:{ all -> 0x00cb }
                com.google.android.gms.vision.CameraSource r3 = com.google.android.gms.vision.CameraSource.this     // Catch:{ all -> 0x00cb }
                com.google.android.gms.common.images.Size r3 = r3.zzf     // Catch:{ all -> 0x00cb }
                int r3 = r3.getWidth()     // Catch:{ all -> 0x00cb }
                com.google.android.gms.vision.CameraSource r4 = com.google.android.gms.vision.CameraSource.this     // Catch:{ all -> 0x00cb }
                com.google.android.gms.common.images.Size r4 = r4.zzf     // Catch:{ all -> 0x00cb }
                int r4 = r4.getHeight()     // Catch:{ all -> 0x00cb }
                r5 = 17
                com.google.android.gms.vision.Frame$Builder r1 = r1.setImageData(r2, r3, r4, r5)     // Catch:{ all -> 0x00cb }
                int r2 = r6.zzf     // Catch:{ all -> 0x00cb }
                com.google.android.gms.vision.Frame$Builder r1 = r1.setId(r2)     // Catch:{ all -> 0x00cb }
                long r2 = r6.zze     // Catch:{ all -> 0x00cb }
                com.google.android.gms.vision.Frame$Builder r1 = r1.setTimestampMillis(r2)     // Catch:{ all -> 0x00cb }
                com.google.android.gms.vision.CameraSource r2 = com.google.android.gms.vision.CameraSource.this     // Catch:{ all -> 0x00cb }
                int r2 = r2.zze     // Catch:{ all -> 0x00cb }
                com.google.android.gms.vision.Frame$Builder r1 = r1.setRotation(r2)     // Catch:{ all -> 0x00cb }
                com.google.android.gms.vision.Frame r1 = r1.build()     // Catch:{ all -> 0x00cb }
                java.nio.ByteBuffer r2 = r6.zzg     // Catch:{ all -> 0x00cb }
                r3 = 0
                r6.zzg = r3     // Catch:{ all -> 0x00cb }
                monitor-exit(r0)     // Catch:{ all -> 0x00cb }
                com.google.android.gms.vision.Detector<?> r0 = r6.zza     // Catch:{ Exception -> 0x008e }
                java.lang.Object r0 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)     // Catch:{ Exception -> 0x008e }
                com.google.android.gms.vision.Detector r0 = (com.google.android.gms.vision.Detector) r0     // Catch:{ Exception -> 0x008e }
                r0.receiveFrame(r1)     // Catch:{ Exception -> 0x008e }
                com.google.android.gms.vision.CameraSource r0 = com.google.android.gms.vision.CameraSource.this
                android.hardware.Camera r0 = r0.zzc
                java.lang.Object r0 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)
                android.hardware.Camera r0 = (android.hardware.Camera) r0
                java.lang.Object r1 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r2)
                java.nio.ByteBuffer r1 = (java.nio.ByteBuffer) r1
                byte[] r1 = r1.array()
                r0.addCallbackBuffer(r1)
                goto L_0x0000
            L_0x008c:
                r0 = move-exception
                goto L_0x00b1
            L_0x008e:
                r0 = move-exception
                java.lang.String r1 = "CameraSource"
                java.lang.String r3 = "Exception thrown from receiver."
                android.util.Log.e(r1, r3, r0)     // Catch:{ all -> 0x008c }
                com.google.android.gms.vision.CameraSource r0 = com.google.android.gms.vision.CameraSource.this
                android.hardware.Camera r0 = r0.zzc
                java.lang.Object r0 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)
                android.hardware.Camera r0 = (android.hardware.Camera) r0
                java.lang.Object r1 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r2)
                java.nio.ByteBuffer r1 = (java.nio.ByteBuffer) r1
                byte[] r1 = r1.array()
                r0.addCallbackBuffer(r1)
                goto L_0x0000
            L_0x00b1:
                com.google.android.gms.vision.CameraSource r1 = com.google.android.gms.vision.CameraSource.this
                android.hardware.Camera r1 = r1.zzc
                java.lang.Object r1 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r1)
                android.hardware.Camera r1 = (android.hardware.Camera) r1
                java.lang.Object r2 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r2)
                java.nio.ByteBuffer r2 = (java.nio.ByteBuffer) r2
                byte[] r2 = r2.array()
                r1.addCallbackBuffer(r2)
                throw r0
            L_0x00cb:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00cb }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.vision.CameraSource.zza.run():void");
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static class Builder {
        private final Detector<?> zza;
        private CameraSource zzb;

        public Builder(@RecentlyNonNull Context context, @RecentlyNonNull Detector<?> detector) {
            CameraSource cameraSource = new CameraSource();
            this.zzb = cameraSource;
            if (context == null) {
                throw new IllegalArgumentException("No context supplied.");
            } else if (detector != null) {
                this.zza = detector;
                Context unused = cameraSource.zza = context;
            } else {
                throw new IllegalArgumentException("No detector supplied.");
            }
        }

        @RecentlyNonNull
        public Builder setRequestedFps(float f) {
            if (f > 0.0f) {
                float unused = this.zzb.zzg = f;
                return this;
            }
            StringBuilder sb = new StringBuilder(28);
            sb.append("Invalid fps: ");
            sb.append(f);
            throw new IllegalArgumentException(sb.toString());
        }

        @RecentlyNonNull
        public Builder setRequestedPreviewSize(int i, int i2) {
            if (i <= 0 || i > 1000000 || i2 <= 0 || i2 > 1000000) {
                StringBuilder sb = new StringBuilder(45);
                sb.append("Invalid preview size: ");
                sb.append(i);
                sb.append("x");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            }
            int unused = this.zzb.zzh = i;
            int unused2 = this.zzb.zzi = i2;
            return this;
        }

        @RecentlyNonNull
        public Builder setFacing(int i) {
            if (i == 0 || i == 1) {
                int unused = this.zzb.zzd = i;
                return this;
            }
            StringBuilder sb = new StringBuilder(27);
            sb.append("Invalid camera: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }

        @RecentlyNonNull
        public Builder setAutoFocusEnabled(boolean z) {
            boolean unused = this.zzb.zzj = z;
            return this;
        }

        @RecentlyNonNull
        public Builder setFocusMode(@RecentlyNonNull String str) {
            if (str.equals("continuous-video") || str.equals("continuous-picture")) {
                String unused = this.zzb.zzk = str;
            } else {
                Log.w("CameraSource", String.format("FocusMode %s is not supported for now.", new Object[]{str}));
                String unused2 = this.zzb.zzk = null;
            }
            return this;
        }

        @RecentlyNonNull
        public CameraSource build() {
            CameraSource cameraSource = this.zzb;
            cameraSource.getClass();
            zza unused = cameraSource.zzn = new zza(this.zza);
            return this.zzb;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002e, code lost:
        return r2;
     */
    @androidx.annotation.RecentlyNonNull
    @androidx.annotation.RequiresPermission("android.permission.CAMERA")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.vision.CameraSource start(@androidx.annotation.RecentlyNonNull android.view.SurfaceHolder r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.zzb
            monitor-enter(r0)
            android.hardware.Camera r1 = r2.zzc     // Catch:{ all -> 0x002f }
            if (r1 == 0) goto L_0x0009
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return r2
        L_0x0009:
            android.hardware.Camera r1 = r2.zza()     // Catch:{ all -> 0x002f }
            r2.zzc = r1     // Catch:{ all -> 0x002f }
            r1.setPreviewDisplay(r3)     // Catch:{ all -> 0x002f }
            android.hardware.Camera r3 = r2.zzc     // Catch:{ all -> 0x002f }
            r3.startPreview()     // Catch:{ all -> 0x002f }
            java.lang.Thread r3 = new java.lang.Thread     // Catch:{ all -> 0x002f }
            com.google.android.gms.vision.CameraSource$zza r1 = r2.zzn     // Catch:{ all -> 0x002f }
            r3.<init>(r1)     // Catch:{ all -> 0x002f }
            r2.zzm = r3     // Catch:{ all -> 0x002f }
            com.google.android.gms.vision.CameraSource$zza r3 = r2.zzn     // Catch:{ all -> 0x002f }
            r1 = 1
            r3.zza(r1)     // Catch:{ all -> 0x002f }
            java.lang.Thread r3 = r2.zzm     // Catch:{ all -> 0x002f }
            if (r3 == 0) goto L_0x002d
            r3.start()     // Catch:{ all -> 0x002f }
        L_0x002d:
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return r2
        L_0x002f:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.vision.CameraSource.start(android.view.SurfaceHolder):com.google.android.gms.vision.CameraSource");
    }

    public void stop() {
        synchronized (this.zzb) {
            this.zzn.zza(false);
            Thread thread = this.zzm;
            if (thread != null) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Log.d("CameraSource", "Frame processing thread interrupted on release.");
                }
                this.zzm = null;
            }
            Camera camera = this.zzc;
            if (camera != null) {
                camera.stopPreview();
                this.zzc.setPreviewCallbackWithBuffer((Camera.PreviewCallback) null);
                try {
                    this.zzc.setPreviewTexture((SurfaceTexture) null);
                    this.zzl = null;
                    this.zzc.setPreviewDisplay((SurfaceHolder) null);
                } catch (Exception e2) {
                    String valueOf = String.valueOf(e2);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 32);
                    sb.append("Failed to clear camera preview: ");
                    sb.append(valueOf);
                    Log.e("CameraSource", sb.toString());
                }
                ((Camera) Preconditions.checkNotNull(this.zzc)).release();
                this.zzc = null;
            }
            this.zzo.clear();
        }
    }

    @RecentlyNonNull
    public Size getPreviewSize() {
        return this.zzf;
    }

    public int getCameraFacing() {
        return this.zzd;
    }

    public void takePicture(@Nullable ShutterCallback shutterCallback, @Nullable PictureCallback pictureCallback) {
        synchronized (this.zzb) {
            if (this.zzc != null) {
                zzd zzd2 = new zzd();
                ShutterCallback unused = zzd2.zza = shutterCallback;
                zzc zzc2 = new zzc();
                PictureCallback unused2 = zzc2.zza = pictureCallback;
                this.zzc.takePicture(zzd2, (Camera.PictureCallback) null, (Camera.PictureCallback) null, zzc2);
            }
        }
    }

    private CameraSource() {
        this.zzb = new Object();
        this.zzd = 0;
        this.zzg = 30.0f;
        this.zzh = 1024;
        this.zzi = AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART;
        this.zzj = false;
        this.zzo = new IdentityHashMap<>();
    }

    @SuppressLint({"InlinedApi"})
    private final Camera zza() {
        int i;
        int i2;
        int i3;
        int i4 = this.zzd;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int i5 = 0;
        while (true) {
            if (i5 >= Camera.getNumberOfCameras()) {
                i5 = -1;
                break;
            }
            Camera.getCameraInfo(i5, cameraInfo);
            if (cameraInfo.facing == i4) {
                break;
            }
            i5++;
        }
        if (i5 != -1) {
            Camera open = Camera.open(i5);
            int i6 = this.zzh;
            int i7 = this.zzi;
            Camera.Parameters parameters = open.getParameters();
            List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
            List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
            ArrayList arrayList = new ArrayList();
            for (Camera.Size next : supportedPreviewSizes) {
                float f = ((float) next.width) / ((float) next.height);
                Iterator<Camera.Size> it = supportedPictureSizes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Camera.Size next2 = it.next();
                    if (Math.abs(f - (((float) next2.width) / ((float) next2.height))) < 0.01f) {
                        arrayList.add(new zze(next, next2));
                        break;
                    }
                }
            }
            if (arrayList.size() == 0) {
                Log.w("CameraSource", "No preview sizes have a corresponding same-aspect-ratio picture size");
                for (Camera.Size zze2 : supportedPreviewSizes) {
                    arrayList.add(new zze(zze2, (Camera.Size) null));
                }
            }
            int size = arrayList.size();
            int i8 = Integer.MAX_VALUE;
            int i9 = 0;
            int i10 = Integer.MAX_VALUE;
            zze zze3 = null;
            while (i9 < size) {
                Object obj = arrayList.get(i9);
                i9++;
                zze zze4 = (zze) obj;
                Size zza2 = zze4.zza();
                int abs = Math.abs(zza2.getWidth() - i6) + Math.abs(zza2.getHeight() - i7);
                if (abs < i10) {
                    zze3 = zze4;
                    i10 = abs;
                }
            }
            zze zze5 = (zze) Preconditions.checkNotNull(zze3);
            if (zze5 != null) {
                Size zzb2 = zze5.zzb();
                this.zzf = zze5.zza();
                int i11 = (int) (this.zzg * 1000.0f);
                int[] iArr = null;
                for (int[] next3 : open.getParameters().getSupportedPreviewFpsRange()) {
                    int abs2 = Math.abs(i11 - next3[0]) + Math.abs(i11 - next3[1]);
                    if (abs2 < i8) {
                        iArr = next3;
                        i8 = abs2;
                    }
                }
                int[] iArr2 = (int[]) Preconditions.checkNotNull(iArr);
                if (iArr2 != null) {
                    Camera.Parameters parameters2 = open.getParameters();
                    if (zzb2 != null) {
                        parameters2.setPictureSize(zzb2.getWidth(), zzb2.getHeight());
                    }
                    parameters2.setPreviewSize(this.zzf.getWidth(), this.zzf.getHeight());
                    parameters2.setPreviewFpsRange(iArr2[0], iArr2[1]);
                    parameters2.setPreviewFormat(17);
                    int rotation = ((WindowManager) Preconditions.checkNotNull((WindowManager) this.zza.getSystemService("window"))).getDefaultDisplay().getRotation();
                    switch (rotation) {
                        case 0:
                            i = 0;
                            break;
                        case 1:
                            i = 90;
                            break;
                        case 2:
                            i = 180;
                            break;
                        case 3:
                            i = SubsamplingScaleImageView.ORIENTATION_270;
                            break;
                        default:
                            StringBuilder sb = new StringBuilder(31);
                            sb.append("Bad rotation value: ");
                            sb.append(rotation);
                            Log.e("CameraSource", sb.toString());
                            i = 0;
                            break;
                    }
                    Camera.CameraInfo cameraInfo2 = new Camera.CameraInfo();
                    Camera.getCameraInfo(i5, cameraInfo2);
                    if (cameraInfo2.facing == 1) {
                        i2 = (cameraInfo2.orientation + i) % 360;
                        i3 = (360 - i2) % 360;
                    } else {
                        i2 = ((cameraInfo2.orientation - i) + 360) % 360;
                        i3 = i2;
                    }
                    this.zze = i2 / 90;
                    open.setDisplayOrientation(i3);
                    parameters2.setRotation(i2);
                    if (this.zzk != null) {
                        if (parameters2.getSupportedFocusModes().contains(this.zzk)) {
                            parameters2.setFocusMode((String) Preconditions.checkNotNull(this.zzk));
                        } else {
                            Log.w("CameraSource", String.format("FocusMode %s is not supported on this device.", new Object[]{this.zzk}));
                            this.zzk = null;
                        }
                    }
                    if (this.zzk == null && this.zzj) {
                        if (parameters2.getSupportedFocusModes().contains("continuous-video")) {
                            parameters2.setFocusMode("continuous-video");
                            this.zzk = "continuous-video";
                        } else {
                            Log.i("CameraSource", "Camera auto focus is not supported on this device.");
                        }
                    }
                    open.setParameters(parameters2);
                    open.setPreviewCallbackWithBuffer(new zzb());
                    open.addCallbackBuffer(zza(this.zzf));
                    open.addCallbackBuffer(zza(this.zzf));
                    open.addCallbackBuffer(zza(this.zzf));
                    open.addCallbackBuffer(zza(this.zzf));
                    return open;
                }
                throw new IOException("Could not find suitable preview frames per second range.");
            }
            throw new IOException("Could not find suitable preview size.");
        }
        throw new IOException("Could not find requested camera.");
    }

    @SuppressLint({"InlinedApi"})
    private final byte[] zza(Size size) {
        byte[] bArr = new byte[(((int) Math.ceil(((double) ((((long) size.getHeight()) * ((long) size.getWidth())) * ((long) ImageFormat.getBitsPerPixel(17)))) / 8.0d)) + 1)];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        if (!wrap.hasArray() || wrap.array() != bArr) {
            throw new IllegalStateException("Failed to create valid buffer for camera source.");
        }
        this.zzo.put(bArr, wrap);
        return bArr;
    }
}
