package saulmm.avengers.domain;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import saulmm.avengers.model.Character;
import saulmm.avengers.model.Repository;

public class GetCharacterInformationUsecase implements Usecase {

    private final Repository mRepository;
    private int mCharacterId;
    private Bus mBus;

    @Inject
    public GetCharacterInformationUsecase(int characterId, Bus bus, Repository repository) {

        mBus = bus;
        mCharacterId = characterId;
        mRepository = repository;
    }

    @Override
    public void execute() {

        mBus.register(this);
        mRepository.getCharacter(mCharacterId);
    }

    @Subscribe
    public void onCharacterReceived (Character character) {

        mBus.unregister(this);
    }
}
