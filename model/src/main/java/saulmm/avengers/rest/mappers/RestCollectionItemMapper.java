package saulmm.avengers.rest.mappers;


import saulmm.avengers.entities.Character;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.entities.mappers.Mapper;
import saulmm.avengers.rest.entities.RestCharacter;
import saulmm.avengers.rest.entities.RestCollectionItem;

public class RestCollectionItemMapper implements Mapper<CollectionItem, RestCollectionItem> {

    @Override
    public CollectionItem map(RestCollectionItem restCollectionItem) {
        CollectionItem collectionItem = new CollectionItem();

        collectionItem.setId(restCollectionItem.getId());
        collectionItem.setTitle(restCollectionItem.getTitle());
        collectionItem.setDescription(restCollectionItem.getDescription());
        collectionItem.setImageUrl(restCollectionItem.getThumbnail().getImageUrl());

        return collectionItem;
    }
}
