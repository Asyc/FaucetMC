package org.faucetmc.chat.component;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.MalformedJsonException;
import org.faucetmc.chat.component.impl.ChatComponentText;

import java.lang.reflect.Type;
import java.util.List;

public interface ChatComponent {
	String getText();
	void setText(String text);

	void addSibling(ChatComponent component);
	List<ChatComponent> getSiblings();

	ChatComponent getParent();
	void setParent(ChatComponent parent);

	class Serializer implements JsonSerializer<ChatComponent>, JsonDeserializer<ChatComponent> {
		@Override
		public ChatComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			if(json.isJsonPrimitive()) {
				return new ChatComponentText(json.getAsString());
			}

			JsonObject object = json.getAsJsonObject();
			if(object.has("translate")) {
				//todo : translation component
			} else if (object.has("text")) {
				//todo : text component + styling
			} else {
				throw new JsonParseException("Not a recognized chat component type.");
			}
			return null;
		}

		@Override
		public JsonElement serialize(ChatComponent src, Type typeOfSrc, JsonSerializationContext context) {
			return null;    //todo : this
		}
	}

}
