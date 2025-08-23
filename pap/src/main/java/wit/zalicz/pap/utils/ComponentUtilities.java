package wit.zalicz.pap.utils;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ComponentUtilities {
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    public static final Color PRIMARY  = Color.decode("#c43c3c");
    public static final Color SECONDARY =  Color.decode("#484444");
    public static final Color DARK_THEME  = Color.decode("#121212");
    public static final Color GRAY_THEME = Color.decode("#808080");
    public static final Color TEXT_COLOR  = Color.WHITE;

    public static EmptyBorder addPadding(int top, int left, int bottom, int right){

        return new EmptyBorder(top, left, bottom, right);
    }
}
