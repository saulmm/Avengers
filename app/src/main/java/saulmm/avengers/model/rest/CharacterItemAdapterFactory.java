package saulmm.avengers.model.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;

public class CharacterItemAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        final TypeAdapter<T> customTypeAdapter = new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {

                JsonElement jsonElement = elementAdapter.read(in);

                if (type.getRawType() == saulmm.avengers.model.Character.class)
                   return adaptJsonToCharacter(jsonElement, type);

                if (type.getRawType() == List.class)
                    return adaptJsonToComic(jsonElement, type);


                return delegate.fromJsonTree(jsonElement);
            }

        }.nullSafe();

        return customTypeAdapter;
    }

    private <T> T adaptJsonToCharacter(JsonElement jsonElement, TypeToken<T> type) {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (jsonObject.has("data")) {

            JsonElement marvelDataElement = jsonObject.get("data");
            JsonObject marvelDataObject = marvelDataElement.getAsJsonObject();

            if (marvelDataObject.get("count").getAsInt() == 1) {

                JsonArray marvelResults = marvelDataObject.get("results")
                    .getAsJsonArray();

                JsonElement finalElement = marvelResults.get(0);
                return new Gson().fromJson(finalElement, type.getType());
            }
        }

        return null;
    }

    private <T> T adaptJsonToComic(JsonElement jsonElement, TypeToken<T> type) {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (jsonObject.has("data")) {

            JsonElement marvelDataElement = jsonObject.get("data");
            JsonObject marvelDataObject = marvelDataElement.getAsJsonObject();

            if (marvelDataObject.get("count").getAsInt() > 0) {

                JsonElement marvelResultsElement = marvelDataObject.get("results");
                JsonArray marvelResults = marvelResultsElement.getAsJsonArray();

                JsonElement finalElement = marvelResults.get(0);
                JsonObject comicObject = finalElement.getAsJsonObject();

                if (comicObject.has("pageCount") && comicObject.has("isbn")) {
                    return new Gson().fromJson(marvelResultsElement, type.getType());
                }
            }
        }

        return null;
    }
}
