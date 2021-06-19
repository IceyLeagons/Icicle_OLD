package net.iceyleagons.icicle.beans.auto;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.iceyleagons.icicle.Icicle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Auto-Creation Tests")
public class AutoCreationTest {

    private static ServerMock serverMock;
    private static Icicle mainPlugin;

    @BeforeAll
    public static void setup() {
        serverMock = MockBukkit.mock();
        mainPlugin = (Icicle) MockBukkit.load(Icicle.class);
    }

    @Test
    public void testAutoCreation() {

    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }
}
