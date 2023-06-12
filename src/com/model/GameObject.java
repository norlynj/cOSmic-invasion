package model;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class GameObject {
    protected Image img;
    protected AffineTransform tx;
    protected int x, y;
    protected int oX, oY;
    protected double scaleSize;
    protected double width, height;
    protected int range;
    protected boolean paused; // Flag to indicate whether the game is paused or not

    public GameObject(int x, int y, String fileName, double scaleSize) {
        this.scaleSize = scaleSize;
        this.scaleSize = scaleSize;
        this.oX = this.x = x;
        this.oY = this.y = y;
        img = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/elements/" + fileName))).getImage();
        tx = AffineTransform.getTranslateInstance(x, y);
        init(x, y);

        range = 400;
    }

    protected void changePicture(String fileName) {
        img = new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/elements/" + fileName))).getImage();
        init(x, y);
    }

    public void paint(Graphics g) {
        width = img.getWidth(null) * scaleSize;
        height = img.getHeight(null) * scaleSize;

        if (!paused) { // Check if the game is not paused
            move();
        }
        // draw image
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(img, tx, null);
        if (!paused) { // Check if the game is not paused
            update();
        }
    }

    public void move() {
    }

    // update the picture variable location
    protected void update() {
        tx.setToTranslation(x, y);
        tx.scale(scaleSize, scaleSize);
    }

    protected void init(double a, double b) {
        tx.setToTranslation(a, b);
        tx.scale(scaleSize, scaleSize);
    }

    public String toString() {
        return "[x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
    }

    public int x() {
        return x;
    }

    public void x(int x) {
        this.x = x;
    }

    public int y() {
        return y;
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
