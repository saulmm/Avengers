package saulmm.avengers.rest.utils.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import saulmm.avengers.entities.Comic;

public class MarvelResultsComicsDeserialiser implements JsonDeserializer<List<Comic>> {

    @Override
    public List<Comic> deserialize(JsonElement je, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

            Type listType = new TypeToken<List<Comic>>() {}.getType();

            JsonElement data = je.getAsJsonObject().get("data");
            JsonElement results = je.getAsJsonObject().get("results");
            JsonArray resultsArray = results.getAsJsonArray();
            JsonElement comicsObject = resultsArray.get(0);
            JsonElement items = comicsObject.getAsJsonObject().get("items");

            return new Gson().fromJson(items, listType);
        }
    }