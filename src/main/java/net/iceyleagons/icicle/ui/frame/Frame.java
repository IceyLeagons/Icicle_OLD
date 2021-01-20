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

package net.iceyleagons.icicle.ui.frame;

import lombok.Getter;
import net.iceyleagons.icicle.item.InventoryUtils;
import net.iceyleagons.icicle.ui.components.Component;
import net.iceyleagons.icicle.ui.components.ComponentTemplate;
import net.iceyleagons.icicle.ui.components.VariableSizeComponent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.2.0-SNAPSHOT
 */
public class Frame {

    @Getter
    private final Map<Integer, Map.Entry<Component, ComponentTemplate>> components; //<Slot, Entry<Component,ComponentTemplate>>
    private final List<Integer> takenPlaces;

    public Frame() {
        this.components = new HashMap<>();
        this.takenPlaces = new ArrayList<>();
    }

    /**
     * Renders the frame to an inventory
     *
     * @param inventory the {@link Inventory} to render to
     */
    public void render(Inventory inventory) {
        ItemStack[] itemStacks = new ItemStack[inventory.getSize()];
        for (int i = 0; i < inventory.getSize(); i++) {
            Map.Entry<Component, ComponentTemplate> componentComponentTemplateEntry = components.get(i);
            if (componentComponentTemplateEntry == null) continue;

            Component component = componentComponentTemplateEntry.getKey();
            ComponentTemplate componentTemplate = componentComponentTemplateEntry.getValue();
            if (!componentTemplate.renderAllowed()) continue;


            ItemStack[][] toRender;
            if (componentTemplate instanceof VariableSizeComponent)
                toRender = new ItemStack[((VariableSizeComponent) componentTemplate).getWidth()]
                        [((VariableSizeComponent) componentTemplate).getHeight()];
            else
                toRender = new ItemStack[component.width()][component.height()];

            componentTemplate.render(toRender);

            for (int j = 0; j < toRender.length; j++) {
                for (int k = 0; k < toRender[j].length; k++) {
                    int x = j + 1;
                    int y = k + 1;
                    int fromUtils = InventoryUtils.calculateSlotFromXY(x, y);
                    int slotToAdd = i + fromUtils;
                    itemStacks[slotToAdd] = toRender[j][k];
                }
            }
        }
        inventory.setContents(itemStacks);
    }


    /**
     * Registers a component
     *
     * @param componentTemplate the component
     * @param x                 the X coordinate
     * @param y                 the Y coordinate
     * @return a {@link CompletableFuture} of {@link Void}
     * @throws IllegalArgumentException if the component does not annotate {@link Component}
     * @throws IllegalStateException    if the frame does not have room for thec component (width, height)
     */
    public CompletableFuture<Void> registerComponent(ComponentTemplate componentTemplate, int x, int y) throws IllegalArgumentException, IllegalStateException {
        return CompletableFuture.supplyAsync(() -> {
            if (!componentTemplate.getClass().isAnnotationPresent(Component.class))
                throw new IllegalArgumentException("Supplied ComponentTemplate does not annotate Component");

            Component component = componentTemplate.getClass().getAnnotation(Component.class);
            componentTemplate.setXY(x, y);
            int slot = InventoryUtils.calculateSlotFromXY(x, y);
            int width = component.width();
            int height = component.height();


            checkSpace(x, y, width, height).thenAccept(result -> {
                if (!result) {
                    throw new IllegalStateException("Frame does not have room for this component!");
                }
                components.put(slot, new AbstractMap.SimpleEntry<>(component, componentTemplate));
            });
            return null;
        });
    }

    /**
     * Used to fire an onclick event for a slot. This will calculate which component's onclick should be invoked
     *
     * @param slot the clicked slot
     * @return a {@link CompletableFuture} of {@link Void}
     */
    public CompletableFuture<ComponentTemplate> onClick(int slot) {
        return CompletableFuture.supplyAsync(() -> {
            for (Map.Entry<Component, ComponentTemplate> componentEntry : components.values()) {
                ComponentTemplate componentTemplate = componentEntry.getValue();
                Component component = componentEntry.getKey();
                final int startX = componentTemplate.getX();
                final int startY = componentTemplate.getY();

                boolean match = false;
                for (int x = startX; x < component.width() + startX; x++) {
                    for (int y = startY; y < component.height() + startY; y++) {

                        int toCheck = InventoryUtils.calculateSlotFromXY(x, y);
                        match = (slot == toCheck);

                        if (match) return componentTemplate;
                    }
                }
            }
            return null;
        });
    }

    /**
     * Used to check whether a component has space or not
     *
     * @param startingX the X coordinate of the component
     * @param startingY the Y coordinate of the component
     * @param width     the width of the component
     * @param height    the height of the component
     * @return a {@link CompletableFuture} of {@link Boolean} if true the component has space
     */
    private CompletableFuture<Boolean> checkSpace(int startingX, int startingY, int width, int height) {
        return CompletableFuture.supplyAsync(() -> {
            List<Integer> toBeTaken = new ArrayList<>();
            for (int x = startingX; x < width + startingX; x++) {
                for (int y = startingY; y < height + startingY; y++) {
                    int slot = InventoryUtils.calculateSlotFromXY(x, y);
                    if (takenPlaces.contains(slot)) {
                        return false;
                    }
                    toBeTaken.add(slot);
                }
            }

            takenPlaces.addAll(toBeTaken);
            return true;
        });
    }


}
