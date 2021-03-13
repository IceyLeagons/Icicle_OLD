package net.iceyleagons.icicle.test;

import net.iceyleagons.icicle.annotations.Autowired;
import net.iceyleagons.icicle.annotations.Service;

@Service
public class TestService1 {

    @Autowired
    public TestService2 testService2;

    public void test() {
        testService2.test();
        System.out.println("Service1");
    }

}
