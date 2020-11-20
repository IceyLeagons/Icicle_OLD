package net.iceyleagons.icicle.jtext;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JTextChatFormat {
    BOLD("bold"), //&l
    UNDERLINED("underlined"), //&n
    STRIKETHROUGH("strikethrough"), //&m
    ITALIC("italic"), //&o
    OBFUSCATED("obfuscated"); //&k

    private final String format;
}
