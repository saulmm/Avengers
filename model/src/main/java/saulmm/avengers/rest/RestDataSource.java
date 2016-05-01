/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.rest;

import java.util.List;
import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;
import saulmm.avengers.entities.Character;
import saulmm.avengers.entities.mappers.ListMapper;
import saulmm.avengers.rest.entities.RestCollectionItem;
import saulmm.avengers.rest.entities.RestCharacter;
import saulmm.avengers.CharacterDatasource;
import saulmm.avengers.rest.exceptions.ServerErrorException;
import saulmm.avengers.rest.exceptions.UknownErrorException;
import saulmm.avengers.rest.mappers.RestCharacterMapper;

public class RestDataSource implements CharacterDatasource {
    public static String END_POINT       = "http://gateway.marvel.com/";
    public static String PARAM_API_KEY   = "apikey";
    public static String PARAM_HASH      = "hash";
    public static String PARAM_TIMESTAMP = "ts";

    private final MarvelApi mMarvelApi;

    @Inject
    public RestDataSource(MarvelApi marvelApi) {
        mMarvelApi = marvelApi;
    }

	@Override
    public Observable<Character> getCharacter(final int characterId) {
        return mMarvelApi.getCharacterById(characterId)
            .flatMap(new Func1<List<RestCharacter>, Observable<Character>>() {
                @Override
                public Observable<Character> call(List<RestCharacter> restCharacters) {
                    RestCharacter restCharacter = restCharacters.get(0);
                    return Observable.just(new RestCharacterMapper().map(restCharacter));
                }
            });
	}

    @Override
    public Observable<List<Character>> getCharacters(int currentOffset) {
        return mMarvelApi.getCharacters(currentOffset)
            .flatMap(new Func1<List<RestCharacter>, Observable<List<Character>>>() {
                @Override
                public Observable<List<Character>> call(List<RestCharacter> restCharacters) {
                    return Observable.just(ListMapper.map(restCharacters, new RestCharacterMapper()));
                }
            })
            .onErrorResumeNext(new Func1<Throwable, Observable<? extends List<Character>>>() {
                @Override
                public Observable<? extends List<Character>> call(Throwable throwable) {
                    boolean serverError = throwable.getMessage().equals(HttpErrors.SERVER_ERROR);
                    return Observable.error((serverError) ? new ServerErrorException() : new UknownErrorException());
                }
            });
    }

    @Override
    public Observable<List<RestCollectionItem>> getCharacterCollection(int characterId, String type) {
        return null;
    }
}
