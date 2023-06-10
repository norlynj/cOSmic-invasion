package view.component;

import javax.swing.*;
import java.awt.*;

public class ScrollBar extends JScrollBar {

    private Color theme = Color.gray;

    public ScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(8, 8));
        setForeground(theme);
    }
}