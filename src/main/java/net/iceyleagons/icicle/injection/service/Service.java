package net.iceyleagons.icicle.injection.service;

import net.iceyleagons.icicle.injection.annotations.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes annotated with this annotation should have an empty constructor, because the handler will automatically generate it.
 * These classes can be injected into fields using the {@link ServiceField} annotation.
 * These classes can also have injection field inside them as well.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Autowired
public @interface Service { }
