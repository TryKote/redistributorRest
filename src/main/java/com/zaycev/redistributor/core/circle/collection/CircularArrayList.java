package com.zaycev.redistributor.core.circle.collection;

import com.zaycev.redistributor.core.circle.iterator.CircularIterator;
import javafx.util.Pair;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList с кольцевым итератором
 */
@Service
@Scope("prototype")
public class CircularArrayList implements Iterable<Pair<Integer, Integer>> {
    private List<Integer> data;

    /**
     * Принимает на вход изначальный массив данных
     */
    public CircularArrayList withData(List<Integer> data) {
        this.data = data;
        return this;
    }

    public CircularArrayList build() {
        return this;
    }

    /**
     * Поиск максимума
     * @return возвращает максимум на массиве
     */
    public Pair<Integer, Integer> getMaximum() {
        Pair<Integer, Integer> maximum = new Pair<>(0, data.get(0));
        for (Integer i = 1; i < data.size(); i++) {
            if (maximum.getValue() < data.get(i)) {
                maximum = new Pair<>(i, data.get(i));
            }
        }
        return maximum;
    }

    /**
     * Установка значения по индексу
     * @param index индекс
     * @param value значение
     */
    public void set(Integer index, Integer value) {
        data.set(index, value);
    }

    /**
     * Получение значения по индексу
     * @param index индекс
     * @return значение
     */
    public Integer get(Integer index) {
        return data.get(index);
    }

    /**
     * Возвращает клон текущего состояния массива
     * @return массив
     */
    public List<Integer> getData() {
        List<Integer> cloneData = new ArrayList<>();
        cloneData.addAll(data);
        return cloneData;
    }

    /**
     * RR итератор
     * @return итератор
     */
    @Override
    public CircularIterator<Pair<Integer, Integer>> iterator() {
        return new CircularIterator<Pair<Integer, Integer>>() {
            private Integer pos = -1;

            @Override
            public boolean hasPrevious() {
                return !data.isEmpty();
            }

            @Override
            public Pair<Integer, Integer> previous() {
                pos = previousIndex();
                return new Pair<>(pos, data.get(pos));
            }

            @Override
            public int nextIndex() {
                if(pos >= data.size() - 1) {
                    return 0;
                } else {
                    return pos + 1;
                }
            }

            @Override
            public int previousIndex() {
                if(pos <= 0) {
                    return data.size() - 1;
                } else {
                    return pos - 1;
                }
            }

            @Override
            public void setIndex(Integer index) {
                this.pos = index;
            }

            @Override
            public boolean hasNext() {
                return !data.isEmpty();
            }

            @Override
            public Pair<Integer, Integer> next() {
                pos = nextIndex();
                return new Pair<>(pos, data.get(pos));
            }
        };
    }
}
