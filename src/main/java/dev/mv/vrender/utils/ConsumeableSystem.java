package dev.mv.vrender.utils;

import java.util.HashMap;
import java.util.Map;

public class ConsumeableSystem<T extends Consumeable> {
    private Map<String, T> goodies = new HashMap<>();
    private ConsumeableConsumer<T> consumer;

    public ConsumeableSystem(){
        consumer = new ConsumeableConsumer<>(this);
    }

    public ConsumeableSystem<T> addConsumeable(T c){
        goodies.put(c.getName(), c);

        return this;
    }

    public T get(String name){
        return goodies.get(name);
    }

    public ConsumeableConsumer<T> getConsumer(){
        return consumer;
    }
}