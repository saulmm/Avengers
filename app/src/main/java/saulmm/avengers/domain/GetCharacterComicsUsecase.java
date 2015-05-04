package saulmm.avengers.domain;

import javax.inject.Inject;

public class GetCharacterComicsUsecase implements Usecase {

    private String mCharacterId;

    @Inject
    public GetCharacterComicsUsecase(String characterId) {

        this.mCharacterId = characterId;
    }

    @Override
    public void execute() {

    }
}
