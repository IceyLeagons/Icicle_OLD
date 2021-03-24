package net.iceyleagons.icicle;

import net.iceyleagons.icicle.annotations.config.ConfigHeader;
import net.iceyleagons.icicle.annotations.config.ConfigPath;
import net.iceyleagons.icicle.annotations.config.Configuration;

@ConfigHeader("For information about the config options, please check our documentation here: https://docs.iceyleagons.net/icicle/")
@Configuration("config.yml")
public class IcicleConfig {

    @ConfigPath("update-checker")
    public boolean checkForUpdates = true;

    @ConfigPath("auto-update")
    public boolean autoUpdate = false;

    @ConfigPath("metrics")
    public boolean metricsEnabled = true;

}
