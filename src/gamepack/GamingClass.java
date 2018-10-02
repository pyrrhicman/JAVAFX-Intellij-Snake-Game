package gamepack;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javax.swing.*;

import static javafx.scene.input.KeyCode.*;

public class GamingClass implements Initializable {
    private final int TOTALX = 22;
    private final int TOTALY = 22;
    private final int TOTALCUBE = TOTALX*TOTALY;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private JFXButton start;
    @FXML
    private JFXButton quit;
    @FXML
    private Text text;
    @FXML
    private VBox vBox;
    @FXML
    private GridPane gridPane;
    @FXML
    private Pane showpane;
    private double xOffset = 0;
    private double yOffset = 0;
    private int snakeLength = 4;
    private Timer timer;
    private int delay = 100;
    private KeyCode currentKeyCode = RIGHT;
    private KeyCode lastKeyCode;
    private KeyCode readedCode;


    private int newChild = 1;
    private int oldChild = 0;

    private boolean leftGoing = false;
    private boolean rightGoing = false;
    private boolean upGoing = false;
    private boolean downGoing = false;
    private ArrayList<PictureClass> arrayList = new ArrayList<>();
    private boolean moved = false;

    private int[] forbiddenBlocksUP = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    private int[] forbiddenBlocksDOWN = {482,481,480,479,478,477,476,475,474,473,472,471,470,469,468,467,466,465,464,463};
    private int[] forbiddenBlocksLEFT = {22,44,66,88,110,132,154,176,198,220,242,264,286,308,330,352,374,396,418,440};
    private int[] forbiddenBlocksRIGHT= {43,65,87,109,131,153,175,197,219,241,263,285,305,329,351,373,395,417,439,461};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        anchorPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        anchorPane.setOnMouseDragged(event -> {
            anchorPane.getScene().getWindow().setX(event.getScreenX() - xOffset);
            anchorPane.getScene().getWindow().setY(event.getScreenY() - yOffset);
            anchorPane.getScene().getWindow().setOpacity(0.8);
        });
        anchorPane.setOnMouseReleased(event -> {
            anchorPane.getScene().getWindow().setOpacity(1);
           // vBox.getChildren().add(snakeHead);
        });


        for (int counter = 0; counter < TOTALCUBE; counter++) {
        arrayList.add(new PictureClass());
        }
        resetALL();
        KeyHandling();
        Gaming();
        timer.start();
    }

    public void KeyHandling() {
        anchorPane.setOnKeyPressed(event ->{
            readedCode = event.getCode();
            if (readedCode != currentKeyCode & readedCode != giveMeReverse(currentKeyCode) &moved) {
                currentKeyCode = readedCode;
                moved = false;
                System.out.println("entered: " + currentKeyCode);
            }

            /*if (moved) {
                lastKeyCode = currentKeyCode;
                moved = false;
            }*/

                //currentKeyCode = event.getCode();

                /*switch (currentKeyCode) {

                    case UP:
                        //System.out.println("UP");

                        if (lastKeyCode != DOWN) {
                            upGoing = true;
                            downGoing = false;
                            leftGoing = false;
                            rightGoing = false;
                        }else {
                            upGoing = false;
                            downGoing = true;
                            leftGoing = false;
                            rightGoing = false;
                        }
                        break;




                    case DOWN:
                        //System.out.println("DOWN");
                        if (lastKeyCode != UP) {
                            upGoing = false;
                            downGoing = true;
                            leftGoing = false;
                            rightGoing = false;
                        }else {
                            upGoing = true;
                            downGoing = false;
                            leftGoing = false;
                            rightGoing = false;
                        }
                        break;

                    case LEFT:
                        //System.out.println("LEFT");
                        if (lastKeyCode != RIGHT) {
                            upGoing = false;
                            downGoing = false;
                            leftGoing = true;
                            rightGoing = false;
                        }else {
                            upGoing = false;
                            downGoing = false;
                            leftGoing = false;
                            rightGoing = true;
                        }
                        break;

                    case RIGHT:
                        //System.out.println("RIGHT");
                        if (lastKeyCode != LEFT) {
                            upGoing = false;
                            downGoing = false;
                            leftGoing = false;
                            rightGoing = true;
                        }else {
                            upGoing = false;
                            downGoing = false;
                            leftGoing = true;
                            rightGoing = false;
                        }
                        break;
                }*/
        });
    }






    public void Gaming() {

        timer = new Timer(delay, e -> {

            //System.out.println(currentKeyCode);
            //System.out.println(whereToGoHUMAN() + " <GO");
            anchorPane.setDisable(true);

            //lastKeyCode = currentKeyCode;
            oldChild = newChild;
            switch (currentKeyCode) {
                //switch (whereToGoHUMAN()) {

                    case UP:
                    newChild -= TOTALY;
                    break;
                case DOWN:
                    newChild += TOTALY;
                    break;
                case LEFT:
                    newChild--;
                    break;
                case RIGHT:
                    newChild++;
                    break;

            }
            newChild = borderControl(newChild);


            try {
                /*FadeTransition ftnewChild = new FadeTransition(Duration.millis(1000), arrayList.get(newChild).getImage());
                ftnewChild.setFromValue(0);
                ftnewChild.setToValue(1);
                ftnewChild.setCycleCount(0);
                ftnewChild.setAutoReverse(true);

                ftnewChild.play();*/

                arrayList.get(newChild).getImage().setOpacity(1);
                /*FadeTransition ftoldChild = new FadeTransition(Duration.millis(10), arrayList.get(oldChild).getImage());
                ftoldChild.setFromValue(0.2);
                ftoldChild.setToValue(0);
                ftoldChild.setCycleCount(0);
                ftoldChild.setAutoReverse(true);

                ftoldChild.play();*/
                arrayList.get(oldChild).getImage().setOpacity(0);
                moved = true;

            } catch (IndexOutOfBoundsException asd) {
                asd.printStackTrace();
                newChild = 21;
                oldChild = 20;
                 lastKeyCode = DOWN;
                currentKeyCode = RIGHT;

            }


            anchorPane.setDisable(false);


        });



    }

    public KeyCode whereToGoHUMAN() {
        //System.out.println(upGoing + " " + downGoing +" " + leftGoing + " " +rightGoing);
        if (upGoing) return UP;
        else if (downGoing) return DOWN;
        else if (leftGoing) return LEFT;
        else if (rightGoing) return RIGHT;
        else return RIGHT;
    }


    public KeyCode giveMeReverse(KeyCode keyCode) {
        //System.out.println(upGoing + " " + downGoing +" " + leftGoing + " " +rightGoing);
        if (keyCode == UP) return DOWN;
        else if (keyCode == DOWN) return UP;
        else if (keyCode == RIGHT) return LEFT;
        else if (keyCode == LEFT) return RIGHT;
        return DOWN;
    }

    public int borderControl(int num) {
        boolean containsUP= false;
        boolean containsDOWN= false;
        boolean containsLEFT= false;
        boolean containsRIGHT= false;
        switch (currentKeyCode) {
            case UP:
                containsUP = IntStream.of(forbiddenBlocksUP).anyMatch(x -> x == num);
                break;
            case DOWN:
                containsDOWN = IntStream.of(forbiddenBlocksDOWN).anyMatch(x -> x == num);
                break;
            case LEFT:
                containsLEFT = IntStream.of(forbiddenBlocksLEFT).anyMatch(x -> x == num);
                break;
            case RIGHT:
                containsRIGHT = IntStream.of(forbiddenBlocksRIGHT).anyMatch(x -> x == num);
                break;
        }

        if (containsUP) {
            return num + 440;
        } else if (containsDOWN) {
            return num - 440;
        } else if (containsLEFT) {
            return num + 20;
        } else if (containsRIGHT) {
            return num - 20;
        } else {
            return num;
        }


    }


    public void resetALL() {
        int countIT = 0;
        for (int counterY = 0; counterY < TOTALY; counterY++) {
            for (int counterX = 0; counterX < TOTALX; counterX++) {
                gridPane.add(arrayList.get(countIT).getImage(), counterX, counterY);

                arrayList.get(countIT).getImage().setOpacity(0);
                countIT++;
                if (countIT >= TOTALCUBE) {
                    countIT = 0;
                }
            }
        }
    }
    public void starting () {
        start.setDisable(true);
        quit.setDisable(true);
        vBox.setOpacity(0);


    }
    public void quiting () {
        Stage stage =(Stage)this.start.getScene().getWindow();
        stage.close();
        System.exit(0);

    }
}
