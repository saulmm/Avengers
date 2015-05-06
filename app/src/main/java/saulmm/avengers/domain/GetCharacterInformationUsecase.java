package saulmm.avengers.domain;

import com.squareup.otto.Bus;

import javax.inject.Inject;

public class GetCharacterInformationUsecase implements Usecase {

    private String mCharacterId;
    private Bus mBus;

    @Inject
    public GetCharacterInformationUsecase(String characterId, Bus bus) {

        mBus = bus;
        mCharacterId = characterId;
    }

    @Override
    public void execute() {

    }
}
