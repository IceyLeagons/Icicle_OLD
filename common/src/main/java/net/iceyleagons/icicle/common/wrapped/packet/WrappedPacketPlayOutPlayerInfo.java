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

package net.iceyleagons.icicle.common.wrapped.packet;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.iceyleagons.icicle.common.wrapped.mojang.WrappedGameProfile;
import net.iceyleagons.icicle.common.reflect.Reflections;
import net.iceyleagons.icicle.common.wrapped.WrappedEnumGamemode;
import net.iceyleagons.icicle.common.wrapped.player.WrappedEntityPlayer;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Wrapped representation PacketPlayOutPlayerInfo
 *
 * @author TOTHTOMI
 * @version 1.1.0
 * @since 1.3.3-SNAPSHOT
 */
public class WrappedPacketPlayOutPlayerInfo extends Packet {

    private static final Class<?> mc_packetPlayOutPlayerInfo;
    private static final Class<?> mc_playerInfoData;
    private static final Class<? extends Enum<?>> mc_enumPlayerInfoAction;
    private static final Class<?> iChatBaseComponent;
    private static final Method mc_enumPlayerInfoAction_valueOf;

    private static final Constructor<?> mc_playerInfoDataConstructor;
    private static final Constructor<?> mc_packetPlayOutPlayerInfoConstructor;

    static {
        //TODO (soon tm)
        mc_packetPlayOutPlayerInfo = Reflections.getNormalNMSClass("PacketPlayOutPlayerInfo");
        mc_enumPlayerInfoAction = (Class<? extends Enum<?>>) Reflections.getNormalNMSClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
        mc_playerInfoData = Reflections.getNormalNMSClass("PacketPlayOutPlayerInfo$PlayerInfoData");
        iChatBaseComponent = Reflections.getNormalNMSClass("IChatBaseComponent");

        mc_playerInfoDataConstructor = Reflections.getConstructor(mc_playerInfoData, true, mc_packetPlayOutPlayerInfo, WrappedGameProfile.mojang_gameProfile, int.class,
                WrappedEnumGamemode.mc_wrappedEnumGamemode, iChatBaseComponent);

        mc_packetPlayOutPlayerInfoConstructor = Reflections.getConstructor(mc_packetPlayOutPlayerInfo, true,
                mc_enumPlayerInfoAction, Array.newInstance(WrappedEntityPlayer.entityPlayerClass, 0).getClass());

        mc_enumPlayerInfoAction_valueOf = Reflections.getMethod(mc_enumPlayerInfoAction, "valueOf", true, String.class);
    }

    public WrappedPacketPlayOutPlayerInfo(EnumPlayerInfoAction enumPlayerInfoAction, WrappedEntityPlayer entityPlayer) {
        super(mc_packetPlayOutPlayerInfo, mc_packetPlayOutPlayerInfoConstructor, getInstance(enumPlayerInfoAction, entityPlayer));
    }

    public WrappedPacketPlayOutPlayerInfo(Class<?> clazz, Object instance) {
        super(clazz, instance);
    }

    private static Object getInstance(EnumPlayerInfoAction enumPlayerInfoAction, WrappedEntityPlayer entityPlayer) {
        Object array = Array.newInstance(WrappedEntityPlayer.entityPlayerClass, 1);
        Array.set(array, 0, entityPlayer.getEntityPlayer());
        try {
            return mc_packetPlayOutPlayerInfoConstructor.newInstance(enumPlayerInfoAction.getNmsObject(), array);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequiredArgsConstructor
    @Getter
    public enum EnumPlayerInfoAction {
        ADD_PLAYER(Reflections.invoke(mc_enumPlayerInfoAction_valueOf, Object.class, null, "ADD_PLAYER")),
        UPDATE_GAME_MODE(Reflections.invoke(mc_enumPlayerInfoAction_valueOf, Object.class, null, "UPDATE_GAME_MODE")),
        UPDATE_LATENCY(Reflections.invoke(mc_enumPlayerInfoAction_valueOf, Object.class, null, "UPDATE_LATENCY")),
        UPDATE_DISPLAY_NAME(Reflections.invoke(mc_enumPlayerInfoAction_valueOf, Object.class, null, "UPDATE_DISPLAY_NAME")),
        REMOVE_PLAYER(Reflections.invoke(mc_enumPlayerInfoAction_valueOf, Object.class, null, "REMOVE_PLAYER"));

        private final Object nmsObject;
    }

    public static class PlayerInfoData {

        @Getter
        private final Object nmsObject;

        @SneakyThrows
        public PlayerInfoData(WrappedPacketPlayOutPlayerInfo info, WrappedGameProfile gameProfile, int var, WrappedEnumGamemode.GamemodeType gamemodeType, Object iChatBaseComponent) {
            this.nmsObject = mc_playerInfoDataConstructor.newInstance(info.getPacket(), gameProfile.getNmsObject(), var, gamemodeType.getNmsObject(), iChatBaseComponent);
        }
    }

}
