package net.iceyleagons.icicle.injection.annotations;

import net.iceyleagons.icicle.injection.service.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes annotated with this will be automatically generated and registered/autowired
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface EventListener {
}
