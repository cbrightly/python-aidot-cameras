package com.airbnb.lottie.parser;

import androidx.annotation.Nullable;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.model.animatable.b;
import com.airbnb.lottie.parser.moshi.a;

/* compiled from: DropShadowEffectParser */
public class k {
    private static final a.C0011a a = a.C0011a.a("ef");
    private static final a.C0011a b = a.C0011a.a("nm", "v");
    private com.airbnb.lottie.model.animatable.a c;
    private b d;
    private b e;
    private b f;
    private b g;

    /* access modifiers changed from: package-private */
    @Nullable
    public j b(a reader, c0 composition) {
        b bVar;
        b bVar2;
        b bVar3;
        b bVar4;
        while (reader.l()) {
            switch (reader.x(a)) {
                case 0:
                    reader.c();
                    while (reader.l()) {
                        a(reader, composition);
                    }
                    reader.i();
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        com.airbnb.lottie.model.animatable.a aVar = this.c;
        if (aVar == null || (bVar = this.d) == null || (bVar2 = this.e) == null || (bVar3 = this.f) == null || (bVar4 = this.g) == null) {
            return null;
        }
        return new j(aVar, bVar, bVar2, bVar3, bVar4);
    }

    private void a(a reader, c0 composition) {
        String currentEffectName = "";
        reader.g();
        while (reader.l()) {
            switch (reader.x(b)) {
                case 0:
                    currentEffectName = reader.s();
                    break;
                case 1:
                    char c2 = 65535;
                    switch (currentEffectName.hashCode()) {
                        case 353103893:
                            if (currentEffectName.equals("Distance")) {
                                c2 = 3;
                                break;
                            }
                            break;
                        case 397447147:
                            if (currentEffectName.equals("Opacity")) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 1041377119:
                            if (currentEffectName.equals("Direction")) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 1379387491:
                            if (currentEffectName.equals("Shadow Color")) {
                                c2 = 0;
                                break;
                            }
                            break;
                        case 1383710113:
                            if (currentEffectName.equals("Softness")) {
                                c2 = 4;
                                break;
                            }
                            break;
                    }
                    switch (c2) {
                        case 0:
                            this.c = d.c(reader, composition);
                            break;
                        case 1:
                            this.d = d.f(reader, composition, false);
                            break;
                        case 2:
                            this.e = d.f(reader, composition, false);
                            break;
                        case 3:
                            this.f = d.e(reader, composition);
                            break;
                        case 4:
                            this.g = d.e(reader, composition);
                            break;
                        default:
                            reader.E();
                            break;
                    }
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
    }
}
