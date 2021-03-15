package net.iceyleagons.icicle;

import net.iceyleagons.icicle.annotations.config.ConfigPath;
import net.iceyleagons.icicle.annotations.config.Configuration;
import net.iceyleagons.icicle.config.AbstractConfiguration;

@Configuration("test.yaml")
public class TestConfig extends AbstractConfiguration {

    @ConfigPath("test.hi")
    public String hi = "Hi, this is working";

    @ConfigPath("test.second")
    public String second = "This is the second message";



}
