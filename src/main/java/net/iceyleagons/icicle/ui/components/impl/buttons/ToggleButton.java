/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicle.ui.components.impl.buttons;

import lombok.Getter;
import net.iceyleagons.icicle.ui.GUIClickEvent;
import net.iceyleagons.icicle.ui.components.Component;
import net.iceyleagons.icicle.ui.components.impl.Button;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Self explanatory
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.2-SNAPSHOT
 */
@Component(
        id = "toggle_button",
        width = 1, //Takes up one slot
        height = 1 // /\
)
public class ToggleButton extends Button {

    @Getter
    private boolean state = false;
    private final Consumer<GUIClickEvent> onClicked;

    /**
     * @param off placeholder when the state is false
     * @param on placeholder when the state is true
     * @param onToggled a consumer to be run when the button got clicked
     */
    public ToggleButton(ItemStack off, ItemStack on, Consumer<GUIClickEvent> onToggled) {
        super(off);

        onClicked = guiClickEvent -> {
            state = !state;
            if (state) {
                this.setPlaceholder(on);
            } else {
                this.setPlaceholder(off);
            }

            guiClickEvent.getGUI().update();
            onToggled.accept(guiClickEvent);
        };
    }

    @Override
    public Consumer<GUIClickEvent> onClicked() {
        return this.onClicked;
    }
}
