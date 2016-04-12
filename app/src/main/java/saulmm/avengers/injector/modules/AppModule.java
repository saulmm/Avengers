/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.List;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.BuildConfig;
import saulmm.avengers.CharacterDatasource;
import saulmm.avengers.rest.Endpoint;
import saulmm.avengers.rest.MarvelApi;
import saulmm.avengers.rest.MarvelAuthorizer;
import saulmm.avengers.rest.RestDataSource;
import saulmm.avengers.rest.entities.RestCharacter;
import saulmm.avengers.rest.entities.RestCollectionItem;
import saulmm.avengers.rest.utils.deserializers.MarvelResultsDeserializer;
import saulmm.avengers.rest.utils.interceptors.MarvelSigningInterceptor;

@Module
public class AppModule {
    public static String END_POINT       = "http://gateway.marvel.com/";
    private final AvengersApplication mAvengersApplication;

    public AppModule(AvengersApplication avengersApplication) {
        this.mAvengersApplication = avengersApplication;
    }

    @Provides @Singleton
    AvengersApplication provideAvengersApplicationContext() {
        return mAvengersApplication;
    }

    @Provides
    MarvelAuthorizer provideMarvelAuthorizer() {
        return new MarvelAuthorizer(BuildConfig.MARVEL_PUBLIC_KEY, BuildConfig.MARVEL_PRIVATE_KEY);
    }

    @Provides
    Endpoint provideRestEndpoint() {
        return new Endpoint("http://gateway.marvel.com/");
    }

    @Provides @Singleton
    CharacterDatasource provideDataRepository(RestDataSource restDataSource) {
        return restDataSource; }

    @Provides @Named("executor_thread")
    Scheduler provideExecutorThread() {
        return Schedulers.newThread();
    }

    @Provides @Named("ui_thread")
    Scheduler provideUiThread() {
        return AndroidSchedulers.mainThread();
    }

    @Provides @Singleton
    public MarvelApi provideMarvelApi(MarvelAuthorizer marvelAuthorizer) {
        OkHttpClient client = new OkHttpClient();

        MarvelSigningInterceptor signingIterceptor =
                new MarvelSigningInterceptor(
                        marvelAuthorizer.getApiClient(),
                        marvelAuthorizer.getApiSecret());

        HttpLoggingInterceptor logginInterceptor = new HttpLoggingInterceptor();
        logginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client.interceptors().add(signingIterceptor);
        client.interceptors().add(logginInterceptor);

        Gson customGsonInstance = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<RestCharacter>>() {}.getType(),
                        new MarvelResultsDeserializer<RestCharacter>())

                .registerTypeAdapter(new TypeToken<List<RestCollectionItem>>() {}.getType(),
                        new MarvelResultsDeserializer<RestCollectionItem>())

                .create();

        Retrofit marvelApiAdapter = new Retrofit.Builder()
                .baseUrl(END_POINT)
                .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return marvelApiAdapter.create(MarvelApi.class);
    }
}
