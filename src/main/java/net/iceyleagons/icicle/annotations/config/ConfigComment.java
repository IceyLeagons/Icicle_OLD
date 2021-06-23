package net.iceyleagons.icicle.annotations.config;

import org.simpleyaml.configuration.comments.CommentType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfigComment {

    String value();
    CommentType type() default CommentType.SIDE;

}
