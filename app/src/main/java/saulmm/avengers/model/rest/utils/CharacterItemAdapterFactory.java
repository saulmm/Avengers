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
import saulmm.avengers.model.entities.Character;

public class CharacterItemAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        if (type.getRawType() != Character.class)
            return null;

        return new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = elementAdapter.read(in);
                return adaptJsonToCharacter(jsonElement, type);
            }

        }.nullSafe();
    }

    private <T> T adaptJsonToCharacter(JsonElement jsonElement, TypeToken<T> type) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement marvelDataElement = jsonObject.get("data");
        JsonObject marvelDataObject = marvelDataElement.getAsJsonObject();

        if (marvelDataObject.get("count").getAsInt() == 1) {

            JsonArray marvelResults = marvelDataObject.get("results")
                .getAsJsonArray();

            JsonElement finalElement = marvelResults.get(0);
            return new Gson().fromJson(finalElement, type.getType());
        }

        return null;
    }
}