package androidx.camera.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

@ExperimentalUseCaseGroup
public final class UseCaseGroup {
    @NonNull
    private final List<UseCase> mUseCases;
    @Nullable
    private final ViewPort mViewPort;

    UseCaseGroup(@Nullable ViewPort viewPort, @NonNull List<UseCase> useCases) {
        this.mViewPort = viewPort;
        this.mUseCases = useCases;
    }

    @Nullable
    public ViewPort getViewPort() {
        return this.mViewPort;
    }

    @NonNull
    public List<UseCase> getUseCases() {
        return this.mUseCases;
    }

    @ExperimentalUseCaseGroup
    public static final class Builder {
        private final List<UseCase> mUseCases = new ArrayList();
        private ViewPort mViewPort;

        @NonNull
        public Builder setViewPort(@NonNull ViewPort viewPort) {
            this.mViewPort = viewPort;
            return this;
        }

        @NonNull
        public Builder addUseCase(@NonNull UseCase useCase) {
            this.mUseCases.add(useCase);
            return this;
        }

        @NonNull
        public UseCaseGroup build() {
            Preconditions.checkArgument(!this.mUseCases.isEmpty(), "UseCase must not be empty.");
            return new UseCaseGroup(this.mViewPort, this.mUseCases);
        }
    }
}
