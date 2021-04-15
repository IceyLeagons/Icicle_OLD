package net.iceyleagons.icicle;

import net.iceyleagons.icicle.config.annotations.ConfigHeader;
import net.iceyleagons.icicle.config.annotations.ConfigPath;
import net.iceyleagons.icicle.config.annotations.Configuration;
import net.iceyleagons.icicle.config.AbstractConfiguration;

@ConfigHeader("For information about the config options, please check our documentation here: https://docs.iceyleagons.net/icicle/")
@Configuration("config.yml")
public class IcicleConfig extends AbstractConfiguration {

    @ConfigPath("update-checker")
    public boolean checkForUpdates = true;

    @ConfigPath("auto-update")
    public boolean autoUpdate = false;

    @ConfigPath("metrics")
    public boolean metricsEnabled = true;

}
