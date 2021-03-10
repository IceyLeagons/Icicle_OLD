package net.iceyleagons.icicle.injection;

import lombok.Getter;
import lombok.Setter;
import net.iceyleagons.icicle.injection.annotations.InjectionHandler;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractInjectionHandler {

    @Getter
    @Setter
    private JavaPlugin javaPlugin;

    @Getter
    @Setter
    private InjectionHandlerRegistry injectionHandlerRegistry;

    @Getter
    @Setter
    private InjectionHandler injectionHandler;

    /**
     * Called by the handler, if the provided object has a injection type, that is handled by this injection handler.
     * Injection logic should be done here.
     *
     * @param o the object to inject
     */
    public abstract void inject(Object o);

    /**
     * Will be called after all the handlers have been registered.
     */
    public void postInitialization() {}

}
