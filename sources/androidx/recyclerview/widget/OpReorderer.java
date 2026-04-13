package androidx.recyclerview.widget;

import androidx.recyclerview.widget.AdapterHelper;
import java.util.List;

public class OpReorderer {
    final Callback mCallback;

    public interface Callback {
        AdapterHelper.UpdateOp obtainUpdateOp(int i, int i2, int i3, Object obj);

        void recycleUpdateOp(AdapterHelper.UpdateOp updateOp);
    }

    OpReorderer(Callback callback) {
        this.mCallback = callback;
    }

    /* access modifiers changed from: package-private */
    public void reorderOps(List<AdapterHelper.UpdateOp> ops) {
        while (true) {
            int lastMoveOutOfOrder = getLastMoveOutOfOrder(ops);
            int badMove = lastMoveOutOfOrder;
            if (lastMoveOutOfOrder != -1) {
                swapMoveOp(ops, badMove, badMove + 1);
            } else {
                return;
            }
        }
    }

    private void swapMoveOp(List<AdapterHelper.UpdateOp> list, int badMove, int next) {
        AdapterHelper.UpdateOp moveOp = list.get(badMove);
        AdapterHelper.UpdateOp nextOp = list.get(next);
        switch (nextOp.cmd) {
            case 1:
                swapMoveAdd(list, badMove, moveOp, next, nextOp);
                return;
            case 2:
                swapMoveRemove(list, badMove, moveOp, next, nextOp);
                return;
            case 4:
                swapMoveUpdate(list, badMove, moveOp, next, nextOp);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public void swapMoveRemove(List<AdapterHelper.UpdateOp> list, int movePos, AdapterHelper.UpdateOp moveOp, int removePos, AdapterHelper.UpdateOp removeOp) {
        boolean moveIsBackwards;
        AdapterHelper.UpdateOp extraRm = null;
        boolean revertedMove = false;
        int i = moveOp.positionStart;
        int i2 = moveOp.itemCount;
        if (i < i2) {
            moveIsBackwards = false;
            if (removeOp.positionStart == i && removeOp.itemCount == i2 - i) {
                revertedMove = true;
            }
        } else {
            moveIsBackwards = true;
            if (removeOp.positionStart == i2 + 1 && removeOp.itemCount == i - i2) {
                revertedMove = true;
            }
        }
        int i3 = removeOp.positionStart;
        if (i2 < i3) {
            removeOp.positionStart = i3 - 1;
        } else {
            int i4 = removeOp.itemCount;
            if (i2 < i3 + i4) {
                removeOp.itemCount = i4 - 1;
                moveOp.cmd = 2;
                moveOp.itemCount = 1;
                if (removeOp.itemCount == 0) {
                    list.remove(removePos);
                    this.mCallback.recycleUpdateOp(removeOp);
                    return;
                }
                return;
            }
        }
        int i5 = moveOp.positionStart;
        int i6 = removeOp.positionStart;
        if (i5 <= i6) {
            removeOp.positionStart = i6 + 1;
        } else {
            int i7 = removeOp.itemCount;
            if (i5 < i6 + i7) {
                extraRm = this.mCallback.obtainUpdateOp(2, i5 + 1, (i6 + i7) - i5, (Object) null);
                removeOp.itemCount = moveOp.positionStart - removeOp.positionStart;
            }
        }
        if (revertedMove) {
            list.set(movePos, removeOp);
            list.remove(removePos);
            this.mCallback.recycleUpdateOp(moveOp);
            return;
        }
        if (moveIsBackwards) {
            if (extraRm != null) {
                int i8 = moveOp.positionStart;
                if (i8 > extraRm.positionStart) {
                    moveOp.positionStart = i8 - extraRm.itemCount;
                }
                int i9 = moveOp.itemCount;
                if (i9 > extraRm.positionStart) {
                    moveOp.itemCount = i9 - extraRm.itemCount;
                }
            }
            int i10 = moveOp.positionStart;
            if (i10 > removeOp.positionStart) {
                moveOp.positionStart = i10 - removeOp.itemCount;
            }
            int i11 = moveOp.itemCount;
            if (i11 > removeOp.positionStart) {
                moveOp.itemCount = i11 - removeOp.itemCount;
            }
        } else {
            if (extraRm != null) {
                int i12 = moveOp.positionStart;
                if (i12 >= extraRm.positionStart) {
                    moveOp.positionStart = i12 - extraRm.itemCount;
                }
                int i13 = moveOp.itemCount;
                if (i13 >= extraRm.positionStart) {
                    moveOp.itemCount = i13 - extraRm.itemCount;
                }
            }
            int i14 = moveOp.positionStart;
            if (i14 >= removeOp.positionStart) {
                moveOp.positionStart = i14 - removeOp.itemCount;
            }
            int i15 = moveOp.itemCount;
            if (i15 >= removeOp.positionStart) {
                moveOp.itemCount = i15 - removeOp.itemCount;
            }
        }
        list.set(movePos, removeOp);
        if (moveOp.positionStart != moveOp.itemCount) {
            list.set(removePos, moveOp);
        } else {
            list.remove(removePos);
        }
        if (extraRm != null) {
            list.add(movePos, extraRm);
        }
    }

    private void swapMoveAdd(List<AdapterHelper.UpdateOp> list, int move, AdapterHelper.UpdateOp moveOp, int add, AdapterHelper.UpdateOp addOp) {
        int offset = 0;
        int i = moveOp.itemCount;
        int i2 = addOp.positionStart;
        if (i < i2) {
            offset = 0 - 1;
        }
        int i3 = moveOp.positionStart;
        if (i3 < i2) {
            offset++;
        }
        if (i2 <= i3) {
            moveOp.positionStart = i3 + addOp.itemCount;
        }
        int i4 = addOp.positionStart;
        if (i4 <= i) {
            moveOp.itemCount = i + addOp.itemCount;
        }
        addOp.positionStart = i4 + offset;
        list.set(move, addOp);
        list.set(add, moveOp);
    }

    /* access modifiers changed from: package-private */
    public void swapMoveUpdate(List<AdapterHelper.UpdateOp> list, int move, AdapterHelper.UpdateOp moveOp, int update, AdapterHelper.UpdateOp updateOp) {
        AdapterHelper.UpdateOp extraUp1 = null;
        AdapterHelper.UpdateOp extraUp2 = null;
        int i = moveOp.itemCount;
        int i2 = updateOp.positionStart;
        if (i < i2) {
            updateOp.positionStart = i2 - 1;
        } else {
            int i3 = updateOp.itemCount;
            if (i < i2 + i3) {
                updateOp.itemCount = i3 - 1;
                extraUp1 = this.mCallback.obtainUpdateOp(4, moveOp.positionStart, 1, updateOp.payload);
            }
        }
        int i4 = moveOp.positionStart;
        int i5 = updateOp.positionStart;
        if (i4 <= i5) {
            updateOp.positionStart = i5 + 1;
        } else {
            int i6 = updateOp.itemCount;
            if (i4 < i5 + i6) {
                int remaining = (i5 + i6) - i4;
                extraUp2 = this.mCallback.obtainUpdateOp(4, i4 + 1, remaining, updateOp.payload);
                updateOp.itemCount -= remaining;
            }
        }
        list.set(update, moveOp);
        if (updateOp.itemCount > 0) {
            list.set(move, updateOp);
        } else {
            list.remove(move);
            this.mCallback.recycleUpdateOp(updateOp);
        }
        if (extraUp1 != null) {
            list.add(move, extraUp1);
        }
        if (extraUp2 != null) {
            list.add(move, extraUp2);
        }
    }

    private int getLastMoveOutOfOrder(List<AdapterHelper.UpdateOp> list) {
        boolean foundNonMove = false;
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).cmd != 8) {
                foundNonMove = true;
            } else if (foundNonMove) {
                return i;
            }
        }
        return -1;
    }
}
