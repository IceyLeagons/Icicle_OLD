package net.iceyleagons.icicle.file;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FileIO")
public class FileIOTest {

    @Test
    @DisplayName("getFileNameWithoutExtension")
    public void testFileNameWithoutExtension() {
        assertEquals(FileIO.getFileNameWithoutExtension(new File("test.txt")), "test");
        assertEquals(FileIO.getFileNameWithoutExtension(new File("may_name_is.png")), "may_name_is");
        assertEquals(FileIO.getFileNameWithoutExtension(new File("will.this.work.png")), "will.this.work");
    }

}
