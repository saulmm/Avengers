package saulmm.avengers.model.rest.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({MarvelApiUtils.class})
public class MarvelApiUtilsTest {

    @Test
    public void testGenerateMarvelHash() throws Exception {
        //First Scenario
        //given
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.currentTimeMillis()).thenReturn(123456789L);

        String publicKey = "publicKey";
        String privateKey = "privateKey";
        String expectedHash = "e83e1d42948f2ae14b0b6a52ec46fdd1";

        //when
        String marvelHash = MarvelApiUtils.generateMarvelHash(publicKey, privateKey);

        //then
        assertEquals(expectedHash, marvelHash);

        //Second Scenario
        //given
        publicKey = "some-random-value-for-public-key-1234567890";
        privateKey = "random-value-for-private-key-12345";
        expectedHash = "5f41b56a03e90879c7f0949201ae8cbb";

        //when
        marvelHash = MarvelApiUtils.generateMarvelHash(publicKey, privateKey);

        //then
        assertEquals(expectedHash, marvelHash);

        //given
        publicKey = null;
        privateKey = null;
        expectedHash = "ca1db823f29651ee81eb735dcf7c4d6a";

        //when
        marvelHash = MarvelApiUtils.generateMarvelHash(publicKey, privateKey);

        //then
        assertEquals(expectedHash, marvelHash);
    }

    @Test
    public void testGetUnixTimeStamp() throws Exception {
        //given
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.currentTimeMillis()).thenReturn(123456789L);

        //when
        String timestamp = MarvelApiUtils.getUnixTimeStamp();
        String expectedTimeStamp = "123456";

        //then
        assertEquals("Time stamp must match expectation", expectedTimeStamp, timestamp);
    }
}