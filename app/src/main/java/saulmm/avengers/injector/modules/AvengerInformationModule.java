package saulmm.avengers.injector.modules;


import dagger.Module;
import dagger.Provides;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.injector.Activity;
import saulmm.avengers.model.Repository;

@Module
public class AvengerInformationModule {

    private final int mCharacterId;

    public AvengerInformationModule(int characterId) {

        mCharacterId = characterId;
    }

    @Provides @Activity
    GetCharacterInformationUsecase provideGetCharacterInformationUsecase (Repository repository) {

        return new GetCharacterInformationUsecase(mCharacterId, repository);
    }

    @Provides @Activity GetCharacterComicsUsecase provideGetCharacherComicsUsecase (Repository repository) {

        return new GetCharacterComicsUsecase(mCharacterId, repository);
    }
}
