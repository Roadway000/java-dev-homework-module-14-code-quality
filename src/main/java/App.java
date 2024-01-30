import java.util.Scanner;
import static java.lang.System.in;

public class App {
    private final char[] box = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    private static final Scanner scan = new Scanner(in);
    private void drawCrossesAndZeros() {
        String row = " %1s | %1s | %1s ";
        MyFormat.logger.info(row.formatted(box[0],box[1],box[2]));   //  X |   | O
        MyFormat.logger.info("-----------");                    // -----------
        MyFormat.logger.info(row.formatted(box[3],box[4],box[5]));   //  O | X | O
        MyFormat.logger.info("-----------");                    // -----------
        MyFormat.logger.info(row.formatted(box[6],box[7],box[8]));   //    |   | X
    }
    private void cleanBox() {
        for(int i=0; i < 9; i++)
            box[i] = ' ';
    }
    private boolean isBoxAvailable() {
        for(int i=0; i<9; i++){
            if(box[i] != 'X' && box[i] != 'O'){
                return true;
            }
        }
        return false;
    }
    private boolean isFilledLine(char c) {
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
            MyFormat.logger.info("Summa of filled Cells in line for '%s' = %d".formatted(c,sum));
        return ((sum % 3 == 0) && (sum % 2 == 0 || sum % 5 == 0) && (stepCount>2));
    }

    private byte whoFilledBoxLine(char c) {
        /*  when you select a number a cross is drawn when the program selects a number, a zero is drawn */
        if (isFilledLine(c)) {
            if (c == 'X')
                return 1;
            if (c == 'O')
                return 2;
        }
        return 0;
    }

    private void programStep() {
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
    private void yourStep() {
        /*  this is your step - choose a number from 1 to 9 and the program will write a cross "X" */
        byte input;
        while (true) {
            input = scan.nextByte();
            if (input > 0 && input < 10) {
                if (box[input - 1] == 'X' || box[input - 1] == 'O')
                    MyFormat.logger.info("That one is already in use. Enter another.");
                else {
                    box[input - 1] = 'X';
                    break;
                }
            }
            else
                MyFormat.logger.info("Invalid input. Enter again.");
        }
    }
    private void ticTacToe() {
        byte winner = 0;
        /*  char box[] = ... It works and is not an error, but it is not easy to read.
            It is better if the type of the variable is explicitly specified
        */
        MyFormat.logger.info("Enter box number to select. Enjoy!\n");
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
        MyFormat.logger.info(signature);
    }
    public static void main(String[] args) {
        App app = new App();
        app.ticTacToe();
    }
}