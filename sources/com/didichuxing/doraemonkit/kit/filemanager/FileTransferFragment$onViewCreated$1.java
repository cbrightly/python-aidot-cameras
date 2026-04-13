package com.didichuxing.doraemonkit.kit.filemanager;

import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import kotlin.l;

@l(d1 = {"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0003\u001a\u00020\u0000H\n¢\u0006\u0004\b\u0001\u0010\u0002"}, d2 = {"Lkotlin/x;", "onRightClick", "()V", "<anonymous>"}, k = 3, mv = {1, 4, 0})
/* compiled from: FileTransferFragment.kt */
public final class FileTransferFragment$onViewCreated$1 implements HomeTitleBar.OnTitleBarClickListener {
    final /* synthetic */ FileTransferFragment this$0;

    FileTransferFragment$onViewCreated$1(FileTransferFragment fileTransferFragment) {
        this.this$0 = fileTransferFragment;
    }

    public final void onRightClick() {
        this.this$0.finish();
    }
}
