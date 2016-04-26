package saulmm.avengers;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import saulmm.avengers.entities.Character;
import saulmm.avengers.rest.MarvelApi;
import saulmm.avengers.rest.entities.RestCharacter;
import saulmm.avengers.rest.mappers.RestCharacterMapper;
import saulmm.avengers.specifications.IdSpecification;
import saulmm.avengers.specifications.PaginationSpecification;
import saulmm.avengers.specifications.Specification;

public class CharacterRestRepository implements Repository<Character> {
    private MarvelApi mMarvelApi;

    @Inject
    public CharacterRestRepository(MarvelApi mMarvelApi) {
        this.mMarvelApi = mMarvelApi;
    }

    @Override
    public void add(Character item) {
        // No available on Rest
    }

    @Override
    public void add(Iterable<Character> item) {
        // No available on rest
    }

    @Override
    public void remove(Character item) {
        // No available on Rest
    }

    @Override
    public Observable<List<Character>> get(Specification specification) {
        if (specification instanceof IdSpecification) {
            IdSpecification idSpecification = (IdSpecification) specification;
            return charactersById(idSpecification);

        } else if (specification instanceof PaginationSpecification) {
            PaginationSpecification paginationSpecification = (PaginationSpecification) specification;
            return charactersByPage(paginationSpecification);
        }

        return Observable.empty();
    }

    public Observable<List<Character>> charactersById(IdSpecification specification) {
        return mMarvelApi.getCharacterById(specification.getId())
            .flatMap(new Func1<List<RestCharacter>, Observable<List<Character>>>() {

            @Override
            public Observable<List<Character>> call(List<RestCharacter> restCharacters) {
                List<Character> characterList = new ArrayList<>(restCharacters.size());
                for (RestCharacter restCharacter : restCharacters) {
                    characterList.add(new RestCharacterMapper().map(restCharacter));
                }

                return Observable.just(characterList);
            }
        });
    }

    public Observable<List<Character>> charactersByPage(PaginationSpecification specification) {
        return mMarvelApi.getCharacters(specification.getOffset())
            .flatMap(new Func1<List<RestCharacter>, Observable<List<Character>>>() {

                @Override
                public Observable<List<Character>> call(List<RestCharacter> restCharacters) {
                    List<Character> characterList = new ArrayList<>(restCharacters.size());

                    for (RestCharacter restCharacter : restCharacters) {
                        characterList.add(new RestCharacterMapper().map(restCharacter));
                    }

                    return Observable.just(characterList);
                }
            });
    }

}
