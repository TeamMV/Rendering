package dev.mv.vrender.utils;

public class ConsumableConsumer<T extends Consumable> {

    private ConsumableSystem<T> system;
    private T current;

    public ConsumableConsumer(ConsumableSystem<T> system){
        this.system = system;
    }

    public void consume(String name){
        current = system.get(name);
        consume(current);
    }

    public void consume(int index){
        consume(system.consumableAt(index));
    }

    public void consume(T consumeable) {
        consumeable.OnConsume();
    }

    public T getConsumedConsumeable(){
        return current;
    }

    public T getConsumeable(String name){
        return system.get(name);
    }

}
