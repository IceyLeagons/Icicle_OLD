package net.iceyleagons.icicle.heap;

/**
 * @param <T> generic type
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
public interface HeapItem<T> extends Comparable<T> {

    /**
     * @return the position of the item in the heap
     */
    int getHeapIndex();

    /**
     * Used to set the position of the item in the heap.
     * Should only be called by a {@link Heap} implementation, outside modification will cause the heap to break.
     *
     * @param index the new position
     */
    void setHeapIndex(int index);
}