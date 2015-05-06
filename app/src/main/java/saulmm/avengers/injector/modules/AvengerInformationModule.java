package saulmm.avengers.injector.modules;


import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.injector.Activity;
import saulmm.avengers.model.Repository;

@Module
public class AvengerInformationModule {

    private final String mCharacterId;

    public AvengerInformationModule(String characterId) {

        mCharacterId = characterId;
    }

    @Provides @Activity
    GetCharacterInformationUsecase provideGetCharacterInformationUsecase (Bus bus, Repository repository) {

        return new GetCharacterInformationUsecase(mCharacterId, bus, repository);
    }

    @Provides @Activity
    GetCharacterComicsUsecase provideGetCharacherComicsUsecase () {

        return new GetCharacterComicsUsecase(mCharacterId);
    }
}
