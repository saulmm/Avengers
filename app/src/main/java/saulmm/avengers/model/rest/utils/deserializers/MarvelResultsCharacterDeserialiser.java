package saulmm.avengers.model.rest.utils.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import saulmm.avengers.model.entities.Character;

public class MarvelResultsCharacterDeserialiser implements JsonDeserializer<List<Character>> {

    @Override
    public List<Character> deserialize(JsonElement je, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

            Type listType = new TypeToken<List<Character>>() {}.getType();

            JsonElement results = je.getAsJsonObject().get("data")
                .getAsJsonObject().get("results");

            return new Gson().fromJson(results, listType);
        }
    }