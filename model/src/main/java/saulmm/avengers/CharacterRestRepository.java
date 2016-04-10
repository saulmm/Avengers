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
        // TODO, mess
        if (specification instanceof CharacterByIdSpecification) {
            CharacterByIdSpecification idSpecification = (CharacterByIdSpecification) specification;
            Observable<List<RestCharacter>> restObs = mMarvelApi.getCharacterById(idSpecification.getId());
            return restObs.flatMap(new Func1<List<RestCharacter>, Observable<List<Character>>>() {
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

        return Observable.empty();
    }
}
