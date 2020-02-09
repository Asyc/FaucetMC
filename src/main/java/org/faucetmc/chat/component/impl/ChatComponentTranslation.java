package org.faucetmc.chat.component.impl;

import org.faucetmc.chat.component.ChatComponent;
import org.faucetmc.chat.component.style.ChatStyle;
import org.faucetmc.chat.event.ComponentClickEvent;
import org.faucetmc.chat.event.ComponentHoverEvent;

import java.util.LinkedList;
import java.util.List;

public final class ChatComponentTranslation implements ChatComponent {

    private transient ChatStyle style;

    private transient String translate;

    private transient List<ChatComponent> siblings;

    private transient ComponentClickEvent clickEvent;
    private transient ComponentHoverEvent hoverEvent;

    public ChatComponentTranslation(String translate) {
        this.translate = translate;
    }

    @Override
    public String getText() {
        return translate;
    }

    @Override
    public ChatComponent setText(String text) {
        this.translate = text;
        return this;
    }

    @Override
    public ChatComponent addSibling(ChatComponent component) {
        if (siblings == null) siblings = new LinkedList<>();
        siblings.add(component);
        return this;
    }

    @Override
    public List<ChatComponent> getSiblings() {
        return siblings;
    }

    @Override
    public ChatStyle getStyle() {
        if (style == null) style = new ChatStyle();
        return style;
    }

    @Override
    public ChatComponent setStyle(ChatStyle style) {
        this.style = style;
        return this;
    }

    @Override
    public ComponentClickEvent getClickEvent() {
        return clickEvent;
    }

    @Override
    public ChatComponent setClickEvent(ComponentClickEvent clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    @Override
    public ComponentHoverEvent getHoverEvent() {
        return hoverEvent;
    }

    @Override
    public ChatComponent setHoverEvent(ComponentHoverEvent hoverEvent) {
        this.hoverEvent = hoverEvent;
        return this;
    }
}
