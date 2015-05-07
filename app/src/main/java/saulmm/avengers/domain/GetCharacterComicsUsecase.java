package saulmm.avengers.domain;

import com.squareup.otto.Bus;

import javax.inject.Inject;

import saulmm.avengers.model.Repository;

public class GetCharacterComicsUsecase implements Usecase {

    private final Bus mBus;
    private final Repository mRepository;
    private int mCharacterId;

    @Inject public GetCharacterComicsUsecase(int characterId,
                                             Bus bus,
                                             Repository repository) {

        mBus = bus;
        mCharacterId = characterId;
        mRepository = repository;
    }


    @Override
    public void execute() {

        mBus.register(this);
        mRepository.getCharacterComics(mCharacterId);
    }
}
