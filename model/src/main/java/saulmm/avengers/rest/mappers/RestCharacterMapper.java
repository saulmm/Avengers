package saulmm.avengers.rest.mappers;


import saulmm.avengers.entities.Character;
import saulmm.avengers.entities.mappers.Mapper;
import saulmm.avengers.rest.entities.RestCharacter;

public class RestCharacterMapper implements Mapper<Character, RestCharacter> {

    @Override
    public Character map(RestCharacter restCharacter) {
        Character domainCharacter = new Character();

        domainCharacter.setName(restCharacter.getName());
        domainCharacter.setBio(restCharacter.getDescription());
        domainCharacter.setImageUrl(restCharacter.getImageUrl());
        domainCharacter.setId(restCharacter.getId());

        return domainCharacter;
    }
}
