package com.didichuxing.doraemonkit.kit.filemanager.action.file;

import io.ktor.http.content.g;
import kotlin.coroutines.jvm.internal.d;
import kotlin.coroutines.jvm.internal.f;
import kotlin.l;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@l(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0003\u0010\u0007\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0001\u001a\u00020\u00002\u0018\u0010\u0006\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00030\u0002H@¢\u0006\u0004\b\u0007\u0010\b"}, d2 = {"Lio/ktor/http/content/g;", "multipart", "Lkotlin/coroutines/d;", "", "", "", "continuation", "uploadFileRes", "(Lio/ktor/http/content/g;Lkotlin/coroutines/d;)Ljava/lang/Object;"}, k = 3, mv = {1, 4, 0})
@f(c = "com.didichuxing.doraemonkit.kit.filemanager.action.file.UploadFileAction", f = "UploadFileAction.kt", l = {28}, m = "uploadFileRes")
/* compiled from: UploadFileAction.kt */
public final class UploadFileAction$uploadFileRes$1 extends d {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UploadFileAction this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    UploadFileAction$uploadFileRes$1(UploadFileAction uploadFileAction, kotlin.coroutines.d dVar) {
        super(dVar);
        this.this$0 = uploadFileAction;
    }

    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.uploadFileRes((g) null, this);
    }
}
