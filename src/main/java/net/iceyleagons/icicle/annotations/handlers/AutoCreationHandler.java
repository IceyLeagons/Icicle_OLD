package net.iceyleagons.icicle.annotations.handlers;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoCreationHandler {

    Class<? extends Annotation> value();

}
