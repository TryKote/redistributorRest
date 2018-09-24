package com.zaycev.redistributor.core.circle.iterator;

import java.util.Iterator;

public interface CircularIterator<E> extends Iterator<E> {
    boolean hasPrevious();

    E previous();

    int nextIndex();

    int previousIndex();

    void setIndex(Integer index);
}
