package JavaDir;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;

/** 
 * a popup to ask user for yes/no question
*/

public class GuiConfirmWindow {
    private boolean isTrue;
    public static final String EXIT_TITLE = "Are You Sure?";
    public static final String EXIT_MESSAGE = "Exiting the Program";

    public boolean isTrue() {
        return isTrue;
    }

    /**
     * to display the window
     * @param title
     * @param message
     * @return 
     */
    public void display(String title, String message){
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Keys.ICON));
        stage.setResizable(false);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);

        Label labelMessage = new Label();
        labelMessage.setId("layoutFont");
        labelMessage.setText(message);
        labelMessage.setPadding(new Insets(5));
        labelMessage.setAlignment(Pos.CENTER);

        Button buttonYes = new Button();
        buttonYes.setText("Yes");
        buttonYes.setMinSize(150, 50);
        buttonYes.setAlignment(Pos.CENTER);
        buttonYes.setOnAction(e -> {
            isTrue = true;
            stage.close();
        });

        Button buttonNo = new Button();
        buttonNo.setText("No");
        buttonNo.setMinSize(150, 50);
        buttonNo.setAlignment(Pos.CENTER);
        buttonNo.setOnAction(e -> {
            isTrue = false;
            stage.close();
        });

        GridPane gridAlert = new GridPane();
        GridPane.setConstraints(labelMessage, 0, 0);
        GridPane.setConstraints(buttonYes, 1, 1);
        GridPane.setConstraints(buttonNo, 0, 1);
        GridPane.setColumnSpan(labelMessage, 2);
        GridPane.setHalignment(labelMessage, HPos.CENTER);
        gridAlert.setPadding(new Insets(5));
        gridAlert.getChildren().addAll(labelMessage, buttonYes, buttonNo);
        gridAlert.setVgap(5);
        gridAlert.setHgap(5);

        Scene sceneAlert = new Scene(gridAlert);
        sceneAlert.getStylesheets().add(Keys.STYLING_FILE);

        stage.setScene(sceneAlert);
        stage.showAndWait();
    }
}
