/*
 * MIT License
 *
 * Copyright (c) 2021 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
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

package net.iceyleagons.icicle.misc;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author TOTHTOMI
 */

public class Animation {

    private final List<Frame> frames = new ArrayList<>();
    private final JavaPlugin javaPlugin;
    private boolean inProgress = false;

    private BukkitTask bukkitTask;
    private Iterator<Frame> frameIterator;
    private int counter;
    private Frame currentFrame;

    public Animation(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public void start(boolean async) {
        if (inProgress) throw new IllegalStateException("The animation is already in progress!");
        inProgress = true;

        frameIterator = frames.iterator();
        bukkitTask = async ? Bukkit.getScheduler().runTaskTimerAsynchronously(javaPlugin, this::tick, 0L, 1L) :
                Bukkit.getScheduler().runTaskTimer(javaPlugin, this::tick, 0L, 1L);
    }

    private void tick() {
        if (!frameIterator.hasNext()) {
            bukkitTask.cancel();
            currentFrame = null;
            counter = 0;
            return;
        }
        if (currentFrame == null) currentFrame = frameIterator.next();

        if (currentFrame.getDuration() <= counter) {
            currentFrame = null;
            counter = 0;
        } else if (counter == 0) {
            currentFrame.getRunnable().run();
            counter++;
        } else {
            counter++;
        }
    }

    public void terminate() {
        bukkitTask.cancel();
        inProgress = false;
    }

    public void addFrame(Frame frame) {
        frames.add(frame);
    }

    @RequiredArgsConstructor
    @Getter
    public static class Frame {
        private final int duration;
        private final Runnable runnable;
    }

}
