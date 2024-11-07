package JavaDir;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * first window
 * show the login/register selections which are:
 * 1. login as admin
 * 2. login as tutor
 * 3. register as tutor
 * 4. login as student
 * 5. register as student
*/

public class GuiSelect {
    
    /**
     * display the window to users
     * @return
     */
    public static void display() {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Keys.ICON));
        stage.setResizable(false);
        stage.setTitle("Motor Driving School Mode Selection");
        stage.setOnCloseRequest(e-> {
            e.consume();
            GuiConfirmWindow guiConfirm = new GuiConfirmWindow();
            guiConfirm.display(GuiConfirmWindow.EXIT_TITLE, GuiConfirmWindow.EXIT_MESSAGE);
            if (guiConfirm.isTrue()) {
                stage.close();
            }
        });

        // labels for select scene
        Label labelTopMode = new Label("Select the Following");
        labelTopMode.setId("layoutFont");
        labelTopMode.setAlignment(Pos.CENTER);

        // redirect to login as admin
        Button buttonAdmin = new Button("Login as Admin");
        buttonAdmin.setId("buttonFlat"); // set style
        buttonAdmin.setPrefSize(250, 500);
        buttonAdmin.setOnAction(e -> {
            GuiLogin guiLogin = new GuiLogin('a');
            guiLogin.display();
            stage.close();
        });

        // redirect to login as tutor
        Button buttonLoginTutor = new Button("Login as Tutor");
        buttonLoginTutor.setId("buttonFlat");
        buttonLoginTutor.setPrefSize(250, 300);
        buttonLoginTutor.setOnAction(e -> {
            GuiLogin guiLogin = new GuiLogin('t');
            guiLogin.display();
            stage.close();
        });

        // redirect to register as tutor
        Button buttonRegisterTutor = new Button("Register as Tutor");
        buttonRegisterTutor.setId("buttonFlat");
        buttonRegisterTutor.setPrefSize(250, 150);
        buttonRegisterTutor.setOnAction(e -> {
            GuiRegister guiRegister = new GuiRegister('t');
            guiRegister.display();
            stage.close();
        });
        
        // redirect to login as student
        Button buttonLoginStudent = new Button("Login as Student");
        buttonLoginStudent.setId("buttonFlat");
        buttonLoginStudent.setPrefSize(250, 300);
        buttonLoginStudent.setOnAction(e -> {
            GuiLogin guiLogin = new GuiLogin('s');
            guiLogin.display();
            stage.close();
        });

        // redirect to register as student
        Button buttonRegisterStudent = new Button("Register as Student");
        buttonRegisterStudent.setId("buttonFlat");
        buttonRegisterStudent.setPrefSize(250, 150);
        buttonRegisterStudent.setOnAction(e -> {
            GuiRegister guiRegister = new GuiRegister('s');
            guiRegister.display();
            stage.close();
        });

        // layout for select scene
        StackPane stackMode = new StackPane();
        stackMode.setPadding(new Insets(10,10,10,10));
        stackMode.getChildren().add(labelTopMode);

        // setting up grid for select mode scene
        GridPane gridMode = new GridPane();
        GridPane.setRowSpan(buttonAdmin, 2);
        GridPane.setConstraints(buttonAdmin, 0, 0);
        GridPane.setConstraints(buttonLoginTutor, 1, 0);
        GridPane.setConstraints(buttonRegisterTutor, 1, 1);
        GridPane.setConstraints(buttonLoginStudent, 2, 0);
        GridPane.setConstraints(buttonRegisterStudent, 2, 1);
        gridMode.setHgap(20);
        gridMode.setVgap(50);
        gridMode.setPadding(new Insets(5, 10, 10, 10));
        gridMode.getChildren().addAll(buttonAdmin, buttonLoginTutor, buttonRegisterTutor, 
                                      buttonLoginStudent, buttonRegisterStudent);
        gridMode.setAlignment(Pos.CENTER);

        // layout borderpane
        BorderPane borderPaneMode = new BorderPane();
        borderPaneMode.setTop(stackMode);
        borderPaneMode.setCenter(gridMode);
        BorderPane.setAlignment(stackMode, Pos.CENTER);
        BorderPane.setAlignment(gridMode, Pos.CENTER);

        // login scene
        Scene sceneMode = new Scene(borderPaneMode, 900, 600);
        sceneMode.getStylesheets().add(Keys.STYLING_FILE);

        stage.setScene(sceneMode);
        stage.show();
    }
}
