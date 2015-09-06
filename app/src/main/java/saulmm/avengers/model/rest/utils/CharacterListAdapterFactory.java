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
import saulmm.avengers.model.entities.Character;

public class CharacterListAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

        boolean rightType = false;

        if (type.getType() instanceof ParameterizedType) {
            Type[] genericArguments = ((ParameterizedType) type.getType())
                .getActualTypeArguments();

            rightType = genericArguments.length == 1
                && genericArguments[0] == Character.class;
        }

        if (!rightType) return null;

        return new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = elementAdapter.read(in);
                return adaptJsonToCharacterList(jsonElement, type);

            }
        }.nullSafe();
    }

    private <T> T adaptJsonToCharacterList(JsonElement jsonElement, TypeToken<T> type) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement marvelDataElement = jsonObject.get("data");
        JsonObject marvelDataObject = marvelDataElement.getAsJsonObject();

        if (marvelDataObject.get("count").getAsInt() > 0) {
            JsonElement marvelResultsElement = marvelDataObject.get("results");
            JsonArray marvelResults = marvelResultsElement.getAsJsonArray();
            return new Gson().fromJson(marvelResults, type.getType());

        } else {
            // TODO Return Empty POJO
        }

        return null;
    }
}
