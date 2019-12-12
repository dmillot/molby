package controller;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.jfoenix.validation.RequiredFieldValidator;
import entity.User;
import javafx.animation.RotateTransition;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;
import model.UserDao;
import molbyui.controls.*;
import molbyui.customs.CreateCustomUser;
import row.RowMolbyUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static molbyui.animations.TimelineAnimation.timeLineAnimation;
import static molbyui.controls.DropShadowMolby.dropShadowIconRow;
import static row.RowMolby.buttonActionRow;

@SuppressWarnings("ALL")
public class UserController {

    @FXML
    private ImageViewMolby leftUp;
    @FXML
    private ImageViewMolby rightUp;
    @FXML
    private ImageViewMolby leftDown;
    @FXML
    private ImageViewMolby rightDown;
    @FXML
    private ListViewMolby listViewUser;
    @FXML
    private ImageViewMolby buttonAdd;
    @FXML
    private GridPaneMolby gridMenu;
    @FXML
    private GridPaneMolby loadingScreen;
    @FXML
    private AnchorPaneMolby anchorUser;

    @FXML
    private StackPane stackDialog;

    private CreateCustomUser custom;
    private JFXDialogLayout content;

    private Boolean expanded = false;
    private Boolean animationExpanded = false;
    private JFXDialog dialog;

    public Boolean burgerExpand = false;


    @FXML
    void buttonExpand_Clicked(MouseEvent event) {
        if(!animationExpanded) {
            animationExpanded = true;
            int degres = 180;
            final Map<ImageViewMolby, Integer> arrayDegres = (expanded) ? new HashMap<>() {
                {
                    put(leftUp, -degres);
                    put(rightUp, degres);
                    put(leftDown, degres);
                    put(rightDown, -degres);
                }
            } : new HashMap<>() {
                {
                    put(leftUp, degres);
                    put(rightUp, -degres);
                    put(leftDown, -degres);
                    put(rightDown, degres);
                }
            };
            expanded = !expanded;
            arrayDegres.forEach((k, v) -> {
                RotateTransition rt = new RotateTransition(Duration.millis(500), k);
                rt.setByAngle(v);
                rt.play();
                rt.onFinishedProperty().set(actionEvent -> {
                    animationExpanded = false;
                    Double valueVertical = (expanded) ? 20.0 : 0.0;
                    timeLineAnimation(50.0, listViewUser.verticalGapProperty(), valueVertical).play();
                    if(expanded){
                        listViewUser.refresh();
                    }
                });
            });
        }
    }




    public void initialize() throws ExecutionException, InterruptedException {
        InitializeDataListView();
        InitializeDialogs();
    }


    private void openDialog(GridPaneMolby gridPaneMolby){
        content.setBody(gridPaneMolby);
        dialog.setContent(content);
        dialog.show();
    }

    public void closeDialog(){
        listViewUser.getSelectionModel().clearAndSelect(-1);
        dialog.close();
    }
    
    private void InitializeDataListView() throws ExecutionException, InterruptedException {
        listViewUser.setParam(true, 0.0, 5);
        loadingScreen.setVisible(true);
        Task<ArrayList<User>> task = new Task<ArrayList<User>>() {
            @Override
            public ArrayList<User> call() {
                return UserDao.getUsers();
            }
        };
        new Thread(task).start();
        task.onSucceededProperty().set(workerStateEvent -> {
            try {
                listViewUser.getItems().addAll(RowMolbyUser.createRows(task.get(),burgerExpand));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            RowMolbyUser.controller = this;
            loadingScreen.setVisible(false);
        });
    }

    private void InitializeDialogs(){
        custom = new CreateCustomUser();
        custom.setController(this);
        content = new JFXDialogLayout();
        dialog = new JFXDialog(stackDialog, null,JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent jfxDialogEvent) {
                stackDialog.setDisable(true);
                stackDialog.setVisible(false);
            }
        });
        dialog.setOnDialogOpened(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent jfxDialogEvent) {
                stackDialog.setDisable(false);
                stackDialog.setVisible(true);
            }
        });
    }


    public void actionAnchor(Boolean expand, AnchorPaneMolby anchorView, Double duration){
        Double valueAction = (expand) ? 1831.0 : 1536.0;
        Double duree = (expand) ? (duration != null) ? duration : 500.0 : 200.0;
        Double columnFirst = (expand) ? 250.0 : 200.0;
        Double columnLast = (expand) ? 240.0 : 175.0;
        AnchorPaneMolby anchorLoader = (AnchorPaneMolby) anchorView.getChild(0);
        GridPaneMolby gridPaneList = (GridPaneMolby) anchorLoader.getChild(0);
        if(duration == null) {
            timeLineAnimation(duree, anchorLoader.prefWidthProperty(), valueAction).play();
            timeLineAnimation(duree, ((GridPaneMolby) anchorLoader.getChild(0)).prefWidthProperty(), valueAction).play();
            for (int i = 1; i < 3; i++) {
                timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).prefWidthProperty(), columnFirst).play();
                timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).maxWidthProperty(), columnFirst).play();
            }
            for (int i = 4; i < 7; i++) {
                timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).prefWidthProperty(), columnLast).play();
                timeLineAnimation(duree, ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).maxWidthProperty(), columnLast).play();
            }
            for (Object paneList : listViewUser.getItems()) {
                for (int i = 1; i < 3; i++) {
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).prefWidthProperty(), columnFirst).play();
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).maxWidthProperty(), columnFirst).play();
                }
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(2)).translateXProperty(), (expand) ? 50.0 : 0).play();
                timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(3)).translateXProperty(), (expand) ? 100.0 : 0).play();
                Double positionXLast = 100.0;
                for (int i = 4; i < 7; i++) {
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).prefWidthProperty(), columnLast).play();
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).maxWidthProperty(), columnLast).play();
                    timeLineAnimation(duree, ((Label) ((PaneMolby) paneList).getChild(i)).translateXProperty(), (expand) ? positionXLast : 0).play();
                    positionXLast += 65.0;
                }
                timeLineAnimation(duree, ((PaneMolby) paneList).getChild(7).translateXProperty(), (expand) ? 295.0 : 0).play();
            }
            timeLineAnimation(duree, ((StackPane) anchorLoader.getChild(1)).prefWidthProperty(), valueAction).play();
        } else {
            anchorLoader.setPrefWidth(valueAction);
            ((GridPaneMolby) anchorLoader.getChild(0)).setPrefWidth(valueAction);
            for (int i = 1; i < 3; i++) {
                ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).setPrefWidth(columnFirst);
                ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).setMaxWidth(columnFirst);
            }
            for (int i = 4; i < 7; i++) {
                ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).setPrefWidth(columnLast);
                ((GridPaneMolby) gridPaneList.getChild(1)).getColumn(i).setMaxWidth(columnLast);
            }
            for (Object paneList : listViewUser.getItems()) {
                for (int i = 1; i < 3; i++) {
                    ((Label) ((PaneMolby) paneList).getChild(i)).setPrefWidth(columnFirst);
                    ((Label) ((PaneMolby) paneList).getChild(i)).setMaxWidth(columnFirst);
                }
                ((Label) ((PaneMolby) paneList).getChild(2)).setTranslateX((expand) ? 50.0 : 0);
                ((Label) ((PaneMolby) paneList).getChild(3)).setTranslateX((expand) ? 100.0 : 0);
                Double positionXLast = 100.0;
                for (int i = 4; i < 7; i++) {
                    ((Label) ((PaneMolby) paneList).getChild(i)).setPrefWidth(columnLast);
                    ((Label) ((PaneMolby) paneList).getChild(i)).setMaxWidth(columnLast);
                    ((Label) ((PaneMolby) paneList).getChild(i)).setTranslateX((expand) ? positionXLast : 0);
                    positionXLast += 65.0;
                }
                ((PaneMolby) paneList).getChild(7).setTranslateX((expand) ? 295.0 : 0);
            }
            ((StackPane) anchorLoader.getChild(1)).setPrefWidth(valueAction);
        }
    }


    public void createDialogAddEdit(User user){
        GridPaneMolby gridDialog = new GridPaneMolby("contentDialog", 500.0, 450.0, 7, 3, new HashMap<>(){{
            put(2,20.0);
            put(5,20.0);
            put(7,40.0);
        }}, new HashMap<>(){{
            put(0,50.0);
            put(2,50.0);
        }});

        Font font = Font.font("Segoe UI Light", 25.0);
        Font fontLabel = Font.font("Segoe UI Light", 25.0);
        Insets padding = new Insets(0);
        Color colorGray= Color.rgb(220, 220, 220, 0.8);
        Color colorMain = Color.rgb(0,0,0);

        RequiredFieldValidator required = new RequiredFieldValidator();
        required.setMessage("Field must be completed.");

        String imageURL = "./src/view/img/user/" + user.getId() + ".jpg";
        String valueButton = "UPDATE";


        TextFieldMolby xpADD = custom.InitializeTextField(font, String.valueOf(100), required, true, 0.0, 6);
        TextFieldMolby xpLESS = custom.InitializeTextField(font, String.valueOf(100), required, true, 0.0, 2, "jfx-text-field-second");
        LabelMolby nameLabel = new LabelMolby(user.getName() + " " + user.getFirstName(),Font.font("Segoe UI", 35.0), colorMain, DropShadowMolby.dropShadowLabel(colorGray), 1, 1, HPos.CENTER);

        ImageViewMolby imageViewMolby = custom.createImageViewMolby(imageURL, 1, 0, HPos.CENTER, VPos.TOP, Cursor.DEFAULT);
        ButtonMolby buttonClose = custom.createButtonClose(2, 0, HPos.RIGHT, VPos.TOP);

        HBoxMolby gridLabelXP = custom.createHBox(200.0,50.0, 1, 3, padding).addChildChaining(
                new LabelMolby("His experience: ", fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
        ).addChildChaining(
                new LabelMolby(String.valueOf(user.getExperience()), fontLabel, colorMain, DropShadowMolby.dropShadowLabel(colorGray))
        );

        GridPaneMolby gridButtonAction = custom.createGridPaneChild(100.0,1, 6,  null, new HashMap<Integer, Double>() {{ put(0, 50.0); put(1, 10.0); put(2, 125.0); put(3, 25.0);  put(4, 50.0);  put(5, 10.0); put(6, 100.0);}}, 1, 4);
        gridButtonAction.addChildChaining(
                buttonActionRow("button-remove-xp", new ImageViewMolby("./src/view/img/minus.png", false, true, 20.0, dropShadowIconRow()),  mouseEvent -> xpAction((LabelMolby)gridLabelXP.getChild(1), xpLESS, true, user.getExperience()), Paint.valueOf("#670017"), 50.0, 0)
        ).addChildChaining(xpLESS).addChildChaining(
                buttonActionRow("button-add-xp", new ImageViewMolby("./src/view/img/plus.png", false, true, 20.0, dropShadowIconRow()),  mouseEvent -> xpAction((LabelMolby)gridLabelXP.getChild(1), xpADD, false, user.getExperience()) ,Paint.valueOf("#003988") , 50.0, 4)
        ).addChildChaining(xpADD);


        GridPaneMolby gridButton = custom.createGridPaneChild(1, 3,  null, new HashMap<Integer, Double>() {{ put(1, 25.0);}}, 1, 6);
        gridButton.addChildChaining(
                custom.createButtonAddEdit(0, 0, HPos.CENTER, VPos.CENTER, font, 50.0, 250.0, valueButton, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Integer indexUserUpdated = null;
                        for(Object pane : listViewUser.getItems()){
                            if(user.getId() == Integer.parseInt((((PaneMolby)pane).getId()).replace("paneUser",""))){
                                indexUserUpdated = listViewUser.getItems().indexOf(pane);
                                listViewUser.getItems().remove(pane);
                                break;
                            }
                        }
                        user.setExperience(Integer.parseInt(((LabelMolby)gridLabelXP.getChild(1)).getText()));
                        UserDao.update(user);
                        listViewUser.getItems().add(indexUserUpdated, RowMolbyUser.createRow(user, burgerExpand));
                        listViewUser.refresh();
                        closeDialog();
                    }
                })).addChildChaining(
                custom.createButtonCancel(2, 0, HPos.CENTER, VPos.CENTER, font, 50.0, 250.0)
        );


        GridPaneMolby gridPaneMolby = gridDialog.addChildChaining(imageViewMolby).addChildChaining(buttonClose).addChildChaining(nameLabel).addChildChaining(gridLabelXP).addChildChaining(gridButtonAction).addChildChaining(gridButton);
        openDialog(gridPaneMolby);

    }

    private void xpAction (LabelMolby display, TextFieldMolby text, Boolean remove, Integer xpUser){
        if(text.validate()){
            Integer valueAction = (remove) ? -(Integer.parseInt(text.getText())) : Integer.parseInt(text.getText());
            Integer result = Integer.parseInt(display.getText()) + valueAction;
            if(result >= 0) {
                display.setText(String.valueOf(Integer.parseInt(display.getText()) + valueAction));
            } else {
                text.setUnFocusColor(Color.RED);
            }
        }
    }
}
