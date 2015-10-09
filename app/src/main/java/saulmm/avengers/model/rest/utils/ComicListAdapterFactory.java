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
import java.util.List;
import saulmm.avengers.model.entities.Comic;

public class ComicListAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {
        Type[] genericArguments = ((ParameterizedType) type.getType())
            .getActualTypeArguments();

        if (genericArguments.length == 1) {
            if (genericArguments[0] == Comic.class) {
                System.out.println("[DEBUG]" + " ComicListAdapterFactory create - " +
                    "");
            }
        }

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

                if (type.getRawType() == List.class) {

                    // The List is parametrised, i.e List<Character>
                    if (type.getType() instanceof ParameterizedType) {

                        Type[] genericArguments = ((ParameterizedType) type.getType())
                            .getActualTypeArguments();

                        if (genericArguments.length == 1) {
                            if (genericArguments[0] == Comic.class) {

                                isEmpty(jsonElement);
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
