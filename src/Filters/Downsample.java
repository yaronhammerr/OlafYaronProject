package Filters;

import Interfaces.PixelFilter;
import core.DImage;

public class Downsample implements PixelFilter {


    @Override
    public DImage processImage(DImage img) {
        short[][] grid = img.getBWPixelGrid();

        short[][] red = img.getRedChannel();
        short[][] blue = img.getBlueChannel();
        short[][] green = img.getGreenChannel();

        short[][] grid2 = new short[grid.length/2][grid[0].length/2];

        short[][] newRed = new short[red.length/2][red[0].length/2];
        short[][] newGreen = new short[red.length/2][red[0].length/2];
        short[][] newBlue = new short[red.length/2][red[0].length/2];


        for (int i = 0; i < grid.length-1; i=i+2) {
            for (int j = 0; j < grid[0].length-1; j=j+2) {

                grid2[i/2][j/2] = grid[i][j];



            }

        }

        img.setPixels(grid2);
        return img;
    }


}

