package net.iceyleagons.icicle.annotations.handlers.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoCreationHandler {

    Class<? extends Annotation> value();

}
