package org.faucetmc.chat.event;

import org.faucetmc.chat.component.ChatComponent;

public class ComponentHoverEvent {

    private Action action;
    private ChatComponent value;

    public ComponentHoverEvent(Action action, ChatComponent value) {
        this.action = action;
        this.value = value;
        //todo : handle json nbt with show_item
    }

    public enum Action {
        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY,
        SHOW_ACHIEVEMENT
    }

}
