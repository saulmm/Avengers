package saulmm.avengers.domain;

import javax.inject.Inject;

public class GetCharacterInformationUsecase implements Usecase {

    private String mCharacterId;

    @Inject
    public GetCharacterInformationUsecase(String characterId) {

        this.mCharacterId = characterId;
    }

    @Override
    public void execute() {

    }
}
