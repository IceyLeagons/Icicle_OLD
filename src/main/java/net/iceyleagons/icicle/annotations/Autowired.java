package net.iceyleagons.icicle.annotations;

import org.bukkit.Server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All fields or constructors annotated with this annotation will get dependency injected. The options:
 *
 * 1) if you decide to use the constructor autowiring (@{@link Autowired} is placed on top of a {@link java.lang.reflect.Constructor}):
 *
 *      - you must only have 1 public constructor in the class
 *      - the class must be icicle-managed (aka. annotated with an auto-creation annotation, such as @{@link Service} or @{@link net.iceyleagons.icicle.annotations.bukkit.EventListener})
 *
 *
 * 2) if you decide to use the field method you can either (@{@link Autowired} are placed on top of {@link java.lang.reflect.Field}s)
 *
 *      a, have Icicle create the class, for this:
 *          - you must only have 1 public empty constructor in the class
 *          - the class must be icicle-managed (aka. annotated with an auto-creation annotation, such as @{@link Service} or @{@link net.iceyleagons.icicle.annotations.bukkit.EventListener})
 *
 *      b, autowire the object manually (useful, when you don't want to class to be created by Icicle), the following applies:
 *          - you may have multiple constructors
 *          - the class must not have final fields, for such use please use the a) version.
 *          - the class can only be autowired later (after creation of the instance), therefore the constructor must not call field dependant methods
 *
 *
 * For every autowiring, the following rules apply:
 *      - The class can not, and should not autowire beans defined inside itself.
 *      - Icicle does not currently have a way to register and inject: Interfaces and Abstract classes. Please use the class, that implements or extends these super-types.
 *        Exception to such a rule are the following, but not limited to:
 *              -> JavaPlugin and Plugin which will all get injected with the Main of your plugin.
 *              -> PluginManager which will be from {@link Server#getPluginManager()}
 *              -> For more info please check our online documentation
 *
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface Autowired {
}
