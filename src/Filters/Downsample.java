package Filters;

import Interfaces.Drawable;
import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;
import processing.core.PApplet;

import javax.swing.*;

public class Downsample implements PixelFilter, Interactive, Drawable {



    @Override
    public DImage processImage(DImage img) {


        short[][] grid = img.getBWPixelGrid();

        short[][] grid2 = new short[grid.length/2][grid[0].length/2];

        for (int i = 0; i < grid.length-1; i=i+2) {
            for (int j = 0; j < grid[0].length - 1; j = j + 2) {

                grid2[i / 2][j / 2] = grid[i][j];

            }
        }




//                for (int i = 55; i < 55 + yDistanceBetRows*rowAmt; i = i + yDistanceBetRows) {
//
//                    for (int j = 0; j < length1; j++) {
//
//
//                        for (int k = 0; k < 11; k++) {
//                            grid2[i + k][j] = 50;
//                        }
//                    }
//                }



        img.setPixels(grid2);
        return img;
    }


    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {

    }

    @Override
    public void keyPressed(char key) {
if ((key == 'y')){
    String ans = JOptionPane.showInputDialog("Length:");


}
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {

    }
}

