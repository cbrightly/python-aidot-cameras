package io.microshow.rxffmpeg.player;

import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import java.lang.ref.WeakReference;

public class MeasureHelper {
    private FitModel mFitModel = FitModel.FM_DEFAULT;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private VideoSizeInfo mVideoSizeInfo;
    private WeakReference<View> mWeakView;

    public enum FitModel {
        FM_DEFAULT,
        FM_FULL_SCREEN_WIDTH,
        FM_FULL_SCREEN_HEIGHT,
        FM_WH_16X9
    }

    public static class VideoSizeInfo {
        private float mDar;
        private int mHeight;
        private int mWidth;

        public VideoSizeInfo(int width, int height, float dar) {
            this.mWidth = width;
            this.mHeight = height;
            this.mDar = dar;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public float getDar() {
            return this.mDar;
        }
    }

    public void setFitModel(FitModel fitModel) {
        this.mFitModel = fitModel;
    }

    public FitModel getFitModel() {
        return this.mFitModel;
    }

    public MeasureHelper(View view) {
        this.mWeakView = new WeakReference<>(view);
    }

    public View getView() {
        View view;
        WeakReference<View> weakReference = this.mWeakView;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return null;
        }
        return view;
    }

    public void setVideoSizeInfo(VideoSizeInfo videoSizeInfo) {
        this.mVideoSizeInfo = videoSizeInfo;
    }

    public VideoSizeInfo getVideoSizeInfo() {
        return this.mVideoSizeInfo;
    }

    public boolean isFullScreen() {
        return false;
    }

    public void setDefaultVideoLayoutParams() {
        View view = getView();
        if (view instanceof RxFFmpegPlayerView) {
            RxFFmpegPlayerView mPlayerView = (RxFFmpegPlayerView) view;
            int width = Helper.getScreenWidth(view.getContext());
            int height = (width * 9) / 16;
            setVideoSizeInfo(new VideoSizeInfo(width, height, ((float) width) / ((float) height)));
            setVideoLayoutParams(mPlayerView.getTextureView(), mPlayerView.getContainerView());
        }
    }

    public void setVideoLayoutParams(TextureView textureView, FrameLayout container) {
        int viewHeight;
        if (textureView != null && container != null && getVideoSizeInfo() != null) {
            int videoWidth = getVideoSizeInfo().getWidth();
            int videoHeight = getVideoSizeInfo().getHeight();
            float dar = getVideoSizeInfo().getDar();
            float videoAspect = ((float) videoWidth) / ((float) videoHeight);
            int viewWidth = Helper.getScreenWidth(getView().getContext());
            if (isFullScreen()) {
                viewHeight = Helper.getScreenHeight(getView().getContext());
                viewWidth = (int) (((float) viewHeight) * videoAspect);
            } else {
                FitModel fitModel = this.mFitModel;
                if (fitModel == FitModel.FM_FULL_SCREEN_WIDTH) {
                    viewHeight = (int) (((float) viewWidth) / videoAspect);
                } else if (fitModel == FitModel.FM_FULL_SCREEN_HEIGHT) {
                    viewHeight = Helper.getFullScreenHeight(getView().getContext());
                    viewWidth = (int) (((float) viewHeight) * videoAspect);
                } else if (fitModel == FitModel.FM_WH_16X9) {
                    viewHeight = (viewWidth * 9) / 16;
                    viewWidth = (int) (((float) viewHeight) * videoAspect);
                } else if (videoWidth > videoHeight) {
                    viewHeight = (int) (((float) viewWidth) / videoAspect);
                } else if (videoWidth < videoHeight) {
                    viewHeight = viewWidth;
                    viewWidth = (int) (((float) viewHeight) * videoAspect);
                } else {
                    viewHeight = viewWidth;
                }
            }
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(viewWidth, viewHeight);
            params.gravity = 17;
            textureView.setLayoutParams(params);
            container.setLayoutParams(new FrameLayout.LayoutParams(Helper.getScreenWidth(getView().getContext()), viewHeight));
            this.mMeasuredHeight = viewHeight;
            getView().requestLayout();
        }
    }

    public int[] doMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int viewHeight;
        int viewWidth;
        FitModel fitModel = this.mFitModel;
        if (fitModel == FitModel.FM_DEFAULT || fitModel == FitModel.FM_FULL_SCREEN_HEIGHT) {
            viewWidth = widthMeasureSpec;
            viewHeight = this.mMeasuredHeight;
        } else {
            viewWidth = widthMeasureSpec;
            viewHeight = heightMeasureSpec;
        }
        return new int[]{viewWidth, viewHeight};
    }
}
