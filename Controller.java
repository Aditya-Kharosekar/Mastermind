package mastermind;

import javafx.application.Application;
import javafx.stage.Stage;

public class Controller {
    public static class Main extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception{
            (new GUI()).createWelcomeScreen();
        }


        public static void main(String[] args) {
            launch(args);
        }
    }
}
