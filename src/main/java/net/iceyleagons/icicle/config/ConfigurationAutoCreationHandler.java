package net.iceyleagons.icicle.config;

import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.config.Configuration;
import net.iceyleagons.icicle.annotations.handlers.AutoCreationHandlerListener;
import net.iceyleagons.icicle.annotations.handlers.annotations.AutoCreationHandler;
import net.iceyleagons.icicle.utils.Asserts;

@AutoCreationHandler(Configuration.class)
public class ConfigurationAutoCreationHandler implements AutoCreationHandlerListener {

    @Override
    public void onCreated(Object object, Class<?> type, RegisteredIciclePlugin registeredIciclePlugin) {
        //object.getClass() != type because of CGLIB

        Asserts.isInstanceOf(AbstractConfiguration.class, object, "Configuration must extend AbstractConfiguration!");

        Configuration annotation = type.getAnnotation(Configuration.class);
        AbstractConfiguration abstractConfiguration = (AbstractConfiguration) object;

        abstractConfiguration.setOrigin(object);
        abstractConfiguration.setOriginType(type);
        abstractConfiguration.setPlugin(registeredIciclePlugin);
        abstractConfiguration.setPathToFile(annotation.value());
        if (annotation.headerLines().length > 0)
            abstractConfiguration.setHeader(String.join(System.lineSeparator(), annotation.headerLines()));

        System.out.println("Called");
        abstractConfiguration.init();
    }
}
