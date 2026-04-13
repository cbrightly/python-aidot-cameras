package com.yalantis.ucrop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import com.leedarson.serviceinterface.utils.IntentUtils;
import com.luck.picture.lib.R;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.immersive.ImmersiveManage;
import com.luck.picture.lib.tools.ScreenUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.model.AspectRatio;
import com.yalantis.ucrop.util.FileUtils;
import com.yalantis.ucrop.util.SelectedStateListDrawable;
import com.yalantis.ucrop.view.GestureCropImageView;
import com.yalantis.ucrop.view.OverlayView;
import com.yalantis.ucrop.view.TransformImageView;
import com.yalantis.ucrop.view.UCropView;
import com.yalantis.ucrop.view.widget.AspectRatioTextView;
import com.yalantis.ucrop.view.widget.HorizontalProgressWheelView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class UCropActivity extends AppCompatActivity {
    public static final int ALL = 3;
    private static final long CONTROLS_ANIMATION_DURATION = 50;
    public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
    public static final int DEFAULT_COMPRESS_QUALITY = 90;
    public static final int NONE = 0;
    public static final int ROTATE = 2;
    private static final int ROTATE_WIDGET_SENSITIVITY_COEFFICIENT = 42;
    public static final int SCALE = 1;
    private static final int SCALE_WIDGET_SENSITIVITY_COEFFICIENT = 15000;
    private static final int TABS_COUNT = 3;
    private static final String TAG = "UCropActivity";
    private boolean isDragFrame;
    private boolean isOpenWhiteStatusBar;
    private boolean isRotateEnabled;
    private boolean isScaleEnabled;
    private int mActiveControlsWidgetColor;
    private int mActiveWidgetColor;
    private int[] mAllowedGestures = {1, 2, 3};
    private List<AspectRatioTextView> mAspectRatioTextViews = new ArrayList();
    protected View mBlockingView;
    private Bitmap.CompressFormat mCompressFormat = DEFAULT_COMPRESS_FORMAT;
    private int mCompressQuality = 90;
    private Transition mControlsTransition;
    /* access modifiers changed from: private */
    public List<ViewGroup> mCropAspectRatioViews = new ArrayList();
    /* access modifiers changed from: private */
    public GestureCropImageView mGestureCropImageView;
    private TransformImageView.TransformImageListener mImageListener = new TransformImageView.TransformImageListener() {
        public void onRotate(float currentAngle) {
            UCropActivity.this.setAngleText(currentAngle);
        }

        public void onScale(float currentScale) {
            UCropActivity.this.setScaleText(currentScale);
        }

        public void onLoadComplete() {
            UCropActivity.this.mUCropView.animate().alpha(1.0f).setDuration(300).setInterpolator(new AccelerateInterpolator());
            UCropActivity uCropActivity = UCropActivity.this;
            uCropActivity.mBlockingView.setClickable(!uCropActivity.isOnTouch());
            boolean unused = UCropActivity.this.mShowLoader = false;
            UCropActivity.this.supportInvalidateOptionsMenu();
        }

        public void onLoadFailure(@NonNull Exception e) {
            UCropActivity.this.setResultError(e);
            UCropActivity.this.onBackPressed();
        }
    };
    private ViewGroup mLayoutAspectRatio;
    private ViewGroup mLayoutRotate;
    private ViewGroup mLayoutScale;
    private int mLogoColor;
    private OverlayView mOverlayView;
    @ColorInt
    private int mRootViewBackgroundColor;
    protected int mScreenWidth;
    protected boolean mShowBottomControls;
    /* access modifiers changed from: private */
    public boolean mShowLoader = true;
    private final View.OnClickListener mStateClickListener = new View.OnClickListener() {
        @SensorsDataInstrumented
        public void onClick(View view) {
            View v = view;
            if (!v.isSelected()) {
                UCropActivity.this.setWidgetState(v.getId());
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    };
    private int mStatusBarColor;
    private TextView mTextViewRotateAngle;
    private TextView mTextViewScalePercent;
    @DrawableRes
    private int mToolbarCancelDrawable;
    private int mToolbarColor;
    @DrawableRes
    private int mToolbarCropDrawable;
    private String mToolbarTitle;
    private int mToolbarWidgetColor;
    /* access modifiers changed from: private */
    public UCropView mUCropView;
    private ViewGroup mWrapperStateAspectRatio;
    private ViewGroup mWrapperStateRotate;
    private ViewGroup mWrapperStateScale;
    protected RelativeLayout uCropPhotoBox;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GestureTypes {
    }

    public boolean isImmersive() {
        return true;
    }

    public void immersive() {
        ImmersiveManage.immersiveAboveAPI23(this, this.mStatusBarColor, this.mToolbarColor, this.isOpenWhiteStatusBar);
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setNewRequestedOrientation(intent);
        getCustomOptionsData(intent);
        if (isImmersive()) {
            immersive();
        }
        setContentView(R.layout.ucrop_activity_photobox);
        this.mScreenWidth = ScreenUtils.getScreenWidth(this);
        setupViews(intent);
        setNavBar();
        setImageData(intent);
        setInitialState();
        addBlockingView();
    }

    /* access modifiers changed from: protected */
    public void setNewRequestedOrientation(Intent intent) {
        int requestedOrientation = intent.getIntExtra(UCrop.Options.EXTRA_ACTIVITY_ORIENTATION, -1);
        if (getRequestedOrientation() != requestedOrientation) {
            setRequestedOrientation(requestedOrientation);
        }
    }

    private void getCustomOptionsData(@NonNull Intent intent) {
        this.isOpenWhiteStatusBar = intent.getBooleanExtra(UCrop.Options.EXTRA_UCROP_WIDGET_CROP_OPEN_WHITE_STATUSBAR, false);
        int i = R.color.ucrop_color_statusbar;
        this.mStatusBarColor = intent.getIntExtra(UCrop.Options.EXTRA_STATUS_BAR_COLOR, ContextCompat.getColor(this, i));
        int i2 = R.color.ucrop_color_toolbar;
        int intExtra = intent.getIntExtra(UCrop.Options.EXTRA_TOOL_BAR_COLOR, ContextCompat.getColor(this, i2));
        this.mToolbarColor = intExtra;
        if (intExtra == 0) {
            this.mToolbarColor = ContextCompat.getColor(this, i2);
        }
        if (this.mStatusBarColor == 0) {
            this.mStatusBarColor = ContextCompat.getColor(this, i);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ucrop_menu_activity, menu);
        MenuItem menuItemLoader = menu.findItem(R.id.menu_loader);
        Drawable menuItemLoaderIcon = menuItemLoader.getIcon();
        if (menuItemLoaderIcon != null) {
            try {
                menuItemLoaderIcon.mutate();
                menuItemLoaderIcon.setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(this.mToolbarWidgetColor, BlendModeCompat.SRC_ATOP));
                menuItemLoader.setIcon(menuItemLoaderIcon);
            } catch (IllegalStateException e) {
                Log.i(TAG, String.format("%s - %s", new Object[]{e.getMessage(), "必須指定輸入以及輸出的 Uri"}));
            }
            ((Animatable) menuItemLoader.getIcon()).start();
        }
        MenuItem menuItemCrop = menu.findItem(R.id.menu_crop);
        Drawable menuItemCropIcon = ContextCompat.getDrawable(this, this.mToolbarCropDrawable);
        if (menuItemCropIcon != null) {
            menuItemCropIcon.mutate();
            menuItemCropIcon.setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(this.mToolbarWidgetColor, BlendModeCompat.SRC_ATOP));
            menuItemCrop.setIcon(menuItemCropIcon);
        }
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_crop).setVisible(!this.mShowLoader);
        menu.findItem(R.id.menu_loader).setVisible(this.mShowLoader);
        return super.onPrepareOptionsMenu(menu);
    }

    @SensorsDataInstrumented
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        MenuItem item = menuItem;
        if (item.getItemId() == R.id.menu_crop) {
            cropAndSaveImage();
            SensorsDataAutoTrackHelper.trackMenuItem(this, menuItem);
            return true;
        } else if (item.getItemId() == 16908332) {
            onBackPressed();
            SensorsDataAutoTrackHelper.trackMenuItem(this, menuItem);
            return true;
        } else {
            boolean onOptionsItemSelected = super.onOptionsItemSelected(item);
            SensorsDataAutoTrackHelper.trackMenuItem(this, menuItem);
            return onOptionsItemSelected;
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        GestureCropImageView gestureCropImageView = this.mGestureCropImageView;
        if (gestureCropImageView != null) {
            gestureCropImageView.cancelAllAnimations();
        }
    }

    /* access modifiers changed from: protected */
    public void setImageData(@NonNull Intent intent) {
        Uri inputUri = (Uri) intent.getParcelableExtra(UCrop.EXTRA_INPUT_URI);
        Uri outputUri = (Uri) intent.getParcelableExtra(UCrop.EXTRA_OUTPUT_URI);
        processOptions(intent);
        if (inputUri == null || outputUri == null) {
            setResultError(new NullPointerException("在你的 App 內复写颜色资源 (ucrop_color_toolbar_widget) 使 5.0 以前裝置正常运作"));
            onBackPressed();
            return;
        }
        try {
            boolean isOnTouch = isOnTouch(inputUri);
            this.mGestureCropImageView.setRotateEnabled(isOnTouch ? this.isRotateEnabled : isOnTouch);
            this.mGestureCropImageView.setScaleEnabled(isOnTouch ? this.isScaleEnabled : isOnTouch);
            this.mGestureCropImageView.setImageUri(inputUri, outputUri);
        } catch (Exception e) {
            setResultError(e);
            onBackPressed();
        }
    }

    /* access modifiers changed from: private */
    public boolean isOnTouch() {
        Uri inputUri = (Uri) getIntent().getParcelableExtra(UCrop.EXTRA_INPUT_URI);
        if (inputUri == null) {
            return true;
        }
        return isOnTouch(inputUri);
    }

    private boolean isOnTouch(Uri inputUri) {
        if (inputUri == null) {
            return true;
        }
        if (PictureMimeType.isHasHttp(inputUri.toString())) {
            return true ^ PictureMimeType.isGifForSuffix(PictureMimeType.getLastImgType(inputUri.toString()));
        }
        String mimeType = PictureMimeType.getMimeTypeFromMediaContentUri(this, inputUri);
        if (mimeType.endsWith(IntentUtils.TYPE_IMAGE)) {
            mimeType = PictureMimeType.getImageMimeType(FileUtils.getPath(this, inputUri));
        }
        return true ^ PictureMimeType.isGif(mimeType);
    }

    private void processOptions(@NonNull Intent intent) {
        String compressionFormatName = intent.getStringExtra(UCrop.Options.EXTRA_COMPRESSION_FORMAT_NAME);
        Bitmap.CompressFormat compressFormat = null;
        if (!TextUtils.isEmpty(compressionFormatName)) {
            compressFormat = Bitmap.CompressFormat.valueOf(compressionFormatName);
        }
        this.mCompressFormat = compressFormat == null ? DEFAULT_COMPRESS_FORMAT : compressFormat;
        this.mCompressQuality = intent.getIntExtra(UCrop.Options.EXTRA_COMPRESSION_QUALITY, 90);
        OverlayView overlayView = this.mOverlayView;
        Resources resources = getResources();
        int i = R.color.ucrop_color_default_crop_frame;
        overlayView.setDimmedBorderColor(intent.getIntExtra(UCrop.Options.EXTRA_DIMMED_LAYER_BORDER_COLOR, resources.getColor(i)));
        this.isDragFrame = intent.getBooleanExtra(UCrop.Options.EXTRA_DRAG_CROP_FRAME, true);
        this.mOverlayView.setDimmedStrokeWidth(intent.getIntExtra(UCrop.Options.EXTRA_CIRCLE_STROKE_WIDTH_LAYER, 1));
        this.isScaleEnabled = intent.getBooleanExtra(UCrop.Options.EXTRA_SCALE, true);
        this.isRotateEnabled = intent.getBooleanExtra(UCrop.Options.EXTRA_ROTATE, true);
        int[] allowedGestures = intent.getIntArrayExtra(UCrop.Options.EXTRA_ALLOWED_GESTURES);
        if (allowedGestures != null && allowedGestures.length == 3) {
            this.mAllowedGestures = allowedGestures;
        }
        this.mGestureCropImageView.setMaxBitmapSize(intent.getIntExtra(UCrop.Options.EXTRA_MAX_BITMAP_SIZE, 0));
        this.mGestureCropImageView.setMaxScaleMultiplier(intent.getFloatExtra(UCrop.Options.EXTRA_MAX_SCALE_MULTIPLIER, 10.0f));
        this.mGestureCropImageView.setImageToWrapCropBoundsAnimDuration((long) intent.getIntExtra(UCrop.Options.EXTRA_IMAGE_TO_CROP_BOUNDS_ANIM_DURATION, 500));
        this.mOverlayView.setFreestyleCropEnabled(intent.getBooleanExtra(UCrop.Options.EXTRA_FREE_STYLE_CROP, false));
        this.mOverlayView.setDragFrame(this.isDragFrame);
        this.mOverlayView.setDimmedColor(intent.getIntExtra(UCrop.Options.EXTRA_DIMMED_LAYER_COLOR, getResources().getColor(R.color.ucrop_color_default_dimmed)));
        this.mOverlayView.setCircleDimmedLayer(intent.getBooleanExtra(UCrop.Options.EXTRA_CIRCLE_DIMMED_LAYER, false));
        this.mOverlayView.setShowCropFrame(intent.getBooleanExtra(UCrop.Options.EXTRA_SHOW_CROP_FRAME, true));
        this.mOverlayView.setCropFrameColor(intent.getIntExtra(UCrop.Options.EXTRA_CROP_FRAME_COLOR, getResources().getColor(i)));
        this.mOverlayView.setCropFrameStrokeWidth(intent.getIntExtra(UCrop.Options.EXTRA_CROP_FRAME_STROKE_WIDTH, getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_frame_stoke_width)));
        this.mOverlayView.setShowCropGrid(intent.getBooleanExtra(UCrop.Options.EXTRA_SHOW_CROP_GRID, true));
        this.mOverlayView.setCropGridRowCount(intent.getIntExtra(UCrop.Options.EXTRA_CROP_GRID_ROW_COUNT, 2));
        this.mOverlayView.setCropGridColumnCount(intent.getIntExtra(UCrop.Options.EXTRA_CROP_GRID_COLUMN_COUNT, 2));
        this.mOverlayView.setCropGridColor(intent.getIntExtra(UCrop.Options.EXTRA_CROP_GRID_COLOR, getResources().getColor(R.color.ucrop_color_default_crop_grid)));
        this.mOverlayView.setCropGridStrokeWidth(intent.getIntExtra(UCrop.Options.EXTRA_CROP_GRID_STROKE_WIDTH, getResources().getDimensionPixelSize(R.dimen.ucrop_default_crop_grid_stoke_width)));
        float aspectRatioX = intent.getFloatExtra(UCrop.EXTRA_ASPECT_RATIO_X, 0.0f);
        float aspectRatioY = intent.getFloatExtra(UCrop.EXTRA_ASPECT_RATIO_Y, 0.0f);
        int aspectRationSelectedByDefault = intent.getIntExtra(UCrop.Options.EXTRA_ASPECT_RATIO_SELECTED_BY_DEFAULT, 0);
        ArrayList<AspectRatio> aspectRatioList = intent.getParcelableArrayListExtra(UCrop.Options.EXTRA_ASPECT_RATIO_OPTIONS);
        if (aspectRatioX > 0.0f && aspectRatioY > 0.0f) {
            ViewGroup viewGroup = this.mWrapperStateAspectRatio;
            if (viewGroup != null) {
                viewGroup.setVisibility(8);
            }
            this.mGestureCropImageView.setTargetAspectRatio(aspectRatioX / aspectRatioY);
        } else if (aspectRatioList == null || aspectRationSelectedByDefault >= aspectRatioList.size()) {
            this.mGestureCropImageView.setTargetAspectRatio(0.0f);
        } else {
            this.mGestureCropImageView.setTargetAspectRatio(aspectRatioList.get(aspectRationSelectedByDefault).getAspectRatioX() / aspectRatioList.get(aspectRationSelectedByDefault).getAspectRatioY());
        }
        int maxSizeX = intent.getIntExtra(UCrop.EXTRA_MAX_SIZE_X, 0);
        int maxSizeY = intent.getIntExtra(UCrop.EXTRA_MAX_SIZE_Y, 0);
        if (maxSizeX > 0 && maxSizeY > 0) {
            this.mGestureCropImageView.setMaxResultImageSizeX(maxSizeX);
            this.mGestureCropImageView.setMaxResultImageSizeY(maxSizeY);
        }
    }

    /* access modifiers changed from: protected */
    public void setupViews(@NonNull Intent intent) {
        this.mStatusBarColor = intent.getIntExtra(UCrop.Options.EXTRA_STATUS_BAR_COLOR, ContextCompat.getColor(this, R.color.ucrop_color_statusbar));
        this.mToolbarColor = intent.getIntExtra(UCrop.Options.EXTRA_TOOL_BAR_COLOR, ContextCompat.getColor(this, R.color.ucrop_color_toolbar));
        this.mActiveWidgetColor = intent.getIntExtra(UCrop.Options.EXTRA_UCROP_COLOR_WIDGET_ACTIVE, ContextCompat.getColor(this, R.color.ucrop_color_widget_background));
        this.mActiveControlsWidgetColor = intent.getIntExtra(UCrop.Options.EXTRA_UCROP_COLOR_CONTROLS_WIDGET_ACTIVE, ContextCompat.getColor(this, R.color.ucrop_color_active_controls_color));
        this.mToolbarWidgetColor = intent.getIntExtra(UCrop.Options.EXTRA_UCROP_WIDGET_COLOR_TOOLBAR, ContextCompat.getColor(this, R.color.ucrop_color_toolbar_widget));
        this.mToolbarCancelDrawable = intent.getIntExtra(UCrop.Options.EXTRA_UCROP_WIDGET_CANCEL_DRAWABLE, R.drawable.ucrop_ic_cross);
        this.mToolbarCropDrawable = intent.getIntExtra(UCrop.Options.EXTRA_UCROP_WIDGET_CROP_DRAWABLE, R.drawable.ucrop_ic_done);
        String stringExtra = intent.getStringExtra(UCrop.Options.EXTRA_UCROP_TITLE_TEXT_TOOLBAR);
        this.mToolbarTitle = stringExtra;
        if (stringExtra == null) {
            stringExtra = getResources().getString(R.string.ucrop_label_edit_photo);
        }
        this.mToolbarTitle = stringExtra;
        this.mLogoColor = intent.getIntExtra(UCrop.Options.EXTRA_UCROP_LOGO_COLOR, ContextCompat.getColor(this, R.color.ucrop_color_default_logo));
        this.mShowBottomControls = !intent.getBooleanExtra(UCrop.Options.EXTRA_HIDE_BOTTOM_CONTROLS, false);
        this.mRootViewBackgroundColor = intent.getIntExtra(UCrop.Options.EXTRA_UCROP_ROOT_VIEW_BACKGROUND_COLOR, ContextCompat.getColor(this, R.color.ucrop_color_crop_background));
        setupAppBar();
        initiateRootViews();
        if (this.mShowBottomControls) {
            ViewGroup wrapper = (ViewGroup) ((ViewGroup) findViewById(R.id.ucrop_photobox)).findViewById(R.id.controls_wrapper);
            wrapper.setVisibility(0);
            wrapper.setBackgroundColor(this.mRootViewBackgroundColor);
            LayoutInflater.from(this).inflate(R.layout.ucrop_controls, wrapper, true);
            AutoTransition autoTransition = new AutoTransition();
            this.mControlsTransition = autoTransition;
            autoTransition.setDuration(50);
            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.state_aspect_ratio);
            this.mWrapperStateAspectRatio = viewGroup;
            viewGroup.setOnClickListener(this.mStateClickListener);
            ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.state_rotate);
            this.mWrapperStateRotate = viewGroup2;
            viewGroup2.setOnClickListener(this.mStateClickListener);
            ViewGroup viewGroup3 = (ViewGroup) findViewById(R.id.state_scale);
            this.mWrapperStateScale = viewGroup3;
            viewGroup3.setOnClickListener(this.mStateClickListener);
            this.mLayoutAspectRatio = (ViewGroup) findViewById(R.id.layout_aspect_ratio);
            this.mLayoutRotate = (ViewGroup) findViewById(R.id.layout_rotate_wheel);
            this.mLayoutScale = (ViewGroup) findViewById(R.id.layout_scale_wheel);
            setupAspectRatioWidget(intent);
            setupRotateWidget();
            setupScaleWidget();
            setupStatesWrapper();
        }
    }

    private void setupAppBar() {
        setStatusBarColor(this.mStatusBarColor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(this.mToolbarColor);
        toolbar.setTitleTextColor(this.mToolbarWidgetColor);
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTextColor(this.mToolbarWidgetColor);
        toolbarTitle.setText(this.mToolbarTitle);
        Drawable stateButtonDrawable = AppCompatResources.getDrawable(this, this.mToolbarCancelDrawable).mutate();
        stateButtonDrawable.setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(this.mToolbarWidgetColor, BlendModeCompat.SRC_ATOP));
        toolbar.setNavigationIcon(stateButtonDrawable);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initiateRootViews() {
        this.uCropPhotoBox = (RelativeLayout) findViewById(R.id.ucrop_photobox);
        UCropView uCropView = (UCropView) findViewById(R.id.ucrop);
        this.mUCropView = uCropView;
        this.mGestureCropImageView = uCropView.getCropImageView();
        this.mOverlayView = this.mUCropView.getOverlayView();
        this.mGestureCropImageView.setTransformImageListener(this.mImageListener);
        ((ImageView) findViewById(R.id.image_view_logo)).setColorFilter(this.mLogoColor, PorterDuff.Mode.SRC_ATOP);
        findViewById(R.id.ucrop_frame).setBackgroundColor(this.mRootViewBackgroundColor);
    }

    private void setupStatesWrapper() {
        ImageView stateScaleImageView = (ImageView) findViewById(R.id.image_view_state_scale);
        ImageView stateRotateImageView = (ImageView) findViewById(R.id.image_view_state_rotate);
        ImageView stateAspectRatioImageView = (ImageView) findViewById(R.id.image_view_state_aspect_ratio);
        stateScaleImageView.setImageDrawable(new SelectedStateListDrawable(stateScaleImageView.getDrawable(), this.mActiveControlsWidgetColor));
        stateRotateImageView.setImageDrawable(new SelectedStateListDrawable(stateRotateImageView.getDrawable(), this.mActiveControlsWidgetColor));
        stateAspectRatioImageView.setImageDrawable(new SelectedStateListDrawable(stateAspectRatioImageView.getDrawable(), this.mActiveControlsWidgetColor));
    }

    private void setNavBar() {
        int navBarColor;
        if (Build.VERSION.SDK_INT >= 21 && (navBarColor = getIntent().getIntExtra(UCrop.Options.EXTRA_NAV_BAR_COLOR, 0)) != 0) {
            getWindow().setNavigationBarColor(navBarColor);
        }
    }

    @TargetApi(21)
    private void setStatusBarColor(@ColorInt int color) {
        Window window;
        if (Build.VERSION.SDK_INT >= 21 && (window = getWindow()) != null) {
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(color);
        }
    }

    private void setupAspectRatioWidget(@NonNull Intent intent) {
        int aspectRationSelectedByDefault = intent.getIntExtra(UCrop.Options.EXTRA_ASPECT_RATIO_SELECTED_BY_DEFAULT, 0);
        ArrayList<AspectRatio> aspectRatioList = intent.getParcelableArrayListExtra(UCrop.Options.EXTRA_ASPECT_RATIO_OPTIONS);
        if (aspectRatioList == null || aspectRatioList.isEmpty()) {
            aspectRationSelectedByDefault = 2;
            aspectRatioList = new ArrayList<>();
            aspectRatioList.add(new AspectRatio((String) null, 1.0f, 1.0f));
            aspectRatioList.add(new AspectRatio((String) null, 3.0f, 4.0f));
            aspectRatioList.add(new AspectRatio(getString(R.string.ucrop_label_original).toUpperCase(), 0.0f, 0.0f));
            aspectRatioList.add(new AspectRatio((String) null, 3.0f, 2.0f));
            aspectRatioList.add(new AspectRatio((String) null, 16.0f, 9.0f));
        }
        LinearLayout wrapperAspectRatioList = (LinearLayout) findViewById(R.id.layout_aspect_ratio);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, -1);
        lp.weight = 1.0f;
        if (getCurrentActivity() instanceof PictureMultiCuttingActivity) {
            this.mAspectRatioTextViews = new ArrayList();
            this.mCropAspectRatioViews = new ArrayList();
        }
        Iterator<AspectRatio> it = aspectRatioList.iterator();
        while (it.hasNext()) {
            FrameLayout wrapperAspectRatio = (FrameLayout) getLayoutInflater().inflate(R.layout.ucrop_aspect_ratio, (ViewGroup) null);
            wrapperAspectRatio.setLayoutParams(lp);
            AspectRatioTextView aspectRatioTextView = (AspectRatioTextView) wrapperAspectRatio.getChildAt(0);
            aspectRatioTextView.setActiveColor(this.mActiveControlsWidgetColor);
            aspectRatioTextView.setAspectRatio(it.next());
            this.mAspectRatioTextViews.add(aspectRatioTextView);
            wrapperAspectRatioList.addView(wrapperAspectRatio);
            this.mCropAspectRatioViews.add(wrapperAspectRatio);
        }
        this.mCropAspectRatioViews.get(aspectRationSelectedByDefault).setSelected(true);
        int index = -1;
        for (ViewGroup cropAspectRatioView : this.mCropAspectRatioViews) {
            index++;
            cropAspectRatioView.setTag(Integer.valueOf(index));
            cropAspectRatioView.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View v = view;
                    UCropActivity.this.mGestureCropImageView.setTargetAspectRatio(((AspectRatioTextView) ((ViewGroup) v).getChildAt(0)).getAspectRatio(v.isSelected()));
                    UCropActivity.this.mGestureCropImageView.setImageToWrapCropBounds();
                    if (!v.isSelected()) {
                        for (ViewGroup cropAspectRatioView : UCropActivity.this.mCropAspectRatioViews) {
                            cropAspectRatioView.setSelected(cropAspectRatioView == v);
                        }
                    }
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
    }

    private void setupRotateWidget() {
        this.mTextViewRotateAngle = (TextView) findViewById(R.id.text_view_rotate);
        int i = R.id.rotate_scroll_wheel;
        ((HorizontalProgressWheelView) findViewById(i)).setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() {
            public void onScroll(float delta, float totalDistance) {
                UCropActivity.this.mGestureCropImageView.postRotate(delta / 42.0f);
            }

            public void onScrollEnd() {
                UCropActivity.this.mGestureCropImageView.setImageToWrapCropBounds();
            }

            public void onScrollStart() {
                UCropActivity.this.mGestureCropImageView.cancelAllAnimations();
            }
        });
        ((HorizontalProgressWheelView) findViewById(i)).setMiddleLineColor(this.mActiveWidgetColor);
        findViewById(R.id.wrapper_reset_rotate).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                UCropActivity.this.resetRotation();
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        findViewById(R.id.wrapper_rotate_by_angle).setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                UCropActivity.this.rotateByAngle(90);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    private void setupScaleWidget() {
        this.mTextViewScalePercent = (TextView) findViewById(R.id.text_view_scale);
        int i = R.id.scale_scroll_wheel;
        ((HorizontalProgressWheelView) findViewById(i)).setScrollingListener(new HorizontalProgressWheelView.ScrollingListener() {
            public void onScroll(float delta, float totalDistance) {
                if (delta > 0.0f) {
                    UCropActivity.this.mGestureCropImageView.zoomInImage(UCropActivity.this.mGestureCropImageView.getCurrentScale() + (((UCropActivity.this.mGestureCropImageView.getMaxScale() - UCropActivity.this.mGestureCropImageView.getMinScale()) / 15000.0f) * delta));
                } else {
                    UCropActivity.this.mGestureCropImageView.zoomOutImage(UCropActivity.this.mGestureCropImageView.getCurrentScale() + (((UCropActivity.this.mGestureCropImageView.getMaxScale() - UCropActivity.this.mGestureCropImageView.getMinScale()) / 15000.0f) * delta));
                }
            }

            public void onScrollEnd() {
                UCropActivity.this.mGestureCropImageView.setImageToWrapCropBounds();
            }

            public void onScrollStart() {
                UCropActivity.this.mGestureCropImageView.cancelAllAnimations();
            }
        });
        ((HorizontalProgressWheelView) findViewById(i)).setMiddleLineColor(this.mActiveWidgetColor);
    }

    /* access modifiers changed from: private */
    public void setAngleText(float angle) {
        TextView textView = this.mTextViewRotateAngle;
        if (textView != null) {
            textView.setText(String.format(Locale.getDefault(), "%.1f°", new Object[]{Float.valueOf(angle)}));
        }
    }

    /* access modifiers changed from: private */
    public void setScaleText(float scale) {
        TextView textView = this.mTextViewScalePercent;
        if (textView != null) {
            textView.setText(String.format(Locale.getDefault(), "%d%%", new Object[]{Integer.valueOf((int) (100.0f * scale))}));
        }
    }

    /* access modifiers changed from: private */
    public void resetRotation() {
        GestureCropImageView gestureCropImageView = this.mGestureCropImageView;
        gestureCropImageView.postRotate(-gestureCropImageView.getCurrentAngle());
        this.mGestureCropImageView.setImageToWrapCropBounds();
    }

    /* access modifiers changed from: private */
    public void rotateByAngle(int angle) {
        this.mGestureCropImageView.postRotate((float) angle);
        this.mGestureCropImageView.setImageToWrapCropBounds();
    }

    /* access modifiers changed from: protected */
    public void setInitialState() {
        if (!this.mShowBottomControls) {
            setAllowedGestures(0);
        } else if (this.mWrapperStateAspectRatio.getVisibility() == 0) {
            setWidgetState(R.id.state_aspect_ratio);
        } else {
            setWidgetState(R.id.state_scale);
        }
    }

    /* access modifiers changed from: private */
    public void setWidgetState(@IdRes int stateViewId) {
        if (this.mShowBottomControls) {
            ViewGroup viewGroup = this.mWrapperStateAspectRatio;
            int i = R.id.state_aspect_ratio;
            viewGroup.setSelected(stateViewId == i);
            ViewGroup viewGroup2 = this.mWrapperStateRotate;
            int i2 = R.id.state_rotate;
            viewGroup2.setSelected(stateViewId == i2);
            ViewGroup viewGroup3 = this.mWrapperStateScale;
            int i3 = R.id.state_scale;
            viewGroup3.setSelected(stateViewId == i3);
            int i4 = 8;
            this.mLayoutAspectRatio.setVisibility(stateViewId == i ? 0 : 8);
            this.mLayoutRotate.setVisibility(stateViewId == i2 ? 0 : 8);
            ViewGroup viewGroup4 = this.mLayoutScale;
            if (stateViewId == i3) {
                i4 = 0;
            }
            viewGroup4.setVisibility(i4);
            changeSelectedTab(stateViewId);
            if (stateViewId == i3) {
                setAllowedGestures(0);
            } else if (stateViewId == i2) {
                setAllowedGestures(1);
            } else {
                setAllowedGestures(2);
            }
        }
    }

    private void changeSelectedTab(int stateViewId) {
        TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.ucrop_photobox), this.mControlsTransition);
        int i = 0;
        this.mWrapperStateScale.findViewById(R.id.text_view_scale).setVisibility(stateViewId == R.id.state_scale ? 0 : 8);
        this.mWrapperStateAspectRatio.findViewById(R.id.text_view_crop).setVisibility(stateViewId == R.id.state_aspect_ratio ? 0 : 8);
        View findViewById = this.mWrapperStateRotate.findViewById(R.id.text_view_rotate);
        if (stateViewId != R.id.state_rotate) {
            i = 8;
        }
        findViewById.setVisibility(i);
    }

    private void setAllowedGestures(int tab) {
        if (isOnTouch()) {
            GestureCropImageView gestureCropImageView = this.mGestureCropImageView;
            boolean z = this.isScaleEnabled;
            boolean z2 = false;
            if (z && this.mShowBottomControls) {
                int[] iArr = this.mAllowedGestures;
                z = iArr[tab] == 3 || iArr[tab] == 1;
            }
            gestureCropImageView.setScaleEnabled(z);
            GestureCropImageView gestureCropImageView2 = this.mGestureCropImageView;
            boolean z3 = this.isRotateEnabled;
            if (!z3 || !this.mShowBottomControls) {
                z2 = z3;
            } else {
                int[] iArr2 = this.mAllowedGestures;
                if (iArr2[tab] == 3 || iArr2[tab] == 2) {
                    z2 = true;
                }
            }
            gestureCropImageView2.setRotateEnabled(z2);
        }
    }

    /* access modifiers changed from: protected */
    public void addBlockingView() {
        if (this.mBlockingView == null) {
            this.mBlockingView = new View(this);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
            lp.addRule(3, R.id.toolbar);
            this.mBlockingView.setLayoutParams(lp);
            this.mBlockingView.setClickable(true);
        }
        ((RelativeLayout) findViewById(R.id.ucrop_photobox)).addView(this.mBlockingView);
    }

    /* access modifiers changed from: protected */
    public void cropAndSaveImage() {
        this.mBlockingView.setClickable(true);
        this.mShowLoader = true;
        supportInvalidateOptionsMenu();
        this.mGestureCropImageView.cropAndSaveImage(this.mCompressFormat, this.mCompressQuality, new BitmapCropCallback() {
            public void onBitmapCropped(@NonNull Uri resultUri, int offsetX, int offsetY, int imageWidth, int imageHeight) {
                UCropActivity uCropActivity = UCropActivity.this;
                uCropActivity.setResultUri(resultUri, uCropActivity.mGestureCropImageView.getTargetAspectRatio(), offsetX, offsetY, imageWidth, imageHeight);
                if (!(UCropActivity.this.getCurrentActivity() instanceof PictureMultiCuttingActivity)) {
                    UCropActivity.this.onBackPressed();
                }
            }

            public void onCropFailure(@NonNull Throwable t) {
                UCropActivity.this.setResultError(t);
                UCropActivity.this.onBackPressed();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void setResultUri(Uri uri, float resultAspectRatio, int offsetX, int offsetY, int imageWidth, int imageHeight) {
        setResult(-1, new Intent().putExtra(UCrop.EXTRA_OUTPUT_URI, uri).putExtra(UCrop.EXTRA_OUTPUT_CROP_ASPECT_RATIO, resultAspectRatio).putExtra(UCrop.EXTRA_OUTPUT_IMAGE_WIDTH, imageWidth).putExtra(UCrop.EXTRA_OUTPUT_IMAGE_HEIGHT, imageHeight).putExtra(UCrop.EXTRA_OUTPUT_OFFSET_X, offsetX).putExtra(UCrop.EXTRA_OUTPUT_OFFSET_Y, offsetY));
    }

    /* access modifiers changed from: protected */
    public void setResultError(Throwable throwable) {
        setResult(96, new Intent().putExtra(UCrop.EXTRA_ERROR, throwable));
    }

    /* access modifiers changed from: protected */
    public Activity getCurrentActivity() {
        return this;
    }

    public void onBackPressed() {
        closeActivity();
    }

    /* access modifiers changed from: protected */
    public void closeActivity() {
        finish();
        exitAnimation();
    }

    /* access modifiers changed from: protected */
    public void exitAnimation() {
        int exitAnimation = getIntent().getIntExtra(UCrop.Options.EXTRA_WINDOW_EXIT_ANIMATION, 0);
        overridePendingTransition(R.anim.ucrop_anim_fade_in, exitAnimation != 0 ? exitAnimation : R.anim.ucrop_close);
    }
}
