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

package net.iceyleagons.icicle;

import net.iceyleagons.icicle.api.IciclePlugin;
import net.iceyleagons.icicle.api.IciclePluginBootstrapper;
import net.iceyleagons.icicle.api.annotations.Autowired;
import net.iceyleagons.icicle.api.annotations.ServiceProvider;
import net.iceyleagons.icicle.api.plugin.IciclePluginManager;
import net.iceyleagons.icicle.event.Events;
import net.iceyleagons.icicle.event.packets.PacketInterception;
<<<<<<< HEAD:core/src/main/java/net/iceyleagons/icicle/Icicle.java
=======
import net.iceyleagons.icicle.registry.IciclePluginManager;
import net.iceyleagons.icicle.registry.RegisteredPlugin;
import net.iceyleagons.icicle.storage.StorageHandlerService;
import net.iceyleagons.icicle.storage.handlers.sql.MySQL;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/Icicle.java
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

/**
 * This class doesn't really have functions, only update checkers.
 * All methods/utils in Icicle should be used with static access or with the provided constructors!
 * Only purpose is version checking and providing methods in Classloader without shading.
 * Also contains metadata information!
 *
 * @author TOTHTOMI
 * @version 2.0.0
 * @since 1.0.0-SNAPSHOT
 */
public class Icicle extends JavaPlugin implements IciclePlugin {

    @Autowired
    public IcicleConfig icicleConfig;
    private IciclePluginManagerImpl pluginRegistry;

    /**
     * It's, because of Unit Testing
     */
    public Icicle() {
        super();
    }

    /**
     * It's here, because of Unit Testing
     */
    protected Icicle(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    /**
     * @return the version of the current icicle library. Our versions use the Semantic versioning.
     */
    @Override
    public String getVersion() {
        return "2.0.0-SNAPSHOT";
    }

    /**
     * Not yet implemented!
     *
     * @return will always return false atm
     */
    public boolean checkForUpdates() {
        if (!icicleConfig.checkForUpdates) return false;

        //TODO actually checking
        return false;
    }

    /**
     * This method will setup our Metrics for bStats.
     * Metrics is only set up if it's enabled in the config.
     */
    public void setupBStats() {
        if (icicleConfig.metricsEnabled) {
            //TODO
        }
    }

    /**
     * This is optional to show us some love, by printing this out.
     * You'd ideally print this out to the console, or to the players.
     *
     * @return our copyright text
     */
    @Override
    public String getCopyrightText() {
        return "This project was built upon IceyLeagons' Icicle Library v" + getVersion() +
                " (Licensed under the terms of MIT License)";
    }

    /**
     * Called by Bukkit.
     *
     * @see JavaPlugin#onLoad()
     */
    @Override
    public void onLoad() {

    }

    /**
     * Called by Bukkit.
     *
     * @see JavaPlugin#onDisable()
     */
    @Override
    public void onDisable() {

    }

    /**
     * Called by Bukkit.
     *
     * @see JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {
<<<<<<< HEAD:core/src/main/java/net/iceyleagons/icicle/Icicle.java
        pluginRegistry = new IciclePluginManagerImpl();
        IciclePluginBootstrapper.registerIcicleInstance(this);
=======
        enabled = true;
        pluginRegistry = new IciclePluginManager();
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/Icicle.java
        IciclePluginBootstrapper.bootstrap(this, "net.iceyleagons.icicle");

        // Inject player with our packet interceptor.
        Events.createBukkitConsumer(this, PlayerJoinEvent.class).asObservable().map(PlayerEvent::getPlayer).subscribe(PacketInterception::injectPlayer);

        if (checkForUpdates()) {
            //TODO
            Bukkit.getConsoleSender().sendMessage("&eIcicle is outdated!");
        }

        setupBStats();
    }


    @Override
    public JavaPlugin getPlugin() {
        return this;
    }

    @Override
    public IciclePluginManager getIciclePluginManager() {
        return this.pluginRegistry;
    }
}
