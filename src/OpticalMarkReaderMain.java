import FileIO.PDFHelper;
import Filters.CheckBoxes;
import Filters.CheckBoxes2;
import Filters.CheckQuestionAmt;
import Filters.Downsample;
import core.DImage;
import processing.core.PImage;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;

public class OpticalMarkReaderMain {

    public static String currentFolder = System.getProperty("user.dir") + "/";


    public static void main(String[] args) {



        try (FileWriter f = new FileWriter("response.csv");

             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {

            CheckBoxes checkBoxes = new CheckBoxes();
            CheckBoxes2 checkBoxes2 = new CheckBoxes2();
            Downsample downsample = new Downsample();
            CheckQuestionAmt checkQuestionAmt = new CheckQuestionAmt();
            PImage in = PDFHelper.getPageImage("assets/OfficialOMRSampleDoc.pdf",1);
            DImage img = new DImage(in);
            DImage downsampled = downsample.processImage(img);
            int questionAmt = checkQuestionAmt.getQuestionAmt(downsampled);
            int[] questions1to25 = checkBoxes.getAnswers1to25(downsampled);
            int[] questions26to50 = checkBoxes2.getAnswers26to50(downsampled);

            String[][] answers = new String[5][questionAmt];


            DImage currDownsampled, currImg;
            PImage curr;
            int[] currQuestions26to50;
            //load pdf
            for (int page = 2; page < 7; page++) {
                //get answers from page


                curr = PDFHelper.getPageImage("assets/OfficialOMRSampleDoc.pdf",page);
                currImg = new DImage(curr);
                currDownsampled = downsample.processImage(currImg);
                int [] currQuestions1to25 = checkBoxes.getAnswers1to25(currDownsampled);

                currQuestions26to50 = checkBoxes2.getAnswers26to50(currDownsampled);

                //compare

                if(questionAmt>50){
                    questionAmt = 50;
                }

                if(questionAmt > 25){

                    for (int i = 0; i < 25; i++) {
                        if(questions1to25[i]==currQuestions1to25[i]){
                            answers[page-2][i] = "Correct";
                        }else{
                            answers[page-2][i] = "Incorrect";
                        }
                    }

                    int page2questionAmt = questionAmt-25;

                    for (int i = 25; i <25+page2questionAmt; i++) {
                        if(questions26to50[i]==currQuestions26to50[i]){
                            answers[page-2][i] = "Correct";
                        }else{
                            answers[page-2][i] = "Incorrect";
                        }

                    }


                } else{

                    for (int currQuestion = 0; currQuestion < questionAmt; currQuestion++) {
                        if(questions1to25[currQuestion]==currQuestions1to25[currQuestion]){
                            answers[page-2][currQuestion] = "Correct";
                        }else{
                            answers[page-2][currQuestion] = "Incorrect";
                        }


                    }

                }


            }


            writer.print("page, # right");
            for (int i = 0; i < questionAmt; i++) {
                writer.print(", q"+(i+1) + " ");
            }

            for (int i = 0; i < answers.length; i++) {
                writer.println("");
                writer.print((i+1)+" , "+getAmtRight(answers[i]));
                for (int j = 0; j < answers[i].length; j++) {
                    writer.print(" , " + answers[i][j]);
                }
            }

            int[] amtWrong = getAmtWrongArray(answers, questionAmt);
            makeFileForAmtWrongForEachQuestion(amtWrong);



        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " );
            error.printStackTrace();
        }
        /*
        Your code here to...
        (1).  Load the pdf
        (2).  Loop over its pages
        (3).  Create a DImage from each page and process its pixels
        (4).  Output 2 csv files
         */



        //get question amt








    }

    private static String fileChooser() {
        String userDirLocation = System.getProperty("user.dir");
        File userDir = new File(userDirLocation);
        JFileChooser fc = new JFileChooser(userDir);
        int returnVal = fc.showOpenDialog(null);
        File file = fc.getSelectedFile();
        return file.getAbsolutePath();
    }

    public static int getAmtRight (String[] arr){
        int amt = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i].equals("Correct")){
                amt++;
            }
        }

        return amt;
    }

    public static int[] getAmtWrongArray (String[][] answers, int questionAmt){
        int[] amtWrong = new int[questionAmt];
        for (int currQuestion = 0; currQuestion < answers[0].length; currQuestion++) {


            for (int answerSheet = 0; answerSheet < answers.length; answerSheet++) {

                if(answers[answerSheet][currQuestion].equals("Incorrect")){
                    amtWrong[currQuestion]++;
                }

            }

        }

        return amtWrong;
    }

    public static void makeFileForAmtWrongForEachQuestion (int[] amtWrong) {
        {
            try (FileWriter f = new FileWriter("amtWrong");
                 BufferedWriter b = new BufferedWriter(f);
                 PrintWriter writer = new PrintWriter(b);) {


                for (int currQuestion = 0; currQuestion < amtWrong.length; currQuestion++) {
                    writer.println("Question #" + (currQuestion + 1) + ": " + amtWrong[currQuestion] + " wrong answers");
                }


            } catch (IOException error) {
                System.err.println("There was a problem writing to the file: ");
                error.printStackTrace();
            }

        }
    }
}
