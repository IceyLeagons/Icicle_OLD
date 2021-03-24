<<<<<<< HEAD:api/src/main/java/net/iceyleagons/icicle/api/commands/RegisteredCommand.java
package net.iceyleagons.icicle.api.commands;
=======
package net.iceyleagons.icicle.commands.system;
>>>>>>> parent of e77d82b (:package: multi module):src/main/java/net/iceyleagons/icicle/commands/system/RegisteredCommand.java

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.iceyleagons.icicle.annotations.commands.Command;

import java.lang.reflect.Method;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class RegisteredCommand {

    private final Object object;
    private final Method method;
    private final Command annotation;

}
