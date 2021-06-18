package net.iceyleagons.icicle.other;

import net.iceyleagons.icicle.utils.Asserts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

public final class Base64Converter {

    @NotNull
    public static String imageToBase64(BufferedImage bufferedImage, String format) {
        if (bufferedImage == null) return "";
        Asserts.notNull(format, "Format must not be null!");

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, format, byteArrayOutputStream);

            return getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (Exception ignored) {
            return "";
        }
    }

    @Nullable
    public static BufferedImage base64ToImage(String base64, String format) {
        if (base64 == null || base64.isEmpty()) return null;
        Asserts.notNull(format, "Format must not be null!");

        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getDecoder().decode(base64))) {
            return ImageIO.read(byteArrayInputStream);
        } catch (IOException ignored) {
            return null;
        }
    }
}
