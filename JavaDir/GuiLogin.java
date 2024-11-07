package JavaDir;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

/** 
 * to let the user to login to their account
*/

public class GuiLogin {
    private Stage stage;
    private char selectedMode;
    private Student student;
    private Tutor tutor;

    GuiLogin() {}
    GuiLogin(char selectedMode) {
        this.selectedMode = selectedMode;
    }

    /**
     * to display the window
     * @return
     */
    public void display() {
        String text = "Person";
        if      (selectedMode == 'a') text = "Admin";
        else if (selectedMode == 't') text = "Tutor";
        else if (selectedMode == 's') text = "Student";

        stage = new Stage();
        stage.getIcons().add(new Image(Keys.ICON));
        stage.setResizable(false);
        stage.setTitle("Login as " + text);
        stage.setOnCloseRequest(e-> {
            e.consume();
            GuiConfirmWindow guiConfirm = new GuiConfirmWindow();
            guiConfirm.display(GuiConfirmWindow.EXIT_TITLE, GuiConfirmWindow.EXIT_MESSAGE);
            if (guiConfirm.isTrue()) {
                stage.close();
            }
        });

        // Labels and fields for login scene
        Label labelLoginID = new Label("IC/Passport");
        Label labelPassword = new Label("Password");
        labelLoginID.setId("layoutFont");
        labelPassword.setId("layoutFont");

        if (selectedMode == 'a')
            labelLoginID.setText("Admin ID");

        TextField fieldLoginID = new TextField();
        fieldLoginID.setPromptText("Enter IC/Passport");

        PasswordField fieldPassword = new PasswordField();
        fieldPassword.setPromptText("Enter Password");
        
        Button buttonLogin = new Button("Login");
        buttonLogin.setDefaultButton(true); // pressing enter key will launch this button

        // button to login
        buttonLogin.setOnAction(e -> {
            if (checkData(fieldLoginID.getText(), fieldPassword.getText())) {
                if (selectedMode == 'a') {
                    GuiMenuAdmin guiAdmin = new GuiMenuAdmin();
                    guiAdmin.display();
                    stage.close();
                } else if (selectedMode == 't') {
                    GuiMenuTutor guiTutor = new GuiMenuTutor(tutor);
                    guiTutor.display();
                    stage.close();
                } else if (selectedMode == 's') {
                    GuiMenuStudent guiStudent = new GuiMenuStudent(student);
                    guiStudent.display();
                    stage.close();
                }
            } else {
                System.out.println("not found");
            }
        });

        // button back to select mode
        Button buttonBack = new Button("Back");
        buttonBack.setOnAction(e -> {
            GuiSelect.display();
            stage.close();
        });

        // gridpane used in login
        GridPane gridLogin = new GridPane();
        GridPane.setConstraints(labelLoginID, 0, 0);
        GridPane.setConstraints(labelPassword, 0, 1);
        GridPane.setConstraints(fieldLoginID, 1, 0);
        GridPane.setConstraints(fieldPassword, 1, 1);
        GridPane.setConstraints(buttonLogin, 1, 2);
        GridPane.setConstraints(buttonBack, 0, 2);

        // adding up all nodes inside gridlogin
        gridLogin.setAlignment(Pos.CENTER);
        gridLogin.setVgap(20);
        gridLogin.setHgap(50);
        gridLogin.getChildren().addAll(labelLoginID, labelPassword, 
                                       fieldLoginID, fieldPassword, 
                                       buttonLogin,  buttonBack);

        // scene for login window
        Scene sceneLogin = new Scene(gridLogin, 400, 200);
        sceneLogin.getStylesheets().add(Keys.STYLING_FILE);

        stage.setScene(sceneLogin);
        stage.show();
    }

    /**
     * to check the account exists or not
     * @param id
     * @param password
     * @return
     */
    private boolean checkData(String id, String password) {
        // check whether the id and password match or not
        // redirect to one of the menus if correct id and password
        // show error on popup window if incorrect id/password

        try {
            if (selectedMode == 'a')
                if ((Finder.admin.getIc().equals(id)) && 
                    (Finder.admin.getPassword().equals(password)))
                    return true; 
                else {
                    GuiPopupWindow.display("Error", "Incorrect Admin ID/Password");
                    return false;
                }
            else if (selectedMode == 't') {
                tutor = Finder.getTutor(id);
                if (tutor.getPassword().equals(password))
                    return true;
                else {
                    GuiPopupWindow.display("Error", "Incorrect Password");
                    return false;
                }
            }
            else if (selectedMode == 's') {
                student = Finder.getStudent(id);
                if (student.getPassword().equals(password))
                    return true;
                else {
                    GuiPopupWindow.display("Error", "Incorrect Password");
                    return false;
                }
            }
            return false;
        } catch (NullPointerException e) {
            GuiPopupWindow.display("Error", Keys.NOTFOUND);
            return false;
        } catch (Exception e) {
            GuiPopupWindow.display("Error", e.getMessage());
            return false;
        }
    }
}
