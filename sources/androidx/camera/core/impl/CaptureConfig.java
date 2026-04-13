package androidx.camera.core.impl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.impl.Config;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class CaptureConfig {
    public static final Config.Option<Integer> OPTION_JPEG_QUALITY = Config.Option.create("camerax.core.captureConfig.jpegQuality", Integer.class);
    public static final Config.Option<Integer> OPTION_ROTATION = Config.Option.create("camerax.core.captureConfig.rotation", Integer.TYPE);
    final List<CameraCaptureCallback> mCameraCaptureCallbacks;
    final Config mImplementationOptions;
    final List<DeferrableSurface> mSurfaces;
    @NonNull
    private final TagBundle mTagBundle;
    final int mTemplateType;
    private final boolean mUseRepeatingSurface;

    public interface OptionUnpacker {
        void unpack(@NonNull UseCaseConfig<?> useCaseConfig, @NonNull Builder builder);
    }

    CaptureConfig(List<DeferrableSurface> surfaces, Config implementationOptions, int templateType, List<CameraCaptureCallback> cameraCaptureCallbacks, boolean useRepeatingSurface, @NonNull TagBundle tagBundle) {
        this.mSurfaces = surfaces;
        this.mImplementationOptions = implementationOptions;
        this.mTemplateType = templateType;
        this.mCameraCaptureCallbacks = Collections.unmodifiableList(cameraCaptureCallbacks);
        this.mUseRepeatingSurface = useRepeatingSurface;
        this.mTagBundle = tagBundle;
    }

    @NonNull
    public static CaptureConfig defaultEmptyCaptureConfig() {
        return new Builder().build();
    }

    @NonNull
    public List<DeferrableSurface> getSurfaces() {
        return Collections.unmodifiableList(this.mSurfaces);
    }

    @NonNull
    public Config getImplementationOptions() {
        return this.mImplementationOptions;
    }

    public int getTemplateType() {
        return this.mTemplateType;
    }

    public boolean isUseRepeatingSurface() {
        return this.mUseRepeatingSurface;
    }

    @NonNull
    public List<CameraCaptureCallback> getCameraCaptureCallbacks() {
        return this.mCameraCaptureCallbacks;
    }

    @NonNull
    public TagBundle getTagBundle() {
        return this.mTagBundle;
    }

    public static final class Builder {
        private List<CameraCaptureCallback> mCameraCaptureCallbacks;
        private MutableConfig mImplementationOptions;
        private MutableTagBundle mMutableTagBundle;
        private final Set<DeferrableSurface> mSurfaces;
        private int mTemplateType;
        private boolean mUseRepeatingSurface;

        public Builder() {
            this.mSurfaces = new HashSet();
            this.mImplementationOptions = MutableOptionsBundle.create();
            this.mTemplateType = -1;
            this.mCameraCaptureCallbacks = new ArrayList();
            this.mUseRepeatingSurface = false;
            this.mMutableTagBundle = MutableTagBundle.create();
        }

        private Builder(CaptureConfig base) {
            HashSet hashSet = new HashSet();
            this.mSurfaces = hashSet;
            this.mImplementationOptions = MutableOptionsBundle.create();
            this.mTemplateType = -1;
            this.mCameraCaptureCallbacks = new ArrayList();
            this.mUseRepeatingSurface = false;
            this.mMutableTagBundle = MutableTagBundle.create();
            hashSet.addAll(base.mSurfaces);
            this.mImplementationOptions = MutableOptionsBundle.from(base.mImplementationOptions);
            this.mTemplateType = base.mTemplateType;
            this.mCameraCaptureCallbacks.addAll(base.getCameraCaptureCallbacks());
            this.mUseRepeatingSurface = base.isUseRepeatingSurface();
            this.mMutableTagBundle = MutableTagBundle.from(base.getTagBundle());
        }

        @NonNull
        public static Builder createFrom(@NonNull UseCaseConfig<?> config) {
            OptionUnpacker unpacker = config.getCaptureOptionUnpacker((OptionUnpacker) null);
            if (unpacker != null) {
                Builder builder = new Builder();
                unpacker.unpack(config, builder);
                return builder;
            }
            throw new IllegalStateException("Implementation is missing option unpacker for " + config.getTargetName(config.toString()));
        }

        @NonNull
        public static Builder from(@NonNull CaptureConfig base) {
            return new Builder(base);
        }

        public int getTemplateType() {
            return this.mTemplateType;
        }

        public void setTemplateType(int templateType) {
            this.mTemplateType = templateType;
        }

        public void addCameraCaptureCallback(@NonNull CameraCaptureCallback cameraCaptureCallback) {
            if (!this.mCameraCaptureCallbacks.contains(cameraCaptureCallback)) {
                this.mCameraCaptureCallbacks.add(cameraCaptureCallback);
                return;
            }
            throw new IllegalArgumentException("duplicate camera capture callback");
        }

        public void addAllCameraCaptureCallbacks(@NonNull Collection<CameraCaptureCallback> cameraCaptureCallbacks) {
            for (CameraCaptureCallback c : cameraCaptureCallbacks) {
                addCameraCaptureCallback(c);
            }
        }

        public void addSurface(@NonNull DeferrableSurface surface) {
            this.mSurfaces.add(surface);
        }

        public void removeSurface(@NonNull DeferrableSurface surface) {
            this.mSurfaces.remove(surface);
        }

        public void clearSurfaces() {
            this.mSurfaces.clear();
        }

        @NonNull
        public Set<DeferrableSurface> getSurfaces() {
            return this.mSurfaces;
        }

        public void setImplementationOptions(@NonNull Config config) {
            this.mImplementationOptions = MutableOptionsBundle.from(config);
        }

        public void addImplementationOptions(@NonNull Config config) {
            for (Config.Option<?> option : config.listOptions()) {
                Config.Option<?> option2 = option;
                Object existValue = this.mImplementationOptions.retrieveOption(option2, null);
                Object newValue = config.retrieveOption(option2);
                if (existValue instanceof MultiValueSet) {
                    ((MultiValueSet) existValue).addAll(((MultiValueSet) newValue).getAllItems());
                } else {
                    if (newValue instanceof MultiValueSet) {
                        newValue = ((MultiValueSet) newValue).clone();
                    }
                    this.mImplementationOptions.insertOption(option2, config.getOptionPriority(option), newValue);
                }
            }
        }

        public <T> void addImplementationOption(@NonNull Config.Option<T> option, @NonNull T value) {
            this.mImplementationOptions.insertOption(option, value);
        }

        @NonNull
        public Config getImplementationOptions() {
            return this.mImplementationOptions;
        }

        /* access modifiers changed from: package-private */
        public boolean isUseRepeatingSurface() {
            return this.mUseRepeatingSurface;
        }

        public void setUseRepeatingSurface(boolean useRepeatingSurface) {
            this.mUseRepeatingSurface = useRepeatingSurface;
        }

        @Nullable
        public Integer getTag(@NonNull String key) {
            return this.mMutableTagBundle.getTag(key);
        }

        public void addTag(@NonNull String key, @NonNull Integer tag) {
            this.mMutableTagBundle.putTag(key, tag);
        }

        public void addAllTags(@NonNull TagBundle bundle) {
            this.mMutableTagBundle.addTagBundle(bundle);
        }

        @NonNull
        public CaptureConfig build() {
            return new CaptureConfig(new ArrayList(this.mSurfaces), OptionsBundle.from(this.mImplementationOptions), this.mTemplateType, this.mCameraCaptureCallbacks, this.mUseRepeatingSurface, TagBundle.from(this.mMutableTagBundle));
        }
    }
}
