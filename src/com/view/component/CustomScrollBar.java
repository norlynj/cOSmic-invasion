package view.component;

import javax.swing.*;
import java.awt.*;

public class CustomScrollBar extends JScrollBar {

    private Color theme = Color.gray;

    public CustomScrollBar() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(8, 8));
        setForeground(theme);
    }
}