import org.apache.commons.lang3.*;

import java.util.Scanner;

public class FrogsAndToads {
    public static void main(String[] args) {
        FrogsAndToads frogsAndToads = new FrogsAndToads();
        frogsAndToads.playGAme();

        System.out.println("Running test cases:");
        frogsAndToads.testCases();

    }

    public String getInput(String prompt) {
        String inputStr = "";

        Scanner scan = new Scanner(System.in);

        do {
            System.out.print(prompt);
            inputStr = scan.next();
        }

        //verify that user enetered a valid number that is 4 digits
        while (!validateNumber(inputStr));

        return inputStr;
    }


    public void playGAme() {
        String hidden = getInput("Player A: Input a hidden, 4 digit whole number: ");

        String guess = "";
        String result = "";
        do {
            guess = getInput("Player B: Input a a guess. Please use a 4 digit whole number: ");
            result = getHint(hidden, guess);
            System.out.println("Results: " + result);
        }
        while (!"4F0T".equals(result));
        System.out.println("Good job- you guessed the number!");

    }


    public String getHint(String hiddenStr, String guessStr) {
        String frogsAndToadsCount = "Invalid Input";

        //verify numbers again
        if (validateNumber(hiddenStr) && validateNumber(guessStr)) {

            int T = 0;
            int F = 0;

            StringBuilder hiddenSB = new StringBuilder(hiddenStr);
            StringBuilder guessSB = new StringBuilder(guessStr);

            //Identify all Frogs and remove from comparisons
            for (int i = 0; i < hiddenStr.length(); i += 1) {
                if (hiddenStr.charAt(i) == guessStr.charAt(i)) {
                    //increment frog
                    F = F + 1;
                    //Change Frogs to "F" so they won't be compared for Toadworthiness
                    hiddenSB.setCharAt(i, (char) 70);
                    guessSB.setCharAt(i, (char) 70);
                }
                //update strings
                hiddenStr = hiddenSB.toString();
                guessStr = guessSB.toString();
            }

            //check for toads
            for (int i = 0; i < guessStr.length(); i++) {
                char g = guessStr.charAt(i);
                //check only integers, so Frogs marked F are ignores
                if (Character.isDigit(g)) {
                    int hit = hiddenStr.indexOf(g);
                    if (hit > -1) {
                        //increment toads
                        T = T + 1;
                        // //mark it as a toad in the hidden string, to avoid possibleduplicates
                        hiddenSB = new StringBuilder(hiddenStr);
                        hiddenSB.setCharAt(hit, (char) 84);
                        hiddenStr = hiddenSB.toString();
                    }
                }
            }

            frogsAndToadsCount = F + "F" + T + "T";
        }

        return frogsAndToadsCount;

    }


    private boolean validateNumber(String numberStr) {
        boolean returnedValue = false;
        if (numberStr.length() == 4 && StringUtils.isNumeric(numberStr)) {
            returnedValue = true;
        }
        return returnedValue;
    }


    public boolean assertHint(int caseNumber, String results, String expected) {
        boolean correct = results.equals(expected);
        System.out.println("Expected value = " + expected + " Actual value = " + results);
        System.out.println("Case " + caseNumber + " = " + (correct ? "passed" : "fail"));
        return results.equals(expected);
    }

    public void testCases() {

        //0F0T

        int p = 0;
        int f = 0;
        String case1 = getHint("1807", "7810");
        assertHint(1, case1, "1F3T");
        String case2 = getHint("1807", "2805");
        assertHint(2, case2, "2F0T");
        String case3 = getHint("1807", "9807");
        assertHint(3, case3, "3F0T");
        String case4 = getHint("1807", "1807");
        assertHint(4, case4, "4F0T");        //insert additional test cases here}}

        //Test all possible combinations of xFxT
        //xF0T
        String case5 = getHint("5555", "4967");
        assertHint(5, case5, "0F0T");
        String case6 = getHint("5555", "5444");
        assertHint(6, case6, "1F0T");
        String case7 = getHint("5555", "5050");
        assertHint(7, case7, "2F0T");
        String case8 = getHint("5555", "5559");
        assertHint(8, case8, "3F0T");
        String case9 = getHint("5555", "5555");
        assertHint(9, case9, "4F0T");
        //0FxT
        String case10 = getHint("1234", "4567");
        assertHint(10, case10, "0F1T");
        String case11 = getHint("1234", "4390");
        assertHint(11, case11, "0F2T");
        String case12 = getHint("1234", "4329");
        assertHint(12, case12, "0F3T");
        String case13 = getHint("1234", "4321");
        assertHint(13, case13, "0F4T");
        //1Fxt
        String case14 = getHint("9876", "9600");
        assertHint(14, case14, "1F1T");
        String case15 = getHint("9876", "9680");
        assertHint(15, case15, "1F2T");
        String case16 = getHint("9876", "9687");
        assertHint(16, case16, "1F3T");
        //2FxT
        String case17 = getHint("1887", "1814");
        assertHint(17, case17, "2F0T");
        String case18 = getHint("1887", "1872");
        assertHint(18, case18, "2F1T");
        String case19 = getHint("1887", "1878");
        assertHint(19, case19, "2F2T");
        //3F0T
        String case20 = getHint("1887", "1883");
        assertHint(20, case20, "3F0T");

        //test for invalid input in hidden
        String case21 = getHint("184", "1887");
        assertHint(21, case21, "Invalid Input");
        String case22 = getHint("18743", "1885");
        assertHint(22, case22, "Invalid Input");
        String case23 = getHint("five", "1886");
        assertHint(23, case23, "Invalid Input");
//test for invalid input in guess
        String case24 = getHint("1234", "4");
        assertHint(24, case24, "Invalid Input");
        String case25 = getHint("0000", "654321");
        assertHint(25, case25, "Invalid Input");
        String case26 = getHint("0000", "four");
        assertHint(26, case26, "Invalid Input");
//test for non-integer numbers
        String case27 = getHint("3.14", "1234");
        assertHint(27, case27, "Invalid Input");
        String case28 = getHint("", "1234");
        assertHint(28, case28, "Invalid Input");

        String case29 = getHint("0123", "");
        assertHint(29, case29, "Invalid Input");
        String case30 = getHint("0123", "12.5");
        assertHint(30, case30, "Invalid Input");

        //Check cases where frogs and toads could get mixed up.
        String case31 = getHint("0100", "1000");
        assertHint(31, case31, "2F2T");
        String case32 = getHint("0100", "0101");
        assertHint(32, case32, "3F0T");
        String case33 = getHint("0101", "1001");
        assertHint(33, case33, "2F2T");

        String case34 = getHint("1AC3", "7354");
        assertHint(34, case34, "Invalid Input");
        String case35 = getHint("1233", "735A");
        assertHint(35, case35, "Invalid Input");
    }
}