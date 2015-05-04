package saulmm.avengers.injector.modules;


import dagger.Module;
import dagger.Provides;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.injector.ActivityScope;

@Module
public class AvengerInformationModule {

    private final String mCharacterId;

    public AvengerInformationModule(String characterId) {

        mCharacterId = characterId;
    }

    @Provides @ActivityScope
    GetCharacterInformationUsecase provideGetCharacterInformationUsecase () {

        return new GetCharacterInformationUsecase(mCharacterId);
    }

    @Provides @ActivityScope GetCharacterComicsUsecase provideGetCharacherComicsUsecase () {

        return new GetCharacterComicsUsecase(mCharacterId);
    }
}
