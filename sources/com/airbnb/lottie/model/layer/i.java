package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.animation.keyframe.o;
import com.airbnb.lottie.animation.keyframe.q;
import com.airbnb.lottie.c0;
import com.airbnb.lottie.e0;
import com.airbnb.lottie.j0;
import com.airbnb.lottie.model.animatable.k;
import com.airbnb.lottie.model.b;
import com.airbnb.lottie.model.content.p;
import com.airbnb.lottie.q0;
import com.airbnb.lottie.utils.h;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: TextLayer */
public class i extends b {
    private final StringBuilder D = new StringBuilder(2);
    private final RectF E = new RectF();
    private final Matrix F = new Matrix();
    private final Paint G = new a(1);
    private final Paint H = new b(1);
    private final Map<com.airbnb.lottie.model.d, List<com.airbnb.lottie.animation.content.d>> I = new HashMap();
    private final LongSparseArray<String> J = new LongSparseArray<>();
    private final List<d> K = new ArrayList();
    private final o L;
    private final e0 M;
    private final c0 N;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Integer, Integer> O;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Integer, Integer> P;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Integer, Integer> Q;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Integer, Integer> R;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Float, Float> S;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Float, Float> T;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Float, Float> U;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Float, Float> V;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Float, Float> W;
    @Nullable
    private com.airbnb.lottie.animation.keyframe.a<Typeface, Typeface> X;

    /* compiled from: TextLayer */
    public class a extends Paint {
        a(int arg0) {
            super(arg0);
            setStyle(Paint.Style.FILL);
        }
    }

    /* compiled from: TextLayer */
    public class b extends Paint {
        b(int arg0) {
            super(arg0);
            setStyle(Paint.Style.STROKE);
        }
    }

    i(e0 lottieDrawable, e layerModel) {
        super(lottieDrawable, layerModel);
        com.airbnb.lottie.model.animatable.b bVar;
        com.airbnb.lottie.model.animatable.b bVar2;
        com.airbnb.lottie.model.animatable.a aVar;
        com.airbnb.lottie.model.animatable.a aVar2;
        this.M = lottieDrawable;
        this.N = layerModel.b();
        o a2 = layerModel.s().j();
        this.L = a2;
        a2.a(this);
        g(a2);
        k textProperties = layerModel.t();
        if (!(textProperties == null || (aVar2 = textProperties.a) == null)) {
            com.airbnb.lottie.animation.keyframe.a<Integer, Integer> j = aVar2.j();
            this.O = j;
            j.a(this);
            g(this.O);
        }
        if (!(textProperties == null || (aVar = textProperties.b) == null)) {
            com.airbnb.lottie.animation.keyframe.a<Integer, Integer> j2 = aVar.j();
            this.Q = j2;
            j2.a(this);
            g(this.Q);
        }
        if (!(textProperties == null || (bVar2 = textProperties.c) == null)) {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> j3 = bVar2.j();
            this.S = j3;
            j3.a(this);
            g(this.S);
        }
        if (textProperties != null && (bVar = textProperties.d) != null) {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> j4 = bVar.j();
            this.U = j4;
            j4.a(this);
            g(this.U);
        }
    }

    public void f(RectF outBounds, Matrix parentMatrix, boolean applyParents) {
        super.f(outBounds, parentMatrix, applyParents);
        outBounds.set(0.0f, 0.0f, (float) this.N.b().width(), (float) this.N.b().height());
    }

    /* access modifiers changed from: package-private */
    public void s(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        com.airbnb.lottie.model.b documentData = (com.airbnb.lottie.model.b) this.L.h();
        com.airbnb.lottie.model.c font = this.N.g().get(documentData.b);
        if (font != null) {
            canvas.save();
            canvas.concat(parentMatrix);
            P(documentData, parentMatrix);
            if (this.M.Z0()) {
                X(documentData, parentMatrix, font, canvas);
            } else {
                W(documentData, font, canvas);
            }
            canvas.restore();
        }
    }

    private void P(com.airbnb.lottie.model.b documentData, Matrix parentMatrix) {
        com.airbnb.lottie.animation.keyframe.a<Integer, Integer> aVar = this.P;
        if (aVar != null) {
            this.G.setColor(aVar.h().intValue());
        } else {
            com.airbnb.lottie.animation.keyframe.a<Integer, Integer> aVar2 = this.O;
            if (aVar2 != null) {
                this.G.setColor(aVar2.h().intValue());
            } else {
                this.G.setColor(documentData.h);
            }
        }
        com.airbnb.lottie.animation.keyframe.a<Integer, Integer> aVar3 = this.R;
        if (aVar3 != null) {
            this.H.setColor(aVar3.h().intValue());
        } else {
            com.airbnb.lottie.animation.keyframe.a<Integer, Integer> aVar4 = this.Q;
            if (aVar4 != null) {
                this.H.setColor(aVar4.h().intValue());
            } else {
                this.H.setColor(documentData.i);
            }
        }
        int alpha = ((this.x.h() == null ? 100 : this.x.h().h().intValue()) * 255) / 100;
        this.G.setAlpha(alpha);
        this.H.setAlpha(alpha);
        com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar5 = this.T;
        if (aVar5 != null) {
            this.H.setStrokeWidth(aVar5.h().floatValue());
            return;
        }
        com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar6 = this.S;
        if (aVar6 != null) {
            this.H.setStrokeWidth(aVar6.h().floatValue());
        } else {
            this.H.setStrokeWidth(documentData.j * h.e());
        }
    }

    private void X(com.airbnb.lottie.model.b documentData, Matrix parentMatrix, com.airbnb.lottie.model.c font, Canvas canvas) {
        float textSize;
        float tracking;
        com.airbnb.lottie.model.b bVar = documentData;
        com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar = this.W;
        if (aVar != null) {
            textSize = aVar.h().floatValue();
        } else {
            textSize = bVar.c;
        }
        float fontScale = textSize / 100.0f;
        float parentScale = h.g(parentMatrix);
        String text = bVar.a;
        List<String> textLines = a0(text);
        int textLineCount = textLines.size();
        float tracking2 = ((float) bVar.e) / 10.0f;
        com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar2 = this.V;
        if (aVar2 != null) {
            tracking = tracking2 + aVar2.h().floatValue();
        } else {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar3 = this.U;
            if (aVar3 != null) {
                tracking = tracking2 + aVar3.h().floatValue();
            } else {
                tracking = tracking2;
            }
        }
        int lineIndex = -1;
        int i = 0;
        while (i < textLineCount) {
            String textLine = textLines.get(i);
            PointF pointF = bVar.m;
            int i2 = i;
            List<d> e0 = e0(textLine, pointF == null ? 0.0f : pointF.x, font, fontScale, tracking, true);
            int j = 0;
            while (j < e0.size()) {
                d line = e0.get(j);
                int lineIndex2 = lineIndex + 1;
                canvas.save();
                d0(canvas, bVar, lineIndex2, line.b);
                V(line.a, documentData, font, canvas, parentScale, fontScale, tracking);
                canvas.restore();
                j++;
                lineIndex = lineIndex2;
                textLineCount = textLineCount;
                textLines = textLines;
                text = text;
            }
            List<String> list = textLines;
            String str = text;
            i = i2 + 1;
        }
    }

    private void V(String text, com.airbnb.lottie.model.b documentData, com.airbnb.lottie.model.c font, Canvas canvas, float parentScale, float fontScale, float tracking) {
        for (int i = 0; i < text.length(); i++) {
            com.airbnb.lottie.model.d character = this.N.c().get(com.airbnb.lottie.model.d.c(text.charAt(i), font.a(), font.c()));
            if (character != null) {
                R(character, fontScale, documentData, canvas);
                canvas.translate((((float) character.b()) * fontScale * h.e()) + tracking, 0.0f);
            }
        }
    }

    private void W(com.airbnb.lottie.model.b documentData, com.airbnb.lottie.model.c font, Canvas canvas) {
        String text;
        float textSize;
        com.airbnb.lottie.model.b bVar = documentData;
        Canvas canvas2 = canvas;
        Typeface typeface = b0(font);
        if (typeface != null) {
            String text2 = bVar.a;
            q0 textDelegate = this.M.J();
            if (textDelegate != null) {
                text = textDelegate.a(getName(), text2);
            } else {
                text = text2;
            }
            this.G.setTypeface(typeface);
            com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar = this.W;
            if (aVar != null) {
                textSize = aVar.h().floatValue();
            } else {
                textSize = bVar.c;
            }
            this.G.setTextSize(h.e() * textSize);
            this.H.setTypeface(this.G.getTypeface());
            this.H.setTextSize(this.G.getTextSize());
            float tracking = ((float) bVar.e) / 10.0f;
            com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar2 = this.V;
            if (aVar2 != null) {
                tracking += aVar2.h().floatValue();
            } else {
                com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar3 = this.U;
                if (aVar3 != null) {
                    tracking += aVar3.h().floatValue();
                }
            }
            float tracking2 = ((h.e() * tracking) * textSize) / 100.0f;
            List<String> textLines = a0(text);
            int textLineCount = textLines.size();
            int lineIndex = -1;
            int lineIndex2 = 0;
            while (lineIndex2 < textLineCount) {
                String textLine = textLines.get(lineIndex2);
                PointF pointF = bVar.m;
                int i = lineIndex2;
                int textLineCount2 = textLineCount;
                List<String> textLines2 = textLines;
                List<d> e0 = e0(textLine, pointF == null ? 0.0f : pointF.x, font, 0.0f, tracking2, false);
                int j = 0;
                while (j < e0.size()) {
                    d line = e0.get(j);
                    int lineIndex3 = lineIndex + 1;
                    canvas.save();
                    d0(canvas2, bVar, lineIndex3, line.b);
                    T(line.a, bVar, canvas2, tracking2);
                    canvas.restore();
                    j++;
                    lineIndex = lineIndex3;
                }
                lineIndex2 = i + 1;
                textLineCount = textLineCount2;
                textLines = textLines2;
            }
        }
    }

    private void d0(Canvas canvas, com.airbnb.lottie.model.b documentData, int lineIndex, float lineWidth) {
        PointF position = documentData.l;
        PointF size = documentData.m;
        float dpScale = h.e();
        float boxWidth = 0.0f;
        float lineOffset = (((float) lineIndex) * documentData.f * dpScale) + (position == null ? 0.0f : (documentData.f * dpScale) + position.y);
        float lineStart = position == null ? 0.0f : position.x;
        if (size != null) {
            boxWidth = size.x;
        }
        switch (c.a[documentData.d.ordinal()]) {
            case 1:
                canvas.translate(lineStart, lineOffset);
                return;
            case 2:
                canvas.translate((lineStart + boxWidth) - lineWidth, lineOffset);
                return;
            case 3:
                canvas.translate(((boxWidth / 2.0f) + lineStart) - (lineWidth / 2.0f), lineOffset);
                return;
            default:
                return;
        }
    }

    /* compiled from: TextLayer */
    public static /* synthetic */ class c {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[b.a.values().length];
            a = iArr;
            try {
                iArr[b.a.LEFT_ALIGN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.a.RIGHT_ALIGN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b.a.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @Nullable
    private Typeface b0(com.airbnb.lottie.model.c font) {
        Typeface callbackTypeface;
        com.airbnb.lottie.animation.keyframe.a<Typeface, Typeface> aVar = this.X;
        if (aVar != null && (callbackTypeface = aVar.h()) != null) {
            return callbackTypeface;
        }
        Typeface drawableTypeface = this.M.K(font);
        if (drawableTypeface != null) {
            return drawableTypeface;
        }
        return font.d();
    }

    private List<String> a0(String text) {
        return Arrays.asList(text.replaceAll("\r\n", "\r").replaceAll("\u0003", "\r").replaceAll("\n", "\r").split("\r"));
    }

    private void T(String text, com.airbnb.lottie.model.b documentData, Canvas canvas, float tracking) {
        int i = 0;
        while (i < text.length()) {
            String charString = O(text, i);
            i += charString.length();
            S(charString, documentData, canvas);
            canvas.translate(this.G.measureText(charString) + tracking, 0.0f);
        }
    }

    private List<d> e0(String textLine, float boxWidth, com.airbnb.lottie.model.c font, float fontScale, float tracking, boolean usingGlyphs) {
        float currentCharWidth;
        String str = textLine;
        int currentLineStartIndex = 0;
        float currentLineWidth = 0.0f;
        int currentWordStartIndex = 0;
        int currentWordStartIndex2 = 0;
        float currentWordWidth = 0.0f;
        boolean nextCharacterStartsWord = false;
        float spaceWidth = 0.0f;
        for (int i = 0; i < textLine.length(); i++) {
            char c2 = str.charAt(i);
            if (usingGlyphs) {
                com.airbnb.lottie.model.d character = this.N.c().get(com.airbnb.lottie.model.d.c(c2, font.a(), font.c()));
                if (character == null) {
                } else {
                    currentCharWidth = (((float) character.b()) * fontScale * h.e()) + tracking;
                }
            } else {
                currentCharWidth = this.G.measureText(str.substring(i, i + 1)) + tracking;
            }
            if (c2 == ' ') {
                spaceWidth = currentCharWidth;
                nextCharacterStartsWord = true;
            } else if (nextCharacterStartsWord) {
                nextCharacterStartsWord = false;
                currentWordStartIndex2 = i;
                currentWordWidth = currentCharWidth;
            } else {
                currentWordWidth += currentCharWidth;
            }
            currentLineWidth += currentCharWidth;
            if (boxWidth > 0.0f && currentLineWidth >= boxWidth && c2 != ' ') {
                int lineCount = currentLineStartIndex + 1;
                d subLine = Y(lineCount);
                if (currentWordStartIndex2 == currentWordStartIndex) {
                    String substr = str.substring(currentWordStartIndex, i);
                    String trimmed = substr.trim();
                    subLine.c(trimmed, (currentLineWidth - currentCharWidth) - (((float) (trimmed.length() - substr.length())) * spaceWidth));
                    currentLineWidth = currentCharWidth;
                    currentWordStartIndex = i;
                    currentWordWidth = currentCharWidth;
                    currentWordStartIndex2 = currentWordStartIndex;
                    currentLineStartIndex = lineCount;
                } else {
                    int lineCount2 = lineCount;
                    String substr2 = str.substring(currentWordStartIndex, currentWordStartIndex2 - 1);
                    String trimmed2 = substr2.trim();
                    subLine.c(trimmed2, ((currentLineWidth - currentWordWidth) - (((float) (substr2.length() - trimmed2.length())) * spaceWidth)) - spaceWidth);
                    currentWordStartIndex = currentWordStartIndex2;
                    currentLineWidth = currentWordWidth;
                    currentLineStartIndex = lineCount2;
                }
            }
        }
        if (currentLineWidth > 0.0f) {
            currentLineStartIndex++;
            Y(currentLineStartIndex).c(str.substring(currentWordStartIndex), currentLineWidth);
        }
        return this.K.subList(0, currentLineStartIndex);
    }

    private d Y(int numLines) {
        for (int i = this.K.size(); i < numLines; i++) {
            this.K.add(new d((a) null));
        }
        return this.K.get(numLines - 1);
    }

    private void R(com.airbnb.lottie.model.d character, float fontScale, com.airbnb.lottie.model.b documentData, Canvas canvas) {
        List<com.airbnb.lottie.animation.content.d> Z = Z(character);
        for (int j = 0; j < Z.size(); j++) {
            Path path = Z.get(j).getPath();
            path.computeBounds(this.E, false);
            this.F.reset();
            this.F.preTranslate(0.0f, (-documentData.g) * h.e());
            this.F.preScale(fontScale, fontScale);
            path.transform(this.F);
            if (documentData.k) {
                U(path, this.G, canvas);
                U(path, this.H, canvas);
            } else {
                U(path, this.H, canvas);
                U(path, this.G, canvas);
            }
        }
    }

    private void U(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() != 0) {
            if (paint.getStyle() != Paint.Style.STROKE || paint.getStrokeWidth() != 0.0f) {
                canvas.drawPath(path, paint);
            }
        }
    }

    private void S(String character, com.airbnb.lottie.model.b documentData, Canvas canvas) {
        if (documentData.k) {
            Q(character, this.G, canvas);
            Q(character, this.H, canvas);
            return;
        }
        Q(character, this.H, canvas);
        Q(character, this.G, canvas);
    }

    private void Q(String character, Paint paint, Canvas canvas) {
        if (paint.getColor() != 0) {
            if (paint.getStyle() != Paint.Style.STROKE || paint.getStrokeWidth() != 0.0f) {
                canvas.drawText(character, 0, character.length(), 0.0f, 0.0f, paint);
            }
        }
    }

    private List<com.airbnb.lottie.animation.content.d> Z(com.airbnb.lottie.model.d character) {
        if (this.I.containsKey(character)) {
            return this.I.get(character);
        }
        List<p> a2 = character.a();
        int size = a2.size();
        List<ContentGroup> contents = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            contents.add(new com.airbnb.lottie.animation.content.d(this.M, this, a2.get(i), this.N));
        }
        this.I.put(character, contents);
        return contents;
    }

    private String O(String text, int startIndex) {
        int firstCodePoint = text.codePointAt(startIndex);
        int key = firstCodePoint;
        int index = startIndex + Character.charCount(firstCodePoint);
        while (index < text.length()) {
            int nextCodePoint = text.codePointAt(index);
            if (!c0(nextCodePoint)) {
                break;
            }
            index += Character.charCount(nextCodePoint);
            key = (key * 31) + nextCodePoint;
        }
        if (this.J.containsKey((long) key)) {
            return this.J.get((long) key);
        }
        this.D.setLength(0);
        int i = startIndex;
        while (i < index) {
            int codePoint = text.codePointAt(i);
            this.D.appendCodePoint(codePoint);
            i += Character.charCount(codePoint);
        }
        String str = this.D.toString();
        this.J.put((long) key, str);
        return str;
    }

    private boolean c0(int codePoint) {
        return Character.getType(codePoint) == 16 || Character.getType(codePoint) == 27 || Character.getType(codePoint) == 6 || Character.getType(codePoint) == 28 || Character.getType(codePoint) == 8 || Character.getType(codePoint) == 19;
    }

    public <T> void d(T property, @Nullable com.airbnb.lottie.value.c<T> callback) {
        super.d(property, callback);
        if (property == j0.a) {
            com.airbnb.lottie.animation.keyframe.a<Integer, Integer> aVar = this.P;
            if (aVar != null) {
                G(aVar);
            }
            if (callback == null) {
                this.P = null;
                return;
            }
            q qVar = new q(callback);
            this.P = qVar;
            qVar.a(this);
            g(this.P);
        } else if (property == j0.b) {
            com.airbnb.lottie.animation.keyframe.a<Integer, Integer> aVar2 = this.R;
            if (aVar2 != null) {
                G(aVar2);
            }
            if (callback == null) {
                this.R = null;
                return;
            }
            q qVar2 = new q(callback);
            this.R = qVar2;
            qVar2.a(this);
            g(this.R);
        } else if (property == j0.s) {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar3 = this.T;
            if (aVar3 != null) {
                G(aVar3);
            }
            if (callback == null) {
                this.T = null;
                return;
            }
            q qVar3 = new q(callback);
            this.T = qVar3;
            qVar3.a(this);
            g(this.T);
        } else if (property == j0.t) {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar4 = this.V;
            if (aVar4 != null) {
                G(aVar4);
            }
            if (callback == null) {
                this.V = null;
                return;
            }
            q qVar4 = new q(callback);
            this.V = qVar4;
            qVar4.a(this);
            g(this.V);
        } else if (property == j0.F) {
            com.airbnb.lottie.animation.keyframe.a<Float, Float> aVar5 = this.W;
            if (aVar5 != null) {
                G(aVar5);
            }
            if (callback == null) {
                this.W = null;
                return;
            }
            q qVar5 = new q(callback);
            this.W = qVar5;
            qVar5.a(this);
            g(this.W);
        } else if (property == j0.M) {
            com.airbnb.lottie.animation.keyframe.a<Typeface, Typeface> aVar6 = this.X;
            if (aVar6 != null) {
                G(aVar6);
            }
            if (callback == null) {
                this.X = null;
                return;
            }
            q qVar6 = new q(callback);
            this.X = qVar6;
            qVar6.a(this);
            g(this.X);
        } else if (property == j0.O) {
            this.L.q(callback);
        }
    }

    /* compiled from: TextLayer */
    public static class d {
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public float b;

        private d() {
            this.a = "";
            this.b = 0.0f;
        }

        /* synthetic */ d(a x0) {
            this();
        }

        /* access modifiers changed from: package-private */
        public void c(String text, float width) {
            this.a = text;
            this.b = width;
        }
    }
}
