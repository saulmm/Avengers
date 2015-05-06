package saulmm.avengers.mvp.presenters;

import android.content.Intent;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.model.Character;
import saulmm.avengers.mvp.views.AvengersDetailView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.activities.AvengersListActivity;

public class AvengerDetailPresenter implements Presenter {

    private final GetCharacterInformationUsecase mGetCharacterInformationUsecase;
    private final Bus mMainBus;
    private AvengersDetailView mAvengersDetailView;
    private String mAvengerCharacterId;
    private Intent mIntent;

    @Inject
    public AvengerDetailPresenter(GetCharacterInformationUsecase getCharacterInformationUsecase, Bus mainBus) {

        mGetCharacterInformationUsecase = getCharacterInformationUsecase;
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

        mAvengerCharacterId = mIntent.getExtras().getString(
            AvengersListActivity.EXTRA_CHARACTER_ID);

        mAvengersDetailView.startLoading();

        mGetCharacterInformationUsecase.execute();
    }

    @Subscribe
    public void onAvengerDetailReceived (Character character) {

        mAvengersDetailView.stopLoading();
        mAvengersDetailView.showAvengerName(character.getName());
        mAvengersDetailView.showAvengerBio(character.getDescription());
        mAvengersDetailView.showAvengerImage(character.getImageUrl());
    }

    @Override
    public void onStop() {

        mMainBus.unregister(this);
    }
}
