package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import io.ktor.http.content.k;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.coroutines.jvm.internal.f;
import kotlin.jvm.functions.p;
import kotlin.jvm.internal.z;
import kotlin.l;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000H@¢\u0006\u0004\b\u0003\u0010\u0004"}, d2 = {"Lio/ktor/http/content/k;", "part", "Lkotlin/x;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "<anonymous>"}, k = 3, mv = {1, 4, 0})
@f(c = "com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction$uploadFileRes$2", f = "UploadFileAction.kt", l = {}, m = "invokeSuspend")
/* compiled from: UploadFileAction.kt */
public final class UploadFileAction$uploadFileRes$2 extends kotlin.coroutines.jvm.internal.l implements p<k, d<? super x>, Object> {
    final /* synthetic */ z $filePart;
    final /* synthetic */ z $formPart;
    int label;
    private k p$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    UploadFileAction$uploadFileRes$2(z zVar, z zVar2, d dVar) {
        super(2, dVar);
        this.$filePart = zVar;
        this.$formPart = zVar2;
    }

    @NotNull
    public final d<x> create(@Nullable Object obj, @NotNull d<?> dVar) {
        kotlin.jvm.internal.k.f(dVar, "completion");
        UploadFileAction$uploadFileRes$2 uploadFileAction$uploadFileRes$2 = new UploadFileAction$uploadFileRes$2(this.$filePart, this.$formPart, dVar);
        k kVar = (k) obj;
        uploadFileAction$uploadFileRes$2.p$0 = (k) obj;
        return uploadFileAction$uploadFileRes$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((UploadFileAction$uploadFileRes$2) create(obj, (d) obj2)).invokeSuspend(x.a);
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object $result) {
        c.d();
        switch (this.label) {
            case 0:
                kotlin.p.b($result);
                T t = this.p$0;
                if (t instanceof k.a) {
                    this.$filePart.element = (k.a) t;
                } else if (t instanceof k.b) {
                    this.$formPart.element = (k.b) t;
                }
                return x.a;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
