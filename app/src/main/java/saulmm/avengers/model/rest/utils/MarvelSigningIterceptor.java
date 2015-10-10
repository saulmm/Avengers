package saulmm.avengers.model.rest.utils;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import saulmm.avengers.model.rest.MarvelApi;

public class MarvelSigningIterceptor implements Interceptor {
	private final String mApiKey;
	private final String mApiSecret;

	public MarvelSigningIterceptor(String apiKey, String apiSecret) {
		mApiKey = apiKey;
		mApiSecret = apiSecret;
	}

	@Override public Response intercept(Chain chain) throws IOException {
		String marvelHash = MarvelApiUtils.generateMarvelHash(mApiKey, mApiSecret);
		Request oldRequest = chain.request();

		HttpUrl.Builder authorizedUrlBuilder = oldRequest.httpUrl().newBuilder()
			.scheme(oldRequest.httpUrl().scheme())
			.host(oldRequest.httpUrl().host());

		authorizedUrlBuilder.addQueryParameter(MarvelApi.PARAM_API_KEY, mApiKey)
			.addQueryParameter(MarvelApi.PARAM_TIMESTAMP, MarvelApiUtils.getUnixTimeStamp())
			.addQueryParameter(MarvelApi.PARAM_HASH, marvelHash);

		Request newRequest = oldRequest.newBuilder()
			.method(oldRequest.method(), oldRequest.body())
			.url(authorizedUrlBuilder.build())
			.build();

		System.out.println("[DEBUG]" + " Retrofit -->" +
		    ""+authorizedUrlBuilder.build().url().toString());

		return chain.proceed(newRequest);
	}
}

