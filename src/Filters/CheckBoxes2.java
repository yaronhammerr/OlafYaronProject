package Filters;

import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;


public class CheckBoxes2 implements PixelFilter {

    ArrayList<int[]> rowAnswerList = new ArrayList<>();

    public int length1 = 66, yDistanceBetRows = 24, distToFirstBox = 137, xDistBetBoxes = 2, boxLength = 11, rowAmt = 25, boxHeight = 11;
    public int[] avgBoxInRowPixelVal = new int[5];

    @Override
    public DImage processImage(DImage img) {

        short[][] grid = img.getBWPixelGrid();

        int startXVal, startYVal;
        for (int row = 0; row < rowAmt; row++) { // for every row (new list for every row)

            int[] currRow = new int[5];

            for (int boxesChecked = 0; boxesChecked < 5; boxesChecked++) { // for every box in a row (5)

                int total = 0;

                startXVal = distToFirstBox + ((xDistBetBoxes * boxesChecked) + (boxLength * boxesChecked)); //where each new box check starts (x val)

                for (int currX = startXVal; currX < startXVal + boxLength; currX++) { // length to go for each box

                    startYVal = length1 + (row * yDistanceBetRows);

                    for (int currY = startYVal; currY < startYVal + 11; currY++) {

                        total = total + grid[currY][currX];
                    }

                }

                currRow[boxesChecked] = (total/(boxLength*11));

            }

            rowAnswerList.add(currRow);

        }

        int[] answers = new int[rowAnswerList.size()];
        for (int row = 0; row < rowAnswerList.size(); row++) {
            int[] currRow = rowAnswerList.get(row);
            int darkestInd = getDarkestBox(currRow);
            if(checkForNoneOrMultipleMarked(20, currRow, darkestInd)){
                answers[row] = -1;
            }else{
                answers[row] = darkestInd;
            }
        }

        //print ans

        for (int i = 0; i < answers.length; i++) {
            System.out.println("Question #" + (i+25) + " : " + answers[i]);
        }

img.setPixels(grid);
        return img;
    }

    public static int getDarkestBox (int[] row){

        int darkest = 255;
        int darkestInd = -1;
        for (int i = 0; i < row.length; i++) {

            if(row[i] < darkest){
                darkest = row[i];
                darkestInd = i;
            }
        }

        return darkestInd;
    }

    public static boolean checkForNoneOrMultipleMarked (int thresh, int[] row, int darkest){

        for (int currRow = 0; currRow < row.length; currRow++) {

            if(currRow != darkest){

                if(row[currRow] > row[darkest] - thresh && row[currRow] < row[darkest] + thresh){
                    return true;
                }

            }

        }

        return false;
    }

    public int[] getAnswers26to50 (DImage img){

        short[][] grid = img.getBWPixelGrid();

        int startXVal, startYVal;
        for (int row = 0; row < rowAmt; row++) { // for every row (new list for every row)

            int[] currRow = new int[5];

            for (int boxesChecked = 0; boxesChecked < 5; boxesChecked++) { // for every box in a row (5)

                int total = 0;

                startXVal = distToFirstBox + ((xDistBetBoxes * boxesChecked) + (boxLength * boxesChecked)); //where each new box check starts (x val)

                for (int currX = startXVal; currX < startXVal + boxLength; currX++) { // length to go for each box

                    startYVal = length1 + (row * yDistanceBetRows);

                    for (int currY = startYVal; currY < startYVal + 11; currY++) {

                        total = total + grid[currY][currX];
                    }

                }

                currRow[boxesChecked] = (total/(boxLength*11));

            }

            rowAnswerList.add(currRow);

        }

        int[] answers = new int[rowAnswerList.size()];
        for (int row = 0; row < rowAnswerList.size(); row++) {
            int[] currRow = rowAnswerList.get(row);
            int darkestInd = getDarkestBox(currRow);
            if(checkForNoneOrMultipleMarked(20, currRow, darkestInd)){
                answers[row] = -1;
            }else{
                answers[row] = darkestInd;
            }
        }

        //print ans

       return answers;
    }
}
