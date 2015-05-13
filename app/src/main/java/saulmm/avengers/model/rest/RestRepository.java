package saulmm.avengers.model.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;
import saulmm.avengers.model.Comic;
import saulmm.avengers.model.Repository;

public class RestRepository implements Repository {

    private final MarvelApi mMarvelApi;
    private final Bus mBus;

    String publicKey    = "74129ef99c9fd5f7692608f17abb88f9";
    String privateKey   = "281eb4f077e191f7863a11620fa1865f2940ebeb";

    @Inject
    public RestRepository(Bus bus) {

        Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new CharacterItemAdapterFactory())
            .create();

        RestAdapter marvelApiAdapter = new RestAdapter.Builder()
            .setEndpoint(MarvelApi.END_POINT)
            .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
            .setRequestInterceptor(authorizationInterceptor)
            .setConverter(new GsonConverter(gson))
            .build();

        mMarvelApi = marvelApiAdapter.create(MarvelApi.class);
        mBus = bus;
    }

    RequestInterceptor authorizationInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade request) {

            String marvelHash = MarvelApiUtils.generateMarvelHash(publicKey, privateKey);
            request.addQueryParam(MarvelApi.PARAM_API_KEY, publicKey);
            request.addQueryParam(MarvelApi.PARAM_TIMESTAMP, MarvelApiUtils.getUnixTimeStamp());
            request.addQueryParam(MarvelApi.PARAM_HASH, marvelHash);
        }
    };

    @Override
    public Observable<saulmm.avengers.model.Character> getCharacter(int characterId) {
        return mMarvelApi.getCharacter(characterId);
    }

    @Override
    public Observable<List<Comic>> getCharacterComics(int characterId) {

        final String comicsFormat   = "comic";
        final String comicsType     = "comic";

        return mMarvelApi.getCharacterComics(characterId, comicsFormat, comicsType);
    }

    private Callback retrofitCallback = new Callback() {

        @Override
        public void success(Object o, Response response) {

            if (o instanceof saulmm.avengers.model.Character)
                mBus.post(o);

            if (o instanceof ArrayList) {

                ArrayList comicsList = (ArrayList) o;

                if (!comicsList.isEmpty() && comicsList.get(0) instanceof Comic)
                    mBus.post(new ComicsWrapper(comicsList));
            }
        }

        @Override
        public void failure(RetrofitError error) {

            System.out.println("[ERROR]" + " RestRepository, failure (66)- " +
                "error "+error.toString());
        }
    } ;
}
