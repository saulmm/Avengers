package saulmm.avengers.rest.utils.interceptors;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

import saulmm.avengers.rest.utils.MarvelApiUtils;

public class MarvelSigningInterceptor implements Interceptor {
	public static String PARAM_API_KEY   = "apikey";
	public static String PARAM_HASH      = "hash";
	public static String PARAM_TIMESTAMP = "ts";

	private final String mApiKey;
	private final String mApiSecret;

	public MarvelSigningInterceptor(String apiKey, String apiSecret) {
		mApiKey = apiKey;
		mApiSecret = apiSecret;
	}

	@Override public Response intercept(Chain chain) throws IOException {
		String marvelHash = MarvelApiUtils.generateMarvelHash(mApiKey, mApiSecret);
		Request oldRequest = chain.request();

		HttpUrl.Builder authorizedUrlBuilder = oldRequest.httpUrl().newBuilder()
			.scheme(oldRequest.httpUrl().scheme())
			.host(oldRequest.httpUrl().host());

		authorizedUrlBuilder.addQueryParameter(PARAM_API_KEY, mApiKey)
			.addQueryParameter(PARAM_TIMESTAMP, MarvelApiUtils.getUnixTimeStamp())
			.addQueryParameter(PARAM_HASH, marvelHash);

		Request newRequest = oldRequest.newBuilder()
			.method(oldRequest.method(), oldRequest.body())
			.url(authorizedUrlBuilder.build())
			.build();

		return chain.proceed(newRequest);
	}
}

