package org.faucetmc.chat.component.impl;

import org.faucetmc.chat.component.ChatComponent;

import java.util.LinkedList;
import java.util.List;

public class ChatComponentText implements ChatComponent {

	private ChatComponent parent;

	private String text;
	private List<ChatComponent> siblings;

	public ChatComponentText(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void addSibling(ChatComponent component) {
		if(siblings == null) siblings = new LinkedList<>();
		component.setParent(this);
		siblings.add(component);
	}

	@Override
	public List<ChatComponent> getSiblings() {
		return siblings;
	}

	@Override
	public ChatComponent getParent() {
		return parent;
	}

	@Override
	public void setParent(ChatComponent parent) {
		this.parent = parent;
	}
}
