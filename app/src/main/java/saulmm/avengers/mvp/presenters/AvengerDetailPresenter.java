package saulmm.avengers.mvp.presenters;

import android.content.Intent;

import javax.inject.Inject;

import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.mvp.views.AvengersDetailView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.activities.AvengersListActivity;

public class AvengerDetailPresenter implements Presenter {

    private final GetCharacterInformationUsecase mGetCharacterInformationUsecase;
    private AvengersDetailView mAvengersDetailView;
    private String mAvengerCharacterId;
    private Intent mIntent;

    @Inject
    public AvengerDetailPresenter(GetCharacterInformationUsecase getCharacterInformationUsecase) {

        mGetCharacterInformationUsecase = getCharacterInformationUsecase;
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
}
