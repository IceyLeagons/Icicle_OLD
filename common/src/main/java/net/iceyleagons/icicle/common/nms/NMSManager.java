package net.iceyleagons.icicle.common.nms;

import net.iceyleagons.icicle.common.nms.interfaces.ICraftServer;
import net.iceyleagons.icicle.common.nms.interfaces.IWrappable;
import net.iceyleagons.icicle.common.reflect.Reflections;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.logging.Logger;

public class NMSManager {

    private static final Logger logger = Logger.getLogger(NMSManager.class.getName());

    public void init(JavaPlugin plugin) {
        logger.info("Initializing NMSManager...");

        logger.info(String.format("Detected Minecraft version: %s", Reflections.version));
    }

    public void sendPacket(Player player) {
        // Send packets...
    }

    public void injectPacketInterceptor(Player player) {
        // TODO: Check if packet interception module is available
    }

    public void uninjectPacketInterceptor(Player player) {
        // TODO: Check if packet interception module is available
    }

    // Temporary???
    public <T extends IWrappable> T createNew(Class<T> type, Object... creationArgs) {
        try {
            return (T) Class.forName(String.format("net.iceyleagons.icicle.nms.%s.%s.%s", Reflections.version,
                    type.getPackage().toString().replace("net.iceyleagons.icicle.nms.interfaces", ""),
                    type.getName().replaceFirst("I", "Wrapped")))
                    .getConstructor(Arrays.stream(creationArgs).map(obj -> obj.getClass()).toArray(Class[]::new))
                    .newInstance(creationArgs);
        } catch (ClassNotFoundException exception) {
            logger.info("Not found???");
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException e) {
            logger.info("Wrong arguments :)");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ICraftServer getCraftServer(Server server) {
        return createNew(ICraftServer.class, server);
    }

}
