package mastermind;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import static javafx.scene.layout.AnchorPane.setLeftAnchor;
import static javafx.scene.layout.AnchorPane.setTopAnchor;

public class GUI {

    public int[] nums;
    HBox[] guesses;
    int index; //required to set location for corresponding guess circles
    int guessNum;
    Game game;
    int[] code;
    String c;

    GUI() {
        nums = new int[] {-1, -1, -1, -1}; //These have to be -1 because in my changeColor function,
                                           //I update the color index and then display the color
        guesses = new HBox[10];
        index = 13; //this is 13 only because it worked well with the spacing
        game = new Game();
        c = game.generateSecretCode();
        code = game.codeToInts(c);
        guessNum = 1;
    }
    /**
     * Creates and displays welcome screen. This screen will show the rules of the game
     * and will have a "Start game" button so the user can start playing.
     * It will also have an "Exit" button so the user can quit instead
     */
    public void createWelcomeScreen() {
        Stage stage = new Stage();
        stage.setTitle("Welcome");
        AnchorPane welcomeScreen = new AnchorPane();
        Scene scene = new Scene(welcomeScreen, 520, 550);
        Label rules = new Label();
        setTopAnchor(rules, 15.0);
        setLeftAnchor(rules, 80.0);
        rules.setText("Welcome to Mastermind.\n" +
                "\n" +
                "The objective of the game is to correctly guess a secret code consisting\n" +
                "of a series of 4 colored pegs.\n\n" +
                "Each peg will be of one of 6 colors – Blue, Green, Orange, Purple, Red,\nand Yellow.\n" +
                "More than one peg in the secret code could be of the same color.\nYou must guess the correct color " +
                "and order of the code.\n\n" +
                "You will have 10 chances to correctly guess the code.\n\nAfter every guess, the computer " +
                "will provide you feedback in the\nform of 0 to 4 colored pegs. A black peg indicates " +
                "that a peg in your guess is\nof the correct color and is in the correct position.\n" +
                "A white peg indicates that a peg in your guess is of the correct color\n" +
                "but is not in the correct position.\n\n" +
                "NOTE: The order of the feedback pegs does not correspond to either the\npegs in the code " +
                "or the pegs in your guess. " +
                "\nIn other words, the color of the pegs is important, not the order they are in.\n\n" +
                "Are you ready?");
        welcomeScreen.getChildren().add(rules);

        Button play = new Button("Start Game");
        setTopAnchor(play, 420.0);
        setLeftAnchor(play, 80.0);
        play.setPrefHeight(50.0);
        play.setPrefWidth(120.0);
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
                createGameScreen();

            }
        });
        Button quit = new Button("Quit");
        setTopAnchor(quit, 420.0);
        setLeftAnchor(quit, 330.0);
        quit.setPrefWidth(120.0);
        quit.setPrefHeight(50.0);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
                System.exit(0);
            }
        });

        welcomeScreen.getChildren().addAll(play,quit);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates and displays game screen
     */
    public void createGameScreen() {
        Stage stage = new Stage();
        stage.setTitle("Mastermind");
        AnchorPane gameScreen = new AnchorPane();
        Scene scene = new Scene(gameScreen, 1000, 650);

        /*
        This part of the code creates the circles which the player will use to select their guess
         */
        HBox options = new HBox();
        options.setSpacing(30.0);
        Circle c1 = new Circle(20.0);
        c1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleColor(c1, 0); //0 denotes that the first circle's color should be changed
            }
        });
        Circle c2 = new Circle(20.0);
        c2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleColor(c2, 1);
            }
        });
        Circle c3 = new Circle(20.0);
        c3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleColor(c3, 2);
            }
        });
        Circle c4 = new Circle(20.0);
        c4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toggleColor(c4, 3);
            }
        });
        options.getChildren().addAll(c1, c2, c3, c4);
        setTopAnchor(options, 30.0);
        setLeftAnchor(options, 270.0);


        Button submit = new Button("Submit Guess");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (index>3) { //this will ensure that player only has 10 guesses
                                  /*
                                  This is an ugly way to do this. I will work on this later
                                   */
                    createGuessCircles(gameScreen, stage);
                }
                else {
                    playAgainScreen(false, stage);

                    for (int i=0; i < 4; i++) {
                        System.out.print(code[i]);
                    }
                }
            }
        });
        submit.setPrefHeight(50.0);
        submit.setPrefWidth(120.0);
        setTopAnchor(submit, 25.0);
        setLeftAnchor(submit, 560.0);
        gameScreen.getChildren().add(submit);

        gameScreen.getChildren().add(options);
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This function is responsible for the different colors that the guess circles can have.
     * Calls changeColor within which is the function actually responsible for assigning colors to circles
     * @param c Circle whose color needs to be changed
     * @param index denotes which color the circle currently has. Order of colors corresponds
     *              to order in GameConfiguration.colors array
     *              i.e. 0-Blue, 1-Green, 2-Orange, 3-Purple, 4-Red, 5-Yellow
     */
    public void toggleColor(Circle c, int index) {
        if (nums[index]==5) { //there are only 6 color options
            nums[index]=0;
        }
        else {
            nums[index]++;
        }
        changeColor(c, index);
    }

    /**
     * Assigns colors to each circle
     * @param c Circle whose color needs to be changed
     * @param index denotes which color the circle currently has. Order of colors corresponds
     *              to order in GameConfiguration.colors array
     *              i.e. 0-Blue, 1-Green, 2-Orange, 3-Purple, 4-Red, 5-Yellow
     */
    public void changeColor(Shape c, int index)  {
        switch (nums[index]) {
            case 0: {
                c.setFill(Color.BLUE); break;
            }
            case 1: {
                c.setFill(Color.GREEN); break;
            }
            case 2: {
                c.setFill(Color.ORANGE); break;
            }
            case 3: {
                c.setFill(Color.PURPLE); break;
            }
            case 4: {
                c.setFill(Color.RED); break;
            }
            case 5: {
                c.setFill(Color.YELLOW); break;
            }
        }
    }

    public void createGuessCircles(AnchorPane a, Stage stage) {
        HBox guess = new HBox();
        guess.setSpacing(30.0);
        Label whichGuess = new Label();
        whichGuess.setText("" + guessNum);
        guessNum++;
        Circle c1 = new Circle(20.0);
        changeColor(c1, 0);
        Circle c2 = new Circle(20.0);
        changeColor(c2, 1);
        Circle c3 = new Circle(20.0);
        changeColor(c3, 2);
        Circle c4 = new Circle(20.0);
        changeColor(c4, 3);

        Separator s = new Separator();
        s.setOrientation(Orientation.VERTICAL);

        guess.getChildren().addAll(whichGuess, c1, c2, c3, c4, s);

        int[] feedback = game.getFeedback(code, nums);

        if (feedback[0]==4) {
            playAgainScreen(true, stage);
        }
        for (int i=0; i < 4; i++) {
            Rectangle r = new Rectangle(30.0, 30.0);
            guess.getChildren().add(r);
            if (feedback[0] > 0) {
                r.setFill(Color.BLACK);
                feedback[0]--;
            }
            else if (feedback[1] > 0) {
                r.setFill(Color.WHITE);
                feedback[1]--;
            }
            else {
                r.setFill(Color.PINK);
            }
        }

        if (guessNum!=11) {
            setLeftAnchor(guess, 220.0);
        }
        else {
            setLeftAnchor(guess, 214.0);
        }
        index--; //I want the first guess to be at the bottom of the page
                    //and later guesses to move upwards
        setTopAnchor(guess, index * 50.0);

        a.getChildren().add(guess);

    }

    public void playAgainScreen(boolean won, Stage s) {
        Stage stage = new Stage();
        AnchorPane playAgain = new AnchorPane();
        Scene scene = new Scene(playAgain, 300, 100);

        Label message = new Label();
        setTopAnchor(message, 10.0);
        setLeftAnchor(message, 105.0);
        if (won) {
            stage.setTitle("You Win!");
            message.setText("Congrats! You Win!");
        }
        else {
            stage.setTitle("You Lose!");
            message.setText("Sorry! You lose!");
        }
        Button play = new Button("Play Again");
        setTopAnchor(play, 60.0);
        setLeftAnchor(play, 40.0);
        play.setPrefHeight(15.0);
        play.setPrefWidth(80.0);
        play.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                s.close();
                stage.close();
                (new GUI()).createGameScreen();
            }
        });
        Button quit = new Button("Quit");
        setTopAnchor(quit, 60.0);
        setLeftAnchor(quit, 180.0);
        quit.setPrefHeight(15.0);
        quit.setPrefWidth(80.0);
        quit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        playAgain.getChildren().addAll(message, play, quit);


        stage.setScene(scene);
        stage.show();
    }
}
