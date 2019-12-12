package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import molbyui.controls.*;

import static java.util.Arrays.asList;
import static model.PurchaseDao.getNumberPurchase;
import static molbyui.animations.TimelineAnimation.timeLineAnimation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("ConstantConditions")
public class MainController extends Application{

    private static Stage stageMain;
    public GridPaneMolby notifMenu;
    public Label labelNotifMenu;
    public Label notifLabel;
    public PaneMolby notifPane;
    public Rectangle rectangleNotif;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));

        Scene scene = new Scene(root, 600, 200);
        stageMain = stage;
        stageMain.setTitle("Administration panel");
        stageMain.setScene(scene);

        stageMain.setMaximized(true);
        stageMain.show();
    }

    @FXML
    private VBoxMolby vBoxMenu;

    @FXML
    private AnchorPaneMolby anchorView;

    @FXML
    private GridPaneMolby mainGrid;

    @FXML
    private HBoxMolby topLogo;

    @FXML
    private JFXHamburger burgerMenu;


    final Map<String, Object> listController = new HashMap<>();

    final Map<String, Boolean> listButtonAction = new HashMap<>();
    HamburgerBackArrowBasicTransition burgerTask;
    FXMLLoader loaderAnchor;
    Boolean expanded = false;

    public static Stage getStage(){
        return stageMain;
    }

    @FXML
    void initialize() {
        InitializeRippler();
        InitializeClicked();
        InitializeHover();
        InitializeHamburger();
        InitializePurchase();
        InitializeHome();
    }

    public void InitializeHome(){
        navigateTo("home");
    }

    private void InitializePurchase(){
        int total = getNumberPurchase();
        if(total != 0){
            if(expanded){
                notifMenu.setVisible(false);
            } else {
                notifMenu.setVisible(true);
            }
            labelNotifMenu.setText(String.valueOf(total));

            notifPane.setVisible(true);
            notifLabel.setText(String.valueOf(total));
        } else {
            notifMenu.setVisible(false);
            notifPane.setVisible(false);
            rectangleNotif.setVisible(false);
        }
    }

    private void InitializeRippler(){
        for(int i=0; i < vBoxMenu.count(); i++){
            RipplerMolby rippler = new RipplerMolby(vBoxMenu.getChild(i), RipplerMolby.RipplerMask.RECT, Paint.valueOf("#ffffff"));
            vBoxMenu.addChild(i,rippler);
        }
    }

    private void InitializeHamburger(){
        burgerTask = new HamburgerBackArrowBasicTransition(burgerMenu);
        burgerTask.setRate(1);
        burgerTask.play();
    }

    private void InitializeClicked(){
        for (Node ripplerMenu: vBoxMenu.getChildren()) {
            PaneMolby paneMenu = (PaneMolby) ((RipplerMolby)ripplerMenu).getChild(0);
            listButtonAction.put(paneMenu.getId(), false);
            paneMenu.setOnMouseClicked(mouseEvent -> {
                if(listButtonAction.get(((PaneMolby)mouseEvent.getSource()).getId())){
                    backgroundAnimationPane((PaneMolby) mouseEvent.getSource(), false);
                } else {
                    for (Node ripplerMenuTwo: vBoxMenu.getChildren()) {
                        PaneMolby paneMenuTwo = (PaneMolby)((RipplerMolby)ripplerMenuTwo).getChild(0);
                        backgroundAnimationPane(paneMenuTwo, false);
                    }
                    backgroundAnimationPane((PaneMolby) mouseEvent.getSource(), true);
                    navigateTo(((PaneMolby)mouseEvent.getSource()).getId().replace("button", "").toLowerCase());
                }
            });
        }
        notifPane.setOnMouseClicked(mouseEvent -> navigateTo("purchase"));
    }


    private void affectController(String path, FXMLLoader loader){
        if(!listController.containsKey(path) && listController.size() > 0) {
            listController.clear();
        }
        listController.put(path, loader.getController());
    }

    private void launchAnimationIntoController(Double duration){
        listController.forEach((key, value) -> {
            switch(key){
                case "level":
                    ((LevelController)value).burgerExpand = expanded;
                    ((LevelController)value).actionAnchor(expanded, anchorView, duration);
                    break;
                case "reward":
                    ((RewardController)value).burgerExpand = expanded;
                    ((RewardController)value).actionAnchor(expanded, anchorView, duration);
                    break;
                case "user":
                    ((UserController)value).burgerExpand = expanded;
                    ((UserController)value).actionAnchor(expanded, anchorView, duration);
                    break;
                case "group":
                    ((GroupController)value).burgerExpand = expanded;
                    ((GroupController)value).actionAnchor(expanded, anchorView, duration);
                    break;
                case "yeargroup":
                    ((YearGroupController)value).burgerExpand = expanded;
                    ((YearGroupController)value).actionAnchor(expanded, anchorView, duration);
                    break;
                case "purchase":
                    ((PurchaseController)value).burgerExpand = expanded;
                    ((PurchaseController)value).actionAnchor(expanded, anchorView, duration);
                    break;
                case "home":
                    ((HomeController)value).burgerExpand = expanded;
                    ((HomeController)value).actionAnchor(expanded, anchorView, duration);
                    break;
            }
        });

    }

    private void navigateTo(String path) {
        InitializePurchase();
        Timeline fadeOut = timeLineAnimation(250.0, anchorView.opacityProperty(), 0.0);
        fadeOut.play();
        fadeOut.onFinishedProperty().set(actionEvent -> {
            anchorView.addChilds(loaderView(path));
            Timeline fadeIn = timeLineAnimation(250.0, anchorView.opacityProperty(), 1.0);
            fadeIn.play();
            fadeIn.onFinishedProperty().set(actionEvent1 -> launchAnimationIntoController(200.0));
        });
    }

    private AnchorPane loaderView(String path){
        loaderAnchor = new FXMLLoader(getClass().getResource("../view/"+ path + "/ui.fxml"));
        AnchorPane anchorPane = null;
        try {
            anchorPane = loaderAnchor.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        affectController(path, loaderAnchor);
        return anchorPane;
    }

    private void backgroundAnimationPane(PaneMolby paneBackground, Boolean selected){
        listButtonAction.put(paneBackground.getId(), selected);
        String classHBox = (selected) ? "label-menu-hover" : "label-menu";
        Color colorHBox =  (selected) ? Color.rgb(64, 66, 68) : Color.rgb(48, 49, 51);
        ((HBoxMolby)paneBackground.getChild(1)).getChild(1).setId(classHBox);
        ((Rectangle)paneBackground.getChild(0)).setFill(colorHBox);
    }

    private void InitializeHover(){
        for (Node ripplerMenu: vBoxMenu.getChildren()) {
            PaneMolby paneMenu = (PaneMolby)((RipplerMolby)ripplerMenu).getChild(0);
            addMouseAction(paneMenu, true);
            addMouseAction(paneMenu, false);
        }
    }

    private void addMouseAction(PaneMolby paneMenu, Boolean entered){
        List<Color> colors = new ArrayList<>(asList(
                Color.rgb(48,49,51),
                Color.rgb(64,66,68)
        ));

        Color first = (entered) ? colors.get(0) : colors.get(1);
        Color second = (entered) ?  colors.get(1) : colors.get(0);

        EventType<MouseEvent> mouseEvent = (entered) ? MouseEvent.MOUSE_ENTERED : MouseEvent.MOUSE_EXITED;

        String classHBox = (entered) ? "label-menu-hover" : "label-menu";

        paneMenu.addEventHandler(mouseEvent,
        e -> {
            if(!listButtonAction.get(paneMenu.getId())){
                FillTransition menuMouseAction = new FillTransition(Duration.millis(200), (Rectangle)paneMenu.getChild(0), first,second);
                menuMouseAction.play();
                ((HBoxMolby)paneMenu.getChild(1)).getChild(1).setId(classHBox);
            }
        });
    }


    @FXML
    void burgerClicked() {
        burgerTask.setRate(burgerTask.getRate()*-1);
        burgerTask.play();
        InitializePurchase();
        Double duration = (expanded) ? 200.0 : 500.0;
        Double percentWidthMenu = (expanded) ? 20.0 : 4.5;
        Double widthMenu = (expanded) ? 384.0 : 85.0;
        timeLineAnimation(duration, mainGrid.getColumn(0).percentWidthProperty(), percentWidthMenu).play();
        timeLineAnimation(duration, topLogo.prefWidthProperty(), widthMenu).play();
        for (Node ripplerMenu: vBoxMenu.getChildren()) {
            PaneMolby paneMenu = (PaneMolby)((RipplerMolby)ripplerMenu).getChildren().get(0);
            Timeline timeLineAwait = timeLineAnimation(duration, paneMenu.prefWidthProperty(), widthMenu);
            timeLineAnimation(duration, ((Rectangle)paneMenu.getChild(0)).widthProperty(), widthMenu).play();
            timeLineAnimation(duration, ((HBoxMolby)paneMenu.getChild(1)).prefWidthProperty(), widthMenu).play();
            timeLineAwait.play();
            if(!expanded){
                if(paneMenu.getId().equals("buttonPurchase")){
                    ((Label)((GridPaneMolby)((HBoxMolby)paneMenu.getChild(1)).getChild(2)).getChild(1)).setText("");
                    ((Rectangle)((GridPaneMolby)((HBoxMolby)paneMenu.getChild(1)).getChild(2)).getChild(0)).setWidth(0);
                    ((GridPaneMolby)((HBoxMolby)paneMenu.getChild(1)).getChild(2)).setPrefWidth(0);
                    ((HBoxMolby)paneMenu.getChild(1)).getChild(2).setVisible(false);
                }
            } else {
                timeLineAwait.onFinishedProperty().set(actionEvent -> {
                    if(paneMenu.getId().equals("buttonPurchase")){
                        timeLineAnimation(0.1, ((GridPaneMolby)((HBoxMolby)paneMenu.getChild(1)).getChild(2)).prefWidthProperty(), 90.0).play();
                        Timeline timeLineGridPane = timeLineAnimation(0.1, ((Rectangle)((GridPaneMolby)((HBoxMolby)paneMenu.getChild(1)).getChild(2)).getChild(0)).widthProperty(), 30.0);
                        timeLineGridPane.play();
                        timeLineGridPane.onFinishedProperty().set(actionEvent1 -> ((HBoxMolby) paneMenu.getChild(1)).getChild(2).setVisible(true));
                    }
                });
            }
        }
        expanded = !expanded;
        if(anchorView.count() != 0) {
            launchAnimationIntoController(null);
        }
    }


}
