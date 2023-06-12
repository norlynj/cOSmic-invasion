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
        this(text, false);
    }

    public Label(String text, boolean multiLine) {
        GraphicsEnvironment ge = null;
        try{
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/font/retropix.ttf")));

        } catch(FontFormatException e){} catch (IOException e){}

        setFont(new Font("RetroPix Regular", Font.PLAIN, 20));
        setForeground(new Color(255, 255, 255));
        this.multiLine = multiLine;

        setText(text);
    }

    @Override
    public void setText(String text) {
        if (multiLine) {
            text = "<html>" + text + "</html>";
        }
        super.setText(text);
    }
}