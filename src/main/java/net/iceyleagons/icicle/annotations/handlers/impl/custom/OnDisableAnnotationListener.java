package net.iceyleagons.icicle.annotations.handlers.impl.custom;

import net.iceyleagons.icicle.RegisteredIciclePlugin;
import net.iceyleagons.icicle.annotations.bukkit.OnDisable;
import net.iceyleagons.icicle.annotations.handlers.CustomAnnotationHandlerListener;
import net.iceyleagons.icicle.annotations.handlers.annotations.CustomAnnotationHandler;
import net.iceyleagons.icicle.utils.Asserts;

@CustomAnnotationHandler(OnDisable.class)
public class OnDisableAnnotationListener implements CustomAnnotationHandlerListener {

    @Override
    public void postRegistered(Object object, Class<?> type, RegisteredIciclePlugin registeredIciclePlugin) {
        registeredIciclePlugin.getClassScanner().getMethodsAnnotatedWithInsideClazz(type, OnDisable.class).forEach(method -> {
            Asserts.isSize(0, method.getParameterTypes(), "@OnDisable marked method must not have parameters!");
            method.setAccessible(true);

            registeredIciclePlugin.getOnDisabledRunnables().add(() -> {
                try {
                    method.invoke(object);
                } catch (Exception e) {
                    throw new IllegalStateException("Could not invoke @OnDisable marked method.", e);
                }
            });
        });
    }
}
