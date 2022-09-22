package dev.mv.vrender.utils;

import java.util.*;
import java.util.function.Consumer;

public class ConsumableSystem<T extends Consumable> implements Iterable<T> {
    private Map<String, T> goodies = new HashMap<>();

    private int modCount = 0;
    private ConsumableConsumer<T> consumer;

    public ConsumableSystem(){
        consumer = new ConsumableConsumer<>(this);
    }

    public ConsumableSystem<T> addConsumable(T c){
        goodies.put(c.getName(), c);
        modCount++;

        return this;
    }

    public T get(String name){
        return goodies.get(name);
    }

    public T[] getAll(){
        return (T[]) goodies.values().toArray(new Object[goodies.size()]);
    }

    public ConsumableConsumer<T> getConsumer(){
        return consumer;
    }

    public T consumableAt(int index){
        return goodies.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            final int size = goodies.size();
            int cursor = 0;

            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public T next() {
                return consumableAt(cursor++);
            }
        };
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return Iterable.super.spliterator();
    }
}