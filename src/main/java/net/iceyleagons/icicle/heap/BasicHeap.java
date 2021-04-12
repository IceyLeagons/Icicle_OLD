package net.iceyleagons.icicle.heap;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Array;
import java.util.Objects;

public class BasicHeap<T extends HeapItem<T>> implements Heap<T> {

    private final T[] array;
    @Getter
    private int itemCount;

    @SuppressWarnings({ "unchecked" })
    public BasicHeap(@NonNull Class<T> clazz, int heapSize) {
        Preconditions.checkState(heapSize >= 0, new IllegalStateException("Heap size must be larger or equal to 0!"));
        this.itemCount = 0;
        this.array = (T[]) Array.newInstance(clazz, heapSize);
    }

    private void swapItems(T a, T b) {
        array[a.getHeapIndex()] = b;
        array[b.getHeapIndex()] = a;

        int ai = a.getHeapIndex();
        a.setHeapIndex(b.getHeapIndex());
        b.setHeapIndex(ai);
    }

    private void sortUpward(T item) {
        int pi = (item.getHeapIndex() - 1)/2;

        while (true) {
            T parent = array[pi];
            if (item.compareTo(parent) > 0) {
                swapItems(item, parent);
            } else break;

            pi = (item.getHeapIndex()-1)/2;
        }
    }

    private void sortDownward(T item) {
        while (true) {
            int left = item.getHeapIndex() * 2 + 1;
            int right = item.getHeapIndex() * 2 + 2;
            int swap = 0;

            if (left < itemCount) {
                swap = left;

                if (right < itemCount) {
                    if (array[left].compareTo(array[right]) < 0) {
                        swap = right;
                    }
                }

                if (item.compareTo(array[swap]) < 0) {
                    swapItems(item, array[swap]);
                } else return;
            } else return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(T item) {
        item.setHeapIndex(itemCount);
        array[itemCount] = item;

        sortUpward(item);

        itemCount += 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T item) {
        return Objects.equals(array[item.getHeapIndex()], item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(T item) {
        sortUpward(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T pop() {
        T firstItem = array[0];
        itemCount -= 1;

        array[0] = array[itemCount];
        array[0].setHeapIndex(0);
        sortDownward(array[0]);

        return firstItem;
    }

}
