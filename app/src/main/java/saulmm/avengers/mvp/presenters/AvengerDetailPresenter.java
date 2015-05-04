package saulmm.avengers.mvp.presenters;

import javax.inject.Inject;

import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.mvp.views.AvengersDetailView;
import saulmm.avengers.mvp.views.View;

public class AvengerDetailPresenter implements Presenter {

    private final GetCharacterInformationUsecase mGetCharacterInformationUsecase;
    private AvengersDetailView mAvengersDetailView;

    @Inject
    public AvengerDetailPresenter(GetCharacterInformationUsecase getCharacterInformationUsecase) {

        mGetCharacterInformationUsecase = getCharacterInformationUsecase;
    }

    @Override
    public void attachView(View v) {

        mAvengersDetailView = (AvengersDetailView) v;
        mAvengersDetailView.startLoading();
    }
}
