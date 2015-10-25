package GUI;

import java.awt.*;

/**
 * Created by MSDK on 8/24/15.
 */
enum CORRECTNESS {
    CORRECT(Color.GREEN), WRONG(Color.RED);

    private final Color c;

    CORRECTNESS(Color c) {
        this.c = c;
    }

    public Color getColor() {
        return c;
    }
}
