package saulmm.avengers.domain;

import rx.Subscriber;
import rx.Subscription;

public interface Usecase {

    Subscription execute(Subscriber subscriber);
}
