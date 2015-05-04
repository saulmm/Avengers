package saulmm.avengers.domain;

import javax.inject.Inject;

public class GetCharacerInformationUsecase implements Usecase {

    private String mCharacterId;

    @Inject
    public GetCharacerInformationUsecase(String characterId) {

        this.mCharacterId = characterId;
    }

    @Override
    public void execute() {

    }
}
