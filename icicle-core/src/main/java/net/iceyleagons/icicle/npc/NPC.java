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

package net.iceyleagons.icicle.npc;

import lombok.Getter;
import net.iceyleagons.icicle.utils.web.Mojang;
import net.iceyleagons.icicle.utils.web.WebUtils;
import net.iceyleagons.icicleold.wrapped.WrappedDedicatedServer;
import net.iceyleagons.icicle.wrapping.bukkit.WrappedCraftWorld;
import net.iceyleagons.icicle.wrapping.mojang.WrappedGameProfile;
import net.iceyleagons.icicle.wrapping.mojang.WrappedProperty;
import net.iceyleagons.icicle.wrapping.packet.WrappedPacketPlayOutEntity;
import net.iceyleagons.icicle.wrapping.packet.WrappedPacketPlayOutEntityDestroy;
import net.iceyleagons.icicle.wrapping.packet.WrappedPacketPlayOutNamedEntitySpawn;
import net.iceyleagons.icicle.wrapping.packet.WrappedPacketPlayOutPlayerInfo;
import net.iceyleagons.icicle.wrapping.player.WrappedEntityPlayer;
import net.iceyleagons.icicle.wrapping.player.WrappedPlayerInteractManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

import java.net.URL;
import java.util.Objects;
import java.util.UUID;

/**
 * Used for creating NPCs.
 * This is handled with packets, after relogging you need to send the packets again!
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.3.3-SNAPSHOT
 */
@Getter
public class NPC {

    private final String name;
    private final String skinName;
    private NPCListener npcListener;
    private WrappedEntityPlayer wrappedEntityPlayer;

    public NPC(String name, String skinName) {
        this.name = name;
        this.skinName = skinName;
    }

    public static void spawnNPCPacket(WrappedEntityPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            spawnNPCPacket(npc, player);
        }
    }

    public static void spawnNPCPacket(WrappedEntityPlayer npc, Player player) {

        WrappedPacketPlayOutPlayerInfo wrappedPacketPlayOutPlayerInfo = new WrappedPacketPlayOutPlayerInfo(WrappedPacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc);
        wrappedPacketPlayOutPlayerInfo.send(player);//.getHandle().getPlayerConnection().sendPacket(wrappedPacketPlayOutPlayerInfo.getPacket());

        WrappedPacketPlayOutNamedEntitySpawn namedEntitySpawn = new WrappedPacketPlayOutNamedEntitySpawn(npc);
        namedEntitySpawn.send(player);

        WrappedPacketPlayOutEntity.PacketPlayOutEntityLook packetPlayOutEntityLook = new WrappedPacketPlayOutEntity.PacketPlayOutEntityLook(npc.getId(), (byte) ((npc.getYaw() * 256 / 360)), (byte) ((npc.getPitch() * 256 / 360)), false);
        packetPlayOutEntityLook.send(player);

    }

    private static String[] getSkin(String name) {
        try {
            String uuid = Objects.requireNonNull(Mojang.getUUID(name)).toString();
            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            String response = WebUtils.readURL(url2);

            assert response != null;
            JSONObject property = new JSONObject(response).getJSONArray("properties").getJSONObject(0);
            String texture = property.getString("value");
            String signature = property.getString("signature");
            return new String[]{texture, signature};
        } catch (Exception e) {
            return null;
        }
    }

    public static void removeNPC(WrappedEntityPlayer npc) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            removeNPC(player, npc);
        }
    }

    public static void removeNPC(Player player, WrappedEntityPlayer npc) {
        WrappedPacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new WrappedPacketPlayOutEntityDestroy(npc.getId());
        packetPlayOutEntityDestroy.send(player);

    }

    public static NPCListener get(JavaPlugin javaPlugin) {
        NPCListener npcListener = new NPCListener();
        javaPlugin.getServer().getPluginManager().registerEvents(npcListener, javaPlugin);
        return npcListener;
    }

    public void setupNPCListener(NPCListener npcListener) {
        this.npcListener = npcListener;
    }

    public void kill() {
        if (npcListener != null) npcListener.removeNPC(this);
        NPC.removeNPC(getWrappedEntityPlayer());
    }

    public WrappedEntityPlayer setup(Location loc) {
        String[] skin = getSkin(skinName);
        if (skin == null) {
            throw new IllegalStateException("Could not get skin for " + skinName);
        }

        WrappedDedicatedServer dedicatedServer = WrappedDedicatedServer.from(Bukkit.getServer());
        WrappedCraftWorld world = new WrappedCraftWorld(loc.getWorld());
        WrappedGameProfile gameProfile = new WrappedGameProfile(UUID.randomUUID(), this.name);

        WrappedEntityPlayer npc = new WrappedEntityPlayer(
                dedicatedServer,
                world.getHandle(),
                gameProfile,
                new WrappedPlayerInteractManager(world.getHandle()));

        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(),
                loc.getYaw(), loc.getPitch());

        gameProfile.getProperties().put("textures", new WrappedProperty("textures", skin[0], skin[1]).getNmsObject()); //com.mojang.authlib.properties.Property;

        this.wrappedEntityPlayer = npc;
        if (npcListener != null) npcListener.addNPC(this);
        return npc;
    }
}