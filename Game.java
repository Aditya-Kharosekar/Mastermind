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
}
