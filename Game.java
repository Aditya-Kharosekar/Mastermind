package mastermind;

import java.util.Random;

public class Game {

    public String generateSecretCode() {
        Random generator = new Random();
        String result="";
        int index, numberOfPegs=GameConfiguration.pegNumber;
        String[] colors=GameConfiguration.colors;
        for(int i=0; i<numberOfPegs; i++)
        {
            index=generator.nextInt(colors.length);
            result+=colors[index];
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
}
