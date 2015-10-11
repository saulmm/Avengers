/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.inject.Inject;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.model.entities.Comic;
import saulmm.avengers.model.repository.Repository;
import saulmm.avengers.model.rest.exceptions.ServerErrorException;
import saulmm.avengers.model.rest.exceptions.UknownErrorException;
import saulmm.avengers.model.rest.utils.MarvelSigningIterceptor;
import saulmm.avengers.model.rest.utils.deserializers.MarvelResultsCharacterDeserialiser;
import saulmm.avengers.model.rest.utils.deserializers.MarvelResultsComicsDeserialiser;

public class RestRepository implements Repository {

    private final MarvelApi mMarvelApi;
    public final static int MAX_ATTEMPS = 3;

    String publicKey    = "74129ef99c9fd5f7692608f17abb88f9";
    String privateKey   = "281eb4f077e191f7863a11620fa1865f2940ebeb";

    @Inject public RestRepository() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new MarvelSigningIterceptor(publicKey, privateKey));

        Gson gson = new GsonBuilder()
            .registerTypeAdapter(new TypeToken<List<Character>>() {}.getType(), new MarvelResultsCharacterDeserialiser())
            .registerTypeAdapter(new TypeToken<List<Comic>>() {}.getType(), new MarvelResultsComicsDeserialiser())
            .create();

        Retrofit marvelApiAdapter = new Retrofit.Builder()
            .baseUrl(MarvelApi.END_POINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client)
            .build();

        mMarvelApi =  marvelApiAdapter.create(MarvelApi.class);
    }

	@Override
    public Observable<Character> getCharacter(int characterId) {
           return mMarvelApi.getCharacterById(characterId).flatMap(
               characters -> Observable.just(characters.get(0)));
	}

    @Override
    public Observable<List<Character>> getCharacters(int currentOffset) {
        return mMarvelApi.getCharacters(currentOffset)
            .onErrorResumeNext(throwable -> {
                boolean serverError = throwable.getMessage().equals(HttpErrors.SERVER_ERROR);
                return  Observable.error((serverError) ? new ServerErrorException() : new UknownErrorException());
            });
    }

    @Override
    public Observable<List<Comic>> getCharacterComics(int characterId) {
        return mMarvelApi.getCharacterComics(characterId)
            .retry((attemps, error) -> error instanceof SocketTimeoutException
                && attemps < MAX_ATTEMPS);
    }
}
