package org.faucetmc.chat.component.style;

import com.google.gson.JsonObject;

public final class ChatStyle {

	private Boolean bold;
	private Boolean italic;
	private Boolean underlined;
	private Boolean strikethrough;
	private Boolean obfuscated;

	private ChatFormatting color;
	private String insertion;

	public static ChatStyle readFromJSON(JsonObject in) {
		Boolean bold = null, italic = null, underlined = null, strikethrough = null, obfuscated = null;
		ChatFormatting color = null;
		String insertion = null;

		if (in.has("bold")) bold = in.getAsJsonPrimitive("bold").getAsBoolean();
		if (in.has("italic")) italic = in.getAsJsonPrimitive("italic").getAsBoolean();
		if (in.has("underlined")) underlined = in.getAsJsonPrimitive("underlined").getAsBoolean();
		if (in.has("strikethrough")) strikethrough = in.getAsJsonPrimitive("strikethrough").getAsBoolean();
		if (in.has("obfuscated")) obfuscated = in.getAsJsonPrimitive("obfuscated").getAsBoolean();
		if (in.has("color")) color = ChatFormatting.valueOf(in.getAsJsonPrimitive("color").getAsString().toUpperCase());
		if (in.has("insertion")) insertion = in.getAsJsonPrimitive("insertion").getAsString();
		return bold != null || italic != null || underlined != null || strikethrough != null || obfuscated != null || color != null || insertion != null ?
				new ChatStyle().setBold(bold).setItalic(italic).setUnderlined(underlined).setStrikethrough(strikethrough).setObfuscated(obfuscated).setColor(color).setInsertion(insertion) : null;
	}

	public boolean isBold() {
		return bold != null && bold;
	}

	public ChatStyle setBold(Boolean bold) {
		this.bold = bold;
		return this;
	}

	public boolean isItalic() {
		return italic != null && italic;
	}

	public ChatStyle setItalic(Boolean italic) {
		this.italic = italic;
		return this;
	}

	public boolean isUnderlined() {
		return underlined != null && underlined;
	}

	public ChatStyle setUnderlined(Boolean underlined) {
		this.underlined = underlined;
		return this;
	}

	public boolean isStrikethrough() {
		return strikethrough != null && strikethrough;
	}

	public ChatStyle setStrikethrough(Boolean strikethrough) {
		this.strikethrough = strikethrough;
		return this;
	}

	public boolean getObfuscated() {
		return obfuscated != null && obfuscated;
	}

	public ChatStyle setObfuscated(Boolean obfuscated) {
		this.obfuscated = obfuscated;
		return this;
	}

	public ChatFormatting getColor() {
		return color == null ? ChatFormatting.WHITE : color;
	}

	public ChatStyle setColor(ChatFormatting color) {
		this.color = color;
		return this;
	}

	public String getInsertion() {
		return insertion;
	}

	public ChatStyle setInsertion(String insertion) {
		this.insertion = insertion;
		return this;
	}

	public boolean isStyled() {
		return underlined != null || strikethrough != null || obfuscated != null || color != null || insertion != null;
	}

	public void appendToJSON(JsonObject out) {
		if (!isStyled()) return;
		if (bold != null) out.addProperty("bold", bold);
		if (italic != null) out.addProperty("italic", italic);
		if (underlined != null) out.addProperty("underlined", underlined);
		if (strikethrough != null) out.addProperty("strikethrough", strikethrough);
		if (obfuscated != null) out.addProperty("obfuscated", obfuscated);
		if (color != null) out.addProperty("color", color.name());
		if (insertion != null) out.addProperty("insertion", insertion);
	}
}
