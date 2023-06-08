package view.component;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Label extends JLabel {
    boolean multiLine;
    boolean center;

    public Label() {
        this("");
    }

    public Label(String text) {
        this(text, 25, false, SwingConstants.CENTER);
    }

    public Label(String text, int fontSize, boolean multiLine, int alignment) {
        setFont(new Font("Arial", Font.BOLD, fontSize));
        setForeground(new Color(255, 255, 255));
        this.multiLine = multiLine;

        //sets the horizontal alignment of the label
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.CENTER);
        setText(text);
    }

    @Override
    public void setText(String text) {
        if (multiLine) {
            if (center) {
                text = "<html><center>" + text + "</center></html>"; //align in the center
            } else {
                text = "<html>" + text + "</html>";
            }
        }
        super.setText(text);
    }
}