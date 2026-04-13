package zendesk.messaging.android.internal.conversationscreen;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelLazy;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.UninitializedPropertyAccessException;
import kotlin.collections.r;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.a0;
import kotlin.jvm.internal.k;
import kotlin.x;
import kotlinx.coroutines.o0;
import kotlinx.coroutines.q0;
import kotlinx.coroutines.z1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.android.d;
import zendesk.messaging.R$layout;
import zendesk.messaging.android.internal.conversationscreen.attachments.Attachment;
import zendesk.messaging.android.internal.conversationscreen.attachments.AttachmentActivity;
import zendesk.messaging.android.internal.m;
import zendesk.ui.android.R$id;
import zendesk.ui.android.conversation.form.DisplayedField;

/* compiled from: ConversationActivity.kt */
public final class ConversationActivity extends AppCompatActivity implements zendesk.android.messaging.d {
    @NotNull
    private static final a c = new a((DefaultConstructorMarker) null);
    @Nullable
    private z1 a1;
    @NotNull
    private final kotlin.g d = new ViewModelLazy(a0.b(ConversationViewModel.class), new i(this), new h(this));
    @NotNull
    private final l<DisplayedField, x> f = new f(this);
    /* access modifiers changed from: private */
    public f p0;
    @NotNull
    private final kotlin.jvm.functions.a<x> q = new d(this);
    @NotNull
    private final l<Integer, x> x = new c(this);
    @NotNull
    private final m y = new a(this);
    @NotNull
    private final kotlin.g z = kotlin.i.b(new b(this));

    public ConversationActivity() {
    }

    /* compiled from: ActivityViewModelLazy.kt */
    public static final class h extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<ViewModelProvider.Factory> {
        final /* synthetic */ ComponentActivity $this_viewModels;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public h(ComponentActivity componentActivity) {
            super(0);
            this.$this_viewModels = componentActivity;
        }

        @NotNull
        public final ViewModelProvider.Factory invoke() {
            ViewModelProvider.Factory defaultViewModelProviderFactory = this.$this_viewModels.getDefaultViewModelProviderFactory();
            k.d(defaultViewModelProviderFactory, "defaultViewModelProviderFactory");
            return defaultViewModelProviderFactory;
        }
    }

    /* access modifiers changed from: private */
    public final ConversationViewModel R() {
        return (ConversationViewModel) this.d.getValue();
    }

    /* compiled from: ActivityViewModelLazy.kt */
    public static final class i extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<ViewModelStore> {
        final /* synthetic */ ComponentActivity $this_viewModels;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public i(ComponentActivity componentActivity) {
            super(0);
            this.$this_viewModels = componentActivity;
        }

        @NotNull
        public final ViewModelStore invoke() {
            ViewModelStore viewModelStore = this.$this_viewModels.getViewModelStore();
            k.d(viewModelStore, "viewModelStore");
            return viewModelStore;
        }
    }

    /* compiled from: ConversationActivity.kt */
    public static final class f extends kotlin.jvm.internal.l implements l<DisplayedField, x> {
        final /* synthetic */ ConversationActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        f(ConversationActivity conversationActivity) {
            super(1);
            this.this$0 = conversationActivity;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke((DisplayedField) p1);
            return x.a;
        }

        public final void invoke(@NotNull DisplayedField displayedField) {
            k.e(displayedField, "displayedField");
            this.this$0.R().b(displayedField);
        }
    }

    /* compiled from: ConversationActivity.kt */
    public static final class d extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ ConversationActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        d(ConversationActivity conversationActivity) {
            super(0);
            this.this$0 = conversationActivity;
        }

        public final void invoke() {
            zendesk.messaging.android.internal.extension.a.a(this.this$0);
            this.this$0.finish();
        }
    }

    /* compiled from: ConversationActivity.kt */
    public static final class c extends kotlin.jvm.internal.l implements l<Integer, x> {
        final /* synthetic */ ConversationActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        c(ConversationActivity conversationActivity) {
            super(1);
            this.this$0 = conversationActivity;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object p1) {
            invoke(((Number) p1).intValue());
            return x.a;
        }

        public final void invoke(int menuId) {
            if (menuId == R$id.menu_item_camera) {
                AttachmentActivity.c.c(this.this$0, 41);
            } else if (menuId == R$id.menu_item_gallery) {
                AttachmentActivity.c.b(this.this$0, 41);
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void T(ConversationActivity this$0, String uri, zendesk.android.messaging.e source) {
        k.e(this$0, "this$0");
        k.e(uri, "uri");
        k.e(source, "source");
        try {
            f fVar = this$0.p0;
            if (fVar == null) {
                k.t("conversationScreenCoordinator");
                fVar = null;
            }
            fVar.u(uri, source, new j(source, this$0, uri));
        } catch (UninitializedPropertyAccessException uninitializedException) {
            zendesk.logger.a.c("MessagingConversationActivity", k.l("conversationScreenCoordinator was not initialized, unable to handle ", uri), uninitializedException, new Object[0]);
        } catch (ActivityNotFoundException activityNotFoundException) {
            zendesk.logger.a.c("MessagingConversationActivity", k.l("Failed to launch the ACTION_VIEW intent for : ", uri), activityNotFoundException, new Object[0]);
        }
    }

    /* compiled from: ConversationActivity.kt */
    public static final class j extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
        final /* synthetic */ zendesk.android.messaging.e $source;
        final /* synthetic */ String $uri;
        final /* synthetic */ ConversationActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        j(zendesk.android.messaging.e eVar, ConversationActivity conversationActivity, String str) {
            super(0);
            this.$source = eVar;
            this.this$0 = conversationActivity;
            this.$uri = str;
        }

        public final void invoke() {
            if (this.$source == zendesk.android.messaging.e.IMAGE) {
                ConversationActivity conversationActivity = this.this$0;
                Intent intent = conversationActivity.getIntent();
                k.d(intent, "intent");
                this.this$0.startActivity(new k(conversationActivity, d.c(intent)).b(this.$uri).a());
                return;
            }
            this.this$0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.$uri)));
        }
    }

    /* compiled from: ConversationActivity.kt */
    public static final class b extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.messaging.android.internal.a> {
        final /* synthetic */ ConversationActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(ConversationActivity conversationActivity) {
            super(0);
            this.this$0 = conversationActivity;
        }

        @NotNull
        public final zendesk.messaging.android.internal.a invoke() {
            return new zendesk.messaging.android.internal.a(this.this$0);
        }
    }

    private final zendesk.messaging.android.internal.a O() {
        return (zendesk.messaging.android.internal.a) this.z.getValue();
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R$layout.zma_screen_conversation);
        e eVar = new e(this);
        View findViewById = findViewById(zendesk.messaging.R$id.zma_conversation_screen_conversation);
        k.d(findViewById, "findViewById(R.id.zma_co…tion_screen_conversation)");
        this.p0 = new f(eVar, (zendesk.ui.android.a) findViewById, this.q, this.x, this.y, O(), LifecycleOwnerKt.getLifecycleScope(this), this.f, R().a(), new j());
    }

    /* compiled from: ConversationActivity.kt */
    public static final class e extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<zendesk.android.d> {
        final /* synthetic */ ConversationActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        e(ConversationActivity conversationActivity) {
            super(0);
            this.this$0 = conversationActivity;
        }

        @Nullable
        public final zendesk.android.d invoke() {
            d.b bVar = zendesk.android.d.a;
            Intent intent = this.this$0.getIntent();
            k.d(intent, "intent");
            return bVar.b(d.c(intent));
        }
    }

    @kotlin.coroutines.jvm.internal.f(c = "zendesk.messaging.android.internal.conversationscreen.ConversationActivity$onStart$1", f = "ConversationActivity.kt", l = {131}, m = "invokeSuspend")
    /* compiled from: ConversationActivity.kt */
    public static final class g extends kotlin.coroutines.jvm.internal.l implements p<o0, kotlin.coroutines.d<? super x>, Object> {
        int label;
        final /* synthetic */ ConversationActivity this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        g(ConversationActivity conversationActivity, kotlin.coroutines.d<? super g> dVar) {
            super(2, dVar);
            this.this$0 = conversationActivity;
        }

        @NotNull
        public final kotlin.coroutines.d<x> create(@Nullable Object obj, @NotNull kotlin.coroutines.d<?> dVar) {
            return new g(this.this$0, dVar);
        }

        @Nullable
        public final Object invoke(@NotNull o0 o0Var, @Nullable kotlin.coroutines.d<? super x> dVar) {
            return ((g) create(o0Var, dVar)).invokeSuspend(x.a);
        }

        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            Object d = kotlin.coroutines.intrinsics.c.d();
            switch (this.label) {
                case 0:
                    kotlin.p.b($result);
                    f M = this.this$0.p0;
                    if (M == null) {
                        k.t("conversationScreenCoordinator");
                        M = null;
                    }
                    ConversationActivity conversationActivity = this.this$0;
                    a aVar = new a(conversationActivity);
                    this.label = 1;
                    if (M.v(conversationActivity, aVar, this) != d) {
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

        /* compiled from: ConversationActivity.kt */
        public static final class a extends kotlin.jvm.internal.l implements kotlin.jvm.functions.a<x> {
            final /* synthetic */ ConversationActivity this$0;

            /* JADX INFO: super call moved to the top of the method (can break code semantics) */
            a(ConversationActivity conversationActivity) {
                super(0);
                this.this$0 = conversationActivity;
            }

            public final void invoke() {
                zendesk.logger.a.d("MessagingConversationActivity", "Unable to show the conversation screen without a Messaging instance.", new Object[0]);
                this.this$0.finish();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        this.a1 = kotlinx.coroutines.j.d(LifecycleOwnerKt.getLifecycleScope(this), (kotlin.coroutines.g) null, (q0) null, new g(this, (kotlin.coroutines.d<? super g>) null), 3, (Object) null);
        zendesk.messaging.android.internal.j.a.c(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 41) {
            int i2 = resultCode;
        } else if (resultCode == -1) {
            List attachmentFiles = AttachmentActivity.c.a(data);
            if (!attachmentFiles.isEmpty()) {
                List<Attachment> $this$mapTo$iv$iv = attachmentFiles;
                List arrayList = new ArrayList(r.r($this$mapTo$iv$iv, 10));
                for (Attachment it : $this$mapTo$iv$iv) {
                    List attachmentFiles2 = attachmentFiles;
                    zendesk.messaging.android.internal.model.h hVar = r13;
                    zendesk.messaging.android.internal.model.h hVar2 = new zendesk.messaging.android.internal.model.h(it.d(), it.b(), it.c(), it.a());
                    arrayList.add(hVar);
                    attachmentFiles = attachmentFiles2;
                }
                List uploadFiles = arrayList;
                f fVar = this.p0;
                if (fVar == null) {
                    k.t("conversationScreenCoordinator");
                    fVar = null;
                }
                fVar.y(uploadFiles);
                return;
            }
            List list = attachmentFiles;
            return;
        }
        Intent intent = data;
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        f fVar = this.p0;
        if (fVar == null) {
            k.t("conversationScreenCoordinator");
            fVar = null;
        }
        fVar.t();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (!isChangingConfigurations()) {
            f fVar = this.p0;
            if (fVar == null) {
                k.t("conversationScreenCoordinator");
                fVar = null;
            }
            fVar.s();
        }
        z1 z1Var = this.a1;
        if (z1Var != null) {
            z1.a.a(z1Var, (CancellationException) null, 1, (Object) null);
        }
        zendesk.messaging.android.internal.j.a.a(this);
    }

    /* compiled from: ConversationActivity.kt */
    public static final class a {
        public /* synthetic */ a(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private a() {
        }
    }
}
