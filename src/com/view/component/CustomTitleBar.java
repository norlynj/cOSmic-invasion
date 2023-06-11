package view.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomTitleBar extends JPanel {
    private JFrame frame;
    private JLabel titleLabel;

    public CustomTitleBar(JFrame frame, String title) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(frame.getWidth(), 30));

        // Create button container panel with right-aligned FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));

        // Create custom minimize button
        JButton minimizeButton = new JButton("-");
        minimizeButton.setPreferredSize(new Dimension(50, 30));
        minimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setState(Frame.ICONIFIED);
            }
        });

        // Create custom close button
        JButton closeButton = new JButton("x");
        closeButton.setPreferredSize(new Dimension(50, 30));
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        // Add buttons to the button container panel
        buttonPanel.add(minimizeButton);
        buttonPanel.add(closeButton);

        // Create title label
        titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.BLACK);

        // Add button container panel and title label to the title bar
        add(buttonPanel, BorderLayout.EAST);
        add(titleLabel, BorderLayout.CENTER);
    }
}
