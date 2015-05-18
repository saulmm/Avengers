package saulmm.avengers.domain;

import rx.Observable;

public interface Usecase<T> {

    Observable<T> execute();
}
