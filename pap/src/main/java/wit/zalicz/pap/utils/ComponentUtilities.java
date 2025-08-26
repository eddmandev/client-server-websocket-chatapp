package wit.zalicz.pap.utils;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ComponentUtilities {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color ACCENT_BORDO = Color.decode("#A2131B");
    public static final Color DARK_THEME = Color.decode("#1E1E1E");
    public static final Color GRAY_THEME = Color.decode("#939598");
    public static final Color TEXT_COLOR = Color.WHITE;

    public static EmptyBorder addPadding(int top, int left, int bottom, int right) {
        return new EmptyBorder(top, left, bottom, right);
    }
}
