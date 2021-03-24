package net.iceyleagons.icicle.api.plugin;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.api.annotations.commands.Command;

import java.lang.reflect.Method;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class RegisteredCommand {

    private final Object object;
    private final Method method;
    private final Command annotation;

}
