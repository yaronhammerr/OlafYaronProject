package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class CheckQuestionAmt implements PixelFilter, Interactive {

    public CheckQuestionAmt(){
        PixelFilter Downsample=new Downsample();
        

    }
    @Override
    public DImage processImage(DImage img) {

        short[][] grid = img.getBWPixelGrid();

        short[][] grid2 = new short[grid.length/2][grid[0].length/2];

        for (int i = 0; i < grid.length-1; i=i+2) {
            for (int j = 0; j < grid[0].length - 1; j = j + 2) {

                grid2[i / 2][j / 2] = grid[i][j];

            }
        }

        int startRowHundreds =164;
        int startColHundreds =221;
        int endRowHundreds =186;
        int sideLength = 10;

        int startRowTens=164;
        int startColTens=233;
        int endRowTens=282;

        int startRowOnes=164;
        int startColOnes=245;
        int endRowOnes=282;







//        for (int StartR = startRowTens; StartR < endRowTens; StartR+=(sideLength)+2) {
//            for (int row =StartR; row <StartR+sideLength; row++) {
//                for (int col = startColTens; col <startColTens+sideLength; col++) {
//
//                }
//
//            }
//
//        }


        int hundreds=findDigit(startRowHundreds, startColHundreds, endRowHundreds, sideLength, grid2);
        int tens=findDigit(startRowTens, startColTens, endRowTens, sideLength, grid2);
        int ones=findDigit(startRowOnes,startColOnes,endRowOnes, sideLength,grid2);
        System.out.print(hundreds);
        System.out.print(tens);
        System.out.print(ones);

        img.setPixels(grid2);
        return img;
    }




    public static int findDigit(int startRow, int startCol, int endRow, int sideLength, short[][]grid){
//        int[][] values=new int[sideLength*sideLength][(startRow+endRow)/2];
        ArrayList<Integer> values=new ArrayList<>();


        for (int StartR = startRow; StartR < endRow; StartR+=(sideLength)+2) {
            int sum=0;
            for (int row =StartR; row <StartR+sideLength; row++) {
                for (int col = startCol; col <startCol+sideLength; col++) {
//                  grid2[row][col]=25;
                    sum=sum+grid[row][col];

                }

            }
            sum=sum/(sideLength*sideLength);
            values.add(sum);

        }

        int digit=checkSummer(values);


    return digit;

    }

    private static int checkSummer(ArrayList<Integer> values) {
        System.out.println(values);
        int darkestValIndex=darkestValFinder(values);

        for (int i = 0; i < values.size(); i++) {
            if(Math.abs(values.get(darkestValIndex)-values.get(i))>30){
                return darkestValIndex;


            }

        }
        return 0;
    }





    private static int darkestValFinder(ArrayList<Integer> values) {
        int darkest=256;
        int count=0;

        for (int i = 0; i < values.size(); i++) {
            if(values.get(i)<darkest){
                count=i;
                darkest=values.get(i);

            }
        }
        return count;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {

    }

    @Override
    public void keyPressed(char key) {

    }
}
