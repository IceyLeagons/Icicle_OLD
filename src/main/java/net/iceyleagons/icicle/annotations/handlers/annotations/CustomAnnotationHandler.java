package net.iceyleagons.icicle.annotations.handlers.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomAnnotationHandler {

    Class<? extends Annotation> value();

}

