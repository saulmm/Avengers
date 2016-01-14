package saulmm.avengers.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.model.repository.Repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Schedulers.class, AndroidSchedulers.class, rx.Observable.class})
public class GetCharacterInformationUsecaseTest {

    private Repository repository;

    private int characterId;

    @Before
    public void setup() {
        repository = mock(Repository.class);
        characterId = 10;
    }

    @Test
    public void testConstructor() {
        //when
        GetCharacterInformationUsecase getCharacterInformationUsecase = new GetCharacterInformationUsecase(characterId, repository);

        //then
        assertEquals(repository, Whitebox.getInternalState(getCharacterInformationUsecase, "mRepository"));
        assertEquals(characterId, Whitebox.getInternalState(getCharacterInformationUsecase, "mCharacterId"));
    }
}