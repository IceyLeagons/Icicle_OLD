package net.iceyleagons.icicle;

import net.iceyleagons.icicle.annotations.config.ConfigComment;
import net.iceyleagons.icicle.annotations.config.ConfigField;
import net.iceyleagons.icicle.annotations.config.Configuration;
import net.iceyleagons.icicle.config.AbstractConfiguration;
import org.simpleyaml.configuration.comments.CommentType;

@Configuration("config.yml")
public class IcicleConfig extends AbstractConfiguration {

    @ConfigField("auto-update")
    @ConfigComment(value =
            "By enabling this, Icicle will automatically check for updates, download and install them automatically at startup.\n" +
            "We strongly recommend enabling this to save the hassle, because other plugins may depend on new features we introduce to Icicle.\n" +
            "(Requires update-check to be true)", type = CommentType.BLOCK)
    public boolean autoUpdate = true;

    @ConfigField("update-check")
    @ConfigComment("If enabled, Icicle will check the latest version available and compare it to the current one. If the currently used one is outdated, it will notify you in the console.")
    public boolean checkUpdates = true;

}
