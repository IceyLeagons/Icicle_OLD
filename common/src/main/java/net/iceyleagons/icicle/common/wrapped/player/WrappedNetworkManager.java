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

package net.iceyleagons.icicle.common.wrapped.player;

import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.common.reflect.Reflections;

import java.lang.reflect.Field;

/**
 * Wrapped representation of NetworkManager
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
@RequiredArgsConstructor
public class WrappedNetworkManager {

    private static final Class<?> nManagerClass;

    private static final Field mc_channel;

    static {
        nManagerClass = Reflections.getNormalNMSClass("NetworkManager");

        mc_channel = Reflections.getField(nManagerClass, "channel", true);
    }

    private final Object networkManager;

    /**
     * @return the channel of network manager.
     */
    public Channel getChannel() {
        return Reflections.get(mc_channel, Channel.class, networkManager);
    }

}
