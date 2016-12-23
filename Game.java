package mastermind;

import java.util.Random;

public class Game {

    public static final String[] colors = {"B", "G", "O", "P", "R", "Y"};

    public String generateSecretCode() {
        Random generator = new Random();
        String result="";
        int index, numberOfPegs=4;
        String[] c=colors;
        for(int i=0; i<numberOfPegs; i++)
        {
            index=generator.nextInt(c.length);
            result+=c[index];
        }
        return result;
    }

    public int[] codeToInts(String code) {
        int[] codeInInts = new int[4];
        char[] chars = code.toCharArray();

        for (int i=0; i < 4; i++) {
            switch (chars[i]) {
                case 'B': {
                    codeInInts[i] = 0; break;
                }
                case 'G': {
                    codeInInts[i] = 1; break;
                }
                case 'O': {
                    codeInInts[i] = 2; break;
                }
                case 'P': {
                    codeInInts[i] = 3; break;
                }
                case 'R': {
                    codeInInts[i] = 4; break;
                }
                case 'Y': {
                    codeInInts[i] = 5; break;
                }
            }
        }
        return codeInInts;
    }

    public int[] getFeedback(int[] code, int[] nums) {

        int[] tempCode = new int[4];
        int[] tempNums = new int[4];
        for (int i=0; i < 4; i++) {
            tempCode[i] = code[i];
            tempNums[i] = nums[i];
        }
        int blackPegs = 0;
        for (int i=0; i < code.length; i++) {
            if (tempCode[i]==tempNums[i]) {
                blackPegs++;
                tempCode[i]=-1;
                tempNums[i]=-1;
            }
        }

        int whitePegs = 0;
        for (int i=0; i < code.length; i++) {
            if (tempCode[i]==-1) {
                continue;
            }
            for (int j=0;j < code.length; j++) {
                if (tempNums[j]==-1) {
                    continue;
                }
                if (tempCode[i]==tempNums[j]) {
                    whitePegs++;
                    tempCode[i]=-1;
                    tempNums[j]=-1;
                }
            }
        }
        int[] result = new int[2];
        result[0] = blackPegs;
        result[1] = whitePegs;

        return result;
    }

}
