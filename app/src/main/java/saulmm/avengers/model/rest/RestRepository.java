/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.rest;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import javax.inject.Inject;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import saulmm.avengers.TestPojo;
import saulmm.avengers.model.rest.utils.MarvelApiUtils;

public class RestRepository  /*Repository*/ {

    private final MarvelApi mMarvelApi;
    public final static int MAX_ATTEMPS = 3;

    String publicKey    = "74129ef99c9fd5f7692608f17abb88f9";
    String privateKey   = "281eb4f077e191f7863a11620fa1865f2940ebeb";

    @Inject
    public RestRepository() {

        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(chain -> {

			String marvelHash = MarvelApiUtils.generateMarvelHash(publicKey, privateKey);
			Request oldRequest = chain.request();

			HttpUrl.Builder authorizedUrlBuilder = oldRequest.httpUrl().newBuilder()
                .scheme(oldRequest.httpUrl().scheme())
                .host(oldRequest.httpUrl().host());

            //for (String pathSegment : oldRequest.httpUrl().pathSegments())
            //    authorizedUrlBuilder.addPathSegment(pathSegment);
			//
            //for (int i = 0; i < oldRequest.httpUrl().queryParameterNames().size(); i++) {
            //    String parameterName = oldRequest.httpUrl().queryParameterName(i);
            //    String parameterValue = oldRequest.httpUrl().queryParameterValue(i);
            //    authorizedUrlBuilder.addQueryParameter(parameterName, parameterValue);
            //}

            authorizedUrlBuilder.addQueryParameter(MarvelApi.PARAM_API_KEY, publicKey)
                .addQueryParameter(MarvelApi.PARAM_TIMESTAMP, MarvelApiUtils.getUnixTimeStamp())
                .addQueryParameter(MarvelApi.PARAM_HASH, marvelHash);

            Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

            Response newRequestResponse = chain.proceed(newRequest);

            if (newRequestResponse.body() != null)
            System.out.println("[DEBUG]" + " RestRepository RestRepository - " +
                ""+newRequestResponse.body().toString());
            else
                System.out.println("[DEBUG]" + " RestRepository RestRepository - " +
                    "Response body null");


			return newRequestResponse;
		});

        Retrofit marvelApiAdapter = new Retrofit.Builder()
            .baseUrl(MarvelApi.END_POINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

        mMarvelApi =  marvelApiAdapter.create(MarvelApi.class);
    }

    public void test() {

        mMarvelApi.getCharacters(20).enqueue(new Callback<TestPojo>() {
            @Override
            public void onResponse(retrofit.Response<TestPojo> response, Retrofit retrofit) {
                if (response.isSuccess()) {

                } else {
                    int statusCode = response.code();
                }


                System.out.println("[DEBUG]" + " RestRepository onResponse - " +
                    "");
            }

            @Override public void onFailure(Throwable t) {
                System.out.println("[DEBUG]" + " RestRepository onFailure - " +
                    "");
            }
        });
    }


	//RequestInterceptor authorizationInterceptor = request -> {

	//};

    //public class RetrofitErrorHandler implements retrofit.ErrorHandler {
	//
    //    @Override
    //    public Throwable handleError(retrofit.RetrofitError cause) {
	//
    //        if (cause.getKind() == retrofit.RetrofitError.Kind.NETWORK) {
	//
    //            if (cause.getCause() instanceof SocketTimeoutException)
    //                return new NetworkTimeOutException();
	//
    //            else if (cause.getCause() instanceof UnknownHostException)
    //                return new NetworkUknownHostException();
	//
    //            else if (cause.getCause() instanceof ConnectException)
    //                return cause.getCause();
	//
    //        } else {
	//
    //            return new NetworkErrorException();
    //        }
	//
    //        return new Exception();
    //    };
    //}
	//
	//@Override
	//public Observable<saulmm.avengers.model.entities.Character> getCharacter(int characterId) {
     //   return mMarvelApi.getCharacterById(characterId);
	//}

    //@Override
    //public Observable<List<Character>> getCharacters(int currentOffset) {
    //    return mMarvelApi.getCharacters(currentOffset);
    //}

    //@Override
    //public Observable<List<Comic>> getCharacterComics(int characterId) {
    //    final String comicsFormat   = "comic";
    //    final String comicsType     = "comic";
	//
    //    return mMarvelApi.getCharacterComics(characterId, comicsFormat, comicsType)
    //        .retry((attemps, error) -> error instanceof SocketTimeoutException && attemps < MAX_ATTEMPS);
    //}

    //public Observable<RetrofitError> emitRetrofitError (RetrofitError retrofitError) {
	//
    //    return Observable.just(retrofitError);
    //}
}
