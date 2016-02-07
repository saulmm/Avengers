package saulmm.avengers.rest.utils.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;

public class MarvelResultsDeserializer<T> implements JsonDeserializer<List<T>> {

    @Override
    public List<T> deserialize(JsonElement je, Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {

            JsonElement results = je.getAsJsonObject().get("data")
                .getAsJsonObject().get("results");

            return new Gson().fromJson(results, typeOfT);
        }
    }