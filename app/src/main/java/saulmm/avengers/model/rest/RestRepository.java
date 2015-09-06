/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import javax.inject.Inject;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import rx.Observable;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.model.entities.Comic;
import saulmm.avengers.model.repository.Repository;
import saulmm.avengers.model.rest.exceptions.NetworkErrorException;
import saulmm.avengers.model.rest.exceptions.NetworkTimeOutException;
import saulmm.avengers.model.rest.exceptions.NetworkUknownHostException;
import saulmm.avengers.model.rest.utils.CharacterItemAdapterFactory;
import saulmm.avengers.model.rest.utils.CharacterListAdapterFactory;
import saulmm.avengers.model.rest.utils.MarvelApiUtils;

public class RestRepository implements Repository {

    private final MarvelApi mMarvelApi;
    public final static int MAX_ATTEMPS = 3;

    String publicKey    = "74129ef99c9fd5f7692608f17abb88f9";
    String privateKey   = "281eb4f077e191f7863a11620fa1865f2940ebeb";

    @Inject
    public RestRepository() {

        Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new CharacterListAdapterFactory())
            .registerTypeAdapterFactory(new CharacterItemAdapterFactory())
            //.registerTypeAdapterFactory(new ComicListAdapterFactory())
            .create();

        RestAdapter marvelApiAdapter = new RestAdapter.Builder()
            .setEndpoint(MarvelApi.END_POINT)
            .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
            .setRequestInterceptor(authorizationInterceptor)
            .setConverter(new GsonConverter(gson))
            .setErrorHandler(new RetrofitErrorHandler())
            .build();

        mMarvelApi = marvelApiAdapter.create(MarvelApi.class);
    }

    RequestInterceptor authorizationInterceptor = request -> {

		String marvelHash = MarvelApiUtils.generateMarvelHash(publicKey, privateKey);
		request.addQueryParam(MarvelApi.PARAM_API_KEY, publicKey);
		request.addQueryParam(MarvelApi.PARAM_TIMESTAMP, MarvelApiUtils.getUnixTimeStamp());
		request.addQueryParam(MarvelApi.PARAM_HASH, marvelHash);
	};

    public class RetrofitErrorHandler implements retrofit.ErrorHandler {

        @Override
        public Throwable handleError(retrofit.RetrofitError cause) {

            if (cause.getKind() == retrofit.RetrofitError.Kind.NETWORK) {

                if (cause.getCause() instanceof SocketTimeoutException)
                    return new NetworkTimeOutException();

                else if (cause.getCause() instanceof UnknownHostException)
                    return new NetworkUknownHostException();

                else if (cause.getCause() instanceof ConnectException)
                    return cause.getCause();

            } else {

                return new NetworkErrorException();
            }

            return new Exception();
        };
    }

    @Override
    public Observable<saulmm.avengers.model.entities.Character> getCharacter(int characterId) {
        return mMarvelApi.getCharacterById(characterId);
    }

    @Override
    public Observable<List<Character>> getCharacters(int currentOffset) {

        return mMarvelApi.getCharacters(currentOffset);
    }

    @Override
    public Observable<List<Comic>> getCharacterComics(int characterId) {

        final String comicsFormat   = "comic";
        final String comicsType     = "comic";

        return mMarvelApi.getCharacterComics(characterId, comicsFormat, comicsType)
            .retry((attemps, error) -> error instanceof SocketTimeoutException && attemps < MAX_ATTEMPS);
    }

    public Observable<RetrofitError> emitRetrofitError (RetrofitError retrofitError) {

        return Observable.just(retrofitError);
    }
}
