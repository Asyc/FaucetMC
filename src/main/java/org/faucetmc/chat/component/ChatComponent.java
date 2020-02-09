package org.faucetmc.chat.component;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.faucetmc.chat.component.impl.ChatComponentText;
import org.faucetmc.chat.component.impl.ChatComponentTranslation;
import org.faucetmc.chat.component.style.ChatStyle;
import org.faucetmc.chat.event.ComponentClickEvent;
import org.faucetmc.chat.event.ComponentHoverEvent;

import java.lang.reflect.Type;
import java.util.List;

public interface ChatComponent {
    String getText();

    ChatComponent setText(String text);

    ChatComponent addSibling(ChatComponent component);

    List<ChatComponent> getSiblings();

    ChatStyle getStyle();

    ChatComponent setStyle(ChatStyle style);

    ComponentClickEvent getClickEvent();

    ChatComponent setClickEvent(ComponentClickEvent clickEvent);

    ComponentHoverEvent getHoverEvent();

    ChatComponent setHoverEvent(ComponentHoverEvent hoverEvent);

    class Serializer implements JsonSerializer<ChatComponent>, JsonDeserializer<ChatComponent> {
        @Override
        public ChatComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonPrimitive()) {
                return new ChatComponentText(json.getAsString());
            }

            JsonObject object = json.getAsJsonObject();
            ChatStyle style = ChatStyle.readFromJSON(object);

            ChatComponent component;
            if (object.has("translate")) {
                component = new ChatComponentTranslation(object.getAsJsonPrimitive("translate").getAsString()).setStyle(style);
            } else if (object.has("text")) {
                component = new ChatComponentText(object.getAsJsonPrimitive("text").getAsString()).setStyle(style);
            } else {
                throw new JsonParseException("Not a recognized chat component type.");
            }

            if (object.has("extra")) {
                JsonArray array = object.getAsJsonArray("extra");
                for (int i = 0; i < array.size(); i++) {
                    component.addSibling(deserialize(array.get(i), null, context));
                }
            }
            return component;
        }

        @Override
        public JsonElement serialize(ChatComponent src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            if ((src.getStyle() == null || !src.getStyle().isStyled()) && src instanceof ChatComponentText) {
                return new JsonPrimitive(src.getText());
            }
            String key = src instanceof ChatComponentTranslation ? "translate" : "text";
            object.addProperty(key, src.getText());
            src.getStyle().appendToJSON(object);

            if (src.getClickEvent() != null) {
                object.add("clickEvent", context.serialize(src.getClickEvent()));
            }

            if (src.getHoverEvent() != null) {
                object.add("hoverEvent", context.serialize(src.getHoverEvent()));
            }

            if (src.getSiblings() != null) {
                JsonArray array = new JsonArray();
                for (ChatComponent component : src.getSiblings()) {
                    array.add(serialize(component, null, context));
                }
                object.add("extra", array);
            }

            return object;
        }
    }

}
