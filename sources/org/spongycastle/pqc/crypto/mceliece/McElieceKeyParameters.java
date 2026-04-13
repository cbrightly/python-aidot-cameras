package org.spongycastle.pqc.crypto.mceliece;

import org.spongycastle.crypto.params.AsymmetricKeyParameter;

public class McElieceKeyParameters extends AsymmetricKeyParameter {
    private McElieceParameters d;

    public McElieceKeyParameters(boolean isPrivate, McElieceParameters params) {
        super(isPrivate);
        this.d = params;
    }
}
