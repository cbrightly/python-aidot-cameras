package androidx.recyclerview.widget;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DiffUtil {
    private static final Comparator<Diagonal> DIAGONAL_COMPARATOR = new Comparator<Diagonal>() {
        public int compare(Diagonal o1, Diagonal o2) {
            return o1.x - o2.x;
        }
    };

    private DiffUtil() {
    }

    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback cb) {
        return calculateDiff(cb, true);
    }

    @NonNull
    public static DiffResult calculateDiff(@NonNull Callback cb, boolean detectMoves) {
        int oldSize = cb.getOldListSize();
        int newSize = cb.getNewListSize();
        List<Diagonal> diagonals = new ArrayList<>();
        List<Range> stack = new ArrayList<>();
        stack.add(new Range(0, oldSize, 0, newSize));
        int max = ((oldSize + newSize) + 1) / 2;
        CenteredArray forward = new CenteredArray((max * 2) + 1);
        CenteredArray backward = new CenteredArray((max * 2) + 1);
        List<Range> rangePool = new ArrayList<>();
        while (!stack.isEmpty()) {
            Range range = stack.remove(stack.size() - 1);
            Snake snake = midPoint(range, cb, forward, backward);
            if (snake != null) {
                if (snake.diagonalSize() > 0) {
                    diagonals.add(snake.toDiagonal());
                }
                Range left = rangePool.isEmpty() ? new Range() : rangePool.remove(rangePool.size() - 1);
                left.oldListStart = range.oldListStart;
                left.newListStart = range.newListStart;
                left.oldListEnd = snake.startX;
                left.newListEnd = snake.startY;
                stack.add(left);
                Range right = range;
                right.oldListEnd = range.oldListEnd;
                right.newListEnd = range.newListEnd;
                right.oldListStart = snake.endX;
                right.newListStart = snake.endY;
                stack.add(right);
            } else {
                rangePool.add(range);
            }
        }
        Callback callback = cb;
        Collections.sort(diagonals, DIAGONAL_COMPARATOR);
        return new DiffResult(cb, diagonals, forward.backingData(), backward.backingData(), detectMoves);
    }

    @Nullable
    private static Snake midPoint(Range range, Callback cb, CenteredArray forward, CenteredArray backward) {
        if (range.oldSize() < 1 || range.newSize() < 1) {
            return null;
        }
        int max = ((range.oldSize() + range.newSize()) + 1) / 2;
        forward.set(1, range.oldListStart);
        backward.set(1, range.oldListEnd);
        for (int d = 0; d < max; d++) {
            Snake snake = forward(range, cb, forward, backward, d);
            if (snake != null) {
                return snake;
            }
            Snake snake2 = backward(range, cb, forward, backward, d);
            if (snake2 != null) {
                return snake2;
            }
        }
        return null;
    }

    @Nullable
    private static Snake forward(Range range, Callback cb, CenteredArray forward, CenteredArray backward, int d) {
        int startX;
        int startX2;
        Range range2 = range;
        CenteredArray centeredArray = forward;
        int i = d;
        boolean checkForSnake = Math.abs(range.oldSize() - range.newSize()) % 2 == 1;
        int delta = range.oldSize() - range.newSize();
        for (int k = -i; k <= i; k += 2) {
            if (k == (-i) || (k != i && centeredArray.get(k + 1) > centeredArray.get(k - 1))) {
                startX = centeredArray.get(k + 1);
                startX2 = startX;
            } else {
                startX2 = centeredArray.get(k - 1);
                startX = startX2 + 1;
            }
            int y = (range2.newListStart + (startX - range2.oldListStart)) - k;
            int startY = (i == 0 || startX != startX2) ? y : y - 1;
            while (true) {
                if (startX < range2.oldListEnd && y < range2.newListEnd) {
                    if (!cb.areItemsTheSame(startX, y)) {
                        break;
                    }
                    startX++;
                    y++;
                } else {
                    Callback callback = cb;
                }
            }
            centeredArray.set(k, startX);
            if (checkForSnake) {
                int backwardsK = delta - k;
                if (backwardsK < (-i) + 1 || backwardsK > i - 1) {
                    CenteredArray centeredArray2 = backward;
                } else if (backward.get(backwardsK) <= startX) {
                    Snake snake = new Snake();
                    snake.startX = startX2;
                    snake.startY = startY;
                    snake.endX = startX;
                    snake.endY = y;
                    snake.reverse = false;
                    return snake;
                }
            } else {
                CenteredArray centeredArray3 = backward;
            }
        }
        Callback callback2 = cb;
        CenteredArray centeredArray4 = backward;
        return null;
    }

    @Nullable
    private static Snake backward(Range range, Callback cb, CenteredArray forward, CenteredArray backward, int d) {
        int startX;
        int startX2;
        int forwardsK;
        boolean checkForSnake = (range.oldSize() - range.newSize()) % 2 == 0;
        int delta = range.oldSize() - range.newSize();
        int k = -d;
        while (k <= d) {
            if (k == (-d) || (k != d && backward.get(k + 1) < backward.get(k - 1))) {
                startX = backward.get(k + 1);
                startX2 = startX;
            } else {
                startX2 = backward.get(k - 1);
                startX = startX2 - 1;
            }
            int y = range.newListEnd - ((range.oldListEnd - startX) - k);
            int startY = (d == 0 || startX != startX2) ? y : y + 1;
            while (startX > range.oldListStart && y > range.newListStart && cb.areItemsTheSame(startX - 1, y - 1)) {
                startX--;
                y--;
            }
            backward.set(k, startX);
            if (!checkForSnake || (forwardsK = delta - k) < (-d) || forwardsK > d || forward.get(forwardsK) < startX) {
                k += 2;
            } else {
                Snake snake = new Snake();
                snake.startX = startX;
                snake.startY = y;
                snake.endX = startX2;
                snake.endY = startY;
                snake.reverse = true;
                return snake;
            }
        }
        return null;
    }

    public static abstract class Callback {
        public abstract boolean areContentsTheSame(int i, int i2);

        public abstract boolean areItemsTheSame(int i, int i2);

        public abstract int getNewListSize();

        public abstract int getOldListSize();

        @Nullable
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            return null;
        }
    }

    public static abstract class ItemCallback<T> {
        public abstract boolean areContentsTheSame(@NonNull T t, @NonNull T t2);

        public abstract boolean areItemsTheSame(@NonNull T t, @NonNull T t2);

        @Nullable
        public Object getChangePayload(@NonNull T t, @NonNull T t2) {
            return null;
        }
    }

    public static class Diagonal {
        public final int size;
        public final int x;
        public final int y;

        Diagonal(int x2, int y2, int size2) {
            this.x = x2;
            this.y = y2;
            this.size = size2;
        }

        /* access modifiers changed from: package-private */
        public int endX() {
            return this.x + this.size;
        }

        /* access modifiers changed from: package-private */
        public int endY() {
            return this.y + this.size;
        }
    }

    public static class Snake {
        public int endX;
        public int endY;
        public boolean reverse;
        public int startX;
        public int startY;

        Snake() {
        }

        /* access modifiers changed from: package-private */
        public boolean hasAdditionOrRemoval() {
            return this.endY - this.startY != this.endX - this.startX;
        }

        /* access modifiers changed from: package-private */
        public boolean isAddition() {
            return this.endY - this.startY > this.endX - this.startX;
        }

        /* access modifiers changed from: package-private */
        public int diagonalSize() {
            return Math.min(this.endX - this.startX, this.endY - this.startY);
        }

        /* access modifiers changed from: package-private */
        @NonNull
        public Diagonal toDiagonal() {
            if (!hasAdditionOrRemoval()) {
                int i = this.startX;
                return new Diagonal(i, this.startY, this.endX - i);
            } else if (this.reverse) {
                return new Diagonal(this.startX, this.startY, diagonalSize());
            } else {
                if (isAddition()) {
                    return new Diagonal(this.startX, this.startY + 1, diagonalSize());
                }
                return new Diagonal(this.startX + 1, this.startY, diagonalSize());
            }
        }
    }

    public static class Range {
        int newListEnd;
        int newListStart;
        int oldListEnd;
        int oldListStart;

        public Range() {
        }

        public Range(int oldListStart2, int oldListEnd2, int newListStart2, int newListEnd2) {
            this.oldListStart = oldListStart2;
            this.oldListEnd = oldListEnd2;
            this.newListStart = newListStart2;
            this.newListEnd = newListEnd2;
        }

        /* access modifiers changed from: package-private */
        public int oldSize() {
            return this.oldListEnd - this.oldListStart;
        }

        /* access modifiers changed from: package-private */
        public int newSize() {
            return this.newListEnd - this.newListStart;
        }
    }

    public static class DiffResult {
        private static final int FLAG_CHANGED = 2;
        private static final int FLAG_MASK = 15;
        private static final int FLAG_MOVED = 12;
        private static final int FLAG_MOVED_CHANGED = 4;
        private static final int FLAG_MOVED_NOT_CHANGED = 8;
        private static final int FLAG_NOT_CHANGED = 1;
        private static final int FLAG_OFFSET = 4;
        public static final int NO_POSITION = -1;
        private final Callback mCallback;
        private final boolean mDetectMoves;
        private final List<Diagonal> mDiagonals;
        private final int[] mNewItemStatuses;
        private final int mNewListSize;
        private final int[] mOldItemStatuses;
        private final int mOldListSize;

        DiffResult(Callback callback, List<Diagonal> diagonals, int[] oldItemStatuses, int[] newItemStatuses, boolean detectMoves) {
            this.mDiagonals = diagonals;
            this.mOldItemStatuses = oldItemStatuses;
            this.mNewItemStatuses = newItemStatuses;
            Arrays.fill(oldItemStatuses, 0);
            Arrays.fill(newItemStatuses, 0);
            this.mCallback = callback;
            this.mOldListSize = callback.getOldListSize();
            this.mNewListSize = callback.getNewListSize();
            this.mDetectMoves = detectMoves;
            addEdgeDiagonals();
            findMatchingItems();
        }

        private void addEdgeDiagonals() {
            Diagonal first = this.mDiagonals.isEmpty() ? null : this.mDiagonals.get(0);
            if (!(first != null && first.x == 0 && first.y == 0)) {
                this.mDiagonals.add(0, new Diagonal(0, 0, 0));
            }
            this.mDiagonals.add(new Diagonal(this.mOldListSize, this.mNewListSize, 0));
        }

        private void findMatchingItems() {
            for (Diagonal diagonal : this.mDiagonals) {
                for (int offset = 0; offset < diagonal.size; offset++) {
                    int posX = diagonal.x + offset;
                    int posY = diagonal.y + offset;
                    int changeFlag = this.mCallback.areContentsTheSame(posX, posY) ? 1 : 2;
                    this.mOldItemStatuses[posX] = (posY << 4) | changeFlag;
                    this.mNewItemStatuses[posY] = (posX << 4) | changeFlag;
                }
            }
            if (this.mDetectMoves) {
                findMoveMatches();
            }
        }

        private void findMoveMatches() {
            int posX = 0;
            for (Diagonal diagonal : this.mDiagonals) {
                while (posX < diagonal.x) {
                    if (this.mOldItemStatuses[posX] == 0) {
                        findMatchingAddition(posX);
                    }
                    posX++;
                }
                posX = diagonal.endX();
            }
        }

        private void findMatchingAddition(int posX) {
            int changeFlag;
            int posY = 0;
            int diagonalsSize = this.mDiagonals.size();
            for (int i = 0; i < diagonalsSize; i++) {
                Diagonal diagonal = this.mDiagonals.get(i);
                while (posY < diagonal.y) {
                    if (this.mNewItemStatuses[posY] != 0 || !this.mCallback.areItemsTheSame(posX, posY)) {
                        posY++;
                    } else {
                        if (this.mCallback.areContentsTheSame(posX, posY)) {
                            changeFlag = 8;
                        } else {
                            changeFlag = 4;
                        }
                        this.mOldItemStatuses[posX] = (posY << 4) | changeFlag;
                        this.mNewItemStatuses[posY] = (posX << 4) | changeFlag;
                        return;
                    }
                }
                posY = diagonal.endY();
            }
        }

        public int convertOldPositionToNew(@IntRange(from = 0) int oldListPosition) {
            if (oldListPosition < 0 || oldListPosition >= this.mOldListSize) {
                throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + oldListPosition + ", old list size = " + this.mOldListSize);
            }
            int status = this.mOldItemStatuses[oldListPosition];
            if ((status & 15) == 0) {
                return -1;
            }
            return status >> 4;
        }

        public int convertNewPositionToOld(@IntRange(from = 0) int newListPosition) {
            if (newListPosition < 0 || newListPosition >= this.mNewListSize) {
                throw new IndexOutOfBoundsException("Index out of bounds - passed position = " + newListPosition + ", new list size = " + this.mNewListSize);
            }
            int status = this.mNewItemStatuses[newListPosition];
            if ((status & 15) == 0) {
                return -1;
            }
            return status >> 4;
        }

        public void dispatchUpdatesTo(@NonNull RecyclerView.Adapter adapter) {
            dispatchUpdatesTo((ListUpdateCallback) new AdapterListUpdateCallback(adapter));
        }

        public void dispatchUpdatesTo(@NonNull ListUpdateCallback updateCallback) {
            BatchingListUpdateCallback batchingCallback;
            boolean z;
            int posY;
            ListUpdateCallback updateCallback2;
            ListUpdateCallback updateCallback3 = updateCallback;
            if (updateCallback3 instanceof BatchingListUpdateCallback) {
                batchingCallback = (BatchingListUpdateCallback) updateCallback3;
            } else {
                batchingCallback = new BatchingListUpdateCallback(updateCallback3);
                updateCallback3 = batchingCallback;
            }
            int currentListSize = this.mOldListSize;
            Collection<PostponedUpdate> postponedUpdates = new ArrayDeque<>();
            int posX = this.mOldListSize;
            int posY2 = this.mNewListSize;
            int posY3 = true;
            int diagonalIndex = this.mDiagonals.size() - 1;
            while (diagonalIndex >= 0) {
                Diagonal diagonal = this.mDiagonals.get(diagonalIndex);
                int endX = diagonal.endX();
                int endY = diagonal.endY();
                while (true) {
                    z = false;
                    if (posX <= endX) {
                        break;
                    }
                    posX--;
                    int status = this.mOldItemStatuses[posX];
                    if ((status & 12) != 0) {
                        int newPos = status >> 4;
                        PostponedUpdate postponedUpdate = getPostponedUpdate(postponedUpdates, newPos, false);
                        if (postponedUpdate != null) {
                            int updatedNewPos = currentListSize - postponedUpdate.currentPos;
                            batchingCallback.onMoved(posX, updatedNewPos - 1);
                            if ((status & 4) != 0) {
                                updateCallback2 = updateCallback3;
                                posY = posY2;
                                batchingCallback.onChanged(updatedNewPos - 1, 1, this.mCallback.getChangePayload(posX, newPos));
                            } else {
                                updateCallback2 = updateCallback3;
                                posY = posY2;
                            }
                        } else {
                            updateCallback2 = updateCallback3;
                            posY = posY2;
                            boolean z2 = posY3;
                            postponedUpdates.add(new PostponedUpdate(posX, (currentListSize - posX) - (z2 ? 1 : 0), z2));
                        }
                    } else {
                        updateCallback2 = updateCallback3;
                        posY = posY2;
                        batchingCallback.onRemoved(posX, (int) posY3);
                        currentListSize--;
                    }
                    updateCallback3 = updateCallback2;
                    posY2 = posY;
                    posY3 = true;
                }
                ListUpdateCallback updateCallback4 = updateCallback3;
                int i = posY2;
                while (posY2 > endY) {
                    posY2--;
                    int status2 = this.mNewItemStatuses[posY2];
                    if ((status2 & 12) != 0) {
                        int oldPos = status2 >> 4;
                        PostponedUpdate postponedUpdate2 = getPostponedUpdate(postponedUpdates, oldPos, true);
                        if (postponedUpdate2 == null) {
                            postponedUpdates.add(new PostponedUpdate(posY2, currentListSize - posX, z));
                        } else {
                            batchingCallback.onMoved((currentListSize - postponedUpdate2.currentPos) - 1, posX);
                            if ((status2 & 4) != 0) {
                                batchingCallback.onChanged(posX, 1, this.mCallback.getChangePayload(oldPos, posY2));
                            }
                        }
                    } else {
                        batchingCallback.onInserted(posX, 1);
                        currentListSize++;
                    }
                    z = false;
                }
                int posX2 = diagonal.x;
                int posY4 = diagonal.y;
                for (int i2 = 0; i2 < diagonal.size; i2++) {
                    if ((this.mOldItemStatuses[posX2] & 15) == 2) {
                        batchingCallback.onChanged(posX2, 1, this.mCallback.getChangePayload(posX2, posY4));
                    }
                    posX2++;
                    posY4++;
                }
                int posX3 = diagonal.x;
                posY2 = diagonal.y;
                diagonalIndex--;
                posX = posX3;
                posY3 = true;
                updateCallback3 = updateCallback4;
            }
            batchingCallback.dispatchLastEvent();
        }

        @Nullable
        private static PostponedUpdate getPostponedUpdate(Collection<PostponedUpdate> postponedUpdates, int posInList, boolean removal) {
            PostponedUpdate postponedUpdate = null;
            Iterator<PostponedUpdate> itr = postponedUpdates.iterator();
            while (true) {
                if (!itr.hasNext()) {
                    break;
                }
                PostponedUpdate update = itr.next();
                if (update.posInOwnerList == posInList && update.removal == removal) {
                    postponedUpdate = update;
                    itr.remove();
                    break;
                }
            }
            while (itr.hasNext()) {
                PostponedUpdate update2 = itr.next();
                if (removal) {
                    update2.currentPos--;
                } else {
                    update2.currentPos++;
                }
            }
            return postponedUpdate;
        }
    }

    public static class PostponedUpdate {
        int currentPos;
        int posInOwnerList;
        boolean removal;

        PostponedUpdate(int posInOwnerList2, int currentPos2, boolean removal2) {
            this.posInOwnerList = posInOwnerList2;
            this.currentPos = currentPos2;
            this.removal = removal2;
        }
    }

    public static class CenteredArray {
        private final int[] mData;
        private final int mMid;

        CenteredArray(int size) {
            int[] iArr = new int[size];
            this.mData = iArr;
            this.mMid = iArr.length / 2;
        }

        /* access modifiers changed from: package-private */
        public int get(int index) {
            return this.mData[this.mMid + index];
        }

        /* access modifiers changed from: package-private */
        public int[] backingData() {
            return this.mData;
        }

        /* access modifiers changed from: package-private */
        public void set(int index, int value) {
            this.mData[this.mMid + index] = value;
        }

        public void fill(int value) {
            Arrays.fill(this.mData, value);
        }
    }
}
