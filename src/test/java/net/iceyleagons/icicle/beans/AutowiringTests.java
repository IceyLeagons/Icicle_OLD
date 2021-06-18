package net.iceyleagons.icicle.beans;

import net.iceyleagons.icicle.annotations.Autowired;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Autowiring")
public class AutowiringTests {

    private static final RegisteredBeanDictionary registeredBeanDictionary = new RegisteredBeanDictionary();

    @BeforeAll
    public static void setup() {
        registeredBeanDictionary.registerBean(new TestAutowiring());
    }

    @Test
    @DisplayName("Constructor")
    public void testConstructorAutowiring() {
        AutowiringConstructorTest autowiringConstructorTest = (AutowiringConstructorTest) AutowiringUtils.autowireAndCreateInstance(AutowiringConstructorTest.class, registeredBeanDictionary);
        assertNotNull(autowiringConstructorTest);

        String value = autowiringConstructorTest.test();
        assertEquals(value, "works");
    }

    @Test
    @DisplayName("Field")
    public void testFieldAutowiring() {
        AutowiringFieldTest autowiringConstructorTest = new AutowiringFieldTest();
        AutowiringUtils.autowireObject(autowiringConstructorTest, registeredBeanDictionary);

        String value = autowiringConstructorTest.test();
        assertEquals(value, "works");
    }

    static class TestAutowiring {
        public String test() {
            return "works";
        }
    }

    static class AutowiringConstructorTest {
        private final TestAutowiring testAutowiring;

        @Autowired
        public AutowiringConstructorTest(TestAutowiring testAutowiring) {
            this.testAutowiring = testAutowiring;
        }

        public String test() {
            return testAutowiring.test();
        }
    }

    static class AutowiringFieldTest {
        @Autowired
        private TestAutowiring testAutowiring;

        public String test() {
            return testAutowiring.test();
        }
    }

}
