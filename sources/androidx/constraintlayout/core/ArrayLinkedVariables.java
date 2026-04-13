package androidx.constraintlayout.core;

import androidx.constraintlayout.core.ArrayRow;
import java.io.PrintStream;
import java.util.Arrays;

public class ArrayLinkedVariables implements ArrayRow.ArrayRowVariables {
    private static final boolean DEBUG = false;
    private static final boolean FULL_NEW_CHECK = false;
    static final int NONE = -1;
    private static float epsilon = 0.001f;
    private int ROW_SIZE = 8;
    private SolverVariable candidate = null;
    int currentSize = 0;
    private int[] mArrayIndices = new int[8];
    private int[] mArrayNextIndices = new int[8];
    private float[] mArrayValues = new float[8];
    protected final Cache mCache;
    private boolean mDidFillOnce = false;
    private int mHead = -1;
    private int mLast = -1;
    private final ArrayRow mRow;

    ArrayLinkedVariables(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.mCache = cache;
    }

    public final void put(SolverVariable variable, float value) {
        if (value == 0.0f) {
            remove(variable, true);
        } else if (this.mHead == -1) {
            this.mHead = 0;
            this.mArrayValues[0] = value;
            this.mArrayIndices[0] = variable.id;
            this.mArrayNextIndices[0] = -1;
            variable.usageInRowCount++;
            variable.addToRow(this.mRow);
            this.currentSize++;
            if (!this.mDidFillOnce) {
                int i = this.mLast + 1;
                this.mLast = i;
                int[] iArr = this.mArrayIndices;
                if (i >= iArr.length) {
                    this.mDidFillOnce = true;
                    this.mLast = iArr.length - 1;
                }
            }
        } else {
            int current = this.mHead;
            int previous = -1;
            int counter = 0;
            while (current != -1 && counter < this.currentSize) {
                int[] iArr2 = this.mArrayIndices;
                int i2 = iArr2[current];
                int i3 = variable.id;
                if (i2 == i3) {
                    this.mArrayValues[current] = value;
                    return;
                }
                if (iArr2[current] < i3) {
                    previous = current;
                }
                current = this.mArrayNextIndices[current];
                counter++;
            }
            int i4 = this.mLast;
            int availableIndice = i4 + 1;
            if (this.mDidFillOnce) {
                int[] iArr3 = this.mArrayIndices;
                if (iArr3[i4] == -1) {
                    availableIndice = this.mLast;
                } else {
                    availableIndice = iArr3.length;
                }
            }
            int[] iArr4 = this.mArrayIndices;
            if (availableIndice >= iArr4.length && this.currentSize < iArr4.length) {
                int i5 = 0;
                while (true) {
                    int[] iArr5 = this.mArrayIndices;
                    if (i5 >= iArr5.length) {
                        break;
                    } else if (iArr5[i5] == -1) {
                        availableIndice = i5;
                        break;
                    } else {
                        i5++;
                    }
                }
            }
            int[] iArr6 = this.mArrayIndices;
            if (availableIndice >= iArr6.length) {
                availableIndice = iArr6.length;
                int i6 = this.ROW_SIZE * 2;
                this.ROW_SIZE = i6;
                this.mDidFillOnce = false;
                this.mLast = availableIndice - 1;
                this.mArrayValues = Arrays.copyOf(this.mArrayValues, i6);
                this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
                this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
            }
            this.mArrayIndices[availableIndice] = variable.id;
            this.mArrayValues[availableIndice] = value;
            if (previous != -1) {
                int[] iArr7 = this.mArrayNextIndices;
                iArr7[availableIndice] = iArr7[previous];
                iArr7[previous] = availableIndice;
            } else {
                this.mArrayNextIndices[availableIndice] = this.mHead;
                this.mHead = availableIndice;
            }
            variable.usageInRowCount++;
            variable.addToRow(this.mRow);
            int i7 = this.currentSize + 1;
            this.currentSize = i7;
            if (!this.mDidFillOnce) {
                this.mLast++;
            }
            int[] iArr8 = this.mArrayIndices;
            if (i7 >= iArr8.length) {
                this.mDidFillOnce = true;
            }
            if (this.mLast >= iArr8.length) {
                this.mDidFillOnce = true;
                this.mLast = iArr8.length - 1;
            }
        }
    }

    public void add(SolverVariable variable, float value, boolean removeFromDefinition) {
        float f = epsilon;
        if (value > (-f) && value < f) {
            return;
        }
        if (this.mHead == -1) {
            this.mHead = 0;
            this.mArrayValues[0] = value;
            this.mArrayIndices[0] = variable.id;
            this.mArrayNextIndices[0] = -1;
            variable.usageInRowCount++;
            variable.addToRow(this.mRow);
            this.currentSize++;
            if (!this.mDidFillOnce) {
                int i = this.mLast + 1;
                this.mLast = i;
                int[] iArr = this.mArrayIndices;
                if (i >= iArr.length) {
                    this.mDidFillOnce = true;
                    this.mLast = iArr.length - 1;
                    return;
                }
                return;
            }
            return;
        }
        int current = this.mHead;
        int previous = -1;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            int[] iArr2 = this.mArrayIndices;
            int idx = iArr2[current];
            int i2 = variable.id;
            if (idx == i2) {
                float[] fArr = this.mArrayValues;
                float v = fArr[current] + value;
                float f2 = epsilon;
                if (v > (-f2) && v < f2) {
                    v = 0.0f;
                }
                fArr[current] = v;
                if (v == 0.0f) {
                    if (current == this.mHead) {
                        this.mHead = this.mArrayNextIndices[current];
                    } else {
                        int[] iArr3 = this.mArrayNextIndices;
                        iArr3[previous] = iArr3[current];
                    }
                    if (removeFromDefinition) {
                        variable.removeFromRow(this.mRow);
                    }
                    if (this.mDidFillOnce) {
                        this.mLast = current;
                    }
                    variable.usageInRowCount--;
                    this.currentSize--;
                    return;
                }
                return;
            }
            if (iArr2[current] < i2) {
                previous = current;
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        int i3 = this.mLast;
        int availableIndice = i3 + 1;
        if (this.mDidFillOnce) {
            int[] iArr4 = this.mArrayIndices;
            if (iArr4[i3] == -1) {
                availableIndice = this.mLast;
            } else {
                availableIndice = iArr4.length;
            }
        }
        int[] iArr5 = this.mArrayIndices;
        if (availableIndice >= iArr5.length && this.currentSize < iArr5.length) {
            int i4 = 0;
            while (true) {
                int[] iArr6 = this.mArrayIndices;
                if (i4 >= iArr6.length) {
                    break;
                } else if (iArr6[i4] == -1) {
                    availableIndice = i4;
                    break;
                } else {
                    i4++;
                }
            }
        }
        int[] iArr7 = this.mArrayIndices;
        if (availableIndice >= iArr7.length) {
            availableIndice = iArr7.length;
            int i5 = this.ROW_SIZE * 2;
            this.ROW_SIZE = i5;
            this.mDidFillOnce = false;
            this.mLast = availableIndice - 1;
            this.mArrayValues = Arrays.copyOf(this.mArrayValues, i5);
            this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
            this.mArrayNextIndices = Arrays.copyOf(this.mArrayNextIndices, this.ROW_SIZE);
        }
        this.mArrayIndices[availableIndice] = variable.id;
        this.mArrayValues[availableIndice] = value;
        if (previous != -1) {
            int[] iArr8 = this.mArrayNextIndices;
            iArr8[availableIndice] = iArr8[previous];
            iArr8[previous] = availableIndice;
        } else {
            this.mArrayNextIndices[availableIndice] = this.mHead;
            this.mHead = availableIndice;
        }
        variable.usageInRowCount++;
        variable.addToRow(this.mRow);
        this.currentSize++;
        if (!this.mDidFillOnce) {
            this.mLast++;
        }
        int i6 = this.mLast;
        int[] iArr9 = this.mArrayIndices;
        if (i6 >= iArr9.length) {
            this.mDidFillOnce = true;
            this.mLast = iArr9.length - 1;
        }
    }

    public float use(ArrayRow definition, boolean removeFromDefinition) {
        float value = get(definition.variable);
        remove(definition.variable, removeFromDefinition);
        ArrayRow.ArrayRowVariables definitionVariables = definition.variables;
        int definitionSize = definitionVariables.getCurrentSize();
        for (int i = 0; i < definitionSize; i++) {
            SolverVariable definitionVariable = definitionVariables.getVariable(i);
            add(definitionVariable, definitionVariables.get(definitionVariable) * value, removeFromDefinition);
        }
        return value;
    }

    public final float remove(SolverVariable variable, boolean removeFromDefinition) {
        if (this.candidate == variable) {
            this.candidate = null;
        }
        if (this.mHead == -1) {
            return 0.0f;
        }
        int current = this.mHead;
        int previous = -1;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayIndices[current] == variable.id) {
                if (current == this.mHead) {
                    this.mHead = this.mArrayNextIndices[current];
                } else {
                    int[] iArr = this.mArrayNextIndices;
                    iArr[previous] = iArr[current];
                }
                if (removeFromDefinition) {
                    variable.removeFromRow(this.mRow);
                }
                variable.usageInRowCount--;
                this.currentSize--;
                this.mArrayIndices[current] = -1;
                if (this.mDidFillOnce) {
                    this.mLast = current;
                }
                return this.mArrayValues[current];
            }
            previous = current;
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return 0.0f;
    }

    public final void clear() {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            SolverVariable variable = this.mCache.mIndexedVariables[this.mArrayIndices[current]];
            if (variable != null) {
                variable.removeFromRow(this.mRow);
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        this.mHead = -1;
        this.mLast = -1;
        this.mDidFillOnce = false;
        this.currentSize = 0;
    }

    public boolean contains(SolverVariable variable) {
        if (this.mHead == -1) {
            return false;
        }
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayIndices[current] == variable.id) {
                return true;
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return false;
    }

    public int indexOf(SolverVariable variable) {
        if (this.mHead == -1) {
            return -1;
        }
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayIndices[current] == variable.id) {
                return current;
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public boolean hasAtLeastOnePositiveVariable() {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayValues[current] > 0.0f) {
                return true;
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return false;
    }

    public void invert() {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            float[] fArr = this.mArrayValues;
            fArr[current] = fArr[current] * -1.0f;
            current = this.mArrayNextIndices[current];
            counter++;
        }
    }

    public void divideByAmount(float amount) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            float[] fArr = this.mArrayValues;
            fArr[current] = fArr[current] / amount;
            current = this.mArrayNextIndices[current];
            counter++;
        }
    }

    public int getHead() {
        return this.mHead;
    }

    public int getCurrentSize() {
        return this.currentSize;
    }

    public final int getId(int index) {
        return this.mArrayIndices[index];
    }

    public final float getValue(int index) {
        return this.mArrayValues[index];
    }

    public final int getNextIndice(int index) {
        return this.mArrayNextIndices[index];
    }

    /* access modifiers changed from: package-private */
    public SolverVariable getPivotCandidate() {
        SolverVariable solverVariable = this.candidate;
        if (solverVariable != null) {
            return solverVariable;
        }
        int current = this.mHead;
        int counter = 0;
        SolverVariable pivot = null;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayValues[current] < 0.0f) {
                SolverVariable v = this.mCache.mIndexedVariables[this.mArrayIndices[current]];
                if (pivot == null || pivot.strength < v.strength) {
                    pivot = v;
                }
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return pivot;
    }

    public SolverVariable getVariable(int index) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (counter == index) {
                return this.mCache.mIndexedVariables[this.mArrayIndices[current]];
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return null;
    }

    public float getVariableValue(int index) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (counter == index) {
                return this.mArrayValues[current];
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return 0.0f;
    }

    public final float get(SolverVariable v) {
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            if (this.mArrayIndices[current] == v.id) {
                return this.mArrayValues[current];
            }
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return 0.0f;
    }

    public int sizeInBytes() {
        return 0 + (this.mArrayIndices.length * 4 * 3) + 36;
    }

    public void display() {
        int count = this.currentSize;
        System.out.print("{ ");
        for (int i = 0; i < count; i++) {
            SolverVariable v = getVariable(i);
            if (v != null) {
                PrintStream printStream = System.out;
                printStream.print(v + " = " + getVariableValue(i) + " ");
            }
        }
        System.out.println(" }");
    }

    public String toString() {
        String result = "";
        int current = this.mHead;
        int counter = 0;
        while (current != -1 && counter < this.currentSize) {
            result = ((result + " -> ") + this.mArrayValues[current] + " : ") + this.mCache.mIndexedVariables[this.mArrayIndices[current]];
            current = this.mArrayNextIndices[current];
            counter++;
        }
        return result;
    }
}
