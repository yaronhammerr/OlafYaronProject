package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

public class testLoc implements PixelFilter, Interactive {
    @Override
    public DImage processImage(DImage img) {
        return img;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {
        System.out.println("x: " + mouseX + " y: " + mouseY);
    }

    @Override
    public void keyPressed(char key) {

    }
}
