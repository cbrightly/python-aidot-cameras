package com.yalantis.ucrop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.SdkVersionUtils;
import com.yalantis.ucrop.PicturePhotoGalleryAdapter;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.util.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PictureMultiCuttingActivity extends UCropActivity {
    private static final int MIN_NUM = 1;
    /* access modifiers changed from: private */
    public int cutIndex;
    private boolean isAnimation;
    private boolean isCamera;
    private boolean isWithVideoImage;
    /* access modifiers changed from: private */
    public final ArrayList<LocalMedia> list = new ArrayList<>();
    private PicturePhotoGalleryAdapter mAdapter;
    private RecyclerView mRecyclerView;
    /* access modifiers changed from: private */
    public int oldCutIndex;
    private String renameCropFilename;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.renameCropFilename = intent.getStringExtra(UCrop.Options.EXTRA_RENAME_CROP_FILENAME);
        this.isCamera = intent.getBooleanExtra(UCrop.Options.EXTRA_CAMERA, false);
        this.isWithVideoImage = intent.getBooleanExtra(UCrop.Options.EXTRA_WITH_VIDEO_IMAGE, false);
        List<LocalMedia> localMedia = getIntent().getParcelableArrayListExtra(UCrop.Options.EXTRA_CUT_CROP);
        this.isAnimation = getIntent().getBooleanExtra(UCrop.Options.EXTRA_MULTIPLE_RECYCLERANIMATION, true);
        if (localMedia == null || localMedia.size() == 0) {
            onBackPressed();
            return;
        }
        this.list.addAll(localMedia);
        if (this.list.size() > 1) {
            initLoadCutData();
            addPhotoRecyclerView();
        }
    }

    private void addPhotoRecyclerView() {
        boolean isMultipleSkipCrop = getIntent().getBooleanExtra(UCrop.Options.EXTRA_SKIP_MULTIPLE_CROP, true);
        RecyclerView recyclerView = new RecyclerView(this);
        this.mRecyclerView = recyclerView;
        int i = R.id.id_recycler;
        recyclerView.setId(i);
        this.mRecyclerView.setBackgroundColor(ContextCompat.getColor(this, R.color.ucrop_color_widget_background));
        this.mRecyclerView.setLayoutParams(new RelativeLayout.LayoutParams(-1, ScreenUtils.dip2px(this, 80.0f)));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(0);
        if (this.isAnimation) {
            this.mRecyclerView.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(getApplicationContext(), R.anim.ucrop_layout_animation_fall_down));
        }
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
        Objects.requireNonNull(itemAnimator);
        ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        resetCutDataStatus();
        this.list.get(this.cutIndex).setCut(true);
        PicturePhotoGalleryAdapter picturePhotoGalleryAdapter = new PicturePhotoGalleryAdapter(this.list);
        this.mAdapter = picturePhotoGalleryAdapter;
        this.mRecyclerView.setAdapter(picturePhotoGalleryAdapter);
        if (isMultipleSkipCrop) {
            this.mAdapter.setOnItemClickListener(new PicturePhotoGalleryAdapter.OnItemClickListener() {
                public void onItemClick(int position, View view) {
                    if (!PictureMimeType.isHasVideo(((LocalMedia) PictureMultiCuttingActivity.this.list.get(position)).getMimeType()) && PictureMultiCuttingActivity.this.cutIndex != position) {
                        PictureMultiCuttingActivity.this.resetLastCropStatus();
                        int unused = PictureMultiCuttingActivity.this.cutIndex = position;
                        PictureMultiCuttingActivity pictureMultiCuttingActivity = PictureMultiCuttingActivity.this;
                        int unused2 = pictureMultiCuttingActivity.oldCutIndex = pictureMultiCuttingActivity.cutIndex;
                        PictureMultiCuttingActivity.this.resetCutData();
                    }
                }
            });
        }
        this.uCropPhotoBox.addView(this.mRecyclerView);
        changeLayoutParams(this.mShowBottomControls);
        ((RelativeLayout.LayoutParams) ((FrameLayout) findViewById(R.id.ucrop_frame)).getLayoutParams()).addRule(2, i);
        ((RelativeLayout.LayoutParams) this.mRecyclerView.getLayoutParams()).addRule(2, R.id.controls_wrapper);
    }

    /* access modifiers changed from: protected */
    public void resetCutData() {
        Uri uri;
        String str;
        this.uCropPhotoBox.removeView(this.mRecyclerView);
        View view = this.mBlockingView;
        if (view != null) {
            this.uCropPhotoBox.removeView(view);
        }
        setContentView(R.layout.ucrop_activity_photobox);
        this.uCropPhotoBox = (RelativeLayout) findViewById(R.id.ucrop_photobox);
        addBlockingView();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras == null) {
            extras = new Bundle();
        }
        LocalMedia cutInfo = this.list.get(this.cutIndex);
        String path = cutInfo.getPath();
        boolean isHttp = PictureMimeType.isHasHttp(path);
        String suffix = PictureMimeType.getLastImgType(PictureMimeType.isContent(path) ? FileUtils.getPath(this, Uri.parse(path)) : path);
        if (!TextUtils.isEmpty(cutInfo.getAndroidQToPath())) {
            uri = Uri.fromFile(new File(cutInfo.getAndroidQToPath()));
        } else {
            uri = (isHttp || PictureMimeType.isContent(path)) ? Uri.parse(path) : Uri.fromFile(new File(path));
        }
        extras.putParcelable(UCrop.EXTRA_INPUT_URI, uri);
        File file = Environment.getExternalStorageState().equals("mounted") ? getExternalFilesDir(Environment.DIRECTORY_PICTURES) : getCacheDir();
        if (TextUtils.isEmpty(this.renameCropFilename)) {
            str = DateUtils.getCreateFileName("IMG_CROP_") + suffix;
        } else {
            str = this.isCamera ? this.renameCropFilename : FileUtils.rename(this.renameCropFilename);
        }
        extras.putParcelable(UCrop.EXTRA_OUTPUT_URI, Uri.fromFile(new File(file, str)));
        intent.putExtras(extras);
        setupViews(intent);
        refreshPhotoRecyclerData();
        setImageData(intent);
        setInitialState();
        int scrollWidth = this.cutIndex * ScreenUtils.dip2px(this, 60.0f);
        int i = this.mScreenWidth;
        if (((double) scrollWidth) > ((double) i) * 0.8d) {
            this.mRecyclerView.scrollBy(ScreenUtils.dip2px(this, 60.0f), 0);
        } else if (((double) scrollWidth) < ((double) i) * 0.4d) {
            this.mRecyclerView.scrollBy(ScreenUtils.dip2px(this, -60.0f), 0);
        }
    }

    private void refreshPhotoRecyclerData() {
        resetCutDataStatus();
        this.list.get(this.cutIndex).setCut(true);
        this.mAdapter.notifyItemChanged(this.cutIndex);
        this.uCropPhotoBox.addView(this.mRecyclerView);
        changeLayoutParams(this.mShowBottomControls);
        ((RelativeLayout.LayoutParams) ((FrameLayout) findViewById(R.id.ucrop_frame)).getLayoutParams()).addRule(2, R.id.id_recycler);
        ((RelativeLayout.LayoutParams) this.mRecyclerView.getLayoutParams()).addRule(2, R.id.controls_wrapper);
    }

    /* access modifiers changed from: private */
    public void resetLastCropStatus() {
        int i;
        int size = this.list.size();
        if (size > 1 && size > (i = this.oldCutIndex)) {
            this.list.get(i).setCut(false);
            this.mAdapter.notifyItemChanged(this.cutIndex);
        }
    }

    private void resetCutDataStatus() {
        int size = this.list.size();
        for (int i = 0; i < size; i++) {
            this.list.get(i).setCut(false);
        }
    }

    private void initLoadCutData() {
        ArrayList<LocalMedia> arrayList = this.list;
        if (arrayList == null || arrayList.size() == 0) {
            onBackPressed();
            return;
        }
        int size = this.list.size();
        if (this.isWithVideoImage) {
            getIndex(size);
        }
    }

    private void getIndex(int size) {
        int i = 0;
        while (i < size) {
            LocalMedia cutInfo = this.list.get(i);
            if (cutInfo == null || !PictureMimeType.isHasImage(cutInfo.getMimeType())) {
                i++;
            } else {
                this.cutIndex = i;
                return;
            }
        }
    }

    private void changeLayoutParams(boolean mShowBottomControls) {
        if (this.mRecyclerView.getLayoutParams() != null) {
            if (mShowBottomControls) {
                ((RelativeLayout.LayoutParams) this.mRecyclerView.getLayoutParams()).addRule(12, 0);
                ((RelativeLayout.LayoutParams) this.mRecyclerView.getLayoutParams()).addRule(2, R.id.wrapper_controls);
                return;
            }
            ((RelativeLayout.LayoutParams) this.mRecyclerView.getLayoutParams()).addRule(12);
            ((RelativeLayout.LayoutParams) this.mRecyclerView.getLayoutParams()).addRule(2, 0);
        }
    }

    /* access modifiers changed from: protected */
    public void setResultUri(Uri uri, float resultAspectRatio, int offsetX, int offsetY, int imageWidth, int imageHeight) {
        try {
            int size = this.list.size();
            int i = this.cutIndex;
            if (size < i) {
                onBackPressed();
                return;
            }
            LocalMedia info = this.list.get(i);
            info.setCutPath(uri.getPath());
            info.setCut(true);
            info.setCropResultAspectRatio(resultAspectRatio);
            info.setCropOffsetX(offsetX);
            info.setCropOffsetY(offsetY);
            info.setCropImageWidth(imageWidth);
            info.setCropImageHeight(imageHeight);
            info.setAndroidQToPath(SdkVersionUtils.checkedAndroid_Q() ? info.getCutPath() : info.getAndroidQToPath());
            info.setSize(!TextUtils.isEmpty(info.getCutPath()) ? new File(info.getCutPath()).length() : info.getSize());
            resetLastCropStatus();
            int i2 = this.cutIndex + 1;
            this.cutIndex = i2;
            if (this.isWithVideoImage && i2 < this.list.size() && PictureMimeType.isHasVideo(this.list.get(this.cutIndex).getMimeType())) {
                while (true) {
                    if (this.cutIndex >= this.list.size()) {
                        break;
                    } else if (PictureMimeType.isHasImage(this.list.get(this.cutIndex).getMimeType())) {
                        break;
                    } else {
                        this.cutIndex++;
                    }
                }
            }
            int i3 = this.cutIndex;
            this.oldCutIndex = i3;
            if (i3 >= this.list.size()) {
                for (int i4 = 0; i4 < this.list.size(); i4++) {
                    LocalMedia media = this.list.get(i4);
                    media.setCut(!TextUtils.isEmpty(media.getCutPath()));
                }
                setResult(-1, new Intent().putExtra(UCrop.Options.EXTRA_OUTPUT_URI_LIST, this.list));
                onBackPressed();
                return;
            }
            resetCutData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        PicturePhotoGalleryAdapter picturePhotoGalleryAdapter = this.mAdapter;
        if (picturePhotoGalleryAdapter != null) {
            picturePhotoGalleryAdapter.setOnItemClickListener((PicturePhotoGalleryAdapter.OnItemClickListener) null);
        }
        super.onDestroy();
    }
}
