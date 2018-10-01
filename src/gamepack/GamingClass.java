package gamepack;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javax.swing.*;

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
    private int delay = 500;
    private KeyCode currentKeyCode = KeyCode.RIGHT;
    private KeyCode lastKeyCode;

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

        Gaming();
        timer.start();
    }

    public void Gaming() {
        anchorPane.setOnKeyPressed(event ->{
            if (moved) {
                lastKeyCode = currentKeyCode;
                moved = false;
            }
            if (event.getCode() != lastKeyCode) {



                currentKeyCode = event.getCode();

                switch (currentKeyCode) {

                    case UP:
                        //System.out.println("UP");

                        if (lastKeyCode != KeyCode.DOWN) {
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
                        if (lastKeyCode != KeyCode.UP) {
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
                        if (lastKeyCode != KeyCode.RIGHT) {
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
                        if (lastKeyCode != KeyCode.LEFT) {
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
                }
            } else {

            }
        });


        timer = new Timer(delay, e -> {
            //System.out.println(currentKeyCode);
            //System.out.println(whereToGoHUMAN() + " <GO");
            anchorPane.setDisable(true);

            //lastKeyCode = currentKeyCode;
            oldChild = newChild;
            switch (whereToGoHUMAN()) {
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
            System.out.println("before:" + newChild);
            newChild = borderControl(newChild);
            System.out.println("after:" + newChild);
            System.out.println("                               ");

            try {


                arrayList.get(newChild).getImage().setOpacity(1);
                arrayList.get(oldChild).getImage().setOpacity(0);
                moved = true;
            } catch (IndexOutOfBoundsException asd) {
                asd.printStackTrace();
                newChild = 21;
                oldChild = 20;
                lastKeyCode = KeyCode.DOWN;
                currentKeyCode = KeyCode.RIGHT;

            }


            anchorPane.setDisable(false);


        });



    }

    public KeyCode whereToGoHUMAN() {
        //System.out.println(upGoing + " " + downGoing +" " + leftGoing + " " +rightGoing);
        if (upGoing) return KeyCode.UP;
        else if (downGoing) return KeyCode.DOWN;
        else if (leftGoing) return KeyCode.LEFT;
        else if (rightGoing) return KeyCode.RIGHT;
        else return KeyCode.RIGHT;
    }

    public int borderControl(int num) {
        boolean containsUP= false;
        boolean containsDOWN= false;
        boolean containsLEFT= false;
        boolean containsRIGHT= false;
        switch (whereToGoHUMAN()) {
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
            System.out.println("containsUP:" + containsUP);
            return num + 440;
        } else if (containsDOWN) {
            System.out.println("containsDOWN:" + containsDOWN);
            return num - 440;
        } else if (containsLEFT) {
            System.out.println("containsLEFT:" + containsLEFT);

            return num + 20;
        } else if (containsRIGHT) {
            System.out.println("containsRIGHT:" + containsRIGHT);
            return num - 20;
        } else {
            return num;
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
