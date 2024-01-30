package org.example;
import java.util.Scanner;

import static java.lang.System.in;
import static org.example.MyFormat.logger;

public class App {
    private static final char[] box = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    private static final Scanner scan = new Scanner(in);
    static void drawCrossesAndZeros() {
        String row = " %1s | %1s | %1s ";
        logger.info(row.formatted(box[0],box[1],box[2]));   //  X |   | O
        logger.info("-----------");                    // -----------
        logger.info(row.formatted(box[3],box[4],box[5]));   //  O | X | O
        logger.info("-----------");                    // -----------
        logger.info(row.formatted(box[6],box[7],box[8]));   //    |   | X
    }
    static void cleanBox() {
        for(int i=0; i < 9; i++)
            box[i] = ' ';
    }
    static boolean isBoxAvailable() {
        for(int i=0; i<9; i++){
            if(box[i] != 'X' && box[i] != 'O'){
                return true;
            }
        }
        return false;
    }
    static boolean isFilledLine(char c) {
        /*  We calculate the sum of the numbers of filled cells.
            If the sum is a multiple of 3 and divisible by 2 or 5, and
            the number of steps is greater than 2, then the line is considered complete. */
        int sum = 0;
        byte stepCount = 0;
        for (int i=0; i<9; i++) {
            if (box[i]==c) {
                sum += i+1;
                stepCount++;
            }
        }
        if (stepCount>2)
            logger.info("Summa of filled Cells in line for '%s' = %d".formatted(c,sum));
        return ((sum % 3 == 0) && (sum % 2 == 0 || sum % 5 == 0) && (stepCount>2));
    }

    static byte whoFilledBoxLine(char c) {
        /*  when you select a number a cross is drawn when the program selects a number, a zero is drawn */
        if (isFilledLine(c)) {
            if (c == 'X')
                return 1;
            if (c == 'O')
                return 2;
        }
        return 0;
    }
    static void programStep() {
        /* this step is done by the program by choosing a pseudo random number from 0 to 9 in response to your step */
        byte rand;
        while (true) {
            rand = (byte) (Math.random() * 9);
            if (box[rand] == ' ') {
                box[rand] = 'O';
                break;
            }
        }
    }
    static void yourStep() {
        /*  this is your step - choose a number from 1 to 9 and the program will write a cross "X" */
        byte input;
        while (true) {
            input = scan.nextByte();
            if (input > 0 && input < 10) {
                if (box[input - 1] == 'X' || box[input - 1] == 'O')
                    logger.info("That one is already in use. Enter another.");
                else {
                    box[input - 1] = 'X';
                    break;
                }
            }
            else
                logger.info("Invalid input. Enter again.");
        }
    }
    static void ticTacToe() {
        byte winner = 0;
        /*  char box[] = ... It works and is not an error, but it is not easy to read.
            It is better if the type of the variable is explicitly specified
        */
        logger.info("Enter box number to select. Enjoy!\n");
        boolean boxEmpty = false;
        while (winner == 0) {
            drawCrossesAndZeros();
            if(!boxEmpty){
                cleanBox();
                boxEmpty = true;
            }

            yourStep();

            winner = whoFilledBoxLine('X');

            if (winner==0 && !isBoxAvailable()) {
                winner = 3;
            }
            if (winner==0) {
                programStep();
                winner = whoFilledBoxLine('O');
            }
        }
        drawCrossesAndZeros();
        String signature = "\nCreated by Shreyas Saha. Thanks for playing!";
        if(winner == 1)
            signature = "You won the game!%s".formatted(signature);
        else if(winner == 2)
            signature = "You lost the game!%s".formatted(signature);
        else if(winner == 3)
            signature = "It's a draw!%s".formatted(signature);
        logger.info(signature);
    }
    public static void main(String[] args) {
        ticTacToe();
    }
}