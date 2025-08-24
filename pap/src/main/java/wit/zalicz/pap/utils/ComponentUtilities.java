package wit.zalicz.pap.utils;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ComponentUtilities {
    public static final Color WIT_RED        = Color.decode("#c43c3c");
    public static final Color WIT_DARK_GRAY  = Color.decode("#333333");
    public static final Color WIT_LIGHT_GRAY = Color.decode("#f0f0f0");
    public static final Color TEXT_COLOR     = Color.WHITE;

    public static final Color TRANSPARENT    = new Color(0, 0, 0, 0);
    public static final Color PRIMARY        = WIT_RED;
    public static final Color SECONDARY      = WIT_DARK_GRAY;
    public static final Color DARK_THEME     = WIT_DARK_GRAY;
    public static final Color GRAY_THEME     = Color.decode("#808080");

    public static EmptyBorder addPadding(int top, int left, int bottom, int right) {
        return new EmptyBorder(top, left, bottom, right);
    }
}
