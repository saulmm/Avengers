package saulmm.avengers.mvp.presenters;

import android.content.Intent;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.model.Character;
import saulmm.avengers.model.Comic;
import saulmm.avengers.model.rest.ComicsWrapper;
import saulmm.avengers.mvp.views.AvengersDetailView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.activities.AvengersListActivity;

public class AvengerDetailPresenter implements Presenter {

    private AvengersDetailView mAvengersDetailView;

    private final Bus mMainBus;
    private int mAvengerCharacterId;
    private final GetCharacterInformationUsecase mGetCharacterInformationUsecase;
    private final GetCharacterComicsUsecase mGetCharacterComicsUsecase;
    private Intent mIntent;

    @Inject
    public AvengerDetailPresenter(Bus mainBus,
                                  GetCharacterInformationUsecase getCharacterInformationUsecase,
                                  GetCharacterComicsUsecase getCharacterComicsUsecase) {

        mGetCharacterInformationUsecase = getCharacterInformationUsecase;
        mGetCharacterComicsUsecase = getCharacterComicsUsecase;
        mMainBus = mainBus;
    }

    @Override
    public void onStart() {

        mMainBus.register(this);
    }

    @Override
    public void attachView(View v) {

        mAvengersDetailView = (AvengersDetailView) v;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

        mIntent = intent;
    }

    public void initializePresenter() {

        mAvengerCharacterId = mIntent.getExtras().getInt(
            AvengersListActivity.EXTRA_CHARACTER_ID);

        mAvengersDetailView.startLoading();

        mGetCharacterInformationUsecase.execute();
        mGetCharacterComicsUsecase.execute();
    }

    @Subscribe
    public void onAvengerDetailReceived (Character character) {

        mAvengersDetailView.stopLoading();
        mAvengersDetailView.showAvengerName(character.getName());
        mAvengersDetailView.showAvengerBio(character.getDescription());

        if (character.getImageUrl() != null)
            mAvengersDetailView.showAvengerImage(character.getImageUrl());
    }

    @Subscribe
    public void onAvengerComicsReceived (ComicsWrapper comicsWrapper) {

        ArrayList<Comic> comics = comicsWrapper.getmComics();

        for (Comic comic : comics) {
            mAvengersDetailView.addComic(comic);
        }
    }

    @Override
    public void onStop() {

        mMainBus.unregister(this);
    }
}
