package dev.mv.vrender.utils;

public class ConsumeableConsumer<T extends Consumeable> {

    private ConsumeableSystem<T> system;

    public ConsumeableConsumer(ConsumeableSystem<T> system){
        this.system = system;
    }

    public void consume(String name){
        consume(system.get(name));
    }

    public void consume(T consumeable) {
        consumeable.OnConsume();
    }

    public T getConsumeable(String name){
        return system.get(name);
    }
}
