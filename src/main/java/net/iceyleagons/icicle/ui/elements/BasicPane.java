package net.iceyleagons.icicle.ui.elements;

import lombok.Getter;
import lombok.Setter;
import net.iceyleagons.icicle.ui.UIRenderable;

@Getter
public abstract class BasicPane implements UIRenderable {

    @Setter
    private int x = 0;
    @Setter
    private int y = 0;

    private final int width;
    private final int height;

    @Setter
    private boolean visible = true;

    public BasicPane(int w, int h) {
        this.width = w;
        this.height = h;
    }
}
