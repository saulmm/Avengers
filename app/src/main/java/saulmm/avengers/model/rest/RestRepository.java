package saulmm.avengers.model.rest;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import saulmm.avengers.model.MarvelApiWrapper;
import saulmm.avengers.model.Repository;

public class RestRepository implements Repository {

    private final MarvelApi mMarvelApi;
    private final Bus mBus;

    String publicKey    = "74129ef99c9fd5f7692608f17abb88f9";
    String privateKey   = "281eb4f077e191f7863a11620fa1865f2940ebeb";

    @Inject
    public RestRepository(Bus bus) {

        RestAdapter marvelApiAdapter = new RestAdapter.Builder()
            .setEndpoint(MarvelApi.END_POINT)
            .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
            .setRequestInterceptor(authorizationInterceptor)
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
    public void getCharacter(String characterId) {

        mMarvelApi.getCharacter(characterId, retrofitCallback);
    }

    private Callback retrofitCallback = new Callback() {

        @Override
        public void success(Object o, Response response) {

            if (o instanceof MarvelApiWrapper) {

                MarvelApiWrapper marvelApi = (MarvelApiWrapper) o;
                mBus.post(o);
            }
        }

        @Override
        public void failure(RetrofitError error) {

            System.out.println("[ERROR]" + " RestRepository, failure (66)- " +
                "error "+error.toString());
        }
    }  ;
}
