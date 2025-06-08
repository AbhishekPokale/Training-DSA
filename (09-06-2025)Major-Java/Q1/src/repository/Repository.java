package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Repository<T> {
    List<T> items = new ArrayList<>();
    public void add(T item){
        items.add(item);
    }

    public void remove(T item){
        items.remove(item);
    }

    public List<T> getAll(){
        return new ArrayList<>(items);
    }

    public Optional<T> find(Predicate<T> predicate){
        return items.stream().filter(predicate).findFirst();
    }

    public List<T> filter(Predicate<T> predicate){
        return items.stream().filter(predicate).toList();
    }

}
