package saulmm.avengers.repository;


import java.util.List;

import rx.Observable;
import saulmm.avengers.specifications.Specification;

public interface Repository<T> {
    void add(T item);

    void add(Iterable<T> item);

    void remove(T item);

    Observable<List<T>> get(Specification specification);
}
