package saulmm.avengers.domain;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import saulmm.avengers.model.Character;
import saulmm.avengers.model.MarvelApiWrapper;
import saulmm.avengers.model.Repository;
import saulmm.avengers.model.rest.MarvelDataWrapper;

public class GetCharacterInformationUsecase implements Usecase {

    private final Repository mRepository;
    private String mCharacterId;
    private Bus mBus;

    @Inject
    public GetCharacterInformationUsecase(String characterId, Bus bus, Repository repository) {

        mBus = bus;
        mCharacterId = characterId;
        mRepository = repository;
    }

    @Subscribe
    public void onMarvelApiInfoReceived (MarvelApiWrapper marvelApiWrapper) {

        MarvelDataWrapper dataWrapper = marvelApiWrapper.getData();

        if (dataWrapper != null && dataWrapper.getCount() == 1) {

            Character character = dataWrapper.getResults().get(0);
            mBus.post(character);
        }
    }

    @Override
    public void execute() {

        mBus.register(this);
        mRepository.getCharacter(mCharacterId);
    }
}
