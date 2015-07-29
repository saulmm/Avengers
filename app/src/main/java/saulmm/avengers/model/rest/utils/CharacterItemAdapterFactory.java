/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.model.rest.utils;

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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.model.entities.Comic;

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

                if (type.getRawType() == Character.class)
                   return adaptJsonToCharacter(jsonElement, type);

                if (type.getRawType() == List.class) {

                    // The List is parametrised, i.e List<Character>
                    if (type.getType() instanceof ParameterizedType) {

                        Type[] genericArguments = ((ParameterizedType) type.getType())
                            .getActualTypeArguments();

                        if (genericArguments.length == 1) {

                            if (genericArguments[0] == Character.class)
                                return adaptJsonToCharacterList(jsonElement, type);

                             else if (genericArguments[0] == Comic.class) {

                                if(isEmpty(elementAdapter.read(in))) {
                                    return (T) new ArrayList<Comic>();
                                }

                                return adaptJsonToComic(jsonElement, type);
                            }
                        }
                    }


                }


                return delegate.fromJsonTree(jsonElement);
            }

        }.nullSafe();

        return customTypeAdapter;
    }

    private boolean isEmpty (JsonElement jsonElement) {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (jsonObject.has("data")) {

            JsonElement marvelDataElement = jsonObject.get("data");
            JsonObject marvelDataObject = marvelDataElement.getAsJsonObject();

            if (marvelDataObject.get("count").getAsInt() == 0) {
                return true;
            }
        }

        return false;
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

    private <T> T adaptJsonToCharacterList(JsonElement jsonElement, TypeToken<T> type) {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        if (jsonObject.has("data")) {

            JsonElement marvelDataElement = jsonObject.get("data");
            JsonObject marvelDataObject = marvelDataElement.getAsJsonObject();

            if (marvelDataObject.get("count").getAsInt() > 0) {

                JsonElement marvelResultsElement = marvelDataObject.get("results");
                JsonArray marvelResults = marvelResultsElement.getAsJsonArray();
                return new Gson().fromJson(marvelResults, type.getType());
            }
        }

        return null;
    }
}
