package net.iceyleagons.icicle.jtext;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JTextChatHoverEventType {

    SHOW_ACHIEVEMENT("show_achievement"),
    SHOW_ITEM("show_item"),
    SHOW_TEXT("show_text");

    private final String type;
}