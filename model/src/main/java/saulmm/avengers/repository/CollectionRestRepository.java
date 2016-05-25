package saulmm.avengers.repository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import saulmm.avengers.entities.Character;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.rest.MarvelApi;
import saulmm.avengers.rest.entities.RestCollectionItem;
import saulmm.avengers.rest.mappers.RestCollectionItemMapper;
import saulmm.avengers.specifications.CollectionSpecification;
import saulmm.avengers.specifications.Specification;


public class CollectionRestRepository implements Repository<CollectionItem> {
    private MarvelApi mMarvelApi;

    @Inject
    public CollectionRestRepository(MarvelApi marvelApi) {
        this.mMarvelApi = marvelApi;
    }

    @Override
    public void add(CollectionItem item) {
        // No available on rest
    }

    @Override
    public void add(Iterable<CollectionItem> item) {
        // No available on rest
    }

    @Override
    public void remove(CollectionItem item) {
        // No available on rest
    }

    @Override
    public Observable<List<CollectionItem>> get(Specification specification) {
        if (specification instanceof CollectionSpecification) {
            CollectionSpecification collectionSpecification = (CollectionSpecification) specification;

            return mMarvelApi.getCharacterCollection(
                collectionSpecification.getCharacterId(), collectionSpecification.getType().key())
                .flatMap(new Func1<List<RestCollectionItem>, Observable<RestCollectionItem>>() {
                    @Override
                    public Observable<RestCollectionItem> call(List<RestCollectionItem> restCollectionItems) {
                        return Observable.from(restCollectionItems);
                    }
                })
                .flatMap(new Func1<RestCollectionItem, Observable<CollectionItem>>() {
                    @Override
                    public Observable<CollectionItem> call(RestCollectionItem restCollectionItem) {
                        return Observable.just(new RestCollectionItemMapper().map(restCollectionItem));
                    }

                }).toList();
        }

        return null;
    }
}
