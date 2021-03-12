package net.iceyleagons.icicle;

import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.annotations.Service;

@Service
public class TestService {

    @Autowired
    public TestService2 testService2;

    public void test() {
        testService2.test();
    }

}
