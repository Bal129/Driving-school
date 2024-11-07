package JavaDir;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * template for the menus (admin, tutor, student)
 */

public class GuiTemplate {
    private Stage stage;
    private Button buttonBack;
    private Label labelMenuTop;
    private Label labelMenuDown;
    private VBox sideMenu;
    private HBox layoutMenuTop;
    private BorderPane borderPaneMenu;
    private Button buttonNotification;
    private StackPane notificationStack;

    // getters/accessors
    public Stage getStage() {
        return stage;
    }

    public Button getButtonBack() {
        return buttonBack;
    }

    public Label getLabelMenuTop() {
        return labelMenuTop;
    }

    public Label getLabelMenuDown() {
        return labelMenuDown;
    }

    public VBox getSideMenu() {
        return sideMenu;
    }

    public BorderPane getBorderPaneMenu() {
        return borderPaneMenu;
    }

    public Button getButtonNotification() {
        return buttonNotification;
    }

    public StackPane getNotificationStack() {
        return notificationStack;
    }

    /**
     * basic setup for the menus
     * @return
     */
    public void setup() {
        stage = new Stage();
        stage.getIcons().add(new Image(Keys.ICON));
        stage.setTitle("Motor Driving School");
        stage.setOnCloseRequest(e -> {
            e.consume();
            GuiConfirmWindow guiConfirm = new GuiConfirmWindow();
            guiConfirm.display(GuiConfirmWindow.EXIT_TITLE, GuiConfirmWindow.EXIT_MESSAGE);
            if (guiConfirm.isTrue()) {
                stage.close();
            }
        });

        // layout for top
        labelMenuTop = new Label("Menu");
        labelMenuTop.setId("layoutFont");
        labelMenuTop.setAlignment(Pos.CENTER_LEFT);

        buttonNotification = new Button();
        buttonNotification.setPadding(new Insets(5));
        buttonNotification.setAlignment(Pos.TOP_RIGHT);

        StackPane stackNotification = new StackPane();
        stackNotification.setPadding(new Insets(10));
        stackNotification.setMinWidth(700);
        stackNotification.setAlignment(Pos.TOP_RIGHT);
        stackNotification.getChildren().add(buttonNotification);

        layoutMenuTop = new HBox();
        layoutMenuTop.setAlignment(Pos.CENTER);
        layoutMenuTop.setPrefHeight(60);
        layoutMenuTop.getChildren().addAll(labelMenuTop, stackNotification);

        // layout for bottom
        labelMenuDown = new Label("You Are Signing As");
        labelMenuDown.setId("layoutFont");
        labelMenuDown.setAlignment(Pos.CENTER);
        labelMenuDown.setMinHeight(60);

        // setting up layout
        borderPaneMenu = new BorderPane();
        borderPaneMenu.setTop(layoutMenuTop);
        borderPaneMenu.setBottom(labelMenuDown);
        BorderPane.setAlignment(layoutMenuTop, Pos.CENTER);
        BorderPane.setAlignment(labelMenuDown, Pos.CENTER);

        // side menu
        sideMenu = new VBox();
        sideMenu.getStyleClass().add("borderDesign");
        sideMenu.setPrefWidth(200);
        sideMenu.setSpacing(20);
        sideMenu.setAlignment(Pos.TOP_CENTER);
        sideMenu.setPadding(new Insets(20));

        // side button
        buttonBack = new Button("Log Out");
        buttonBack.setOnAction(e-> {
            GuiSelect.display();
            stage.close();
        });

        // stage setup
        stage.setMinHeight(700);
        stage.setMinWidth(900);
    }
}