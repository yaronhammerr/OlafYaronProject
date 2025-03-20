package Filters;

import Interfaces.Interactive;
import Interfaces.PixelFilter;
import core.DImage;

import java.util.ArrayList;

public class Analyze implements PixelFilter, Interactive {
    ArrayList<int[]> rowAnswerList = new ArrayList<>();

    public int length1 = 55, yDistanceBetRows = 24, distToFirstBox = 51, xDistBetBoxes = 2, boxLength = 11, rowAmt = 25, boxHeight = 11;
    public int[] avgBoxInRowPixelVal = new int[5];

    Downsample downsample = new Downsample();
    CheckBoxes checkBoxes = new CheckBoxes();
    CheckBoxes2 checkBoxes2 = new CheckBoxes2();
    CheckQuestionAmt checkQuestionAmt = new CheckQuestionAmt();

    public DImage processImage(DImage img){

        img = downsample.processImage(img);

        int[] answers1to25 = checkBoxes.getAnswers1to25(img);
        int[] answers26to50 = checkBoxes2.getAnswers26to50(img);


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
