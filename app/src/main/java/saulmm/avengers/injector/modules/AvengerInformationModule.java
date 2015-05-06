package saulmm.avengers.injector.modules;


import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.injector.ActivityScope;

@Module
public class AvengerInformationModule {

    private final String mCharacterId;

    public AvengerInformationModule(String characterId) {

        mCharacterId = characterId;
    }

    @Provides @ActivityScope
    GetCharacterInformationUsecase provideGetCharacterInformationUsecase (Bus bus) {

        return new GetCharacterInformationUsecase(mCharacterId, bus);
    }

    @Provides @ActivityScope GetCharacterComicsUsecase provideGetCharacherComicsUsecase () {

        return new GetCharacterComicsUsecase(mCharacterId);
    }
}
