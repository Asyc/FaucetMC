package org.faucetmc.chat.component.style;

public enum ChatFormatting {
    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    OBFUSCATED('k', true),
    BOLD('l', true),
    STRIKETHROUGH('m', true),
    UNDERLINE('n', true),
    ITALIC('o', true),
    RESET('r');

    public static final String SECTION_MARK = "\u00a7";

    private final char formattingCode;
    private final boolean fancyStyling;

    private final String controlString;

    ChatFormatting(char formatCode) {
        this(formatCode, false);
    }

    ChatFormatting(char formatCode, boolean fancyStyling) {
        this.formattingCode = formatCode;
        this.fancyStyling = fancyStyling;
        this.controlString = SECTION_MARK + formatCode;
    }

    public char getFormattingCode() {
        return formattingCode;
    }

    public boolean isFancyStyling() {
        return fancyStyling;
    }

    public String getControlString() {
        return controlString;
    }
}
