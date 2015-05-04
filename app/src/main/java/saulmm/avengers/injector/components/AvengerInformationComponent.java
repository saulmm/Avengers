package saulmm.avengers.injector.components;

import dagger.Component;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.injector.ActivityScope;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.views.activities.AvengerDetailActivity;

@ActivityScope @Component(dependencies = AppComponent.class, modules = {AvengerInformationModule.class, ActivityModule.class})
public interface AvengerInformationComponent extends ActivityComponent {

    void inject (AvengerDetailActivity detailActivity);

    GetCharacterInformationUsecase getCharacerInformationUsecase();
    GetCharacterComicsUsecase getCharacterComicsUsecase();
}
