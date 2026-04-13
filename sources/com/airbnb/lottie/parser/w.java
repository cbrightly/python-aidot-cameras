package com.airbnb.lottie.parser;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.f0;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.c;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.model.layer.e;
import com.airbnb.lottie.parser.moshi.a;
import com.airbnb.lottie.utils.d;
import com.airbnb.lottie.utils.h;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/* compiled from: LottieCompositionMoshiParser */
public class w {
    private static final a.C0011a a = a.C0011a.a("w", "h", IjkMediaPlayer.OnNativeInvokeListener.ARG_IP, "op", "fr", "v", "layers", "assets", "fonts", "chars", "markers");
    static a.C0011a b = a.C0011a.a("id", "layers", "w", "h", "p", "u");
    private static final a.C0011a c = a.C0011a.a("list");
    private static final a.C0011a d = a.C0011a.a("cm", "tm", "dr");

    public static c0 a(a reader) {
        SparseArrayCompat<FontCharacter> characters;
        List<Marker> markers;
        a aVar = reader;
        float scale = h.e();
        float startFrame = 0.0f;
        float endFrame = 0.0f;
        float frameRate = 0.0f;
        LongSparseArray<Layer> layerMap = new LongSparseArray<>();
        ArrayList arrayList = new ArrayList();
        Map<String, LottieImageAsset> images = new HashMap<>();
        Map<String, LottieImageAsset> images2 = new HashMap<>();
        Map<String, Font> fonts = new HashMap<>();
        List<Marker> markers2 = new ArrayList<>();
        SparseArrayCompat<FontCharacter> characters2 = new SparseArrayCompat<>();
        c0 composition = new c0();
        reader.g();
        int width = 0;
        int height = 0;
        while (true) {
            float frameRate2 = frameRate;
            if (reader.l() != 0) {
                switch (aVar.x(a)) {
                    case 0:
                        List<Marker> list = markers2;
                        width = reader.o();
                        frameRate = frameRate2;
                        aVar = reader;
                        continue;
                    case 1:
                        List<Marker> list2 = markers2;
                        height = reader.o();
                        frameRate = frameRate2;
                        aVar = reader;
                        continue;
                    case 2:
                        startFrame = (float) reader.n();
                        markers2 = markers2;
                        characters2 = characters2;
                        frameRate = frameRate2;
                        aVar = reader;
                        continue;
                    case 3:
                        endFrame = ((float) reader.n()) - 0.01f;
                        markers2 = markers2;
                        characters2 = characters2;
                        frameRate = frameRate2;
                        aVar = reader;
                        continue;
                    case 4:
                        markers2 = markers2;
                        characters2 = characters2;
                        frameRate = (float) reader.n();
                        aVar = reader;
                        continue;
                    case 5:
                        String[] versions = reader.s().split("\\.");
                        if (h.j(Integer.parseInt(versions[0]), Integer.parseInt(versions[1]), Integer.parseInt(versions[2]), 4, 4, 0)) {
                            characters = characters2;
                            markers = markers2;
                            break;
                        } else {
                            composition.a("Lottie only supports bodymovin >= 4.4.0");
                            characters = characters2;
                            markers = markers2;
                            break;
                        }
                    case 6:
                        e(aVar, composition, arrayList, layerMap);
                        characters = characters2;
                        markers = markers2;
                        break;
                    case 7:
                        b(aVar, composition, images, images2);
                        characters = characters2;
                        markers = markers2;
                        break;
                    case 8:
                        d(aVar, fonts);
                        characters = characters2;
                        markers = markers2;
                        break;
                    case 9:
                        c(aVar, composition, characters2);
                        characters = characters2;
                        markers = markers2;
                        break;
                    case 10:
                        f(aVar, markers2);
                        characters = characters2;
                        markers = markers2;
                        break;
                    default:
                        characters = characters2;
                        markers = markers2;
                        reader.z();
                        reader.E();
                        break;
                }
                markers2 = markers;
                characters2 = characters;
                frameRate = frameRate2;
                aVar = reader;
            } else {
                SparseArrayCompat<FontCharacter> characters3 = characters2;
                List<Marker> list3 = markers2;
                int scaledWidth = (int) (((float) width) * scale);
                int scaledHeight = (int) (((float) height) * scale);
                List<Marker> list4 = list3;
                int i = height;
                int i2 = width;
                c0 composition2 = composition;
                int i3 = scaledHeight;
                int i4 = scaledWidth;
                List<Marker> list5 = list4;
                Map<String, LottieImageAsset> map = images;
                ArrayList arrayList2 = arrayList;
                composition.s(new Rect(0, 0, scaledWidth, scaledHeight), startFrame, endFrame, frameRate2, arrayList, layerMap, images, images2, characters3, fonts, list4);
                return composition2;
            }
        }
    }

    private static void e(a reader, c0 composition, List<e> layers, LongSparseArray<e> layerMap) {
        int imageCount = 0;
        reader.c();
        while (reader.l()) {
            e layer = v.b(reader, composition);
            if (layer.f() == e.a.IMAGE) {
                imageCount++;
            }
            layers.add(layer);
            layerMap.put(layer.d(), layer);
            if (imageCount > 4) {
                d.c("You have " + imageCount + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
            }
        }
        reader.i();
    }

    private static void b(a reader, c0 composition, Map<String, List<e>> precomps, Map<String, f0> images) {
        reader.c();
        while (reader.l()) {
            String id = null;
            List<Layer> layers = new ArrayList<>();
            LongSparseArray<Layer> layerMap = new LongSparseArray<>();
            int width = 0;
            int height = 0;
            String imageFileName = null;
            String relativeFolder = null;
            reader.g();
            while (reader.l()) {
                a aVar = reader;
                switch (reader.x(b)) {
                    case 0:
                        id = reader.s();
                        break;
                    case 1:
                        reader.c();
                        while (reader.l()) {
                            e layer = v.b(reader, composition);
                            layerMap.put(layer.d(), layer);
                            layers.add(layer);
                        }
                        reader.i();
                        break;
                    case 2:
                        width = reader.o();
                        break;
                    case 3:
                        height = reader.o();
                        break;
                    case 4:
                        imageFileName = reader.s();
                        break;
                    case 5:
                        relativeFolder = reader.s();
                        break;
                    default:
                        reader.z();
                        reader.E();
                        break;
                }
            }
            a aVar2 = reader;
            reader.j();
            if (imageFileName != null) {
                f0 image = new f0(width, height, id, imageFileName, relativeFolder);
                images.put(image.d(), image);
                Map<String, List<e>> map = precomps;
            } else {
                Map<String, f0> map2 = images;
                precomps.put(id, layers);
            }
        }
        a aVar3 = reader;
        Map<String, List<e>> map3 = precomps;
        Map<String, f0> map4 = images;
        reader.i();
    }

    private static void d(a reader, Map<String, c> fonts) {
        reader.g();
        while (reader.l()) {
            switch (reader.x(c)) {
                case 0:
                    reader.c();
                    while (reader.l()) {
                        c font = n.a(reader);
                        fonts.put(font.b(), font);
                    }
                    reader.i();
                    break;
                default:
                    reader.z();
                    reader.E();
                    break;
            }
        }
        reader.j();
    }

    private static void c(a reader, c0 composition, SparseArrayCompat<com.airbnb.lottie.model.d> characters) {
        reader.c();
        while (reader.l()) {
            com.airbnb.lottie.model.d character = m.a(reader, composition);
            characters.put(character.hashCode(), character);
        }
        reader.i();
    }

    private static void f(a reader, List<com.airbnb.lottie.model.h> markers) {
        reader.c();
        while (reader.l()) {
            String comment = null;
            float frame = 0.0f;
            float durationFrames = 0.0f;
            reader.g();
            while (reader.l()) {
                switch (reader.x(d)) {
                    case 0:
                        comment = reader.s();
                        break;
                    case 1:
                        frame = (float) reader.n();
                        break;
                    case 2:
                        durationFrames = (float) reader.n();
                        break;
                    default:
                        reader.z();
                        reader.E();
                        break;
                }
            }
            reader.j();
            markers.add(new com.airbnb.lottie.model.h(comment, frame, durationFrames));
        }
        reader.i();
    }
}
