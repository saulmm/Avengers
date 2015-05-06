package saulmm.avengers.domain;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import saulmm.avengers.model.Repository;

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

    @Override
    public void execute() {

        mRepository.getCharacter(mCharacterId);
    }
}
