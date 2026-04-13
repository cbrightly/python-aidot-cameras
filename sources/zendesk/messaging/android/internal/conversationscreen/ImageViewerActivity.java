package zendesk.messaging.android.internal.conversationscreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwnerKt;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.g;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.x;
import kotlinx.coroutines.j;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.d;
import zendesk.messaging.R$color;
import zendesk.ui.android.conversation.imagerviewer.ImageViewerView;

/* compiled from: ImageViewerActivity.kt */
public final class ImageViewerActivity extends AppCompatActivity implements zendesk.android.messaging.d {
    @NotNull
    public static final a c = new a((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public m d;
    @Nullable
    private z1 f;

    /* access modifiers changed from: protected */
    @SuppressLint({"UseCompatLoadingForDrawables"})
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageViewerView $this$onCreate_u24lambda_u2d0 = new ImageViewerView(this, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
        $this$onCreate_u24lambda_u2d0.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        $this$onCreate_u24lambda_u2d0.setBackground(getDrawable(R$color.zuia_color_black));
        ImageViewerView imageViewerView = $this$onCreate_u24lambda_u2d0;
        b bVar = new b(this);
        Intent intent = getIntent();
        k.d(intent, "intent");
        this.d = new m(this, bVar, l.f(intent), Integer.valueOf(ContextCompat.getColor(this, R$color.zuia_color_black_38p)), new c(this), imageViewerView, LifecycleOwnerKt.getLifecycleScope(this));
        setContentView((View) imageViewerView);
    }

    /* compiled from: ImageViewerActivity.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<zendesk.android.d> {
        final /* synthetic */ ImageViewerActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(ImageViewerActivity imageViewerActivity) {
            super(0);
            this.this$0 = imageViewerActivity;
        }

        @Nullable
        public final zendesk.android.d invoke() {
            d.b bVar = zendesk.android.d.a;
            Intent intent = this.this$0.getIntent();
            k.d(intent, "intent");
            return bVar.b(l.e(intent));
        }
    }

    /* compiled from: ImageViewerActivity.kt */
    public static final class c extends l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ ImageViewerActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(ImageViewerActivity imageViewerActivity) {
            super(0);
            this.this$0 = imageViewerActivity;
        }

        public final void invoke() {
            this.this$0.onBackPressed();
        }
    }

    @f(c = "zendesk.messaging.android.internal.conversationscreen.ImageViewerActivity$onStart$1", f = "ImageViewerActivity.kt", l = {73}, m = "invokeSuspend")
    /* compiled from: ImageViewerActivity.kt */
    public static final class d extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ ImageViewerActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(ImageViewerActivity imageViewerActivity, kotlin.coroutines.d<? super d> dVar) {
            super(2, dVar);
            this.this$0 = imageViewerActivity;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new d(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((d) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    m M = this.this$0.d;
                    if (M == null) {
                        k.t("imageViewerScreenCoordinator");
                        M = null;
                    }
                    a aVar = new a(this.this$0);
                    this.label = 1;
                    if (M.e(aVar, this) != d) {
                        break;
                    } else {
                        return d;
                    }
                case 1:
                    kotlin.p.b($result);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return x.a;
        }

        /* compiled from: ImageViewerActivity.kt */
        public static final class a extends l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ ImageViewerActivity this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(ImageViewerActivity imageViewerActivity) {
                super(0);
                this.this$0 = imageViewerActivity;
            }

            public final void invoke() {
                zendesk.logger.a.d("ImageViewerActivity", "Unable to show the image viewer screen without a Messaging instance.", new Object[0]);
                this.this$0.finish();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.f = j.d(LifecycleOwnerKt.getLifecycleScope(this), (g) null, (q0) null, new d(this, (kotlin.coroutines.d<? super d>) null), 3, (Object) null);
        zendesk.messaging.android.internal.j.a.c(this);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        z1 z1Var = this.f;
        if (z1Var != null) {
            z1.a.a(z1Var, (CancellationException) null, 1, (Object) null);
        }
        zendesk.messaging.android.internal.j.a.a(this);
    }

    /* compiled from: ImageViewerActivity.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
