package saulmm.avengers.model.entities;

import org.junit.Test;
import org.mockito.internal.util.reflection.Whitebox;

import static org.junit.Assert.assertEquals;

public class CharacterTest {

    @Test
    public void testConstructor_name_resource_id() {
        //given
        Character character = new Character("sample-character", 10, 20);

        //then
        assertEquals(20, character.getId());
        assertEquals(10, character.getImageResource());
        assertEquals("sample-character", character.getName());
    }

    @Test
    public void testConstructor_name_imageResource() {
        //given
        Character character = new Character("sample-character", 10);

        //then
        assertEquals(10, character.getImageResource());
        assertEquals("sample-character", character.getName());
    }

    @Test
    public void testGetters(){
        //given
        Character character = new Character("sample-character", 10, 20);
        Thumbnail thumbnail= new Thumbnail();
        Whitebox.setInternalState(thumbnail, "path", "D://a-folder/file");
        Whitebox.setInternalState(thumbnail, "extension", "txt");
        Whitebox.setInternalState(character, "thumbnail", thumbnail);

        //then
        assertEquals("D://a-folder/file.txt", character.getImageUrl());
    }
}