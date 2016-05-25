package saulmm.avengers.entities.mappers;


import java.util.ArrayList;
import java.util.List;

public final class ListMapper {
    public static <M, T> List<M> map(List<T> parseObjects, Mapper<M, T> mapper) {
        List<M> modelObjects = new ArrayList<>();

        for (T parseObject : parseObjects) {
            M modelObject = mapper.map(parseObject);

            if (modelObject != null)
                modelObjects.add(modelObject);
        }

        return modelObjects;
    }
}

