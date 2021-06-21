package net.iceyleagons.icicle.heap.impl;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.iceyleagons.icicle.heap.Heap;
import net.iceyleagons.icicle.heap.HeapItem;
import net.iceyleagons.icicle.utils.Asserts;

import java.lang.reflect.Array;
import java.util.Objects;

@SuppressWarnings({ "unchecked" })
public class BasicHeap<T extends HeapItem<T>> implements Heap<T> {

    private T[] array = null;
    @Getter
    private int itemCount;
    private final int initialSize;

    public BasicHeap(int heapSize) {
        Preconditions.checkState(heapSize >= 0, new IllegalStateException("Heap size must be larger or equal to 0!"));
        this.itemCount = 0;
        this.initialSize = heapSize;
    }

    private T[] generateArray(Class<T> clazz, int size) {
        return (T[]) Array.newInstance(clazz.getComponentType(), size);
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
            int swap;

            if (left < itemCount) {
                swap = left;

                if (right < itemCount) {
                    if (( array[left]).compareTo(array[right]) < 0) {
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
    @SuppressWarnings({"unchecked"})
    @Override
    public void add(T item) {
        //Will only run once
        //we do this, because we think it's better than casting an Object[] all the time
        if (array == null) array = generateArray((Class<T>) item.getClass(), initialSize);

        if (itemCount+1 >= array.length) {
            T[] newArray = generateArray((Class<T>) item.getClass(), array.length + 10);
            System.arraycopy(array, 0, newArray, 0, array.length);
            this.array = newArray;
        }

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
        Asserts.isTrue(array.length > 0, "Cannot pop an empty heap.");

        T firstItem = array[0];
        itemCount -= 1;

        array[0] = array[itemCount];
        array[0].setHeapIndex(0);
        sortDownward(array[0]);

        return firstItem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return itemCount;
    }

}
