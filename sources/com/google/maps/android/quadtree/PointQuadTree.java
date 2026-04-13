package com.google.maps.android.quadtree;

import com.google.maps.android.geometry.Bounds;
import com.google.maps.android.geometry.Point;
import com.google.maps.android.quadtree.PointQuadTree.Item;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PointQuadTree<T extends Item> {
    private static final int MAX_DEPTH = 40;
    private static final int MAX_ELEMENTS = 50;
    private final Bounds mBounds;
    private List<PointQuadTree<T>> mChildren;
    private final int mDepth;
    private Set<T> mItems;

    public interface Item {
        Point getPoint();
    }

    public PointQuadTree(double minX, double maxX, double minY, double maxY) {
        this(new Bounds(minX, maxX, minY, maxY));
    }

    public PointQuadTree(Bounds bounds) {
        this(bounds, 0);
    }

    private PointQuadTree(double minX, double maxX, double minY, double maxY, int depth) {
        this(new Bounds(minX, maxX, minY, maxY), depth);
    }

    private PointQuadTree(Bounds bounds, int depth) {
        this.mChildren = null;
        this.mBounds = bounds;
        this.mDepth = depth;
    }

    public void add(T item) {
        Point point = item.getPoint();
        if (this.mBounds.contains(point.x, point.y)) {
            insert(point.x, point.y, item);
        }
    }

    private void insert(double x, double y, T item) {
        List<PointQuadTree<T>> list = this.mChildren;
        if (list != null) {
            Bounds bounds = this.mBounds;
            if (y < bounds.midY) {
                if (x < bounds.midX) {
                    list.get(0).insert(x, y, item);
                } else {
                    list.get(1).insert(x, y, item);
                }
            } else if (x < bounds.midX) {
                list.get(2).insert(x, y, item);
            } else {
                list.get(3).insert(x, y, item);
            }
        } else {
            if (this.mItems == null) {
                this.mItems = new LinkedHashSet();
            }
            this.mItems.add(item);
            if (this.mItems.size() > 50 && this.mDepth < 40) {
                split();
            }
        }
    }

    private void split() {
        ArrayList arrayList = new ArrayList(4);
        this.mChildren = arrayList;
        Bounds bounds = this.mBounds;
        arrayList.add(new PointQuadTree(bounds.minX, bounds.midX, bounds.minY, bounds.midY, this.mDepth + 1));
        List<PointQuadTree<T>> list = this.mChildren;
        Bounds bounds2 = this.mBounds;
        list.add(new PointQuadTree(bounds2.midX, bounds2.maxX, bounds2.minY, bounds2.midY, this.mDepth + 1));
        List<PointQuadTree<T>> list2 = this.mChildren;
        Bounds bounds3 = this.mBounds;
        list2.add(new PointQuadTree(bounds3.minX, bounds3.midX, bounds3.midY, bounds3.maxY, this.mDepth + 1));
        List<PointQuadTree<T>> list3 = this.mChildren;
        Bounds bounds4 = this.mBounds;
        list3.add(new PointQuadTree(bounds4.midX, bounds4.maxX, bounds4.midY, bounds4.maxY, this.mDepth + 1));
        Set<T> items = this.mItems;
        this.mItems = null;
        for (T item : items) {
            insert(item.getPoint().x, item.getPoint().y, item);
        }
    }

    public boolean remove(T item) {
        Point point = item.getPoint();
        if (!this.mBounds.contains(point.x, point.y)) {
            return false;
        }
        return remove(point.x, point.y, item);
    }

    private boolean remove(double x, double y, T item) {
        List<PointQuadTree<T>> list = this.mChildren;
        if (list != null) {
            Bounds bounds = this.mBounds;
            if (y < bounds.midY) {
                if (x < bounds.midX) {
                    return list.get(0).remove(x, y, item);
                }
                return list.get(1).remove(x, y, item);
            } else if (x < bounds.midX) {
                return list.get(2).remove(x, y, item);
            } else {
                return list.get(3).remove(x, y, item);
            }
        } else {
            Set<T> set = this.mItems;
            if (set == null) {
                return false;
            }
            return set.remove(item);
        }
    }

    public void clear() {
        this.mChildren = null;
        Set<T> set = this.mItems;
        if (set != null) {
            set.clear();
        }
    }

    public Collection<T> search(Bounds searchBounds) {
        List<T> results = new ArrayList<>();
        search(searchBounds, results);
        return results;
    }

    private void search(Bounds searchBounds, Collection<T> results) {
        if (this.mBounds.intersects(searchBounds)) {
            List<PointQuadTree<T>> list = this.mChildren;
            if (list != null) {
                for (PointQuadTree<T> quad : list) {
                    quad.search(searchBounds, results);
                }
            } else if (this.mItems == null) {
            } else {
                if (searchBounds.contains(this.mBounds)) {
                    results.addAll(this.mItems);
                    return;
                }
                for (T item : this.mItems) {
                    if (searchBounds.contains(item.getPoint())) {
                        results.add(item);
                    }
                }
            }
        }
    }
}
