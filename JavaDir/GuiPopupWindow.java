package JavaDir;

import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Modality;

/**
 * showing popup to show message
 */

public class GuiPopupWindow{

    /**
     * display the popup window
     * @param title
     * @param message
     * @return
     */
    public static void display(String title, String message){
        Stage stagePopup = new Stage();
        stagePopup.getIcons().add(new Image(Keys.ICON));
        stagePopup.setResizable(false);
        stagePopup.setTitle(title);
        stagePopup.initModality(Modality.APPLICATION_MODAL);

        Label labelMessage = new Label();
        labelMessage.setId("layoutFont");
        labelMessage.setText(message);
        labelMessage.setMinSize(200, 100);
        labelMessage.setAlignment(Pos.CENTER);

        Button buttonAlert = new Button();
        buttonAlert.setText("OK");
        buttonAlert.setMinSize(200, 50);
        buttonAlert.setOnAction(e -> {
            stagePopup.close();
        });
        buttonAlert.setAlignment(Pos.CENTER);

        VBox gridAlert = new VBox();
        gridAlert.setPadding(new Insets(5));
        gridAlert.getChildren().addAll(labelMessage, buttonAlert);
        gridAlert.setAlignment(Pos.CENTER);

        Scene sceneAlert = new Scene(gridAlert);
        sceneAlert.getStylesheets().add(Keys.STYLING_FILE);

        stagePopup.setScene(sceneAlert);
        stagePopup.showAndWait();
    }
}
