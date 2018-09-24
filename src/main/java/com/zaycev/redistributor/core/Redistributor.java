package com.zaycev.redistributor.core;

import com.zaycev.redistributor.core.circle.collection.CircularArrayList;
import com.zaycev.redistributor.core.circle.iterator.CircularIterator;
import javafx.util.Pair;
import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Запускалка таска
 */
@Service
public class Redistributor {
    private ObjectFactory<CircularArrayList> arrayListObjectFactory;
    private final Logger log = LoggerFactory.getLogger(Redistributor.class.getName());

    /**
     * Выполняет рассчет
     * @param sourceData начальный массив данных
     * @return результат в виде списка: шагов до обнаружения бесконечного цикла, длина цикла
     */
    public List<Integer> doIt(List<Integer> sourceData) {
        CircularArrayList data = createCircularArrayList(sourceData);

        Set<List<Integer>> calculated = new LinkedHashSet<>();

        List<Integer> redistributed;
        while (true) {
            Pair<Integer, Integer> maximum = data.getMaximum();
            Integer maximumValue = maximum.getValue();
            Integer maximumIndex = maximum.getKey();
            List<Integer> cut = data.getData();

            if (calculated.contains(cut)) {
                redistributed = cut;
                break;
            }
            calculated.add(cut);

            CircularIterator<Pair<Integer, Integer>> it = data.iterator();

            data.set(maximumIndex, 0);
            it.setIndex(maximumIndex);

            while (maximumValue > 0) {
                final Integer nextIndex = it.nextIndex();
                Integer nextValue = data.get(nextIndex);

                nextValue++;
                maximumValue--;

                data.set(nextIndex, nextValue);

                it.next();
            }

        }

        Integer loopLength = 0;
        Boolean flag = false;

        for (List<Integer> cut : calculated) {
            //Вероятно, немного быстрее стандартной реализации equals()
            //ввиду отсутсвия instanceof. В данной ситуации тип гарантирован.
            if (ListUtils.isEqualList(cut, redistributed)) {
                flag = true;
            }
            if (flag) {
                loopLength++;
            }
        }

        List<Integer> answers = new LinkedList<>();
        answers.add(calculated.size());
        answers.add(loopLength);

        log.info("Steps to found infinity-loop: " + calculated.size());
        log.info("Loop length: " + loopLength);
        return answers;
    }

    /**
     * Фабричный метод, создает экземпляр CircularArrayList с заданными параметрами
     * @param data содержимое CircularArrayList
     * @return CircularArrayList
     */
    public CircularArrayList createCircularArrayList(List<Integer> data) {
        return arrayListObjectFactory.getObject()
                .withData(data)
                .build();
    }

    @Autowired
    public void setArrayListObjectFactory(ObjectFactory<CircularArrayList> arrayListObjectFactory) {
        this.arrayListObjectFactory = arrayListObjectFactory;
    }
}
