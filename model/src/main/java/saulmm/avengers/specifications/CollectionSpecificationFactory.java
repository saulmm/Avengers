package saulmm.avengers.specifications;

import saulmm.avengers.entities.CollectionItem;


public class CollectionSpecificationFactory {
    public static CollectionSpecification comicSpecification(int characterId) {
        return new CollectionSpecification(CollectionItem.Type.COMIC, characterId);
    }

    public static CollectionSpecification serieSpecification(int characterId) {
        return new CollectionSpecification(CollectionItem.Type.SERIE, characterId);
    }

    public static CollectionSpecification storySpecification(int characterId) {
        return new CollectionSpecification(CollectionItem.Type.STORY, characterId);
    }

    public static CollectionSpecification eventSpecification(int characterId) {
        return new CollectionSpecification(CollectionItem.Type.EVENT, characterId);
    }

    public static CollectionSpecification get(CollectionItem.Type type, int characterId) {
        switch (type) {
            case COMIC: return comicSpecification(characterId);
            case SERIE: return serieSpecification(characterId);
            case STORY: return storySpecification(characterId);
            case EVENT: return eventSpecification(characterId);

            default: return null;
        }
    }
}
